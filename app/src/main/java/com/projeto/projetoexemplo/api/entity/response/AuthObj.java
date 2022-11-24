package com.projeto.projetoexemplo.api.entity.response;

public class AuthObj {


    private Integer timeProcess;
    private Boolean success;
    private String message;
    private AuthenticationResponse objectReturn;

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

    public AuthenticationResponse getObjectReturn() {
        return objectReturn;
    }

    public void setObjectReturn(AuthenticationResponse objectReturn) {
        this.objectReturn = objectReturn;
    }
}
