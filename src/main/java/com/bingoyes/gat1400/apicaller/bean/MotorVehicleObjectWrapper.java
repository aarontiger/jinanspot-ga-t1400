package com.bingoyes.gat1400.apicaller.bean;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
public class MotorVehicleObjectWrapper {

    private MotorVehicleListObject MotorVehicleListObject;

    public MotorVehicleObjectWrapper.MotorVehicleListObject getMotorVehicleListObject() {
        return MotorVehicleListObject;
    }

    public void setMotorVehicleListObject(MotorVehicleObjectWrapper.MotorVehicleListObject motorVehicleListObject) {
        MotorVehicleListObject = motorVehicleListObject;
    }

    @Data
    public static class MotorVehicleListObject {
        private List<MotorVehicle> MotorVehicleObject;

        public List<MotorVehicle> getMotorVehicleObject() {
            return MotorVehicleObject;
        }

        public void setMotorVehicleObject(List<MotorVehicle> motorVehicleObject) {
            MotorVehicleObject = motorVehicleObject;
        }
    }

    @Data
    @Document(collection = "motor_vehicle")
    public static class MotorVehicle {


        //        1-设备名称

        //        2-设备编号
        private String DeviceID;
        //        3-设备IP
        //        4-卡口编号

        //        5-车牌号
        private String PlateNo;
        //        6-车牌颜色
        private String PlateColor;
        //        7-车道号
        private int LaneNo;
        //        8-车辆速度
        private int Speed;
        //        9-行驶方向
        private int Direction;
        //        10-车身颜色
        private String VehicleColor;
        //      11-中心点经纬度
        //      12-时间(还有出现时间和消失时间）
        private String PassTime;
        //      13-自定义（经度,纬度#人车合一配置）？？？
        //      14-车辆类型
        private String VehicleClass;

        //以下属性待筛选
        private String TollgateId;
        private SubImageList SubImageList;
        private String MotorVehicleID;
        private String StorageUrl1;
        private Integer LeftTopX;
        private Integer LeftTopY;
        private Integer RightBtmX;
        private Integer RightBtmY;
        private Boolean HasPlate;
        private String PlateClass;

        //人工或者自动采集
        private Integer InfoKind;
        //来源图像标识
        private String SourceID;


        public String getDeviceID() {
            return DeviceID;
        }

        public void setDeviceID(String deviceID) {
            DeviceID = deviceID;
        }

        public String getPlateNo() {
            return PlateNo;
        }

        public void setPlateNo(String plateNo) {
            PlateNo = plateNo;
        }

        public String getPlateColor() {
            return PlateColor;
        }

        public void setPlateColor(String plateColor) {
            PlateColor = plateColor;
        }

        public int getLaneNo() {
            return LaneNo;
        }

        public void setLaneNo(int laneNo) {
            LaneNo = laneNo;
        }

        public int getSpeed() {
            return Speed;
        }

        public void setSpeed(int speed) {
            Speed = speed;
        }

        public int getDirection() {
            return Direction;
        }

        public void setDirection(int direction) {
            Direction = direction;
        }

        public String getVehicleColor() {
            return VehicleColor;
        }

        public void setVehicleColor(String vehicleColor) {
            VehicleColor = vehicleColor;
        }

        public String getPassTime() {
            return PassTime;
        }

        public void setPassTime(String passTime) {
            PassTime = passTime;
        }

        public String getVehicleClass() {
            return VehicleClass;
        }

        public void setVehicleClass(String vehicleClass) {
            VehicleClass = vehicleClass;
        }

        public String getTollgateId() {
            return TollgateId;
        }

        public void setTollgateId(String tollgateId) {
            TollgateId = tollgateId;
        }

        public MotorVehicleObjectWrapper.SubImageList getSubImageList() {
            return SubImageList;
        }

        public void setSubImageList(MotorVehicleObjectWrapper.SubImageList subImageList) {
            SubImageList = subImageList;
        }

        public String getMotorVehicleID() {
            return MotorVehicleID;
        }

        public void setMotorVehicleID(String motorVehicleID) {
            MotorVehicleID = motorVehicleID;
        }

        public String getStorageUrl1() {
            return StorageUrl1;
        }

        public void setStorageUrl1(String storageUrl1) {
            StorageUrl1 = storageUrl1;
        }

        public Integer getLeftTopX() {
            return LeftTopX;
        }

        public void setLeftTopX(Integer leftTopX) {
            LeftTopX = leftTopX;
        }

        public Integer getLeftTopY() {
            return LeftTopY;
        }

        public void setLeftTopY(Integer leftTopY) {
            LeftTopY = leftTopY;
        }

        public Integer getRightBtmX() {
            return RightBtmX;
        }

        public void setRightBtmX(Integer rightBtmX) {
            RightBtmX = rightBtmX;
        }

        public Integer getRightBtmY() {
            return RightBtmY;
        }

        public void setRightBtmY(Integer rightBtmY) {
            RightBtmY = rightBtmY;
        }

        public Boolean getHasPlate() {
            return HasPlate;
        }

        public void setHasPlate(Boolean hasPlate) {
            HasPlate = hasPlate;
        }

        public String getPlateClass() {
            return PlateClass;
        }

        public void setPlateClass(String plateClass) {
            PlateClass = plateClass;
        }

        public Integer getInfoKind() {
            return InfoKind;
        }

        public void setInfoKind(Integer infoKind) {
            InfoKind = infoKind;
        }

        public String getSourceID() {
            return SourceID;
        }

        public void setSourceID(String sourceID) {
            SourceID = sourceID;
        }
    }

    @Data
    public static class SubImageList {
        private List<SubImageInfo> SubImageInfoObject;
    }



}
