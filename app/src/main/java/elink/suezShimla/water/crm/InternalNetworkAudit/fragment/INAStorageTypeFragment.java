package elink.suezShimla.water.crm.InternalNetworkAudit.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.InternalNetworkAudit.Model.Complaint;
import elink.suezShimla.water.crm.InternalNetworkAudit.Model.MeterConnected;
import elink.suezShimla.water.crm.InternalNetworkAudit.Model.PhysicaldetailsAndIntcomm;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Utils.PreferenceServicesINA;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;
import elink.suezShimla.water.crm.constant.AppConstant;

public class INAStorageTypeFragment extends Fragment implements View.OnClickListener {

    Complaint complaint;
    String isSubmitOrSave = "", jsonResponse = "", consumerNo = "", complaintNo;

    private Invoke invServices;
    private Gson gson;
    private Context mCon;
    private ConnectionDetector connection;
    private MaterialDialog progress;

    private EditText et_overhead_tank_nos;
    private EditText et_overhead_tank_capacity;
    private EditText et_ground_tank_nos;
    private EditText et_ground_tank_capacity;
    private EditText et_under_ground_sump_nos;
    private EditText et_under_ground_sump_capacity;
    private EditText et_grand_total_nos;
    private EditText et_grand_total_capacity;

    String strMeterNo = "", strMeterMake = "", strMeterSize = "", strMeterTested = "", strTestResult = "", strMeterReading = "",
            strSealStatus = "", str_overhead_tank_capacity = "", str_ground_tank_capacity = "", str_under_ground_sump_capacity = "",
            str_ground_tank_nos = "", str_under_ground_sump_nos = "", str_overhead_tank_nos = "", strOverHeadTankCondition = "",
            strGroundTankCondition = "", strSuctionPumpCondition = "", strUGSumpCondition = "", strLeakFoundMeter = "",
            strLeadFoundInHouse = "", strFloatingValueAlv = "", str_details_of_observation = "", str_recomondations_to_customer = "",
            strMeterStatusId = "", str_meter_received_by = "";

