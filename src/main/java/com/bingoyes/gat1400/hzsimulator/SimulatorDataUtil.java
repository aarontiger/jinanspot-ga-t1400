package com.bingoyes.gat1400.hzsimulator;

import com.bingoyes.gat1400.apicaller.bean.MotorVehicleObjectWrapper;
import com.bingoyes.gat1400.serverpoint.dao.SubscribeNotificationDao;
import com.bingoyes.gat1400.serverpoint.entiy.DeviceObjectWrapper;
import com.bingoyes.gat1400.serverpoint.entiy.SubscribeNotificationRequestObject;
import com.bingoyes.gat1400.serverpoint.entiy.TollgateObjectWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@PropertySource(value = {"file:/opt/gat1400/conf/gat1400.yml"})
public class SimulatorDataUtil {

    private static Logger logger = LoggerFactory.getLogger(SimulatorDataUtil.class);

    @Value("${dataFileDir}")
    private String dataFileDir;

    private Map<String,String> tollgateMap = new HashMap<>();
    private Map<String,String> directoinMap = new HashMap<>();
    private Map<String,String> colorMap = new HashMap<>();
    private Map<String,String> vehicleTypeMap = new HashMap<>();

    private static String DEVICE_LIST_FILE_NAME ="device_list.csv";
    private static String TOLLGATE_LIST_FILE_NAME ="tollgate_list.csv";
    private static String DIRECTION_ID_MAPPING_FILE_NAME ="direction_id_mapping.csv";
    private static String COLOR_LIST_FILE_NAME ="color_list.csv";
    private static String VEHICLE_TYPE_LIST_FILE_NAME ="vehicle_type_list.csv";

    String serverUrl ="http://127.0.0.1:9120";
    String deviceId = "abcd";

    @Resource
    RestTemplate restTemplate;


    @Autowired
    SubscribeNotificationDao notificationDao;

    @PostConstruct
    private void loadMaps(){

        tollgateMap = loadMapFromCsv(TOLLGATE_LIST_FILE_NAME);
        directoinMap = loadMapFromCsv(DIRECTION_ID_MAPPING_FILE_NAME);
        colorMap = loadMapFromCsv(COLOR_LIST_FILE_NAME);
        vehicleTypeMap = loadMapFromCsv(VEHICLE_TYPE_LIST_FILE_NAME);
    }

