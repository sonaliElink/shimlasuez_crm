package elink.suezShimla.water.crm.Login.MasterData.Download;

import com.google.gson.annotations.SerializedName;

public class DocSource {
    @SerializedName("DOCSOURCE")
    private String DOCSOURCE;

    @SerializedName("DOCSOURCECODE")
    private String DOCSOURCECODE;

    @SerializedName("DOCCOMPTYPE")
    private String DOCCOMPTYPE;

    public DocSource() {
    }

    public DocSource(String DOCSOURCE, String DOCSOURCECODE, String DOCCOMPTYPE) {
        this.DOCSOURCE = DOCSOURCE;
        this.DOCSOURCECODE = DOCSOURCECODE;
        this.DOCCOMPTYPE = DOCCOMPTYPE;
    }

    public String getDOCSOURCE() {
        return DOCSOURCE;
    }

    public void setDOCSOURCE(String DOCSOURCE) {
        this.DOCSOURCE = DOCSOURCE;
    }

    public String getDOCSOURCECODE() {
        return DOCSOURCECODE;
    }

    public void setDOCSOURCECODE(String DOCSOURCECODE) {
        this.DOCSOURCECODE = DOCSOURCECODE;
    }

    public String getDOCCOMPTYPE() {
        return DOCCOMPTYPE;
    }

    public void setDOCCOMPTYPE(String DOCCOMPTYPE) {
        this.DOCCOMPTYPE = DOCCOMPTYPE;
    }

    @Override
    public String toString() {
        return "DocSource{" +
                "DOCSOURCE='" + DOCSOURCE + '\'' +
                ", DOCSOURCECODE='" + DOCSOURCECODE + '\'' +
                ", DOCCOMPTYPE='" + DOCCOMPTYPE + '\'' +
                '}';
    }
}
