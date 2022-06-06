package com.wx.springboot.workplace.books.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author dell
 */
@Data
@TableName("book_borrow")
public class BookBorrowing {

    /**
     * 主键
     */
    @TableId(type= IdType.ASSIGN_ID)
    private String id;

    private String peopleName;

    private String days;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 是否展示
     */
    private Integer deleted;

    @TableField(exist = false)
    private List<String> bookIds;
}
