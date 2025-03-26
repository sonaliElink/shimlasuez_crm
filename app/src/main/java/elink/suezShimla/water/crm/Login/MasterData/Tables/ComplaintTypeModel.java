package elink.suezShimla.water.crm.Login.MasterData.Tables;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class ComplaintTypeModel extends RealmObject {

    @SerializedName("CMTM_ID")
    private int CMTM_ID;

    @SerializedName("CMTM_CODE")
    private String CMTM_CODE;

    @SerializedName("CMTM_NAME")
    private String CMTM_NAME;

    @SerializedName("REQUEST")
    private String REQUEST;

    @SerializedName("NOCONSUMER")
    private String NOCONSUMER;

    @SerializedName("DEPTID")
    private String DEPTID;

      @SerializedName("COMPLAINT")
    private String COMPLAINT;

     @SerializedName("ENQUIRY")
    private String ENQUIRY;

    public ComplaintTypeModel() {
    }

    public ComplaintTypeModel(int CMTM_ID, String CMTM_CODE, String CMTM_NAME, String REQUEST, String NOCONSUMER, String DEPTID, String COMPLAINT, String ENQUIRY) {
        this.CMTM_ID = CMTM_ID;
        this.CMTM_CODE = CMTM_CODE;
        this.CMTM_NAME = CMTM_NAME;
        this.REQUEST = REQUEST;
        this.NOCONSUMER = NOCONSUMER;
        this.DEPTID = DEPTID;
        this.COMPLAINT = COMPLAINT;
        this.ENQUIRY = ENQUIRY;
    }

//    public ComplaintTypeModel(int CMTM_ID, String CMTM_CODE, String CMTM_NAME) {
//        this.CMTM_ID = CMTM_ID;
//        this.CMTM_CODE = CMTM_CODE;
//        this.CMTM_NAME = CMTM_NAME;
//    }

    public int getCMTM_ID() {
        return CMTM_ID;
    }

    public String getCMTM_CODE() {
        return CMTM_CODE;
    }

    public String getCMTM_NAME() {
        return CMTM_NAME;
    }

    public String getREQUEST() {
        return REQUEST;
    }

    public String getNOCONSUMER() {
        return NOCONSUMER;
    }

    public String getDEPTID() {
        return DEPTID;
    }

    public String getCOMPLAINT() {
        return COMPLAINT;
    }

    public void setCOMPLAINT(String COMPLAINT) {
        this.COMPLAINT = COMPLAINT;
    }

    public String getENQUIRY() {
        return ENQUIRY;
    }

    public void setENQUIRY(String ENQUIRY) {
        this.ENQUIRY = ENQUIRY;
    }

    @Override
    public String toString() {
        return "ComplaintTypeModel{" +
                "CMTM_ID=" + CMTM_ID +
                ", CMTM_CODE='" + CMTM_CODE + '\'' +
                ", CMTM_NAME='" + CMTM_NAME + '\'' +
                ", REQUEST='" + REQUEST + '\'' +
                ", NOCONSUMER='" + NOCONSUMER + '\'' +
                ", DEPTID='" + DEPTID + '\'' +
                ", COMPLAINT='" + COMPLAINT + '\'' +
                ", ENQUIRY='" + ENQUIRY + '\'' +
                '}';
    }
}
