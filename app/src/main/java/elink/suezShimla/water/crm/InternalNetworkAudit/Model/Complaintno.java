package elink.suezShimla.water.crm.InternalNetworkAudit.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Complaintno implements Serializable {

    @SerializedName("PM_RCPTNO")
    String PM_RCPTNO;

    public String getPM_RCPTNO() {
        return PM_RCPTNO;
    }

    public void setPM_RCPTNO(String PM_RCPTNO) {
        this.PM_RCPTNO = PM_RCPTNO;
    }
}
