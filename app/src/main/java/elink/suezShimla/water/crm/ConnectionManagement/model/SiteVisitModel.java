package elink.suezShimla.water.crm.ConnectionManagement.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SiteVisitModel implements Parcelable {

    /**
     * SiteVisitModel{REQUEST_NO='NC01200700100', NAME='RAKESH KUMAR', AM_AAPP_NO_TYPE='1', AM_APP_SERVNO='0',
     * AM_APP_ZONE='4', AM_APP_CIRCLE='45', AM_APP_DIVISION='0', AM_APP_SUB_DIVISION='0', AM_APP_SECTION='0',
     * AM_APP_BU='4', AM_APP_PC='176', AM_APP_AREA='10727', AM_APP_MRC='1523', AM_APP_LOT='1523', AM_APP_SEQUENCE='0',
     * AM_APP_NEARBYSER='40318862', AM_APP_NMTITLE='MR.', AM_APP_FNAME='RAKESH', AM_APP_MNAME='', AM_APP_SURNAME='KUMAR',
     * AM_AAPP_DATE='21-Jul-2020 11:34 AM', AM_APP_PHONEM='9712989885', am_app_email='chandel.pushpendra@gmail.com',
     * AM_APP_ADDRESS1='ABC', AM_APP_ADDRESS2='XYZ', AM_APP_ADDRESS3='MG ROAD', AM_APP_ADDRESS4='Coimbatore / Tamil Nadu',
     * AM_APP_PINCODE='212656', ADDRESS='ABC,XYZ,MG ROAD-212656', AM_REMARKS='test by pushpendra, ', AM_APP_ISMSCDCL_EMPLOYEE='No',
     * AM_APP_ACCEPT_DATE='', AM_APP_APPOINT_DATE='21-Jul-2020 01:00 PM', AM_APP_PROCDT='21-Jul-2020 12:31 PM', PROCESSTAG='1',
     * PROCESSCODE='101', STATUS='Appointment Fixed', AM_APP_SOURCE_TYPE='C', SOURCE='Call Center', METERREPLACE='No', REASON='',
     * REASONNAME='', AM_APP_REJECT_DATE='', AM_APP_ORESCD='0', AREANAME='10727-Elango Nagar', ZONENAME='4-North',
     * SUBZONE='45-WD-40 ', AM_APP_CATEGORY='1', PURPOSE_NAME='Apartment', CONNECTION_SIZE='15', AM_APP_PURPOSE='4',
     * AM_APP_TRNO='', AM_APP_POLENO='', AM_APP_ASD='0', AM_APP_SD='0', AM_APP_SCC='', AM_APP_REGFEE='0',
     * TARIFF_NAME='Domestic', AM_APP_TARIFF='1', AM_APP_AREA_TYPE='2', AM_APP_TARIFF_TYPE_CD='1', AM_APP_METER_INDICATION='',
     * AM_APP_RCPT_DATE='21-Jul-2020 12:06 PM', AM_APP_TOTALAMT='125', AM_APP_PDWITHIN6='', AM_APP_METERAVLB='',
     * AM_APP_PDRECON_TYPE='', MONTHYR='0', AM_APP_PREMISE_NO='', AM_APP_TR_DISTANCE='', AM_APP_AREAPREMISE='',
     * AM_APP_CONNECTION_SIZE='1', AM_APP_STDCODE='0', AM_APP_PHONE='0', PROCCESCODE='101', AM_APP_METER_REQUIRED='',
     * AM_APP_BOARING_CHARGES='0', AM_APP_SECUTIRY_CHARGES='0', AM_APP_ADDITIONAL_CHARGES='0',
     * AM_APP_WATER_MACADAM_CHARGES='0', AM_APP_REINFORCEMENT_CHARGES='0', AM_APP_VISITING_CHARGES='0',
     * AM_APP_BITUMINOUSTAR_CHARGES='0', AM_APP_METER_COST='0', AM_APP_REGFEE1='0', AM_APP_ASD1='0', AM_APP_SD1='0',
     * AM_APP_DEMAND_AMT='125', AM_APP_METER_REQUIRED_FUTILITY='', AM_APP_CONNECTION_TYPE='', APTM_NAME='New Connection',
     * AM_APP_PREMTYPE='4', AM_APP_GOVT_PROPERTY='', AM_APP_EXISTING_CONSUMER_TAG='2', AM_APP_EXISTING_CONSUMERNO='',
     * AM_APP_OTHERUSAGE='', AM_APP_FAMILY_MEMBER='5', APPLICATION='SINGLE', VCM_CATNAME='', FATHERNAME='', AM_APP_NO_OF_FLOORS='2',
     * AM_APP_NO_OF_DWELLING='4', AM_APP_NO_OF_ROOMS='', AM_APP_ISAUTH_CONNECTION='', AM_APP_CIVIL_CONS_CCMC_WATER='',
     * AM_APP_NO_OF_DWELLING_UNITS='4', AM_APP_ISOCCUPIER_SECURITY='', AM_APP_ISMSCDCL_EMPLOYEE_ID='0', AM_APP_ISTECH_FESIBILITY='',
     * AM_APP_SPIPELINE='0', AM_APP_LENGTH_MEASURE='', AM_APP_ROADCUTMTR='0', AM_APP_ROADTYPE='', AM_APP_ROADOWNER='',
     * AM_APP_SUBSTATION='0', CATEGORY_NAME='Individual', PRM_NAME='Apartment', AM_APP_S_GIS_ID='', PCM_PC_NAME='NZ38',
     * LM_LOT_NAME='NZ3803', TRM_NAME='', PM_NAME='', SBM_NAME='', AM_APP_NEAR_LCONS='', OWNERSHIP='Owner',
     * UPLOADEDDOCS='5'}
     *
     *
     * <sAppNo>string</sAppNo>
     * <sInitial>string</sInitial>
     * <sFirstName>string</sFirstName>
     * <sMiddleName>string</sMiddleName>
     * <sLastName>string</sLastName>
     * <sFatherName>string</sFatherName>
     * <sAddress1>string</sAddress1>
     * <sAddress2>string</sAddress2>
     * <sAddress3>string</sAddress3>
     * <sPinCode>string</sPinCode>
     * <sTariff>string</sTariff>
     * <sConnCategory>string</sConnCategory>
     * <sPremiseType>string</sPremiseType>
     * <sPurpose>string</sPurpose>
     * <sNoOfFloors>string</sNoOfFloors>
     * <sFamilyMember>string</sFamilyMember>
     * <sNoOfRooms>string</sNoOfRooms>
     * <sUnAuthConn>string</sUnAuthConn>
     * <sCivilConsCCMCWater>string</sCivilConsCCMCWater>
     * <sDwellingUnits>string</sDwellingUnits>
     * <sOccupierSecurity>string</sOccupierSecurity>
     * <sGovtEmployee>string</sGovtEmployee>
     * <sTechFesibility>string</sTechFesibility>
     * <sDistConnSize>string</sDistConnSize>
     * <sRoadLenMeasure>string</sRoadLenMeasure>
     * <sRoadLength>string</sRoadLength>
     * <sRoadType>string</sRoadType>
     * <sRoadOwner>string</sRoadOwner>
     * <sConnSize>string</sConnSize>
     * <sZone>string</sZone>
     * <sCircle>string</sCircle>
     * <sMSR>string</sMSR>
     * <sSR>string</sSR>
     * <sPC>string</sPC>
     * <sMRC>string</sMRC>
     * <sArea>string</sArea>
     * <sRemarks>string</sRemarks>
     * <sProcessDate>string</sProcessDate>
     * <sEmpCode>string</sEmpCode>
     * <sIP>string</sIP>
     * <sDivision>string</sDivision>
     * <sSubDivision>string</sSubDivision>
     * <sSection>string</sSection>
     * <sBU>string</sBU>
     * <sAppType>string</sAppType>
     * <sType>string</sType>
     * <sEmpID>string</sEmpID>
     * <sSourceType>string</sSourceType>
     * <sIsWaterAvailInDP>string</sIsWaterAvailInDP>
     * <sDistId>string</sDistId>
     * <sRightConsumer>string</sRightConsumer>
     * <sLeftConsumer>string</sLeftConsumer>
     * <sIsRoadCuttingReqd>string</sIsRoadCuttingReqd>
     * <sIsConstrCompl>string</sIsConstrCompl>
     * <sIsExistConn>string</sIsExistConn>
     * <sExistConn>string</sExistConn>
     * <sIsDwellHasConn>string</sIsDwellHasConn>
     * <sMeterSize>string</sMeterSize>
     * <sStorageCapType>string</sStorageCapType>
     * <sStorageCapacity>string</sStorageCapacity>
     * <sIsInternalNetwork>string</sIsInternalNetwork>
     * <sIsDisposalOfWater>string</sIsDisposalOfWater>
     * <sIsRainWaterHarwest>string</sIsRainWaterHarwest>
     * <sMeterLocation>string</sMeterLocation>
     * </SaveSiteVisitData>
     */
    @SerializedName("REQUEST_NO")
    private String REQUEST_NO;

    @SerializedName("NAME")
    private String NAME;

    @SerializedName("AM_AAPP_NO_TYPE")
    private String AM_AAPP_NO_TYPE;

    @SerializedName("AM_APP_SERVNO")
    private String AM_APP_SERVNO;

    @SerializedName("AM_APP_ZONE")
    private String AM_APP_ZONE;

    @SerializedName("AM_APP_CIRCLE")
    private String AM_APP_CIRCLE;

    @SerializedName("AM_APP_DIVISION")
    private String AM_APP_DIVISION;

    @SerializedName("AM_APP_SUB_DIVISION")
    private String AM_APP_SUB_DIVISION;

    @SerializedName("AM_APP_SECTION")
    private String AM_APP_SECTION;

    @SerializedName("AM_APP_BU")
    private String AM_APP_BU;

    @SerializedName("AM_APP_PC")
    private String AM_APP_PC;

    @SerializedName("AM_APP_AREA")
    private String AM_APP_AREA;

    @SerializedName("AM_APP_MRC")
    private String AM_APP_MRC;

    @SerializedName("AM_APP_LOT")
    private String AM_APP_LOT;

    @SerializedName("AM_APP_SEQUENCE")
    private String AM_APP_SEQUENCE;

    @SerializedName("AM_APP_NEARBYSER")
    private String AM_APP_NEARBYSER;

    @SerializedName("AM_APP_NMTITLE")
    private String AM_APP_NMTITLE;

    @SerializedName("AM_APP_FNAME")
    private String AM_APP_FNAME;

    @SerializedName("AM_APP_MNAME")
    private String AM_APP_MNAME;

    @SerializedName("AM_APP_SURNAME")
    private String AM_APP_SURNAME;

    @SerializedName("AM_AAPP_DATE")
    private String AM_AAPP_DATE;

    @SerializedName("AM_APP_PHONEM")
    private String AM_APP_PHONEM;

    @SerializedName("AM_APP_EMAIL")
    private String AM_APP_EMAIL;

    @SerializedName("AM_APP_ADDRESS1")
    private String AM_APP_ADDRESS1;

    @SerializedName("AM_APP_ADDRESS2")
    private String AM_APP_ADDRESS2;

    @SerializedName("AM_APP_ADDRESS3")
    private String AM_APP_ADDRESS3;

    @SerializedName("AM_APP_LOCALITY")
    private String AM_APP_LOCALITY;

    @SerializedName("AM_APP_ADDRESS4")
    private String AM_APP_ADDRESS4;

    @SerializedName("AM_APP_PINCODE")
    private String AM_APP_PINCODE;

    @SerializedName("ADDRESS")
    private String ADDRESS;
    @SerializedName("AM_APP_APPLI_TYPE")
    private String AM_APP_APPLI_TYPE;
    @SerializedName("AM_APP_NOOF_APPS")
    private String AM_APP_NOOF_APPS;
    @SerializedName("NEWADDRESS")
    private String NEWADDRESS;
    @SerializedName("AM_APP_BOREWELL")
    private String AM_APP_BOREWELL;

    @SerializedName("AM_APP_IS_OPERATIONAL")
    private String AM_APP_IS_OPERATIONAL;

    @SerializedName("AM_REMARKS")
    private String AM_REMARKS;

    @SerializedName("AM_APP_ISMSCDCL_EMPLOYEE")
    private String AM_APP_ISMSCDCL_EMPLOYEE;

    @SerializedName("AM_APP_ACCEPT_DATE")
    private String AM_APP_ACCEPT_DATE;

    @SerializedName("AM_APP_APPOINT_DATE")
    private String AM_APP_APPOINT_DATE;

    @SerializedName("AM_APP_PROCDT")
    private String AM_APP_PROCDT;

    @SerializedName("PROCESSTAG")
    private String PROCESSTAG;

    @SerializedName("PROCESSCODE")
    private String PROCESSCODE;

    @SerializedName("STATUS")
    private String STATUS;

    @SerializedName("AM_APP_SOURCE_TYPE")
    private String AM_APP_SOURCE_TYPE;

    @SerializedName("SOURCE")
    private String SOURCE;

    @SerializedName("METERREPLACE")
    private String METERREPLACE;

    @SerializedName("REASON")
    private String REASON;

    @SerializedName("REASONNAME")
    private String REASONNAME;

    @SerializedName("AM_APP_REJECT_DATE")
    private String AM_APP_REJECT_DATE;

    @SerializedName("AM_APP_ORESCD")
    private String AM_APP_ORESCD;

    @SerializedName("AREANAME")
    private String AREANAME;

    @SerializedName("ZONENAME")
    private String ZONENAME;

    @SerializedName("SUBZONE")
    private String SUBZONE;

    @SerializedName("AM_APP_CATEGORY")
    private String AM_APP_CATEGORY;

    @SerializedName("PURPOSE_NAME")
    private String PURPOSE_NAME;

    @SerializedName("CONNECTION_SIZE")
    private String CONNECTION_SIZE;

    @SerializedName("AM_APP_PURPOSE")
    private String AM_APP_PURPOSE;

    @SerializedName("AM_APP_TRNO")
    private String AM_APP_TRNO;

    @SerializedName("AM_APP_POLENO")
    private String AM_APP_POLENO;

    @SerializedName("AM_APP_ASD")
    private String AM_APP_ASD;

    @SerializedName("AM_APP_SD")
    private String AM_APP_SD;

    @SerializedName("AM_APP_SCC")
    private String AM_APP_SCC;

    @SerializedName("AM_APP_REGFEE")
    private String AM_APP_REGFEE;

    @SerializedName("TARIFF_NAME")
    private String TARIFF_NAME;

    @SerializedName("AM_APP_TARIFF")
    private String AM_APP_TARIFF;

    @SerializedName("AM_APP_AREA_TYPE")
    private String AM_APP_AREA_TYPE;

    @SerializedName("AM_APP_TARIFF_TYPE_CD")
    private String AM_APP_TARIFF_TYPE_CD;

    @SerializedName("AM_APP_METER_INDICATION")
    private String AM_APP_METER_INDICATION;

    @SerializedName("AM_APP_RCPT_DATE")
    private String AM_APP_RCPT_DATE;

    @SerializedName("AM_APP_TOTALAMT")
    private String AM_APP_TOTALAMT;

    @SerializedName("AM_APP_PDWITHIN6")
    private String AM_APP_PDWITHIN6;

    @SerializedName("AM_APP_METERAVLB")
    private String AM_APP_METERAVLB;

    @SerializedName("AM_APP_PDRECON_TYPE")
    private String AM_APP_PDRECON_TYPE;

    @SerializedName("MONTHYR")
    private String MONTHYR;

    @SerializedName("AM_APP_PREMISE_NO")
    private String AM_APP_PREMISE_NO;

    @SerializedName("AM_APP_TR_DISTANCE")
    private String AM_APP_TR_DISTANCE;

    @SerializedName("AM_APP_AREAPREMISE")
    private String AM_APP_AREAPREMISE;

    @SerializedName("AM_APP_CONNECTION_SIZE")
    private String AM_APP_CONNECTION_SIZE;

    @SerializedName("AM_APP_STDCODE")
    private String AM_APP_STDCODE;

    @SerializedName("AM_APP_PHONE")
    private String AM_APP_PHONE;

    @SerializedName("PROCCESCODE")
    private String PROCCESCODE;

    @SerializedName("AM_APP_METER_REQUIRED")
    private String AM_APP_METER_REQUIRED;

    @SerializedName("AM_APP_BOARING_CHARGES")
    private String AM_APP_BOARING_CHARGES;

    @SerializedName("AM_APP_SECUTIRY_CHARGES")
    private String AM_APP_SECUTIRY_CHARGES;

    @SerializedName("AM_APP_ADDITIONAL_CHARGES")
    private String AM_APP_ADDITIONAL_CHARGES;

    @SerializedName("AM_APP_WATER_MACADAM_CHARGES")
    private String AM_APP_WATER_MACADAM_CHARGES;

    @SerializedName("AM_APP_REINFORCEMENT_CHARGES")
    private String AM_APP_REINFORCEMENT_CHARGES;

    @SerializedName("AM_APP_VISITING_CHARGES")
    private String AM_APP_VISITING_CHARGES;

    @SerializedName("AM_APP_BITUMINOUSTAR_CHARGES")
    private String AM_APP_BITUMINOUSTAR_CHARGES;

    @SerializedName("AM_APP_METER_COST")
    private String AM_APP_METER_COST;

    @SerializedName("AM_APP_REGFEE1")
    private String AM_APP_REGFEE1;

    @SerializedName("AM_APP_ASD1")
    private String AM_APP_ASD1;

    @SerializedName("AM_APP_SD1")
    private String AM_APP_SD1;

    @SerializedName("AM_APP_DEMAND_AMT")
    private String AM_APP_DEMAND_AMT;

    @SerializedName("AM_APP_METER_REQUIRED_FUTILITY")
    private String AM_APP_METER_REQUIRED_FUTILITY;

    @SerializedName("AM_APP_CONNECTION_TYPE")
    private String AM_APP_CONNECTION_TYPE;

    @SerializedName("APTM_NAME")
    private String APTM_NAME;

    @SerializedName("AM_APP_PREMTYPE")
    private String AM_APP_PREMTYPE;

    @SerializedName("AM_APP_GOVT_PROPERTY")
    private String AM_APP_GOVT_PROPERTY;

    @SerializedName("AM_APP_EXISTING_CONSUMER_TAG")
    private String AM_APP_EXISTING_CONSUMER_TAG;

    @SerializedName("AM_APP_EXISTING_CONSUMERNO")
    private String AM_APP_EXISTING_CONSUMERNO;

    @SerializedName("AM_APP_OTHERUSAGE")
    private String AM_APP_OTHERUSAGE;

    @SerializedName("AM_APP_FAMILY_MEMBER")
    private String AM_APP_FAMILY_MEMBER;

    @SerializedName("APPLICATION")
    private String APPLICATION;

    @SerializedName("VCM_CATNAME")
    private String VCM_CATNAME;

    @SerializedName("FATHERNAME")
    private String FATHERNAME;

    @SerializedName("AM_APP_NO_OF_FLOORS")
    private String AM_APP_NO_OF_FLOORS;

    @SerializedName("AM_APP_NO_OF_DWELLING")
    private String AM_APP_NO_OF_DWELLING;

    @SerializedName("AM_APP_NO_OF_ROOMS")
    private String AM_APP_NO_OF_ROOMS;

    @SerializedName("AM_APP_ISAUTH_CONNECTION")
    private String AM_APP_ISAUTH_CONNECTION;

    @SerializedName("AM_APP_CIVIL_CONS_CCMC_WATER")
    private String AM_APP_CIVIL_CONS_CCMC_WATER;

    @SerializedName("AM_APP_NO_OF_DWELLING_UNITS")
    private String AM_APP_NO_OF_DWELLING_UNITS;

    @SerializedName("AM_APP_ISOCCUPIER_SECURITY")
    private String AM_APP_ISOCCUPIER_SECURITY;

    @SerializedName("AM_APP_ISMSCDCL_EMPLOYEE_ID")
    private String AM_APP_ISMSCDCL_EMPLOYEE_ID;

    @SerializedName("AM_APP_ISTECH_FESIBILITY")
    private String AM_APP_ISTECH_FESIBILITY;

    @SerializedName("AM_APP_SPIPELINE")
    private String AM_APP_SPIPELINE;

    @SerializedName("AM_APP_LENGTH_MEASURE")
    private String AM_APP_LENGTH_MEASURE;

    @SerializedName("AM_APP_ROADCUTMTR")
    private String AM_APP_ROADCUTMTR;

    @SerializedName("AM_APP_ROADTYPE")
    private String AM_APP_ROADTYPE;

    @SerializedName("AM_APP_ROADOWNER")
    private String AM_APP_ROADOWNER;

    @SerializedName("AM_APP_SUBSTATION")
    private String AM_APP_SUBSTATION;

    @SerializedName("CATEGORY_NAME")
    private String CATEGORY_NAME;

    @SerializedName("PRM_NAME")
    private String PRM_NAME;

    @SerializedName("AM_APP_S_GIS_ID")
    private String AM_APP_S_GIS_ID;

    @SerializedName("PCM_PC_NAME")
    private String PCM_PC_NAME;

    @SerializedName("LM_LOT_NAME")
    private String LM_LOT_NAME;

    @SerializedName("TRM_NAME")
    private String TRM_NAME;

    @SerializedName("PM_NAME")
    private String PM_NAME;

    @SerializedName("SBM_NAME")
    private String SBM_NAME;

    @SerializedName("AM_APP_NEAR_LCONS")
    private String AM_APP_NEAR_LCONS;

    @SerializedName("OWNERSHIP")
    private String OWNERSHIP;

    @SerializedName("UPLOADEDDOCS")
    private String UPLOADEDDOCS;

    @SerializedName("LATI")
    private String LATI;

    @SerializedName("LONGI")
    private String LONGI;

    @SerializedName("AM_APP_ISWATER_AVAILINDP")
    private String AM_APP_ISWATER_AVAILINDP;

    @SerializedName("AM_APP_DISTRIBUTIONID")
    private String AM_APP_DISTRIBUTIONID;

    @SerializedName("AM_APP_SRIGHTCONSUMER")
    private String AM_APP_SRIGHTCONSUMER;

    @SerializedName("AM_APP_SLEFTCONSUMER")
    private String AM_APP_SLEFTCONSUMER;

    @SerializedName("AM_APP_ISROADCUTTING_REQD")
    private String AM_APP_ISROADCUTTING_REQD;

    @SerializedName("AM_APP_ISCONSTRUCTION_COMP")
    private String AM_APP_ISCONSTRUCTION_COMP;

    @SerializedName("AM_APP_ISEXISTINGCONN")
    private String AM_APP_ISEXISTINGCONN;

    @SerializedName("AM_APP_EXISTINGCONN")
    private String AM_APP_EXISTINGCONN;

    @SerializedName("AM_APP_ISDWELLING_HASCONN")
    private String AM_APP_ISDWELLING_HASCONN;

    @SerializedName("AM_APP_STORAGECAPACITYTYPE")
    private String AM_APP_STORAGECAPACITYTYPE;

    @SerializedName("AM_APP_STORAGECAPACITY")
    private String AM_APP_STORAGECAPACITY;

    @SerializedName("AM_APP_ISINTERNAL_NETWORK")
    private String AM_APP_ISINTERNAL_NETWORK;

    @SerializedName("AM_APP_ISDISPOSAL_OFWATER")
    private String AM_APP_ISDISPOSAL_OFWATER;

    @SerializedName("AM_APP_ISRAINWATERHARVEST")
    private String AM_APP_ISRAINWATERHARVEST;

    @SerializedName("AM_APP_METERLOCATION")
    private String AM_APP_METERLOCATION;

    @SerializedName("AM_APP_METERSIZE")
    private String AM_APP_METERSIZE;


      @SerializedName("AM_APP_COSOWO_NAME")
    private String AM_APP_COSOWO_NAME;

    @SerializedName("MTRM_SERIAL_NO")
    private String MTRM_SERIAL_NO;

    @SerializedName("MTRM_CURRENT_READING")
    private String MTRM_CURRENT_READING;

    @SerializedName("isgovtemp")
    private String isgovtemp;

    @SerializedName("NOOFVISITS")
    private String NOOFVISITS;


    private String pass;
    private String pass1;

    private boolean expanded;



    public SiteVisitModel() {
    }

    public SiteVisitModel(String AM_APP_ISAUTH_CONNECTION, String AM_APP_CIVIL_CONS_CCMC_WATER,
                          String AM_APP_ISOCCUPIER_SECURITY, String AM_APP_ISMSCDCL_EMPLOYEE_ID) {
        this.AM_APP_ISAUTH_CONNECTION = AM_APP_ISAUTH_CONNECTION;
        this.AM_APP_CIVIL_CONS_CCMC_WATER = AM_APP_CIVIL_CONS_CCMC_WATER;
        this.AM_APP_ISOCCUPIER_SECURITY = AM_APP_ISOCCUPIER_SECURITY;
        this.AM_APP_ISMSCDCL_EMPLOYEE_ID = AM_APP_ISMSCDCL_EMPLOYEE_ID;
    }
 public SiteVisitModel(String AM_APP_ISCONSTRUCTION_COMP, String AM_APP_ISDWELLING_HASCONN,
                          String AM_APP_ISINTERNAL_NETWORK, String AM_APP_ISDISPOSAL_OFWATER,
                       String AM_APP_ISRAINWATERHARVEST, String AM_APP_ISEXISTINGCONN, String AM_APP_EXISTINGCONN,
                       String AM_APP_NO_OF_DWELLING_UNITS, String AM_APP_CONNECTION_SIZE,
                       String AM_APP_METERSIZE, String AM_APP_STORAGECAPACITYTYPE, String AM_APP_STORAGECAPACITY, String AM_APP_METERLOCATION) {
        this.AM_APP_ISCONSTRUCTION_COMP = AM_APP_ISCONSTRUCTION_COMP;
        this.AM_APP_ISDWELLING_HASCONN = AM_APP_ISDWELLING_HASCONN;
        this.AM_APP_ISINTERNAL_NETWORK = AM_APP_ISINTERNAL_NETWORK;

        this.AM_APP_ISDISPOSAL_OFWATER = AM_APP_ISDISPOSAL_OFWATER;
        this.AM_APP_ISRAINWATERHARVEST = AM_APP_ISRAINWATERHARVEST;
        this.AM_APP_ISEXISTINGCONN = AM_APP_ISEXISTINGCONN;
        this.AM_APP_EXISTINGCONN = AM_APP_EXISTINGCONN;

        this.AM_APP_NO_OF_DWELLING_UNITS = AM_APP_NO_OF_DWELLING_UNITS;

        this.AM_APP_CONNECTION_SIZE = AM_APP_CONNECTION_SIZE;

        this.AM_APP_METERSIZE = AM_APP_METERSIZE;
        this.AM_APP_STORAGECAPACITYTYPE = AM_APP_STORAGECAPACITYTYPE;
        this.AM_APP_STORAGECAPACITY = AM_APP_STORAGECAPACITY;

        this.AM_APP_METERLOCATION = AM_APP_METERLOCATION;



 }

    public SiteVisitModel(String REQUEST_NO, String AM_APP_NMTITLE, String AM_APP_FNAME, String AM_APP_MNAME, String FATHERNAME, String AM_APP_SURNAME, String AM_APP_ADDRESS1,
                          String AM_APP_ADDRESS3, String AM_APP_ADDRESS2, String AM_APP_ADDRESS4, String ADDRESS,
                          String AM_APP_PINCODE, String AM_APP_PHONEM, String AM_APP_PHONE, String AM_APP_EMAIL, String AM_APP_LOCALITY, String isgovtemp) {
        this.REQUEST_NO = REQUEST_NO;
        this.AM_APP_NMTITLE = AM_APP_NMTITLE;
        this.AM_APP_FNAME = AM_APP_FNAME;
        this.AM_APP_MNAME = AM_APP_MNAME;
        this.FATHERNAME = FATHERNAME;
        this.AM_APP_SURNAME = AM_APP_SURNAME;
        this.AM_APP_ADDRESS1 = AM_APP_ADDRESS1;
        this.AM_APP_ADDRESS3 = AM_APP_ADDRESS3;

        this.AM_APP_ADDRESS2 = AM_APP_ADDRESS2;
        this.AM_APP_ADDRESS4 = AM_APP_ADDRESS4;
        this.ADDRESS = ADDRESS;
        this.AM_APP_BOREWELL=AM_APP_BOREWELL;
        this. AM_APP_IS_OPERATIONAL=AM_APP_IS_OPERATIONAL;
        this.AM_APP_LOCALITY = AM_APP_LOCALITY;
        this.AM_APP_PINCODE = AM_APP_PINCODE;
        this.AM_APP_PHONEM = AM_APP_PHONEM;
        this.AM_APP_PHONE = AM_APP_PHONE;
        this.AM_APP_EMAIL = AM_APP_EMAIL;
this.isgovtemp=isgovtemp;

    }

    public SiteVisitModel(String AM_APP_TARIFF, String AM_APP_CATEGORY,
                          String AM_APP_PREMTYPE, String AM_APP_PURPOSE,
                          String AM_APP_NO_OF_FLOORS, String AM_APP_FAMILY_MEMBER,
                          String AM_APP_NO_OF_DWELLING_UNITS,
                          String AM_APP_NO_OF_ROOMS
                          ) {
        this.AM_APP_TARIFF = AM_APP_TARIFF;
        this.AM_APP_CATEGORY = AM_APP_CATEGORY;
        this.AM_APP_PREMTYPE = AM_APP_PREMTYPE;
        this.AM_APP_PURPOSE = AM_APP_PURPOSE;

        this.AM_APP_NO_OF_FLOORS = AM_APP_NO_OF_FLOORS;
        this.AM_APP_FAMILY_MEMBER = AM_APP_FAMILY_MEMBER;

        this.AM_APP_NO_OF_DWELLING_UNITS = AM_APP_NO_OF_DWELLING_UNITS;

        this.AM_APP_NO_OF_ROOMS = AM_APP_NO_OF_ROOMS;
       /* this.AM_APP_APPLI_TYPE = AM_APP_APPLI_TYPE;
        this.AM_APP_NOOF_APPS = AM_APP_NOOF_APPS;*/
    }
    public SiteVisitModel(String pass, String pass1, String AM_APP_ISTECH_FESIBILITY, String AM_APP_SPIPELINE, String AM_APP_LENGTH_MEASURE, String AM_APP_ROADCUTMTR,
                          String AM_APP_ROADTYPE, String AM_APP_ROADOWNER, String AM_APP_CONNECTION_SIZE) {
        this.pass = pass;
        this.pass1 = pass1;
        this.AM_APP_ISTECH_FESIBILITY = AM_APP_ISTECH_FESIBILITY;
        this.AM_APP_SPIPELINE = AM_APP_SPIPELINE;
        this.AM_APP_LENGTH_MEASURE = AM_APP_LENGTH_MEASURE;
        this.AM_APP_ROADCUTMTR = AM_APP_ROADCUTMTR;

        this.AM_APP_ROADTYPE = AM_APP_ROADTYPE;
        this.AM_APP_ROADOWNER = AM_APP_ROADOWNER;
        this.AM_APP_CONNECTION_SIZE = AM_APP_CONNECTION_SIZE;
    }
   public SiteVisitModel(String AM_APP_ISTECH_FESIBILITY, String AM_APP_ISWATER_AVAILINDP, String AM_APP_ISROADCUTTING_REQD, String AM_APP_SPIPELINE,
                          String AM_APP_DISTRIBUTIONID, String AM_APP_SRIGHTCONSUMER, String AM_APP_SLEFTCONSUMER, String AM_APP_ROADCUTMTR, String AM_APP_ROADTYPE,
                         String AM_APP_ROADOWNER) {

        this.AM_APP_ISTECH_FESIBILITY = AM_APP_ISTECH_FESIBILITY;
        this.AM_APP_ISWATER_AVAILINDP = AM_APP_ISWATER_AVAILINDP;
        this.AM_APP_ISROADCUTTING_REQD = AM_APP_ISROADCUTTING_REQD;
       this.AM_APP_SPIPELINE = AM_APP_SPIPELINE;

        this.AM_APP_DISTRIBUTIONID = AM_APP_DISTRIBUTIONID;
        this.AM_APP_SRIGHTCONSUMER = AM_APP_SRIGHTCONSUMER;
        this.AM_APP_SLEFTCONSUMER = AM_APP_SLEFTCONSUMER;

        this.AM_APP_ROADCUTMTR = AM_APP_ROADCUTMTR;

        this.AM_APP_ROADTYPE = AM_APP_ROADTYPE;
        this.AM_APP_ROADOWNER = AM_APP_ROADOWNER;
         }


    public SiteVisitModel(String AM_APP_ZONE, String AM_APP_CIRCLE, String SBM_NAME, String TRM_NAME,
                          String AM_APP_PC, String AM_APP_MRC, String AM_APP_AREA) {

        this.AM_APP_ZONE = AM_APP_ZONE;
        this.AM_APP_CIRCLE = AM_APP_CIRCLE;
        this.SBM_NAME = SBM_NAME;
        this.TRM_NAME = TRM_NAME;

        this.AM_APP_PC = AM_APP_PC;

        this.AM_APP_MRC = AM_APP_MRC;
        this.AM_APP_AREA = AM_APP_AREA;

    }

    public SiteVisitModel(String REQUEST_NO,String NAME, String AM_AAPP_NO_TYPE, String AM_APP_SERVNO, String AM_APP_ZONE, String AM_APP_CIRCLE, String AM_APP_DIVISION, String AM_APP_SUB_DIVISION, String AM_APP_SECTION, String AM_APP_BU, String AM_APP_PC, String AM_APP_AREA, String AM_APP_MRC, String AM_APP_LOT, String AM_APP_SEQUENCE, String AM_APP_NEARBYSER, String AM_APP_NMTITLE, String AM_APP_FNAME, String AM_APP_MNAME, String AM_APP_SURNAME, String AM_AAPP_DATE, String AM_APP_PHONEM, String AM_APP_EMAIL, String AM_APP_ADDRESS1, String AM_APP_ADDRESS2, String AM_APP_ADDRESS3, String AM_APP_ADDRESS4, String AM_APP_PINCODE, String ADDRESS,String AM_APP_APPLI_TYPE,String AM_APP_NOOF_APPS,String NEWADDRESS,String AM_APP_BOREWELL,String AM_APP_IS_OPERATIONAL, String AM_REMARKS, String AM_APP_ISMSCDCL_EMPLOYEE, String AM_APP_ACCEPT_DATE, String AM_APP_APPOINT_DATE, String AM_APP_PROCDT, String PROCESSTAG, String PROCESSCODE, String STATUS, String AM_APP_SOURCE_TYPE, String SOURCE, String METERREPLACE, String REASON, String REASONNAME, String AM_APP_REJECT_DATE, String AM_APP_ORESCD, String AREANAME, String ZONENAME, String SUBZONE, String AM_APP_CATEGORY, String PURPOSE_NAME, String CONNECTION_SIZE, String AM_APP_PURPOSE, String AM_APP_TRNO, String AM_APP_POLENO, String AM_APP_ASD, String AM_APP_SD, String AM_APP_SCC, String AM_APP_REGFEE, String TARIFF_NAME, String AM_APP_TARIFF, String AM_APP_AREA_TYPE, String AM_APP_TARIFF_TYPE_CD, String AM_APP_METER_INDICATION, String AM_APP_RCPT_DATE, String AM_APP_TOTALAMT, String AM_APP_PDWITHIN6, String AM_APP_METERAVLB, String AM_APP_PDRECON_TYPE, String MONTHYR, String AM_APP_PREMISE_NO, String AM_APP_TR_DISTANCE, String AM_APP_AREAPREMISE, String AM_APP_CONNECTION_SIZE, String AM_APP_STDCODE, String AM_APP_PHONE, String PROCCESCODE, String AM_APP_METER_REQUIRED, String AM_APP_BOARING_CHARGES, String AM_APP_SECUTIRY_CHARGES, String AM_APP_ADDITIONAL_CHARGES, String AM_APP_WATER_MACADAM_CHARGES, String AM_APP_REINFORCEMENT_CHARGES, String AM_APP_VISITING_CHARGES, String AM_APP_BITUMINOUSTAR_CHARGES, String AM_APP_METER_COST, String AM_APP_REGFEE1, String AM_APP_ASD1, String AM_APP_SD1, String AM_APP_DEMAND_AMT, String AM_APP_METER_REQUIRED_FUTILITY, String AM_APP_CONNECTION_TYPE, String APTM_NAME, String AM_APP_PREMTYPE, String AM_APP_GOVT_PROPERTY, String AM_APP_EXISTING_CONSUMER_TAG, String AM_APP_EXISTING_CONSUMERNO, String AM_APP_OTHERUSAGE, String AM_APP_FAMILY_MEMBER, String APPLICATION, String VCM_CATNAME, String FATHERNAME, String AM_APP_NO_OF_FLOORS, String AM_APP_NO_OF_DWELLING, String AM_APP_NO_OF_ROOMS, String AM_APP_ISAUTH_CONNECTION, String AM_APP_CIVIL_CONS_CCMC_WATER, String AM_APP_NO_OF_DWELLING_UNITS, String AM_APP_ISOCCUPIER_SECURITY, String AM_APP_ISMSCDCL_EMPLOYEE_ID, String AM_APP_ISTECH_FESIBILITY, String AM_APP_SPIPELINE, String AM_APP_LENGTH_MEASURE, String AM_APP_ROADCUTMTR, String AM_APP_ROADTYPE, String AM_APP_ROADOWNER, String AM_APP_SUBSTATION, String CATEGORY_NAME, String PRM_NAME, String AM_APP_S_GIS_ID, String PCM_PC_NAME, String LM_LOT_NAME, String TRM_NAME, String PM_NAME, String SBM_NAME, String AM_APP_NEAR_LCONS, String OWNERSHIP, String UPLOADEDDOCS, String LATI, String LONGI, String AM_APP_ISWATER_AVAILINDP, String AM_APP_DISTRIBUTIONID, String AM_APP_SRIGHTCONSUMER, String AM_APP_SLEFTCONSUMER, String AM_APP_ISROADCUTTING_REQD, String AM_APP_ISCONSTRUCTION_COMP, String AM_APP_ISEXISTINGCONN, String AM_APP_EXISTINGCONN, String AM_APP_ISDWELLING_HASCONN, String AM_APP_STORAGECAPACITYTYPE, String AM_APP_STORAGECAPACITY, String AM_APP_ISINTERNAL_NETWORK, String AM_APP_ISDISPOSAL_OFWATER, String AM_APP_ISRAINWATERHARVEST, String AM_APP_METERLOCATION, String AM_APP_METERSIZE, String AM_APP_COSOWO_NAME, String pass, String pass1, String AM_APP_LOCALITY,String MTRM_SERIAL_NO,String MTRM_CURRENT_READING,String NOOFVISITS) {
        this.REQUEST_NO = REQUEST_NO;
        this.NAME = NAME;
        this.AM_AAPP_NO_TYPE = AM_AAPP_NO_TYPE;
        this.AM_APP_SERVNO = AM_APP_SERVNO;
        this.AM_APP_ZONE = AM_APP_ZONE;
        this.AM_APP_CIRCLE = AM_APP_CIRCLE;
        this.AM_APP_DIVISION = AM_APP_DIVISION;
        this.AM_APP_SUB_DIVISION = AM_APP_SUB_DIVISION;
        this.AM_APP_SECTION = AM_APP_SECTION;
        this.AM_APP_BU = AM_APP_BU;
        this.AM_APP_PC = AM_APP_PC;
        this.AM_APP_AREA = AM_APP_AREA;
        this.AM_APP_MRC = AM_APP_MRC;
        this.AM_APP_LOT = AM_APP_LOT;
        this.AM_APP_SEQUENCE = AM_APP_SEQUENCE;
        this.AM_APP_NEARBYSER = AM_APP_NEARBYSER;
        this.AM_APP_NMTITLE = AM_APP_NMTITLE;
        this.AM_APP_FNAME = AM_APP_FNAME;
        this.AM_APP_MNAME = AM_APP_MNAME;
        this.AM_APP_SURNAME = AM_APP_SURNAME;
        this.AM_AAPP_DATE = AM_AAPP_DATE;
        this.AM_APP_PHONEM = AM_APP_PHONEM;
        this.AM_APP_EMAIL = AM_APP_EMAIL;
        this.AM_APP_ADDRESS1 = AM_APP_ADDRESS1;
        this.AM_APP_ADDRESS2 = AM_APP_ADDRESS2;
        this.AM_APP_ADDRESS3 = AM_APP_ADDRESS3;
        this.AM_APP_LOCALITY = AM_APP_LOCALITY;
        this.AM_APP_ADDRESS4 = AM_APP_ADDRESS4;
        this.AM_APP_PINCODE = AM_APP_PINCODE;
        this.ADDRESS = ADDRESS;
        this.AM_APP_APPLI_TYPE=AM_APP_APPLI_TYPE;
        this.AM_APP_NOOF_APPS=AM_APP_NOOF_APPS;
        this.NEWADDRESS=NEWADDRESS;
        this.AM_APP_BOREWELL=AM_APP_BOREWELL;
        this. AM_APP_IS_OPERATIONAL=AM_APP_IS_OPERATIONAL;
        this.AM_REMARKS = AM_REMARKS;
        this.AM_APP_ISMSCDCL_EMPLOYEE = AM_APP_ISMSCDCL_EMPLOYEE;
        this.AM_APP_ACCEPT_DATE = AM_APP_ACCEPT_DATE;
        this.AM_APP_APPOINT_DATE = AM_APP_APPOINT_DATE;
        this.AM_APP_PROCDT = AM_APP_PROCDT;
        this.PROCESSTAG = PROCESSTAG;
        this.PROCESSCODE = PROCESSCODE;
        this.STATUS = STATUS;
        this.AM_APP_SOURCE_TYPE = AM_APP_SOURCE_TYPE;
        this.SOURCE = SOURCE;
        this.METERREPLACE = METERREPLACE;
        this.REASON = REASON;
        this.REASONNAME = REASONNAME;
        this.AM_APP_REJECT_DATE = AM_APP_REJECT_DATE;
        this.AM_APP_ORESCD = AM_APP_ORESCD;
        this.AREANAME = AREANAME;
        this.ZONENAME = ZONENAME;
        this.SUBZONE = SUBZONE;
        this.AM_APP_CATEGORY = AM_APP_CATEGORY;
        this.PURPOSE_NAME = PURPOSE_NAME;
        this.CONNECTION_SIZE = CONNECTION_SIZE;
        this.AM_APP_PURPOSE = AM_APP_PURPOSE;
        this.AM_APP_TRNO = AM_APP_TRNO;
        this.AM_APP_POLENO = AM_APP_POLENO;
        this.AM_APP_ASD = AM_APP_ASD;
        this.AM_APP_SD = AM_APP_SD;
        this.AM_APP_SCC = AM_APP_SCC;
        this.AM_APP_REGFEE = AM_APP_REGFEE;
        this.TARIFF_NAME = TARIFF_NAME;
        this.AM_APP_TARIFF = AM_APP_TARIFF;
        this.AM_APP_AREA_TYPE = AM_APP_AREA_TYPE;
        this.AM_APP_TARIFF_TYPE_CD = AM_APP_TARIFF_TYPE_CD;
        this.AM_APP_METER_INDICATION = AM_APP_METER_INDICATION;
        this.AM_APP_RCPT_DATE = AM_APP_RCPT_DATE;
        this.AM_APP_TOTALAMT = AM_APP_TOTALAMT;
        this.AM_APP_PDWITHIN6 = AM_APP_PDWITHIN6;
        this.AM_APP_METERAVLB = AM_APP_METERAVLB;
        this.AM_APP_PDRECON_TYPE = AM_APP_PDRECON_TYPE;
        this.MONTHYR = MONTHYR;
        this.AM_APP_PREMISE_NO = AM_APP_PREMISE_NO;
        this.AM_APP_TR_DISTANCE = AM_APP_TR_DISTANCE;
        this.AM_APP_AREAPREMISE = AM_APP_AREAPREMISE;
        this.AM_APP_CONNECTION_SIZE = AM_APP_CONNECTION_SIZE;
        this.AM_APP_STDCODE = AM_APP_STDCODE;
        this.AM_APP_PHONE = AM_APP_PHONE;
        this.PROCCESCODE = PROCCESCODE;
        this.AM_APP_METER_REQUIRED = AM_APP_METER_REQUIRED;
        this.AM_APP_BOARING_CHARGES = AM_APP_BOARING_CHARGES;
        this.AM_APP_SECUTIRY_CHARGES = AM_APP_SECUTIRY_CHARGES;
        this.AM_APP_ADDITIONAL_CHARGES = AM_APP_ADDITIONAL_CHARGES;
        this.AM_APP_WATER_MACADAM_CHARGES = AM_APP_WATER_MACADAM_CHARGES;
        this.AM_APP_REINFORCEMENT_CHARGES = AM_APP_REINFORCEMENT_CHARGES;
        this.AM_APP_VISITING_CHARGES = AM_APP_VISITING_CHARGES;
        this.AM_APP_BITUMINOUSTAR_CHARGES = AM_APP_BITUMINOUSTAR_CHARGES;
        this.AM_APP_METER_COST = AM_APP_METER_COST;
        this.AM_APP_REGFEE1 = AM_APP_REGFEE1;
        this.AM_APP_ASD1 = AM_APP_ASD1;
        this.AM_APP_SD1 = AM_APP_SD1;
        this.AM_APP_DEMAND_AMT = AM_APP_DEMAND_AMT;
        this.AM_APP_METER_REQUIRED_FUTILITY = AM_APP_METER_REQUIRED_FUTILITY;
        this.AM_APP_CONNECTION_TYPE = AM_APP_CONNECTION_TYPE;
        this.APTM_NAME = APTM_NAME;
        this.AM_APP_PREMTYPE = AM_APP_PREMTYPE;
        this.AM_APP_GOVT_PROPERTY = AM_APP_GOVT_PROPERTY;
        this.AM_APP_EXISTING_CONSUMER_TAG = AM_APP_EXISTING_CONSUMER_TAG;
        this.AM_APP_EXISTING_CONSUMERNO = AM_APP_EXISTING_CONSUMERNO;
        this.AM_APP_OTHERUSAGE = AM_APP_OTHERUSAGE;
        this.AM_APP_FAMILY_MEMBER = AM_APP_FAMILY_MEMBER;
        this.APPLICATION = APPLICATION;
        this.VCM_CATNAME = VCM_CATNAME;
        this.FATHERNAME = FATHERNAME;
        this.AM_APP_NO_OF_FLOORS = AM_APP_NO_OF_FLOORS;
        this.AM_APP_NO_OF_DWELLING = AM_APP_NO_OF_DWELLING;
        this.AM_APP_NO_OF_ROOMS = AM_APP_NO_OF_ROOMS;
        this.AM_APP_ISAUTH_CONNECTION = AM_APP_ISAUTH_CONNECTION;
        this.AM_APP_CIVIL_CONS_CCMC_WATER = AM_APP_CIVIL_CONS_CCMC_WATER;
        this.AM_APP_NO_OF_DWELLING_UNITS = AM_APP_NO_OF_DWELLING_UNITS;
        this.AM_APP_ISOCCUPIER_SECURITY = AM_APP_ISOCCUPIER_SECURITY;
        this.AM_APP_ISMSCDCL_EMPLOYEE_ID = AM_APP_ISMSCDCL_EMPLOYEE_ID;
        this.AM_APP_ISTECH_FESIBILITY = AM_APP_ISTECH_FESIBILITY;
        this.AM_APP_SPIPELINE = AM_APP_SPIPELINE;
        this.AM_APP_LENGTH_MEASURE = AM_APP_LENGTH_MEASURE;
        this.AM_APP_ROADCUTMTR = AM_APP_ROADCUTMTR;
        this.AM_APP_ROADTYPE = AM_APP_ROADTYPE;
        this.AM_APP_ROADOWNER = AM_APP_ROADOWNER;
        this.AM_APP_SUBSTATION = AM_APP_SUBSTATION;
        this.CATEGORY_NAME = CATEGORY_NAME;
        this.PRM_NAME = PRM_NAME;
        this.AM_APP_S_GIS_ID = AM_APP_S_GIS_ID;
        this.PCM_PC_NAME = PCM_PC_NAME;
        this.LM_LOT_NAME = LM_LOT_NAME;
        this.TRM_NAME = TRM_NAME;
        this.PM_NAME = PM_NAME;
        this.SBM_NAME = SBM_NAME;
        this.AM_APP_NEAR_LCONS = AM_APP_NEAR_LCONS;
        this.OWNERSHIP = OWNERSHIP;
        this.UPLOADEDDOCS = UPLOADEDDOCS;
        this.LATI = LATI;
        this.LONGI = LONGI;
        this.AM_APP_ISWATER_AVAILINDP = AM_APP_ISWATER_AVAILINDP;
        this.AM_APP_DISTRIBUTIONID = AM_APP_DISTRIBUTIONID;
        this.AM_APP_SRIGHTCONSUMER = AM_APP_SRIGHTCONSUMER;
        this.AM_APP_SLEFTCONSUMER = AM_APP_SLEFTCONSUMER;
        this.AM_APP_ISROADCUTTING_REQD = AM_APP_ISROADCUTTING_REQD;
        this.AM_APP_ISCONSTRUCTION_COMP = AM_APP_ISCONSTRUCTION_COMP;
        this.AM_APP_ISEXISTINGCONN = AM_APP_ISEXISTINGCONN;
        this.AM_APP_EXISTINGCONN = AM_APP_EXISTINGCONN;
        this.AM_APP_ISDWELLING_HASCONN = AM_APP_ISDWELLING_HASCONN;
        this.AM_APP_STORAGECAPACITYTYPE = AM_APP_STORAGECAPACITYTYPE;
        this.AM_APP_STORAGECAPACITY = AM_APP_STORAGECAPACITY;
        this.AM_APP_ISINTERNAL_NETWORK = AM_APP_ISINTERNAL_NETWORK;
        this.AM_APP_ISDISPOSAL_OFWATER = AM_APP_ISDISPOSAL_OFWATER;
        this.AM_APP_ISRAINWATERHARVEST = AM_APP_ISRAINWATERHARVEST;
        this.AM_APP_METERLOCATION = AM_APP_METERLOCATION;
        this.AM_APP_METERSIZE = AM_APP_METERSIZE;
        this.pass = pass;
        this.pass1 = pass1;
        this.MTRM_SERIAL_NO = MTRM_SERIAL_NO;
        this.MTRM_CURRENT_READING= MTRM_CURRENT_READING;
        this.NOOFVISITS= NOOFVISITS;

    }


    protected SiteVisitModel(Parcel in) {
        REQUEST_NO = in.readString();
        NAME = in.readString();
        AM_AAPP_NO_TYPE = in.readString();
        AM_APP_SERVNO = in.readString();
        AM_APP_ZONE = in.readString();
        AM_APP_CIRCLE = in.readString();
        AM_APP_DIVISION = in.readString();
        AM_APP_SUB_DIVISION = in.readString();
        AM_APP_SECTION = in.readString();
        AM_APP_BU = in.readString();
        AM_APP_PC = in.readString();
        AM_APP_AREA = in.readString();
        AM_APP_MRC = in.readString();
        AM_APP_LOT = in.readString();
        AM_APP_SEQUENCE = in.readString();
        AM_APP_NEARBYSER = in.readString();
        AM_APP_NMTITLE = in.readString();
        AM_APP_FNAME = in.readString();
        AM_APP_MNAME = in.readString();
        AM_APP_SURNAME = in.readString();
        AM_AAPP_DATE = in.readString();
        AM_APP_PHONEM = in.readString();
        AM_APP_EMAIL = in.readString();
        AM_APP_ADDRESS1 = in.readString();
        AM_APP_ADDRESS2 = in.readString();
        AM_APP_ADDRESS3 = in.readString();
        AM_APP_LOCALITY = in.readString();
        AM_APP_ADDRESS4 = in.readString();
        AM_APP_PINCODE = in.readString();
        ADDRESS = in.readString();
        AM_APP_APPLI_TYPE = in.readString();
        AM_APP_NOOF_APPS=in.readString();
        NEWADDRESS=in.readString();
        AM_APP_BOREWELL= in.readString();
         AM_APP_IS_OPERATIONAL= in.readString();
        AM_REMARKS = in.readString();
        AM_APP_ISMSCDCL_EMPLOYEE = in.readString();
        AM_APP_ACCEPT_DATE = in.readString();
        AM_APP_APPOINT_DATE = in.readString();
        AM_APP_PROCDT = in.readString();
        PROCESSTAG = in.readString();
        PROCESSCODE = in.readString();
        STATUS = in.readString();
        AM_APP_SOURCE_TYPE = in.readString();
        SOURCE = in.readString();
        METERREPLACE = in.readString();
        REASON = in.readString();
        REASONNAME = in.readString();
        AM_APP_REJECT_DATE = in.readString();
        AM_APP_ORESCD = in.readString();
        AREANAME = in.readString();
        ZONENAME = in.readString();
        SUBZONE = in.readString();
        AM_APP_CATEGORY = in.readString();
        PURPOSE_NAME = in.readString();
        CONNECTION_SIZE = in.readString();
        AM_APP_PURPOSE = in.readString();
        AM_APP_TRNO = in.readString();
        AM_APP_POLENO = in.readString();
        AM_APP_ASD = in.readString();
        AM_APP_SD = in.readString();
        AM_APP_SCC = in.readString();
        AM_APP_REGFEE = in.readString();
        TARIFF_NAME = in.readString();
        AM_APP_TARIFF = in.readString();
        AM_APP_AREA_TYPE = in.readString();
        AM_APP_TARIFF_TYPE_CD = in.readString();
        AM_APP_METER_INDICATION = in.readString();
        AM_APP_RCPT_DATE = in.readString();
        AM_APP_TOTALAMT = in.readString();
        AM_APP_PDWITHIN6 = in.readString();
        AM_APP_METERAVLB = in.readString();
        AM_APP_PDRECON_TYPE = in.readString();
        MONTHYR = in.readString();
        AM_APP_PREMISE_NO = in.readString();
        AM_APP_TR_DISTANCE = in.readString();
        AM_APP_AREAPREMISE = in.readString();
        AM_APP_CONNECTION_SIZE = in.readString();
        AM_APP_STDCODE = in.readString();
        AM_APP_PHONE = in.readString();
        PROCCESCODE = in.readString();
        AM_APP_METER_REQUIRED = in.readString();
        AM_APP_BOARING_CHARGES = in.readString();
        AM_APP_SECUTIRY_CHARGES = in.readString();
        AM_APP_ADDITIONAL_CHARGES = in.readString();
        AM_APP_WATER_MACADAM_CHARGES = in.readString();
        AM_APP_REINFORCEMENT_CHARGES = in.readString();
        AM_APP_VISITING_CHARGES = in.readString();
        AM_APP_BITUMINOUSTAR_CHARGES = in.readString();
        AM_APP_METER_COST = in.readString();
        AM_APP_REGFEE1 = in.readString();
        AM_APP_ASD1 = in.readString();
        AM_APP_SD1 = in.readString();
        AM_APP_DEMAND_AMT = in.readString();
        AM_APP_METER_REQUIRED_FUTILITY = in.readString();
        AM_APP_CONNECTION_TYPE = in.readString();
        APTM_NAME = in.readString();
        AM_APP_PREMTYPE = in.readString();
        AM_APP_GOVT_PROPERTY = in.readString();
        AM_APP_EXISTING_CONSUMER_TAG = in.readString();
        AM_APP_EXISTING_CONSUMERNO = in.readString();
        AM_APP_OTHERUSAGE = in.readString();
        AM_APP_FAMILY_MEMBER = in.readString();
        APPLICATION = in.readString();
        VCM_CATNAME = in.readString();
        FATHERNAME = in.readString();
        AM_APP_NO_OF_FLOORS = in.readString();
        AM_APP_NO_OF_DWELLING = in.readString();
        AM_APP_NO_OF_ROOMS = in.readString();
        AM_APP_ISAUTH_CONNECTION = in.readString();
        AM_APP_CIVIL_CONS_CCMC_WATER = in.readString();
        AM_APP_NO_OF_DWELLING_UNITS = in.readString();
        AM_APP_ISOCCUPIER_SECURITY = in.readString();
        AM_APP_ISMSCDCL_EMPLOYEE_ID = in.readString();
        AM_APP_ISTECH_FESIBILITY = in.readString();
        AM_APP_SPIPELINE = in.readString();
        AM_APP_LENGTH_MEASURE = in.readString();
        AM_APP_ROADCUTMTR = in.readString();
        AM_APP_ROADTYPE = in.readString();
        AM_APP_ROADOWNER = in.readString();
        AM_APP_SUBSTATION = in.readString();
        CATEGORY_NAME = in.readString();
        PRM_NAME = in.readString();
        AM_APP_S_GIS_ID = in.readString();
        PCM_PC_NAME = in.readString();
        LM_LOT_NAME = in.readString();
        TRM_NAME = in.readString();
        PM_NAME = in.readString();
        SBM_NAME = in.readString();
        AM_APP_NEAR_LCONS = in.readString();
        OWNERSHIP = in.readString();
        UPLOADEDDOCS = in.readString();
        LATI = in.readString();
        LONGI = in.readString();
        AM_APP_ISWATER_AVAILINDP = in.readString();
        AM_APP_DISTRIBUTIONID = in.readString();
        AM_APP_SRIGHTCONSUMER = in.readString();
        AM_APP_SLEFTCONSUMER = in.readString();
        AM_APP_ISROADCUTTING_REQD = in.readString();
        AM_APP_ISCONSTRUCTION_COMP = in.readString();
        AM_APP_ISEXISTINGCONN = in.readString();
        AM_APP_EXISTINGCONN = in.readString();
        AM_APP_ISDWELLING_HASCONN = in.readString();
        AM_APP_STORAGECAPACITYTYPE = in.readString();
        AM_APP_STORAGECAPACITY = in.readString();
        AM_APP_ISINTERNAL_NETWORK = in.readString();
        AM_APP_ISDISPOSAL_OFWATER = in.readString();
        AM_APP_ISRAINWATERHARVEST = in.readString();
        AM_APP_METERLOCATION = in.readString();
        AM_APP_METERSIZE = in.readString();
        AM_APP_COSOWO_NAME = in.readString();

        pass = in.readString();
        pass1 = in.readString();
        MTRM_SERIAL_NO = in.readString();
        MTRM_CURRENT_READING=in.readString();
        NOOFVISITS=in.readString();
    }

    public static final Creator<SiteVisitModel> CREATOR = new Creator<SiteVisitModel>() {
        @Override
        public SiteVisitModel createFromParcel(Parcel in) {
            return new SiteVisitModel(in);
        }

        @Override
        public SiteVisitModel[] newArray(int size) {
            return new SiteVisitModel[size];
        }
    };



    public String getREQUEST_NO() {
        return REQUEST_NO;
    }

    public void setREQUEST_NO(String REQUEST_NO) {
        this.REQUEST_NO = REQUEST_NO;
    }

    public String getIsgovtemp() {
        return isgovtemp;
    }

    public void setIsgovtemp(String isgovtemp) {
        this.isgovtemp = isgovtemp;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getAM_AAPP_NO_TYPE() {
        return AM_AAPP_NO_TYPE;
    }

    public void setAM_AAPP_NO_TYPE(String AM_AAPP_NO_TYPE) {
        this.AM_AAPP_NO_TYPE = AM_AAPP_NO_TYPE;
    }

    public String getAM_APP_SERVNO() {
        return AM_APP_SERVNO;
    }

    public void setAM_APP_SERVNO(String AM_APP_SERVNO) {
        this.AM_APP_SERVNO = AM_APP_SERVNO;
    }

    public String getAM_APP_ZONE() {
        return AM_APP_ZONE;
    }

    public void setAM_APP_ZONE(String AM_APP_ZONE) {
        this.AM_APP_ZONE = AM_APP_ZONE;
    }

    public String getAM_APP_CIRCLE() {
        return AM_APP_CIRCLE;
    }

    public void setAM_APP_CIRCLE(String AM_APP_CIRCLE) {
        this.AM_APP_CIRCLE = AM_APP_CIRCLE;
    }

    public String getAM_APP_DIVISION() {
        return AM_APP_DIVISION;
    }

    public void setAM_APP_DIVISION(String AM_APP_DIVISION) {
        this.AM_APP_DIVISION = AM_APP_DIVISION;
    }

    public String getAM_APP_SUB_DIVISION() {
        return AM_APP_SUB_DIVISION;
    }

    public void setAM_APP_SUB_DIVISION(String AM_APP_SUB_DIVISION) {
        this.AM_APP_SUB_DIVISION = AM_APP_SUB_DIVISION;
    }

    public String getAM_APP_SECTION() {
        return AM_APP_SECTION;
    }

    public void setAM_APP_SECTION(String AM_APP_SECTION) {
        this.AM_APP_SECTION = AM_APP_SECTION;
    }

    public String getAM_APP_BU() {
        return AM_APP_BU;
    }

    public void setAM_APP_BU(String AM_APP_BU) {
        this.AM_APP_BU = AM_APP_BU;
    }

    public String getAM_APP_PC() {
        return AM_APP_PC;
    }

    public void setAM_APP_PC(String AM_APP_PC) {
        this.AM_APP_PC = AM_APP_PC;
    }

    public String getAM_APP_AREA() {
        return AM_APP_AREA;
    }

    public void setAM_APP_AREA(String AM_APP_AREA) {
        this.AM_APP_AREA = AM_APP_AREA;
    }

    public String getAM_APP_MRC() {
        return AM_APP_MRC;
    }

    public void setAM_APP_MRC(String AM_APP_MRC) {
        this.AM_APP_MRC = AM_APP_MRC;
    }

    public String getAM_APP_LOT() {
        return AM_APP_LOT;
    }

    public void setAM_APP_LOT(String AM_APP_LOT) {
        this.AM_APP_LOT = AM_APP_LOT;
    }

    public String getAM_APP_SEQUENCE() {
        return AM_APP_SEQUENCE;
    }

    public void setAM_APP_SEQUENCE(String AM_APP_SEQUENCE) {
        this.AM_APP_SEQUENCE = AM_APP_SEQUENCE;
    }

    public String getAM_APP_NEARBYSER() {
        return AM_APP_NEARBYSER;
    }

    public void setAM_APP_NEARBYSER(String AM_APP_NEARBYSER) {
        this.AM_APP_NEARBYSER = AM_APP_NEARBYSER;
    }

    public String getAM_APP_NMTITLE() {
        return AM_APP_NMTITLE;
    }

    public void setAM_APP_NMTITLE(String AM_APP_NMTITLE) {
        this.AM_APP_NMTITLE = AM_APP_NMTITLE;
    }

    public String getAM_APP_FNAME() {
        return AM_APP_FNAME;
    }

    public void setAM_APP_FNAME(String AM_APP_FNAME) {
        this.AM_APP_FNAME = AM_APP_FNAME;
    }

    public String getAM_APP_MNAME() {
        return AM_APP_MNAME;
    }

    public void setAM_APP_MNAME(String AM_APP_MNAME) {
        this.AM_APP_MNAME = AM_APP_MNAME;
    }

    public String getAM_APP_SURNAME() {
        return AM_APP_SURNAME;
    }

    public void setAM_APP_SURNAME(String AM_APP_SURNAME) {
        this.AM_APP_SURNAME = AM_APP_SURNAME;
    }

    public String getAM_AAPP_DATE() {
        return AM_AAPP_DATE;
    }

    public void setAM_AAPP_DATE(String AM_AAPP_DATE) {
        this.AM_AAPP_DATE = AM_AAPP_DATE;
    }

    public String getAM_APP_PHONEM() {
        return AM_APP_PHONEM;
    }

    public void setAM_APP_PHONEM(String AM_APP_PHONEM) {
        this.AM_APP_PHONEM = AM_APP_PHONEM;
    }

    public String getAM_APP_EMAIL() {
        return AM_APP_EMAIL;
    }

    public void setAM_APP_EMAIL(String AM_APP_EMAIL) {
        this.AM_APP_EMAIL = AM_APP_EMAIL;
    }

    public String getAM_APP_ADDRESS1() {
        return AM_APP_ADDRESS1;
    }

    public void setAM_APP_ADDRESS1(String AM_APP_ADDRESS1) {
        this.AM_APP_ADDRESS1 = AM_APP_ADDRESS1;
    }

    public String getAM_APP_ADDRESS2() {
        return AM_APP_ADDRESS2;
    }

    public void setAM_APP_ADDRESS2(String AM_APP_ADDRESS2) {
        this.AM_APP_ADDRESS2 = AM_APP_ADDRESS2;
    }

    public String getAM_APP_ADDRESS3() {
        return AM_APP_ADDRESS3;
    }

    public void setAM_APP_ADDRESS3(String AM_APP_ADDRESS3) {
        this.AM_APP_ADDRESS3 = AM_APP_ADDRESS3;
    }

    public String getAM_APP_LOCALITY() {
        return AM_APP_LOCALITY;
    }

    public void setAM_APP_LOCALITY(String AM_APP_LOCALITY) {
        this.AM_APP_LOCALITY = AM_APP_LOCALITY;
    }
    public String getAM_APP_ADDRESS4() {
        return AM_APP_ADDRESS4;
    }

    public void setAM_APP_ADDRESS4(String AM_APP_ADDRESS4) {
        this.AM_APP_ADDRESS4 = AM_APP_ADDRESS4;
    }

    public String getAM_APP_PINCODE() {
        return AM_APP_PINCODE;
    }

    public void setAM_APP_PINCODE(String AM_APP_PINCODE) {
        this.AM_APP_PINCODE = AM_APP_PINCODE;
    }

    public String getADDRESS() {
        return ADDRESS;
    }
    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }
    public String getAM_APP_APPLI_TYPE() {
        return AM_APP_APPLI_TYPE;
    }
    public void setAM_APP_APPLI_TYPE(String AM_APP_APPLI_TYPE) {
        this.AM_APP_APPLI_TYPE = AM_APP_APPLI_TYPE;

    }
    public String getAM_APP_NOOF_APPS() {
        return AM_APP_NOOF_APPS;
    }
    public void setAM_APP_NOOF_APPS(String AM_APP_NOOF_APPS) {
        this.AM_APP_NOOF_APPS = AM_APP_NOOF_APPS;

    }
    public String getNEWADDRESS() {
        return NEWADDRESS;
    }
    public void setNEWADDRESS(String NEWADDRESS) {
        this.NEWADDRESS = NEWADDRESS;

    }
    public String getAM_APP_BOREWELL() {
        return AM_APP_BOREWELL;
    }

    public void setAM_APP_BOREWELL(String AM_APP_BOREWELL) {
        this.AM_APP_BOREWELL = AM_APP_BOREWELL;

    }
    public String getAM_APP_IS_OPERATIONAL() {
        return AM_APP_IS_OPERATIONAL;
    }
    public void setAM_APP_IS_OPERATIONAL(String AM_APP_IS_OPERATIONAL) {
        this.AM_APP_IS_OPERATIONAL = AM_APP_IS_OPERATIONAL;
    }

    public String getAM_REMARKS() {
        return AM_REMARKS;
    }

    public void setAM_REMARKS(String AM_REMARKS) {
        this.AM_REMARKS = AM_REMARKS;
    }

    public String getAM_APP_ISMSCDCL_EMPLOYEE() {
        return AM_APP_ISMSCDCL_EMPLOYEE;
    }

    public void setAM_APP_ISMSCDCL_EMPLOYEE(String AM_APP_ISMSCDCL_EMPLOYEE) {
        this.AM_APP_ISMSCDCL_EMPLOYEE = AM_APP_ISMSCDCL_EMPLOYEE;
    }

    public String getAM_APP_ACCEPT_DATE() {
        return AM_APP_ACCEPT_DATE;
    }

    public void setAM_APP_ACCEPT_DATE(String AM_APP_ACCEPT_DATE) {
        this.AM_APP_ACCEPT_DATE = AM_APP_ACCEPT_DATE;
    }

    public String getAM_APP_APPOINT_DATE() {
        return AM_APP_APPOINT_DATE;
    }

    public void setAM_APP_APPOINT_DATE(String AM_APP_APPOINT_DATE) {
        this.AM_APP_APPOINT_DATE = AM_APP_APPOINT_DATE;
    }

    public String getAM_APP_PROCDT() {
        return AM_APP_PROCDT;
    }

    public void setAM_APP_PROCDT(String AM_APP_PROCDT) {
        this.AM_APP_PROCDT = AM_APP_PROCDT;
    }

    public String getPROCESSTAG() {
        return PROCESSTAG;
    }

    public void setPROCESSTAG(String PROCESSTAG) {
        this.PROCESSTAG = PROCESSTAG;
    }

    public String getPROCESSCODE() {
        return PROCESSCODE;
    }

    public void setPROCESSCODE(String PROCESSCODE) {
        this.PROCESSCODE = PROCESSCODE;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getAM_APP_SOURCE_TYPE() {
        return AM_APP_SOURCE_TYPE;
    }

    public void setAM_APP_SOURCE_TYPE(String AM_APP_SOURCE_TYPE) {
        this.AM_APP_SOURCE_TYPE = AM_APP_SOURCE_TYPE;
    }

    public String getSOURCE() {
        return SOURCE;
    }

    public void setSOURCE(String SOURCE) {
        this.SOURCE = SOURCE;
    }

    public String getMETERREPLACE() {
        return METERREPLACE;
    }

    public void setMETERREPLACE(String METERREPLACE) {
        this.METERREPLACE = METERREPLACE;
    }

    public String getREASON() {
        return REASON;
    }

    public void setREASON(String REASON) {
        this.REASON = REASON;
    }

    public String getREASONNAME() {
        return REASONNAME;
    }

    public void setREASONNAME(String REASONNAME) {
        this.REASONNAME = REASONNAME;
    }

    public String getAM_APP_REJECT_DATE() {
        return AM_APP_REJECT_DATE;
    }

    public void setAM_APP_REJECT_DATE(String AM_APP_REJECT_DATE) {
        this.AM_APP_REJECT_DATE = AM_APP_REJECT_DATE;
    }

    public String getAM_APP_ORESCD() {
        return AM_APP_ORESCD;
    }

    public void setAM_APP_ORESCD(String AM_APP_ORESCD) {
        this.AM_APP_ORESCD = AM_APP_ORESCD;
    }

    public String getAREANAME() {
        return AREANAME;
    }

    public void setAREANAME(String AREANAME) {
        this.AREANAME = AREANAME;
    }

    public String getZONENAME() {
        return ZONENAME;
    }

    public void setZONENAME(String ZONENAME) {
        this.ZONENAME = ZONENAME;
    }

    public String getSUBZONE() {
        return SUBZONE;
    }

    public void setSUBZONE(String SUBZONE) {
        this.SUBZONE = SUBZONE;
    }

    public String getAM_APP_CATEGORY() {
        return AM_APP_CATEGORY;
    }

    public void setAM_APP_CATEGORY(String AM_APP_CATEGORY) {
        this.AM_APP_CATEGORY = AM_APP_CATEGORY;
    }

    public String getPURPOSE_NAME() {
        return PURPOSE_NAME;
    }

    public void setPURPOSE_NAME(String PURPOSE_NAME) {
        this.PURPOSE_NAME = PURPOSE_NAME;
    }

    public String getCONNECTION_SIZE() {
        return CONNECTION_SIZE;
    }

    public void setCONNECTION_SIZE(String CONNECTION_SIZE) {
        this.CONNECTION_SIZE = CONNECTION_SIZE;
    }

    public String getAM_APP_PURPOSE() {
        return AM_APP_PURPOSE;
    }

    public void setAM_APP_PURPOSE(String AM_APP_PURPOSE) {
        this.AM_APP_PURPOSE = AM_APP_PURPOSE;
    }

    public String getAM_APP_TRNO() {
        return AM_APP_TRNO;
    }

    public void setAM_APP_TRNO(String AM_APP_TRNO) {
        this.AM_APP_TRNO = AM_APP_TRNO;
    }

    public String getAM_APP_POLENO() {
        return AM_APP_POLENO;
    }

    public void setAM_APP_POLENO(String AM_APP_POLENO) {
        this.AM_APP_POLENO = AM_APP_POLENO;
    }

    public String getAM_APP_ASD() {
        return AM_APP_ASD;
    }

    public void setAM_APP_ASD(String AM_APP_ASD) {
        this.AM_APP_ASD = AM_APP_ASD;
    }

    public String getAM_APP_SD() {
        return AM_APP_SD;
    }

    public void setAM_APP_SD(String AM_APP_SD) {
        this.AM_APP_SD = AM_APP_SD;
    }

    public String getAM_APP_SCC() {
        return AM_APP_SCC;
    }

    public void setAM_APP_SCC(String AM_APP_SCC) {
        this.AM_APP_SCC = AM_APP_SCC;
    }

    public String getAM_APP_REGFEE() {
        return AM_APP_REGFEE;
    }

    public void setAM_APP_REGFEE(String AM_APP_REGFEE) {
        this.AM_APP_REGFEE = AM_APP_REGFEE;
    }

    public String getTARIFF_NAME() {
        return TARIFF_NAME;
    }

    public void setTARIFF_NAME(String TARIFF_NAME) {
        this.TARIFF_NAME = TARIFF_NAME;
    }

    public String getAM_APP_TARIFF() {
        return AM_APP_TARIFF;
    }

    public void setAM_APP_TARIFF(String AM_APP_TARIFF) {
        this.AM_APP_TARIFF = AM_APP_TARIFF;
    }

    public String getAM_APP_AREA_TYPE() {
        return AM_APP_AREA_TYPE;
    }

    public void setAM_APP_AREA_TYPE(String AM_APP_AREA_TYPE) {
        this.AM_APP_AREA_TYPE = AM_APP_AREA_TYPE;
    }

    public String getAM_APP_TARIFF_TYPE_CD() {
        return AM_APP_TARIFF_TYPE_CD;
    }

    public void setAM_APP_TARIFF_TYPE_CD(String AM_APP_TARIFF_TYPE_CD) {
        this.AM_APP_TARIFF_TYPE_CD = AM_APP_TARIFF_TYPE_CD;
    }

    public String getAM_APP_METER_INDICATION() {
        return AM_APP_METER_INDICATION;
    }

    public void setAM_APP_METER_INDICATION(String AM_APP_METER_INDICATION) {
        this.AM_APP_METER_INDICATION = AM_APP_METER_INDICATION;
    }

    public String getAM_APP_RCPT_DATE() {
        return AM_APP_RCPT_DATE;
    }

    public void setAM_APP_RCPT_DATE(String AM_APP_RCPT_DATE) {
        this.AM_APP_RCPT_DATE = AM_APP_RCPT_DATE;
    }

    public String getAM_APP_TOTALAMT() {
        return AM_APP_TOTALAMT;
    }

    public void setAM_APP_TOTALAMT(String AM_APP_TOTALAMT) {
        this.AM_APP_TOTALAMT = AM_APP_TOTALAMT;
    }

    public String getAM_APP_PDWITHIN6() {
        return AM_APP_PDWITHIN6;
    }

    public void setAM_APP_PDWITHIN6(String AM_APP_PDWITHIN6) {
        this.AM_APP_PDWITHIN6 = AM_APP_PDWITHIN6;
    }

    public String getAM_APP_METERAVLB() {
        return AM_APP_METERAVLB;
    }

    public void setAM_APP_METERAVLB(String AM_APP_METERAVLB) {
        this.AM_APP_METERAVLB = AM_APP_METERAVLB;
    }

    public String getAM_APP_PDRECON_TYPE() {
        return AM_APP_PDRECON_TYPE;
    }

    public void setAM_APP_PDRECON_TYPE(String AM_APP_PDRECON_TYPE) {
        this.AM_APP_PDRECON_TYPE = AM_APP_PDRECON_TYPE;
    }

    public String getMONTHYR() {
        return MONTHYR;
    }

    public void setMONTHYR(String MONTHYR) {
        this.MONTHYR = MONTHYR;
    }

    public String getAM_APP_PREMISE_NO() {
        return AM_APP_PREMISE_NO;
    }

    public void setAM_APP_PREMISE_NO(String AM_APP_PREMISE_NO) {
        this.AM_APP_PREMISE_NO = AM_APP_PREMISE_NO;
    }

    public String getAM_APP_TR_DISTANCE() {
        return AM_APP_TR_DISTANCE;
    }

    public void setAM_APP_TR_DISTANCE(String AM_APP_TR_DISTANCE) {
        this.AM_APP_TR_DISTANCE = AM_APP_TR_DISTANCE;
    }

    public String getAM_APP_AREAPREMISE() {
        return AM_APP_AREAPREMISE;
    }

    public void setAM_APP_AREAPREMISE(String AM_APP_AREAPREMISE) {
        this.AM_APP_AREAPREMISE = AM_APP_AREAPREMISE;
    }

    public String getAM_APP_CONNECTION_SIZE() {
        return AM_APP_CONNECTION_SIZE;
    }

    public void setAM_APP_CONNECTION_SIZE(String AM_APP_CONNECTION_SIZE) {
        this.AM_APP_CONNECTION_SIZE = AM_APP_CONNECTION_SIZE;
    }

    public String getAM_APP_STDCODE() {
        return AM_APP_STDCODE;
    }

    public void setAM_APP_STDCODE(String AM_APP_STDCODE) {
        this.AM_APP_STDCODE = AM_APP_STDCODE;
    }

    public String getAM_APP_PHONE() {
        return AM_APP_PHONE;
    }

    public void setAM_APP_PHONE(String AM_APP_PHONE) {
        this.AM_APP_PHONE = AM_APP_PHONE;
    }

    public String getPROCCESCODE() {
        return PROCCESCODE;
    }

    public void setPROCCESCODE(String PROCCESCODE) {
        this.PROCCESCODE = PROCCESCODE;
    }

    public String getAM_APP_METER_REQUIRED() {
        return AM_APP_METER_REQUIRED;
    }

    public void setAM_APP_METER_REQUIRED(String AM_APP_METER_REQUIRED) {
        this.AM_APP_METER_REQUIRED = AM_APP_METER_REQUIRED;
    }

    public String getAM_APP_BOARING_CHARGES() {
        return AM_APP_BOARING_CHARGES;
    }

    public void setAM_APP_BOARING_CHARGES(String AM_APP_BOARING_CHARGES) {
        this.AM_APP_BOARING_CHARGES = AM_APP_BOARING_CHARGES;
    }

    public String getAM_APP_SECUTIRY_CHARGES() {
        return AM_APP_SECUTIRY_CHARGES;
    }

    public void setAM_APP_SECUTIRY_CHARGES(String AM_APP_SECUTIRY_CHARGES) {
        this.AM_APP_SECUTIRY_CHARGES = AM_APP_SECUTIRY_CHARGES;
    }

    public String getAM_APP_ADDITIONAL_CHARGES() {
        return AM_APP_ADDITIONAL_CHARGES;
    }

    public void setAM_APP_ADDITIONAL_CHARGES(String AM_APP_ADDITIONAL_CHARGES) {
        this.AM_APP_ADDITIONAL_CHARGES = AM_APP_ADDITIONAL_CHARGES;
    }

    public String getAM_APP_WATER_MACADAM_CHARGES() {
        return AM_APP_WATER_MACADAM_CHARGES;
    }

    public void setAM_APP_WATER_MACADAM_CHARGES(String AM_APP_WATER_MACADAM_CHARGES) {
        this.AM_APP_WATER_MACADAM_CHARGES = AM_APP_WATER_MACADAM_CHARGES;
    }

    public String getAM_APP_REINFORCEMENT_CHARGES() {
        return AM_APP_REINFORCEMENT_CHARGES;
    }

    public void setAM_APP_REINFORCEMENT_CHARGES(String AM_APP_REINFORCEMENT_CHARGES) {
        this.AM_APP_REINFORCEMENT_CHARGES = AM_APP_REINFORCEMENT_CHARGES;
    }

    public String getAM_APP_VISITING_CHARGES() {
        return AM_APP_VISITING_CHARGES;
    }

    public void setAM_APP_VISITING_CHARGES(String AM_APP_VISITING_CHARGES) {
        this.AM_APP_VISITING_CHARGES = AM_APP_VISITING_CHARGES;
    }

    public String getAM_APP_BITUMINOUSTAR_CHARGES() {
        return AM_APP_BITUMINOUSTAR_CHARGES;
    }

    public void setAM_APP_BITUMINOUSTAR_CHARGES(String AM_APP_BITUMINOUSTAR_CHARGES) {
        this.AM_APP_BITUMINOUSTAR_CHARGES = AM_APP_BITUMINOUSTAR_CHARGES;
    }

    public String getAM_APP_METER_COST() {
        return AM_APP_METER_COST;
    }

    public void setAM_APP_METER_COST(String AM_APP_METER_COST) {
        this.AM_APP_METER_COST = AM_APP_METER_COST;
    }

    public String getAM_APP_REGFEE1() {
        return AM_APP_REGFEE1;
    }

    public void setAM_APP_REGFEE1(String AM_APP_REGFEE1) {
        this.AM_APP_REGFEE1 = AM_APP_REGFEE1;
    }

    public String getAM_APP_ASD1() {
        return AM_APP_ASD1;
    }

    public void setAM_APP_ASD1(String AM_APP_ASD1) {
        this.AM_APP_ASD1 = AM_APP_ASD1;
    }

    public String getAM_APP_SD1() {
        return AM_APP_SD1;
    }

    public void setAM_APP_SD1(String AM_APP_SD1) {
        this.AM_APP_SD1 = AM_APP_SD1;
    }

    public String getAM_APP_DEMAND_AMT() {
        return AM_APP_DEMAND_AMT;
    }

    public void setAM_APP_DEMAND_AMT(String AM_APP_DEMAND_AMT) {
        this.AM_APP_DEMAND_AMT = AM_APP_DEMAND_AMT;
    }

    public String getAM_APP_METER_REQUIRED_FUTILITY() {
        return AM_APP_METER_REQUIRED_FUTILITY;
    }

    public void setAM_APP_METER_REQUIRED_FUTILITY(String AM_APP_METER_REQUIRED_FUTILITY) {
        this.AM_APP_METER_REQUIRED_FUTILITY = AM_APP_METER_REQUIRED_FUTILITY;
    }

    public String getAM_APP_CONNECTION_TYPE() {
        return AM_APP_CONNECTION_TYPE;
    }

    public void setAM_APP_CONNECTION_TYPE(String AM_APP_CONNECTION_TYPE) {
        this.AM_APP_CONNECTION_TYPE = AM_APP_CONNECTION_TYPE;
    }

    public String getAPTM_NAME() {
        return APTM_NAME;
    }

    public void setAPTM_NAME(String APTM_NAME) {
        this.APTM_NAME = APTM_NAME;
    }

    public String getAM_APP_PREMTYPE() {
        return AM_APP_PREMTYPE;
    }

    public void setAM_APP_PREMTYPE(String AM_APP_PREMTYPE) {
        this.AM_APP_PREMTYPE = AM_APP_PREMTYPE;
    }

    public String getAM_APP_GOVT_PROPERTY() {
        return AM_APP_GOVT_PROPERTY;
    }

    public void setAM_APP_GOVT_PROPERTY(String AM_APP_GOVT_PROPERTY) {
        this.AM_APP_GOVT_PROPERTY = AM_APP_GOVT_PROPERTY;
    }

    public String getAM_APP_EXISTING_CONSUMER_TAG() {
        return AM_APP_EXISTING_CONSUMER_TAG;
    }

    public void setAM_APP_EXISTING_CONSUMER_TAG(String AM_APP_EXISTING_CONSUMER_TAG) {
        this.AM_APP_EXISTING_CONSUMER_TAG = AM_APP_EXISTING_CONSUMER_TAG;
    }

    public String getAM_APP_EXISTING_CONSUMERNO() {
        return AM_APP_EXISTING_CONSUMERNO;
    }

    public void setAM_APP_EXISTING_CONSUMERNO(String AM_APP_EXISTING_CONSUMERNO) {
        this.AM_APP_EXISTING_CONSUMERNO = AM_APP_EXISTING_CONSUMERNO;
    }

    public String getAM_APP_OTHERUSAGE() {
        return AM_APP_OTHERUSAGE;
    }

    public void setAM_APP_OTHERUSAGE(String AM_APP_OTHERUSAGE) {
        this.AM_APP_OTHERUSAGE = AM_APP_OTHERUSAGE;
    }

    public String getAM_APP_FAMILY_MEMBER() {
        return AM_APP_FAMILY_MEMBER;
    }

    public void setAM_APP_FAMILY_MEMBER(String AM_APP_FAMILY_MEMBER) {
        this.AM_APP_FAMILY_MEMBER = AM_APP_FAMILY_MEMBER;
    }

    public String getAPPLICATION() {
        return APPLICATION;
    }

    public void setAPPLICATION(String APPLICATION) {
        this.APPLICATION = APPLICATION;
    }

    public String getVCM_CATNAME() {
        return VCM_CATNAME;
    }

    public void setVCM_CATNAME(String VCM_CATNAME) {
        this.VCM_CATNAME = VCM_CATNAME;
    }

    public String getFATHERNAME() {
        return FATHERNAME;
    }

    public void setFATHERNAME(String FATHERNAME) {
        this.FATHERNAME = FATHERNAME;
    }

    public String getAM_APP_NO_OF_FLOORS() {
        return AM_APP_NO_OF_FLOORS;
    }

    public void setAM_APP_NO_OF_FLOORS(String AM_APP_NO_OF_FLOORS) {
        this.AM_APP_NO_OF_FLOORS = AM_APP_NO_OF_FLOORS;
    }

    public String getAM_APP_NO_OF_DWELLING() {
        return AM_APP_NO_OF_DWELLING;
    }

    public void setAM_APP_NO_OF_DWELLING(String AM_APP_NO_OF_DWELLING) {
        this.AM_APP_NO_OF_DWELLING = AM_APP_NO_OF_DWELLING;
    }

    public String getAM_APP_NO_OF_ROOMS() {
        return AM_APP_NO_OF_ROOMS;
    }

    public void setAM_APP_NO_OF_ROOMS(String AM_APP_NO_OF_ROOMS) {
        this.AM_APP_NO_OF_ROOMS = AM_APP_NO_OF_ROOMS;
    }

    public String getAM_APP_ISAUTH_CONNECTION() {
        return AM_APP_ISAUTH_CONNECTION;
    }

    public void setAM_APP_ISAUTH_CONNECTION(String AM_APP_ISAUTH_CONNECTION) {
        this.AM_APP_ISAUTH_CONNECTION = AM_APP_ISAUTH_CONNECTION;
    }

    public String getAM_APP_CIVIL_CONS_CCMC_WATER() {
        return AM_APP_CIVIL_CONS_CCMC_WATER;
    }

    public void setAM_APP_CIVIL_CONS_CCMC_WATER(String AM_APP_CIVIL_CONS_CCMC_WATER) {
        this.AM_APP_CIVIL_CONS_CCMC_WATER = AM_APP_CIVIL_CONS_CCMC_WATER;
    }

    public String getAM_APP_NO_OF_DWELLING_UNITS() {
        return AM_APP_NO_OF_DWELLING_UNITS;
    }

    public void setAM_APP_NO_OF_DWELLING_UNITS(String AM_APP_NO_OF_DWELLING_UNITS) {
        this.AM_APP_NO_OF_DWELLING_UNITS = AM_APP_NO_OF_DWELLING_UNITS;
    }

    public String getAM_APP_ISOCCUPIER_SECURITY() {
        return AM_APP_ISOCCUPIER_SECURITY;
    }

    public void setAM_APP_ISOCCUPIER_SECURITY(String AM_APP_ISOCCUPIER_SECURITY) {
        this.AM_APP_ISOCCUPIER_SECURITY = AM_APP_ISOCCUPIER_SECURITY;
    }

    public String getAM_APP_ISMSCDCL_EMPLOYEE_ID() {
        return AM_APP_ISMSCDCL_EMPLOYEE_ID;
    }

    public void setAM_APP_ISMSCDCL_EMPLOYEE_ID(String AM_APP_ISMSCDCL_EMPLOYEE_ID) {
        this.AM_APP_ISMSCDCL_EMPLOYEE_ID = AM_APP_ISMSCDCL_EMPLOYEE_ID;
    }

    public String getAM_APP_ISTECH_FESIBILITY() {
        return AM_APP_ISTECH_FESIBILITY;
    }

    public void setAM_APP_ISTECH_FESIBILITY(String AM_APP_ISTECH_FESIBILITY) {
        this.AM_APP_ISTECH_FESIBILITY = AM_APP_ISTECH_FESIBILITY;
    }

    public String getAM_APP_SPIPELINE() {
        return AM_APP_SPIPELINE;
    }

    public void setAM_APP_SPIPELINE(String AM_APP_SPIPELINE) {
        this.AM_APP_SPIPELINE = AM_APP_SPIPELINE;
    }

    public String getAM_APP_LENGTH_MEASURE() {
        return AM_APP_LENGTH_MEASURE;
    }

    public void setAM_APP_LENGTH_MEASURE(String AM_APP_LENGTH_MEASURE) {
        this.AM_APP_LENGTH_MEASURE = AM_APP_LENGTH_MEASURE;
    }

    public String getAM_APP_ROADCUTMTR() {
        return AM_APP_ROADCUTMTR;
    }

    public void setAM_APP_ROADCUTMTR(String AM_APP_ROADCUTMTR) {
        this.AM_APP_ROADCUTMTR = AM_APP_ROADCUTMTR;
    }

    public String getAM_APP_ROADTYPE() {
        return AM_APP_ROADTYPE;
    }

    public void setAM_APP_ROADTYPE(String AM_APP_ROADTYPE) {
        this.AM_APP_ROADTYPE = AM_APP_ROADTYPE;
    }

    public String getAM_APP_ROADOWNER() {
        return AM_APP_ROADOWNER;
    }

    public void setAM_APP_ROADOWNER(String AM_APP_ROADOWNER) {
        this.AM_APP_ROADOWNER = AM_APP_ROADOWNER;
    }

    public String getAM_APP_SUBSTATION() {
        return AM_APP_SUBSTATION;
    }

    public void setAM_APP_SUBSTATION(String AM_APP_SUBSTATION) {
        this.AM_APP_SUBSTATION = AM_APP_SUBSTATION;
    }

    public String getCATEGORY_NAME() {
            return CATEGORY_NAME;
    }

    public void setCATEGORY_NAME(String CATEGORY_NAME) {
        this.CATEGORY_NAME = CATEGORY_NAME;
    }

    public String getPRM_NAME() {
        return PRM_NAME;
    }

    public void setPRM_NAME(String PRM_NAME) {
        this.PRM_NAME = PRM_NAME;
    }

    public String getAM_APP_S_GIS_ID() {
        return AM_APP_S_GIS_ID;
    }

    public void setAM_APP_S_GIS_ID(String AM_APP_S_GIS_ID) {
        this.AM_APP_S_GIS_ID = AM_APP_S_GIS_ID;
    }

    public String getPCM_PC_NAME() {
        return PCM_PC_NAME;
    }

    public void setPCM_PC_NAME(String PCM_PC_NAME) {
        this.PCM_PC_NAME = PCM_PC_NAME;
    }

    public String getLM_LOT_NAME() {
        return LM_LOT_NAME;
    }

    public void setLM_LOT_NAME(String LM_LOT_NAME) {
        this.LM_LOT_NAME = LM_LOT_NAME;
    }

    public String getTRM_NAME() {
        return TRM_NAME;
    }

    public void setTRM_NAME(String TRM_NAME) {
        this.TRM_NAME = TRM_NAME;
    }

    public String getPM_NAME() {
        return PM_NAME;
    }

    public void setPM_NAME(String PM_NAME) {
        this.PM_NAME = PM_NAME;
    }

    public String getSBM_NAME() {
        return SBM_NAME;
    }

    public void setSBM_NAME(String SBM_NAME) {
        this.SBM_NAME = SBM_NAME;
    }

    public String getAM_APP_NEAR_LCONS() {
        return AM_APP_NEAR_LCONS;
    }

    public void setAM_APP_NEAR_LCONS(String AM_APP_NEAR_LCONS) {
        this.AM_APP_NEAR_LCONS = AM_APP_NEAR_LCONS;
    }

    public String getOWNERSHIP() {
        return OWNERSHIP;
    }

    public void setOWNERSHIP(String OWNERSHIP) {
        this.OWNERSHIP = OWNERSHIP;
    }

    public String getUPLOADEDDOCS() {
        return UPLOADEDDOCS;
    }

    public void setUPLOADEDDOCS(String UPLOADEDDOCS) {
        this.UPLOADEDDOCS = UPLOADEDDOCS;
    }

    public String getLATI() {
        return LATI;
    }

    public void setLATI(String LATI) {
        this.LATI = LATI;
    }

    public String getLONGI() {
        return LONGI;
    }

    public void setLONGI(String LONGI) {
        this.LONGI = LONGI;
    }

    public String getAM_APP_ISWATER_AVAILINDP() {
        return AM_APP_ISWATER_AVAILINDP;
    }

    public void setAM_APP_ISWATER_AVAILINDP(String AM_APP_ISWATER_AVAILINDP) {
        this.AM_APP_ISWATER_AVAILINDP = AM_APP_ISWATER_AVAILINDP;
    }

    public String getAM_APP_DISTRIBUTIONID() {
        return AM_APP_DISTRIBUTIONID;
    }

    public void setAM_APP_DISTRIBUTIONID(String AM_APP_DISTRIBUTIONID) {
        this.AM_APP_DISTRIBUTIONID = AM_APP_DISTRIBUTIONID;
    }

    public String getAM_APP_SRIGHTCONSUMER() {
        return AM_APP_SRIGHTCONSUMER;
    }

    public void setAM_APP_SRIGHTCONSUMER(String AM_APP_SRIGHTCONSUMER) {
        this.AM_APP_SRIGHTCONSUMER = AM_APP_SRIGHTCONSUMER;
    }

    public String getAM_APP_SLEFTCONSUMER() {
        return AM_APP_SLEFTCONSUMER;
    }

    public void setAM_APP_SLEFTCONSUMER(String AM_APP_SLEFTCONSUMER) {
        this.AM_APP_SLEFTCONSUMER = AM_APP_SLEFTCONSUMER;
    }

    public String getAM_APP_ISROADCUTTING_REQD() {
        return AM_APP_ISROADCUTTING_REQD;
    }

    public void setAM_APP_ISROADCUTTING_REQD(String AM_APP_ISROADCUTTING_REQD) {
        this.AM_APP_ISROADCUTTING_REQD = AM_APP_ISROADCUTTING_REQD;
    }

    public String getAM_APP_ISCONSTRUCTION_COMP() {
        return AM_APP_ISCONSTRUCTION_COMP;
    }

    public void setAM_APP_ISCONSTRUCTION_COMP(String AM_APP_ISCONSTRUCTION_COMP) {
        this.AM_APP_ISCONSTRUCTION_COMP = AM_APP_ISCONSTRUCTION_COMP;
    }

    public String getAM_APP_ISEXISTINGCONN() {
        return AM_APP_ISEXISTINGCONN;
    }

    public void setAM_APP_ISEXISTINGCONN(String AM_APP_ISEXISTINGCONN) {
        this.AM_APP_ISEXISTINGCONN = AM_APP_ISEXISTINGCONN;
    }

    public String getAM_APP_EXISTINGCONN() {
        return AM_APP_EXISTINGCONN;
    }

    public void setAM_APP_EXISTINGCONN(String AM_APP_EXISTINGCONN) {
        this.AM_APP_EXISTINGCONN = AM_APP_EXISTINGCONN;
    }

    public String getAM_APP_ISDWELLING_HASCONN() {
        return AM_APP_ISDWELLING_HASCONN;
    }

    public void setAM_APP_ISDWELLING_HASCONN(String AM_APP_ISDWELLING_HASCONN) {
        this.AM_APP_ISDWELLING_HASCONN = AM_APP_ISDWELLING_HASCONN;
    }

    public String getAM_APP_STORAGECAPACITYTYPE() {
        return AM_APP_STORAGECAPACITYTYPE;
    }

    public void setAM_APP_STORAGECAPACITYTYPE(String AM_APP_STORAGECAPACITYTYPE) {
        this.AM_APP_STORAGECAPACITYTYPE = AM_APP_STORAGECAPACITYTYPE;
    }

    public String getAM_APP_STORAGECAPACITY() {
        return AM_APP_STORAGECAPACITY;
    }

    public void setAM_APP_STORAGECAPACITY(String AM_APP_STORAGECAPACITY) {
        this.AM_APP_STORAGECAPACITY = AM_APP_STORAGECAPACITY;
    }

    public String getAM_APP_ISINTERNAL_NETWORK() {
        return AM_APP_ISINTERNAL_NETWORK;
    }

    public void setAM_APP_ISINTERNAL_NETWORK(String AM_APP_ISINTERNAL_NETWORK) {
        this.AM_APP_ISINTERNAL_NETWORK = AM_APP_ISINTERNAL_NETWORK;
    }

    public String getAM_APP_ISDISPOSAL_OFWATER() {
        return AM_APP_ISDISPOSAL_OFWATER;
    }

    public void setAM_APP_ISDISPOSAL_OFWATER(String AM_APP_ISDISPOSAL_OFWATER) {
        this.AM_APP_ISDISPOSAL_OFWATER = AM_APP_ISDISPOSAL_OFWATER;
    }

    public String getAM_APP_ISRAINWATERHARVEST() {
        return AM_APP_ISRAINWATERHARVEST;
    }

    public void setAM_APP_ISRAINWATERHARVEST(String AM_APP_ISRAINWATERHARVEST) {
        this.AM_APP_ISRAINWATERHARVEST = AM_APP_ISRAINWATERHARVEST;
    }

    public String getAM_APP_METERLOCATION() {
        return AM_APP_METERLOCATION;
    }

    public void setAM_APP_METERLOCATION(String AM_APP_METERLOCATION) {
        this.AM_APP_METERLOCATION = AM_APP_METERLOCATION;
    }

    public String getAM_APP_METERSIZE() {
        return AM_APP_METERSIZE;
    }

    public void setAM_APP_METERSIZE(String AM_APP_METERSIZE) {
        this.AM_APP_METERSIZE = AM_APP_METERSIZE;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPass1() {
        return pass1;
    }

    public void setPass1(String pass1) {
        this.pass1 = pass1;
    }

    public String getAM_APP_COSOWO_NAME() {
        return AM_APP_COSOWO_NAME;
    }

    public void setAM_APP_COSOWO_NAME(String AM_APP_COSOWO_NAME) {
        this.AM_APP_COSOWO_NAME = AM_APP_COSOWO_NAME;
    }


    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public boolean isExpanded() {
        return expanded;
    }
    public String getMTRM_SERIAL_NO() {
        return MTRM_SERIAL_NO;
    }

    public String getNOOFVISITS() {
        return NOOFVISITS;
    }

    public void setNOOFVISITS(String NOOFVISITS) {
        this.NOOFVISITS = NOOFVISITS;
    }

    public String getMTRM_CURRENT_READING() {
        return MTRM_CURRENT_READING;
    }

    public void setMTRM_SERIAL_NO(String MTRM_SERIAL_NO) {
        this.MTRM_SERIAL_NO = MTRM_SERIAL_NO;
    }

    public String get() {
        return MTRM_CURRENT_READING;
    }

    public void setMTRM_CURRENT_READING(String MTRM_CURRENT_READING) {
        this.MTRM_CURRENT_READING = MTRM_CURRENT_READING;
    }


    @Override
    public String toString() {
        return "SiteVisitModel{" +
                "REQUEST_NO='" + REQUEST_NO + '\'' +
                ", NAME='" + NAME + '\'' +
                ", AM_AAPP_NO_TYPE='" + AM_AAPP_NO_TYPE + '\'' +
                ", AM_APP_SERVNO='" + AM_APP_SERVNO + '\'' +
                ", AM_APP_ZONE='" + AM_APP_ZONE + '\'' +
                ", AM_APP_CIRCLE='" + AM_APP_CIRCLE + '\'' +
                ", AM_APP_DIVISION='" + AM_APP_DIVISION + '\'' +
                ", AM_APP_SUB_DIVISION='" + AM_APP_SUB_DIVISION + '\'' +
                ", AM_APP_SECTION='" + AM_APP_SECTION + '\'' +
                ", AM_APP_BU='" + AM_APP_BU + '\'' +
                ", AM_APP_PC='" + AM_APP_PC + '\'' +
                ", AM_APP_AREA='" + AM_APP_AREA + '\'' +
                ", AM_APP_MRC='" + AM_APP_MRC + '\'' +
                ", AM_APP_LOT='" + AM_APP_LOT + '\'' +
                ", AM_APP_SEQUENCE='" + AM_APP_SEQUENCE + '\'' +
                ", AM_APP_NEARBYSER='" + AM_APP_NEARBYSER + '\'' +
                ", AM_APP_NMTITLE='" + AM_APP_NMTITLE + '\'' +
                ", AM_APP_FNAME='" + AM_APP_FNAME + '\'' +
                ", AM_APP_MNAME='" + AM_APP_MNAME + '\'' +
                ", AM_APP_SURNAME='" + AM_APP_SURNAME + '\'' +
                ", AM_AAPP_DATE='" + AM_AAPP_DATE + '\'' +
                ", AM_APP_PHONEM='" + AM_APP_PHONEM + '\'' +
                ", AM_APP_EMAIL='" + AM_APP_EMAIL + '\'' +
                ", AM_APP_ADDRESS1='" + AM_APP_ADDRESS1 + '\'' +
                ", AM_APP_ADDRESS2='" + AM_APP_ADDRESS2 + '\'' +
                ", AM_APP_ADDRESS3='" + AM_APP_ADDRESS3 + '\'' +
                ", AM_APP_LOCALITY='" + AM_APP_ADDRESS4 + '\'' +
                ", AM_APP_ADDRESS4='" + AM_APP_ADDRESS4 + '\'' +
                ", AM_APP_PINCODE='" + AM_APP_PINCODE + '\'' +
                ", ADDRESS='" + ADDRESS + '\'' +
                ", AM_APP_APPLI_TYPE='" + AM_APP_APPLI_TYPE + '\'' +
                ", AM_APP_NOOF_APPS='" + AM_APP_NOOF_APPS + '\'' +
               ", NEWADDRESS='" + NEWADDRESS + '\'' +
                ", AM_APP_BOREWELL='" + AM_APP_BOREWELL + '\'' +
                ", AM_APP_IS_OPERATIONAL='" + AM_APP_IS_OPERATIONAL + '\'' +

                ", AM_REMARKS='" + AM_REMARKS + '\'' +
                ", AM_APP_ISMSCDCL_EMPLOYEE='" + AM_APP_ISMSCDCL_EMPLOYEE + '\'' +
                ", AM_APP_ACCEPT_DATE='" + AM_APP_ACCEPT_DATE + '\'' +
                ", AM_APP_APPOINT_DATE='" + AM_APP_APPOINT_DATE + '\'' +
                ", AM_APP_PROCDT='" + AM_APP_PROCDT + '\'' +
                ", PROCESSTAG='" + PROCESSTAG + '\'' +
                ", PROCESSCODE='" + PROCESSCODE + '\'' +
                ", STATUS='" + STATUS + '\'' +
                ", AM_APP_SOURCE_TYPE='" + AM_APP_SOURCE_TYPE + '\'' +
                ", SOURCE='" + SOURCE + '\'' +
                ", METERREPLACE='" + METERREPLACE + '\'' +
                ", REASON='" + REASON + '\'' +
                ", REASONNAME='" + REASONNAME + '\'' +
                ", AM_APP_REJECT_DATE='" + AM_APP_REJECT_DATE + '\'' +
                ", AM_APP_ORESCD='" + AM_APP_ORESCD + '\'' +
                ", AREANAME='" + AREANAME + '\'' +
                ", ZONENAME='" + ZONENAME + '\'' +
                ", SUBZONE='" + SUBZONE + '\'' +
                ", AM_APP_CATEGORY='" + AM_APP_CATEGORY + '\'' +
                ", PURPOSE_NAME='" + PURPOSE_NAME + '\'' +
                ", CONNECTION_SIZE='" + CONNECTION_SIZE + '\'' +
                ", AM_APP_PURPOSE='" + AM_APP_PURPOSE + '\'' +
                ", AM_APP_TRNO='" + AM_APP_TRNO + '\'' +
                ", AM_APP_POLENO='" + AM_APP_POLENO + '\'' +
                ", AM_APP_ASD='" + AM_APP_ASD + '\'' +
                ", AM_APP_SD='" + AM_APP_SD + '\'' +
                ", AM_APP_SCC='" + AM_APP_SCC + '\'' +
                ", AM_APP_REGFEE='" + AM_APP_REGFEE + '\'' +
                ", TARIFF_NAME='" + TARIFF_NAME + '\'' +
                ", AM_APP_TARIFF='" + AM_APP_TARIFF + '\'' +
                ", AM_APP_AREA_TYPE='" + AM_APP_AREA_TYPE + '\'' +
                ", AM_APP_TARIFF_TYPE_CD='" + AM_APP_TARIFF_TYPE_CD + '\'' +
                ", AM_APP_METER_INDICATION='" + AM_APP_METER_INDICATION + '\'' +
                ", AM_APP_RCPT_DATE='" + AM_APP_RCPT_DATE + '\'' +
                ", AM_APP_TOTALAMT='" + AM_APP_TOTALAMT + '\'' +
                ", AM_APP_PDWITHIN6='" + AM_APP_PDWITHIN6 + '\'' +
                ", AM_APP_METERAVLB='" + AM_APP_METERAVLB + '\'' +
                ", AM_APP_PDRECON_TYPE='" + AM_APP_PDRECON_TYPE + '\'' +
                ", MONTHYR='" + MONTHYR + '\'' +
                ", AM_APP_PREMISE_NO='" + AM_APP_PREMISE_NO + '\'' +
                ", AM_APP_TR_DISTANCE='" + AM_APP_TR_DISTANCE + '\'' +
                ", AM_APP_AREAPREMISE='" + AM_APP_AREAPREMISE + '\'' +
                ", AM_APP_CONNECTION_SIZE='" + AM_APP_CONNECTION_SIZE + '\'' +
                ", AM_APP_STDCODE='" + AM_APP_STDCODE + '\'' +
                ", AM_APP_PHONE='" + AM_APP_PHONE + '\'' +
                ", PROCCESCODE='" + PROCCESCODE + '\'' +
                ", AM_APP_METER_REQUIRED='" + AM_APP_METER_REQUIRED + '\'' +
                ", AM_APP_BOARING_CHARGES='" + AM_APP_BOARING_CHARGES + '\'' +
                ", AM_APP_SECUTIRY_CHARGES='" + AM_APP_SECUTIRY_CHARGES + '\'' +
                ", AM_APP_ADDITIONAL_CHARGES='" + AM_APP_ADDITIONAL_CHARGES + '\'' +
                ", AM_APP_WATER_MACADAM_CHARGES='" + AM_APP_WATER_MACADAM_CHARGES + '\'' +
                ", AM_APP_REINFORCEMENT_CHARGES='" + AM_APP_REINFORCEMENT_CHARGES + '\'' +
                ", AM_APP_VISITING_CHARGES='" + AM_APP_VISITING_CHARGES + '\'' +
                ", AM_APP_BITUMINOUSTAR_CHARGES='" + AM_APP_BITUMINOUSTAR_CHARGES + '\'' +
                ", AM_APP_METER_COST='" + AM_APP_METER_COST + '\'' +
                ", AM_APP_REGFEE1='" + AM_APP_REGFEE1 + '\'' +
                ", AM_APP_ASD1='" + AM_APP_ASD1 + '\'' +
                ", AM_APP_SD1='" + AM_APP_SD1 + '\'' +
                ", AM_APP_DEMAND_AMT='" + AM_APP_DEMAND_AMT + '\'' +
                ", AM_APP_METER_REQUIRED_FUTILITY='" + AM_APP_METER_REQUIRED_FUTILITY + '\'' +
                ", AM_APP_CONNECTION_TYPE='" + AM_APP_CONNECTION_TYPE + '\'' +
                ", APTM_NAME='" + APTM_NAME + '\'' +
                ", AM_APP_PREMTYPE='" + AM_APP_PREMTYPE + '\'' +
                ", AM_APP_GOVT_PROPERTY='" + AM_APP_GOVT_PROPERTY + '\'' +
                ", AM_APP_EXISTING_CONSUMER_TAG='" + AM_APP_EXISTING_CONSUMER_TAG + '\'' +
                ", AM_APP_EXISTING_CONSUMERNO='" + AM_APP_EXISTING_CONSUMERNO + '\'' +
                ", AM_APP_OTHERUSAGE='" + AM_APP_OTHERUSAGE + '\'' +
                ", AM_APP_FAMILY_MEMBER='" + AM_APP_FAMILY_MEMBER + '\'' +
                ", APPLICATION='" + APPLICATION + '\'' +
                ", VCM_CATNAME='" + VCM_CATNAME + '\'' +
                ", FATHERNAME='" + FATHERNAME + '\'' +
                ", AM_APP_NO_OF_FLOORS='" + AM_APP_NO_OF_FLOORS + '\'' +
                ", AM_APP_NO_OF_DWELLING='" + AM_APP_NO_OF_DWELLING + '\'' +
                ", AM_APP_NO_OF_ROOMS='" + AM_APP_NO_OF_ROOMS + '\'' +
                ", AM_APP_ISAUTH_CONNECTION='" + AM_APP_ISAUTH_CONNECTION + '\'' +
                ", AM_APP_CIVIL_CONS_CCMC_WATER='" + AM_APP_CIVIL_CONS_CCMC_WATER + '\'' +
                ", AM_APP_NO_OF_DWELLING_UNITS='" + AM_APP_NO_OF_DWELLING_UNITS + '\'' +
                ", AM_APP_ISOCCUPIER_SECURITY='" + AM_APP_ISOCCUPIER_SECURITY + '\'' +
                ", AM_APP_ISMSCDCL_EMPLOYEE_ID='" + AM_APP_ISMSCDCL_EMPLOYEE_ID + '\'' +
                ", AM_APP_ISTECH_FESIBILITY='" + AM_APP_ISTECH_FESIBILITY + '\'' +
                ", AM_APP_SPIPELINE='" + AM_APP_SPIPELINE + '\'' +
                ", AM_APP_LENGTH_MEASURE='" + AM_APP_LENGTH_MEASURE + '\'' +
                ", AM_APP_ROADCUTMTR='" + AM_APP_ROADCUTMTR + '\'' +
                ", AM_APP_ROADTYPE='" + AM_APP_ROADTYPE + '\'' +
                ", AM_APP_ROADOWNER='" + AM_APP_ROADOWNER + '\'' +
                ", AM_APP_SUBSTATION='" + AM_APP_SUBSTATION + '\'' +
                ", CATEGORY_NAME='" + CATEGORY_NAME + '\'' +
                ", PRM_NAME='" + PRM_NAME + '\'' +
                ", AM_APP_S_GIS_ID='" + AM_APP_S_GIS_ID + '\'' +
                ", PCM_PC_NAME='" + PCM_PC_NAME + '\'' +
                ", LM_LOT_NAME='" + LM_LOT_NAME + '\'' +
                ", TRM_NAME='" + TRM_NAME + '\'' +
                ", PM_NAME='" + PM_NAME + '\'' +
                ", SBM_NAME='" + SBM_NAME + '\'' +
                ", AM_APP_NEAR_LCONS='" + AM_APP_NEAR_LCONS + '\'' +
                ", OWNERSHIP='" + OWNERSHIP + '\'' +
                ", UPLOADEDDOCS='" + UPLOADEDDOCS + '\'' +
                ", LATI='" + LATI + '\'' +
                ", LONGI='" + LONGI + '\'' +
                ", AM_APP_ISWATER_AVAILINDP='" + AM_APP_ISWATER_AVAILINDP + '\'' +
                ", AM_APP_DISTRIBUTIONID='" + AM_APP_DISTRIBUTIONID + '\'' +
                ", AM_APP_SRIGHTCONSUMER='" + AM_APP_SRIGHTCONSUMER + '\'' +
                ", AM_APP_SLEFTCONSUMER='" + AM_APP_SLEFTCONSUMER + '\'' +
                ", AM_APP_ISROADCUTTING_REQD='" + AM_APP_ISROADCUTTING_REQD + '\'' +
                ", AM_APP_ISCONSTRUCTION_COMP='" + AM_APP_ISCONSTRUCTION_COMP + '\'' +
                ", AM_APP_ISEXISTINGCONN='" + AM_APP_ISEXISTINGCONN + '\'' +
                ", AM_APP_EXISTINGCONN='" + AM_APP_EXISTINGCONN + '\'' +
                ", AM_APP_ISDWELLING_HASCONN='" + AM_APP_ISDWELLING_HASCONN + '\'' +
                ", AM_APP_STORAGECAPACITYTYPE='" + AM_APP_STORAGECAPACITYTYPE + '\'' +
                ", AM_APP_STORAGECAPACITY='" + AM_APP_STORAGECAPACITY + '\'' +
                ", AM_APP_ISINTERNAL_NETWORK='" + AM_APP_ISINTERNAL_NETWORK + '\'' +
                ", AM_APP_ISDISPOSAL_OFWATER='" + AM_APP_ISDISPOSAL_OFWATER + '\'' +
                ", AM_APP_ISRAINWATERHARVEST='" + AM_APP_ISRAINWATERHARVEST + '\'' +
                ", AM_APP_METERLOCATION='" + AM_APP_METERLOCATION + '\'' +
                ", AM_APP_METERSIZE='" + AM_APP_METERSIZE + '\'' +
                ", AM_APP_COSOWO_NAME='" + AM_APP_COSOWO_NAME + '\'' +
                ", pass='" + pass + '\'' +
                ", pass1='" + pass1 + '\'' +
                ", MTRM_SERIAL_NO='" + MTRM_SERIAL_NO + '\'' +

                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(REQUEST_NO);
        dest.writeString(NAME);
        dest.writeString(AM_AAPP_NO_TYPE);
        dest.writeString(AM_APP_SERVNO);
        dest.writeString(AM_APP_ZONE);
        dest.writeString(AM_APP_CIRCLE);
        dest.writeString(AM_APP_DIVISION);
        dest.writeString(AM_APP_SUB_DIVISION);
        dest.writeString(AM_APP_SECTION);
        dest.writeString(AM_APP_BU);
        dest.writeString(AM_APP_PC);
        dest.writeString(AM_APP_AREA);
        dest.writeString(AM_APP_MRC);
        dest.writeString(AM_APP_LOT);
        dest.writeString(AM_APP_SEQUENCE);
        dest.writeString(AM_APP_NEARBYSER);
        dest.writeString(AM_APP_NMTITLE);
        dest.writeString(AM_APP_FNAME);
        dest.writeString(AM_APP_MNAME);
        dest.writeString(AM_APP_SURNAME);
        dest.writeString(AM_AAPP_DATE);
        dest.writeString(AM_APP_PHONEM);
        dest.writeString(AM_APP_EMAIL);
        dest.writeString(AM_APP_ADDRESS1);
        dest.writeString(AM_APP_ADDRESS2);
        dest.writeString(AM_APP_ADDRESS3);
        dest.writeString(AM_APP_LOCALITY);

        dest.writeString(AM_APP_ADDRESS4);
        dest.writeString(AM_APP_PINCODE);
        dest.writeString(ADDRESS);
        dest.writeString(AM_APP_APPLI_TYPE);
        dest.writeString(AM_APP_NOOF_APPS);
        dest.writeString(NEWADDRESS);
        dest.writeString(AM_APP_BOREWELL);
        dest.writeString(AM_APP_IS_OPERATIONAL);

        dest.writeString(AM_REMARKS);
        dest.writeString(AM_APP_ISMSCDCL_EMPLOYEE);
        dest.writeString(AM_APP_ACCEPT_DATE);
        dest.writeString(AM_APP_APPOINT_DATE);
        dest.writeString(AM_APP_PROCDT);
        dest.writeString(PROCESSTAG);
        dest.writeString(PROCESSCODE);
        dest.writeString(STATUS);
        dest.writeString(AM_APP_SOURCE_TYPE);
        dest.writeString(SOURCE);
        dest.writeString(METERREPLACE);
        dest.writeString(REASON);
        dest.writeString(REASONNAME);
        dest.writeString(AM_APP_REJECT_DATE);
        dest.writeString(AM_APP_ORESCD);
        dest.writeString(AREANAME);
        dest.writeString(ZONENAME);
        dest.writeString(SUBZONE);
        dest.writeString(AM_APP_CATEGORY);
        dest.writeString(PURPOSE_NAME);
        dest.writeString(CONNECTION_SIZE);
        dest.writeString(AM_APP_PURPOSE);
        dest.writeString(AM_APP_TRNO);
        dest.writeString(AM_APP_POLENO);
        dest.writeString(AM_APP_ASD);
        dest.writeString(AM_APP_SD);
        dest.writeString(AM_APP_SCC);
        dest.writeString(AM_APP_REGFEE);
        dest.writeString(TARIFF_NAME);
        dest.writeString(AM_APP_TARIFF);
        dest.writeString(AM_APP_AREA_TYPE);
        dest.writeString(AM_APP_TARIFF_TYPE_CD);
        dest.writeString(AM_APP_METER_INDICATION);
        dest.writeString(AM_APP_RCPT_DATE);
        dest.writeString(AM_APP_TOTALAMT);
        dest.writeString(AM_APP_PDWITHIN6);
        dest.writeString(AM_APP_METERAVLB);
        dest.writeString(AM_APP_PDRECON_TYPE);
        dest.writeString(MONTHYR);
        dest.writeString(AM_APP_PREMISE_NO);
        dest.writeString(AM_APP_TR_DISTANCE);
        dest.writeString(AM_APP_AREAPREMISE);
        dest.writeString(AM_APP_CONNECTION_SIZE);
        dest.writeString(AM_APP_STDCODE);
        dest.writeString(AM_APP_PHONE);
        dest.writeString(PROCCESCODE);
        dest.writeString(AM_APP_METER_REQUIRED);
        dest.writeString(AM_APP_BOARING_CHARGES);
        dest.writeString(AM_APP_SECUTIRY_CHARGES);
        dest.writeString(AM_APP_ADDITIONAL_CHARGES);
        dest.writeString(AM_APP_WATER_MACADAM_CHARGES);
        dest.writeString(AM_APP_REINFORCEMENT_CHARGES);
        dest.writeString(AM_APP_VISITING_CHARGES);
        dest.writeString(AM_APP_BITUMINOUSTAR_CHARGES);
        dest.writeString(AM_APP_METER_COST);
        dest.writeString(AM_APP_REGFEE1);
        dest.writeString(AM_APP_ASD1);
        dest.writeString(AM_APP_SD1);
        dest.writeString(AM_APP_DEMAND_AMT);
        dest.writeString(AM_APP_METER_REQUIRED_FUTILITY);
        dest.writeString(AM_APP_CONNECTION_TYPE);
        dest.writeString(APTM_NAME);
        dest.writeString(AM_APP_PREMTYPE);
        dest.writeString(AM_APP_GOVT_PROPERTY);
        dest.writeString(AM_APP_EXISTING_CONSUMER_TAG);
        dest.writeString(AM_APP_EXISTING_CONSUMERNO);
        dest.writeString(AM_APP_OTHERUSAGE);
        dest.writeString(AM_APP_FAMILY_MEMBER);
        dest.writeString(APPLICATION);
        dest.writeString(VCM_CATNAME);
        dest.writeString(FATHERNAME);
        dest.writeString(AM_APP_NO_OF_FLOORS);
        dest.writeString(AM_APP_NO_OF_DWELLING);
        dest.writeString(AM_APP_NO_OF_ROOMS);
        dest.writeString(AM_APP_ISAUTH_CONNECTION);
        dest.writeString(AM_APP_CIVIL_CONS_CCMC_WATER);
        dest.writeString(AM_APP_NO_OF_DWELLING_UNITS);
        dest.writeString(AM_APP_ISOCCUPIER_SECURITY);
        dest.writeString(AM_APP_ISMSCDCL_EMPLOYEE_ID);
        dest.writeString(AM_APP_ISTECH_FESIBILITY);
        dest.writeString(AM_APP_SPIPELINE);
        dest.writeString(AM_APP_LENGTH_MEASURE);
        dest.writeString(AM_APP_ROADCUTMTR);
        dest.writeString(AM_APP_ROADTYPE);
        dest.writeString(AM_APP_ROADOWNER);
        dest.writeString(AM_APP_SUBSTATION);
        dest.writeString(CATEGORY_NAME);
        dest.writeString(PRM_NAME);
        dest.writeString(AM_APP_S_GIS_ID);
        dest.writeString(PCM_PC_NAME);
        dest.writeString(LM_LOT_NAME);
        dest.writeString(TRM_NAME);
        dest.writeString(PM_NAME);
        dest.writeString(SBM_NAME);
        dest.writeString(AM_APP_NEAR_LCONS);
        dest.writeString(OWNERSHIP);
        dest.writeString(UPLOADEDDOCS);
        dest.writeString(LATI);
        dest.writeString(LONGI);
        dest.writeString(AM_APP_ISWATER_AVAILINDP);
        dest.writeString(AM_APP_DISTRIBUTIONID);
        dest.writeString(AM_APP_SRIGHTCONSUMER);
        dest.writeString(AM_APP_SLEFTCONSUMER);
        dest.writeString(AM_APP_ISROADCUTTING_REQD);
        dest.writeString(AM_APP_ISCONSTRUCTION_COMP);
        dest.writeString(AM_APP_ISEXISTINGCONN);
        dest.writeString(AM_APP_EXISTINGCONN);
        dest.writeString(AM_APP_ISDWELLING_HASCONN);
        dest.writeString(AM_APP_STORAGECAPACITYTYPE);
        dest.writeString(AM_APP_STORAGECAPACITY);
        dest.writeString(AM_APP_ISINTERNAL_NETWORK);
        dest.writeString(AM_APP_ISDISPOSAL_OFWATER);
        dest.writeString(AM_APP_ISRAINWATERHARVEST);
        dest.writeString(AM_APP_METERLOCATION);
        dest.writeString(AM_APP_METERSIZE);
        dest.writeString(AM_APP_COSOWO_NAME);

        dest.writeString(pass);
        dest.writeString(pass1);

        dest.writeString(MTRM_SERIAL_NO);
    }
}
