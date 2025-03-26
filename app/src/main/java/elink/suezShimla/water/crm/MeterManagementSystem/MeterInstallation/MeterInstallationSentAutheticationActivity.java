package elink.suezShimla.water.crm.MeterManagementSystem.MeterInstallation;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.map.MapActivity;
import elink.suezShimla.water.crm.Complaint.MapScreen.Model.LatLongModel;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGRequestTypeModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGSubZoneModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGZoneModel;
import elink.suezShimla.water.crm.MessageWindow;
import elink.suezShimla.water.crm.MeterManagementSystem.MeterInstallation.Adapter.MeterInstallationSentAutheticationAdapter;
import elink.suezShimla.water.crm.MeterManagementSystem.MeterInstallation.Model.MeterInstallationModel;
import elink.suezShimla.water.crm.MeterManagementSystem.MeterInstallation.Model.MeterInstallationReplacementEntryShow;
import elink.suezShimla.water.crm.MeterManagementSystem.MeterInstallation.Model.Other;
import elink.suezShimla.water.crm.MeterManagementSystem.MeterInstallation.Model.ShowMeterDataModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;

public class MeterInstallationSentAutheticationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private BottomSheetDialog sheetDialog;
    private Context mCon;

    ImageView closeImageView;
    private MaterialButton showButton, clearButton;
    private RecyclerView meterInstllRecyclerView;
    private RadioGroup radioGroup;
    private RadioButton meterInstallationRadioButton;
    private List<MeterInstallationModel> meterInstallationModelList = new ArrayList<>();
    private MeterInstallationSentAutheticationAdapter meterInstallationAdapter;
    private AppCompatSpinner requestTypeSpinner, zoneSpinner, subzoneSpinner, observationSpinner, sourceTypeSpinner;
    private TextInputLayout fromDate, toDate;
    private EditText fromDateInputEditText, toDateInputEditText;
    private String requestTypeStr, fromDateStr = "", toDateStr = "", zoneIdValueStr, zoneIdStr, subzoneIdValueStr, requestType = "";
    private Calendar fromDateCalendar, toDateCalendar;
    RealmOperations realmOperations;
    int zoneId = 0, subZoneId = 0, requestId = 0;
    String zoneName = "", subZoneName = "", requstType = "", requestIdValueStr = "";
    List<String> ZoneDataList = new ArrayList<>();
    List<String> SubZoneDataList = new ArrayList<>();
    List<String> RequestTypeList = new ArrayList<>();
    private ArrayAdapter zoneAdapter, subZoneAdapter, requestTypeAdapter;
    private MaterialDialog progress;
    private String jsonResponse = "";
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    private TextView viewrequestTypeSpinner, viewzoneSpinner;
    private ImageButton allocatedFilter;

    List<ShowMeterDataModel> showMtrList = new ArrayList<>();
    ShowMeterDataModel showMeterDataModel;
    MeterinstallShowAsyncTask meterinstallShowAsyncTask;
    String consumerNo = "", consumerType = "", reqNo = "", serviceType = "", phase = "", oldMtrNo = "", oldMtrTyp = "", oldMtrReading = "",
            newMtrNo = "", newMtrTyp = "", newMtrReading = "", mtrIndication = "", installReplaceDt = "", mtrInsttledReplcd = "",
            mtrIrReasn = "", fixrName = "", contactNo = "", nameAdd = "", observ = "", reqSubType = "", risingMains = "",
            sourceTypeValStr = "", sourceTypeVal = "", obserStr = "", obserValStr = "", cons_numStr = "";

    String Tag = "";
    int srNo = 0;
    private ArrayList<String> subzoneList = new ArrayList<>();
    private MMGZoneModel zoneModel;
    private MMGSubZoneModel subZoneModel;
    private EditText consummerNoInputEditText;
    private int subZone_id = 0;

    LinearLayout errorLinear;
    View view;
    private SearchView searchView;
    private SwipeRefreshLayout allocateSwipeRefresher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_authentication_meter_installation);
      //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mCon = this;
        gson = new Gson();

        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();
        realmOperations = new RealmOperations(mCon);
        errorLinear = findViewById(R.id.errorLinear);
        allocateSwipeRefresher = findViewById(R.id.allocateSwipeRefresher);
        allocateSwipeRefresher.setOnRefreshListener(this);
        allocateSwipeRefresher.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark, android.R.color.holo_orange_dark, android.R.color.holo_red_dark);

        fromDateStr = getIntent().getStringExtra("fromDate");
        toDateStr = getIntent().getStringExtra("toDate");
