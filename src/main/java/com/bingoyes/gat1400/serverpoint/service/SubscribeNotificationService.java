package com.bingoyes.gat1400.serverpoint.service;

import afu.org.checkerframework.checker.igj.qual.I;
import cn.hutool.json.JSONUtil;
import com.bingoyes.gat1400.apicaller.bean.MotorVehicleObjectWrapper;
import com.bingoyes.gat1400.common.exception.ServiceException;
import com.bingoyes.gat1400.serverpoint.dao.SubscribeNotificationDao;
import com.bingoyes.gat1400.serverpoint.entiy.DeviceObjectWrapper;
import com.bingoyes.gat1400.serverpoint.entiy.SubscribeNotificationRequestObject;
import com.bingoyes.gat1400.serverpoint.entiy.TollgateObjectWrapper;
import com.bingoyes.gat1400.hzsimulator.SimulatorDataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Service
@PropertySource(value = {"file:/opt/gat1400/conf/gat1400.yml"})
public class SubscribeNotificationService {

    private static Logger logger = LoggerFactory.getLogger(SubscribeNotificationService.class);

    private final static String  IMAGE_ROOT_DIR = "E:\\gat-image\\";

    @Value("${imageBaseDir}")
    private String imageBaseDir;

    @Autowired
    private SubscribeNotificationDao notificationDao;

    @Autowired
    private SimulatorDataUtil simulatorDataUtil;

    /**
     * 处理订阅通知
     * @param notificaitonListObject
     */
    public void processNotificationList(SubscribeNotificationRequestObject.SubscribeNotificationListObject notificaitonListObject){

        logger.info("begin to process notifications");

        //todo
        //导入设备信息
        //todo 吉安
        simulatorDataUtil.importDeviceIntoMongo();

        //通知json保存到mongo
        String jsonStr = JSONUtil.toJsonStr(notificaitonListObject);

        notificationDao.insertNotificationHistory(jsonStr); //todo 吉安

        List<SubscribeNotificationRequestObject.SubscribeNotification> notificationList = notificaitonListObject.getSubscribeNotificationObject();

        for(SubscribeNotificationRequestObject.SubscribeNotification notification:notificationList){
            //处理卡口数据
            TollgateObjectWrapper.TollgateListObject tollgateListObject= notification.getTollgateListObject();
            if(tollgateListObject!=null) processTollgates(tollgateListObject.getTollgateObject());

            //处理设备数据
            DeviceObjectWrapper.ApeListObject apeListObject= notification.getApeListObject();
            if(apeListObject!=null) processDevices(apeListObject.getApeObject());

            //处理机动车数据
           MotorVehicleObjectWrapper.MotorVehicleListObject motorVehicleListObject = notification.getMotorVehicleObjectList();
            if(motorVehicleListObject!=null) processMotorVehicles(motorVehicleListObject.getMotorVehicleObject());
        }
        logger.info("end to process notifications");
    }

    /**
     * 处理机动车列表数据
     * @param vehicleListList
     */
    public void processMotorVehicles( List<MotorVehicleObjectWrapper.MotorVehicle> vehicleListList)
    {
        logger.info("begin to process vehicles");
        //处理机动车数据
        for(MotorVehicleObjectWrapper.MotorVehicle motorVehicle:vehicleListList){
            //保存机动车数据
            //todo testcode
            notificationDao.saveNotificationMotorVehicle(motorVehicle);
            //logger.info("success save vehicle in mongo");
            //保存机动车图片
            this.saveVehicleImage(motorVehicle); //tod 吉安
            logger.info("success save vehicle on  disk");
            logger.info("platNo："+motorVehicle.getPlateNo());
        }
        logger.info("end to process vehicles");
    }

    /**
     * 处理机动车列表数据
     * @param tollgateList
     */
    public void processTollgates( List<TollgateObjectWrapper.Tollgate> tollgateList)
    {
        logger.info("begin save tollgate list in csv");
        simulatorDataUtil.saveTollgateList(tollgateList);
        logger.info("success save tollgate list in csv");
    }

    /**
     * 处理机动车列表数据
     * @param deviceList
     */
    public void processDevices( List<DeviceObjectWrapper.Ape> deviceList)
    {
        logger.info("begin save device list in csv");
        simulatorDataUtil.saveDeviceList(deviceList);
        logger.info("begin save device list in csv");
    }

    /**
     * 按照文件名规则保存车辆图片
     * @param motorVehicle
     */
    private void saveVehicleImage(MotorVehicleObjectWrapper.MotorVehicle motorVehicle){
        if(motorVehicle.getStorageUrl1()==null || "".equals(motorVehicle.getStorageUrl1())){
            logger.error("图片路径为空");
        }
        checkImageUrl(motorVehicle.getStorageUrl1());
        String imageFullName = genImageFileName(motorVehicle);
        String imageSourceUrl = motorVehicle.getStorageUrl1();
        saveVehicleImageOnDisk(imageFullName,imageSourceUrl); //todo 吉安
    }

