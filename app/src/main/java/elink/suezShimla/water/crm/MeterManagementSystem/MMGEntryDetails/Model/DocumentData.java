package elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model;


public class DocumentData {
    String imageStr;

    String docSubtype;

    public DocumentData() {
    }

    public DocumentData(String imageStr, String docSubtype) {
        this.imageStr = imageStr;
        this.docSubtype = docSubtype;
    }

    public String getImageStr() {
        return imageStr;
    }

    public void setImageStr(String imageStr) {
        this.imageStr = imageStr;
    }

    public String getDocSubtype() {
        return docSubtype;
    }

    public void setDocSubtype(String docSubtype) {
        this.docSubtype = docSubtype;
    }

    @Override
    public String toString() {
        return "DocumentData{" +
                "imageStr='" + imageStr + '\'' +
                ", docSubtype='" + docSubtype + '\'' +
                '}';
    }


}
