package elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import io.realm.RealmObject;

public class MMGCvlMeasurementResponseModel extends RealmObject {

    @SerializedName("SLNO")
    String SLNO;

    @SerializedName("MCD_MATERIAL_ID")
    String MCD_MATERIAL_ID;

    @SerializedName("MCD_MATERIAL_NAME")
    String MCD_MATERIAL_NAME;


    @SerializedName("MCD_ISDROPDOWN")
    String MCD_ISDROPDOWN;

    @SerializedName("MCD_ISQUANTITY")
    String MCD_ISQUANTITY;

    @SerializedName("DDLID")
    String DDLID;


    @SerializedName("QUANTITY")
    String QUANTITY;

    @SerializedName("LENGTH")
    String LENGTH;

    @SerializedName("WIDTH")
    String WIDTH;

    @SerializedName("DEPTH")
    String DEPTH;

    public MMGCvlMeasurementResponseModel() {
    }

    public MMGCvlMeasurementResponseModel(String SLNO, String MCD_MATERIAL_ID, String MCD_MATERIAL_NAME, String MCD_ISDROPDOWN, String MCD_ISQUANTITY, String DDLID, String QUANTITY, String LENGTH, String WIDTH, String DEPTH) {
        this.SLNO = SLNO;
        this.MCD_MATERIAL_ID = MCD_MATERIAL_ID;
        this.MCD_MATERIAL_NAME = MCD_MATERIAL_NAME;
        this.MCD_ISDROPDOWN = MCD_ISDROPDOWN;
        this.MCD_ISQUANTITY = MCD_ISQUANTITY;
        this.DDLID = DDLID;
        this.QUANTITY = QUANTITY;
        this.LENGTH = LENGTH;
        this.WIDTH = WIDTH;
        this.DEPTH = DEPTH;
    }

    public String getSLNO() {
        return SLNO;
    }

    public void setSLNO(String SLNO) {
        this.SLNO = SLNO;
    }

    public String getMCD_MATERIAL_ID() {
        return MCD_MATERIAL_ID;
    }

    public void setMCD_MATERIAL_ID(String MCD_MATERIAL_ID) {
        this.MCD_MATERIAL_ID = MCD_MATERIAL_ID;
    }

    public String getMCD_MATERIAL_NAME() {
        return MCD_MATERIAL_NAME;
    }

    public void setMCD_MATERIAL_NAME(String MCD_MATERIAL_NAME) {
        this.MCD_MATERIAL_NAME = MCD_MATERIAL_NAME;
    }

    public String getMCD_ISDROPDOWN() {
        return MCD_ISDROPDOWN;
    }

    public void setMCD_ISDROPDOWN(String MCD_ISDROPDOWN) {
        this.MCD_ISDROPDOWN = MCD_ISDROPDOWN;
    }

    public String getMCD_ISQUANTITY() {
        return MCD_ISQUANTITY;
    }

    public void setMCD_ISQUANTITY(String MCD_ISQUANTITY) {
        this.MCD_ISQUANTITY = MCD_ISQUANTITY;
    }

    public String getDDLID() {
        return DDLID;
    }

    public void setDDLID(String DDLID) {
        this.DDLID = DDLID;
    }

    public String getQUANTITY() {
        return QUANTITY;
    }

    public void setQUANTITY(String QUANTITY) {
        this.QUANTITY = QUANTITY;
    }

    public String getLENGTH() {
        return LENGTH;
    }

    public void setLENGTH(String LENGTH) {
        this.LENGTH = LENGTH;
    }

    public String getWIDTH() {
        return WIDTH;
    }

    public void setWIDTH(String WIDTH) {
        this.WIDTH = WIDTH;
    }

    public String getDEPTH() {
        return DEPTH;
    }

    public void setDEPTH(String DEPTH) {
        this.DEPTH = DEPTH;
    }

    @Override
    public String toString() {
        return "MMGCvlMeasurementResponseModel{" +
                "SLNO ='" + SLNO  + '\'' +
                ", MCD_MATERIAL_ID=" + MCD_MATERIAL_ID +
                ", MCD_MATERIAL_NAME=" + MCD_MATERIAL_NAME +
                ", MCD_ISDROPDOWN=" + MCD_ISDROPDOWN +
                ", MCD_ISQUANTITY=" + MCD_ISQUANTITY +
                ", DDLID=" + DDLID +
                ", QUANTITY=" + QUANTITY +
                ", LENGTH=" + LENGTH +
                ", WIDTH=" + WIDTH +
                ", DEPTH=" + DEPTH +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MMGCvlMeasurementResponseModel)) return false;
        MMGCvlMeasurementResponseModel that = (MMGCvlMeasurementResponseModel) o;
        return Objects.equals(SLNO, that.SLNO) &&
                MCD_MATERIAL_ID.equals(that.MCD_MATERIAL_ID) &&
                Objects.equals(MCD_MATERIAL_NAME, that.MCD_MATERIAL_NAME);
    }

    @Override
    public int hashCode() {
        return Objects.hash(MCD_MATERIAL_ID);
    }
}
