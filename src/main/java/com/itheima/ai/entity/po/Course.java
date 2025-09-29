package com.itheima.ai.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 学科表
 * </p>
 *
 * @author Xiaoyi
 * @since 2025-09-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("course")
@ApiModel(value="Course对象", description="学科表")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "学科名称")
    private String name;

    @ApiModelProperty(value = "学历背景要求：0-无，1-初中，2-高中、3-大专、4-本科以上")
    private Integer edu;

    @ApiModelProperty(value = "课程类型：编程、设计、自媒体、其它")
    private String type;

    @ApiModelProperty(value = "课程价格")
    private Long price;

    @ApiModelProperty(value = "学习时长，单位: 天")
    private Integer duration;


}
