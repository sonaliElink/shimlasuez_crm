package elink.suezShimla.water.crm.NoConsumerComplaint.NCRegistrationAndHistory.Model;

import com.google.gson.annotations.SerializedName;

public class NCComplaintRegisterResponseModel {

    private String W_WSComno;


    private String compno;

    @SerializedName("'div_Cmsg")
    private String Complaint_Number;


    private String Service_no;   // consumer No


    private String Msg;


    private String ErrorMsg;


    private String innerHTML;    // result message

    public NCComplaintRegisterResponseModel() {
    }

    public NCComplaintRegisterResponseModel(String w_WSComno, String compno, String complaint_Number, String service_no, String msg, String innerHTML,String ErrorMsg) {
        W_WSComno = w_WSComno;
        this.compno = compno;
        Complaint_Number = complaint_Number;
        Service_no = service_no;
        Msg = msg;
        this.innerHTML = innerHTML;
        this.ErrorMsg = ErrorMsg;
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
        this.ErrorMsg = ErrorMsg;
    }

    public String getErrorMsg() {
        return ErrorMsg;
    }

    public void setErrorMsg(String ErrorMsg) {
        this.ErrorMsg = ErrorMsg;
    }

    @Override
    public String toString() {
        return "NCComplaintRegisterResponseModel{" +
                "W_WSComno='" + W_WSComno + '\'' +
                ", compno='" + compno + '\'' +
                ", Complaint_Number='" + Complaint_Number + '\'' +
                ", Service_no='" + Service_no + '\'' +
                ", Msg='" + Msg + '\'' +
                ", innerHTML='" + innerHTML + '\'' +
                ", ErrorMsg='" + ErrorMsg + '\'' +
                '}';
    }
}

