package elink.suezShimla.water.crm.ConnectionManagement.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.fragment.app.Fragment;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.tiper.MaterialSpinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.ConnectionManagement.activity.SiteVisitListActivityDetails;
import elink.suezShimla.water.crm.ConnectionManagement.model.RequestConsumerBidModel;
import elink.suezShimla.water.crm.ConnectionManagement.model.SiteVisitModel;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGMeterSizeModel;
import elink.suezShimla.water.crm.MessageWindow;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.SizeModel;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;

public class NCCommercialFeasibilityDetailsFragment extends Fragment implements MaterialSpinner.OnItemSelectedListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener, CompoundButton.OnCheckedChangeListener {
    LinearLayout layoutconnectsize, ll_unauthorized_connection_spinner_edit, ll_unauthorized_connection_spinner, ll_civil_cons_with_ccmc_water_spinner_edit, ll_civil_cons_with_ccmc_water_spinner, ll_no_of_dwelling_units_edit,
            ll_no_of_dwelling_units, ll_is_occupier_security_edit, ll_is_occupier_security, ll_connection_purpose_spinner_edit, ll_connection_purpose_spinner, ll_consumer_bid_request;

    MaterialSpinner connection_newSizeSpinner,connection_oldSizeSpinner, unAuthorizedConnectionSpinner, civilConsWithCcmcWaterSpinner, noOfDwellingUnitSpinner, isOccupierSecuritySpinner, governmentEmployeeSpinner, sizeConnectionsSpinner,
            connectionSizeSpinner, meterSizeSpinner, storageCapacitySpinner, gisBidCustomerSpinner, meterLocationSpinner;

    String[] numbers = {"Select", "1", "2", "3", "4", "5"};

    private ArrayAdapter noOfDwellingUnitAdapter, sizeConnectiontAdapter, customerNameAdapter, meterSizeAdapter;

    SiteVisitListActivityDetails activity;

    SiteVisitModel connectionModel, model;
    Button btn_commercial_consumer, btn_commercial_back;

    String unAuthorizedConnectionStr = "", civilConsWithCcmcWaterStr = "", noOfDwellingUnitStr, isOccupierSecurityStr = "", governmentEmployeeStr = "", connectionSizeStr = "", giSiD = "";
    String unAuthorizedConnectionId = "", civilConsWithCcmcWaterId = "", noOfDwellingUnitId = "", connectionSizeId = "", isOccupierSecurityId = "", governmentEmployeeId = "";
    String consumerNumberStr = "", bidStr = "", jsonRequestBidResponce, meterSizeId = "";

    String construction_completed, applied_dwelling_unit_has_no_connection,
            internal_network_available, disposal_of_water_available,
            rainwater_harvesting_system_available, any_existing_connection, storage_capacity_type;
    String selectConnectionSize, selectMeterSize, selectStorageCapacity, add_consumer = "", storageCapacity = "", meterLocation;

    NCCommercialFeasibility NCFD;
    private RealmOperations realmOperations;
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    private Context mCon;

    ArrayList<String> distanceNetworkLineList = new ArrayList<>();

    private SizeModel sizeModel;
    private MMGMeterSizeModel mmgMeterSizeModel;


    Spinner postfield_category;

    ImageButton ib_consumer_bid_request;

    RadioGroup rg_any_existing_connection_in_building_property, rg_applied_dwelling_unit_has_no_connection, rg_construction_completed, rg_internal_network_available, rg_disposal_of_water_available,
            rg_rainwater_harvesting_system_available, rg_storage_capacity;
    RadioButton rb_internal_network_available_yes, rb_internal_network_available_no, rb_any_existing_connection_no, rb_any_existing_connection_yes, rb_construction_completed_yes, rb_construction_completed_no, rb_applied_dwelling_unit_has_no_connection_no, rb_applied_dwelling_unit_has_no_connection_yes,
            rb_disposal_of_water_available_no, rb_disposal_of_water_available_yes, rb_rainwater_harvesting_system_available_no, rb_rainwater_harvesting_system_available_yes, rb_storage_capacity_no, rb_storage_capacity_yes;
    int selectedID;
    EditText et_consumer, et_bid, et_gis_bid;
    TextInputEditText et_storage_capacity, et_ugr_storage_capacity;

    private MaterialDialog progressRequest;

    LinearLayout ll_any_existing_connection, ll_consumer_details;

    String SRM_SERVICE_NO = "",addapplicationtype="", SRM_S_GIS_ID = "", SRM_S_FLOOR_NO = "", FLG = "", listString = "";
    TextView tv_consumer_details, tv_add_consumer_details;
    String[] valid;

    AppCompatCheckBox ohCheckBox, ugrCheckBox;

    TextInputLayout ugrInputLayout, ohInputLayout;

    Button btn_submit_connection, btn_connection_back;

    ArrayList<RequestConsumerBidModel> gisBidConsumerlist;
    ArrayList<String> gisBidConsumerNamelist, gisBidConsumerAddresslist;
    String consumer, address;
    String STARTTIME = "", ALERTSTARTTIME = "";
    String rtimem = "Reading can be taken between";
    boolean submitData = false;
    String ENDTIME = "", ALERTENDTIME = "", time = "",add;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // prevent ss and hide content when app is on background
     //   getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        View rootView = inflater.inflate(R.layout.fragment_commercial_feasibility, container, false);

