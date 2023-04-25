package com.wx.springboot.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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

    
    public String getId() {
        return id;
    }

      public void setId(String id) {
          this.id = id;
      }
    
    public String getOperationUser() {
        return operationUser;
    }

      public void setOperationUser(String operationUser) {
          this.operationUser = operationUser;
      }
    
    public String getPath() {
        return path;
    }

      public void setPath(String path) {
          this.path = path;
      }
    
    public String getTime() {
        return time;
    }

      public void setTime(String time) {
          this.time = time;
      }
    
    public String getParameter() {
        return parameter;
    }

      public void setParameter(String parameter) {
          this.parameter = parameter;
      }
    
    public String getTitle() {
        return title;
    }

      public void setTitle(String title) {
          this.title = title;
      }
    
    public String getAction() {
        return action;
    }

      public void setAction(String action) {
          this.action = action;
      }
    
    public Integer getType() {
        return type;
    }

      public void setType(Integer type) {
          this.type = type;
      }

    @Override
    public String toString() {
        return "Log{" +
              "id=" + id +
                  ", operationUser=" + operationUser +
                  ", path=" + path +
                  ", time=" + time +
                  ", parameter=" + parameter +
                  ", title=" + title +
                  ", action=" + action +
                  ", type=" + type +
              "}";
    }
}
