package elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model;

import com.google.gson.annotations.SerializedName;

public class MMGCustDetModel {

    @SerializedName("SRM_SERVICE_NO")
    String SRM_SERVICE_NO;

    @SerializedName("SRM_S_NMTITLE")
    String SRM_S_NMTITLE;

    @SerializedName("SRM_S_FIRST_NAME")
    String SRM_S_FIRST_NAME;


    @SerializedName("SRM_S_MIDDLE_NAME")
    String SRM_S_MIDDLE_NAME;


    @SerializedName("SRM_S_SURNAME")
    String SRM_S_SURNAME;

    @SerializedName("SRM_B_DOOR_NO")
    String SRM_B_DOOR_NO;


    @SerializedName("SRM_B_ADDRESS1")
    String SRM_B_ADDRESS1;

    @SerializedName("SRM_B_ADDRESS2")
    String SRM_B_ADDRESS2;


    @SerializedName("SRM_B_ADDRESS2_1")
    String SRM_B_ADDRESS2_1;


    @SerializedName("SRM_B_ADDRESS3")
    String SRM_B_ADDRESS3;

    @SerializedName("B_ADDRESS4")
    String B_ADDRESS4;

    @SerializedName("SRM_B_PIN")
    String SRM_B_PIN;

    @SerializedName("SRM_BU_ID")
    String SRM_BU_ID;

    @SerializedName("SRM_PC_ID")
    String SRM_PC_ID;

    @SerializedName("GROUPNAME")
    String GROUPNAME;


    @SerializedName("BUM_BU_NAME")
    String BUM_BU_NAME;


    @SerializedName("SRM_CONNECTION_LOAD")
    String SRM_CONNECTION_LOAD;


    @SerializedName("TFM_TARIFF_NAME")
    String TFM_TARIFF_NAME;


    @SerializedName("SRM_S_MOBILE_NO")
    String SRM_S_MOBILE_NO;

    @SerializedName("ALTMOBILE")
    String ALTMOBILE;


    @SerializedName("SRM_S_GIS_ID")
    String SRM_S_GIS_ID;

    @SerializedName("MSR")
    String MSR;

    @SerializedName("SR")
    String SR;

    @SerializedName("DMA")
    String DMA;

    @SerializedName("PROPERTY_ASSESSMENT_NO")
    String PROPERTY_ASSESSMENT_NO;

    @SerializedName("WARD")
    String WARD;

    @SerializedName("CM_CIRCLE_NAME")
    String CM_CIRCLE_NAME;

    @SerializedName("NO_OFFLATS")
    String No_OfFlats;



    public MMGCustDetModel() {

    }

    public MMGCustDetModel(String SRM_SERVICE_NO, String SRM_S_NMTITLE, String SRM_S_FIRST_NAME, String SRM_S_MIDDLE_NAME,
                           String SRM_S_SURNAME, String SRM_B_DOOR_NO, String SRM_B_ADDRESS1, String SRM_B_ADDRESS2,
                           String SRM_B_ADDRESS2_1, String SRM_B_ADDRESS3, String b_ADDRESS4, String SRM_B_PIN,
                           String SRM_BU_ID, String SRM_PC_ID, String GROUPNAME, String BUM_BU_NAME, String SRM_CONNECTION_LOAD,
                           String TFM_TARIFF_NAME, String SRM_S_MOBILE_NO, String ALTMOBILE, String SRM_S_GIS_ID,
                           String MSR, String SR, String DMA, String PROPERTY_ASSESSMENT_NO, String WARD,String No_OfFlats) {
        this.SRM_SERVICE_NO = SRM_SERVICE_NO;
        this.SRM_S_NMTITLE = SRM_S_NMTITLE;
        this.SRM_S_FIRST_NAME = SRM_S_FIRST_NAME;
        this.SRM_S_MIDDLE_NAME = SRM_S_MIDDLE_NAME;
        this.SRM_S_SURNAME = SRM_S_SURNAME;
        this.SRM_B_DOOR_NO = SRM_B_DOOR_NO;
        this.SRM_B_ADDRESS1 = SRM_B_ADDRESS1;
        this.SRM_B_ADDRESS2 = SRM_B_ADDRESS2;
        this.SRM_B_ADDRESS2_1 = SRM_B_ADDRESS2_1;
        this.SRM_B_ADDRESS3 = SRM_B_ADDRESS3;
        B_ADDRESS4 = b_ADDRESS4;
        this.SRM_B_PIN = SRM_B_PIN;
        this.SRM_BU_ID = SRM_BU_ID;
        this.SRM_PC_ID = SRM_PC_ID;
        this.GROUPNAME = GROUPNAME;
        this.BUM_BU_NAME = BUM_BU_NAME;
        this.SRM_CONNECTION_LOAD = SRM_CONNECTION_LOAD;
        this.TFM_TARIFF_NAME = TFM_TARIFF_NAME;
        this.SRM_S_MOBILE_NO = SRM_S_MOBILE_NO;
        this.ALTMOBILE = ALTMOBILE;
        this.SRM_S_GIS_ID = SRM_S_GIS_ID;
        this.MSR = MSR;
        this.SR = SR;
        this.DMA = DMA;
        this.PROPERTY_ASSESSMENT_NO = PROPERTY_ASSESSMENT_NO;
        this.WARD = WARD;
        this.No_OfFlats= No_OfFlats;
    }

