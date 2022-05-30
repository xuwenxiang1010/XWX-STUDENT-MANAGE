package com.wx.springboot.system.common.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: xwx
 * @createTime: 2022/5/20/020 12:02
 * @description:
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Dict {
	/**
	 * 字典类型
	 *
	 * @return
	 */
	String dictCode();

	/**
	 * 返回属性名
	 *
	 * @return
	 */
	String dictText() default "";

}
