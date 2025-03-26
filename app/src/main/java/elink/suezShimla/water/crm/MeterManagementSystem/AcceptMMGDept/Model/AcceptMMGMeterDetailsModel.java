package elink.suezShimla.water.crm.MeterManagementSystem.AcceptMMGDept.Model;

import com.google.gson.annotations.SerializedName;

public class AcceptMMGMeterDetailsModel {
    @SerializedName("SR")
    int srNo;

    @SerializedName("SERIAL_NO")
    String meterNo;

    @SerializedName("MFGNAME")
    String makeCode;

    @SerializedName("MDT_DISPATCH_DATE")
    String dispatchDate;


    boolean isChecked;


    public AcceptMMGMeterDetailsModel() {
    }

    public AcceptMMGMeterDetailsModel(int srNo, String meterNo, String makeCode, String dispatchDate, boolean isChecked) {
        this.srNo = srNo;
        this.meterNo = meterNo;
        this.makeCode = makeCode;
        this.dispatchDate = dispatchDate;
        this.isChecked = isChecked;
    }

    public int getSrNo() {
        return srNo;
    }

    public void setSrNo(int srNo) {
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

    public String getDispatchDate() {
        return dispatchDate;
    }

    public void setDispatchDate(String dispatchDate) {
        this.dispatchDate = dispatchDate;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "AcceptMMGMeterDetailsModel{" +
                "srNo=" + srNo +
                ", meterNo='" + meterNo + '\'' +
                ", makeCode='" + makeCode + '\'' +
                ", dispatchDate='" + dispatchDate + '\'' +
                ", isChecked=" + isChecked +
                '}';
    }
}
