package elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Annexure6;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class ContractorDetailAnnex6 extends RealmObject {
    @SerializedName("CONTRACTOR_NAME")
    private int CONTRACTOR_NAME;

    @SerializedName("VENDOR_CODE")
    private String VENDOR_CODE;

    @SerializedName("OTHER_CODE")
    private String OTHER_CODE;

    @SerializedName("CONTRACTOR_EMPLOYEE")
    private String CONTRACTOR_EMPLOYEE;

    @SerializedName("REMOVED_MATERIAL_HANDOVER")
    private int REMOVED_MATERIAL_HANDOVER;

    public ContractorDetailAnnex6() {
    }

    public ContractorDetailAnnex6(int CONTRACTOR_NAME, String VENDOR_CODE, String OTHER_CODE,
                                  String CONTRACTOR_EMPLOYEE, int REMOVED_MATERIAL_HANDOVER) {
        this.CONTRACTOR_NAME = CONTRACTOR_NAME;
        this.VENDOR_CODE = VENDOR_CODE;
        this.OTHER_CODE = OTHER_CODE;
        this.CONTRACTOR_EMPLOYEE = CONTRACTOR_EMPLOYEE;
        this.REMOVED_MATERIAL_HANDOVER = REMOVED_MATERIAL_HANDOVER;
    }

    public int getCONTRACTOR_NAME() {
        return CONTRACTOR_NAME;
    }

    public String getVENDOR_CODE() {
        return VENDOR_CODE;
    }

    public String getOTHER_CODE() {
        return OTHER_CODE;
    }

    public String getCONTRACTOR_EMPLOYEE() {
        return CONTRACTOR_EMPLOYEE;
    }

    public int getREMOVED_MATERIAL_HANDOVER() {
        return REMOVED_MATERIAL_HANDOVER;
    }

    @Override
    public String toString() {
        return "ContractorDetailAnnex6{" +
                "CONTRACTOR_NAME=" + CONTRACTOR_NAME +
                ", VENDOR_CODE='" + VENDOR_CODE + '\'' +
                ", OTHER_CODE='" + OTHER_CODE + '\'' +
                ", CONTRACTOR_EMPLOYEE='" + CONTRACTOR_EMPLOYEE + '\'' +
                ", REMOVED_MATERIAL_HANDOVER=" + REMOVED_MATERIAL_HANDOVER +
                '}';
    }
}
