package elink.suezShimla.water.crm.MeterManagementSystem.StoreManagement.IssueToMmgDept.Model;

import com.google.gson.annotations.SerializedName;

public class IssueToMmgModel {
    @SerializedName("SR")
    String srNo;

    @SerializedName("MDT_AUTHENTICATION_DATE")
    String authDate;

    @SerializedName("MDT_SERIAL_NO")
   int availableStock;

    public IssueToMmgModel() {
    }

    public IssueToMmgModel(String srNo, String authDate, int availableStock) {
        this.srNo = srNo;
        this.authDate = authDate;
        this.availableStock = availableStock;
    }

    public String getSrNo() {
        return srNo;
    }

    public void setSrNo(String srNo) {
        this.srNo = srNo;
    }

    public String getAuthDate() {
        return authDate;
    }

    public void setAuthDate(String authDate) {
        this.authDate = authDate;
    }

    public int getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(int availableStock) {
        this.availableStock = availableStock;
    }

    @Override
    public String toString() {
        return "IssueToMmgModel{" +
                "srNo='" + srNo + '\'' +
                ", authDate='" + authDate + '\'' +
                ", availableStock='" + availableStock + '\'' +
                '}';
    }
}
