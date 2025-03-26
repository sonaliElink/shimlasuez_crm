package elink.suezShimla.water.crm.Login.MasterData.Download;

import com.google.gson.annotations.SerializedName;

public class DocType {

    @SerializedName("DOCUMENTTYPE")
    private String DOCUMENTTYPE;

    @SerializedName("DOCUMENTTYPEID")
    private String DOCUMENTTYPEID;

    @SerializedName("DOCUMENTSOURCE")
    private String DOCUMENTSOURCE;

    public DocType() {
    }

    public DocType(String DOCUMENTTYPE, String DOCUMENTTYPEID, String DOCUMENTSOURCE) {
        this.DOCUMENTTYPE = DOCUMENTTYPE;
        this.DOCUMENTTYPEID = DOCUMENTTYPEID;
        this.DOCUMENTSOURCE = DOCUMENTSOURCE;
    }

    public String getDOCUMENTTYPE() {
        return DOCUMENTTYPE;
    }

    public void setDOCUMENTTYPE(String DOCUMENTTYPE) {
        this.DOCUMENTTYPE = DOCUMENTTYPE;
    }

    public String getDOCUMENTTYPEID() {
        return DOCUMENTTYPEID;
    }

    public void setDOCUMENTTYPEID(String DOCUMENTTYPEID) {
        this.DOCUMENTTYPEID = DOCUMENTTYPEID;
    }

    public String getDOCUMENTSOURCE() {
        return DOCUMENTSOURCE;
    }

    public void setDOCUMENTSOURCE(String DOCUMENTSOURCE) {
        this.DOCUMENTSOURCE = DOCUMENTSOURCE;
    }

    @Override
    public String toString() {
        return "DocType{" +
                "DOCUMENTTYPE='" + DOCUMENTTYPE + '\'' +
                ", DOCUMENTTYPEID='" + DOCUMENTTYPEID + '\'' +
                ", DOCUMENTSOURCE='" + DOCUMENTSOURCE + '\'' +
                '}';
    }
}