    private Map<String,String> loadMapFromCsv(String fileName){
        Map<String,String> resultMap = new HashMap<>();

        String fullFileName = dataFileDir+fileName;

        BufferedReader br=null;
        try {
            File file = new File(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(fullFileName), "GBK");
            br = new BufferedReader(inputStreamReader);
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");

                resultMap.put(fields[0],fields[1]);

            }
        }catch (Exception e) {
            logger.error("load csv文件失败："+fileName,e);
        }finally{
            if(br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return resultMap;
    }

    /**
     * 取得车辆模拟数据，从csv文件获取数据
     * @param fileName
     * @return
     */
    public  SubscribeNotificationRequestObject getVehicleSimulateData(String fileName){
        List<MotorVehicleObjectWrapper.MotorVehicle> dataList=new ArrayList<>();

        BufferedReader br=null;
        try {
            File file = new File(dataFileDir+fileName);

            InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "GBK");
            br = new BufferedReader(isr);
            String line = "";

            br.readLine();
            while ((line = br.readLine()) != null) {

                String[] fields = line.split(",");

                MotorVehicleObjectWrapper.MotorVehicle vehicle = new MotorVehicleObjectWrapper.MotorVehicle();

                //vehicle.setDeviceID(fields[1]);
                vehicle.setDeviceID(genDeviceId(8));
                vehicle.setTollgateId(fields[3]);
                vehicle.setPlateNo(fields[4]);
                vehicle.setPlateColor(fields[5]);
                vehicle.setLaneNo(Integer.parseInt(fields[6]));
                vehicle.setSpeed(Integer.parseInt(fields[7]));
                vehicle.setDirection(Integer.parseInt(fields[8]));
                vehicle.setVehicleColor(fields[9]);
                vehicle.setLeftTopX(Integer.parseInt(fields[10]));
                vehicle.setLeftTopY(Integer.parseInt(fields[11]));
                vehicle.setRightBtmX(Integer.parseInt(fields[12]));
                vehicle.setRightBtmY(Integer.parseInt(fields[13]));

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                vehicle.setPassTime(fields[14]);
                vehicle.setVehicleClass(fields[15]);
                vehicle.setStorageUrl1(fields[16]);
                dataList.add(vehicle);
                logger.debug("generated vehicle ,plateNo:"+vehicle.getPlateNo());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(br!=null){
                try {
                    br.close();
                    br=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        MotorVehicleObjectWrapper.MotorVehicleListObject vehicleListObject = new MotorVehicleObjectWrapper.MotorVehicleListObject();
        vehicleListObject.setMotorVehicleObject(dataList);

        SubscribeNotificationRequestObject.SubscribeNotification notification = new SubscribeNotificationRequestObject.SubscribeNotification();

        notification.setMotorVehicleObjectList(vehicleListObject);

        List<SubscribeNotificationRequestObject.SubscribeNotification> notificationList = new ArrayList<>();
        notificationList.add(notification);

        SubscribeNotificationRequestObject.SubscribeNotificationListObject notificaitonListObject = new SubscribeNotificationRequestObject.SubscribeNotificationListObject();

        notificaitonListObject.setSubscribeNotificationObject(notificationList);

        SubscribeNotificationRequestObject subscribeNotificationRequestObject = new SubscribeNotificationRequestObject();
        subscribeNotificationRequestObject.setSubscribeNotificationListObject(notificaitonListObject);
        return subscribeNotificationRequestObject;
    }

    //todo 暂时没用
    public List<DeviceObjectWrapper.Ape> getDeviceTestData(String fileName){
        List<DeviceObjectWrapper.Ape> dataList=new ArrayList<>();

        BufferedReader br=null;
        try {
            File file = new File(dataFileDir+fileName);

            InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "GBK");
            br = new BufferedReader(isr);
            String line = "";

            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");

               DeviceObjectWrapper.Ape device = new DeviceObjectWrapper.Ape();

                device.setApeId(fields[0]);
                device.setName(fields[1]);
                device.setIpAddr(fields[2]);
                dataList.add(device);

               logger.info("get device list,deviceName:"+device.getName());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return dataList;
    }

    /**
     *
     * @param tollgateList
     */
    public void saveTollgateList(List<TollgateObjectWrapper.Tollgate> tollgateList){
        FileOutputStream out=null;
        OutputStreamWriter osw=null;
        BufferedWriter bw=null;
        try {
            File file = new File(DEVICE_LIST_FILE_NAME);
            out = new FileOutputStream(file);
            osw = new OutputStreamWriter(out);
            bw =new BufferedWriter(osw);
            String line ="";
            if(tollgateList!=null && !tollgateList.isEmpty()){
                for(TollgateObjectWrapper.Tollgate tollgate : tollgateList){
                    line = tollgate.getTollgateID()+","+tollgate.getName();
                    bw.append(line).append("\r");
                }
            }
        } catch (Exception e) {
            logger.debug("保存卡口列表出错",e);
        }finally{
            try {
                if (bw != null) bw.close();
                if (osw != null) osw.close();
                if (out != null) out.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Deprecated
    public void saveDeviceList(List<DeviceObjectWrapper.Ape> deviceList){

        FileOutputStream out=null;
        OutputStreamWriter osw=null;
        BufferedWriter bw=null;
        try {
            File file = new File(DEVICE_LIST_FILE_NAME);
            out = new FileOutputStream(file);
            osw = new OutputStreamWriter(out);
            bw =new BufferedWriter(osw);
            String line ="";
            if(deviceList!=null && !deviceList.isEmpty()){
                for(DeviceObjectWrapper.Ape ape : deviceList){
                    line = ape.getIsOnline()+","+ape.getName()+","+ape.getModel()+","+ape.getIpAddr();
                    bw.append(line).append("\r");
                }
            }
        } catch (Exception e) {
           logger.debug("保存设备列表出错",e);
        }finally{
            try {
                if (bw != null) bw.close();
                if (osw != null) osw.close();
                if (out != null) out.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void importDeviceIntoMongo(){
        notificationDao.removeAllDevice();
        List<DeviceObjectWrapper.Ape> deviceList = getDeviceTestData(DEVICE_LIST_FILE_NAME);
        for(DeviceObjectWrapper.Ape ape: deviceList) {
            notificationDao.saveDevice(ape);
        }
    }

    private String genDeviceId(int len){
        Random r = new Random();
        StringBuilder rs = new StringBuilder();
        for (int i = 0; i < len; i++) {
            rs.append(r.nextInt(10));
        }
        return rs.toString();
    }

    public String transferTollgateId(String tollgateId){
        return tollgateMap.get(tollgateId);
    }

    public String transderDirectionId(int directionId){

        return directoinMap.get(String.valueOf(directionId));
    }
    public String getColorValue(String colorId){
        return colorMap.get(colorId);
    }

    public String getVehicleTypeValue(String vehicleType){
        return vehicleTypeMap.get(vehicleType);
    }


}
