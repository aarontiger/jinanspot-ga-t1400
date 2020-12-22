package com.bingoyes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * GAT1400服务启动入口
 **/
//@SpringBootApplication
@EnableScheduling
public class Gat1400ServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(Gat1400ServiceApp.class, args);
    }
}
