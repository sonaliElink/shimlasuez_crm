package elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class MSRModel extends RealmObject {

    @SerializedName("SBM_ID")
    private String SBM_ID;

    @SerializedName("SBM_NAME")
    private String SBM_NAME;

    public MSRModel() {
    }

    public MSRModel(String SBM_ID, String SBM_NAME) {
        this.SBM_ID = SBM_ID;
        this.SBM_NAME = SBM_NAME;
    }

    public String getSBM_ID() {
        return SBM_ID;
    }

    public String getSBM_NAME() {
        return SBM_NAME;
    }

    @Override
    public String toString() {
        return "MSRModel{" +
                "SBM_ID='" + SBM_ID + '\'' +
                ", SBM_NAME='" + SBM_NAME + '\'' +
                '}';
    }
}
