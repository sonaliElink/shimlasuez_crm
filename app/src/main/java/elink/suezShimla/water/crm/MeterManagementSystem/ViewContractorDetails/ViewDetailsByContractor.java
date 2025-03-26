package elink.suezShimla.water.crm.MeterManagementSystem.ViewContractorDetails;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGRequestTypeModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGVendorDetModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGZoneModel;
import elink.suezShimla.water.crm.MessageWindow;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;

public class ViewDetailsByContractor extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Context mCon;
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    private AppCompatSpinner spn_work_type, spn_area_type, spn_request_type, spn_zone, spn_contractor;
    private ArrayAdapter zoneAdapter, contractorAdapter, reqAdapter;
    List<String> zoneDataList = new ArrayList<>();
    List<String> reqDataList = new ArrayList<>();
    MMGZoneModel mmgZoneModel;
    MMGVendorDetModel mmgVendorDetModel;
    MMGRequestTypeModel mmgRequestTypeModel;
    private ArrayList<String> contractorName = new ArrayList<>();
    RealmOperations realmOperations;
    private LinearLayout ll_from_date, ll_to_date;
    private EditText etFromDate, etToDate;
    private Calendar fromDateCalendar, toDateCalendar;
    private Button showButton, clearButton;
    private int workId,areaId,zoneId,contractorId, zone_bum_bu_id;

    String fromDate="",toDate="", work_type_str="", area_type_str, request_type_str, zone_str,
            contractor_str="", req_id_value="", contractor_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_view_details_by_contractor);

        init();
    }

    private void init() {
        mCon = this;
        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();
        realmOperations = new RealmOperations(mCon);

        fromDateCalendar = Calendar.getInstance();
        toDateCalendar = Calendar.getInstance();

        spn_work_type = findViewById(R.id.spn_work_type);
        spn_area_type = findViewById(R.id.spn_area_type);
        spn_request_type = findViewById(R.id.spn_request_type);
        spn_zone = findViewById(R.id.spn_zone);
        spn_contractor = findViewById(R.id.spn_contractor);

        etFromDate = findViewById(R.id.etFromDate);
        etToDate = findViewById(R.id.etToDate);
        showButton = findViewById(R.id.showButton);
        clearButton = findViewById(R.id.clearButton);
        ll_from_date = findViewById(R.id.ll_from_date);
        ll_to_date = findViewById(R.id.ll_to_date);
        showButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        ll_from_date.setOnClickListener(this);
        ll_to_date.setOnClickListener(this);

        Date date1 = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");

        String strDate= formatter.format(date1);

//        etFromDate.setText(strDate);
//        etToDate.setText(strDate);

        zoneDataList = realmOperations.fetchIssueToMeterZone() ;
        ArrayList<String> zone = new ArrayList<>();
        zone.add("--Select--");
        zone.addAll(zoneDataList);

        zoneAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, zone);
        zoneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_zone.setAdapter(zoneAdapter);

        contractorName = realmOperations.fetchVendorDetails();

        ArrayList<String> contractorList = new ArrayList<>();
        contractorList.add("--Select--");
        contractorList.addAll(contractorName);

        contractorAdapter = new ArrayAdapter<>(mCon, android.R.layout.simple_spinner_item, contractorList);
        contractorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_contractor.setAdapter(contractorAdapter);

        requestTypeDropdown();

        spn_work_type.setOnItemSelectedListener(this);
        spn_area_type.setOnItemSelectedListener(this);
        spn_request_type.setOnItemSelectedListener(this);
        spn_zone.setOnItemSelectedListener(this);
        spn_contractor.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_from_date: {
                DatePickerDialog datePickerDialog = new DatePickerDialog(mCon, fromCalendarDate, fromDateCalendar.get(Calendar.YEAR), fromDateCalendar.get(Calendar.MONTH),
                        fromDateCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.show();
            }
            break;
            case R.id.ll_to_date: {
                DatePickerDialog datePickerDialog = new DatePickerDialog(mCon, toCalendarDate, toDateCalendar.get(Calendar.YEAR), toDateCalendar.get(Calendar.MONTH),
                        toDateCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.show();
            }
            break;
            case R.id.showButton: {
                showContratorDetails();
            }
            break;
            case R.id.clearButton:{
                clearFields();
            }
            default:
            break;
        }
    }

    final DatePickerDialog.OnDateSetListener fromCalendarDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            fromDateCalendar.set(Calendar.YEAR, year);
            fromDateCalendar.set(Calendar.MONTH, monthOfYear);
            fromDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateFromDateCalendar();
        }
    };

    private void updateFromDateCalendar() {
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        etFromDate.setText(sdf.format(fromDateCalendar.getTime()));
    }

    final DatePickerDialog.OnDateSetListener toCalendarDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            toDateCalendar.set(Calendar.YEAR, year);
            toDateCalendar.set(Calendar.MONTH, monthOfYear);
            toDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateToDateCalendar();
        }
    };

    private void updateToDateCalendar() {
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etToDate.setText(sdf.format(toDateCalendar.getTime()));
    }

    private void showContratorDetails() {
        fromDate =etFromDate.getText().toString();
        toDate=etToDate.getText().toString();

        startNextActivity();
    }

    private void clearFields() {
        try {
            spn_work_type.setSelection(0);
            spn_area_type.setSelection(0);
            spn_request_type.setSelection(0);
            spn_zone.setSelection(0);
            spn_contractor.setSelection(0);
            etFromDate.setText("");
            etToDate.setText("");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void startNextActivity() {
        if (request_type_str.equalsIgnoreCase("--Select--") || zoneId == 0 ||
                fromDate.equalsIgnoreCase("") || toDate.equalsIgnoreCase("")) {
            if(request_type_str.equalsIgnoreCase("--Select--")){
                MessageWindow.msgWindow(mCon, getResources().getString(R.string.please_select_req_type));
                return;
            } else if(zoneId == 0){
                MessageWindow.msgWindow(mCon, getResources().getString(R.string.please_select_zone));
                return;
            } else if(fromDate.equalsIgnoreCase("")){
                MessageWindow.msgWindow(mCon, getResources().getString(R.string.please_select_fromdate));
                return;
            } else if(toDate.equalsIgnoreCase("")){
                MessageWindow.msgWindow(mCon, getResources().getString(R.string.please_select_todate));
            }

        } else {
            Intent intent = new Intent(this, MeterInstallationContractorDetails.class);
            intent.putExtra("workId", workId);
            intent.putExtra("areaId", areaId);
            intent.putExtra("request_type_str", req_id_value);
            intent.putExtra("zoneId", zone_bum_bu_id);
            intent.putExtra("contractorId", contractor_id);
            intent.putExtra("fromDate", fromDate);
            intent.putExtra("toDate", toDate);

            startActivity(intent);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        work_type_str = String.valueOf(spn_work_type.getSelectedItemPosition());
        area_type_str = String.valueOf(spn_area_type.getSelectedItemPosition());
        zone_str = String.valueOf(spn_zone.getSelectedItemPosition());
        contractor_str = String.valueOf(spn_contractor.getSelectedItemPosition());

        switch (parent.getId()) {
            case R.id.spn_work_type: {
                workId = spn_work_type.getSelectedItemPosition();
            }
            break;
            case R.id.spn_area_type: {
                areaId = spn_area_type.getSelectedItemPosition();
            }
            break;
            case R.id.spn_request_type: {
                request_type_str = spn_request_type.getSelectedItem().toString();
                String select = spn_request_type.getSelectedItem().toString();
                if(!select.equalsIgnoreCase("--Select--")){
                    mmgRequestTypeModel = realmOperations.fetchReqTypeById(request_type_str);
                    String name = mmgRequestTypeModel.getSelectVal();
                    req_id_value = mmgRequestTypeModel.getAllVal();
                }
            }
            break;
            case R.id.spn_zone: {
                zoneId = spn_zone.getSelectedItemPosition();
                String select = spn_zone.getSelectedItem().toString();
                if(!select.equalsIgnoreCase("--Select--")){
                    mmgZoneModel = realmOperations.fetchZoneId(zoneId);
                    zone_bum_bu_id = mmgZoneModel.getBUM_BU_ID();
                }
            }
            break;
            case R.id.spn_contractor: {
                contractorId = spn_contractor.getSelectedItemPosition();
                String select = spn_contractor.getSelectedItem().toString();
                if (!select.equalsIgnoreCase("--Select--")) {
                    mmgVendorDetModel = realmOperations.fetchVendorEmpByName(select);
                    contractor_id = mmgVendorDetModel.getEM_EMP_CODE();
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

    private void requestTypeDropdown(){
        reqDataList = realmOperations.fetchIssueToMeterFixrRequestType() ;

        reqAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, reqDataList);
        reqAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_request_type.setAdapter(reqAdapter);
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
