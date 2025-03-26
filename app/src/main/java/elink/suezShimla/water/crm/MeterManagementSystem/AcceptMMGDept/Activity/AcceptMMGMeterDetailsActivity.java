package elink.suezShimla.water.crm.MeterManagementSystem.AcceptMMGDept.Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.MeterManagementSystem.AcceptMMGDept.Adapter.AcceptMMGMeterDetailsAdapter;
import elink.suezShimla.water.crm.MeterManagementSystem.AcceptMMGDept.Model.AcceptMMGMeterDetailsModel;
import elink.suezShimla.water.crm.MeterManagementSystem.AcceptMMGDept.Model.AcceptResponseModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;

public class AcceptMMGMeterDetailsActivity extends AppCompatActivity {

    private Context mCon;

    private RecyclerView meterDetailsRecycler;
    private AcceptMMGMeterDetailsAdapter acceptMMGMeterDetailsAdapter;
    private List<AcceptMMGMeterDetailsModel> acceptMMGMeterDetailsModelList;
    private LinearLayoutManager linearLayoutManager;

    private Calendar acceptDateCalendar;

    private TextInputLayout acceptDateInputLayout, acceptedByInputLayout;
    private TextInputEditText acceptDateEditText, acceptedByEditText,remarkEditText;

    private MaterialButton acceptButton;
    private Menu menu;
    private MaterialDialog progress;
    private TextView cmnissuedToPersonTextView,textViewTotRecords;
    private String jsonResponse = "";
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    AcceptMMGDetailAsyncTask acceptMMGDetailAsyncTask;
    private String acceptDateStr="", acceptedByStr="",remarkStr="";
    String V_dispatchDate="",V_meterCount="",V_issuedToperson="";
    String meterNo="",dispatchDate="",makeCode="",employeeCode="";
    boolean isChecked=false;
    int srNo=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_mmgmeter_details);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mCon = this;
        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();

        Bundle bundle=getIntent().getExtras();
        V_dispatchDate=bundle.getString("dispatchDate");
        V_meterCount=bundle.getString("meterCount");
        V_issuedToperson=bundle.getString("issuedToPerson");

        acceptDateCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener acceptCalenderDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                acceptDateCalendar.set(Calendar.YEAR, year);
                acceptDateCalendar.set(Calendar.MONTH, monthOfYear);
                acceptDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateAcceptDateCalendar();
            }
        };

        meterDetailsRecycler = findViewById(R.id.meterDetailsRecycler);
        acceptDateInputLayout = findViewById(R.id.acceptDateInputLayout);
        acceptedByInputLayout = findViewById(R.id.acceptedByInputLayout);
        acceptDateEditText = findViewById(R.id.acceptDateEditText);
        acceptedByEditText = findViewById(R.id.acceptedByEditText);
        acceptButton = findViewById(R.id.acceptButton);
        cmnissuedToPersonTextView = findViewById(R.id.cmnissuedToPersonTextView);
        textViewTotRecords = findViewById(R.id.textViewTotRecords);
        remarkEditText = findViewById(R.id.remarkEditText);


        acceptMMGMeterDetailsAdapter = new AcceptMMGMeterDetailsAdapter(mCon);
        acceptMMGMeterDetailsModelList = new ArrayList<>();

        meterDetailsRecycler.addItemDecoration(new DividerItemDecoration(mCon, DividerItemDecoration.VERTICAL));

        meterDetailsRecycler.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(mCon);
        meterDetailsRecycler.setLayoutManager(linearLayoutManager);
        meterDetailsRecycler.setItemAnimator(new DefaultItemAnimator());

        acceptDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(mCon, acceptCalenderDate, acceptDateCalendar
                        .get(Calendar.YEAR), acceptDateCalendar.get(Calendar.MONTH),
                        acceptDateCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
                datePickerDialog.show();
            }
        });

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptDateStr = acceptDateEditText.getText().toString();
                acceptedByStr = acceptedByEditText.getText().toString();
                remarkStr = remarkEditText.getText().toString();

                validateAcceptMeter();
            }
        });

        getAcceptMMGRecordData();
       // loadAcceptRecycler();
        //initially accepted date is system date
        String date_accepted = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date());
        acceptDateEditText.setText(date_accepted);
    }
    private void getAcceptMMGRecordData() {

        String[] params = new String[3];
        params[0] = V_dispatchDate;
        params[1] = V_issuedToperson;
        params[2] = "M";

        if (connection.isConnectingToInternet()) {
            acceptMMGDetailAsyncTask = new AcceptMMGDetailAsyncTask();
            acceptMMGDetailAsyncTask.execute(params);
        } else {
            Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }
    // Set Accept Date
    private void updateAcceptDateCalendar() {
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        acceptDateEditText.setText(sdf.format(acceptDateCalendar.getTime()));
    }

    // Set Recycler Data
    private void loadAcceptRecycler() {
        for (int i = 1; i < 3; i++) {
            AcceptMMGMeterDetailsModel model = new AcceptMMGMeterDetailsModel(i, "0001107" + i, "1-ARAD", "2" + i + "-Mar-2019 03:07 PM", false);
            acceptMMGMeterDetailsModelList.add(model);
            acceptMMGMeterDetailsAdapter.addList(acceptMMGMeterDetailsModelList);
            meterDetailsRecycler.setAdapter(acceptMMGMeterDetailsAdapter);
        }
    }


    // Validate Accept Meter On Click
    private void validateAcceptMeter() {
        boolean isValidAcceptDate = false, isValidAcceptedBy = false;

        if (TextUtils.isEmpty(acceptDateStr)) {

            acceptDateInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            acceptDateInputLayout.setError(null);
            isValidAcceptDate = true;
        }

        if (TextUtils.isEmpty(acceptedByStr)) {
            acceptedByInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            acceptedByInputLayout.setError(null);
            isValidAcceptedBy = true;
        }

        if (isValidAcceptDate && isValidAcceptedBy) {

            List<Boolean> isCheckedList = new ArrayList<>();

            for (AcceptMMGMeterDetailsModel model : acceptMMGMeterDetailsAdapter.getList()) {
                if (model.isChecked()) {
                    isCheckedList.add(model.isChecked());
                }
            }

            if (isCheckedList.size() > 0) {
                submitAction();
            } else {
                Toast.makeText(mCon, "Select at one least Meter", Toast.LENGTH_SHORT).show();
            }
        }
    }

        public void submitAction(){
            String empcode = null;
            try {
                // Decrypt EmpCode
                empcode = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
            } catch (Exception e) {
                e.printStackTrace();
            }

            String params[] = new String[9];
            params[0] =  acceptDateStr;
            params[1] =  V_dispatchDate;
            params[2] =  meterNo ;
            params[3] =  remarkStr ;
            params[4] =  makeCode;
            params[5] =  empcode;
            params[6] =  "";
            params[7] =  acceptedByStr;
            params[8] =  V_issuedToperson;

            if (connection.isConnectingToInternet()) {
                AcceptMMGsubmitAsyncTask  acceptMMGsubmitAsyncTask = new AcceptMMGsubmitAsyncTask();
                acceptMMGsubmitAsyncTask.execute(params);
            } else {
                Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            }
        }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.accept_meter_checkbox_menu, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_check_all) {

            for (AcceptMMGMeterDetailsModel model : acceptMMGMeterDetailsModelList) {

                if (model.isChecked()) {
                    model.setChecked(false);
                    menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_check_box_blank));
                } else {
                    model.setChecked(true);
                    menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_check_box_checked));
                }
                acceptMMGMeterDetailsAdapter.notifyDataSetChanged();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("StaticFieldLeak")
    private class AcceptMMGDetailAsyncTask extends AsyncTask<String, Void, Void> {
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
                String paraNames[] = new String[3];
                paraNames[0] = "w_dispatchdate";
                paraNames[1] = "w_issuedtoperson";
                paraNames[2] = "w_meter";

                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.MMG_AcceptAtMMGDetail, params, paraNames);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                acceptMMGMeterDetailsModelList=new ArrayList<>();
                AcceptMMGMeterDetailsModel[] enums = gson.fromJson(jsonResponse, AcceptMMGMeterDetailsModel[].class);
                cmnissuedToPersonTextView.setText(V_issuedToperson);
                textViewTotRecords.setText("Total No. of Records " +enums.length);
                if (enums != null && enums.length > 0) {
                    for ( AcceptMMGMeterDetailsModel model : enums) {
                        srNo=model.getSrNo();
                        makeCode=model.getMakeCode();
                        dispatchDate=model.getDispatchDate();
                        meterNo=model.getMeterNo();

                        AcceptMMGMeterDetailsModel   data = new AcceptMMGMeterDetailsModel(srNo,meterNo,makeCode,dispatchDate,isChecked );
                        acceptMMGMeterDetailsModelList.add(data);
                        acceptMMGMeterDetailsAdapter.addList(acceptMMGMeterDetailsModelList);
                    }
                    meterDetailsRecycler.setAdapter(acceptMMGMeterDetailsAdapter);
                    acceptMMGMeterDetailsAdapter.notifyDataSetChanged();
                }
            } catch (Exception e) {
                String error = e.toString();
                ErrorClass.errorData(mCon, "AcceptMMGMeterDetailsActivity", "MMG_AcceptAtMMGDetail", error);
            }
            progress.dismiss();
        }
    }


    @SuppressLint("StaticFieldLeak")
    private class AcceptMMGsubmitAsyncTask extends AsyncTask<String, Void, Void> {

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
                String paraNames[] = new String[9];

                paraNames[0] = "M_IssueDt";
                paraNames[1] = "M_DispatchDt";
                paraNames[2] = "M_MeterNo";
                paraNames[3] = "M_remarks";
                paraNames[4] = "M_MakeName";
                paraNames[5] = "M_EmpCode";
                paraNames[6] = "M_IP";
                paraNames[7] = "AcceptBy";
                paraNames[8] = "IssuedPerson";

                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.MMG_AcceptAtMMGSubmit, params, paraNames);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                AcceptResponseModel enums = gson.fromJson(jsonResponse, AcceptResponseModel.class);
                if (enums.getDiv_Cmsg() != null && !enums.getDiv_Cmsg().equals("")) {
                    //int count = Integer.parseInt(enums.getCount());
                    if (enums.getDiv_Cmsg().equals("Success")) {
                        Toast.makeText(mCon, R.string.accept_mmg_successfully, Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(mCon, enums.getDiv_Cmsg(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(mCon, enums.getDiv_CError(), Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Log.d("AcceptMMGDetailActivity", e.getMessage());
                Toast.makeText(mCon, e.getMessage(), Toast.LENGTH_SHORT).show();
                String error = e.toString();
                ErrorClass.errorData(mCon, "AcceptMMGDetailActivity", " accept button", error);
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
