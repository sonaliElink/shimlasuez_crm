package elink.suezShimla.water.crm.ConnectionRequest.Activity.ApplicationRequest.Model;

public class DocumentsModel {

    String proofs;

    public DocumentsModel() {
    }

    public DocumentsModel(String proofs) {
        this.proofs = proofs;
    }

    public String getProofs() {
        return proofs;
    }

    public void setProofs(String proofs) {
        this.proofs = proofs;
    }

    @Override
    public String toString() {
        return "DocumentsAdapter{" +
                "proofs='" + proofs + '\'' +
                '}';
    }
}
