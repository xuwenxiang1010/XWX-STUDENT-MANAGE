package com.wx.springboot.workplace.books.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wx.springboot.system.common.tools.getuuid.GetUUId;
import com.wx.springboot.system.common.vo.Constants;
import com.wx.springboot.system.common.vo.Result;
import com.wx.springboot.workplace.books.entity.Libraries;
import com.wx.springboot.workplace.books.mapper.LibrariesMapper;
import com.wx.springboot.workplace.books.service.LibrariesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
                .eq(Libraries::getFatherId,1)
                .orderByDesc(Libraries::getCreateTime);
        return librariesMapper.selectPage(page,queryWrapper);
    }

    @Override
    public Result add(Libraries libraries) {
        libraries.setCreateTime(new Date());
        libraries.setFatherId("1");
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
    public Result roomName(Integer flower, Integer room,Integer bookShelf,Integer layer, String id){
        if (flower != 0 && room != 0){
            for (int i = 1;  i<=flower; i++){
                String name1 = String.valueOf(i);
                for (int j = 1; j<=room;j++){
                    String name2 = String.valueOf(j);
                    Libraries lib = new Libraries();
                    lib.setId(GetUUId.getUUID());
                    lib.setFatherId(id);
                    lib.setName(name1+"0"+name2+"室");
                    lib.setCreateTime(new Date());
                    librariesMapper.insert(lib);
                    for (int k =1;k<=bookShelf;k++){
                        Libraries lib2 = new Libraries();
                        lib2.setId(GetUUId.getUUID());
                        lib2.setFatherId(lib.getId());
                        lib2.setName(String.format("%03d",k)+"书架");
                        lib2.setCreateTime(new Date());
                        librariesMapper.insert(lib2);
                        for (int l = 1;l<=layer;l++){
                            Libraries lib3 = new Libraries();
                            lib3.setId(GetUUId.getUUID());
                            lib3.setFatherId(lib2.getId());
                            lib3.setName("第"+l+"层");
                            lib3.setCreateTime(new Date());
                            librariesMapper.insert(lib3);
                        }
                    }
                }
            }
            return Result.success();
        }
        return Result.error();
    }

    @Override
    public Libraries selectById(String positionId) {
        return librariesMapper.selectById(positionId);
    }
}
