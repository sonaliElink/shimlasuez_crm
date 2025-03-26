package elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class MMGMaterialResponseModel extends RealmObject implements Parcelable {

    @SerializedName("SLNO")
    String SLNO;

    @SerializedName("MRM_MATERIAL_ID")
    String MRM_MATERIAL_ID;

    @SerializedName("MRM_MATERIAL_NAME")
    String MRM_MATERIAL_NAME;

    @SerializedName("DEFAULTQUANTITY")
    String DEFAULTQUANTITY;

    @SerializedName("UOM_NAME")
    String UOM_NAME;

     @SerializedName("MRM_GROUPID")
    String MRM_GROUPID;

    public MMGMaterialResponseModel(String SLNO, String MRM_MATERIAL_ID, String MRM_MATERIAL_NAME, String DEFAULTQUANTITY, String UOM_NAME, String MRM_GROUPID) {
        this.SLNO = SLNO;
        this.MRM_MATERIAL_ID = MRM_MATERIAL_ID;
        this.MRM_MATERIAL_NAME = MRM_MATERIAL_NAME;
        this.DEFAULTQUANTITY = DEFAULTQUANTITY;
        this.UOM_NAME = UOM_NAME;
        this.MRM_GROUPID = MRM_GROUPID;
    }

    public MMGMaterialResponseModel() {
    }

    public MMGMaterialResponseModel(String MRM_MATERIAL_ID, String MRM_MATERIAL_NAME){
        this.MRM_MATERIAL_ID = MRM_MATERIAL_ID;
        this.MRM_MATERIAL_NAME = MRM_MATERIAL_NAME;
    }

    protected MMGMaterialResponseModel(Parcel in) {
        SLNO = in.readString();
        MRM_MATERIAL_ID = in.readString();
        MRM_MATERIAL_NAME = in.readString();
        DEFAULTQUANTITY = in.readString();
        UOM_NAME = in.readString();
        MRM_GROUPID = in.readString();
    }

    public static final Creator<MMGMaterialResponseModel> CREATOR = new Creator<MMGMaterialResponseModel>() {
        @Override
        public MMGMaterialResponseModel createFromParcel(Parcel in) {
            return new MMGMaterialResponseModel(in);
        }

        @Override
        public MMGMaterialResponseModel[] newArray(int size) {
            return new MMGMaterialResponseModel[size];
        }
    };

    public String getSLNO() {
        return SLNO;
    }

    public void setSLNO(String SLNO) {
        this.SLNO = SLNO;
    }

    public String getMRM_MATERIAL_ID() {
        return MRM_MATERIAL_ID;
    }

    public void setMRM_MATERIAL_ID(String MRM_MATERIAL_ID) {
        this.MRM_MATERIAL_ID = MRM_MATERIAL_ID;
    }

    public String getMRM_MATERIAL_NAME() {
        return MRM_MATERIAL_NAME;
    }

    public void setMRM_MATERIAL_NAME(String MRM_MATERIAL_NAME) {
        this.MRM_MATERIAL_NAME = MRM_MATERIAL_NAME;
    }

    public String getDEFAULTQUANTITY() {
        return DEFAULTQUANTITY;
    }

    public void setDEFAULTQUANTITY(String DEFAULTQUANTITY) {
        this.DEFAULTQUANTITY = DEFAULTQUANTITY;
    }

    public String getUOM_NAME() {
        return UOM_NAME;
    }

    public void setUOM_NAME(String UOM_NAME) {
        this.UOM_NAME = UOM_NAME;
    }

    public String getMRM_GROUPID() {
        return MRM_GROUPID;
    }

    public void setMRM_GROUPID(String MRM_GROUPID) {
        this.MRM_GROUPID = MRM_GROUPID;
    }

    @Override
    public String toString() {
        return "MMGMaterialResponseModel{" +
                "SLNO='" + SLNO + '\'' +
                ", MRM_MATERIAL_ID='" + MRM_MATERIAL_ID + '\'' +
                ", MRM_MATERIAL_NAME='" + MRM_MATERIAL_NAME + '\'' +
                ", DEFAULTQUANTITY='" + DEFAULTQUANTITY + '\'' +
                ", UOM_NAME='" + UOM_NAME + '\'' +
                ", MRM_GROUPID='" + MRM_GROUPID + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(SLNO);
        dest.writeString(MRM_MATERIAL_ID);
        dest.writeString(MRM_MATERIAL_NAME);
        dest.writeString(DEFAULTQUANTITY);
        dest.writeString(UOM_NAME);
        dest.writeString(MRM_GROUPID);
    }
}
