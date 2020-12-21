package com.bingoyes.gat1400.hzsimulator;

import cn.hutool.json.JSONUtil;
import com.bingoyes.gat1400.apicaller.bean.ResponseStatusListObjectWrapper;
import com.bingoyes.gat1400.serverpoint.entiy.SubscribeNotificationRequestObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Service
@PropertySource(value = {"file:/opt/gat1400/conf/gat1400.yml"})
public class NofificationSimulatorService {

    private static Logger logger = LoggerFactory.getLogger(NofificationSimulatorService.class);

    @Value("${dataFileDir}")
    private String dataFileDir;

    private static String VEHICLE_FILE_NAME ="motor_vehicle_list.csv";


    String serverUrl ="http://127.0.0.1:9120";

    @Value("${deviceId}")
    String deviceId;

    @Resource
    RestTemplate restTemplate;

    @Autowired
    SimulatorDataUtil simulatorDataUtil;



    public void sendVehicleNotificaiton()
    {

        SubscribeNotificationRequestObject subscribeNotificationRequestObject = simulatorDataUtil.getVehicleSimulateData(VEHICLE_FILE_NAME);
        String uri = "/VIID/SubscribeNotifications";
        String url = serverUrl + uri;

        // 请求头设置
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/viid+json"));

        headers.set("Accept","*/*");
        headers.set("User-Identify", deviceId);
        //headers.setConnection("keepalive");

        String requestJson = JSONUtil.toJsonStr(subscribeNotificationRequestObject);
        // 请求参数设置
        HttpEntity<String> httpEntity = new HttpEntity<>(requestJson, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        if (org.apache.http.HttpStatus.SC_OK == responseEntity.getStatusCode().value()) {

            //String resultJson = JSONUtil.toJsonStr(responseEntity.getBody());
            String resultJson = responseEntity.getBody();
            logger.info("send notification success");
            logger.info("notification result json:"+ resultJson);


        }else
            logger.error("send notification error");
    }
}
