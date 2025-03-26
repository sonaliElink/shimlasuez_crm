package elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model;

public class MMGContractorResponseDetail {

  String METERSTATUS;
  String MDT_TOTAL_DIGITS;
  String M_CONT_CODE;
  String M_CONT_EMP_CODE;
  String SEALSTATUS;

    public MMGContractorResponseDetail() {
    }

    public MMGContractorResponseDetail(String METERSTATUS, String MDT_TOTAL_DIGITS, String m_CONT_CODE, String m_CONT_EMP_CODE, String SEALSTATUS) {
        this.METERSTATUS = METERSTATUS;
        this.MDT_TOTAL_DIGITS = MDT_TOTAL_DIGITS;
        M_CONT_CODE = m_CONT_CODE;
        M_CONT_EMP_CODE = m_CONT_EMP_CODE;
        SEALSTATUS = SEALSTATUS;
    }

    public String getMETERSTATUS() {
        return METERSTATUS;
    }

    public void setMETERSTATUS(String METERSTATUS) {
        this.METERSTATUS = METERSTATUS;
    }

    public String getM_CONT_CODE() {
        return M_CONT_CODE;
    }

    public void setM_CONT_CODE(String m_CONT_CODE) {
        M_CONT_CODE = m_CONT_CODE;
    }

    public String getM_CONT_EMP_CODE() {
        return M_CONT_EMP_CODE;
    }

    public void setM_CONT_EMP_CODE(String m_CONT_EMP_CODE) {
        M_CONT_EMP_CODE = m_CONT_EMP_CODE;
    }

    public String getMDT_TOTAL_DIGITS() {
        return MDT_TOTAL_DIGITS;
    }

    public String getSEALSTATUS() {
        return SEALSTATUS;
    }

    public void setSEALSTATUS(String SEALSTATUS) {
        this.SEALSTATUS = SEALSTATUS;
    }
}
