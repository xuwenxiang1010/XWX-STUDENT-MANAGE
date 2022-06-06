package com.wx.springboot.system.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xwx
 * @version 1.0
 * @description Result接口统一返回包装类
 * @date 2022/4/25 17:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private String code;
    private String message;
    private Object data;

    public static Result success(){
        return new Result(Constants.CODE_200,"",null);
    }

    public static Result success(String message){
        return new Result(Constants.CODE_200,message,null);
    }

    public static Result success(Object data){
        return new Result(Constants.CODE_200,"",data);
    }

    public static Result error(String code,String message){
        return new Result(code,message,null);
    }
    public static Result error(String message){
        return new Result(null,message,null);
    }
    public static Result error(){
        return new Result(Constants.CODE_500,"系统错误",null);
    }


}