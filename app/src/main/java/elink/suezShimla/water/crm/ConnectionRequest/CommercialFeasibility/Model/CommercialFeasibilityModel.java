package elink.suezShimla.water.crm.ConnectionRequest.CommercialFeasibility.Model;

public class CommercialFeasibilityModel {
    private String applicationNo;
    private String applicationDate;
    private String complaintType;
    private String applicantName;
    private String contactNo;
    private String address;

    public CommercialFeasibilityModel(){}

    public CommercialFeasibilityModel(String applicationNo, String applicationDate, String complaintType, String applicantName, String contactNo, String address) {
        this.applicationNo = applicationNo;
        this.applicationDate = applicationDate;
        this.complaintType = complaintType;
        this.applicantName = applicantName;
        this.contactNo = contactNo;
        this.address = address;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(String complaintType) {
        this.complaintType = complaintType;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "CommercialFeasibilityModel{" +
                "applicationNo='" + applicationNo + '\'' +
                ", applicationDate='" + applicationDate + '\'' +
                ", complaintType='" + complaintType + '\'' +
                ", applicantName='" + applicantName + '\'' +
                ", contactNo='" + contactNo + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
