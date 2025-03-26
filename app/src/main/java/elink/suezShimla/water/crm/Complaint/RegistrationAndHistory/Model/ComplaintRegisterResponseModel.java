package elink.suezShimla.water.crm.Complaint.RegistrationAndHistory.Model;

import com.google.gson.annotations.SerializedName;

public class ComplaintRegisterResponseModel {
    @SerializedName("W_WSComno")
    private String W_WSComno;

    @SerializedName("compno")
    private String compno;

    @SerializedName("Complaint_Number")
    private String Complaint_Number;

    @SerializedName("Service_no")
    private String Service_no;   // consumer No

    @SerializedName("Msg")
    private String Msg;

    @SerializedName("innerHTML")
    private String innerHTML;    // result message

    public ComplaintRegisterResponseModel() {
    }

    public ComplaintRegisterResponseModel(String w_WSComno, String compno, String complaint_Number, String service_no, String msg, String innerHTML) {
        W_WSComno = w_WSComno;
        this.compno = compno;
        Complaint_Number = complaint_Number;
        Service_no = service_no;
        Msg = msg;
        this.innerHTML = innerHTML;
    }

    public String getW_WSComno() {
        return W_WSComno;
    }

    public void setW_WSComno(String w_WSComno) {
        W_WSComno = w_WSComno;
    }

    public String getCompno() {
        return compno;
    }

    public void setCompno(String compno) {
        this.compno = compno;
    }

    public String getComplaint_Number() {
        return Complaint_Number;
    }

    public void setComplaint_Number(String complaint_Number) {
        Complaint_Number = complaint_Number;
    }

    public String getService_no() {
        return Service_no;
    }

    public void setService_no(String service_no) {
        Service_no = service_no;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public String getInnerHTML() {
        return innerHTML;
    }

    public void setInnerHTML(String innerHTML) {
        this.innerHTML = innerHTML;
    }

    @Override
    public String toString() {
        return "ComplaintRegisterResponseModel{" +
                "W_WSComno='" + W_WSComno + '\'' +
                ", compno='" + compno + '\'' +
                ", Complaint_Number='" + Complaint_Number + '\'' +
                ", Service_no='" + Service_no + '\'' +
                ", Msg='" + Msg + '\'' +
                ", innerHTML='" + innerHTML + '\'' +
                '}';
    }
}
