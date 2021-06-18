package com.bingoyes.gat1400.apicaller.controller;


import cn.hutool.json.JSONUtil;
import com.bingoyes.Gat1400BingoClientApp;
import com.bingoyes.gat1400.apicaller.service.HzApiCallingService;
import com.bingoyes.gat1400.apicaller.bean.KeepaliveRequestObject;
import com.bingoyes.gat1400.apicaller.bean.RegisterRequestObject;
import com.bingoyes.gat1400.apicaller.bean.UnRegisterRequestObject;
import com.bingoyes.gat1400.common.result.Result;
import com.bingoyes.gat1400.util.DigestUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Deprecated
@RestController
@Slf4j
@PropertySource(value = {"file:/opt/gat1400/conf/gat1400.yml"})
public class HzApiCallingController {

    private static Logger logger = LoggerFactory.getLogger(HzApiCallingController.class);

    private String serverUrl="http://127.0.0.1:9120";
    @Value("${hz-hostname}")
    private String hostname;
    @Value("${hz-port}")
    private int port;
    //@Value("${realm}")
    private String realm;

    @Value("${hz-user}")
    private String username;

    @Value("${hz-password}")
    private String password;

    @Value("${deviceId}")
    private String deviceId;

    @Resource
    @Qualifier("restTemplateDigest")
    private RestTemplate restTemplateDigest;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private HzApiCallingService hzApiCallingService;

    @PostMapping("/api2/register")
    public Result register(String deviceId) {
        String uri = "/VIID/System/Register";
        String url = serverUrl + uri;

        // 请求头设置
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
        headers.set("User-Identify", deviceId);
        headers.setConnection("keepalive");
        // 请求参数设置
        RegisterRequestObject registerRequestObject = new RegisterRequestObject();
        RegisterRequestObject.RegisterObject registerObject = new RegisterRequestObject.RegisterObject();
        registerObject.setDeviceID(deviceId);
        registerRequestObject.setRegisterObject(registerObject);

        HttpEntity<String> httpEntity = new HttpEntity<>(JSONUtil.toJsonStr(registerRequestObject), headers);
        // 第一次请求
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        if (HttpStatus.SC_UNAUTHORIZED == responseEntity.getStatusCode().value()) {
            HttpHeaders responseEntityHeaders = responseEntity.getHeaders();
            String authenticate = responseEntityHeaders.get("WWW-Authenticate").get(0);
            String[] children = authenticate.split(",");
            // Digest realm="myrealm",qop="auth",nonce="dmktZGlnZXN0OjQzNTQyNzI3Nzg="
            String realm = null, qop = null, nonce = null, opaque = null, method = "POST";
            for (int i = 0; i < children.length; i++) {
                String item = children[i];

                String[] itemEntry = item.split("=");
                String name = itemEntry[0].trim();
                String value = itemEntry[1].replaceAll("\"", "").trim();
                if (name.equals("Digest realm")) {
                    realm = value;
                } else if (name.equals("qop")) {
                    qop = value;
                } else if (name.equals("nonce")) {
                    nonce = value;
                }
            }
            String nc = "00000001";
            String cnonce = DigestUtils.generateSalt2(8);
            String response = DigestUtils.getResponse(username, realm, password, nonce, nc, cnonce, qop, method, uri);
            String authorization = DigestUtils.getAuthorization(username, realm, nonce, uri, qop, nc, cnonce, response, opaque);
            headers.set("Authorization", authorization);

            // 第二次请求
            httpEntity = new HttpEntity<>(JSONUtil.toJsonStr(registerRequestObject), headers);
            responseEntity = restTemplateDigest.exchange(url, HttpMethod.POST, httpEntity, String.class);
            if (HttpStatus.SC_OK == responseEntity.getStatusCode().value()) {
                return Result.success();
            }
        }
        return Result.error("注册失败");

    }

