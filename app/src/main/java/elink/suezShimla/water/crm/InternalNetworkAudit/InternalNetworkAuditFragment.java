package elink.suezShimla.water.crm.InternalNetworkAudit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import java.util.ArrayList;

import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.InternalNetworkAudit.Model.Complaint;
import elink.suezShimla.water.crm.InternalNetworkAudit.Model.ComplaintInfo;
import elink.suezShimla.water.crm.InternalNetworkAudit.activity.InternalNetworkAuditActivity;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;

public class InternalNetworkAuditFragment extends Fragment implements View.OnClickListener {

    private Context mCon;
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    private String jsonResponse = "",complaintNo="",consumerNo="";
    Complaint complaint;
    private MaterialDialog progress;

    Button btn_get_details;
    private TextView tv_consumer_account_no;
    private AppCompatSpinner msrSpinner;
    private ArrayList<ComplaintInfo> complaintInfo = new ArrayList<>();
    private ArrayList<String> mList = new ArrayList<>();
    private ArrayList<String> mValueList = new ArrayList<>();
    ArrayList<ComplaintInfo> complaintInfoArrayList;
    private ArrayAdapter meterStatusAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_internal_network_audit, container, false);
        // prevent ss and hide content when app is on background
        //getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mCon = getActivity();

        init(view);

        return view;
    }

    private void init(View view) {

        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();
        btn_get_details = view.findViewById(R.id.btn_get_details);
        msrSpinner = view.findViewById(R.id.msrSpinner);
        tv_consumer_account_no = view.findViewById(R.id.tv_consumer_account_no);

        btn_get_details.setOnClickListener(this);

        if (connection.isConnectingToInternet()) {
            ConsumerNumber consumerNumber = new ConsumerNumber();
            consumerNumber.execute();
            progress = new MaterialDialog.Builder(mCon)
                    .content(R.string.loading)
                    .autoDismiss(false)
                    .canceledOnTouchOutside(false)
                    .progress(true, 0)
                    .widgetColorRes(R.color.colorPrimary)
                    .show();
        } else {
            Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }

        msrSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                complaintNo = msrSpinner.getSelectedItem().toString();
                consumerNo = mValueList.get(msrSpinner.getSelectedItemPosition());
                tv_consumer_account_no.setText(consumerNo);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get_details:
                if (complaintNo.equals("Select Complaint No.")) {
                     Toast.makeText(getActivity(), "Select Complaint No.", Toast.LENGTH_SHORT).show();
                }else {

                    getAllData(complaintNo, consumerNo);


                }
                break;

            default:
                break;
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class ConsumerNumber extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                jsonResponse = invServices.getDataWOParams(Constants.URL, Constants.NameSpace, Constants.GetComplaintAndConsumerNo);

            } catch (Exception e) {
                Log.e("jsonRespError", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                complaintInfoArrayList = new ArrayList<>();

                Complaint complaint = gson.fromJson(jsonResponse, Complaint.class);
                mList.add("Select Complaint No.");
                mValueList.add("");
                if (complaint.getComplaintInfoList() != null && complaint.getComplaintInfoList().size() > 0) {
                    for (ComplaintInfo complaintInfo : complaint.getComplaintInfoList()) {
                        String COM_SERVICE_NO=complaintInfo.getCOM_SERVICE_NO();
                        String COMPLAINT_REF_NO=complaintInfo.getCOMPLAINT_REF_NO();

                        ComplaintInfo complaintInfo1=new ComplaintInfo(COM_SERVICE_NO,COMPLAINT_REF_NO);
                        complaintInfoArrayList.add(complaintInfo1);

                        mList.add(complaintInfo.getCOMPLAINT_REF_NO());
                        mValueList.add(String.valueOf(complaintInfo.getCOM_SERVICE_NO()));
                    }
                }

                complaintInfo.addAll(complaintInfoArrayList);
                meterStatusAdapter = new ArrayAdapter(mCon, R.layout.simple_spinner_item, mList);
                meterStatusAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                msrSpinner.setAdapter(meterStatusAdapter);


            } catch (Exception e) {
                Log.d("postexecError", e.getMessage());
                Toast.makeText(mCon, e.getMessage(), Toast.LENGTH_SHORT).show();
                String error = e.toString();
                ErrorClass.errorData(mCon, "InternalNetworkAuditActivity", "Register button event", error);
            }

            progress.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }

    private void getAllData(String complaintNo, String consumerNo) {

        String params[] = new String[2];

        params[0] = consumerNo;
        params[1] = complaintNo;

        if (connection.isConnectingToInternet()) {
            GetAllData getAllData = new GetAllData();
            getAllData.execute(params);
            progress = new MaterialDialog.Builder(mCon)
                    .content(R.string.loading)
                    .autoDismiss(false)
                    .canceledOnTouchOutside(false)
                    .progress(true, 0)
                    .widgetColorRes(R.color.colorPrimary)
                    .show();
        } else {
            Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class GetAllData extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                String paraName[] = new String[2];

                paraName[0] = "SearchStr";
                paraName[1] = "RefNo";

                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.MMG_GetConsumerAndMeterDetailsForIntNetAudit, params, paraName);
                Log.e("getAllData", "" + jsonResponse);
            } catch (Exception e) {
                Log.e("jsonRespError", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                complaint=new Complaint();
                complaint = gson.fromJson(jsonResponse, Complaint.class);

                Intent intent = new Intent(getActivity(), InternalNetworkAuditActivity.class);
                intent.putExtra("complaint",complaint);
                intent.putExtra("complaintNo",complaintNo);
                intent.putExtra("consumerNo",consumerNo);
                startActivity(intent);
            } catch (Exception e) {
                String error = e.toString();
                ErrorClass.errorData(mCon, "RegisterActivity", "Register button event", error);
            }

            progress.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }

}
