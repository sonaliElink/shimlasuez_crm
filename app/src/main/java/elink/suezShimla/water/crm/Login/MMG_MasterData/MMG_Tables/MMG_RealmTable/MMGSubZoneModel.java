package elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class MMGSubZoneModel extends RealmObject {

    @SerializedName("PCM_PC_ID")
    private int PCM_PC_ID;

    @SerializedName("PCM_PC_NAME")
    private String PCM_PC_NAME;

    @SerializedName("PCM_BU_ID")
    private int PCM_BU_ID;

    public MMGSubZoneModel() {
    }

    public MMGSubZoneModel(int PCM_PC_ID, String PCM_PC_NAME, int PCM_BU_ID) {
        this.PCM_PC_ID = PCM_PC_ID;
        this.PCM_PC_NAME = PCM_PC_NAME;
        this.PCM_BU_ID = PCM_BU_ID;
    }

    public int getPCM_PC_ID() {
        return PCM_PC_ID;
    }

    public String getPCM_PC_NAME() {
        return PCM_PC_NAME;
    }

    public int getPCM_BU_ID() {
        return PCM_BU_ID;
    }

    @Override
    public String toString() {
        return "MMGSubZoneModel{" +
                "PCM_PC_ID=" + PCM_PC_ID +
                ", PCM_PC_NAME='" + PCM_PC_NAME + '\'' +
                ", PCM_BU_ID=" + PCM_BU_ID +
                '}';
    }
}
