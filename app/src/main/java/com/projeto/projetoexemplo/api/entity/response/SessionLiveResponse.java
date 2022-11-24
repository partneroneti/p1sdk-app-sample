package com.projeto.projetoexemplo.api.entity.response;

import com.google.gson.annotations.SerializedName;

public class SessionLiveResponse {

    private Integer timeProcess;
    private Boolean success;
    private String message;
    private String requestId;
    @SerializedName("objectReturn")
    private SessionLive objectReturn;

    public Integer getTimeProcess() {
        return timeProcess;
    }

    public void setTimeProcess(Integer timeProcess) {
        this.timeProcess = timeProcess;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public SessionLive getObjectReturn() {
        return objectReturn;
    }

    public void setObjectReturn(SessionLive objectReturn) {
        this.objectReturn = objectReturn;
    }
}
