package com.bingoyes.gat1400.apicaller.dao;

import com.alibaba.fastjson.JSONObject;
import com.bingoyes.gat1400.common.exception.ServiceException;
import com.bingoyes.gat1400.serverpoint.service.SubscribeNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
@Slf4j
public class SubscribeDao {
    private static Logger log = LoggerFactory.getLogger(SubscribeDao.class);

    @Resource
    private MongoTemplate mongoTemplate;

    public void insertSubscribeHistory(String json) {

        try {
            JSONObject parseObject = JSONObject.parseObject(json);
            mongoTemplate.insert(parseObject, "subscribe_history");
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new ServiceException("插入订阅信息失败");
        }
    }
}
