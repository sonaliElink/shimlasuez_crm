package elink.suezShimla.water.crm.ConnectionRequest.UploadDocuments.model;

import android.graphics.Bitmap;

public class SelectedDocumentModel {

    private String proofName;

    private Bitmap bitmap;

    private String docType;

    public SelectedDocumentModel() {
    }

    public SelectedDocumentModel(String proofName, Bitmap bitmap, String docType) {
        this.proofName = proofName;
        this.bitmap = bitmap;
        this.docType = docType;
    }

    public String getProofName() {
        return proofName;
    }

    public void setProofName(String proofName) {
        this.proofName = proofName;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    @Override
    public String toString() {
        return "SelectedDocumentModel{" +
                "proofName='" + proofName + '\'' +
                ", bitmap=" + bitmap +
                ", docType='" + docType + '\'' +
                '}';
    }
}
