package com.wx.springboot.tools.gettoken;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wyb
 */
public class GetToken {
    /**
     * 根据request中的token获取用户账号
     *
     * @param request
     * @return
     * @throws JeecgBootException
     */
    public static String getUserNameByToken(HttpServletRequest request) throws JeecgBootException {
        String accessToken = request.getHeader("X-Access-Token");
        String username = getUsername(accessToken);
        if (OConvertUtils.isEmpty(username)) {
            throw new JeecgBootException("未获取到用户");
        }
        return username;
    }
    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}
