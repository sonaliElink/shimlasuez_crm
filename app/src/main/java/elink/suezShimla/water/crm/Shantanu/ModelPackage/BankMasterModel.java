package elink.suezShimla.water.crm.Shantanu.ModelPackage;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class BankMasterModel extends RealmObject {

    @SerializedName("ID")
    private String ID;

    @SerializedName("NAME")
    private String NAME;

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
        return "BankMasterModel{" +
                "ID='" + ID + '\'' +
                ", NAME=" + NAME +
                '}';
    }
}
