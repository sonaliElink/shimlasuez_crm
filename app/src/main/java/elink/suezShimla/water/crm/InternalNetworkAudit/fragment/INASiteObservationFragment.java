package elink.suezShimla.water.crm.InternalNetworkAudit.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.InternalNetworkAudit.Model.Complaint;
import elink.suezShimla.water.crm.InternalNetworkAudit.Model.MeterConnected;
import elink.suezShimla.water.crm.InternalNetworkAudit.Model.PhysicaldetailsAndIntcomm;
import elink.suezShimla.water.crm.InternalNetworkAudit.activity.InternalNetworkAuditActivity;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGMeterStatusModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Utils.PreferenceServicesINA;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;
import elink.suezShimla.water.crm.constant.AppConstant;
import io.realm.RealmResults;

public class INASiteObservationFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {


    String strMeterNo = "", strMeterMake = "", strMeterSize = "", strMeterTested = "", strTestResult = "", strMeterReading = "",
            strSealStatus = "", str_overhead_tank_capacity = "", str_ground_tank_capacity = "", str_under_ground_sump_capacity = "",
            str_ground_tank_nos = "", str_under_ground_sump_nos = "", str_overhead_tank_nos = "", strOverHeadTankCondition = "",
            strGroundTankCondition = "", strSuctionPumpCondition = "", strUGSumpCondition = "", strLeakFoundMeter = "",
            strLeadFoundInHouse = "", strFloatingValueAlv = "", str_details_of_observation = "", str_recomondations_to_customer = "",
            strMeterStatusId = "", str_meter_received_by;

    String isSubmitOrSave = "", jsonResponse = "", consumerNo = "", complaintNo = "", finalStatusName = "";
    private Invoke invServices;
    private Gson gson;
    private Context mCon;
    private ConnectionDetector connection;
    private MaterialDialog progress;
    Complaint complaint;

    private EditText et_details_of_observation;
    private EditText et_recomondations_to_customer;
    private EditText edtMeterSealNo, et_meter_reading, edtMeterNumber;

    private ArrayList<String> sealStatusList = new ArrayList<>();
    private ArrayList<String> overHeadTankConnectionList = new ArrayList<>();
    private ArrayList<String> suctionPumpConnectionList = new ArrayList<>();
    private ArrayList<String> leakFoundAfterMeterList = new ArrayList<>();
    private ArrayList<String> leakFoundInsideHouseList = new ArrayList<>();
    private ArrayList<String> floatingValueAvailableList = new ArrayList<>();

    private RealmOperations realmOperations;

    private AppCompatSpinner sealStatusSpinner, overHeadTankConditionSpinner,
            suctionPumpConditionSpinner, groundTankConditionSpinner, uGSumpConditionSpinner, leakFoundAfterMeterSpinner,
            leakFoundInsideHouseSpinner, floatingValveAvailableSpinner, meterStatusSpinner;

