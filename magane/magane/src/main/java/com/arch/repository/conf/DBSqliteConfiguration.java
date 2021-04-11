package com.arch.repository.conf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.datasource")
@Setter
@Getter
public class DBSqliteConfiguration {
    String url;
    String userName;
    String password;
}
