package elink.suezShimla.water.crm.NoConsumerComplaint.NCRegistrationAndHistory.Model;

import com.google.gson.annotations.SerializedName;

public class NCConsumerComplaintMeterModel {
    @SerializedName("MTRM_SERIAL_NO")
    private String MTRM_SERIAL_NO;

    public NCConsumerComplaintMeterModel() {
    }

    public  NCConsumerComplaintMeterModel(String MTRM_SERIAL_NO) {
        this.MTRM_SERIAL_NO = MTRM_SERIAL_NO;
    }

    public String getMTRM_SERIAL_NO() {
        return MTRM_SERIAL_NO;
    }

    @Override
    public String toString() {
        return "NCConsumerComplaintMeterModel{" +
                "MTRM_SERIAL_NO='" + MTRM_SERIAL_NO + '\'' +
                '}';
    }
}
