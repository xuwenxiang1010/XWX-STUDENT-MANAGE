package com.wx.springboot;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class SpringbootApplicationTests {

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
}
