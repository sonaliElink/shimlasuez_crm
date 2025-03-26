package elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGValidateDetails;


public class MMGGetDetailsResponseModel {
    @SerializedName("CustInfo")
    private List<MMGCustDetModel> MMGCustomerDetails;

    @SerializedName("MeterConnected")
    private List<MMGMeterConnectedDetailsModel> MMGMeterConnectionDetails;

    @SerializedName("Validate")
    private List<MMGValidateDetails> mmgValidateDetails;


    @SerializedName("InstallDetails")
    private List<InstallDetails> installDetailsList;

    public MMGGetDetailsResponseModel(List<MMGCustDetModel> MMGCustomerDetails, List<MMGMeterConnectedDetailsModel> MMGMeterConnectionDetails,List<MMGValidateDetails> mmgValidateDetails,List<InstallDetails> installDetailsList) {
        this.MMGCustomerDetails = MMGCustomerDetails;
        this.MMGMeterConnectionDetails = MMGMeterConnectionDetails;
        this.mmgValidateDetails = mmgValidateDetails;
        this.installDetailsList = installDetailsList;
    }

    public List<MMGCustDetModel> getMMGCustomerDetails() {
        return MMGCustomerDetails;
    }

    public List<MMGValidateDetails> getMmgValidateDetails() {
        return mmgValidateDetails;
    }

    public void setMmgValidateDetails(List<MMGValidateDetails> mmgValidateDetails) {
        this.mmgValidateDetails = mmgValidateDetails;
    }

    public void setMMGCustomerDetails(List<MMGCustDetModel> MMGCustomerDetails) {
        this.MMGCustomerDetails = MMGCustomerDetails;
    }

    public List<MMGMeterConnectedDetailsModel> getMMGMeterConnectionDetails() {
        return MMGMeterConnectionDetails;
    }

    public void setMMGMeterConnectionDetails(List<MMGMeterConnectedDetailsModel> MMGMeterConnectionDetails) {
        this.MMGMeterConnectionDetails = MMGMeterConnectionDetails;
    }

    public List<InstallDetails> getInstallDetailsList() {
        return installDetailsList;
    }

    public void setInstallDetailsList(List<InstallDetails> installDetailsList) {
        this.installDetailsList = installDetailsList;
    }

    @Override
    public String toString() {
        return "MMGGetDetailsResponseModel{" +
                "MMGCustomerDetails=" + MMGCustomerDetails +
                ", MMGMeterConnectionDetails=" + MMGMeterConnectionDetails +
                ", mmgValidateDetails=" + mmgValidateDetails +
                ", installDetailsList=" + installDetailsList +
                '}';
    }
}
