package com.wx.springboot.system.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Date;

/**
 * @author: xwx
 * @createTime: 2022/5/9/009 9:09
 * @description:
 */
public class TokenUtil {

	private static final long EXPIRE_TIME = 60 * 60 * 1000;

	public static  String genToken(String userId,String sign){
		Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
		//将userId保存到token里面，作为载荷
		String token = JWT.create().withAudience(userId)
													// 两小时后token过期
													.withExpiresAt(date)
													//以密码作为token的密钥
													.sign(Algorithm.HMAC256(sign));
		return token;
	}

}
