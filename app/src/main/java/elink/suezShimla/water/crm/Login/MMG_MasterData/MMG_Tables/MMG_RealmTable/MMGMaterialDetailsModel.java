package elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class MMGMaterialDetailsModel extends RealmObject {

    @SerializedName("MM_ID")
    private int MM_ID ;

    @SerializedName("MM_NAME")
    private String MM_NAME;

    @SerializedName("M_UNIT")
    private String M_UNIT;

    @SerializedName("MM_DEF_QTY")
    private String MM_DEF_QTY;

    @SerializedName("SIZEID")
    private int SIZEID ;

    public MMGMaterialDetailsModel() {
    }

    public MMGMaterialDetailsModel(int MM_ID , String MM_NAME,String M_UNIT,String MM_DEF_QTY,int SIZEID) {
        this.MM_ID  = MM_ID ;
        this.MM_NAME = MM_NAME;
        this.M_UNIT = M_UNIT;
        this.MM_DEF_QTY = MM_DEF_QTY;
        this.SIZEID = SIZEID;
    }

    public int getMM_ID () {
        return MM_ID  ;
    }

    public String getMM_NAME() {
        return MM_NAME;
    }
    public String getM_UNIT() {
        return M_UNIT;
    }
    public String getMM_DEF_QTY() {
        return MM_DEF_QTY;
    }
    public int getSIZEID () {
        return SIZEID  ;
    }

    @Override
    public String toString() {
        return "MMGMaterialDetailsModel{" +
                "MM_ID ='" + MM_ID  + '\'' +
                ", MM_NAME=" + MM_NAME +
                ", M_UNIT=" + M_UNIT +
                ", SIZEID=" + SIZEID +
                '}';
    }
}
