package elink.suezShimla.water.crm.Complaint.TodaysCompletedWork;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.Complaint.TodaysCompletedWork.Adapter.TodayCompleteWorkAdapter;
import elink.suezShimla.water.crm.Complaint.TodaysCompletedWork.Model.TodayWorkCompletionResponseModel;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;

public class TodaysCompletedWorkActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView todayWorkRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TodayCompleteWorkAdapter todayCompleteWorkAdapter;
    private List<TodayWorkCompletionResponseModel> todayCompleteWorkModels = new ArrayList<>();
    private Context mCon;
    private LinearLayout errorLinear;
    private MaterialDialog progress;

    private String jsonResponse = "", imei, mac, employeeID;
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    private RealmOperations realmOperations;

    private String subZoneStr, consumerNoStr, nameStr, addressStr, phoneStr, tariffStr, complaintTypeStr, complaintSubTypeStr, workCompletionDateStr,
            attendedByStr, remarkStr, complaintNoStr, allocationDateStr, complaintDateStr, statusStr, allocatedEmpStr,vipName,originStr="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todays_completed_work);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mCon = this;

        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();
        realmOperations = new RealmOperations(mCon);

        errorLinear = findViewById(R.id.errorLinear);
        todayWorkRecyclerView = findViewById(R.id.todayWorkRecyclerView);
        todayWorkRecyclerView.setHasFixedSize(true);
        todayWorkRecyclerView.setLayoutManager(new LinearLayoutManager(mCon));

        todayCompleteWorkAdapter = new TodayCompleteWorkAdapter(mCon);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark, android.R.color.holo_red_dark);




       getTodaysWorkCompletion();
    }

    private void getTodaysWorkCompletion() {

        String empcode = null;
        try {
            // Decrypt EmpCode
            empcode = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String params[] = new String[1];

        params[0] = empcode;

        if (connection.isConnectingToInternet()) {
            TodayWorkCompletion todayWorkCompletion = new TodayWorkCompletion();
            todayWorkCompletion.execute(params);
        } else {
            Toast.makeText(mCon, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRefresh() {
        getTodaysWorkCompletion();
        swipeRefreshLayout.setRefreshing(false);
    }

    @SuppressLint("StaticFieldLeak")
    private class TodayWorkCompletion extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            progress = new MaterialDialog.Builder(mCon)
                    .content(R.string.loading)
                    .progress(true, 0)
                    .canceledOnTouchOutside(false)
                    .cancelable(false)
                    .widgetColorRes(R.color.colorPrimary)
                    .show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                String paramNames[] = new String[1];

                paramNames[0] = "w_EmpCode";

                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.TodayWorkCompletionData, params, paramNames);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                todayCompleteWorkModels = new ArrayList<>();
                TodayWorkCompletionResponseModel[] enums = gson.fromJson(jsonResponse, TodayWorkCompletionResponseModel[].class);

                if (enums != null && enums.length > 0) {
                    for (TodayWorkCompletionResponseModel model : enums) {
                        subZoneStr = model.getSUBZONE();
                        consumerNoStr = model.getSRM_SERVICE_NO();
                        nameStr = model.getCONSUMERNAME();
                        addressStr = model.getADDRESS();
                        phoneStr = model.getPHONENO();
                        tariffStr = model.getTARIFF();
                        complaintTypeStr = model.getCOMPTYPE();
                        complaintSubTypeStr = model.getCOMPSUBTYPE();
                        workCompletionDateStr = model.getCOM_WORKCOMPLETIONDATE();
                        attendedByStr = model.getATTENDEDBY();
                        remarkStr = model.getCOM_REMARKS();
                        complaintNoStr = model.getCOMNO();
                        allocationDateStr = model.getCOM_ALLOCATIONDATE();
                        complaintDateStr = model.getCOM_COMPDATE();
                        statusStr = model.getCOM_STATUS();
                        allocatedEmpStr = model.getCOM_EMP_ALLOCATE();
                        vipName = model.getVIP();
                        originStr = model.getORIGIN();

                        TodayWorkCompletionResponseModel data = new TodayWorkCompletionResponseModel(subZoneStr, consumerNoStr, nameStr, addressStr, phoneStr,
                                tariffStr, complaintTypeStr, complaintSubTypeStr, workCompletionDateStr, attendedByStr, remarkStr, complaintNoStr, allocationDateStr,
                                complaintDateStr, statusStr, allocatedEmpStr, vipName,originStr);
                        todayCompleteWorkModels.add(data);
                    }
                    errorLinear.setVisibility(View.GONE);
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    todayCompleteWorkAdapter = new TodayCompleteWorkAdapter(mCon);
                    todayCompleteWorkAdapter.addList(todayCompleteWorkModels);
                    todayWorkRecyclerView.setAdapter(todayCompleteWorkAdapter);
                    todayCompleteWorkAdapter.notifyDataSetChanged();

                } else {
                    errorLinear.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setVisibility(View.GONE);
                    //Toast.makeText(mCon, R.string.no_complaints, Toast.LENGTH_SHORT).show();
                }

                progress.dismiss();
            }catch (Exception e){
                String error = e.toString();
                ErrorClass.errorData(mCon, "TodaysCompletedWorkActivity", "TodayWorkCompletion", error);
            }
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
    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(new Intent(mCon, SplashScreen.class));
    }
}
