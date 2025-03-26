package elink.suezShimla.water.crm.Complaint.RegistrationAndHistory.Model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class MeterNumberModel extends RealmObject {

    @SerializedName("MeterNumber")
    private String MeterNumber;

    public MeterNumberModel() {
    }

    public MeterNumberModel(String meterNumber) {
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
        return "MeterNumberModel{" +
                "MeterNumber='" + MeterNumber + '\'' +
                '}';
    }
}
