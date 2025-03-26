package elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model;

import com.google.gson.annotations.SerializedName;

public class MMGMeterConnectedDetailsModel {
    @SerializedName("MTRM_MANUFACTURE_CODE")
    String MTRM_MANUFACTURE_CODE;

    @SerializedName("MMFG_MFGNAME")
    String MMFG_MFGNAME;

    @SerializedName("MTRM_SERIAL_NO")
    String MTRM_SERIAL_NO;

    @SerializedName("MTRM_INSTALLATION_DATE")
    String MTRM_INSTALLATION_DATE;

    @SerializedName("SIZEOFMETER")
    String SIZEOFMETER;

    @SerializedName("SEALNO")
    String SEALNO;

    @SerializedName("MTRM_PAST_READING")
    String MTRM_PAST_READING;

    @SerializedName("LASTREADINGDATE")
    String LASTREADINGDATE;

    @SerializedName("SRM_CONNSIZE_ID")
    String SRM_CONNSIZE_ID;

    @SerializedName("MRT_OLDMETER_STATUS")
    String MRT_OLDMETER_STATUS;

    @SerializedName("MTRM_METERSTATUS_ID")
    String MTRM_METERSTATUS_ID;

    @SerializedName("MSM_METERSTATUS_NAME")
    String MSM_METERSTATUS_NAME;

    @SerializedName("MTRM_MANUFACTURE_TYPE_CODE")
    String MTRM_MANUFACTURE_TYPE_CODE;

    @SerializedName("O_METERTYPE")
    String O_METERTYPE;

    @SerializedName("MTRM_FROMNODE")
    String MTRM_FROMNODE;

    @SerializedName("MTRM_TONODE")
    String MTRM_TONODE;

    @SerializedName("MI_METERINSTALLID")
    String MI_METERINSTALLID;

    @SerializedName("MI_STATUS")
    String MI_STATUS;

    @SerializedName("MTRM_DIGITS")
    String MTRM_DIGITS;

    @SerializedName("CONTRACTOR")
    String CONTRACTOR;

    @SerializedName("CONTEMPCODE")
    String CONTEMPCODE;

     @SerializedName("REFNO")
    String REFNO;

    @SerializedName("MTRM_AVERAGE_CONSUMPTION")
    String MTRM_AVERAGE_CONSUMPTION;


    @SerializedName("MTRM_ESTIMATED_AVRG_CONSUMP")
    String MTRM_ESTIMATED_AVRG_CONSUMP;

    public MMGMeterConnectedDetailsModel() {

    }

    public MMGMeterConnectedDetailsModel(String MTRM_MANUFACTURE_CODE, String MMFG_MFGNAME, String MTRM_SERIAL_NO, String MTRM_INSTALLATION_DATE, String SIZEOFMETER, String SEALNO, String MTRM_PAST_READING, String LASTREADINGDATE, String SRM_CONNSIZE_ID, String MRT_OLDMETER_STATUS, String MTRM_METERSTATUS_ID, String MSM_METERSTATUS_NAME, String MTRM_MANUFACTURE_TYPE_CODE, String o_METERTYPE, String MTRM_FROMNODE, String MTRM_TONODE, String MI_METERINSTALLID, String MI_STATUS, String MTRM_DIGITS, String CONTRACTOR, String CONTEMPCODE, String REFNO,String MTRM_AVERAGE_CONSUMPTION,String MTRM_ESTIMATED_AVRG_CONSUMP) {
        this.MTRM_MANUFACTURE_CODE = MTRM_MANUFACTURE_CODE;
        this.MMFG_MFGNAME = MMFG_MFGNAME;
        this.MTRM_SERIAL_NO = MTRM_SERIAL_NO;
        this.MTRM_INSTALLATION_DATE = MTRM_INSTALLATION_DATE;
        this.SIZEOFMETER = SIZEOFMETER;
        this.SEALNO = SEALNO;
        this.MTRM_PAST_READING = MTRM_PAST_READING;
        this.LASTREADINGDATE = LASTREADINGDATE;
        this.SRM_CONNSIZE_ID = SRM_CONNSIZE_ID;
        this.MRT_OLDMETER_STATUS = MRT_OLDMETER_STATUS;
        this.MTRM_METERSTATUS_ID = MTRM_METERSTATUS_ID;
        this.MSM_METERSTATUS_NAME = MSM_METERSTATUS_NAME;
        this.MTRM_MANUFACTURE_TYPE_CODE = MTRM_MANUFACTURE_TYPE_CODE;
        O_METERTYPE = o_METERTYPE;
        this.MTRM_FROMNODE = MTRM_FROMNODE;
        this.MTRM_TONODE = MTRM_TONODE;
        this.MI_METERINSTALLID = MI_METERINSTALLID;
        this.MI_STATUS = MI_STATUS;
        this.MTRM_DIGITS = MTRM_DIGITS;
        this.CONTRACTOR = CONTRACTOR;
        this.CONTEMPCODE = CONTEMPCODE;
        this.REFNO = REFNO;
        this.MTRM_AVERAGE_CONSUMPTION = MTRM_AVERAGE_CONSUMPTION;
        this.MTRM_ESTIMATED_AVRG_CONSUMP = MTRM_ESTIMATED_AVRG_CONSUMP;
    }


