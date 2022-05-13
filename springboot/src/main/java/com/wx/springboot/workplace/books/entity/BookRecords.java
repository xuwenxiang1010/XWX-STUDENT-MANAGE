package com.wx.springboot.workplace.books.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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

}
