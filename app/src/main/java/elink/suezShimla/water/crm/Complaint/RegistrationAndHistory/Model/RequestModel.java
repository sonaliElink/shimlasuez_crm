package elink.suezShimla.water.crm.Complaint.RegistrationAndHistory.Model;

import com.google.gson.annotations.SerializedName;

public class RequestModel {
    @SerializedName("Message")
    private String Message;

    @SerializedName("Complaint_Number")
    private String Complaint_Number;

    public RequestModel() {
    }

    public RequestModel(String message, String complaint_Number) {
        Message = message;
        Complaint_Number = complaint_Number;
    }

    public String getMessage() {
        return Message;
    }

    public String getComplaint_Number() {
        return Complaint_Number;
    }

    @Override
    public String toString() {
        return "RequestModel{" +
                "Message='" + Message + '\'' +
                ", Complaint_Number='" + Complaint_Number + '\'' +
                '}';
    }
}
