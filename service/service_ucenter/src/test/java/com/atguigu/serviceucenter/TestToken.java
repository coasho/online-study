package com.atguigu.serviceucenter;

import com.atguigu.commonutils.util.JwtUtils;
import org.junit.Test;


public class TestToken {
    public static void main(String[] args) {
        System.out.println(JwtUtils.getJwtToken("1654098433930862594", "文轩"));

    }    
}
