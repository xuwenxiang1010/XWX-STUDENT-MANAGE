package com.wx.springboot.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author: xwx
 * @createTime: 2022/5/18/018 10:06
 * @description:
 */
@Data
@TableName("sys_dict_item")
public class DictItem {
	@TableId(type = IdType.ASSIGN_ID)
	private Long id;
	private String code;
	private String text;
	private String value;
}
