package elink.suezShimla.water.crm.Complaint.RegistrationAndHistory.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class HistoryModel implements Parcelable {

    @SerializedName("ORIGIN")
    private String ORIGIN; // complaint type

    @SerializedName("APPLICATIONDATE")
    private String APPLICATIONDATE;

    @SerializedName("REFNO")
    private String REFNO;

    @SerializedName("TYPENAME")
    private String TYPENAME; // complaint type

    @SerializedName("CATEGORY")
    private String CATEGORY;

    @SerializedName("SUBCATEGORY")
    private String SUBCATEGORY;

    @SerializedName("APPSOURCE")
    private String APPSOURCE;

    @SerializedName("STATUS")
    private String STATUS; // complaint type

    @SerializedName("OPENCLOSED")
    private String OPENCLOSED;

    @SerializedName("SLA")
    private String SLA;

    @SerializedName("AGING")
    private String AGING;

    @SerializedName("REMINDER")
    private String REMINDER;


    private boolean expanded;


    public HistoryModel() {
    }

    public HistoryModel(String ORIGIN, String APPLICATIONDATE, String REFNO, String TYPENAME, String CATEGORY, String SUBCATEGORY, String APPSOURCE, String STATUS, String OPENCLOSED, String SLA, String AGING, String REMINDER) {
        this.ORIGIN = ORIGIN;
        this.APPLICATIONDATE = APPLICATIONDATE;
        this.REFNO = REFNO;
        this.TYPENAME = TYPENAME;
        this.CATEGORY = CATEGORY;
        this.SUBCATEGORY = SUBCATEGORY;
        this.APPSOURCE = APPSOURCE;
        this.STATUS = STATUS;
        this.OPENCLOSED = OPENCLOSED;
        this.SLA = SLA;
        this.AGING = AGING;
        this.REMINDER = REMINDER;
    }


    protected HistoryModel(Parcel in) {
        ORIGIN = in.readString();
        APPLICATIONDATE = in.readString();
        REFNO = in.readString();
        TYPENAME = in.readString();
        CATEGORY = in.readString();
        SUBCATEGORY = in.readString();
        APPSOURCE = in.readString();
        STATUS = in.readString();
        OPENCLOSED = in.readString();
        SLA = in.readString();
        AGING = in.readString();
        REMINDER = in.readString();
    }

    public static final Creator<HistoryModel> CREATOR = new Creator<HistoryModel>() {
        @Override
        public HistoryModel createFromParcel(Parcel in) {
            return new HistoryModel(in);
        }

        @Override
        public HistoryModel[] newArray(int size) {
            return new HistoryModel[size];
        }
    };

    public String getORIGIN() {
        return ORIGIN;
    }

    public void setORIGIN(String ORIGIN) {
        this.ORIGIN = ORIGIN;
    }

    public String getAPPLICATIONDATE() {
        return APPLICATIONDATE;
    }

    public void setAPPLICATIONDATE(String APPLICATIONDATE) {
        this.APPLICATIONDATE = APPLICATIONDATE;
    }

    public String getREFNO() {
        return REFNO;
    }

    public void setREFNO(String REFNO) {
        this.REFNO = REFNO;
    }

    public String getTYPENAME() {
        return TYPENAME;
    }

    public void setTYPENAME(String TYPENAME) {
        this.TYPENAME = TYPENAME;
    }

    public String getCATEGORY() {
        return CATEGORY;
    }

    public void setCATEGORY(String CATEGORY) {
        this.CATEGORY = CATEGORY;
    }

    public String getSUBCATEGORY() {
        return SUBCATEGORY;
    }

    public void setSUBCATEGORY(String SUBCATEGORY) {
        this.SUBCATEGORY = SUBCATEGORY;
    }

    public String getAPPSOURCE() {
        return APPSOURCE;
    }

    public void setAPPSOURCE(String APPSOURCE) {
        this.APPSOURCE = APPSOURCE;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getOPENCLOSED() {
        return OPENCLOSED;
    }

    public void setOPENCLOSED(String OPENCLOSED) {
        this.OPENCLOSED = OPENCLOSED;
    }

    public String getSLA() {
        return SLA;
    }

    public void setSLA(String SLA) {
        this.SLA = SLA;
    }

    public String getAGING() {
        return AGING;
    }

    public void setAGING(String AGING) {
        this.AGING = AGING;
    }

    public String getREMINDER() {
        return REMINDER;
    }

    public void setREMINDER(String REMINDER) {
        this.REMINDER = REMINDER;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public boolean isExpanded() {
        return expanded;
    }

    @Override
    public String toString() {
        return "HistoryModel{" +
                "ORIGIN='" + ORIGIN + '\'' +
                ", APPLICATIONDATE='" + APPLICATIONDATE + '\'' +
                ", REFNO='" + REFNO + '\'' +
                ", TYPENAME='" + TYPENAME + '\'' +
                ", CATEGORY='" + CATEGORY + '\'' +
                ", SUBCATEGORY='" + SUBCATEGORY + '\'' +
                ", APPSOURCE='" + APPSOURCE + '\'' +
                ", STATUS='" + STATUS + '\'' +
                ", OPENCLOSED='" + OPENCLOSED + '\'' +
                ", SLA='" + SLA + '\'' +
                ", AGING='" + AGING + '\'' +
                ", REMINDER='" + REMINDER + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ORIGIN);
        dest.writeString(APPLICATIONDATE);
        dest.writeString(REFNO);
        dest.writeString(TYPENAME);
        dest.writeString(CATEGORY);
        dest.writeString(SUBCATEGORY);
        dest.writeString(APPSOURCE);
        dest.writeString(STATUS);
        dest.writeString(OPENCLOSED);
        dest.writeString(SLA);
        dest.writeString(AGING);
        dest.writeString(REMINDER);
    }
}
