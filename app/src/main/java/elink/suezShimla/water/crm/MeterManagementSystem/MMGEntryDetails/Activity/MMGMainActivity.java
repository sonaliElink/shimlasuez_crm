package elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;

public class MMGMainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private Context mCon;
    RadioButton rb_old_hsc_meter, rb_old_hsc, rb_new_hsc_meter, rb_only_meter_replacement,rb_td_reconnection,
            rb_only_seal_replacement, rb_only_meter_box_replacement, radioComm, radioNonComm;

    TextView tv_old_hsc_meter, tv_old_hsc, tv_new_hsc_meter, tv_only_meter_replacement,tv_td_reconnection, tv_only_seal_replacement, tv_only_meter_box_replacement;

    String meterID = "", radiobuttonValStr = "", commisioned_noncommisioned = "", pagename = "", action = "", fromscreen = "", ref_no = "",
            is_commisioned = "", consumerNo = "", contList = "", reqNo = "";
    Button btn_submit_consumer;
    private String submitStatus = "", consumerNoStr = "", refNoStr = "", serachByStr = "", serachById = "", reqType = "", serviceType = "",subType = "";
    EditText et_nsc_application_no, et_consumer_account_no;
    Spinner searchBySpinner;
    private int positionId = 0;
    ArrayList<String> myList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_main_mmg);
        mCon = this;
        clearAllSharedPrefs();
        init();
    }

    private void init() {
        Intent startingIntent = getIntent();
        pagename = startingIntent.getStringExtra("pagename");
        if (pagename == null) {

        } else {
            if (!pagename.equalsIgnoreCase("")) {
                if (pagename.equalsIgnoreCase("MeterInstallationContractorDet")) {
                    pagename = startingIntent.getStringExtra("pagename");
                    contList = startingIntent.getStringExtra("contList");
                } else if (pagename.equalsIgnoreCase("MeterInstallationEntry")) {
                    refNoStr = startingIntent.getStringExtra("reqNo");
                    consumerNoStr = startingIntent.getStringExtra("ConsumerNo");
                    reqType = startingIntent.getStringExtra("reqType");
                    action = startingIntent.getStringExtra("checkboxType");
                    fromscreen = startingIntent.getStringExtra("fromAdapter");
                    serviceType = startingIntent.getStringExtra("serviceType");
                    subType = startingIntent.getStringExtra("subType");
                    //Toast.makeText(this, serviceType, Toast.LENGTH_SHORT).show();
//                    if (startingIntent.getStringExtra("serviceType") == null) {
//                        serviceType = "";
//                    } else {
//                        serviceType = startingIntent.getStringExtra("serviceType");
//                    }
                    // serviceType = startingIntent.getStringExtra("serviceType");

                }
            }
        }

        //Toast.makeText(this, action, Toast.LENGTH_SHORT).show();
        et_nsc_application_no = findViewById(R.id.et_nsc_application_no);
        if (getIntent().getStringExtra("NavigationPage") != null) {
            if (getIntent().getStringExtra("NavigationPage").equalsIgnoreCase("FromNavigation")) {
                et_nsc_application_no.setEnabled(false);
            }
        }

        et_consumer_account_no = findViewById(R.id.et_consumer_account_no);

        radioComm = findViewById(R.id.radioComm);
        radioNonComm = findViewById(R.id.radioNonComm);

        if (pagename != null && !(pagename.equalsIgnoreCase(""))) {
            if (fromscreen.equalsIgnoreCase("saveDetails")) {
                radioComm.setEnabled(false);
                radioNonComm.setEnabled(false);//rupali
                //Toast.makeText(this, serviceType, Toast.LENGTH_SHORT).show();
                if (serviceType.equalsIgnoreCase("Y")) {
                    radioComm.setChecked(true);
                    radioNonComm.setChecked(false);
                } else if (serviceType.equalsIgnoreCase("N")) {
                    radioComm.setChecked(false);
                    radioNonComm.setChecked(true);
                }
            } else if (fromscreen.equalsIgnoreCase("allocatedWork")) {
                radioComm.setEnabled(true);
                radioNonComm.setEnabled(true);
                if (serviceType.equalsIgnoreCase("Y")) {
                    radioComm.setChecked(true);
                    radioNonComm.setChecked(false);
                } else if (serviceType.equalsIgnoreCase("N")) {
                    radioComm.setChecked(false);
                    radioNonComm.setChecked(true);
                }
            }
        }

        searchBySpinner = findViewById(R.id.searchBySpinner);
        searchBySpinner.setOnItemSelectedListener(this);

        rb_old_hsc_meter = findViewById(R.id.rb_old_hsc_meter);
        rb_old_hsc = findViewById(R.id.rb_old_hsc);
        rb_new_hsc_meter = findViewById(R.id.rb_new_hsc_meter);
        rb_td_reconnection = findViewById(R.id.rb_td_reconnection);
        rb_only_meter_replacement = findViewById(R.id.rb_only_meter_replacement);
        rb_only_seal_replacement = findViewById(R.id.rb_only_seal_replacement);
        rb_only_meter_box_replacement = findViewById(R.id.rb_only_meter_box_replacement);

        tv_old_hsc_meter = findViewById(R.id.tv_old_hsc_meter);
        tv_old_hsc = findViewById(R.id.tv_old_hsc);
        tv_new_hsc_meter = findViewById(R.id.tv_new_hsc_meter);
        tv_td_reconnection = findViewById(R.id.tv_td_reconnection);
        tv_only_meter_replacement = findViewById(R.id.tv_only_meter_replacement);
        tv_only_seal_replacement = findViewById(R.id.tv_only_seal_replacement);
        tv_only_meter_box_replacement = findViewById(R.id.tv_only_meter_box_replacement);

        btn_submit_consumer = findViewById(R.id.btn_submit_consumer);
        btn_submit_consumer.setOnClickListener(this);

        if (pagename != null) {
            if (pagename.equalsIgnoreCase("MeterInstallationContractorDet")) {

                fetchedDetails();
            }
            if (pagename.equalsIgnoreCase("MeterInstallationEntry")) {
                fetchMeterInstalltionData();
            }
        }

        UtilitySharedPreferences.clearPrefKey(MMGMainActivity.this, Constants.RADIOBUTTONVAL);
        UtilitySharedPreferences.clearPrefKey(MMGMainActivity.this, Constants.COMMISIONED_NONCOMMISIONED);

        rb_old_hsc_meter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    radioComm.setEnabled(true);
                    radioNonComm.setEnabled(true);

                    tv_old_hsc_meter.setTextColor(getResources().getColor(R.color.black));
                    tv_old_hsc_meter.setTypeface(null, Typeface.BOLD);
                    tv_old_hsc.setTypeface(null, Typeface.NORMAL);
                    tv_new_hsc_meter.setTypeface(null, Typeface.NORMAL);
                    tv_only_meter_replacement.setTypeface(null, Typeface.NORMAL);
                    tv_only_seal_replacement.setTypeface(null, Typeface.NORMAL);
                    tv_only_meter_box_replacement.setTypeface(null, Typeface.NORMAL);


                    tv_old_hsc.setTextColor(getResources().getColor(R.color.black));
                    tv_new_hsc_meter.setTextColor(getResources().getColor(R.color.black));
                    tv_only_meter_replacement.setTextColor(getResources().getColor(R.color.black));
                    tv_only_seal_replacement.setTextColor(getResources().getColor(R.color.black));
                    tv_only_meter_box_replacement.setTextColor(getResources().getColor(R.color.black));

                    meterID = "1";
                    // Toast.makeText(ActivitySelectRiskProfile.this, clickCount, Toast.LENGTH_SHORT).show();

                    radiobuttonValStr = "OM";
                    UtilitySharedPreferences.clearPrefKey(MMGMainActivity.this, Constants.RADIOBUTTONVAL);
                    UtilitySharedPreferences.setPrefs(MMGMainActivity.this, Constants.RADIOBUTTONVAL, radiobuttonValStr);

                    rb_old_hsc.setChecked(false);
                    rb_new_hsc_meter.setChecked(false);
                    rb_td_reconnection.setChecked(false);
                    rb_only_meter_replacement.setChecked(false);
                    rb_only_seal_replacement.setChecked(false);
                    rb_only_meter_box_replacement.setChecked(false);
                }
            }
        });
        rb_old_hsc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    radioComm.setEnabled(true);
                    radioNonComm.setEnabled(true);

                    rb_old_hsc.setTextColor(getResources().getColor(R.color.blue));
                    tv_old_hsc.setTypeface(null, Typeface.BOLD);

                    tv_old_hsc_meter.setTypeface(null, Typeface.NORMAL);
                    tv_new_hsc_meter.setTypeface(null, Typeface.NORMAL);
                    tv_only_meter_replacement.setTypeface(null, Typeface.NORMAL);
                    tv_only_seal_replacement.setTypeface(null, Typeface.NORMAL);
                    tv_only_meter_box_replacement.setTypeface(null, Typeface.NORMAL);

                    tv_old_hsc_meter.setTextColor(getResources().getColor(R.color.black));
                    tv_new_hsc_meter.setTextColor(getResources().getColor(R.color.black));
                    tv_only_meter_replacement.setTextColor(getResources().getColor(R.color.black));
                    tv_only_seal_replacement.setTextColor(getResources().getColor(R.color.black));
                    tv_only_meter_box_replacement.setTextColor(getResources().getColor(R.color.black));

                    meterID = "2";
                    // Toast.makeText(ActivitySelectRiskProfile.this, clickCount, Toast.LENGTH_SHORT).show();

                    radiobuttonValStr = "OH";

                    rb_old_hsc_meter.setChecked(false);
                    rb_new_hsc_meter.setChecked(false);
                    rb_only_meter_replacement.setChecked(false);
                    rb_td_reconnection.setChecked(false);
                    rb_only_seal_replacement.setChecked(false);
                    rb_only_meter_box_replacement.setChecked(false);

                    UtilitySharedPreferences.clearPrefKey(MMGMainActivity.this, Constants.RADIOBUTTONVAL);
                    UtilitySharedPreferences.setPrefs(MMGMainActivity.this, Constants.RADIOBUTTONVAL, radiobuttonValStr);


                }
            }
        });
        rb_new_hsc_meter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    radioComm.setEnabled(true);
                    radioNonComm.setEnabled(true);

                    rb_new_hsc_meter.setTextColor(getResources().getColor(R.color.blue));
                    tv_new_hsc_meter.setTypeface(null, Typeface.BOLD);

                    tv_old_hsc_meter.setTypeface(null, Typeface.NORMAL);
                    tv_old_hsc.setTypeface(null, Typeface.NORMAL);
                    tv_only_meter_replacement.setTypeface(null, Typeface.NORMAL);
                    tv_only_seal_replacement.setTypeface(null, Typeface.NORMAL);
                    tv_only_meter_box_replacement.setTypeface(null, Typeface.NORMAL);

                    tv_old_hsc_meter.setTextColor(getResources().getColor(R.color.black));
                    tv_old_hsc.setTextColor(getResources().getColor(R.color.black));
                    tv_only_meter_replacement.setTextColor(getResources().getColor(R.color.black));
                    tv_only_seal_replacement.setTextColor(getResources().getColor(R.color.black));
                    tv_only_meter_box_replacement.setTextColor(getResources().getColor(R.color.black));

                    meterID = "3";
                    // Toast.makeText(ActivitySelectRiskProfile.this, clickCount, Toast.LENGTH_SHORT).show();

                    radiobuttonValStr = "N";
                    UtilitySharedPreferences.clearPrefKey(MMGMainActivity.this, Constants.RADIOBUTTONVAL);
                    UtilitySharedPreferences.setPrefs(MMGMainActivity.this, Constants.RADIOBUTTONVAL, radiobuttonValStr);

                    rb_old_hsc_meter.setChecked(false);
                    rb_old_hsc.setChecked(false);
                    rb_td_reconnection.setChecked(false);
                    rb_only_meter_replacement.setChecked(false);
                    rb_only_seal_replacement.setChecked(false);
                    rb_only_meter_box_replacement.setChecked(false);
                }
            }
        });

        rb_td_reconnection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    radioComm.setEnabled(true);
                    radioNonComm.setEnabled(true);

                    rb_td_reconnection.setTextColor(getResources().getColor(R.color.blue));
                    tv_new_hsc_meter.setTypeface(null, Typeface.NORMAL);

                    tv_old_hsc_meter.setTypeface(null, Typeface.NORMAL);
                    tv_old_hsc.setTypeface(null, Typeface.NORMAL);
                    tv_only_meter_replacement.setTypeface(null, Typeface.NORMAL);

                    tv_only_seal_replacement.setTypeface(null, Typeface.NORMAL);
                    tv_only_meter_box_replacement.setTypeface(null, Typeface.NORMAL);

                    tv_old_hsc_meter.setTextColor(getResources().getColor(R.color.black));
                    tv_new_hsc_meter.setTextColor(getResources().getColor(R.color.black));

                    tv_old_hsc.setTextColor(getResources().getColor(R.color.black));
                    tv_only_meter_replacement.setTextColor(getResources().getColor(R.color.black));
                    tv_only_seal_replacement.setTextColor(getResources().getColor(R.color.black));
                    tv_only_meter_box_replacement.setTextColor(getResources().getColor(R.color.black));

                    meterID = "3";
                    // Toast.makeText(ActivitySelectRiskProfile.this, clickCount, Toast.LENGTH_SHORT).show();

                    radiobuttonValStr = "N";
                    UtilitySharedPreferences.clearPrefKey(MMGMainActivity.this, Constants.RADIOBUTTONVAL);
                    UtilitySharedPreferences.setPrefs(MMGMainActivity.this, Constants.RADIOBUTTONVAL, radiobuttonValStr);

                    rb_old_hsc_meter.setChecked(false);
                    rb_old_hsc.setChecked(false);
                    rb_td_reconnection.setChecked(true);
                    rb_new_hsc_meter.setChecked(false);
                    rb_only_meter_replacement.setChecked(false);
                    rb_only_seal_replacement.setChecked(false);
                    rb_only_meter_box_replacement.setChecked(false);
                }
            }
        });
        rb_only_meter_replacement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    radioComm.setEnabled(true);
                    radioNonComm.setEnabled(true);

                    rb_only_meter_replacement.setTextColor(getResources().getColor(R.color.blue));
                    tv_only_meter_replacement.setTypeface(null, Typeface.BOLD);

                    tv_old_hsc_meter.setTypeface(null, Typeface.NORMAL);
                    tv_new_hsc_meter.setTypeface(null, Typeface.NORMAL);
                    tv_old_hsc.setTypeface(null, Typeface.NORMAL);
                    tv_only_seal_replacement.setTypeface(null, Typeface.NORMAL);
                    tv_only_meter_box_replacement.setTypeface(null, Typeface.NORMAL);

                    tv_old_hsc_meter.setTextColor(getResources().getColor(R.color.black));
                    tv_new_hsc_meter.setTextColor(getResources().getColor(R.color.black));
                    tv_old_hsc.setTextColor(getResources().getColor(R.color.black));
                    tv_only_seal_replacement.setTextColor(getResources().getColor(R.color.black));
                    tv_only_meter_box_replacement.setTextColor(getResources().getColor(R.color.black));

                    meterID = "4";
                    // Toast.makeText(ActivitySelectRiskProfile.this, clickCount, Toast.LENGTH_SHORT).show();

                    radiobuttonValStr = "R";
                    UtilitySharedPreferences.clearPrefKey(MMGMainActivity.this, Constants.RADIOBUTTONVAL);
                    UtilitySharedPreferences.setPrefs(MMGMainActivity.this, Constants.RADIOBUTTONVAL, radiobuttonValStr);
                    rb_old_hsc_meter.setChecked(false);
                    rb_old_hsc.setChecked(false);
                    rb_new_hsc_meter.setChecked(false);
                    rb_td_reconnection.setChecked(false);
                    rb_only_seal_replacement.setChecked(false);
                    rb_only_meter_box_replacement.setChecked(false);

                }

            }
        });
        rb_only_seal_replacement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    radioComm.setEnabled(false);
                    radioNonComm.setEnabled(false);
                    radioComm.setChecked(true);

                    rb_only_seal_replacement.setTextColor(getResources().getColor(R.color.blue));
                    tv_only_seal_replacement.setTypeface(null, Typeface.BOLD);

                    tv_old_hsc_meter.setTypeface(null, Typeface.NORMAL);
                    tv_new_hsc_meter.setTypeface(null, Typeface.NORMAL);
                    tv_old_hsc.setTypeface(null, Typeface.NORMAL);
                    tv_only_meter_replacement.setTypeface(null, Typeface.NORMAL);
                    tv_only_meter_box_replacement.setTypeface(null, Typeface.NORMAL);

                    tv_old_hsc_meter.setTextColor(getResources().getColor(R.color.black));
                    tv_new_hsc_meter.setTextColor(getResources().getColor(R.color.black));
                    tv_old_hsc.setTextColor(getResources().getColor(R.color.black));
                    tv_only_meter_replacement.setTextColor(getResources().getColor(R.color.black));
                    tv_only_meter_box_replacement.setTextColor(getResources().getColor(R.color.black));

                    meterID = "5";
                    // Toast.makeText(ActivitySelectRiskProfile.this, clickCount, Toast.LENGTH_SHORT).show();

                    radiobuttonValStr = "S";
                    UtilitySharedPreferences.clearPrefKey(MMGMainActivity.this, Constants.RADIOBUTTONVAL);
                    UtilitySharedPreferences.setPrefs(MMGMainActivity.this, Constants.RADIOBUTTONVAL, radiobuttonValStr);
                    rb_old_hsc_meter.setChecked(false);
                    rb_old_hsc.setChecked(false);
                    rb_new_hsc_meter.setChecked(false);
                    rb_td_reconnection.setChecked(false);
                    rb_only_meter_replacement.setChecked(false);
                    rb_only_meter_box_replacement.setChecked(false);
                }

            }
        });
        rb_only_meter_box_replacement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    radioComm.setEnabled(false);
                    radioNonComm.setEnabled(false);
                    radioComm.setChecked(true);

                    rb_only_meter_box_replacement.setTextColor(getResources().getColor(R.color.blue));
                    tv_only_meter_box_replacement.setTypeface(null, Typeface.BOLD);

                    tv_old_hsc_meter.setTypeface(null, Typeface.NORMAL);
                    tv_new_hsc_meter.setTypeface(null, Typeface.NORMAL);
                    tv_old_hsc.setTypeface(null, Typeface.NORMAL);
                    tv_only_meter_replacement.setTypeface(null, Typeface.NORMAL);
                    tv_only_seal_replacement.setTypeface(null, Typeface.NORMAL);

                    tv_old_hsc_meter.setTextColor(getResources().getColor(R.color.black));
                    tv_new_hsc_meter.setTextColor(getResources().getColor(R.color.black));
                    tv_old_hsc.setTextColor(getResources().getColor(R.color.black));
                    tv_only_meter_replacement.setTextColor(getResources().getColor(R.color.black));
                    tv_only_seal_replacement.setTextColor(getResources().getColor(R.color.black));

                    meterID = "6";
                    // Toast.makeText(ActivitySelectRiskProfile.this, clickCount, Toast.LENGTH_SHORT).show();

                    radiobuttonValStr = "MB";
                    UtilitySharedPreferences.clearPrefKey(MMGMainActivity.this, Constants.RADIOBUTTONVAL);
                    UtilitySharedPreferences.setPrefs(MMGMainActivity.this, Constants.RADIOBUTTONVAL, radiobuttonValStr);
                    rb_old_hsc_meter.setChecked(false);
                    rb_old_hsc.setChecked(false);
                    rb_new_hsc_meter.setChecked(false);
                    rb_td_reconnection.setChecked(false);
                    rb_only_meter_replacement.setChecked(false);
                    rb_only_seal_replacement.setChecked(false);
                }

            }
        });

        radioComm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    UtilitySharedPreferences.clearPrefKey(MMGMainActivity.this, Constants.COMMISIONED_NONCOMMISIONED);
                    commisioned_noncommisioned = "Y";
                    UtilitySharedPreferences.setPrefs(MMGMainActivity.this, Constants.COMMISIONED_NONCOMMISIONED, commisioned_noncommisioned);
                }
            }
        });
        radioNonComm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    UtilitySharedPreferences.clearPrefKey(MMGMainActivity.this, Constants.COMMISIONED_NONCOMMISIONED);
                    commisioned_noncommisioned = "N";
                    UtilitySharedPreferences.setPrefs(MMGMainActivity.this, Constants.COMMISIONED_NONCOMMISIONED, commisioned_noncommisioned);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_submit_consumer) {
            submitStatus = "Y";
            consumerNoStr = et_consumer_account_no.getText().toString();
            refNoStr = et_nsc_application_no.getText().toString().trim();

            if (rb_old_hsc.isChecked()) {
                radiobuttonValStr = "OH";
                UtilitySharedPreferences.clearPrefKey(MMGMainActivity.this, Constants.RADIOBUTTONVAL);
                UtilitySharedPreferences.setPrefs(MMGMainActivity.this, Constants.RADIOBUTTONVAL, radiobuttonValStr);
            } else if (rb_old_hsc_meter.isChecked()) {
                radiobuttonValStr = "OM";
                UtilitySharedPreferences.clearPrefKey(MMGMainActivity.this, Constants.RADIOBUTTONVAL);
                UtilitySharedPreferences.setPrefs(MMGMainActivity.this, Constants.RADIOBUTTONVAL, radiobuttonValStr);
            } else if (rb_new_hsc_meter.isChecked()||rb_td_reconnection.isChecked()) {
                radiobuttonValStr = "N";
                UtilitySharedPreferences.clearPrefKey(MMGMainActivity.this, Constants.RADIOBUTTONVAL);
                UtilitySharedPreferences.setPrefs(MMGMainActivity.this, Constants.RADIOBUTTONVAL, radiobuttonValStr);
            } else if (rb_only_meter_replacement.isChecked()) {
                radiobuttonValStr = "R";
                UtilitySharedPreferences.clearPrefKey(MMGMainActivity.this, Constants.RADIOBUTTONVAL);
                UtilitySharedPreferences.setPrefs(MMGMainActivity.this, Constants.RADIOBUTTONVAL, radiobuttonValStr);
            } else if (rb_only_seal_replacement.isChecked()) {
                radiobuttonValStr = "S";
                UtilitySharedPreferences.clearPrefKey(MMGMainActivity.this, Constants.RADIOBUTTONVAL);
                UtilitySharedPreferences.setPrefs(MMGMainActivity.this, Constants.RADIOBUTTONVAL, radiobuttonValStr);
            } else if (rb_only_meter_box_replacement.isChecked()) {
                radiobuttonValStr = "MB";
                UtilitySharedPreferences.clearPrefKey(MMGMainActivity.this, Constants.RADIOBUTTONVAL);
                UtilitySharedPreferences.setPrefs(MMGMainActivity.this, Constants.RADIOBUTTONVAL, radiobuttonValStr);
            }

            if (radioComm.isChecked()) {
                UtilitySharedPreferences.clearPrefKey(MMGMainActivity.this, Constants.COMMISIONED_NONCOMMISIONED);
                commisioned_noncommisioned = "Y";
                UtilitySharedPreferences.setPrefs(MMGMainActivity.this, Constants.COMMISIONED_NONCOMMISIONED, commisioned_noncommisioned);
            } else if (radioNonComm.isChecked()) {
                UtilitySharedPreferences.clearPrefKey(MMGMainActivity.this, Constants.COMMISIONED_NONCOMMISIONED);
                commisioned_noncommisioned = "N";
                UtilitySharedPreferences.setPrefs(MMGMainActivity.this, Constants.COMMISIONED_NONCOMMISIONED, commisioned_noncommisioned);
            }
        }

        UtilitySharedPreferences.clearPrefKey(MMGMainActivity.this, Constants.OLDSEALNUM);
        UtilitySharedPreferences.clearPrefKey(MMGMainActivity.this, Constants.CONNECTIONLOAD);
        UtilitySharedPreferences.clearPrefKey(MMGMainActivity.this, Constants.MAKERCODENAME);
        UtilitySharedPreferences.clearPrefKey(MMGMainActivity.this, Constants.MAKERCODENAME);
        UtilitySharedPreferences.clearPrefKey(MMGMainActivity.this, Constants.CONTLIST);
        //  UtilitySharedPreferences.clearPrefKey(this, Constants.NEW_METER_NO);
        //   UtilitySharedPreferences.clearPrefKey(this, Constants.NEW_METERNUM);
        UtilitySharedPreferences.clearPrefKey(this, Constants.NEW_INSTALLDATE);
        UtilitySharedPreferences.clearPrefKey(this, Constants.NEW_METERSIZE);
        UtilitySharedPreferences.clearPrefKey(this, Constants.NEW_SEALNO);
        UtilitySharedPreferences.clearPrefKey(this, Constants.NEW_INITIALREADING);
        UtilitySharedPreferences.clearPrefKey(this, Constants.NEW_METERTYPE);
        UtilitySharedPreferences.clearPrefKey(this, Constants.NEW_METERLOCATION);
        UtilitySharedPreferences.clearPrefKey(this, Constants.NEW_PROTECTEDBOX);
        UtilitySharedPreferences.clearPrefKey(this, Constants.NEW_TAXNO);

        if (is_commisioned.equalsIgnoreCase("Y")) {
            UtilitySharedPreferences.clearPrefKey(MMGMainActivity.this, Constants.COMMISIONED_NONCOMMISIONED);
            commisioned_noncommisioned = "Y";
            UtilitySharedPreferences.setPrefs(MMGMainActivity.this, Constants.COMMISIONED_NONCOMMISIONED, commisioned_noncommisioned);
        } else if (is_commisioned.equalsIgnoreCase("N")) {
            UtilitySharedPreferences.clearPrefKey(MMGMainActivity.this, Constants.COMMISIONED_NONCOMMISIONED);
            commisioned_noncommisioned = "N";
            UtilitySharedPreferences.setPrefs(MMGMainActivity.this, Constants.COMMISIONED_NONCOMMISIONED, commisioned_noncommisioned);
        }

        UtilitySharedPreferences.clearPrefKey(this, Constants.CONSUMER_NO);
        UtilitySharedPreferences.clearPrefKey(this, Constants.CONSUMERREFERENCENUMBER);
        UtilitySharedPreferences.setPrefs(this, Constants.CONSUMER_NO, consumerNoStr);
        UtilitySharedPreferences.setPrefs(this, Constants.CONSUMERREFERENCENUMBER, refNoStr);
        UtilitySharedPreferences.setPrefs(this, Constants.SUBMIT_STATUS, submitStatus);
        //    UtilitySharedPreferences.clearPrefKey(this, Constants.CONTACTORNAME);
        //    UtilitySharedPreferences.clearPrefKey(this, Constants.CONTACTOREMP);
        //   UtilitySharedPreferences.clearPrefKey(this, Constants.VALIDMETER);
        UtilitySharedPreferences.clearPrefKey(this, Constants.CONTLIST);
        UtilitySharedPreferences.clearPrefKey(this, Constants.NEW_METER_NO);
        UtilitySharedPreferences.clearPrefKey(this, Constants.NEW_METERNUM);
        UtilitySharedPreferences.clearPrefKey(this, Constants.NEW_INSTALLDATE);
        UtilitySharedPreferences.clearPrefKey(this, Constants.NEW_METERSIZE);
        UtilitySharedPreferences.clearPrefKey(this, Constants.NEW_SEALNO);
        UtilitySharedPreferences.clearPrefKey(this, Constants.NEW_INITIALREADING);
        UtilitySharedPreferences.clearPrefKey(this, Constants.NEW_METERTYPE);
        UtilitySharedPreferences.clearPrefKey(this, Constants.NEW_METERLOCATION);
        UtilitySharedPreferences.clearPrefKey(this, Constants.NEW_PROTECTEDBOX);
        UtilitySharedPreferences.clearPrefKey(this, Constants.NEW_TAXNO);
        UtilitySharedPreferences.clearPrefKey(this, Constants.MTR_SIZE_ID);

        onSubmitConsumerDetails();
    }

    private void onSubmitConsumerDetails() {
        if (
                /*!radioNonComm.isChecked() && !radioComm.isChecked() ||*/
                (!rb_new_hsc_meter.isChecked() &&!rb_td_reconnection.isChecked() && !rb_old_hsc.isChecked()
                && !rb_old_hsc_meter.isChecked() && !rb_only_meter_replacement.isChecked() && !rb_only_seal_replacement.isChecked() && !rb_only_meter_box_replacement.isChecked()
                || serachByStr.equalsIgnoreCase("--Select--"))
                || consumerNoStr.equalsIgnoreCase("")) {
           /* if (!radioNonComm.isChecked() && !radioComm.isChecked()) {
                Toast.makeText(this, "Please check Commisioned or Non-Commisioned Area", Toast.LENGTH_SHORT).show();
            }*/
            if (!rb_new_hsc_meter.isChecked() && !rb_td_reconnection.isChecked() && !rb_old_hsc.isChecked() && !rb_old_hsc_meter.isChecked()
                    && !rb_only_meter_replacement.isChecked() && !rb_only_seal_replacement.isChecked() && !rb_only_meter_box_replacement.isChecked()) {
                Toast.makeText(this, "Please check amongst OLD Connection & METER or OLD Connection or NEW Connection " +
                        "& METER or ONLY METER REPLACEMENT or ONLY SEAL REPLACEMENT or ONLY METER BOX REPLACEMENT", Toast.LENGTH_SHORT).show();
            }
            if (serachByStr.equalsIgnoreCase("") || serachByStr.equalsIgnoreCase("--Select--")) {
                Toast.makeText(this, "Please select search by criteria", Toast.LENGTH_SHORT).show();
            }
            if (consumerNoStr.equalsIgnoreCase("")) {
                Toast.makeText(this, "Please Enter Consumer number", Toast.LENGTH_SHORT).show();
            }
        } else {
            getCustomerDetails();
        }
    }

    private void getCustomerDetails() {
        consumerNoStr = et_consumer_account_no.getText().toString();
        refNoStr = et_nsc_application_no.getText().toString().trim();

        //Change here
        if (radioComm.isChecked()) {
            UtilitySharedPreferences.clearPrefKey(MMGMainActivity.this, Constants.COMMISIONED_NONCOMMISIONED);
            commisioned_noncommisioned = "Y";
            UtilitySharedPreferences.setPrefs(MMGMainActivity.this, Constants.COMMISIONED_NONCOMMISIONED, commisioned_noncommisioned);
        } else if (radioNonComm.isChecked()) {
            UtilitySharedPreferences.clearPrefKey(MMGMainActivity.this, Constants.COMMISIONED_NONCOMMISIONED);
            commisioned_noncommisioned = "N";
            UtilitySharedPreferences.setPrefs(MMGMainActivity.this, Constants.COMMISIONED_NONCOMMISIONED, commisioned_noncommisioned);
        }


        Intent i = new Intent(this, MMGScreenActivity.class);
        i.putExtra("meterID", meterID);
        i.putExtra("serachById", serachById);
        i.putExtra("consumerNoStr", consumerNoStr);
        i.putExtra("refNoStr", refNoStr);
        i.putExtra("commisioned_noncommisioned", commisioned_noncommisioned);
        i.putExtra("radioButtonVal", radiobuttonValStr);
        i.putExtra("pagename", pagename);
        i.putExtra("contList", contList);
        UtilitySharedPreferences.clearPrefKey(this, Constants.COMPSUBTYPE);
        UtilitySharedPreferences.setPrefs(this, Constants.COMPSUBTYPE, subType);

        UtilitySharedPreferences.clearPrefKey(this, Constants.RADIOBUTTONVAL);
        UtilitySharedPreferences.setPrefs(this, Constants.RADIOBUTTONVAL, radiobuttonValStr);
        startActivity(i);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        serachByStr = searchBySpinner.getSelectedItem().toString().trim();
        searchBySpinner.setSelection(1);


        if (serachByStr.equalsIgnoreCase("Consumer No")) {
            serachById = "CNO";

            UtilitySharedPreferences.setPrefs(this, Constants.serachById, serachById);

        } else if (serachByStr.equalsIgnoreCase("Assessee No")) {
            serachById = "ANO";

            UtilitySharedPreferences.setPrefs(this, Constants.serachById, serachById);
        } else if (serachByStr.equalsIgnoreCase("Property Id")) {
            serachById = "PID";

            UtilitySharedPreferences.setPrefs(this, Constants.serachById, serachById);
        } else if (serachByStr.equalsIgnoreCase("GIS Id")) {
            serachById = "GID";

            UtilitySharedPreferences.setPrefs(this, Constants.serachById, serachById);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void fetchedDetails() {
        Intent intent = getIntent();
        if (pagename.equalsIgnoreCase("MeterInstallationContractorDet")) {
            String jsonArray = intent.getStringExtra("contList");

            try {
                JSONArray array = new JSONArray(jsonArray);
                System.out.println(array.toString(2));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Intent startingIntent = getIntent();
            positionId = startingIntent.getIntExtra("positionId", 0);
            consumerNo = startingIntent.getStringExtra("consumerNo");
            action = startingIntent.getStringExtra("action");
            ref_no = startingIntent.getStringExtra("ref_no");
            is_commisioned = startingIntent.getStringExtra("is_commisioned");

            Log.e("is_commisioned",is_commisioned);

            radioComm.setEnabled(false);
            radioNonComm.setEnabled(false);
            et_consumer_account_no.setEnabled(false);
            et_nsc_application_no.setEnabled(false);
            // temporarly true when comes MI_ACTION data then false  due to SUEZDEMO link ;

            rb_old_hsc_meter.setEnabled(false);
            rb_old_hsc.setEnabled(false);
            rb_new_hsc_meter.setEnabled(false);
            rb_td_reconnection.setEnabled(false);
            rb_only_meter_replacement.setEnabled(false);
            rb_only_seal_replacement.setEnabled(false);
            rb_only_meter_box_replacement.setEnabled(false);

            et_nsc_application_no.setText(ref_no);

            if (is_commisioned.equalsIgnoreCase("N")) {
                radioNonComm.setChecked(true);
            } else {
                radioComm.setChecked(true);
            }

            if (action.equalsIgnoreCase("OM")) {
                rb_old_hsc_meter.setChecked(true);
            } else if (action.equalsIgnoreCase("OH")) {
                rb_old_hsc.setChecked(true);
            } else if (action.equalsIgnoreCase("N")) {
                if(subType.equalsIgnoreCase("TD Reconnection")){

                    rb_td_reconnection.setChecked(true);
                }else {
                    rb_new_hsc_meter.setChecked(true);
                }
            } else if (action.equalsIgnoreCase("R")) {
                rb_only_meter_replacement.setChecked(true);
            } else if (action.equalsIgnoreCase("S")) {
                rb_only_seal_replacement.setChecked(true);
            } else if (action.equalsIgnoreCase("MB")) {
                rb_only_meter_box_replacement.setChecked(true);
            }

            if (ref_no.equalsIgnoreCase("")) {
                et_nsc_application_no.setText("");
            } else
                et_nsc_application_no.setText(ref_no);

            et_consumer_account_no.setText(consumerNo);
        }
    }

    private void fetchMeterInstalltionData() {

//
//        if (serviceType.equalsIgnoreCase("")) {
//            radioComm.setChecked(true);
//        //    radioComm.setEnabled(false);
//       //     radioNonComm.setEnabled(false);
//        } else {
//            radioComm.setChecked(true);
//           // radioNonComm.setEnabled(false);
//         //   radioComm.setEnabled(false);
//        }

        // temporarly  true for MI_ACTION

        rb_old_hsc_meter.setEnabled(false);
        rb_old_hsc.setEnabled(false);
        rb_new_hsc_meter.setEnabled(false);
        rb_td_reconnection.setEnabled(false);
        rb_only_meter_replacement.setEnabled(false);
        rb_only_seal_replacement.setEnabled(false);
        rb_only_meter_box_replacement.setEnabled(false);

       /* radioNonComm.setEnabled(false);
        et_consumer_account_no.setEnabled(false);
        et_nsc_application_no.setEnabled(false);
        rb_old_hsc_meter.setEnabled(false);
        rb_old_hsc.setEnabled(false);
        rb_new_hsc_meter.setEnabled(false);
        rb_only_meter_replacement.setEnabled(false);
        et_nsc_application_no.setText(ref_no);*/
//        if(reqType.equalsIgnoreCase("A")){
//
//            //radiobuttonValStr = "N";
//            UtilitySharedPreferences.clearPrefKey(MMGMainActivity.this,Constants.RADIOBUTTONVAL);
//            UtilitySharedPreferences.setPrefs(MMGMainActivity.this, Constants.RADIOBUTTONVAL, radiobuttonValStr);
//            rb_old_hsc_meter.setChecked(false);
//            rb_old_hsc.setChecked(false);
//            rb_new_hsc_meter.setChecked(true);
//            rb_only_meter_replacement.setChecked(false);
//
//            rb_old_hsc_meter.setEnabled(false);
//            rb_old_hsc.setEnabled(false);
//            rb_new_hsc_meter.setEnabled(false);
//            rb_only_meter_replacement.setEnabled(false);
//
//        }else if(reqType.equalsIgnoreCase("C")){
//            //radiobuttonValStr = "R";
//            UtilitySharedPreferences.clearPrefKey(MMGMainActivity.this,Constants.RADIOBUTTONVAL);
//            UtilitySharedPreferences.setPrefs(MMGMainActivity.this, Constants.RADIOBUTTONVAL, radiobuttonValStr);
//            rb_old_hsc_meter.setChecked(false);
//            rb_old_hsc.setChecked(false);
//            rb_new_hsc_meter.setChecked(false);
//            rb_only_meter_replacement.setChecked(true);
//
//            rb_old_hsc_meter.setEnabled(false);
//            rb_old_hsc.setEnabled(false);
//            rb_new_hsc_meter.setEnabled(false);
//            rb_only_meter_replacement.setEnabled(false);
//        }else if(reqType.equalsIgnoreCase("MC")){
//            //radiobuttonValStr = "R";
//            UtilitySharedPreferences.clearPrefKey(MMGMainActivity.this,Constants.RADIOBUTTONVAL);
//            UtilitySharedPreferences.setPrefs(MMGMainActivity.this, Constants.RADIOBUTTONVAL, radiobuttonValStr);
//            rb_old_hsc_meter.setChecked(false);
//            rb_old_hsc.setChecked(false);
//            rb_new_hsc_meter.setChecked(false);
//            rb_only_meter_replacement.setChecked(true);
//
//            rb_old_hsc_meter.setEnabled(false);
//            rb_old_hsc.setEnabled(false);
//            rb_new_hsc_meter.setEnabled(false);
//            rb_only_meter_replacement.setEnabled(false);
//        }

        //Toast.makeText(this, action, Toast.LENGTH_SHORT).show();

        if (action.equalsIgnoreCase("OM")) {
            radiobuttonValStr = "OM";
            UtilitySharedPreferences.clearPrefKey(MMGMainActivity.this, Constants.RADIOBUTTONVAL);
            UtilitySharedPreferences.setPrefs(MMGMainActivity.this, Constants.RADIOBUTTONVAL, radiobuttonValStr);

            rb_old_hsc_meter.setChecked(true);
            rb_old_hsc.setChecked(false);
            rb_new_hsc_meter.setChecked(false);
            rb_td_reconnection.setChecked(false);
            rb_only_meter_replacement.setChecked(false);
            rb_only_seal_replacement.setChecked(false);
            rb_only_meter_box_replacement.setChecked(false);
        } else if (action.equalsIgnoreCase("OH")) {
            radiobuttonValStr = "OH";
            UtilitySharedPreferences.clearPrefKey(MMGMainActivity.this, Constants.RADIOBUTTONVAL);
            UtilitySharedPreferences.setPrefs(MMGMainActivity.this, Constants.RADIOBUTTONVAL, radiobuttonValStr);


            rb_old_hsc_meter.setChecked(false);
            rb_old_hsc.setChecked(true);
            rb_new_hsc_meter.setChecked(false);
            rb_td_reconnection.setChecked(false);
            rb_only_meter_replacement.setChecked(false);
            rb_only_seal_replacement.setChecked(false);
            rb_only_meter_box_replacement.setChecked(false);
        } else if (action.equalsIgnoreCase("N")) {
            if (subType.equalsIgnoreCase("TD Reconnection")){
                radiobuttonValStr = "N";
                UtilitySharedPreferences.clearPrefKey(MMGMainActivity.this, Constants.RADIOBUTTONVAL);
                UtilitySharedPreferences.setPrefs(MMGMainActivity.this, Constants.RADIOBUTTONVAL, radiobuttonValStr);

                rb_old_hsc_meter.setChecked(false);
                rb_old_hsc.setChecked(false);
                rb_new_hsc_meter.setChecked(false);
                rb_only_meter_replacement.setChecked(false);
                rb_only_seal_replacement.setChecked(false);
                rb_only_meter_box_replacement.setChecked(false);
                rb_td_reconnection.setChecked(true);


            }else {
                radiobuttonValStr = "N";
                UtilitySharedPreferences.clearPrefKey(MMGMainActivity.this, Constants.RADIOBUTTONVAL);
                UtilitySharedPreferences.setPrefs(MMGMainActivity.this, Constants.RADIOBUTTONVAL, radiobuttonValStr);

                rb_old_hsc_meter.setChecked(false);
                rb_old_hsc.setChecked(false);
                rb_td_reconnection.setChecked(false);
                rb_new_hsc_meter.setChecked(true);
                rb_only_meter_replacement.setChecked(false);
                rb_only_seal_replacement.setChecked(false);
                rb_only_meter_box_replacement.setChecked(false);
            }

        } else if (action.equalsIgnoreCase("R")) {
            radiobuttonValStr = "R";
            UtilitySharedPreferences.clearPrefKey(MMGMainActivity.this, Constants.RADIOBUTTONVAL);
            UtilitySharedPreferences.setPrefs(MMGMainActivity.this, Constants.RADIOBUTTONVAL, radiobuttonValStr);

            rb_old_hsc_meter.setChecked(false);
            rb_old_hsc.setChecked(false);
            rb_new_hsc_meter.setChecked(false);
            rb_td_reconnection.setChecked(false);
            rb_only_meter_replacement.setChecked(true);
            rb_only_seal_replacement.setChecked(false);
            rb_only_meter_box_replacement.setChecked(false);
        } else if (action.equalsIgnoreCase("S")) {
            radiobuttonValStr = "S";
            UtilitySharedPreferences.clearPrefKey(MMGMainActivity.this, Constants.RADIOBUTTONVAL);
            UtilitySharedPreferences.setPrefs(MMGMainActivity.this, Constants.RADIOBUTTONVAL, radiobuttonValStr);

            rb_old_hsc_meter.setChecked(false);
            rb_old_hsc.setChecked(false);
            rb_new_hsc_meter.setChecked(false);
            rb_td_reconnection.setChecked(false);
            rb_only_meter_replacement.setChecked(false);
            rb_only_seal_replacement.setChecked(true);
            rb_only_meter_box_replacement.setChecked(false);
        } else if (action.equalsIgnoreCase("MB")) {
            radiobuttonValStr = "MB";
            UtilitySharedPreferences.clearPrefKey(MMGMainActivity.this, Constants.RADIOBUTTONVAL);
            UtilitySharedPreferences.setPrefs(MMGMainActivity.this, Constants.RADIOBUTTONVAL, radiobuttonValStr);

            rb_old_hsc_meter.setChecked(false);
            rb_old_hsc.setChecked(false);
            rb_new_hsc_meter.setChecked(false);
            rb_td_reconnection.setChecked(false);
            rb_only_meter_replacement.setChecked(false);
            rb_only_seal_replacement.setChecked(false);
            rb_only_meter_box_replacement.setChecked(true);
        }

        et_nsc_application_no.setText(refNoStr);
        et_consumer_account_no.setText(consumerNoStr);
        //rb_only_meter_replacement.setChecked(true); //default this is set R because for now we are only fetching complaint data of allocation
    }

    private void clearAllSharedPrefs() {
        UtilitySharedPreferences.clearPrefKey(this, Constants.DATAFOUND);
        UtilitySharedPreferences.clearPrefKey(this, Constants.METERSIZEID);
        UtilitySharedPreferences.clearPrefKey(this, Constants.OLD_METER_NO);
        UtilitySharedPreferences.clearPrefKey(this, Constants.NEW_METER_NO);
        UtilitySharedPreferences.clearPrefKey(this, Constants.CONSUMER_NAME);
        UtilitySharedPreferences.clearPrefKey(this, Constants.CONSUMER_NO);
        UtilitySharedPreferences.clearPrefKey(this, Constants.CONSUMER_SOURCE);
        UtilitySharedPreferences.clearPrefKey(this, Constants.BU);
        UtilitySharedPreferences.clearPrefKey(this, Constants.PC);
        UtilitySharedPreferences.clearPrefKey(this, Constants.CONSUMERREFERENCENUMBER);
        UtilitySharedPreferences.clearPrefKey(this, Constants.CONTACTORNAME);
        UtilitySharedPreferences.clearPrefKey(this, Constants.CONTACTOREMP);
        UtilitySharedPreferences.clearPrefKey(this, Constants.VENDORCODE);
        UtilitySharedPreferences.clearPrefKey(this, Constants.MATERIALHANDOVER);
        UtilitySharedPreferences.clearPrefKey(this, Constants.NEW_MAKERCODE);
        UtilitySharedPreferences.clearPrefKey(this, Constants.NEW_METERNUM);
        UtilitySharedPreferences.clearPrefKey(this, Constants.OLD_INSTALLDATE);
        UtilitySharedPreferences.clearPrefKey(this, Constants.O_MANUFACTURE_CODE);
        UtilitySharedPreferences.clearPrefKey(this, Constants.NEW_INSTALLDATE);
        UtilitySharedPreferences.clearPrefKey(this, Constants.NEW_METERSIZE);
        UtilitySharedPreferences.clearPrefKey(this, Constants.NEW_SEALNO);
        UtilitySharedPreferences.clearPrefKey(this, Constants.NEW_INITIALREADING);
        UtilitySharedPreferences.clearPrefKey(this, Constants.NEW_METERTYPE);
        UtilitySharedPreferences.clearPrefKey(this, Constants.NEW_METERLOCATION);
        UtilitySharedPreferences.clearPrefKey(this, Constants.NEW_PROTECTEDBOX);
        UtilitySharedPreferences.clearPrefKey(this, Constants.NEW_TAXNO);
        UtilitySharedPreferences.clearPrefKey(this, Constants.PCCLEN);
        UtilitySharedPreferences.clearPrefKey(this, Constants.PCCWIDTH);
        UtilitySharedPreferences.clearPrefKey(this, Constants.PCCDEPTH);
        UtilitySharedPreferences.clearPrefKey(this, Constants.PCCTOTAL);
        UtilitySharedPreferences.clearPrefKey(this, Constants.RDCUTTINGID);
        UtilitySharedPreferences.clearPrefKey(this, Constants.RDCUTTINGLENGTH);
        UtilitySharedPreferences.clearPrefKey(this, Constants.RDCUTTINGWIDTH);
        UtilitySharedPreferences.clearPrefKey(this, Constants.RDCUTTINGDEPTH);
        UtilitySharedPreferences.clearPrefKey(this, Constants.RDCUTTINGTOTAL);
        UtilitySharedPreferences.clearPrefKey(this, Constants.MATERIALXML);
        UtilitySharedPreferences.clearPrefKey(this, Constants.CIVILMEASUREMENTXML);
        UtilitySharedPreferences.clearPrefKey(this, Constants.OLD_MAKERCODE);
        UtilitySharedPreferences.clearPrefKey(this, Constants.OLD_METERNUM);
        UtilitySharedPreferences.clearPrefKey(this, Constants.OLD_INSTALLDT);
        UtilitySharedPreferences.clearPrefKey(this, Constants.OLD_METERSIZE);
        UtilitySharedPreferences.clearPrefKey(this, Constants.OLDSEALNUM);
        UtilitySharedPreferences.clearPrefKey(this, Constants.PASTMETERNO);
        UtilitySharedPreferences.clearPrefKey(this, Constants.OLDMTRSTS);
        UtilitySharedPreferences.clearPrefKey(this, Constants.OLDMETERTYPE);
        UtilitySharedPreferences.clearPrefKey(this, Constants.FINALREADING);
        UtilitySharedPreferences.clearPrefKey(this, Constants.FINALSTATUS);
        UtilitySharedPreferences.clearPrefKey(this, Constants.REASONID);
        UtilitySharedPreferences.clearPrefKey(this, Constants.RADIOBUTTONVAL);
        UtilitySharedPreferences.clearPrefKey(this, Constants.METERSTATUS);
        UtilitySharedPreferences.clearPrefKey(this, Constants.CONNECTIONLOAD);
        UtilitySharedPreferences.clearPrefKey(this, Constants.SUBMITMATERIALBUTTONTAG);
        UtilitySharedPreferences.clearPrefKey(this, Constants.SUBMITCVLMEASUREMENTBUTTONTAG);
        UtilitySharedPreferences.clearPrefKey(this, Constants.SUBMITCIVILLIST);
        UtilitySharedPreferences.clearPrefKey(this, Constants.MAKERCODENAME);
        UtilitySharedPreferences.clearPrefKey(this, Constants.METERTYPENAME);
        UtilitySharedPreferences.clearPrefKey(this, Constants.COMMISIONED_NONCOMMISIONED);
        UtilitySharedPreferences.clearPrefKey(this, Constants.PROPERTY_ASSESSMENT);
        UtilitySharedPreferences.clearPrefKey(this, Constants.FROM_NODE);
        UtilitySharedPreferences.clearPrefKey(this, Constants.TO_NODE);
        UtilitySharedPreferences.clearPrefKey(this, Constants.PRIMARY_MOBILE);
        UtilitySharedPreferences.clearPrefKey(this, Constants.ALTERNATE_MOBILE);
        UtilitySharedPreferences.clearPrefKey(this, Constants.GIS_BID);
        UtilitySharedPreferences.clearPrefKey(this, Constants.SUBMIT_STATUS);
        UtilitySharedPreferences.clearPrefKey(this, Constants.MTR_SIZE_ID);
        UtilitySharedPreferences.clearPrefKey(this, Constants.ALLOCATED_WORK_LIST);
        UtilitySharedPreferences.clearPrefKey(this, Constants.MTR_TYPE_CODE_ID);
        UtilitySharedPreferences.clearPrefKey(this, Constants.VALIDMETER);
        UtilitySharedPreferences.clearPrefKey(this, Constants.CONTLIST);
        UtilitySharedPreferences.clearPrefKey(this, Constants.MI_METERINSTALLID);
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
