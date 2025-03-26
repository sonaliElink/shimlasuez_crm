package elink.suezShimla.water.crm.Login;

import com.google.gson.annotations.SerializedName;

public class LoginModel {
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
    @SerializedName("EM_DEPT_RIGHTS")
    private String EM_DEPT_RIGHTS;
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

    @SerializedName("MMGFIXER")
    private String MMGFIXER;
    @SerializedName("STARTTIME")
    private String STARTTIME;

    @SerializedName("ENDTIME")
    private String ENDTIME;

    @SerializedName("SessionToken")
    private String SessionToken;

    public LoginModel() {
    }

    public LoginModel(String EMPCODE, String USERNAME, String ISVALID, String UPASSWORD, String EMPNAME,
                      String MOBILENO, String EMAIL, String ZONEID, String AREA, String RDRID, String LASTLOGIN,
                      String EM_DEPARTMENT,String EM_DEPT_RIGHTS, String AV, String ERMAIL, String EM_DESIGNATION, String APP_ISLOGGED,
                      String queryStatus, String MMGFIXER) {
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
        this.EM_DEPT_RIGHTS=EM_DEPT_RIGHTS;
        this.AV = AV;
        this.ERMAIL = ERMAIL;
        this.EM_DESIGNATION = EM_DESIGNATION;
        this.APP_ISLOGGED = APP_ISLOGGED;
        QueryStatus = queryStatus;
        this.MMGFIXER = MMGFIXER;
    }

    public String getEMPCODE() {
        return EMPCODE;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public String getISVALID() {
        return ISVALID;
    }

    public String getUPASSWORD() {
        return UPASSWORD;
    }

    public String getEMPNAME() {
        return EMPNAME;
    }

    public String getMOBILENO() {
        return MOBILENO;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public String getZONEID() {
        return ZONEID;
    }

    public String getAREA() {
        return AREA;
    }

    public String getRDRID() {
        return RDRID;
    }

    public String getLASTLOGIN() {
        return LASTLOGIN;
    }

    public String getEM_DEPARTMENT() {
        return EM_DEPARTMENT;
    }

    public String getEM_DEPT_RIGHTS() {
        return EM_DEPT_RIGHTS;
    }

    public String getAV() {
        return AV;
    }

    public String getERMAIL() {
        return ERMAIL;
    }

    public String getEM_DESIGNATION() {
        return EM_DESIGNATION;
    }

    public String getAPP_ISLOGGED() {
        return APP_ISLOGGED;
    }

    public String getQueryStatus() {
        return QueryStatus;
    }

    public String getMMGFIXER() {
        return MMGFIXER;
    }

    public void setMMGFIXER(String MMGFIXER) {
        this.MMGFIXER = MMGFIXER;
    }

    public String getSTARTTIME() {
        return STARTTIME;
    }

    public void setSTARTTIME(String STARTTIME) {
        this.STARTTIME = STARTTIME;
    }

    public String getENDTIME() {
        return ENDTIME;
    }
    public String getSessionToken() {
        return SessionToken;
    }

    public void setSessionToken(String SessionToken) {
        this.SessionToken = SessionToken;
    }
    public void setENDTIME(String ENDTIME) {
        this.ENDTIME = ENDTIME;
    }
}
