package elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class MMGMakerCodeModel extends RealmObject {

    @SerializedName("MMFG_MFGCODE")
    private String MMFG_MFGCODE;

    @SerializedName("MMFG_MFGNAME")
    private String MMFG_MFGNAME;

    @SerializedName("MMFG_MATERIAL_TYPE")
    private Float MMFG_MATERIAL_TYPE;

    public MMGMakerCodeModel() {
    }

    public MMGMakerCodeModel(String MMFG_MFGCODE, String MMFG_MFGNAME, Float MMFG_MATERIAL_TYPE) {
        this.MMFG_MFGCODE = MMFG_MFGCODE;
        this.MMFG_MFGNAME = MMFG_MFGNAME;
        this.MMFG_MATERIAL_TYPE = MMFG_MATERIAL_TYPE;
    }

    public String getMMFG_MFGCODE() {
        return MMFG_MFGCODE;
    }

    public String getMMFG_MFGNAME() {
        return MMFG_MFGNAME;
    }

    public Float getMMFG_MATERIAL_TYPE() {
        return MMFG_MATERIAL_TYPE;
    }

    @Override
    public String toString() {
        return "MMGMakerCodeModel{" +
                "MMFG_MFGCODE='" + MMFG_MFGCODE + '\'' +
                ", MMFG_MFGNAME='" + MMFG_MFGNAME + '\'' +
                ", MMFG_MATERIAL_TYPE='" + MMFG_MATERIAL_TYPE + '\'' +
                '}';
    }
}
