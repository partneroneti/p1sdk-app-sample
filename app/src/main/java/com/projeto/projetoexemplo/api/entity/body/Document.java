package com.projeto.projetoexemplo.api.entity.body;

import com.google.gson.annotations.SerializedName;

public class Document {

    private String type;

    @SerializedName("byte")
    private String _byte;

    public String getByte() {
        return _byte;
    }

    public void setByte(String _byte) {
        this._byte = _byte;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
