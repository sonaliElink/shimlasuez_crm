package elink.suezShimla.water.crm.InternalNetworkAudit.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class Complaint implements Serializable {

    @SerializedName("ComplaintInfo")
    private List<ComplaintInfo> complaintInfoList;

    @SerializedName("CustInfo")
    private List<CustInfo> custInfoList;

    @SerializedName("MeterConnected")
    private List<MeterConnected> MeterConnected;

    @SerializedName("physicaldetailsAndIntcomm")
    private List<PhysicaldetailsAndIntcomm> physicaldetailsAndIntcomm;

    @SerializedName("complaintno")
    private List<Complaintno> complaintno;

    public List<ComplaintInfo> getComplaintInfoList() {
        return complaintInfoList;
    }

    public void setComplaintInfoList(List<ComplaintInfo> complaintInfoList) {
        this.complaintInfoList = complaintInfoList;
    }

    public List<CustInfo> getCustInfoList() {
        return custInfoList;
    }

    public void setCustInfoList(List<CustInfo> custInfoList) {
        this.custInfoList = custInfoList;
    }

    public List<elink.suezShimla.water.crm.InternalNetworkAudit.Model.MeterConnected> getMeterConnected() {
        return MeterConnected;
    }

    public void setMeterConnected(List<MeterConnected> meterConnected) {
        MeterConnected = meterConnected;
    }

    public List<PhysicaldetailsAndIntcomm> getPhysicaldetailsAndIntcomm() {
        return physicaldetailsAndIntcomm;
    }

    public void setPhysicaldetailsAndIntcomm(List<PhysicaldetailsAndIntcomm> physicaldetailsAndIntcomm) {
        this.physicaldetailsAndIntcomm = physicaldetailsAndIntcomm;
    }

    public List<Complaintno> getComplaintno() {
        return complaintno;
    }

    public void setComplaintno(List<Complaintno> complaintno) {
        this.complaintno = complaintno;
    }
}
