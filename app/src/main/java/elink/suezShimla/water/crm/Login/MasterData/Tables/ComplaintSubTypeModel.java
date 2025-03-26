package elink.suezShimla.water.crm.Login.MasterData.Tables;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class ComplaintSubTypeModel extends RealmObject {

    @SerializedName("COMPLAINTSUBTYPEID")
    private int COMPLAINTSUBTYPEID;

    @SerializedName("COMPLAINTSUBTYPENAME")
    private String COMPLAINTSUBTYPENAME;

    @SerializedName("COMPLAINTTYPEID")
    private int COMPLAINTTYPEID;


    @SerializedName("COMPLAINT")
    private String COMPLAINT;


    @SerializedName("REQUEST")
    private String REQUEST;



    public ComplaintSubTypeModel() {
    }

    public ComplaintSubTypeModel(int COMPLAINTSUBTYPEID, String COMPLAINTSUBTYPENAME, int COMPLAINTTYPEID, String COMPLAINT, String REQUEST) {
        this.COMPLAINTSUBTYPEID = COMPLAINTSUBTYPEID;
        this.COMPLAINTSUBTYPENAME = COMPLAINTSUBTYPENAME;
        this.COMPLAINTTYPEID = COMPLAINTTYPEID;
        this.COMPLAINT = COMPLAINT;
        this.REQUEST = REQUEST;
    }

    public int getCOMPLAINTSUBTYPEID() {
        return COMPLAINTSUBTYPEID;
    }

    public String getCOMPLAINTSUBTYPENAME() {
        return COMPLAINTSUBTYPENAME;
    }

    public int getCOMPLAINTTYPEID() {
        return COMPLAINTTYPEID;
    }

    public String getCOMPLAINT() {
        return COMPLAINT;
    }

    public void setCOMPLAINT(String COMPLAINT) {
        this.COMPLAINT = COMPLAINT;
    }

    public String getREQUEST() {
        return REQUEST;
    }

    public void setREQUEST(String REQUEST) {
        this.REQUEST = REQUEST;
    }

    @Override
    public String toString() {
        return "ComplaintSubTypeModel{" +
                "COMPLAINTSUBTYPEID=" + COMPLAINTSUBTYPEID +
                ", COMPLAINTSUBTYPENAME='" + COMPLAINTSUBTYPENAME + '\'' +
                ", COMPLAINTTYPEID=" + COMPLAINTTYPEID +
                ", COMPLAINT='" + COMPLAINT + '\'' +
                ", REQUEST='" + REQUEST + '\'' +
                '}';
    }
}
