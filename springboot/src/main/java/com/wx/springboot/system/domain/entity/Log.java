package com.wx.springboot.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author xwx
 * @since 2023-04-25
 */
@TableName("sys_log")
@ApiModel(value = "Log对象", description = "")
@Data
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("id")
      @TableId(type = IdType.ASSIGN_ID)
      private String id;

      @ApiModelProperty("操作人")
      private String operationUser;

      @ApiModelProperty("路径")
      private String path;

      @ApiModelProperty("执行时间")
      private String time;

      @ApiModelProperty("方法入参")
      private String parameter;

      @ApiModelProperty("操作方法")
      private String title;

      @ApiModelProperty("方法描述")
      private String action;

      @ApiModelProperty("操作类型")
      private Integer type;

    public Log(String operationUser, String path, String time,
                  String parameter, String title, String action, Integer type) {
      super();
      this.operationUser = operationUser;
      this.path = path;
      this.time = time;
      this.parameter = parameter;
      this.title = title;
      this.action = action;
      this.type = type;
    }

}
