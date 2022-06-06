package com.wx.springboot.workplace.books.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author dell
 */
@Data
@TableName("book_readers_books")
public class ReaderAndBooks {

    /**
     * 主键
     */
    @TableId(type= IdType.ASSIGN_ID)
    private String id;

    private String bookId;

    private String readerId;
}

