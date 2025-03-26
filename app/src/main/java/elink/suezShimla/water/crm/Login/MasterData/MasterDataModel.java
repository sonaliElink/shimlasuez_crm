package elink.suezShimla.water.crm.Login.MasterData;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.DMAModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MeterObservationModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MeterStatusModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.SRModel;
import elink.suezShimla.water.crm.Login.MasterData.Download.DocSource;
import elink.suezShimla.water.crm.Login.MasterData.Download.DocSubType;
import elink.suezShimla.water.crm.Login.MasterData.Download.DocType;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadActionCRM;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadComplaintBy;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadConnCategory;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadConnSize;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadCustomerType;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadFinishAction;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadComplaintSource;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadComplaintSubType;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadComplaintType;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadSiteEngineer;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadSourceType;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadSubZone;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadZone;
import elink.suezShimla.water.crm.Login.MasterData.Download.WorkCompObservation;
import elink.suezShimla.water.crm.Shantanu.ModelPackage.BankMasterModel;
import elink.suezShimla.water.crm.Shantanu.ModelPackage.ConnectionStatusModel;

public class MasterDataModel {

    @SerializedName("ComplaintType")
    private List<DownloadComplaintType> downloadComplaintTypes;

    @SerializedName("ComplaintSubType")
    private List<DownloadComplaintSubType> downloadComplaintSubTypes;

    @SerializedName("ComplaintSource")
    private List<DownloadComplaintSource> downloadComplaintSources;

    @SerializedName("Zone")
    private List<DownloadZone> downloadZones;

    @SerializedName("SubZone")
    private List<DownloadSubZone> downloadSubZones;

    @SerializedName("SiteEngineer")
    private List<DownloadSiteEngineer> downloadSiteEngineers;

    @SerializedName("Action")
    private List<DownloadFinishAction> downloadFinishActions;

    @SerializedName("ComplaintBy")
    private List<DownloadComplaintBy> downloadComplaintBy;

    @SerializedName("CustType")
    private List<DownloadCustomerType> CustType;

    @SerializedName("Source")
    private List<DownloadSourceType> Source;

    @SerializedName("ActionCRM")
    private List<DownloadActionCRM> actionCRM;

    @SerializedName("WorkCompObservation")
    private List<WorkCompObservation> workCompObservations;

    @SerializedName("DocSource")
    private List<DocSource> docSources;

    @SerializedName("DocType")
    private List<DocType> docTypes;

    @SerializedName("DocSubType")
    private List<DocSubType> docSubTypes;

    @SerializedName("SR")
    private List<SRModel> srModels;

    @SerializedName("DMA")
    private List<DMAModel> dmaModel;

    @SerializedName("MeterStatus")
    private List<MeterStatusModel> MeterStatus;

    @SerializedName("MeterObservation")
    private List<MeterObservationModel> MeterObservation;

    @SerializedName("ConnCategory")
    private List<DownloadConnCategory> ConnCategory;

    @SerializedName("ConnSize")
    private List<DownloadConnSize> ConnSize;

    @SerializedName("BankMaster")
    private List<BankMasterModel> BankMaster;

    @SerializedName("ConnectionStatus")
    private List<ConnectionStatusModel> ConnectionStatus;

    public MasterDataModel() {
    }

    public MasterDataModel(List<DownloadComplaintType> downloadComplaintTypes, List<DownloadComplaintSubType> downloadComplaintSubTypes, List<DownloadComplaintSource> downloadComplaintSources, List<DownloadZone> downloadZones, List<DownloadSubZone> downloadSubZones, List<DownloadSiteEngineer> downloadSiteEngineers, List<DownloadFinishAction> downloadFinishActions,
                           List<DownloadComplaintBy> downloadComplaintBy, List<DownloadCustomerType> custType, List<DownloadSourceType> source, List<DownloadActionCRM> actionCRM, List<WorkCompObservation> workCompObservations, List<DocSource> docSources, List<DocType> docTypes, List<DocSubType> docSubTypes,
                           List<SRModel> srModels, List<DMAModel> dmaModels, List<MeterStatusModel> meterStatus, List<MeterObservationModel> meterObservation, List<DownloadConnCategory> connCategory,List<DownloadConnSize> connSize,List<BankMasterModel> BankMaster, List<ConnectionStatusModel> cStatus) {
        this.downloadComplaintTypes = downloadComplaintTypes;
        this.downloadComplaintSubTypes = downloadComplaintSubTypes;
        this.downloadComplaintSources = downloadComplaintSources;
        this.downloadZones = downloadZones;
        this.downloadSubZones = downloadSubZones;
        this.downloadSiteEngineers = downloadSiteEngineers;
        this.downloadFinishActions = downloadFinishActions;
        this.downloadComplaintBy = downloadComplaintBy;
        CustType = custType;
        Source = source;
        actionCRM = actionCRM;
        workCompObservations = workCompObservations;
        this.docSources = docSources;
        this.docTypes = docTypes;
        this.docSubTypes = docSubTypes;
        this.srModels = srModels;
        this.dmaModel = dmaModels;
        this.MeterStatus = meterStatus;
        this.MeterObservation = meterObservation;
        this.ConnCategory = connCategory;
        this.ConnSize = connSize;
        this.BankMaster = BankMaster;
        this.ConnectionStatus= cStatus;
    }

