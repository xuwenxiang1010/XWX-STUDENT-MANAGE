package com.wx.springboot.workplace.books.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wx.springboot.system.common.vo.Result;
import com.wx.springboot.workplace.books.entity.Libraries;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 */
public interface LibrariesService {
    /**
     * 分页查询
     * @param page
     * @param vo
     * @return
     */
    IPage<Libraries> queryPageList(IPage<Libraries> page, Libraries vo);

    Result add(Libraries libraries);

    Result roomName(Integer flower, Integer room, String name);
}