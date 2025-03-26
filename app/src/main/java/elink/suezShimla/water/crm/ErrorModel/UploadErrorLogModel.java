package elink.suezShimla.water.crm.ErrorModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UploadErrorLogModel {

    @SerializedName("Status")
    private List<UploadErroLogStatusModel> UploadStatusModel;

    public UploadErrorLogModel() {
    }

    public List<UploadErroLogStatusModel> getUploadStatusModel() {
        return UploadStatusModel;
    }

    public void setUploadStatusModel(List<UploadErroLogStatusModel> uploadStatusModel) {
        UploadStatusModel = uploadStatusModel;
    }

    @Override
    public String toString() {
        return "UploadErrorLogModel{" +
                "UploadStatusModel=" + UploadStatusModel +
                '}';
    }
}
