package elink.suezShimla.water.crm.MeterManagementSystem.StoreManagement;

import android.content.Context;
import android.content.Intent;
import com.google.android.material.button.MaterialButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.MeterManagementSystem.StoreManagement.AcceptMeter.AcceptMeterActivity;
import elink.suezShimla.water.crm.MeterManagementSystem.StoreManagement.Authentication.AuthenticationActivity;
import elink.suezShimla.water.crm.MeterManagementSystem.StoreManagement.IssueToMmgDept.IssueToMmgDept;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;

public class StoreManagementActivity extends AppCompatActivity {
    MaterialButton acceptMeterButton, authenticationButton, issueMmgButton;
    private Context mCOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_store_management);
        mCOn = this;
        acceptMeterButton = findViewById(R.id.acceptMeterButton);
        authenticationButton = findViewById(R.id.authenticationButton);
        issueMmgButton = findViewById(R.id.issueMmgButton);

        acceptMeterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mCOn, AcceptMeterActivity.class));
            }
        });

        authenticationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mCOn, AuthenticationActivity.class));
            }
        });

        issueMmgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(mCOn, IssueToMmgDept.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        //finish();
        super.onBackPressed();
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
}
