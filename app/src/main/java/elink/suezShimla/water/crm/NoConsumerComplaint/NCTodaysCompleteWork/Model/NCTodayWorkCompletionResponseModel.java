package elink.suezShimla.water.crm.NoConsumerComplaint.NCTodaysCompleteWork.Model;

import com.google.gson.annotations.SerializedName;

public class NCTodayWorkCompletionResponseModel {
    @SerializedName("SUBZONE")
    private String SUBZONE;

    @SerializedName("COMPTYPE")
    private String COMPTYPE;

    @SerializedName("COMPSUBTYPE")
    private String COMPSUBTYPE;

    @SerializedName("COM_WORKCOMPLETIONDATE")
    private String COM_WORKCOMPLETIONDATE;

    @SerializedName("ATTENDEDBY")
    private String ATTENDEDBY;

    @SerializedName("COM_REMARKS")
    private String COM_REMARKS;

    @SerializedName("COMNO")
    private String COMNO;

    @SerializedName("COM_ALLOCATIONDATE")
    private String COM_ALLOCATIONDATE;

    @SerializedName("COM_COMPDATE")
    private String COM_COMPDATE;


    @SerializedName("FLM_ALOCTO")
    private String FLM_ALOCTO;


    public NCTodayWorkCompletionResponseModel(){}

    public NCTodayWorkCompletionResponseModel(String SUBZONE,String COMPTYPE, String COMPSUBTYPE, String COM_WORKCOMPLETIONDATE, String ATTENDEDBY, String COM_REMARKS, String COMNO, String COM_ALLOCATIONDATE, String COM_COMPDATE, String FLM_ALOCTO) {
        this.SUBZONE = SUBZONE;
        this.COMPTYPE = COMPTYPE;
        this.COMPSUBTYPE = COMPSUBTYPE;
        this.COM_WORKCOMPLETIONDATE = COM_WORKCOMPLETIONDATE;
        this.ATTENDEDBY = ATTENDEDBY;
        this.COM_REMARKS = COM_REMARKS;
        this.COMNO = COMNO;
        this.COM_ALLOCATIONDATE = COM_ALLOCATIONDATE;
        this.COM_COMPDATE = COM_COMPDATE;
        this.FLM_ALOCTO = FLM_ALOCTO;

    }

    public String getSUBZONE() {
        return SUBZONE;
    }

    public void setSUBZONE(String SUBZONE) {
        this.SUBZONE = SUBZONE;
    }

    public String getCOMPTYPE() {
        return COMPTYPE;
    }

    public void setCOMPTYPE(String COMPTYPE) {
        this.COMPTYPE = COMPTYPE;
    }

    public String getCOMPSUBTYPE() {
        return COMPSUBTYPE;
    }

    public void setCOMPSUBTYPE(String COMPSUBTYPE) {
        this.COMPSUBTYPE = COMPSUBTYPE;
    }

    public String getCOM_WORKCOMPLETIONDATE() {
        return COM_WORKCOMPLETIONDATE;
    }

    public void setCOM_WORKCOMPLETIONDATE(String COM_WORKCOMPLETIONDATE) {
        this.COM_WORKCOMPLETIONDATE = COM_WORKCOMPLETIONDATE;
    }

    public String getATTENDEDBY() {
        return ATTENDEDBY;
    }

    public void setATTENDEDBY(String ATTENDEDBY) {
        this.ATTENDEDBY = ATTENDEDBY;
    }

    public String getCOM_REMARKS() {
        return COM_REMARKS;
    }

    public void setCOM_REMARKS(String COM_REMARKS) {
        this.COM_REMARKS = COM_REMARKS;
    }

    public String getCOMNO() {
        return COMNO;
    }

    public void setCOMNO(String COMNO) {
        this.COMNO = COMNO;
    }

    public String getCOM_ALLOCATIONDATE() {
        return COM_ALLOCATIONDATE;
    }

    public void setCOM_ALLOCATIONDATE(String COM_ALLOCATIONDATE) {
        this.COM_ALLOCATIONDATE = COM_ALLOCATIONDATE;
    }

    public String getCOM_COMPDATE() {
        return COM_COMPDATE;
    }

    public void setCOM_COMPDATE(String COM_COMPDATE) {
        this.COM_COMPDATE = COM_COMPDATE;
    }

    public String getFLM_ALOCTO() {
        return FLM_ALOCTO;
    }

    public void setFLM_ALOCTO(String FLM_ALOCTO) {
        this.FLM_ALOCTO = FLM_ALOCTO;
    }

    @Override
    public String toString() {
        return "NCTodayWorkCompletionResponseModel{" +
                "SUBZONE='" + SUBZONE + '\'' +
                ", COMPTYPE='" + COMPTYPE + '\'' +
                ", COMPSUBTYPE='" + COMPSUBTYPE + '\'' +
                ", COM_WORKCOMPLETIONDATE='" + COM_WORKCOMPLETIONDATE + '\'' +
                ", ATTENDEDBY='" + ATTENDEDBY + '\'' +
                ", COM_REMARKS='" + COM_REMARKS + '\'' +
                ", COMNO='" + COMNO + '\'' +
                ", COM_ALLOCATIONDATE='" + COM_ALLOCATIONDATE + '\'' +
                ", COM_COMPDATE='" + COM_COMPDATE + '\'' +
                ", FLM_ALOCTO='" + FLM_ALOCTO + '\'' +
                '}';
    }
}
