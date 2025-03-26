package elink.suezShimla.water.crm.ConnectionRequest.Activity.ApplicationRequest.Model;

public class ApplicationStatusModel {
    private String complaintNo;
    String applicationType;
    String date;
    String applicantName;
    String status;
    String billingAddress;
    String siteAddress;
    String contactNo;
    String emailId;
    String meetingDate;
    String sourceType;
    String govtEmp;
    String remark;
    String statusDate;

    public ApplicationStatusModel() {
    }

    public ApplicationStatusModel(String complaintNo, String applicationType, String date, String applicantName, String status, String billingAddress, String siteAddress, String contactNo, String emailId, String meetingDate, String sourceType, String govtEmp, String remark, String statusDate) {
        this.complaintNo = complaintNo;
        this.applicationType = applicationType;
        this.date = date;
        this.applicantName = applicantName;
        this.status = status;
        this.billingAddress = billingAddress;
        this.siteAddress = siteAddress;
        this.contactNo = contactNo;
        this.emailId = emailId;
        this.meetingDate = meetingDate;
        this.sourceType = sourceType;
        this.govtEmp = govtEmp;
        this.remark = remark;
        this.statusDate = statusDate;
    }

    public String getComplaintNo() {
        return complaintNo;
    }

    public void setComplaintNo(String complaintNo) {
        this.complaintNo = complaintNo;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getSiteAddress() {
        return siteAddress;
    }

    public void setSiteAddress(String siteAddress) {
        this.siteAddress = siteAddress;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(String meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getGovtEmp() {
        return govtEmp;
    }

    public void setGovtEmp(String govtEmp) {
        this.govtEmp = govtEmp;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
    }

    @Override
    public String toString() {
        return "ApplicationStatusModel{" +
                "complaintNo='" + complaintNo + '\'' +
                ", applicationType='" + applicationType + '\'' +
                ", date='" + date + '\'' +
                ", applicantName='" + applicantName + '\'' +
                ", status='" + status + '\'' +
                ", billingAddress='" + billingAddress + '\'' +
                ", siteAddress='" + siteAddress + '\'' +
                ", contactNo='" + contactNo + '\'' +
                ", emailId='" + emailId + '\'' +
                ", meetingDate='" + meetingDate + '\'' +
                ", sourceType='" + sourceType + '\'' +
                ", govtEmp='" + govtEmp + '\'' +
                ", remark='" + remark + '\'' +
                ", statusDate='" + statusDate + '\'' +
                '}';
    }
}
