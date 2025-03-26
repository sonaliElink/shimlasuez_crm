package elink.suezShimla.water.crm.androidhsc.hscModelTable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class RoadTypeModel extends RealmObject {


    @SerializedName("ID")
    private String ID;

    @SerializedName("REM_REASONNM")
    private String REM_REASONNM;

    public RoadTypeModel() {
    }

    public RoadTypeModel(String ID, String REM_REASONNM) {
        this.ID = ID;
        this.REM_REASONNM = REM_REASONNM;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getREM_REASONNM() {
        return REM_REASONNM;
    }

    public void setREM_REASONNM(String REM_REASONNM) {
        this.REM_REASONNM = REM_REASONNM;
    }

    @Override
    public String toString() {
        return "RoadTypeModel{" +
                "ID='" + ID + '\'' +
                ", REM_REASONNM=" + REM_REASONNM +
                '}';
    }
}
