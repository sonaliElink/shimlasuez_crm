package elink.suezShimla.water.crm.NoConsumerComplaint.NCAllocation.Model;

import com.google.gson.annotations.SerializedName;

public class NCWorkAllocateModel {
    @SerializedName("comseq")
    private String comSeq;

    @SerializedName("w_consumer")
    private String wConsumer;

    @SerializedName("W_Wadt")
    private String wWadt;

    @SerializedName("EmpCode")
    private String empCode;

    @SerializedName("IP_Address")
    private String ipAddress;

    @SerializedName("Remarks")
    private String remarks;

    @SerializedName("empid")
    private String empId;

    @SerializedName("w_Com_Seq")
    private String wComSeq;

    @SerializedName("ComplaintType")
    private String complaintType;

    @SerializedName("ComplaintSubType")
    private String complaintSubType;

    public NCWorkAllocateModel(){}

    public NCWorkAllocateModel(String comSeq, String wConsumer, String wWadt, String empCode, String ipAddress, String remarks, String empId, String wComSeq, String complaintType, String complaintSubType) {
        this.comSeq = comSeq;
        this.wConsumer = wConsumer;
        this.wWadt = wWadt;
        this.empCode = empCode;
        this.ipAddress = ipAddress;
        this.remarks = remarks;
        this.empId = empId;
        this.wComSeq = wComSeq;
        this.complaintType = complaintType;
        this.complaintSubType = complaintSubType;
    }

    public String getComSeq() {
        return comSeq;
    }

    public void setComSeq(String comSeq) {
        this.comSeq = comSeq;
    }

    public String getwConsumer() {
        return wConsumer;
    }

    public void setwConsumer(String wConsumer) {
        this.wConsumer = wConsumer;
    }

    public String getwWadt() {
        return wWadt;
    }

    public void setwWadt(String wWadt) {
        this.wWadt = wWadt;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getwComSeq() {
        return wComSeq;
    }

    public void setwComSeq(String wComSeq) {
        this.wComSeq = wComSeq;
    }

    public String getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(String complaintType) {
        this.complaintType = complaintType;
    }

    public String getComplaintSubType() {
        return complaintSubType;
    }

    public void setComplaintSubType(String complaintSubType) {
        this.complaintSubType = complaintSubType;
    }

    @Override
    public String toString() {
        return "NCWorkAllocateModel{" +
                "comSeq='" + comSeq + '\'' +
                ", wConsumer='" + wConsumer + '\'' +
                ", wWadt='" + wWadt + '\'' +
                ", empCode='" + empCode + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", remarks='" + remarks + '\'' +
                ", empId='" + empId + '\'' +
                ", wComSeq='" + wComSeq + '\'' +
                ", complaintType='" + complaintType + '\'' +
                ", complaintSubType='" + complaintSubType + '\'' +
                '}';
    }
}
