package elink.suezShimla.water.crm.ErrorModel;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ErrorLogModel extends RealmObject {

    @PrimaryKey
    @SerializedName("id")
    private int id;

    @SerializedName("USERID")
    private String EMPCODE;

    @SerializedName("IMEI")
    private String IMEI ;

    @SerializedName("MODEL")
    private String MODEL;

    @SerializedName("DNAME")
    private String DEVICENAME;

    @SerializedName("VER")
    private int VERSION;

    @SerializedName("AV")
    private String APPVERSION;

    @SerializedName("ACTIVITY")
    private String ACTIVITYNAME;

    @SerializedName("EVENT")
    private String EVENTNAME;

    @SerializedName("ERROR")
    private String ERRORLOG;

    @SerializedName("UPLOADSTATUS")
    private int UPLOADSTATUS;

    @SerializedName("EDATE")
    private String TIMESTAMP;

    @SerializedName("AI")
    private String AI;


    public ErrorLogModel() {
    }

    public ErrorLogModel(int id, String EMPCODE, String IMEI, String MODEL, String DEVICENAME, int VERSION, String APPVERSION,
                         String ACTIVITYNAME, String EVENTNAME, String ERRORLOG, int UPLOADSTATUS, String TIMESTAMP,
                         String AI) {
        this.id = id;
        this.EMPCODE = EMPCODE;
        this.IMEI = IMEI;
        this.MODEL = MODEL;
        this.DEVICENAME = DEVICENAME;
        this.VERSION = VERSION;
        this.APPVERSION = APPVERSION;
        this.ACTIVITYNAME = ACTIVITYNAME;
        this.EVENTNAME = EVENTNAME;
        this.ERRORLOG = ERRORLOG;
        this.UPLOADSTATUS = UPLOADSTATUS;
        this.TIMESTAMP = TIMESTAMP;
        this.AI = AI;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEMPCODE() {
        return EMPCODE;
    }

    public void setEMPCODE(String EMPCODE) {
        this.EMPCODE = EMPCODE;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getMODEL() {
        return MODEL;
    }

    public void setMODEL(String MODEL) {
        this.MODEL = MODEL;
    }

    public String getDEVICENAME() {
        return DEVICENAME;
    }

    public void setDEVICENAME(String DEVICENAME) {
        this.DEVICENAME = DEVICENAME;
    }

    public int getVERSION() {
        return VERSION;
    }

    public void setVERSION(int VERSION) {
        this.VERSION = VERSION;
    }

    public String getAPPVERSION() {
        return APPVERSION;
    }

    public void setAPPVERSION(String APPVERSION) {
        this.APPVERSION = APPVERSION;
    }

    public String getACTIVITYNAME() {
        return ACTIVITYNAME;
    }

    public void setACTIVITYNAME(String ACTIVITYNAME) {
        this.ACTIVITYNAME = ACTIVITYNAME;
    }

    public String getEVENTNAME() {
        return EVENTNAME;
    }

    public void setEVENTNAME(String EVENTNAME) {
        this.EVENTNAME = EVENTNAME;
    }

    public String getERRORLOG() {
        return ERRORLOG;
    }

    public void setERRORLOG(String ERRORLOG) {
        this.ERRORLOG = ERRORLOG;
    }

    public int getUPLOADSTATUS() {
        return UPLOADSTATUS;
    }

    public void setUPLOADSTATUS(int UPLOADSTATUS) {
        this.UPLOADSTATUS = UPLOADSTATUS;
    }

    public String getTIMESTAMP() {
        return TIMESTAMP;
    }

    public void setTIMESTAMP(String TIMESTAMP) {
        this.TIMESTAMP = TIMESTAMP;
    }

    public String getAI() {
        return AI;
    }

    public void setAI(String AI) {
        this.AI = AI;
    }

    @Override
    public String toString() {
        return "ErrorLogModel{" +
                "id=" + id +
                ", EMPCODE='" + EMPCODE + '\'' +
                ", IMEI='" + IMEI + '\'' +
                ", MODEL='" + MODEL + '\'' +
                ", DEVICENAME='" + DEVICENAME + '\'' +
                ", VERSION=" + VERSION +
                ", APPVERSION='" + APPVERSION + '\'' +
                ", ACTIVITYNAME='" + ACTIVITYNAME + '\'' +
                ", EVENTNAME='" + EVENTNAME + '\'' +
                ", ERRORLOG='" + ERRORLOG + '\'' +
                ", UPLOADSTATUS=" + UPLOADSTATUS +
                ", TIMESTAMP='" + TIMESTAMP + '\'' +
                ", AI='" + AI + '\'' +
                '}';
    }
}
