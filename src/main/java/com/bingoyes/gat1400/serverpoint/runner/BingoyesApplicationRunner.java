package com.bingoyes.gat1400.serverpoint.runner;

import cn.hutool.json.JSONUtil;
import com.bingoyes.gat1400.apicaller.bean.ResponseStatusObjectWrapper;
import com.bingoyes.gat1400.apicaller.service.HzApiCallingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value="keepalive.runner")
public class BingoyesApplicationRunner implements ApplicationRunner, Ordered {

    private static Logger logger = LoggerFactory.getLogger(HzApiCallingService.class);

    @Autowired
    HzApiCallingService hzApiCallingService;
    @Override
    public void run(ApplicationArguments args) throws Exception {

        logger.info("register of runner starting");
        new KeepaliveThread().start();
    }

    @Override
    public int getOrder() {
        return 0;
    }

    class KeepaliveThread extends Thread{

        @Override
        public void run() {
            register();
        }
    }

    public void register() {
        int registerResult = -1;
        while (registerResult != 0) {
            logger.info("register to huazun staring");
            String result = hzApiCallingService.register();
            logger.info("register result：");
            logger.info(result);

            ResponseStatusObjectWrapper wrapper = JSONUtil.toBean(result, ResponseStatusObjectWrapper.class);

            registerResult = wrapper.getResponseStatusObject().getStatusCode();

            try {
                //如果注册失败，300秒后继续注册
                if (registerResult != 0) {
                    Thread.sleep(300 * 1000L);
                } else {
                    //如果注册成功，进入保活程序，通知终止当前循环
                    keepalive();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    public void keepalive(){

        while(true) {

            logger.info("keep alive thread to huazun staring");
            String result = hzApiCallingService.keepalive();
            logger.info("keepalive result：");
            logger.info(result);

            ResponseStatusObjectWrapper wrapper = JSONUtil.toBean(result,ResponseStatusObjectWrapper.class);

            int statusCode = wrapper.getResponseStatusObject().getStatusCode();

            try {
                //保活成功，间隔90秒继续保活
                if(statusCode==0) {
                    Thread.sleep(90*1000L);
                }else{
                    //如果保活失败，进行注册，同时推出保活循环
                    register();
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    }
