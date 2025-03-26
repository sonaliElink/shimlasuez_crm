package elink.suezShimla.water.crm.Register;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.Arrays;

import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.Login.LoginActivity;
import elink.suezShimla.water.crm.MessageWindow;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;
import elink.suezShimla.water.crm.constant.AppConstant;

public class RegisterActivity extends AppCompatActivity {
    private Context mCon;
    private TextInputLayout employeeIdInputLayout;
    private TextInputEditText employeeIdEditText;
    private MaterialDialog progress;
    private TextView textVersionName,textview_imei;

    private String employeeIdStr, versionNumber, osName, android_id;
    private String password, conPass, imei="", mac="", selectTag="", versionName="";
    private int SDKVersion;

    private String jsonResponse = "", fcmToken="";
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    private MaterialButton registerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.register);

        mCon = this;
        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();
        // Get IMEI and MAC address
        Intent startingIntent = getIntent();


        /*imei = startingIntent.getStringExtra("IMEINumber");
        mac = startingIntent.getStringExtra("MACAddress");*/
        imei = startingIntent.getStringExtra("IMEINumber");
        mac = startingIntent.getStringExtra("MACAddress");
        versionName = startingIntent.getStringExtra("versionName");

     /*   try {
            android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

            Field[] fields = Build.VERSION_CODES.class.getFields();
            osName = fields[Build.VERSION.SDK_INT + 1].getName();
            Log.d("osName", osName);

            SDKVersion = Build.VERSION.SDK_INT;
            versionNumber = Build.VERSION.RELEASE;

            Log.d("SDKVersion", "" + SDKVersion + ", " + versionNumber + ", " + android_id);
        } catch (Exception e) {
            Log.d("errorMessage", e.getMessage());
         //   Toast.makeText(mCon, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
*/
        employeeIdInputLayout = findViewById(R.id.employeeIdInputLayout);
        employeeIdEditText = findViewById(R.id.employeeIdEditText);
        textVersionName = findViewById(R.id.textVersionName);
        textview_imei = findViewById(R.id.textview_imei);
        textVersionName.setText(getResources().getString(R.string.version) + versionName);
        textview_imei.setText(getResources().getString(R.string.unique_id)  +imei);

        registerButton = findViewById(R.id.registerButton);
       // mPinHiddenEditText = findViewById(R.id.pin_hidden_edittext);

        // Pin Listener Function
        //setPINListeners();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                employeeIdStr = employeeIdEditText.getText().toString().trim();
              /*  pinOneStr = pinOneEditText.getText().toString().trim();
                pinTwoStr = pinTwoEditText.getText().toString().trim();
                pinThreeStr = pinThreeEditText.getText().toString().trim();
                pinFourStr = pinFourEditText.getText().toString().trim();
                conPinOneStr = conPinOneEditText.getText().toString().trim();
                conPinTwoStr = conPinTwoEditText.getText().toString().trim();
                conPinThreeStr = conPinThreeEditText.getText().toString().trim();
                conPinFourStr = conPinFourEditText.getText().toString().trim();

                password = pinOneStr + pinTwoStr + pinThreeStr + pinFourStr;
                conPass = conPinOneStr + conPinTwoStr + conPinThreeStr + conPinFourStr;*/

                // Validate EditText
                validate();
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    // Validate Register EditText
    private void validate() {

        boolean isValidEmployeeId = false;

        if (TextUtils.isEmpty(employeeIdStr)) {
            employeeIdInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            employeeIdInputLayout.setError(null);
            isValidEmployeeId = true;
        }

        if (isValidEmployeeId ) {
            String params[] = new String[6];

            params[0] = employeeIdStr;
            params[1] = "";
            params[2] = imei;
            params[3] = mac;
            params[4] = "0";
            params[5] = versionName;

            Log.d("aaaa", "" + Arrays.toString(params));

            if (connection.isConnectingToInternet()) {
                RegisterUser registerUser = new RegisterUser();
                registerUser.execute(params);
                progress = new MaterialDialog.Builder(mCon)
                        .content(R.string.loading)
                        .autoDismiss(false)
                        .canceledOnTouchOutside(false)
                        .progress(true, 0)
                        .widgetColorRes(R.color.colorPrimary)
                        .show();
            } else {
                Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class RegisterUser extends AsyncTask<String, Void, Void> {
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
                Log.d("jsonResponse", "" + jsonResponse);
            } catch (Exception e) {
                Log.e("jsonRespError", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                String[] enums = gson.fromJson(jsonResponse, String[].class);

                if (enums[0].equalsIgnoreCase("Success")) {

                    UtilitySharedPreferences.setPrefs(mCon, AppConstant.LASTLOGIN,"1");

                    PreferenceUtil.setLastLogin("");
                    Toast.makeText(mCon, R.string.device_register, Toast.LENGTH_LONG).show();
                    Intent i = new Intent(mCon, LoginActivity.class);
                    i.putExtra("IMEINumber", imei);
                    i.putExtra("MACAddress", mac);
                    i.putExtra("versionName", versionName);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(mCon, enums[2], Toast.LENGTH_LONG).show();
                    MessageWindow.messageWindow(mCon, enums[2], "Error");

                }
            } catch (Exception e) {
                Log.d("postexecError", e.getMessage());
                Toast.makeText(mCon,  e.getMessage(), Toast.LENGTH_SHORT).show();
                String error = e.toString();
                ErrorClass.errorData(mCon, "RegisterActivity", "Register button event", error);
            }

            progress.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(new Intent(mCon, SplashScreen.class));
    }

}
