package elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class MMGObersvationModel extends RealmObject {

    @SerializedName("OCRM_ID")
    private String OCRM_ID;

    @SerializedName("OCRM_NAME")
    private String OCRM_NAME;

    public MMGObersvationModel() {
    }

    public MMGObersvationModel(String OCRM_ID, String OCRM_NAME) {
        this.OCRM_ID = OCRM_ID;
        this.OCRM_NAME = OCRM_NAME;
    }

    public String getOCRM_ID() {
        return OCRM_ID;
    }

    public String getOCRM_NAME() {
        return OCRM_NAME;
    }

    @Override
    public String toString() {
        return "MMGObersvationModel{" +
                "OCRM_ID='" + OCRM_ID + '\'' +
                ", OCRM_NAME=" + OCRM_NAME +
                '}';
    }
}
