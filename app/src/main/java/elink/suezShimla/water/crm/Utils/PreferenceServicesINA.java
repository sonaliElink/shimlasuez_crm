package elink.suezShimla.water.crm.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceServicesINA {

    private static final String SRM_S_NAME="SRM_S_NAME";
    private static final String SRM_B_ADDRESS2="SRM_B_ADDRESS2";
    private static final String SRM_S_MOBILE_NO="SRM_S_MOBILE_NO";
    private static final String testingFeeReceiptNo="testingFeeReceiptNo";
    private static final String MeterReceivedMTL="MeterReceivedMTL";
    private static final String MeterReceivedBy="MeterReceivedBy";
    private static final String MTRM_SERIAL_NO="MTRM_SERIAL_NO";
    private static final String MMFG_MFGNAME="MMFG_MFGNAME";
    private static final String SRM_CONNECTION_LOAD="SRM_CONNECTION_LOAD";
    private static final String O_METERTYPE="O_METERTYPE";
    private static final String MIA_ISMETERTESTED="MIA_ISMETERTESTED";
    private static final String MIA_TEST_RESULTS="MIA_TEST_RESULTS";
    private static final String MIA_METER_NO="MIA_METER_NO";
    private static final String SEALNO="SEALNO";
    private static final String MIA_METER_READING="MIA_METER_READING";
    private static final String MIA_METERSEAL_STATUS="MIA_METERSEAL_STATUS";
    private static final String MIA_OHTANK_CONDITION="MIA_OHTANK_CONDITION";
    private static final String MIA_SUCTIONPUMP_CONDITION="MIA_SUCTIONPUMP_CONDITION";
    private static final String MIA_GROUNDTANK_CONDITION="MIA_GROUNDTANK_CONDITION";
    private static final String MIA_UGSUMP_CONDITION="MIA_UGSUMP_CONDITION";
    private static final String MIA_LEAKFOUND_AFTERMETER="MIA_LEAKFOUND_AFTERMETER";
    private static final String MIA_LEAKFOUND_INHOUSE="MIA_LEAKFOUND_INHOUSE";
    private static final String MIA_FLOATINGVALVE_AVLBL="MIA_FLOATINGVALVE_AVLBL";
    private static final String MIA_METER_STATUS_ID="MIA_METER_STATUS_ID";
    private static final String MIA_DETAILSOFOBSRV="MIA_DETAILSOFOBSRV";
    private static final String MIA_RETO_CUSTOMER="MIA_RETO_CUSTOMER";

    private static final String PREFS_NAME = "fyl_user_SharedPref";
    private static PreferenceServicesINA mSingleton = new PreferenceServicesINA();
    private static Context mContext;

    private PreferenceServicesINA() {
    }

    public static PreferenceServicesINA instance() {
        return mSingleton;
    }

    public static PreferenceServicesINA getInstance() {
        return mSingleton;
    }

    public static void init(Context context) {
        mContext = context;
    }

    public SharedPreferences getPrefs() {
        return mContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public String getSRM_S_NAME() {
        return getPrefs().getString(SRM_S_NAME, "");
    }

    public void setSRM_S_NAME(String id) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(SRM_S_NAME, id);
        editor.commit();
    }

    public String getSRM_B_ADDRESS2() {
        return getPrefs().getString(SRM_B_ADDRESS2, "");
    }

    public void setSRM_B_ADDRESS2(String id) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(SRM_B_ADDRESS2, id);
        editor.commit();
    }

    public String getSRM_S_MOBILE_NO() {
        return getPrefs().getString(SRM_S_MOBILE_NO, "");
    }

    public void setSRM_S_MOBILE_NO(String id) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(SRM_S_MOBILE_NO, id);
        editor.commit();
    }

    public String getTestingFeeReceiptNo() {
        return getPrefs().getString(testingFeeReceiptNo, "");
    }

    public void setTestingFeeReceiptNo(String id) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(testingFeeReceiptNo, id);
        editor.commit();
    }
    public String getMeterReceivedMTL() {
        return getPrefs().getString(MeterReceivedMTL, "");
    }

    public void setMeterReceivedMTL(String id) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(MeterReceivedMTL, id);
        editor.commit();
    }

    public String getMeterReceivedBy() {
        return getPrefs().getString(MeterReceivedBy, "");
    }

    public void setMeterReceivedBy(String id) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(MeterReceivedBy, id);
        editor.commit();
    }

    public String getMTRM_SERIAL_NO() {
        return getPrefs().getString(MTRM_SERIAL_NO, "");
    }

    public void setMTRM_SERIAL_NO(String id) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(MTRM_SERIAL_NO, id);
        editor.commit();
    }

    public String getMMFG_MFGNAME() {
        return getPrefs().getString(MMFG_MFGNAME, "");
    }

    public void setMMFG_MFGNAME(String id) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(MMFG_MFGNAME, id);
        editor.commit();
    }

    public String getSRM_CONNECTION_LOAD() {
        return getPrefs().getString(SRM_CONNECTION_LOAD, "");
    }

    public void setSRM_CONNECTION_LOAD(String id) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(SRM_CONNECTION_LOAD, id);
        editor.commit();
    }

    public String getO_METERTYPE() {
        return getPrefs().getString(O_METERTYPE, "");
    }

    public void setO_METERTYPE(String id) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(O_METERTYPE, id);
        editor.commit();
    }

    public String getMIA_ISMETERTESTED() {
        return getPrefs().getString(MIA_ISMETERTESTED, "");
    }

    public void setMIA_ISMETERTESTED(String id) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(MIA_ISMETERTESTED, id);
        editor.commit();
    }

    public String getMIA_TEST_RESULTS() {
        return getPrefs().getString(MIA_TEST_RESULTS, "");
    }

    public void setMIA_TEST_RESULTS(String id) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(MIA_TEST_RESULTS, id);
        editor.commit();
    }
    public String getMIA_METER_NO() {
        return getPrefs().getString(MIA_METER_NO, "");
    }

    public void setMIA_METER_NO(String id) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(MIA_METER_NO, id);
        editor.commit();
    }

    public String getSEALNO() {
        return getPrefs().getString(SEALNO, "");
    }

    public void setSEALNO(String id) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(SEALNO, id);
        editor.commit();
    }

    public String getMIA_METER_READING() {
        return getPrefs().getString(MIA_METER_READING, "");
    }

    public void setMIA_METER_READING(String id) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(MIA_METER_READING, id);
        editor.commit();
    }

    public String getMIA_METERSEAL_STATUS() {
        return getPrefs().getString(MIA_METERSEAL_STATUS, "");
    }

    public void setMIA_METERSEAL_STATUS(String id) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(MIA_METERSEAL_STATUS, id);
        editor.commit();
    }

    public String getMIA_OHTANK_CONDITION() {
        return getPrefs().getString(MIA_OHTANK_CONDITION, "");
    }

    public void setMIA_OHTANK_CONDITION(String id) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(MIA_OHTANK_CONDITION, id);
        editor.commit();
    }
    public String getMIA_SUCTIONPUMP_CONDITION() {
        return getPrefs().getString(MIA_SUCTIONPUMP_CONDITION, "");
    }

    public void setMIA_SUCTIONPUMP_CONDITION(String id) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(MIA_SUCTIONPUMP_CONDITION, id);
        editor.commit();
    }

    public String getMIA_GROUNDTANK_CONDITION() {
        return getPrefs().getString(MIA_GROUNDTANK_CONDITION, "");
    }

    public void setMIA_GROUNDTANK_CONDITION(String id) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(MIA_GROUNDTANK_CONDITION, id);
        editor.commit();
    }

    public String getMIA_UGSUMP_CONDITION() {
        return getPrefs().getString(MIA_UGSUMP_CONDITION, "");
    }

    public void setMIA_UGSUMP_CONDITION(String id) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(MIA_UGSUMP_CONDITION, id);
        editor.commit();
    }
    public String getMIA_LEAKFOUND_AFTERMETER() {
        return getPrefs().getString(MIA_LEAKFOUND_AFTERMETER, "");
    }

    public void setMIA_LEAKFOUND_AFTERMETER(String id) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(MIA_LEAKFOUND_AFTERMETER, id);
        editor.commit();
    }

    public String getMIA_LEAKFOUND_INHOUSE() {
        return getPrefs().getString(MIA_LEAKFOUND_INHOUSE, "");
    }

    public void setMIA_LEAKFOUND_INHOUSE(String id) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(MIA_LEAKFOUND_INHOUSE, id);
        editor.commit();
    }
    public String getMIA_FLOATINGVALVE_AVLBL() {
        return getPrefs().getString(MIA_FLOATINGVALVE_AVLBL, "");
    }

    public void setMIA_FLOATINGVALVE_AVLBL(String id) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(MIA_FLOATINGVALVE_AVLBL, id);
        editor.commit();
    }

    public String getMIA_DETAILSOFOBSRV() {
        return getPrefs().getString(MIA_DETAILSOFOBSRV, "");
    }

    public void setMIA_DETAILSOFOBSRV(String id) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(MIA_DETAILSOFOBSRV, id);
        editor.commit();
    }
    public String getMIA_RETO_CUSTOMER() {
        return getPrefs().getString(MIA_RETO_CUSTOMER, "");
    }

    public void setMIA_RETO_CUSTOMER(String id) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(MIA_RETO_CUSTOMER, id);
        editor.commit();
    }

    public String getMIA_METER_STATUS_ID() {
        return getPrefs().getString(MIA_METER_STATUS_ID, "");
    }

    public void setMIA_METER_STATUS_ID(String id) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(MIA_METER_STATUS_ID, id);
        editor.commit();
    }
}
