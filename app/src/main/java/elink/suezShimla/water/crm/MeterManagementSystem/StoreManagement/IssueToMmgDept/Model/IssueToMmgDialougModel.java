package elink.suezShimla.water.crm.MeterManagementSystem.StoreManagement.IssueToMmgDept.Model;

import com.google.gson.annotations.SerializedName;

public class IssueToMmgDialougModel {

    @SerializedName("SR")
    String srNo;

    @SerializedName("MDT_SERIAL_NO")
    String meterNo;

    @SerializedName("MMFG_MFGNAME")
    String makeCode;

    @SerializedName("MDT_AUTHENTICATION_DATE")
    String authDate;


    boolean isSelected;

    public IssueToMmgDialougModel() {
    }

    public IssueToMmgDialougModel(String srNo, String meterNo, String makeCode, String statusDate, boolean isSelected) {
        this.srNo = srNo;
        this.meterNo = meterNo;
        this.makeCode = makeCode;
        this.authDate = statusDate;
        this.isSelected = isSelected;
    }

    public String getSrNo() {
        return srNo;
    }

    public void setSrNo(String srNo) {
        this.srNo = srNo;
    }

    public String getMeterNo() {
        return meterNo;
    }

    public void setMeterNo(String meterNo) {
        this.meterNo = meterNo;
    }

    public String getMakeCode() {
        return makeCode;
    }

    public void setMakeCode(String makeCode) {
        this.makeCode = makeCode;
    }

    public String getStatusDate() {
        return authDate;
    }

    public void setStatusDate(String statusDate) {
        this.authDate = statusDate;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "IssueToMmgDialougModel{" +
                "srNo='" + srNo + '\'' +
                ", meterNo='" + meterNo + '\'' +
                ", makeCode='" + makeCode + '\'' +
                ", statusDate='" + authDate + '\'' +
                ", checkbox=" + isSelected +
                '}';
    }
}
