package com.wx.springboot.system.common.anno;

import java.lang.annotation.*;

/**
 * @author x
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface LogAnno {
	String content() default "";//内容
	int type() default 0;//操作类型(0登录1增加2删除3修改4查询5查看)
	String action() default "";//功能名称

}
