package elink.suezShimla.water.crm.Shantanu.ModelPackage;


import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class ConnectionStatusModel extends RealmObject {
    @SerializedName("DTM_DISCONN_TAG_ID")
    private String DTM_DISCONN_TAG_ID;

    @SerializedName("DTM_DISCONN_TAG_NAME")
    private String DTM_DISCONN_TAG_NAME;

    public String getDTM_DISCONN_TAG_ID() {
        return DTM_DISCONN_TAG_ID;
    }

    public void setDTM_DISCONN_TAG_ID(String DTM_DISCONN_TAG_ID) {
        this.DTM_DISCONN_TAG_ID = DTM_DISCONN_TAG_ID;
    }

    public String getDTM_DISCONN_TAG_NAME() {
        return DTM_DISCONN_TAG_NAME;
    }

    public void setDTM_DISCONN_TAG_NAME(String DTM_DISCONN_TAG_NAME) {
        this.DTM_DISCONN_TAG_NAME = DTM_DISCONN_TAG_NAME;
    }

    @Override
    public String toString() {
        return "ConecctionStatusModel{" +
                "DTM_DISCONN_TAG_ID='" + DTM_DISCONN_TAG_ID + '\'' +
                ", DTM_DISCONN_TAG_NAME=" + DTM_DISCONN_TAG_NAME +
                '}';
    }
}
