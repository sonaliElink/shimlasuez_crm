package elink.suezShimla.water.crm.Login.MasterData.Download;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class DownloadConnCategory extends RealmObject {

    @SerializedName("CATEGORY_ID")
    public String CATEGORY_ID;

    @SerializedName("CATEGORY_NAME")
    public String CATEGORY_NAME;


    public DownloadConnCategory() {
    }

    public DownloadConnCategory(String CATEGORY_ID, String CATEGORY_NAME) {
        this.CATEGORY_ID = CATEGORY_ID;
        this.CATEGORY_NAME = CATEGORY_NAME;
    }

    public String getCATEGORY_ID() {
        return CATEGORY_ID;
    }

    public void setCATEGORY_ID(String CATEGORY_ID) {
        this.CATEGORY_ID = CATEGORY_ID;
    }

    public String getCATEGORY_NAME() {
        return CATEGORY_NAME;
    }

    public void setCATEGORY_NAME(String CATEGORY_NAME) {
        this.CATEGORY_NAME = CATEGORY_NAME;
    }

    @Override
    public String toString() {
        return "DownloadConnCategory{" +
                "CATEGORY_ID='" + CATEGORY_ID + '\'' +
                ", CATEGORY_NAME='" + CATEGORY_NAME + '\'' +
                '}';
    }
}
