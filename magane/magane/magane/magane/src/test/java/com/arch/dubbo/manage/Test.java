package com.arch.dubbo.manage;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            //static/mybatis/generator/*.xml
            Resource[] resources2 = resolver.getResources("classpath:./");
            System.out.println(resources2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
