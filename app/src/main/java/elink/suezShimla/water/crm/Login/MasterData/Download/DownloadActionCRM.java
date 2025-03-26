package elink.suezShimla.water.crm.Login.MasterData.Download;

import com.google.gson.annotations.SerializedName;

public class DownloadActionCRM  {



    @SerializedName("A_NAME")
    private String A_NAME;

    @SerializedName("A_COM_TYPE")
    private String A_COM_TYPE;

    public DownloadActionCRM() {
    }

    public DownloadActionCRM(String a_NAME, String a_COM_TYPE) {
        A_NAME = a_NAME;
        A_COM_TYPE = a_COM_TYPE;
    }

    public String getA_NAME() {
        return A_NAME;
    }

    public void setA_NAME(String a_NAME) {
        A_NAME = a_NAME;
    }

    public String getA_COM_TYPE() {
        return A_COM_TYPE;
    }

    public void setA_COM_TYPE(String a_COM_TYPE) {
        A_COM_TYPE = a_COM_TYPE;
    }

    @Override
    public String toString() {
        return "DownloadActionCRM{" +
                "A_NAME='" + A_NAME + '\'' +
                ", A_COM_TYPE='" + A_COM_TYPE + '\'' +
                '}';
    }
}
