package elink.suezShimla.water.crm.MeterManagementSystem.StoreManagement.AcceptMeter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;

public class AcceptMeterActivity extends AppCompatActivity {
    private Context mCon;
    private RadioButton meterNoRadioButton;
    private TextInputLayout processDateInputLayout,fromInputLayout,toInputLayout,remarkInputLayout;
    private LinearLayout reConnRelativeView;
    private EditText processDateEditText;
    private AppCompatSpinner meterNoSpinner,makeCodeSpinner;
    private TextInputEditText fromEditText,toEditText,remarkEditText;
    private MaterialButton saveButton,clearBotton;
    private MaterialDialog progress;
    private String jsonResponse = "";
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    AcceptMeterAsyncTask acceptMeterAsyncTask;
    String fromEditTextstr="",toEditTextstr="",remarkEditTextstr="",meterNoSpinnerstr="",makeCodeSpinnerstr="",processDateStr="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_accept_meter);

        mCon = this;
        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();
        meterNoRadioButton = findViewById(R.id.meterNoRadioButton);
        processDateInputLayout = findViewById(R.id.processDateInputLayout);
        fromInputLayout = findViewById(R.id.fromInputLayout);
        toInputLayout = findViewById(R.id.toInputLayout);
        remarkInputLayout = findViewById(R.id.remarkInputLayout);
        reConnRelativeView = findViewById(R.id.reConnRelativeView);
        processDateEditText = findViewById(R.id.processDateEditText);
        fromEditText = findViewById(R.id.fromEditText);
        toEditText = findViewById(R.id.toEditText);
        remarkEditText = findViewById(R.id.remarkEditText);
        meterNoSpinner = findViewById(R.id.meterNoSpinner);
        makeCodeSpinner = findViewById(R.id.makeCodeSpinner);
        saveButton = findViewById(R.id.saveButton);
        Date date = new Date();
        String strDateFormat = "dd-MMM-yyyy HH:mm:ss";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate = dateFormat.format(date);
        processDateStr=  formattedDate.toString();
        processDateEditText.setText(processDateStr);
        fromEditText.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            fromEditTextstr = fromEditText.getText().toString().trim();
            int a=0;
            a= Integer.parseInt(fromEditTextstr)+500;
            toEditText.setText(String.valueOf(a));
        }
    });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                meterNoSpinnerstr=meterNoSpinner.getSelectedItem().toString().trim();
                fromEditTextstr = fromEditText.getText().toString().trim();
                toEditTextstr = toEditText.getText().toString().trim();
                makeCodeSpinnerstr=makeCodeSpinner.getSelectedItem().toString().trim();
                remarkEditTextstr = remarkEditText.getText().toString().trim();


                validate();
            }
        });
    }

    private void saveAcceptMeterData(){

        String empcode = null;
        try {
            // Decrypt EmpCode
            empcode = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] params = new String[8];

        params[0] = fromEditTextstr;
        params[1] = toEditTextstr;
        params[2] = makeCodeSpinnerstr;
        params[3] = processDateStr;
        params[4] = remarkEditTextstr;
        params[5] = meterNoSpinnerstr;
        params[6] = empcode;
        params[7] = "";


        if (connection.isConnectingToInternet()) {
            acceptMeterAsyncTask  = new AcceptMeterAsyncTask();
            acceptMeterAsyncTask.execute(params);
        } else {
            Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }


    }

    private void validate() {

        boolean isvalidFromvalue = false, isvalidTovalue = false, isRemark = false, isMeterNo=false , isMakeCode=false;


        if (TextUtils.isEmpty(fromEditTextstr)) {
            fromInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            isvalidFromvalue = true;
            fromInputLayout.setError(null);
        }

        if (TextUtils.isEmpty(toEditTextstr)) {
            toInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            isvalidTovalue = true;
            toInputLayout.setError(null);
        }

        if (TextUtils.isEmpty(remarkEditTextstr)) {
            remarkInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            isRemark = true;
            remarkInputLayout.setError(null);
        }

        if(remarkEditText.length()>300)
        {
            remarkInputLayout.setError("Remarks Cannot be greater than 300");
        } else {
            isRemark = true;
            remarkInputLayout.setError(null);
        }
        if (meterNoSpinnerstr.equals("-- Select --")) {
          Toast.makeText(mCon,"MeterNo Cannot be empty",Toast.LENGTH_SHORT).show();
        } else {
            isMeterNo = true;

        }
        if (makeCodeSpinnerstr.equals("-- Select --")) {
            Toast.makeText(mCon,"MakeCode Cannot be empty",Toast.LENGTH_SHORT).show();
        } else {
            isMakeCode = true;

        }
        if (isvalidFromvalue && isvalidTovalue && isRemark && isMeterNo && isMakeCode  ) {

            saveAcceptMeterData();

        }
    }
    @SuppressLint("StaticFieldLeak")
    private class AcceptMeterAsyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            progress = new MaterialDialog.Builder(mCon)
                    .content(R.string.loading)
                    .progress(true, 0)
                    .cancelable(false)
                    .canceledOnTouchOutside(false)
                    .widgetColorRes(R.color.colorPrimary)
                    .show();
        }
        @Override
        protected Void doInBackground(String... params) {
            try {
                String paraNames[] = new String[8];
                paraNames[0] = "FromMeterNo";
                paraNames[1] = "ToMeterNo";
                paraNames[2] = "MakeCode";
                paraNames[3] = "ProcessDate";
                paraNames[4] = "Remarks";
                paraNames[5] = "drpMeterNo";
                paraNames[6] = "EmpCode";
                paraNames[7] = "IP";
                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.MMG_StoreManagementAcceptMeterSave, params, paraNames);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
            } catch (Exception e) {
                Log.e("AcceptMeterException",e.toString());
            }
            progress.dismiss();
        }
    }
    @Override
    public void onResume() {
        super.onResume();

        App myApp = (App) this.getApplication();
        if (myApp.wasInBackground) {
            finish();
            startActivity(new Intent(mCon, SplashScreen.class));
        }

        myApp.stopActivityTransitionTimer();
    }

    @Override
    public void onPause() {
        super.onPause();
        ((App) this.getApplication()).startActivityTransitionTimer();
    }
}
