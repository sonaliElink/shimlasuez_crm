package elink.suezShimla.water.crm.Complaint.WorkCompletion.Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.tiper.MaterialSpinner;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.Complaint.Allocation.Activity.WorkAllocationCompletionActivity;
import elink.suezShimla.water.crm.Complaint.RegistrationAndHistory.Model.ConsumerAndMeterListModel;
import elink.suezShimla.water.crm.Complaint.WorkCompletion.Model.BillAdjustBill;
import elink.suezShimla.water.crm.Complaint.WorkCompletion.Model.BillingAdjustmentResponceModel;
import elink.suezShimla.water.crm.Complaint.WorkCompletion.Model.DownloadAdjustmentTypeTable;
import elink.suezShimla.water.crm.Complaint.WorkCompletion.Model.DownloadMeterStatusBillAdjustTable;
import elink.suezShimla.water.crm.Complaint.WorkCompletion.Model.UpdateCompletionResponseModel;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MStatusObservationModel;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadConnCategory;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadConnSize;
import elink.suezShimla.water.crm.Login.MasterData.Download.DownloadCustomerType;
import elink.suezShimla.water.crm.Login.MasterData.Tables.ComplaintSubTypeModel;
import elink.suezShimla.water.crm.MessageWindow;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.Utils.DateFormat;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;
import io.realm.RealmResults;

public class WorkCompletionBillingAdjustmentActivity extends AppCompatActivity implements MaterialSpinner.OnItemSelectedListener, View.OnClickListener {
    private String fromDate = "", toDate = "", clickStr = "", strConnCategory = "", strConnSize = "", strNewConnCategoryId = "", strNewConnSizeId = "", consumerNoStr, complaintNoStr, complaintWorkAllocateDateStr, complaintDateStr, complaintSubTypeStr, tariffStr, statusStr,
            consumerNameStr, addressStr, priorityStr, contactNoStr, zoneStr, subZoneStr, disputeBillMonthYrStr, meterNoStr, meterTransIdStr, address1Str, address2Str,
            address3Str, pincodeStr, complaintTypeStr, completionDateStr, completionTimeStr, remarkStr, complaintCode, actionIdStr, workCompletionDateTime,
            observationStr, observationIdStr, searchForStr, otpStr, otpReasonStr, timeFormat, AM_PM, OTP, userType, filterIdStr, vipName;

    private String billingAdjustTypeStr = "0", swappedConsumeNumberStr = "", adjustPeriodFromTypeStr = "0", adjustPeriodToTypeStr = "0", correctReadingStr = "", correctReadingDateStr = "", correctReadingMeterStatusStr = "", correctReadingObservationStatusStr = "",
            newTarrifStr, tariffChangeFromDateStr = "", tariffChangeToDateStr = "", consumerNoEnterStr = "", receiptNumberStr = "", removalReadingComplanintentConsumerStr = "", removalReadingSwappedConsumerStr = "", removalDateStr = "";
    private String observationsId = "", billAdjustMentTypeId = "0", correctMeterStatusId = "0", newTariffId = "", meterObservationIdStr = "";

    Date strTariffFromDate, strTariffToDate;

    private Invoke invServices;
    private Context mCon;
    private Gson gson;
    private RealmOperations realmOperations;
    private ConnectionDetector connection;
    private String jsonResponse = "", jsonSMSResponse = "", jsonBillAdjustBillMonthResponse, actionStr = "For Billing Adjustment", jsonPostedOnWrongAccountResponse = "";
    private Calendar dateCalendar;
    private TimePickerDialog mTimePicker;

    private ArrayList<ComplaintSubTypeModel> observationList;
    private ArrayAdapter adjustTypeAdapter, adjustMeterStatusTypeAdapter, finishActionAdapter, customerTypeArrayAdapter, statusObservationAdapter, billAdjustBillAdapter, actionFormArrayAdapter, connCategoryArrayAdapter, connConnectionArrayAdapter;

    MaterialSpinner observationSpinner, billingAdjustTypeSpinner, adjustPeriodFromTypeSpinner, adjustPeriodToTypeSpinner, correctReadingMeterStatusSpinner,
            correctReadingMeterObservationSpinner, newTariffSpinner, CategorySpinner, ConnectionSpinner;

    private TextInputLayout til_number_of_unit, til_swapped_consumer_number, til_correct_reading, til_correct_reading_date, til_tariff_change_from_date,
            til_tariff_change_to_date, til_consumer_number, til_receipt_number, til_removal_reading_of_complaintent_consumer, til_removal_reading_of_swapped_consumer,
            til_removal_date;

    private TextInputEditText et_number_of_unit, et_swapped_consumer_number, et_correct_reading, et_correct_reading_date, et_tariff_change_from_date,
            et_tariff_change_to_date, et_consumer_number, et_receipt_number, et_removal_reading_of_complaintent_consumer, et_removal_reading_of_swapped_consumer,
            et_removal_date;
    private LinearLayout ll_current_reading_date, ll_tariff_change_from_date, ll_tariff_change_to_date, ll_removal_date;

    private ImageView iv_current_reading_date, iv_tariff_change_from_date, iv_tariff_change_to_date, iv_removal_date;
    private Button btn_submit;
    int compareAdjustFromAndTo;

    ArrayList<String> billAdjustBillStringList;


/*
    String[] ITEMS = {"Select", "Meter Fast", "Meter Slow", "Wrong Reading", "Tariff Category Change", "Posted Wrong Account Amount", "Meter Swapping"};
*/

    private Calendar cal, calMinDate;
    DatePickerDialog datePickerDialog;

    DatePickerDialog.OnDateSetListener fromDateListener, fromDateListenerTo;
    private int year, month, day;
    static final int DATE_ID = 123;

    private ArrayList<String> adjustmentTypeList, adjustmentMeterStatusTypeList, billMonthTypeList, meterObservationList;

    private MaterialDialog progress, masterProgress, progressPostedOnWrongAccount, checkConsumerIdExitProgress, mmgMasterProgress;

    ArrayList<String> msrList = new ArrayList<>();
    ArrayList<String> msrListId = new ArrayList<>();


    ArrayList<String> observationMeterList = new ArrayList<>();
    ArrayList<String> observationMeterIdList = new ArrayList<>();


    ArrayList<String> downloadAdjustmentTypeList = new ArrayList<String>();
    ArrayList<String> downloadAdjustmentTypeValueList = new ArrayList<String>();


    ArrayList<String> downloadMeterStatusList = new ArrayList<String>();
    ArrayList<String> downloadMeterStatusValueList = new ArrayList<String>();

    ArrayList<String> moList = new ArrayList<String>();
    ArrayList<String> moValueList = new ArrayList<String>();

    ArrayList<String> downloadTariffList = new ArrayList<String>();
    ArrayList<String> downloadTariffValueList = new ArrayList<String>();

    ArrayList<String> downloadConnCategoryId = new ArrayList<String>();
    ArrayList<String> downloadConnCategoryName = new ArrayList<String>();

    ArrayList<String> downloadConnSizeId = new ArrayList<String>();
    ArrayList<String> downloadConnSizeName = new ArrayList<String>();

    RealmResults<MStatusObservationModel> meterObservationModels;

