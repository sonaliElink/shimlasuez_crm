package elink.suezShimla.water.crm.androidhsc.hscModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import elink.suezShimla.water.crm.androidhsc.hscModelTable.AreaModel;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.ConnCategoryModel;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.ConnPurposeModel;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.DwellingUnitModel;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.LotModel;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.PropertyTypeModel;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.RejectModel;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.RoadOwnershipModel;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.RoadRestorationLenRoadModel;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.RoadTypeModel;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.SizeModel;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.WardModel;

public class MasterDataForAndroidHSCModel {

    @SerializedName("ConnCategory")
    private List<ConnCategoryModel> connCategoryModelList;

    @SerializedName("PropertyType")
    private List<PropertyTypeModel> propertyTypeModels;

    @SerializedName("ConnPurpose")
    private List<ConnPurposeModel> connPurposeModels;

    @SerializedName("DwellingUnit")
    private List<DwellingUnitModel> dwellingUnitModels;

    @SerializedName("NetworkDistLineSize_MeterSanctionSize")
    private List<SizeModel> sizeModels;

    @SerializedName("RoadRestorationLenRoad")
    private List<RoadRestorationLenRoadModel> roadRestorationLenRoadModels;


    @SerializedName("RoadType")
    private List<RoadTypeModel> roadTypeModels;

    @SerializedName("RoadOwnership")
    private List<RoadOwnershipModel> roadOwnershipModels;

    @SerializedName("Ward")
    private List<WardModel> wardModels;

    @SerializedName("Lot")
    private List<LotModel> lotModels;

    @SerializedName("Area")
    private List<AreaModel> areaModels;


    @SerializedName("Reject")
    private List<RejectModel> rejectModels;


    public MasterDataForAndroidHSCModel(List<ConnCategoryModel> connCategoryModelList, List<PropertyTypeModel> propertyTypeModels, List<ConnPurposeModel> connPurposeModels, List<DwellingUnitModel> dwellingUnitModels, List<SizeModel> sizeModels, List<RoadRestorationLenRoadModel> roadRestorationLenRoadModels, List<RoadTypeModel> roadTypeModels, List<RoadOwnershipModel> roadOwnershipModels, List<WardModel> wardModels, List<LotModel> lotModels, List<AreaModel> areaModels, List<RejectModel> rejectModels) {
        this.connCategoryModelList = connCategoryModelList;
        this.propertyTypeModels = propertyTypeModels;
        this.connPurposeModels = connPurposeModels;
        this.dwellingUnitModels = dwellingUnitModels;
        this.sizeModels = sizeModels;
        this.roadRestorationLenRoadModels = roadRestorationLenRoadModels;
        this.roadTypeModels = roadTypeModels;
        this.roadOwnershipModels = roadOwnershipModels;
        this.wardModels = wardModels;
        this.lotModels = lotModels;
        this.areaModels = areaModels;
        this.rejectModels = rejectModels;
    }

    public MasterDataForAndroidHSCModel() {
    }

    public List<ConnCategoryModel> getConnCategoryModelList() {
        return connCategoryModelList;
    }

    public void setConnCategoryModelList(List<ConnCategoryModel> connCategoryModelList) {
        this.connCategoryModelList = connCategoryModelList;
    }

    public List<PropertyTypeModel> getPropertyTypeModels() {
        return propertyTypeModels;
    }

    public void setPropertyTypeModels(List<PropertyTypeModel> propertyTypeModels) {
        this.propertyTypeModels = propertyTypeModels;
    }

    public List<ConnPurposeModel> getConnPurposeModels() {
        return connPurposeModels;
    }

    public void setConnPurposeModels(List<ConnPurposeModel> connPurposeModels) {
        this.connPurposeModels = connPurposeModels;
    }

    public List<DwellingUnitModel> getDwellingUnitModels() {
        return dwellingUnitModels;
    }

    public void setDwellingUnitModels(List<DwellingUnitModel> dwellingUnitModels) {
        this.dwellingUnitModels = dwellingUnitModels;
    }

    public List<SizeModel> getSizeModels() {
        return sizeModels;
    }

    public void setSizeModels(List<SizeModel> sizeModels) {
        this.sizeModels = sizeModels;
    }

    public List<RoadRestorationLenRoadModel> getRoadRestorationLenRoadModels() {
        return roadRestorationLenRoadModels;
    }

    public void setRoadRestorationLenRoadModels(List<RoadRestorationLenRoadModel> roadRestorationLenRoadModels) {
        this.roadRestorationLenRoadModels = roadRestorationLenRoadModels;
    }

    public List<RoadTypeModel> getRoadTypeModels() {
        return roadTypeModels;
    }

    public void setRoadTypeModels(List<RoadTypeModel> roadTypeModels) {
        this.roadTypeModels = roadTypeModels;
    }

    public List<RoadOwnershipModel> getRoadOwnershipModels() {
        return roadOwnershipModels;
    }

    public void setRoadOwnershipModels(List<RoadOwnershipModel> roadOwnershipModels) {
        this.roadOwnershipModels = roadOwnershipModels;
    }

    public List<WardModel> getWardModels() {
        return wardModels;
    }

    public void setWardModels(List<WardModel> wardModels) {
        this.wardModels = wardModels;
    }

    public List<LotModel> getLotModels() {
        return lotModels;
    }

    public void setLotModels(List<LotModel> lotModels) {
        this.lotModels = lotModels;
    }

    public List<AreaModel> getAreaModels() {
        return areaModels;
    }

    public void setAreaModels(List<AreaModel> areaModels) {
        this.areaModels = areaModels;
    }

    public List<RejectModel> getRejectModels() {
        return rejectModels;
    }

    public void setRejectModels(List<RejectModel> rejectModels) {
        this.rejectModels = rejectModels;
    }

    @Override
    public String toString() {
        return "MasterDataForAndroidHSCModel{" +
                "connCategoryModelList=" + connCategoryModelList +
                ", propertyTypeModels=" + propertyTypeModels +
                ", connPurposeModels=" + connPurposeModels +
                ", dwellingUnitModels=" + dwellingUnitModels +
                ", sizeModels=" + sizeModels +
                ", roadRestorationLenRoadModels=" + roadRestorationLenRoadModels +
                ", roadTypeModels=" + roadTypeModels +
                ", roadOwnershipModels=" + roadOwnershipModels +
                ", wardModels=" + wardModels +
                ", lotModels=" + lotModels +
                ", areaModels=" + areaModels +
                ", rejectModels=" + rejectModels +
                '}';
    }
}
