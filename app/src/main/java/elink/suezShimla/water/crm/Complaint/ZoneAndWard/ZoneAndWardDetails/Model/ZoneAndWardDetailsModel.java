package elink.suezShimla.water.crm.Complaint.ZoneAndWard.ZoneAndWardDetails.Model;

import com.google.gson.annotations.SerializedName;

public class ZoneAndWardDetailsModel {

    @SerializedName("ZM_ZONE_ID")
    private String ZM_ZONE_ID;

    @SerializedName("DTE_ZONE")
    private String DTE_ZONE;

    @SerializedName("DTE_BU")
    private String DTE_BU;

    @SerializedName("BUM_BU_NAME")
    private String BUM_BU_NAME;

    @SerializedName("PC")
    private String PC;

    @SerializedName("DTE_PC")
    private String DTE_PC;

    @SerializedName("CMTM_ID")
    private String CMTM_ID;

    @SerializedName("COM_TYPE")
    private String COM_TYPE;

    @SerializedName("DTE_REASON")
    private String DTE_REASON;

    @SerializedName("COUNT_RECEIVED")
    private String COUNT_RECEIVED;

    @SerializedName("COUNT_PREVIOUSPENDING")
    private String COUNT_PREVIOUSPENDING;

    @SerializedName("COUNT_TOTALCOMPCOUNT")
    private String COUNT_TOTALCOMPCOUNT;

    @SerializedName("COUNT_COMPLETEDCOMP")
    private String COUNT_COMPLETEDCOMP;

    @SerializedName("A")
    private String A;

    @SerializedName("B")
    private String B;

    @SerializedName("C")
    private String C;

    @SerializedName("D")
    private String D;

    @SerializedName("COUNT_PENDINGCOMP")
    private String COUNT_PENDINGCOMP;

    @SerializedName("PENDINGA")
    private String PENDINGA;

    @SerializedName("PENDINGB")
    private String PENDINGB;

    @SerializedName("PENDINGC")
    private String PENDINGC;

    @SerializedName("PENDINGD")
    private String PENDINGD;

    public ZoneAndWardDetailsModel() {
    }

    public ZoneAndWardDetailsModel(String ZM_ZONE_ID, String DTE_ZONE, String DTE_BU, String BUM_BU_NAME, String PC, String DTE_PC, String CMTM_ID, String COM_TYPE, String DTE_REASON, String COUNT_RECEIVED, String COUNT_PREVIOUSPENDING, String COUNT_TOTALCOMPCOUNT, String COUNT_COMPLETEDCOMP, String a, String b, String c, String d, String COUNT_PENDINGCOMP, String PENDINGA, String PENDINGB, String PENDINGC, String PENDINGD) {
        this.ZM_ZONE_ID = ZM_ZONE_ID;
        this.DTE_ZONE = DTE_ZONE;
        this.DTE_BU = DTE_BU;
        this.BUM_BU_NAME = BUM_BU_NAME;
        this.PC = PC;
        this.DTE_PC = DTE_PC;
        this.CMTM_ID = CMTM_ID;
        this.COM_TYPE = COM_TYPE;
        this.DTE_REASON = DTE_REASON;
        this.COUNT_RECEIVED = COUNT_RECEIVED;
        this.COUNT_PREVIOUSPENDING = COUNT_PREVIOUSPENDING;
        this.COUNT_TOTALCOMPCOUNT = COUNT_TOTALCOMPCOUNT;
        this.COUNT_COMPLETEDCOMP = COUNT_COMPLETEDCOMP;
        A = a;
        B = b;
        C = c;
        D = d;
        this.COUNT_PENDINGCOMP = COUNT_PENDINGCOMP;
        this.PENDINGA = PENDINGA;
        this.PENDINGB = PENDINGB;
        this.PENDINGC = PENDINGC;
        this.PENDINGD = PENDINGD;
    }

    public String getZM_ZONE_ID() {
        return ZM_ZONE_ID;
    }

    public String getDTE_ZONE() {
        return DTE_ZONE;
    }

    public String getDTE_BU() {
        return DTE_BU;
    }

    public String getBUM_BU_NAME() {
        return BUM_BU_NAME;
    }

    public String getPC() {
        return PC;
    }

    public String getDTE_PC() {
        return DTE_PC;
    }

    public String getCMTM_ID() {
        return CMTM_ID;
    }

    public String getCOM_TYPE() {
        return COM_TYPE;
    }

    public String getDTE_REASON() {
        return DTE_REASON;
    }

    public String getCOUNT_RECEIVED() {
        return COUNT_RECEIVED;
    }

    public String getCOUNT_PREVIOUSPENDING() {
        return COUNT_PREVIOUSPENDING;
    }

    public String getCOUNT_TOTALCOMPCOUNT() {
        return COUNT_TOTALCOMPCOUNT;
    }

    public String getCOUNT_COMPLETEDCOMP() {
        return COUNT_COMPLETEDCOMP;
    }

    public String getA() {
        return A;
    }

    public String getB() {
        return B;
    }

    public String getC() {
        return C;
    }

    public String getD() {
        return D;
    }

    public String getCOUNT_PENDINGCOMP() {
        return COUNT_PENDINGCOMP;
    }

    public String getPENDINGA() {
        return PENDINGA;
    }

    public String getPENDINGB() {
        return PENDINGB;
    }

    public String getPENDINGC() {
        return PENDINGC;
    }

    public String getPENDINGD() {
        return PENDINGD;
    }

    @Override
    public String toString() {
        return "ZoneAndWardDetailsModel{" +
                "ZM_ZONE_ID='" + ZM_ZONE_ID + '\'' +
                ", DTE_ZONE='" + DTE_ZONE + '\'' +
                ", DTE_BU='" + DTE_BU + '\'' +
                ", BUM_BU_NAME='" + BUM_BU_NAME + '\'' +
                ", PC='" + PC + '\'' +
                ", DTE_PC='" + DTE_PC + '\'' +
                ", CMTM_ID='" + CMTM_ID + '\'' +
                ", COM_TYPE='" + COM_TYPE + '\'' +
                ", DTE_REASON='" + DTE_REASON + '\'' +
                ", COUNT_RECEIVED='" + COUNT_RECEIVED + '\'' +
                ", COUNT_PREVIOUSPENDING='" + COUNT_PREVIOUSPENDING + '\'' +
                ", COUNT_TOTALCOMPCOUNT='" + COUNT_TOTALCOMPCOUNT + '\'' +
                ", COUNT_COMPLETEDCOMP='" + COUNT_COMPLETEDCOMP + '\'' +
                ", A='" + A + '\'' +
                ", B='" + B + '\'' +
                ", C='" + C + '\'' +
                ", D='" + D + '\'' +
                ", COUNT_PENDINGCOMP='" + COUNT_PENDINGCOMP + '\'' +
                ", PENDINGA='" + PENDINGA + '\'' +
                ", PENDINGB='" + PENDINGB + '\'' +
                ", PENDINGC='" + PENDINGC + '\'' +
                ", PENDINGD='" + PENDINGD + '\'' +
                '}';
    }
}
