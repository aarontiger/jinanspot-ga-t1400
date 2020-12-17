package com.bingoyes.gat1400.apicaller.bean;

import lombok.Data;

@Data
public class UnRegisterRequestObject {
    private UnRegisterObject UnRegisterObject;

    public UnRegisterRequestObject.UnRegisterObject getUnRegisterObject() {
        return UnRegisterObject;
    }

    public void setUnRegisterObject(UnRegisterRequestObject.UnRegisterObject unRegisterObject) {
        UnRegisterObject = unRegisterObject;
    }

    @Data
    public static class UnRegisterObject {
        private String DeviceID;

        public String getDeviceID() {
            return DeviceID;
        }

        public void setDeviceID(String deviceID) {
            DeviceID = deviceID;
        }
    }
}
