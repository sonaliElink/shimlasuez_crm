package elink.suezShimla.water.crm.DisconnectionAndDismantling;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.button.MaterialButton;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import elink.suezShimla.water.crm.DisconnectionAndDismantling.CaptureReason.CaptureReasonActivity;
import elink.suezShimla.water.crm.DisconnectionAndDismantling.DefaulterList.DefaulterListActivity;
import elink.suezShimla.water.crm.DisconnectionAndDismantling.DisconnectionOrderlist.DisconnectionOrderListActivity;
import elink.suezShimla.water.crm.DisconnectionAndDismantling.NoticeHistory.NoticeHistoryActivity;
import elink.suezShimla.water.crm.DisconnectionAndDismantling.ReConnectionWorkAllocation.ReConnectionWorkAllocationActivity;
import elink.suezShimla.water.crm.DisconnectionAndDismantling.ReConnectionWorkClosing.ReConnectionWorkClosingActivity;
import elink.suezShimla.water.crm.R;


public class DisconnectionAndDismantlingFragment extends Fragment {

    private Context mCon;

    public DisconnectionAndDismantlingFragment() {
        // Required empty public constructor
    }

    public static DisconnectionAndDismantlingFragment newInstance() {
        return new DisconnectionAndDismantlingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_disconnection_and_dismantling, container, false);
        // prevent ss and hide content when app is on background
       // getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mCon = getActivity();

        MaterialButton defaulterListButton = view.findViewById(R.id.defaulterListButton);
        MaterialButton disconnectionOrderListButton = view.findViewById(R.id.disconnectionOrderListButton);
        MaterialButton captureReasonButton = view.findViewById(R.id.captureReasonButton);
        MaterialButton noticeHistoryButton = view.findViewById(R.id.noticeHistoryButton);
        MaterialButton reConnectionWorkAllocationButton = view.findViewById(R.id.reConnectionWorkAllocationButton);
        MaterialButton reConnectionWorkClosingButton = view.findViewById(R.id.reConnectionWorkClosingButton);

        defaulterListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mCon, DefaulterListActivity.class));
            }
        });

        disconnectionOrderListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mCon, DisconnectionOrderListActivity.class));
            }
        });

        captureReasonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mCon, CaptureReasonActivity.class));
            }
        });

        noticeHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mCon, NoticeHistoryActivity.class));
            }
        });

        reConnectionWorkAllocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mCon, ReConnectionWorkAllocationActivity.class));
            }
        });

        reConnectionWorkClosingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mCon, ReConnectionWorkClosingActivity.class));
            }
        });

        return view;
    }

}
