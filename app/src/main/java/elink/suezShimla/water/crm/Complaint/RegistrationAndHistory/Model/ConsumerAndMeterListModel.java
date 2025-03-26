package elink.suezShimla.water.crm.Complaint.RegistrationAndHistory.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ConsumerAndMeterListModel {

    @SerializedName("CustInfo")
    private List<ConsumerComplaintDetailModel> ConsumerComplaintDetailModel;

    @SerializedName("MeterConnected")
    private List<ConsumerComplaintMeterModel> consumerComplaintMeterModels;

     @SerializedName("History")
    private List<HistoryModel> historyModel;

    public ConsumerAndMeterListModel() {
    }

    public ConsumerAndMeterListModel(List<ConsumerComplaintDetailModel> consumerComplaintDetailModel, List<ConsumerComplaintMeterModel> consumerComplaintMeterModels, List<HistoryModel> historyModel) {
        ConsumerComplaintDetailModel = consumerComplaintDetailModel;
        this.consumerComplaintMeterModels = consumerComplaintMeterModels;
        this.historyModel = historyModel;
    }

    public List<ConsumerComplaintDetailModel> getConsumerComplaintDetailModel() {
        return ConsumerComplaintDetailModel;
    }

    public List<ConsumerComplaintMeterModel> getConsumerComplaintMeterModels() {
        return consumerComplaintMeterModels;
    }

    public List<HistoryModel> getHistoryModel() {
        return historyModel;
    }

    public void setHistoryModel(List<HistoryModel> historyModel) {
        this.historyModel = historyModel;
    }

    @Override
    public String toString() {
        return "ConsumerAndMeterListModel{" +
                "ConsumerComplaintDetailModel=" + ConsumerComplaintDetailModel +
                ", consumerComplaintMeterModels=" + consumerComplaintMeterModels +
                '}';
    }
}
