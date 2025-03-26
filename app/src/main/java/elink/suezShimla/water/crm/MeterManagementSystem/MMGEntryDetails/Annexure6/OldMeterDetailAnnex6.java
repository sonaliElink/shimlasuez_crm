package elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Annexure6;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class OldMeterDetailAnnex6 extends RealmObject {
    @SerializedName("CONSUMER_NO")
    private String CONSUMER_NO;

    @SerializedName("METER_MAKE")
    private String METER_MAKE;

    @SerializedName("METER_NO")
    private String METER_NO;

    @SerializedName("SEAL_NO")
    private String SEAL_NO;

    @SerializedName("DATE_OF_INSTALL")
    private String DATE_OF_INSTALL;

    @SerializedName("SIZE_OF_METER")
    private String SIZE_OF_METER;

    @SerializedName("METER_STATUS")
    private String METER_STATUS;

    @SerializedName("TYPE_OF_METER")
    private String TYPE_OF_METER;

    @SerializedName("FINAL_STATUS")
    private int FINAL_STATUS;

    @SerializedName("OBSERVATION")
    private int OBSERVATION;

    @SerializedName("REASON_REPLACE")
    private int REASON_REPLACE;

    @SerializedName("PREVIOUS_READING")
    private String PREVIOUS_READING;

    @SerializedName("FINAL_READING")
    private String FINAL_READING;

    public OldMeterDetailAnnex6() {
    }

    public OldMeterDetailAnnex6(String CONSUMER_NO, String METER_MAKE, String METER_NO, String SEAL_NO,
                                String DATE_OF_INSTALL, String SIZE_OF_METER, String METER_STATUS,
                                String TYPE_OF_METER, int FINAL_STATUS, int OBSERVATION, int REASON_REPLACE,
                                String PREVIOUS_READING, String FINAL_READING) {
        this.CONSUMER_NO = CONSUMER_NO;
        this.METER_MAKE = METER_MAKE;
        this.METER_NO = METER_NO;
        this.SEAL_NO = SEAL_NO;
        this.DATE_OF_INSTALL = DATE_OF_INSTALL;
        this.SIZE_OF_METER = SIZE_OF_METER;
        this.METER_STATUS = METER_STATUS;
        this.TYPE_OF_METER = TYPE_OF_METER;
        this.FINAL_STATUS = FINAL_STATUS;
        this.OBSERVATION = OBSERVATION;
        this.REASON_REPLACE = REASON_REPLACE;
        this.PREVIOUS_READING = PREVIOUS_READING;
        this.FINAL_READING = FINAL_READING;
    }

    public String getCONSUMER_NO() {
        return CONSUMER_NO;
    }

    public String getMETER_MAKE() {
        return METER_MAKE;
    }

    public String getMETER_NO() {
        return METER_NO;
    }

    public String getSEAL_NO() {
        return SEAL_NO;
    }

    public String getDATE_OF_INSTALL() {
        return DATE_OF_INSTALL;
    }

    public String getSIZE_OF_METER() {
        return SIZE_OF_METER;
    }

    public String getMETER_STATUS() {
        return METER_STATUS;
    }

    public String getTYPE_OF_METER() {
        return TYPE_OF_METER;
    }

    public int getFINAL_STATUS() {
        return FINAL_STATUS;
    }

    public int getOBSERVATION() {
        return OBSERVATION;
    }

    public int getREASON_REPLACE() {
        return REASON_REPLACE;
    }

    public String getPREVIOUS_READING() {
        return PREVIOUS_READING;
    }

    public String getFINAL_READING() {
        return FINAL_READING;
    }

    @Override
    public String toString() {
        return "OldMeterDetailAnnex6{" +
                "CONSUMER_NO='" + CONSUMER_NO + '\'' +
                ", METER_MAKE='" + METER_MAKE + '\'' +
                ", METER_NO='" + METER_NO + '\'' +
                ", SEAL_NO='" + SEAL_NO + '\'' +
                ", DATE_OF_INSTALL='" + DATE_OF_INSTALL + '\'' +
                ", SIZE_OF_METER='" + SIZE_OF_METER + '\'' +
                ", METER_STATUS='" + METER_STATUS + '\'' +
                ", TYPE_OF_METER='" + TYPE_OF_METER + '\'' +
                ", FINAL_STATUS=" + FINAL_STATUS +
                ", OBSERVATION=" + OBSERVATION +
                ", REASON_REPLACE=" + REASON_REPLACE +
                ", PREVIOUS_READING='" + PREVIOUS_READING + '\'' +
                ", FINAL_READING='" + FINAL_READING + '\'' +
                '}';
    }
}
