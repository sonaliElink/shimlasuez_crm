package elink.suezShimla.water.crm.DisconnectionAndDismantling.CaptureReason.Model;

public class CaptureReasonModel {
    String complaintNumber;
    String name;
    String number;
    String address;

    public CaptureReasonModel() {
    }

    public CaptureReasonModel(String complaintNumber, String name, String number, String address) {
        this.complaintNumber = complaintNumber;
        this.name = name;
        this.number = number;
        this.address = address;
    }

    public String getComplaintNumber() {
        return complaintNumber;
    }

    public void setComplaintNumber(String complaintNumber) {
        this.complaintNumber = complaintNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "CaptureReasonModel{" +
                "complaintNumber='" + complaintNumber + '\'' +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
