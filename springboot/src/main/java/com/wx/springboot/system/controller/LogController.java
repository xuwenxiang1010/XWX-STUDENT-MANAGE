package com.wx.springboot.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.wx.springboot.system.common.vo.Result;
import com.wx.springboot.system.service.ILogService;
import com.wx.springboot.system.domain.entity.Log;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xwx
 * @since 2023-04-25
 */

@RestController
@RequestMapping("/student/log")
public class LogController {
    @Autowired
    private ILogService logService;

    @GetMapping("/list")
    @ApiOperation(value = "列表查询")
	public Result list(){
		List<Log> list = logService.list();
		return Result.success(list);
    }

    @GetMapping("/pageList")
	@ApiOperation(value = "分页查询")
	public Result pageList(Log log, @RequestParam Integer pageNum, @RequestParam Integer pageSize){
		IPage<Log> page = new Page<>(pageNum,pageSize);
		IPage<Log> pageList = logService.pageList(page,log);
		return Result.success(pageList);
    }

    @PostMapping("/add")
    @ApiOperation(value = "增加")
    public Result add(@RequestBody Log log){
        Result result = logService.add(log);
        return result;
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "删除")
    public Result delete(@PathVariable Long id){
        Result result = logService.deleted(id);
        return result;
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改")
    public Result update(@RequestBody Log log){
        Result result = logService.update(log);
        return result;
    }

}
