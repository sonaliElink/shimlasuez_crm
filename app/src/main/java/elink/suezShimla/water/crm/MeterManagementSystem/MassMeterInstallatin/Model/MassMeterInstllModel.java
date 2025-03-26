package elink.suezShimla.water.crm.MeterManagementSystem.MassMeterInstallatin.Model;

public class MassMeterInstllModel {
    int srNo;
    String message;

    public MassMeterInstllModel() {
    }

    public MassMeterInstllModel(int srNo, String message) {
        this.srNo = srNo;
        this.message = message;
    }

    public int getSrNo() {
        return srNo;
    }

    public void setSrNo(int srNo) {
        this.srNo = srNo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MassMeterInstllModel{" +
                "srNo=" + srNo +
                ", cosummerNo='" + message + '\'' +
                '}';
    }
}
