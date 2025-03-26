package elink.suezShimla.water.crm.Complaint.ZoneAndWard.Model;

public class ZoneWardModel {

    private int zone_id;
    private int ward_id;
    private String zone;
    private String ward;

    public ZoneWardModel() {
    }

    public ZoneWardModel(int zone_id, int ward_id, String zone, String ward) {
        this.zone_id = zone_id;
        this.ward_id = ward_id;
        this.zone = zone;
        this.ward = ward;
    }

    public int getZone_id() {
        return zone_id;
    }

    public void setZone_id(int zone_id) {
        this.zone_id = zone_id;
    }

    public int getWard_id() {
        return ward_id;
    }

    public void setWard_id(int ward_id) {
        this.ward_id = ward_id;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    @Override
    public String toString() {
        return "ZoneWardModel{" +
                "zone_id=" + zone_id +
                ", ward_id=" + ward_id +
                ", zone='" + zone + '\'' +
                ", ward='" + ward + '\'' +
                '}';
    }
}
