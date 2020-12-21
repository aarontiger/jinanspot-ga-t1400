package com.bingoyes.gat1400.hzsimulator;

import com.bingoyes.gat1400.common.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



@EnableScheduling
@Component
@ConditionalOnProperty(value="notification.scheduler")
public class NofificationSimulatorScheduler {

    private static Logger logger = LoggerFactory.getLogger(NofificationSimulatorScheduler.class);

    @Autowired
    NofificationSimulatorService nofificationSimulatorService;

    @Scheduled(fixedRate = 60000)
    public void schedule(){
        try {
            nofificationSimulatorService.sendVehicleNotificaiton();
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
