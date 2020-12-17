package com.bingoyes.gat1400.apicaller.bean;

import lombok.Data;

@Data
public class RegisterRequestObject {

    private RegisterObject RegisterObject;

    public RegisterRequestObject.RegisterObject getRegisterObject() {
        return RegisterObject;
    }

    public void setRegisterObject(RegisterRequestObject.RegisterObject registerObject) {
        RegisterObject = registerObject;
    }

    @Data
    public static class RegisterObject {
        private String DeviceID;

        public String getDeviceID() {
            return DeviceID;
        }

        public void setDeviceID(String deviceID) {
            DeviceID = deviceID;
        }
    }
}
