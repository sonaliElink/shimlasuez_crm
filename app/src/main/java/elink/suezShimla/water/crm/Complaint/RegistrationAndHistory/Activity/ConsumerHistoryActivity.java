package elink.suezShimla.water.crm.Complaint.RegistrationAndHistory.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.Complaint.RegistrationAndHistory.Adapter.ConsumerHistoryAdapter;
import elink.suezShimla.water.crm.Complaint.RegistrationAndHistory.Model.HistoryModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;

public class ConsumerHistoryActivity extends AppCompatActivity {
    private Context mCon;

    RecyclerView consumerRecyclerView;
    List<HistoryModel> historyModelList = new ArrayList<>();
    public ConsumerHistoryAdapter consumerHistoryAdapter;
    String consumerNumber="";

    LinearLayout errorLinear;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer_history);
      //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mCon = this;

        init();

    }

    private void init() {
        consumerRecyclerView=findViewById(R.id.consumerRecyclerView);
        errorLinear=findViewById(R.id.errorLinear);

        historyModelList= getIntent().getParcelableArrayListExtra("history");
        consumerNumber= getIntent().getStringExtra("consumerNumber");


        consumerRecyclerView.setHasFixedSize(true);
        consumerRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        if(historyModelList.size()>0) {
            consumerRecyclerView.setVisibility(View.VISIBLE);
            errorLinear.setVisibility(View.GONE);
            consumerHistoryAdapter = new ConsumerHistoryAdapter(this, historyModelList,consumerNumber);
            consumerRecyclerView.setAdapter(consumerHistoryAdapter);
            consumerHistoryAdapter.notifyDataSetChanged();
        }else {
            consumerRecyclerView.setVisibility(View.GONE);
            errorLinear.setVisibility(View.VISIBLE);
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
