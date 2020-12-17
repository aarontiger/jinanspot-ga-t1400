package com.bingoyes.gat1400.serverpoint.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bingoyes.gat1400.apicaller.bean.MotorVehicleObjectWrapper;
import com.bingoyes.gat1400.common.exception.ServiceException;
import com.bingoyes.gat1400.serverpoint.entiy.DeviceObjectWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class SubscribeNotificationDao {

    private static Logger logger = LoggerFactory.getLogger(SubscribeNotificationDao.class);

    @Resource
    private MongoTemplate mongoTemplate;

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

    public void removeAllDevice(){
        try {
            mongoTemplate.dropCollection("device");
        }catch (Exception e){
            logger.debug(e.getMessage());
            throw new ServiceException("插入Ape信息失败");
        }
    }

    public void insertNotificationHistory(String json){
        try {
            JSONObject parseObject = JSON.parseObject(json);
            mongoTemplate.insert(parseObject, "notification_history");
        }catch (Exception e){
            logger.debug(e.getMessage());
            throw new ServiceException("插入订阅通知信息失败");
        }
    }
}
