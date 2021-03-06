package com.wx.springboot.workplace.books.enums;

import org.apache.commons.lang.StringUtils;

/**
 * @author wyb
 */

public enum NatureEnum {
        儿童(1, "儿童读物"),
        成人(2, "成人读物"),
        特殊(3, "特殊读物"),
        ;

        private Integer code;
        private String name;

        public static String valueByCode(Integer code) {
            if(code==null){
                return "";
            }
            for (NatureEnum natureEnum : NatureEnum.values()) {
                if (natureEnum.getCode().equals(code)) {
                    return natureEnum.getName();
                }
            }
            return "";
        }

        public static Integer valueByName(String name) {
            if(StringUtils.isBlank(name)){
                return 0;
            }
            for (NatureEnum natureEnum : NatureEnum.values()) {
                if (natureEnum.getName().equals(name)) {
                    return natureEnum.getCode();
                }
            }
            return 0;
        }

        /**
         * @param code
         * @param name
         */

        private NatureEnum(Integer code, String name) {
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
