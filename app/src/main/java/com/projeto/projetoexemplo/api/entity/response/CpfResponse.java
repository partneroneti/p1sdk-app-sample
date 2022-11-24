package com.projeto.projetoexemplo.api.entity.response;

public class CpfResponse {

    private String transactionId;
    private String certificate;
    private String deviceKeyIdentifier;
    private String productionKeyText;

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getDeviceKeyIdentifier() {
        return deviceKeyIdentifier;
    }

    public void setDeviceKeyIdentifier(String deviceKeyIdentifier) {
        this.deviceKeyIdentifier = deviceKeyIdentifier;
    }

    public String getProductionKeyText() {
        return productionKeyText;
    }

    public void setProductionKeyText(String productionKeyText) {
        this.productionKeyText = productionKeyText;
    }



    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }


}
