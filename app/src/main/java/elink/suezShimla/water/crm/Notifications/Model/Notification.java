package elink.suezShimla.water.crm.Notifications.Model;

import com.google.gson.annotations.SerializedName;

public class Notification {
    @SerializedName("N_DATE")
    private String N_DATE;

    @SerializedName("N_TITLE")
    private String N_TITLE;

    @SerializedName("N_MSG")
    private String N_MSG;

    public String getN_DATE() {
        return N_DATE;
    }

    public void setN_DATE(String n_DATE) {
        N_DATE = n_DATE;
    }

    public String getN_TITLE() {
        return N_TITLE;
    }

    public void setN_TITLE(String n_TITLE) {
        N_TITLE = n_TITLE;
    }

    public String getN_MSG() {
        return N_MSG;
    }

    public void setN_MSG(String n_MSG) {
        N_MSG = n_MSG;
    }




}



