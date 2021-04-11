package com.arch.repository.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class MybatisDbSqliteConfig {
    @Primary
    @Bean(name = "primaryDataSource")
    public DataSource sqliteDataSource(DBSqliteConfiguration dbSqliteConfiguration) throws SQLException {
        SQLiteDataSource sqLiteDataSource = new SQLiteDataSource();
        sqLiteDataSource.setUrl(dbSqliteConfiguration.getUrl());
        return sqLiteDataSource;
    }

//    @Bean(name = "primarySqlSessionFactory")
//    public SqlSessionFactory primarySqlSessionFactory(@Qualifier("primaryDataSource") DataSource dataSource)
//            throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        Resource[] resources2 = resolver.getResources("classpath:static/mybatis/generator/*.xml");
//        List<Resource> resources = new ArrayList<>();
//        resources.addAll(Arrays.asList(resources2));
//        bean.setMapperLocations(resources.toArray(new Resource[resources.size()]));
//        return bean.getObject();
//    }

//    @Bean(name = "primarySqlSessionTemplate")
//    public SqlSessionTemplate testSqlSessionTemplate(
//            @Qualifier("primarySqlSessionFactory") SqlSessionFactory primarySqlSessionFactory) throws Exception {
//        return new SqlSessionTemplate(primarySqlSessionFactory);
//    }
}
