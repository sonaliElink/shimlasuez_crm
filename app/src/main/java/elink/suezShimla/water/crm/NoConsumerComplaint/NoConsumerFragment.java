package elink.suezShimla.water.crm.NoConsumerComplaint;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.button.MaterialButton;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ComplaintSourceModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ComplaintSubTypeModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ComplaintTypeModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.FinishActionModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.SiteEngineerModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.SubZoneModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ZoneModel;
import elink.suezShimla.water.crm.MasterData.DownloadMasterData;
import elink.suezShimla.water.crm.NoConsumerComplaint.NCAllocation.Activity.NCWorkAllocationActivity;
import elink.suezShimla.water.crm.NoConsumerComplaint.NCReallocation.Activity.NCWorkReallocationActivity;
import elink.suezShimla.water.crm.NoConsumerComplaint.NCRegistrationAndHistory.Activity.NCComplaintRegistrationActivity;
import elink.suezShimla.water.crm.NoConsumerComplaint.NCTodaysCompleteWork.NCTodaysWorkCompletedActivity;
import elink.suezShimla.water.crm.NoConsumerComplaint.NCWorkCompletion.Activity.NCWorkCompletionActivity;
import elink.suezShimla.water.crm.NoConsumerComplaint.NCZoneAndWard.Adapter.NCZoneWardAdapter;
import elink.suezShimla.water.crm.NoConsumerComplaint.NCZoneAndWard.Model.NCZoneWardModel;
import elink.suezShimla.water.crm.NoConsumerComplaint.NCZoneAndWard.NCZoneAndWardDetails.NCZoneWardDetailsActivity;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;

public class NoConsumerFragment extends Fragment {
    private Context mCon;
    private RecyclerView zoneWardRecyclerView;
    private SearchView searchView;
    private NCZoneWardAdapter nczoneWardAdapter;
    private List<NCZoneWardModel> nczoneWardModelList;
    private List<ZoneModel> zoneModelList;
    private List<SubZoneModel> subZoneModelList;
    private RelativeLayout viewAllRelativeLayout, dashBoardRelativeLayout, historyRegistrationRelativeLayout, workAllocationRelativeLayout, workReallocationRelativeLayout,
            workCompletionRelativeLayout, todayCompletedRelativeLayout;
    private RealmOperations realmOperations;

