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

    //每天0点执行一次
    //@Scheduled(cron = "0 0 0 * * ?")
    @Scheduled(fixedRate = 60000)
    public void schedule(){
        try {
            subscribeNotificationService.clearHistoryData(1);
            logger.info("send notification success");

        } catch (ServiceException e) {
            logger.error("send notification  error");
            e.printStackTrace();
        }catch (Exception e)
        {
            logger.error("send notification  error");
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
