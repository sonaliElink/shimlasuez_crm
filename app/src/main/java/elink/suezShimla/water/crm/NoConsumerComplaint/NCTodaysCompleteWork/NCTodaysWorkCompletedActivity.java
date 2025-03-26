package elink.suezShimla.water.crm.NoConsumerComplaint.NCTodaysCompleteWork;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.NoConsumerComplaint.NCTodaysCompleteWork.Adapter.NCTodayWorkCompleteAdapter;
import elink.suezShimla.water.crm.NoConsumerComplaint.NCTodaysCompleteWork.Model.NCTodayWorkCompletionResponseModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;

public class NCTodaysWorkCompletedActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView todayWorkRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NCTodayWorkCompleteAdapter  ncTodayWorkCompleteAdapter;
    private List<NCTodayWorkCompletionResponseModel> nctodayCompleteWorkModels = new ArrayList<>();
    private Context mCon;
    private LinearLayout errorLinear;
    private MaterialDialog progress;

    private String jsonResponse = "", imei, mac, employeeID;
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    private RealmOperations realmOperations;

    private String subZoneStr, consumerNoStr, nameStr, addressStr, phoneStr, tariffStr, complaintTypeStr, complaintSubTypeStr, workCompletionDateStr,
            attendedByStr, remarkStr, complaintNoStr, allocationDateStr, complaintDateStr, statusStr, allocatedEmpStr,vipName,alloc_to;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nctodays_work_completed);
        mCon = this;

        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();
        realmOperations = new RealmOperations(mCon);

        errorLinear = findViewById(R.id.errorLinear);
        todayWorkRecyclerView = findViewById(R.id.todayWorkRecyclerView);
        todayWorkRecyclerView.setHasFixedSize(true);
        todayWorkRecyclerView.setLayoutManager(new LinearLayoutManager(mCon));

        ncTodayWorkCompleteAdapter = new NCTodayWorkCompleteAdapter(mCon);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark, android.R.color.holo_red_dark);

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
        ncTodayWorkCompleteAdapter.notifyDataSetChanged();
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
                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.NCon_TodaysWorkCompBySE, params, paramNames);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                nctodayCompleteWorkModels = new ArrayList<>();
                NCTodayWorkCompletionResponseModel[] enums = gson.fromJson(jsonResponse, NCTodayWorkCompletionResponseModel[].class);
                if (enums != null && enums.length > 0) {
                    for (NCTodayWorkCompletionResponseModel model : enums) {
                        subZoneStr = model.getSUBZONE();
                        complaintTypeStr = model.getCOMPTYPE();
                        complaintSubTypeStr = model.getCOMPSUBTYPE();
                        workCompletionDateStr = model.getCOM_WORKCOMPLETIONDATE();
                        attendedByStr = model.getATTENDEDBY();
                        remarkStr = model.getCOM_REMARKS();
                        complaintNoStr = model.getCOMNO();
                        allocationDateStr = model.getCOM_ALLOCATIONDATE();
                        complaintDateStr = model.getCOM_COMPDATE();
                        alloc_to = model.getFLM_ALOCTO();

                        NCTodayWorkCompletionResponseModel data = new NCTodayWorkCompletionResponseModel(subZoneStr, complaintTypeStr, complaintSubTypeStr, workCompletionDateStr, attendedByStr, remarkStr, complaintNoStr, allocationDateStr, complaintDateStr, alloc_to);
                        nctodayCompleteWorkModels.add(data);
                    }
                    errorLinear.setVisibility(View.GONE);
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    ncTodayWorkCompleteAdapter = new NCTodayWorkCompleteAdapter(mCon);
                    ncTodayWorkCompleteAdapter.addList(nctodayCompleteWorkModels);
                    todayWorkRecyclerView.setAdapter(ncTodayWorkCompleteAdapter);
                    ncTodayWorkCompleteAdapter.notifyDataSetChanged();

                } else {
                    errorLinear.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setVisibility(View.GONE);
                    Toast.makeText(mCon, R.string.no_complaints, Toast.LENGTH_SHORT).show();
                }

                progress.dismiss();
            }catch (Exception e){
                String error = e.toString();
                ErrorClass.errorData(mCon, "NCTodaysWorkCompletedActivity", "NC Todays WorkCompleted Card", error);
            }
        }
    }
}
