package elink.suezShimla.water.crm.Complaint.WorkCompletion.Model;

import com.google.gson.annotations.SerializedName;

public class UpdateCompletionResponseModel {
    @SerializedName("div_CError")
    private String div_CError;

    @SerializedName("div_Cmsg")
    private String div_Cmsg;

    public UpdateCompletionResponseModel() {
    }

    public UpdateCompletionResponseModel(String div_CError, String div_Cmsg) {
        this.div_CError = div_CError;
        this.div_Cmsg = div_Cmsg;
    }

    public String getDiv_CError() {
        return div_CError;
    }

    public String getDiv_Cmsg() {
        return div_Cmsg;
    }

    @Override
    public String toString() {
        return "UpdateCompletionResponseModel{" +
                "div_CError='" + div_CError + '\'' +
                ", div_Cmsg='" + div_Cmsg + '\'' +
                '}';
    }
}
