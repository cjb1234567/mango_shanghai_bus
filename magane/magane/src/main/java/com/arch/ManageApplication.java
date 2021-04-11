package com.arch;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {
        "com.arch.controller",
        "com.arch.service",
        "com.arch.repository",
        "com.arch.dao"
})
@MapperScan(basePackages = "com.arch.dao")
public class ManageApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageApplication.class, args);
    }
}
