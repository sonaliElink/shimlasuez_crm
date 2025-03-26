package elink.suezShimla.water.crm.ChangePassword.Model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ChangePinModel extends RealmObject {
    @PrimaryKey
    private int id;

    private String userName;

    private String empCode;

    private String empName;

    private String empMobileNo;

    private String empEmail;

    private String RDRID;

    private int empStatus;

    private String zone;

    private String area;

    @SerializedName("UPASSWORD")
    private String empPassword;

    private String toDate;

    private int isValid;

    private String deviceImei;

    private String macAddress;

    @SerializedName("QueryStatus")
    private String QueryStatus;

    @SerializedName("AV")
    private String AV;

    @SerializedName("APP_ISLOGGED")
    private String APP_ISLOGGED;

    private String LastLogin;

    public ChangePinModel() {
    }

    public ChangePinModel(int id, String userName, String empCode, String empName, String empMobileNo, String empEmail,
                          String RDRID, int empStatus, String zone, String area, String empPassword, String toDate, int isValid, String deviceImei, String macAddress,String queryStatus,String LastLogin) {
        this.id = id;
        this.userName = userName;
        this.empCode = empCode;
        this.empName = empName;
        this.empMobileNo = empMobileNo;
        this.empEmail = empEmail;
        this.RDRID = RDRID;
        this.empStatus = empStatus;
        this.zone = zone;
        this.area = area;
        this.empPassword = empPassword;
        this.toDate = toDate;
        this.isValid = isValid;
        this.deviceImei = deviceImei;
        this.macAddress = macAddress;
        this.QueryStatus = queryStatus;
        this.LastLogin = LastLogin;
    }

    public ChangePinModel(int id, String userName, String empCode, String empName, String empMobileNo, String empEmail, String RDRID, int empStatus, String zone, String area, String empPassword, String toDate, int isValid, String deviceImei, String macAddress, String queryStatus, String AV, String APP_ISLOGGED, String lastLogin) {
        this.id = id;
        this.userName = userName;
        this.empCode = empCode;
        this.empName = empName;
        this.empMobileNo = empMobileNo;
        this.empEmail = empEmail;
        this.RDRID = RDRID;
        this.empStatus = empStatus;
        this.zone = zone;
        this.area = area;
        this.empPassword = empPassword;
        this.toDate = toDate;
        this.isValid = isValid;
        this.deviceImei = deviceImei;
        this.macAddress = macAddress;
        QueryStatus = queryStatus;
        this.AV = AV;
        this.APP_ISLOGGED = APP_ISLOGGED;
        LastLogin = lastLogin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpMobileNo() {
        return empMobileNo;
    }

    public void setEmpMobileNo(String empMobileNo) {
        this.empMobileNo = empMobileNo;
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }

    public String getRDRID() {
        return RDRID;
    }

    public void setRDRID(String RDRID) {
        this.RDRID = RDRID;
    }

    public int getEmpStatus() {
        return empStatus;
    }

    public void setEmpStatus(int empStatus) {
        this.empStatus = empStatus;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getEmpPassword() {
        return empPassword;
    }

    public void setEmpPassword(String empPassword) {
        this.empPassword = empPassword;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public String getDeviceImei() {
        return deviceImei;
    }

    public void setDeviceImei(String deviceImei) {
        this.deviceImei = deviceImei;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }


    public String getQueryStatus() {
        return QueryStatus;
    }

    public String getLastLogin() {
        return LastLogin;
    }

    public void setLastLogin(String lastLogin) {
        LastLogin = lastLogin;
    }

    public void setQueryStatus(String queryStatus) {
        QueryStatus = queryStatus;
    }

    public String getAV() {
        return AV;
    }

    public void setAV(String AV) {
        this.AV = AV;
    }

    public String getAPP_ISLOGGED() {
        return APP_ISLOGGED;
    }

    public void setAPP_ISLOGGED(String APP_ISLOGGED) {
        this.APP_ISLOGGED = APP_ISLOGGED;
    }

    @Override
    public String toString() {
        return "ChangePinModel{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", empCode='" + empCode + '\'' +
                ", empName='" + empName + '\'' +
                ", empMobileNo='" + empMobileNo + '\'' +
                ", empEmail='" + empEmail + '\'' +
                ", RDRID='" + RDRID + '\'' +
                ", empStatus=" + empStatus +
                ", zone='" + zone + '\'' +
                ", area='" + area + '\'' +
                ", empPassword='" + empPassword + '\'' +
                ", toDate='" + toDate + '\'' +
                ", isValid=" + isValid +
                ", deviceImei='" + deviceImei + '\'' +
                ", macAddress='" + macAddress + '\'' +
                ", QueryStatus='" + QueryStatus + '\'' +
                ", LastLogin='" + LastLogin + '\'' +
                '}';
    }
}
