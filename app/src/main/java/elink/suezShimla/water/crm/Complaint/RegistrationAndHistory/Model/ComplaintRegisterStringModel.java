package elink.suezShimla.water.crm.Complaint.RegistrationAndHistory.Model;

public class ComplaintRegisterStringModel {
    private String consumerNo;
    private String consumerName;
    private String mobileNo;
    private String email;
    private String wBillAmount;
    private String wArrAmount;
    private String cHardcore;
    private String zoneId;
    private String cHardcore2;
    private String subzone;
    private String status;
    private String hdnTheft;
    private String lblSAPPhase;

    public ComplaintRegisterStringModel(){}

    public ComplaintRegisterStringModel(String consumerNo, String consumerName, String mobileNo, String email, String wBillAmount, String wArrAmount, String cHardcore, String zoneId, String cHardcore2, String subzone, String status, String hdnTheft, String lblSAPPhase) {
        this.consumerNo = consumerNo;
        this.consumerName = consumerName;
        this.mobileNo = mobileNo;
        this.email = email;
        this.wBillAmount = wBillAmount;
        this.wArrAmount = wArrAmount;
        this.cHardcore = cHardcore;
        this.zoneId = zoneId;
        this.cHardcore2 = cHardcore2;
        this.subzone = subzone;
        this.status = status;
        this.hdnTheft = hdnTheft;
        this.lblSAPPhase = lblSAPPhase;
    }

    public String getConsumerNo() {
        return consumerNo;
    }

    public void setConsumerNo(String consumerNo) {
        this.consumerNo = consumerNo;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getwBillAmount() {
        return wBillAmount;
    }

    public void setwBillAmount(String wBillAmount) {
        this.wBillAmount = wBillAmount;
    }

    public String getwArrAmount() {
        return wArrAmount;
    }

    public void setwArrAmount(String wArrAmount) {
        this.wArrAmount = wArrAmount;
    }

    public String getcHardcore() {
        return cHardcore;
    }

    public void setcHardcore(String cHardcore) {
        this.cHardcore = cHardcore;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getcHardcore2() {
        return cHardcore2;
    }

    public void setcHardcore2(String cHardcore2) {
        this.cHardcore2 = cHardcore2;
    }

    public String getSubzone() {
        return subzone;
    }

    public void setSubzone(String subzone) {
        this.subzone = subzone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHdnTheft() {
        return hdnTheft;
    }

    public void setHdnTheft(String hdnTheft) {
        this.hdnTheft = hdnTheft;
    }

    public String getLblSAPPhase() {
        return lblSAPPhase;
    }

    public void setLblSAPPhase(String lblSAPPhase) {
        this.lblSAPPhase = lblSAPPhase;
    }

    @Override
    public String toString() {
        return "ComplaintRegisterStringModel{" +
                "consumerNo='" + consumerNo + '\'' +
                ", consumerName='" + consumerName + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", email='" + email + '\'' +
                ", wBillAmount='" + wBillAmount + '\'' +
                ", wArrAmount='" + wArrAmount + '\'' +
                ", cHardcore='" + cHardcore + '\'' +
                ", zoneId='" + zoneId + '\'' +
                ", cHardcore2='" + cHardcore2 + '\'' +
                ", subzone='" + subzone + '\'' +
                ", status='" + status + '\'' +
                ", hdnTheft='" + hdnTheft + '\'' +
                ", lblSAPPhase='" + lblSAPPhase + '\'' +
                '}';
    }
}