    private KeyGenerator keyGenerator;
    private SecretKey secretKey;
    private byte[] IV = new byte[16];
    private SecureRandom random;
    AesAlgorithm aes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_ina_site_observation, container, false);
        // prevent ss and hide content when app is on background
     //   getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mCon = getActivity();
        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();

        realmOperations = new RealmOperations(mCon);

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

        et_details_of_observation = rootView.findViewById(R.id.et_details_of_observation);
        et_recomondations_to_customer = rootView.findViewById(R.id.et_recomondations_to_customer);
        edtMeterSealNo = rootView.findViewById(R.id.edtMeterSealNo);
        et_meter_reading = rootView.findViewById(R.id.et_meter_reading);
        edtMeterNumber = rootView.findViewById(R.id.edtMeterNumber);

        EditText etApplicationNumber = rootView.findViewById(R.id.etApplicationNumber);
        EditText etComplaintNo = rootView.findViewById(R.id.etComplaintNo);

        etApplicationNumber.setText(consumerNo);
        etComplaintNo.setText(complaintNo);

        sealStatusSpinner = rootView.findViewById(R.id.sealStatusSpinner);
        overHeadTankConditionSpinner = rootView.findViewById(R.id.overHeadTankConditionSpinner);
        suctionPumpConditionSpinner = rootView.findViewById(R.id.suctionPumpConditionSpinner);
        groundTankConditionSpinner = rootView.findViewById(R.id.groundTankConditionSpinner);
        uGSumpConditionSpinner = rootView.findViewById(R.id.uGSumpConditionSpinner);
        leakFoundAfterMeterSpinner = rootView.findViewById(R.id.leakFoundAfterMeterSpinner);
        leakFoundInsideHouseSpinner = rootView.findViewById(R.id.leakFoundInsideHouseSpinner);
        floatingValveAvailableSpinner = rootView.findViewById(R.id.floatingValveAvailableSpinner);
        meterStatusSpinner = rootView.findViewById(R.id.meterStatusSpinner);

        sealStatusSpinner.setOnItemSelectedListener(this);
        overHeadTankConditionSpinner.setOnItemSelectedListener(this);
        suctionPumpConditionSpinner.setOnItemSelectedListener(this);
        groundTankConditionSpinner.setOnItemSelectedListener(this);
        uGSumpConditionSpinner.setOnItemSelectedListener(this);
        leakFoundAfterMeterSpinner.setOnItemSelectedListener(this);
        leakFoundInsideHouseSpinner.setOnItemSelectedListener(this);
        floatingValveAvailableSpinner.setOnItemSelectedListener(this);
        meterStatusSpinner.setOnItemSelectedListener(this);

        spinnerListValue();

        setSpinnerValue();

        getData();


    }

    private void getData() {

        if (complaint.getMeterConnected() != null && complaint.getMeterConnected().size() > 0) {
            for (MeterConnected meterConnected : complaint.getMeterConnected()) {

                edtMeterSealNo.setText(meterConnected.getSealNo());

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

                if (physicaldetailsAndIntcomm.getMIA_ISMETERTESTED() != null) {

                    if (physicaldetailsAndIntcomm.getMIA_ISMETERTESTED().equalsIgnoreCase("Y")) {
                        strMeterTested = "Y";
                    } else if (physicaldetailsAndIntcomm.getMIA_ISMETERTESTED().equalsIgnoreCase("N")) {
                        strMeterTested = "N";
                    }
                    if (physicaldetailsAndIntcomm.getMIA_TEST_RESULTS() != null) {
                        strTestResult = physicaldetailsAndIntcomm.getMIA_TEST_RESULTS();
                    }
                }
                str_meter_received_by = physicaldetailsAndIntcomm.getMTRTR_MTRRECEIVEDBY();

                et_details_of_observation.setText(physicaldetailsAndIntcomm.getMIA_DETAILSOFOBSRV());
                et_recomondations_to_customer.setText(physicaldetailsAndIntcomm.getMIA_RETO_CUSTOMER());

                et_meter_reading.setText(physicaldetailsAndIntcomm.getMIA_METER_READING());
                edtMeterNumber.setText(physicaldetailsAndIntcomm.getMIA_METER_NO());

                str_overhead_tank_capacity = String.valueOf(physicaldetailsAndIntcomm.getMIA_OHTANK_CAPACITY());
                str_ground_tank_capacity = String.valueOf(physicaldetailsAndIntcomm.getMIA_GROUNDTANK_CAPACITY());
                str_under_ground_sump_capacity = String.valueOf(physicaldetailsAndIntcomm.getMIA_UGSUMP_CAPACITY());
                str_ground_tank_nos = String.valueOf(physicaldetailsAndIntcomm.getMIA_GROUNDTANK_QTY());
                str_under_ground_sump_nos = String.valueOf(physicaldetailsAndIntcomm.getMIA_UGSUMP_QTY());
                str_overhead_tank_nos = String.valueOf(physicaldetailsAndIntcomm.getMIA_OHTANK_QTY());

                if (physicaldetailsAndIntcomm.getMIA_METER_STATUS_ID() != null) {
                    int finalStatusID = Integer.parseInt(physicaldetailsAndIntcomm.getMIA_METER_STATUS_ID());

                    if (!finalStatusName.equalsIgnoreCase("Select")) {
                        RealmResults<MMGMeterStatusModel> mmgMeterStatusModel = realmOperations.fetchMeterStatusId(finalStatusID);
                        for (int i = 0; i < mmgMeterStatusModel.size(); i++) {
                            finalStatusName = String.valueOf(mmgMeterStatusModel.get(i).getMSM_METERSTATUS_NAME());
                        }
                    }

                    if (finalStatusName.equalsIgnoreCase("Normal Meter")) {
                        meterStatusSpinner.setSelection(1);
                    } else if (finalStatusName.equalsIgnoreCase("Faulty Meter")) {
                        meterStatusSpinner.setSelection(2);
                    } else if (finalStatusName.equalsIgnoreCase("Meter Inaccessible")) {
                        meterStatusSpinner.setSelection(3);
                    } else if (finalStatusName.equalsIgnoreCase("Meter Missing")) {
                        meterStatusSpinner.setSelection(4);
                    } else {
                        meterStatusSpinner.setSelection(0);
                    }
                }

                //seal status
                if (physicaldetailsAndIntcomm.getMIA_METERSEAL_STATUS() != null) {
                    if (physicaldetailsAndIntcomm.getMIA_METERSEAL_STATUS().equalsIgnoreCase("G")) {
                        sealStatusSpinner.setSelection(1);
                    } else if (physicaldetailsAndIntcomm.getMIA_METERSEAL_STATUS().equalsIgnoreCase("D")) {
                        sealStatusSpinner.setSelection(2);
                    } else if (physicaldetailsAndIntcomm.getMIA_METERSEAL_STATUS().equalsIgnoreCase("0")) {
                        sealStatusSpinner.setSelection(0);
                    }
                }

                //overhead tank condition
                if (physicaldetailsAndIntcomm.getMIA_OHTANK_CONDITION() != null) {
                    if (physicaldetailsAndIntcomm.getMIA_OHTANK_CONDITION().equalsIgnoreCase("G")) {
                        overHeadTankConditionSpinner.setSelection(1);
                    } else if (physicaldetailsAndIntcomm.getMIA_OHTANK_CONDITION().equalsIgnoreCase("L")) {
                        overHeadTankConditionSpinner.setSelection(2);
                    } else if (physicaldetailsAndIntcomm.getMIA_OHTANK_CONDITION().equalsIgnoreCase("O")) {
                        overHeadTankConditionSpinner.setSelection(3);
                    } else if (physicaldetailsAndIntcomm.getMIA_OHTANK_CONDITION().equalsIgnoreCase("0")) {
                        overHeadTankConditionSpinner.setSelection(0);
                    }
                }

                //suction pump condition
                if (physicaldetailsAndIntcomm.getMIA_SUCTIONPUMP_CONDITION() != null) {
                    if (physicaldetailsAndIntcomm.getMIA_SUCTIONPUMP_CONDITION().equalsIgnoreCase("G")) {
                        suctionPumpConditionSpinner.setSelection(1);
                    } else if (physicaldetailsAndIntcomm.getMIA_SUCTIONPUMP_CONDITION().equalsIgnoreCase("B")) {
                        suctionPumpConditionSpinner.setSelection(2);
                    } else if (physicaldetailsAndIntcomm.getMIA_SUCTIONPUMP_CONDITION().equalsIgnoreCase("0")) {
                        suctionPumpConditionSpinner.setSelection(0);
                    }
                }

                //ground tank condition
                if (physicaldetailsAndIntcomm.getMIA_GROUNDTANK_CONDITION() != null) {
                    if (physicaldetailsAndIntcomm.getMIA_GROUNDTANK_CONDITION().equalsIgnoreCase("G")) {
                        groundTankConditionSpinner.setSelection(1);
                    } else if (physicaldetailsAndIntcomm.getMIA_GROUNDTANK_CONDITION().equalsIgnoreCase("L")) {
                        groundTankConditionSpinner.setSelection(2);
                    } else if (physicaldetailsAndIntcomm.getMIA_GROUNDTANK_CONDITION().equalsIgnoreCase("O")) {
                        groundTankConditionSpinner.setSelection(3);
                    } else if (physicaldetailsAndIntcomm.getMIA_GROUNDTANK_CONDITION().equalsIgnoreCase("0")) {
                        groundTankConditionSpinner.setSelection(0);
                    }
                }

                //ug sump condition
                if (physicaldetailsAndIntcomm.getMIA_UGSUMP_CONDITION() != null) {
                    if (physicaldetailsAndIntcomm.getMIA_UGSUMP_CONDITION().equalsIgnoreCase("G")) {
                        uGSumpConditionSpinner.setSelection(1);
                    } else if (physicaldetailsAndIntcomm.getMIA_UGSUMP_CONDITION().equalsIgnoreCase("L")) {
                        uGSumpConditionSpinner.setSelection(2);
                    } else if (physicaldetailsAndIntcomm.getMIA_UGSUMP_CONDITION().equalsIgnoreCase("O")) {
                        uGSumpConditionSpinner.setSelection(3);
                    } else if (physicaldetailsAndIntcomm.getMIA_UGSUMP_CONDITION().equalsIgnoreCase("0")) {
                        uGSumpConditionSpinner.setSelection(0);
                    }
                }

                //leak found after meter
                if (physicaldetailsAndIntcomm.getMIA_LEAKFOUND_AFTERMETER() != null) {
                    if (physicaldetailsAndIntcomm.getMIA_LEAKFOUND_AFTERMETER().equalsIgnoreCase("F")) {
                        leakFoundAfterMeterSpinner.setSelection(1);
                    } else if (physicaldetailsAndIntcomm.getMIA_LEAKFOUND_AFTERMETER().equalsIgnoreCase("IN")) {
                        leakFoundAfterMeterSpinner.setSelection(2);
                    } else if (physicaldetailsAndIntcomm.getMIA_LEAKFOUND_AFTERMETER().equalsIgnoreCase("SP")) {
                        leakFoundAfterMeterSpinner.setSelection(3);
                    } else if (physicaldetailsAndIntcomm.getMIA_LEAKFOUND_AFTERMETER().equalsIgnoreCase("0")) {
                        leakFoundAfterMeterSpinner.setSelection(0);
                    }
                }

                //leak found inside house
                if (physicaldetailsAndIntcomm.getMIA_LEAKFOUND_INHOUSE() != null) {
                    if (physicaldetailsAndIntcomm.getMIA_LEAKFOUND_INHOUSE().equalsIgnoreCase("STOPCOCKS")) {
                        leakFoundInsideHouseSpinner.setSelection(1);
                    } else if (physicaldetailsAndIntcomm.getMIA_LEAKFOUND_INHOUSE().equalsIgnoreCase("SINKS")) {
                        leakFoundInsideHouseSpinner.setSelection(2);
                    } else if (physicaldetailsAndIntcomm.getMIA_LEAKFOUND_INHOUSE().equalsIgnoreCase("TAPS")) {
                        leakFoundInsideHouseSpinner.setSelection(3);
                    } else if (physicaldetailsAndIntcomm.getMIA_LEAKFOUND_INHOUSE().equalsIgnoreCase("0")) {
                        leakFoundInsideHouseSpinner.setSelection(0);
                    }
                }

                //floating value
                if (physicaldetailsAndIntcomm.getMIA_FLOATINGVALVE_AVLBL() != null) {
                    if (physicaldetailsAndIntcomm.getMIA_FLOATINGVALVE_AVLBL().equalsIgnoreCase("Y")) {
                        floatingValveAvailableSpinner.setSelection(1);
                    } else if (physicaldetailsAndIntcomm.getMIA_FLOATINGVALVE_AVLBL().equalsIgnoreCase("N")) {
                        floatingValveAvailableSpinner.setSelection(2);
                    } else if (physicaldetailsAndIntcomm.getMIA_FLOATINGVALVE_AVLBL().equalsIgnoreCase("0")) {
                        floatingValveAvailableSpinner.setSelection(0);
                    }
                }
            }
        }

    }

    private void setSpinnerValue() {

        ArrayAdapter arrayAdapterOverHeadTankConnection = new ArrayAdapter(mCon, R.layout.simple_spinner_item, overHeadTankConnectionList);
        arrayAdapterOverHeadTankConnection.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        overHeadTankConditionSpinner.setAdapter(arrayAdapterOverHeadTankConnection);


        ArrayAdapter arrayAdapterSuctionPumpConnection = new ArrayAdapter(mCon, R.layout.simple_spinner_item, suctionPumpConnectionList);
        arrayAdapterSuctionPumpConnection.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        suctionPumpConditionSpinner.setAdapter(arrayAdapterSuctionPumpConnection);

        ArrayAdapter arrayAdapterGroundTank = new ArrayAdapter(mCon, R.layout.simple_spinner_item, overHeadTankConnectionList);
        arrayAdapterGroundTank.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        groundTankConditionSpinner.setAdapter(arrayAdapterGroundTank);

        ArrayAdapter arrayAdapterUGSumpCondition = new ArrayAdapter(mCon, R.layout.simple_spinner_item, overHeadTankConnectionList);
        arrayAdapterUGSumpCondition.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        uGSumpConditionSpinner.setAdapter(arrayAdapterUGSumpCondition);

        ArrayAdapter arrayAdapterLeakFoundAfterMeter = new ArrayAdapter(mCon, R.layout.simple_spinner_item, leakFoundAfterMeterList);
        arrayAdapterLeakFoundAfterMeter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        leakFoundAfterMeterSpinner.setAdapter(arrayAdapterLeakFoundAfterMeter);

        ArrayAdapter arrayAdapterLeakFoundInsideHouse = new ArrayAdapter(mCon, R.layout.simple_spinner_item, leakFoundInsideHouseList);
        arrayAdapterLeakFoundInsideHouse.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        leakFoundInsideHouseSpinner.setAdapter(arrayAdapterLeakFoundInsideHouse);

        ArrayAdapter arrayAdapterFloatingValueAvailable = new ArrayAdapter(mCon, R.layout.simple_spinner_item, floatingValueAvailableList);
        arrayAdapterFloatingValueAvailable.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        floatingValveAvailableSpinner.setAdapter(arrayAdapterFloatingValueAvailable);

        ArrayAdapter arrayAdapterSelaStatus = new ArrayAdapter(mCon, R.layout.simple_spinner_item, sealStatusList);
        arrayAdapterSelaStatus.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sealStatusSpinner.setAdapter(arrayAdapterSelaStatus);

        ArrayList<String> finalStatusName = new ArrayList<>();
        finalStatusName = realmOperations.fetchMeterStatusDetails("1");
        ArrayList<String> finalStatusNameList = new ArrayList<>();
        finalStatusNameList.add("Select");
        finalStatusNameList.addAll(finalStatusName);

        ArrayAdapter finalStatusAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, finalStatusNameList);
        finalStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        meterStatusSpinner.setAdapter(finalStatusAdapter);
    }

    private void spinnerListValue() {

        leakFoundAfterMeterList.add("Select");
        leakFoundAfterMeterList.add("Fitting");
        leakFoundAfterMeterList.add("Internal Network");
        leakFoundAfterMeterList.add("Suction Pipe");

        leakFoundInsideHouseList.add("Select");
        leakFoundInsideHouseList.add("STOPCOCKS");
        leakFoundInsideHouseList.add("SINKS");
        leakFoundInsideHouseList.add("TAPS");

        sealStatusList.add("Select");
        sealStatusList.add("Good");
        sealStatusList.add("Damaged");

        overHeadTankConnectionList.add("Select");
        overHeadTankConnectionList.add("Good");
        overHeadTankConnectionList.add("Leaking");
        overHeadTankConnectionList.add("OverFlow");

        suctionPumpConnectionList.add("Select");
        suctionPumpConnectionList.add("Good");
        suctionPumpConnectionList.add("Bad");

        floatingValueAvailableList.add("Select");
        floatingValueAvailableList.add("Yes");
        floatingValueAvailableList.add("No");


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_consumer_back:
                submitINAData("N");
                break;
            case R.id.btn_submit_consumer: {

                if (edtMeterNumber.getText().toString().isEmpty()) {
                    Toast.makeText(mCon, "Please enter meter reading", Toast.LENGTH_SHORT).show();
                } else {

                    PreferenceServicesINA.getInstance().setMIA_METER_NO(edtMeterNumber.getText().toString());
                    PreferenceServicesINA.getInstance().setSEALNO(edtMeterSealNo.getText().toString());
                    PreferenceServicesINA.getInstance().setMIA_METER_READING(et_meter_reading.getText().toString());
                    PreferenceServicesINA.getInstance().setMIA_METERSEAL_STATUS(strSealStatus);
                    PreferenceServicesINA.getInstance().setMIA_OHTANK_CONDITION(strOverHeadTankCondition);
                    PreferenceServicesINA.getInstance().setMIA_SUCTIONPUMP_CONDITION(strSuctionPumpCondition);
                    PreferenceServicesINA.getInstance().setMIA_GROUNDTANK_CONDITION(strGroundTankCondition);
                    PreferenceServicesINA.getInstance().setMIA_UGSUMP_CONDITION(strUGSumpCondition);
                    PreferenceServicesINA.getInstance().setMIA_LEAKFOUND_AFTERMETER(strLeakFoundMeter);
                    PreferenceServicesINA.getInstance().setMIA_LEAKFOUND_INHOUSE(strLeadFoundInHouse);
                    PreferenceServicesINA.getInstance().setMIA_FLOATINGVALVE_AVLBL(strFloatingValueAlv);
                    PreferenceServicesINA.getInstance().setMIA_METER_STATUS_ID(strMeterStatusId);
                    PreferenceServicesINA.getInstance().setMIA_DETAILSOFOBSRV(et_details_of_observation.getText().toString());
                    PreferenceServicesINA.getInstance().setMIA_RETO_CUSTOMER(et_recomondations_to_customer.getText().toString());

                    ((InternalNetworkAuditActivity) getActivity()).onClickNext();
                    InternalNetworkAuditActivity.animationOnArrow();
                }
            }
            break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if (sealStatusSpinner.getSelectedItem().toString().equalsIgnoreCase("Good")) {
            strSealStatus = "G";
        } else if (sealStatusSpinner.getSelectedItem().toString().equalsIgnoreCase("Damaged")) {
            strSealStatus = "D";
        } else {
            strSealStatus = "0";
        }
        if (overHeadTankConditionSpinner.getSelectedItem().toString().equalsIgnoreCase("Good")) {
            strOverHeadTankCondition = "G";
        } else if (overHeadTankConditionSpinner.getSelectedItem().toString().equalsIgnoreCase("Leaking")) {
            strOverHeadTankCondition = "L";
        } else if (overHeadTankConditionSpinner.getSelectedItem().toString().equalsIgnoreCase("OverFlow")) {
            strOverHeadTankCondition = "O";
        } else {
            strOverHeadTankCondition = "0";
        }
        if (suctionPumpConditionSpinner.getSelectedItem().toString().equalsIgnoreCase("Good")) {
            strSuctionPumpCondition = "G";
        } else if (suctionPumpConditionSpinner.getSelectedItem().toString().equalsIgnoreCase("Bad")) {
            strSuctionPumpCondition = "B";
        } else {
            strSuctionPumpCondition = "0";
        }
        if (groundTankConditionSpinner.getSelectedItem().toString().equalsIgnoreCase("Good")) {
            strGroundTankCondition = "G";
        } else if (groundTankConditionSpinner.getSelectedItem().toString().equalsIgnoreCase("Leaking")) {
            strGroundTankCondition = "L";
        } else if (groundTankConditionSpinner.getSelectedItem().toString().equalsIgnoreCase("OverFlow")) {
            strGroundTankCondition = "O";
        } else {
            strGroundTankCondition = "0";
        }
        if (uGSumpConditionSpinner.getSelectedItem().toString().equalsIgnoreCase("Good")) {
            strUGSumpCondition = "G";
        } else if (uGSumpConditionSpinner.getSelectedItem().toString().equalsIgnoreCase("Leaking")) {
            strUGSumpCondition = "L";
        } else if (uGSumpConditionSpinner.getSelectedItem().toString().equalsIgnoreCase("OverFlow")) {
            strUGSumpCondition = "O";
        } else {
            strUGSumpCondition = "0";
        }

        if (leakFoundAfterMeterSpinner.getSelectedItem().toString().equalsIgnoreCase("Fitting")) {
            strLeakFoundMeter = "F";
        } else if (leakFoundAfterMeterSpinner.getSelectedItem().toString().equalsIgnoreCase("Internal Network")) {
            strLeakFoundMeter = "IN";
        } else if (leakFoundAfterMeterSpinner.getSelectedItem().toString().equalsIgnoreCase("Suction Pipe")) {
            strLeakFoundMeter = "SP";
        } else {
            strLeakFoundMeter = "0";
        }

        if (leakFoundInsideHouseSpinner.getSelectedItem().toString().equalsIgnoreCase("STOPCOCKS")) {
            strLeadFoundInHouse = "STOPCOCKS";
        } else if (leakFoundInsideHouseSpinner.getSelectedItem().toString().equalsIgnoreCase("SINKS")) {
            strLeadFoundInHouse = "SINKS";
        } else if (leakFoundInsideHouseSpinner.getSelectedItem().toString().equalsIgnoreCase("TAPS")) {
            strLeadFoundInHouse = "TAPS";
        } else {
            strLeadFoundInHouse = "0";
        }

        if (floatingValveAvailableSpinner.getSelectedItem().toString().equalsIgnoreCase("Yes")) {
            strFloatingValueAlv = "Y";
        } else if (floatingValveAvailableSpinner.getSelectedItem().toString().equalsIgnoreCase("No")) {
            strFloatingValueAlv = "N";
        } else {
            strFloatingValueAlv = "0";
        }

        finalStatusName = meterStatusSpinner.getSelectedItem().toString();
        if (!finalStatusName.equalsIgnoreCase("Select")) {
            MMGMeterStatusModel mmgMeterStatusModel = realmOperations.fetchMeterStatusByName(finalStatusName);
            strMeterStatusId = String.valueOf(mmgMeterStatusModel.getMSM_METERSTATUS_ID());
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
        params[2] = edtMeterNumber.getText().toString();

        params[3] = PreferenceServicesINA.getInstance().getO_METERTYPE();
        params[4] = PreferenceServicesINA.getInstance().getSRM_CONNECTION_LOAD();

        params[5] = PreferenceServicesINA.getInstance().getMIA_ISMETERTESTED();
        params[6] = PreferenceServicesINA.getInstance().getMIA_TEST_RESULTS();

        params[7] = et_meter_reading.getText().toString();
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
        params[23] = et_details_of_observation.getText().toString();
        params[24] = et_recomondations_to_customer.getText().toString();
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

                Log.e("INASiteObservationPARAMS", Arrays.toString(params));

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
