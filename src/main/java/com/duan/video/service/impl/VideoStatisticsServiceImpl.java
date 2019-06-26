package com.duan.video.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duan.video.mapper.VideoStatisticsMapper;
import com.duan.video.pojo.entity.VideoStatistics;
import com.duan.video.service.VideoStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 视频统计service接口
 * @author duanjw
 */
@Slf4j
@Service
public class VideoStatisticsServiceImpl extends ServiceImpl<VideoStatisticsMapper, VideoStatistics> implements VideoStatisticsService {
}
