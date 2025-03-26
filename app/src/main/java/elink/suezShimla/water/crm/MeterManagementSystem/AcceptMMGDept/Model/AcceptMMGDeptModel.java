package elink.suezShimla.water.crm.MeterManagementSystem.AcceptMMGDept.Model;

import com.google.gson.annotations.SerializedName;

public class AcceptMMGDeptModel {

    @SerializedName("SR")
    private int srNo;

    @SerializedName("DISPATCH_DATE")
    private String dispatchDate;

    @SerializedName("SERIAL_NO")
    private String meterCount;

    @SerializedName("ISSUED_PERSON")
    private String issuedToPerson;

    public AcceptMMGDeptModel() {
    }

    public AcceptMMGDeptModel(int srNo, String dispatchDate, String meterCount, String issuedToPerson) {
        this.srNo = srNo;
        this.dispatchDate = dispatchDate;
        this.meterCount = meterCount;
        this.issuedToPerson = issuedToPerson;
    }

    public int getSrNo() {
        return srNo;
    }

    public void setSrNo(int srNo) {
        this.srNo = srNo;
    }

    public String getDispatchDate() {
        return dispatchDate;
    }

    public void setDispatchDate(String dispatchDate) {
        this.dispatchDate = dispatchDate;
    }

    public String getMeterCount() {
        return meterCount;
    }

    public void setMeterCount(String meterCount) {
        this.meterCount = meterCount;
    }

    public String getIssuedToPerson() {
        return issuedToPerson;
    }

    public void setIssuedToPerson(String issuedToPerson) {
        this.issuedToPerson = issuedToPerson;
    }

    @Override
    public String toString() {
        return "AcceptMMGDeptModel{" +
                "srNo=" + srNo +
                ", dispatchDate='" + dispatchDate + '\'' +
                ", meterCount='" + meterCount + '\'' +
                ", issuedToPerson='" + issuedToPerson + '\'' +
                '}';
    }
}
