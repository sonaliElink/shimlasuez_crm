package elink.suezShimla.water.crm.ConnectionRequest.Activity.ApplicationRequest.Model;

public class AuthenticationAndApplicationModel {

    String ApplicationNumber;
    String ApplicationType;
    String Name;
    String Date;
    String ContactNumber;

    public AuthenticationAndApplicationModel() {
    }

    public AuthenticationAndApplicationModel(String applicationNumber, String applicationType, String name, String date, String contactNumber) {
        ApplicationNumber = applicationNumber;
        ApplicationType = applicationType;
        Name = name;
        Date = date;
        ContactNumber = contactNumber;
    }

    public String getApplicationNumber() {
        return ApplicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        ApplicationNumber = applicationNumber;
    }

    public String getApplicationType() {
        return ApplicationType;
    }

    public void setApplicationType(String applicationType) {
        ApplicationType = applicationType;
    }

    public String getApplicaintName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }

    @Override
    public String toString() {
        return "AuthenticationAndApplicationModel{" +
                "ApplicationNumber='" + ApplicationNumber + '\'' +
                ", ApplicationType='" + ApplicationType + '\'' +
                ", Name='" + Name + '\'' +
                ", Date='" + Date + '\'' +
                ", ContactNumber='" + ContactNumber + '\'' +
                '}';
    }
}
