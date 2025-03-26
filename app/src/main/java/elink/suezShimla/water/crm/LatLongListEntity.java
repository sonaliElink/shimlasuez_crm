package elink.suezShimla.water.crm;

public class LatLongListEntity {
    double lat;
    double longs;
    String place;

    public LatLongListEntity(double lat, double longs, String place) {
        this.lat = lat;
        this.longs = longs;
        this.place = place;
    }

    public LatLongListEntity() {
    }

    public LatLongListEntity(double lat, double longs) {
        this.lat = lat;
        this.longs = longs;
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

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("LatLongListEntity{lat=");
        stringBuilder.append(this.lat);
        stringBuilder.append(", longs=");
        stringBuilder.append(this.longs);
        stringBuilder.append(", place='");
        stringBuilder.append(this.place);
        stringBuilder.append('\'');
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
