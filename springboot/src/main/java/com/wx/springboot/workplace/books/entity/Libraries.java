package com.wx.springboot.workplace.books.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author wyb
 */
@Data
@TableName(value = "book_libraries")
public class Libraries {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String name;

    private Date createTime;

    private Date updateTime;

    private Integer deleted;

    private Integer flower;

    private Integer room;

    private Integer bookShelf;

    private Integer layer;

    @TableField(exist = false)
    private List<String> flowerList;

    @TableField(exist = false)
    private List<String> roomList;

    @TableField(exist = false)
    private List<String> shelfList;

    @TableField(exist = false)
    private List<String> layerList;
}
