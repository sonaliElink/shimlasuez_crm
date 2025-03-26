package elink.suezShimla.water.crm.MeterManagementSystem.AcceptMMGDept.Model;

import com.google.gson.annotations.SerializedName;

public class AcceptResponseModel {



    private String div_CError;

    @SerializedName("Msg")
    private String div_Cmsg;

    private String count;

    public AcceptResponseModel() {
    }

    public AcceptResponseModel(String div_CError, String div_Cmsg, String count) {
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
        return "AcceptResponseModel{" +
                "div_CError='" + div_CError + '\'' +
                ", div_Cmsg='" + div_Cmsg + '\'' +
                ", count='" + count + '\'' +
                '}';
    }
}
