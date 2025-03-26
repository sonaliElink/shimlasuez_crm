package elink.suezShimla.water.crm.Login.MasterData.Download;

import com.google.gson.annotations.SerializedName;

public class DownloadComplaintSubType {

    @SerializedName("COMPLAINTSUBTYPEID")
    private String COMPLAINTSUBTYPEID;

    @SerializedName("COMPLAINTSUBTYPENAME")
    private String COMPLAINTSUBTYPENAME;

    @SerializedName("COMPLAINTTYPEID")
    private String COMPLAINTTYPEID;

    @SerializedName("COMPLAINT")
    private String COMPLAINT;


    @SerializedName("REQUEST")
    private String REQUEST;


    public DownloadComplaintSubType() {
    }

    public DownloadComplaintSubType(String COMPLAINTSUBTYPEID, String COMPLAINTSUBTYPENAME, String COMPLAINTTYPEID, String COMPLAINT, String REQUEST) {
        this.COMPLAINTSUBTYPEID = COMPLAINTSUBTYPEID;
        this.COMPLAINTSUBTYPENAME = COMPLAINTSUBTYPENAME;
        this.COMPLAINTTYPEID = COMPLAINTTYPEID;
        this.COMPLAINT = COMPLAINT;
        this.REQUEST = REQUEST;
    }

    public String getCOMPLAINTSUBTYPEID() {
        return COMPLAINTSUBTYPEID;
    }

    public String getCOMPLAINTSUBTYPENAME() {
        return COMPLAINTSUBTYPENAME;
    }

    public String getCOMPLAINTTYPEID() {
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
        return "DownloadComplaintSubType{" +
                "COMPLAINTSUBTYPEID='" + COMPLAINTSUBTYPEID + '\'' +
                ", COMPLAINTSUBTYPENAME='" + COMPLAINTSUBTYPENAME + '\'' +
                ", COMPLAINTTYPEID='" + COMPLAINTTYPEID + '\'' +
                ", COMPLAINT='" + COMPLAINT + '\'' +
                ", REQUEST='" + REQUEST + '\'' +
                '}';
    }
}
