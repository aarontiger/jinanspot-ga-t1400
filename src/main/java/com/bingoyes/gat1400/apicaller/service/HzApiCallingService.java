package com.bingoyes.gat1400.apicaller.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.bingoyes.gat1400.apicaller.bean.*;
import com.bingoyes.gat1400.apicaller.dao.SubscribeDao;
import com.bingoyes.gat1400.common.exception.ServiceException;
import com.bingoyes.gat1400.hzsimulator.SimulatorDataUtil;
import com.bingoyes.gat1400.util.IdUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@PropertySource(value = {"file:/opt/gat1400/conf/gat1400.yml"})
//@ConfigurationProperties(prefix = "remotegat")
//@Slf4j
public class HzApiCallingService {
    private static Logger log = LoggerFactory.getLogger(HzApiCallingService.class);

    @Resource
    private RestTemplate restTemplate;

    @Resource
    RestTemplate restTemplateDigest;

    @Autowired
    private SubscribeDao subscribeDao;

    @Autowired
    private SimulatorDataUtil simulatorDataUtil;

    @Value("${hz-hostname}")
    private String hostname;

    @Value("${hz-port}")
    private int port;

    @Value("${deviceId}")
    private String deviceId;

    @Value("${hz-user}")
    private String username;

    @Value("${hz-password}")
    private String password;

    @Value("${resourceUri}")
    private String resourceUri;

    //@Value("${realm}")
    private String realm;

    @Value("${notificationReceiveAddr}")
    private String notificationReceiveAddr;

    public List<String> subscribeMotorVehicle()
    {
       return doSubscribeWithAuth("13"); //13为机动车信息订阅
    }

    public List<String> doSubscribeWithAuth(String detailType){
        List<String> resultList = new ArrayList();
        Set<String> keySet = simulatorDataUtil.getAllHuazunTollgageIdSet();
        int count =0;
        String[] idList = new String[]{};
        idList = keySet.toArray(idList);

        Date operatDate = new Date();
        int j=0;
        for (;j<idList.length/10;j++) {
            List<String> idArrayList = new ArrayList();
            for(int i=0;i<10;i++) {
                idArrayList.add(idList[j * 10 + i]);
            }
            String [] idPartList = new String[]{};
            idPartList = idArrayList.toArray(idPartList);
            String tollgateIds = String.join(",",idPartList);

            String result = doSubscribeWithAuth(detailType,tollgateIds,String.valueOf(j+1),operatDate);
            resultList.add(result);
        }

        ///
        List<String> idArrayList = new ArrayList();
        for(int i=0;i<idList.length%10;i++) {
            idArrayList.add(idList[j * 10 + i]);
        }
        String [] idPartList = new String[]{};
        idPartList = idArrayList.toArray(idPartList);
        String tollgateIds = String.join(",",idPartList);

        String result = doSubscribeWithAuth(detailType,tollgateIds,String.valueOf(j+1),operatDate);
        resultList.add(result);


        return  resultList;
    }

