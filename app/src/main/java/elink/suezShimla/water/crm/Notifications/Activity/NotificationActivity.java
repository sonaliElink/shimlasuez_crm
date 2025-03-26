package elink.suezShimla.water.crm.Notifications.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.MainActivity;
import elink.suezShimla.water.crm.Notifications.Model.Notification_List;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;
import elink.suezShimla.water.crm.constant.AppConstant;


public class NotificationActivity extends AppCompatActivity {

    static int n_count;
    ProgressDialog pDialog;
    Gson gson = new Gson();
    String[] N_DATE = null, N_TITLE = null, N_MSG = null;
    String empCode="",imei="",sessionid="";
    ScrollView scroll;
    TextView tvserviceNo;
    Bundle bundle;
    private ConnectionDetector Connection;
    private Invoke invServices;
    private String URL = "", NAMESPACE = "", jsonResponse = "",mac="",employeeID="",deviceAuthorization="",appIsLogged="";
    private LinearLayout notificationList, No_notification;

    private KeyGenerator keyGenerator;
    private SecretKey secretKey;
    private byte[] IV = new byte[16];
    private SecureRandom random;
    AesAlgorithm aes;    private Context mCon;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        mCon=this;
        setContentView(R.layout.activity_notification);

        /*getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left_arrow);
        getSupportActionBar().setTitle(R.string.notification);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));*/
        notificationList = (LinearLayout) findViewById(R.id.list);
        No_notification = (LinearLayout) findViewById(R.id.no_notification);

        ImageView imgBack=findViewById(R.id.imgBack);

        scroll = (ScrollView) findViewById(R.id.scroll);
        Connection = new ConnectionDetector(getApplicationContext());
        invServices = new Invoke();
        pDialog = new ProgressDialog(NotificationActivity.this);
        pDialog.setMessage(getString(R.string.loading));
        pDialog.setCancelable(false);
     /*   URL = getResources().getString(R.string.URL);
        NAMESPACE = getResources().getString(R.string.NAMESPACE);*/
     /*   bundle = getIntent().getExtras();
        ServiceNo = bundle.getString("ServiceNo");*/

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

        imei =  UtilitySharedPreferences.getPrefs(this, AppConstant.IMEI);
        empCode = UtilitySharedPreferences.getPrefs(this, AppConstant.EMPCODE);
        try {
            empCode = aes.decrypt( empCode);
            sessionid=new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon,AppConstant.SID));

        } catch (Exception e) {
            e.printStackTrace();
        }

        deviceAuthorization = UtilitySharedPreferences.getPrefs(this, AppConstant.DEVICEAUTHORIZATION);
//        deviceAuthorization = aes.decrypt( deviceAuthorization.getBytes(), secretKey, IV);

        appIsLogged = UtilitySharedPreferences.getPrefs(this, AppConstant.APP_ISLOGGED);
       /* if (deviceAuthorization.equalsIgnoreCase("0")) {
            deviceAuthorizedDialog();

        }else {
            if(appIsLogged.equalsIgnoreCase("0")){
                adminLogout();
            }
        }*/

        Intent startingIntent = getIntent();
        imei = startingIntent.getStringExtra("IMEINumber");
        mac = startingIntent.getStringExtra("MACAddress");
        employeeID = startingIntent.getStringExtra("employeeID");


        String params[] = new String[3];
        params[0] = empCode;
        params[1] = imei;
        params[2] = sessionid;
        if (Connection.isConnectingToInternet()) {
            GetNotification notification = new GetNotification();
            notification.execute(params);
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();

        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent iLogin = new Intent(NotificationActivity.this, MainActivity.class);
        iLogin.putExtra("IMEINumber", imei);

        iLogin.putExtra("MACAddress", mac);
        iLogin.putExtra("employeeID", employeeID);

        startActivity(iLogin);
        finish();
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public class GetNotification extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            try {

                String username=null,password=null;
                username=new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon,AppConstant.EMPCODE));
                password=new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon,AppConstant.PASSWORD));

                String paraName[] = new String[3];
                paraName[0] = "EmpCode";
                paraName[1] = "IMEI";
                paraName[2] = "SessionToken";

                // responseJSON = invServices.getDataWITHParams(URL, NAMESPACE, "getNotification", params, paraName);
                jsonResponse = invServices.getOtherData(Constants.URL, Constants.NameSpace, "getNotification",username,password, params, paraName);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            showpDialog();
        }

        @Override
        protected void onPostExecute(Void result) {

            try {
                Notification_List[] enums = gson.fromJson(jsonResponse, Notification_List[].class);
                notificationList.removeAllViews();
                if (enums.length > 0) {
                    n_count = enums.length;
                    N_DATE = new String[enums.length];
                    N_TITLE = new String[enums.length];
                    N_MSG = new String[enums.length];
                    LayoutInflater layoutInflater = LayoutInflater
                            .from(getApplicationContext());
                    View grid;
                    for (int i = 0; i <= N_DATE.length - 1; i++) {

                        if (enums[i].getN_DATE() == null) {
                            N_DATE[i] = "N.A.";
                        } else {
                            N_DATE[i] = enums[i].getN_DATE();
                        }
                        if (enums[i].getN_MSG() == null) {
                            N_MSG[i] = "N.A.";
                        } else {
                            N_MSG[i] = enums[i].getN_MSG();
                        }
                        if (enums[i].getN_TITLE() == null) {
                            N_TITLE[i] = "N.A.";
                        } else {
                            N_TITLE[i] = enums[i].getN_TITLE();
                        }
                        grid = layoutInflater.inflate(R.layout.complaint_list_item_notification, notificationList, false);

                        final TextView Date = (TextView) grid.findViewById(R.id.tvHistDate);
                        final TextView Message = (TextView) grid.findViewById(R.id.tv2);
                        final TextView Title = (TextView) grid.findViewById(R.id.tv1);
                        final LinearLayout img = (LinearLayout) grid.findViewById(R.id.emoji);
                        final TextView tv3 = (TextView) grid.findViewById(R.id.tv3);
                        final TextView tv4 = (TextView) grid.findViewById(R.id.tv4);
                        LinearLayout llComplaints = (LinearLayout) grid.findViewById(R.id.llComplaints);
                        llComplaints.setWeightSum(6);
                        tv3.setVisibility(View.GONE);
                        tv4.setVisibility(View.GONE);
                        img.setVisibility(View.GONE);
                        Title.setTypeface(null, Typeface.BOLD);
                        Date.setText(N_DATE[i].toString());
                        Message.setText(N_MSG[i].toString());
                        Title.setText(N_TITLE[i].toString());
                        notificationList.addView(grid);
                    }
                } else {
                    scroll.setVisibility(View.GONE);
                    No_notification.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {

            }
            hidepDialog();
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
