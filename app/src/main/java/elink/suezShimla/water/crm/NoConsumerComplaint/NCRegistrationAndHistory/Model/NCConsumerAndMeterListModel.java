package elink.suezShimla.water.crm.NoConsumerComplaint.NCRegistrationAndHistory.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class NCConsumerAndMeterListModel {
    @SerializedName("CustInfo")
    private List<NCConsumerComplaintDetailModel>    ncConsumerComplaintDetailModel;

    @SerializedName("MeterConnected")
    private List<NCConsumerComplaintMeterModel> ncconsumerComplaintMeterModels;

    public NCConsumerAndMeterListModel() {
    }

    public NCConsumerAndMeterListModel(List<NCConsumerComplaintDetailModel> ncConsumerComplaintDetailModel1, List<NCConsumerComplaintMeterModel> ncconsumerComplaintMeterModels) {
        ncConsumerComplaintDetailModel = ncConsumerComplaintDetailModel1;
        this.ncconsumerComplaintMeterModels = ncconsumerComplaintMeterModels;
    }

    public List<NCConsumerComplaintDetailModel> getConsumerComplaintDetailModel() {
        return ncConsumerComplaintDetailModel;
    }

    public List<NCConsumerComplaintMeterModel> getNCConsumerComplaintMeterModels() {
        return ncconsumerComplaintMeterModels;
    }

    @Override
    public String toString() {
        return "NCConsumerAndMeterListModel{" +
                "ncConsumerComplaintDetailModel=" + ncConsumerComplaintDetailModel +
                ", ncconsumerComplaintMeterModels=" + ncconsumerComplaintMeterModels +
                '}';
    }
}
