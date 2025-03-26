package elink.suezShimla.water.crm.Login.MasterData.Tables;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class ZoneModel extends RealmObject {

    @SerializedName("BU_NAME")
    private String BU_NAME;

    @SerializedName("BUM_BU_ID")
    private int BUM_BU_ID;

    public ZoneModel() {
    }

    public ZoneModel(String BU_NAME, int BUM_BU_ID) {
        this.BU_NAME = BU_NAME;
        this.BUM_BU_ID = BUM_BU_ID;
    }

    public String getBU_NAME() {
        return BU_NAME;
    }

    public int getBUM_BU_ID() {
        return BUM_BU_ID;
    }

    @Override
    public String toString() {
        return "ZoneModel{" +
                "BU_NAME='" + BU_NAME + '\'' +
                ", BUM_BU_ID=" + BUM_BU_ID +
                '}';
    }
}
