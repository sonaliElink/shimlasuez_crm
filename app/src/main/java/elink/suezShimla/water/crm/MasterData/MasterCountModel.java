package elink.suezShimla.water.crm.MasterData;

import com.google.gson.annotations.SerializedName;

public class MasterCountModel {

    @SerializedName("WORK_ALLOCATED")
    private String WORK_ALLOCATED;

    @SerializedName("WORK_RE_ALLOCATED")
    private String WORK_RE_ALLOCATED;

    @SerializedName("WORK_DONE")
    private String WORK_DONE;

    @SerializedName("TOTAL_WORK")
    private String TOTAL_WORK;

    @SerializedName("SAVEDETAILBYFIXER")
    private String SAVED_DETAILS_BY_FIXER;


    public MasterCountModel(String WORK_ALLOCATED, String WORK_RE_ALLOCATED, String WORK_DONE, String TOTAL_WORK, String SAVED_DETAILS_BY_FIXER) {
        this.WORK_ALLOCATED = WORK_ALLOCATED;
        this.WORK_RE_ALLOCATED = WORK_RE_ALLOCATED;
        this.WORK_DONE = WORK_DONE;
        this.TOTAL_WORK = TOTAL_WORK;
        this.SAVED_DETAILS_BY_FIXER = SAVED_DETAILS_BY_FIXER;
    }

    public String getWORK_ALLOCATED() {
        return WORK_ALLOCATED;
    }

    public void setWORK_ALLOCATED(String WORK_ALLOCATED) {
        this.WORK_ALLOCATED = WORK_ALLOCATED;
    }

    public String getWORK_RE_ALLOCATED() {
        return WORK_RE_ALLOCATED;
    }

    public void setWORK_RE_ALLOCATED(String WORK_RE_ALLOCATED) {
        this.WORK_RE_ALLOCATED = WORK_RE_ALLOCATED;
    }

    public String getWORK_DONE() {
        return WORK_DONE;
    }

    public void setWORK_DONE(String WORK_DONE) {
        this.WORK_DONE = WORK_DONE;
    }

    public String getTOTAL_WORK() {
        return TOTAL_WORK;
    }

    public void setTOTAL_WORK(String TOTAL_WORK) {
        this.TOTAL_WORK = TOTAL_WORK;
    }

    public String getSAVED_DETAILS_BY_FIXER() {
        return SAVED_DETAILS_BY_FIXER;
    }

    public void setSAVED_DETAILS_BY_FIXER(String SAVED_DETAILS_BY_FIXER) {
        this.SAVED_DETAILS_BY_FIXER = SAVED_DETAILS_BY_FIXER;
    }

    @Override
    public String toString() {
        return "MasterCountModel{" +
                "WORK_ALLOCATED='" + WORK_ALLOCATED + '\'' +
                ", WORK_RE_ALLOCATED='" + WORK_RE_ALLOCATED + '\'' +
                ", WORK_DONE='" + WORK_DONE + '\'' +
                ", TOTAL_WORK='" + TOTAL_WORK + '\'' +
                ", SAVED_DETAILS_BY_FIXER='" + SAVED_DETAILS_BY_FIXER + '\'' +
                '}';
    }
}
