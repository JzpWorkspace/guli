package com.atguigu.guli.service.edu.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "Teacher查询对象",description = "讲师查询的对象封装")
@Data
public class TeacherQueryVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "讲师姓名")
    private String name;

    @ApiModelProperty(value = "讲师级别")
    private Integer level;

    @ApiModelProperty(value = "查询时间段的开始时间")
    private String beginDate;

    @ApiModelProperty(value = "查询时间段的结束时间")
    private String endDate;


}
