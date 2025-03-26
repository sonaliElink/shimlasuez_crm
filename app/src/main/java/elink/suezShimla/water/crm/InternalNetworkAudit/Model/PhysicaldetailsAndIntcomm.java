package elink.suezShimla.water.crm.InternalNetworkAudit.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

public class PhysicaldetailsAndIntcomm extends RealmObject implements Serializable {

    @SerializedName("MTRTR_MTRRECEIVEDBY")
    String MTRTR_MTRRECEIVEDBY;

    @SerializedName("MTRTR_MTRRCVDINMTLONDATE")
    String MTRTR_MTRRCVDINMTLONDATE;

    @SerializedName("MIA_METER_READING")
    String MIA_METER_READING;

    @SerializedName("MIA_DETAILSOFOBSRV")
    String MIA_DETAILSOFOBSRV;

    @SerializedName("MIA_RETO_CUSTOMER")
    String MIA_RETO_CUSTOMER;

    @SerializedName("MIA_OHTANK_QTY")
    int MIA_OHTANK_QTY;

    @SerializedName("MIA_GROUNDTANK_QTY")
    int MIA_GROUNDTANK_QTY;

    @SerializedName("MIA_UGSUMP_QTY")
    int MIA_UGSUMP_QTY;

    @SerializedName("MIA_OHTANK_CAPACITY")
    int MIA_OHTANK_CAPACITY;

    @SerializedName("MIA_GROUNDTANK_CAPACITY")
    int MIA_GROUNDTANK_CAPACITY;

    @SerializedName("MIA_UGSUMP_CAPACITY")
    int MIA_UGSUMP_CAPACITY;

    @SerializedName("MIA_METERSEAL_STATUS")
    String MIA_METERSEAL_STATUS;

    @SerializedName("MIA_OHTANK_CONDITION")
    String MIA_OHTANK_CONDITION;

    @SerializedName("MIA_SUCTIONPUMP_CONDITION")
    String MIA_SUCTIONPUMP_CONDITION;

    @SerializedName("MIA_GROUNDTANK_CONDITION")
    String MIA_GROUNDTANK_CONDITION;

    @SerializedName("MIA_UGSUMP_CONDITION")
    String MIA_UGSUMP_CONDITION;

    @SerializedName("MIA_LEAKFOUND_AFTERMETER")
    String MIA_LEAKFOUND_AFTERMETER;

    @SerializedName("MIA_LEAKFOUND_INHOUSE")
    String MIA_LEAKFOUND_INHOUSE;

    @SerializedName("MIA_FLOATINGVALVE_AVLBL")
    String MIA_FLOATINGVALVE_AVLBL;

    @SerializedName("MIA_METER_STATUS_ID")
    String MIA_METER_STATUS_ID;

    @SerializedName("MIA_TEST_RESULTS")
    String MIA_TEST_RESULTS;

    @SerializedName("MIA_ISMETERTESTED")
    String MIA_ISMETERTESTED;

    @SerializedName("MIA_METER_NO")
    String MIA_METER_NO;
    @SerializedName("MTRTR_TESTINGFEERECEIPTNO")
    String MTRTR_TESTINGFEERECEIPTNO;

    String SEALNO;

    public String getSEALNO() {
        return SEALNO;
    }

    public void setSEALNO(String SEALNO) {
        this.SEALNO = SEALNO;
    }

    public String getMTRTR_TESTINGFEERECEIPTNO() {
        return MTRTR_TESTINGFEERECEIPTNO;
    }

    public void setMTRTR_TESTINGFEERECEIPTNO(String MTRTR_TESTINGFEERECEIPTNO) {
        this.MTRTR_TESTINGFEERECEIPTNO = MTRTR_TESTINGFEERECEIPTNO;
    }

    public String getMIA_METER_NO() {
        return MIA_METER_NO;
    }

    public void setMIA_METER_NO(String MIA_METER_NO) {
        this.MIA_METER_NO = MIA_METER_NO;
    }

    public String getMIA_ISMETERTESTED() {
        return MIA_ISMETERTESTED;
    }

    public void setMIA_ISMETERTESTED(String MIA_ISMETERTESTED) {
        this.MIA_ISMETERTESTED = MIA_ISMETERTESTED;
    }

    public String getMTRTR_MTRRECEIVEDBY() {
        return MTRTR_MTRRECEIVEDBY;
    }

    public void setMTRTR_MTRRECEIVEDBY(String MTRTR_MTRRECEIVEDBY) {
        this.MTRTR_MTRRECEIVEDBY = MTRTR_MTRRECEIVEDBY;
    }

    public String getMTRTR_MTRRCVDINMTLONDATE() {
        return MTRTR_MTRRCVDINMTLONDATE;
    }

    public void setMTRTR_MTRRCVDINMTLONDATE(String MTRTR_MTRRCVDINMTLONDATE) {
        this.MTRTR_MTRRCVDINMTLONDATE = MTRTR_MTRRCVDINMTLONDATE;
    }