        mCon = getActivity();

        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();
        realmOperations = new RealmOperations(mCon);


        activity = (SiteVisitListActivityDetails) getActivity();
        model = activity.getSiteVisitDataData();

        unAuthorizedConnectionStr = model.getAM_APP_ISAUTH_CONNECTION();
        civilConsWithCcmcWaterStr = model.getAM_APP_CIVIL_CONS_CCMC_WATER();
        noOfDwellingUnitStr = model.getAM_APP_NO_OF_DWELLING_UNITS();
        Log.e("noOfDwellingUnitStr", noOfDwellingUnitStr);
        isOccupierSecurityStr = model.getAM_APP_ISOCCUPIER_SECURITY();
        governmentEmployeeStr = model.getAM_APP_ISMSCDCL_EMPLOYEE_ID();
        connectionSizeStr = model.getCONNECTION_SIZE();
        giSiD = model.getAM_APP_S_GIS_ID();
        addapplicationtype = model.getAM_AAPP_NO_TYPE();
        // giSiD = "75_05_075";

        //showDialog();
        initializeViews(rootView);
        // getFirmTypeRequest();

        if (addapplicationtype.equalsIgnoreCase("12")) {
            noOfDwellingUnitSpinner.setEnabled(false);


            meterSizeSpinner.setEnabled(false);
            storageCapacitySpinner.setEnabled(false);
            et_ugr_storage_capacity.setEnabled(false);
            meterLocationSpinner.setEnabled(false);






        }

