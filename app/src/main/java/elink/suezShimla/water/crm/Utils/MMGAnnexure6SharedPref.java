package elink.suezShimla.water.crm.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class MMGAnnexure6SharedPref {
    static String prefName = "MMGAnnexure6SharePref";
    static SharedPreferences preferences;
    static SharedPreferences.Editor editor;

    public static void setPrefs(Context context, String prefKey, String prefValue) {
        preferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(prefKey, prefValue);
        editor.apply();
    }

    public static String getPrefs(Context context, String prefKey) {
        preferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return preferences.getString(prefKey, null);
    }


/*    public static StringBuilder getPrefs(Context context, StringBuilder prefKey) {
        preferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return preferences.getString(prefKey, null);
    }*/
    public static void clearPref(Context context) {
        preferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.clear().apply();
    }

    public static void clearAll() {
        preferences.edit().clear().apply();
    }

    public static void clearPrefKey(Context context, String prefKey) {
        preferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        preferences.edit().remove(prefKey).apply();
        //editor = preferences.edit();
        //editor.clear().commit();
    }
}
