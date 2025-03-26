package elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class MDialDigitModel extends RealmObject {
    @SerializedName("DIGITTEXT")
    private String DIGITTEXT;

    @SerializedName("DIGITID")
    private String DIGITID;

    public MDialDigitModel() {
    }

    public MDialDigitModel(String DIGITTEXT, String DIGITID) {
        this.DIGITTEXT = DIGITTEXT;
        this.DIGITID = DIGITID;
    }

    public String getDIGITTEXT() {
        return DIGITTEXT;
    }

    public void setDIGITTEXT(String DIGITTEXT) {
        this.DIGITTEXT = DIGITTEXT;
    }

    public String getDIGITID() {
        return DIGITID;
    }

    public void setDIGITID(String DIGITID) {
        this.DIGITID = DIGITID;
    }

    @Override
    public String toString() {
        return "MDialDigitModel{" +
                "DIGITTEXT='" + DIGITTEXT + '\'' +
                ", DIGITID='" + DIGITID + '\'' +
                '}';
    }
}
