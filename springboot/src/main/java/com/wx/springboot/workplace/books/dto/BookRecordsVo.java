package com.wx.springboot.workplace.books.dto;

import com.wx.springboot.workplace.books.enums.CategoryEnum;
import com.wx.springboot.workplace.books.enums.NatureEnum;
import lombok.Data;
/**
 * @author dell
 */
@Data
public class BookRecordsVo {

    private String bookCode;

    private String bookName;

    private Integer bookNature;

    private Integer bookCategory;

    private String start;

    private String end;

    private Integer deleted;

    private String natureName;

    private String categoryName;

    public String getNatureName() {
        return NatureEnum.valueByCode(getBookNature());
    }

    public String getCategoryName() {
        return CategoryEnum.valueByCode(getBookCategory());
    }
}
