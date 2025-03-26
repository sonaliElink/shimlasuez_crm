package elink.suezShimla.water.crm.Login.MMG_MasterData;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MDialDigitModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.DMAModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGCgRestroModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGContEmpModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGCvlMeasurementResponseModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGFcRestroModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGMakerCodeModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGMaterialDetailsModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGMeterLocationModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGMeterSizeModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGMeterStatusModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGMeterTypeModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGObersvationModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGRampAndRRModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGRequestTypeModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGSaddleAndPitExcavModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGSubZoneModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGTypeOfRoadcuttingModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGVendorDetModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGWallBoringModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGZoneModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MSRModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MStatusObservationModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.SRModel;

public class MMG_IssuetoMeterFixer_MasterDataModel {

    @SerializedName("Zone")
    private List<MMGZoneModel> issueToMeterZones;

    @SerializedName("SubZone")
    private List<MMGSubZoneModel> issueToMeterSubZones;

    @SerializedName("RequestType")
    private List<MMGRequestTypeModel>issueToMeterRequstType;

    @SerializedName("MakerCode")
    private List<MMGMakerCodeModel> MakerCode;

/*    @SerializedName("MeterPrefix")
    private List<MMGMeterPrefixModel> MeterPrefix;*/

    @SerializedName("Observation")
    private List<MMGObersvationModel> Observation;

    @SerializedName("MeterType")
    private List<MMGMeterTypeModel> MeterType;

   /* @SerializedName("MeterCondition")
    private List<MMGMeterConditionModel> MeterCondition;*/

    @SerializedName("MeterLocation")
    private List<MMGMeterLocationModel> MeterLocation;

    @SerializedName("Roadcutting")
    private List<MMGTypeOfRoadcuttingModel> Roadcutting;

    @SerializedName("Excavation")
    private List<MMGSaddleAndPitExcavModel> Excavation;

    @SerializedName("MeterSize")
    private List<MMGMeterSizeModel> MeterSize;

    @SerializedName("RAMPRR")
    private List<MMGRampAndRRModel> RAMPRR;

    @SerializedName("WallBoring")
    private List<MMGWallBoringModel> WallBoring;

    @SerializedName("CGR")
    private List<MMGCgRestroModel> CGR;

    @SerializedName("FCR")
    private List<MMGFcRestroModel> FCR;

    @SerializedName("MaterialDetails")
    private List<MMGMaterialDetailsModel> MaterialDetails;


    @SerializedName("MeterStatus")
    private List<MMGMeterStatusModel> MeterStatus;

    @SerializedName("Vendor")
    private List<MMGVendorDetModel> Vendor;

    @SerializedName("ContEmp")
    private List<MMGContEmpModel> ContEmp;

    @SerializedName("CivilDetails")
    private List<MMGCvlMeasurementResponseModel> CivilDetails;


    @SerializedName("MStatusObservation")
    private List<MStatusObservationModel> MStatusObservation;

    @SerializedName("MSR")
    private List<MSRModel> MSR;

    @SerializedName("SR")
    private List<SRModel> SR;

    @SerializedName("DMA")
    private List<DMAModel> DMA;


    @SerializedName("DialDigit")
    private List<MDialDigitModel> DialDigit;


    public MMG_IssuetoMeterFixer_MasterDataModel() {

    }

    public MMG_IssuetoMeterFixer_MasterDataModel(List<MMGZoneModel> issueToMeterZones,
                                                 List<MMGSubZoneModel> issueToMeterSubZones,
                                                 List<MMGRequestTypeModel> issueToMeterRequstType,
                                                 List<MMGMakerCodeModel> mmgMakerCodeModels,
                                                 List<MMGObersvationModel> mmgObersvationModels,
                                                 List<MMGMeterTypeModel> mmgMeterTypeModels,
                                                 List<MMGMeterLocationModel> mmgMeterLocation,
                                                 List<MMGTypeOfRoadcuttingModel> mmgRoadcutting,
                                                 List<MMGSaddleAndPitExcavModel> mmgExcavation,
                                                 List<MMGMeterSizeModel> mmgMeterSize,
                                                 List<MMGRampAndRRModel> mmgRAMPRR,
                                                 List<MMGWallBoringModel> mmgWallBoring,
                                                 List<MMGCgRestroModel> mmgCGR,
                                                 List<MMGFcRestroModel> mmgFCR,
                                                 List<MMGMaterialDetailsModel> MaterialDetails,
                                                 List<MMGMeterStatusModel> meterStatus,
                                                 List<MMGVendorDetModel> vendor,
                                                 List<MMGContEmpModel> contEmp,
                                                 List<MMGCvlMeasurementResponseModel> civilDetails,
                                                 List<MStatusObservationModel> mStatusObservationModels,
                                                 List<MSRModel> msrModels,
                                                 List<SRModel> srModels,
                                                 List<DMAModel> dmaModels,
                                                 List<MDialDigitModel> mDialDigitModels)