//        openSheetDialog();
        getMeterInstallationList();

        fromDateCalendar = Calendar.getInstance();
        toDateCalendar = Calendar.getInstance();

//        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        //   swipeRefreshLayout.setOnRefreshListener(this);
//        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark,
//                android.R.color.holo_orange_dark, android.R.color.holo_red_dark);

        meterInstallationAdapter = new MeterInstallationSentAutheticationAdapter(mCon, fromDateStr, toDateStr);

        meterInstllRecyclerView = findViewById(R.id.meterInstllRecyclerView);
        allocatedFilter = findViewById(R.id.allocatedFilter);
        meterInstllRecyclerView.setHasFixedSize(true);
        meterInstllRecyclerView.setLayoutManager(new LinearLayoutManager(mCon));
        ZoneDataList = realmOperations.fetchIssueToMeterZone();
//        SubZoneDataList = realmOperations.fetchIssueToMeterSubZone();
        RequestTypeList = realmOperations.fetchIssueToMeterFixrRequestType();

        allocatedFilter.setOnClickListener(this);

        try {


            searchView = findViewById(R.id.searchView);
            searchView.clearFocus();
/*
    searchView.setMaxWidth(Integer.MAX_VALUE);
    searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
*/

        }catch (Exception e){
            e.printStackTrace();
        }

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                // hideSoftKeyboard();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                filter(query);
                return false;
            }
        });


      /*  Log.e("ZoneList", "" + ZoneDataList.toString());
        Log.e("SubzoneList", "" + SubZoneDataList.toString());
        Log.e("RequestType", "" + RequestTypeList.toString());*/

    }

    private void openSheetDialog() {
        sheetDialog = new BottomSheetDialog(Objects.requireNonNull(mCon));
        View sheetView = Objects.requireNonNull(getLayoutInflater().inflate(R.layout.bottom_sheet_meter_installation_filter, null));

        fromDate = sheetView.findViewById(R.id.fromDate);
        toDate = sheetView.findViewById(R.id.toDate);

        //meterInstallationRadioButton=sheetView.findViewById(R.id.meterInstallationRadioButton);
        radioGroup = sheetView.findViewById(R.id.radioGroup);
        radioGroup.clearCheck();
        consummerNoInputEditText = sheetView.findViewById(R.id.consummerNoInputEditText);

        fromDateInputEditText = sheetView.findViewById(R.id.fromDateInputEditText);
        toDateInputEditText = sheetView.findViewById(R.id.toDateInputEditText);
        requestTypeSpinner = sheetView.findViewById(R.id.requestTypeSpinner);
        sourceTypeSpinner = sheetView.findViewById(R.id.sourceTypeSpinner);
        observationSpinner = sheetView.findViewById(R.id.observationSpinner);
        zoneSpinner = sheetView.findViewById(R.id.zoneSpinner);
        subzoneSpinner = sheetView.findViewById(R.id.subZoneSpinner);
        closeImageView = sheetView.findViewById(R.id.closeImageView);
        showButton = sheetView.findViewById(R.id.showButton);
        clearButton = sheetView.findViewById(R.id.clearButton);
        sheetDialog.setContentView(sheetView);
        sheetDialog.show();
        sheetDialog.setCanceledOnTouchOutside(false);

        fromDateInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                fromDateInputEditText.setError(null);
            }
        });

        toDateInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                toDateInputEditText.setError(null);
            }
        });

        final DatePickerDialog.OnDateSetListener fromDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                fromDateCalendar.set(Calendar.YEAR, year);
                fromDateCalendar.set(Calendar.MONTH, month);
                fromDateCalendar.set(Calendar.DATE, dayOfMonth);
                updateFromDate();
            }
        };

        final DatePickerDialog.OnDateSetListener toDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                toDateCalendar.set(Calendar.YEAR, year);
                toDateCalendar.set(Calendar.MONTH, month);
                toDateCalendar.set(Calendar.DATE, dayOfMonth);
                updateToDate();
            }
        };

        fromDateInputEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(mCon, fromDate, fromDateCalendar.get(Calendar.YEAR),
                        fromDateCalendar.get(Calendar.MONTH),
                        fromDateCalendar.get(Calendar.DATE));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        toDateInputEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(mCon, toDate, toDateCalendar.get(Calendar.YEAR),
                        toDateCalendar.get(Calendar.MONTH),
                        toDateCalendar.get(Calendar.DATE));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // finish();
                //  Toast.makeText(mCon, "Plz enter details to view Meter Details", Toast.LENGTH_SHORT).show();

                sheetDialog.cancel();
            }
        });

        clearButton.setOnClickListener(this);
        showButton.setOnClickListener(this);

