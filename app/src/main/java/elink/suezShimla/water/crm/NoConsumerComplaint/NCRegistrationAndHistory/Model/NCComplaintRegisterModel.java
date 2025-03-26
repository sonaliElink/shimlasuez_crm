package elink.suezShimla.water.crm.NoConsumerComplaint.NCRegistrationAndHistory.Model;

import java.util.List;


public class NCComplaintRegisterModel {
    private List<NCComplaintRegisterStringModel> data;
    private String complaintType;
    private String informedDate;
    private String complaintSubType;
    private String remark;
    private String meterNo;
    private String yearMonth;
    private String hdnYearMonth;
    private String hdnBa;
    private String namePrefix;
    private String firstName;
    private String middleName;
    private String lastName;
    private String complaintOrigin;
    private String complaintFor;
    private String oldComplaintNo;
    private String sourceType;
    private String sourceTypeValue;
    private String mobileNo;
    private String priority;
    private String empCode;
    private String IpAddress;

    public NCComplaintRegisterModel() {
    }

    public NCComplaintRegisterModel(List<NCComplaintRegisterStringModel> data, String complaintType, String informedDate, String complaintSubType, String remark, String meterNo, String yearMonth, String hdnYearMonth, String hdnBa, String namePrefix, String firstName, String middleName, String lastName, String complaintOrigin, String complaintFor, String oldComplaintNo, String sourceType, String sourceTypeValue, String mobileNo, String priority, String empCode, String ipAddress) {
        this.data = data;
        this.complaintType = complaintType;
        this.informedDate = informedDate;
        this.complaintSubType = complaintSubType;
        this.remark = remark;
        this.meterNo = meterNo;
        this.yearMonth = yearMonth;
        this.hdnYearMonth = hdnYearMonth;
        this.hdnBa = hdnBa;
        this.namePrefix = namePrefix;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.complaintOrigin = complaintOrigin;
        this.complaintFor = complaintFor;
        this.oldComplaintNo = oldComplaintNo;
        this.sourceType = sourceType;
        this.sourceTypeValue = sourceTypeValue;
        this.mobileNo = mobileNo;
        this.priority = priority;
        this.empCode = empCode;
        IpAddress = ipAddress;
    }

    public List<NCComplaintRegisterStringModel> getData() {
        return data;
    }

    public void setData(List<NCComplaintRegisterStringModel> data) {
        this.data = data;
    }

    public String getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(String complaintType) {
        this.complaintType = complaintType;
    }

    public String getInformedDate() {
        return informedDate;
    }

    public void setInformedDate(String informedDate) {
        this.informedDate = informedDate;
    }

    public String getComplaintSubType() {
        return complaintSubType;
    }

    public void setComplaintSubType(String complaintSubType) {
        this.complaintSubType = complaintSubType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMeterNo() {
        return meterNo;
    }

    public void setMeterNo(String meterNo) {
        this.meterNo = meterNo;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public String getHdnYearMonth() {
        return hdnYearMonth;
    }

    public void setHdnYearMonth(String hdnYearMonth) {
        this.hdnYearMonth = hdnYearMonth;
    }

    public String getHdnBa() {
        return hdnBa;
    }

    public void setHdnBa(String hdnBa) {
        this.hdnBa = hdnBa;
    }

    public String getNamePrefix() {
        return namePrefix;
    }

    public void setNamePrefix(String namePrefix) {
        this.namePrefix = namePrefix;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getComplaintOrigin() {
        return complaintOrigin;
    }

    public void setComplaintOrigin(String complaintOrigin) {
        this.complaintOrigin = complaintOrigin;
    }

    public String getComplaintFor() {
        return complaintFor;
    }

    public void setComplaintFor(String complaintFor) {
        this.complaintFor = complaintFor;
    }

    public String getOldComplaintNo() {
        return oldComplaintNo;
    }

    public void setOldComplaintNo(String oldComplaintNo) {
        this.oldComplaintNo = oldComplaintNo;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceTypeValue() {
        return sourceTypeValue;
    }

    public void setSourceTypeValue(String sourceTypeValue) {
        this.sourceTypeValue = sourceTypeValue;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getIpAddress() {
        return IpAddress;
    }

    public void setIpAddress(String ipAddress) {
        IpAddress = ipAddress;
    }

    @Override
    public String toString() {
        return "NCComplaintRegisterModel{" +
                "data=" + data +
                ", complaintType='" + complaintType + '\'' +
                ", informedDate='" + informedDate + '\'' +
                ", complaintSubType='" + complaintSubType + '\'' +
                ", remark='" + remark + '\'' +
                ", meterNo='" + meterNo + '\'' +
                ", yearMonth='" + yearMonth + '\'' +
                ", hdnYearMonth='" + hdnYearMonth + '\'' +
                ", hdnBa='" + hdnBa + '\'' +
                ", namePrefix='" + namePrefix + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", complaintOrigin='" + complaintOrigin + '\'' +
                ", complaintFor='" + complaintFor + '\'' +
                ", oldComplaintNo='" + oldComplaintNo + '\'' +
                ", sourceType='" + sourceType + '\'' +
                ", sourceTypeValue='" + sourceTypeValue + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", priority='" + priority + '\'' +
                ", empCode='" + empCode + '\'' +
                ", IpAddress='" + IpAddress + '\'' +
                '}';
    }
}
