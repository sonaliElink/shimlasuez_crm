package elink.suezShimla.water.crm.ConnectionRequest.UploadDocuments.model;

import java.util.List;

public class UploadDocumentModel {

    private String proofName;
    private String proofDescription;
    private List<SelectedDocumentModel> selectDocumentModelList;
    private Boolean isSelected;

    public UploadDocumentModel() {
    }

    public UploadDocumentModel(String proofName, String proofDescription, List<SelectedDocumentModel> selectDocumentModelList, Boolean isSelected) {
        this.proofName = proofName;
        this.proofDescription = proofDescription;
        this.selectDocumentModelList = selectDocumentModelList;
        this.isSelected = isSelected;
    }

    public String getProofName() {
        return proofName;
    }

    public void setProofName(String proofName) {
        this.proofName = proofName;
    }

    public String getProofDescription() {
        return proofDescription;
    }

    public void setProofDescription(String proofDescription) {
        this.proofDescription = proofDescription;
    }

    public List<SelectedDocumentModel> getSelectDocumentModelList() {
        return selectDocumentModelList;
    }

    public void setSelectDocumentModelList(List<SelectedDocumentModel> selectDocumentModelList) {
        this.selectDocumentModelList = selectDocumentModelList;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "UploadDocumentModel{" +
                "proofName='" + proofName + '\'' +
                ", proofDescription='" + proofDescription + '\'' +
                ", selectDocumentModelList=" + selectDocumentModelList +
                ", isSelected=" + isSelected +
                '}';
    }
}