    {
        this.issueToMeterZones = issueToMeterZones;
        this.issueToMeterSubZones = issueToMeterSubZones;
        this.issueToMeterRequstType = issueToMeterRequstType;
        this.MakerCode = mmgMakerCodeModels;
        this.Observation = mmgObersvationModels;
        this.MeterType = mmgMeterTypeModels;
       // this.MeterCondition = mmgMeterConditionModels;
        this.MeterLocation = mmgMeterLocation;
        this.Roadcutting = mmgRoadcutting;
        this.Excavation = mmgExcavation;
        this.MeterSize = mmgMeterSize;
        this.RAMPRR = mmgRAMPRR;
        this.WallBoring = mmgWallBoring;
        this.CGR = mmgCGR;
        this.FCR = mmgFCR;
        this.MaterialDetails = MaterialDetails;
        this.MeterStatus = meterStatus;
        this.Vendor = vendor;
        this.ContEmp = contEmp;
        this.CivilDetails = civilDetails;
        this.MStatusObservation = mStatusObservationModels;
        this.MSR = msrModels;
        this.SR = srModels;
        this.DMA = dmaModels;
        this.DialDigit = mDialDigitModels;
    }

    public List<MMGZoneModel> getissueToMeterZones() {
        return issueToMeterZones;
    }

    public List<MMGSubZoneModel> getissueToMeterSubZones() {
        return issueToMeterSubZones;
    }

    public List<MMGRequestTypeModel> getissueToMeterRequstType() {
        return issueToMeterRequstType;
    }
    public List<MMGMakerCodeModel> getMakerCode() {
        return MakerCode;
    }
   /* public List<MMGMeterPrefixModel> getMeterPrefix() {
        return MeterPrefix;
    }*/
    public List<MMGObersvationModel> getObersvation() {
        return Observation;
    }

    public List<MMGMeterTypeModel> getMeterType() {
        return MeterType;
    }
    /*public List<MMGMeterConditionModel> getMeterCondition() {
        return MeterCondition;} */

    public List<MMGMeterLocationModel> getMeterLocation() {
        return MeterLocation;
    }

    public List<MMGTypeOfRoadcuttingModel> getRoadcutting() {
        return Roadcutting;
    }

    public List<MMGSaddleAndPitExcavModel> getExcavation() {
        return Excavation;
    }

    public List<MMGMeterSizeModel> getMeterSizeModel() {
        return MeterSize;
    }

    public List<MMGRampAndRRModel> getRAMPRR() {
        return RAMPRR;
    }

    public List<MMGWallBoringModel> getWallBoring() {
        return WallBoring;
    }

    public List<MMGCgRestroModel> getCGR() {
        return CGR;
    }

    public List<MMGFcRestroModel> getFCR() {
        return FCR;
    }

    public List<MMGMaterialDetailsModel> getMaterialDetails() {
        return MaterialDetails;
    }

    public List<MMGMeterStatusModel> getMeterStatus() {
        return MeterStatus;
    }

    public List<MMGVendorDetModel> getVendor() {
        return Vendor;
    }

    public List<MMGContEmpModel> getContEmp() {
        return ContEmp;
    }

    public List<MMGCvlMeasurementResponseModel> getCivilDetails() {
        return CivilDetails;
    }

    public List<MStatusObservationModel> getMStatusObservation() {
        return MStatusObservation;
    }

    public List<MSRModel> getMSR() {
        return MSR;
    }

    public List<SRModel> getSR() {
        return SR;
    }

    public List<DMAModel> getDMA() {
        return DMA;
    }

    public List<MDialDigitModel> getDialDigit() {
        return DialDigit;
    }



    @Override
    public String toString() {
        return "MMG_IssuetoMeterFixer_MasterDataModel{" +
                "Zone=" + issueToMeterZones +
                ", SubZone=" + issueToMeterSubZones +
                ", RequestType=" + issueToMeterRequstType +
                ", MakerCode=" + MakerCode +
                ", Observation=" + Observation +
                ", MeterType=" + MeterType +
                ", MeterLocation=" + MeterLocation +
                ", Roadcutting=" + Roadcutting +
                ", Excavation=" + Excavation +
                ", MeterSize=" + MeterSize +
                ", RAMPRR=" + RAMPRR +
                ", WallBoring=" + WallBoring +
                ", CGR=" + CGR +
                ", FCR=" + FCR +
                ", MaterialDetails=" + MaterialDetails +
                ", MeterStatus=" + MeterStatus +
                ", Vendor=" + Vendor +
                ", ContEmp=" + ContEmp +
                ", CivilDetails=" + CivilDetails +
                ", MStatusObservation=" + MStatusObservation +
                ", MSR=" + MSR +
                ", SR=" + SR +
                ", DMA=" + DMA +
                ", DialDigit=" + DialDigit +
                '}';
    }

}