    @PostMapping("/keepalive")
    public Result keepalive(
            String deviceId
    ) {
        String url = serverUrl + "/VIID/System/Keepalive";

        // 请求头设置
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
        headers.set("User-Identify", deviceId);
        headers.setConnection("keepalive");
        // 请求参数设置
        KeepaliveRequestObject keepaliveRequestObject = new KeepaliveRequestObject();
        KeepaliveRequestObject.KeepaliveObject keepaliveObject = new KeepaliveRequestObject.KeepaliveObject();
        keepaliveObject.setDeviceID(deviceId);
        keepaliveRequestObject.setKeepaliveObject(keepaliveObject);
        logger.info("保活请求 url:{} ,参数：{}", url, keepaliveRequestObject);

        HttpEntity<KeepaliveRequestObject> httpEntity = new HttpEntity<>(keepaliveRequestObject, headers);
        // 请求执行
        ResponseEntity<String> responseEntity = restTemplateDigest.exchange(url, HttpMethod.POST, httpEntity, String.class);
        if (HttpStatus.SC_OK == responseEntity.getStatusCode().value()) {
            return Result.success();
        }
        return Result.error("保活失败");
    }


    @PostMapping("/unregister")
    public Result unregister(String deviceId) {

        String uri = "/VIID/System/UnRegister";
        String url = serverUrl + uri;

        // 请求头设置
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
        headers.set("User-Identify", deviceId);
        headers.setConnection("keepalive");
        // 请求参数设置
        UnRegisterRequestObject unRegisterRequestObject = new UnRegisterRequestObject();
        UnRegisterRequestObject.UnRegisterObject unRegisterObject = new UnRegisterRequestObject.UnRegisterObject();
        unRegisterObject.setDeviceID(deviceId);
        unRegisterRequestObject.setUnRegisterObject(unRegisterObject);

        HttpEntity<String> httpEntity = new HttpEntity<>(JSONUtil.toJsonStr(unRegisterRequestObject), headers);
        // 第一次请求
        ResponseEntity<String> responseEntity = restTemplateDigest.exchange(url, HttpMethod.POST, httpEntity, String.class);
        if (HttpStatus.SC_UNAUTHORIZED == responseEntity.getStatusCode().value()) {
            HttpHeaders responseEntityHeaders = responseEntity.getHeaders();
            String authenticate = responseEntityHeaders.get("WWW-Authenticate").get(0);
            String[] children = authenticate.split(",");
            // Digest realm="myrealm",qop="auth",nonce="dmktZGlnZXN0OjQzNTQyNzI3Nzg="
            String realm = null, qop = null, nonce = null, opaque = null, method = "POST";
            for (int i = 0; i < children.length; i++) {
                String item = children[i];
                String[] itemEntry = item.split("=");
                if (itemEntry[0].equals("Digest realm")) {
                    realm = itemEntry[1].replaceAll("\"", "");
                } else if (itemEntry[0].equals("qop")) {
                    qop = itemEntry[1].replaceAll("\"", "");
                } else if (itemEntry[0].equals("nonce")) {
                    nonce = itemEntry[1].replaceAll("\"", "");
                }
            }
            String nc = "00000001";
            String cnonce = DigestUtils.generateSalt2(8);
            String response = DigestUtils.getResponse(username, realm, password, nonce, nc, cnonce, qop, method, uri);
            String authorization = DigestUtils.getAuthorization(username, realm, nonce, uri, qop, nc, cnonce, response, opaque);
            headers.set("Authorization", authorization);

            // 第二次请求
            httpEntity = new HttpEntity<>(JSONUtil.toJsonStr(unRegisterRequestObject), headers);
            responseEntity = restTemplateDigest.exchange(url, HttpMethod.POST, httpEntity, String.class);
            if (HttpStatus.SC_OK == responseEntity.getStatusCode().value()) {
                return Result.success();
            }
        }
        return Result.error("注销失败");
    }

    @GetMapping("/time")
    public Result time() {
        /*String url = "http://" + ip + ":" + port + "/VIID/System/Time";
        // 请求头设置
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Identify", deviceId);
        headers.setConnection("keepalive");
        HttpEntity<Object> httpEntity = new HttpEntity<>(null,headers);

        ResponseEntity<SystemTimeObject> entity = restTemplate.getForEntity(url, SystemTimeObject.class, httpEntity);
        SystemTimeObject.SystemTime systemTime = entity.getBody().getSystemTime();*/

        String time = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
        return Result.success(time);
    }
    @PostMapping("/api2/registerHz")
    public Result registerHz() {
        String result = hzApiCallingService.register();
        return Result.success(result);
    }



    public static void main(String[] args){
        HzApiCallingController controller = new HzApiCallingController();
        Result result = controller.registerHz();
        System.out.println(result);
    }
}