    public String getMMFG_MFGNAME() {
        return MMFG_MFGNAME;
    }

    public void setMMFG_MFGNAME(String MMFG_MFGNAME) {
        this.MMFG_MFGNAME = MMFG_MFGNAME;
    }

    public String getMTRM_SERIAL_NO() {
        return MTRM_SERIAL_NO;
    }

    public void setMTRM_SERIAL_NO(String MTRM_SERIAL_NO) {
        this.MTRM_SERIAL_NO = MTRM_SERIAL_NO;
    }

    public String getMTRM_INSTALLATION_DATE() {
        return MTRM_INSTALLATION_DATE;
    }

    public void setMTRM_INSTALLATION_DATE(String MTRM_INSTALLATION_DATE) {
        this.MTRM_INSTALLATION_DATE = MTRM_INSTALLATION_DATE;
    }

    public String getSIZEOFMETER() {
        return SIZEOFMETER;
    }

    public void setSIZEOFMETER(String SIZEOFMETER) {
        this.SIZEOFMETER = SIZEOFMETER;
    }

    public String getSEALNO() {
        return SEALNO;
    }

    public void setSEALNO(String SEALNO) {
        this.SEALNO = SEALNO;
    }

    public String getMTRM_PAST_READING() {
        return MTRM_PAST_READING;
    }

    public void setMTRM_PAST_READING(String MTRM_PAST_READING) {
        this.MTRM_PAST_READING = MTRM_PAST_READING;
    }

    public String getMTRM_MANUFACTURE_CODE() {
        return MTRM_MANUFACTURE_CODE;
    }

    public void setMTRM_MANUFACTURE_CODE(String MTRM_MANUFACTURE_CODE) {
        this.MTRM_MANUFACTURE_CODE = MTRM_MANUFACTURE_CODE;
    }

    public String getMTRM_FROMNODE() {
        return MTRM_FROMNODE;
    }

    public String getMTRM_TONODE() {
        return MTRM_TONODE;
    }

    public String getMTRM_METERSTATUS_ID() {
        return MTRM_METERSTATUS_ID;
    }

    public String getMSM_METERSTATUS_NAME() {
        return MSM_METERSTATUS_NAME;
    }

    public String getMTRM_MANUFACTURE_TYPE_CODE() {
        return MTRM_MANUFACTURE_TYPE_CODE;
    }

    public String getO_METERTYPE() {
        return O_METERTYPE;
    }

    public String getSRM_CONNSIZE_ID() {
        return SRM_CONNSIZE_ID;
    }

    public void setSRM_CONNSIZE_ID(String SRM_CONNSIZE_ID) {
        this.SRM_CONNSIZE_ID = SRM_CONNSIZE_ID;
    }

    public String getMRT_OLDMETER_STATUS() {
        return MRT_OLDMETER_STATUS;
    }

    public String getMI_METERINSTALLID() {
        return MI_METERINSTALLID;
    }

    public String getMI_STATUS() {
        return MI_STATUS;
    }

    public String getMTRM_DIGITS() {
        return MTRM_DIGITS;
    }

    public String getCONTRACTOR() {
        return CONTRACTOR;
    }

    public String getCONTEMPCODE() {
        return CONTEMPCODE;
    }

    public String getREFNO() {
        return REFNO;
    }

    public String getMTRM_AVERAGE_CONSUMPTION() {
        return MTRM_AVERAGE_CONSUMPTION;
    }

    public void setMTRM_AVERAGE_CONSUMPTION(String MTRM_AVERAGE_CONSUMPTION) {
        this.MTRM_AVERAGE_CONSUMPTION = MTRM_AVERAGE_CONSUMPTION;
    }

    public String getMTRM_ESTIMATED_AVRG_CONSUMP() {
        return MTRM_ESTIMATED_AVRG_CONSUMP;
    }

