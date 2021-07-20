package com.gmcc.aliface;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.gmcc.aliface.mapper"})
public class AlifaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlifaceApplication.class, args);
    }

}
