package elink.suezShimla.water.crm.ConnectionManagement.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.Complaint.WorkCompletion.Model.BillingAdjustmentResponceModel;
import elink.suezShimla.water.crm.ConnectionManagement.activity.SiteVisitListActivityDetails;
import elink.suezShimla.water.crm.ConnectionManagement.model.SiteVisitModel;
import elink.suezShimla.water.crm.ConnectionManagement.utility.MultiSelectSpinner;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.MainActivity;
import elink.suezShimla.water.crm.MessageWindow;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.RoadOwnershipModel;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.RoadRestorationLenRoadModel;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.RoadTypeModel;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.SizeModel;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;
import elink.suezShimla.water.crm.constant.AppConstant;

public class NCTechnicalFeasibilityDetailsFragment extends Fragment implements View.OnClickListener, MaterialSpinner.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener, MultiSelectSpinner.OnMultipleItemsSelectedListener  {
    MaterialSpinner technicalFeasibilitySpinner, distanceNetworkLineSizeSpinner, roadRestorationLengthSpinner, meterSanctionTypeSpinner;
 /*   String[] distanceNetworkLineSize = { "Select", "15", "20","25", "32","35", "40", "50","60", "75","80", "100","150", "200","300"};
    String[] roadRestrotion = { "Select", "Feet", "Meter"};
    String[] roadType = { "Select", "Concrete Road (RCC)", "Soil Road", "Tiles Road"};
    String[] roadOwnerShip = { "Select", "CCMC", "NHAI", "Private", "SH (State Highway)"};*/
    /*viren*/
    SiteVisitModel commercialFeasibilityModel, model;
    SiteVisitListActivityDetails activity;
    Button btn_submit_consumer, btn_technical_back;
    TextInputEditText etUnit;ImageButton bt_add;
    String technicalFeasibilityStr = "", distanceNetworkLineSizeStr = "", roadRestorationLengthStr = "", roadTypeStr = "", roadOwnerShipStr = "", meterSanctionTypeStr = "", unitStr = "";
    String technicalFeasibilityId = "", distanceNetworkLineSizeId = "", roadRestorationLengthId = "Feet",roadTypeValue = "", roadOwnerShipValue = "", roadTypeId = "", roadOwnerShipId = "", meterSanctionTypeId = "", unitId = "", AM_APP_BU = "";
    String distribution_pipeline, distribution_id, road_length_id;
    LinearLayout ll_add;
    NCTechnicalFeasibility NCTFD;

    private List<String> selectedIDs = new ArrayList<>();

    ArrayAdapter distanceNetworkAdapter, meterSanctionAdapter, roadOwnerShipAdapter, roadTypeAdapter, roadRestorationAdapter;
    private RealmOperations realmOperations;
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    private Context mCon;
    private MultiSelectSpinner.OnMultipleItemsSelectedListener mConn;
    boolean flagadd=false;
    private SizeModel sizeModel;
    private RoadRestorationLenRoadModel roadRestorationLenRoadModel;
    private RoadTypeModel roadTypeModel;
    private RoadOwnershipModel roadOwnershipModel;
    public LinearLayout llconsumer,ll_textttt;
    TextInputEditText et_left_consumer, et_right_consumer,et_consumer;
    ImageButton ib_consumer_bid_request;
    String leftConsumerStr = "", rightConsumerStr = "", jsonLeftRightResponse = "",addapplicationtype;
    MaterialDialog progressLeftRightConsumerSearch;
    int selectedID;
    boolean validConsumer = false;
    TextView tv_consumrno;
    RadioGroup rg_Consumer_Premise,rg_connection_feasibility, rg_water_availability_in_dp, rg_road_cutting_required;
    RadioButton rb_connection_feasibility_no, rb_connection_feasibility_yes, rb_water_availability_yes, rb_water_availability_no,
            rb_Sewerage_Premiseno,rb_yes_Sewerage,rb_road_cutting_required_no, rb_road_cutting_required_yes,rb_yes_Consumer_Premise,rb_pipeConsumer_Premiseno;
    String connection_feasibility, road_cutting_required, water_availability,pipeconsumer,SeweragePremiseno;
    TextInputEditText et_distribution_pipeline, et_distribution_id, et_road_length_id;

    TextInputLayout distributionPipelineInputLayout, distributionIdInputLayout, leftConsumerInputLayout, rightConsumerInputLayout,
            roadLengthIdConsumerInputLayout,roadTypeIdConsumerInputLayout,roadOwnerShipInputLayout;
    String addconsumer[] = new String[5];

    MultiSelectSpinner roadTypeSpinnere,roadOwnerShipSpinnerer;
    ArrayList<String> roadTypeList;
    String STARTTIME = "", ALERTSTARTTIME = "";
    String rtimem = "Reading can be taken between";
    boolean submitData = false;
    String ENDTIME = "", ALERTENDTIME = "", time = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // prevent ss and hide content when app is on background
       // getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        View rootView = inflater.inflate(R.layout.fragment_technical_feasibility, container, false);

