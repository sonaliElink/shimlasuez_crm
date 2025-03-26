package elink.suezShimla.water.crm.Complaint.RegistrationAndHistory.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.Complaint.RegistrationAndHistory.Adapter.TrackingAdapter;
import elink.suezShimla.water.crm.Complaint.RegistrationAndHistory.Model.TractingModel;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;

public class ActivityTrackingHistory extends AppCompatActivity {

    String refNumber="",jsonResponse="";
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    private MaterialDialog progress;
    private List<TractingModel> tractingModelsList ;
    String typeStr="",fcoMnoStr="",cmmServiceNoStr="",cmmCompbyStr="",ocrmNameStr="",cmmProcdtStr="",
            cscmSecNameStr="",cmmRemarkStr="",cmmStatusStr="",empStr="",cmmProceodeStr="",repeatCallStr="",
            reasonStr="",cmmConNoSequenceStr="",comNoTypeStr="",subTypeStr="",compByStr="",
            csmSourceDesStr="",agingStr="",todayReapetCallStr="";
    private Context mCon;


    private RecyclerView allocationRecyclerView;
    private LinearLayout errorLinear;
    private TrackingAdapter trackingAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_history);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        init();
        mCon = this;

    }

    private void init() {
        refNumber = getIntent().getStringExtra("refNumber");

        trackingAdapter = new TrackingAdapter(this);

        errorLinear = findViewById(R.id.errorLinear);

        allocationRecyclerView = findViewById(R.id.allocationRecyclerView);
        allocationRecyclerView.setHasFixedSize(true);
        allocationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        gson = new Gson();
        connection = new ConnectionDetector(this);
        invServices = new Invoke();
        trackingHistory();
    }

    private void trackingHistory() {

        String empcode = null;
        try {
            // Decrypt EmpCode
            empcode = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String params[] = new String[2];

        params[0] = "C";   //type
        params[1] = refNumber;  // referance number
        if (connection.isConnectingToInternet()) {
           TrackingHistory trackingHistory = new TrackingHistory();
            trackingHistory.execute(params);
        } else {
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }

    }

    @SuppressLint("StaticFieldLeak")
    private class TrackingHistory extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            progress = new MaterialDialog.Builder(ActivityTrackingHistory.this)
                    .content(R.string.loading)
                    .progress(true, 0)
                    .widgetColorRes(R.color.colorPrimary)
                    .canceledOnTouchOutside(false)
                    .cancelable(false)
                    .show();
        }

        @Override
        protected Void doInBackground(String... params) {

            try {
                String paraNames[] = new String[2];

                paraNames[0] = "W_tag";
                paraNames[1] = "W_CompNo";


                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.Complaint_Detail, params, paraNames);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                tractingModelsList = new ArrayList<>();
                TractingModel[] enums = gson.fromJson(jsonResponse, TractingModel[].class);



                if (enums != null && enums.length > 0) {
                    for (TractingModel model : enums) {
                        typeStr = model.getTYPE();
                        fcoMnoStr = model.getFCOMNO();
                        cmmServiceNoStr = model.getCMM_SERVICE_NO();
                        cmmCompbyStr = model.getCMM_COMPBY();
                        ocrmNameStr = model.getOCRM_NAME();

                        cmmProcdtStr = model.getCMM_PROCDT();
                        cscmSecNameStr = model.getCSCM_SECNAME();
                        cmmRemarkStr = model.getCMM_REMARKS();
                        cmmStatusStr = model.getCMM_STATUS();
                        empStr = model.getEMP();

                        cmmProceodeStr = model.getCMM_PROCCODE();
                        repeatCallStr = model.getREPEATCALL();
                        reasonStr = model.getREASON();
                        cmmConNoSequenceStr = model.getCMM_COMNO_SEQUENCE();
                        comNoTypeStr=model.getCOM_NO_TYPE();

                        subTypeStr = model.getSUBTYPEID();
                        compByStr = model.getCMM_COMPBY();
                        csmSourceDesStr = model.getCSM_SOURCEDESC();
                        agingStr = model.getAGING();
                        todayReapetCallStr = model.getTODAYREPEATCOUNT();


                        TractingModel data = new TractingModel(typeStr, fcoMnoStr, cmmServiceNoStr, cmmCompbyStr, ocrmNameStr, cmmProcdtStr,
                                cscmSecNameStr, cmmRemarkStr, cmmStatusStr, empStr, cmmProceodeStr, repeatCallStr, reasonStr,
                                cmmConNoSequenceStr,comNoTypeStr, subTypeStr,compByStr, csmSourceDesStr, agingStr, todayReapetCallStr);

                        tractingModelsList.add(data);


                    }

                    errorLinear.setVisibility(View.GONE);
                //    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    allocationRecyclerView.setVisibility(View.VISIBLE);
                    trackingAdapter.addList(tractingModelsList);
                    allocationRecyclerView.setAdapter(trackingAdapter);
                    trackingAdapter.notifyDataSetChanged();


                } else {
                    errorLinear.setVisibility(View.VISIBLE);
                //    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    allocationRecyclerView.setVisibility(View.GONE);
                    Toast.makeText(ActivityTrackingHistory.this, R.string.no_data_found, Toast.LENGTH_SHORT).show();
                    progress.dismiss();

                }
            } catch (Exception e) {
                Log.d("check", e.getMessage());
                Toast.makeText(ActivityTrackingHistory.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                String error = e.toString();
                ErrorClass.errorData(ActivityTrackingHistory.this, "Complaint_GetWorkAllocationData", "Show or Swipe to refresh", error);
                progress.dismiss();

            }
            progress.dismiss();
        }

        }

    @Override
    public void onBackPressed() {
//        finish();
        super.onBackPressed();
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
    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(new Intent(mCon, SplashScreen.class));
    }
}
