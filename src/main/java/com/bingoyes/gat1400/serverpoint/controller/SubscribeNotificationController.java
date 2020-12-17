package com.bingoyes.gat1400.serverpoint.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import com.bingoyes.gat1400.apicaller.bean.ResponseStatusListObjectWrapper;
import com.bingoyes.gat1400.common.exception.GlobalExceptionHandler;
import com.bingoyes.gat1400.common.exception.ServiceException;
import com.bingoyes.gat1400.common.result.Result;
import com.bingoyes.gat1400.serverpoint.entiy.SubscribeNotificationRequestObject;
import com.bingoyes.gat1400.serverpoint.service.SubscribeNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class SubscribeNotificationController {
    private static Logger log = LoggerFactory.getLogger(SubscribeNotificationController.class);

    @Autowired
    private SubscribeNotificationService notificationService;

    @RequestMapping(value = "/VIID/SubscribeNotifications", method = RequestMethod.POST)
    public @ResponseBody String notification(@RequestBody Map<String,Object> requestMap) {
        log.info("SubscribeNotification received");
        String requestJson = JSONUtil.toJsonStr(requestMap);
        log.info("requestJson:"+requestJson);

        SubscribeNotificationRequestObject subscribeNotificationRequestObject = JSONUtil.toBean(requestJson,SubscribeNotificationRequestObject.class);

        ResponseStatusListObjectWrapper.ResponseStatusListObject responseStatusListObject = new ResponseStatusListObjectWrapper.ResponseStatusListObject();
        List<ResponseStatusListObjectWrapper.ResponseStatus> statusList = new ArrayList<>();
        ResponseStatusListObjectWrapper.ResponseStatus responseStatus = new ResponseStatusListObjectWrapper.ResponseStatus();
        responseStatus.setId(UUID.fastUUID().toString());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        responseStatus.setLocalTime(sdf.format(new Date()));
        responseStatus.setRequestURL("/VIID/SubscribeNotifications");

        try {

            notificationService.processNotificationList(subscribeNotificationRequestObject.getSubscribeNotificationListObject());

            responseStatus.setStatusCode(0);
            responseStatus.setStatusString("发送订阅通知成功");
            log.info("subscribe notification success");
        } catch (ServiceException e) {
            responseStatus.setStatusCode(-1);
            responseStatus.setStatusString("发送订阅通知失败");
            log.info("subscribe notification failure");
        }
        statusList.add(responseStatus);
        responseStatusListObject.setResponseStatusObject(statusList);
        ResponseStatusListObjectWrapper wrapper = new ResponseStatusListObjectWrapper();
        wrapper.setResponseStatusListObject(responseStatusListObject);
        String responseJson = JSONUtil.toJsonStr(wrapper);
        return responseJson;
    }
}
