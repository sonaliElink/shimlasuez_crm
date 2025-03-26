package elink.suezShimla.water.crm;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static final String PREF_NAME = "AndroidHivePref";
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;


    public SharedPrefManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void savesession(String sessionid){
        editor.putString("SESSION_ID",sessionid);
        editor.commit();
    }

    public String getsessionid(){
        String sessionid = pref.getString("SESSION_ID", null);
        return sessionid;
    }


}
