package com.lzx.crm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

//(exclude=DataSourceAutoConfiguration.class)
@SpringBootApplication
@MapperScan("com.lzx.crm.dao")
public class Starter extends SpringBootServletInitializer {
    public static void main(String[] args) {
        System.out.println("李子翔加油master！");
        System.out.println("李子翔加油lzx！");
        System.out.println("李子翔加油lzx2！");
        System.out.println("李子翔加油lzx3！");
        SpringApplication.run(Starter.class);
    }

    /**
     * @description: web项目启动入口
     * @author: lizixiang
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Starter.class);
    }
}