    MStatusObservationModel mStatusObservationModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.work_completion_billing_adjustment_activity);
        init();
    }

    private void init() {
        mCon = this;

        dateCalendar = Calendar.getInstance();


        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();
        realmOperations = new RealmOperations(mCon);

        observationSpinner = findViewById(R.id.observationSpinner);
        billingAdjustTypeSpinner = findViewById(R.id.billingAdjustTypeSpinner);
        adjustPeriodFromTypeSpinner = findViewById(R.id.adjustPeriodFromTypeSpinner);
        adjustPeriodToTypeSpinner = findViewById(R.id.adjustPeriodToTypeSpinner);
        adjustPeriodFromTypeSpinner.setOnItemSelectedListener(this);
        adjustPeriodToTypeSpinner.setOnItemSelectedListener(this);

        correctReadingMeterStatusSpinner = findViewById(R.id.correctReadingMeterStatusSpinner);
        correctReadingMeterStatusSpinner.setOnItemSelectedListener(this);

        correctReadingMeterObservationSpinner = findViewById(R.id.correctReadingMeterObservationSpinner);
        correctReadingMeterObservationSpinner.setOnItemSelectedListener(this);

        newTariffSpinner = findViewById(R.id.newTariffSpinner);
        CategorySpinner = findViewById(R.id.CategorySpinner);
        ConnectionSpinner = findViewById(R.id.ConnectionSpinner);


        til_number_of_unit = findViewById(R.id.til_number_of_unit);
        til_swapped_consumer_number = findViewById(R.id.til_swapped_consumer_number);
        til_correct_reading = findViewById(R.id.til_correct_reading);
        til_correct_reading_date = findViewById(R.id.til_correct_reading_date);
        til_tariff_change_from_date = findViewById(R.id.til_tariff_change_from_date);
        til_tariff_change_to_date = findViewById(R.id.til_tariff_change_to_date);
        til_consumer_number = findViewById(R.id.til_consumer_number);
        til_receipt_number = findViewById(R.id.til_receipt_number);
        til_removal_reading_of_complaintent_consumer = findViewById(R.id.til_removal_reading_of_complaintent_consumer);
        til_removal_reading_of_swapped_consumer = findViewById(R.id.til_removal_reading_of_swapped_consumer);
        til_removal_date = findViewById(R.id.til_removal_date);


        et_number_of_unit = findViewById(R.id.et_number_of_unit);
        et_swapped_consumer_number = findViewById(R.id.et_swapped_consumer_number);
        et_correct_reading = findViewById(R.id.et_correct_reading);
        et_correct_reading_date = findViewById(R.id.et_correct_reading_date);
        et_tariff_change_from_date = findViewById(R.id.et_tariff_change_from_date);
        et_tariff_change_to_date = findViewById(R.id.et_tariff_change_to_date);
        et_consumer_number = findViewById(R.id.et_consumer_number);
        et_receipt_number = findViewById(R.id.et_receipt_number);
        et_removal_reading_of_complaintent_consumer = findViewById(R.id.et_removal_reading_of_complaintent_consumer);
        et_removal_reading_of_swapped_consumer = findViewById(R.id.et_removal_reading_of_swapped_consumer);
        et_removal_date = findViewById(R.id.et_removal_date);

        ll_current_reading_date = findViewById(R.id.ll_current_reading_date);
        ll_tariff_change_from_date = findViewById(R.id.ll_tariff_change_from_date);
        ll_tariff_change_to_date = findViewById(R.id.ll_tariff_change_to_date);
        ll_removal_date = findViewById(R.id.ll_removal_date);

        iv_current_reading_date = findViewById(R.id.iv_current_reading_date);
        iv_tariff_change_from_date = findViewById(R.id.iv_tariff_change_from_date);
        iv_tariff_change_to_date = findViewById(R.id.iv_tariff_change_to_date);
        iv_removal_date = findViewById(R.id.iv_removal_date);
        iv_current_reading_date.setOnClickListener(this);
        iv_tariff_change_from_date.setOnClickListener(this);
        iv_tariff_change_to_date.setOnClickListener(this);
        iv_removal_date.setOnClickListener(this);
        btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);



        consumerNoStr = getIntent().getStringExtra("consumerNo");
        complaintNoStr = getIntent().getStringExtra("complaintNo");
        complaintWorkAllocateDateStr = getIntent().getStringExtra("complaintWorkAllocateDate");
        complaintDateStr = getIntent().getStringExtra("complaintDate");
        complaintSubTypeStr = getIntent().getStringExtra("complaintSubType");
        tariffStr = getIntent().getStringExtra("tariff");
        statusStr = getIntent().getStringExtra("status");
        consumerNameStr = getIntent().getStringExtra("consumerName");
        addressStr = getIntent().getStringExtra("address");
        priorityStr = getIntent().getStringExtra("priority");
        contactNoStr = getIntent().getStringExtra("contactNo");
        zoneStr = getIntent().getStringExtra("zone");
        subZoneStr = getIntent().getStringExtra("subZone");
        disputeBillMonthYrStr = getIntent().getStringExtra("disputeBillMonthYr");
        meterNoStr = getIntent().getStringExtra("meterNo");
        meterTransIdStr = getIntent().getStringExtra("meterTransId");
        address1Str = getIntent().getStringExtra("address1");
        address2Str = getIntent().getStringExtra("address2");
        address3Str = getIntent().getStringExtra("address3");
        pincodeStr = getIntent().getStringExtra("pincode");
        complaintTypeStr = getIntent().getStringExtra("complaintType");
        searchForStr = getIntent().getStringExtra("searchForStr");
        vipName = getIntent().getStringExtra("vipName");
        remarkStr = getIntent().getStringExtra("actionForm");
        complaintCode = getIntent().getStringExtra("complaintCode");
        et_number_of_unit.setText(remarkStr);
        strConnCategory = getIntent().getStringExtra("connCategory");
        strConnSize = getIntent().getStringExtra("connSize");
        fromDate = getIntent().getStringExtra("fromDate");
        toDate = getIntent().getStringExtra("toDate");
        clickStr = getIntent().getStringExtra("strClick");

        String siteEng = null;
        try {
            siteEng = new AesAlgorithm().decrypt(PreferenceUtil.getSiteEng());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (siteEng != null) {
            if (siteEng.equals("SiteEng")) {
                userType = "Android";
            } else {
                userType = "Web";
            }
        } else {
            userType = "Web";
        }
        setCurrentDateOnView();

        cal = Calendar.getInstance();
        calMinDate = Calendar.getInstance();

        getBillAdjustBillMonth();
        setObservationSpinner();
        billAdjustTypeSpinner();
        billMeterStatusTypeSpinner();
        setNewTarifDropDown();
        setConnCategory();
        setConnSize();

        defaultObservation();
/* setObservationSpinner()
        FinishActionModel{CSCM_ID=7, CSCM_SECNAME='Billing Adjustment', FILTER='9'}
*/
    }

    private void getBillAdjustBillMonth() {
        String[] params = new String[1];
        params[0] = consumerNoStr;
        //params[0] = "23218164";
        if (connection.isConnectingToInternet()) {

            BillAdjustBillMonth billAdjustBillMonth = new BillAdjustBillMonth();
            billAdjustBillMonth.execute(params);

            masterProgress = new MaterialDialog.Builder(mCon)
                    .content(R.string.loading)
                    .progress(true, 0)
                    .autoDismiss(false)
                    .canceledOnTouchOutside(false)
                    .widgetColorRes(R.color.colorPrimary)
                    .show();

        } else {
            //Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            MessageWindow.messageWindow(mCon, getResources().getString(R.string.no_internet_connection), "Alert");

        }
    }

    private void defaultObservation() {
        //  String meterStatusIdStr = "2";
        //meterObservationList = realmOperations.fetchStatusObservationList(correctMeterStatusId);
        observationMeterList.clear();


        observationMeterList.add("Select");

        statusObservationAdapter = new ArrayAdapter<>(mCon, android.R.layout.simple_spinner_item, observationMeterList);
        statusObservationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        correctReadingMeterObservationSpinner.setAdapter(statusObservationAdapter);
        correctReadingMeterObservationSpinner.setSelection(0);

    }


    @SuppressLint("StaticFieldLeak")
    private class BillAdjustBillMonth extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                //jsonMasterDataResponse = invServices.getDataWOParams(Constants.URL, Constants.NameSpace, Constants.MasterData);
                String paraNames[] = new String[1];
                paraNames[0] = "Consumer";

                jsonBillAdjustBillMonthResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.Complaint_BillAdjBillMonth, params, paraNames);
                Log.e("jsonBillAdjustBillMonthResponse", jsonBillAdjustBillMonthResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                // MasterDataModel masterDataModel = gson.fromJson(jsonBillAdjustBillMonthResponse, MasterDataModel.class);
                BillAdjustBill[] enums = gson.fromJson(jsonBillAdjustBillMonthResponse, BillAdjustBill[].class);
                billAdjustBillStringList = new ArrayList<>();
                billAdjustBillStringList.add("Select");
               /* billAdjustBillStringList.add("202001");
                billAdjustBillStringList.add("202003");
                billAdjustBillStringList.add("202006");*/
                if (enums != null) {

                    for (BillAdjustBill billAdjustBill : enums) {
                        String monthName = billAdjustBill.getMONTHYEAR();
                        billAdjustBillStringList.add(monthName);

                    }


                } else {
                    // Toast.makeText(mCon, R.string.no_data_found, Toast.LENGTH_SHORT).show();
                    MessageWindow.messageWindow(mCon, getResources().getString(R.string.no_data_found), "Alert");

                    masterProgress.dismiss();
                }
                billAdjustBillAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, billAdjustBillStringList);
                billAdjustBillAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                adjustPeriodFromTypeSpinner.setAdapter(billAdjustBillAdapter);
                adjustPeriodFromTypeSpinner.setSelection(0);
                adjustPeriodToTypeSpinner.setAdapter(billAdjustBillAdapter);
                adjustPeriodToTypeSpinner.setSelection(0);
                masterProgress.dismiss();
                masterProgress.cancel();

                // getBilllingAdjustmentdataDownload();
            } catch (Exception e) {
                String error = e.toString();
                ErrorClass.errorData(mCon, "LoginActivity", "GetMasterDataForAndroid", error);
                masterProgress.dismiss();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }


    public void setCurrentDateOnView() {
        try {
            cal = GregorianCalendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.DAY_OF_YEAR, 0);
            //cal.add(Calendar.DAY_OF_YEAR, 7);
            // et_date.setText(DateFormat.dateFormat_1.format(cal.getTime()));

            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
            day = cal.get(Calendar.DAY_OF_MONTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

/*    private void updateDate() {
            String myFormat = DateFormat.DATEFORMAT_1; //In which format you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            et_correct_reading_date.setText(sdf.format(cal.getTime()));

    }*/

    private void billAdjustTypeSpinner() {

        //  adjustmentTypeList = realmOperations.fetchBilllingAdjustType();
        RealmResults<DownloadAdjustmentTypeTable> downloadAdjustmentTypeTables = realmOperations.fetchBilllingAdjustType();
        for (DownloadAdjustmentTypeTable downloadAdjustmentTypeTable : downloadAdjustmentTypeTables) {
            downloadAdjustmentTypeList.add(downloadAdjustmentTypeTable.getREM_REASONNM());
            downloadAdjustmentTypeValueList.add(downloadAdjustmentTypeTable.getREM_REASONCD());
        }

        ArrayList<String> adjustmentTypeStringList = new ArrayList<>();
        adjustmentTypeStringList.add("Select");
        adjustmentTypeStringList.addAll(downloadAdjustmentTypeList);


        adjustTypeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, adjustmentTypeStringList);
        adjustTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        billingAdjustTypeSpinner.setAdapter(adjustTypeAdapter);
        billingAdjustTypeSpinner.setSelection(0);



    /*    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        billingAdjustTypeSpinner.setAdapter(adapter);
        billingAdjustTypeSpinner.setSelection(0);*/

        billingAdjustTypeSpinner.setOnItemSelectedListener(this);

    }

    private void billMeterStatusTypeSpinner() {

        //  adjustmentMeterStatusTypeList = realmOperations.fetchBilllinMeterStatusType();
        RealmResults<DownloadMeterStatusBillAdjustTable> downloadMeterStatusBillAdjustTables = realmOperations.fetchBilllinMeterStatusType();
        for (DownloadMeterStatusBillAdjustTable meterStatusBillAdjustTable : downloadMeterStatusBillAdjustTables) {
            downloadMeterStatusList.add(meterStatusBillAdjustTable.getMSM_METERSTATUS_NAME());
            downloadMeterStatusValueList.add(meterStatusBillAdjustTable.getMSM_METERSTATUS_ID());
        }

        ArrayList<String> adjustmentMeterStatusTypeStringList = new ArrayList<>();
        adjustmentMeterStatusTypeStringList.add("Select");
        adjustmentMeterStatusTypeStringList.addAll(downloadMeterStatusList);


        adjustMeterStatusTypeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, adjustmentMeterStatusTypeStringList);
        adjustMeterStatusTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        correctReadingMeterStatusSpinner.setAdapter(adjustMeterStatusTypeAdapter);
        correctReadingMeterStatusSpinner.setSelection(0);


    /*    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        billingAdjustTypeSpinner.setAdapter(adapter);
        billingAdjustTypeSpinner.setSelection(0);*/

        correctReadingMeterStatusSpinner.setOnItemSelectedListener(this);

    }

    private void setObservationSpinner() {

        RealmResults<ComplaintSubTypeModel> msrModels = realmOperations.fetchComplaintSubTypeObservation(9);


        //msrList.add("Select");
        msrList.add("For Billing Adjustment");

       /* for (int i = 0; i < msrModels.size(); i++) {
            ComplaintSubTypeModel complaintSubTypeModel = msrModels.get(i);
            String msrid = String.valueOf(complaintSubTypeModel.getCOMPLAINTSUBTYPEID());
            String msrName = complaintSubTypeModel.getCOMPLAINTSUBTYPENAME();
            msrList.add(msrName);
            msrListId.add(msrid);

        }
*/

        statusObservationAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, msrList);
        statusObservationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        observationSpinner.setAdapter(statusObservationAdapter);
        observationSpinner.setSelection(0);
        observationSpinner.setOnItemSelectedListener(this);

    }

    private void setNewTarifDropDown() {
        //  ArrayList<String> srName = new ArrayList<>();
        //   srName = realmOperations.fetchTariffName();

        RealmResults<DownloadCustomerType> downloadCustomerTypes = realmOperations.fetchTariffName();
        for (DownloadCustomerType downloadCustomerType : downloadCustomerTypes) {
            downloadTariffList.add(downloadCustomerType.getCUSTTYPETEXT());
            downloadTariffValueList.add(downloadCustomerType.getCUSTTYPEID());
        }
        ArrayList<String> srList = new ArrayList<>();
        srList.add("All");
        srList.addAll(downloadTariffList);

        customerTypeArrayAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, srList);
        customerTypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newTariffSpinner.setAdapter(customerTypeArrayAdapter);
        newTariffSpinner.setOnItemSelectedListener(this);
        newTariffSpinner.setSelection(0);
    }

    private void setConnCategory() {
        try {
            RealmResults<DownloadConnCategory> downalodConnCategory = realmOperations.fetchConnCategory();
            for (DownloadConnCategory connCategory : downalodConnCategory) {
                downloadConnCategoryId.add(connCategory.getCATEGORY_ID());
                downloadConnCategoryName.add(connCategory.getCATEGORY_NAME());
            }
            ArrayList<String> connCategoryList = new ArrayList<>();
            connCategoryList.add("Select");
            connCategoryList.addAll(downloadConnCategoryName);

            connCategoryArrayAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, connCategoryList);
            connCategoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            CategorySpinner.setAdapter(connCategoryArrayAdapter);
            CategorySpinner.setOnItemSelectedListener(this);

            if (!strConnCategory.isEmpty()&& strConnCategory!=null) {
                for (int i = 0; i < downloadConnCategoryId.size(); i++) {
                    if (Integer.parseInt(strConnCategory) == Integer.parseInt(downloadConnCategoryId.get(i))) {
                        int position = downloadConnCategoryId.indexOf(strConnCategory);
                        CategorySpinner.setSelection(position + 1);
                        break;
                    } else {
                        CategorySpinner.setSelection(0);
                    }
                }
            }
        } catch (Exception e) {
            Log.d("Conn Size", "" + e.toString() + "Conn Size Message" + e.getMessage());
        }
    }

    private void setConnSize() {

        RealmResults<DownloadConnSize> downalodConnSize = realmOperations.fetchConnSize();
        for (DownloadConnSize connSize : downalodConnSize) {
            downloadConnSizeId.add(connSize.getID());
            downloadConnSizeName.add(connSize.getNAME() + " MM");
        }
        ArrayList<String> connSizeList = new ArrayList<>();
        connSizeList.add("Select");
        connSizeList.addAll(downloadConnSizeName);

        connConnectionArrayAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, connSizeList);
        connConnectionArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ConnectionSpinner.setAdapter(connConnectionArrayAdapter);
        ConnectionSpinner.setOnItemSelectedListener(this);

        if (!strConnSize.isEmpty() && strConnSize!=null) {
            for (int i = 0; i < downloadConnSizeId.size(); i++) {
                if (Integer.parseInt(strConnSize) == Integer.parseInt(downloadConnSizeId.get(i))) {
                    int position = downloadConnSizeId.indexOf(strConnSize);
                    ConnectionSpinner.setSelection(position + 1);
                    break;
                } else {
                    ConnectionSpinner.setSelection(0);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onItemSelected(MaterialSpinner materialSpinner, View view, int i, long l) {
        //   String[] ITEMS = {"Select", "Meter Fast", "Meter ", "Tariff Category Change", "Posted Wrong Account Amount", "Meter Swapping"};
        switch (materialSpinner.getId()) {
            case R.id.CategorySpinner:
                String connCategoryValue = CategorySpinner.getSelectedItem().toString();
                if (connCategoryValue.equalsIgnoreCase("Select")) {
                    strNewConnCategoryId = "";
                } else {
                    DownloadConnCategory downloadConCategory = realmOperations.getDownloadConnCategoryId(connCategoryValue);
                    strNewConnCategoryId = downloadConCategory.getCATEGORY_ID();
                }
                // Log.e("strNewConnCategoryId", strNewConnCategoryId);
                break;
            case R.id.ConnectionSpinner:
                String connSizeValue = ConnectionSpinner.getSelectedItem().toString();
                if (connSizeValue.equalsIgnoreCase("Select")) {
                    strNewConnSizeId = "";
                } else {
                    String cSize = connSizeValue.replace("MM", "").trim();
                    //   Log.e("cSize", cSize);
                    DownloadConnSize downloadConSize = realmOperations.getDownloadConnSizeId(cSize);
                    strNewConnSizeId = downloadConSize.getID();
                }

                //Log.e("strNewConnSizeId", strNewConnSizeId);
                break;
            case R.id.billingAdjustTypeSpinner: {
                billingAdjustTypeStr = billingAdjustTypeSpinner.getSelectedItem().toString();

                if (billingAdjustTypeStr.equalsIgnoreCase("Select")) {

                    resetAllValues();
                    adjustPeriodFromTypeSpinner.setVisibility(View.GONE);
                    adjustPeriodToTypeSpinner.setVisibility(View.GONE);

                    correctReadingMeterStatusSpinner.setVisibility(View.GONE);
                    correctReadingMeterObservationSpinner.setVisibility(View.GONE);
                    til_correct_reading.setVisibility(View.GONE);
                    til_correct_reading_date.setVisibility(View.GONE);
                    ll_tariff_change_from_date.setVisibility(View.GONE);
                    ll_tariff_change_to_date.setVisibility(View.GONE);
                    newTariffSpinner.setVisibility(View.GONE);
                    CategorySpinner.setVisibility(View.GONE);
                    ConnectionSpinner.setVisibility(View.GONE);
                    til_consumer_number.setVisibility(View.GONE);
                    til_receipt_number.setVisibility(View.GONE);

                    til_swapped_consumer_number.setVisibility(View.GONE);
                    til_removal_reading_of_complaintent_consumer.setVisibility(View.GONE);
                    til_removal_reading_of_swapped_consumer.setVisibility(View.GONE);
                    ll_removal_date.setVisibility(View.GONE);
                    ll_current_reading_date.setVisibility(View.GONE);

                } else if (billingAdjustTypeStr.equalsIgnoreCase("Meter Fast")) {

                    billAdjustMentTypeId = downloadAdjustmentTypeValueList.get(billingAdjustTypeSpinner.getSelection() - 1).toString();

                    resetAllValues();

                    adjustPeriodFromTypeSpinner.setVisibility(View.VISIBLE);
                    adjustPeriodToTypeSpinner.setVisibility(View.VISIBLE);

                    correctReadingMeterStatusSpinner.setVisibility(View.GONE);
                    correctReadingMeterObservationSpinner.setVisibility(View.GONE);
                    til_correct_reading.setVisibility(View.GONE);
                    til_correct_reading_date.setVisibility(View.GONE);
                    ll_tariff_change_from_date.setVisibility(View.GONE);
                    ll_tariff_change_to_date.setVisibility(View.GONE);
                    newTariffSpinner.setVisibility(View.GONE);
                    CategorySpinner.setVisibility(View.GONE);
                    ConnectionSpinner.setVisibility(View.GONE);
                    til_consumer_number.setVisibility(View.GONE);
                    til_receipt_number.setVisibility(View.GONE);

                    til_swapped_consumer_number.setVisibility(View.GONE);
                    til_removal_reading_of_complaintent_consumer.setVisibility(View.GONE);
                    til_removal_reading_of_swapped_consumer.setVisibility(View.GONE);
                    ll_removal_date.setVisibility(View.GONE);
                    ll_current_reading_date.setVisibility(View.GONE);

                    strNewConnCategoryId = "";
                    strNewConnSizeId = "";


                } else if (billingAdjustTypeStr.equalsIgnoreCase("Meter Slow")) {

                    resetAllValues();
                    billAdjustMentTypeId = downloadAdjustmentTypeValueList.get(billingAdjustTypeSpinner.getSelection() - 1).toString();
                    adjustPeriodFromTypeSpinner.setVisibility(View.VISIBLE);
                    adjustPeriodToTypeSpinner.setVisibility(View.VISIBLE);

                    correctReadingMeterStatusSpinner.setVisibility(View.GONE);
                    correctReadingMeterObservationSpinner.setVisibility(View.GONE);
                    til_correct_reading.setVisibility(View.GONE);
                    til_correct_reading_date.setVisibility(View.GONE);
                    ll_tariff_change_from_date.setVisibility(View.GONE);
                    ll_tariff_change_to_date.setVisibility(View.GONE);
                    newTariffSpinner.setVisibility(View.GONE);
                    CategorySpinner.setVisibility(View.GONE);
                    ConnectionSpinner.setVisibility(View.GONE);
                    til_consumer_number.setVisibility(View.GONE);
                    til_receipt_number.setVisibility(View.GONE);

                    til_swapped_consumer_number.setVisibility(View.GONE);
                    til_removal_reading_of_complaintent_consumer.setVisibility(View.GONE);
                    til_removal_reading_of_swapped_consumer.setVisibility(View.GONE);
                    ll_removal_date.setVisibility(View.GONE);
                    ll_current_reading_date.setVisibility(View.GONE);

                    strNewConnCategoryId = "";
                    strNewConnSizeId = "";


                } else if (billingAdjustTypeStr.equalsIgnoreCase("Wrong Reading")) {
                    resetAllValues();
                    billAdjustMentTypeId = downloadAdjustmentTypeValueList.get(billingAdjustTypeSpinner.getSelection() - 1).toString();
                    adjustPeriodFromTypeSpinner.setVisibility(View.VISIBLE);
                    adjustPeriodToTypeSpinner.setVisibility(View.VISIBLE);
                    correctReadingMeterStatusSpinner.setVisibility(View.VISIBLE);
                    correctReadingMeterObservationSpinner.setVisibility(View.VISIBLE);
                    til_correct_reading.setVisibility(View.VISIBLE);
                    til_correct_reading_date.setVisibility(View.VISIBLE);
                    ll_current_reading_date.setVisibility(View.VISIBLE);


                    ll_tariff_change_from_date.setVisibility(View.GONE);
                    ll_tariff_change_to_date.setVisibility(View.GONE);
                    newTariffSpinner.setVisibility(View.GONE);
                    CategorySpinner.setVisibility(View.GONE);
                    ConnectionSpinner.setVisibility(View.GONE);
                    til_consumer_number.setVisibility(View.GONE);
                    til_receipt_number.setVisibility(View.GONE);

                    til_swapped_consumer_number.setVisibility(View.GONE);
                    til_removal_reading_of_complaintent_consumer.setVisibility(View.GONE);
                    til_removal_reading_of_swapped_consumer.setVisibility(View.GONE);
                    ll_removal_date.setVisibility(View.GONE);

                    strNewConnCategoryId = "";
                    strNewConnSizeId = "";


                } else if (billingAdjustTypeStr.equalsIgnoreCase("Tariff Category Change")) {
                    resetAllValues();
                    billAdjustMentTypeId = downloadAdjustmentTypeValueList.get(billingAdjustTypeSpinner.getSelection() - 1).toString();
                    adjustPeriodFromTypeSpinner.setVisibility(View.VISIBLE);
                    adjustPeriodToTypeSpinner.setVisibility(View.VISIBLE);
                    newTariffSpinner.setVisibility(View.VISIBLE);
                    CategorySpinner.setVisibility(View.VISIBLE);
                    ConnectionSpinner.setVisibility(View.VISIBLE);
                    ll_tariff_change_from_date.setVisibility(View.VISIBLE);
                    ll_tariff_change_to_date.setVisibility(View.VISIBLE);

                    ll_current_reading_date.setVisibility(View.GONE);

                    correctReadingMeterStatusSpinner.setVisibility(View.GONE);
                    correctReadingMeterObservationSpinner.setVisibility(View.GONE);
                    til_correct_reading.setVisibility(View.GONE);
                    til_correct_reading_date.setVisibility(View.GONE);
                    til_consumer_number.setVisibility(View.GONE);
                    til_receipt_number.setVisibility(View.GONE);

                    til_swapped_consumer_number.setVisibility(View.GONE);
                    til_removal_reading_of_complaintent_consumer.setVisibility(View.GONE);
                    til_removal_reading_of_swapped_consumer.setVisibility(View.GONE);
                    ll_removal_date.setVisibility(View.GONE);

                    String connCategoryValue2 = CategorySpinner.getSelectedItem().toString();
                    if (connCategoryValue2.equalsIgnoreCase("Select")) {
                        strNewConnCategoryId = "";
                    } else {
                        DownloadConnCategory downloadConCategory = realmOperations.getDownloadConnCategoryId(connCategoryValue2);
                        strNewConnCategoryId = downloadConCategory.getCATEGORY_ID();
                    }

                    String connSizeValue2 = ConnectionSpinner.getSelectedItem().toString();
                    if (connSizeValue2.equalsIgnoreCase("Select")) {
                        strNewConnSizeId = "";
                    } else {
                        String cSize = connSizeValue2.replace("MM", "").trim();
                        //   Log.e("cSize", cSize);
                        DownloadConnSize downloadConSize = realmOperations.getDownloadConnSizeId(cSize);
                        strNewConnSizeId = downloadConSize.getID();
                    }


                } else if (billingAdjustTypeStr.equalsIgnoreCase("Posted on Wrong Account")) {
                    resetAllValues();
                    billAdjustMentTypeId = downloadAdjustmentTypeValueList.get(billingAdjustTypeSpinner.getSelection() - 1).toString();

                    til_consumer_number.setVisibility(View.VISIBLE);
                    til_receipt_number.setVisibility(View.VISIBLE);

                    adjustPeriodFromTypeSpinner.setVisibility(View.GONE);
                    adjustPeriodToTypeSpinner.setVisibility(View.GONE);
                    newTariffSpinner.setVisibility(View.GONE);
                    CategorySpinner.setVisibility(View.GONE);
                    ConnectionSpinner.setVisibility(View.GONE);
                    ll_tariff_change_from_date.setVisibility(View.GONE);
                    ll_tariff_change_to_date.setVisibility(View.GONE);

                    ll_current_reading_date.setVisibility(View.GONE);

                    correctReadingMeterStatusSpinner.setVisibility(View.GONE);
                    correctReadingMeterObservationSpinner.setVisibility(View.GONE);
                    til_correct_reading.setVisibility(View.GONE);
                    til_correct_reading_date.setVisibility(View.GONE);

                    til_swapped_consumer_number.setVisibility(View.GONE);
                    til_removal_reading_of_complaintent_consumer.setVisibility(View.GONE);
                    til_removal_reading_of_swapped_consumer.setVisibility(View.GONE);
                    ll_removal_date.setVisibility(View.GONE);

                    strNewConnCategoryId = "";
                    strNewConnSizeId = "";

                } else if (billingAdjustTypeStr.equalsIgnoreCase("Meter Swapping")) {
                    resetAllValues();
                    billAdjustMentTypeId = downloadAdjustmentTypeValueList.get(billingAdjustTypeSpinner.getSelection() - 1).toString();

                    til_swapped_consumer_number.setVisibility(View.VISIBLE);
                    adjustPeriodFromTypeSpinner.setVisibility(View.VISIBLE);
                    adjustPeriodToTypeSpinner.setVisibility(View.VISIBLE);

                    til_removal_reading_of_complaintent_consumer.setVisibility(View.VISIBLE);
                    til_removal_reading_of_swapped_consumer.setVisibility(View.VISIBLE);
                    ll_removal_date.setVisibility(View.VISIBLE);

                    til_consumer_number.setVisibility(View.GONE);
                    til_receipt_number.setVisibility(View.GONE);

                    newTariffSpinner.setVisibility(View.GONE);
                    CategorySpinner.setVisibility(View.GONE);
                    ConnectionSpinner.setVisibility(View.GONE);
                    ll_tariff_change_from_date.setVisibility(View.GONE);
                    ll_tariff_change_to_date.setVisibility(View.GONE);
                    ll_current_reading_date.setVisibility(View.GONE);


                    correctReadingMeterStatusSpinner.setVisibility(View.GONE);
                    correctReadingMeterObservationSpinner.setVisibility(View.GONE);
                    til_correct_reading.setVisibility(View.GONE);
                    til_correct_reading_date.setVisibility(View.GONE);

                    strNewConnCategoryId = "";
                    strNewConnSizeId = "";

                }

            }


            case R.id.observationSpinner:
                observationStr = observationSpinner.getSelectedItem().toString();
                if (observationStr.equalsIgnoreCase("Select")) {
                    observationStr = "0";
                } else {
                    // observationsId = msrListId.get(observationSpinner.getSelection() - 1).toString();
                    observationsId = "7";
                }
                // Log.d("gssssss",g+"   "+s);
                break;
            case R.id.correctReadingMeterStatusSpinner:

                correctReadingMeterStatusStr = correctReadingMeterStatusSpinner.getSelectedItem().toString();


                if (correctReadingMeterStatusStr.equalsIgnoreCase("Select")) {
                    correctReadingMeterStatusStr = "0";

                } else {
                    //   defaultObservation(correctMeterStatusId);
                    try {
                        if (correctReadingMeterStatusStr.equals(getResources().getString(R.string.select_plan))) {
                            ArrayList<String> subAll = new ArrayList<>();
                            subAll.add(getResources().getString(R.string.select_plan));

                            statusObservationAdapter = new ArrayAdapter(mCon, R.layout.simple_spinner_item, subAll);
                            statusObservationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            correctReadingMeterObservationSpinner.setAdapter(statusObservationAdapter);
                            correctReadingMeterStatusSpinner.setSelection(0);

                        } else {


                            correctMeterStatusId = downloadMeterStatusValueList.get(correctReadingMeterStatusSpinner.getSelection() - 1).toString();
                            meterObservationModels = realmOperations.fetchMeterObservation(correctMeterStatusId);
                            RealmResults<MStatusObservationModel> mStatusObservationModels = realmOperations.fetchMeterObservation(correctMeterStatusId);

                            moList.clear();

                            for (MStatusObservationModel model : meterObservationModels) {
                                moList.add(model.getMSNM_MSUBSTATUS_NAME());
                                moValueList.add(String.valueOf(model.getMSNM_MSUBSTATUS_ID()));
                            }


                            ArrayList<String> observationData = new ArrayList<>();
                            observationData.add(getResources().getString(R.string.select_plan));
                            observationData.addAll(moList);
                            int observation_list_size = observationData.size();


                            statusObservationAdapter = new ArrayAdapter(mCon, R.layout.simple_spinner_item, observationData);
                            statusObservationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            correctReadingMeterObservationSpinner.setAdapter(statusObservationAdapter);
                            correctReadingMeterObservationSpinner.setSelection(0);


                        }

                    } catch (Exception ex) {

                    }


                }
                break;
            case R.id.correctReadingMeterObservationSpinner:

                String name = correctReadingMeterObservationSpinner.getSelectedItem().toString();

                //   meterStatusModel = realmOperations.fetchMeterStatusByName(statusName);

                correctReadingObservationStatusStr = correctReadingMeterObservationSpinner.getSelectedItem().toString();


                if (!name.equals(getResources().getString(R.string.select_plan))) {

                    // meterObservationId = Integer.parseInt(moValueList.get(readerObservationSpinner.getSelectedItemPosition()-1).toString());

                    mStatusObservationModel = realmOperations.fetchMeterObservationById(name);
                    meterObservationIdStr = mStatusObservationModel.getMSNM_MSUBSTATUS_ID();
                }

                break;

            default:
                break;

            case R.id.newTariffSpinner:
                newTarrifStr = newTariffSpinner.getSelectedItem().toString();
                if (newTarrifStr.equalsIgnoreCase("All")) {
                    newTariffId = "0";
                } else {
                    newTariffId = downloadTariffValueList.get(newTariffSpinner.getSelection() - 1).toString();
                }
                break;

        }


    }

    private void resetAllValues() {
        et_swapped_consumer_number.setText("");
        adjustPeriodFromTypeSpinner.setSelection(0);
        adjustPeriodToTypeSpinner.setSelection(0);
        et_correct_reading.setText("");
        et_correct_reading_date.setText("");
        et_tariff_change_from_date.setText("");
        et_tariff_change_to_date.setText("");
        et_consumer_number.setText("");
        correctReadingMeterStatusSpinner.setSelection(0);
        correctReadingMeterObservationSpinner.setSelection(0);
        et_consumer_number.setText("");
        et_removal_reading_of_complaintent_consumer.setText("");
        et_removal_reading_of_swapped_consumer.setText("");
        et_removal_date.setText("");
        et_receipt_number.setText("");

    }


    @Override
    public void onNothingSelected(MaterialSpinner materialSpinner) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_current_reading_date:
                date_picker_current_reading_date(DATE_ID, year, month, day);

                break;

            case R.id.iv_tariff_change_from_date:
                date_picker_from(DATE_ID, year, month, day);
                break;
            case R.id.iv_tariff_change_to_date:
                date_picker_to(DATE_ID, year, month, day);
                break;
            case R.id.iv_removal_date:
                date_picker_removal(DATE_ID, year, month, day);
                break;
            case R.id.btn_submit:
                if (checkValidation()) {
                    int adjustFrom = Integer.parseInt(String.valueOf(Math.round(Float.parseFloat(adjustPeriodFromTypeStr))));
                    int adjustTo = Integer.parseInt(String.valueOf(Math.round(Float.parseFloat(adjustPeriodToTypeStr))));

                    if (billingAdjustTypeStr.equalsIgnoreCase("Posted on Wrong Account")) {
                        CheckConsumerReceiptNumber();
                    }

                    if ((!adjustPeriodFromTypeStr.equalsIgnoreCase("0")) && (!adjustPeriodToTypeStr.equalsIgnoreCase("0"))) {

                        if ((adjustFrom > adjustTo)) {
                            adjustPeriodFromTypeSpinner.setError(getResources().getString(R.string.cannot_be_empty));
                            //Toast.makeText(mCon, "Adjustment From Period should not be greater than Adjustment To Period.", Toast.LENGTH_SHORT).show();
                            MessageWindow.messageWindow(mCon, "Adjustment From Period should not be greater than Adjustment To Period.", "Alert");

                        } else {
                            if (billingAdjustTypeStr.equalsIgnoreCase("Meter Swapping")) {
                                CheckConsumerIdExit();
                            } else {
                                submitAllData();
                            }
                        }
                    }
                }
                break;

            default:
                break;
        }
    }

    private void CheckConsumerIdExit() {
        String params[] = new String[2];

        //you can get serviceId here
        params[0] = swappedConsumeNumberStr;
        params[1] = "3";

        if (connection.isConnectingToInternet()) {
            CheckConsumerIdExit checkConsumerIdExit = new CheckConsumerIdExit();
            checkConsumerIdExit.execute(params);

            checkConsumerIdExitProgress = new MaterialDialog.Builder(mCon)
                    .content(R.string.loading)
                    .progress(true, 0)
                    .autoDismiss(false)
                    .canceledOnTouchOutside(false)
                    .widgetColorRes(R.color.colorPrimary)
                    .show();
        } else {
            //Toast.makeText(mCon, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            MessageWindow.messageWindow(mCon, getResources().getString(R.string.no_internet_connection), "Alert");

        }
    }

    @SuppressLint("StaticFieldLeak")
    private class CheckConsumerIdExit extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                String paraName[] = new String[2];
                paraName[0] = "W_ServiceNo";
                paraName[1] = "Utility_Id";

                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.ComplaintSearchByConsumerNo, params, paraName);

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
                    if (enums.getConsumerComplaintDetailModel() != null && !enums.getConsumerComplaintDetailModel().isEmpty()) {

                        submitAllData();

                    } else {
                        // Toast.makeText(mCon, R.string.consumer_no_does_not_exist, Toast.LENGTH_SHORT).show();
                        MessageWindow.messageWindow(mCon, getResources().getString(R.string.consumer_no_does_not_exist), "Alert");
                    }
                } else {
                    //Toast.makeText(mCon, R.string.no_data_found, Toast.LENGTH_SHORT).show();
                    MessageWindow.messageWindow(mCon, getResources().getString(R.string.no_data_found), "Alert");

                }
            } catch (Exception e) {
                String error = e.toString();
                ErrorClass.errorData(mCon, "ComplaintHistoryActivity", "SearchConsumerById", error);
            }

            checkConsumerIdExitProgress.dismiss();
        }
    }

    private void CheckConsumerReceiptNumber() {

        String[] params = new String[2];
        params[0] = consumerNoEnterStr;
        params[1] = receiptNumberStr;
        //params[0] = "23218164";
        if (connection.isConnectingToInternet()) {

            PostedOnWrongAccount postedOnWrongAccount = new PostedOnWrongAccount();
            postedOnWrongAccount.execute(params);


        } else {
            //Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            MessageWindow.messageWindow(mCon, getResources().getString(R.string.no_internet_connection), "Alert");

        }

    }

    @SuppressLint("StaticFieldLeak")
    private class PostedOnWrongAccount extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            progressPostedOnWrongAccount = new MaterialDialog.Builder(mCon)
                    .content(R.string.checking_receipt)
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
                paraName[0] = "M_ServiceNo";
                paraName[1] = "ReceiptNo";


                jsonPostedOnWrongAccountResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.SCISPayment, params, paraName);
                Log.e("jsonResponse", jsonPostedOnWrongAccountResponse);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                progressPostedOnWrongAccount.dismiss();
                String[] enums = gson.fromJson(jsonPostedOnWrongAccountResponse, String[].class);
                if (enums[0].equalsIgnoreCase("Success")) {
                    //  Toast.makeText(mCon, "SUBMITTED", Toast.LENGTH_SHORT).show();
                    MessageWindow.messageWindow(mCon, "SUBMITTED", "Success");

                    progressPostedOnWrongAccount.dismiss();
                    submitAllData();
                } else {

                    // Toast.makeText(mCon, "" + enums[0], Toast.LENGTH_SHORT).show();
                    MessageWindow.messageWindow(mCon, enums[0], "Success");
                    progressPostedOnWrongAccount.dismiss();
                }
            } catch (Exception e) {
                Log.e("Exception", e.toString());
                progressPostedOnWrongAccount.dismiss();
                Toast.makeText(mCon, "Something went wrong", Toast.LENGTH_SHORT).show();
                String error = e.toString();
                ErrorClass.errorData(mCon, "Authentication Fragment", "Send Data to meter Installation", error);
            }
        }
    }

    private void date_picker_current_reading_date(final int id, final int year1, final int month, final int day) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                if (id == DATE_ID) {
                    cal.set(year, monthOfYear, dayOfMonth, 0, 0);
                    WorkCompletionBillingAdjustmentActivity.this.year = year;
                    WorkCompletionBillingAdjustmentActivity.this.month = monthOfYear;
                    WorkCompletionBillingAdjustmentActivity.this.day = dayOfMonth;
                    et_correct_reading_date.setText(DateFormat.dateFormat_1.format(cal.getTime()));
                    //  fromDate = et_tariff_change_from_date.getText().toString().trim();


                }
            }
        }, year1, month, day);
        //    datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis() - 1000);
        datePickerDialog.show();
    }

    private void date_picker_from(final int id, final int year1, final int month, final int day) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                if (id == DATE_ID) {
                    cal.set(year, monthOfYear, dayOfMonth, 0, 0);
                    WorkCompletionBillingAdjustmentActivity.this.year = year;
                    WorkCompletionBillingAdjustmentActivity.this.month = monthOfYear;
                    WorkCompletionBillingAdjustmentActivity.this.day = dayOfMonth;
                    et_tariff_change_from_date.setText(DateFormat.dateFormat_1.format(cal.getTime()));
                    //  fromDate = et_tariff_change_from_date.getText().toString().trim();


                }
            }
        }, year1, month, day);
        //    datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis() - 1000);
        datePickerDialog.show();
    }

    private void date_picker_to(final int id, final int year1, final int month, final int day) {

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                if (id == DATE_ID) {
                    cal.set(year, monthOfYear, dayOfMonth, 0, 0);
                    WorkCompletionBillingAdjustmentActivity.this.year = year;
                    WorkCompletionBillingAdjustmentActivity.this.month = monthOfYear;
                    WorkCompletionBillingAdjustmentActivity.this.day = dayOfMonth;
                    et_tariff_change_to_date.setText(DateFormat.dateFormat_1.format(cal.getTime()));
                    //  toDate = et_tariff_change_to_date.getText().toString().trim();
                  /*  try {
                        jsonObjectVehicleSearch.put("to_date",toDate);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
*/
                    strTariffFromDate = null;
                    strTariffToDate = null;
                    try {
                        strTariffFromDate = DateFormat.dateFormat_1.parse(et_tariff_change_from_date.getText().toString());
                        strTariffToDate = DateFormat.dateFormat_1.parse(et_tariff_change_to_date.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (strTariffToDate.before(strTariffFromDate)) {
                        // Toast.makeText(WorkCompletionBillingAdjustmentActivity.this, "Tariff From Date Cannot be greater than Tariff To Date.", Toast.LENGTH_SHORT).show();
                        MessageWindow.messageWindow(mCon, "Tariff From Date Cannot be greater than Tariff To Date.", "Alert");
                        et_tariff_change_to_date.setText("");
                    }

                }
            }
        }, year1, month, day);
        //   datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis() - 1000);
        datePickerDialog.show();
    }

    private void date_picker_removal(final int id, final int year1, final int month, final int day) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                if (id == DATE_ID) {
                    cal.set(year, monthOfYear, dayOfMonth, 0, 0);
                    WorkCompletionBillingAdjustmentActivity.this.year = year;
                    WorkCompletionBillingAdjustmentActivity.this.month = monthOfYear;
                    WorkCompletionBillingAdjustmentActivity.this.day = dayOfMonth;
                    et_removal_date.setText(DateFormat.dateFormat_1.format(cal.getTime()));
                    //  toDate = et_tariff_change_to_date.getText().toString().trim();
                  /*  try {
                        jsonObjectVehicleSearch.put("to_date",toDate);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
*/
                }
            }
        }, year1, month, day);
        //   datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis() - 1000);
        datePickerDialog.show();
    }

    private boolean checkValidation() {

        remarkStr = et_number_of_unit.getText().toString();
        observationStr = observationSpinner.getSelectedItem().toString();
        billingAdjustTypeStr = billingAdjustTypeSpinner.getSelectedItem().toString();
        adjustPeriodFromTypeStr = adjustPeriodFromTypeSpinner.getSelectedItem().toString();
        adjustPeriodToTypeStr = adjustPeriodToTypeSpinner.getSelectedItem().toString();

        if (adjustPeriodFromTypeStr.equalsIgnoreCase("Select")) {
            adjustPeriodFromTypeStr = "0";
        }


        if (adjustPeriodToTypeStr.equalsIgnoreCase("Select")) {
            adjustPeriodToTypeStr = "0";
        }

        swappedConsumeNumberStr = et_swapped_consumer_number.getText().toString();

        newTarrifStr = newTariffSpinner.getSelectedItem().toString();
        correctReadingStr = et_correct_reading.getText().toString();
        correctReadingDateStr = et_correct_reading_date.getText().toString();
        tariffChangeFromDateStr = et_tariff_change_from_date.getText().toString();
        tariffChangeToDateStr = et_tariff_change_to_date.getText().toString();
        consumerNoEnterStr = et_consumer_number.getText().toString();
        correctReadingMeterStatusStr = correctReadingMeterStatusSpinner.getSelectedItem().toString();

        correctReadingObservationStatusStr = correctReadingMeterObservationSpinner.getSelectedItem().toString();

        consumerNoEnterStr = et_consumer_number.getText().toString();
        removalReadingComplanintentConsumerStr = et_removal_reading_of_complaintent_consumer.getText().toString();
        removalReadingSwappedConsumerStr = et_removal_reading_of_swapped_consumer.getText().toString();
        removalDateStr = et_removal_date.getText().toString();
        receiptNumberStr = et_receipt_number.getText().toString();


        if (checkVisibility(observationSpinner)) {
            if (observationStr.equalsIgnoreCase("Select")) {
                observationStr = "0";
                observationSpinner.setError(getResources().getString(R.string.cannot_be_empty));

                // Toast.makeText(mCon, R.string.select_observation, Toast.LENGTH_SHORT).show();
                MessageWindow.messageWindow(mCon, this.getResources().getString(R.string.select_observation), "Alert");

                return false;
            } else {
                observationSpinner.setError(null);

            }
        }

        if (remarkStr.equalsIgnoreCase("")) {
            til_number_of_unit.setError(getResources().getString(R.string.cannot_be_empty));
            //Toast.makeText(mCon, R.string.please_enter_remark, Toast.LENGTH_SHORT).show();
            MessageWindow.messageWindow(mCon, this.getResources().getString(R.string.please_enter_remark), "Alert");

            return false;
        } else {
            til_number_of_unit.setError(null);

        }

        if (billingAdjustTypeStr.equalsIgnoreCase("Select")) {
            // Toast.makeText(mCon, "Please select bill adjust type", Toast.LENGTH_SHORT).show();
            MessageWindow.messageWindow(mCon, "Please select bill adjust type", "Alert");
            billingAdjustTypeSpinner.setError(getResources().getString(R.string.cannot_be_empty));
            return false;
        } else {
            billingAdjustTypeSpinner.setError(null);

        }

        if (checkVisibility(adjustPeriodFromTypeSpinner)) {
            adjustPeriodFromTypeStr = adjustPeriodFromTypeSpinner.getSelectedItem().toString();

            adjustPeriodToTypeStr = adjustPeriodToTypeSpinner.getSelectedItem().toString();
            if (adjustPeriodFromTypeStr.equalsIgnoreCase("Select")) {
                adjustPeriodFromTypeStr = "0";
                adjustPeriodFromTypeSpinner.setError(getResources().getString(R.string.cannot_be_empty));
                // Toast.makeText(mCon, "Please select adjustment period from", Toast.LENGTH_SHORT).show();
                MessageWindow.messageWindow(mCon, "Please select adjustment period from", "Alert");

                return false;
            } else {
                adjustPeriodFromTypeSpinner.setError(null);

            }
        }

        if (checkVisibility(CategorySpinner)) {
            if (CategorySpinner.getSelectedItem().toString().equalsIgnoreCase("Select")) {
                CategorySpinner.setError(getResources().getString(R.string.cannot_be_empty));

                //Toast.makeText(mCon, "Please select meter observation ", Toast.LENGTH_SHORT).show();
                MessageWindow.messageWindow(mCon, "Please select Category", "Alert");

                return false;
            }
        } else {
            CategorySpinner.setError(null);
        }

        if (checkVisibility(ConnectionSpinner)) {
            if (ConnectionSpinner.getSelectedItem().toString().equalsIgnoreCase("Select")) {
                ConnectionSpinner.setError(getResources().getString(R.string.cannot_be_empty));
                MessageWindow.messageWindow(mCon, "Please select Connection Size", "Alert");
                return false;
            }
        } else {
            ConnectionSpinner.setError(null);
        }
        if (checkVisibility(adjustPeriodToTypeSpinner)) {
            if (adjustPeriodToTypeStr.equalsIgnoreCase("Select")) {
                adjustPeriodToTypeStr = "0";
                adjustPeriodToTypeSpinner.setError(getResources().getString(R.string.cannot_be_empty));

                //Toast.makeText(mCon, "Please select adjustment period to", Toast.LENGTH_SHORT).show();
                MessageWindow.messageWindow(mCon, "Please select adjustment period to", "Alert");

                return false;
            } else {
                adjustPeriodToTypeSpinner.setError(null);
            }
        }
        if (checkVisibility(til_swapped_consumer_number)) {
            if (swappedConsumeNumberStr.equalsIgnoreCase("")) {
                til_swapped_consumer_number.setError(getResources().getString(R.string.cannot_be_empty));

                //Toast.makeText(mCon, "Swapped consumer Cannot be blank.", Toast.LENGTH_SHORT).show();
                MessageWindow.messageWindow(mCon, "Swapped consumer Cannot be blank.", "Alert");

                return false;
            } else {
                til_swapped_consumer_number.setError(null);
            }
        }
        if (checkVisibility(til_correct_reading)) {
            if (correctReadingStr.equalsIgnoreCase("")) {
                til_correct_reading.setError(getResources().getString(R.string.cannot_be_empty));
                //Toast.makeText(mCon, "Correct Reading Cannot be blank.", Toast.LENGTH_SHORT).show();
                MessageWindow.messageWindow(mCon, "Correct Reading Cannot be blank.", "Alert");

                return false;
            } else {
                til_correct_reading.setError(null);

            }

        }
        if (checkVisibility(til_correct_reading_date)) {
            if (correctReadingDateStr.equalsIgnoreCase("")) {
                til_correct_reading_date.setError(getResources().getString(R.string.cannot_be_empty));
                //Toast.makeText(mCon, "Please select correct reading date", Toast.LENGTH_SHORT).show();
                MessageWindow.messageWindow(mCon, "Please select correct reading date", "Alert");

                return false;
            } else {
                til_correct_reading_date.setError(null);

            }
        }
        if (checkVisibility(correctReadingMeterStatusSpinner)) {
            if (correctReadingMeterStatusStr.equalsIgnoreCase("Select")) {
                correctReadingMeterStatusSpinner.setError(getResources().getString(R.string.cannot_be_empty));

                //Toast.makeText(mCon, "Please select meter status", Toast.LENGTH_SHORT).show();
                MessageWindow.messageWindow(mCon, "Please select meter status", "Alert");

                return false;
            } else {
                correctReadingMeterStatusSpinner.setError(null);
            }
        }
        if (checkVisibility(correctReadingMeterObservationSpinner)) {
            if (correctReadingObservationStatusStr.equalsIgnoreCase("--Select--")) {
                correctReadingMeterObservationSpinner.setError(getResources().getString(R.string.cannot_be_empty));

                //Toast.makeText(mCon, "Please select meter observation ", Toast.LENGTH_SHORT).show();
                MessageWindow.messageWindow(mCon, "Please select meter observation ", "Alert");

                return false;
            }
        } else {
            correctReadingMeterObservationSpinner.setError(null);
        }
        if (checkVisibility(newTariffSpinner)) {
            if (newTarrifStr.equalsIgnoreCase("Select")) {
                newTariffSpinner.setError(getResources().getString(R.string.cannot_be_empty));

                //Toast.makeText(mCon, "Please select meter observation ", Toast.LENGTH_SHORT).show();
                MessageWindow.messageWindow(mCon, "Please select meter observation", "Alert");

                return false;
            }
        } else {
            newTariffSpinner.setError(null);
        }

        if (checkVisibility(ll_tariff_change_from_date)) {
            if (tariffChangeFromDateStr.equalsIgnoreCase("")) {
                til_tariff_change_from_date.setError(getResources().getString(R.string.cannot_be_empty));
                //Toast.makeText(mCon, "Please select correct reading from date", Toast.LENGTH_SHORT).show();
                MessageWindow.messageWindow(mCon, "Please select correct reading from date", "Alert");

                return false;
            } else {
                til_tariff_change_from_date.setError(null);

            }
        }
        if (checkVisibility(ll_tariff_change_to_date)) {
            if (tariffChangeToDateStr.equalsIgnoreCase("")) {
                til_tariff_change_to_date.setError(getResources().getString(R.string.cannot_be_empty));
                // Toast.makeText(mCon, "Please select correct reading to date", Toast.LENGTH_SHORT).show();
                MessageWindow.messageWindow(mCon, "Please select correct reading to date", "Alert");

                return false;
            } else {
                til_tariff_change_to_date.setError(null);

            }
        }

        if (checkVisibility(til_consumer_number)) {
            if (consumerNoEnterStr.equalsIgnoreCase("")) {
                til_consumer_number.setError(getResources().getString(R.string.cannot_be_empty));
                //Toast.makeText(mCon, "Please enter consumer number", Toast.LENGTH_SHORT).show();
                MessageWindow.messageWindow(mCon, "Please enter consumer number", "Alert");

                return false;
            } else {
                til_consumer_number.setError(null);

            }
        }
        if (checkVisibility(til_receipt_number)) {
            if (receiptNumberStr.equalsIgnoreCase("")) {
                til_receipt_number.setError(getResources().getString(R.string.cannot_be_empty));
                // Toast.makeText(mCon, "Please enter receipt number", Toast.LENGTH_SHORT).show();
                MessageWindow.messageWindow(mCon, "Please enter receipt number", "Alert");

                return false;
            } else {
                til_receipt_number.setError(null);

            }
        }
        if (checkVisibility(til_removal_reading_of_complaintent_consumer)) {
            if (removalReadingComplanintentConsumerStr.equalsIgnoreCase("")) {
                til_removal_reading_of_complaintent_consumer.setError(getResources().getString(R.string.cannot_be_empty));
                //Toast.makeText(mCon, "Please enter removal reading of complaint consumer number", Toast.LENGTH_SHORT).show();
                MessageWindow.messageWindow(mCon, "Please enter removal reading of complaint consumer number", "Alert");

                return false;
            } else {
                til_removal_reading_of_complaintent_consumer.setError(null);

            }
        }
        if (checkVisibility(til_removal_reading_of_swapped_consumer)) {
            if (removalReadingSwappedConsumerStr.equalsIgnoreCase("")) {
                til_removal_reading_of_swapped_consumer.setError(getResources().getString(R.string.cannot_be_empty));
                //Toast.makeText(mCon, "Please enter removal reading of swapped consumer number", Toast.LENGTH_SHORT).show();
                MessageWindow.messageWindow(mCon, "Please enter removal reading of swapped consumer number", "Alert");

                return false;
            } else {
                til_removal_reading_of_swapped_consumer.setError(null);

            }
        }
        if (checkVisibility(ll_removal_date)) {
            if (removalDateStr.equalsIgnoreCase("")) {
                til_removal_date.setError(getResources().getString(R.string.cannot_be_empty));
                //Toast.makeText(mCon, "Please enter removal date", Toast.LENGTH_SHORT).show();
                MessageWindow.messageWindow(mCon, "Please enter removal date", "Alert");

                return false;
            } else {
                til_removal_date.setError(null);

            }
        }
        return true;
    }

    private boolean checkVisibility(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            return true;
        }
        return false;
    }

    private void submitAllData() {

        String empcode = null;
        try {
            // Decrypt EmpCode
            empcode = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String params[] = new String[24];
        params[0] = complaintNoStr;
        params[1] = consumerNoStr;
        params[2] = billAdjustMentTypeId;
        params[3] = billAdjustMentTypeId;
        params[4] = adjustPeriodFromTypeStr;
        params[5] = adjustPeriodToTypeStr;
        params[6] = "";
        params[7] = correctReadingStr;
        params[8] = correctReadingDateStr;
        params[9] = correctMeterStatusId;
        params[10] = meterObservationIdStr;
        params[11] = newTariffId;
        params[12] = tariffChangeFromDateStr;
        params[13] = tariffChangeToDateStr;
        params[14] = consumerNoEnterStr;
        params[15] = receiptNumberStr;
        params[16] = swappedConsumeNumberStr;
        params[17] = remarkStr;
        params[18] = empcode;
        //params[19] = PreferenceUtil.getMac();
        params[19] = "";

        params[20] = strConnCategory;
        params[21] = strNewConnCategoryId;
        params[22] = strConnSize;
        params[23] = strNewConnSizeId;

        Log.e("Params", Arrays.toString(params));

        if (connection.isConnectingToInternet()) {
            BillingAdjustment billingAdjustment = new BillingAdjustment();
            billingAdjustment.execute(params);
        } else {
            Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }

    }

    @SuppressLint("StaticFieldLeak")
    private class BillingAdjustment extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            progress = new MaterialDialog.Builder(mCon)
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
                String paraName[] = new String[24];


                paraName[0] = "RefNo";
                paraName[1] = "Consumer";
                paraName[2] = "AdjType";
                paraName[3] = "AdjSubType";
                paraName[4] = "AdjFrom";
                paraName[5] = "AdjTo";
                paraName[6] = "MeterPercent";
                paraName[7] = "CorrectReading";
                paraName[8] = "CorrectReadingDt";
                paraName[9] = "CorrectReadingMeterStatus";
                paraName[10] = "CorrectReadingObservation";
                paraName[11] = "Tariff";
                paraName[12] = "TariffFrom";
                paraName[13] = "TariffTo";
                paraName[14] = "WrongConsumer";
                paraName[15] = "ReceiptNo";
                paraName[16] = "SwappedConsumerRemovalReading";
                paraName[17] = "Remarks";
                paraName[18] = "ModifiedBy";
                paraName[19] = "IP";
                paraName[20] = "OldCategory";
                paraName[21] = "NewCategory";
                paraName[22] = "OldConnSize";
                paraName[23] = "NewConnSize";


                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.Complaint_BillAdjRequest, params, paraName);
                Log.e("Complaint_BillAdjRequest", jsonResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                BillingAdjustmentResponceModel enums = gson.fromJson(jsonResponse, BillingAdjustmentResponceModel.class);
                if (enums.getMessage().equalsIgnoreCase("200")) {
                    //  Toast.makeText(mCon, R.string.billing_adjustment_on_process, Toast.LENGTH_LONG).show();
                    validate();
                } else {
                    Toast.makeText(mCon, R.string.billing_adjustment_on_process, Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Log.d("check", e.getMessage());
                Toast.makeText(mCon, e.getMessage(), Toast.LENGTH_SHORT).show();

                String error = e.toString();
                ErrorClass.errorData(mCon, "WorkCompletionBillingAdjustmentActivity", "click reAllocateTextView", error);
            }
            progress.dismiss();
        }
    }


    public void validate() {
        progress.dismiss();

        String siteEng = null;
        try {
            siteEng = new AesAlgorithm().decrypt(PreferenceUtil.getSiteEng());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (siteEng != null) {


            if (connection.isConnectingToInternet()) {
//                        if (PreferenceUtil.getSiteEng().equals("ZonalManager")) {
                workCompletionDateTime = completionDateStr + " " + timeFormat;

                String empcode = null;
                try {
                    // Decrypt EmpCode
                    empcode = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String param1[] = new String[24];

                param1[0] = "0";
                param1[1] = meterNoStr;  // 15051025
                param1[2] = "7";
                param1[3] = complaintWorkAllocateDateStr;   // test data "09-May-2019 12:33 PM";
                param1[4] = consumerNoStr;  // test data 111
                param1[5] = "146";
                param1[6] = remarkStr;
                param1[7] = empcode;
                param1[8] = address1Str;
                param1[9] = "";
                param1[10] = "";
                param1[11] = pincodeStr;
                param1[12] = complaintNoStr;
                param1[13] = "";
                param1[14] = complaintTypeStr;  // test data "Meter Reading Management";
                param1[15] = complaintSubTypeStr;   // test data "Wrong Reading";
                param1[16] = "For Billing Adjustment";    // test data  "MMG";
                param1[17] = PreferenceUtil.getRights();// param[17] = PreferenceUtil.getRights(); param[17] = "SYSADM001";
                param1[18] = meterTransIdStr;  // test data  "1";
                param1[19] = userType; // from login
                param1[20] = "No-OTP";
                param1[21] = empcode;
                param1[22] = "123456";
                param1[23] = "";


                Log.e("UpdateWorkCompletionParams", Arrays.toString(param1));

                if (connection.isConnectingToInternet()) {
                    UpdateWorkCompletion updateWorkCompletion = new UpdateWorkCompletion();
                    updateWorkCompletion.execute(param1);
                } else {
                    Toast.makeText(mCon, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }

//                        }
//                        else {
//                            sendSMS sendSMS = new sendSMS();
//                            sendSMS.execute(param);
//                        }

            } else {
                Toast.makeText(mCon, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            }

        }


    }

    public class UpdateWorkCompletion extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            progress = new MaterialDialog.Builder(mCon)
                    .content(R.string.loading)
                    .widgetColorRes(R.color.colorPrimary)
                    .progress(true, 0)
                    .cancelable(false)
                    .canceledOnTouchOutside(false)
                    .show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                String paraNames[] = new String[24];

                paraNames[0] = "SearchFor";
                paraNames[1] = "MeterNo";   //COM_METER_REPLACE
                paraNames[2] = "Action";
                paraNames[3] = "WCDate";   // work completion date and time
                paraNames[4] = "ConsumerNo";
                paraNames[5] = "Observation";  // observation id
                paraNames[6] = "Remarks";
                paraNames[7] = "EmpId";  // string of employees
                paraNames[8] = "Address1";
                paraNames[9] = "Address2";
                paraNames[10] = "Address3";
                paraNames[11] = "Pin";
                // paraNames[12] = "w_comseq";  // test data(OC/30/03/19/150)
                paraNames[12] = "RefNo";
                paraNames[13] = "EmpType";
                paraNames[14] = "CompType";
                paraNames[15] = "CompSubType";
                paraNames[16] = "ActionText";
                paraNames[17] = "CSVRights";   // rights test-data (SYSADM001)
                paraNames[18] = "COM_METER_TRANSID";
                paraNames[19] = "UserType";  // test-data(Web)
                paraNames[20] = "OTP";  // test-data (NO-OTP)
                paraNames[21] = "Emp_Code";
                paraNames[22] = "IP";
                paraNames[23] = "NonOTPRemarks";


                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.UpdateWorkCompletionData, params, paraNames);

                Log.e("UpdateWorkCompletionData", "Response: " + jsonResponse);

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ExceptionUpdatedWork", e.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                UpdateCompletionResponseModel enums = gson.fromJson(jsonResponse, UpdateCompletionResponseModel.class);

                if (enums.getDiv_Cmsg() == null || enums.getDiv_Cmsg().equals("")) {
                    // Toast.makeText(mCon, enums.getDiv_CError(), Toast.LENGTH_SHORT).show();
                    MessageWindow.messageWindow(mCon, enums.getDiv_CError(), "Alert");

                } else {
                    if (actionStr.equals("Finish")) {
                        //Toast.makeText(mCon, R.string.work_completion_successfully, Toast.LENGTH_SHORT).show();
                        messageWindow(mCon, getResources().getString(R.string.work_completion_successfully), "Alert");
                    } else if (actionStr.equals("REJECT")) {
                        //Toast.makeText(mCon, R.string.work_rejected_successfully, Toast.LENGTH_SHORT).show();
                        messageWindow(mCon, getResources().getString(R.string.work_rejected_successfully), "Alert");
                    } else {
                        //Toast.makeText(mCon, "Complaint forwarded to " + actionStr + " successfully", Toast.LENGTH_SHORT).show();
                        messageWindow(mCon, "Complaint forwarded to " + actionStr + " successfully", "Alert");
                    }
                }

            } catch (Exception e) {
                Log.e("check_Exception", e.getMessage());
                Log.e("check_Exception", "" + jsonResponse);
                String error = e.toString();
                ErrorClass.errorData(mCon, "WorkCompletionDetailedActivity", "validate Submit Otp", error);
            }
            progress.dismiss();
        }
    }

    public void messageWindow(Context context, String msg, String title) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(title)
                    .setCancelable(false)
                    .setMessage(msg);

            builder.setPositiveButton(context.getString(R.string.ok),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            startNextActivity();
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startNextActivity() {

        //Intent intent = new Intent(WorkCompletionBillingAdjustmentActivity.this, WorkComplaintLocationActivity.class);
        Intent intent = new Intent(WorkCompletionBillingAdjustmentActivity.this, WorkAllocationCompletionActivity.class);
        intent.putExtra("fromDate", fromDate);
        intent.putExtra("toDate", toDate);
        intent.putExtra("strClick", clickStr);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        finish();
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

