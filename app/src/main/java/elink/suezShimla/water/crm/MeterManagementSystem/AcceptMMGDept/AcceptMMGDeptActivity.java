package elink.suezShimla.water.crm.MeterManagementSystem.AcceptMMGDept;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
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
import elink.suezShimla.water.crm.MeterManagementSystem.AcceptMMGDept.Adapter.AcceptMMGDeptAdapter;
import elink.suezShimla.water.crm.MeterManagementSystem.AcceptMMGDept.Model.AcceptMMGDeptModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;

public class AcceptMMGDeptActivity extends AppCompatActivity {

    private Context mCon;

    private RecyclerView meterDetailsRecycler;
    private AcceptMMGDeptAdapter acceptMMGDeptAdapter;
    private List<AcceptMMGDeptModel> acceptMMGDeptModelList;
    private LinearLayoutManager linearLayoutManager;
    private MaterialDialog progress;
    private String jsonResponse = "";
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    AcceptMMGAsyncTask acceptMMGAsyncTask;
    private List<AcceptMMGDeptModel> acceptmmgList;
    int srNo=0;
    String dispatchDate="",meterCount="",issuedToPerson="",totalMeterCount="";
    String  meterNumber=null;
    private TextView TotalMeterCountTextView;
    float initiaLMeterCount = (float) 0.0;
    float meterTotalCount =(float) 0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_mmgdept);
     //   getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mCon = this;
        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();
        meterDetailsRecycler = findViewById(R.id.meterDetailsRecycler);
        TotalMeterCountTextView = findViewById(R.id.TotalMeterCountTextView);
        acceptMMGDeptAdapter = new AcceptMMGDeptAdapter(mCon);
        acceptMMGDeptModelList = new ArrayList<>();


        meterDetailsRecycler.addItemDecoration(new DividerItemDecoration(mCon, DividerItemDecoration.VERTICAL));

        meterDetailsRecycler.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(mCon);
        meterDetailsRecycler.setLayoutManager(linearLayoutManager);
        meterDetailsRecycler.setItemAnimator(new DefaultItemAnimator());

        getAcceptMMGRecordData();

       // loadAcceptRecycler();
    }

    private void loadAcceptRecycler() {
        for (int i = 1; i < 10; i++) {
            AcceptMMGDeptModel model = new AcceptMMGDeptModel(i, "2" + i + "-Mar-2019", "2", "Kruti " + i);
            acceptMMGDeptModelList.add(model);
            acceptMMGDeptAdapter.addList(acceptMMGDeptModelList);
            meterDetailsRecycler.setAdapter(acceptMMGDeptAdapter);
        }
    }

    private void getAcceptMMGRecordData(){

        String[] params = new String[1];
        params[0] = "M";

        if (connection.isConnectingToInternet()) {
            acceptMMGAsyncTask  = new AcceptMMGAsyncTask();
            acceptMMGAsyncTask.execute(params);
        } else {
            Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }


    }

    @SuppressLint("StaticFieldLeak")
    private class AcceptMMGAsyncTask extends AsyncTask<String, Void, Void> {

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
            String paraNames[] = new String[1];
            paraNames[0] = "Tag";
            jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.MMG_AcceptAtMMGSummary, params, paraNames);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        try {
            acceptmmgList=new ArrayList<>();
            AcceptMMGDeptModel[] enums = gson.fromJson(jsonResponse, AcceptMMGDeptModel[].class);
            initiaLMeterCount = (float) 0.0;
            meterTotalCount =(float) 0.0;
            totalMeterCount="";
            if (enums != null && enums.length > 0) {

                String meterCountafterCnvr="";
                String totalCounytAftercnv="";
                for (AcceptMMGDeptModel model : enums) {
                    srNo =model.getSrNo();
                    dispatchDate = model.getDispatchDate();
                    meterCount = model.getMeterCount();
                    meterCountafterCnvr= (meterCount.substring(0, meterCount.indexOf('.')));
                    issuedToPerson = model.getIssuedToPerson();

                    for(int i=0;i<=enums.length;i++){
                        meterNumber = meterCountafterCnvr;
                        meterTotalCount= Float.parseFloat(meterNumber);
                    }

                    initiaLMeterCount = initiaLMeterCount+meterTotalCount;
                    totalMeterCount = String.valueOf(initiaLMeterCount);
                     totalCounytAftercnv=(totalMeterCount.substring(0, totalMeterCount.indexOf('.')));
                    TotalMeterCountTextView.setText(totalCounytAftercnv);
                    //Toast.makeText(mCon, ""+a, Toast.LENGTH_SHORT).show();
                    AcceptMMGDeptModel data = new AcceptMMGDeptModel(srNo, dispatchDate, meterCountafterCnvr, issuedToPerson);
                    acceptmmgList.add(data);


                }

                acceptMMGDeptAdapter.addList(acceptmmgList);
                meterDetailsRecycler.setAdapter(acceptMMGDeptAdapter);
                acceptMMGDeptAdapter.notifyDataSetChanged();

            } else {
               Toast.makeText(mCon,"NodataInJson",Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("AcceptMMGDeptException",e.toString());
            String error = e.toString();
            ErrorClass.errorData(mCon, "AcceptMMGDeptActivity", "MMG_AcceptAtMMGSummary", error);
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