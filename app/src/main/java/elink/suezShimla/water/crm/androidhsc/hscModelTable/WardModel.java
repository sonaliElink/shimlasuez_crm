package elink.suezShimla.water.crm.androidhsc.hscModelTable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class WardModel extends RealmObject {

    @SerializedName("ID")
    private String ID;

    @SerializedName("NAME")
    private String NAME;

    @SerializedName("ZONEID")
    private String ZONEID;

    public WardModel(String ID, String NAME, String ZONEID) {
        this.ID = ID;
        this.NAME = NAME;
        this.ZONEID = ZONEID;
    }

    public WardModel() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getZONEID() {
        return ZONEID;
    }

    public void setZONEID(String ZONEID) {
        this.ZONEID = ZONEID;
    }

    @Override
    public String toString() {
        return "WardModel{" +
                "ID='" + ID + '\'' +
                ", NAME=" + NAME +
                ", ZONEID=" + ZONEID +
                '}';
    }
}
