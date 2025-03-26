package elink.suezShimla.water.crm.DisconnectionAndDismantling.CaptureReason;

import android.content.Context;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.DisconnectionAndDismantling.CaptureReason.Adapter.CaptureReasonAdapter;
import elink.suezShimla.water.crm.DisconnectionAndDismantling.CaptureReason.Model.CaptureReasonModel;
import elink.suezShimla.water.crm.R;

public class CaptureReasonActivity extends AppCompatActivity {
    private ActionBar actionBar;

    private RecyclerView captureReasonRecyclerView;

    private CaptureReasonAdapter captureReasonAdapter;

    private List<CaptureReasonModel> captureReasonModelList = new ArrayList<>();

    private Context mCon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_reason);
        // prevent ss and hide content when app is on background
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setElevation(30);
            actionBar.setTitle("Capture Reason");

        }

        mCon = this;

        captureReasonRecyclerView = findViewById(R.id.captureReasonRecyclerView);
        captureReasonRecyclerView.setHasFixedSize(true);
        captureReasonRecyclerView.setLayoutManager(new LinearLayoutManager(mCon));

        captureReasonAdapter = new CaptureReasonAdapter(mCon);

        loadData();

    }

    private void loadData() {
        captureReasonModelList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            CaptureReasonModel captureReasonModel = new CaptureReasonModel("WM/21/07/16/52 " + i, "PATEL JANTBI GAISUDIN " + i, "886616893" + i, "Flat No. " + i + " Triveni Apartments Pitam Pura NEW DELHI 110034 INDIA");
            captureReasonModelList.add(captureReasonModel);
            captureReasonAdapter.addList(captureReasonModelList);
            captureReasonRecyclerView.setAdapter(captureReasonAdapter);
            captureReasonAdapter.notifyDataSetChanged();
        }
    }
}
