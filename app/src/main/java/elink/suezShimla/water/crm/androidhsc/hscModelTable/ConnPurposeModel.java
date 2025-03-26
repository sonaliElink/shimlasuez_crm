package elink.suezShimla.water.crm.androidhsc.hscModelTable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class ConnPurposeModel extends RealmObject {

    @SerializedName("MCT_ID")
    private String MCT_ID;

    @SerializedName("MCT_CONNTYPE_NAME")
    private String MCT_CONNTYPE_NAME;

    public ConnPurposeModel() {
    }
   public ConnPurposeModel(String MCT_ID, String MCT_CONNTYPE_NAME) {
        this.MCT_ID = MCT_ID;
        this.MCT_CONNTYPE_NAME = MCT_CONNTYPE_NAME;
    }

    public String getMCT_ID() {
        return MCT_ID;
    }

    public void setMCT_ID(String MCT_ID) {
        this.MCT_ID = MCT_ID;
    }

    public String getMCT_CONNTYPE_NAME() {
        return MCT_CONNTYPE_NAME;
    }

    public void setMCT_CONNTYPE_NAME(String MCT_CONNTYPE_NAME) {
        this.MCT_CONNTYPE_NAME = MCT_CONNTYPE_NAME;
    }

    @Override
    public String toString() {
        return "ConnPurposeModel{" +
                "MCT_ID='" + MCT_ID + '\'' +
                ", MCT_CONNTYPE_NAME='" + MCT_CONNTYPE_NAME + '\'' +
                '}';
    }
}