    public String getMIA_METER_READING() {
        return MIA_METER_READING;
    }

    public void setMIA_METER_READING(String MIA_METER_READING) {
        this.MIA_METER_READING = MIA_METER_READING;
    }

    public String getMIA_DETAILSOFOBSRV() {
        return MIA_DETAILSOFOBSRV;
    }

    public void setMIA_DETAILSOFOBSRV(String MIA_DETAILSOFOBSRV) {
        this.MIA_DETAILSOFOBSRV = MIA_DETAILSOFOBSRV;
    }

    public String getMIA_RETO_CUSTOMER() {
        return MIA_RETO_CUSTOMER;
    }

    public void setMIA_RETO_CUSTOMER(String MIA_RETO_CUSTOMER) {
        this.MIA_RETO_CUSTOMER = MIA_RETO_CUSTOMER;
    }

    public int getMIA_OHTANK_QTY() {
        return MIA_OHTANK_QTY;
    }

    public void setMIA_OHTANK_QTY(int MIA_OHTANK_QTY) {
        this.MIA_OHTANK_QTY = MIA_OHTANK_QTY;
    }

    public int getMIA_GROUNDTANK_QTY() {
        return MIA_GROUNDTANK_QTY;
    }

    public void setMIA_GROUNDTANK_QTY(int MIA_GROUNDTANK_QTY) {
        this.MIA_GROUNDTANK_QTY = MIA_GROUNDTANK_QTY;
    }

    public int getMIA_UGSUMP_QTY() {
        return MIA_UGSUMP_QTY;
    }

    public void setMIA_UGSUMP_QTY(int MIA_UGSUMP_QTY) {
        this.MIA_UGSUMP_QTY = MIA_UGSUMP_QTY;
    }

    public int getMIA_OHTANK_CAPACITY() {
        return MIA_OHTANK_CAPACITY;
    }

    public void setMIA_OHTANK_CAPACITY(int MIA_OHTANK_CAPACITY) {
        this.MIA_OHTANK_CAPACITY = MIA_OHTANK_CAPACITY;
    }

    public int getMIA_GROUNDTANK_CAPACITY() {
        return MIA_GROUNDTANK_CAPACITY;
    }

    public void setMIA_GROUNDTANK_CAPACITY(int MIA_GROUNDTANK_CAPACITY) {
        this.MIA_GROUNDTANK_CAPACITY = MIA_GROUNDTANK_CAPACITY;
    }

    public int getMIA_UGSUMP_CAPACITY() {
        return MIA_UGSUMP_CAPACITY;
    }

    public void setMIA_UGSUMP_CAPACITY(int MIA_UGSUMP_CAPACITY) {
        this.MIA_UGSUMP_CAPACITY = MIA_UGSUMP_CAPACITY;
    }

    public String getMIA_METERSEAL_STATUS() {
        return MIA_METERSEAL_STATUS;
    }

    public void setMIA_METERSEAL_STATUS(String MIA_METERSEAL_STATUS) {
        this.MIA_METERSEAL_STATUS = MIA_METERSEAL_STATUS;
    }

    public String getMIA_OHTANK_CONDITION() {
        return MIA_OHTANK_CONDITION;
    }

    public void setMIA_OHTANK_CONDITION(String MIA_OHTANK_CONDITION) {
        this.MIA_OHTANK_CONDITION = MIA_OHTANK_CONDITION;
    }

    public String getMIA_SUCTIONPUMP_CONDITION() {
        return MIA_SUCTIONPUMP_CONDITION;
    }

    public void setMIA_SUCTIONPUMP_CONDITION(String MIA_SUCTIONPUMP_CONDITION) {
        this.MIA_SUCTIONPUMP_CONDITION = MIA_SUCTIONPUMP_CONDITION;
    }

    public String getMIA_GROUNDTANK_CONDITION() {
        return MIA_GROUNDTANK_CONDITION;
    }

    public void setMIA_GROUNDTANK_CONDITION(String MIA_GROUNDTANK_CONDITION) {
        this.MIA_GROUNDTANK_CONDITION = MIA_GROUNDTANK_CONDITION;
    }

    public String getMIA_UGSUMP_CONDITION() {
        return MIA_UGSUMP_CONDITION;
    }

    public void setMIA_UGSUMP_CONDITION(String MIA_UGSUMP_CONDITION) {
        this.MIA_UGSUMP_CONDITION = MIA_UGSUMP_CONDITION;
    }

    public String getMIA_LEAKFOUND_AFTERMETER() {
        return MIA_LEAKFOUND_AFTERMETER;
    }

    public void setMIA_LEAKFOUND_AFTERMETER(String MIA_LEAKFOUND_AFTERMETER) {
        this.MIA_LEAKFOUND_AFTERMETER = MIA_LEAKFOUND_AFTERMETER;
    }

