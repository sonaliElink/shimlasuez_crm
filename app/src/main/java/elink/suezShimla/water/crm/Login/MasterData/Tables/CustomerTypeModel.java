package elink.suezShimla.water.crm.Login.MasterData.Tables;

import com.google.gson.annotations.SerializedName;

public class CustomerTypeModel {
    @SerializedName("CUSTTYPEID")
    private String CUSTTYPEID;

    @SerializedName("CUSTTYPETEXT")
    private String CUSTTYPETEXT;

    public CustomerTypeModel() {
    }

    public CustomerTypeModel(String CUSTTYPEID, String CUSTTYPETEXT) {
        this.CUSTTYPEID = CUSTTYPEID;
        this.CUSTTYPETEXT = CUSTTYPETEXT;
    }

    public String getCUSTTYPEID() {
        return CUSTTYPEID;
    }

    public String getCUSTTYPETEXT() {
        return CUSTTYPETEXT;
    }

    @Override
    public String toString() {
        return "CustomerTypeModel{" +
                "CUSTTYPEID='" + CUSTTYPEID + '\'' +
                ", CUSTTYPETEXT='" + CUSTTYPETEXT + '\'' +
                '}';
    }
}
