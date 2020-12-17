package com.bingoyes.gat1400.serverpoint.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import com.bingoyes.gat1400.apicaller.bean.*;
import com.bingoyes.gat1400.common.exception.ServiceException;
import com.bingoyes.gat1400.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class SystemController {
    private static Logger log = LoggerFactory.getLogger(SystemController.class);
    @RequestMapping(value = "/hello3", method = RequestMethod.GET)
    public Result hello3(){
        ResponseStatusListObjectWrapper.ResponseStatus responseStatus = new ResponseStatusListObjectWrapper.ResponseStatus();
        return Result.success(responseStatus);
    }

    @RequestMapping(value = "/hello2", method = RequestMethod.GET)
    public Result hello2(){
        ResponseStatusListObjectWrapper.ResponseStatus responseStatus = new ResponseStatusListObjectWrapper.ResponseStatus();
        return Result.success(responseStatus);
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public Result hello(){
        ResponseStatusListObjectWrapper.ResponseStatus responseStatus = new ResponseStatusListObjectWrapper.ResponseStatus();
        return Result.success(responseStatus);
    }

    @RequestMapping(value = "/VIID/System/Register", method = RequestMethod.POST)
    public String register(@RequestBody Map<String,Object> requestMap, HttpServletResponse response){
        log.info("Register received");
        String requestJson = JSONUtil.toJsonStr(requestMap);
        log.info("requestJson:"+requestJson);

        RegisterRequestObject registerRequestObject = JSONUtil.toBean(requestJson,RegisterRequestObject.class);

        response.addHeader("Content-Type","text/html;charset=UTF-8");
        ResponseStatusObjectWrapper.ResponseStatusObject responseStatusObject = new ResponseStatusObjectWrapper.ResponseStatusObject();
        responseStatusObject.setId(registerRequestObject.getRegisterObject().getDeviceID());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        responseStatusObject.setLocalTime(sdf.format(new Date()));
        responseStatusObject.setRequestURL("/VIID/System/Register");

        try {
            responseStatusObject.setStatusCode(0);
            responseStatusObject.setStatusString("注册成功");

            log.info("register request success");
        }catch(ServiceException e){
            responseStatusObject.setStatusCode(-1);
            responseStatusObject.setStatusString("注册失败");
            log.error("register request error",e);
        }
        ResponseStatusObjectWrapper wrapper = new ResponseStatusObjectWrapper();
        wrapper.setResponseStatusObject(responseStatusObject);
        String responseJson = JSONUtil.toJsonStr(wrapper);
        return responseJson;
    }

    @RequestMapping(value = "/VIID/System/Keepalive", method = RequestMethod.POST)
    public String Keepalive(@RequestBody Map<String,Object> requestMap){
        log.info("Keepalive received");

        String requestJson = JSONUtil.toJsonStr(requestMap);
        log.info("requestJson:"+requestJson);

        KeepaliveRequestObject keepaliveRequestObject = JSONUtil.toBean(requestJson,KeepaliveRequestObject.class);

        ResponseStatusObjectWrapper.ResponseStatusObject responseStatusObject = new ResponseStatusObjectWrapper.ResponseStatusObject();
        responseStatusObject.setId(keepaliveRequestObject.getKeepaliveObject().getDeviceID());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        responseStatusObject.setLocalTime(sdf.format(new Date()));
        responseStatusObject.setRequestURL("/VIID/System/Keepalive");
        try {

           responseStatusObject.setStatusCode(0);
           responseStatusObject.setStatusString("保活成功");
           log.info("keepalive request success");

       }catch(ServiceException e){
           responseStatusObject.setStatusCode(-1);
           responseStatusObject.setStatusString("保活失败");
           log.error("keepalive request error",e);
       }
        ResponseStatusObjectWrapper wrapper = new ResponseStatusObjectWrapper();
        wrapper.setResponseStatusObject(responseStatusObject);
        String responseJson = JSONUtil.toJsonStr(wrapper);
        return responseJson;
    }

    @RequestMapping(value = "/VIID/System/UnRegister", method = RequestMethod.POST)
    public String UnRegister(@RequestBody Map<String,Object> requestMap)
    {
        log.info("UnRegister received");
        String requestJson = JSONUtil.toJsonStr(requestMap);
        log.info("requestJson:"+requestJson);

        UnRegisterRequestObject unRegisterRequestObject = JSONUtil.toBean(requestJson,UnRegisterRequestObject.class);

        ResponseStatusObjectWrapper.ResponseStatusObject responseStatusObject = new ResponseStatusObjectWrapper.ResponseStatusObject();
        responseStatusObject.setId(unRegisterRequestObject.getUnRegisterObject().getDeviceID());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        responseStatusObject.setLocalTime(sdf.format(new Date()));
        responseStatusObject.setRequestURL("/VIID/System/UnRegister");
        try {

            responseStatusObject.setStatusCode(0);
            responseStatusObject.setStatusString("注销成功");
            log.info("unregister request success");

        }catch(ServiceException e){
            responseStatusObject.setStatusCode(-1);
            responseStatusObject.setStatusString("注销失败");
            log.error("unregister request error",e);
        }
        ResponseStatusObjectWrapper wrapper = new ResponseStatusObjectWrapper();
        wrapper.setResponseStatusObject(responseStatusObject);
        String responseJson = JSONUtil.toJsonStr(wrapper);
        return responseJson;
    }

    @RequestMapping(value = "/VIID/Subscribes", method = RequestMethod.POST)
    public String  Subscribes(@RequestBody Map<String,Object> requestMap)
    {
        log.info("Subscribes received");
        String requestJson = JSONUtil.toJsonStr(requestMap);
        log.info("requestJson:"+requestJson);

        SubscribeListRequestObject subscribeListRequestObject = JSONUtil.toBean(requestJson,SubscribeListRequestObject.class);

        ResponseStatusListObjectWrapper.ResponseStatusListObject responseStatusListObject = new ResponseStatusListObjectWrapper.ResponseStatusListObject();
        List<ResponseStatusListObjectWrapper.ResponseStatus> statusList = new ArrayList<>();
        ResponseStatusListObjectWrapper.ResponseStatus responseStatus = new ResponseStatusListObjectWrapper.ResponseStatus();

        List<SubscribeListRequestObject.Subscribe> list= subscribeListRequestObject.getSubscribeListObject().getSubscribeObject();
        String subscribeId="-1";
        if(!list.isEmpty())
            subscribeId = list.get(0).getSubscribeID();
        responseStatus.setId(subscribeId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        responseStatus.setLocalTime(sdf.format(new Date()));
        responseStatus.setRequestURL("/VIID/System/Subscribes");

        try{

            responseStatus.setStatusCode(0);
            responseStatus.setStatusString("订阅成功");
            log.info("subscribe request success");
        }catch(ServiceException e){
            responseStatus.setStatusCode(-1);
            responseStatus.setStatusString("订阅失败");
            log.error("subscribe request error",e);
        }
        statusList.add(responseStatus);
        responseStatusListObject.setResponseStatusObject(statusList);
        ResponseStatusListObjectWrapper wrapper = new ResponseStatusListObjectWrapper();
        wrapper.setResponseStatusListObject(responseStatusListObject);
        String responseJson = JSONUtil.toJsonStr(wrapper);
        return responseJson;
    }
}
