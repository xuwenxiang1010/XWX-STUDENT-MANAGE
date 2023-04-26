package com.wx.springboot.system.controller;

import com.wx.springboot.system.common.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: xwx
 * @createTime: 2022/5/20/020 14:47
 * @description:
 */
@RestController
@RequestMapping("/setRedisUtils")
@Api(tags = "redis")
public class TestController {

	@Autowired
	private RedisUtil redisUtil;

	@ApiOperation(value = "get")
	@GetMapping("/getName")
	public Map<String,Object> getName(@RequestParam Map<String,Object> paramsMap){
		Map<String,Object> resuleMap = new HashMap<>();
		resuleMap.put("name",redisUtil.get(paramsMap.get("name").toString()));
		return resuleMap;
	}

	@ApiOperation(value = "set")
	@GetMapping("/setName")
	public Object setName(@RequestParam Map<String,Object> paramsMap){
		String nameKey = paramsMap.get("nameKey").toString();
		String nameValue = paramsMap.get("nameValue").toString();
		redisUtil.set(nameKey,nameValue);
		return "加入成功！";
	}
}