    private MaterialButton RegAndHistoryButton, allocationButton, reallocationButton, completionButton, locationButton, todayCompleteWorkButton;
    public NoConsumerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_no_consumer, container, false);
        // prevent ss and hide content when app is on background
       // getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);


        mCon = getActivity();
        realmOperations = new RealmOperations(mCon);

        zoneWardRecyclerView = view.findViewById(R.id.zoneWardRecyclerView);
        searchView = view.findViewById(R.id.searchView);
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                // hideSoftKeyboard();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                filter(query);
                return false;
            }
        });

        dashBoardRelativeLayout = view.findViewById(R.id.dashBoardRelativeLayout);
        historyRegistrationRelativeLayout = view.findViewById(R.id.historyRegistrationRelativeLayout);
        workAllocationRelativeLayout = view.findViewById(R.id.workAllocationRelativeLayout);
        workReallocationRelativeLayout = view.findViewById(R.id.workReallocationRelativeLayout);
        workCompletionRelativeLayout = view.findViewById(R.id.workCompletionRelativeLayout);
        todayCompletedRelativeLayout = view.findViewById(R.id.todayCompletedRelativeLayout);
        viewAllRelativeLayout = view.findViewById(R.id.viewAllRelativeLayout);

        setVisibility(false, false, false, false, false, false);

        nczoneWardAdapter = new NCZoneWardAdapter(mCon);
        zoneWardRecyclerView.setHasFixedSize(true);
        zoneWardRecyclerView.setLayoutManager(new LinearLayoutManager(mCon, LinearLayoutManager.HORIZONTAL, false));
        zoneWardRecyclerView.setItemAnimator(new DefaultItemAnimator());

        nczoneWardModelList = new ArrayList<>();

        loadRecyclerData();

        viewAllRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mCon, NCZoneWardDetailsActivity.class);
                i.putExtra("GETWARD", "All");
                i.putExtra("GETWARD_ID", "-1");
                i.putExtra("GETZONE_ID", "-1");
                startActivity(i);
            }
        });

        RegAndHistoryButton = view.findViewById(R.id.RegAndHistoryButton);
        allocationButton = view.findViewById(R.id.allocationButton);
        reallocationButton = view.findViewById(R.id.reallocationButton);
        completionButton = view.findViewById(R.id.completionButton);
        //  locationButton = view.findViewById(R.id.locationButton);
        todayCompleteWorkButton = view.findViewById(R.id.todayCompleteWorkButton);
        String isSiteEng= null;
        try {
            isSiteEng = new AesAlgorithm().decrypt(PreferenceUtil.getSiteEng());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String rights = PreferenceUtil.getRights();

        if (rights.contains("SYSADM001") || rights.contains("SADMIN423")) {
            setVisibility(true, true, true, true, true, true);
        } else {
            viewVisibility(dashBoardRelativeLayout, rights, "MICM00779");
            viewVisibility(historyRegistrationRelativeLayout, rights, "CCCIOG159");
            viewVisibility(workAllocationRelativeLayout, rights, "OCWA00312");
            viewVisibility(workReallocationRelativeLayout, rights, "OCWR00315");
            viewVisibility(workCompletionRelativeLayout, rights, "OCWC00318");
            viewVisibility(todayCompletedRelativeLayout, rights, "OCWC00318");
        }
        if(isSiteEng.equals("SiteEng")){
            todayCompletedRelativeLayout.setVisibility(View.VISIBLE);
        }else{
            todayCompletedRelativeLayout.setVisibility(View.GONE);
        }
        RegAndHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkMasterDataBase(NCComplaintRegistrationActivity.class);
            }
        });

        allocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkMasterDataBase(NCWorkAllocationActivity.class);
                //  startActivity(new Intent(mCon, WorkAllocationActivity.class));
            }
        });

        reallocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkMasterDataBase(NCWorkReallocationActivity.class);
                //  startActivity(new Intent(mCon, WorkReallocationActivity.class));
            }
        });

        completionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkMasterDataBase(NCWorkCompletionActivity.class);
                //  PreferenceUtil.clearAll();
                //PreferenceUtil.setUserType("Admin");
                //  startActivity(new Intent(mCon, WorkCompletionActivity.class));
            }
        });


      /*  locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                *//*checkMasterDataBase(WorkComplaintLocationActivity.class);
                PreferenceUtil.clearAll();
                PreferenceUtil.setUserType("Employee");*//*

                checkMasterDataBase(WorkCompletionActivity.class);
                // PreferenceUtil.clearAll();

                //  startActivity(new Intent(mCon, WorkComplaintLocationActivity.class));
            }
        });*/

        todayCompleteWorkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkMasterDataBase(NCTodaysWorkCompletedActivity.class);
                //  startActivity(new Intent(mCon, TodaysCompletedWorkActivity.class));
            }
        });

        return view;
    }

    private void loadRecyclerData() {
        try {
            String zoneIdStr = PreferenceUtil.getZone();

            List<String> elephantList = Arrays.asList(zoneIdStr.split(","));

            for (int i = 0; i < elephantList.size(); i++) {
                int id = Integer.parseInt(elephantList.get(i));
                zoneModelList = realmOperations.fetchZoneById(id);

                for (ZoneModel zoneModel : zoneModelList) {

                    subZoneModelList = realmOperations.fetchSubZoneByZoneId(zoneModel.getBUM_BU_ID());

                    for (SubZoneModel subZoneModel : subZoneModelList) {

                        String id_name = zoneModel.getBUM_BU_ID() + "-" + zoneModel.getBU_NAME();

                        NCZoneWardModel zoneWardModel = new NCZoneWardModel(zoneModel.getBUM_BU_ID(), subZoneModel.getPCM_PC_ID(), id_name, subZoneModel.getPCM_PC_NAME());
                        nczoneWardModelList.add(zoneWardModel);
                        nczoneWardAdapter.addList(nczoneWardModelList);
                        zoneWardRecyclerView.setAdapter(nczoneWardAdapter);
                    }
                }
            }
        } catch (Exception e) {
            Log.d("check", e.getMessage());
        }
    }

    public void filter(String input) {
        List<NCZoneWardModel> list = new ArrayList<>();

        for (NCZoneWardModel item : nczoneWardModelList) {
            if (item.getZone().toLowerCase().contains(input.toLowerCase()) || item.getWard().toLowerCase().contains(input.toLowerCase())) {
                list.add(item);
            }
        }
        nczoneWardAdapter.filterList(list);
    }

    private void checkMasterDataBase(Class aClass) {
        ComplaintTypeModel complaintTypeModelExist = realmOperations.getComplaintTypeExist();
        ComplaintSubTypeModel complaintSubTypeModelExist = realmOperations.getComplaintSubTypeExist();
        ComplaintSourceModel complaintSourceModelExist = realmOperations.getComplaintSourceExist();
        ZoneModel zoneModelExist = realmOperations.getZoneExist();
        SubZoneModel subZoneModelExist = realmOperations.getSubZoneExist();
        SiteEngineerModel siteEngineerModelExist = realmOperations.getSiteEngineerExist();
        FinishActionModel finishActionModelExist = realmOperations.getFinishActionExist();
        if (complaintTypeModelExist != null && complaintSubTypeModelExist != null && complaintSourceModelExist != null && zoneModelExist != null
                && subZoneModelExist != null && siteEngineerModelExist != null && finishActionModelExist != null) {
            startActivity(new Intent(mCon, aClass));
        } else {
            DownloadMasterData downloadMasterData = new DownloadMasterData();
            downloadMasterData.downloadData(mCon);
        }
    }

    private void viewVisibility(View view, String rights, String rightCode) {
        if (rights.contains(rightCode)) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    private void setVisibility(boolean dashboard, boolean historyReg, boolean allocate, boolean reallocate, boolean completion, boolean today) {
        dashBoardRelativeLayout.setVisibility(dashboard ? View.VISIBLE : View.GONE);
        historyRegistrationRelativeLayout.setVisibility(historyReg ? View.VISIBLE : View.GONE);
        workAllocationRelativeLayout.setVisibility(allocate ? View.VISIBLE : View.GONE);
        workReallocationRelativeLayout.setVisibility(reallocate ? View.VISIBLE : View.GONE);
        workCompletionRelativeLayout.setVisibility(completion ? View.VISIBLE : View.GONE);
        todayCompletedRelativeLayout.setVisibility(today ? View.VISIBLE : View.GONE);
    }
}
