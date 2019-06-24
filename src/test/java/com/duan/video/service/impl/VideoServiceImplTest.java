package com.duan.video.service.impl;

import cn.hutool.core.io.file.FileReader;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.duan.video.VideoApplication;
import com.duan.video.common.Query;
import com.duan.video.mapper.VideoMapper;
import com.duan.video.pojo.entity.Video;
import com.duan.video.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = VideoApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Slf4j
public class VideoServiceImplTest {

    @Autowired
    private VideoService videoService;


    @Autowired
    private VideoMapper videoMapper;
    @Test
    public void searchByName() {
        log.info("视频地址：{}",videoService.searchByName("下一任"));
    }

    @Test
    public void start() {
        videoService.start(80617,80617);
    }

    @Test
    public void getById() {
        for (int i = 0; i < 10; i++) {
            videoService.crawByNo(i,null);
        }
    }


    @Test
    public void douban() throws IOException {
        Document document = Jsoup.connect("https://movie.douban.com/subject/26100958/").get();
        log.info("豆瓣：{}",document.html());
    }

    @Test
    public void getByDoubanId() {
        videoService.getByDoubanId("1");
    }

    @Test
    public void selectHotPage() {
        Map map = new HashMap<>();
        map.put("size",10);
    }

    @Test
    public void deleteAllInfoById() {
//        List<Video> videoList = videoService.list(new QueryWrapper<Video>().lambda().last("LIMIT 50").orderByDesc(Video::getNo));
//        videoList.forEach(item->{
//            item.deleteById();
//            videoService.deleteAllInfoById(item.getId());
//
//        });
        videoService.removeById(1137630418405691393L);
        videoService.deleteAllInfoById(1137630418405691393L);
    }


    @Test
    public void selectRemarksByIds(){
        videoMapper.selectRemarksByIds(Arrays.asList(1L));
    }

    @Test
    public void getAddCountByDate(){
        Integer count = videoMapper.getAddCountByStartDayAdnEndDay(new Date(),new Date());
        log.info("今日新增视频数：{}", count);
    }
    @Test
    public void getUpdateCountByDate(){
        Integer count = videoMapper.getUpdateCountByStartDayAdnEndDay(new Date(),new Date());
        log.info("今日更新视频数：{}", count);
    }

}
