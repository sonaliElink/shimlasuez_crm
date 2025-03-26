package elink.suezShimla.water.crm.MeterManagementSystem.StoreManagement.IssueToMmgDept;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGMakerCodeModel;
import elink.suezShimla.water.crm.MeterManagementSystem.StoreManagement.IssueToMmgDept.Adapter.IssueToMmgDialougAdapter;
import elink.suezShimla.water.crm.MeterManagementSystem.StoreManagement.IssueToMmgDept.Model.IssueToMMGSaveResponseModel;
import elink.suezShimla.water.crm.MeterManagementSystem.StoreManagement.IssueToMmgDept.Model.IssueToMmgDialougModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;

public class IssueToMmgDialoug extends AppCompatActivity {

    RecyclerView dialougRecycler ;
    private List<IssueToMmgDialougModel> issueToMmgDialougModelList = new ArrayList<>();
    private IssueToMmgDialougAdapter
            issueToMmgDialougAdapter;
    private Context mCon;
    private TextInputLayout dispatchDateTextInputLayout,issueToPersonTextInputLayout,remarkTextInputLayout;
    private TextInputEditText dispatchDateInputEditText;
    private TextView totalRecordsCount,issueToPersonInputEditText,remarkInputEditText;
    private Calendar disaptchdateCalendar;
    private String dispatchDatestr="";
    private MaterialDialog progress;
    private String jsonResponse = "";
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    private String srNo="",meterNo="",makeCode="",statusDate="";
    String dispatchDateStr ="",issueTopersonStr="",remarkStr="";
    private MaterialButton issueButton;
    RealmOperations realmOperations;
    private boolean isSelected = false;
    String makerCodeId="";
    IssueMMGDeptDetailsAsyncTask issueMMGDeptDetailsAsyncTask;
    IssueToMMGsubmitAsyncTask issueToMMGsubmitAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_to_mmg_dialoug);
      //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mCon = this;
        realmOperations = new RealmOperations(mCon);
        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();

        issueToMmgDialougAdapter = new IssueToMmgDialougAdapter(mCon);
        dialougRecycler = findViewById(R.id.dialougRecycler);
        totalRecordsCount = findViewById(R.id.totalRecordsCount);
        dispatchDateTextInputLayout = findViewById(R.id.dispatchDateTextInputLayout);
        dispatchDateInputEditText = findViewById(R.id.dispatchDateInputEditText);
        issueToPersonTextInputLayout = findViewById(R.id.issueToPersonTextInputLayout);
        remarkTextInputLayout = findViewById(R.id.remarkTextInputLayout);
        issueToPersonInputEditText = findViewById(R.id.issueToPersonInputEditText);
        remarkInputEditText = findViewById(R.id.remarkInputEditText);
        issueButton = findViewById(R.id.issueButton);
        dialougRecycler.setHasFixedSize(true);
        dialougRecycler.setLayoutManager(new LinearLayoutManager(mCon));

        disaptchdateCalendar = Calendar.getInstance();
        //Dispatch date set to system date onload
        Date date = new Date();
        String strDateFormat = "dd-MMM-yyyy";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        dispatchDateStr = dateFormat.format(date);
        dispatchDateInputEditText.setText(dispatchDateStr);

        getIssuetoMMMGDetails();

        //loaddata();
        final DatePickerDialog.OnDateSetListener fromCalendarDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                disaptchdateCalendar.set(Calendar.YEAR, year);
                disaptchdateCalendar.set(Calendar.MONTH, monthOfYear);
                disaptchdateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDispatchDateCalendar();
            }
        };

      issueButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
         issueTopersonStr= issueToPersonInputEditText.getText().toString();
         remarkStr=remarkInputEditText.getText().toString();
             validate();
         }
     });
    }

    private void validate() {
        boolean isValidIssueToPerson = false, isValidRemarks = false;
        if (TextUtils.isEmpty(issueTopersonStr)) {

            issueToPersonTextInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            issueToPersonTextInputLayout.setError(null);
            isValidIssueToPerson = true;
        }
        if (TextUtils.isEmpty(remarkStr)) {

            remarkTextInputLayout .setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            remarkTextInputLayout.setError(null);
            isValidRemarks = true;
        }
        if (isValidIssueToPerson && isValidRemarks) {

            List<Boolean> isCheckedList = new ArrayList<>();

            for (IssueToMmgDialougModel model :issueToMmgDialougAdapter.getList()) {
                if (model.isSelected()) {
                    isCheckedList.add(model.isSelected());
                }
            }
            if (isCheckedList.size() > 0) {
                saveIssuetoMMMGDetails();
            } else {
                Toast.makeText(mCon, "Select at one least Meter", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateDispatchDateCalendar() {
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dispatchDateInputEditText.setText(sdf.format(disaptchdateCalendar.getTime()));
    }


    private void loaddata() {

        IssueToMmgDialougModel model = new IssueToMmgDialougModel("1","00011078","1-ARAD","01-Mar-2019 10:54 AM",false);
        issueToMmgDialougModelList.add(model);

        model = new IssueToMmgDialougModel("2","00011078","1-ARAD","01-Mar-2019 10:54 AM",false);
        issueToMmgDialougModelList.add(model);

        model = new IssueToMmgDialougModel("3","00011078","1-ARAD","01-Mar-2019 10:54 AM",false);
        issueToMmgDialougModelList.add(model);

        model = new IssueToMmgDialougModel("4","00011078","1-ARAD","01-Mar-2019 10:54 AM",false);
        issueToMmgDialougModelList.add(model);

        model = new IssueToMmgDialougModel("5","00011078","1-ARAD","01-Mar-2019 10:54 AM",false);
        issueToMmgDialougModelList.add(model);

        issueToMmgDialougAdapter.addList(issueToMmgDialougModelList);
        dialougRecycler.setAdapter(issueToMmgDialougAdapter);


    }
    public void getIssuetoMMMGDetails(){
        String[] params = new String[2];
        params[0] = "0";
        params[1] = "";


        if (connection.isConnectingToInternet()) {
            IssueMMGDeptDetailsAsyncTask issueMMGDeptDetailsAsyncTask  = new IssueMMGDeptDetailsAsyncTask();
            issueMMGDeptDetailsAsyncTask.execute(params);
        } else {

            Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();

        }
    }
    public void saveIssuetoMMMGDetails(){

        String empcode = null;
        try {
            // Decrypt EmpCode
            empcode = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] params = new String[7];
        params[0] = meterNo;
        params[1] = dispatchDateStr;
        params[2] = issueTopersonStr;
        params[3] = remarkStr;
        params[4] = makerCodeId;
        params[5] = empcode;
        params[6] = "";

        if (connection.isConnectingToInternet()) {
              IssueToMMGsubmitAsyncTask  issueToMMGsubmitAsyncTask  = new IssueToMMGsubmitAsyncTask();
              issueToMMGsubmitAsyncTask.execute(params);
        } else {
            Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }
    @SuppressLint("StaticFieldLeak")
    private class IssueMMGDeptDetailsAsyncTask extends AsyncTask<String, Void, Void> {
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
                String paraNames[] = new String[2];
                paraNames[0] = "MeterPhase";
                paraNames[1] = "Processdate";
                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.MMG_StoreManagementIssueToMMGDetail, params, paraNames);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                issueToMmgDialougModelList =new ArrayList<>();
                IssueToMmgDialougModel[] enums = gson.fromJson(jsonResponse, IssueToMmgDialougModel[].class);
                String SerialNo="";
                if (enums != null && enums.length > 0) {
                    for (IssueToMmgDialougModel model : enums) {

                      srNo=model.getSrNo();
                      SerialNo=(srNo.substring(0, srNo.indexOf('.')));
                      makeCode=model.getMakeCode();
                      MMGMakerCodeModel mmgMakerCodeModel=realmOperations.fetchMakerCodeByName(makeCode);
                      makerCodeId  = mmgMakerCodeModel.getMMFG_MFGCODE();
                      meterNo=model.getMeterNo();
                      statusDate=model.getStatusDate();

                    IssueToMmgDialougModel data = new IssueToMmgDialougModel(SerialNo,meterNo,makeCode,statusDate,false);
                    issueToMmgDialougModelList.add(data);

                    issueToMmgDialougAdapter.addList(issueToMmgDialougModelList);
                    }
                    totalRecordsCount.setText("Total No Of Records : "+ enums.length );
                    dialougRecycler.setAdapter(issueToMmgDialougAdapter);
                    issueToMmgDialougAdapter.notifyDataSetChanged();
                }
            } catch (Exception e) {
                Log.e("IssuetoMeterDetailsExpn",e.toString());
                String error = e.toString();
                ErrorClass.errorData(mCon, "IssueToMmgDialoug", "MMG_StoreManagementIssueToMMGDetail task", error);
            }
            progress.dismiss();
        }
    }


    @SuppressLint("StaticFieldLeak")
    private class IssueToMMGsubmitAsyncTask extends AsyncTask<String, Void, Void> {
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
                String paraNames[] = new String[7];
                paraNames[0] = "M_MeterNo";
                paraNames[1] = "DispatchDate";
                paraNames[2] = "IssuePerson";
                paraNames[3] = "M_remarks";
                paraNames[4] = "M_MakeCode";
                paraNames[5] = "EmpCode";
                paraNames[6] = "IP";

                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.MMG_StoreManagementIssueToMMGSave, params, paraNames);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                IssueToMMGSaveResponseModel enums = gson.fromJson(jsonResponse, IssueToMMGSaveResponseModel.class);
                if (enums.getDiv_Cmsg() != null && !enums.getDiv_Cmsg().equals("")) {
                    //int count = Integer.parseInt(enums.getCount());
                    if (enums.getDiv_Cmsg().equals("True")) {
                        Toast.makeText(mCon, R.string.issuedTo_mmg_successfully, Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(mCon, enums.getDiv_CError(), Toast.LENGTH_LONG).show();
                    }
                } else {

                    Toast.makeText(mCon, enums.getDiv_CError(), Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Log.d("IssueToMMGDetailExpn", e.getMessage());
                Toast.makeText(mCon, e.getMessage(), Toast.LENGTH_SHORT).show();
                String error = e.toString();
                ErrorClass.errorData(mCon, "IssueToMmgDialoug Activity", "MMG_StoreManagementIssueToMMGSave task", error);
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
        realmOperations.close();
        ((App) this.getApplication()).startActivityTransitionTimer();
    }
}
