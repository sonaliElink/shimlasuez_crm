package elink.suezShimla.water.crm.Login.MasterData.Download;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;


public class DownloadCustomerType extends RealmObject {
    @SerializedName("CUSTTYPEID")
    private String CUSTTYPEID;

    @SerializedName("CUSTTYPETEXT")
    private String CUSTTYPETEXT;

    @SerializedName("CUSTBUID")
    private String CUSTBUID;


    public DownloadCustomerType() {
    }

    public DownloadCustomerType(String CUSTTYPEID, String CUSTTYPETEXT, String CUSTBUID) {
        this.CUSTTYPEID = CUSTTYPEID;
        this.CUSTTYPETEXT = CUSTTYPETEXT;
        this.CUSTBUID = CUSTBUID;
    }

    public String getCUSTTYPEID() {
        return CUSTTYPEID;
    }

    public String getCUSTTYPETEXT() {
        return CUSTTYPETEXT;
    }

    public String getCUSTBUID() {
        return CUSTBUID;
    }

    @Override
    public String toString() {
        return "DownloadCustomerType{" +
                "CUSTTYPEID='" + CUSTTYPEID + '\'' +
                ", CUSTTYPETEXT='" + CUSTTYPETEXT + '\'' +
                ", CUSTBUID='" + CUSTBUID + '\'' +
                '}';
    }
}
