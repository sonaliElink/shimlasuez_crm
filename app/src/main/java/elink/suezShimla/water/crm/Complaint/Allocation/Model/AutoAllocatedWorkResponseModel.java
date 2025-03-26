package elink.suezShimla.water.crm.Complaint.Allocation.Model;

import com.google.gson.annotations.SerializedName;

public class AutoAllocatedWorkResponseModel {
    @SerializedName("div_CError")
    private String div_CError;

    @SerializedName("div_Cmsg")
    private String div_Cmsg;

    public AutoAllocatedWorkResponseModel(){}

    public AutoAllocatedWorkResponseModel(String div_CError, String div_Cmsg) {
        this.div_CError = div_CError;
        this.div_Cmsg = div_Cmsg;
    }

    public String getDiv_CError() {
        return div_CError;
    }

    public void setDiv_CError(String div_CError) {
        this.div_CError = div_CError;
    }

    public String getDiv_Cmsg() {
        return div_Cmsg;
    }

    public void setDiv_Cmsg(String div_Cmsg) {
        this.div_Cmsg = div_Cmsg;
    }

    @Override
    public String toString() {
        return "AutoAllocatedWorkResponseModel{" +
                "div_CError='" + div_CError + '\'' +
                ", div_Cmsg='" + div_Cmsg + '\'' +
                '}';
    }
}
