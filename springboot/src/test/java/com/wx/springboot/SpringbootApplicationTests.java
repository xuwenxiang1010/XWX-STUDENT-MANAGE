package com.wx.springboot;


import com.wx.springboot.system.domain.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;

@SpringBootTest
class SpringbootApplicationTests {

    @Autowired(required = false)
    private RedisTemplate redisTemplate;

    @Test
    public void get(){
        redisTemplate.opsForValue().set("name","qqq");
        System.out.println(redisTemplate.opsForValue().get("name"));
    }

    @Test
    void contextLoads() {
    }

    @Test
    public static void main(String[] args) {
        BigDecimal bd = new BigDecimal("11.10");
        String str = bd.stripTrailingZeros().toPlainString();
        System.out.println(str);
        System.out.println(bd.stripTrailingZeros().toPlainString());
    }

    @Test
    void setObject(){
        User user = new User();
        user.setUserName("wx");
        user.setUserAge(22);
        user.setPhone("1234");
        redisTemplate.opsForValue().set("user",user);
        System.out.println(redisTemplate.opsForValue().get("user"));
    }
}
