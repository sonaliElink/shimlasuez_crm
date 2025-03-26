

package elink.suezShimla.water.crm.Splash;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.Login.LoginActivity;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Register.RegisterActivity;
import elink.suezShimla.water.crm.SelectLanguage.ChangeLanguageUtil;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;
import elink.suezShimla.water.crm.constant.AppConstant;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_PHONE_STATE;

public class SplashScreen extends AppCompatActivity {

    private Context mCon;
    private String imeiNumber = "", macAddress, imeiStr, language, fcmToken = "", versionName = "";
    private static final int PERMISSION_REQUEST_CODE = 200;
    private BottomSheetDialog imeiMacDialog;
    private RealmOperations realmOperations;
    private AppCompatImageView closeImageView;
    private TextView imeiMacSettingsTextView;
    private TextInputLayout imeiInputLayout, macInputLayout;
    private TextInputEditText imeiEditText, macEditText;
    private MaterialButton submitButton;

    private String jsonResponse = "";
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    private MaterialDialog progress;
    TextView rootFinder;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        // prevent ss and hide content when app is on background
      //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        rootFinder = findViewById(R.id.rootFinder);

        executeShellCommand("su");


        mCon = this;

        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();

        Intent startingIntent = getIntent();
        imeiNumber = startingIntent.getStringExtra("IMEINumber");
        macAddress = startingIntent.getStringExtra("MACAddress");
        versionName = startingIntent.getStringExtra("versionName");

        PackageInfo pinfo = null;

        try {
            pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        versionName = pinfo.versionName;
        PreferenceUtil.setVersionName(versionName);

        // Language Code
        if (PreferenceUtil.getString() != null && PreferenceUtil.getString().equals(PreferenceUtil.LANGUAGE_TAM)) {
            language = PreferenceUtil.LANGUAGE_TAM;
        } else {
            language = PreferenceUtil.LANGUAGE_ENG;
        }

        try {
            ChangeLanguageUtil.yourLanguage(mCon, language);
        } catch (Exception e) {
            e.printStackTrace();
        }

        imeiMacDialog = new BottomSheetDialog(Objects.requireNonNull(mCon));

        if (!checkPermission()) {
            requestPermission();
        } else {
            employeeCheck();
        }
        getTokenId();
    }
   private void executeShellCommand(String su) {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(su);
            rootFinder.setText("It is rooted device");
            Toast.makeText(SplashScreen.this, "It is rooted device", Toast.LENGTH_LONG).show();
        } catch (Exception e) {

           // Intent intent = new Intent("android.intent.action.DELETE");
           // intent.setData(Uri.parse("package:" + "elink.mjp.water.crm"));
           // startActivity(intent);
           //
            // rootFinder.setText("It is not rooted device");
        } finally {
            if (process != null) {
                try {
                      Intent intent = new Intent(Intent.ACTION_DELETE);
                      intent.setData(Uri.parse("package:elink.suezShimla.water.crm"));
                      startActivity(intent);
                    process.destroy();

                } catch (Exception e) { }
            }
        }
    }


    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{READ_PHONE_STATE, ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), READ_PHONE_STATE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
        //return result == PackageManager.PERMISSION_GRANTED;
    }

    // Getting READ_PHONE_STATE Permission Runtime
    @SuppressLint("HardwareIds")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                boolean phoneState = false;
                boolean locationState = false;

                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    requestPermission();
                } else {
                    phoneState = true;
                }

                if (grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                    requestPermission();
                } else {
                    locationState = true;
                }

                if (phoneState && locationState) {
                    employeeCheck();
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(READ_PHONE_STATE) && shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                            showMessageOKCancel(
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            requestPermissions(new String[]{READ_PHONE_STATE, ACCESS_FINE_LOCATION},
                                                    PERMISSION_REQUEST_CODE);
                                        }
                                    });
                        }
                    }
                }
            }
        }

    }

    private void showMessageOKCancel(DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(mCon)
                .setMessage("You need to allow the permission")
                .setPositiveButton("OK", okListener)
                .create()
                .show();
    }

    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            Log.e("check", ex.getMessage());
        }
        return "02:00:00:00:00:00";
    }

    private void imeiMacBottomDialog() {
        View sheetView = Objects.requireNonNull(getLayoutInflater().inflate(R.layout.bottom_dialod_imei_mac, null));
        imeiMacDialog.setContentView(sheetView);
        imeiMacDialog.setCancelable(false);

        closeImageView = sheetView.findViewById(R.id.closeImageView);
        imeiMacSettingsTextView = sheetView.findViewById(R.id.imeiMacSettingsTextView);
        imeiInputLayout = sheetView.findViewById(R.id.imeiInputLayout);
        imeiEditText = sheetView.findViewById(R.id.imeiEditText);
        submitButton = sheetView.findViewById(R.id.submitButton);

        imeiMacSettingsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Settings.ACTION_DEVICE_INFO_SETTINGS), 0);
            }
        });

        closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imeiMacDialog.cancel();
                recreate();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imeiStr = imeiEditText.getText().toString().trim();
                validate();
            }
        });
    }

    private void validate() {
        boolean isValidImei = false;

        if (TextUtils.isEmpty(imeiStr)) {
            imeiInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            imeiInputLayout.setError(null);
            isValidImei = true;
        }

        if (isValidImei) {
            PreferenceUtil.setImei(imeiStr);

            employeeCheck();
            imeiMacDialog.cancel();
        }
    }

    @SuppressLint("HardwareIds")
    private void employeeCheck() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
           /* if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                if (!TextUtils.isEmpty(PreferenceUtil.getImei()) && !TextUtils.isEmpty(PreferenceUtil.getMac())) {
                    imeiNumber = PreferenceUtil.getImei();
                    macAddress = PreferenceUtil.getMac();

                    Log.d("check", imeiNumber);
                }
            } else {*/

        imeiNumber = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
       // macAddress = getMacAddr();
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        macAddress = wInfo.getMacAddress();

        Log.e("imeiNumber",imeiNumber);
        Log.e("macAddress","macAddress"+ macAddress);
        //}
