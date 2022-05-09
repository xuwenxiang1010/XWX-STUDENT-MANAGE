package com.wx.springboot.system.common.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.wx.springboot.system.common.exception.ServiceException;
import com.wx.springboot.system.common.vo.Constants;
import com.wx.springboot.system.dao.UserDao;
import com.wx.springboot.system.domain.entity.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: xwx
 * @createTime: 2022/5/9/009 14:44
 * @description:
 */
public class JwtInterceptor implements HandlerInterceptor {
	@Autowired
	private UserDao userMapper;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) {
		String token = request.getHeader("token");
		// 如果不是映射到方法直接通过
		if(!(object instanceof HandlerMethod)){
			return true;
		}
		//执行认证
		if(StringUtils.isEmpty(token)){
			throw new ServiceException(Constants.CODE_401,"无token，请重新登录");
		}
		//获得token里面的userId
		String userId;
		try {
			userId = JWT.decode(token).getAudience().get(0);
		}catch (JWTDecodeException j){
			throw new ServiceException(Constants.CODE_401,"token验证失败，请重新登录");
		}
		User user = userMapper.selectById(userId);
		if(user == null){
			throw new ServiceException(Constants.CODE_401,"用户不存在，请重新登录");
		}
		//用户密码加签 验证token
		JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
		try {
			//验证token
			jwtVerifier.verify(token);
		}catch (JWTVerificationException j){
			throw new ServiceException(Constants.CODE_401,"token验证失败，请重新登录");
		}
		return true;
	}
}