            return rootView;
    }
    private void checkTimes(Date startime, Date current_time, Date endtime) {

        boolean isInBetween = false;
        if (endtime.after(startime)) {
            if (startime.before(current_time) && endtime.after(current_time)) {
                isInBetween = true;
            }
        } else if (current_time.after(startime) || current_time.before(endtime)) {
            isInBetween = true;
        }
        if (isInBetween) {
            submitData = true;
        } /*else {

            timeoutAlertBox();
        }*/

    }
    private Date dateParsing(String dtStart) {
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        try {
            date = format.parse(dtStart);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    private void timeoutAlertBox() {
        MaterialDialog dialog = new MaterialDialog.Builder(mCon)
                .title(R.string.alert)
                .titleColorRes(R.color.red_500)
                .content(rtimem + " " + ALERTSTARTTIME + " " + "to" + " " + ALERTENDTIME)
                .contentColor(this.getResources().getColor(R.color.colorPrimary))
                .canceledOnTouchOutside(false)
                .positiveText(R.string.ok)

                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        Intent intent = new Intent(mCon, SplashScreen.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        getActivity().finish();
                        dialog.dismiss();
                    }
                }).show();
    }
    private void initializeViews(View rootView) {
        activity = (SiteVisitListActivityDetails) getActivity();

        unAuthorizedConnectionSpinner = rootView.findViewById(R.id.unAuthorizedConnectionSpinner);
        unAuthorizedConnectionSpinner.setOnItemSelectedListener(this);

        civilConsWithCcmcWaterSpinner = rootView.findViewById(R.id.civilConsWithCcmcWaterSpinner);
        civilConsWithCcmcWaterSpinner.setOnItemSelectedListener(this);

        noOfDwellingUnitSpinner = rootView.findViewById(R.id.noOfDwellingUnitSpinner);
        noOfDwellingUnitSpinner.setOnItemSelectedListener(this);

        meterSizeSpinner = rootView.findViewById(R.id.meterSizeSpinner);
        meterSizeSpinner.setOnItemSelectedListener(this);

        meterLocationSpinner = rootView.findViewById(R.id.meterLocationSpinner);
        meterLocationSpinner.setOnItemSelectedListener(this);

        storageCapacitySpinner = rootView.findViewById(R.id.storageCapacitySpinner);
        storageCapacitySpinner.setOnItemSelectedListener(this);

        gisBidCustomerSpinner = rootView.findViewById(R.id.gisBidCustomerSpinner);
        gisBidCustomerSpinner.setOnItemSelectedListener(this);

        isOccupierSecuritySpinner = rootView.findViewById(R.id.isOccupierSecuritySpinner);
        isOccupierSecuritySpinner.setOnItemSelectedListener(this);

        governmentEmployeeSpinner = rootView.findViewById(R.id.governmentEmployeeSpinner);
        governmentEmployeeSpinner.setOnItemSelectedListener(this);


        connectionSizeSpinner = rootView.findViewById(R.id.connectionSizeSpinner);
        connectionSizeSpinner.setOnItemSelectedListener(this);

        connection_oldSizeSpinner = rootView.findViewById(R.id.connection_oldSizeSpinner);
        connection_oldSizeSpinner.setOnItemSelectedListener(this);


        btn_commercial_consumer = rootView.findViewById(R.id.btn_commercial_consumer);
        btn_commercial_consumer.setOnClickListener(this);

        btn_commercial_back = rootView.findViewById(R.id.btn_commercial_back);
        btn_commercial_back.setOnClickListener(this);


        ohCheckBox = rootView.findViewById(R.id.ohCheckBox);
        ohCheckBox.setOnCheckedChangeListener(this);


        ugrCheckBox = rootView.findViewById(R.id.ugrCheckBox);

        ugrCheckBox.setOnCheckedChangeListener(this);

        ugrInputLayout = rootView.findViewById(R.id.ugrInputLayout);
        ohInputLayout = rootView.findViewById(R.id.ohInputLayout);


        rg_construction_completed = rootView.findViewById(R.id.rg_construction_completed);
        rg_applied_dwelling_unit_has_no_connection = rootView.findViewById(R.id.rg_applied_dwelling_unit_has_no_connection);
        rg_any_existing_connection_in_building_property = rootView.findViewById(R.id.rg_any_existing_connection_in_building_property);
        rg_internal_network_available = rootView.findViewById(R.id.rg_internal_network_available);
        rg_disposal_of_water_available = rootView.findViewById(R.id.rg_disposal_of_water_available);
        rg_rainwater_harvesting_system_available = rootView.findViewById(R.id.rg_rainwater_harvesting_system_available);

        rg_construction_completed.setOnCheckedChangeListener(this);
        rg_applied_dwelling_unit_has_no_connection.setOnCheckedChangeListener(this);
        rg_any_existing_connection_in_building_property.setOnCheckedChangeListener(this);
        rg_internal_network_available.setOnCheckedChangeListener(this);
        rg_disposal_of_water_available.setOnCheckedChangeListener(this);
        rg_rainwater_harvesting_system_available.setOnCheckedChangeListener(this);
        //  rg_storage_capacity.setOnCheckedChangeListener(this);

        rb_any_existing_connection_no = rootView.findViewById(R.id.rb_any_existing_connection_no);
        rb_any_existing_connection_yes = rootView.findViewById(R.id.rb_any_existing_connection_yes);
        rb_internal_network_available_no = rootView.findViewById(R.id.rb_internal_network_available_no);
        rb_internal_network_available_yes = rootView.findViewById(R.id.rb_internal_network_available_yes);

        rb_applied_dwelling_unit_has_no_connection_no = rootView.findViewById(R.id.rb_applied_dwelling_unit_has_no_connection_no);
        rb_applied_dwelling_unit_has_no_connection_yes = rootView.findViewById(R.id.rb_applied_dwelling_unit_has_no_connection_yes);

        rb_construction_completed_yes = rootView.findViewById(R.id.rb_construction_completed_yes);
        rb_construction_completed_no = rootView.findViewById(R.id.rb_construction_completed_no);

        rb_applied_dwelling_unit_has_no_connection_no = rootView.findViewById(R.id.rb_applied_dwelling_unit_has_no_connection_no);
        rb_applied_dwelling_unit_has_no_connection_yes = rootView.findViewById(R.id.rb_applied_dwelling_unit_has_no_connection_yes);

        rb_disposal_of_water_available_no = rootView.findViewById(R.id.rb_disposal_of_water_available_no);
        rb_disposal_of_water_available_yes = rootView.findViewById(R.id.rb_disposal_of_water_available_yes);

        rb_rainwater_harvesting_system_available_no = rootView.findViewById(R.id.rb_rainwater_harvesting_system_available_no);
        rb_rainwater_harvesting_system_available_yes = rootView.findViewById(R.id.rb_rainwater_harvesting_system_available_yes);

      /*  rb_storage_capacity_no = rootView.findViewById(R.id.rb_storage_capacity_no);
        rb_storage_capacity_yes = rootView.findViewById(R.id.rb_storage_capacity_yes);
*/

        et_gis_bid = rootView.findViewById(R.id.et_gis_bid);
        et_storage_capacity = rootView.findViewById(R.id.et_storage_capacity);
        et_storage_capacity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String tmp = s.toString().trim();
                if (tmp.length() == 1 && tmp.equals("0"))
                    s.clear();
            }
        });
        et_ugr_storage_capacity = rootView.findViewById(R.id.et_ugr_storage_capacity);
        et_ugr_storage_capacity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String tmp = s.toString().trim();
                if (tmp.length() == 1 && tmp.equals("0"))
                    s.clear();
            }
        });
        //  et_meter_location = rootView.findViewById(R.id.et_meter_location);
        // tv_add_consumer = rootView.findViewById(R.id.tv_add_consumer);
        tv_consumer_details = rootView.findViewById(R.id.tv_consumer_details);
        tv_add_consumer_details = rootView.findViewById(R.id.tv_add_consumer_details);
        ll_any_existing_connection = rootView.findViewById(R.id.ll_any_existing_connection);
        ll_consumer_details = rootView.findViewById(R.id.ll_consumer_details);
        ib_consumer_bid_request = rootView.findViewById(R.id.ib_consumer_bid_request);
        ib_consumer_bid_request.setOnClickListener(this);


        btn_submit_connection = rootView.findViewById(R.id.btn_submit_connection);
        btn_submit_connection.setOnClickListener(this);


        btn_connection_back = rootView.findViewById(R.id.btn_connection_back);
        btn_connection_back.setOnClickListener(this);


        gisBidConsumerlist = new ArrayList<RequestConsumerBidModel>();

        //    rb_applied_dwelling_no.setOnCheckedChangeListener(this);


        setUnAuthorizedConnectionDropDown();
        setCivilConsWithCcmcWaterDropDown();
        setNumberOfDwellingDropDown();
        setIsOccupierSecurityDropDown();
        //  setGovernmentEmployeeDropDown();
        setConnectionSize();
        setStorageCapacityDropDown();
        setMterLocationSpinnerDropDown();
        //setNoofConnectionsSpinnerDropDown();
        meterSizeDropdown();
       /* if (giSiD.equalsIgnoreCase("") || (giSiD == null)) {
            ll_any_existing_connection.setVisibility(View.GONE);
            et_gis_bid.setText("");
            tv_consumer_details.setText("");


        } else {
            ll_any_existing_connection.setVisibility(View.VISIBLE);
            rb_any_existing_connection_yes.setChecked(true);
            et_gis_bid.setText(giSiD);
            // gisBidDropdowon(giSiD);

        }*/


    }


    private void meterSizeDropdown() {
        ArrayList<String> meterSizeName = new ArrayList<>();
        meterSizeName = realmOperations.fetchMeterSize();
        ArrayList<String> meterSizeList = new ArrayList<>();
        meterSizeList.add("Select");
        meterSizeList.addAll(meterSizeName);

        meterSizeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, meterSizeList);
        meterSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        meterSizeSpinner.setAdapter(meterSizeAdapter);
        meterSizeSpinner.setSelection(0);
        meterSizeSpinner.setOnItemSelectedListener(this);
    }

    private void setConnectionSize() {

        ArrayList<String> distanceNetworkLineStringList = new ArrayList<>();
        distanceNetworkLineStringList = realmOperations.fetchDistanceNetworkLineSizeListName();

        distanceNetworkLineList.add("Select");
        distanceNetworkLineList.addAll(distanceNetworkLineStringList);
        sizeConnectiontAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, distanceNetworkLineList);
        sizeConnectiontAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        connectionSizeSpinner.setAdapter(sizeConnectiontAdapter);
        getCategoryPos(connectionSizeStr);
        connectionSizeSpinner.setOnItemSelectedListener(this);


    }

    private void getCategoryPos(String connectionSize) {

        int index = distanceNetworkLineList.indexOf(connectionSize);
        connectionSizeSpinner.setSelection(index);

    }

    /*  private void setGovernmentEmployeeDropDown() {
          ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.choice, android.R.layout.simple_spinner_item);
          adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
          governmentEmployeeSpinner.setAdapter(adapter);
          governmentEmployeeSpinner.setSelection(2);
          governmentEmployeeSpinner.setOnItemSelectedListener(this);

      }*/
    private void setStorageCapacityDropDown() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.storage_capacity, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        storageCapacitySpinner.setAdapter(adapter);
        storageCapacitySpinner.setSelection(0);
        storageCapacitySpinner.setOnItemSelectedListener(this);

    }

    private void setMterLocationSpinnerDropDown() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.meter_location, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        meterLocationSpinner.setAdapter(adapter);
        meterLocationSpinner.setSelection(0);
        meterLocationSpinner.setOnItemSelectedListener(this);

    }

    private void setIsOccupierSecurityDropDown() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.choice, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        isOccupierSecuritySpinner.setAdapter(adapter);
        isOccupierSecuritySpinner.setSelection(1);
        isOccupierSecuritySpinner.setOnItemSelectedListener(this);

    }


    private void setNumberOfDwellingDropDown() {

        ArrayList<String> noOfDwellingUnitStringList = new ArrayList<>();
        noOfDwellingUnitStringList = realmOperations.fetchnumberOfDwellingListName();
        ArrayList<String> numberOfDwellingList = new ArrayList<>();
        numberOfDwellingList.add("Select");
        numberOfDwellingList.addAll(noOfDwellingUnitStringList);

        noOfDwellingUnitAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, numberOfDwellingList);
        noOfDwellingUnitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        noOfDwellingUnitSpinner.setAdapter(noOfDwellingUnitAdapter);
        // noOfDwellingUnitSpinner.setSelection(1);//rupali
        //ff noOfDwellingUnitSpinner.setOnItemSelectedListener(this);

        int index = numberOfDwellingList.indexOf(noOfDwellingUnitAdapter);
        //pinky added
        for (int i = 0; i < numberOfDwellingList.size(); i++) {
            if (noOfDwellingUnitStr.equalsIgnoreCase(numberOfDwellingList.get(i).toString()))
                noOfDwellingUnitSpinner.setSelection(i);
        }
        //pinky added above code
        noOfDwellingUnitSpinner.setOnItemSelectedListener(this);
    }

    private void setCivilConsWithCcmcWaterDropDown() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.choice, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        civilConsWithCcmcWaterSpinner.setAdapter(adapter);
        civilConsWithCcmcWaterSpinner.setSelection(1);
        civilConsWithCcmcWaterSpinner.setOnItemSelectedListener(this);

    }

    private void setUnAuthorizedConnectionDropDown() {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.choice, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unAuthorizedConnectionSpinner.setAdapter(adapter);
        unAuthorizedConnectionSpinner.setSelection(1);
        unAuthorizedConnectionSpinner.setOnItemSelectedListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_commercial_consumer:
                if (validation()) {
                    dataCommercialFeasibility();
                }
                break;
            case R.id.btn_commercial_back:

                backFragmentActivity();


                break;
            case R.id.ib_consumer_bid_request:
                if (validationRequest()) {
                    gisBidDropdowon(giSiD);

                }


                break;

            case R.id.btn_submit_connection:
                if (validation()) {
                    dataCommercialFeasibility();
                }

                break;
            case R.id.btn_connection_back:

                backFragmentActivity();


                break;
            default:
                break;
        }
    }

    private boolean validationRequest() {
        giSiD = et_gis_bid.getText().toString();

        if (giSiD.equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), R.string.gis_bid, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void gisBidDropdowon(String bidStr) {
        String[] params = new String[2];
        params[0] = null;
        params[1] = giSiD;
        //params[0] = "23218164";
        if (connection.isConnectingToInternet()) {

            RequestConsumerBid requestConsumerBid = new RequestConsumerBid();
            requestConsumerBid.execute(params);


        } else {
            Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.ohCheckBox:
                if (ohCheckBox.isChecked()) {
                    ohInputLayout.setVisibility(View.VISIBLE);

                } else {
                    ohInputLayout.setVisibility(View.GONE);
                }
                break;
            case R.id.ugrCheckBox:
                if (ugrCheckBox.isChecked()) {
                    ugrInputLayout.setVisibility(View.VISIBLE);

                } else {
                    ugrInputLayout.setVisibility(View.GONE);
                }
                break;
            default:
                break;

        }

    }


    private class RequestConsumerBid extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            progressRequest = new MaterialDialog.Builder(mCon)
                    .content(R.string.loading)
                    .progress(true, 0)
                    .cancelable(false)
                    .canceledOnTouchOutside(false)
                    .widgetColorRes(R.color.colorPrimary)
                    .show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                String paraName[] = new String[2];
                paraName[0] = "GIS";
                paraName[1] = "Consumer";


                jsonRequestBidResponce = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.CheckSameBldg_Consumer, params, paraName);
                Log.e("jsonResponse", jsonRequestBidResponce);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                progressRequest.dismiss();
                RequestConsumerBidModel[] enums = gson.fromJson(jsonRequestBidResponce, RequestConsumerBidModel[].class);
                gisBidConsumerNamelist = new ArrayList<>();
            /*    if(gisBidConsumerlist.size()==0){
                    tv_consumer_details.setText("");
                    tv_add_consumer_details.setText("");

                }else {*/
                if (enums.length > 0) {
                    gisBidConsumerlist.clear();
                    ll_consumer_details.setVisibility(View.GONE);
                    for (RequestConsumerBidModel requestConsumerBidModels : enums) {
                        String result = requestConsumerBidModels.getQueryStatus();

                        if (result.equalsIgnoreCase("Success")) {
                            RequestConsumerBidModel requestConsumerBidModel = new RequestConsumerBidModel(requestConsumerBidModels.getSRM_SERVICE_NO(), requestConsumerBidModels.getCONSUMER(), requestConsumerBidModels.getADDRESS(), requestConsumerBidModels.getQueryStatus());
                            gisBidConsumerlist.add(requestConsumerBidModel);
                            gisBidConsumerNamelist.add(requestConsumerBidModel.toString());
                             add=requestConsumerBidModel.getADDRESS();
                            MessageWindow.messageWindow(mCon, getResources().getString(R.string.consumer_id_valid), "Alert");
                            if (add.endsWith(",")) {
                                add = add.substring(0, add.length() - 1);
                            }
                            tv_consumer_details.setText(add);


                            tv_consumer_details.setVisibility(View.VISIBLE);

                        } else {
                            ll_consumer_details.setVisibility(View.GONE);
                            tv_consumer_details.setVisibility(View.GONE);
                            tv_add_consumer_details.setText("");
                            MessageWindow.messageWindow(mCon, getResources().getString(R.string.consumer_invalid), "Alert");


                            //Toast.makeText(activity, ""+result, Toast.LENGTH_SHORT).show();
                        }

                    }

                    StringBuilder sbString = new StringBuilder("");

                    //iterate through ArrayList
                    for (String language : gisBidConsumerNamelist) {

                        //append ArrayList element followed by comma
                        sbString.append(language).append(",");
                    }

                    //convert StringBuffer to String
                   // String strList = sbString.toString();
                    String strList = add;

                    //remove last comma from String if you want
                    if (strList.length() > 0)
                        strList = strList.substring(0, strList.length() - 1);

                   // tv_consumer_details.setText(add);
                    //  Log.println(Log.DEBUG,"list",strList);
                  //  loadCustomerName();
                       /* SRM_SERVICE_NO = enums[0].getSRM_SERVICE_NO();
                        SRM_S_GIS_ID = enums[0].getSRM_S_GIS_ID();
                        SRM_S_FLOOR_NO = enums[0].getSRM_S_FLOOR_NO();
*/




             /*           HashSet<String> hashSet = new HashSet<String>();
                        hashSet.addAll(list);
                        list.clear();
                        list.addAll(hashSet);
                        for (String s : list)
                        {
                            listString += s + ",";
                        }
                        String names = "";
                        int last = listString.length() - 1;
                        if (last > 0 && listString.charAt(last) == ',') {
                            names = listString.substring(0, last);
                        }*/
                    //tv_add_consumer.setText(names);


                } else {
                    tv_consumer_details.setText("");
                    tv_add_consumer_details.setText("");
                    //  Toast.makeText(mCon, "No consumer available", Toast.LENGTH_SHORT).show();
                }
                //   }

            } catch (Exception e) {
                Log.e("Exception", e.toString());
                progressRequest.dismiss();
                Toast.makeText(mCon, "Something went wrong", Toast.LENGTH_SHORT).show();
                String error = e.toString();
                ErrorClass.errorData(mCon, "Authentication Fragment", "Send Data to meter Installation", error);
            }
        }
    }

    private void loadCustomerName() {

        gisBidCustomerSpinner.setVisibility(View.VISIBLE);
        customerNameAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, gisBidConsumerlist);
        customerNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gisBidCustomerSpinner.setAdapter(customerNameAdapter);
        gisBidCustomerSpinner.setSelection(0);
        gisBidCustomerSpinner.setOnItemSelectedListener(this);

    }


    private void backFragmentActivity() {
        ((SiteVisitListActivityDetails) getActivity()).onClickPrev();
    }

    private void dataCommercialFeasibility() {
        //    SiteVisitModel siteVisitModel = new SiteVisitModel(unAuthorizedConnectionId, civilConsWithCcmcWaterId, isOccupierSecurityId, governmentEmployeeId);
        SiteVisitModel siteVisitModel = new SiteVisitModel(construction_completed, applied_dwelling_unit_has_no_connection, internal_network_available,
                disposal_of_water_available, rainwater_harvesting_system_available, any_existing_connection,
                add_consumer, noOfDwellingUnitStr, connectionSizeId, meterSizeId,storage_capacity_type ,storageCapacity, meterLocation);

        NCFD.sendCommercialFeasibility(siteVisitModel);
        //  Toast.makeText(getActivity(), getResources().getString(R.string.details_submitted_successfully), Toast.LENGTH_SHORT).show();
        ((SiteVisitListActivityDetails) getActivity()).onClickNext();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (radioGroup.getId()) {
            case R.id.rg_any_existing_connection_in_building_property:
                selectedID = radioGroup.getCheckedRadioButtonId();
                if (selectedID == R.id.rb_any_existing_connection_no) {
                    ll_any_existing_connection.setVisibility(View.GONE);

                    gisBidCustomerSpinner.setVisibility(View.GONE);
                    tv_consumer_details.setText("");
                    tv_add_consumer_details.setText("");
                    // et_gis_bid.setText("");
                }
                if (selectedID == R.id.rb_any_existing_connection_yes) {
                    ll_any_existing_connection.setVisibility(View.VISIBLE);


                }
                break;
            case R.id.rg_construction_completed:
                selectedID = radioGroup.getCheckedRadioButtonId();
                if (selectedID == R.id.rb_construction_completed_yes) {

                }
                if (selectedID == R.id.rb_construction_completed_no) {

                }
                break;
            /*  case R.id.rg_storage_capacity:
                selectedID = radioGroup.getCheckedRadioButtonId();
                if (selectedID == R.id.rb_storage_capacity_yes) {
                    storageCapacitySpinner.setVisibility(View.VISIBLE);


                }
                if(selectedID == R.id.rb_storage_capacity_no){
                    storageCapacitySpinner.setVisibility(View.GONE);

                }
                break;*/
            default:
        }
    }


    public interface NCCommercialFeasibility {
        //        void sendData(String submitStatus, String radiobuttonValStr,  String makerCodeId,String serialNoStr,String installDtStr,String meterSizeStr,String sealNoStr,String pastMeterReadingStr);
        void sendCommercialFeasibility(SiteVisitModel siteVisitModel);
    }


    private boolean validation() {
     /*   unAuthorizedConnectionStr = unAuthorizedConnectionSpinner.getSelectedItem().toString();
        civilConsWithCcmcWaterStr = civilConsWithCcmcWaterSpinner.getSelectedItem().toString();
        isOccupierSecurityStr = isOccupierSecuritySpinner.getSelectedItem().toString();*/

        if (rb_construction_completed_no.isChecked() || rb_construction_completed_yes.isChecked()) {
            if (rb_construction_completed_no.isChecked()) {
                construction_completed = "0";
            } else if (rb_construction_completed_yes.isChecked()) {
                construction_completed = "1";
            }
        } else {
            Toast.makeText(activity, "Please select construction completed ", Toast.LENGTH_SHORT).show();

            return false;
        }
        if (rb_applied_dwelling_unit_has_no_connection_no.isChecked() || rb_applied_dwelling_unit_has_no_connection_yes.isChecked()) {
            if (rb_applied_dwelling_unit_has_no_connection_no.isChecked()) {
                applied_dwelling_unit_has_no_connection = "0";
            } else if (rb_applied_dwelling_unit_has_no_connection_yes.isChecked()) {
                applied_dwelling_unit_has_no_connection = "1";
            }

        } else {
            Toast.makeText(activity, "Please select applied dwelling unit has connection", Toast.LENGTH_SHORT).show();

            return false;
        }

        if (rb_internal_network_available_no.isChecked() || rb_internal_network_available_yes.isChecked()) {
            if (rb_internal_network_available_no.isChecked()) {
                internal_network_available = "0";
            } else if (rb_internal_network_available_yes.isChecked()) {
                internal_network_available = "1";
            }


        } else {
            Toast.makeText(activity, "Please select internal network available", Toast.LENGTH_SHORT).show();

            return false;
        }


        if (rb_disposal_of_water_available_no.isChecked() || rb_disposal_of_water_available_yes.isChecked()) {
            if (rb_disposal_of_water_available_no.isChecked()) {
                disposal_of_water_available = "0";
            } else if (rb_disposal_of_water_available_yes.isChecked()) {
                disposal_of_water_available = "1";
            }


        } else {
            Toast.makeText(activity, "Please select disposal of water available", Toast.LENGTH_SHORT).show();

            return false;
        }


        if (rb_rainwater_harvesting_system_available_no.isChecked() || rb_rainwater_harvesting_system_available_yes.isChecked()) {
            if (rb_rainwater_harvesting_system_available_no.isChecked()) {
                rainwater_harvesting_system_available = "0";
            } else if (rb_rainwater_harvesting_system_available_yes.isChecked()) {
                rainwater_harvesting_system_available = "1";
            }


        } else {
            Toast.makeText(activity, "Please select rainwater harvesting system available", Toast.LENGTH_SHORT).show();

            return false;
        }

        if(noOfDwellingUnitSpinner!=null) {
            noOfDwellingUnitStr = noOfDwellingUnitSpinner.getSelectedItem().toString();
        }
        try{
        if(connectionSizeSpinner.getSelectedItem().toString()!=null) {
            selectConnectionSize = connectionSizeSpinner.getSelectedItem().toString();
        }}catch (Exception e){
            selectConnectionSize="";
        }
        if(meterSizeSpinner.getSelectedItem().toString()!=null) {
            selectMeterSize = meterSizeSpinner.getSelectedItem().toString();
        }

        add_consumer = tv_consumer_details.getText().toString();
        storageCapacity = et_storage_capacity.getText().toString();
        storage_capacity_type = et_ugr_storage_capacity.getText().toString();
        meterLocation = meterLocationSpinner.getSelectedItem().toString();

        if (rb_any_existing_connection_no.isChecked()) {
            any_existing_connection = "0";
        } else if (rb_any_existing_connection_yes.isChecked()) {
            any_existing_connection = "1";
           /* if (et_gis_bid.getVisibility() == View.VISIBLE) {
                if (et_gis_bid.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(activity, "Please enter Building ID", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            if (tv_consumer_details.getVisibility() == View.VISIBLE) {
                if (tv_consumer_details.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(activity, "Please search Building ID", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }*/
        }

     /*   if(rb_storage_capacity_no.isChecked()){
            storage_capacity="0";
        }else if(rb_storage_capacity_yes.isChecked()){
            storage_capacity="1";

        }*/

        if (unAuthorizedConnectionStr.equalsIgnoreCase("Select")) {

            unAuthorizedConnectionSpinner.setError(getResources().getString(R.string.cannot_be_empty));

            Toast.makeText(getActivity(), R.string.please_select_unauthorized_connection, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            unAuthorizedConnectionSpinner.setError(null);

        }
        if (civilConsWithCcmcWaterStr.equalsIgnoreCase("Select")) {

            civilConsWithCcmcWaterSpinner.setError(getResources().getString(R.string.cannot_be_empty));

            Toast.makeText(getActivity(), R.string.please_select_civil_cons_with_ccmc_water, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            civilConsWithCcmcWaterSpinner.setError(null);

        }
        if (noOfDwellingUnitStr.equalsIgnoreCase("Select")) {

            noOfDwellingUnitSpinner.setError(getResources().getString(R.string.cannot_be_empty));

            Toast.makeText(getActivity(), R.string.please_select_no_of_dwelling_unit, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            noOfDwellingUnitSpinner.setError(null);

        }
        if (isOccupierSecurityStr.equalsIgnoreCase("Select")) {
            isOccupierSecuritySpinner.setError(getResources().getString(R.string.cannot_be_empty));
            Toast.makeText(getActivity(), R.string.please_enter_is_occupier_security, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            isOccupierSecuritySpinner.setError(null);

        }
        if (governmentEmployeeStr.equalsIgnoreCase("Select")) {
            governmentEmployeeSpinner.setError(getResources().getString(R.string.cannot_be_empty));
            Toast.makeText(getActivity(), R.string.please_select_government_employee, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            governmentEmployeeSpinner.setError(null);

        }
        if (noOfDwellingUnitStr.equalsIgnoreCase("Select")) {
            noOfDwellingUnitSpinner.setError(getResources().getString(R.string.cannot_be_empty));
            Toast.makeText(getActivity(), R.string.please_select_connection_size, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            noOfDwellingUnitSpinner.setError(null);
        }

        if (selectConnectionSize.equalsIgnoreCase("Select")&& selectConnectionSize.equalsIgnoreCase("null")) {
            connectionSizeSpinner.setError(getResources().getString(R.string.cannot_be_empty));
            Toast.makeText(getActivity(), R.string.please_select_connection_size, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            connectionSizeSpinner.setError(null);
        }
        if (selectMeterSize.equalsIgnoreCase("Select")) {
            meterSizeSpinner.setError(getResources().getString(R.string.cannot_be_empty));
            Toast.makeText(getActivity(), R.string.please_select_meter_size, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            meterSizeSpinner.setError(null);

        }


        if (ohInputLayout.getVisibility() == View.VISIBLE) {
            if (et_storage_capacity.getText().toString().equalsIgnoreCase("")) {
                Toast.makeText(activity, "Please enter overhead tank capacity", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (ugrInputLayout.getVisibility() == View.VISIBLE) {
            if (et_ugr_storage_capacity.getText().toString().equalsIgnoreCase("")) {
                Toast.makeText(activity, "Please enter underground tank capacity", Toast.LENGTH_SHORT).show();
                return false;
            }


        }

        if (meterLocation.equalsIgnoreCase("Select")) {
            Toast.makeText(activity, "Please select meter location ", Toast.LENGTH_SHORT).show();
            meterLocationSpinner.setError(getResources().getString(R.string.cannot_be_empty));
            return false;
        } else {
            meterLocationSpinner.setError(null);

        }


        return true;
    }

    @Override
    public void onItemSelected(MaterialSpinner materialSpinner, View view, int i, long l) {
        switch (materialSpinner.getId()) {

            case R.id.unAuthorizedConnectionSpinner: {
                String selectedValue = unAuthorizedConnectionSpinner.getSelectedItem().toString();
                if (selectedValue.equalsIgnoreCase("Select")) {
                    unAuthorizedConnectionId = "-1";
                } else if (selectedValue.equalsIgnoreCase("Yes")) {

                    unAuthorizedConnectionId = "1";
                } else if (selectedValue.equalsIgnoreCase("No")) {

                    unAuthorizedConnectionId = "0";
                }
            }
            break;

            case R.id.civilConsWithCcmcWaterSpinner: {
                String selectedValue = civilConsWithCcmcWaterSpinner.getSelectedItem().toString();
                if (selectedValue.equalsIgnoreCase("Select")) {
                    civilConsWithCcmcWaterId = "-1";
                } else if (selectedValue.equalsIgnoreCase("Yes")) {

                    civilConsWithCcmcWaterId = "1";
                } else if (selectedValue.equalsIgnoreCase("No")) {

                    civilConsWithCcmcWaterId = "0";
                }
            }
            break;
            case R.id.noOfDwellingUnitSpinner: {
                String selectedValue = noOfDwellingUnitSpinner.getSelectedItem().toString();
                if (selectedValue.equalsIgnoreCase("Select")) {
                    noOfDwellingUnitId = "-1";
                } else {

                    noOfDwellingUnitId = selectedValue;

                }


            }
            break;

            case R.id.isOccupierSecuritySpinner: {
                String selectedValue = isOccupierSecuritySpinner.getSelectedItem().toString();
                if (selectedValue.equalsIgnoreCase("Select")) {
                    isOccupierSecurityId = "-1";
                } else if (selectedValue.equalsIgnoreCase("Yes")) {
                    isOccupierSecurityId = "1";
                } else if (selectedValue.equalsIgnoreCase("No")) {

                    isOccupierSecurityId = "0";
                }
            }
            break;

            case R.id.governmentEmployeeSpinner: {
                String selectedValue = governmentEmployeeSpinner.getSelectedItem().toString();
                if (selectedValue.equalsIgnoreCase("Select")) {
                    governmentEmployeeId = "-1";
                } else if (selectedValue.equalsIgnoreCase("Yes")) {

                    governmentEmployeeId = "1";
                } else if (selectedValue.equalsIgnoreCase("No")) {

                    governmentEmployeeId = "0";
                }
            }
            case R.id.connectionSizeSpinner: {
                String selectedValue = connectionSizeSpinner.getSelectedItem().toString();
                if (selectedValue.equalsIgnoreCase("Select")) {
                    connectionSizeId = "-1";
                } else {
                    sizeModel = realmOperations.getSizeTypeId(selectedValue);
                    connectionSizeId = sizeModel.getID();
                }

            }
            break;
            case R.id.meterSizeSpinner: {
                String selectedValue = meterSizeSpinner.getSelectedItem().toString();
                if (selectedValue.equalsIgnoreCase("Select")) {
                    meterSizeId = "-1";
                } else {
                    mmgMeterSizeModel = realmOperations.fetchMeterSizeByName(selectedValue);
                    meterSizeId = String.valueOf(mmgMeterSizeModel.getMCS_ID());
                }

            }
            break;
            case R.id.meterLocationSpinner: {
                String selectedValue = meterLocationSpinner.getSelectedItem().toString();
                if (selectedValue.equalsIgnoreCase("Select")) {

                } else {
                    meterLocation = selectedValue;

                }

            }
            break;
            case R.id.gisBidCustomerSpinner:
                RequestConsumerBidModel requestConsumerBidModel = gisBidConsumerlist.get(i);
                consumer = requestConsumerBidModel.getCONSUMER();
                address = requestConsumerBidModel.getADDRESS();
                //  tv_add_consumer_details.setText(strList);
                tv_add_consumer_details.setVisibility(View.GONE);
                tv_add_consumer_details.setText(consumer + "," + address);
                break;
            default:
                break;
        }


    }

    @Override
    public void onNothingSelected(MaterialSpinner materialSpinner) {

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser) {
                new NCCommercialFeasibilityDetailsFragment();
                getData();
             //   gisBidDropdowon(giSiD);

            }
        }
    }

    private void getData() {
        connectionModel = activity.getConnectionList();
        Log.d("Click" + "Connectionfrg", connectionModel.toString());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            NCFD = (NCCommercialFeasibility) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
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
