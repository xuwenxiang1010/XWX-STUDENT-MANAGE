package com.wx.springboot.workplace.books.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author dell
 */
@Data
public class BookRecordsVo {

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
     * 选择最初时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private String start;
    /**
     * 选择截止时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private String end;
}
