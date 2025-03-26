package elink.suezShimla.water.crm.ConnectionManagement.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.ConnectionManagement.model.NSCMasterModel;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;
import elink.suezShimla.water.crm.constant.AppConstant;

public class SiteVisitDashboardActivity extends AppCompatActivity implements View.OnClickListener {
    AppCompatRadioButton rbLeft,rbRight,rbCenter;
    LinearLayout ll_nc_schedule_visit,ll_nc_visit_completed,ll_nc_visit_pending;
    Intent intent;
    TextView tvNSCVisitSechdule,tvNSCVisitCompleted,tvNSCVisitPending;
    String empMasterDataCode="",sessionid="",Tag="1",jsonMasterResponse="",visitPendingStr="",visitCompleted="",scheduleVisit="";
    String visitPendingMonthStr="",visitMonthCompleted="",visitPendingYearStr="",visitYearCompleted="";
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    private MaterialDialog progress;
    boolean today=true,month=false,year=false;
    String formattedDate="",startDateStr="",endDateStr="",startYearDateStr="",endYearDateStr="",fromDate="",toDate="";

    private KeyGenerator keyGenerator;
    private SecretKey secretKey;
    private byte[] IV = new byte[16];
    private SecureRandom random;
    AesAlgorithm aes;
    private Context mCon;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_visit_dashboard);
      //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mCon=this;
        try {
            // AES Algorithm for encryption / decryption

            keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            secretKey = keyGenerator.generateKey();

            random = new SecureRandom();
            random.nextBytes(IV);

            aes=new AesAlgorithm();

        } catch (Exception e) {
            e.printStackTrace();
        }

        init();


    }

    private void init() {

        gson = new Gson();
        connection = new ConnectionDetector(this);
        invServices = new Invoke();


        rbLeft =findViewById(R.id.rbLeft);
        rbRight= findViewById(R.id.rbRight);
        rbCenter= findViewById(R.id.rbCenter);

        ll_nc_schedule_visit= findViewById(R.id.ll_nc_schedule_visit);
        ll_nc_visit_completed= findViewById(R.id.ll_nc_visit_completed);
        ll_nc_visit_pending= findViewById(R.id.ll_nc_visit_pending);

        tvNSCVisitPending= findViewById(R.id.tvNSCVisitPending);
        tvNSCVisitCompleted= findViewById(R.id.tvNSCVisitCompleted);
        tvNSCVisitSechdule= findViewById(R.id.tvNSCVisitSechdule);

        ll_nc_schedule_visit.setOnClickListener(this);
      //  ll_nc_visit_completed.setOnClickListener(this);
        ll_nc_visit_pending.setOnClickListener(this);
        empMasterDataCode = UtilitySharedPreferences.getPrefs(this, AppConstant.EMPCODE);
        try {
            empMasterDataCode = aes.decrypt( empMasterDataCode);
            sessionid=new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon,AppConstant.SID));

        } catch (Exception e) {
            e.printStackTrace();
        }

        getMasterDataCount();

        currentDate();

    }

    private void currentDate() {
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date monthFirstDay = calendar.getTime();
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date monthLastDay = calendar.getTime();
        // Create first day of year
        Calendar firstDayOfCurrentYear = Calendar.getInstance();
        firstDayOfCurrentYear.set(Calendar.DATE, 1);
        firstDayOfCurrentYear.set(Calendar.MONTH, 0);
        // Create last day of year

        Calendar lastDayOfCurrentYear = Calendar.getInstance();
        lastDayOfCurrentYear.set(Calendar.DATE, 31);
        lastDayOfCurrentYear.set(Calendar.MONTH, 11);
        Calendar c = Calendar.getInstance();



        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");

       // SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
         formattedDate = df.format(c.getTime());
         startDateStr = df.format(monthFirstDay);
         endDateStr = df.format(monthLastDay);
         startYearDateStr=df.format(firstDayOfCurrentYear.getTime());
         endYearDateStr=df.format(lastDayOfCurrentYear.getTime());




    /*    System.out.println(df.format(firstDayOfCurrentYear.getTime()));


        System.out.println(df.format(lastDayOfCurrentYear.getTime()));*/


      //  Log.e("DateFirstLast",startDateStr+" "+endDateStr+" "+formattedDate);
      //  Toast.makeText(this, ""+formattedDate+" "+startDateStr+" "+endDateStr+" "+startYearDateStr+" "+endYearDateStr, Toast.LENGTH_SHORT).show();
    }

    private void getMasterDataCount() {
        String[] params = new String[3];

        params[0] = empMasterDataCode;
        params[1] = Tag;
        params[2]=sessionid;

        if (connection.isConnectingToInternet()) {
            MasterCount masterCount = new MasterCount();
            masterCount.execute(params);
        } else {
            Toast.makeText(this, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }

    }

    private class MasterCount extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            progress = new MaterialDialog.Builder(SiteVisitDashboardActivity.this)
                    .content(R.string.please_wait)
                    .progress(true, 0)
                    .cancelable(false)
                    .canceledOnTouchOutside(false)
                    .widgetColorRes(R.color.colorPrimary)
                    .show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {

                String username=null,password=null;
                username=new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon,AppConstant.EMPCODE));
                password=new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon,AppConstant.PASSWORD));

                String paraName[] = new String[3];
                paraName[0] = "LoginUser";
                paraName[1] = "Tag";
                paraName[2] = "SessionToken";


                jsonMasterResponse = invServices.getOtherData(Constants.URL, Constants.NameSpace, Constants.NSCDASHBOARD,username,password, params, paraName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                NSCMasterModel[] enums = gson.fromJson(jsonMasterResponse, NSCMasterModel[].class);

                if (enums.length > 0) {

                    visitPendingStr = enums[0].getPENDING_TODAY();
                    visitCompleted =enums[0].getCOMPLETED_TODAY();

                    visitPendingMonthStr = enums[0].getPENDING_MONTH();
                    visitMonthCompleted =enums[0].getCOMPLETED_MONTH();

                    visitPendingYearStr = enums[0].getPENDING_YEAR();
                    visitYearCompleted =enums[0].getCOMPLETED_YEAR();

                    tvNSCVisitPending.setText(String.valueOf(visitPendingStr));
                    tvNSCVisitCompleted.setText(String.valueOf(visitCompleted));




                }
                progress.dismiss();
            } catch (Exception e) {
                Log.d("check", e.getMessage());
                //  Toast.makeText(mCon, e.getMessage(), Toast.LENGTH_SHORT).show();
                // error = e.toString();
                String error = e.toString();
                ErrorClass.errorData(getApplication(), "LoginActivity", "Click Login Button", error);
            }
            progress.dismiss();
        }
    }


    public void onRadioButtonClick(View v){
        boolean isSelected = ((AppCompatRadioButton)v).isChecked();
        switch (v.getId()){
            case R.id.rbLeft:
                if(isSelected){
                    rbLeft.setTextColor(getResources().getColor(R.color.white));
                    rbRight.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    rbCenter.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                   // getMasterDataCount();
                    today=true;
                    year=false;
                    month=false;
                    tvNSCVisitPending.setText(String.valueOf(visitPendingStr));
                    tvNSCVisitCompleted.setText(String.valueOf(visitCompleted));

                }
                break;
             case R.id.rbCenter:
                if(isSelected){
                    rbLeft.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    rbRight.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    rbCenter.setTextColor(getResources().getColor(R.color.white));
                   // getMasterDataCount();
                    month=true;
                    today=false;
                    year=false;
                    tvNSCVisitPending.setText(String.valueOf(visitPendingMonthStr));
                    tvNSCVisitCompleted.setText(String.valueOf(visitMonthCompleted));
                   // month=false;

                }
                break;
            case R.id.rbRight:
                if(isSelected){
                    rbRight.setTextColor(getResources().getColor(R.color.white));
                    rbLeft.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    rbCenter.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                  //  getMasterDataCount();
                    year=true;
                    today=false;
                    month=false;
                    tvNSCVisitPending.setText(String.valueOf(visitPendingYearStr));
                    tvNSCVisitCompleted.setText(String.valueOf(visitYearCompleted));
                   // year=false;


                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_nc_schedule_visit:
                if(today){
                    fromDate= formattedDate;
                    toDate= formattedDate;
                }else if(month){
                    fromDate= startDateStr;
                    toDate= endDateStr;
                }else if(year){
                    fromDate= startYearDateStr;
                    toDate= endYearDateStr;
                }

                intent = new Intent(this, SiteVisitListActivity.class);
                intent.putExtra("fromDate",fromDate);
                intent.putExtra("toDate",toDate);

                startActivity(intent);
                finish();
                break;


            case R.id.ll_nc_visit_pending:
                 if(today){
                     fromDate= formattedDate;
                     toDate= formattedDate;
                 }else if(month){
                     fromDate= startDateStr;
                     toDate= endDateStr;
                 }else if(year){
                     fromDate= startYearDateStr;
                     toDate= endYearDateStr;
                 }

                intent = new Intent(this, SiteVisitListActivity.class);
                intent.putExtra("fromDate",fromDate);
                intent.putExtra("toDate",toDate);

                startActivity(intent);
                finish();
                break;

            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        App myApp = (App) this.getApplication();
        if (myApp.wasInBackground) {
            finish();
            startActivity(new Intent(this, SplashScreen.class));
        }

        myApp.stopActivityTransitionTimer();
    }
/*
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
    }*/
}
