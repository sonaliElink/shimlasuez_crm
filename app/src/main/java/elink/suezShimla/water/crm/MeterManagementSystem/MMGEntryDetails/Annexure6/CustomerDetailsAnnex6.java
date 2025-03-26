package elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Annexure6;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class CustomerDetailsAnnex6 extends RealmObject {

/*    @SerializedName("id")
    private int id;*/

    @SerializedName("CONSUMER_NO")
    private String CONSUMER_NO;

    @SerializedName("CONSUMER_NAME")
    private String CONSUMER_NAME;

    @SerializedName("PRIMARY_MOBILE")
    private String PRIMARY_MOBILE;

    @SerializedName("ALTERNATE_MOBILE")
    private String ALTERNATE_MOBILE;

    @SerializedName("CATEGORY_CONN")
    private String CATEGORY_CONN;

    @SerializedName("CONN_SIZE")
    private String CONN_SIZE;

    @SerializedName("FROM_NODE")
    private String FROM_NODE;

    @SerializedName("TO_NODE")
    private String TO_NODE;

    @SerializedName("HOUSE_NO")
    private String HOUSE_NO;

    @SerializedName("GIS_BID")
    private String GIS_BID;

    @SerializedName("ROAD_STREET")
    private String ROAD_STREET;

    @SerializedName("LANDMARK")
    private String LANDMARK;

    @SerializedName("PROPERTY_ASSESSMNT")
    private String PROPERTY_ASSESSMNT;

    @SerializedName("PINCODE")
    private String PINCODE;

    @SerializedName("ZONE")
    private String ZONE;

    @SerializedName("GROUP")
    private String GROUP;

    @SerializedName("WARD")
    private String WARD;

    @SerializedName("MSR")
    private int MSR;

    @SerializedName("SR")
    private int SR;

    @SerializedName("DMA")
    private int DMA;

    public CustomerDetailsAnnex6(String CONSUMER_NO, String CONSUMER_NAME, String PRIMARY_MOBILE,
                                 String ALTERNATE_MOBILE, String CATEGORY_CONN, String CONN_SIZE,
                                 String FROM_NODE, String TO_NODE, String HOUSE_NO, String GIS_BID,
                                 String ROAD_STREET, String LANDMARK, String PROPERTY_ASSESSMNT, String PINCODE,
                                 String ZONE, String GROUP, String WARD, int MSR, int SR, int DMA){
        this.CONSUMER_NO = CONSUMER_NO;
        this.CONSUMER_NAME = CONSUMER_NAME;
        this.PRIMARY_MOBILE = PRIMARY_MOBILE;
        this.ALTERNATE_MOBILE = ALTERNATE_MOBILE;
        this.CATEGORY_CONN = CATEGORY_CONN;
        this.CONN_SIZE = CONN_SIZE;
        this.FROM_NODE = FROM_NODE;
        this.TO_NODE = TO_NODE;
        this.HOUSE_NO = HOUSE_NO;
        this.GIS_BID = GIS_BID;
        this.ROAD_STREET = ROAD_STREET;
        this.LANDMARK = LANDMARK;
        this.PROPERTY_ASSESSMNT = PROPERTY_ASSESSMNT;
        this.PINCODE = PINCODE;
        this.ZONE = ZONE;
        this.GROUP = GROUP;
        this.WARD = WARD;
        this.MSR = MSR;
        this.SR = SR;
        this.DMA = DMA;
    }

    public CustomerDetailsAnnex6() {
    }

    public String getCONSUMER_NO() {
        return CONSUMER_NO;
    }

    public String getCONSUMER_NAME() {
        return CONSUMER_NAME;
    }

    public String getPRIMARY_MOBILE() {
        return PRIMARY_MOBILE;
    }

    public String getALTERNATE_MOBILE() {
        return ALTERNATE_MOBILE;
    }

    public String getCATEGORY_CONN() {
        return CATEGORY_CONN;
    }

    public String getCONN_SIZE() {
        return CONN_SIZE;
    }

    public String getFROM_NODE() {
        return FROM_NODE;
    }

    public String getTO_NODE() {
        return TO_NODE;
    }

    public String getHOUSE_NO() {
        return HOUSE_NO;
    }

    public String getGIS_BID() {
        return GIS_BID;
    }

    public String getROAD_STREET() {
        return ROAD_STREET;
    }

    public String getLANDMARK() {
        return LANDMARK;
    }

    public String getPROPERTY_ASSESSMNT() {
        return PROPERTY_ASSESSMNT;
    }

    public String getPINCODE() {
        return PINCODE;
    }

    public String getZONE() {
        return ZONE;
    }

    public String getGROUP() {
        return GROUP;
    }

    public String getWARD() {
        return WARD;
    }

    public int getMSR() {
        return MSR;
    }

    public int getSR() {
        return SR;
    }

    public int getDMA() {
        return DMA;
    }

    @Override
    public String toString() {
        return "CustomerDetailsAnnex6{" +
                "CONSUMER_NO=" + CONSUMER_NO +
                ", CONSUMER_NAME='" + CONSUMER_NAME + '\'' +
                ", PRIMARY_MOBILE='" + PRIMARY_MOBILE + '\'' +
                ", ALTERNATE_MOBILE='" + ALTERNATE_MOBILE + '\'' +
                ", CATEGORY_CONN='" + CATEGORY_CONN + '\'' +
                ", CONN_SIZE='" + CONN_SIZE + '\'' +
                ", FROM_NODE=" + FROM_NODE +
                ", TO_NODE='" + TO_NODE + '\'' +
                ", HOUSE_NO=" + HOUSE_NO +
                ", GIS_BID='" + GIS_BID + '\'' +
                ", ROAD_STREET=" + ROAD_STREET +
                ", LANDMARK='" + LANDMARK + '\'' +
                ", PROPERTY_ASSESSMNT=" + PROPERTY_ASSESSMNT +
                ", PINCODE='" + PINCODE + '\'' +
                ", ZONE='" + ZONE + '\'' +
                ", GROUP='" + GROUP + '\'' +
                ", WARD='" + WARD + '\'' +
                ", MSR='" + MSR + '\'' +
                ", SR='" + SR + '\'' +
                ", DMA='" + DMA + '\'' +
                '}';
    }
}

