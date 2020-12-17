package com.bingoyes.gat1400.serverpoint.entiy;

import lombok.Data;

import java.util.List;

@Data
public class LaneObjectWrapper {

    private LaneListObject laneListObject;

    public LaneListObject getLaneListObject() {
        return laneListObject;
    }

    public void setLaneListObject(LaneListObject laneListObject) {
        this.laneListObject = laneListObject;
    }

    @Data
    public class LaneListObject {
        private List<Lane> LaneObject;

        public List<Lane> getLaneObject() {
            return LaneObject;
        }

        public void setLaneObject(List<Lane> laneObject) {
            LaneObject = laneObject;
        }
    }

    @Data
    public class Lane {
        //卡口编号
        private String tollgateID;
        //卡口内车道唯一编号，从1开始
        private int LaneId;
        //车辆行驶方向最左车道为1，由左向右顺序编号。与方向有关
        private int LaneNo;
        //
        private String Name;
        //车道方向
        private String Direction;
        //车道描述
        private String Desc;
        //限速
        private int MaxSpeed;
        //车道出入城：1 进城、2出城、3非进出城、4 进出城混合
        private int CityPass;
        //车道上对应的采集处理设备ID
        private String ApeID;

        public String getTollgateID() {
            return tollgateID;
        }

        public void setTollgateID(String tollgateID) {
            this.tollgateID = tollgateID;
        }

        public int getLaneId() {
            return LaneId;
        }

        public void setLaneId(int laneId) {
            LaneId = laneId;
        }

        public int getLaneNo() {
            return LaneNo;
        }

        public void setLaneNo(int laneNo) {
            LaneNo = laneNo;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getDirection() {
            return Direction;
        }

        public void setDirection(String direction) {
            Direction = direction;
        }

        public String getDesc() {
            return Desc;
        }

        public void setDesc(String desc) {
            Desc = desc;
        }

        public int getMaxSpeed() {
            return MaxSpeed;
        }

        public void setMaxSpeed(int maxSpeed) {
            MaxSpeed = maxSpeed;
        }

        public int getCityPass() {
            return CityPass;
        }

        public void setCityPass(int cityPass) {
            CityPass = cityPass;
        }

        public String getApeID() {
            return ApeID;
        }

        public void setApeID(String apeID) {
            ApeID = apeID;
        }
    }
}
