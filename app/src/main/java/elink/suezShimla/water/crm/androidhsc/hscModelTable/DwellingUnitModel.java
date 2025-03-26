package elink.suezShimla.water.crm.androidhsc.hscModelTable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class DwellingUnitModel extends RealmObject{

    @SerializedName("ID")
    private String ID;

    @SerializedName("TEXT")
    private String TEXT;

    public DwellingUnitModel(String ID, String TEXT) {
        this.ID = ID;
        this.TEXT = TEXT;
    }

    public DwellingUnitModel() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTEXT() {
        return TEXT;
    }

    public void setTEXT(String TEXT) {
        this.TEXT = TEXT;
    }

    @Override
    public String toString() {
        return "DwellingUnitModel{" +
                "ID='" + ID + '\'' +
                ", TEXT=" + TEXT +
                '}';
    }
}
