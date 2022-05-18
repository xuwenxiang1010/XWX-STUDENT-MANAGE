package com.wx.springboot.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author xwx
 * @version 1.0
 * @description Dict
 * @date 2022/4/28 17:25
 */
@Data
@TableName("sys_dict")
public class Dict {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String code;
    private String description;
    private Integer status;
}