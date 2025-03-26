package elink.suezShimla.water.crm.MeterManagementSystem.MMGReports.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGReports.Adapter.MMGZoneWardDetailAdapter;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGReports.Model.MMGZoneWardDetailModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;


public class MeterInstallationFragment extends Fragment {
    private Context mCon;
    private String ward, ward_id, zone_id;
    private MMGZoneWardDetailAdapter mmgZoneWardDetailAdapter;
    private List<MMGZoneWardDetailModel> mmgZoneWardDetailModels;
    private LinearLayout errorLinear;

    private String jsonResponse = "", FromDateFormat, toDateFormat;
    private MaterialDialog progress;
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    RecyclerView mmgRecycler;
    TextView tv_new_connection;
    List<MMGZoneWardDetailModel> data=new ArrayList<MMGZoneWardDetailModel>();
    public MeterInstallationFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_meter_installation, container, false);
        mCon = getActivity();

        mmgRecycler = view.findViewById(R.id.mmgRecycler);
        errorLinear = view.findViewById(R.id.errorLinear);
        tv_new_connection = view.findViewById(R.id.tv_new_connection);
        tv_new_connection.setSelected(true);
        mmgZoneWardDetailAdapter = new MMGZoneWardDetailAdapter(mCon,data);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mCon);
        mmgRecycler.setLayoutManager(mLayoutManager);
        mmgRecycler.setItemAnimator(new DefaultItemAnimator());
        mmgRecycler.setAdapter(mmgZoneWardDetailAdapter);
         preparedata();
         return  view;

    }

        public void  preparedata(){
           MMGZoneWardDetailModel mmgZoneWardDetailModel= new MMGZoneWardDetailModel("1-East-01","EZ01","East","East","1-EZ01","East","001246","Meter Burnt","Wire Issue","0","0","0","0","0","0","0","0","0","0","0","0","0[' ");
           data.add(mmgZoneWardDetailModel);
            mmgZoneWardDetailModel= new MMGZoneWardDetailModel("1-East-01","EZ35","East","East","1-EZ01","East","00568","NSC","Illegal Cross Connection","1","0","0","0","1","0","0","0","0","0","0","0","0");
            data.add(mmgZoneWardDetailModel);
        }

    @Override
    public void onResume() {
        super.onResume();

        App myApp = (App)mCon.getApplicationContext();
        if (myApp.wasInBackground) {
            getActivity().finish();
            startActivity(new Intent(mCon, SplashScreen.class));
        }

        myApp.stopActivityTransitionTimer();
    }

    @Override
    public void onPause() {
        super.onPause();
        ((App) mCon.getApplicationContext()).startActivityTransitionTimer();
    }

}
