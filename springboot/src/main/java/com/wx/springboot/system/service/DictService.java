package com.wx.springboot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wx.springboot.system.common.vo.Result;
import com.wx.springboot.system.domain.entity.Dict;

import java.util.List;

/**
 * @author xuwenxiang
 * @create 2022/4/28~17:27
 */
public interface DictService {

    List<Dict> list();

    IPage<Dict> pageList(IPage<Dict> page, Dict dict);

    Result add(Dict dict);

    Result deleted(Long id);

    Result update(Dict dict);

    Result queryValueByCode(String code);
}
