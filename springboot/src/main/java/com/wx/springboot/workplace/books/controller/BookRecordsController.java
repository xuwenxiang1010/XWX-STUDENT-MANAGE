package com.wx.springboot.workplace.books.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.springboot.system.common.vo.Constants;
import com.wx.springboot.system.common.vo.Result;
import com.wx.springboot.workplace.books.dto.BookRecordsVo;
import com.wx.springboot.workplace.books.entity.BookRecords;
import com.wx.springboot.workplace.books.entity.Libraries;
import com.wx.springboot.workplace.books.service.BookRecordsService;
import com.wx.springboot.workplace.books.service.LibrariesService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author wyb
 */
@RestController
@RequestMapping("books/records")
@Api(tags = "BookRecordsController")
@Slf4j
public class BookRecordsController {


    @Autowired
    private BookRecordsService bookRecordsService;
    @Autowired
    private LibrariesService librariesService;

    @GetMapping("/list")
    public Result list(BookRecordsVo vo,
                                @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        IPage<BookRecords> page = new Page<>(pageNum, pageSize);
        IPage<BookRecords> pageList = bookRecordsService.queryPageList(page, vo);
        for (BookRecords records : pageList.getRecords()){
            records.setPosition(address(records.getPositionCode()));
        }
        return Result.success(pageList);
    }

    @PutMapping("/add")
    public Result add(@RequestBody BookRecords bookRecords){
        String code = bookRecords.getLibId()
                +String.format("%02d",bookRecords.getFlower())
                +String.format("%02d",bookRecords.getRoom())
                +String.format("%02d",bookRecords.getBookShelf())
                +String.format("%02d",bookRecords.getLayer());
        bookRecords.setPositionCode(code);
        return bookRecordsService.add(bookRecords);
    }

    @GetMapping("/getCode")
    public Result getCode(){
        return bookRecordsService.getCode();
    }

    @GetMapping("/getLibId")
    public Result getLibId(){
        List<Libraries> librariesList = librariesService.selectAll();
        Map<String,Object> map =new HashMap<>();
        for (Libraries libraries : librariesList){
            for (int f = 1;f<=libraries.getFlower();f++){
                libraries.getFlowerList().add(String.valueOf(f));
            }
            for (int r = 1;r<=libraries.getRoom();r++){
                libraries.getRoomList().add(String.valueOf(r));
            }
            for (int s = 1;s<=libraries.getBookShelf();s++){
                libraries.getShelfList().add(String.valueOf(s));
            }
            for (int l = 1;l<=libraries.getFlower();l++){
                libraries.getLayerList().add(String.valueOf(l));
            }
        }
        map.put("librariesList",librariesList);
        return Result.success(map);
    }


    private String address(String positionCode){
        String position = "";
        if (positionCode!=null){
            String number = positionCode;
            String pId = number.substring(0,19);
            int f = Integer.valueOf(number.substring(19,21));
            int r = Integer.valueOf(number.substring(21,23));
            int b = Integer.valueOf(number.substring(23,25));
            int l = Integer.valueOf(number.substring(25,27));
            Libraries libraries = librariesService.selectById(pId);
            if(f<=libraries.getFlower()&&r<=libraries.getRoom()&&b<=libraries.getBookShelf()&&l<=libraries.getLayer()){
                position = (libraries.getName()+f+"0"+r+"室"+String.format("%03d",b)+"号书架第"+l+"层！");
            }
        }
        return position;
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable String id){
        BookRecords bookRecords = bookRecordsService.selectById(id);
        return bookRecordsService.delete(bookRecords);
    }

    @PostMapping("/update")
    public Result update(@RequestBody BookRecords bookRecords){
        return bookRecordsService.update(bookRecords);
    }

}
