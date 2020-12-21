package com.bingoyes.gat1400.serverpoint.entiy;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TollgateObjectWrapper {
    private TollgateListObject tollgateListObject;

    public TollgateListObject getTollgateListObject() {
        return tollgateListObject;
    }

    public void setTollgateListObject(TollgateListObject tollgateListObject) {
        this.tollgateListObject = tollgateListObject;
    }

    @Data
    public class TollgateListObject {
        private List<Tollgate> TollgateObject;

        public List<Tollgate> getTollgateObject() {
            return TollgateObject;
        }

        public void setTollgateObject(List<Tollgate> tollgateObject) {
            TollgateObject = tollgateObject;
        }
    }

    @Data
    public static class Tollgate {
        private String TollgateID;
        private String Name;
        //经度
        private String Longtitude;
        //维度
        private String Latitude;
        //安装地点行政区划代码
        private String PlaceCode;
        //位置名
        private String Place;
        //卡口状态 1正常， 2停用， 9其他
        private String Status;
        //卡口类型
        private String TollgateCat;
        //卡口用途0治安卡口，81交通卡口，82其他
        private int TollgateUsage;
        //卡口车道数
        private int LanmeNum;


        //管辖单位代码
        private String OrgCode;
        //卡口启用时间
        private Date AtiveTime;
        private String domainGroupCode;

        public String getTollgateID() {
            return TollgateID;
        }

        public void setTollgateID(String tollgateID) {
            TollgateID = tollgateID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
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

        public String getStatus() {
            return Status;
        }

        public void setStatus(String status) {
            Status = status;
        }

        public String getTollgateCat() {
            return TollgateCat;
        }

        public void setTollgateCat(String tollgateCat) {
            TollgateCat = tollgateCat;
        }

        public int getTollgateUsage() {
            return TollgateUsage;
        }

        public void setTollgateUsage(int tollgateUsage) {
            TollgateUsage = tollgateUsage;
        }

        public int getLanmeNum() {
            return LanmeNum;
        }

        public void setLanmeNum(int lanmeNum) {
            LanmeNum = lanmeNum;
        }

        public String getOrgCode() {
            return OrgCode;
        }

        public void setOrgCode(String orgCode) {
            OrgCode = orgCode;
        }

        public Date getAtiveTime() {
            return AtiveTime;
        }

        public void setAtiveTime(Date ativeTime) {
            AtiveTime = ativeTime;
        }

        public void setDomainGroupCode(String domainGroupCode) {
            this.domainGroupCode = domainGroupCode;
        }

        public String getDomainGroupCode() {
            return domainGroupCode;
        }
    }
}
