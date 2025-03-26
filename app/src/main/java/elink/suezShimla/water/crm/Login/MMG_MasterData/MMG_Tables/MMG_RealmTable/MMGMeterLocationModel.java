package elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class MMGMeterLocationModel extends RealmObject {
    @SerializedName("ML_ID")
    private int  ML_ID;

    @SerializedName("ML_DESC")
    private String ML_DESC;

    public MMGMeterLocationModel() {
    }

    public MMGMeterLocationModel(int ML_ID, String ML_DESC) {
        this.ML_ID = ML_ID;
        this.ML_DESC = ML_DESC;
    }

    public int getML_ID() {
        return ML_ID;
    }

    public String getML_DESC() {
        return ML_DESC;
    }

    @Override
    public String toString() {
        return "MMGMeterLocationModel{" +
                "ML_ID='" + ML_ID + '\'' +
                ", ML_DESC=" + ML_DESC +
                '}';
    }
}
