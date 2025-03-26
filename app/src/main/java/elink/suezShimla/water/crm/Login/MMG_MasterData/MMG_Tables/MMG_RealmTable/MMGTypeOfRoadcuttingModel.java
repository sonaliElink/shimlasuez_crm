package elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class MMGTypeOfRoadcuttingModel extends RealmObject {

    @SerializedName("RC_ID")
    private int RC_ID;

    @SerializedName("RC_DESC")
    private String RC_DESC;

    public MMGTypeOfRoadcuttingModel() {
    }

    public  MMGTypeOfRoadcuttingModel(int RC_ID, String RC_DESC) {
        this.RC_ID = RC_ID;
        this.RC_DESC = RC_DESC;
    }

    public int getRC_ID() {
        return RC_ID;
    }

    public String getRC_DESC() {
        return RC_DESC;
    }

    @Override
    public String toString() {
        return "MMGTypeOfRoadcuttingModel{" +
                "RC_ID='" + RC_ID + '\'' +
                ", RC_DESC=" + RC_DESC +
                '}';
    }
}
