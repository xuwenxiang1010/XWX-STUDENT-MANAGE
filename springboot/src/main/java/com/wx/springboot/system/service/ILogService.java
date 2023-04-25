package com.wx.springboot.system.service;

import com.wx.springboot.system.domain.entity.Log;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wx.springboot.system.common.vo.Result;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xwx
 * @since 2023-04-25
 */
public interface ILogService{

	List<Log> list();

    IPage<Log> pageList(IPage<Log> page, Log log);

    Result add(Log log);

    Result deleted(Long id);

    Result update(Log log);

}

