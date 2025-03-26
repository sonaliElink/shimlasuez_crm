package elink.suezShimla.water.crm.Complaint.MapScreen.Model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class LatLongModel extends RealmObject {
    @SerializedName("SRM_LATITUDE")
    private String lat;

    @SerializedName("SRM_LONGITUDE")
    private String lon;

    @SerializedName("NAME")
    private String title;

    public LatLongModel(){}

    public LatLongModel(String lat, String lon, String title) {
        this.lat = lat;
        this.lon = lon;
        this.title = title;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "LatLongModel{" +
                "lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