    private void saveVehicleImageOnDisk(String fileName, String remoteUrl)
    {
        logger.info("begin copy remote image file");
        logger.info("fileName:"+fileName);
        logger.info("remoteUrl:"+remoteUrl);

        File file =new File(fileName);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                throw new ServiceException("创建图片文件失败");
            }
        }

        int bytesRead = 0;

        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            URL url = new URL(remoteUrl);

            URLConnection conn = url.openConnection();
            inputStream = conn.getInputStream();
            outputStream = new FileOutputStream(fileName);

            byte[] buffer = new byte[1204];
            int length;
            while ((bytesRead = inputStream.read(buffer)) != -1) {

                outputStream.write(buffer, 0, bytesRead);
            }
            logger.info("success copy remote image file");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (inputStream != null) inputStream.close();
                if (outputStream != null) outputStream.close();
            } catch (Exception ex) {
                logger.error("error when close stream", ex);
            }
        }
    }

    private String genImageFileName(MotorVehicleObjectWrapper.MotorVehicle motorVehicle)
    {
        try {
            logger.info("begin generate image filename");

            String imageUrl = motorVehicle.getStorageUrl1();
            String fileExt = ".jpg";
       /* int index = imageUrl.lastIndexOf(".");
        if(index!=-1)
            fileExt = imageUrl.substring(index);*/

            String deviceId = motorVehicle.getDeviceID();
            DeviceObjectWrapper.Ape device = notificationDao.getDevice(deviceId);
            //String deviceName = device.getName();
            String deviceName = "摄像头" + deviceId; //todo testcode
            String ip = device.getIpAddr();
            String tollgateId = simulatorDataUtil.transferTollgateId(motorVehicle.getTollgateId());
            String directionId = simulatorDataUtil.transderDirectionId(motorVehicle.getDirection());
            String plateColor = simulatorDataUtil.getColorValue(motorVehicle.getPlateColor());
            String vehicleColor = simulatorDataUtil.getColorValue(motorVehicle.getVehicleColor());
            String point = "X" + motorVehicle.getLeftTopX() + "Y" + motorVehicle.getLeftTopY() + "W" + (motorVehicle.getRightBtmX() - motorVehicle.getLeftTopX()) + "H" + (motorVehicle.getRightBtmY() - motorVehicle.getLeftTopY());
            //String laneNo = "02"; //todo testcode
            String laneNo = genStr(motorVehicle.getLaneNo(),2);
            String speed = "000";//todo testcode
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

            String vehicleType = simulatorDataUtil.getVehicleTypeValue(String.valueOf(motorVehicle.getVehicleClass()));

            String userDefineField = "106.573035,24.343741#1";
            String imageFileName = deviceName + "_" + deviceId + "_" + ip + "_" + tollgateId + "_" + motorVehicle.getPlateNo() + "_"
                    + plateColor + "_" + laneNo + "_" + speed + "_" + directionId + "_" + vehicleColor + "_" + point + "_" + motorVehicle.getPassTime()
                    + "_" + userDefineField + "_" + vehicleType;

            imageFileName = getImageDirectory() + imageFileName + fileExt;

            logger.info("success generate image filename:" + imageFileName);
            return imageFileName;
        }catch(Exception e){
            logger.error("generate image filename error");
            throw e;
        }
    }

    private String getImageDirectory() throws ServiceException{
        //String imageDir = IMAGE_ROOT_DIR;
        String imageDir = imageBaseDir;
        File fileDir = new File(imageDir);
        if(!fileDir.exists()) {
            try {
                fileDir.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException("创建图片文件夹失败");
            }
        }
        return  imageDir;
    }

    /**
     * 检查图片url是否可以访问
     * @param url
     * @return
     */
    public static String checkImageUrl(String url) {
        String result = "";
        BufferedReader in = null;
        try {

            String urlNameString = url + "?";
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection)realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            /*for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }*/
            int retCode = connection.getResponseCode();
            if(retCode!=200) throw new ServiceException("url访问失败");
        } catch (Exception e) {
           logger.error("url访问异常" + e);
        }finally {
            try {
                if (in != null)  in.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public String genStr(int intValue,int length){
        String strValue = Integer.toString(intValue);
        if(length<strValue.length())
            for(int i=0;i<length-strValue.length();i++)
                strValue = "0"+strValue;
        return strValue;
    }

}
