package com.wx.springboot.system.common.tools.getuuid;

import java.util.UUID;

/**
 * @author wyb
 */
public class GetUUId {
    public static String  getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
