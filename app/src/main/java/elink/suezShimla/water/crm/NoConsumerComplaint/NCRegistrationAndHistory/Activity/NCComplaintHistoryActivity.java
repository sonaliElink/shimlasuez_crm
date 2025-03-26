package elink.suezShimla.water.crm.NoConsumerComplaint.NCRegistrationAndHistory.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ComplaintSubTypeModel;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ComplaintTypeModel;
import elink.suezShimla.water.crm.NoConsumerComplaint.NCRegistrationAndHistory.Adapter.NCComplaintHistoryAdapter;
import elink.suezShimla.water.crm.NoConsumerComplaint.NCRegistrationAndHistory.Model.NCComplaintHistoryModel;
import elink.suezShimla.water.crm.NoConsumerComplaint.NCRegistrationAndHistory.Model.NCConsumerAndMeterListModel;
import elink.suezShimla.water.crm.NoConsumerComplaint.NCRegistrationAndHistory.Model.NCConsumerComplaintDetailModel;
import elink.suezShimla.water.crm.NoConsumerComplaint.NCRegistrationAndHistory.Model.NCConsumerComplaintMeterModel;
import elink.suezShimla.water.crm.NoConsumerComplaint.NCRegistrationAndHistory.Model.NCMeterNumberModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;

public class NCComplaintHistoryActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private Context mCon;
    private BottomSheetDialog sheetBehavior, filterSheetBehavior;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView complaintHistoryRecyclerView;
    private List<NCComplaintHistoryModel> nccomplaintHistoryModelList;
    private NCComplaintHistoryAdapter  ncComplaintHistoryAdapter;
    private ImageView closeImageView, filterCloseImageView;
    private MaterialButton searchButton, filterButton;
    private TextView registerTextView, consumerNoTextView, complaintTypeTextView, consumerNameTextView, contactNoTextView, consumerAddressTextView, statusTextView,vipTextView;
    private FloatingActionButton registerFab;
    private AppCompatSpinner searchSpinner, complaintTypeSpinner, complaintSubTypeSpinner;
    private MaterialDialog progress, filterProgress;
    private LinearLayout consumerLinearLayout, nameAndAddressLinearLayout, mobileNumberLinearLayout, meterNumberLinearLayout, errorLinear;
    private RelativeLayout fabRelativeLayout;
    private TextInputLayout consumerNoTextInputLayout;
    private TextInputEditText consumerNoEditText;
    private String consumerNo, complaintTypeIdValueStr = "0", complaintSubTypeIdStr = "0";

    private ComplaintTypeModel complaintTypeModel;
    private ComplaintSubTypeModel complaintSubTypeModel;
    private String complaintTypeName, firstNameStr, middleNameStr, lastNameStr, infoDateStr, lastBillAmountStr, arrearAmtStr, mobileNoStr, emailStr, meterNo,
            consumerNameStr, addressStr, areaStr, pinCodeStr, meterNumberStr, statusStr, complaintTypeStr, zoneId, subZoneId,vipName;

    private int complaintTypeId, complaintSubTypeId;

    private ArrayList<String> complaintTypeList, complaintSubTypeList;
    private ArrayAdapter complaintTypeAdapter, complaintSubTypeAdapter;

    private RealmOperations realmOperations;
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    private String jsonResponse = "", jsonHistoryResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nccomplaint_history);
        mCon = this;
        sheetBehavior = new BottomSheetDialog(Objects.requireNonNull(mCon));
        filterSheetBehavior = new BottomSheetDialog(Objects.requireNonNull(mCon));

        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();
        realmOperations = new RealmOperations(mCon);

        complaintTypeList = realmOperations.fetchComplaintType();

        consumerNoTextView = findViewById(R.id.consumerNoTextView);
        complaintTypeTextView = findViewById(R.id.complaintSubTypeTextView);
        consumerNameTextView = findViewById(R.id.consumerNameTextView);
        contactNoTextView = findViewById(R.id.contactNoTextView);
        consumerAddressTextView = findViewById(R.id.consumerAddressTextView);
        statusTextView = findViewById(R.id.statusTextView);
        errorLinear = findViewById(R.id.errorLinear);
        fabRelativeLayout = findViewById(R.id.fabRelativeLayout);
        vipTextView = findViewById(R.id.vipTextView);

        complaintHistoryRecyclerView = findViewById(R.id.complaintHistoryRecyclerView);
        registerFab = findViewById(R.id.registerFab);
        registerTextView = findViewById(R.id.registerTextView);
        complaintHistoryRecyclerView.setHasFixedSize(true);
        complaintHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(mCon));
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark, android.R.color.holo_red_dark);

        ncComplaintHistoryAdapter = new NCComplaintHistoryAdapter(mCon);
        searchBottomSheetDialog();
        filterBottomSheetDialog();

        registerFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerClick();
            }
        });

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerClick();
            }
        });
    }

    private void registerClick() {
        Intent intent = new Intent(mCon, NCComplaintRegistrationActivity.class);
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
        intent.putExtra("address", addressStr);
        startActivity(intent);
    }

    private void searchBottomSheetDialog() {

        @SuppressLint("InflateParams") View sheetView = Objects.requireNonNull(getLayoutInflater().inflate(R.layout.bottom_dialog_search_consumer_history, null));
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

                if (nccomplaintHistoryModelList != null) {
                    sheetBehavior.cancel();
                } else {
                    finish();
                }
            }
        });
    }

    private void validateSearchFilter() {

        boolean isValidConsumerNo = false, isValidName = false, isValidAddressStr = false, isValidAreaStr = false, isValidPinCode = false, isValidMobileNumber = false, isValidMeterNumber = false;

        // if (consumerLinearLayout.getVisibility() == View.VISIBLE) {

        if (TextUtils.isEmpty(consumerNo)) {
            consumerNoTextInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            consumerNoTextInputLayout.setError(null);
            isValidConsumerNo = true;
        }

        if (isValidConsumerNo) {
            String params[] = new String[2];

            params[0] = consumerNo;
            params[1] = "3";

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

        /*  } else if (nameAndAddressLinearLayout.getVisibility() == View.VISIBLE) {

         *//*if (TextUtils.isEmpty(name)) {
                nameTextInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
            } else {
                nameTextInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
                isValidName = true;
            }

            if (TextUtils.isEmpty(address)) {
                addressTextInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
            } else {
                addressTextInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
                isValidAddressStr = true;
            }

            if (TextUtils.isEmpty(area)) {
                areaTextInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
            } else {
                areaTextInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
                isValidAreaStr = true;
            }

            if (TextUtils.isEmpty(pinCode)) {
                pinCodeInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
            } else {
                pinCodeInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
                isValidPinCode = true;
            }*//*

            if (isValidName && isValidAddressStr && isValidAreaStr && isValidPinCode) {
                allocationData();
            }

        } else if (mobileNumberLinearLayout.getVisibility() == View.VISIBLE) {

            *//*if (TextUtils.isEmpty(mobileNumber)) {
                mobileNumberTextInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
            } else {
                mobileNumberTextInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
                isValidMobileNumber = true;
            }*//*

            if (isValidMobileNumber) {
                allocationData();
            }

        } else if (meterNumberLinearLayout.getVisibility() == View.VISIBLE) {
*//*
            if (TextUtils.isEmpty(meterNumber)) {
                meterNumberTextInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
            } else {
                meterNumberTextInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
                isValidMeterNumber = true;
            }*//*

            if (isValidMeterNumber) {
                allocationData();
            }
        }*/
    }

    private void filterBottomSheetDialog() {
        @SuppressLint("InflateParams") View sheetView = Objects.requireNonNull(getLayoutInflater().inflate(R.layout.bottom_dialog_filter_consumer_history, null));
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
        getMenuInflater().inflate(R.menu.complaint_search_filter_menu, menu);
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

    @Override
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

    @SuppressLint("StaticFieldLeak")
    private class SearchConsumerById extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                String paraName[] = new String[2];
                paraName[0] = "W_ServiceNo";
                paraName[1] = "Utility_Id";
                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.ComplaintSearchByConsumerNo, params, paraName);
                // Log.d("check", "" + jsonResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                NCConsumerAndMeterListModel enums = gson.fromJson(jsonResponse, NCConsumerAndMeterListModel.class);
                //  ConsumerComplaintDetailModel[] enums = gson.fromJson(jsonResponse, ConsumerComplaintDetailModel[].class);
                //Log.d("check", "test " + enums);
                if (enums != null) {
                    if (enums.getConsumerComplaintDetailModel() != null && !enums.getConsumerComplaintDetailModel().isEmpty()) {
                        for (NCConsumerComplaintDetailModel model : enums.getConsumerComplaintDetailModel()) {
                            firstNameStr = model.getSRM_S_FIRST_NAME();
                            middleNameStr = model.getSRM_S_MIDDLE_NAME();
                            lastNameStr = model.getSRM_S_SURNAME();
                            //consumerNoStr = model.getSRM_SERVICE_NO();
                            mobileNoStr = model.getMOBILE_NO();
                            emailStr = model.getSRM_S_EMAIL();
                            meterNo = model.getSRM_METERS();
                            lastBillAmountStr = String.valueOf(model.getSRM_BILL_AMOUNT());
                            arrearAmtStr = String.valueOf(model.getSRM_ARREARS());

                            complaintTypeStr = model.getPRM_PURPOSE_NAME();
                            addressStr = model.getSRM_B_ADDRESS1();
                            statusStr = model.getDTM_DISCONN_TAG_NAME();

                            zoneId = model.getBUM_BU_NAME().substring(0, 1);
                            subZoneId = model.getPCM_PC_NAME();
                            vipName=model.getVIP_CATEGORY();

                            consumerNoTextView.setText(consumerNo);
                            complaintTypeTextView.setText(complaintTypeStr);
                            consumerNameTextView.setText(firstNameStr);
                            contactNoTextView.setText(mobileNoStr);
                            consumerAddressTextView.setText(addressStr);
                            statusTextView.setText(statusStr);
                            vipTextView.setText(vipName );
                        }

                        swipeRefreshLayout.setVisibility(View.VISIBLE);
                        fabRelativeLayout.setVisibility(View.VISIBLE);
                        sheetBehavior.cancel();
                        errorLinear.setVisibility(View.GONE);

                        realmOperations.deleteNCMeterNumberTable();
                        for (NCConsumerComplaintMeterModel ncConsumerComplaintMeterModel : enums.getNCConsumerComplaintMeterModels()) {

                            NCMeterNumberModel ncMeterNumberModel = new NCMeterNumberModel(ncConsumerComplaintMeterModel.getMTRM_SERIAL_NO());
                            realmOperations.addNCMeterNumber(ncMeterNumberModel);
                        }

                        String params[] = new String[3];
                        params[0] = consumerNo;
                        params[1] = "0";
                        params[2] = "0";

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
                        Toast.makeText(mCon, R.string.consumer_no_does_not_exist, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mCon, R.string.no_data_found, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d("check", e.getMessage());
                Toast.makeText(mCon, e.getMessage(), Toast.LENGTH_SHORT).show();
                String error = e.toString();
                ErrorClass.errorData(mCon, "NCComplaintHistoryActivity", "Search ConsumerById", error);
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
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                NCComplaintHistoryModel[] enums = gson.fromJson(jsonHistoryResponse, NCComplaintHistoryModel[].class);
                if (enums.length > 0) {
                    nccomplaintHistoryModelList = Arrays.asList(enums);
                    ncComplaintHistoryAdapter.addList(nccomplaintHistoryModelList);
                    complaintHistoryRecyclerView.setAdapter(ncComplaintHistoryAdapter);
                } else {
                    Toast.makeText(mCon, R.string.no_data_found, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d("check", e.getMessage());
                Toast.makeText(mCon, e.getMessage(), Toast.LENGTH_SHORT).show();
                String error = e.toString();
                ErrorClass.errorData(mCon, "NCComplaintHistoryActivity", "Filter button Complaint_History", error);
            }
            filterProgress.dismiss();
            filterSheetBehavior.dismiss();
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
