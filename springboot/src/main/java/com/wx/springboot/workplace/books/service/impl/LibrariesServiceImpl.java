package com.wx.springboot.workplace.books.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wx.springboot.system.common.tools.gettoken.GetToken;
import com.wx.springboot.system.common.tools.getusername.GetUserName;
import com.wx.springboot.system.common.vo.Constants;
import com.wx.springboot.system.common.vo.Result;
import com.wx.springboot.workplace.books.entity.Libraries;
import com.wx.springboot.workplace.books.mapper.LibrariesMapper;
import com.wx.springboot.workplace.books.service.LibrariesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

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
                .orderByDesc(Libraries::getCreateTime);
        return librariesMapper.selectPage(page,queryWrapper);
    }

    @Override
    public Result add(Libraries libraries) {
        libraries.setCreateTime(new Date());
        libraries.setFatherId(0);
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
    public Result roomName(Integer flower, Integer room, String name){
        if (flower != 0 && room != 0){
            for (int i = 1;  i<=flower; i++){
                String name1 = String.valueOf(flower);
                for (int j = 1; j<=room;j++){
                    String name2 = String.valueOf(room);
                    Libraries lib = new Libraries();
                    lib.setFatherId(librariesMapper.selectIdByName(name));
                    lib.setName(name1+"0"+name2);
                    lib.setCreateTime(new Date());
                    librariesMapper.insert(lib);
                }
            }
            return Result.success();
        }
        return Result.error();
    }
}