    public String getSRM_S_NMTITLE() {
        return SRM_S_NMTITLE;
    }

    public void setSRM_S_NMTITLE(String SRM_S_NMTITLE) {
        this.SRM_S_NMTITLE = SRM_S_NMTITLE;
    }

    public String getSRM_S_FIRST_NAME() {
        return SRM_S_FIRST_NAME;
    }

    public void setSRM_S_FIRST_NAME(String SRM_S_FIRST_NAME) {
        this.SRM_S_FIRST_NAME = SRM_S_FIRST_NAME;
    }

    public String getSRM_S_MIDDLE_NAME() {
        return SRM_S_MIDDLE_NAME;
    }

    public void setSRM_S_MIDDLE_NAME(String SRM_S_MIDDLE_NAME) {
        this.SRM_S_MIDDLE_NAME = SRM_S_MIDDLE_NAME;
    }

    public String getSRM_S_SURNAME() {
        return SRM_S_SURNAME;
    }

    public void setSRM_S_SURNAME(String SRM_S_SURNAME) {
        this.SRM_S_SURNAME = SRM_S_SURNAME;
    }

    public String getSRM_B_ADDRESS1() {
        return SRM_B_ADDRESS1;
    }

    public void setSRM_B_ADDRESS1(String SRM_B_ADDRESS1) {
        this.SRM_B_ADDRESS1 = SRM_B_ADDRESS1;
    }

    public String getSRM_B_ADDRESS2() {
        return SRM_B_ADDRESS2;
    }

    public void setSRM_B_ADDRESS2(String SRM_B_ADDRESS2) {
        this.SRM_B_ADDRESS2 = SRM_B_ADDRESS2;
    }

    public String getSRM_B_ADDRESS2_1() {
        return SRM_B_ADDRESS2_1;
    }

    public void setSRM_B_ADDRESS2_1(String SRM_B_ADDRESS2_1) {
        this.SRM_B_ADDRESS2_1 = SRM_B_ADDRESS2_1;
    }

    public String getSRM_B_ADDRESS3() {
        return SRM_B_ADDRESS3;
    }

    public void setSRM_B_ADDRESS3(String SRM_B_ADDRESS3) {
        this.SRM_B_ADDRESS3 = SRM_B_ADDRESS3;
    }

    public String getB_ADDRESS4() {
        return B_ADDRESS4;
    }

    public void setB_ADDRESS4(String b_ADDRESS4) {
        B_ADDRESS4 = b_ADDRESS4;
    }

    public String getSRM_B_PIN() {
        return SRM_B_PIN;
    }

    public void setSRM_B_PIN(String SRM_B_PIN) {
        this.SRM_B_PIN = SRM_B_PIN;
    }

    public String getCM_CIRCLE_NAME() {
        return CM_CIRCLE_NAME;
    }

    public void setCM_CIRCLE_NAME(String CM_CIRCLE_NAME) {
        this.CM_CIRCLE_NAME = CM_CIRCLE_NAME;
    }

    public String getBUM_BU_NAME() {
        return BUM_BU_NAME;
    }

    public void setBUM_BU_NAME(String BUM_BU_NAME) {
        this.BUM_BU_NAME = BUM_BU_NAME;
    }

    public String getSRM_CONNECTION_LOAD() {
        return SRM_CONNECTION_LOAD;
    }

    public void setSRM_CONNECTION_LOAD(String SRM_CONNECTION_LOAD) {
        this.SRM_CONNECTION_LOAD = SRM_CONNECTION_LOAD;
    }

