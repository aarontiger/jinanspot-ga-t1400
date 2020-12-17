package com.bingoyes.gat1400.serverpoint.entiy;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
public class DeviceObjectWrapper {

    private ApeListObject apeListObject;

    public ApeListObject getApeListObject() {
        return apeListObject;
    }

    public void setApeListObject(ApeListObject apeListObject) {
        this.apeListObject = apeListObject;
    }

    @Data
    public static class ApeListObject {
        private List<Ape> ApeObject;

        public List<Ape> getApeObject() {
            return ApeObject;
        }

        public void setApeObject(List<Ape> apeObject) {
            ApeObject = apeObject;
        }
    }

    @Data
    @Document(collection = "device")
    public static class Ape {
        private String apeId;
        private String name;
        private String model;
        private String ipAddr;
        private String IPV6Addr;
        private String Port;
        //经度
        private String Longtitude;
        //维度
        private String Latitude;
        //安装地点行政区划代码
        private String PlaceCode;
        //位置名
        private String Place;
        //管辖单位代码
        private String OrgCode;
        //抓拍方向
        private String CapDirection;
        //监视方向
        private String MonitorDirection;
        //监视区域说明
        private String MonitorAreaDesc;
        //是否在线
        private String IsOnline;
        //所属采集系统
        private String OwnerApsID;
        //
        private String UserId;
        //
        private String Password;

        public String getApeId() {
            return apeId;
        }

        public void setApeId(String apeId) {
            this.apeId = apeId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getIpAddr() {
            return ipAddr;
        }

        public void setIpAddr(String ipAddr) {
            this.ipAddr = ipAddr;
        }

        public String getIPV6Addr() {
            return IPV6Addr;
        }

        public void setIPV6Addr(String IPV6Addr) {
            this.IPV6Addr = IPV6Addr;
        }

        public String getPort() {
            return Port;
        }

        public void setPort(String port) {
            Port = port;
        }

        public String getLongtitude() {
            return Longtitude;
        }

        public void setLongtitude(String longtitude) {
            Longtitude = longtitude;
        }

        public String getLatitude() {
            return Latitude;
        }

        public void setLatitude(String latitude) {
            Latitude = latitude;
        }

        public String getPlaceCode() {
            return PlaceCode;
        }

        public void setPlaceCode(String placeCode) {
            PlaceCode = placeCode;
        }

        public String getPlace() {
            return Place;
        }

        public void setPlace(String place) {
            Place = place;
        }

        public String getOrgCode() {
            return OrgCode;
        }

        public void setOrgCode(String orgCode) {
            OrgCode = orgCode;
        }

        public String getCapDirection() {
            return CapDirection;
        }

        public void setCapDirection(String capDirection) {
            CapDirection = capDirection;
        }

        public String getMonitorDirection() {
            return MonitorDirection;
        }

        public void setMonitorDirection(String monitorDirection) {
            MonitorDirection = monitorDirection;
        }

        public String getMonitorAreaDesc() {
            return MonitorAreaDesc;
        }

        public void setMonitorAreaDesc(String monitorAreaDesc) {
            MonitorAreaDesc = monitorAreaDesc;
        }

        public String getIsOnline() {
            return IsOnline;
        }

        public void setIsOnline(String isOnline) {
            IsOnline = isOnline;
        }

        public String getOwnerApsID() {
            return OwnerApsID;
        }

        public void setOwnerApsID(String ownerApsID) {
            OwnerApsID = ownerApsID;
        }

        public String getUserId() {
            return UserId;
        }

        public void setUserId(String userId) {
            UserId = userId;
        }

        public String getPassword() {
            return Password;
        }

        public void setPassword(String password) {
            Password = password;
        }
    }
}
