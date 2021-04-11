package com.arch.repository.conf;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

//@Configuration
@MapperScan(basePackages = {"com.arch.dao"})
public class MybatisDbMysqlConfig {
    @Bean(name = "test2DataSource")
    public DataSource testDataSource(DBMysqlConfig testConfig) throws SQLException {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUrl("jdbc:mysql://" + testConfig.getIp() + ":" + testConfig.getPort() + "/" + testConfig.getDatabase() + "?characterEncoding=utf8&useSSL=true");
        mysqlDataSource.setUser(testConfig.getUsername());
        mysqlDataSource.setPassword(testConfig.getPassword());
        mysqlDataSource.setDatabaseName("test2DataSource");
        return mysqlDataSource;
    }

    @Bean(name = "test2SqlSessionFactory")
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("test2DataSource") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        return bean.getObject();
    }

    @Bean(name = "test2SqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(
            @Qualifier("test2SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
