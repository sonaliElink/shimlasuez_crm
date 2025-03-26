package elink.suezShimla.water.crm.ConnectionManagement.model;

import com.google.gson.annotations.SerializedName;

public class SiteVisitRequestModel {
    @SerializedName("Message")
    private String Message;



    public SiteVisitRequestModel() {
    }

    public SiteVisitRequestModel(String message) {
        Message = message;
    }

    public String getMessage() {
        return Message;
    }


    @Override
    public String toString() {
        return "SiteVisitRequestModel{" +
                "Message='" + Message + '\'' +
                '}';
    }
}
