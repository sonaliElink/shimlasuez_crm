package elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class MMGMeterTypeModel extends RealmObject {

    @SerializedName("MTC_METERTYPE_CODE")
    private String MTC_METERTYPE_CODE;

    @SerializedName("MTC_TYPEDESC")
    private String MTC_TYPEDESC;

    public MMGMeterTypeModel() {
    }

    public MMGMeterTypeModel(String MTC_METERTYPE_CODE, String MTC_TYPEDESC) {
        this.MTC_METERTYPE_CODE = MTC_METERTYPE_CODE;
        this.MTC_TYPEDESC = MTC_TYPEDESC;
    }

    public String getMTC_METERTYPE_CODE() {
        return MTC_METERTYPE_CODE;
    }

    public String getMTC_TYPEDESC() {
        return MTC_TYPEDESC;
    }

    @Override
    public String toString() {
        return "MMGMeterTypeModel{" +
                "MTC_METERTYPE_CODE='" + MTC_METERTYPE_CODE + '\'' +
                ", MTC_TYPEDESC=" + MTC_TYPEDESC +
                '}';
    }
}
