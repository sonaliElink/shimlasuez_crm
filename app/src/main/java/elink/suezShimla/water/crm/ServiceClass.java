package elink.suezShimla.water.crm;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.ChangePassword.Model.ChangePinModel;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.ErrorModel.ErrorLogModel;
import elink.suezShimla.water.crm.ErrorModel.UploadErroLogStatusModel;
import elink.suezShimla.water.crm.ErrorModel.UploadErrorLogModel;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;
import elink.suezShimla.water.crm.constant.AppConstant;
import io.realm.Realm;

public class ServiceClass extends Service {

    public static final int STATUS_RUNNING = 0;
    private Gson gson;
    public Context mCon = this;
    private ConnectionDetector Connection;
    private Invoke invServices;
    private RealmOperations realmOperations;
    private List<ErrorLogModel> errorLogModelList = new ArrayList<>();
    private String jsonResponse = "" ,syncJsonResponse = "",jsonErrorResponce="",jsonBillResponse="";

    private final static String TAG = MainActivity.class.getName();
    private Realm realm;

    int errorNextId;

    String timeStamp="",AppVersion="",empCode="",imei="",fcmToken="";

    private KeyGenerator keyGenerator;
    private SecretKey secretKey;
    private byte[] IV = new byte[16];
    private SecureRandom random;
    AesAlgorithm aes;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO IBINDER
        return null;
    }

    @Override
    public void onCreate() {
        realmOperations = new RealmOperations(mCon);
        gson = new Gson();
        Connection = new ConnectionDetector(mCon);
        invServices = new Invoke();
        realm = Realm.getDefaultInstance();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
        timeStamp = simpleDateFormat.format(new Date());
        Log.d("MainActivity", "Current Timestamp: " + timeStamp);

        try {
            // AES Algorithm for encryption / decryption

            keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            secretKey = keyGenerator.generateKey();

            random = new SecureRandom();
            random.nextBytes(IV);

            aes=new AesAlgorithm();

            AppVersion = UtilitySharedPreferences.getPrefs(mCon, AppConstant.APPVERSION);
            AppVersion = aes.decrypt(AppVersion);

            try {
                // Decrypt EmpCode
                empCode = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
            } catch (Exception e) {
                e.printStackTrace();
            }
            imei = PreferenceUtil.getImei();

            fcmToken =  UtilitySharedPreferences.getPrefs(mCon, Constants.FCMTOKEN);
            fcmToken = aes.decrypt( fcmToken);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        UploadData();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        // super.onDestroy();
    }

    protected void UploadData() {
        try {
            final Handler mHandler = new Handler();
            // Create runnable for posting
            final Runnable mUpdateResults = new Runnable() {

                @SuppressLint("SimpleDateFormat")
                @Override
                public void run() {
                    try {
                        if (Connection.isConnectingToInternet()) {
                            ChangePinModel isExist = realmOperations.isemployeeExist();
                            if (isExist != null) {
                                SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
                                String restoredText = prefs.getString("empCode", null);

                                ChangePinModel changePinModel;
                                changePinModel = realmOperations.fetchDataReadingByEmpCode(PreferenceUtil.getEmployeeCode());
                                fcmToken =  UtilitySharedPreferences.getPrefs(mCon, Constants.FCMTOKEN);
                                fcmToken = aes.decrypt(fcmToken);

                               selectErrorData();
                            }
                        }
                    } catch (Exception ex) {
                    }
                }
            };
            long delay = 10000; // delay for 10 sec.
            long period = 600000; // repeat every 600 sec (10 min)
            //  long period = 60000; // repeat every 120 sec (2 min)
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    mHandler.post(mUpdateResults);
                }
            }, delay, period);

        } catch (Exception ex) {
            Log.e("Service Meter Reading: ", ex.getMessage());
        }
    }

    private void selectErrorData() {
        if (Connection.isConnectingToInternet()) {
            ErrorLogModel isErrorExist = realmOperations.getErrorLogExit();
            if (isErrorExist != null) {

                SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
                String strEmpCode = prefs.getString("empCode", null);

                JSONArray jArray = new JSONArray();
                errorLogModelList = realmOperations.uploadErrorData(0);
                for (ErrorLogModel errorLogModel  : errorLogModelList) {
                    JSONObject jsonObj = new JSONObject();
                    try {
                        jsonObj.put("USERID", errorLogModel.getEMPCODE());
                        jsonObj.put("IMEI", errorLogModel.getIMEI());
                        jsonObj.put("MODEL", errorLogModel.getMODEL());
                        jsonObj.put("DNAME", errorLogModel.getDEVICENAME());
                        jsonObj.put("VER", errorLogModel.getVERSION());
                        jsonObj.put("ACTIVITY", errorLogModel.getACTIVITYNAME());
                        jsonObj.put("EVENT", errorLogModel.getEVENTNAME());
                        jsonObj.put("ERROR", errorLogModel.getERRORLOG());
                        jsonObj.put("EDATE", errorLogModel.getTIMESTAMP());
                        jsonObj.put("AV", errorLogModel.getAPPVERSION());
                        jsonObj.put("AI", errorLogModel.getAI());
                        jsonObj.put("ID", errorLogModel.getId());

                        jArray.put(jsonObj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(mCon, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                Log.d("check", String.valueOf(jArray));

                String params[] = new String[1];
                params[0] = String.valueOf(jArray);

                UploadErrorLog uploadErrorLog = new UploadErrorLog();
                uploadErrorLog.execute(params);
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class UploadErrorLog extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                String paraName[] = new String[1];
                paraName[0] = "ERRORDATA";
                jsonErrorResponce = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, "ErrorLog", params, paraName);
            } catch (Exception e) {
            }
            return null;
        }

        @SuppressLint("DefaultLocale")
        @Override
        protected void onPostExecute(Void result) {
            try {
                UploadErrorLogModel model = gson.fromJson(jsonErrorResponce, UploadErrorLogModel.class);
                for (UploadErroLogStatusModel uploadStatusModel : model.getUploadStatusModel()) {
                    if (uploadStatusModel.getSTATUS().equalsIgnoreCase("SUCCESS")) {
                        for (UploadErroLogStatusModel statusModel : model.getUploadStatusModel()) {
                            realmOperations.updateErrorLogCount(statusModel.getID(), 1);
                        }
                    }
                }
            } catch (Exception e) {
                Log.d("Catch", e.toString());
            }
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

}
