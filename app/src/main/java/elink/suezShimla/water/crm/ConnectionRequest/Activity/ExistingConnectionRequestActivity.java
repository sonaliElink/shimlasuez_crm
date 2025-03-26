package elink.suezShimla.water.crm.ConnectionRequest.Activity;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import elink.suezShimla.water.crm.ConnectionRequest.Activity.Fragment.ConnectionParaChangeFragment;
import elink.suezShimla.water.crm.ConnectionRequest.Activity.Fragment.MeterInstallrationFragment;
import elink.suezShimla.water.crm.ConnectionRequest.Activity.Fragment.NameChangeAndCorrectionFragment;
import elink.suezShimla.water.crm.ConnectionRequest.Activity.Fragment.TDReConnectionFragment;
import elink.suezShimla.water.crm.R;

public class ExistingConnectionRequestActivity extends AppCompatActivity {

    private Context mCon;

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_connection_request);
        // prevent ss and hide content when app is on background
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mCon = this;

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragment = new TDReConnectionFragment();
        SetFragment();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_re_connection:
                    fragment = new TDReConnectionFragment();
                    SetFragment();
                    return true;
                case R.id.action_meter_installation:
                    fragment = new MeterInstallrationFragment();
                    SetFragment();
                    return true;
                case R.id.action_name_change_correction:
                    fragment = new NameChangeAndCorrectionFragment();
                    SetFragment();
                    return true;
                case R.id.action_connection_para_change:
                    fragment = new ConnectionParaChangeFragment();
                    SetFragment();
                    return true;
            }

            return false;
        }
    };

    public void SetFragment() {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.existing_container, fragment).commit();
        }
    }
}
