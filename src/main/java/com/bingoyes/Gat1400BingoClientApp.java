package com.bingoyes;

import com.bingoyes.gat1400.apicaller.service.HzApiCallingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
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

        logger.info("bingoyes client starting");

        String result = "";
        String operatType ="";

        if(args.length>0){
            operatType = args[0];
        }

        logger.info("operate type:"+operatType);

        if("register".equals(operatType)){
            result = hzApiCallingService.register();
            logger.info("注册成功："+result);
        }else if("unregister".equals(operatType)){
            result = hzApiCallingService.unregister();
            logger.info("注销成功："+result);
        }else if("keepalive".equals(operatType)){
            result = hzApiCallingService.keepalive();
            logger.info("保活成功："+result);
        }else if("sub-v".equals(operatType)){
            List<String> resultList = hzApiCallingService.subscribeMotorVehicle();
            logger.info("订阅机动车成功：");
            for(String item:resultList) {
                logger.info("result:" + item);
            }

        }else
            throw new SecurityException("输入的操作类型不正确");

        //logger.info("api call result:"+result);
    }
}
