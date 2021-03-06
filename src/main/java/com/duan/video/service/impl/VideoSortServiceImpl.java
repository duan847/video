package com.duan.video.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duan.video.common.Constants;
import com.duan.video.mapper.VideoSortMapper;
import com.duan.video.pojo.entity.Video;
import com.duan.video.pojo.entity.VideoSort;
import com.duan.video.service.VideoService;
import com.duan.video.service.VideoSortService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 视频排序service实现
 *
 * @author duanjw
 */
@Slf4j
@Service
public class VideoSortServiceImpl extends ServiceImpl<VideoSortMapper, VideoSort> implements VideoSortService {

    @Autowired
    private VideoService videoService;

    /**
     * 更新不同类型的视频排序
     *
     * @param type
     * @param page
     * @return
     */
    @Override
    public boolean updateByType(Integer type, Integer page) {
        String url = null;
        String typeName = "";
        boolean flag = true;
        switch (type) {
            case Constants.MOVIE_HOT:
                url = "http://api.douban.com/v2/movie/in_theaters";
                typeName = "热映电影";
                break;
            case Constants.MOVIE_TOP250:
                url = "http://api.douban.com/v2/movie/top250";
                typeName = "top250";
                break;
            case Constants.MOVIE_COMING:
                url = "http://api.douban.com/v2/movie/coming_soon";
                typeName = "即将上映";
                break;
            case Constants.MV_HOT:
                url = "https://movie.douban.com/j/search_subjects?type=tv&tag=%E7%83%AD%E9%97%A8&page_limit=50&page_start=0";
                typeName = "热播电视剧";
                flag = false;
                break;
            case Constants.MOVIE_RECENT_HOT:
                url = "https://movie.douban.com/j/search_subjects?type=movie&tag=%E7%83%AD%E9%97%A8&page_limit=50&page_start=0";
                typeName = "最近热门电影";
                flag = false;
                break;
            case Constants.VARIETY_SHOW_HOT:
                url = "https://movie.douban.com/j/search_subjects?type=tv&tag=综艺&page_limit=50&page_start=0";
                typeName = "热播综艺";
                flag = false;
                break;

        }
        log.info("{}，开始更新", typeName);
        Integer count = 100;
        List<Map<String, Object>> list = new ArrayList<>();

        do {
            Integer start = (page - 1) * count;
            //获取请求连接
            Document document = null;
            String newUrl = url + "?start=" + start + "&count=" + count + "&apikey=0df993c66c0c636e29ecbb5344252a4a";
            try {
                document = Jsoup.parse(new URL(newUrl).openStream(), "UTF-8", newUrl);
            } catch (IOException e) {
                log.error("{}更新失败", typeName);
            }

            if (null == document) {
                flag = false;
                break;
            }
            JSONObject jsonObject = JSONObject.parseObject(document.text());
            JSONArray jsonArray = jsonObject.getJSONArray("subjects");
            if (jsonArray.size() == 0) {
                flag = false;
            }
            jsonArray.forEach(item -> {
                Map map = (Map) item;
                list.add(map);
            });
            page += 1;
        } while (flag);

        List<VideoSort> videoSortList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> stringObjectMap = list.get(i);
            if (null == stringObjectMap) {
                continue;
            }
            List<Video> list1 = videoService.list(new QueryWrapper<Video>().lambda().eq(Video::getName, stringObjectMap.get("title").toString()+stringObjectMap.get("year")).or().eq(Video::getName, stringObjectMap.get("title").toString()));

            if (list1.size() > 0) {
                Video video = null;
                //如果匹配到多个视频，再根据年份匹配，
                if (list1.size() > 1) {
                    try {
                        for (Video item : list1) {
                            if (item.getYear().equals(stringObjectMap.get("year").toString())) {
                                video = item;
                                break;
                            }
                        }
                        if(video == null) {
                            video = list1.get(0);
                        }
                    } catch (Exception e) {
                        video = list1.get(0);
                    }
                } else {
                    video = list1.get(0);
                }

                //如果没有同步过豆瓣的视频信息，同步豆瓣的评分、简介。并更新
                if (null == video.getDoubanSyncTime()) {
                    String doubanId = stringObjectMap.get("id").toString();
                    JSONObject doubanVideoDetail = videoService.getByDoubanId(doubanId);
                    if (null == doubanVideoDetail) {
                        log.error("同步豆瓣视频信息，但是没有找到，豆瓣id：{}", doubanId);
                        continue;
                    }
                    Video videoNew = new Video();
                    BigDecimal doubanScore = doubanVideoDetail.getJSONObject("rating").getBigDecimal("average");
                    videoNew.setId(video.getId()).setScore(doubanScore).setSynopsis(doubanVideoDetail.getString("summary")).setDoubanId(doubanId);

                    //豆瓣分数不是0才设置同步时间
                    if (null != doubanScore && doubanScore.compareTo(BigDecimal.ZERO) > 0) {
                        videoNew.setDoubanSyncTime(DateUtil.date());
                    }
                    videoNew.updateById();
                    log.info("同步豆瓣信息成功，视频名：{}", stringObjectMap.get("title"));
                }
                videoSortList.add(new VideoSort().setSort(i).setType(type).setVideoId(video.getId()));
            }
        }
        log.info("{},更新条数：{}", typeName, videoSortList.size());
        if (videoSortList.size() > 0) {
            //先删除，再新增
            super.remove(new QueryWrapper<VideoSort>().lambda().eq(VideoSort::getType, type));
        }
        return super.saveBatch(videoSortList);
    }

    /**
     * 更新所有排序
     *
     * @return
     */
    @Override
    public boolean updateAllSort() {
        updateByType(Constants.MOVIE_HOT, 1);
        updateByType(Constants.MOVIE_TOP250, 1);
        updateByType(Constants.MV_HOT, 1);
        updateByType(Constants.MOVIE_RECENT_HOT, 1);
        updateByType(Constants.VARIETY_SHOW_HOT, 1);
        return true;
    }

    /**
     * 根据视频id删除视频排序
     *
     * @param videoId
     * @return
     */
    @Override
    public boolean deleteByVideoId(Long videoId) {
        return remove(new QueryWrapper<VideoSort>().lambda().eq(VideoSort::getVideoId, videoId));
    }
}
