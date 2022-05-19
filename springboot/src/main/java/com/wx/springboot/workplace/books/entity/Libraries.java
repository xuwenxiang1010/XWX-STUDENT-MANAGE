package com.wx.springboot.workplace.books.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author wyb
 */
@Data
@TableName(value = "book_libraries")
public class Libraries {
    @TableId(type = IdType.INPUT)
    private String id;

    private String fatherId;

    private String name;

    private Date createTime;

    private Date updateTime;

    private Integer deleted;

    @TableField(exist = false)
    private Integer flower;
    @TableField(exist = false)
    private Integer room;
}
