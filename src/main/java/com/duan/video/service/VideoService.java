package com.duan.video.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.duan.video.common.Query;
import com.duan.video.pojo.entity.Incompletion;
import com.duan.video.pojo.entity.Video;
import com.duan.video.pojo.vo.VideoDetailVO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 学生service接口
 *
 * @author duanjw
 */
public interface VideoService extends IService<Video> {

    List<Video> searchByName(String name);

    /**
     * 开始爬取
     *
     * @return
     */
    String start(Integer startNo, Integer endNo);

    /**
     *
     * 根据视频编号多线程爬取视频，并保存到数据库
     * @param no 视频编号
     * @param videoId
     */
    void crawByNo(Integer no,Long videoId);

    /**
     * 分页查询视频-简单信息
     *
     * @param query 分页参数、条件
     * @return
     */
    Page<Video> selectSimplePage(Query query);

    /**
     * 分页查询视频-详细信息
     *
     * @param query 分页参数、条件
     * @return
     */
    Page<VideoDetailVO> selectDetailPage(Query query);

    /**
     * 根据类型分页查询视频
     * @param query
     * @return
     */
    Page<VideoDetailVO> selectSortPage(Query query);

    /**
     * 根据id查看视频详细
     * @param id
     * @return
     */
    VideoDetailVO getDetailById(Long id);

    /**
     * 根据豆瓣id获取视频信息
     * @param doubanId
     * @return
     */
    JSONObject getByDoubanId(String doubanId);

    /**
     * 根据id更新视频所有信息
     * @param id
     * @return
     */
    boolean updateAllInfoById(Long id);

    /**
     * 根据id删除视频所有信息
     * @param id
     * @return
     */
    boolean deleteAllInfoById(Long id);

    /**
     * 爬取最新的视频
     * @return
     */
    boolean crawNow();

    /**
     * updateByIncompletionList
     *
     * @return
     * @param incompletionList
     */
    void updateByIncompletionList(List<Incompletion> incompletionList);

    /**
     * 根据视频信息更新视频播放地址
     * @param video
     * @return
     */
    boolean updateUrlByVideo(Video video);

    /**
     * 查询所有数量
     * @return
     */
    Map getAllCount();

    /**
     * 根据ids查询视频备注
     * @param videoIds
     * @return
     */
    List<Video> selectRemarksByIds(List<Long> videoIds);
}