    public String getMIA_LEAKFOUND_INHOUSE() {
        return MIA_LEAKFOUND_INHOUSE;
    }

    public void setMIA_LEAKFOUND_INHOUSE(String MIA_LEAKFOUND_INHOUSE) {
        this.MIA_LEAKFOUND_INHOUSE = MIA_LEAKFOUND_INHOUSE;
    }

    public String getMIA_FLOATINGVALVE_AVLBL() {
        return MIA_FLOATINGVALVE_AVLBL;
    }

    public void setMIA_FLOATINGVALVE_AVLBL(String MIA_FLOATINGVALVE_AVLBL) {
        this.MIA_FLOATINGVALVE_AVLBL = MIA_FLOATINGVALVE_AVLBL;
    }

    public String getMIA_METER_STATUS_ID() {
        return MIA_METER_STATUS_ID;
    }

    public void setMIA_METER_STATUS_ID(String MIA_METER_STATUS_ID) {
        this.MIA_METER_STATUS_ID = MIA_METER_STATUS_ID;
    }

    public String getMIA_TEST_RESULTS() {
        return MIA_TEST_RESULTS;
    }

    public void setMIA_TEST_RESULTS(String MIA_TEST_RESULTS) {
        this.MIA_TEST_RESULTS = MIA_TEST_RESULTS;
    }

    public PhysicaldetailsAndIntcomm(String MIA_METER_NO,String SEALNO,String MIA_METER_READING, String MIA_METERSEAL_STATUS, String MIA_OHTANK_CONDITION, String MIA_SUCTIONPUMP_CONDITION, String MIA_GROUNDTANK_CONDITION, String MIA_UGSUMP_CONDITION, String MIA_LEAKFOUND_AFTERMETER, String MIA_LEAKFOUND_INHOUSE, String MIA_FLOATINGVALVE_AVLBL,
                                     String MIA_METER_STATUS_ID,String MIA_DETAILSOFOBSRV,String MIA_RETO_CUSTOMER) {
        this.MIA_METER_NO = MIA_METER_NO;
        this.SEALNO = SEALNO;
        this.MIA_METER_READING = MIA_METER_READING;
        this.MIA_METERSEAL_STATUS = MIA_METERSEAL_STATUS;
        this.MIA_OHTANK_CONDITION = MIA_OHTANK_CONDITION;
        this.MIA_SUCTIONPUMP_CONDITION = MIA_SUCTIONPUMP_CONDITION;
        this.MIA_GROUNDTANK_CONDITION = MIA_GROUNDTANK_CONDITION;
        this.MIA_UGSUMP_CONDITION = MIA_UGSUMP_CONDITION;
        this.MIA_LEAKFOUND_AFTERMETER = MIA_LEAKFOUND_AFTERMETER;
        this.MIA_LEAKFOUND_INHOUSE = MIA_LEAKFOUND_INHOUSE;
        this.MIA_FLOATINGVALVE_AVLBL = MIA_FLOATINGVALVE_AVLBL;
        this.MIA_METER_STATUS_ID = MIA_METER_STATUS_ID;
        this.MIA_DETAILSOFOBSRV = MIA_DETAILSOFOBSRV;
        this.MIA_RETO_CUSTOMER = MIA_RETO_CUSTOMER;
    }

    public PhysicaldetailsAndIntcomm() {
    }

    @Override
    public String toString() {
        return "PhysicaldetailsAndIntcomm{" +
                "MIA_METER_NO='" + MIA_METER_NO + '\'' +
                ", SEALNO='" + SEALNO + '\'' +
                ", MIA_METER_READING='" + MIA_METER_READING + '\'' +
                ", MIA_METERSEAL_STATUS='" + MIA_METERSEAL_STATUS + '\'' +
                ", MIA_OHTANK_CONDITION='" + MIA_OHTANK_CONDITION + '\''+
                ", MIA_SUCTIONPUMP_CONDITION='" + MIA_SUCTIONPUMP_CONDITION + '\'' +
                ", MIA_GROUNDTANK_CONDITION='" + MIA_GROUNDTANK_CONDITION + '\'' +
                ", MIA_UGSUMP_CONDITION='" + MIA_UGSUMP_CONDITION + '\'' +
                ", MIA_LEAKFOUND_AFTERMETER='" + MIA_LEAKFOUND_AFTERMETER + '\'' +
                ", MIA_LEAKFOUND_INHOUSE='" + MIA_LEAKFOUND_INHOUSE + '\'' +
                ", MIA_FLOATINGVALVE_AVLBL='" + MIA_FLOATINGVALVE_AVLBL + '\'' +
                ", MIA_METER_STATUS_ID='" + MIA_METER_STATUS_ID + '\'' +
                ", MIA_DETAILSOFOBSRV='" + MIA_DETAILSOFOBSRV + '\'' +
                ", MIA_RETO_CUSTOMER='" + MIA_RETO_CUSTOMER + '\'' +
                '}';
    }
}
