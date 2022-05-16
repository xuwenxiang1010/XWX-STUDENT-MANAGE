package com.wx.springboot.workplace.books.enums;

import org.apache.commons.lang.StringUtils;

/**
 * @author wyb
 */

public enum CategoryEnum {
    BUZ_CODE_YCL(1, "原材料"),
    BUZ_CODE_SP(2, "商品"),
    ;

    private Integer code;
    private String name;

    public static String valueByCode(Integer code) {
        if(code==null){
            return "";
        }
        for (CategoryEnum categoryEnum : CategoryEnum.values()) {
            if (categoryEnum.getCode().equals(code)) {
                return categoryEnum.getName();
            }
        }
        return "";
    }

    public static Integer valueByName(String name) {
        if(StringUtils.isBlank(name)){
            return 0;
        }
        for (CategoryEnum categoryEnum : CategoryEnum.values()) {
            if (categoryEnum.getName().equals(name)) {
                return categoryEnum.getCode();
            }
        }
        return 0;
    }

    /**
     * @param code
     * @param name
     */

    private CategoryEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
