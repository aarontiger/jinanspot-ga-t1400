package com.bingoyes.gat1400.apicaller.bean;

import lombok.Data;

@Data
public class ResponseStatusObjectWrapper {

    private ResponseStatusObject ResponseStatusObject;

    public ResponseStatusObjectWrapper.ResponseStatusObject getResponseStatusObject() {
        return ResponseStatusObject;
    }

    public void setResponseStatusObject(ResponseStatusObjectWrapper.ResponseStatusObject responseStatusObject) {
        ResponseStatusObject = responseStatusObject;
    }

    @Data
    public static class ResponseStatusObject {
        private String Id;

        private String LocalTime;

        private String RequestURL;

        private int StatusCode;

        private String StatusString;

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getLocalTime() {
            return LocalTime;
        }

        public void setLocalTime(String localTime) {
            LocalTime = localTime;
        }

        public String getRequestURL() {
            return RequestURL;
        }

        public void setRequestURL(String requestURL) {
            RequestURL = requestURL;
        }

        public int getStatusCode() {
            return StatusCode;
        }

        public void setStatusCode(int statusCode) {
            StatusCode = statusCode;
        }

        public String getStatusString() {
            return StatusString;
        }

        public void setStatusString(String statusString) {
            StatusString = statusString;
        }
    }
}
