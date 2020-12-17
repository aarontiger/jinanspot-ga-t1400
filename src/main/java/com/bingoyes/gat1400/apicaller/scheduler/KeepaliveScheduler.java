package com.bingoyes.gat1400.apicaller.scheduler;

import com.bingoyes.gat1400.apicaller.service.HzApiCallingService;
import com.bingoyes.gat1400.common.exception.ServiceException;
import com.bingoyes.gat1400.hzsimulator.NofificationSimulatorScheduler;
import com.bingoyes.gat1400.hzsimulator.NofificationSimulatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
@ConditionalOnProperty(value="keepalive.scheduler")
public class KeepaliveScheduler {
    private static Logger logger = LoggerFactory.getLogger(NofificationSimulatorScheduler.class);

    @Autowired
    HzApiCallingService hzApiCallingService;

    @Scheduled(fixedRate = 60000)
    public void schedule(){
        try {
            hzApiCallingService.keepalive();
            logger.info("send keepalive success");

        } catch (ServiceException e) {
            logger.error("send keepalive  error");
            e.printStackTrace();
        }catch (Exception e)
        {
            logger.error("send keepalive  error");
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
