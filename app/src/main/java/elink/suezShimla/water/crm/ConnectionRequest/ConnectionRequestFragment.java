package elink.suezShimla.water.crm.ConnectionRequest;


import android.content.Context;
import android.os.Bundle;
import com.google.android.material.button.MaterialButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.List;

import elink.suezShimla.water.crm.Complaint.ZoneAndWard.Adapter.ZoneWardAdapter;
import elink.suezShimla.water.crm.Complaint.ZoneAndWard.Model.ZoneWardModel;
import elink.suezShimla.water.crm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConnectionRequestFragment extends Fragment {

    private Context mCon;
    private RecyclerView zoneWardRecyclerView;
    private ZoneWardAdapter zoneWardAdapter;
    private List<ZoneWardModel> zoneWardModelList;
    private RelativeLayout viewAllRelativeLayout;

    private MaterialButton newConnectionRequestButton, existingConnectionRequestButton, technicalFeasibilityPlumberWorkButton, commercialFeasibilityButton, uploadDocButton;

    public ConnectionRequestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_connection_management, container, false);

        mCon = getActivity();



        return view;
    }


}
