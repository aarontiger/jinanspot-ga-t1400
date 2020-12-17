package com.bingoyes.gat1400.apicaller.bean;

import lombok.Data;

@Data
public class KeepaliveRequestObject {

    public KeepaliveRequestObject.KeepaliveObject getKeepaliveObject() {
        return KeepaliveObject;
    }

    public void setKeepaliveObject(KeepaliveRequestObject.KeepaliveObject keepaliveObject) {
        KeepaliveObject = keepaliveObject;
    }

    private KeepaliveObject KeepaliveObject;
    @Data
    public static class KeepaliveObject {
        private String DeviceID;

        public String getDeviceID() {
            return DeviceID;
        }

        public void setDeviceID(String deviceID) {
            DeviceID = deviceID;
        }
    }

}
