package elink.suezShimla.water.crm.InternalNetworkAudit.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.afollestad.materialdialogs.MaterialDialog;

import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.InternalNetworkAudit.Model.Complaint;
import elink.suezShimla.water.crm.InternalNetworkAudit.Model.CustInfo;
import elink.suezShimla.water.crm.InternalNetworkAudit.Model.MeterConnected;
import elink.suezShimla.water.crm.InternalNetworkAudit.Model.PhysicaldetailsAndIntcomm;
import elink.suezShimla.water.crm.InternalNetworkAudit.activity.InternalNetworkAuditActivity;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Utils.PreferenceServicesINA;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;
import elink.suezShimla.water.crm.constant.AppConstant;

public class INAConsumerDetailsFragment extends Fragment implements View.OnClickListener {

    String jsonResponse = "", consumerNo = "", complaintNo;
    private Invoke invServices;
    private Context mCon;
    private ConnectionDetector connection;
    private MaterialDialog progress;

    private EditText edtConsumerName;
    private EditText edtConsumerAddress;
    private EditText edtContactNumber;
    private EditText et_testing_fee_receipt_no;
    private EditText et_meter_received_in_mtl_on;
    private EditText et_meter_received_by;

    Complaint complaint;

    String strMeterNo = "", strMeterMake = "", strMeterSize = "", strMeterTested = "", strTestResult = "", strMeterReading = "",
            strSealStatus = "", str_overhead_tank_capacity = "", str_ground_tank_capacity = "", str_under_ground_sump_capacity = "",
            str_ground_tank_nos = "", str_under_ground_sump_nos = "", str_overhead_tank_nos = "", strOverHeadTankCondition = "",
            strGroundTankCondition = "", strSuctionPumpCondition = "", strUGSumpCondition = "", strLeakFoundMeter = "",
            strLeadFoundInHouse = "", strFloatingValueAlv = "", str_details_of_observation = "", str_recomondations_to_customer = "",
            strMeterStatusId = "";

    private KeyGenerator keyGenerator;
    private SecretKey secretKey;
    private byte[] IV = new byte[16];
    private SecureRandom random;
    AesAlgorithm aes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        View rootView = inflater.inflate(R.layout.fragment_ina_consumer_details, container, false);

        mCon = getActivity();

        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();

       PreferenceServicesINA.init(mCon);

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

