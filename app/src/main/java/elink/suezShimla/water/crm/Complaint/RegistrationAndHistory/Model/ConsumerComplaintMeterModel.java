package elink.suezShimla.water.crm.Complaint.RegistrationAndHistory.Model;

import com.google.gson.annotations.SerializedName;

public class ConsumerComplaintMeterModel {

    @SerializedName("MTRM_SERIAL_NO")
    private String MTRM_SERIAL_NO;

    public ConsumerComplaintMeterModel() {
    }

    public ConsumerComplaintMeterModel(String MTRM_SERIAL_NO) {
        this.MTRM_SERIAL_NO = MTRM_SERIAL_NO;
    }

    public String getMTRM_SERIAL_NO() {
        return MTRM_SERIAL_NO;
    }

    @Override
    public String toString() {
        return "ConsumerComplaintMeterModel{" +
                "MTRM_SERIAL_NO='" + MTRM_SERIAL_NO + '\'' +
                '}';
    }
}
