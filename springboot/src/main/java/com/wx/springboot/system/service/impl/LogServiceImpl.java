package com.wx.springboot.system.service.impl;

import com.wx.springboot.system.domain.entity.Log;
import com.wx.springboot.system.dao.LogMapper;
import com.wx.springboot.system.service.ILogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;
import com.wx.springboot.system.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xwx
 * @since 2023-04-25
 */
@Service
public class LogServiceImpl implements ILogService {

	@Autowired
	private LogMapper logMapper;

	@Override
    public List<Log> list(){
        return logMapper.list();
    }

    @Override
	public IPage<Log> pageList(IPage<Log> page, Log log) {
		LambdaQueryWrapper<Log> query = new LambdaQueryWrapper<>();
		//todo：设置的分页查询条件
		IPage<Log> pageList = logMapper.selectPage(page,query);
		return pageList;
    }

	@Override
	public Result add(Log log) {
		int add = logMapper.insert(log);
		return Result.success("添加成功");
	}

	@Override
	public Result deleted(Long id) {
		Log log = logMapper.selectById(id);
		//todo：判断删除的字段，一般是deleted软删除
		int delete = logMapper.updateById(log);
		return Result.success();
	}

	@Override
	public Result update(Log log) {
		int update = logMapper.updateById(log);
		return Result.success("修改成功");
	}
}
