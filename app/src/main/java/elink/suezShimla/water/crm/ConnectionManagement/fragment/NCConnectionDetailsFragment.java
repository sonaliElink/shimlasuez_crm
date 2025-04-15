package elink.suezShimla.water.crm.ConnectionManagement.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.tiper.MaterialSpinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.ConnectionManagement.activity.SiteVisitListActivityDetails;
import elink.suezShimla.water.crm.ConnectionManagement.model.SiteVisitModel;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadCustomerType;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.ConnCategoryModel;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.ConnPurposeModel;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.PropertyTypeModel;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;

public class NCConnectionDetailsFragment extends Fragment implements View.OnClickListener, MaterialSpinner.OnItemSelectedListener {

    MaterialSpinner applicationtypeSpinnerFix,applicationtypeSpinner,connectionTypeSpinnerFix, connectionTypeSpinner, connectionCategorySpinnerFix, connectionCategorySpinner, propertyTypeSpinnerFix, propertyTypeSpinner, connectionPurposeSpinnerFix, connectionPurposeSpinner;
    private RealmOperations realmOperations;
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    private Context mCon;
    private ArrayList<String> connectionIdList;
    private ArrayAdapter ConnCategoryAdapter,ConnCategoryAdapterOld, ConnectionPurposeAdapter, customerTypeArrayAdapter, PropertyTypeAdapter,customertraiff;
    SiteVisitModel model, customerModel;
    String applnno,selectedValueappltype,buId="" ;
    MaterialSpinner etBore,tvbore,tvOperational,etOperational,connectionTariffSpinnerFix;
    TextInputEditText tvNoOfapplication,noofTaps,
            tvNoOfFloor, etNoOfFloor, tvPopulation, etPopulation, tvNoOFDwelling, etNoOFDwelling, tvNoOfBeds, etNoOfBeds;
    String noOfFloorStr = "", populationStr = "", noOfDwellingStr = "", noOfBedsStr = "", connectionCategory = "";
    String noOfFloorFixStr = "", populationFixStr = "", noOfDwellingFixStr = "", noOfBedsFixStr = "", connectionCategoryFixStr = "";
    String connectionTypeStr = "", connectionPurposeStr = "", connectionPurposeId = "", connectionCategoryStr = "", propertyTypeStr = "", connectionTypeId="",
            appltype="",addapplicationtype="",noofapp="",operstional="", borewell="",  propertyTypeId = "",propertyTarifId = "",propertyTypeIdd = "", connectionCategoryId="",propertyTypeName="";


    SiteVisitListActivityDetails activity;
    Button btn_submit_connection, btn_connection_back;
    private DownloadCustomerType customerTypeModel;
    private PropertyTypeModel propertyTypeModel;
    private ConnPurposeModel connPurposeModel;
    private ConnCategoryModel connCategoryModel;
    TextView tv;
    LinearLayout ll_no_of_beds;

    ArrayList<String> connectionPurposePropertyTypeList = new ArrayList<>();
    ArrayList<String> connectionPurposeList = new ArrayList<>();
    ConnectionData CD;
    ArrayList<String> connectiontraiff = new ArrayList<>();

    String STARTTIME = "", ALERTSTARTTIME = "";
    String rtimem = "Reading can be taken between";
    boolean submitData = false;
    String ENDTIME = "", ALERTENDTIME = "", time = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // prevent ss and hide content when app is on background
      //  getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        View rootView = inflater.inflate(R.layout.fragment_connection_details, container, false);
        customerModel = new SiteVisitModel();

        activity = (SiteVisitListActivityDetails) getActivity();
        model = activity.getSiteVisitDataData();



        //  getData();
        noOfFloorFixStr = model.getAM_APP_NO_OF_FLOORS();
        populationFixStr = model.getAM_APP_FAMILY_MEMBER();
        noOfDwellingFixStr = model.getAM_APP_NO_OF_DWELLING();
        noOfBedsFixStr = model.getAM_APP_NO_OF_ROOMS();
        borewell = model.getAM_APP_BOREWELL();
        operstional = model.getAM_APP_IS_OPERATIONAL();
        appltype = model.getAM_APP_APPLI_TYPE();
        noofapp = model.getAM_APP_NOOF_APPS();
        buId = model.getAM_APP_BU();
        addapplicationtype = model.getAM_AAPP_NO_TYPE();

