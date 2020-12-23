package com.bingoyes.gat1400.apicaller.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bingoyes.gat1400.common.exception.ServiceException;
import com.bingoyes.gat1400.serverpoint.service.SubscribeNotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
public class SubscribeDao {
    private static Logger log = LoggerFactory.getLogger(SubscribeDao.class);

    @Autowired
    @Qualifier("mongoTemplate")
    private MongoTemplate mongoTemplate;

    public void insertSubscribeHistory(String serverUrl,String deviceId,String json,Date operateDate) {

        try {
            Map contentMap = (Map) JSON.parse(json);
            Map subscribeMap = new HashMap();
            subscribeMap.put("serverUrl",serverUrl);
            subscribeMap.put("deviceId",deviceId);
            subscribeMap.put("content",contentMap);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            subscribeMap.put("operateTime",sdf.format(operateDate));

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonStr = objectMapper.writeValueAsString(subscribeMap);
            JSONObject parseObject = JSON.parseObject(jsonStr);

            mongoTemplate.insert(parseObject, "subscribe_history");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServiceException("插入订阅信息失败");
        }
    }
}
