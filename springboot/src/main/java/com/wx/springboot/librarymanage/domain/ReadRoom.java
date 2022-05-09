package com.wx.springboot.librarymanage.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author xwx
 * @since 2022-05-07
 */
@Data
@TableName("read_room")
@ApiModel(value = "ReadRoom对象", description = "")
public class ReadRoom implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
      private Long id;

    private String name;

    private Long manager;

    private int total;

    private int floor;

}
