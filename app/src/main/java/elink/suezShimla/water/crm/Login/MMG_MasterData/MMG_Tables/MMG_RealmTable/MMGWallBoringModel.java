package elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class MMGWallBoringModel extends RealmObject {
    @SerializedName("WB_ID")
    private int WB_ID;

    @SerializedName("WB_DESC")
    private String WB_DESC ;

    public MMGWallBoringModel() {
    }

    public MMGWallBoringModel(int WB_ID, String WB_DESC ) {
        this.WB_ID = WB_ID;
        this.WB_DESC  = WB_DESC ;
    }

    public int getWB_ID() {
        return WB_ID;
    }

    public String getWB_DESC() {
        return WB_DESC ;
    }

    @Override
    public String toString() {
        return "MMGWallBoringModel{" +
                "WB_ID='" + WB_ID + '\'' +
                ", WB_DESC=" + WB_DESC +
                '}';
    }
}
