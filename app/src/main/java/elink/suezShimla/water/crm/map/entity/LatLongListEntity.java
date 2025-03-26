package elink.suezShimla.water.crm.map.entity;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class LatLongListEntity implements ClusterItem {
    double lat;
    double longs;
    String place;
    String consumnerNumber;
    String consummerName;

    public LatLongListEntity(double lat, double longs, String place, String consumnerNumber, String consummerName) {
        this.lat = lat;
        this.longs = longs;
        this.place = place;
        this.consumnerNumber = consumnerNumber;
        this.consummerName = consummerName;
    }


    public double getLat() {
        return this.lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLongs() {
        return this.longs;
    }

    public void setLongs(double longs) {
        this.longs = longs;
    }

    public String getPlace() {
        return this.place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getConsumnerNumber() {
        return consumnerNumber;
    }

    public void setConsumnerNumber(String consumnerNumber) {
        this.consumnerNumber = consumnerNumber;
    }

    public String getConsummerName() {
        return consummerName;
    }

    public void setConsummerName(String consummerName) {
        this.consummerName = consummerName;
    }

    @Override
    public String toString() {
        return "LatLongListEntity{" +
                "lat=" + lat +
                ", longs=" + longs +
                ", place='" + place + '\'' +
                ", consumnerNumber='" + consumnerNumber + '\'' +
                ", consummerName='" + consummerName + '\'' +
                '}';
    }

    @Override
    public LatLng getPosition() {
        LatLng latLng=new LatLng(lat,longs);
        return latLng;
    }

    @Override
    public String getTitle() {
        return consummerName;
    }

    @Override
    public String getSnippet() {
        return place;
    }




}
