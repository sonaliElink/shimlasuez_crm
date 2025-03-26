package elink.suezShimla.water.crm.ConnectionManagement.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.ConnectionManagement.adapter.SiteVisitListAdapter;
import elink.suezShimla.water.crm.ConnectionManagement.model.SiteVisitModel;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.MainActivity;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;
import elink.suezShimla.water.crm.constant.AppConstant;

public class SiteVisitListActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, SiteVisitListAdapter.RowClick {

    private Context mCon;

    TextView tv_toolbar;
    ImageButton siteVisitListFilter;
    private SearchView searchView;

    private SiteVisitListAdapter siteVisitListAdapter;

    String currentDateTimeStr="",fromDate="",toDate="",empCode="",jsonSiteVisitResponse="";

    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;

    private MaterialDialog progressSiteVisit;
    private List<SiteVisitModel> siteVisitModelList;
    private LinearLayout errorLinearr;
    RecyclerView siteVisitListRecyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    ImageView iv_back;

    String  requestNoStr="",applicantNameStr="",amApplicationDateStr="",addressStr="",phoneNumberStr="",statusStr="",statusDateStr="",
            zonStr="",wardStr="",appointmentDateStr="",governmentTypeStr="",connectionTypeStr="",connectionSizeStr="";

    private KeyGenerator keyGenerator;
    private SecretKey secretKey;
    private byte[] IV = new byte[16];
    private SecureRandom random;
    AesAlgorithm aes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_visit_list);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

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

        try {


            searchView = findViewById(R.id.searchView);
            searchView.clearFocus();
/*
    searchView.setMaxWidth(Integer.MAX_VALUE);
    searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
*/

        }catch (Exception e){
            e.printStackTrace();
        }


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                // hideSoftKeyboard();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                filter(query);
                return false;
            }
        });


    }

    private void init() {

        mCon = this;
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        currentDateTimeStr = simpleDateFormat.format(new Date());

         fromDate= getIntent().getStringExtra("fromDate");
         toDate= getIntent().getStringExtra("toDate");
      //  Toast.makeText(mCon, ""+fromDate, Toast.LENGTH_SHORT).show();

        gson = new Gson();
        connection = new ConnectionDetector(this);
        invServices = new Invoke();

        empCode = UtilitySharedPreferences.getPrefs(this, AppConstant.EMPCODE);
        try {
            empCode = aes.decrypt( empCode);
        } catch (Exception e) {
            e.printStackTrace();
        }

        tv_toolbar= findViewById(R.id.tv_toolbar);
        iv_back= findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_toolbar.setText(getResources().getString(R.string.site_visit_list));

        errorLinearr = findViewById(R.id.errorLinearr);
        siteVisitListRecyclerView = findViewById(R.id.siteVisitListRecyclerView);
        siteVisitListRecyclerView.setLayoutManager(new LinearLayoutManager(mCon));

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark, android.R.color.holo_orange_dark, android.R.color.holo_red_dark);


        siteVisitListFilter= findViewById(R.id.siteVisitListFilter);
        siteVisitListFilter.setOnClickListener(this);

        getFilterSiteVisitWithOutData();

    }

    private void getFilterSiteVisitWithOutData() {

        String params[] = new String[10];

        params[0] = fromDate;   // test-data (02-Apr-2019)
        params[1] = toDate;  // test-data (06-May-2019)
        params[2] = "101";
        params[3] = "0";
        params[4] = "A";
        params[5] = "0";
        params[6] = "0";
        params[7] = "";
        params[8] = "";
        params[9] = empCode;

        if (connection.isConnectingToInternet()) {
            SiteVisitList siteVisitList = new SiteVisitList();
            siteVisitList.execute(params);
        } else {
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:{
                onBackPressed();
            }
            break;
            case R.id.siteVisitListFilter:{
                Intent intent = new Intent(this,SiteVisitListActivityDetails.class);
                startActivity(intent);
            }
            break;
            default:
        }

    }


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Tag", "3");
        startActivity(intent);
        finish();

       // super.onBackPressed();
    }


    @Override
    public void onRefresh() {
        getFilterSiteVisitWithOutData();

        swipeRefreshLayout.setRefreshing(false);
    }




    @SuppressLint("StaticFieldLeak")
    private class SiteVisitList extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            progressSiteVisit = new MaterialDialog.Builder(SiteVisitListActivity.this)
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
                String paraNames[] = new String[10];
                paraNames[0] = "W_FromDate";
                paraNames[1] = "W_ToDate";
                paraNames[2] = "W_ProcessCode";
                paraNames[3] = "W_APPType";
                paraNames[4] = "W_Sourcetype";
                paraNames[5] = "W_ZoneId";
                paraNames[6] = "W_CustomerType";
                paraNames[7] = "W_ApplicationNo";
                paraNames[8] = "W_UserCode";
                paraNames[9] = "W_AllocatedUser";



                jsonSiteVisitResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.GetApplicationData, params, paraNames);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
               siteVisitModelList = new ArrayList<>();
                SiteVisitModel[] enums = gson.fromJson(jsonSiteVisitResponse, SiteVisitModel[].class);
              String borewell,operational,applicationtype,noofappln,addapplicationtype,newadd;
                if (enums != null && enums.length > 0) {
                    for (SiteVisitModel model : enums) {


                        requestNoStr = model.getREQUEST_NO();
                        applicantNameStr = model.getNAME();
                        amApplicationDateStr = model.getAM_AAPP_DATE();
                      addapplicationtype = model.getAM_AAPP_NO_TYPE();
//                        DateFormat inputFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
                        borewell = model.getAM_APP_BOREWELL();
                        operational = model.getAM_APP_IS_OPERATIONAL();
                       applicationtype = model.getAM_APP_APPLI_TYPE();
                       noofappln = model.getAM_APP_NOOF_APPS();
                         newadd= model.getNEWADDRESS();
                        addressStr = model.getADDRESS();
                        phoneNumberStr = model.getAM_APP_PHONE();
                        statusStr = model.getSTATUS();
                        statusDateStr = model.getAM_APP_PROCDT();
                        zonStr = model.getZONENAME();
                        //workAllocationDateTimeStr = current date
                        wardStr = model.getSUBZONE();
                        appointmentDateStr = model.getAM_APP_APPOINT_DATE();
                        governmentTypeStr = model.getAM_APP_GOVT_PROPERTY();
                        connectionTypeStr = model.getAM_APP_CONNECTION_TYPE();
                        connectionSizeStr = model.getAM_APP_CONNECTION_SIZE();






                   /*     SiteVisitModel data = new SiteVisitModel(requestNoStr, applicantNameStr, amApplicationDateStr, addressStr, phoneNumberStr, statusStr,
                                statusDateStr, zonStr, wardStr, appointmentDateStr, governmentTypeStr, connectionTypeStr, connectionSizeStr);
                        siteVisitModelList.add(data);*/

                        SiteVisitModel data = new SiteVisitModel(requestNoStr,applicantNameStr,model.getAM_AAPP_NO_TYPE(),model.getAM_APP_SERVNO(), model.getAM_APP_ZONE(),
                                model.getAM_APP_CIRCLE(),model.getAM_APP_DIVISION(),model.getAM_APP_SUB_DIVISION(),model.getAM_APP_SECTION(),model.getAM_APP_BU(),model.getAM_APP_PC(),model.getAM_APP_AREA(),
                                model.getAM_APP_MRC(),model.getAM_APP_LOT(),model.getAM_APP_SEQUENCE(),model.getAM_APP_NEARBYSER(),model.getAM_APP_NMTITLE(),model.getAM_APP_FNAME(),model.getAM_APP_MNAME(),
                                model.getAM_APP_SURNAME(),amApplicationDateStr,model.getAM_APP_PHONEM(),model.getAM_APP_EMAIL(),model.getAM_APP_ADDRESS1(),model.getAM_APP_ADDRESS2(),
                                model.getAM_APP_ADDRESS3(),model.getAM_APP_ADDRESS4(),model.getAM_APP_PINCODE(),addressStr, applicationtype,noofappln,
                                newadd,borewell,operational,model.getAM_REMARKS(),model.getAM_APP_ISMSCDCL_EMPLOYEE(),
                                model.getAM_APP_ACCEPT_DATE(),appointmentDateStr,statusDateStr,model.getPROCESSTAG(),model.getPROCESSCODE(),statusStr,model.getAM_APP_SOURCE_TYPE(),
                                model.getSOURCE(),model.getMETERREPLACE(),model.getREASON(),model.getREASONNAME(),model.getAM_APP_REJECT_DATE(),model.getAM_APP_ORESCD(),model.getAREANAME(),
                                zonStr,wardStr,model.getAM_APP_CATEGORY(),model.getPURPOSE_NAME(),model.getCONNECTION_SIZE(),model.getAM_APP_PURPOSE(),model.getAM_APP_TRNO(),
                                model.getAM_APP_POLENO(),model.getAM_APP_ASD(),model.getAM_APP_SD(),model.getAM_APP_SCC(),model.getAM_APP_REGFEE(),model.getTARIFF_NAME(),model.getAM_APP_TARIFF(),
                                model.getAM_APP_AREA_TYPE(),model.getAM_APP_TARIFF_TYPE_CD(),model.getAM_APP_METER_INDICATION(),model.getAM_APP_RCPT_DATE(),model.getAM_APP_TOTALAMT(),
                                model.getAM_APP_PDWITHIN6(), model.getAM_APP_METERAVLB(),model.getAM_APP_PDRECON_TYPE(),model.getMONTHYR(),model.getAM_APP_PREMISE_NO(),model.getAM_APP_TR_DISTANCE(),
                                model.getAM_APP_AREAPREMISE(),connectionSizeStr,model.getAM_APP_STDCODE(),phoneNumberStr,model.getPROCESSCODE(),model.getAM_APP_METER_REQUIRED(),
                                model.getAM_APP_BOARING_CHARGES(),model.getAM_APP_SECUTIRY_CHARGES(),model.getAM_APP_ADDITIONAL_CHARGES(),model.getAM_APP_WATER_MACADAM_CHARGES(),model.getAM_APP_REINFORCEMENT_CHARGES(),
                                model.getAM_APP_VISITING_CHARGES(),model.getAM_APP_BITUMINOUSTAR_CHARGES(),model.getAM_APP_METER_COST(),model.getAM_APP_REGFEE1(),
                                model.getAM_APP_ASD1(),model.getAM_APP_SD1(),model.getAM_APP_DEMAND_AMT(),model.getAM_APP_METER_REQUIRED_FUTILITY(),connectionTypeStr,
                                model.getAPTM_NAME(),model.getAM_APP_PREMTYPE(),governmentTypeStr,model.getAM_APP_EXISTING_CONSUMER_TAG(),model.getAM_APP_EXISTING_CONSUMERNO(),
                                model.getAM_APP_OTHERUSAGE(),model.getAM_APP_FAMILY_MEMBER(),model.getAPPLICATION(),model.getVCM_CATNAME(),model.getFATHERNAME(),model.getAM_APP_NO_OF_FLOORS(),
                                model.getAM_APP_NO_OF_DWELLING(),model.getAM_APP_NO_OF_ROOMS(),model.getAM_APP_ISAUTH_CONNECTION(),model.getAM_APP_CIVIL_CONS_CCMC_WATER(),
                                model.getAM_APP_NO_OF_DWELLING_UNITS(),model.getAM_APP_ISOCCUPIER_SECURITY(),model.getAM_APP_ISMSCDCL_EMPLOYEE_ID(),model.getAM_APP_ISTECH_FESIBILITY(),
                                model.getAM_APP_SPIPELINE(),model.getAM_APP_LENGTH_MEASURE(),model.getAM_APP_ROADCUTMTR(),model.getAM_APP_ROADTYPE(),model.getAM_APP_ROADOWNER(),
                                model.getAM_APP_SUBSTATION(),model.getCATEGORY_NAME(),model.getPRM_NAME(),model.getAM_APP_S_GIS_ID(),model.getPCM_PC_NAME(),model.getLM_LOT_NAME(),
                                model.getTRM_NAME(),model.getPM_NAME(),model.getSBM_NAME(),model.getAM_APP_NEAR_LCONS(),model.getOWNERSHIP(),model.getUPLOADEDDOCS(),model.getLATI(),model.getLONGI(),model.getAM_APP_ISWATER_AVAILINDP(),
                                model.getAM_APP_DISTRIBUTIONID(),model.getAM_APP_SRIGHTCONSUMER(),model.getAM_APP_SLEFTCONSUMER(),model.getAM_APP_ISROADCUTTING_REQD(),
                                model.getAM_APP_ISCONSTRUCTION_COMP(),model.getAM_APP_ISEXISTINGCONN(),model.getAM_APP_EXISTINGCONN(),model.getAM_APP_ISDWELLING_HASCONN(),model.getAM_APP_STORAGECAPACITYTYPE(),model.getAM_APP_STORAGECAPACITY(),
                                model.getAM_APP_ISINTERNAL_NETWORK(),model.getAM_APP_ISDISPOSAL_OFWATER(),model.getAM_APP_ISRAINWATERHARVEST(),model.getAM_APP_METERLOCATION(),model.getAM_APP_METERSIZE(),model.getAM_APP_COSOWO_NAME(),model.getPass(), model.getPass1(), model.getAM_APP_LOCALITY(),
                                model.getMTRM_SERIAL_NO(),model.getMTRM_CURRENT_READING(),model.getNOOFVISITS());
                        Log.e("cdata","ID 2:"+model.getAM_APP_ISMSCDCL_EMPLOYEE_ID());

                        siteVisitModelList.add(data);


                    }
                    //sheetBehavior.cancel();
                    errorLinearr.setVisibility(View.GONE);
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    siteVisitListRecyclerView.setVisibility(View.VISIBLE);
                    siteVisitListAdapter = new SiteVisitListAdapter(mCon,siteVisitModelList,SiteVisitListActivity.this);

                  //  siteVisitListAdapter.addList(siteVisitModelList);
                    siteVisitListRecyclerView.setAdapter(siteVisitListAdapter);
                    siteVisitListAdapter.notifyDataSetChanged();


                } else {
                    errorLinearr.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    siteVisitListRecyclerView.setVisibility(View.GONE);

                    Toast.makeText(mCon, R.string.no_data_found, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d("check", e.getMessage());
                Toast.makeText(mCon, e.getMessage(), Toast.LENGTH_SHORT).show();
                String error = e.toString();
                ErrorClass.errorData(mCon, "Complaint_GetWorkAllocationData", "Show or Swipe to refresh", error);
            }
            progressSiteVisit.dismiss();
        }
    }

    @Override
    public void rowClicked(int id, SiteVisitModel siteVisitModel) {

       Intent i = new Intent(SiteVisitListActivity.this,SiteVisitListVerficationActivity.class);
       i.putExtra("siteVisitEntity",siteVisitModel);
       startActivity(i);
       finish();
    }

    public void filter(String input) {
        List<SiteVisitModel> list = new ArrayList<>();

        for (SiteVisitModel item : siteVisitModelList) {
            if (item.getREQUEST_NO().toLowerCase().contains(input.toLowerCase()) || item.getNAME().toLowerCase().contains(input.toLowerCase())) {
                list.add(item);
            }
        }
        siteVisitListAdapter.filterList(list);
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

/*    @Override
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
  /*  Intent i = new Intent(SiteVisitListActivity.this,SiteVisitListActivityDetails.class);
       i.putExtra("siteVisitEntity",siteVisitModel);
               startActivity(i);*/