/*
        if (!TextUtils.isEmpty(PreferenceUtil.getImei())) {
            imeiNumber = PreferenceUtil.getImei();
        } else {
            imeiNumber = telephonyManager.getDeviceId();
        }*/

        // imeiMacBottomDialog();

        if (!TextUtils.isEmpty(imeiNumber) && !TextUtils.isEmpty(macAddress)) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after 5s = 5000ms
                    String params[] = new String[6];

                    params[0] = "";
                    params[1] = "";
                    params[2] = imeiNumber;
                    params[3] = macAddress;
                    params[4] = "1";
                    params[5] = versionName;

                    if (connection.isConnectingToInternet()) {
//                        DeviceReg deviceReg = new DeviceReg();
//                        deviceReg.execute(params);
//                        progress = new MaterialDialog.Builder(mCon)
//                                .content(R.string.loading)
//                                .autoDismiss(false)
//                                .canceledOnTouchOutside(false)
//                                .progress(true, 0)
//                                .widgetColorRes(R.color.colorPrimary)
//                                .show();
                        Intent i = new Intent(mCon, LoginActivity.class);
                        i.putExtra("IMEINumber", imeiNumber);
                        i.putExtra("MACAddress", macAddress);
                        i.putExtra("versionName", versionName);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
                    }
                }
            }, 1000);
        } else {
            imeiMacDialog.show();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class DeviceReg extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                String paraName[] = new String[6];
                paraName[0] = "NAME";
                paraName[1] = "MPIN";
                paraName[2] = "IMEI";
                paraName[3] = "MAC";
                paraName[4] = "selectTag";
                paraName[5] = "AppVersion";

                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.RegisterDevice, params, paraName);

            } catch (Exception e) {
                Log.e("check", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                String[] enums = gson.fromJson(jsonResponse, String[].class);

                if (enums[0].equalsIgnoreCase("Success")) {

                    Intent i = new Intent(mCon, LoginActivity.class);
                    i.putExtra("IMEINumber", imeiNumber);
                    i.putExtra("MACAddress", macAddress);
                    i.putExtra("versionName", versionName);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(mCon, RegisterActivity.class);
                    i.putExtra("IMEINumber", imeiNumber);
                    i.putExtra("MACAddress", macAddress);
                    i.putExtra("versionName", versionName);
                    startActivity(i);
                    finish();
                }
            } catch (Exception e) {
                Log.d("check", e.getMessage());
                String error = e.toString();
                ErrorClass.errorData(mCon, "SplashScreen", "RegisterDevice task", error);
                finish();
            }
            progress.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }

    private void getTokenId() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {

                if(!task.isSuccessful()){
                    return;
                }
                //Log.e("FIREBASE-TOKEN", task.getResult());
                UtilitySharedPreferences.setPrefs(mCon, AppConstant.FCMTOKEN, task.getResult());
            }
        });
    }
}