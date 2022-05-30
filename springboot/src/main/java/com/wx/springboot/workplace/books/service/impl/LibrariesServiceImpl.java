package com.wx.springboot.workplace.books.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wx.springboot.system.common.vo.Constants;
import com.wx.springboot.system.common.vo.Result;
import com.wx.springboot.workplace.books.entity.Libraries;
import com.wx.springboot.workplace.books.mapper.LibrariesMapper;
import com.wx.springboot.workplace.books.service.LibrariesService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

/**
 * @author wyb
 */
@Service
public class LibrariesServiceImpl implements LibrariesService {

    @Autowired
    private LibrariesMapper librariesMapper;

    @Override
    public IPage<Libraries> queryPageList(IPage<Libraries> page, Libraries vo) {
        LambdaQueryWrapper<Libraries> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Libraries::getDeleted,0)
                .like(StringUtils.isNotEmpty(vo.getName()),Libraries::getName,vo.getName())
                .orderByDesc(Libraries::getCreateTime);
        return librariesMapper.selectPage(page,queryWrapper);
    }

    @Override
    public Result add(Libraries libraries) {
        libraries.setCreateTime(new Date());
        LambdaQueryWrapper<Libraries> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Libraries::getName,libraries.getName())
                .eq(Libraries::getDeleted,0);
        Libraries room= librariesMapper.selectOne(queryWrapper);
        if (room != null ){
            return Result.error(Constants.CODE_400,"该图书馆已存在");
        }else {
            librariesMapper.insert(libraries);
            return Result.success("添加成功");
        }
    }
    @Override
    public Libraries selectById(String id) {
        return librariesMapper.selectById(id);
    }

    @Override
    public Result edit(Libraries libraries) {
        libraries.setUpdateTime(new Date());
        LambdaQueryWrapper<Libraries> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Libraries::getName,libraries.getName())
                .eq(Libraries::getDeleted,0)
                .ne(Libraries::getId,libraries.getId());
        Libraries room= librariesMapper.selectOne(queryWrapper);
        if (room != null ){
            return Result.error(Constants.CODE_400,"该图书馆已存在");
        }else {
            librariesMapper.updateById(libraries);
            return Result.success("修改成功");
        }
    }

    @Override
    public Result delete(Libraries libraries) {
        libraries.setDeleted(1);
        librariesMapper.updateById(libraries);
        return Result.success("删除成功");
    }

    @Override
    public List<Libraries> selectAll() {
       List<Libraries> librariesList = librariesMapper.selectAll();
        return librariesList;
    }
}
