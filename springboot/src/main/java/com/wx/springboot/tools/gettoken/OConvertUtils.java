package com.wx.springboot.tools.gettoken;

import lombok.extern.slf4j.Slf4j;
/**
 * @author wyb
 */
@Slf4j
public class OConvertUtils {

    public static boolean isEmpty(Object object) {
        if (object == null) {
            return (true);
        }
        if ("".equals(object)) {
            return (true);
        }
        if ("null".equals(object)) {
            return (true);
        }
        return (false);
    }
}