    /**
     * 提交订阅请求公用方法
     * @param detailType
     * @return
     */
    public String doSubscribeWithAuth(String detailType,String resourceUri,String batch,Date subDate){
        try {

            String serverUrl ="http://"+hostname+":"+port+"/VIID/Subscribes";

            SubscribeListRequestObject.SubscribeListObject subscribeListObject = new SubscribeListRequestObject.SubscribeListObject();
            List<SubscribeListRequestObject.Subscribe> subscribeList = new ArrayList<>();
            SubscribeListRequestObject.Subscribe subscribe = new SubscribeListRequestObject.Subscribe();


            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String subscribeId = "36000000000003"+sdf.format(subDate)+ "9900"+batch;//todo 吉安

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(subDate);
            String beginTime = sdf.format(calendar.getTime());
            calendar.add(Calendar.YEAR,2);
            String endTime = sdf.format(calendar.getTime());

            subscribe.setSubscribeID(subscribeId);
            subscribe.setTitle("信息订阅");
            subscribe.setSubscribeDetail(detailType);
            //subscribe.setResourceURI(resourceUri);  //todo 吉安

            //取得tollgate_list.csv中的所有华尊卡口
            //subscribe.setResourceURI(simulatorDataUtil.getAllHuazunTollgageId());
            subscribe.setResourceURI(resourceUri);  //每10个id发一个请求，请参数传入
            subscribe.setApplicantName("bingoyes"); //订阅人
            subscribe.setApplicantOrg("zhizhu"); //订阅单位
            subscribe.setBeginTime(beginTime);
            subscribe.setEndTime(endTime);
            subscribe.setReceiveAddr(notificationReceiveAddr); //订阅通知接收地址，在配置文件中配置
            subscribe.setOperateType(0); //0位订阅
            subscribe.setReason("business cooperation");
            subscribe.setReportInterval(0);  //0表示不限制
            subscribe.setSubscribeStatus(0); //todo

            subscribeList.add(subscribe);
            subscribeListObject.setSubscribeObject(subscribeList);


            SubscribeListRequestObject subscribeListRequestObject = new SubscribeListRequestObject();
            subscribeListRequestObject.setSubscribeListObject(subscribeListObject);
            Object result = posWithRestTemplateWithAuth(serverUrl,subscribeListRequestObject,ResponseStatusListObjectWrapper.class);
            String responseJson = JSONUtil.toJsonStr(result);
            log.info(responseJson);

            //mongo存储订阅信息
            subscribeDao.insertSubscribeHistory(serverUrl,deviceId,JSONUtil.toJsonStr(subscribeListObject),subDate);

            return  responseJson;

        } catch (Exception e) {
            System.out.println("订阅失败："+e);
            e.printStackTrace();
            throw new SecurityException("订阅失败");
        }
    }

    /**
     * 使用restTemplate带digest auth发请求
     * @param url
     * @param requestObject
     * @return
     */
    public Object posWithRestTemplateWithAuth(String url, Object requestObject,Class responseClass){

        //请求头设置
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/viid+json"));
        headers.set("User-Identify", deviceId);
        //headers.setConnection("keepalive");

         headers.set("Accept","*/*");
//        headers.set("Accept-Encoding","gzip");
//        headers.set("Content-Encoding","UTF-8");
        //todo 吉安
        headers.set("Cookie","JSESSIONID=2DBC9C95A77BBA4229D5BE31E9051A5E");
        headers.set("Postman-Token","2f4d793f-66b8-4a68-9179-650037c3fdfd");


        // 请求参数设置
        String requestJson = JSONUtil.toJsonStr(requestObject);
        HttpEntity<String> httpEntity = new HttpEntity<>(requestJson, headers);

        //ResponseEntity<Object> responseEntity = restTemplateDigest.exchange(url, HttpMethod.POST, httpEntity,responseClass);
//        ResponseEntity<String> responseEntity = restTemplateDigest.exchange(url, HttpMethod.POST, httpEntity,String.class);
        ResponseEntity<String> responseEntity = restTemplateDigest.exchange(url, HttpMethod.POST, httpEntity,String.class);

        if (org.apache.http.HttpStatus.SC_OK == responseEntity.getStatusCode().value()) {
            log.info("restTemplate called success");
            log.info("url:"+url);
            log.info("deviceId:"+deviceId);
            log.info("request body:"+JSONUtil.toJsonStr(requestObject));

            log.info("response body:"+responseEntity.getBody());
            log.info("response headers:");

            for (Map.Entry<String, List<String>> entry : responseEntity.getHeaders().entrySet()) {
                System.out.println("Key : " + entry.getKey() +
                        " ,Value : " + entry.getValue());
            }
            String responseJson = responseEntity.getBody();
            Object responseObject = JSONUtil.toBean(responseJson,responseClass);
            return responseJson;

        }else {
            log.error("restTemplate called error");
            log.error("http status:"+responseEntity.getStatusCode().value());
            throw new ServiceException("restTemplate called error");
        }
    }

    public List<String> subscribeDevice(){
        return doSubscribeWithAuth("3");//3为采集设备订阅
    }

    public List<String> subscribeTollgate()
    {
       return doSubscribeWithAuth("7"); //7为卡口订阅
    }


    public String register(){

        String url ="http://"+hostname+":"+port+"/VIID/System/Register";
        RegisterRequestObject.RegisterObject  registerObject = new RegisterRequestObject.RegisterObject();
        registerObject.setDeviceID(deviceId);

        RegisterRequestObject registerRequestObject = new RegisterRequestObject();
        registerRequestObject.setRegisterObject(registerObject);
        Object result = posWithRestTemplateWithAuth(url,registerRequestObject,ResponseStatusObjectWrapper.class);
        String responseJson = JSONUtil.toJsonStr(result);
        log.info(responseJson);
        return  responseJson;
    }

