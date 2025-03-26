package elink.suezShimla.water.crm.androidhsc.hscModelTable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class SizeModel extends RealmObject {
    @SerializedName("ID")
    private String ID;

    @SerializedName("NAME")
    private String NAME;


    public SizeModel(String ID, String NAME) {
        this.ID = ID;
        this.NAME = NAME;
    }
    public SizeModel() {
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

    @Override
    public String toString() {
        return "SizeModel{" +
                "ID='" + ID + '\'' +
                ", NAME=" + NAME +
                '}';
    }
}
