package elink.suezShimla.water.crm.Complaint.WorkCompletion.Model;

public class WorkCompletionModel {
    private String complaintNo;
    private String consumerNo;
    private String complaintWorkAlloDateTime;
    private String complaintDateTime;
    private String complaintType;
    private String consumerName;
    private String contactNumber;
    private String consumerAddress;


    public WorkCompletionModel() {
    }

    public WorkCompletionModel(String complaintNo, String consumerNo, String complaintWorkAlloDateTime, String complaintDateTime, String complaintType, String consumerName, String contactNumber, String consumerAddress, String VIP) {
        this.complaintNo = complaintNo;
        this.consumerNo = consumerNo;
        this.complaintWorkAlloDateTime = complaintWorkAlloDateTime;
        this.complaintDateTime = complaintDateTime;
        this.complaintType = complaintType;
        this.consumerName = consumerName;
        this.contactNumber = contactNumber;
        this.consumerAddress = consumerAddress;
    }

    public String getComplaintNo() {
        return complaintNo;
    }

    public void setComplaintNo(String complaintNo) {
        this.complaintNo = complaintNo;
    }

    public String getConsumerNo() {
        return consumerNo;
    }

    public void setConsumerNo(String consumerNo) {
        this.consumerNo = consumerNo;
    }

    public String getComplaintWorkAlloDateTime() {
        return complaintWorkAlloDateTime;
    }

    public void setComplaintWorkAlloDateTime(String complaintWorkAlloDateTime) {
        this.complaintWorkAlloDateTime = complaintWorkAlloDateTime;
    }

    public String getComplaintDateTime() {
        return complaintDateTime;
    }

    public void setComplaintDateTime(String complaintDateTime) {
        this.complaintDateTime = complaintDateTime;
    }

    public String getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(String complaintType) {
        this.complaintType = complaintType;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getConsumerAddress() {
        return consumerAddress;
    }

    public void setConsumerAddress(String consumerAddress) {
        this.consumerAddress = consumerAddress;
    }



    @Override
    public String toString() {
        return "WorkCompletionModel{" +
                "complaintNo='" + complaintNo + '\'' +
                ", consumerNo='" + consumerNo + '\'' +
                ", complaintWorkAlloDateTime='" + complaintWorkAlloDateTime + '\'' +
                ", complaintDateTime='" + complaintDateTime + '\'' +
                ", complaintType='" + complaintType + '\'' +
                ", consumerName='" + consumerName + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", consumerAddress='" + consumerAddress + '\'' +
                '}';
    }
}
