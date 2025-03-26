package elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Annexure6;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class NewMeterDetailAnnex6 extends RealmObject {
    @SerializedName("METER_MAKE")
    private String METER_MAKE;

    @SerializedName("METER_NUMBER")
    private String METER_NUMBER;

    @SerializedName("DATE_OF_INSTALL")
    private String DATE_OF_INSTALL;

    @SerializedName("SIZE_OF_METER")
    private String SIZE_OF_METER;

    @SerializedName("SEAL_NO")
    private String SEAL_NO;

    @SerializedName("INITIAL_READING")
    private String INITIAL_READING;

    @SerializedName("TYPE_OF_METER")
    private String TYPE_OF_METER;

    @SerializedName("LOCATION_OF_METER")
    private String LOCATION_OF_METER;

    @SerializedName("PROTECTED_WITH_BOX")
    private String PROTECTED_WITH_BOX;

    @SerializedName("HANDOVER_METER")
    private String HANDOVER_METER;

    public NewMeterDetailAnnex6() {
    }

    public NewMeterDetailAnnex6(String METER_MAKE, String METER_NUMBER, String DATE_OF_INSTALL, String SIZE_OF_METER,
                                String SEAL_NO, String INITIAL_READING, String TYPE_OF_METER, String LOCATION_OF_METER,
                                String PROTECTED_WITH_BOX, String HANDOVER_METER) {
        this.METER_MAKE = METER_MAKE;
        this.METER_NUMBER = METER_NUMBER;
        this.DATE_OF_INSTALL = DATE_OF_INSTALL;
        this.SIZE_OF_METER = SIZE_OF_METER;
        this.SEAL_NO = SEAL_NO;
        this.INITIAL_READING = INITIAL_READING;
        this.TYPE_OF_METER = TYPE_OF_METER;
        this.LOCATION_OF_METER = LOCATION_OF_METER;
        this.PROTECTED_WITH_BOX = PROTECTED_WITH_BOX;
        this.HANDOVER_METER = HANDOVER_METER;
    }

    public String getMETER_MAKE() {
        return METER_MAKE;
    }

    public String getMETER_NUMBER() {
        return METER_NUMBER;
    }

    public String getDATE_OF_INSTALL() {
        return DATE_OF_INSTALL;
    }

    public String getSIZE_OF_METER() {
        return SIZE_OF_METER;
    }

    public String getSEAL_NO() {
        return SEAL_NO;
    }

    public String getINITIAL_READING() {
        return INITIAL_READING;
    }

    public String getTYPE_OF_METER() {
        return TYPE_OF_METER;
    }

    public String getLOCATION_OF_METER() {
        return LOCATION_OF_METER;
    }

    public String getPROTECTED_WITH_BOX() {
        return PROTECTED_WITH_BOX;
    }

    public String getHANDOVER_METER() {
        return HANDOVER_METER;
    }

    @Override
    public String toString() {
        return "NewMeterDetailAnnex6{" +
                "METER_MAKE='" + METER_MAKE + '\'' +
                ", METER_NUMBER='" + METER_NUMBER + '\'' +
                ", DATE_OF_INSTALL='" + DATE_OF_INSTALL + '\'' +
                ", SIZE_OF_METER='" + SIZE_OF_METER + '\'' +
                ", SEAL_NO='" + SEAL_NO + '\'' +
                ", INITIAL_READING='" + INITIAL_READING + '\'' +
                ", TYPE_OF_METER=" + TYPE_OF_METER +
                ", LOCATION_OF_METER=" + LOCATION_OF_METER +
                ", PROTECTED_WITH_BOX=" + PROTECTED_WITH_BOX +
                ", HANDOVER_METER='" + HANDOVER_METER + '\'' +
                '}';
    }
}
