package elink.suezShimla.water.crm.Login.MasterData.Tables;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class DocSubTypeModel extends RealmObject {


    @SerializedName("DOCUMENTSUBTYPEID")
    private String DOCUMENTSUBTYPEID;

    @SerializedName("DOCUMENTTYPEID")
    private String DOCUMENTTYPEID;

    @SerializedName("DOCUMENTSUBTYPE")
    private String DOCUMENTSUBTYPE;

    public DocSubTypeModel() {
    }

    public DocSubTypeModel(String DOCUMENTSUBTYPEID, String DOCUMENTTYPEID, String DOCUMENTSUBTYPE) {
        this.DOCUMENTSUBTYPEID = DOCUMENTSUBTYPEID;
        this.DOCUMENTTYPEID = DOCUMENTTYPEID;
        this.DOCUMENTSUBTYPE = DOCUMENTSUBTYPE;
    }

    public String getDOCUMENTSUBTYPEID() {
        return DOCUMENTSUBTYPEID;
    }

    public void setDOCUMENTSUBTYPEID(String DOCUMENTSUBTYPEID) {
        this.DOCUMENTSUBTYPEID = DOCUMENTSUBTYPEID;
    }

    public String getDOCUMENTTYPEID() {
        return DOCUMENTTYPEID;
    }

    public void setDOCUMENTTYPEID(String DOCUMENTTYPEID) {
        this.DOCUMENTTYPEID = DOCUMENTTYPEID;
    }

    public String getDOCUMENTSUBTYPE() {
        return DOCUMENTSUBTYPE;
    }

    public void setDOCUMENTSUBTYPE(String DOCUMENTSUBTYPE) {
        this.DOCUMENTSUBTYPE = DOCUMENTSUBTYPE;
    }

    @Override
    public String toString() {
        return "DocSubTypeModel{" +
                "DOCUMENTSUBTYPEID='" + DOCUMENTSUBTYPEID + '\'' +
                ", DOCUMENTTYPEID='" + DOCUMENTTYPEID + '\'' +
                ", DOCUMENTSUBTYPE='" + DOCUMENTSUBTYPE + '\'' +
                '}';
    }
}
