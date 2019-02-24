package org.virtue;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
// mapper 接口类扫描包配置
@MapperScan("org.virtue.dao")
@EnableSwagger2
@Configuration
@EnableSpringDataWebSupport
public class DriverAdminApplication{

    public static void main(String[] args) {
        SpringApplication.run(DriverAdminApplication.class, args);
    }
}

