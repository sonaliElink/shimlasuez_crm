package elink.suezShimla.water.crm.androidhsc.hscModelTable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class PropertyTypeModel extends RealmObject {

    @SerializedName("PRM_ID")
    private String PRM_ID;

    @SerializedName("PRM_NAME")
    private String PRM_NAME;

    @SerializedName("PRM_TARIFF")
    private String PRM_TARIFF;

    public PropertyTypeModel() {
    }

    public PropertyTypeModel(String PRM_ID, String PRM_NAME, String PRM_TARIFF) {
        this.PRM_ID = PRM_ID;
        this.PRM_NAME = PRM_NAME;
        this.PRM_TARIFF = PRM_TARIFF;
    }

    public String getPRM_ID() {
        return PRM_ID;
    }

    public void setPRM_ID(String PRM_ID) {
        this.PRM_ID = PRM_ID;
    }

    public String getPRM_NAME() {
        return PRM_NAME;
    }

    public void setPRM_NAME(String PRM_NAME) {
        this.PRM_NAME = PRM_NAME;
    }

    public String getPRM_TARIFF() {
        return PRM_TARIFF;
    }

    public void setPRM_TARIFF(String PRM_TARIFF) {
        this.PRM_TARIFF = PRM_TARIFF;
    }

    @Override
    public String toString() {
        return "PropertyTypeModel{" +
                "PRM_ID='" + PRM_ID + '\'' +
                ", PRM_NAME='" + PRM_NAME + '\'' +
                ", PRM_TARIFF='" + PRM_TARIFF + '\'' +
                '}';
    }
}
