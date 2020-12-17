package com.bingoyes.gat1400.apicaller.bean;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 订阅相关对象
 */
@Data
public class SubscribeListRequestObject {

    private SubscribeListObject SubscribeListObject;

    public SubscribeListRequestObject.SubscribeListObject getSubscribeListObject() {
        return SubscribeListObject;
    }

    public void setSubscribeListObject(SubscribeListRequestObject.SubscribeListObject subscribeListObject) {
        SubscribeListObject = subscribeListObject;
    }

    @Data
    public static class SubscribeListObject {
        private List<Subscribe> SubscribeObject;

        public List<Subscribe> getSubscribeObject() {
            return SubscribeObject;
        }

        public void setSubscribeObject(List<Subscribe> subscribeObject) {
            SubscribeObject = subscribeObject;
        }
    }
    @Data
    public static class Subscribe {

        private String SubscribeID;
        private String Title;
        private String SubscribeDetail;
        private String ResourceURI;
        //申请人
        private String ApplicantName;
        //申请单位
        private String ApplicantOrg;

        private String BeginTime;

        private String EndTime;
        private String ReceiveAddr;

        //0：订阅；1：取消订阅
        private int OperateType;

        private int SubscribeStatus;

        //信息上报间隔时间
        private int ReportInterval;

        //订阅理由
        private String Reason;

        //0：订阅中1：已取消订阅2：订阅到期9：未订阅该字段只读
        /*,
        SubscribeCancelOrg: '',
        SubscribeCancelPerson: '',
        CancelTime: '',
        CancelReason: '',
       */

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

        public String getSubscribeDetail() {
            return SubscribeDetail;
        }

        public void setSubscribeDetail(String subscribeDetail) {
            SubscribeDetail = subscribeDetail;
        }

        public String getResourceURI() {
            return ResourceURI;
        }

        public void setResourceURI(String resourceURI) {
            ResourceURI = resourceURI;
        }

        public String getApplicantName() {
            return ApplicantName;
        }

        public void setApplicantName(String applicantName) {
            ApplicantName = applicantName;
        }

        public String getApplicantOrg() {
            return ApplicantOrg;
        }

        public void setApplicantOrg(String applicantOrg) {
            ApplicantOrg = applicantOrg;
        }

        public String getBeginTime() {
            return BeginTime;
        }

        public void setBeginTime(String beginTime) {
            BeginTime = beginTime;
        }

        public String getEndTime() {
            return EndTime;
        }

        public void setEndTime(String endTime) {
            EndTime = endTime;
        }

        public String getReceiveAddr() {
            return ReceiveAddr;
        }

        public void setReceiveAddr(String receiveAddr) {
            ReceiveAddr = receiveAddr;
        }

        public int getOperateType() {
            return OperateType;
        }

        public void setOperateType(int operateType) {
            OperateType = operateType;
        }

        public int getSubscribeStatus() {
            return SubscribeStatus;
        }

        public void setSubscribeStatus(int subscribeStatus) {
            SubscribeStatus = subscribeStatus;
        }

        public int getReportInterval() {
            return ReportInterval;
        }

        public void setReportInterval(int reportInterval) {
            ReportInterval = reportInterval;
        }

        public String getReason() {
            return Reason;
        }

        public void setReason(String reason) {
            Reason = reason;
        }
    }
}
