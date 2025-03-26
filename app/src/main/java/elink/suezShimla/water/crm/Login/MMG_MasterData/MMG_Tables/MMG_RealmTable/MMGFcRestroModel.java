package elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class MMGFcRestroModel extends RealmObject {

    @SerializedName("FCR_ID")
    private int FCR_ID ;

    @SerializedName("FCR_DESC")
    private String FCR_DESC;

    public MMGFcRestroModel() {
    }

    public MMGFcRestroModel(int FCR_ID , String FCR_DESC) {
        this.FCR_ID  = FCR_ID ;
        this.FCR_DESC = FCR_DESC;
    }

    public int getFCR_ID () {
        return FCR_ID  ;
    }

    public String getFCR_DESC() {
        return FCR_DESC;
    }

    @Override
    public String toString() {
        return "MMGFcRestroModel{" +
                "FCR_ID ='" + FCR_ID  + '\'' +
                ", FCR_DESC=" + FCR_DESC +
                '}';
    }
}
