package com.wx.springboot.workplace.books.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wx.springboot.workplace.books.controller.BookRecordsController;
import com.wx.springboot.workplace.books.controller.LibrariesController;
import com.wx.springboot.workplace.books.enums.CategoryEnum;
import com.wx.springboot.workplace.books.enums.NatureEnum;
import com.wx.springboot.workplace.books.service.impl.BookRecordsServiceImpl;
import lombok.Data;

import java.util.Date;

/**
 * @author wyb
 */
@Data
@TableName("book_records")
public class BookRecords {

    /**
     * 主键
     */
    @TableId(type= IdType.ASSIGN_ID)
    private String id;

    /**
     * 图书编码
     */
    private String bookCode;

    /**
     * 图书名称
     */
    private String bookName;

    /**
     * 图书性质
     */
    private Integer bookNature;

    /**
     * 图书类别
     */
    private Integer bookCategory;

    /**
     * 图书库存
     */
    private Integer stock;

    /**
     * 图书最后位置地址ID
     */
    private String positionCode;

    /**
     * 图书创建时间
     */
    private Date createTime;
    /**
     * 图书修改时间
     */
    private Date updateTime;
    /**
     * 图书是否展示
     */
    private Integer deleted;
    /**
     * 图书性质名称
     */
    @TableField(exist = false)
    private String natureName;

    /**
     * 图书类别名称
     */
    @TableField(exist = false)
    private String categoryName;
    /**
     * 图书放置点
     */
    @TableField(exist = false)
    private String position;

    public String getNatureName() {
        return NatureEnum.valueByCode(getBookNature());
    }

    public String getCategoryName() {
        return CategoryEnum.valueByCode(getBookCategory());
    }

    @TableField(exist = false)
    private String libId;

    @TableField(exist = false)
    private Integer flower;

    @TableField(exist = false)
    private Integer room;

    @TableField(exist = false)
    private Integer bookShelf;

    @TableField(exist = false)
    private Integer layer;

}
