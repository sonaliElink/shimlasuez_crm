package elink.suezShimla.water.crm.InternalNetworkAudit.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ComplaintInfo implements Serializable {

    @SerializedName("COM_SERVICE_NO")
    String COM_SERVICE_NO;

    @SerializedName("COMPLAINT_REF_NO")
    String COMPLAINT_REF_NO;

    public String getCOM_SERVICE_NO() {
        return COM_SERVICE_NO;
    }

    public void setCOM_SERVICE_NO(String COM_SERVICE_NO) {
        this.COM_SERVICE_NO = COM_SERVICE_NO;
    }

    public String getCOMPLAINT_REF_NO() {
        return COMPLAINT_REF_NO;
    }

    public void setCOMPLAINT_REF_NO(String COMPLAINT_REF_NO) {
        this.COMPLAINT_REF_NO = COMPLAINT_REF_NO;
    }

    public ComplaintInfo(String COM_SERVICE_NO, String COMPLAINT_REF_NO) {
        this.COM_SERVICE_NO = COM_SERVICE_NO;
        this.COMPLAINT_REF_NO = COMPLAINT_REF_NO;
    }

    @Override
    public String toString() {
        return "ComplaintInfo{" +
                "COM_SERVICE_NO='" + COM_SERVICE_NO + '\'' +
                ", COMPLAINT_REF_NO='" + COMPLAINT_REF_NO + '\'' +
                '}';
    }
}
