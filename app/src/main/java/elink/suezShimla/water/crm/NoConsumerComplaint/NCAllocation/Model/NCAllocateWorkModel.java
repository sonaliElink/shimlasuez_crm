package elink.suezShimla.water.crm.NoConsumerComplaint.NCAllocation.Model;

import com.google.gson.annotations.SerializedName;

public class NCAllocateWorkModel {

    @SerializedName("ErrMsg")
    private String div_CError;

    @SerializedName("Success")
    private String div_Cmsg;

    @SerializedName("Msg")
    private String Msg;

    public NCAllocateWorkModel() {
    }

    public NCAllocateWorkModel(String div_CError, String div_Cmsg, String Msg) {
        this.div_CError = div_CError;
        this.div_Cmsg = div_Cmsg;
        this.Msg = Msg;
    }

    public String getDiv_CError() {
        return div_CError;
    }

    public String getDiv_Cmsg() {
        return div_Cmsg;
    }

    public String getMsg() {
        return Msg;
    }

    @Override
    public String toString() {
        return "NCAllocateWorkModel{" +
                "div_CError='" + div_CError + '\'' +
                ", div_Cmsg='" + div_Cmsg + '\'' +
                ", Msg='" + Msg + '\'' +
                '}';
    }
}
