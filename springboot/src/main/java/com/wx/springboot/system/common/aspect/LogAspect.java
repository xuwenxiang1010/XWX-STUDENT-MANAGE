package com.wx.springboot.system.common.aspect;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.wx.springboot.system.common.anno.LogAnno;
import com.wx.springboot.system.common.util.TokenUtil;
import com.wx.springboot.system.dao.LogMapper;
import com.wx.springboot.system.domain.entity.Log;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @ClassName: LogAspect
 * @Description:
 * @Author: JH048206
 * @Date: 2023/4/25/025 14:10
 **/

@Aspect
@Component
@EnableAsync
public class LogAspect {
	@Autowired
	private LogMapper logMapper;//日志 mapper

	private String requestPath = null ; // 请求地址
	private long startTimeMillis = 0; // 开始时间
	private long endTimeMillis = 0; // 结束时间
	private String user = null; // 操作人
	private HttpServletRequest request = null;//请求


	/**
	 * 注解的位置
	 */
	@Pointcut("@annotation(com.wx.springboot.system.common.anno.LogAnno)")
	public void logPointCut() {}

/**
	 * @param joinPoint
	 * @Description 前置通知  方法调用前触发   记录开始时间,从session中获取操作人
	 */

	@Before(value="logPointCut()")
	public void before(JoinPoint joinPoint){
		startTimeMillis = System.currentTimeMillis();
	}
/**
	 * @param joinPoint
	 * @Description 获取入参方法参数
	 * @return
	 */

	public Map<String, Object> getNameAndValue(JoinPoint joinPoint) {
		Map<String, Object> param = new HashMap<>();
		Object[] paramValues = joinPoint.getArgs();
		String[] paramNames = ((CodeSignature)joinPoint.getSignature()).getParameterNames();
		for (int i = 0; i < paramNames.length; i++) {
			if(paramValues[i] instanceof Integer || paramValues[i] instanceof String) {
				param.put(paramNames[i], paramValues[i]);
			}
		}
		return param;
	}
/**
	 * @param joinPoint
	 * @Description 后置通知    方法调用后触发   记录结束时间 ,操作人 ,入参等
	 */

	@After(value="logPointCut()")
	public void after(JoinPoint joinPoint) {
		request = getHttpServletRequest();
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class<?> targetClass = null;
		try {
			targetClass = Class.forName(targetName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Method[] methods = targetClass.getMethods();
		String title;
		String action;
		Integer type;
		Class<?>[] clazzs;
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				clazzs = method.getParameterTypes();
				if (clazzs!=null&&clazzs.length == arguments.length
						&&method.getAnnotation(LogAnno.class)!=null) {
					request = getHttpServletRequest();
					requestPath=request.getServletPath();
					HttpSession session = request.getSession();
					user = (String) session.getAttribute("userName");
					if(StringUtils.isEmpty(user)){
						//非登录接口
						String token = request.getHeader("token");
						String userName = JWT.decode(token).getClaim("userName").asString();
						user = userName;
					}
					title = method.getAnnotation(LogAnno.class).content();
					action = method.getAnnotation(LogAnno.class).action();
					type = method.getAnnotation(LogAnno.class).type();
					endTimeMillis = System.currentTimeMillis();

					Log log=new Log(user, requestPath,
							(endTimeMillis-startTimeMillis)+"ms",
							getNameAndValue(joinPoint).toString(), title,action,type);
					System.out.println("增加参数："+log);
					logMapper.insert(log);
//                    break;
				}
			}
		}
	}

/**
	 * @Description: 获取request
	 */

	public HttpServletRequest getHttpServletRequest(){
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes sra = (ServletRequestAttributes)ra;
		HttpServletRequest request = sra.getRequest();
		return request;
	}

/**
	 * @param joinPoint
	 * @return 环绕通知
	 * @throws Throwable
	 */

	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		return null;
	}

/**
	 * @param joinPoint
	 * @Description 异常通知
	 */

	public void throwing(JoinPoint joinPoint) {
		System.out.println("异常通知");
	}
}