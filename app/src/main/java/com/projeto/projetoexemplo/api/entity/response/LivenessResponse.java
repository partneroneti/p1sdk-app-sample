package com.projeto.projetoexemplo.api.entity.response;

public class LivenessResponse {

    private Integer timeProcess;
    private Boolean success;
    private String message;
    private String requestId;
    private LivenessStatusResponse objectReturn;

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

    public LivenessStatusResponse getObjectReturn() {
        return objectReturn;
    }

    public void setObjectReturn(LivenessStatusResponse objectReturn) {
        this.objectReturn = objectReturn;
    }

}