        mCon = getActivity();

        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();
        realmOperations = new RealmOperations(mCon);


        activity = (SiteVisitListActivityDetails) getActivity();
        model = activity.getSiteVisitDataData();

        technicalFeasibilityStr = model.getAM_APP_ISTECH_FESIBILITY();


        distanceNetworkLineSizeStr = model.getAM_APP_SPIPELINE();
        roadRestorationLengthStr = model.getAM_APP_LENGTH_MEASURE();
        unitStr = model.getAM_APP_ROADCUTMTR();
        roadTypeStr = model.getAM_APP_ROADTYPE();
        roadOwnerShipStr = model.getAM_APP_ROADOWNER();
        meterSanctionTypeStr = model.getAM_APP_CONNECTION_SIZE();
        AM_APP_BU = model.getAM_APP_BU();

        rightConsumerStr = model.getAM_APP_NEARBYSER();// right consumer
        leftConsumerStr = model.getAM_APP_NEAR_LCONS();// leftconsumer
        addapplicationtype = model.getAM_AAPP_NO_TYPE();

      //  Toast.makeText(activity, ""+rightConsumerStr+" "+leftConsumerStr, Toast.LENGTH_SHORT).show();

        Log.d("Technical", "" + technicalFeasibilityStr + " " + distanceNetworkLineSizeStr + " " + roadRestorationLengthSpinner + " " + unitStr + " " + roadTypeStr + " " + roadOwnerShipStr);

        STARTTIME = UtilitySharedPreferences.getPrefs(mCon, AppConstant.STARTTIME);
        ENDTIME = UtilitySharedPreferences.getPrefs(mCon, AppConstant.ENDTIME);
        Calendar c = Calendar.getInstance();

        try {
            final SimpleDateFormat sdff = new SimpleDateFormat("HH:mm");
            final Date dateObj = sdff.parse(STARTTIME);
            final Date dateObji = sdff.parse(ENDTIME);
            ALERTSTARTTIME = new SimpleDateFormat("hh:mm aa").format(dateObj);
            ALERTENDTIME = new SimpleDateFormat("hh:mm aa").format(dateObji);
        } catch (final ParseException e) {

            e.printStackTrace();
        }
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        time = df.format(c.getTime());

        checkTimes(dateParsing(STARTTIME), dateParsing(time), dateParsing(ENDTIME));
        //showDialog();
        initializeViews(rootView);
        // getFirmTypeRequest();
        if (addapplicationtype.equalsIgnoreCase("12")) {

            roadTypeSpinnere.setEnabled(false);



            roadOwnerShipSpinnerer.setEnabled(false);
            //spinner_material_type.setListener(activity);



            technicalFeasibilitySpinner.setEnabled(false);

        /*roadOwnerShipSpinner = rootView.findViewById(R.id.roadOwnerShipSpinner);
        roadOwnerShipSpinner.setOnItemSelectedListener(this);

        roadTypeSpinner = rootView.findViewById(R.id.roadTypeSpinner);
        roadTypeSpinner.setOnItemSelectedListener(this);*/

            distanceNetworkLineSizeSpinner.setEnabled(false);
            roadRestorationLengthSpinner.setEnabled(false);

            etUnit.setEnabled(false);
            et_distribution_pipeline.setEnabled(false);
            et_distribution_id.setEnabled(false);
            et_road_length_id.setEnabled(false);
            etUnit.setText(unitStr);
            etUnit.setEnabled(false);
            //   roadTypeSpinner = rootView.findViewById(R.id.roadTypeSpinner);



//            et_left_consumer = rootView.findViewById(R.id.et_left_consumer);
//            et_right_consumer = rootView.findViewById(R.id.et_right_consumer);
//            et_right_consumer.setText(rightConsumerStr);
//            et_left_consumer.setText(leftConsumerStr);


//            ib_consumer_bid_request = rootView.findViewById(R.id.ib_consumer_bid_request);


/*
            rg_connection_feasibility = rootView.findViewById(R.id.rg_connection_feasibility);
            rg_water_availability_in_dp = rootView.findViewById(R.id.rg_water_availability_in_dp);
            rg_road_cutting_required = rootView.findViewById(R.id.rg_road_cutting_required);

            rb_connection_feasibility_no = rootView.findViewById(R.id.rb_connection_feasibility_no);
            rb_connection_feasibility_yes = rootView.findViewById(R.id.rb_connection_feasibility_yes);

            rb_water_availability_yes = rootView.findViewById(R.id.rb_water_availability_yes);
            rb_water_availability_no = rootView.findViewById(R.id.rb_water_availability_no);

            rb_road_cutting_required_no = rootView.findViewById(R.id.rb_road_cutting_required_no);
            rb_road_cutting_required_yes = rootView.findViewById(R.id.rb_road_cutting_required_yes);

*/

            distributionPipelineInputLayout.setEnabled(false);
            distributionIdInputLayout.setEnabled(false);
            leftConsumerInputLayout.setEnabled(false);
            rightConsumerInputLayout .setEnabled(false);
            roadLengthIdConsumerInputLayout.setEnabled(false);
            roadLengthIdConsumerInputLayout.setVisibility(View.GONE);

            roadTypeIdConsumerInputLayout.setEnabled(false);
            roadOwnerShipInputLayout.setEnabled(false);




            String[] ConsumerArray= new String[5];
           /* ll_add.setOnClickListener(new View.OnClickListener()   {
                public void onClick(View v)  {
                    try {
                        addID();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });*/

        }

