package com.wx.springboot.workplace.books.dto;

import com.wx.springboot.workplace.books.enums.CategoryEnum;
import com.wx.springboot.workplace.books.enums.NatureEnum;
import lombok.Data;
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
    private String start;
    /**
     * 选择截止时间
     */
    private String end;
    /**
     * 图书性质名称
     */
    private String natureName;
    /**
     * 图书类别名称
     */
    private String categoryName;

    private String position;

    public String getNatureName() {
        return NatureEnum.valueByCode(getBookNature());
    }

    public String getCategoryName() {
        return CategoryEnum.valueByCode(getBookCategory());
    }
}
