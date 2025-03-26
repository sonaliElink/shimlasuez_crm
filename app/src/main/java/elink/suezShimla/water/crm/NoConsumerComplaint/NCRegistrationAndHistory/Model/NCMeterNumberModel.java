package elink.suezShimla.water.crm.NoConsumerComplaint.NCRegistrationAndHistory.Model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class NCMeterNumberModel extends RealmObject {
    @SerializedName("MeterNumber")
    private String MeterNumber;

    public NCMeterNumberModel() {
    }

    public NCMeterNumberModel(String meterNumber) {
        MeterNumber = meterNumber;
    }

    public String getMeterNumber() {
        return MeterNumber;
    }

    public void setMeterNumber(String meterNumber) {
        MeterNumber = meterNumber;
    }

    @Override

    public String toString() {
        return "NCMeterNumberModel{" +
                "MeterNumber='" + MeterNumber + '\'' +
                '}';
    }
}