        return rootView;
    }

    private void addID() {
        String newID = et_consumer.getText().toString().trim();

        if (newID.isEmpty()) {
            Toast.makeText(getActivity(), "Enter an ID", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedIDs.size() >= 5) {
            Toast.makeText(getActivity(), "Limit reached (5 IDs max)", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedIDs.contains(newID)) {
            Toast.makeText(getActivity(), "ID already added", Toast.LENGTH_SHORT).show();
            return;
        }

        selectedIDs.add(newID);
        updateTextView();
        et_consumer.setText(""); // Clear input field after adding
    }

    private void updateTextView() {
        tv_consumrno.setText(String.join(", ", selectedIDs));
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
        } else {

            timeoutAlertBox();
        }

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

        roadTypeSpinnere = rootView.findViewById(R.id.roadTypeSpinnere);



        roadOwnerShipSpinnerer = rootView.findViewById(R.id.roadOwnerShipSpinnerer);
        //spinner_material_type.setListener(activity);



        technicalFeasibilitySpinner = rootView.findViewById(R.id.technicalFeasibilitySpinner);
        technicalFeasibilitySpinner.setOnItemSelectedListener(this);

        /*roadOwnerShipSpinner = rootView.findViewById(R.id.roadOwnerShipSpinner);
        roadOwnerShipSpinner.setOnItemSelectedListener(this);

        roadTypeSpinner = rootView.findViewById(R.id.roadTypeSpinner);
        roadTypeSpinner.setOnItemSelectedListener(this);*/

        distanceNetworkLineSizeSpinner = rootView.findViewById(R.id.distanceNetworkLineSizeSpinner);
        roadRestorationLengthSpinner = rootView.findViewById(R.id.roadRestorationLengthSpinner);

        etUnit = rootView.findViewById(R.id.etUnit);
        et_distribution_pipeline = rootView.findViewById(R.id.et_distribution_pipeline);
        et_distribution_id = rootView.findViewById(R.id.et_distribution_id);
        et_road_length_id = rootView.findViewById(R.id.et_road_length_id);
        etUnit.setText(unitStr);
     //   roadTypeSpinner = rootView.findViewById(R.id.roadTypeSpinner);


        btn_submit_consumer = rootView.findViewById(R.id.btn_submit_consumer);
        btn_submit_consumer.setOnClickListener(this);

        btn_technical_back = rootView.findViewById(R.id.btn_technical_back);
        btn_technical_back.setOnClickListener(this);

        et_left_consumer = rootView.findViewById(R.id.et_left_consumer);
        et_right_consumer = rootView.findViewById(R.id.et_right_consumer);
        et_right_consumer.setText(rightConsumerStr);
        et_left_consumer.setText(leftConsumerStr);


        ib_consumer_bid_request = rootView.findViewById(R.id.ib_consumer_bid_request);
        ib_consumer_bid_request.setOnClickListener(this);

        rg_connection_feasibility = rootView.findViewById(R.id.rg_connection_feasibility);
        rg_water_availability_in_dp = rootView.findViewById(R.id.rg_water_availability_in_dp);
        rg_road_cutting_required = rootView.findViewById(R.id.rg_road_cutting_required);

        rb_connection_feasibility_no = rootView.findViewById(R.id.rb_connection_feasibility_no);
        rb_connection_feasibility_yes = rootView.findViewById(R.id.rb_connection_feasibility_yes);

        rb_water_availability_yes = rootView.findViewById(R.id.rb_water_availability_yes);
        rb_water_availability_no = rootView.findViewById(R.id.rb_water_availability_no);
        llconsumer= rootView.findViewById(R.id.llconsumer);
        tv_consumrno= rootView.findViewById(R.id.tv_consumrno);
        ll_textttt= rootView.findViewById(R.id.ll_textttt);
        rb_road_cutting_required_no = rootView.findViewById(R.id.rb_road_cutting_required_no);
        rb_road_cutting_required_yes = rootView.findViewById(R.id.rb_road_cutting_required_yes);
        rb_pipeConsumer_Premiseno = rootView.findViewById(R.id.rb_pipeConsumer_Premiseno);
        rb_yes_Consumer_Premise = rootView.findViewById(R.id.rb_yes_Consumer_Premise);
        et_consumer= rootView.findViewById(R.id.et_consumer);
        rb_Sewerage_Premiseno = rootView.findViewById(R.id.rb_Sewerage_Premiseno);
        rb_yes_Sewerage = rootView.findViewById(R.id.rb_yes_Sewerage);
        bt_add= rootView.findViewById(R.id.bt_consadd);
        ll_add= rootView.findViewById(R.id.ll_add);
        rg_Consumer_Premise= rootView.findViewById(R.id.rg_Consumer_Premise);
        distributionPipelineInputLayout = rootView.findViewById(R.id.distributionPipelineInputLayout);
        distributionIdInputLayout = rootView.findViewById(R.id.distributionIdInputLayout);
        leftConsumerInputLayout = rootView.findViewById(R.id.leftConsumerInputLayout);
        rightConsumerInputLayout = rootView.findViewById(R.id.rightConsumerInputLayout);
        roadLengthIdConsumerInputLayout = rootView.findViewById(R.id.roadLengthIdConsumerInputLayout);
        roadLengthIdConsumerInputLayout.setVisibility(View.GONE);

        roadTypeIdConsumerInputLayout = rootView.findViewById(R.id.roadTypeIdConsumerInputLayout);
        roadOwnerShipInputLayout = rootView.findViewById(R.id.roadOwnerShipInputLayout);

        bt_add.setOnClickListener(this);

        rg_Consumer_Premise.setOnCheckedChangeListener(this);
        rg_connection_feasibility.setOnCheckedChangeListener(this);
        rg_water_availability_in_dp.setOnCheckedChangeListener(this);
        rg_road_cutting_required.setOnCheckedChangeListener(this);





        technicalFeasibilityDropDown();
        distanceNetworkLineSizeDropDown();
        roadRestorationLengthDropDown();
        roadTypeDropDown();
        roadOwnerShipDropDown();
        meterSectionTypeDropDown();

    }

    private void meterSectionTypeDropDown() {

        ArrayList<String> distanceNetworkLineStringList = new ArrayList<>();
        distanceNetworkLineStringList = realmOperations.fetchDistanceNetworkLineSizeListName();
        ArrayList<String> distanceNetworkLineList = new ArrayList<>();
        distanceNetworkLineList.add("Select");
        distanceNetworkLineList.addAll(distanceNetworkLineStringList);


    }

/*
    String[] array = {"None", "Apple", "Google", "Facebook", "Tesla", "IBM", "Twitter"};
*/

    private void roadOwnerShipDropDown() {
        ArrayList<String> roadOwnerShipStringList = new ArrayList<>();
        roadOwnerShipStringList = realmOperations.fetchRoadOwnerShipListName();
        ArrayList<String> roadOwnerShipStringListList = new ArrayList<>();
        roadOwnerShipStringListList.add("Select");
        roadOwnerShipStringListList.addAll(roadOwnerShipStringList);
      /*  roadOwnerShipAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, roadOwnerShipStringListList);
        roadOwnerShipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roadOwnerShipSpinner.setAdapter(roadOwnerShipAdapter);
        roadOwnerShipSpinner.setSelection(0);
        roadOwnerShipSpinner.setOnItemSelectedListener(this);*/

        roadOwnerShipSpinnerer.setItems(roadOwnerShipStringListList);
        roadOwnerShipSpinnerer.hasNoneOption(true);
        roadOwnerShipSpinnerer.setSelection(new int[]{0});
        roadOwnerShipSpinnerer.setListener(this);


        //spinner_material_type.setItems(roadOwnerShipStringListList);


    }

    private void roadTypeDropDown() {
        ArrayList<String> roadTypeStringList = new ArrayList<>();
        roadTypeStringList = realmOperations.fetchRoadTypeListListName();
        roadTypeList = new ArrayList<>();
        roadTypeList.add("Select");
        roadTypeList.addAll(roadTypeStringList);
      /*  roadTypeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, roadTypeList);
        roadTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roadTypeSpinner.setAdapter(roadTypeAdapter);
        roadTypeSpinner.setSelection(0);
        roadTypeSpinner.setOnItemSelectedListener(this);*/

        //spinner_material_type.setListener(activity);
       // String[] array = {"None", "Apple", "Google", "Facebook", "Tesla", "IBM", "Twitter"};
        roadTypeSpinnere.setItems(roadTypeList);
        roadTypeSpinnere.hasNoneOption(true);
        roadTypeSpinnere.setSelection(new int[]{0});
        roadTypeSpinnere.setListener(this);



    }

    private void roadRestorationLengthDropDown() {
        ArrayList<String> roadRestorationStringList = new ArrayList<>();
        roadRestorationStringList = realmOperations.roadRestorationListName();
        ArrayList<String> roadRestoration = new ArrayList<>();
        roadRestoration.add("Select");
        roadRestoration.addAll(roadRestorationStringList);


    }


    private void distanceNetworkLineSizeDropDown() {


        ArrayList<String> distanceNetworkLineStringList = new ArrayList<>();
        distanceNetworkLineStringList = realmOperations.fetchDistanceNetworkLineSizeListName();
        ArrayList<String> distanceNetworkLineList = new ArrayList<>();
        distanceNetworkLineList.add("Select");
        distanceNetworkLineList.addAll(distanceNetworkLineStringList);


    }

    private void technicalFeasibilityDropDown() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.choice, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        technicalFeasibilitySpinner.setAdapter(adapter);
        technicalFeasibilitySpinner.setSelection(1);
        technicalFeasibilitySpinner.setOnItemSelectedListener(this);



    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit_consumer: {


                if (validation()) {
                    String addconsumer = tv_consumrno.getText().toString();
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("pipe",pipeconsumer);
                    editor.putString("seweragep",SeweragePremiseno);
                    editor.putString("adconsumer",addconsumer);

                    editor.apply();
                   if (validConsumer) {
                        dataTechnicalFeasibility();
                    } else {
                       et_right_consumer.setError("Please search valid  consumer");
                        Toast.makeText(activity, "Please search valid  consumer", Toast.LENGTH_SHORT).show();
                    }
                }

            }
            break;
            case R.id.btn_technical_back:

                backFragmentActivity();


                break;
            case R.id.bt_consadd:
                flagadd=true;
                leftRightConsumerSearchaddconsumer();


                break;
            case R.id.ib_consumer_bid_request:

                if (checkValidation()) {
                    leftRightConsumerSearch();
                }
                break;

            default:
                break;
        }
    }



    private boolean checkValidation() {
        leftConsumerStr = et_left_consumer.getText().toString();
        rightConsumerStr = et_right_consumer.getText().toString();

      /*  if (leftConsumerStr.equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), R.string.please_enter_left_consumer_number, Toast.LENGTH_SHORT).show();
            return false;
        }else {
            rightConsumerInputLayout.setError(null);
        }*/
        if (rightConsumerStr.equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), R.string.please_enter_right_consumer_number, Toast.LENGTH_SHORT).show();
            return false;
        }else {
            rightConsumerInputLayout.setError(null);
        }
        return true;
    }
    private void leftRightConsumerSearchaddconsumer() {
        String   searchconsumer;
            searchconsumer = et_consumer.getText().toString();
        String[] params = new String[2];
        params[0] = "0";
        params[1] = searchconsumer;
        // params[2] = leftConsumerStr;
        //params[0] = "23218164";
        if (connection.isConnectingToInternet()) {

            LeftRightConsumerSearch leftRightConsumerSearch = new LeftRightConsumerSearch();
            leftRightConsumerSearch.execute(params);


        } else {
            Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void leftRightConsumerSearch() {
        String   searchconsumer;
         searchconsumer = et_right_consumer.getText().toString();

        String[] params = new String[2];
        params[0] = AM_APP_BU;
        params[1] = searchconsumer;
       // params[2] = leftConsumerStr;
        //params[0] = "23218164";
        if (connection.isConnectingToInternet()) {

            LeftRightConsumerSearch leftRightConsumerSearch = new LeftRightConsumerSearch();
            leftRightConsumerSearch.execute(params);


        } else {
            Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (radioGroup.getId()) {
            case R.id.rg_connection_feasibility:
                selectedID = radioGroup.getCheckedRadioButtonId();
                if (selectedID == R.id.rb_connection_feasibility_no) {

                }
                if (selectedID == R.id.rb_connection_feasibility_yes) {


                }
                break;
            case R.id.rg_water_availability_in_dp:
                selectedID = radioGroup.getCheckedRadioButtonId();
                if (selectedID == R.id.rb_water_availability_no) {

                }
                if (selectedID == R.id.rb_water_availability_yes) {

                }
                break;
            case R.id.rg_road_cutting_required:
                selectedID = radioGroup.getCheckedRadioButtonId();
                if (selectedID == R.id.rb_road_cutting_required_no) {
                    roadLengthIdConsumerInputLayout.setVisibility(View.GONE);
                    et_road_length_id.setText("");

                }
                if (selectedID == R.id.rb_road_cutting_required_yes) {
                    roadLengthIdConsumerInputLayout.setVisibility(View.VISIBLE);

                }
                break;
            case R.id.rg_Consumer_Premise:
                selectedID = radioGroup.getCheckedRadioButtonId();
                if (selectedID == R.id.rb_pipeConsumer_Premiseno) {
                    llconsumer.setVisibility(View.GONE);
                    ll_textttt.setVisibility(View.GONE);
                }
                if (selectedID == R.id.rb_yes_Consumer_Premise) {
                    llconsumer.setVisibility(View.VISIBLE);
                    ll_textttt.setVisibility(View.VISIBLE);
                }
                break;
            default:
        }
    }

    @Override
    public void selectedIndices(List<Integer> indices) {

    }

    @Override
    public void selectedStrings(List<String> strings) {
      //  Toast.makeText(mCon,"Selected Companies" + strings,Toast.LENGTH_LONG).show();
    }


    private class LeftRightConsumerSearch extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            progressLeftRightConsumerSearch = new MaterialDialog.Builder(mCon)
                    .content(R.string.loading)
                    .widgetColorRes(R.color.colorPrimary)
                    .canceledOnTouchOutside(false)
                    .autoDismiss(false)
                    .progress(true, 0)
                    .show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                String paraName[] = new String[2];


                paraName[0] = "BUId";
                paraName[1] = "ActConsumer";
               // paraName[2] = "ValConsumer";


                jsonLeftRightResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.CheckSameZone_Consumer, params, paraName);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                BillingAdjustmentResponceModel enums = gson.fromJson(jsonLeftRightResponse, BillingAdjustmentResponceModel.class);
                if (enums.getMessage().equalsIgnoreCase("Valid")) {
                       Toast.makeText(mCon, R.string.valid_consumer, Toast.LENGTH_LONG).show();

                    validConsumer = true;
                    if(flagadd==true){
                        try {
                            addID();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                } else {
                    et_right_consumer.setError("Please search valid  consumer");
                    // Toast.makeText(mCon, R.string.no_data_found, Toast.LENGTH_LONG).show();
                    MessageWindow.connectionFragment(mCon, enums.getMessage(), "Alert", MainActivity.class);

                    validConsumer = false;
                }
            } catch (Exception e) {
                Log.d("check", e.getMessage());
                Toast.makeText(mCon, e.getMessage(), Toast.LENGTH_SHORT).show();

                String error = e.toString();
            }
            progressLeftRightConsumerSearch.dismiss();
        }
    }


    private void backFragmentActivity() {
        ((SiteVisitListActivityDetails) getActivity()).onClickPrev();
    }

    private void dataTechnicalFeasibility() {
        rightConsumerStr = et_right_consumer.getText().toString();
        SharedPreferences pref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = pref.edit();
        distribution_pipeline = et_distribution_pipeline.getText().toString();
        road_length_id = et_road_length_id.getText().toString();

        edt.putString("RIGHTCONSUMER", rightConsumerStr);
        edt.putString("distributionpipeline", distribution_pipeline);
        edt.putString("roadlength", road_length_id);
        edt.putString("wateravailability", water_availability);
        edt.putString("roadcut", road_cutting_required);
        edt.commit();
        /*SiteVisitModel siteVisitModel = new SiteVisitModel("", "", technicalFeasibilityId, distanceNetworkLineSizeId, roadRestorationLengthId,
                unitStr, roadTypeId, roadOwnerShipId, meterSanctionTypeId);*/
        SiteVisitModel siteVisitModel = new SiteVisitModel(connection_feasibility,
                water_availability, road_cutting_required,
                distribution_pipeline, distribution_id, leftConsumerStr, rightConsumerStr,
                road_length_id, roadTypeValue, roadOwnerShipValue);
        NCTFD.sendTechnicalFeasibility(siteVisitModel);
        //   Toast.makeText(getActivity(), getResources().getString(R.string.details_submitted_successfully), Toast.LENGTH_SHORT).show();
        ((SiteVisitListActivityDetails) getActivity()).onClickNext();

    }

 /*   @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int selectedID = group.getCheckedRadioButtonId();
        if (selectedID == R.id.rb_any_existing_connection_no) {
            Toast.makeText(mCon, "rb_any_existing_connection_no", Toast.LENGTH_SHORT).show();
        }
        if(selectedID == R.id.rb_any_existing_connection_yes){
            Toast.makeText(mCon, "rb_any_existing_connection_yes", Toast.LENGTH_SHORT).show();


        }
    }*/
/*
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (radioGroup.getId()){
            case R.id.crg:
                 selectedID = radioGroup.getCheckedRadioButtonId();
                if (selectedID == R.id.rb_any_existing_connection_no) {
                    Toast.makeText(activity, "rb_any_existing_connection_no", Toast.LENGTH_SHORT).show();
                }
                if(selectedID == R.id.rb_any_existing_connection_yes){
                    Toast.makeText(activity, "rb_any_existing_connection_yes", Toast.LENGTH_SHORT).show();


                }
                break;
            case R.id.rg_construction:
                 selectedID = radioGroup.getCheckedRadioButtonId();
                if (selectedID == R.id.rb_construction_yes) {
                    Toast.makeText(activity, "rb_construction_yes", Toast.LENGTH_SHORT).show();

                }
                if(selectedID == R.id.rb_construction_no){
                    Toast.makeText(activity, "rb_construction_no", Toast.LENGTH_SHORT).show();

                }
                break;
            default:
        }

    }*/

    public interface NCTechnicalFeasibility {
        //        void sendData(String submitStatus, String radiobuttonValStr,  String makerCodeId,String serialNoStr,String installDtStr,String meterSizeStr,String sealNoStr,String pastMeterReadingStr);
        void sendTechnicalFeasibility(SiteVisitModel siteVisitModel);
    }

    @Override
    public void onItemSelected(MaterialSpinner materialSpinner, View view, int i, long l) {
        switch (materialSpinner.getId()) {
            case R.id.technicalFeasibilitySpinner: {
                String selectedValue = technicalFeasibilitySpinner.getSelectedItem().toString();
                if (selectedValue.equalsIgnoreCase("Select")) {
                    technicalFeasibilityId = "-1";
                } else if (selectedValue.equalsIgnoreCase("Yes")) {

                    technicalFeasibilityId = "1";
                } else if (selectedValue.equalsIgnoreCase("No")) {

                    technicalFeasibilityId = "0";
                }
            }
            break;
            case R.id.distanceNetworkLineSizeSpinner: {
                String selectedValue = distanceNetworkLineSizeSpinner.getSelectedItem().toString();
                if (selectedValue.equalsIgnoreCase("Select")) {
                    distanceNetworkLineSizeId = "-1";
                } else {
                    sizeModel = realmOperations.getSizeTypeId(selectedValue);
                    distanceNetworkLineSizeId = sizeModel.getID();
                }
            }
            break;
            case R.id.roadRestorationLengthSpinner: {
                String selectedValue = roadRestorationLengthSpinner.getSelectedItem().toString();
                if (selectedValue.equalsIgnoreCase("Select")) {
                    roadRestorationLengthId = "";
                } else {
                    roadRestorationLenRoadModel = realmOperations.getRoadRestortionId(selectedValue);
                    roadRestorationLengthId = roadRestorationLenRoadModel.getID();
                }
            }
            break;
          /*  case R.id.roadOwnerShipSpinner: {
                String selectedValue = roadOwnerShipSpinner.getSelectedItem().toString();
                if (selectedValue.equalsIgnoreCase("Select")) {
                    roadOwnerShipId = "";
                } else {
                    roadOwnershipModel = realmOperations.getRoadOwnershipId(selectedValue);
                    roadOwnerShipId = roadOwnershipModel.getID();
                }
            }
            break;
            case R.id.roadTypeSpinner: {
                String selectedValue = roadTypeSpinner.getSelectedItem().toString();
                if (selectedValue.equalsIgnoreCase("Select")) {
                    roadTypeId = "-1";
                } else {
                    roadTypeModel = realmOperations.getRoadTypeId(selectedValue);
                    roadTypeId = roadTypeModel.getID();
                }

            }
            break;*/

            default:
                break;

        }
    }

    @Override
    public void onNothingSelected(MaterialSpinner materialSpinner) {

    }


    private boolean validation() {

        //  technicalFeasibilityStr = technicalFeasibilitySpinner.getSelectedItem().toString();
        // distanceNetworkLineSizeStr = distanceNetworkLineSizeSpinner.getSelectedItem().toString();
        //  roadRestorationLengthStr = roadRestorationLengthSpinner.getSelectedItem().toString();

      String addconsumer =  et_consumer.getText().toString();
        if (rb_connection_feasibility_no.isChecked() || rb_connection_feasibility_yes.isChecked()) {
            if (rb_connection_feasibility_no.isChecked()) {
                connection_feasibility = "0";
            } else if (rb_connection_feasibility_yes.isChecked()) {
                connection_feasibility = "1";
            }
        } else {
            Toast.makeText(activity, "Please select connection feasibility ", Toast.LENGTH_SHORT).show();

            return false;
        }
        if (rb_water_availability_no.isChecked() || rb_water_availability_yes.isChecked()) {
            if (rb_water_availability_no.isChecked()) {
                water_availability = "0";
            } else if (rb_water_availability_yes.isChecked()) {
                water_availability = "1";
            }
        } else {
            Toast.makeText(activity, "Please select water availability", Toast.LENGTH_SHORT).show();

            return false;
        }

        if (rb_road_cutting_required_no.isChecked() || rb_road_cutting_required_yes.isChecked()) {
            if (rb_road_cutting_required_no.isChecked()) {
                road_cutting_required = "0";
            } else if (rb_road_cutting_required_yes.isChecked()) {
                road_cutting_required = "1";
            }
        } else {
            Toast.makeText(activity, "Please select road cutting required", Toast.LENGTH_SHORT).show();

            return false;
        }
        if (rb_pipeConsumer_Premiseno.isChecked() || rb_yes_Consumer_Premise.isChecked()) {
            if (rb_pipeConsumer_Premiseno.isChecked()) {
                pipeconsumer = "0";
                llconsumer.setVisibility(View.GONE);
                ll_textttt.setVisibility(View.GONE);

            } else if (rb_yes_Consumer_Premise.isChecked()) {
                pipeconsumer = "1";
                llconsumer.setVisibility(View.VISIBLE);
                ll_textttt.setVisibility(View.VISIBLE);
            }
        } else {
            Toast.makeText(activity, "Please select Pipe Consumer no required", Toast.LENGTH_SHORT).show();

            return false;
        }

        if (rb_Sewerage_Premiseno.isChecked() || rb_yes_Sewerage.isChecked()) {
            if (rb_Sewerage_Premiseno.isChecked()) {
                SeweragePremiseno = "0";
            } else if (rb_yes_Sewerage.isChecked()) {
                SeweragePremiseno = "1";
            }
        } else {
            Toast.makeText(activity, "Please select Consumer Premise required", Toast.LENGTH_SHORT).show();

            return false;
        }
        distribution_pipeline = et_distribution_pipeline.getText().toString();
        distribution_id = et_distribution_id.getText().toString();
        road_length_id = et_road_length_id.getText().toString();

        leftConsumerStr = et_left_consumer.getText().toString();
        rightConsumerStr = et_right_consumer.getText().toString();
        roadTypeStr = roadTypeSpinnere.getSelectedItem().toString();
        roadOwnerShipStr = roadOwnerShipSpinnerer.getSelectedItem().toString();


        if (distribution_pipeline.equalsIgnoreCase("")) {
            distributionPipelineInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
            Toast.makeText(getActivity(), R.string.please_enter_distribution_pipeline, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            distributionPipelineInputLayout.setError(null);
        }
        if (distribution_id.equalsIgnoreCase("")) {
            distributionIdInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
            Toast.makeText(getActivity(), R.string.please_enter_distribution_id, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            distributionIdInputLayout.setError(null);
        }

       /* if (leftConsumerStr.equalsIgnoreCase("")) {
            leftConsumerInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
            Toast.makeText(getActivity(), R.string.please_enter_left_consumer_number, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            leftConsumerInputLayout.setError(null);
        }*/
        if (rightConsumerStr.equalsIgnoreCase("")) {
            rightConsumerInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
            Toast.makeText(getActivity(), R.string.please_enter_right_consumer_number, Toast.LENGTH_SHORT).show();
            return false;
        } else {

            rightConsumerInputLayout.setError(null);
        }
      /*  if (addconsumer.equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), R.string.please_enter_right_consumer_number, Toast.LENGTH_SHORT).show();
            return false;
        }*/

        if(roadLengthIdConsumerInputLayout.getVisibility() == View.VISIBLE) {
            if (road_length_id.equalsIgnoreCase("")) {
                roadLengthIdConsumerInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
                Toast.makeText(getActivity(), R.string.please_enter_road_length_id, Toast.LENGTH_SHORT).show();
                return false;
            } else {
                roadLengthIdConsumerInputLayout.setError(null);
            }
        } else {
            road_length_id="";
        }

        if (roadTypeStr.equalsIgnoreCase("Select")) {
            roadTypeIdConsumerInputLayout.setError(getResources().getString(R.string.cannot_be_empty));

          //  roadTypeSpinnere.setError(getResources().getString(R.string.cannot_be_empty));
            Toast.makeText(getActivity(), R.string.please_select_road_type, Toast.LENGTH_SHORT).show();
            return false;
        }else {
            //For Road Type
            List<String> items = Arrays.asList(roadTypeStr.split(","));

            List<String> roadTypeIdList = new LinkedList<>();
            for (int i=0;i<items.size();i++){
                String selectedValue = items.get(i).trim();
                roadTypeModel = realmOperations.getRoadTypeId(selectedValue);
                roadTypeId = roadTypeModel.getID();
                roadTypeValue= roadTypeModel.getREM_REASONNM();
                roadTypeIdList.add(roadTypeId);
            }


            String idList = roadTypeIdList.toString();
            roadTypeId = idList.substring(1, idList.length() - 1).replace(", ", ",");
         //   roadTypeId =items.toString();
            roadTypeIdConsumerInputLayout.setError(null);
           // Toast.makeText(activity, ""+roadTypeId, Toast.LENGTH_SHORT).show();
            roadOwnerShipInputLayout.setError(null);

        }
        if (roadOwnerShipStr.equalsIgnoreCase("Select")) {
            Toast.makeText(getActivity(), R.string.please_enter_road_owner_ship, Toast.LENGTH_SHORT).show();
            roadOwnerShipInputLayout.setError(getResources().getString(R.string.cannot_be_empty));

            return false;
        }else {
            //Road OwnerShip
            List<String> items1 = Arrays.asList(roadOwnerShipStr.split(","));

            List<String> roadOwnerShipList = new LinkedList<>();
            for (int i=0;i<items1.size();i++){
                String selectedValue = items1.get(i).trim();
                roadOwnershipModel = realmOperations.getRoadOwnershipId(selectedValue);
                roadOwnerShipId = roadOwnershipModel.getID();
                roadOwnerShipValue=roadOwnershipModel.getREM_REASONNM();
                roadOwnerShipList.add(roadOwnerShipId);
            }

            roadOwnerShipInputLayout.setError(null);

            String idOwnershipList = roadOwnerShipList.toString();
            roadOwnerShipId = idOwnershipList.substring(1, idOwnershipList.length() - 1).replace(", ", ",");
            //roadOwnerShipId = items1.toString().replace("");
            //Toast.makeText(activity, ""+roadOwnerShipId, Toast.LENGTH_SHORT).show();

        }


        return true;

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser) {
                new NCTechnicalFeasibilityDetailsFragment();
                getData();

            }
        }
    }

    private void getData() {
        commercialFeasibilityModel = activity.getComercialFeasibilityList();
        //  Log.d("Click"+"Connectionfrg",commercialFeasibilityModel.toString());

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            NCTFD = (NCTechnicalFeasibilityDetailsFragment.NCTechnicalFeasibility) getActivity();
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
