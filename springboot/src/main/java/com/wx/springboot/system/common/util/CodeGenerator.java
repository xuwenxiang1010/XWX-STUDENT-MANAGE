package com.wx.springboot.system.common.util;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.util.Collections;

/**
 * @authoer:  xwx
 * @createTime: 2022/5/7/007 9:40
 * @description: 代码生成器mybatisplus
 */
public class CodeGenerator {
	public static void main(String[] args) {
		generate();
	}

	private static void  generate(){
		FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/studentmanager?serverTimezone=GMT%2b8&useUnicode=true&characterEncoding=utf8",
				"root", "root")
				.globalConfig(builder -> {
					builder.author("xwx") // 设置作者
							.enableSwagger() // 开启 swagger 模式
							.fileOverride() // 覆盖已生成文件
							.outputDir("D:\\Work\\Code\\WorkSpace\\XWX-STUDENT-MANAGE\\springboot\\File\\"); // 指定输出目录
				})
				.packageConfig(builder -> {
					builder.parent("com.wx.springboot") // 设置父包名
							.moduleName("student") // 设置父包模块名
							.pathInfo(Collections.singletonMap(OutputFile.mapperXml,
									"D:\\Work\\Code\\WorkSpace\\XWX-STUDENT-MANAGE\\springboot\\src\\main\\resources\\mybatis\\student\\")); // 设置mapperXml生成路径
				})
				.strategyConfig(builder -> {
					builder.addInclude("sys_log") // 设置需要生成的表名
							.addTablePrefix("sys_","t_"); // 设置过滤表前缀
				})
				.execute();
	}
}
