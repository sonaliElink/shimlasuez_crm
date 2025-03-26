package elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class MMGRequestTypeModel extends RealmObject {

    @SerializedName("'SELECT'")
    private String SelectVal;

    @SerializedName("'ALL'")
    private String AllVal;

    public MMGRequestTypeModel() {
    }

    public MMGRequestTypeModel(String  SelectVal, String AllVal) {
        this.SelectVal = SelectVal;
        this.AllVal = AllVal;
    }

    public String getSelectVal() {
        return SelectVal;
    }

    public String getAllVal(){
        return AllVal;
    }

    @Override
    public String toString() {
        return "MMGRequestTypeModel{" +
                "SelectVal='" + SelectVal + '\'' +
                ", AllVal='" + AllVal + '\'' +
                '}';
    }
}
