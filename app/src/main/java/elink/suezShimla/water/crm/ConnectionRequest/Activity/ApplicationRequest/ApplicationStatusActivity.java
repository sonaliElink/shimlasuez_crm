package elink.suezShimla.water.crm.ConnectionRequest.Activity.ApplicationRequest;

import android.content.Context;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.ConnectionRequest.Activity.ApplicationRequest.Adapter.ApplicationStatusAdapter;
import elink.suezShimla.water.crm.ConnectionRequest.Activity.ApplicationRequest.Model.ApplicationStatusModel;
import elink.suezShimla.water.crm.R;

public class ApplicationStatusActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private Context mCon;

    private SwipeRefreshLayout swipeRefreshLayout;

    private RecyclerView applicationStatusRecycler;

    private ApplicationStatusAdapter applicationStatusAdapter;

    private List<ApplicationStatusModel> applicationStatusModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicationt_status);
        // prevent ss and hide content when app is on background
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mCon = this;

        applicationStatusRecycler = findViewById(R.id.applicationStatusRecycler);
        applicationStatusAdapter = new ApplicationStatusAdapter(mCon);

        applicationStatusModelList = new ArrayList<>();

        applicationStatusRecycler.setHasFixedSize(true);
        applicationStatusRecycler.setLayoutManager(new LinearLayoutManager(mCon));

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark, android.R.color.holo_red_dark);

        loadApplication();
    }


    private void loadApplication() {
        for (int i = 0; i < 10; i++) {
            ApplicationStatusModel model = new ApplicationStatusModel("19/03/1/9" + i, "Single", "1" + i + "Mar 2019 ", "MR. SOURABH BABAN JADHAV", "pending", "D12 ROOM NO 16 MAROL POLICE CAM,MAROL POLICE CAMP,MAROL POLICE CAMP VIJAY NAGAR-400025", "D12 ROOM NO 16 MAROL POLICE CAM,MAROL POLICE CAMP,MAROL POLICE CAMP VIJAY NAGAR-400025", "984551654" + i, "saurabh@gmail.com", "1" + i + " mar 2019", "Call Center", "No", "ss", "1" + i + " mar 2019");
            applicationStatusModelList.add(model);
            applicationStatusAdapter.addList(applicationStatusModelList);
            applicationStatusRecycler.setAdapter(applicationStatusAdapter);
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onRefresh() {
        loadApplication();
    }
}
