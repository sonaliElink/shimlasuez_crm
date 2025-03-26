package elink.suezShimla.water.crm.Login.MasterData.Download;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class DownloadConnSize extends RealmObject {

    @SerializedName("ID")
    private String ID;

    @SerializedName("NAME")
    private String NAME;


    public DownloadConnSize(String ID, String NAME) {
        this.ID = ID;
        this.NAME = NAME;
    }
    public DownloadConnSize() {
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
        return "DownloadConnSize{" +
                "ID='" + ID + '\'' +
                ", NAME=" + NAME +
                '}';
    }
}
