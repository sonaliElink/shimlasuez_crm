package elink.suezShimla.water.crm.MeterManagementSystem.MassMeterInstallatin;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.MeterManagementSystem.MassMeterInstallatin.Adapter.MassMeterInstllAdapter;
import elink.suezShimla.water.crm.MeterManagementSystem.MassMeterInstallatin.Model.MassMeterInstllModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;

public class MassMeterInstallation extends AppCompatActivity {
RecyclerView massMtrInstRecyclerView;
private MassMeterInstllAdapter massMeterInstllAdapters;
private List<MassMeterInstllModel> massMeterInstllModelList = new ArrayList<>();
private Context mCon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mass_meter_installation);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mCon = this;
        massMeterInstllAdapters = new MassMeterInstllAdapter(mCon);
        massMtrInstRecyclerView = findViewById(R.id.massMtrInstRecyclerView);
        massMtrInstRecyclerView.setHasFixedSize(true);
        massMtrInstRecyclerView.setLayoutManager(new LinearLayoutManager(mCon));
        
        loadData();
    }

    private void loadData() {
        MassMeterInstllModel model = new MassMeterInstllModel(1,"Meter Installation done");
        massMeterInstllModelList.add(model);

        model = new MassMeterInstllModel(1,"Meter Installation done");
        massMeterInstllModelList.add(model);

        model = new MassMeterInstllModel(1,"Meter Installation done");
        massMeterInstllModelList.add(model);

        model = new MassMeterInstllModel(1,"Meter Installation done");
        massMeterInstllModelList.add(model);

        massMeterInstllAdapters.addList(massMeterInstllModelList);
        massMtrInstRecyclerView.setAdapter(massMeterInstllAdapters);
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
