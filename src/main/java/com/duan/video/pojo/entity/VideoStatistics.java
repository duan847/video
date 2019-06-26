package com.duan.video.pojo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 视频统计
 *
 * @author duanjw
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class VideoStatistics implements Serializable {

    /**
     * 视频统计id
     */
    @ApiModelProperty("视频统计id")
    private Long id;

    /**
     * 类型
     */
    @ApiModelProperty("类型")
    private Integer type;

    /**
     * 时间
     */
    @ApiModelProperty("时间")
    private Date time;

    /**
     * 数量
     */
    @ApiModelProperty("数量")
    private Integer count;
}
