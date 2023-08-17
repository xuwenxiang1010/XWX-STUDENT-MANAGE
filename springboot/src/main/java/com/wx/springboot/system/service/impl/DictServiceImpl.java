package com.wx.springboot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wx.springboot.system.common.vo.Constants;
import com.wx.springboot.system.common.vo.Result;
import com.wx.springboot.system.dao.DictDao;
import com.wx.springboot.system.dao.DictItemDao;
import com.wx.springboot.system.domain.dto.DictDto;
import com.wx.springboot.system.domain.entity.Dict;
import com.wx.springboot.system.domain.entity.DictItem;
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
    @Autowired
    private DictItemDao itemMapper;

    @Override
    public List<Dict> list() {
        return dictMapper.list();
    }

    @Override
    public IPage<Dict> pageList(IPage<Dict> page, Dict dict) {
        LambdaQueryWrapper<Dict> query = new LambdaQueryWrapper<>();
        query.eq(Dict::getStatus,0)
            .like(Dict::getCode,dict.getCode());
        IPage<Dict> pageList = dictMapper.selectPage(page, query);
        return pageList;
    }

    @Override
    public Result add(Dict dict) {
        LambdaQueryWrapper<Dict> query = new LambdaQueryWrapper<>();
        query.eq(Dict::getCode,dict.getCode());
        Dict dict1 = dictMapper.selectOne(query);
        if (dict1 != null){
            return Result.error(Constants.CODE_400,"索引已存在！");
        }else {
            dictMapper.insert(dict);
            return Result.success();
        }
    }

    @Override
    public Result deleted(Long id) {
        Dict dict = dictMapper.selectById(id);
        int quantity = dictMapper.getItemQuantity(dict.getCode());
        if (quantity != 0){
            return Result.error(Constants.CODE_500,"索引有字典值，删除失败！");
        }else {
            dict.setStatus(1);
            dictMapper.updateById(dict);
            return Result.success();
        }
    }

    @Override
    public Result update(Dict dict) {
        dictMapper.updateById(dict);
        return Result.success();
    }

    @Override
    public Result queryValueByCode(String code) {
        List<DictItem> dictItems = dictMapper.queryValueByCode(code);
        return Result.success(dictItems);
    }

    @Override
    public Result addItem(DictItem item) {
        LambdaQueryWrapper<DictItem> query = new LambdaQueryWrapper<>();
        query.eq(DictItem::getCode,item.getCode())
                .eq(DictItem::getText,item.getText());
        DictItem dictItem = itemMapper.selectOne(query);
        if(dictItem != null){
            return Result.error(Constants.CODE_400,"字典名称已存在！");
        }else {
            int addItem = itemMapper.insert(item);
            return Result.success();
        }
    }

    @Override
    public Result updateItem(DictItem item) {
        int count = itemMapper.getByText(item.getCode(),item.getText());
        if (count>1){
            return Result.error(Constants.CODE_400,"字典名称已存在！");
        }else {
            itemMapper.updateById(item);
            return Result.success();
        }
    }

    @Override
    public Result deleteItem(Long itemId) {
        itemMapper.deleteById(itemId);
        return Result.success();
    }

    @Override
    public String getValueByTypeCode(String trim, String code) {
        LambdaQueryWrapper<DictItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DictItem::getCode,code);
        queryWrapper.eq(DictItem::getText,trim);
        DictItem item = itemMapper.selectOne(queryWrapper);
        return item.getValue();
    }

}