package elink.suezShimla.water.crm.Complaint.Allocation.Model;

import com.google.gson.annotations.SerializedName;

public class AllocateWorkModel {

    @SerializedName("div_CError")
    private String div_CError;

    @SerializedName("div_Cmsg")
    private String div_Cmsg;

    @SerializedName("count")
    private String count;

    public AllocateWorkModel() {
    }

    public AllocateWorkModel(String div_CError, String div_Cmsg, String count) {
        this.div_CError = div_CError;
        this.div_Cmsg = div_Cmsg;
        this.count = count;
    }

    public String getDiv_CError() {
        return div_CError;
    }

    public String getDiv_Cmsg() {
        return div_Cmsg;
    }

    public String getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "AllocateWorkModel{" +
                "div_CError='" + div_CError + '\'' +
                ", div_Cmsg='" + div_Cmsg + '\'' +
                ", count='" + count + '\'' +
                '}';
    }
}