        return rootView;
    }

    private void initializeViews(View rootView) {

        Button btn_submit_consumer = rootView.findViewById(R.id.btn_submit_consumer);
        Button btn_consumer_back = rootView.findViewById(R.id.btn_consumer_back);
        btn_consumer_back.setOnClickListener(this);
        btn_submit_consumer.setOnClickListener(this);

        edtConsumerName = rootView.findViewById(R.id.edtConsumerName);
        edtConsumerAddress = rootView.findViewById(R.id.edtConsumerAddress);
        edtContactNumber = rootView.findViewById(R.id.edtContactNumber);
        et_testing_fee_receipt_no = rootView.findViewById(R.id.et_testing_fee_receipt_no);
        et_meter_received_in_mtl_on = rootView.findViewById(R.id.et_meter_received_in_mtl_on);
        et_meter_received_by = rootView.findViewById(R.id.et_meter_received_by);
        EditText etApplicationNumber = rootView.findViewById(R.id.etApplicationNumber);
        EditText etComplaintNo = rootView.findViewById(R.id.etComplaintNo);

        etApplicationNumber.setText(consumerNo);
        etComplaintNo.setText(complaintNo);

        getAllData();

    }

    private void getAllData() {

        if (complaint.getCustInfoList() != null && complaint.getCustInfoList().size() > 0) {
            for (CustInfo custInfo1 : complaint.getCustInfoList()) {

                edtConsumerName.setText(custInfo1.getSRM_S_NAME());
                edtConsumerAddress.setText(custInfo1.getSRM_B_Address2());
                edtContactNumber.setText(custInfo1.getSRM_S_MOBILE_NO());

            }
        }

        if (complaint.getMeterConnected() != null && complaint.getMeterConnected().size() > 0) {
            for (MeterConnected meterConnected : complaint.getMeterConnected()) {
                strMeterMake = meterConnected.getMMFG_MFGNAME();
                strMeterSize = meterConnected.getSRM_Connection_load();
            }
        }

        if (complaint.getPhysicaldetailsAndIntcomm() != null && complaint.getPhysicaldetailsAndIntcomm().size() > 0) {
            for (PhysicaldetailsAndIntcomm physicaldetailsAndIntcomm : complaint.getPhysicaldetailsAndIntcomm()) {
                SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy  hh:mm a");

                Date d = null;
                try {
                    d = input.parse(physicaldetailsAndIntcomm.getMTRTR_MTRRCVDINMTLONDATE());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String formatted = output.format(d);
                Log.i("DATE", "" + formatted);
                et_meter_received_in_mtl_on.setText(formatted);

                et_testing_fee_receipt_no.setText(physicaldetailsAndIntcomm.getMTRTR_TESTINGFEERECEIPTNO());

                et_meter_received_by.setText(physicaldetailsAndIntcomm.getMTRTR_MTRRECEIVEDBY());

                strMeterNo = physicaldetailsAndIntcomm.getMIA_METER_NO();

                if(physicaldetailsAndIntcomm.getMIA_ISMETERTESTED()!=null) {
                    if (physicaldetailsAndIntcomm.getMIA_ISMETERTESTED().equalsIgnoreCase("Y")) {
                        strMeterTested = "Y";
                    } else if (physicaldetailsAndIntcomm.getMIA_ISMETERTESTED().equalsIgnoreCase("N")) {
                        strMeterTested = "N";
                    }
                }

                if(physicaldetailsAndIntcomm.getMIA_TEST_RESULTS()!=null) {
                    strTestResult=physicaldetailsAndIntcomm.getMIA_TEST_RESULTS();
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

                str_overhead_tank_capacity = String.valueOf(physicaldetailsAndIntcomm.getMIA_OHTANK_CAPACITY());
                str_ground_tank_capacity = String.valueOf(physicaldetailsAndIntcomm.getMIA_GROUNDTANK_CAPACITY());
                str_under_ground_sump_capacity = String.valueOf(physicaldetailsAndIntcomm.getMIA_UGSUMP_CAPACITY());
                str_ground_tank_nos = String.valueOf(physicaldetailsAndIntcomm.getMIA_GROUNDTANK_QTY());
                str_under_ground_sump_nos = String.valueOf(physicaldetailsAndIntcomm.getMIA_UGSUMP_QTY());
                str_overhead_tank_nos = String.valueOf(physicaldetailsAndIntcomm.getMIA_OHTANK_QTY());

            }
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_consumer_back:
                submitINAData("N");
                break;
            case R.id.btn_submit_consumer: {

                PreferenceServicesINA.getInstance().setSRM_S_NAME(edtConsumerName.getText().toString());
                PreferenceServicesINA.getInstance().setSRM_B_ADDRESS2(edtConsumerAddress.getText().toString());
                PreferenceServicesINA.getInstance().setSRM_S_MOBILE_NO(edtContactNumber.getText().toString());
                PreferenceServicesINA.getInstance().setTestingFeeReceiptNo(et_testing_fee_receipt_no.getText().toString());
                PreferenceServicesINA.getInstance().setMeterReceivedMTL(et_meter_received_in_mtl_on.getText().toString());
                PreferenceServicesINA.getInstance().setMeterReceivedBy(et_meter_received_by.getText().toString());

                ((InternalNetworkAuditActivity) getActivity()).onClickNext();
                InternalNetworkAuditActivity.animationOnArrow();
            }
            break;
        }
    }

    private void submitINAData(String isSubmitOrSave) {

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
        params[2] = strMeterNo;
        params[3] = strMeterMake;
        params[4] = strMeterSize;
        params[5] = strMeterTested;
        params[6] = strTestResult;
        params[7] = strMeterReading;
        params[8] = strSealStatus;
        params[9] = strMeterStatusId;
        params[10] = str_overhead_tank_nos;
        params[11] = str_overhead_tank_capacity;
        params[12] = str_ground_tank_nos;
        params[13] = str_ground_tank_capacity;
        params[14] = str_under_ground_sump_nos;
        params[15] = str_under_ground_sump_capacity;
        params[16] = strOverHeadTankCondition;
        params[17] = strGroundTankCondition;
        params[18] = strSuctionPumpCondition;
        params[19] = strUGSumpCondition;
        params[20] = strLeakFoundMeter;
        params[21] = strLeadFoundInHouse;
        params[22] = strFloatingValueAlv;
        params[23] = str_details_of_observation;
        params[24] = str_recomondations_to_customer;
        params[25] = empCode;
        params[26] = empcode2;
        params[27] = et_meter_received_by.getText().toString();
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

                Log.e("PARAMS", Arrays.toString(params));

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

                if (jsonResponse.equalsIgnoreCase("Success")) {
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
