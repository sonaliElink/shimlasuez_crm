package elink.suezShimla.water.crm.Complaint.WorkCompletion.Model;

import com.google.gson.annotations.SerializedName;

public class BillingAdjustmentResponceModel {

    @SerializedName("Message")
    private String Message;

    public BillingAdjustmentResponceModel(String message) {
        Message = message;
    }

    public BillingAdjustmentResponceModel() {
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    @Override
    public String toString() {
        return "BillingAdjustmentResponceModel{" +
                "Message='" + Message + '\'' +
                '}';
    }
}
