package elink.suezShimla.water.crm.InternalNetworkAudit.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CustInfo implements Serializable {

    @SerializedName("SRM_S_NAME")
    String SRM_S_NAME;

    @SerializedName("SRM_SERVICE_NO")
    String SRM_SERVICE_NO;

    @SerializedName("SRM_B_ADDRESS1")
    String SRM_B_ADDRESS1;

    @SerializedName("SRM_B_ADDRESS2")
    String SRM_B_ADDRESS2;

    @SerializedName("SRM_S_MOBILE_NO")
    String SRM_S_MOBILE_NO;

    public String getSRM_B_ADDRESS2() {
        return SRM_B_ADDRESS2;
    }

    public void setSRM_B_ADDRESS2(String SRM_B_ADDRESS2) {
        this.SRM_B_ADDRESS2 = SRM_B_ADDRESS2;
    }

    public String getSRM_SERVICE_NO() {
        return SRM_SERVICE_NO;
    }

    public void setSRM_SERVICE_NO(String SRM_SERVICE_NO) {
        this.SRM_SERVICE_NO = SRM_SERVICE_NO;
    }

    public String getSRM_B_Address2() {
        return SRM_B_ADDRESS2;
    }

    public void setSRM_B_Address2(String SRM_B_Address2) {
        this.SRM_B_ADDRESS2 = SRM_B_Address2;
    }

    public String getSRM_S_NAME() {
        return SRM_S_NAME;
    }

    public void setSRM_S_NAME(String SRM_S_NAME) {
        this.SRM_S_NAME = SRM_S_NAME;
    }

    public String getSRM_B_ADDRESS1() {
        return SRM_B_ADDRESS1;
    }

    public void setSRM_B_ADDRESS1(String SRM_B_ADDRESS1) {
        this.SRM_B_ADDRESS1 = SRM_B_ADDRESS1;
    }

    public String getSRM_S_MOBILE_NO() {
        return SRM_S_MOBILE_NO;
    }

    public void setSRM_S_MOBILE_NO(String SRM_S_MOBILE_NO) {
        this.SRM_S_MOBILE_NO = SRM_S_MOBILE_NO;
    }
}
