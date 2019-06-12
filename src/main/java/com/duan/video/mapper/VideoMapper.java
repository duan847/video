package com.duan.video.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.duan.video.common.Query;
import com.duan.video.pojo.entity.Video;
import com.duan.video.pojo.vo.VideoDetailVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 视频mapper
 *
 * @author duanjw
 */
public interface VideoMapper extends BaseMapper<Video> {

    /**
     * 分页查询视频-简单信息
     * @param query 分页参数、条件
     * @return
     */
    List<Video> selectSimplePage(Query query);


    /**
     * 分页查询视频-详细信息
     * @param query 分页参数、条件
     * @return
     */
    List<VideoDetailVO> selectDetailPage(Query query);

    /**
     * 根据id查看视频详细
     * @param id
     * @return
     */
    VideoDetailVO getDetailById(Long id);

    /**
     * 分页查询排序视频
     *
     * @param query 分页参数、条件
     * @return
     */
    List<VideoDetailVO> selectSortPage(Query query);

    /**
     * 根据ids查询视频备注
     * @param ids
     * @return
     */
    List<Video> selectRemarksByIds(@Param("ids") List<Long> ids);


    /**
     * 查看某天到某天的新增视频数
     * @param startDate
     * @param endDate
     * @return
     */
    Integer getAddCountByStartDayAdnEndDay(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    /**
     * 查看某天到某天的新增视频数
     * @param startDate
     * @param endDate
     * @return
     */
    Integer getUpdateCountByStartDayAdnEndDay(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
