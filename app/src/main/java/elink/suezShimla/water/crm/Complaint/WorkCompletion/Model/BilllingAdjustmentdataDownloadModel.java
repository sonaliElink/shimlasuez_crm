package elink.suezShimla.water.crm.Complaint.WorkCompletion.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BilllingAdjustmentdataDownloadModel {

    @SerializedName("AdjustmentType")
    private List<DownloadAdjustmentType> downloadAdjustmentTypes;

  @SerializedName("MeterStatus")
    private List<DownloadMeterStatusBillAdjust>downloadMeterStatusBillAdjusts ;

    public BilllingAdjustmentdataDownloadModel(List<DownloadAdjustmentType> downloadAdjustmentTypes, List<DownloadMeterStatusBillAdjust> downloadMeterStatusBillAdjusts) {
        this.downloadAdjustmentTypes = downloadAdjustmentTypes;
        this.downloadMeterStatusBillAdjusts = downloadMeterStatusBillAdjusts;
    }

    public BilllingAdjustmentdataDownloadModel() {
    }

    public List<DownloadAdjustmentType> getDownloadAdjustmentTypes() {
        return downloadAdjustmentTypes;
    }

    public void setDownloadAdjustmentTypes(List<DownloadAdjustmentType> downloadAdjustmentTypes) {
        this.downloadAdjustmentTypes = downloadAdjustmentTypes;
    }

    public List<DownloadMeterStatusBillAdjust> getDownloadMeterStatusBillAdjusts() {
        return downloadMeterStatusBillAdjusts;
    }

    public void setDownloadMeterStatusBillAdjusts(List<DownloadMeterStatusBillAdjust> downloadMeterStatusBillAdjusts) {
        this.downloadMeterStatusBillAdjusts = downloadMeterStatusBillAdjusts;
    }

    @Override
    public String toString() {
        return "BilllingAdjustmentdataDownloadModel{" +
                "downloadAdjustmentTypes=" + downloadAdjustmentTypes +
                ", downloadMeterStatusBillAdjusts=" + downloadMeterStatusBillAdjusts +
                '}';
    }


}