    public String getTFM_TARIFF_NAME() {
        return TFM_TARIFF_NAME;
    }

    public void setTFM_TARIFF_NAME(String TFM_TARIFF_NAME) {
        this.TFM_TARIFF_NAME = TFM_TARIFF_NAME;
    }

    public String getSRM_B_DOOR_NO() {
        return SRM_B_DOOR_NO;
    }

    public void setSRM_B_DOOR_NO(String SRM_B_DOOR_NO) {
        this.SRM_B_DOOR_NO = SRM_B_DOOR_NO;
    }

    public String getSRM_BU_ID() {
        return SRM_BU_ID;
    }

    public void setSRM_BU_ID(String SRM_BU_ID) {
        this.SRM_BU_ID = SRM_BU_ID;
    }

    public String getSRM_PC_ID() {
        return SRM_PC_ID;
    }

    public void setSRM_PC_ID(String SRM_PC_ID) {
        this.SRM_PC_ID = SRM_PC_ID;
    }

    public String getNo_OfFlats() {
        return No_OfFlats;
    }

    public void setNo_OfFlats(String no_OfFlats) {
        No_OfFlats = no_OfFlats;
    }

    public String getSRM_S_MOBILE_NO() {
        return SRM_S_MOBILE_NO;
    }

    public String getALTMOBILE() {
        return ALTMOBILE;
    }

    public String getGROUPNAME() {
        return GROUPNAME;
    }

    public String getWARD() {
        return WARD;
    }

    public String getSRM_S_GIS_ID() {
        return SRM_S_GIS_ID;
    }

    public String getSRM_SERVICE_NO() {
        return SRM_SERVICE_NO;
    }

    public String getMSR() {
        return MSR;
    }

    public String getSR() {
        return SR;
    }

    public String getDMA() {
        return DMA;
    }

    public String getPROPERTY_ASSESSMENT_NO() {
        return PROPERTY_ASSESSMENT_NO;
    }

    @Override
    public String toString() {
        return "MMGCustDetModel{" +
                "SRM_SERVICE_NO='" + SRM_SERVICE_NO + '\'' +
                ", SRM_S_NMTITLE='" + SRM_S_NMTITLE + '\'' +
                ", SRM_S_FIRST_NAME='" + SRM_S_FIRST_NAME + '\'' +
                ", SRM_S_MIDDLE_NAME='" + SRM_S_MIDDLE_NAME + '\'' +
                ", SRM_S_SURNAME='" + SRM_S_SURNAME + '\'' +
                ", SRM_B_DOOR_NO='" + SRM_B_DOOR_NO + '\'' +
                ", SRM_B_ADDRESS1='" + SRM_B_ADDRESS1 + '\'' +
                ", SRM_B_ADDRESS2='" + SRM_B_ADDRESS2 + '\'' +
                ", SRM_B_ADDRESS2_1='" + SRM_B_ADDRESS2_1 + '\'' +
                ", SRM_B_ADDRESS3='" + SRM_B_ADDRESS3 + '\'' +
                ", B_ADDRESS4='" + B_ADDRESS4 + '\'' +
                ", SRM_B_PIN='" + SRM_B_PIN + '\'' +
                ", SRM_BU_ID='" + SRM_BU_ID + '\'' +
                ", SRM_PC_ID='" + SRM_PC_ID + '\'' +
                ", GROUPNAME='" + GROUPNAME + '\'' +
                ", BUM_BU_NAME='" + BUM_BU_NAME + '\'' +
                ", SRM_CONNECTION_LOAD='" + SRM_CONNECTION_LOAD + '\'' +
                ", TFM_TARIFF_NAME='" + TFM_TARIFF_NAME + '\'' +
                ", SRM_S_MOBILE_NO='" + SRM_S_MOBILE_NO + '\'' +
                ", ALTMOBILE='" + ALTMOBILE + '\'' +
                ", SRM_S_GIS_ID='" + SRM_S_GIS_ID + '\'' +
                ", MSR='" + MSR + '\'' +
                ", SR='" + SR + '\'' +
                ", DMA='" + DMA + '\'' +
                ", PROPERTY_ASSESSMENT_NO='" + PROPERTY_ASSESSMENT_NO + '\'' +
                ", WARD='" + WARD + '\'' +
                ", CM_CIRCLE_NAME='" + CM_CIRCLE_NAME + '\'' +
                ",No_OfFlats='" + No_OfFlats + '\'' +
                '}';
    }
}
