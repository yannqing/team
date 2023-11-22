package com.jx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan("com.jx.mapper")
public class JxTeamUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(JxTeamUserApplication.class, args);
    }

}
