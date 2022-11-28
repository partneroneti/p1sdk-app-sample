package com.projeto.projetoexemplo.api.entity.response.obj;

public class FacetecCredentialsObj {
    private String certificate;
    private String deviceKeyIdentifier;
    private String productionKeyText;


    // Getter Methods

    public String getCertificate() {
        return certificate;
    }

    public String getDeviceKeyIdentifier() {
        return deviceKeyIdentifier;
    }

    public String getProductionKeyText() {
        return productionKeyText;
    }

    // Setter Methods

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public void setDeviceKeyIdentifier(String deviceKeyIdentifier) {
        this.deviceKeyIdentifier = deviceKeyIdentifier;
    }

    public void setProductionKeyText(String productionKeyText) {
        this.productionKeyText = productionKeyText;
    }
}
