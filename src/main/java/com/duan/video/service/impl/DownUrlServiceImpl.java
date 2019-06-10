package com.duan.video.service.impl;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ReUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duan.video.mapper.DownUrlMapper;
import com.duan.video.mapper.RouteUrlMapper;
import com.duan.video.pojo.entity.DownUrl;
import com.duan.video.pojo.entity.RouteUrl;
import com.duan.video.service.DownUrlService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 视频下载地址service实现
 *
 * @author duanjw
 */
@Slf4j
@Service
public class DownUrlServiceImpl extends ServiceImpl<DownUrlMapper, DownUrl> implements DownUrlService {

    @Autowired
    private DownUrlMapper downUrlMapper;

    /**
     * 根据id分页查询视频下载地址
     *
     * @param page
     * @param id
     * @return
     */
    @Override
    public IPage<DownUrl> selectByVideoIdPage(Page page, Long id) {
        return downUrlMapper.selectPage(page, new QueryWrapper<DownUrl>().lambda().eq(DownUrl::getVideoId, id).orderByAsc(DownUrl::getName));

    }

    /**
     * 根据视频id删除视频下载地址
     * @param videoId
     * @return
     */
    @Override
    public boolean deleteByVideoId(Long videoId) {
        return remove(new QueryWrapper<DownUrl>().lambda().eq(DownUrl::getVideoId, videoId));
    }
}