    public List<ConnectionStatusModel> getConnectionStatus() {
        return ConnectionStatus;
    }

    public void setConnectionStatus(List<ConnectionStatusModel> connectionStatus) {
        ConnectionStatus = connectionStatus;
    }

    public List<DownloadComplaintType> getDownloadComplaintTypes() {
        return downloadComplaintTypes;
    }

    public List<DownloadComplaintSubType> getDownloadComplaintSubTypes() {
        return downloadComplaintSubTypes;
    }

    public List<DownloadComplaintSource> getDownloadComplaintSources() {
        return downloadComplaintSources;
    }

    public List<DownloadZone> getDownloadZones() {
        return downloadZones;
    }

    public List<DownloadSubZone> getDownloadSubZones() {
        return downloadSubZones;
    }

    public List<DownloadSiteEngineer> getDownloadSiteEngineers() {
        return downloadSiteEngineers;
    }

    public List<DownloadFinishAction> getDownloadFinishActions() {
        return downloadFinishActions;
    }

    public List<DownloadComplaintBy> getDownloadComplaintBy() {
        return downloadComplaintBy;
    }

    public List<DownloadCustomerType> getCustType() {
        return CustType;
    }

    public List<DownloadSourceType> getSource() {
        return Source;
    }


    public List<DownloadActionCRM> getActionCRM() {
        return actionCRM;
    }

    public void setActionCRM(List<DownloadActionCRM> actionCRM) {
        this.actionCRM = actionCRM;
    }

    public List<WorkCompObservation> getWorkCompObservations() {
        return workCompObservations;
    }

    public void setWorkCompObservations(List<WorkCompObservation> workCompObservations) {
        this.workCompObservations = workCompObservations;
    }

    public List<DocSource> getDocSources() {
        return docSources;
    }

    public void setDocSources(List<DocSource> docSources) {
        this.docSources = docSources;
    }

    public List<DocType> getDocTypes() {
        return docTypes;
    }

    public void setDocTypes(List<DocType> docTypes) {
        this.docTypes = docTypes;
    }

    public List<DocSubType> getDocSubTypes() {
        return docSubTypes;
    }

    public void setDocSubTypes(List<DocSubType> docSubTypes) {
        this.docSubTypes = docSubTypes;
    }


    public List<SRModel> getSrModels() {
        return srModels;
    }

    public void setSrModels(List<SRModel> srModels) {
        this.srModels = srModels;
    }

    public List<DMAModel> getDmaModel() {
        return dmaModel;
    }

    public void setDmaModel(List<DMAModel> dmaModel) {
        this.dmaModel = dmaModel;
    }

    public List<MeterStatusModel> getMeterStatus() {
        return MeterStatus;
    }

    public void setMeterStatus(List<MeterStatusModel> meterStatus) {
        MeterStatus = meterStatus;
    }

    public List<MeterObservationModel> getMeterObservation() {
        return MeterObservation;
    }

    public void setMeterObservation(List<MeterObservationModel> meterObservation) {
        MeterObservation = meterObservation;
    }

    public List<DownloadConnCategory> getConnCategory() {
        return ConnCategory;
    }

    public void setConnCategory(List<DownloadConnCategory> connCategory) {
        ConnCategory = connCategory;
    }

    public List<DownloadConnSize> getConnSize() {
        return ConnSize;
    }

    public void setConnSize(List<DownloadConnSize> connSize) {
        ConnSize = connSize;
    }

    public List<BankMasterModel> getBankMaster() {
        return BankMaster;
    }

    public void setBankMaster(List<BankMasterModel> BankMaster) {
        this.BankMaster = BankMaster;
    }

    @Override
    public String toString() {
        return "MasterDataModel{" +
                "downloadComplaintTypes=" + downloadComplaintTypes +
                ", downloadComplaintSubTypes=" + downloadComplaintSubTypes +
                ", downloadComplaintSources=" + downloadComplaintSources +
                ", downloadZones=" + downloadZones +
                ", downloadSubZones=" + downloadSubZones +
                ", downloadSiteEngineers=" + downloadSiteEngineers +
                ", downloadFinishActions=" + downloadFinishActions +
                ", downloadComplaintBy=" + downloadComplaintBy +
                ", CustType=" + CustType +
                ", Source=" + Source +
                ", actionCRM=" + actionCRM +
                ", workCompObservations=" + workCompObservations +
                ", docSources=" + docSources +
                ", docTypes=" + docTypes +
                ", docSubTypes=" + docSubTypes +
                ", srModels=" + srModels +
                ", dmaModel=" + dmaModel +
                ", MeterStatus=" + MeterStatus +
                ", MeterObservation=" + MeterObservation +
                ", ConnCategory=" + ConnCategory +
                ", ConnSize=" + ConnSize +
                ", BankMaster=" + BankMaster +
                ", ConnectionStatus=" + ConnectionStatus +
                '}';
    }
}
