package elink.suezShimla.water.crm.ErrorModel;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class UploadErroLogStatusModel  extends RealmObject {
    @SerializedName("ID")
    private int ID;

    @SerializedName("STATUS")
    private String STATUS;

    @SerializedName("QueryStatus")
    private String QueryStatus;

    public UploadErroLogStatusModel() {
    }

    public UploadErroLogStatusModel(int ID, String STATUS, String queryStatus) {
        this.ID = ID;
        this.STATUS = STATUS;
        QueryStatus = queryStatus;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getQueryStatus() {
        return QueryStatus;
    }

    public void setQueryStatus(String queryStatus) {
        QueryStatus = queryStatus;
    }

    @Override
    public String toString() {
        return "UploadErroLogStatusModel{" +
                "ID=" + ID +
                ", STATUS='" + STATUS + '\'' +
                ", QueryStatus='" + QueryStatus + '\'' +
                '}';
    }
}
