package elink.suezShimla.water.crm.MeterManagementSystem.StoreManagement.IssueToMmgDept;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.MeterManagementSystem.StoreManagement.IssueToMmgDept.Adapter.IssueToMmgAdapter;
import elink.suezShimla.water.crm.MeterManagementSystem.StoreManagement.IssueToMmgDept.Model.IssueToMmgModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;

public class IssueToMmgDept extends AppCompatActivity {
    RecyclerView summaryRecyclerView;
    private List<IssueToMmgModel> issueToMmgModelList = new ArrayList<>();
    private IssueToMmgAdapter issueToMmgAdapter;
    private Context mCon;
    private MaterialDialog progress;
    private String jsonResponse = "";
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    private TextView totalStockBtmCount,totalstockUprCount;
    private IssueMMGDeptAsyncTask issueMMGDeptAsyncTask;
    String srNo="",authDate="";
    int availStock=0;
    int  availStockNumber=0;
    float initiaLStockCount = (float) 0.0;
    float availStockCount =(float) 0.0;
    String totalStockCount="";

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_to_mmg_dept);
   // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mCon = this;
        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();

        issueToMmgAdapter = new IssueToMmgAdapter(mCon);

        summaryRecyclerView = findViewById(R.id.summaryRecyclerView);
         totalstockUprCount = findViewById(R.id.totalstockUprCount);
         totalStockBtmCount = findViewById(R.id.totalStockBtmCount);
        summaryRecyclerView.setHasFixedSize(true);
        summaryRecyclerView.setLayoutManager(new LinearLayoutManager(mCon));
        getIssuetoMMMGData();
       // loadSummaryData();
    }

    private void loadSummaryData() {
    IssueToMmgModel model = new IssueToMmgModel("1"," 01-Mar-2019  ",5);
    issueToMmgModelList.add(model);

        model = new IssueToMmgModel("2"," 01-Mar-2019  ",2);
        issueToMmgModelList.add(model);

        model = new IssueToMmgModel("3"," 02-Mar-2019  ",8);
        issueToMmgModelList.add(model);

        model = new IssueToMmgModel("4"," 03-Mar-2019  ",3);
        issueToMmgModelList.add(model);

        model = new IssueToMmgModel("5"," 04-Mar-2019  ",9);
        issueToMmgModelList.add(model);

        model = new IssueToMmgModel("6"," 01-Mar-2019  ",5);
        issueToMmgModelList.add(model);

    issueToMmgAdapter.addList(issueToMmgModelList);
    summaryRecyclerView.setAdapter(issueToMmgAdapter);
    }

    @SuppressLint("StaticFieldLeak")
    private class IssueMMGDeptAsyncTask extends AsyncTask<String, Void, Void> {
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
                paraNames[0] = "MeterPhase";
                paraNames[1] = "Processdate";
                paraNames[2] = "flag";
                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.MMG_StoreManagementIssueToMMGSummary, params, paraNames);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                issueToMmgModelList =new ArrayList<>();

                IssueToMmgModel[] enums = gson.fromJson(jsonResponse, IssueToMmgModel[].class);

                String SerialNo="";
                String totalCountCnvr="";
                initiaLStockCount = (float) 0.0;
                initiaLStockCount=(float) 0.0;
                totalStockCount="";
                if (enums != null && enums.length > 0) {

                    for (IssueToMmgModel model : enums) {

                        srNo =model.getSrNo();
                        // srNo ="0";

                       // SerialNo= (srNo.substring(0, srNo.indexOf('.')));
                        authDate = model.getAuthDate();
                        availStock = (model.getAvailableStock());

                        for(int i=0;i<=enums.length;i++){
                            availStockNumber =model.getAvailableStock();
                            availStockCount= (availStockNumber);
                        }
                        initiaLStockCount = initiaLStockCount+availStockCount;

                        totalStockCount = String.valueOf(initiaLStockCount);
                        totalCountCnvr=(totalStockCount.substring(0, totalStockCount.indexOf('.')));

                        totalstockUprCount.setText(totalCountCnvr);
                        totalStockBtmCount.setText(totalCountCnvr);

                        IssueToMmgModel data = new IssueToMmgModel(srNo,authDate,availStock);
                        issueToMmgModelList.add(data);

                        issueToMmgAdapter.addList(issueToMmgModelList);
                    }

                    summaryRecyclerView.setAdapter(issueToMmgAdapter);
                    issueToMmgAdapter.notifyDataSetChanged();


                } else {

                }


            } catch (Exception e) {
                Log.e("AuthenticationException",e.toString());
                String error = e.toString();
                ErrorClass.errorData(mCon, "IssueToMmgDept Activity", "StoreManagementActivity card click", error);
            }
            progress.dismiss();
        }
    }

    private void getIssuetoMMMGData(){
        String[] params = new String[3];
        params[0] = "0";
        params[1] = "";
        params[2] = "0";

        if (connection.isConnectingToInternet()) {
            issueMMGDeptAsyncTask  = new IssueMMGDeptAsyncTask();
            issueMMGDeptAsyncTask.execute(params);
        } else {
            Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
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
