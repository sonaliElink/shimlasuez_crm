package elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Material;

import io.realm.RealmObject;

public class MaterialModel extends RealmObject {
    int sequenceId;
    String materialId;
    String materialName;
    String materialVisible;
    String defaultQuantity;
    String sizeId;
    String MRM_UOM_ID;
    String MRM_UOM_name;

    public MaterialModel(){
    }

    public MaterialModel(int sequenceId, String materialId, String materialName, String materialVisible, String defaultQuantity, String sizeId,String MRM_UOM_ID,String MRM_UOM_name) {
        this.sequenceId = sequenceId;
        this.materialId = materialId;
        this.materialName = materialName;
        this.materialVisible = materialVisible;
        this.defaultQuantity = defaultQuantity;
        this.sizeId = sizeId;
        this.MRM_UOM_ID = MRM_UOM_ID;
        this.MRM_UOM_name = MRM_UOM_name;
    }

    public int getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(int sequenceId) {
        this.sequenceId = sequenceId;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialVisible() {
        return materialVisible;
    }

    public void setMaterialVisible(String materialVisible) {
        this.materialVisible = materialVisible;
    }

    public String getDefaultQuantity() {
        return defaultQuantity;
    }

    public void setDefaultQuantity(String defaultQuantity) {
        this.defaultQuantity = defaultQuantity;
    }

    public String getSizeId() {
        return sizeId;
    }

    public void setSizeId(String sizeId) {
        this.sizeId = sizeId;
    }

    public String getMRM_UOM_ID() {
        return MRM_UOM_ID;
    }

    public void setMRM_UOM_ID(String MRM_UOM_ID) {
        this.MRM_UOM_ID = MRM_UOM_ID;
    }

    public String getMRM_UOM_name() {
        return MRM_UOM_name;
    }

    public void setMRM_UOM_name(String MRM_UOM_name) {
        this.MRM_UOM_name = MRM_UOM_name;
    }

    @Override
    public String toString() {
        return "MaterialModel{" +
                "sequenceId=" + sequenceId +
                ", materialId='" + materialId + '\'' +
                ", materialName='" + materialName + '\'' +
                ", materialVisible='" + materialVisible + '\'' +
                ", defaultQuantity='" + defaultQuantity + '\'' +
                ", sizeId='" + sizeId + '\'' +
                ", MRM_UOM_ID='" + MRM_UOM_ID + '\'' +
                ", MRM_UOM_name='" + MRM_UOM_name + '\'' +
                '}';
    }
}
