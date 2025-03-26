package elink.suezShimla.water.crm.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

/**
 * Created by Darpan on 10-04-2015.
 */

public class UtilitySharedPreferences {

    static String prefName = "user";
    static SharedPreferences preferences;
    static SharedPreferences.Editor editor;
    private static final String START_TIME = "countdown_timer";

    public static void setPrefs(Context context, String prefKey, String prefValue) {
        preferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(prefKey, prefValue);
        editor.apply();
    }

    public static void setPrefsList(Context context, String prefKey, ArrayList prefValue) {
        preferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(prefKey, String.valueOf(prefValue));
        editor.apply();
    }

    public static String getPrefs(Context context, String prefKey) {
        preferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return preferences.getString(prefKey, null);
    }


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


    public static int getStartedTime(Context context) {
        preferences = context.getSharedPreferences(START_TIME, Context.MODE_PRIVATE);
        return preferences.getInt(START_TIME, 0);
    }

    public static void setStartedTime(Context context,int startedTime) {
        preferences = context.getSharedPreferences(START_TIME, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putInt(START_TIME, startedTime);
        editor.apply();
    }
}
