package elink.suezShimla.water.crm.androidhsc.hscModelTable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class AreaModel extends RealmObject {
    @SerializedName("AREAID")
    private String AREAID;

    @SerializedName("NAME")
    private String NAME;

    public AreaModel() {
    }

    public AreaModel(String AREAID, String NAME) {
        this.AREAID = AREAID;
        this.NAME = NAME;
    }

    public String getAREAID() {
        return AREAID;
    }

    public void setAREAID(String AREAID) {
        this.AREAID = AREAID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    @Override
    public String toString() {
        return "AreaModel{" +
                "AREAID='" + AREAID + '\'' +
                ", NAME=" + NAME +
                '}';
    }
}
