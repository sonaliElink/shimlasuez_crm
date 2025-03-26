package elink.suezShimla.water.crm.Complaint.Allocation.Reading.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ConsumersModel extends RealmObject implements Parcelable {

    @PrimaryKey
    @SerializedName("id")
    private int id;

    @SerializedName("consumerNo")
    private String consumerNo;

    @SerializedName("consumerName")
    private String consumerName;

    @SerializedName("RDRID")
    private String RDRID;

    @SerializedName("empCode")
    private String empCode;

    @SerializedName("sequenceId")
    private int sequenceId;

    @SerializedName("PANO")
    private String pano;

    @SerializedName("tariff")
    private String tariff;

    @SerializedName("consumerAddress")
    private String consumerAddress;

    @SerializedName("GIS_BuildingId")
    private String GIS_BuildingId;

    @SerializedName("WARD")
    private String wardName;

    @SerializedName("mobileNo")
    private String mobileNo;

    @SerializedName("meterNo")
    private String meterNo;

    @SerializedName("meterDigit")
    private int meterDigit;

    @SerializedName("meterReading")
    private int meterReading;

    @SerializedName("pastReading")
    private int pastReading;

   /* @SerializedName("pastReading")
    private String pastReading;*/

    @SerializedName("pastReadingDate")
    private String pastReadingDate;

    @SerializedName("pastMeterStatus")
    private String pastMeterStatus;

    @SerializedName("pastMeterConsumption")
    private int pastMeterConsumption;

    @SerializedName("billMonth")
    private int billMonth;

    @SerializedName("meterStatusId")
    private int meterStatusId;

    @SerializedName("readingStatus")
    private int readingStatus;

    @SerializedName("newConsumerStatus")
    private int newConsumerStatus;

    @SerializedName("rereadingStatus")
    private int rereadingStatus;

    @SerializedName("locationStatus")
    private int locationStatus;

    @SerializedName("uploadedStatus")
    private int uploadedStatus;

    @SerializedName("allocatedRereading")
    private int allocatedRereading;

    @SerializedName("ML")
    private String ML;

    @SerializedName("CA")
    private String CA;

    @SerializedName("REGNO")
    private String REGNO;

    @SerializedName("METER_LOCATION")
    private String METER_LOCATION;


    @SerializedName("LAT")
    private String LAT;

    @SerializedName("LON")
    private String LON;

    @SerializedName("OBSRV")
    private String OBSRV;



    public ConsumersModel() {
    }

    public ConsumersModel(String LAT, String LON) {
        this.LAT = LAT;
        this.LON = LON;
    }

    public ConsumersModel(int id, String consumerNo, String consumerName, String RDRID, String empCode, int sequenceId, String pano, String tariff, String consumerAddress, String GIS_BuildingId, String wardName, String mobileNo, String meterNo, int meterDigit, int meterReading, int pastReading, String pastReadingDate, String pastMeterStatus, int pastMeterConsumption, int billMonth, int meterStatusId, int readingStatus, int newConsumerStatus, int rereadingStatus, int locationStatus, int uploadedStatus, int allocatedRereading, String ML, String CA, String REGNO, String METER_LOCATION, String LAT, String LON, String OBSRV) {
        this.id = id;
        this.consumerNo = consumerNo;
        this.consumerName = consumerName;
        this.RDRID = RDRID;
        this.empCode = empCode;
        this.sequenceId = sequenceId;
        this.pano = pano;
        this.tariff = tariff;
        this.consumerAddress = consumerAddress;
        this.GIS_BuildingId = GIS_BuildingId;
        this.wardName = wardName;
        this.mobileNo = mobileNo;
        this.meterNo = meterNo;
        this.meterDigit = meterDigit;
        this.meterReading = meterReading;
        this.pastReading = pastReading;
        this.pastReadingDate = pastReadingDate;
        this.pastMeterStatus = pastMeterStatus;
        this.pastMeterConsumption = pastMeterConsumption;
        this.billMonth = billMonth;
        this.meterStatusId = meterStatusId;
        this.readingStatus = readingStatus;
        this.newConsumerStatus = newConsumerStatus;
        this.rereadingStatus = rereadingStatus;
        this.locationStatus = locationStatus;
        this.uploadedStatus = uploadedStatus;
        this.allocatedRereading = allocatedRereading;
        this.ML = ML;
        this.CA = CA;
        this.REGNO = REGNO;
        this.METER_LOCATION = METER_LOCATION;
        this.LAT = LAT;
        this.LON = LON;
        this.OBSRV = OBSRV;
    }


    protected ConsumersModel(Parcel in) {
        id = in.readInt();
        consumerNo = in.readString();
        consumerName = in.readString();
        RDRID = in.readString();
        empCode = in.readString();
        sequenceId = in.readInt();
        pano = in.readString();
        tariff = in.readString();
        consumerAddress = in.readString();
        GIS_BuildingId = in.readString();
        wardName = in.readString();
        mobileNo = in.readString();
        meterNo = in.readString();
        meterDigit = in.readInt();
        meterReading = in.readInt();
        pastReading = in.readInt();
        pastReadingDate = in.readString();
        pastMeterStatus = in.readString();
        pastMeterConsumption = in.readInt();
        billMonth = in.readInt();
        meterStatusId = in.readInt();
        readingStatus = in.readInt();
        newConsumerStatus = in.readInt();
        rereadingStatus = in.readInt();
        locationStatus = in.readInt();
        uploadedStatus = in.readInt();
        allocatedRereading = in.readInt();
        ML = in.readString();
        CA = in.readString();
        REGNO = in.readString();
        METER_LOCATION = in.readString();
        LAT = in.readString();
        LON = in.readString();
        OBSRV = in.readString();
    }

    public static final Creator<ConsumersModel> CREATOR = new Creator<ConsumersModel>() {
        @Override
        public ConsumersModel createFromParcel(Parcel in) {
            return new ConsumersModel(in);
        }

        @Override
        public ConsumersModel[] newArray(int size) {
            return new ConsumersModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getRDRID() {
        return RDRID;
    }

    public void setRDRID(String RDRID) {
        this.RDRID = RDRID;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public int getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(int sequenceId) {
        this.sequenceId = sequenceId;
    }

    public String getPano() {
        return pano;
    }

    public void setPano(String pano) {
        this.pano = pano;
    }


    public String getTariff() {
        return tariff;
    }

    public void setTariff(String tariff) {
        this.tariff = tariff;
    }

    public String getConsumerAddress() {
        return consumerAddress;
    }

    public void setConsumerAddress(String consumerAddress) {
        this.consumerAddress = consumerAddress;
    }

    public String getGIS_BuildingId() {
        return GIS_BuildingId;
    }

    public void setGIS_BuildingId(String GIS_BuildingId) {
        this.GIS_BuildingId = GIS_BuildingId;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getMeterNo() {
        return meterNo;
    }

    public void setMeterNo(String meterNo) {
        this.meterNo = meterNo;
    }

    public int getMeterDigit() {
        return meterDigit;
    }

    public void setMeterDigit(int meterDigit) {
        this.meterDigit = meterDigit;
    }

    public int getMeterReading() {
        return meterReading;
    }

    public void setMeterReading(int meterReading) {
        this.meterReading = meterReading;
    }

    public int getPastReading() {
        return pastReading;
    }

    public void setPastReading(int pastReading) {
        this.pastReading = pastReading;
    }


    /*  public String getPastReading() {
          return pastReading;
      }

      public void setPastReading(String pastReading) {
          this.pastReading = pastReading;
      }
  */
    public String getPastReadingDate() {
        return pastReadingDate;
    }

    public void setPastReadingDate(String pastReadingDate) {
        this.pastReadingDate = pastReadingDate;
    }

    public String getPastMeterStatus() {
        return pastMeterStatus;
    }

    public void setPastMeterStatus(String pastMeterStatus) {
        this.pastMeterStatus = pastMeterStatus;
    }

    public int getPastMeterConsumption() {
        return pastMeterConsumption;
    }

    public void setPastMeterConsumption(int pastMeterConsumption) {
        this.pastMeterConsumption = pastMeterConsumption;
    }

    public int getBillMonth() {
        return billMonth;
    }

    public void setBillMonth(int billMonth) {
        this.billMonth = billMonth;
    }

    public int getMeterStatusId() {
        return meterStatusId;
    }

    public void setMeterStatusId(int meterStatusId) {
        this.meterStatusId = meterStatusId;
    }

    public int getReadingStatus() {
        return readingStatus;
    }

    public void setReadingStatus(int readingStatus) {
        this.readingStatus = readingStatus;
    }

    public int getNewConsumerStatus() {
        return newConsumerStatus;
    }

    public void setNewConsumerStatus(int newConsumerStatus) {
        this.newConsumerStatus = newConsumerStatus;
    }

    public int getRereadingStatus() {
        return rereadingStatus;
    }

    public void setRereadingStatus(int rereadingStatus) {
        this.rereadingStatus = rereadingStatus;
    }

    public int getLocationStatus() {
        return locationStatus;
    }

    public void setLocationStatus(int locationStatus) {
        this.locationStatus = locationStatus;
    }

    public int getUploadedStatus() {
        return uploadedStatus;
    }

    public void setUploadedStatus(int uploadedStatus) {
        this.uploadedStatus = uploadedStatus;
    }

    public int getAllocatedRereading() {
        return allocatedRereading;
    }

    public void setAllocatedRereading(int allocatedRereading) {
        this.allocatedRereading = allocatedRereading;
    }

    public String getML() {
        return ML;
    }

    public void setML(String ML) {
        this.ML = ML;
    }

    public String getCA() {
        return CA;
    }

    public void setCA(String CA) {
        this.CA = CA;
    }

    public String getREGNO() {
        return REGNO;
    }

    public void setREGNO(String REGNO) {
        this.REGNO = REGNO;
    }

    public String getMETER_LOCATION() {
        return METER_LOCATION;
    }

    public void setMETER_LOCATION(String METER_LOCATION) {
        this.METER_LOCATION = METER_LOCATION;
    }


    public String getLAT() {
        return LAT;
    }

    public void setLAT(String LAT) {
        this.LAT = LAT;
    }

    public String getLON() {
        return LON;
    }

    public void setLON(String LON) {
        this.LON = LON;
    }

    public String getOBSRV() {
        return OBSRV;
    }

    public void setOBSRV(String OBSRV) {
        this.OBSRV = OBSRV;
    }

    @Override
    public String toString() {
        return "ConsumersModel{" +
                "id=" + id +
                ", consumerNo='" + consumerNo + '\'' +
                ", consumerName='" + consumerName + '\'' +
                ", RDRID='" + RDRID + '\'' +
                ", empCode='" + empCode + '\'' +
                ", sequenceId=" + sequenceId +
                ", pano='" + pano + '\'' +
                ", tariff='" + tariff + '\'' +
                ", consumerAddress='" + consumerAddress + '\'' +
                ", GIS_BuildingId='" + GIS_BuildingId + '\'' +
                ", wardName='" + wardName + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", meterNo='" + meterNo + '\'' +
                ", meterDigit=" + meterDigit +
                ", meterReading=" + meterReading +
                ", pastReading=" + pastReading +
                ", pastReadingDate='" + pastReadingDate + '\'' +
                ", pastMeterStatus='" + pastMeterStatus + '\'' +
                ", pastMeterConsumption=" + pastMeterConsumption +
                ", billMonth=" + billMonth +
                ", meterStatusId=" + meterStatusId +
                ", readingStatus=" + readingStatus +
                ", newConsumerStatus=" + newConsumerStatus +
                ", rereadingStatus=" + rereadingStatus +
                ", locationStatus=" + locationStatus +
                ", uploadedStatus=" + uploadedStatus +
                ", allocatedRereading=" + allocatedRereading +
                ", ML='" + ML + '\'' +
                ", CA='" + CA + '\'' +
                ", REGNO='" + REGNO + '\'' +
                ", METER_LOCATION='" + METER_LOCATION + '\'' +
                ", LAT='" + LAT + '\'' +
                ", LON='" + LON + '\'' +
                ", OBSRV='" + OBSRV + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(consumerNo);
        dest.writeString(consumerName);
        dest.writeString(RDRID);
        dest.writeString(empCode);
        dest.writeInt(sequenceId);
        dest.writeString(pano);
        dest.writeString(tariff);
        dest.writeString(consumerAddress);
        dest.writeString(GIS_BuildingId);
        dest.writeString(wardName);
        dest.writeString(mobileNo);
        dest.writeString(meterNo);
        dest.writeInt(meterDigit);
        dest.writeInt(meterReading);
        dest.writeInt(pastReading);
        dest.writeString(pastReadingDate);
        dest.writeString(pastMeterStatus);
        dest.writeInt(pastMeterConsumption);
        dest.writeInt(billMonth);
        dest.writeInt(meterStatusId);
        dest.writeInt(readingStatus);
        dest.writeInt(newConsumerStatus);
        dest.writeInt(rereadingStatus);
        dest.writeInt(locationStatus);
        dest.writeInt(uploadedStatus);
        dest.writeInt(allocatedRereading);
        dest.writeString(ML);
        dest.writeString(CA);
        dest.writeString(REGNO);
        dest.writeString(METER_LOCATION);
        dest.writeString(LAT);
        dest.writeString(LON);
        dest.writeString(OBSRV);
    }
}
