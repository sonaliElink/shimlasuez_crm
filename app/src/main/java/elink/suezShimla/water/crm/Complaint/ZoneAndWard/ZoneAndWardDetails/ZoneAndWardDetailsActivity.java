package elink.suezShimla.water.crm.Complaint.ZoneAndWard.ZoneAndWardDetails;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.Complaint.ZoneAndWard.ZoneAndWardDetails.Adapter.ZoneAndWardDetailsAdapter;
import elink.suezShimla.water.crm.Complaint.ZoneAndWard.ZoneAndWardDetails.Model.ZoneAndWardDetailsModel;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;

public class ZoneAndWardDetailsActivity extends AppCompatActivity {

    private Context mCon;
    private String ward, ward_id, zone_id;

    private RecyclerView complaintTypeRecycler;
    private ZoneAndWardDetailsAdapter zoneAndWardDetailsAdapter;
    private List<ZoneAndWardDetailsModel> zoneAndWardDetailsModelList;
    private LinearLayout errorLinear;

    private String jsonResponse = "", FromDateFormat, toDateFormat;
    private MaterialDialog progress;
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_zone_and_ward_details);

        mCon = this;

        Intent startingIntent = getIntent();
        ward = startingIntent.getStringExtra("GETWARD");
        ward_id = startingIntent.getStringExtra("GETWARD_ID");
        zone_id = startingIntent.getStringExtra("GETZONE_ID");

        //Log.d("check", zone_id + "-" + ward_id);

        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();
        gson = new Gson();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(ward);
        }

        complaintTypeRecycler = findViewById(R.id.complaintTypeRecycler);
        errorLinear = findViewById(R.id.errorLinear);
        zoneAndWardDetailsAdapter = new ZoneAndWardDetailsAdapter(mCon);

        complaintTypeRecycler.setHasFixedSize(true);
        complaintTypeRecycler.setLayoutManager(new LinearLayoutManager(mCon));

        /*@SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String dateFormat = df.format(Calendar.getInstance().getTime());*/

        try {
            Date dateInstance = new Date();

            Calendar cal = Calendar.getInstance();
            cal.setTime(dateInstance);
            cal.add(Calendar.DATE, -1);
            Date dateBefore30Days = cal.getTime();

            @SuppressLint("SimpleDateFormat") SimpleDateFormat fromDf = new SimpleDateFormat("dd-MMM-yyyy");
            FromDateFormat = fromDf.format(dateBefore30Days);

            @SuppressLint("SimpleDateFormat") SimpleDateFormat toDf = new SimpleDateFormat("dd-MMM-yyyy");
            toDateFormat = toDf.format(Calendar.getInstance().getTime());
        } catch (Exception e) {
            Log.d("check", e.getMessage());
        }

        String params[] = new String[10];

        params[0] = FromDateFormat;
        params[1] = toDateFormat;
        params[2] = zone_id;
        params[3] = zone_id;
        params[4] = ward_id;
        params[5] = "-1";
        params[6] = "0";
        params[7] = "0";
        params[8] = "";
        params[9] = "-1";

        Log.d("check", "" + Arrays.toString(params));

        if (connection.isConnectingToInternet()) {
            ComplaintDashboard complaintDashboard = new ComplaintDashboard();
            complaintDashboard.execute(params);
            progress = new MaterialDialog.Builder(mCon)
                    .content(R.string.loading)
                    .progress(true, 0)
                    .autoDismiss(false)
                    .canceledOnTouchOutside(false)
                    .widgetColorRes(R.color.colorPrimary)
                    .show();

            //holder.colorImageView.setBackgroundColor(mCon.getResources().getColor(R.color.green_700));
        } else {
            Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class ComplaintDashboard extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {

            try {
                String paraName[] = new String[10];

                paraName[0] = "txt_From";
                paraName[1] = "txt_To";
                paraName[2] = "W_Zone";
                paraName[3] = "W_BU";
                paraName[4] = "W_PC";
                paraName[5] = "W_CompRes";
                paraName[6] = "drp_SourceType";
                paraName[7] = "W_Category";
                paraName[8] = "W_Qstring";
                paraName[9] = "drp_Compsubtype";

                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.ComplaintDashboard, params, paraName);


                //  Log.d("check", "" + jsonResponse);

            } catch (Exception e) {
                Log.e("check", e.getMessage());
            }

            return null;
        }

    @Override
        protected void onPostExecute(Void aVoid) {
            try {
                ZoneAndWardDetailsModel[] enums = gson.fromJson(jsonResponse, ZoneAndWardDetailsModel[].class);
                if (enums.length > 0) {
                    zoneAndWardDetailsModelList = Arrays.asList(enums);
                    zoneAndWardDetailsAdapter.addList(zoneAndWardDetailsModelList);
                    complaintTypeRecycler.setAdapter(zoneAndWardDetailsAdapter);
                    errorLinear.setVisibility(View.GONE);
                    complaintTypeRecycler.setVisibility(View.VISIBLE);
                } else {
                    errorLinear.setVisibility(View.VISIBLE);
                    complaintTypeRecycler.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                finish();
                Toast.makeText(mCon, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();

                String error = e.toString();
                ErrorClass.errorData(mCon, "ZoneAndWardDetailsActivity", "ComplaintDashboard", error);
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
    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(new Intent(mCon, SplashScreen.class));
    }
}
