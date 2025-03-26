package elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class WorkCompObservationModel extends RealmObject {

    @SerializedName("ID")
    private String ID;

    @SerializedName("OBSERVATION")
    private String OBSERVATION;

    @SerializedName("COMPTYPEID")
    private String COMPTYPEID;


    @SerializedName("ACTIONID")
    private String ACTIONID;

    public WorkCompObservationModel() {
    }

    public WorkCompObservationModel(String ID, String OBSERVATION, String COMPTYPEID, String ACTIONID) {
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

    @Override
    public String toString() {
        return "WorkCompObservation{" +
                "ID='" + ID + '\'' +
                ", OBSERVATION='" + OBSERVATION + '\'' +
                ", COMPTYPEID='" + COMPTYPEID + '\'' +
                ", ACTIONID='" + ACTIONID + '\'' +
                '}';
    }
}
