package elink.suezShimla.water.crm.DisconnectionAndDismantling.ReConnectionWorkClosing.Model;

public class ReConnectionWorkClosingModel {
    private String consumerNo;
    private String applicationNo;
    private String applicationDate;
    private String applicantName;
    private String applicantMobileNo;
    private String status;

    public ReConnectionWorkClosingModel(){}

    public ReConnectionWorkClosingModel(String consumerNo, String applicationNo, String applicationDate, String applicantName, String applicantMobileNo, String status) {
        this.consumerNo = consumerNo;
        this.applicationNo = applicationNo;
        this.applicationDate = applicationDate;
        this.applicantName = applicantName;
        this.applicantMobileNo = applicantMobileNo;
        this.status = status;
    }

    public String getConsumerNo() {
        return consumerNo;
    }

    public void setConsumerNo(String consumerNo) {
        this.consumerNo = consumerNo;
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

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApplicantMobileNo() {
        return applicantMobileNo;
    }

    public void setApplicantMobileNo(String applicantMobileNo) {
        this.applicantMobileNo = applicantMobileNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ReConnectionWorkClosingModel{" +
                "consumerNo='" + consumerNo + '\'' +
                ", applicationNo='" + applicationNo + '\'' +
                ", applicationDate='" + applicationDate + '\'' +
                ", applicantName='" + applicantName + '\'' +
                ", applicantMobileNo='" + applicantMobileNo + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

