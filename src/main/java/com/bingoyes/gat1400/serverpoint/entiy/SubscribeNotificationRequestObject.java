package com.bingoyes.gat1400.serverpoint.entiy;

import com.bingoyes.gat1400.apicaller.bean.MotorVehicleObjectWrapper;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SubscribeNotificationRequestObject {

    private SubscribeNotificationListObject SubscribeNotificationListObject;

    public SubscribeNotificationRequestObject.SubscribeNotificationListObject getSubscribeNotificationListObject() {
        return SubscribeNotificationListObject;
    }

    public void setSubscribeNotificationListObject(SubscribeNotificationRequestObject.SubscribeNotificationListObject subscribeNotificationListObject) {
        SubscribeNotificationListObject = subscribeNotificationListObject;
    }

    @Data
    public static class SubscribeNotificationListObject {
        private List<SubscribeNotification> SubscribeNotificationObject;

        public List<SubscribeNotification> getSubscribeNotificationObject() {
            return SubscribeNotificationObject;
        }

        public void setSubscribeNotificationObject(List<SubscribeNotification> subscribeNotificationObject) {
            SubscribeNotificationObject = subscribeNotificationObject;
        }
    }

    @Data
    public static class SubscribeNotification {
        private String NotificationID;
        private String SubscribeID;
        private String Title;

        //触发时间
        private Date TriggerTime;
        //订阅通知的详细信息(人员、 人脸、 机动车、非机动车 、物 品 、场景)标识集合
        private String InfoIDs;

        private MotorVehicleObjectWrapper.MotorVehicleListObject MotorVehicleObjectList;

        private TollgateObjectWrapper.TollgateListObject tollgateListObject;
        private DeviceObjectWrapper.ApeListObject apeListObject;

        public String getNotificationID() {
            return NotificationID;
        }

        public void setNotificationID(String notificationID) {
            NotificationID = notificationID;
        }

        public String getSubscribeID() {
            return SubscribeID;
        }

        public void setSubscribeID(String subscribeID) {
            SubscribeID = subscribeID;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public Date getTriggerTime() {
            return TriggerTime;
        }

        public void setTriggerTime(Date triggerTime) {
            TriggerTime = triggerTime;
        }

        public String getInfoIDs() {
            return InfoIDs;
        }

        public void setInfoIDs(String infoIDs) {
            InfoIDs = infoIDs;
        }

        public MotorVehicleObjectWrapper.MotorVehicleListObject getMotorVehicleObjectList() {
            return MotorVehicleObjectList;
        }

        public void setMotorVehicleObjectList(MotorVehicleObjectWrapper.MotorVehicleListObject motorVehicleObjectList) {
            MotorVehicleObjectList = motorVehicleObjectList;
        }

        public TollgateObjectWrapper.TollgateListObject getTollgateListObject() {
            return tollgateListObject;
        }

        public void setTollgateListObject(TollgateObjectWrapper.TollgateListObject tollgateListObject) {
            this.tollgateListObject = tollgateListObject;
        }

        public DeviceObjectWrapper.ApeListObject getApeListObject() {
            return apeListObject;
        }

        public void setApeListObject(DeviceObjectWrapper.ApeListObject apeListObject) {
            this.apeListObject = apeListObject;
        }
    }
}
