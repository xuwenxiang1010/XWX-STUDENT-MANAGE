package com.wx.springboot.workplace.books.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wx.springboot.workplace.books.enums.CategoryEnum;
import com.wx.springboot.workplace.books.enums.NatureEnum;
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

    private String bookName;

    private Integer bookNature;

    private Integer bookCategory;

    private Integer stock;

    private String positionId;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    private Integer deleted;

    @TableField(exist = false)
    private String natureName;

    @TableField(exist = false)
    private String categoryName;

    @TableField(exist = false)
    private String position;

    public String getNatureName() {
        return NatureEnum.valueByCode(getBookNature());
    }

    public String getCategoryName() {
        return CategoryEnum.valueByCode(getBookCategory());
    }
}
