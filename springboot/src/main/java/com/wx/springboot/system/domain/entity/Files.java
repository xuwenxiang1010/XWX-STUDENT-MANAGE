package com.wx.springboot.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.models.auth.In;
import lombok.Data;

/**
 * @authoer:xwx
 * @createTime: 2022/5/5/005 16:52
 * @description:
 */
@Data
@TableName("sys_file")
public class Files {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String name;
    private String type;
    private Long size;
    private String url;
    private Integer deleted;
    private boolean status;
}