    public String unregister(){

        String url ="http://"+hostname+":"+port+"/VIID/System/UnRegister";

        UnRegisterRequestObject.UnRegisterObject unRegisterObject = new UnRegisterRequestObject.UnRegisterObject();
        unRegisterObject.setDeviceID(deviceId);

        UnRegisterRequestObject unRegisterRequestObject = new UnRegisterRequestObject();
        unRegisterRequestObject.setUnRegisterObject(unRegisterObject);

        Object result = posWithRestTemplateWithAuth(url,unRegisterRequestObject,ResponseStatusObjectWrapper.class);
        String responseJson = JSONUtil.toJsonStr(result);
        log.info(responseJson);
        return  responseJson;
    }

    public String keepalive() {

        String url ="http://"+hostname+":"+port+ "/VIID/System/Keepalive";

        KeepaliveRequestObject.KeepaliveObject keepaliveObject = new KeepaliveRequestObject.KeepaliveObject();
        keepaliveObject.setDeviceID(deviceId);

        KeepaliveRequestObject keepaliveRequestObject = new KeepaliveRequestObject();
        keepaliveRequestObject.setKeepaliveObject(keepaliveObject);

        Object result = posWithRestTemplateWithAuth(url,keepaliveRequestObject,ResponseStatusObjectWrapper.class);
        String responseJson = JSONUtil.toJsonStr(result);
        log.info(responseJson);
        return  responseJson;
    }

    /**
     * 不带安全发送订阅请求
     * @param detailType
     * @return
     */
    @Deprecated
    public String doSubscribeNoAuth(String detailType)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/json;charset=utf-8"));
        headers.set("User-Identify", deviceId);
        SubscribeListRequestObject.SubscribeListObject subscribeListObject = new SubscribeListRequestObject.SubscribeListObject();
        List<SubscribeListRequestObject.Subscribe> subscribeList = new ArrayList<>();
        SubscribeListRequestObject.Subscribe subscribe = new SubscribeListRequestObject.Subscribe();

        String subscribeId =UUID.randomUUID().toString();
        subscribe.setSubscribeID(subscribeId); //todo
        subscribe.setSubscribeDetail(detailType); // 3为采集设备

        subscribeList.add(subscribe);
        subscribeListObject.setSubscribeObject(subscribeList);

        HttpEntity<String> httpEntity = new HttpEntity<>(JSONUtil.toJsonStr(subscribeListObject), headers);

        //log.info("车牌上传消息体：{}", JSONUtil.toJsonStr(subscribeObject));

        String serverUrl = "http://"+hostname+":"+port;
        String url = serverUrl + "/VIID/Subscribes";
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, httpEntity, String.class);
        int statusCode = responseEntity.getStatusCode().value();
        if (statusCode == HttpStatus.SC_OK) {
            String responseBody = responseEntity.getBody();
            if (StrUtil.isNotBlank(responseBody)) {

                return responseBody;
            }
        }
        throw new ServiceException("订阅失败");
    }

    /**
     * 带digest auth 的 post,请求华尊服务器
     * @param url
     * @param requestBody
     * @return
     */
    @Deprecated
    public String postWithHttpClientAuth(String url, String requestBody){
        try {

            CredentialsProvider credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(
                    new AuthScope(hostname, port,realm),
                    new UsernamePasswordCredentials(username, password));
            CloseableHttpClient httpclient = HttpClients.custom()
                    .setDefaultCredentialsProvider(credsProvider).build();

            HttpPost postMethod = new HttpPost(url);
            postMethod.addHeader("User-Identify", deviceId);
            StringEntity s = new StringEntity(requestBody);
            s.setContentEncoding("utf-8");
            s.setContentType("application/json");
            postMethod.setEntity(s);
            HttpResponse response = httpclient.execute(postMethod);

            log.debug("resCode = " + response.getStatusLine().getStatusCode());

            String result = EntityUtils.toString(response.getEntity(), "utf-8");
            log.debug("result = " +result );
            return result ;

        } catch (Exception e) {
            throw new SecurityException("访问华尊系统接口失败");
        }
    }

}
