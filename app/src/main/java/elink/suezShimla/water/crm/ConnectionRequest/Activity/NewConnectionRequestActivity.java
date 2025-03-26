package elink.suezShimla.water.crm.ConnectionRequest.Activity;

import android.content.Context;
import android.content.Intent;
import com.google.android.material.button.MaterialButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import elink.suezShimla.water.crm.ConnectionRequest.Activity.ApplicationRequest.ApplicationRequestActivity;
import elink.suezShimla.water.crm.ConnectionRequest.Activity.ApplicationRequest.ApplicationStatusActivity;
import elink.suezShimla.water.crm.ConnectionRequest.Activity.ApplicationRequest.AuthenticationAndApplicationActivity;
import elink.suezShimla.water.crm.R;

public class NewConnectionRequestActivity extends AppCompatActivity {

    private Context mCon;
    private ActionBar actionBar;
    private MaterialButton applicationRequestButton, authenticationApplicationButton, applicationStatusButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_connection_request);
        // prevent ss and hide content when app is on background
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mCon = this;
        actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setElevation(0);
        }

        applicationRequestButton = findViewById(R.id.applicationRequestButton);
        authenticationApplicationButton = findViewById(R.id.authenticationApplicationButton);
        applicationStatusButton = findViewById(R.id.applicationStatusButton);

        applicationRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mCon, ApplicationRequestActivity.class));
            }
        });

        authenticationApplicationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mCon, AuthenticationAndApplicationActivity.class));
            }
        });

        applicationStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mCon, ApplicationStatusActivity.class));
            }
        });

    }


}
