package elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class ActionFormModel extends RealmObject {


     @SerializedName("A_NAME")
    private String A_NAME;

    @SerializedName("A_COM_TYPE")
    private String A_COM_TYPE;


    public ActionFormModel() {
    }


    public ActionFormModel(String a_NAME, String a_COM_TYPE) {
        A_NAME = a_NAME;
        A_COM_TYPE = a_COM_TYPE;
    } public ActionFormModel(String a_NAME) {
        A_NAME = a_NAME;

    }

    public String getA_NAME() {
        return A_NAME;
    }

    public void setA_NAME(String a_NAME) {
        A_NAME = a_NAME;
    }

    public String getA_COM_TYPE() {
        return A_COM_TYPE;
    }

    public void setA_COM_TYPE(String a_COM_TYPE) {
        A_COM_TYPE = a_COM_TYPE;
    }

    @Override
    public String toString() {
        return "ActionFormModel{" +
                "A_NAME='" + A_NAME + '\'' +
                ", A_COM_TYPE='" + A_COM_TYPE + '\'' +
                '}';
    }
}
