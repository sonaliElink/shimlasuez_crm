package elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class MMGCgRestroModel extends RealmObject {
    @SerializedName("CGR_ID")
    private int CGR_ID;

    @SerializedName("CGR_DESC")
    private String CGR_DESC;

    public MMGCgRestroModel() {
    }

    public MMGCgRestroModel(int CGR_ID, String CGR_DESC) {
        this.CGR_ID = CGR_ID;
        this.CGR_DESC = CGR_DESC;
    }

    public int getCGR_ID() {
        return CGR_ID;
    }

    public String getCGR_DESC() {
        return CGR_DESC;
    }

    @Override
    public String toString() {
        return "MMGCgRestroModel{" +
                "CGR_ID='" + CGR_ID + '\'' +
                ", CGR_DESC=" + CGR_DESC +
                '}';
    }
}