    private KeyGenerator keyGenerator;
    private SecretKey secretKey;
    private byte[] IV = new byte[16];
    private SecureRandom random;
    AesAlgorithm aes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_ina_storage_type, container, false);
        // prevent ss and hide content when app is on background
       // getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mCon = getActivity();
        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();

        if (getArguments() != null) {
            complaint = (Complaint) getArguments().getSerializable("complaint");
            consumerNo = getArguments().getString("consumerNo");
            complaintNo = getArguments().getString("complaintNo");
        }

        try {
            // AES Algorithm for encryption / decryption

            keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            secretKey = keyGenerator.generateKey();

            random = new SecureRandom();
            random.nextBytes(IV);

            aes=new AesAlgorithm();

        } catch (Exception e) {
            e.printStackTrace();
        }

        initializeViews(rootView);

        et_overhead_tank_nos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().isEmpty() && !s.toString().equalsIgnoreCase("")) {
                    int totalNo = Integer.parseInt(String.valueOf(s))
                            + Integer.parseInt(et_ground_tank_nos.getText().toString()) +
                            Integer.parseInt(et_under_ground_sump_nos.getText().toString());
                    et_grand_total_nos.setText(String.valueOf(totalNo));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_ground_tank_nos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty() && !s.toString().equalsIgnoreCase("")) {

                    int totalNo = Integer.parseInt(et_overhead_tank_nos.getText().toString())
                            + Integer.parseInt(String.valueOf(s)) +
                            Integer.parseInt(et_under_ground_sump_nos.getText().toString());
                    et_grand_total_nos.setText(String.valueOf(totalNo));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_under_ground_sump_nos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().isEmpty() && !s.toString().equalsIgnoreCase("")) {

                    int totalNo = Integer.parseInt(et_overhead_tank_nos.getText().toString())
                            + Integer.parseInt(et_ground_tank_nos.getText().toString()) +
                            Integer.parseInt(String.valueOf(s));
                    et_grand_total_nos.setText(String.valueOf(totalNo));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_overhead_tank_capacity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().isEmpty() && !s.toString().equalsIgnoreCase("")) {

                    int totalNo = Integer.parseInt(String.valueOf(s))
                            + Integer.parseInt(et_ground_tank_capacity.getText().toString()) +
                            Integer.parseInt(et_under_ground_sump_capacity.getText().toString());
                    et_grand_total_capacity.setText(String.valueOf(totalNo));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_ground_tank_capacity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().isEmpty() && !s.toString().equalsIgnoreCase("")) {

                    int totalNo = Integer.parseInt(et_overhead_tank_capacity.getText().toString())
                            + Integer.parseInt(String.valueOf(s)) +
                            Integer.parseInt(et_under_ground_sump_capacity.getText().toString());
                    et_grand_total_capacity.setText(String.valueOf(totalNo));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_under_ground_sump_capacity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().isEmpty() && !s.toString().equalsIgnoreCase("")) {

                    int totalNo = Integer.parseInt(et_overhead_tank_capacity.getText().toString())
                            + Integer.parseInt(et_ground_tank_capacity.getText().toString()) +
                            Integer.parseInt(String.valueOf(s));
                    et_grand_total_capacity.setText(String.valueOf(totalNo));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return rootView;
    }

    private void initializeViews(View rootView) {

        Button btn_submit_consumer = rootView.findViewById(R.id.btn_submit_consumer);
        Button btn_consumer_back = rootView.findViewById(R.id.btn_consumer_back);
        btn_consumer_back.setOnClickListener(this);
        btn_submit_consumer.setOnClickListener(this);

        EditText etApplicationNumber = rootView.findViewById(R.id.etApplicationNumber);
        EditText etComplaintNo = rootView.findViewById(R.id.etComplaintNo);

        etApplicationNumber.setText(consumerNo);
        etComplaintNo.setText(complaintNo);


        et_overhead_tank_nos = rootView.findViewById(R.id.et_overhead_tank_nos);
        et_overhead_tank_capacity = rootView.findViewById(R.id.et_overhead_tank_capacity);

        et_ground_tank_nos = rootView.findViewById(R.id.et_ground_tank_nos);
        et_ground_tank_capacity = rootView.findViewById(R.id.et_ground_tank_capacity);

        et_under_ground_sump_nos = rootView.findViewById(R.id.et_under_ground_sump_nos);
        et_under_ground_sump_capacity = rootView.findViewById(R.id.et_under_ground_sump_capacity);

        et_grand_total_nos = rootView.findViewById(R.id.et_grand_total_nos);
        et_grand_total_capacity = rootView.findViewById(R.id.et_grand_total_capacity);

        getValue();


    }

    private void getValue() {

        if (complaint.getPhysicaldetailsAndIntcomm() != null && complaint.getPhysicaldetailsAndIntcomm().size() > 0) {
            for (PhysicaldetailsAndIntcomm physicaldetailsAndIntcomm : complaint.getPhysicaldetailsAndIntcomm()) {

                int grandTotalNo = physicaldetailsAndIntcomm.getMIA_OHTANK_QTY() +
                        physicaldetailsAndIntcomm.getMIA_GROUNDTANK_QTY() +
                        physicaldetailsAndIntcomm.getMIA_UGSUMP_QTY();

                int grandTotalCapacity = physicaldetailsAndIntcomm.getMIA_OHTANK_CAPACITY() +
                        physicaldetailsAndIntcomm.getMIA_GROUNDTANK_CAPACITY() +
                        physicaldetailsAndIntcomm.getMIA_UGSUMP_CAPACITY();

                et_grand_total_nos.setText(String.valueOf(grandTotalNo));
                et_grand_total_capacity.setText(String.valueOf(grandTotalCapacity));

                et_overhead_tank_capacity.setText(String.valueOf(physicaldetailsAndIntcomm.getMIA_OHTANK_CAPACITY()));
                et_ground_tank_capacity.setText(String.valueOf(physicaldetailsAndIntcomm.getMIA_GROUNDTANK_CAPACITY()));
                et_under_ground_sump_capacity.setText(String.valueOf(physicaldetailsAndIntcomm.getMIA_UGSUMP_CAPACITY()));
                et_ground_tank_nos.setText(String.valueOf(physicaldetailsAndIntcomm.getMIA_GROUNDTANK_QTY()));
                et_under_ground_sump_nos.setText(String.valueOf(physicaldetailsAndIntcomm.getMIA_UGSUMP_QTY()));
                et_overhead_tank_nos.setText(String.valueOf(physicaldetailsAndIntcomm.getMIA_OHTANK_QTY()));

                str_meter_received_by = physicaldetailsAndIntcomm.getMTRTR_MTRRECEIVEDBY();

                strMeterNo = physicaldetailsAndIntcomm.getMIA_METER_NO();
                if (physicaldetailsAndIntcomm.getMIA_ISMETERTESTED() != null) {

                    if (physicaldetailsAndIntcomm.getMIA_ISMETERTESTED().equalsIgnoreCase("Y")) {
                        strMeterTested = "Y";
                    } else if (physicaldetailsAndIntcomm.getMIA_ISMETERTESTED().equalsIgnoreCase("N")) {
                        strMeterTested = "N";
                    }
                }
                if (physicaldetailsAndIntcomm.getMIA_TEST_RESULTS() != null) {
                    strTestResult = physicaldetailsAndIntcomm.getMIA_TEST_RESULTS();
                }

                strMeterStatusId = physicaldetailsAndIntcomm.getMIA_METER_STATUS_ID();

                strMeterReading = physicaldetailsAndIntcomm.getMIA_METER_READING();
                strSealStatus = physicaldetailsAndIntcomm.getMIA_METERSEAL_STATUS();
                strOverHeadTankCondition = physicaldetailsAndIntcomm.getMIA_OHTANK_CONDITION();
                strGroundTankCondition = physicaldetailsAndIntcomm.getMIA_GROUNDTANK_CONDITION();
                strSuctionPumpCondition = physicaldetailsAndIntcomm.getMIA_SUCTIONPUMP_CONDITION();
                strUGSumpCondition = physicaldetailsAndIntcomm.getMIA_UGSUMP_CONDITION();
                strLeakFoundMeter = physicaldetailsAndIntcomm.getMIA_LEAKFOUND_AFTERMETER();
                strLeadFoundInHouse = physicaldetailsAndIntcomm.getMIA_LEAKFOUND_INHOUSE();
                strFloatingValueAlv = physicaldetailsAndIntcomm.getMIA_FLOATINGVALVE_AVLBL();

                str_details_of_observation = physicaldetailsAndIntcomm.getMIA_DETAILSOFOBSRV();
                str_recomondations_to_customer = physicaldetailsAndIntcomm.getMIA_RETO_CUSTOMER();

            }
        }
        if (complaint.getMeterConnected() != null && complaint.getMeterConnected().size() > 0) {
            for (MeterConnected meterConnected : complaint.getMeterConnected()) {
                strMeterMake = meterConnected.getMMFG_MFGNAME();
                strMeterSize = meterConnected.getSRM_Connection_load();
            }
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_consumer_back:
                isSubmitOrSave = "No";
                saveINAData("N");
                break;

            case R.id.btn_submit_consumer:
                isSubmitOrSave = "Yes";
                saveINAData("Y");
                break;
        }
    }

    private void saveINAData(String isSubmitOrSave) {

        String empCode  =UtilitySharedPreferences.getPrefs(mCon, AppConstant.EMPCODE);
        try {
            empCode = aes.decrypt( empCode);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String empcode2 = null;
        try {
            // Decrypt EmpCode
            empcode2 = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String params[] = new String[29];

        params[0] = isSubmitOrSave;
        params[1] = consumerNo;

        params[2] = PreferenceServicesINA.getInstance().getMIA_METER_NO();

        params[3] = PreferenceServicesINA.getInstance().getO_METERTYPE();
        params[4] = PreferenceServicesINA.getInstance().getSRM_CONNECTION_LOAD();

        params[5] = PreferenceServicesINA.getInstance().getMIA_ISMETERTESTED();
        params[6] = PreferenceServicesINA.getInstance().getMIA_TEST_RESULTS();

        params[7] = PreferenceServicesINA.getInstance().getMIA_METER_READING();
        params[8] = PreferenceServicesINA.getInstance().getMIA_METERSEAL_STATUS();
        params[9] = PreferenceServicesINA.getInstance().getMIA_METER_STATUS_ID();
        ;
        params[10] = et_overhead_tank_nos.getText().toString();
        params[11] = et_overhead_tank_capacity.getText().toString();
        params[12] = et_ground_tank_nos.getText().toString();
        params[13] = et_ground_tank_capacity.getText().toString();
        params[14] = et_under_ground_sump_nos.getText().toString();
        params[15] = et_under_ground_sump_capacity.getText().toString();

        params[16] = PreferenceServicesINA.getInstance().getMIA_OHTANK_CONDITION();
        params[17] = PreferenceServicesINA.getInstance().getMIA_GROUNDTANK_CONDITION();
        params[18] = PreferenceServicesINA.getInstance().getMIA_SUCTIONPUMP_CONDITION();
        params[19] = PreferenceServicesINA.getInstance().getMIA_UGSUMP_CONDITION();
        params[20] = PreferenceServicesINA.getInstance().getMIA_LEAKFOUND_AFTERMETER();
        params[21] = PreferenceServicesINA.getInstance().getMIA_LEAKFOUND_INHOUSE();
        params[22] = PreferenceServicesINA.getInstance().getMIA_FLOATINGVALVE_AVLBL();
        params[23] = PreferenceServicesINA.getInstance().getMIA_DETAILSOFOBSRV();
        params[24] = PreferenceServicesINA.getInstance().getMIA_RETO_CUSTOMER();
        params[25] = empCode;
        params[26] = empcode2;
        params[27] = PreferenceServicesINA.getInstance().getMeterReceivedBy();
        params[28] = complaintNo;

        if (connection.isConnectingToInternet()) {
            SubmitINA submitINA = new SubmitINA();
            submitINA.execute(params);
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
    private class SubmitINA extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                String paraName[] = new String[29];

                Log.e("INAStorageTypePARAMS", Arrays.toString(params));

                paraName[0] = "IsSubmit";
                paraName[1] = "MIA_Consumerno";
                paraName[2] = "MIA_Meterno";
                paraName[3] = "MIA_Metermake";
                paraName[4] = "MIA_metersize";
                paraName[5] = "MIA_IsMeterTested";
                paraName[6] = "MIA_TestResults";
                paraName[7] = "MIA_MeterReading";
                paraName[8] = "MIA_SealStatus";
                paraName[9] = "MIA_MeterStatusId";
                paraName[10] = "MIA_OHTankQty";
                paraName[11] = "MIA_OHTankCapacity";
                paraName[12] = "MIA_GroundTankQty";
                paraName[13] = "MIA_GroundTankCapacity";
                paraName[14] = "MIA_UGSumpQty";
                paraName[15] = "MIA_UGSumpCapacity";
                paraName[16] = "MIA_OHTankCondition";
                paraName[17] = "MIA_GroundTankCondition";
                paraName[18] = "MIA_SuctionPumpCondition";
                paraName[19] = "MIA_UGSumpCondition";
                paraName[20] = "MIA_LeakFoundAMeter";
                paraName[21] = "MIA_LeakFoundInHouse";
                paraName[22] = "MIA_FloatingValveAvlbl";
                paraName[23] = "MIA_DetailsOfObsrv";
                paraName[24] = "MIA_ReToCustomer";
                paraName[25] = "MTRTR_MODIFIEDBYID";
                paraName[26] = "MTRTR_IP_ADDRESS";
                paraName[27] = "MTRTR_MTRRECEIVEDBY";
                paraName[28] = "MIA_REFNO";

                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.MeterIntNetAuditForsave, params, paraName);
                Log.e("jsonResponseSaveINA", "" + jsonResponse);
            } catch (Exception e) {
                Log.e("jsonRespError", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {

                if (jsonResponse.equalsIgnoreCase("Success"))
                    if(isSubmitOrSave.equalsIgnoreCase("Yes")){
                        Toast.makeText(mCon, "Data Submitted Successfully", Toast.LENGTH_SHORT).show();
                        getActivity().finish();

                    }else {
                        Toast.makeText(mCon, "Data Saved Successfully", Toast.LENGTH_SHORT).show();
                        getActivity().finish();

                    }


            } catch (Exception e) {
                Toast.makeText(mCon, e.getMessage(), Toast.LENGTH_SHORT).show();
                String error = e.toString();
                ErrorClass.errorData(mCon, "InternalNetworkAduditDetailActivity", "INA button event", error);
            }

            progress.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }
}
