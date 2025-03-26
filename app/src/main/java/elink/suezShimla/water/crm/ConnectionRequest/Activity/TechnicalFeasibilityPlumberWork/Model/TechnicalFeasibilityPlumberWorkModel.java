package elink.suezShimla.water.crm.ConnectionRequest.Activity.TechnicalFeasibilityPlumberWork.Model;

public class TechnicalFeasibilityPlumberWorkModel {

    String RequestNo;
    String Date;
    String ApplicantName;
    String ContactNo;
    String SiteAddress;

    public TechnicalFeasibilityPlumberWorkModel() {
    }

    public TechnicalFeasibilityPlumberWorkModel(String requestNo, String date, String applicantName, String contactNo, String siteAddress) {
        RequestNo = requestNo;
        Date = date;
        ApplicantName = applicantName;
        ContactNo = contactNo;
        SiteAddress = siteAddress;
    }

    public String getRequestNo() {
        return RequestNo;
    }

    public void setRequestNo(String requestNo) {
        RequestNo = requestNo;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getApplicantName() {
        return ApplicantName;
    }

    public void setApplicantName(String applicantName) {
        ApplicantName = applicantName;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public String getSiteAddress() {
        return SiteAddress;
    }

    public void setSiteAddress(String siteAddress) {
        SiteAddress = siteAddress;
    }

    @Override
    public String toString() {
        return "TechnicalFeasibilityPlumberWorkModel{" +
                "RequestNo='" + RequestNo + '\'' +
                ", Date='" + Date + '\'' +
                ", ApplicantName='" + ApplicantName + '\'' +
                ", ContactNo='" + ContactNo + '\'' +
                ", SiteAddress='" + SiteAddress + '\'' +
                '}';
    }
}
