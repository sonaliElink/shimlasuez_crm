package elink.suezShimla.water.crm.Login;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class LoginDB extends RealmObject {
    @SerializedName("EMPCODE")
    private String EMPCODE;

    @SerializedName("USERNAME")
    private String USERNAME;

    @SerializedName("ISVALID")
    private String ISVALID;

    @SerializedName("UPASSWORD")
    private String UPASSWORD;

    @SerializedName("EMPNAME")
    private String EMPNAME;

    @SerializedName("MOBILENO")
    private String MOBILENO;

    @SerializedName("EMAIL")
    private String EMAIL;

    @SerializedName("ZONEID")
    private String ZONEID;

    @SerializedName("AREA")
    private String AREA;

    @SerializedName("RDRID")
    private String RDRID;

    @SerializedName("LASTLOGIN")
    private String LASTLOGIN;

    @SerializedName("EM_DEPARTMENT")
    private String EM_DEPARTMENT;

    @SerializedName("AV")
    private String AV;

    @SerializedName("ERMAIL")
    private String ERMAIL;

    @SerializedName("EM_DESIGNATION")
    private String EM_DESIGNATION;

    @SerializedName("APP_ISLOGGED")
    private String APP_ISLOGGED;

    @SerializedName("QueryStatus")
    private String QueryStatus;

    public LoginDB() {
    }

    public LoginDB(String EMPCODE, String USERNAME, String ISVALID, String UPASSWORD, String EMPNAME,
                   String MOBILENO, String EMAIL, String ZONEID, String AREA, String RDRID,
                   String LASTLOGIN, String EM_DEPARTMENT, String AV, String ERMAIL, String EM_DESIGNATION, String APP_ISLOGGED, String queryStatus) {
        this.EMPCODE = EMPCODE;
        this.USERNAME = USERNAME;
        this.ISVALID = ISVALID;
        this.UPASSWORD = UPASSWORD;
        this.EMPNAME = EMPNAME;
        this.MOBILENO = MOBILENO;
        this.EMAIL = EMAIL;
        this.ZONEID = ZONEID;
        this.AREA = AREA;
        this.RDRID = RDRID;
        this.LASTLOGIN = LASTLOGIN;
        this.EM_DEPARTMENT = EM_DEPARTMENT;
        this.AV = AV;
        this.ERMAIL = ERMAIL;
        this.EM_DESIGNATION = EM_DESIGNATION;
        this.APP_ISLOGGED = APP_ISLOGGED;
        QueryStatus = queryStatus;
    }

    public String getEMPCODE() {
        return EMPCODE;
    }

    public void setEMPCODE(String EMPCODE) {
        this.EMPCODE = EMPCODE;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getISVALID() {
        return ISVALID;
    }

    public void setISVALID(String ISVALID) {
        this.ISVALID = ISVALID;
    }

    public String getUPASSWORD() {
        return UPASSWORD;
    }

    public void setUPASSWORD(String UPASSWORD) {
        this.UPASSWORD = UPASSWORD;
    }

    public String getEMPNAME() {
        return EMPNAME;
    }

    public void setEMPNAME(String EMPNAME) {
        this.EMPNAME = EMPNAME;
    }

    public String getMOBILENO() {
        return MOBILENO;
    }

    public void setMOBILENO(String MOBILENO) {
        this.MOBILENO = MOBILENO;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getZONEID() {
        return ZONEID;
    }

    public void setZONEID(String ZONEID) {
        this.ZONEID = ZONEID;
    }

    public String getAREA() {
        return AREA;
    }

    public void setAREA(String AREA) {
        this.AREA = AREA;
    }

    public String getRDRID() {
        return RDRID;
    }

    public void setRDRID(String RDRID) {
        this.RDRID = RDRID;
    }

    public String getLASTLOGIN() {
        return LASTLOGIN;
    }

    public void setLASTLOGIN(String LASTLOGIN) {
        this.LASTLOGIN = LASTLOGIN;
    }

    public String getEM_DEPARTMENT() {
        return EM_DEPARTMENT;
    }

    public void setEM_DEPARTMENT(String EM_DEPARTMENT) {
        this.EM_DEPARTMENT = EM_DEPARTMENT;
    }

    public String getAV() {
        return AV;
    }

    public void setAV(String AV) {
        this.AV = AV;
    }

    public String getERMAIL() {
        return ERMAIL;
    }

    public void setERMAIL(String ERMAIL) {
        this.ERMAIL = ERMAIL;
    }

    public String getEM_DESIGNATION() {
        return EM_DESIGNATION;
    }

    public void setEM_DESIGNATION(String EM_DESIGNATION) {
        this.EM_DESIGNATION = EM_DESIGNATION;
    }

    public String getAPP_ISLOGGED() {
        return APP_ISLOGGED;
    }

    public void setAPP_ISLOGGED(String APP_ISLOGGED) {
        this.APP_ISLOGGED = APP_ISLOGGED;
    }

    public String getQueryStatus() {
        return QueryStatus;
    }

    public void setQueryStatus(String queryStatus) {
        QueryStatus = queryStatus;
    }

    @Override
    public String toString() {
        return "LoginDB{" +
                "EMPCODE='" + EMPCODE + '\'' +
                ", USERNAME='" + USERNAME + '\'' +
                ", ISVALID='" + ISVALID + '\'' +
                ", UPASSWORD='" + UPASSWORD + '\'' +
                ", EMPNAME='" + EMPNAME + '\'' +
                ", MOBILENO='" + MOBILENO + '\'' +
                ", EMAIL='" + EMAIL + '\'' +
                ", ZONEID='" + ZONEID + '\'' +
                ", AREA='" + AREA + '\'' +
                ", RDRID='" + RDRID + '\'' +
                ", LASTLOGIN='" + LASTLOGIN + '\'' +
                ", EM_DEPARTMENT='" + EM_DEPARTMENT + '\'' +
                ", AV='" + AV + '\'' +
                ", ERMAIL='" + ERMAIL + '\'' +
                ", EM_DESIGNATION='" + EM_DESIGNATION + '\'' +
                ", APP_ISLOGGED='" + APP_ISLOGGED + '\'' +
                ", QueryStatus='" + QueryStatus + '\'' +
                '}';
    }
}