//        zoneSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (zoneName.equals("ALL")) {
//                    zoneId =0;
//                } else {
//                    zoneName = zoneSpinner.getSelectedItem().toString().trim();
//                }
//                zoneIdValueStr=String.valueOf(zoneId);
//                MMGZoneModel zoneModel=realmOperations.fetchIssueToMeterZoneByName(zoneName);
//
//                zoneId= zoneModel.getBUM_BU_ID();
//                Log.e("zoneId", String.valueOf(zoneId));
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

//        subzoneSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String subZone = subzoneSpinner.getSelectedItem().toString();
//                MMGSubZoneModel MMGSubZoneModel = realmOperations.fetchIssueToMeterSubZoneByName(subZone);
//                try {
//                    if (zoneName.equals("ALL")) {
//                        subZoneId = -1;
//                    }else {
//                        subZoneId = MMGSubZoneModel.getPCM_PC_ID();
//
//                    }
//                    subzoneIdValueStr = String.valueOf(subZoneId);
//                    Log.e("subzoneIdValueStr",subzoneIdValueStr);
//                }catch (Exception e){
//                    Log.e("spinnerValue",e.toString());
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        requestTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (position == 0) {
//                    sourceTypeSpinner.setVisibility(View.GONE);
//                    observationSpinner.setVisibility(View.GONE);
//                } else if (position == 1) {
//                    sourceTypeSpinner.setVisibility(View.VISIBLE);
//                    observationSpinner.setVisibility(View.GONE);
//                } else if (position == 2) {
//                    sourceTypeSpinner.setVisibility(View.GONE);
//                    observationSpinner.setVisibility(View.VISIBLE);
//                } else if (position == 3) {
//                    sourceTypeSpinner.setVisibility(View.GONE);
//                    observationSpinner.setVisibility(View.GONE);
//                } else if (position == 4) {
//                    sourceTypeSpinner.setVisibility(View.GONE);
//                    observationSpinner.setVisibility(View.GONE);
//                }

                requestType = requestTypeSpinner.getSelectedItem().toString();
                MMGRequestTypeModel MMGRequestTypeModel = realmOperations.fetchIssueToMeterFixrRequestTypeByName(requestType);
                try {
                    if (requestType.equals("--Select--")) {
                        requestIdValueStr = "A";
                    } else {
                        if (requestType.equalsIgnoreCase("New Connection")) {
                            requestIdValueStr = "A";
                        } else if (requestType.equals("Complaint")) {
                            requestIdValueStr = "C";
                        } else if (sourceTypeVal.equals("Exisiting Application")) {
                            requestIdValueStr = "EA";
                        } else if (sourceTypeVal.equals("Temp.Connection")) {
                            requestIdValueStr = "TR";
                        }
                    }

                    Log.e("requestIdValueStr", String.valueOf(requestIdValueStr));
                } catch (Exception e) {
                    Log.e("spinnerValue", e.toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void filteredSearch() {
        openSheetDialog();

        ArrayList<String> zoneAll = new ArrayList<>();
        zoneAll.add("--Select--");
        zoneAll.addAll(ZoneDataList);

        zoneAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, zoneAll);
        zoneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        zoneSpinner.setAdapter(zoneAdapter);
        zoneSpinner.setOnItemSelectedListener(this);

        subzoneSpinner.setOnItemSelectedListener(this);

        requestTypeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, RequestTypeList);
        requestTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        requestTypeSpinner.setAdapter(requestTypeAdapter);

    }

    private void updateFromDate() {
        String dateFormat = "dd-MMM-yyyy"; //set date format
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        fromDateInputEditText.setText(sdf.format(fromDateCalendar.getTime()));
    }

    private void updateToDate() {
        String dateFormat = "dd-MMM-yyyy"; //set date format
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        toDateInputEditText.setText(sdf.format(toDateCalendar.getTime()));
    }


    private void validate() {
        boolean isValidFromDate = false, isValidToDate = false, isValidRequestType = false,
                isValidZone = false, isValidGroup = false;

        if (TextUtils.isEmpty(fromDateStr)) {
            fromDateInputEditText.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            fromDateInputEditText.setError(null);
            isValidFromDate = true;
        }

        if (TextUtils.isEmpty(toDateStr)) {
            toDateInputEditText.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            toDateInputEditText.setError(null);
            isValidToDate = true;
        }

        if (requestTypeStr.equalsIgnoreCase("--Select--")) {
            viewrequestTypeSpinner = (TextView) requestTypeSpinner.getSelectedView();
            viewrequestTypeSpinner.setError(getResources().getString(R.string.select_options));
        } else {
            TextView view = (TextView) requestTypeSpinner.getSelectedView();
            view.setError(null);
            isValidRequestType = true;
        }

        if (zoneIdValueStr.equalsIgnoreCase("--Select--")) {
            viewzoneSpinner = (TextView) zoneSpinner.getSelectedView();
            viewzoneSpinner.setError(getResources().getString(R.string.select_options));
        } else {
            TextView view = (TextView) zoneSpinner.getSelectedView();
            view.setError(null);
            isValidRequestType = true;
        }
//
//        if (subzoneIdValueStr.equalsIgnoreCase("ALL")) {
//            TextView view = (TextView) subzoneSpinner.getSelectedView();
//            view.setError(getResources().getString(R.string.select_options));
//        } else {
//            TextView view = (TextView) subzoneSpinner.getSelectedView();
//            view.setError(null);
//            isValidRequestType = true;
//        }


        if (isValidFromDate && isValidToDate && isValidRequestType) {
            sheetDialog.cancel();
            getFilteredMeterInstallation();
            //loadData();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.location_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.locButton).setVisible(false);
       /* if (PreferenceUtil.getUserType() != null && !PreferenceUtil.getUserType().equals("") && PreferenceUtil.getUserType().equals("Employee")) {
            menu.findItem(R.id.locButton).setVisible(true);
        } else if (PreferenceUtil.getUserType() != null && !PreferenceUtil.getUserType().equals("") && PreferenceUtil.getUserType().equals("Admin")) {
            menu.findItem(R.id.locButton).setVisible(false);
        }*/

        return super.onPrepareOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.locButton) {
            startActivity(new Intent(mCon, MapActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

//    private void loadData() {
//        for (int i = 0;  i <=5 ; i++) {
//            MeterInstallationModel model = new MeterInstallationModel(i, "16/07/1/"+i , "10109310"+i, "3", "8", "3", "TOD", "00001000", "EMP502-Mr. Rinki Arge", "9845165621", "Sakinaka,Andheri(E)", "NA", "NA");
//            meterInstallationModelList.add(model);
//        }
//        meterInstallationAdapter.addList(meterInstallationModelList);
//        meterInstllRecyclerView.setAdapter(meterInstallationAdapter);
//        swipeRefreshLayout.setRefreshing(false);
//    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.zoneSpinner: {
                zoneId = zoneSpinner.getSelectedItemPosition();
                subZoneId = subzoneSpinner.getSelectedItemPosition();

                String zoneName = zoneSpinner.getSelectedItem().toString();
                zoneIdValueStr = zoneName;
                if (zoneName.equalsIgnoreCase("--Select--")) {
                    ArrayList<String> subAll = new ArrayList<>();
                    subAll.add("ALL");

                    subZoneAdapter = new ArrayAdapter(mCon, R.layout.simple_spinner_item, subAll);
                    subZoneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    subzoneSpinner.setAdapter(subZoneAdapter);
                } else {
                    zoneModel = realmOperations.fetchIssueToMeterZoneByName(zoneName);
                    zoneIdStr = String.valueOf(zoneModel.getBUM_BU_ID());

                    subzoneList = realmOperations.fetchSubZoneList(Integer.parseInt(zoneIdStr));

                    ArrayList<String> zone_sub_zone_list = new ArrayList<>();
                    zone_sub_zone_list.add("ALL");
                    zone_sub_zone_list.addAll(subzoneList);

                    subZoneAdapter = new ArrayAdapter<>(mCon, android.R.layout.simple_spinner_item, zone_sub_zone_list);
                    subZoneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    subzoneSpinner.setAdapter(subZoneAdapter);
                }
            }
            break;
            case R.id.subZoneSpinner: {
                String subzoneValue = subzoneSpinner.getSelectedItem().toString();
                if (subzoneValue.equalsIgnoreCase("ALL")) {
                    subZone_id = -1;
                } else {
                    subZoneModel = realmOperations.fetchIssueToMeterSubZoneId(subzoneValue);
                    subZone_id = subZoneModel.getPCM_PC_ID();
                }
            }
            break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.showButton:
                cons_numStr = consummerNoInputEditText.getText().toString();
                fromDateStr = fromDateInputEditText.getText().toString().trim();
                toDateStr = toDateInputEditText.getText().toString().trim();

                requestTypeStr = requestTypeSpinner.getSelectedItem().toString();

                validate();
                break;

            case R.id.clearButton:
                clearFields();

                break;

            case R.id.allocatedFilter:
                filteredSearch();

                break;

            default:
                break;
        }
    }

    private void clearFields() {
        try {
            radioGroup.clearCheck();
            consummerNoInputEditText.setText("");
            requestTypeSpinner.setSelection(0);
            zoneSpinner.setSelection(0);
            subzoneSpinner.setSelection(0);
            fromDateInputEditText.setText("");
            toDateInputEditText.setText("");
            viewrequestTypeSpinner.setError(null);
            viewzoneSpinner.setError(null);
            fromDate.setError(null);
            toDate.setError(null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
        getMeterInstallationList();
        allocateSwipeRefresher.setRefreshing(false);

    }

    @SuppressLint("StaticFieldLeak")
    private class MeterinstallShowAsyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            progress = new MaterialDialog.Builder(mCon)
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
                String paraNames[] = new String[8];
                paraNames[0] = "SourceType";
                paraNames[1] = "EmpCode";
                paraNames[2] = "ConsumerNo";
                paraNames[3] = "FromIssueDate";
                paraNames[4] = "ToIssueDate";
                paraNames[5] = "BU";
                paraNames[6] = "PC";
                paraNames[7] = "ProcessCode";

                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.MMG_MeterInstallationReplacementEntryShow_1, params, paraNames);
                Log.e("MMG_WorkCompletion", jsonResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                MeterInstallationReplacementEntryShow meterInstallationModel = gson.fromJson(jsonResponse, MeterInstallationReplacementEntryShow.class);
                List<ShowMeterDataModel> downloadShowMeterData = meterInstallationModel.getData();
                List<Other> downloadOther = meterInstallationModel.getDownloadOther();
                if (downloadShowMeterData.size() == 0) {
                    MessageWindow.errorWindow(mCon, "No data found");
                    errorLinear.setVisibility(View.VISIBLE);
                    allocatedFilter.setEnabled(false);
                    allocatedFilter.setAlpha((float) 0.3);

                } else {
               /* int allocatedWorkList = downloadShowMeterData.size();
                UtilitySharedPreferences.setPrefs(mCon, Constants.ALLOCATED_WORK_LIST, String.valueOf(allocatedWorkList));*//**/
                    showMtrList.clear();
                    allocatedFilter.setEnabled(true);

                    for (ShowMeterDataModel meterDetails : meterInstallationModel.getData()) {
                        if (downloadShowMeterData.size() > 0) {

                            showMeterDataModel = new ShowMeterDataModel(meterDetails.getMFX_NEW_SERIAL_NO(), meterDetails.getMFX_ISSUE_DATE(), meterDetails.getTOD_NAME(),
                                    meterDetails.getMDT_MPHASE(), meterDetails.getMFX_REFNO(), meterDetails.getDIGIT(), meterDetails.getMF(), meterDetails.getMFX_SERVICENO(), meterDetails.getMETER_SEQ_CODE(),
                                    meterDetails.getMFX_OLD_METER_NO(), meterDetails.getMETER_INDICATION(), meterDetails.getMETER_INDICATION_ID(), meterDetails.getMFX_SERVICE_TYPE(),
                                    meterDetails.getSERVICE_TOD_FLAG(), meterDetails.getOLD_METER_TOD_FLAG(), meterDetails.getBU(), meterDetails.getPC(), meterDetails.getFIXERCD(), meterDetails.getMFX_FIXERCODE(),
                                    meterDetails.getCONTACTNO(), meterDetails.getNEWMFGCODE(), meterDetails.getOLDMFGCODE(), meterDetails.getCONSUMERNAME(), meterDetails.getNAME_ADDRESS(),
                                    meterDetails.getPROPERTY_ASSESSMENT_NO(), meterDetails.getSRM_LATITUDE(), meterDetails.getSRM_LONGITUDE(), meterDetails.getSRM_S_GIS_ID(),
                                    meterDetails.getTFM_TARIFF_NAME(), meterDetails.getBU_NAME(), meterDetails.getPC_NAME(), meterDetails.getCONTRACTORFIXER(), meterDetails.getDTC(),
                                    meterDetails.getPOLE(), meterDetails.getCOMPLAINT_SUBTYPE(), meterDetails.getOBSERVATION(), meterDetails.getOBS_ID(), meterDetails.getPRIM_SEC(),
                                    meterDetails.getCOM_SOURCECD(), meterDetails.getMRT_OLDMETER_STATUS(), meterDetails.getMRT_OLDMETER_READING(), meterDetails.getMRT_OLDMETER_OBSERVATION(),
                                    meterDetails.getMRT_TRANSACTION_ID(), meterDetails.getMFX_VENDOR_ID(), meterDetails.getMFX_PRINT_COUNT(), meterDetails.getWALKROUTE(), meterDetails.getSEQUENCE_NO(),
                                    meterDetails.getMCT_CONNTYPE_NAME(), requestIdValueStr, meterDetails.getSTATUS(), meterDetails.getSUBMITION_DATE(), meterDetails.getALLOCATIONDATE(),
                                    meterDetails.getMRT_PROCESS_DATE(), meterDetails.getMI_ISCOMMISSIONED(), meterDetails.getMI_ACTION(), meterDetails.getMICPATH());

                            showMtrList.add(showMeterDataModel);

                        }

                        realmOperations.deleteLatLongTable();
                        if (meterDetails.getSRM_LATITUDE() != null && meterDetails.getSRM_LONGITUDE() != null) {
                            LatLongModel LatLongModelExist = realmOperations.getLatLongExist();
                            if (LatLongModelExist == null) {
                                LatLongModel LatLongModel = new LatLongModel(meterDetails.getSRM_LATITUDE(), meterDetails.getSRM_LONGITUDE(), meterDetails.getCONSUMERNAME());
                                realmOperations.addLatLong(LatLongModel);
                            } else {
                                if (!LatLongModelExist.getTitle().equals(meterDetails.getCONSUMERNAME())) {
                                    LatLongModel LatLongModel = new LatLongModel(meterDetails.getSRM_LATITUDE(), meterDetails.getSRM_LONGITUDE(), meterDetails.getCONSUMERNAME());
                                    realmOperations.addLatLong(LatLongModel);
                                }
                            }
                        }
                    }

                    meterInstallationAdapter.addList(showMtrList);
                    meterInstllRecyclerView.setAdapter(meterInstallationAdapter);
                    meterInstallationAdapter.notifyDataSetChanged();

                }
                //  realmOperations.deleteLatLongTable();
            } catch (Exception e) {
                Log.e("MIActExcpn", e.toString());
                String error = e.toString();
                ErrorClass.errorData(mCon, "MeterInstallationActivity", "Meter Installation Button on home page", error);
            }
            progress.dismiss();
        }
    }

    private void getMeterInstallationList() {

        String empcode = null;
        try {
            // Decrypt EmpCode
            empcode = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] params = new String[8];
        String bu = PreferenceUtil.getFirstZoneAvailable();

        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        String dateToStr = format.format(today);

        params[0] = "0";
        params[1] = empcode; //"1310";
        params[2] = "";
        params[3] = fromDateStr;
        params[4] = toDateStr;
        params[5] = "0";
        params[6] = "-1";
        params[7] = "606";

        if (connection.isConnectingToInternet()) {
            meterinstallShowAsyncTask = new MeterinstallShowAsyncTask();
            meterinstallShowAsyncTask.execute(params);
            Log.e("PARAMSKKKK", Arrays.toString(params));
        } else {
            Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    public void filter(String input) {
        List<ShowMeterDataModel> list = new ArrayList<>();

        for (ShowMeterDataModel item : showMtrList) {
            if (item.getMFX_SERVICENO().toLowerCase().contains(input.toLowerCase()) || item.getCONSUMERNAME().toLowerCase().contains(input.toLowerCase()) || item.getMFX_REFNO().toLowerCase().contains(input.toLowerCase())) {
                list.add(item);
            }
        }
        meterInstallationAdapter.filterList(list);
    }

    private void getFilteredMeterInstallation() {

        String empcode = null;
        try {
            // Decrypt EmpCode
            empcode = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] params = new String[9];
        params[0] = requestIdValueStr;
        params[1] = empcode;
        params[2] = "0";
        params[3] = cons_numStr;
        params[4] = fromDateStr;
        params[5] = toDateStr;
        params[6] = zoneIdStr;
        params[7] = String.valueOf(subZone_id);
        params[8] = "N";

        if (connection.isConnectingToInternet()) {
            meterinstallShowAsyncTask = new MeterinstallShowAsyncTask();
            meterinstallShowAsyncTask.execute(params);
            //Log.e("PARAMSKKKK", Arrays.toString(params));
        } else {
            Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
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

}
