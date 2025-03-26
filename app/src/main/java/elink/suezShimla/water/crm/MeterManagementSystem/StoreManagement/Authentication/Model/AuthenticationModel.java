package elink.suezShimla.water.crm.MeterManagementSystem.StoreManagement.Authentication.Model;

import com.google.gson.annotations.SerializedName;

public class AuthenticationModel {

    @SerializedName("SR")
     String srNo;

    @SerializedName("MDT_SERIAL_NO")
     String meterNo;

    @SerializedName("MDT_STATUS_DATE")
     String statusDate;

    @SerializedName("MMFG_MFGNAME")
     String makeCode;

     boolean isSelected;


    public AuthenticationModel() {
    }


    public AuthenticationModel(String srNo, String meterNo, String statusDate, String makeCode, boolean isSelected) {
        this.srNo = srNo;
        this.meterNo = meterNo;
        this.statusDate = statusDate;
        this.makeCode = makeCode;

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

    public String getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
    }

    public String getMakeCode() {
        return makeCode;
    }

    public void setMakeCode(String makeCode) {
        this.makeCode = makeCode;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "AuthenticationModel{" +
                "srNo='" + srNo + '\'' +
                ", meterNo='" + meterNo + '\'' +
                ", statusDate='" + statusDate + '\'' +
                ", makeCode='" + makeCode + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}
