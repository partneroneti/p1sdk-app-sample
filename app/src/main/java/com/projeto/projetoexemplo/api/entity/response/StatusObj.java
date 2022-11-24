package com.projeto.projetoexemplo.api.entity.response;

public class StatusObj {

    private Integer timeProcess;
    private Boolean success;
    private String message;
    private StatusResponse objectReturn;

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

    public StatusResponse getObjectReturn() {
        return objectReturn;
    }

    public void setObjectReturn(StatusResponse objectReturn) {
        this.objectReturn = objectReturn;
    }

}
