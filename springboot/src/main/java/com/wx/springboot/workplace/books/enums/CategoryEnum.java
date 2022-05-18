package com.wx.springboot.workplace.books.enums;

import org.apache.commons.lang.StringUtils;

/**
 * @author wyb
 */

public enum CategoryEnum {
    科幻(1, "科幻"),
    玄幻(2, "玄幻"),
    奇幻(3, "奇幻"),
    武侠(4, "武侠"),
    仙侠(5, "仙侠"),
    都市(6, "都市"),
    游戏(7, "游戏"),
    灵异(8, "灵异"),
    历史(9, "历史"),
    军事(10, "军事"),
    职场(11, "职场"),
    体育(12, "体育"),
    同人(13, "同人"),
    轻小说(14, "轻小说"),
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
