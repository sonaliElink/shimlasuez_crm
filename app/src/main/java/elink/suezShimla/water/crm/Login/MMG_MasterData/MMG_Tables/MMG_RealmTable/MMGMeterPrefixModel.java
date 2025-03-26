package elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class MMGMeterPrefixModel extends RealmObject {


    @SerializedName("'NOPREFIX'")
    private String NOPREFIX;

    @SerializedName("VALUE")
    private String VALUE;

    public MMGMeterPrefixModel() {
    }

    public MMGMeterPrefixModel(String NOPREFIX, String VALUE) {
        this.NOPREFIX = NOPREFIX;
        this.VALUE = VALUE;
    }

    public String getNOPREFIX() {
        return NOPREFIX;
    }

    public String getVALUE() {
        return VALUE;
    }

    @Override
    public String toString() {
        return "MMGMeterPrefixModel{" +
                "NOPREFIX='" + NOPREFIX + '\'' +
                ", MMFG_MFGNAME=" + VALUE +
                '}';
    }
}
