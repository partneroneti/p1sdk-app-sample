package com.projeto.projetoexemplo.api.entity.body;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DocumentBody {
    @Expose
    private String transactionId;

    @Expose
    @SerializedName("documents")
    private List<com.projeto.photoface.entity.body.Document> documentos = null;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public List<com.projeto.photoface.entity.body.Document> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<com.projeto.photoface.entity.body.Document> documentos) {
        this.documentos = documentos;
    }
}

