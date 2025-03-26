package elink.suezShimla.water.crm.Complaint.RegistrationAndHistory.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.Complaint.RegistrationAndHistory.Adapter.ComplaintHistoryAdapter;
import elink.suezShimla.water.crm.Complaint.RegistrationAndHistory.Model.ComplaintHistoryModel;
import elink.suezShimla.water.crm.Complaint.RegistrationAndHistory.Model.ConsumerAndMeterListModel;
import elink.suezShimla.water.crm.Complaint.RegistrationAndHistory.Model.ConsumerComplaintDetailModel;
import elink.suezShimla.water.crm.Complaint.RegistrationAndHistory.Model.ConsumerComplaintMeterModel;
import elink.suezShimla.water.crm.Complaint.RegistrationAndHistory.Model.HistoryModel;
import elink.suezShimla.water.crm.Complaint.RegistrationAndHistory.Model.MeterNumberModel;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ComplaintSubTypeModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ComplaintTypeModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;

public class ComplaintHistoryActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mCon;
    private BottomSheetDialog sheetBehavior, filterSheetBehavior;
   // private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView complaintHistoryRecyclerView;
    private List<ComplaintHistoryModel> complaintHistoryModelList;
    private ComplaintHistoryAdapter ComplaintHistoryAdapter;
    private ImageView closeImageView, filterCloseImageView;
    private MaterialButton searchButton, filterButton,registerFab,btnHistory;
    private TextView  complaintTypeTextView,consumerNoTextView,
            statusTextView,vipTextView, cityStateTextView ;
    private EditText consumerAddressTextView, landmarkTextView, contactNoTextView, co_so_wo_textView, regMailTextView,
            postalCodeTextView, consumerNameTextView, streetLocalityTextView, assesseNoTextView;
   // private FloatingActionButton registerFab;
    private AppCompatSpinner searchSpinner, complaintTypeSpinner, complaintSubTypeSpinner, serviceTypeSpinner;
    private MaterialDialog progress, filterProgress;
    private LinearLayout consumerLinearLayout, nameAndAddressLinearLayout, mobileNumberLinearLayout, meterNumberLinearLayout, errorLinear;
    private RelativeLayout fabRelativeLayout;
    private TextInputLayout consumerNoTextInputLayout;
    private TextInputEditText consumerNoEditText;
    private String consumerNo, complaintTypeIdValueStr = "0", complaintSubTypeIdStr = "0";

    private ComplaintTypeModel complaintTypeModel;
    private ComplaintSubTypeModel complaintSubTypeModel;
    private String complaintTypeName, firstNameStr, middleNameStr, lastNameStr, infoDateStr, lastBillAmountStr, arrearAmtStr, mobileNoStr, emailStr, meterNo,meterNoStr="",strStreetLocality="",
            consumerNameStr, addressStr, areaStr, pinCodeStr, meterNumberStr, statusStr, complaintTypeStr, zoneId, subZoneId,vipName, landmark="",
     city="", postal_code="", co_so_wo_str="", assess_no;
    List<HistoryModel> historyModelList;
    private int complaintTypeId, complaintSubTypeId;

    private ArrayList<String> complaintTypeList, complaintSubTypeList;
    private ArrayAdapter complaintTypeAdapter, complaintSubTypeAdapter;

    private RealmOperations realmOperations;
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    private String jsonResponse = "", jsonHistoryResponse="", serviceId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_history);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mCon = this;
        sheetBehavior = new BottomSheetDialog(Objects.requireNonNull(mCon));
        filterSheetBehavior = new BottomSheetDialog(Objects.requireNonNull(mCon));

        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();
        realmOperations = new RealmOperations(mCon);

        String siteEng = null;
        try {
            siteEng = new AesAlgorithm().decrypt(PreferenceUtil.getSiteEng());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(siteEng.equals("SiteEng")){
            complaintTypeList = realmOperations.fetchComplaintTypeBySE();
        }else{
            complaintTypeList = realmOperations.fetchComplaintType();
        }

        consumerNoTextView = findViewById(R.id.consumerNoTextView);
        //complaintTypeTextView = findViewById(R.id.complaintSubTypeTextView);
        consumerNameTextView = findViewById(R.id.consumerNameTextView);
       // consumerNameTextView.setSelected(true);
        contactNoTextView = findViewById(R.id.contactNoTextView);
        assesseNoTextView = findViewById(R.id.assesseNoTextView);
        co_so_wo_textView = findViewById(R.id.co_so_wo_textView);
        consumerAddressTextView = findViewById(R.id.consumerAddressTextView);
        statusTextView = findViewById(R.id.statusTextView);
        errorLinear = findViewById(R.id.errorLinear);
        fabRelativeLayout = findViewById(R.id.fabRelativeLayout);
        vipTextView = findViewById(R.id.vipTextView);
        landmarkTextView = findViewById(R.id.landmarkTextView);
        vipTextView.setSelected(true);
        streetLocalityTextView = findViewById(R.id.streetLocalityTextView);
        postalCodeTextView = findViewById(R.id.postalCodeTextView);
        cityStateTextView = findViewById(R.id.cityStateTextView);
        cityStateTextView.setSelected(true);
        regMailTextView = findViewById(R.id.regMailTextView);

        complaintHistoryRecyclerView = findViewById(R.id.complaintHistoryRecyclerView);
        registerFab = findViewById(R.id.registerFab);
        btnHistory = findViewById(R.id.btnHistory);
        complaintHistoryRecyclerView.setHasFixedSize(true);
        complaintHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(mCon));

      /*  swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark, android.R.color.holo_red_dark);
*/
        ComplaintHistoryAdapter = new ComplaintHistoryAdapter(mCon);
        searchBottomSheetDialog();
        filterBottomSheetDialog();

        registerFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
        btnHistory.setOnClickListener(this);




    }

    private void validation(){
        firstNameStr = consumerNameTextView.getText().toString();
        addressStr = consumerAddressTextView.getText().toString();
        landmark = landmarkTextView.getText().toString();
        mobileNoStr = contactNoTextView.getText().toString();
        strStreetLocality = streetLocalityTextView.getText().toString();
        postal_code = postalCodeTextView.getText().toString();
        emailStr = regMailTextView.getText().toString().trim();

        if(firstNameStr.equalsIgnoreCase("") || addressStr.equalsIgnoreCase("") || landmark.equalsIgnoreCase("")|| mobileNoStr.equalsIgnoreCase("")|| strStreetLocality.equalsIgnoreCase("")|| postal_code.equalsIgnoreCase("")){
            if(firstNameStr.equalsIgnoreCase("")){
                Toast.makeText(mCon, "Please enter consumer name", Toast.LENGTH_SHORT).show();
            }else if(addressStr.equalsIgnoreCase("")){
                Toast.makeText(mCon, "Please enter consumer address", Toast.LENGTH_SHORT).show();
            }else if(landmark.equalsIgnoreCase("")){
                Toast.makeText(mCon, "Please enter landmark", Toast.LENGTH_SHORT).show();
            }else if(mobileNoStr.equalsIgnoreCase("")){
                Toast.makeText(mCon, "Please enter mobile number", Toast.LENGTH_SHORT).show();
            }else if(strStreetLocality.equalsIgnoreCase("")){
                Toast.makeText(mCon, "Please enter Street/Locality", Toast.LENGTH_SHORT).show();
            }else if(postal_code.equalsIgnoreCase("")){
                Toast.makeText(mCon, "Please enter postal code", Toast.LENGTH_SHORT).show();
            }else if(postal_code.length() > 0 && postal_code.length() < 6){
                Toast.makeText(mCon, "Postal code Invalid", Toast.LENGTH_SHORT).show();
            }

        }else {
            String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

            if(emailStr.equalsIgnoreCase("")){
                registerClick();
            }else{
                if(emailStr.length()>0 ){
                    if (emailStr.matches(emailPattern)) {
                        registerClick();
                    } else {
                        Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show();
                    }

                }
            }

        }
    }

    private void registerClick() {
        Intent intent = new Intent(mCon, ComplaintRegistrationActivity.class);
        intent.putExtra("consumerNo", consumerNo);
        intent.putExtra("firstName", firstNameStr);
        intent.putExtra("middleName", middleNameStr);
        intent.putExtra("lastName", lastNameStr);
        intent.putExtra("sendSMS", mobileNoStr);
        intent.putExtra("lastBillAmt", lastBillAmountStr);
        intent.putExtra("arrearAmt", arrearAmtStr);
        intent.putExtra("status", statusStr);
        intent.putExtra("email", emailStr);
        intent.putExtra("zone", zoneId);
        intent.putExtra("subZone", subZoneId);

        intent.putExtra("city", city);
        intent.putExtra("landmark", landmark);
        intent.putExtra("postal_code", postal_code);
        intent.putExtra("mobileNoStr", mobileNoStr);
        intent.putExtra("serviceId", serviceId);
        intent.putExtra("addressStr", addressStr);
        intent.putExtra("meterNoStr", meterNoStr);
        startActivity(intent);
    }

    private void searchBottomSheetDialog() {
        @SuppressLint("InflateParams")
        View sheetView = Objects.requireNonNull(getLayoutInflater().inflate(R.layout.bottom_dialog_search_consumer_history, null));
        sheetBehavior.setContentView(sheetView);

        closeImageView = sheetView.findViewById(R.id.employeeCloseImageView);
        searchButton = sheetView.findViewById(R.id.searchButton);
        searchSpinner = sheetView.findViewById(R.id.searchSpinner);

        consumerLinearLayout = sheetView.findViewById(R.id.consumerLinearLayout);
      /*  nameAndAddressLinearLayout = sheetView.findViewById(R.id.nameAndAddressLinearLayout);
        mobileNumberLinearLayout = sheetView.findViewById(R.id.mobileNumberLinearLayout);
        meterNumberLinearLayout = sheetView.findViewById(R.id.meterNumberLinearLayout);*/

        consumerNoTextInputLayout = sheetView.findViewById(R.id.consumerNoTextInputLayout);
       /* nameTextInputLayout = sheetView.findViewById(R.id.nameTextInputLayout);
        addressTextInputLayout = sheetView.findViewById(R.id.addressTextInputLayout);
        areaTextInputLayout = sheetView.findViewById(R.id.areaTextInputLayout);
        pinCodeInputLayout = sheetView.findViewById(R.id.pinCodeInputLayout);
        mobileNumberTextInputLayout = sheetView.findViewById(R.id.mobileNumberTextInputLayout);
        meterNumberTextInputLayout = sheetView.findViewById(R.id.meterNumberTextInputLayout);*/

        consumerNoEditText = sheetView.findViewById(R.id.consumerNoEditText);
       /* nameEditText = sheetView.findViewById(R.id.nameEditText);
        addressEditText = sheetView.findViewById(R.id.addressEditText);
        areaEditText = sheetView.findViewById(R.id.areaEditText);
        pinCodeEditText = sheetView.findViewById(R.id.pinCodeEditText);
        mobileNumberEditText = sheetView.findViewById(R.id.mobileNumberEditText);
        meterNumberEditText = sheetView.findViewById(R.id.meterNumberEditText);*/

        searchSpinner.setEnabled(false);
        searchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    setFilterVisibility(true, false, false, false);
                } else if (position == 1) {
                    setFilterVisibility(false, true, false, false);
                } else if (position == 2) {
                    setFilterVisibility(false, false, true, false);
                } else if (position == 3) {
                    setFilterVisibility(false, false, false, true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setFilterVisibility(true, false, false, false);
            }
        });

        sheetBehavior.setCanceledOnTouchOutside(false);
        sheetBehavior.setCancelable(false);
        sheetBehavior.show();
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                consumerNo = consumerNoEditText.getText().toString().trim();
               /* name = nameEditText.getText().toString().trim();
                address = addressEditText.getText().toString().trim();
                area = areaEditText.getText().toString().trim();
                pinCode = pinCodeEditText.getText().toString().trim();
                mobileNumber = mobileNumberEditText.getText().toString().trim();
                meterNumber = meterNumberEditText.getText().toString().trim();*/

                validateSearchFilter();

            }
        });

        closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (complaintHistoryModelList != null) {
                    sheetBehavior.cancel();
                } else {
                    finish();
                }
            }
        });

    }

    private void validateSearchFilter() {

        boolean isValidConsumerNo = false,
                isValidName = false, isValidAddressStr = false, isValidAreaStr = false,
                isValidPinCode = false, isValidMobileNumber = false, isValidMeterNumber = false;

        // if (consumerLinearLayout.getVisibility() == View.VISIBLE) {

        if (TextUtils.isEmpty(consumerNo)) {
            consumerNoTextInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            consumerNoTextInputLayout.setError(null);
            isValidConsumerNo = true;
        }

        if (isValidConsumerNo) {
            String params[] = new String[2];
            String zoneid = PreferenceUtil.getFirstZoneAvailable();

            //you can get serviceId here
            params[0] = consumerNo;
            params[1] = zoneid;

            if (connection.isConnectingToInternet()) {
                SearchConsumerById searchConsumerById = new SearchConsumerById();
                searchConsumerById.execute(params);

                progress = new MaterialDialog.Builder(mCon)
                        .content(R.string.loading)
                        .progress(true, 0)
                        .autoDismiss(false)
                        .canceledOnTouchOutside(false)
                        .widgetColorRes(R.color.colorPrimary)
                        .show();
            } else {
                Toast.makeText(mCon, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            }
            consumerNoEditText.setText("");
        }
    }

    private void filterBottomSheetDialog() {
        @SuppressLint("InflateParams")
        View sheetView = Objects.requireNonNull(getLayoutInflater().inflate(R.layout.bottom_dialog_filter_consumer_history, null));
        filterSheetBehavior.setContentView(sheetView);

        filterCloseImageView = sheetView.findViewById(R.id.filterCloseImageView);
        complaintTypeSpinner = sheetView.findViewById(R.id.complaintTypeSpinner);
        complaintSubTypeSpinner = sheetView.findViewById(R.id.complaintSubTypeSpinner);
        filterButton = sheetView.findViewById(R.id.filterButton);

        complaintTypeList = realmOperations.fetchComplaintType();

        ArrayList<String> complaintType = new ArrayList<>();
        complaintType.add("All");
        complaintType.addAll(complaintTypeList);

        complaintTypeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, complaintType);
        complaintTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        complaintTypeSpinner.setAdapter(complaintTypeAdapter);

        complaintTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                String complaintTypeName = complaintTypeSpinner.getSelectedItem().toString();
                complaintTypeModel = realmOperations.fetchComplaintTypeByName(complaintTypeName);

                if (complaintTypeName.equals("All")) {
                    complaintTypeId = 0;
                    ArrayList<String> subAll = new ArrayList<>();
                    subAll.add("All");
                    complaintSubTypeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, subAll);
                    complaintSubTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    complaintSubTypeSpinner.setAdapter(complaintSubTypeAdapter);
                } else {
                    complaintTypeId = complaintTypeModel.getCMTM_ID();
                    complaintTypeIdValueStr = String.valueOf(complaintTypeId);
                    complaintSubTypeList = realmOperations.fetchComplaintSubType(complaintTypeId);

                    ArrayList<String> complaintSubType = new ArrayList<>();

                    complaintSubType.add("All");
                    complaintSubType.addAll(complaintSubTypeList);

                    complaintSubTypeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, complaintSubType);
                    complaintSubTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    complaintSubTypeSpinner.setAdapter(complaintSubTypeAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        complaintSubTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String complaintSubTypeName = complaintSubTypeSpinner.getSelectedItem().toString();

                if (complaintSubTypeName.equals("All")) {
                    complaintSubTypeId = 0;
                } else {
                    complaintSubTypeModel = realmOperations.fetchComplaintSubTypeByName(complaintSubTypeName);
                    complaintSubTypeId = complaintSubTypeModel.getCOMPLAINTSUBTYPEID();
                }
                complaintSubTypeIdStr = String.valueOf(complaintSubTypeId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String params[] = new String[3];

                params[0] = consumerNo;
                params[1] = complaintTypeIdValueStr;
                params[1] = complaintSubTypeIdStr;

                if (connection.isConnectingToInternet()) {
                    ComplaintHistory complaintHistory = new ComplaintHistory();
                    complaintHistory.execute(params);

                    filterProgress = new MaterialDialog.Builder(mCon)
                            .content(R.string.loading)
                            .progress(true, 0)
                            .autoDismiss(false)
                            .canceledOnTouchOutside(false)
                            .widgetColorRes(R.color.colorPrimary)
                            .show();
                } else {
                    Toast.makeText(mCon, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }
            }
        });

        filterCloseImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterSheetBehavior.cancel();
            }
        });
    }

    // Search Filter Visibility Function
    private void setFilterVisibility(boolean consumerNo, boolean nameAndAdd, boolean mobileNo, boolean meterNo) {
        consumerLinearLayout.setVisibility(consumerNo ? View.VISIBLE : View.GONE);
        /*nameAndAddressLinearLayout.setVisibility(nameAndAdd ? View.VISIBLE : View.GONE);
        mobileNumberLinearLayout.setVisibility(mobileNo ? View.VISIBLE : View.GONE);
        meterNumberLinearLayout.setVisibility(meterNo ? View.VISIBLE : View.GONE);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.complaint_search_filter_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_search) {
            sheetBehavior.show();
            return true;
        } else if (id == R.id.action_filter) {
            filterSheetBehavior.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

   /* @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        String params[] = new String[3];

        params[0] = consumerNo;
        params[1] = "0";
        params[1] = "0";

        if (connection.isConnectingToInternet()) {
            ComplaintHistory complaintHistory = new ComplaintHistory();
            complaintHistory.execute(params);
        } else {
            Toast.makeText(mCon, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }
*/
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnHistory:

                Intent intent = new Intent(this,ConsumerHistoryActivity.class);
                intent.putParcelableArrayListExtra("history", (ArrayList<? extends Parcelable>) historyModelList);
                intent.putExtra("consumerNumber",consumerNo);

                startActivity(intent);


                break;
            default:
        }

    }

    @SuppressLint("StaticFieldLeak")
    private class SearchConsumerById extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try{
                String paraName[] = new String[2];
                paraName[0] = "W_ServiceNo";
                paraName[1] = "Utility_Id";

                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.ComplaintSearchByConsumerNo, params, paraName);

              //  Log.e("ComplaintSearchByConsumerNoResp",jsonResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                ConsumerAndMeterListModel enums = gson.fromJson(jsonResponse, ConsumerAndMeterListModel.class);
                //  ConsumerComplaintDetailModel[] enums = gson.fromJson(jsonResponse, ConsumerComplaintDetailModel[].class);

                //Log.d("check", "test " + enums);
                if (enums != null) {
                    if (enums.getConsumerComplaintDetailModel() != null && !enums.getConsumerComplaintDetailModel().isEmpty()
                            && enums.getConsumerComplaintMeterModels() != null && !enums.getConsumerComplaintMeterModels().isEmpty()
                        /*    && enums.getHistoryModel() != null && !enums.getHistoryModel().isEmpty()*/) {
                        for (ConsumerComplaintDetailModel model : enums.getConsumerComplaintDetailModel()) {
                            firstNameStr = model.getSRM_S_FIRST_NAME();
                            if(firstNameStr == null){
                                firstNameStr = "";
                                consumerNameTextView.setEnabled(true);
                            }

                            middleNameStr = model.getSRM_S_MIDDLE_NAME();
                            if(middleNameStr == null){
                                middleNameStr = "";
                            }

                            lastNameStr = model.getSRM_S_SURNAME();
                            if(lastNameStr == null){
                                lastNameStr = "";
                            }

                            //consumerNoStr = model.getSRM_SERVICE_NO();
                            mobileNoStr = model.getMOBILE_NO();
                            emailStr = model.getSRM_S_EMAIL();
                            meterNo = model.getSRM_METERS();
                            lastBillAmountStr = String.valueOf(model.getSRM_BILL_AMOUNT());
                            arrearAmtStr = String.valueOf(model.getSRM_ARREARS());

                            complaintTypeStr = model.getPRM_PURPOSE_NAME();
                            addressStr = model.getSRM_S_ADDRESS1();
                            statusStr = model.getDTM_DISCONN_TAG_NAME();

                            zoneId = model.getBUM_BU_NAME().substring(0, 1);
                            subZoneId = model.getPCM_PC_NAME();
                            vipName=model.getVIP_CATEGORY();
                            landmark = model.getSRM_S_ADDRESS3();
                            city = model.getADDRESS4();
                            String state = model.getSM_STATE_NAME();
                            String city_state = city+"/"+state;
                            postal_code = model.getSRM_B_PIN();
                            assess_no = model.getPROPERTY_ASSESSMENT_NO();
                           // co_so_wo_str = model.co

                            consumerNoTextView.setText(model.getSRM_NEW_NO());
                           // complaintTypeTextView.setText(complaintTypeStr);
                            consumerNameTextView.setText(firstNameStr + " " +middleNameStr+ " " +lastNameStr);
                            contactNoTextView.setText(mobileNoStr);
                            consumerAddressTextView.setText(addressStr);
                            statusTextView.setText(statusStr);
                            vipTextView.setText(vipName);
                            landmarkTextView.setText(landmark);
                            cityStateTextView.setText(city_state);
                            //locality = model.getLoc
                            String streetLocality = model.getSRM_S_ADDRESS2();
                            streetLocalityTextView.setText(streetLocality);
                            postalCodeTextView.setText(postal_code);
                            regMailTextView.setText(emailStr);
                            assesseNoTextView.setText(consumerNo);

                            if(addressStr == null || addressStr.equalsIgnoreCase("")){
                                consumerAddressTextView.setEnabled(true);
                            }

                            if(landmark == null || landmark.equalsIgnoreCase("")){
                                landmarkTextView.setEnabled(true);
                            }

                            if(mobileNoStr == null || mobileNoStr.equalsIgnoreCase("")){
                                contactNoTextView.setEnabled(true);
                            }

                            if(emailStr == null || emailStr.equalsIgnoreCase("")|| emailStr.equalsIgnoreCase("NA")){
                                regMailTextView.setEnabled(true);
                                regMailTextView.setText("");
                            }

//                            if(co_so_wo_str == null || co_so_wo_str.equalsIgnoreCase("")){
                                co_so_wo_textView.setEnabled(true);
//                            }
//
                            if(postal_code == null || postal_code.equalsIgnoreCase("")){
                                postalCodeTextView.setEnabled(true);
                            }


                            if(city_state.equalsIgnoreCase("/")){
                                cityStateTextView.setEnabled(true);
                            }

                            emailStr = regMailTextView.getText().toString();

                        }  for (ConsumerComplaintMeterModel consumerComplaintMeterModel : enums.getConsumerComplaintMeterModels()) {
                            meterNoStr = consumerComplaintMeterModel.getMTRM_SERIAL_NO();

                        }
                        historyModelList = new ArrayList<>();
                        historyModelList = enums.getHistoryModel();
                        Log.d("list",historyModelList.toString());
                        /*for (HistoryModel historyModel : enums.getHistoryModel()) {
                            HistoryModel historyModel1 =


                        }*/



                       // swipeRefreshLayout.setVisibility(View.VISIBLE);
                        fabRelativeLayout.setVisibility(View.VISIBLE);
                        sheetBehavior.cancel();
                        errorLinear.setVisibility(View.GONE);

                        realmOperations.deleteMeterNumberTable();
                        for (ConsumerComplaintMeterModel meterModel : enums.getConsumerComplaintMeterModels()) {

                            MeterNumberModel meterNumberModel = new MeterNumberModel(meterModel.getMTRM_SERIAL_NO());
                            realmOperations.addMeterNumber(meterNumberModel);
                        }

                     /*   String params[] = new String[3];

                        params[0] = consumerNo;
                        params[1] = "0";
                        params[2] = "0";

                      //  Log.e("ComaplintParams",Arrays.toString(params));
                        ComplaintHistory complaintHistory = new ComplaintHistory();
                        complaintHistory.execute(params);*/

                     /*   filterProgress = new MaterialDialog.Builder(mCon)
                                .content(R.string.loading)
                                .progress(true, 0)
                                .autoDismiss(false)
                                .canceledOnTouchOutside(false)
                                .widgetColorRes(R.color.colorPrimary)
                                .show();*/

                    } else {
                        Toast.makeText(mCon, R.string.consumer_no_does_not_exist, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mCon, R.string.no_data_found, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(mCon, e.getMessage(), Toast.LENGTH_SHORT).show();
                String error = e.toString();
                ErrorClass.errorData(mCon, "ComplaintHistoryActivity", "SearchConsumerById", error);
            }

            progress.dismiss();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class ComplaintHistory extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {

            try {
                String paraName[] = new String[3];

                paraName[0] = "ServiceNo";
                paraName[1] = "W_Comtype";
                paraName[2] = "drp_wuc_ComplaintRes";

                jsonHistoryResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.ComplaintHistory, params, paraName);

              //  Log.e("ComplaintHistory",jsonHistoryResponse);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                ComplaintHistoryModel[] enums = gson.fromJson(jsonHistoryResponse, ComplaintHistoryModel[].class);

                if (enums.length > 0) {
                    complaintHistoryModelList = Arrays.asList(enums);
                    ComplaintHistoryAdapter.addList(complaintHistoryModelList);
                    complaintHistoryRecyclerView.setAdapter(ComplaintHistoryAdapter);
                } else {
                    //Toast.makeText(mCon, "Complaint history not available", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d("check", e.getMessage());
                Toast.makeText(mCon, e.getMessage(), Toast.LENGTH_SHORT).show();

                String error = e.toString();
                ErrorClass.errorData(mCon, "ComplaintHistory", "filterButton", error);
            }

            filterProgress.dismiss();
            filterSheetBehavior.dismiss();
          //  swipeRefreshLayout.setRefreshing(false);
        }
    }
    @Override
    public void onResume() {
        super.onResume();

        App myApp = (App) this.getApplication();
        if (myApp.wasInBackground) {
            finish();
            startActivity(new Intent(mCon, SplashScreen.class));
        }

        myApp.stopActivityTransitionTimer();
    }

    @Override
    public void onPause() {
        super.onPause();
        realmOperations.close();
        ((App) this.getApplication()).startActivityTransitionTimer();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(new Intent(mCon, SplashScreen.class));
    }
}
