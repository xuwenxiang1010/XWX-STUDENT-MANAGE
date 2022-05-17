package com.wx.springboot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wx.springboot.system.common.vo.Constants;
import com.wx.springboot.system.common.vo.Result;
import com.wx.springboot.system.dao.DictDao;
import com.wx.springboot.system.domain.dto.DictDto;
import com.wx.springboot.system.domain.entity.Dict;
import com.wx.springboot.system.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xwx
 * @version 1.0
 * @description DictServiceImpl
 * @date 2022/4/28 17:28
 */
@Service
public class DictServiceImpl implements DictService {

    @Autowired
    private DictDao dictMapper;

    @Override
    public List<Dict> list() {
        return dictMapper.list();
    }

    @Override
    public IPage<Dict> pageList(IPage<Dict> page, Dict dict) {
        LambdaQueryWrapper<Dict> query = new LambdaQueryWrapper<>();
        query.like(Dict::getCode,dict.getCode());
        IPage<Dict> pageList = dictMapper.selectPage(page, query);
        return pageList;
    }

    @Override
    public Result add(Dict dict) {
        return null;
    }

    @Override
    public Result deleted(Long id) {
        return null;
    }

    @Override
    public Result update(Dict dict) {
        return null;
    }

    @Override
    public Result queryValueByCode(String code) {
        List<DictDto> dictItems = dictMapper.queryValueByCode(code);
        return Result.success(dictItems);
    }
}