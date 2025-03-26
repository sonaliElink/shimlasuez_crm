package elink.suezShimla.water.crm.NoConsumerComplaint.NCWorkCompletion.Model;

import com.google.gson.annotations.SerializedName;

public class NCUpdateWorkResponseModel {
    @SerializedName("ErrMsg")
    private String div_CError;

    @SerializedName("div_Cmsg")
    private String div_Cmsg;

    @SerializedName("Success")
    private String Success;

    public NCUpdateWorkResponseModel() {
    }

    public NCUpdateWorkResponseModel(String div_CError, String div_Cmsg, String Success) {
        this.div_CError = div_CError;
        this.div_Cmsg = div_Cmsg;
        this.Success = Success;
    }

    public String getDiv_CError() {
        return div_CError;
    }

    public String getDiv_Cmsg() {
        return div_Cmsg;
    }

    public String getSuccess() {
        return Success;
    }

    public void setSuccess(String success) {
        Success = success;
    }

    @Override
    public String toString() {
        return "NCUpdateWorkResponseModel{" +
                "div_CError='" + div_CError + '\'' +
                ", div_Cmsg='" + div_Cmsg + '\'' +
                ", Success='" + Success + '\'' +
                '}';
    }
}
