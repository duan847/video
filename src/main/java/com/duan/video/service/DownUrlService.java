package com.duan.video.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.duan.video.pojo.entity.DownUrl;

/**
 * 视频下载地址service接口
 *
 * @author duanjw
 */
public interface DownUrlService extends IService<DownUrl> {

    /**
     * 根据id分页查询视频下载地址
     * @param page
     * @param id
     * @return
     */
    IPage<DownUrl> selectByVideoIdPage(Page page, Long id);

    /**
     * 根据视频id删除视频下载地址
     * @param videoId
     * @return
     */
    boolean deleteByVideoId(Long videoId);
}
