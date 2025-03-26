package elink.suezShimla.water.crm.Complaint.WorkCompletion.Model;

import com.google.gson.annotations.SerializedName;

public class DownloadAdjustmentType {

        @SerializedName("REM_REASONCD")
        private String REM_REASONCD;

        @SerializedName("REM_REASONNM")
        private String REM_REASONNM;

        public DownloadAdjustmentType() {
        }

        public DownloadAdjustmentType(String REM_REASONCD, String REM_REASONNM) {
            this.REM_REASONCD = REM_REASONCD;
            this.REM_REASONNM = REM_REASONNM;
        }

        public String getREM_REASONCD() {
            return REM_REASONCD;
        }

        public void setREM_REASONCD(String REM_REASONCD) {
            this.REM_REASONCD = REM_REASONCD;
        }

    public String getREM_REASONNM() {
        return REM_REASONNM;
    }

    public void setREM_REASONNM(String REM_REASONNM) {
        this.REM_REASONNM = REM_REASONNM;
    }

    @Override
        public String toString() {
            return "DownloadAdjustmentType{" +
                    "REM_REASONCD='" + REM_REASONCD + '\'' +
                    ", REM_REASONNM='" + REM_REASONNM + '\'' +
                    '}';
        }
}