    public void setMTRM_ESTIMATED_AVRG_CONSUMP(String MTRM_ESTIMATED_AVRG_CONSUMP) {
        this.MTRM_ESTIMATED_AVRG_CONSUMP = MTRM_ESTIMATED_AVRG_CONSUMP;
    }

    public void setREFNO(String REFNO) {
        this.REFNO = REFNO;
    }

    public String getLASTREADINGDATE() {
        return LASTREADINGDATE;
    }

    public void setLASTREADINGDATE(String LASTREADINGDATE) {
        this.LASTREADINGDATE = LASTREADINGDATE;
    }

    public void setMRT_OLDMETER_STATUS(String MRT_OLDMETER_STATUS) {
        this.MRT_OLDMETER_STATUS = MRT_OLDMETER_STATUS;
    }

    public void setMTRM_METERSTATUS_ID(String MTRM_METERSTATUS_ID) {
        this.MTRM_METERSTATUS_ID = MTRM_METERSTATUS_ID;
    }

    public void setMSM_METERSTATUS_NAME(String MSM_METERSTATUS_NAME) {
        this.MSM_METERSTATUS_NAME = MSM_METERSTATUS_NAME;
    }

    public void setMTRM_MANUFACTURE_TYPE_CODE(String MTRM_MANUFACTURE_TYPE_CODE) {
        this.MTRM_MANUFACTURE_TYPE_CODE = MTRM_MANUFACTURE_TYPE_CODE;
    }

    public void setO_METERTYPE(String o_METERTYPE) {
        O_METERTYPE = o_METERTYPE;
    }

    public void setMTRM_FROMNODE(String MTRM_FROMNODE) {
        this.MTRM_FROMNODE = MTRM_FROMNODE;
    }

    public void setMTRM_TONODE(String MTRM_TONODE) {
        this.MTRM_TONODE = MTRM_TONODE;
    }

    public void setMI_METERINSTALLID(String MI_METERINSTALLID) {
        this.MI_METERINSTALLID = MI_METERINSTALLID;
    }

    public void setMI_STATUS(String MI_STATUS) {
        this.MI_STATUS = MI_STATUS;
    }

    public void setMTRM_DIGITS(String MTRM_DIGITS) {
        this.MTRM_DIGITS = MTRM_DIGITS;
    }

    public void setCONTRACTOR(String CONTRACTOR) {
        this.CONTRACTOR = CONTRACTOR;
    }

    public void setCONTEMPCODE(String CONTEMPCODE) {
        this.CONTEMPCODE = CONTEMPCODE;
    }

    @Override
    public String toString() {
        return "MMGMeterConnectedDetailsModel{" +
                "MTRM_MANUFACTURE_CODE='" + MTRM_MANUFACTURE_CODE + '\'' +
                ", MMFG_MFGNAME='" + MMFG_MFGNAME + '\'' +
                ", MTRM_SERIAL_NO='" + MTRM_SERIAL_NO + '\'' +
                ", MTRM_INSTALLATION_DATE='" + MTRM_INSTALLATION_DATE + '\'' +
                ", SIZEOFMETER='" + SIZEOFMETER + '\'' +
                ", SEALNO='" + SEALNO + '\'' +
                ", MTRM_PAST_READING='" + MTRM_PAST_READING + '\'' +
                ", LASTREADINGDATE='" + LASTREADINGDATE + '\'' +
                ", SRM_CONNSIZE_ID='" + SRM_CONNSIZE_ID + '\'' +
                ", MRT_OLDMETER_STATUS='" + MRT_OLDMETER_STATUS + '\'' +
                ", MTRM_METERSTATUS_ID='" + MTRM_METERSTATUS_ID + '\'' +
                ", MSM_METERSTATUS_NAME='" + MSM_METERSTATUS_NAME + '\'' +
                ", MTRM_MANUFACTURE_TYPE_CODE='" + MTRM_MANUFACTURE_TYPE_CODE + '\'' +
                ", O_METERTYPE='" + O_METERTYPE + '\'' +
                ", MTRM_FROMNODE='" + MTRM_FROMNODE + '\'' +
                ", MTRM_TONODE='" + MTRM_TONODE + '\'' +
                ", MI_METERINSTALLID='" + MI_METERINSTALLID + '\'' +
                ", MI_STATUS='" + MI_STATUS + '\'' +
                ", MTRM_DIGITS='" + MTRM_DIGITS + '\'' +
                ", CONTRACTOR='" + CONTRACTOR + '\'' +
                ", CONTEMPCODE='" + CONTEMPCODE + '\'' +
                ", REFNO='" + REFNO + '\'' +
                '}';
    }
}
