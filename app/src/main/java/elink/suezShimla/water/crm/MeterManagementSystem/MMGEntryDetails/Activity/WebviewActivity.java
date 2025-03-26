package elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.MainActivity;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;

public class WebviewActivity extends AppCompatActivity {

    private Context mCon;
    private String pdfLink;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_webview);

        mCon = this;
        webView = findViewById(R.id.pdfWebview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(false);
        webSettings.setLoadsImagesAutomatically(true);
        clearAllSharedPrefs();
        pdfLink = getIntent().getStringExtra("pdfUrl");
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(pdfLink);
    }

    private void clearAllSharedPrefs(){
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.METERSIZEID);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.OLD_METER_NO);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.NEW_METER_NO);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.CONSUMER_NAME);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.CONSUMER_NO);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.CONSUMER_SOURCE);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.BU);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.PC);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.CONSUMERREFERENCENUMBER);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.CONTACTORNAME);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.VENDORCODE);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.MATERIALHANDOVER);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.NEW_MAKERCODE);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.NEW_METERNUM);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.OLD_INSTALLDATE);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.O_MANUFACTURE_CODE);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.NEW_INSTALLDATE);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.NEW_METERSIZE);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.NEW_SEALNO);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.NEW_INITIALREADING);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.NEW_METERTYPE);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.NEW_METERLOCATION);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.NEW_PROTECTEDBOX);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.NEW_TAXNO);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.PCCLEN);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.PCCWIDTH);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.PCCDEPTH);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.PCCTOTAL);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.RDCUTTINGID);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.RDCUTTINGLENGTH);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.RDCUTTINGWIDTH);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.RDCUTTINGDEPTH);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.RDCUTTINGTOTAL);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.MATERIALXML);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.CIVILMEASUREMENTXML);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.OLD_MAKERCODE);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.OLD_METERNUM);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.OLD_INSTALLDT);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.OLD_METERSIZE);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.OLDSEALNUM);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.PASTMETERNO);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.OLDMTRSTS);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.OLDMETERTYPE);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.FINALREADING);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.FINALSTATUS);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.REASONID);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.RADIOBUTTONVAL);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.METERSTATUS);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.CONNECTIONLOAD);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.SUBMITMATERIALBUTTONTAG);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.SUBMITCVLMEASUREMENTBUTTONTAG);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.SUBMITCIVILLIST);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.MAKERCODENAME);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.METERTYPENAME);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.COMMISIONED_NONCOMMISIONED);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.PROPERTY_ASSESSMENT);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.FROM_NODE);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.TO_NODE);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.PRIMARY_MOBILE);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.ALTERNATE_MOBILE);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.GIS_BID);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.SUBMIT_STATUS);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.MTR_SIZE_ID);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.ALLOCATED_WORK_LIST);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.MTR_TYPE_CODE_ID);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.VALIDMETER);
        UtilitySharedPreferences.clearPrefKey(mCon, Constants.CONTLIST);
    }

    @Override
    public void onBackPressed() {
        clearAllSharedPrefs();
        App.backPressMMGFragment = "Y";
        Intent intent = new Intent(mCon, MainActivity.class);
        intent.putExtra("Tag", "2");
        startActivity(intent);
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