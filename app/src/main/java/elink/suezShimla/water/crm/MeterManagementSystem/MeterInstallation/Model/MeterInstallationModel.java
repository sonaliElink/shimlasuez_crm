package elink.suezShimla.water.crm.MeterManagementSystem.MeterInstallation.Model;

import com.google.gson.annotations.SerializedName;

public class MeterInstallationModel {

    int srNo;

    @SerializedName("REQUESTNO")
    String referenceNo;

    @SerializedName("CONSUMERNO")
    String consummerNo;

    @SerializedName("PHASE")
    String phase;

    String risingMains;

    @SerializedName("INSTALLREPLACEDDATE")
    String dtc;

    @SerializedName("REQUESTTYPE")
    String serviceType;

    @SerializedName("OLDMETERNO")
    String oldMeterNO;


    String fixerName;


    String contactNO;


    String nameAddress;


    String observation;

    @SerializedName("CONSUMERTYPE")
    String requestSubType;



    public MeterInstallationModel() {
    }

    public MeterInstallationModel(int srNo, String referenceNo, String consummerNo, String phase, String risingMains, String dtc, String serviceType, String oldMeterNO, String fixerName, String contactNO, String nameAddress, String observation, String requestSubType) {
        this.srNo = srNo;
        this.referenceNo = referenceNo;
        this.consummerNo = consummerNo;
        this.phase = phase;
        this.risingMains = risingMains;
        this.dtc = dtc;
        this.serviceType = serviceType;
        this.oldMeterNO = oldMeterNO;
        this.fixerName = fixerName;
        this.contactNO = contactNO;
        this.nameAddress = nameAddress;
        this.observation = observation;
        this.requestSubType = requestSubType;
    }

    public int getSrNo() {
        return srNo;
    }

    public void setSrNo(int srNo) {
        this.srNo = srNo;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getConsummerNo() {
        return consummerNo;
    }

    public void setConsummerNo(String consummerNo) {
        this.consummerNo = consummerNo;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getRisingMains() {
        return risingMains;
    }

    public void setRisingMains(String risingMains) {
        this.risingMains = risingMains;
    }

    public String getDtc() {
        return dtc;
    }

    public void setDtc(String dtc) {
        this.dtc = dtc;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getOldMeterNO() {
        return oldMeterNO;
    }

    public void setOldMeterNO(String oldMeterNO) {
        this.oldMeterNO = oldMeterNO;
    }

    public String getFixerName() {
        return fixerName;
    }

    public void setFixerName(String fixerName) {
        this.fixerName = fixerName;
    }

    public String getContactNO() {
        return contactNO;
    }

    public void setContactNO(String contactNO) {
        this.contactNO = contactNO;
    }

    public String getNameAddress() {
        return nameAddress;
    }

    public void setNameAddress(String nameAddress) {
        this.nameAddress = nameAddress;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getRequestSubType() {
        return requestSubType;
    }

    public void setRequestSubType(String requestSubType) {
        this.requestSubType = requestSubType;
    }

    @Override
    public String toString() {
        return "MeterInstallationModel{" +
                "srNo=" + srNo +
                ", referenceNo='" + referenceNo + '\'' +
                ", consummerNo='" + consummerNo + '\'' +
                ", phase='" + phase + '\'' +
                ", risingMains='" + risingMains + '\'' +
                ", dtc='" + dtc + '\'' +
                ", serviceType='" + serviceType + '\'' +
                ", oldMeterNO='" + oldMeterNO + '\'' +
                ", fixerName='" + fixerName + '\'' +
                ", contactNO='" + contactNO + '\'' +
                ", nameAddress='" + nameAddress + '\'' +
                ", observation='" + observation + '\'' +
                ", requestSubType='" + requestSubType + '\'' +
                '}';
    }
}
