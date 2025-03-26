package elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable;

import com.google.gson.annotations.SerializedName;

public class MMGValidateDetails {

    @SerializedName("REFTYPE")
    private String REFTYPE;

    @SerializedName("STATUS")
    private String STATUS;

    @SerializedName("CONSUMER")
    private String CONSUMER;


    public MMGValidateDetails(String REFTYPE, String STATUS, String CONSUMER) {
        this.REFTYPE = REFTYPE;
        this.STATUS = STATUS;
        this.CONSUMER = CONSUMER;
    }

    public String getREFTYPE() {
        return REFTYPE;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public String getCONSUMER() {
        return CONSUMER;
    }

    public void setREFTYPE(String REFTYPE) {
        this.REFTYPE = REFTYPE;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public void setCONSUMER(String CONSUMER) {
        this.CONSUMER = CONSUMER;
    }


    @Override
    public String toString() {
        return "MMGValidateDetails{" +
                "REFTYPE='" + REFTYPE + '\'' +
                ", STATUS='" + STATUS + '\'' +
                ", CONSUMER='" + CONSUMER + '\'' +
                '}';
    }
}
