package elink.suezShimla.water.crm.Complaint.WorkCompletion.Model;

import com.google.gson.annotations.SerializedName;

public class BillAdjustBill {
    @SerializedName("MONTHYEAR")
    private String MONTHYEAR;

    public BillAdjustBill(String MONTHYEAR) {
        this.MONTHYEAR = MONTHYEAR;
    }

    public BillAdjustBill() {
    }

    public String getMONTHYEAR() {
        return MONTHYEAR;
    }

    public void setMONTHYEAR(String MONTHYEAR) {
        this.MONTHYEAR = MONTHYEAR;
    }

    @Override
    public String toString() {
        return "BillAdjustBill{" +
                "MONTHYEAR='" + MONTHYEAR + '\'' +
                '}';
    }
}
