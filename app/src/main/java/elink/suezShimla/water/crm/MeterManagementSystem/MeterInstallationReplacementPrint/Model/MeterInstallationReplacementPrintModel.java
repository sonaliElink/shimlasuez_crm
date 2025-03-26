package elink.suezShimla.water.crm.MeterManagementSystem.MeterInstallationReplacementPrint.Model;

public class MeterInstallationReplacementPrintModel {
    private String serviceNo;
    private String serviceTodType;
    private String requestNo;
    private String requestType;
    private String oldMeterNo;
    private String oldMeterType;
    private String oldMeterReading;
    private String newMeterNo;
    private String newMeterTodType;
    private String newMeterInitReading;
    private String phase;
    private String meterIndicator;
    private String meterInstalledReplacedDate;
    private String meterInstalledReplaced;
    private String meterNotInstalledReplacedReason;

    public MeterInstallationReplacementPrintModel(){}

    public MeterInstallationReplacementPrintModel(String serviceNo, String serviceTodType, String requestNo, String requestType, String oldMeterNo, String oldMeterType, String oldMeterReading, String newMeterNo, String newMeterTodType, String newMeterInitReading, String phase, String meterIndicator, String meterInstalledReplacedDate, String meterInstalledReplaced, String meterNotInstalledReplacedReason) {
        this.serviceNo = serviceNo;
        this.serviceTodType = serviceTodType;
        this.requestNo = requestNo;
        this.requestType = requestType;
        this.oldMeterNo = oldMeterNo;
        this.oldMeterType = oldMeterType;
        this.oldMeterReading = oldMeterReading;
        this.newMeterNo = newMeterNo;
        this.newMeterTodType = newMeterTodType;
        this.newMeterInitReading = newMeterInitReading;
        this.phase = phase;
        this.meterIndicator = meterIndicator;
        this.meterInstalledReplacedDate = meterInstalledReplacedDate;
        this.meterInstalledReplaced = meterInstalledReplaced;
        this.meterNotInstalledReplacedReason = meterNotInstalledReplacedReason;
    }

    public String getServiceNo() {
        return serviceNo;
    }

    public void setServiceNo(String serviceNo) {
        this.serviceNo = serviceNo;
    }

    public String getServiceTodType() {
        return serviceTodType;
    }

    public void setServiceTodType(String serviceTodType) {
        this.serviceTodType = serviceTodType;
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getOldMeterNo() {
        return oldMeterNo;
    }

    public void setOldMeterNo(String oldMeterNo) {
        this.oldMeterNo = oldMeterNo;
    }

    public String getOldMeterType() {
        return oldMeterType;
    }

    public void setOldMeterType(String oldMeterType) {
        this.oldMeterType = oldMeterType;
    }

    public String getOldMeterReading() {
        return oldMeterReading;
    }

    public void setOldMeterReading(String oldMeterReading) {
        this.oldMeterReading = oldMeterReading;
    }

    public String getNewMeterNo() {
        return newMeterNo;
    }

    public void setNewMeterNo(String newMeterNo) {
        this.newMeterNo = newMeterNo;
    }

    public String getNewMeterTodType() {
        return newMeterTodType;
    }

    public void setNewMeterTodType(String newMeterTodType) {
        this.newMeterTodType = newMeterTodType;
    }

    public String getNewMeterInitReading() {
        return newMeterInitReading;
    }

    public void setNewMeterInitReading(String newMeterInitReading) {
        this.newMeterInitReading = newMeterInitReading;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getMeterIndicator() {
        return meterIndicator;
    }

    public void setMeterIndicator(String meterIndicator) {
        this.meterIndicator = meterIndicator;
    }

    public String getMeterInstalledReplacedDate() {
        return meterInstalledReplacedDate;
    }

    public void setMeterInstalledReplacedDate(String meterInstalledReplacedDate) {
        this.meterInstalledReplacedDate = meterInstalledReplacedDate;
    }

    public String getMeterInstalledReplaced() {
        return meterInstalledReplaced;
    }

    public void setMeterInstalledReplaced(String meterInstalledReplaced) {
        this.meterInstalledReplaced = meterInstalledReplaced;
    }

    public String getMeterNotInstalledReplacedReason() {
        return meterNotInstalledReplacedReason;
    }

    public void setMeterNotInstalledReplacedReason(String meterNotInstalledReplacedReason) {
        this.meterNotInstalledReplacedReason = meterNotInstalledReplacedReason;
    }

    @Override
    public String toString() {
        return "MeterInstallationReplacementPrintModel{" +
                "serviceNo='" + serviceNo + '\'' +
                ", serviceTodType='" + serviceTodType + '\'' +
                ", requestNo='" + requestNo + '\'' +
                ", requestType='" + requestType + '\'' +
                ", oldMeterNo='" + oldMeterNo + '\'' +
                ", oldMeterType='" + oldMeterType + '\'' +
                ", oldMeterReading='" + oldMeterReading + '\'' +
                ", newMeterNo='" + newMeterNo + '\'' +
                ", newMeterTodType='" + newMeterTodType + '\'' +
                ", newMeterInitReading='" + newMeterInitReading + '\'' +
                ", phase='" + phase + '\'' +
                ", meterIndicator='" + meterIndicator + '\'' +
                ", meterInstalledReplacedDate='" + meterInstalledReplacedDate + '\'' +
                ", meterInstalledReplaced='" + meterInstalledReplaced + '\'' +
                ", meterNotInstalledReplacedReason='" + meterNotInstalledReplacedReason + '\'' +
                '}';
    }
}
