package elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class MeterObservationModel extends RealmObject {
    @SerializedName("MTRSTATUS")
    private String MTRSTATUS;

    @SerializedName("DFM_CODE")
    private String DFM_CODE;

    @SerializedName("DFM_DEFNAME")
    private String DFM_DEFNAME;

    @SerializedName("MSNM_READING_MANDATORY")
    private String MSNM_READING_MANDATORY;

    @SerializedName("MSNM_PHOTO_REQ")
    private String MSNM_PHOTO_REQ;

    public MeterObservationModel() {
    }

    public MeterObservationModel(String MTRSTATUS, String DFM_CODE, String DFM_DEFNAME, String MSNM_READING_MANDATORY, String MSNM_PHOTO_REQ) {
        this.MTRSTATUS = MTRSTATUS;
        this.DFM_CODE = DFM_CODE;
        this.DFM_DEFNAME = DFM_DEFNAME;
        this.MSNM_READING_MANDATORY = MSNM_READING_MANDATORY;
        this.MSNM_PHOTO_REQ = MSNM_PHOTO_REQ;
    }

    public String getMTRSTATUS() {
        return MTRSTATUS;
    }

    public void setMTRSTATUS(String MTRSTATUS) {
        this.MTRSTATUS = MTRSTATUS;
    }

    public String getDFM_CODE() {
        return DFM_CODE;
    }

    public void setDFM_CODE(String DFM_CODE) {
        this.DFM_CODE = DFM_CODE;
    }

    public String getDFM_DEFNAME() {
        return DFM_DEFNAME;
    }

    public void setDFM_DEFNAME(String DFM_DEFNAME) {
        this.DFM_DEFNAME = DFM_DEFNAME;
    }

    public String getMSNM_READING_MANDATORY() {
        return MSNM_READING_MANDATORY;
    }

    public void setMSNM_READING_MANDATORY(String MSNM_READING_MANDATORY) {
        this.MSNM_READING_MANDATORY = MSNM_READING_MANDATORY;
    }

    public String getMSNM_PHOTO_REQ() {
        return MSNM_PHOTO_REQ;
    }

    public void setMSNM_PHOTO_REQ(String MSNM_PHOTO_REQ) {
        this.MSNM_PHOTO_REQ = MSNM_PHOTO_REQ;
    }

    @Override
    public String toString() {
        return "MeterObservationModel{" +
                "MTRSTATUS='" + MTRSTATUS + '\'' +
                ", DFM_CODE='" + DFM_CODE + '\'' +
                ", DFM_DEFNAME='" + DFM_DEFNAME + '\'' +
                ", MSNM_READING_MANDATORY='" + MSNM_READING_MANDATORY + '\'' +
                ", MSNM_PHOTO_REQ='" + MSNM_PHOTO_REQ + '\'' +
                '}';
    }
}
