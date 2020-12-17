package com.bingoyes;

import com.bingoyes.gat1400.apicaller.service.HzApiCallingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
public class Gat1400BingoClientApp implements CommandLineRunner
{

    private static Logger logger = LoggerFactory.getLogger(Gat1400BingoClientApp.class);

    @Autowired
    HzApiCallingService hzApiCallingService;

    public static void main(String[] args) {
        SpringApplication.run(Gat1400BingoClientApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        String operatType =args[0];
        String result = "";

        if("register".equals(operatType)){
            result = hzApiCallingService.register();
            //result = hzApiCallingService.registerWithRestDigest();
            logger.info("注册成功："+result);
        }else if("unregister".equals(operatType)){
            result = hzApiCallingService.unregister();
            logger.info("注销成功："+result);
        }else if("keepalive".equals(operatType)){
            result = hzApiCallingService.keepalive();
            logger.info("保活成功："+result);
        }else if("sub-t".equals(operatType)){
            result = hzApiCallingService.subscribeTollgate();
            logger.info("订阅卡口成功："+result);
        }else if("sub-a".equals(operatType)){
            result = hzApiCallingService.subscribeDevice();
            logger.info("订阅设备成功："+result);
        }else if("sub-v".equals(operatType)){
            result = hzApiCallingService.subscribeMotorVehicle();
            logger.info("订阅机动车成功："+result);
        }else
            throw new SecurityException("输入的操作类型不正确");

        logger.info("api call result:"+result);
    }
}
