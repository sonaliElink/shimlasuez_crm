package elink.suezShimla.water.crm.InternalNetworkAudit.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

public class MeterConnected extends RealmObject implements Serializable {

    @SerializedName("SEALNO")
    String SEALNO;

    @SerializedName("MMFG_MFGNAME")
    String MMFG_MFGNAME;

    @SerializedName("SRM_CONNECTION_LOAD")
    String SRM_CONNECTION_LOAD;

    @SerializedName("MTRM_SERIAL_NO")
    String MTRM_SERIAL_NO;

    @SerializedName("O_METERTYPE")
    String O_METERTYPE;

    @SerializedName("MTRM_MANUFACTURE_CODE")
    String MTRM_MANUFACTURE_CODE;

    @SerializedName("MTRM_MANUFACTURE_TYPE_CODE")
    String MTRM_MANUFACTURE_TYPE_CODE;

    @SerializedName("SRM_CONNSIZE_ID")
    String SRM_CONNSIZE_ID;
    @SerializedName("MTRM_METERSTATUS_ID")
    String MTRM_METERSTATUS_ID;

    @SerializedName("MSM_METERSTATUS_NAME")
    String MSM_METERSTATUS_NAME;

    public MeterConnected(String MTRM_SERIAL_NO,String SEALNO, String MMFG_MFGNAME, String SRM_CONNECTION_LOAD,
                           String o_METERTYPE) {
        this.MTRM_SERIAL_NO = MTRM_SERIAL_NO;
        this.SEALNO = SEALNO;
        this.MMFG_MFGNAME = MMFG_MFGNAME;
        this.SRM_CONNECTION_LOAD = SRM_CONNECTION_LOAD;
        this.O_METERTYPE = o_METERTYPE;
    }


    public String getMSM_METERSTATUS_NAME() {
        return MSM_METERSTATUS_NAME;
    }

    public void setMSM_METERSTATUS_NAME(String MSM_METERSTATUS_NAME) {
        this.MSM_METERSTATUS_NAME = MSM_METERSTATUS_NAME;
    }

    public String getSEALNO() {
        return SEALNO;
    }

    public void setSEALNO(String SEALNO) {
        this.SEALNO = SEALNO;
    }

    public String getSRM_CONNECTION_LOAD() {
        return SRM_CONNECTION_LOAD;
    }

    public void setSRM_CONNECTION_LOAD(String SRM_CONNECTION_LOAD) {
        this.SRM_CONNECTION_LOAD = SRM_CONNECTION_LOAD;
    }

    public String getMTRM_SERIAL_NO() {
        return MTRM_SERIAL_NO;
    }

    public void setMTRM_SERIAL_NO(String MTRM_SERIAL_NO) {
        this.MTRM_SERIAL_NO = MTRM_SERIAL_NO;
    }

    public String getO_METERTYPE() {
        return O_METERTYPE;
    }

    public void setO_METERTYPE(String o_METERTYPE) {
        O_METERTYPE = o_METERTYPE;
    }

    public String getMTRM_METERSTATUS_ID() {
        return MTRM_METERSTATUS_ID;
    }

    public void setMTRM_METERSTATUS_ID(String MTRM_METERSTATUS_ID) {
        this.MTRM_METERSTATUS_ID = MTRM_METERSTATUS_ID;
    }

    public String getSealNo() {
        return SEALNO;
    }

    public void setSealNo(String sealNo) {
        SEALNO = sealNo;
    }

    public String getMMFG_MFGNAME() {
        return MMFG_MFGNAME;
    }

    public void setMMFG_MFGNAME(String MMFG_MFGNAME) {
        this.MMFG_MFGNAME = MMFG_MFGNAME;
    }

    public String getSRM_Connection_load() {
        return SRM_CONNECTION_LOAD;
    }

    public void setSRM_Connection_load(String SRM_Connection_load) {
        this.SRM_CONNECTION_LOAD = SRM_Connection_load;
    }

    public String getMTRM_Serial_No() {
        return MTRM_SERIAL_NO;
    }

    public void setMTRM_Serial_No(String MTRM_Serial_No) {
        this.MTRM_SERIAL_NO = MTRM_Serial_No;
    }

    public String getO_MeterType() {
        return O_METERTYPE;
    }

    public void setO_MeterType(String o_MeterType) {
        O_METERTYPE = o_MeterType;
    }

    public String getMTRM_MANUFACTURE_CODE() {
        return MTRM_MANUFACTURE_CODE;
    }

    public void setMTRM_MANUFACTURE_CODE(String MTRM_MANUFACTURE_CODE) {
        this.MTRM_MANUFACTURE_CODE = MTRM_MANUFACTURE_CODE;
    }

    public String getMTRM_MANUFACTURE_TYPE_CODE() {
        return MTRM_MANUFACTURE_TYPE_CODE;
    }

    public void setMTRM_MANUFACTURE_TYPE_CODE(String MTRM_MANUFACTURE_TYPE_CODE) {
        this.MTRM_MANUFACTURE_TYPE_CODE = MTRM_MANUFACTURE_TYPE_CODE;
    }

    public String getSRM_CONNSIZE_ID() {
        return SRM_CONNSIZE_ID;
    }

    public void setSRM_CONNSIZE_ID(String SRM_CONNSIZE_ID) {
        this.SRM_CONNSIZE_ID = SRM_CONNSIZE_ID;
    }

    public MeterConnected() {
    }

    @Override
    public String toString() {
        return "MeterConnected{" +
                "MTRM_SERIAL_NO='" + MTRM_SERIAL_NO + '\'' +
                ",SEALNO='" + SEALNO + '\''+
                ", MMFG_MFGNAME='" + MMFG_MFGNAME + '\'' +
                ", SRM_CONNECTION_LOAD='" + SRM_CONNECTION_LOAD + '\'' +
                ", O_METERTYPE='" + O_METERTYPE + '\'' +
                '}';
    }
}