        connectionCategoryFixStr = model.getCATEGORY_NAME();//connectionCategoryFixStr
        connectionCategoryId = model.getAM_APP_CATEGORY();
        propertyTypeIdd = model.getAM_APP_PREMTYPE();
        propertyTypeName= model.getPRM_NAME();
        connectionPurposeId = model.getAM_APP_PURPOSE();
        connectionTypeId= model.getAM_APP_TARIFF();
        connectionPurposeStr = model.getPURPOSE_NAME();
        if (connectionCategoryFixStr.equalsIgnoreCase("Individual")) {
            connectionCategoryId = "1";
        } else {
            connectionCategoryId = "2";
        }


        //showDialog();
        initializeViews(rootView);
        // getFirmTypeRequest();

        if (addapplicationtype.equalsIgnoreCase("12")){



            noofTaps.setEnabled(false);
          //  applicationtypeSpinner.setEnabled(false);

            etBore.setEnabled(false);
            etOperational.setEnabled(false);
            connectionTypeSpinner.setEnabled(false);


            connectionCategorySpinner.setEnabled(false);



            connectionPurposeSpinner.setEnabled(false);

            propertyTypeSpinner.setEnabled(false);




            etNoOfFloor.setEnabled(false);

            etPopulation.setEnabled(false);

            etNoOFDwelling.setEnabled(false);


            etNoOfBeds.setEnabled(false);



            ll_no_of_beds.setEnabled(false);


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
    private void getData() {
        customerModel = activity.getCustomerList();
        // Log.d("Click" + "frg", customerModel.toString());

    }

    private void initializeViews(View rootView) {
        mCon = getActivity();

        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();
        realmOperations = new RealmOperations(mCon);

        tv = rootView.findViewById(R.id.tv);
        tv.setFocusable(true);
        tvNoOfapplication= rootView.findViewById(R.id.tvNoOfapplication);

        noofTaps= rootView.findViewById(R.id.etNoOfapplication);
 /*       applicationtypeSpinnerFix= rootView.findViewById(R.id.applicationtypeSpinnerFix);
        applicationtypeSpinner= rootView.findViewById(R.id.applicationtypeSpinner);
        applicationtypeSpinner.setOnItemSelectedListener(this);*/

        etBore= rootView.findViewById(R.id.etBore);
        tvbore= rootView.findViewById(R.id.tvbore);
        tvOperational= rootView.findViewById(R.id.tvOperational);
        etOperational= rootView.findViewById(R.id.etOperational);
        connectionTariffSpinnerFix = rootView.findViewById(R.id.connectionTariffSpinnerFix);
        connectionTypeSpinner = rootView.findViewById(R.id.connectionTypeSpinner);
        connectionTypeSpinner.setOnItemSelectedListener(this);


        connectionCategorySpinnerFix = rootView.findViewById(R.id.connectionCategorySpinnerFix);
        connectionCategorySpinner = rootView.findViewById(R.id.connectionCategorySpinner);
        connectionCategorySpinner.setOnItemSelectedListener(this);


        connectionPurposeSpinnerFix = rootView.findViewById(R.id.connectionPurposeSpinnerFix);
        connectionPurposeSpinner = rootView.findViewById(R.id.connectionPurposeSpinner);
        connectionPurposeSpinner.setOnItemSelectedListener(this);

        propertyTypeSpinnerFix = rootView.findViewById(R.id.propertyTypeSpinnerFix);
        propertyTypeSpinner = rootView.findViewById(R.id.propertyTypeSpinner);
        propertyTypeSpinner.setOnItemSelectedListener(this);
        etBore.setOnItemSelectedListener(this);
        etOperational.setOnItemSelectedListener(this);

        tvNoOfFloor = rootView.findViewById(R.id.tvNoOfFloor);
        etNoOfFloor = rootView.findViewById(R.id.etNoOfFloor);
        etNoOfFloor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String tmp = s.toString().trim();
                if(tmp.length()==1 && tmp.equals("0"))
                    s.clear();
            }
        });
        tvPopulation = rootView.findViewById(R.id.tvPopulation);
        etPopulation = rootView.findViewById(R.id.etPopulation);
        etPopulation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String tmp = s.toString().trim();
                if(tmp.length()==1 && tmp.equals("0"))
                    s.clear();
            }
        });

        tvNoOFDwelling = rootView.findViewById(R.id.tvNoOFDwelling);
        etNoOFDwelling = rootView.findViewById(R.id.etNoOFDwelling);


        tvNoOfBeds = rootView.findViewById(R.id.tvNoOfBeds);
        etNoOfBeds = rootView.findViewById(R.id.etNoOfBeds);

        btn_submit_connection = rootView.findViewById(R.id.btn_submit_connection);
        btn_submit_connection.setOnClickListener(this);

        ll_no_of_beds = rootView.findViewById(R.id.ll_no_of_beds);

        btn_connection_back = rootView.findViewById(R.id.btn_connection_back);
        btn_connection_back.setOnClickListener(this);

        initSetData();

        //
        setConnectionTypeDropDown();

        setConnectionCategory();
        setPropertyTypeDropDown();
       // setborewell();
        setoperational();
      //  setapplicanDropDown();
        setConnectionPurposeDropDown();
        setconnectiontype();//added sonali

    }
    private void setconnectiontype() {
        connectionCategoryFixStr = model.getCATEGORY_NAME();//connectionCategoryFixStr

        if(connectionCategoryFixStr.equalsIgnoreCase("M.C. Limit")){
            connectionCategoryId="1";
        }else{
            connectionCategoryId="2";
        }



        connectionCategorySpinnerFix.setAdapter(ConnCategoryAdapter);
        connectionCategorySpinnerFix.setSelection(Integer.parseInt(connectionCategoryId));
        connectionCategorySpinner.setAdapter(ConnCategoryAdapter);
        connectionCategorySpinner.setSelection(Integer.parseInt(connectionCategoryId));
        connectionCategorySpinner.setOnItemSelectedListener(this);

    }
    private void setConnectionTypeDropDown() {

        ArrayList<String> connectionType = new ArrayList<>();
        customerTypeModel = realmOperations.getCustomername(connectionTypeId);
        String custName="",jsonCartItems;int catInt;
        try {
            custName = customerTypeModel.getCUSTTYPETEXT();
            customerTypeArrayAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, Collections.singletonList(custName));


            connectionType = realmOperations.fetchCustomerTypeName();
            connectiontraiff.addAll(connectionType);
              customerTypeArrayAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item,connectiontraiff);
            customerTypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        } catch (Exception e) {
            e.printStackTrace();
        }
        customertraiff = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, connectionType);
        customertraiff.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        connectionTariffSpinnerFix.setAdapter(customertraiff);

        catInt = getCategoryconnection(custName);
        connectionTariffSpinnerFix.setSelection(catInt);
        connectionTypeSpinner.setAdapter(customerTypeArrayAdapter);
        connectionTypeSpinner.setSelection(catInt);


      /*  if(connectionTypeId.equalsIgnoreCase("1")){
            customerTypeModel = realmOperations.getCustomername("1");

            connectionTypeSpinnerFix.setSelection(0);

        }else if(connectionTypeId.equalsIgnoreCase("2")){
            connectionTypeSpinnerFix.setSelection(1);

        }else if(connectionTypeId.equalsIgnoreCase("3")){
            connectionTypeSpinnerFix.setSelection(2);

        }*/




        // connectionTypeSpinner.setSelection(Integer.parseInt(connectionTypeId));
       /* if(connectionTypeId.equalsIgnoreCase("1")){
            connectionTypeSpinner.setSelection(0);

        }else if(connectionTypeId.equalsIgnoreCase("2")){
            connectionTypeSpinner.setSelection(1);

        }else if(connectionTypeId.equalsIgnoreCase("3")){
            connectionTypeSpinner.setSelection(2);
        }*/

    }
  /*  private void setapplicanDropDown() {
        String[] items = new String[]{"Single", "Group"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mCon, android.R.layout.simple_spinner_dropdown_item, items);
        applicationtypeSpinner.setAdapter(adapter);

        applicationtypeSpinnerFix.setAdapter(adapter);
        if(appltype.equalsIgnoreCase("0")){
            applicationtypeSpinner.setSelection(0);
            applicationtypeSpinnerFix.setSelection(0);
            noofTaps.setText("1");
            tvNoOfapplication.setText("1");
            noofTaps.setEnabled(false);
        }else if(appltype.equalsIgnoreCase("1")){
            applicationtypeSpinner.setSelection(1);
            applicationtypeSpinnerFix.setSelection(1);
            noofTaps.setEnabled(true);
            noofTaps.setText(noofapp);
            tvNoOfapplication.setText(noofapp);



        }

    }*/
    private void setConnectionPurposeDropDown() {

        ArrayList<String> connectionPurposeStringList = new ArrayList<>();
        connectionPurposeStringList = realmOperations.fetchConnectionPurposeName();



        connectionPurposeList.add("Select");
        connectionPurposeList.addAll(connectionPurposeStringList);
        ConnectionPurposeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, connectionPurposeList);
        ConnectionPurposeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        connectionPurposeSpinnerFix.setAdapter(ConnectionPurposeAdapter);



        connectionPurposeSpinner.setAdapter(ConnectionPurposeAdapter);

        if(!connectionPurposeStr.equalsIgnoreCase("")) {

            int catInt = getCategoryconnectionPurposePos(connectionPurposeStr);


            connectionPurposeSpinnerFix.setSelection(catInt);
            connectionPurposeSpinner.setSelection(catInt);
        }else{
            connectionPurposeSpinner.setSelection(0);
        }

        connectionPurposeSpinner.setOnItemSelectedListener(this);

    }
    private int getCategoryconnectionPurposePos(String property) {
        return connectionPurposeList.indexOf(property);
    }
    private int getCategoryconnection(String property) {
        return connectiontraiff.indexOf(property);
    }

    private void setConnectionCategory() {

        ArrayList<String> connectionCategoryStringList = new ArrayList<>();
        connectionCategoryStringList = realmOperations.fetchConnectionCategoryName();
        ArrayList<String> connectionCategoryList = new ArrayList<>();
        connectionCategoryList.add("Select");
        connectionCategoryList.addAll(connectionCategoryStringList);
        ConnCategoryAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, connectionCategoryList);
        ConnCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //  connectionCategorySpinnerFix.setAdapter(ConnCategoryAdapter);
        //  connectionCategorySpinnerFix.setSelection(Integer.parseInt(connectionCategoryId));

        //  connectionCategorySpinner.setAdapter(ConnCategoryAdapter);
        //  connectionCategorySpinner.setSelection(Integer.parseInt(connectionCategoryId));
        //  connectionCategorySpinn
        //  er.setOnItemSelectedListener(this);

    }

    private void setPropertyTypeDropDown() {

        ArrayList<String> propertyType = new ArrayList<>();
        propertyType = realmOperations.fetchPropertyTypeName();
        connectionPurposePropertyTypeList.add("Select");
        connectionPurposePropertyTypeList.addAll(propertyType);

        PropertyTypeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, connectionPurposePropertyTypeList);
        PropertyTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        propertyTypeSpinnerFix.setAdapter(PropertyTypeAdapter);
        int catInt = getPropertyPos(propertyTypeName);

        propertyTypeSpinnerFix.setSelection(catInt);

        propertyTypeSpinner.setAdapter(PropertyTypeAdapter);
        propertyTypeSpinner.setSelection(catInt);

    }
    private void setborewell() {

        ArrayList<String> propertyType = new ArrayList<>();

        String[] bore = { "yes", "no"};
        PropertyTypeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, bore);
        PropertyTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        tvbore.setAdapter(PropertyTypeAdapter);
        if(borewell.equals("1")) {
            // int catInt = getPropertyPos(propertyTypeName);

            tvbore.setSelection(0);
        }else {
            tvbore.setSelection(1);
        }
        etBore.setAdapter(PropertyTypeAdapter);
        if(borewell.equals("1")) {
            // int catInt = getPropertyPos(propertyTypeName);

            etBore.setSelection(0);
        }else {
            etBore.setSelection(1);
        }

    }
    private void setoperational() {

        ArrayList<String> propertyType = new ArrayList<>();

        String[] bore = {"yes", "no"};
        PropertyTypeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, bore);
        PropertyTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        tvOperational.setAdapter(PropertyTypeAdapter);
        int catInt = getPropertyPos(propertyTypeName);
        if(operstional.equals("1")) {

            tvOperational.setSelection(0);
        }else {
            tvOperational.setSelection(1);
        }
        etOperational.setAdapter(PropertyTypeAdapter);

        if(operstional.equals("1")) {

            etOperational.setSelection(0);
        }else {
            etOperational.setSelection(1);
        }

    }

    private void initSetData() {

        // etBore .setText(borewell);
        // tvbore.setText(borewell);
        //  tvOperational .setText(operstional);
        // etOperational.setText(operstional);

        tvNoOfFloor.setText(noOfFloorFixStr);
        etNoOfFloor.setText(noOfFloorFixStr);


        tvPopulation.setText(populationFixStr);
        etPopulation.setText(populationFixStr);

        tvNoOFDwelling.setText(noOfDwellingFixStr);
        etNoOFDwelling.setText(noOfDwellingFixStr);


        tvNoOfBeds.setText(noOfBedsFixStr);
        etNoOfBeds.setText(noOfBedsFixStr);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit_connection:
                if (validation()) {
                    dataConnectionNext();
                }

                break;
            case R.id.btn_connection_back:
                backFragmentActivity();
                break;
            default:
                break;
        }
    }

    private void backFragmentActivity() {
        ((SiteVisitListActivityDetails)getActivity()).onClickPrev();
    }

    private void dataConnectionNext() {

        applnno= noofTaps.getText().toString();
        String  operstionaldata = etOperational.getSelectedItem().toString();
       // String  borevalue = etBore.getSelectedItem().toString();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(App.getContext());
        SharedPreferences.Editor editor = sp.edit();
      //  editor.putString("aaplicationtype",selectedValueappltype);
        editor.putString("appno",applnno);
       // editor.putString("BORE",borevalue);
        editor.putString("OPRATIONAL",operstionaldata);

        editor.commit();


        SiteVisitModel siteVisitModel = new SiteVisitModel(connectionTypeId, connectionCategoryId, propertyTypeId,
                connectionPurposeId, noOfFloorStr, populationStr,noOfDwellingStr, noOfBedsStr);
        //connectionPurposeId
        CD.sendConnectionData(siteVisitModel);
        // Toast.makeText(getActivity(), getResources().getString(R.string.details_submitted_successfully), Toast.LENGTH_SHORT).show();
        ((SiteVisitListActivityDetails) getActivity()).onClickNext();
    }

    public interface ConnectionData {
        //        void sendData(String submitStatus, String radiobuttonValStr,  String makerCodeId,String serialNoStr,String installDtStr,String meterSizeStr,String sealNoStr,String pastMeterReadingStr);
        void sendConnectionData(SiteVisitModel siteVisitModel);
    }

    private boolean validation() {
        if (!connectionTypeSpinner.getSelectedItem().toString().equals(null)){
            connectionTypeStr = connectionTypeSpinner.getSelectedItem().toString();

        }
        else {
            connectionTypeSpinner.setError(null);
            connectionTypeStr = "";
        }
        connectionCategoryStr = connectionCategorySpinner.getSelectedItem().toString();
        propertyTypeStr = propertyTypeSpinner.getSelectedItem().toString();

        connectionPurposeStr = connectionPurposeSpinner.getSelectedItem().toString();


        int connectionTypePos =connectionTypeSpinner.getSelection();//viren

  /*      if (connectionTypePos == 1){

            connectionTypeSpinner.setError(getResources().getString(R.string.cannot_be_empty));

            Toast.makeText(getActivity(), R.string.please_select_connection_type, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            connectionTypeSpinner.setError(null);

        }*/

        noOfFloorStr = etNoOfFloor.getText().toString();
        populationStr = etPopulation.getText().toString();
        noOfDwellingStr = etNoOFDwelling.getText().toString();
        noOfBedsStr = etNoOfBeds.getText().toString();

      /*  String noofapp=  noofTaps.getText().toString();
        if(noofapp.equalsIgnoreCase("0")){
            Toast.makeText(getActivity(), "No. Of Application should not be 0 ", Toast.LENGTH_SHORT).show();
            return false;
        }*/
        if (connectionCategoryStr.equalsIgnoreCase("Select")) {

            connectionCategorySpinner.setError(getResources().getString(R.string.cannot_be_empty));

            Toast.makeText(getActivity(), R.string.please_select_connection_category, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            connectionCategorySpinner.setError(null);

        }
        if (propertyTypeStr.equalsIgnoreCase("Select")) {

            propertyTypeSpinner.setError(getResources().getString(R.string.cannot_be_empty));

            Toast.makeText(getActivity(), R.string.please_select_property_type, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            propertyTypeSpinner.setError(null);

        }
        if (connectionPurposeStr.equalsIgnoreCase("Select")) {

            connectionPurposeSpinner.setError(getResources().getString(R.string.cannot_be_empty));

            Toast.makeText(getActivity(), R.string.please_select_connection_purpose, Toast.LENGTH_SHORT).show();
            return false;
        } else {

            connectionPurposeSpinner.setError(null);

        }
        if (noOfFloorStr.equalsIgnoreCase("")) {
            etNoOfFloor.setError(getResources().getString(R.string.cannot_be_empty));
            Toast.makeText(getActivity(), R.string.please_enter_number_of_floors, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            etNoOfFloor.setError(null);

        }
        if (populationStr.equalsIgnoreCase("")) {
            etPopulation.setError(getResources().getString(R.string.cannot_be_empty));
            Toast.makeText(getActivity(), R.string.please_enter_number_population, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            etPopulation.setError(null);

        }
      /*  if (noOfDwellingStr.equalsIgnoreCase("")) {
            etNoOFDwelling.setError(getResources().getString(R.string.cannot_be_empty));
            Toast.makeText(getActivity(), R.string.please_enter_number_of_rooms, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            etNoOFDwelling.setError(null);

        }*/


        if (checkVisibility(ll_no_of_beds)) {
            if (noOfBedsStr.equalsIgnoreCase("")) {
                etNoOfBeds.setError(getResources().getString(R.string.cannot_be_empty));
                Toast.makeText(getActivity(), R.string.please_enter_number_of_beds, Toast.LENGTH_SHORT).show();
                return false;
            } else {
                etNoOfBeds.setError(null);

            }
        }



        return true;
    }


    @Override
    public void onItemSelected(MaterialSpinner materialSpinner, View view, int i, long l) {
        switch (materialSpinner.getId()) {
            case R.id.connectionTypeSpinner: {
                String selectedValue = connectionTypeSpinner.getSelectedItem().toString();

/*
                customerTypeModel = realmOperations.getCustomerTypeId(selectedValue);
                connectionTypeId = customerTypeModel.getCUSTTYPEID();
*/


                connectionIdList = realmOperations.fetchCustomerTypeNameByBuID(selectedValue,buId);
                connectionTypeId = customerTypeModel.getCUSTTYPEID();


            }
            break;
            case R.id.connectionCategorySpinner: {
                String selectedValue = connectionCategorySpinner.getSelectedItem().toString();
                if (selectedValue.equalsIgnoreCase("Select")) {
                    connectionCategoryId= "0";
                } else{
                    connCategoryModel = realmOperations.getConnCategoryId(selectedValue);
                    connectionCategoryId = connCategoryModel.getCATEGORY_ID();
                }
            }
            break;

            case R.id.propertyTypeSpinner: {
                String selectedValue = propertyTypeSpinner.getSelectedItem().toString();
                if (selectedValue.equalsIgnoreCase("Select")) {
                    propertyTypeId = "0";
                } else {
                    if(selectedValue.equalsIgnoreCase("Hotel / Guest House")|| selectedValue.equalsIgnoreCase("Banquet Hall / Marriage complex")|| selectedValue.equalsIgnoreCase("Hospital / Nursing house")){
                        ll_no_of_beds.setVisibility(View.VISIBLE);
                    }else{
                        ll_no_of_beds.setVisibility(View.GONE);
                    }
                    propertyTypeModel = realmOperations.getPropertyTypeId(selectedValue);
                    propertyTypeId = propertyTypeModel.getPRM_ID();
                    propertyTarifId = propertyTypeModel.getPRM_TARIFF();
                    connPurposeModel = realmOperations.getConnPurposeTypeId(propertyTarifId);
                    connectionPurposeId = connPurposeModel.getMCT_ID();
                    connectionPurposeStr = connPurposeModel.getMCT_CONNTYPE_NAME();
                    if(!connectionPurposeStr.equalsIgnoreCase("")) {
                        int catInt = getCategoryconnectionPurposePos(connectionPurposeStr);
                        connectionPurposeSpinner.setSelection(catInt);
                    }else{
                        connectionPurposeSpinner.setSelection(0);
                    }
                 /*   if(connectionPurposeId.equalsIgnoreCase("1")){
                        connectionTypeSpinner.setSelection(0);

                    }else if(connectionPurposeId.equalsIgnoreCase("2")){
                        connectionTypeSpinner.setSelection(0);


                    }else if(connectionPurposeId.equalsIgnoreCase("3")){
                        connectionTypeSpinner.setSelection(0);
                    }*/
                }


            }
            break;

            case R.id.connectionPurposeSpinner: {

                String selectedValue = connectionPurposeSpinner.getSelectedItem().toString();
                if (selectedValue.equalsIgnoreCase("Select")) {
                    connectionPurposeId = "0";
                } else {

                    if(selectedValue.equalsIgnoreCase("Hotel / Guest House")|| selectedValue.equalsIgnoreCase("Banquet Hall / Marriage complex")|| selectedValue.equalsIgnoreCase("Hospital / Nursing house")){
                        ll_no_of_beds.setVisibility(View.VISIBLE);
                    }else{
                        ll_no_of_beds.setVisibility(View.GONE);

                    }try {
                        connPurposeModel = realmOperations.getConnPurposeTypeId(propertyTarifId);
                        connectionPurposeId = connPurposeModel.getMCT_ID();
                    }catch (Exception E){
                        E.printStackTrace();
                    }
                       /* if(connectionPurposeId.equalsIgnoreCase("2")||
                                 connectionPurposeId.equalsIgnoreCase("3")||
                                connectionPurposeId.equalsIgnoreCase("4") ||
                                connectionPurposeId.equalsIgnoreCase("5") ||
                                connectionPurposeId.equalsIgnoreCase("8") ||
                                connectionPurposeId.equalsIgnoreCase("17"))
                        {*/
                    //  connectionTypeSpinner.setSelection(Integer.parseInt(connectionPurposeId));
                       /* }else {
                            connectionTypeSpinner.setSelection(1);

                        }*/

                    //     setConnectionPurposeSameDropDown(connectionPurposeId);
                }
                 /*   if (selectedValue.equalsIgnoreCase("Select")) {
                        connectionPurposeId = "0";
                    }else{
                        connPurposeModel = realmOperations.getConnPurposeId(selectedValue);
                        connectionPurposeId = connPurposeModel.getMCT_ID();
                    }*/
            }
            break;
            /*case R.id.applicationtypeSpinner: {
                selectedValueappltype = applicationtypeSpinner.getSelectedItem().toString();
                if (selectedValueappltype.equalsIgnoreCase("Single")) {
                    noofTaps.setText("1");
                    tvNoOfapplication.setText("1");
                    noofTaps.setEnabled(false);
                    applnno= noofTaps.getText().toString();
                    //  applicationtype = model.getselectedValue_APPLI_TYPE();
                    //   noofappln = model.getapplnno();
                } else{

                    noofTaps.setEnabled(true);
                    noofTaps.setText("");
                    tvNoOfapplication.setText("");
                    applnno= noofTaps.getText().toString();

                    // applicationtype = model.getselectedValue_APPLI_TYPE();
                    //   noofappln = model.getapplnno();
                }
            }
            break;*/

            default:
                break;

        }
    }

    private void setConnectionPurposeSameDropDown(String selectedID) {
        ArrayList<String> connectionPurposePropertyType = new ArrayList<>();
        connPurposeModel= realmOperations.fetchConnPurposeTypeNameByID(selectedID);
        propertyTypeName =connPurposeModel.getMCT_CONNTYPE_NAME();
        int catInt = getCategoryPos(propertyTypeName);


        //   propertyTypeSpinner.setSelection(catInt);
    }
    private int getCategoryPos(String property) {
        return connectionPurposePropertyTypeList.indexOf(property);
    }
    private int getPropertyPos(String property) {
        return connectionPurposePropertyTypeList.indexOf(property);
    }

    @Override
    public void onNothingSelected(MaterialSpinner materialSpinner) {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser) {
                new NCConnectionDetailsFragment();
                getData();


            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            CD = (NCConnectionDetailsFragment.ConnectionData) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }

    private boolean checkVisibility(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            return true;
        }
        return false;
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
