package com.wx.springboot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wx.springboot.system.common.vo.Constants;
import com.wx.springboot.system.common.vo.Result;
import com.wx.springboot.system.dao.DictDao;
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
    public Result getIconList() {
        LambdaQueryWrapper<Dict> query = new LambdaQueryWrapper<>();
        query.eq(Dict::getType, Constants.DICT_TYPE_ICON);
        List<Dict> dictList = dictMapper.selectList(query);
        return Result.success(dictList);
    }
}