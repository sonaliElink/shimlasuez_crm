package elink.suezShimla.water.crm.ConnectionManagement.model;

import com.google.gson.annotations.SerializedName;

public class RequestConsumerBidModel {

    @SerializedName("SRM_SERVICE_NO")
    private String SRM_SERVICE_NO;

    @SerializedName("CONSUMER")
    private String CONSUMER;

    @SerializedName("SRM_S_FLOOR_NO")
    private String SRM_S_FLOOR_NO;

    @SerializedName("ADDRESS")
    private String ADDRESS;

    @SerializedName("QueryStatus")
    private String QueryStatus;

    public RequestConsumerBidModel(String SRM_SERVICE_NO, String CONSUMER, String ADDRESS, String QueryStatus) {
        this.SRM_SERVICE_NO = SRM_SERVICE_NO;
        this.CONSUMER = CONSUMER;
        this.ADDRESS = ADDRESS;
        this.QueryStatus = QueryStatus;
    }


    public String getSRM_SERVICE_NO() {
        return SRM_SERVICE_NO;
    }

    public void setSRM_SERVICE_NO(String SRM_SERVICE_NO) {
        this.SRM_SERVICE_NO = SRM_SERVICE_NO;
    }

    public String getCONSUMER() {
        return CONSUMER;
    }

    public void setCONSUMER(String CONSUMER) {
        this.CONSUMER = CONSUMER;
    }

    public String getSRM_S_FLOOR_NO() {
        return SRM_S_FLOOR_NO;
    }

    public void setSRM_S_FLOOR_NO(String SRM_S_FLOOR_NO) {
        this.SRM_S_FLOOR_NO = SRM_S_FLOOR_NO;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }


    public String getQueryStatus() {
        return QueryStatus;
    }

    public void setQueryStatus(String queryStatus) {
        QueryStatus = queryStatus;
    }

    @Override
    public String toString() {
        return SRM_SERVICE_NO ;
    }
}
