package com.duan.video.pojo.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 视频下载地址实体
 *
 * @author duanjw
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class DownUrl extends Model<DownUrl> {

    /**
     * 视频下载地址id
     */
    @ApiModelProperty("视频下载地址id")
    private Long id;

    /**
     * 视频id
     */
    @ApiModelProperty("视频id")
    private Long videoId;

    /**
     * 视频名称
     */
    @ApiModelProperty("视频名称")
    private String name;

    /**
     * 下载地址
     */
    @ApiModelProperty("下载地址")
    private String url;

    @Override
    protected Serializable pkVal() {
        return id;
    }
}
