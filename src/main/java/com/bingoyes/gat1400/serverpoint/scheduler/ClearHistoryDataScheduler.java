package com.bingoyes.gat1400.serverpoint.scheduler;

import com.bingoyes.gat1400.common.exception.ServiceException;
import com.bingoyes.gat1400.serverpoint.service.SubscribeNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@EnableScheduling
@Component
@ConditionalOnProperty(value="keepalive.runner")
//清理超过一个月的订阅和订阅通知数据
public class ClearHistoryDataScheduler {

    private static Logger logger = LoggerFactory.getLogger(ClearHistoryDataScheduler.class);

    @Autowired
    SubscribeNotificationService subscribeNotificationService;

    @Scheduled(cron = "0 0 0 * * ?") //每天0点执行一次
    //@Scheduled(fixedRate = 60000)   //todo testcode
    //@Scheduled(cron = "0 38 15 * * ?") //todo testcode 每天13:38执行
    public void schedule(){
        try {
            subscribeNotificationService.clearHistoryData(30);
            logger.info("clear history data success");

        } catch (ServiceException e) {
            logger.error("clear history data  error");
            e.printStackTrace();
        }catch (Exception e)
        {
            logger.error("clear history data  error");
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
