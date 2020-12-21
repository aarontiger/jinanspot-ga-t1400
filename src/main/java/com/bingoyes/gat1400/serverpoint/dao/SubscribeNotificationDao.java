package com.bingoyes.gat1400.serverpoint.dao;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bingoyes.gat1400.apicaller.bean.MotorVehicleObjectWrapper;
import com.bingoyes.gat1400.common.exception.ServiceException;
import com.bingoyes.gat1400.serverpoint.entiy.DeviceObjectWrapper;
import com.bingoyes.gat1400.serverpoint.entiy.EfsDomainGroup;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
public class SubscribeNotificationDao {

    private static Logger logger = LoggerFactory.getLogger(SubscribeNotificationDao.class);

    @Autowired
    @Qualifier("mongoTemplate")
    private MongoTemplate mongoTemplate;

    @Autowired
    @Qualifier("efsMongoTemplate")
    private MongoTemplate efsMongoTemplate;

    @Deprecated
    public void saveNotificationMotorVehicle(MotorVehicleObjectWrapper.MotorVehicle motorVehicle){
        try {
            mongoTemplate.insert(motorVehicle);
        }catch (Exception e){
            logger.debug(e.getMessage());
            throw new ServiceException("插入车辆信息失败");
        }
    }

    public void saveDevice(DeviceObjectWrapper.Ape ape){
        try {
            mongoTemplate.insert(ape);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ServiceException("插入Ape信息失败");
        }
    }

    @Deprecated //todo 吉安
    public DeviceObjectWrapper.Ape getDevice(String deviceId){
        try {
            //Query query = new Query(Criteria.where("apeId").is(deviceId));
            Query query = new Query(); //todo
            return mongoTemplate.findOne(query, DeviceObjectWrapper.Ape.class);
        }catch (Exception e){
            logger.debug(e.getMessage());
            throw new ServiceException("查询Ape信息失败");
        }
    }

    @Deprecated //todo 吉安
    public void removeAllDevice(){
        try {
            mongoTemplate.dropCollection("device");
        }catch (Exception e){
            logger.debug(e.getMessage());
            throw new ServiceException("删除设备信息失败");
        }
    }

    public void insertNotificationHistory(String requestUrl,String requestDeviceId,String bodyJson,boolean processSucceed){
        try {
            Map contentMap = (Map)JSON.parse(bodyJson);
            Map notifcationMap = new HashMap();
            notifcationMap.put("content",contentMap);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            notifcationMap.put("operateTime",sdf.format(new Date()));
            notifcationMap.put("requestUrl",requestUrl);
            notifcationMap.put("requestDeviceId",requestDeviceId);
            notifcationMap.put("processSucceed",processSucceed);

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonStr = objectMapper.writeValueAsString(notifcationMap);
            JSONObject parseObject = JSON.parseObject(jsonStr);
            mongoTemplate.insert(parseObject, "notification_history");
        }catch (Exception e){
            logger.debug(e.getMessage());
            throw new ServiceException("插入订阅通知信息失败");
        }
    }

    public EfsDomainGroup getEfsDomainGroup(String adCode){
        Query query =new Query(Criteria.where("adCode").is(adCode));
        return efsMongoTemplate.findOne(query,EfsDomainGroup.class,"domain_group");
    }
}
