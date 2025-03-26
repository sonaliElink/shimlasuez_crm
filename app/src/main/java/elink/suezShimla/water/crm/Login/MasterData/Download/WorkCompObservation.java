package elink.suezShimla.water.crm.Login.MasterData.Download;

import com.google.gson.annotations.SerializedName;

public class WorkCompObservation {

    @SerializedName("ID")
    private String ID;

    @SerializedName("OBSERVATION")
    private String OBSERVATION;

    @SerializedName("COMPTYPEID")
    private String COMPTYPEID;


    @SerializedName("ACTIONID")
    private String ACTIONID;

    public WorkCompObservation(String ID, String OBSERVATION, String COMPTYPEID, String ACTIONID) {
        this.ID = ID;
        this.OBSERVATION = OBSERVATION;
        this.COMPTYPEID = COMPTYPEID;
        this.ACTIONID = ACTIONID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getOBSERVATION() {
        return OBSERVATION;
    }

    public void setOBSERVATION(String OBSERVATION) {
        this.OBSERVATION = OBSERVATION;
    }

    public String getCOMPTYPEID() {
        return COMPTYPEID;
    }

    public void setCOMPTYPEID(String COMPTYPEID) {
        this.COMPTYPEID = COMPTYPEID;
    }

    public String getACTIONID() {
        return ACTIONID;
    }

    public void setACTIONID(String ACTIONID) {
        this.ACTIONID = ACTIONID;
    }


}
