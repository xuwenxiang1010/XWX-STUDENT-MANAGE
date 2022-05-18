package com.wx.springboot.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wx.springboot.system.domain.entity.Dict;
import com.wx.springboot.system.domain.entity.DictItem;
import com.wx.springboot.system.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.wx.springboot.system.common.vo.Result;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xwx
 * @since 2022-05-17
 */

@RestController
@RequestMapping("/system/dict")
@Api(tags = "DictController")
public class DictController {
    @Autowired
    private DictService dictService;

    @GetMapping("/list")
    @ApiOperation(value = "列表查询")
	public Result list(){
		List<Dict> list = dictService.list();
		return Result.success(list);
    }

    @GetMapping("/pageList")
	@ApiOperation(value = "分页查询")
	public Result pageList(Dict dict, @RequestParam Integer pageNum, @RequestParam Integer pageSize){
		IPage<Dict> page = new Page<>(pageNum,pageSize);
		IPage<Dict> pageList = dictService.pageList(page,dict);
		return Result.success(pageList);
    }

    @PostMapping("/add")
    @ApiOperation(value = "增加")
    public Result add(@RequestBody Dict dict){
        Result result = dictService.add(dict);
        return result;
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "删除")
    public Result delete(@PathVariable Long id){
        Result result = dictService.deleted(id);
        return result;
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改")
    public Result update(@RequestBody Dict dict){
        Result result = dictService.update(dict);
        return result;
    }

    @PostMapping("/getItem/{code}")
	@ApiOperation(value = "根据索引获取所对应的所有字典值")
	public Result queryValueByCode(@PathVariable String code){
    	Result result = dictService.queryValueByCode(code);
    	return result;
	}

	@PostMapping("/addItem")
	@ApiOperation(value = "增加字典值")
	public Result addItemByCode(@RequestBody DictItem item){
    	Result result = dictService.addItem(item);
    	return result;
	}

	//todo:新增字典、编辑字典、删除字典方法
}
