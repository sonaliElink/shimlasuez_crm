package elink.suezShimla.water.crm.MeterManagementSystem.MeterInstallation.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MeterInstallationReplacementEntryShow implements Parcelable {

    @SerializedName("ShowMeterData")
    private List<ShowMeterDataModel> downloadMeterData;

      @SerializedName("Data")
    private List<ShowMeterDataModel> Data;

    @SerializedName("Other")
    private List<Other> downloadOther;

    public MeterInstallationReplacementEntryShow() {
    }

    public MeterInstallationReplacementEntryShow(List<ShowMeterDataModel> downloadMeterData, List<Other> downloadOther) {
        this.downloadMeterData = downloadMeterData;
        this.downloadOther = downloadOther;
    }

    public List<ShowMeterDataModel> getDownloadMeterData() {
        return downloadMeterData;
    }

    public void setDownloadMeterData(List<ShowMeterDataModel> downloadMeterData) {
        this.downloadMeterData = downloadMeterData;
    }

    public List<Other> getDownloadOther() {
        return downloadOther;
    }

    public void setDownloadOther(List<Other> downloadOther) {
        this.downloadOther = downloadOther;
    }




    public List<ShowMeterDataModel> getData() {
        return Data;
    }

    public void setData(List<ShowMeterDataModel> data) {
        Data = data;
    }

    public static Creator<MeterInstallationReplacementEntryShow> getCREATOR() {
        return CREATOR;
    }

    protected MeterInstallationReplacementEntryShow(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public String toString() {
        return "MeterInstallationReplacementEntryShow{" +
                "downloadMeterData=" + downloadMeterData +
                ", Data=" + Data +
                ", downloadOther=" + downloadOther +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MeterInstallationReplacementEntryShow> CREATOR = new Creator<MeterInstallationReplacementEntryShow>() {
        @Override
        public MeterInstallationReplacementEntryShow createFromParcel(Parcel in) {
            return new MeterInstallationReplacementEntryShow(in);
        }

        @Override
        public MeterInstallationReplacementEntryShow[] newArray(int size) {
            return new MeterInstallationReplacementEntryShow[size];
        }
    };

}
