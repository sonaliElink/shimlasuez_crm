package elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class DMAModel extends RealmObject {
    @SerializedName("DMAID")
    private String PM_ID;

    @SerializedName("DMANAME")
    private String PM_NAME;

    @SerializedName("SRID")
    private String SRID;

    public DMAModel() {
    }

    public DMAModel(String PM_ID, String PM_NAME, String SRID) {
        this.PM_ID = PM_ID;
        this.PM_NAME = PM_NAME;
        this.SRID = SRID;
    }

    public String getPM_ID() {
        return PM_ID;
    }

    public String getPM_NAME() {
        return PM_NAME;
    }

    public String getSRID() {
        return SRID;
    }

    @Override
    public String toString() {
        return "DMAModel{" +
                "PM_ID='" + PM_ID + '\'' +
                ", PM_NAME='" + PM_NAME + '\'' +
                ", SRID='" + SRID + '\'' +
                '}';
    }
}
