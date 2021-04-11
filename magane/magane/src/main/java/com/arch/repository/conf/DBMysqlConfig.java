package com.arch.repository.conf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Setter
@Getter
@ConfigurationProperties(prefix = "spring.datasource.mysql")
@Configuration
public class DBMysqlConfig {
    private String username;
    private String password;
    private String ip;
    private String database;
    private String port;
}

