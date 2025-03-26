package elink.suezShimla.water.crm.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import elink.suezShimla.water.crm.Base.App;

public class PreferenceUtil {

    private static final String USER_MULTILINGUAL = "user_multilingual";
    public static String LANGUAGE_ENG = "en";
    public static String LANGUAGE_TAM = "ta";

    private static SharedPreferences preferences =
            PreferenceManager.getDefaultSharedPreferences(App.getContext());

    // Multilingual save
    public static void putString(Context activity, String key) {
        preferences.edit().putString(USER_MULTILINGUAL, key).apply();
    }

    public static String getString() {
        return preferences.getString(USER_MULTILINGUAL, null);
    }

    // Save UserName
    public static String getUserName() {
        return preferences.getString("userName", null);
    }

    public static void setUserName(String userType) {
        preferences.edit().putString("userName", userType).apply();
    }

    // Save Imei
    public static String getImei() {
        return preferences.getString("mobileIMEI", null);
    }

    public static void setImei(String imei) {
        preferences.edit().putString("mobileIMEI", imei).apply();
    }

    public static void setVersionName(String versionName) {
        preferences.edit().putString("versionName", versionName).apply();
    }
    public static String getVersionName() {
        return preferences.getString("versionName", null);
    }

    public static void setMMGFixer(String MMGFixer) {
        preferences.edit().putString("MMGFixer", MMGFixer).apply();
    }
    public static String getMMGFixer() {
        return preferences.getString("MMGFixer", null);
    }

    public static String getZone() {
        return preferences.getString("zone", null);
    }

    public static void setZone(String zone) {
        preferences.edit().putString("zone", zone).apply();
    }

    public static String getEmployeeCode() {
        return preferences.getString("employeeCode", null);
    }

    public static void setEmployeeCode(String employeeCode) {
        preferences.edit().putString("employeeCode", employeeCode).apply();
    }

    public static String getRights() {
        return preferences.getString("employeeRights", null);
    }

    public static void setRights(String Rights) {
        preferences.edit().putString("employeeRights", Rights).apply();
    }
    public static String getDeviceAuthorize() {
        return preferences.getString("DeviceAuthorize", null);
    }

    public static void setDeviceAuthorize(String DeviceAuthorize) {
        preferences.edit().putString("DeviceAuthorize", DeviceAuthorize).apply();
    }

    public static String getEmpName() {
        return preferences.getString("EmpName", null);
    }

    public static void setEmpName(String EmpName) {
        preferences.edit().putString("EmpName", EmpName).apply();
    }

    public static String getAPP_ISLOGGED() {
        return preferences.getString("APP_ISLOGGED", null);
    }

    public static void setAPP_ISLOGGED(String DeviceAuthorize) {
        preferences.edit().putString("APP_ISLOGGED", DeviceAuthorize).apply();
    }

    public static String getMac() {
        return preferences.getString("macAddress", null);
    }

    public static void setMac(String Rights) {
        preferences.edit().putString("macAddress", Rights).apply();
    }

    public static String getSiteEng() {
        return preferences.getString("siteEngineer", null);
    }

    public static void setSiteEng(String Rights) {
        preferences.edit().putString("siteEngineer", Rights).apply();
    }

    public static void clearAll() {
        preferences.edit().clear().apply();
    }

    // Save Mpin
    public static String getPin() {
        return preferences.getString("mPin", null);
    }

    public static void setPin(String Pin) {
        preferences.edit().putString("mPin", Pin).apply();
    }
    //notifications
    public static String getNotiCount() {
        return preferences.getString("NotiCount", null);
    }

    public static void setNotiCount(String Pin) {
        preferences.edit().putString("NotiCount", Pin).apply();
    }
    public static String getLastLogin() {
        return preferences.getString("LastLogin", null);
    }

    public static void setLastLogin(String lastLogin) {
        preferences.edit().putString("LastLogin", lastLogin).apply();
    }

    public static String getFirstZoneAvailable() {
        return preferences.getString("ZoneAvailable", null);
    }

    public static void setFirstZoneAvailable(String lastLogin) {
        preferences.edit().putString("ZoneAvailable", lastLogin).apply();
    }


    public static String getDepartmentId() {
        return preferences.getString("DepartmentId", null);
    }

    public static void setDepartmentId(String departmentId) {
        preferences.edit().putString("DepartmentId", departmentId).apply();
    }

}
