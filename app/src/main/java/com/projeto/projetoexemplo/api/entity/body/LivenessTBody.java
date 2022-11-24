package com.projeto.projetoexemplo.api.entity.body;

public class LivenessTBody {

    private String transactionId;
    private String faceScan;
    private String auditTrailImage;
    private String lowQualityAuditTrailImage;
    private String sessionId;
    private String deviceKey;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }



    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getFaceScan() {
        return faceScan;
    }

    public void setFaceScan(String faceScan) {
        this.faceScan = faceScan;
    }

    public String getAuditTrailImage() {
        return auditTrailImage;
    }

    public void setAuditTrailImage(String auditTrailImage) {
        this.auditTrailImage = auditTrailImage;
    }

    public String getLowQualityAuditTrailImage() {
        return lowQualityAuditTrailImage;
    }

    public void setLowQualityAuditTrailImage(String lowQualityAuditTrailImage) {
        this.lowQualityAuditTrailImage = lowQualityAuditTrailImage;
    }


    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }
}
