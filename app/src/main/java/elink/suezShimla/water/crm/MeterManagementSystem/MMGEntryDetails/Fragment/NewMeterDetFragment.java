package elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGMakerCodeModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGMeterLocationModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGMeterSizeModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGMeterTypeModel;
import elink.suezShimla.water.crm.MainActivity;
import elink.suezShimla.water.crm.MessageWindow;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Activity.MMGScreenActivity;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model.InstallDetails;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model.MMGContractorResponseDetail;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model.MMGMeterConnectedDetailsModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;
import elink.suezShimla.water.crm.constant.AppConstant;

public class NewMeterDetFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    SimpleDateFormat newSdf = new SimpleDateFormat("dd-MMM-yyyy");
    private Context mCon;
    private ArrayAdapter dialDigitAdapter;
    private RealmOperations realmOperations;
    private MMGMakerCodeModel mmgMakerCodeModel;
    private MMGMeterSizeModel mmgMeterSizeModel;
    private MMGMeterTypeModel mmgMeterTypeModel;
    private MMGMeterLocationModel mmgMeterLocationModel;
    public SendMtrSizeMM sendMtrSize;
    public SendNewMeterDetails sendNewMeterDetails;
    private MaterialDialog meterInstallationProgress;

    LinearLayout liner_meterMake, liner_meterNo, liner_installDt, liner_sizeMeter, liner_meterType,
            liner_meterLocation, liner_protectedBox, liner_meterHandover, ll_date, dialDigitLayout, liner_meteroewrnship;

    AppCompatSpinner metermakeSpinner, meterSizeSpinner, meterTypSpinner, locationSpinner, protectedBoxSpinner,
            mtrHandoverSpinner, dialDigitSpinner, sealmakeSpinner, meterboxmakeSpinner, meterOwnershipspinner;
    EditText sealNoEditText, initialReadingEditText,etNewavrageconsum, meterNoEditText;
    Button submitButton, saveAndExit;
    String meterMakeStr = "", meterNoStr = "", installDtStr = "", sizeStr = "", sealNoStr = "", initialReadingStr = "",newAvrageconsum = "", meterTypeStr = "", meterLocationStr = "", protectedBoxStr = "", taxNoStr = "",
            meterHandoverStr = "", meterMakeIdStr = "", meterSizeIdStr = "", meterTypeIdStr = "", meterLocationIdStr = "", protectedBoxIdStr = "",
            meterHandoverIdStr = "", sealMakeStr = "", meterBoxMakeStr = "", meterown = "", jsonResponseMeterBalance = "";
    private String makerCodeId = "", meterSizeId = "", meterTypeId = "", meterLocationId = "";
    private String cont_id = "", cont_emp_code = "", meter_status = "", seal_status = "", valid_meter = "";
    private Calendar fromDateCalendar;
    String jsonResponse = "", radiobuttonValStr = "", commisioned_noncommisioned = "", BU, PC, jsonMeterInstallSaveResponse;
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    EditText et_date, dialDigitEditText;
    TextView mid, dial_digit_showtext;
    private MaterialDialog digit_progress, seal_progress, meterBalanceProgressDialog;
    int mHour, mMinute, mSec;
    String am_pm = "", strHrsToShow = "", dateTime = "", time = "", meterownvalue = "";
    ArrayList<String> dialDigitList;

    boolean isNewMeterSubmitted = false;
    public static String MI_METERINSTALLID, MI_ACTION, MI_CONSUMER, MI_BU, MI_PC, MI_REFNO,
            MI_N_MAKE = "", MI_N_SIZE, MI_N_SEAL, MI_N_SEAL_MAKE = "", MI_N_METER_BOX_MAKE, MI_N_METER, MI_INSTALLATIONDATE, MI_N_INITIALREADING, MI_N_METERTYPE, MI_N_METERLOCATION, MI_N_ISPROTECTED, MI_PROPERTYTAXNO, MI_N_ISMETERHANDOVER,
            MI_CONTRACTOR = "", MI_CONTRACTOREMP = "", MI_N_ISMATERIALHANDOVER = "", MI_PCCBEDDINGLEN = "", MI_PCCBEDDINGWID = "",
            MI_PCCBEDDINGDEP = "", MI_ROADCUTTINGTYPE = "", MI_ROADCUTTINGLEN = "", MI_ROADCUTTINGWID = "", MI_ROADCUTTINGDEP = "", MI_FROMNODE,
            MI_TONODE, MI_REGMOBILE, MI_ALTMOBILE, MI_GIS, MI_DMA, MI_SR, MI_MODIFIEDBY, MI_MODIFIEDDATE, MI_IP, MI_AUTHENTICATEDATE,
            MI_AUTHREJECTBY, MI_REJECTEDDATE, MI_STATUS, MI_ISACTIVE, MI_XMLMATERIAL = "", MI_XMLCIVIL = "", MI_O_OBSERVATION = "",
            MI_SOURCE, MI_ISCOMMISSIONED, MI_CONTRACTOROTHER = "", MI_CONTRACTOREMPOTHER = "", MI_N_DIGIT, MSRID, sealmakeId = "", meterboxmakeId = "";
    String MI_O_SIZE = "", MI_O_METER = "", MI_O_MAKE = "", MI_O_PREVIOUSREADING = "", MI_O_FINALREADING = "", MI_O_FINALSTATUS = "", MI_O_REASON = "", MI_O_METERTYPE = "", MI_N_METEROWNERSHIP = "";
    static String contList = "", pagename = "", consumerNoStr, refNoStr, zone_str = "", group_str = "", to_node_str = "", serachById = "", strCurrentDate = "",
            from_node_str, primary_mob_str, alternate_mob_str, gis_bid_str, dma_id, sr_id, property_assess, dial_digit = "",
            old_maker_code_id, mtrSizeId, serialNoStr, previousReading, finalReadingValStr, finalStatusIdStr, meterObservationId, meterReasonId;
    static boolean isConsumerSubmitted = false, is_old_submitted = false;

    //  ArrayList<MMGCustDetModel> customerDetailList = new ArrayList<>(); //pinky 20/01/2022
    //ArrayList<MMGValidateDetails> validateDetailList = new ArrayList<>(); //pinky 20/01/2022

    ArrayList<MMGMeterConnectedDetailsModel> meterConnectionList = new ArrayList<>();
    ArrayList<InstallDetails> installDetails = new ArrayList<>();
    String dataFound, lastReadingDate;
    String STARTTIME = "", ALERTSTARTTIME = "",deviceAuthorization = "", appIsLogged = "";
    String rtimem = "Reading can be taken between";
    boolean submitData = false;
    String ENDTIME = "", ALERTENDTIME = "";

    public NewMeterDetFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // prevent ss and hide content when app is on background
       // getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mCon = getActivity();
        realmOperations = new RealmOperations(mCon);
        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();

        if (pagename != null) {
            pagename = getArguments().getString("pagename");
            contList = getArguments().getString("contList");
        }
        serachById = getArguments().getString("serachById");
        consumerNoStr = getArguments().getString("consumerNoStr");
        refNoStr = getArguments().getString("refNoStr");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_meter_det, container, false);

        initialize(view);

        radiobuttonValStr = UtilitySharedPreferences.getPrefs(getActivity(), Constants.RADIOBUTTONVAL);
        commisioned_noncommisioned = UtilitySharedPreferences.getPrefs(getActivity(), Constants.COMMISIONED_NONCOMMISIONED);

        BU = UtilitySharedPreferences.getPrefs(getActivity(), Constants.BU);
        PC = UtilitySharedPreferences.getPrefs(getActivity(), Constants.PC);
        dataFound = UtilitySharedPreferences.getPrefs(getActivity(), Constants.DATAFOUND);
        MI_METERINSTALLID = UtilitySharedPreferences.getPrefs(getActivity(), Constants.MI_METERINSTALLID);

        MMGScreenActivity activity = (MMGScreenActivity) getActivity();
        // customerDetailList = activity.getMMGCustomerDetailsData(); //pinky 20/01/2022
        // validateDetailList = activity.getMmgvalidateDetailList(); //pinky 20/01/2022

        meterConnectionList = activity.getMMGMeterConnectionDetailsData();
        installDetails = activity.getMMGInstallDetailsData();

        dialDigitEditText.setVisibility(View.GONE);
        dial_digit_showtext.setVisibility(View.GONE);
        String currentDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa", Locale.getDefault()).format(new Date());
        et_date.setText(currentDate);

        deviceAuthorization = UtilitySharedPreferences.getPrefs(mCon, AppConstant.DEVICEAUTHORIZATION);
        appIsLogged = UtilitySharedPreferences.getPrefs(mCon, AppConstant.APP_ISLOGGED);


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
        if (deviceAuthorization.equalsIgnoreCase("0")) {
            ((MainActivity) mCon).deviceAuthorizedDialog();

        } else {
            if (appIsLogged.equalsIgnoreCase("0")) {
                ((MainActivity) getActivity()).adminLogout();
            }
        }
        addFilterToEMeterNo();

        init();

        return view;
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
    private void initialize(View view) {

        liner_meterMake = view.findViewById(R.id.liner_meterMake);
        liner_meterNo = view.findViewById(R.id.liner_meterNo);
        liner_installDt = view.findViewById(R.id.liner_installDt);
        liner_sizeMeter = view.findViewById(R.id.liner_sizeMeter);
        liner_meterType = view.findViewById(R.id.liner_meterType);
        liner_meterLocation = view.findViewById(R.id.liner_meterLocation);
        liner_protectedBox = view.findViewById(R.id.liner_protectedBox);
        liner_meteroewrnship = view.findViewById(R.id.liner_meteroewrnship);
        liner_meterHandover = view.findViewById(R.id.liner_meterHandover);

        ll_date = view.findViewById(R.id.ll_date);
        ll_date.setOnClickListener(this);

        dialDigitLayout = view.findViewById(R.id.dialDigitLayout);

        metermakeSpinner = view.findViewById(R.id.metermakeSpinner);
        metermakeSpinner.setOnItemSelectedListener(this);

        sealmakeSpinner = view.findViewById(R.id.sealmakeSpinner);
        sealmakeSpinner.setOnItemSelectedListener(this);

        meterboxmakeSpinner = view.findViewById(R.id.meterBoxMakeSpinner);
        meterboxmakeSpinner.setOnItemSelectedListener(this);

        meterSizeSpinner = view.findViewById(R.id.meterSizeSpinner);
        meterSizeSpinner.setOnItemSelectedListener(this);

        meterTypSpinner = view.findViewById(R.id.meterTypSpinner);
        meterTypSpinner.setOnItemSelectedListener(this);

        locationSpinner = view.findViewById(R.id.locationSpinner);
        locationSpinner.setOnItemSelectedListener(this);

        meterOwnershipspinner = view.findViewById(R.id.meterOwnership);
        meterOwnershipspinner.setOnItemSelectedListener(this);

        protectedBoxSpinner = view.findViewById(R.id.protectedBoxSpinner);
        protectedBoxSpinner.setOnItemSelectedListener(this);

        mtrHandoverSpinner = view.findViewById(R.id.mtrHandoverSpinner);
        mtrHandoverSpinner.setOnItemSelectedListener(this);

        meterNoEditText = view.findViewById(R.id.meterNoEditText);
        dialDigitEditText = view.findViewById(R.id.dialDigitEditText);
        dialDigitSpinner = view.findViewById(R.id.dialDigitSpinner);
        dialDigitSpinner.setOnItemSelectedListener(this);

        mid = view.findViewById(R.id.mid);
        dial_digit_showtext = view.findViewById(R.id.dial_digit_showtext);

        et_date = view.findViewById(R.id.et_date);
        sealNoEditText = view.findViewById(R.id.sealNoEditText);
        initialReadingEditText = view.findViewById(R.id.initialReadingEditText);
        etNewavrageconsum = view.findViewById(R.id.etNewavrageconsum);
        submitButton = view.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(this);

        saveAndExit = view.findViewById(R.id.saveAndExit);
        saveAndExit.setOnClickListener(this);

        fromDateCalendar = Calendar.getInstance();

        meterNoEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);

        Date date1 = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
        strCurrentDate = formatter.format(date1);

        sealNoEditText.setText("");//pinky
        MI_N_SEAL_MAKE = "";//pinky added 07/03/2022
    }

    private void addFilterToEMeterNo() {

        meterNoEditText.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(8),
                new InputFilter() {
                    public CharSequence filter(CharSequence src, int start,
                                               int end, Spanned dst, int dstart, int dend) {
                        if (src.equals("")) { // for backspace
                            return src;
                        }
                        if (src.toString().matches("[A-Z 0-9]+")) {
                            return src;
                        }
                        if (src.toString().length() < 8) {

                        }
                        return "";
                    }
                }
        });

    }

    private void init() {

        disableNewMeterFields();

        if (radiobuttonValStr.equalsIgnoreCase("MB") ||
                radiobuttonValStr.equalsIgnoreCase("R")) {//condition added by pinky for R
            saveAndExit.setEnabled(true);
        } else {
            saveAndExit.setEnabled(false);
        }

        if (radiobuttonValStr.equalsIgnoreCase("S")) {
            metermakeSpinner.setEnabled(false);
            meterboxmakeSpinner.setEnabled(false);
            meterSizeSpinner.setEnabled(false);
            meterTypSpinner.setEnabled(false);
            locationSpinner.setEnabled(false);
            protectedBoxSpinner.setEnabled(false);
            mtrHandoverSpinner.setEnabled(false);
            meterNoEditText.setEnabled(false);
            dialDigitSpinner.setEnabled(false);
            initialReadingEditText.setEnabled(false);
            meterOwnershipspinner.setEnabled(false);
        } else if (radiobuttonValStr.equalsIgnoreCase("MB")) {
            metermakeSpinner.setEnabled(false);
            meterSizeSpinner.setEnabled(false);
            meterTypSpinner.setEnabled(false);
            locationSpinner.setEnabled(false);
            protectedBoxSpinner.setEnabled(false);
            protectedBoxSpinner.setSelection(1);
            sealmakeSpinner.setEnabled(false);
            sealNoEditText.setEnabled(false);
            mtrHandoverSpinner.setEnabled(false);
            meterNoEditText.setEnabled(false);
            dialDigitSpinner.setEnabled(false);
            initialReadingEditText.setEnabled(false);
            meterOwnershipspinner.setEnabled(false);
        }

        meterMakeDropdown();

        meterSizeDropdown();

        sealMakeDropdown();

        setDialDigitAdapter();

        meterTypeDropwdown();

        meterLocationDropdown();

        meterBoxMakeDropdown();

        MeterOwnershipDropdown();

        setOldMeterData();

    }

    //rupali
    private void MeterOwnershipDropdown() {

       // String[] meterSizeName = new String[]{"MJP", "Consumer Own"};
        String[] meterSizeName = new String[]{"Government", "Private"};
        ArrayList<String> Meterownwershiplist = new ArrayList<>();
        Meterownwershiplist.add("--Select--");
        Meterownwershiplist.addAll(Arrays.asList(meterSizeName));

        try {
            ArrayAdapter meterOwnershipadapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, Meterownwershiplist);
            meterOwnershipadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            meterOwnershipspinner.setAdapter(meterOwnershipadapter);
            meterOwnershipspinner.setOnItemSelectedListener(this);

            if (MI_N_METEROWNERSHIP != null && !(MI_N_METEROWNERSHIP.equalsIgnoreCase(""))) {
                if (MI_N_METEROWNERSHIP.equalsIgnoreCase("1")) {
                    meterOwnershipspinner.setSelection(1);
                } else if (MI_N_METEROWNERSHIP.equalsIgnoreCase("2")) {
                    meterOwnershipspinner.setSelection(2);
                } else {
                    meterOwnershipspinner.setSelection(0);
                }
            }
            //  meterownvalue = String.valueOf(meterOwnershipspinner.getSelectedItemPosition());

        } catch (Exception ex) {

        }

    }

    private void setOldMeterData() {
        if (installDetails != null) {
            Log.e("install",installDetails.toString());

            for (int k = 0; k <= installDetails.size() - 1; k++) {

                dataFound = installDetails.get(k).getNODATAFOUND();
                if (dataFound.equalsIgnoreCase("DataFound")) {

                    MI_N_METER = installDetails.get(k).getMI_N_METER();
                    if (MI_N_METER != null) {
                        meterNoEditText.setText(MI_N_METER);
                        meterNoStr = MI_N_METER;
                    } else
                        meterNoEditText.setText("");

                    MI_N_MAKE = installDetails.get(k).getMI_N_MAKE();
                    // Log.e("MI_N_MAKE",MI_N_MAKE);
                    if (MI_N_MAKE != null)
                        meterMakeDropdown();

                    MI_N_SIZE = installDetails.get(k).getMI_N_SIZE();
                    if (MI_N_SIZE != null)
                        meterSizeDropdown();
                    //Log.e("MI_N_SIZE",MI_N_SIZE);

                    MI_N_SEAL = installDetails.get(k).getMI_N_SEAL();

                    if (MI_N_SEAL != null)
                        sealNoEditText.setText(MI_N_SEAL);
                    else
                        sealNoEditText.setText("");


                    MI_N_SEAL_MAKE = installDetails.get(k).getMI_N_SEALMAKE();
                    //Log.e("MI_N_SEAL_MAKE", "MI_N_SEAL_MAKE_Install: " + MI_N_SEAL_MAKE);


                    if (MI_N_SEAL_MAKE != null)
                        sealMakeDropdown();


                    MI_N_INITIALREADING = installDetails.get(k).getMI_N_INITIALREADING();

                    if (MI_N_INITIALREADING != null)
                        initialReadingEditText.setText(MI_N_INITIALREADING);

                    MI_N_METER_BOX_MAKE = installDetails.get(k).getMI_N_METERBOXMAKE();
                    if (MI_N_METER_BOX_MAKE != null)
                        meterBoxMakeDropdown();

                    //Log.e("MI_N_METER_BOX_MAKE", MI_N_METER_BOX_MAKE);

                    MI_INSTALLATIONDATE = installDetails.get(k).getMI_INSTALLATIONDATE();
                    //Log.e("MI_INSTALLATIONDATE", "MI_INSTALLATIONDATE::" + MI_INSTALLATIONDATE);
                    if (MI_INSTALLATIONDATE != null) {//COMMENT CURRENTDATE SHOW
                        et_date.setText(MI_INSTALLATIONDATE);
                    } else {
                        String currentDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa", Locale.getDefault()).format(new Date());
                        et_date.setText(currentDate);
                    }

                    MI_N_METERTYPE = installDetails.get(k).getMI_N_METERTYPE();
                    if (MI_N_METERTYPE != null)
                        meterTypeDropwdown();
                    // Log.e("MI_N_METERTYPE", MI_N_METERTYPE);

                    MI_N_METERLOCATION = installDetails.get(k).getMI_N_METERLOCATION();
                    if (MI_N_METERLOCATION != null)
                        meterLocationDropdown();

                    // Log.e("MI_N_METERLOCATION",MI_N_METERLOCATION);
                    MI_N_ISPROTECTED = installDetails.get(k).getMI_N_ISPROTECTED();
                    if (MI_N_ISPROTECTED != null)
                        protectedBoxDropdown();

                    // Log.e("MI_N_ISPROTECTED",MI_N_ISPROTECTED);
                    MI_N_ISMETERHANDOVER = installDetails.get(k).getMI_N_ISMETERHANDOVER();

                    if (MI_N_ISMETERHANDOVER != null)
                        handoverDropdown();

                    //Log.e("MI_N_ISMETERHANDOVER", MI_N_ISMETERHANDOVER);

                    MI_N_DIGIT = installDetails.get(k).getMI_N_DIGIT();

                    // Log.e("MI_N_DIGIT", MI_N_DIGIT);

                    setDialDigitSpinner(MI_N_DIGIT);

                    // dialDigitEditText.setText(MI_N_DIGIT);
                    //newMeterSetter();

                    MI_N_METEROWNERSHIP = installDetails.get(k).getMI_N_METEROWNERSHIP();
                    // Log.e("MI_N_METEROWNERSHIP", "MI_N_METEROWNERSHIP :" + MI_N_METEROWNERSHIP);
                    if (MI_N_METEROWNERSHIP != null) {
                        MeterOwnershipDropdown();
                    }

                } else {

                    String currentDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa", Locale.getDefault()).format(new Date());
                    et_date.setText(currentDate);
                    MI_O_SIZE = "";
                    MI_N_INITIALREADING = "";
                    metermakeSpinner.setSelection(0);
                    meterTypSpinner.setSelection(0);
                    locationSpinner.setSelection(0);
                    meterboxmakeSpinner.setSelection(0);

                }
            }
        }
        if (meterConnectionList != null) {

            for (int m = 0; m <= meterConnectionList.size() - 1; m++) {
                if (meterConnectionList.get(m).getLASTREADINGDATE() == null) {
                    lastReadingDate = "";
                } else {
                    lastReadingDate = meterConnectionList.get(m).getLASTREADINGDATE();
                }
                if (meterConnectionList.get(m).getMTRM_ESTIMATED_AVRG_CONSUMP() == null) {
                    newAvrageconsum = "";
                    etNewavrageconsum.setText("");
                } else {
                    etNewavrageconsum.setText(meterConnectionList.get(m).getMTRM_ESTIMATED_AVRG_CONSUMP());
                }
            }
        }
       /* if (customerDetailList != null) {

            for (int c = 0; c <= customerDetailList.size() - 1; c++) {

            }
        }*/
    }

    public void displayNewMeterDet(String mContList) {
        contList = mContList;
    }

    private void setDialDigitAdapter() {
        ArrayList<String> digitName = new ArrayList<>();
        dialDigitList = new ArrayList<>();
        digitName = realmOperations.fetchDiaDigit();
        for (int i = 0; i < digitName.size(); i++) {
            String digit = digitName.get(i);
            if (digit.equalsIgnoreCase("Select")) {
                digitName.remove(i);
            }
        }
        dialDigitList.add("--Select--");
        dialDigitList.addAll(digitName);
        dialDigitAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, dialDigitList);
        dialDigitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dialDigitSpinner.setAdapter(dialDigitAdapter);
        //dialDigitSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            sendMtrSize = (SendMtrSizeMM) getActivity();
            sendNewMeterDetails = (SendNewMeterDetails) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }

    private void saveDataToServer() {
        String mtrTypeCode = UtilitySharedPreferences.getPrefs(getActivity(), Constants.MTR_TYPE_CODE_ID);
        String oldmeterSizeNum = UtilitySharedPreferences.getPrefs(getActivity(), Constants.MTR_SIZE_ID);
        if (radiobuttonValStr.equalsIgnoreCase("OH")) {
            meterMakeStr = "";
            meterNoStr = "";
            installDtStr = et_date.getText().toString().trim();
            sizeStr = "";
            sealNoStr = "";
            initialReadingStr = "";
            meterTypeStr = "";
            meterLocationStr = "";
            protectedBoxStr = "";
            meterHandoverIdStr = "";
            sealMakeStr = "";
            meterBoxMakeStr = "";
        } else if (radiobuttonValStr.equalsIgnoreCase("N") ||
                radiobuttonValStr.equalsIgnoreCase("R") ||
                radiobuttonValStr.equalsIgnoreCase("OM")) {
            meterNoStr = meterNoEditText.getText().toString().trim();

            if (meterNoStr != "") {
                if (meterNoStr.length() < 8) {
                    meterNoStr = String.format("%08d", Integer.parseInt(meterNoStr));
                }
            }
            newAvrageconsum = etNewavrageconsum.getText().toString();
            installDtStr = et_date.getText().toString().trim();
            sealNoStr = sealNoEditText.getText().toString().trim();
            initialReadingStr = initialReadingEditText.getText().toString().trim();
        } else if (radiobuttonValStr.equalsIgnoreCase("S")) {
            sealNoStr = sealNoEditText.getText().toString().trim();
            installDtStr = et_date.getText().toString().trim();//pinky added on 28/01/2022
        }

//        String emp_code = PreferenceUtil.getUserName();
        String emp_code = null;
        try {
            emp_code = new AesAlgorithm().decrypt(PreferenceUtil.getUserName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String ip_str = PreferenceUtil.getMac();

        String params[] = new String[58];
        params[0] = "N";                            //IsSubmit
        if (isConsumerSubmitted) {
            params[1] = UtilitySharedPreferences.getPrefs(getActivity(), Constants.RADIOBUTTONVAL);             //Action
            params[2] = consumerNoStr;                 //Consumer
            params[3] = BU;
            params[4] = PC;
        } else {
            params[1] = UtilitySharedPreferences.getPrefs(getActivity(), Constants.RADIOBUTTONVAL);             //Action
            params[2] = consumerNoStr;                 //Consumer
            params[3] = MI_BU;
            params[4] = MI_PC;
        }
        params[5] = UtilitySharedPreferences.getPrefs(getActivity(), Constants.CONSUMERREFERENCENUMBER);

        if (is_old_submitted) {
            params[6] = oldmeterSizeNum;
            params[7] = serialNoStr;
            params[8] = old_maker_code_id;
            params[9] = previousReading;
            params[10] = finalReadingValStr;
            params[11] = finalStatusIdStr;
            params[12] = meterObservationId;
            params[13] = meterReasonId;
            params[14] = mtrTypeCode;
        } else {
            params[6] = MI_O_SIZE;
            params[7] = MI_O_METER;
            params[8] = MI_O_MAKE;
            params[9] = MI_O_PREVIOUSREADING;
            params[10] = MI_O_FINALREADING;
            params[11] = MI_O_FINALSTATUS;
            params[12] = MI_O_OBSERVATION;
            params[13] = MI_O_REASON;
            params[14] = MI_O_METERTYPE;
        }

        if (radiobuttonValStr.equals("OH")) {
            params[15] = "";
            params[16] = "";                     // N_Size
            params[17] = "";                    // N_Seal
            params[18] = "";                    // N_Meter
            params[19] = installDtStr;          // InstallationDate
            params[20] = "";                    // N_InitialReading
            params[21] = "";                    // N_MeterType
            params[22] = "";                    // N_MeterLocation
            params[23] = "";                    // N_IsProtected
            params[24] = property_assess;      // PropertyTaxNo
            params[25] = "";                   // N_IsMeterHandovertoConsumer
        } else {
            params[15] = makerCodeId;
            params[16] = meterSizeId;
            params[17] = sealNoStr;
            params[18] = meterNoStr;
            params[19] = installDtStr; //pinky added on 28/01/202
            //params[19] = strCurrentDate;//pinky commented on 28/01/2022
            params[20] = initialReadingStr;
            params[21] = meterTypeId;
            params[22] = meterLocationId;
            params[23] = protectedBoxIdStr;
            params[24] = property_assess;
            params[25] = meterHandoverIdStr;
        }

        if (isConsumerSubmitted)
            params[24] = property_assess;      // PropertyTaxNo
        else
            params[24] = MI_PROPERTYTAXNO;

        if (MI_CONTRACTOR == null || MI_CONTRACTOR.equalsIgnoreCase("") || MI_CONTRACTOR.equalsIgnoreCase("0")) {
            String mmgFixer = PreferenceUtil.getMMGFixer();
            params[26] = cont_id;
        } else {
            params[26] = cont_id;
        }
        params[27] = cont_emp_code;
        params[28] = MI_CONTRACTOROTHER;
        params[29] = MI_CONTRACTOREMPOTHER;
        params[30] = MI_N_ISMATERIALHANDOVER;
        params[31] = "";
        params[32] = "";
        params[33] = "";
        params[34] = "";
        params[35] = "";
        params[36] = "";
        params[37] = "";
        params[38] = "";
        params[39] = "";

        if (isConsumerSubmitted) {
            params[40] = from_node_str;
            params[41] = to_node_str;
            params[42] = primary_mob_str;
            params[43] = alternate_mob_str;
            params[44] = gis_bid_str;
            params[45] = dma_id;
            params[46] = sr_id;
        } else {
            params[40] = MI_FROMNODE;
            params[41] = MI_TONODE;
            params[42] = MI_REGMOBILE;
            params[43] = MI_ALTMOBILE;
            params[44] = MI_GIS;
            params[45] = MI_DMA;
            params[46] = MI_SR;
        }
        params[47] = commisioned_noncommisioned;
        params[48] = dial_digit;                         //N_DIGIT
        params[49] = emp_code;
        params[50] = ip_str;
        params[51] = MI_METERINSTALLID;                  // Query String
        params[52] = sealmakeId;
        params[53] = meterboxmakeId;
        params[54] = "NF";
        String mw = meterOwnershipspinner.getSelectedItem().toString().trim();
        if (mw.equalsIgnoreCase("Government")) {
            meterownvalue = "1";
        } else if (mw.equalsIgnoreCase("Private")) {
            meterownvalue = "2";
        } else {
            meterownvalue = "0";
        }
        params[55] = meterownvalue;
        params[56] = "ok";
        params[57] = newAvrageconsum;

        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.MI_N_METEROWNERSHIP);
        UtilitySharedPreferences.setPrefs(getActivity(), Constants.MI_N_METEROWNERSHIP, meterownvalue);

        //Log.e("NewMeterParams", Arrays.toString(params));

        SendDataToMeterInstallation sendDataToMeterInstallation = new SendDataToMeterInstallation();
        sendDataToMeterInstallation.execute(params);
    }

    @SuppressLint("StaticFieldLeak")
    private class SendDataToMeterInstallation extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            meterInstallationProgress = new MaterialDialog.Builder(mCon)
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
                String paraName[] = new String[58];
                paraName[0] = "IsSubmit";
                paraName[1] = "Action";
                paraName[2] = "Consumer";
                paraName[3] = "BU";
                paraName[4] = "PC";
                paraName[5] = "RefNo";
                paraName[6] = "O_Size";
                paraName[7] = "O_Meter";
                paraName[8] = "O_Make";
                paraName[9] = "O_PreviousReading";
                paraName[10] = "O_FinalReading";
                paraName[11] = "O_FinalStatus";
                paraName[12] = "O_StatusObservation";
                paraName[13] = "O_Reason";
                paraName[14] = "O_MeterType";
                paraName[15] = "N_Make";
                paraName[16] = "N_Size";
                paraName[17] = "N_Seal";
                paraName[18] = "N_Meter";
                paraName[19] = "InstallationDate";
                paraName[20] = "N_InitialReading";
                paraName[21] = "N_MeterType";
                paraName[22] = "N_MeterLocation";
                paraName[23] = "N_IsProtected";
                paraName[24] = "PropertyTaxNo";
                paraName[25] = "N_IsMeterHandovertoConsumer";
                paraName[26] = "Contractor";
                paraName[27] = "ContractorEmp";
                paraName[28] = "OtherContractor";
                paraName[29] = "OtherContractorEmp";
                paraName[30] = "N_IsMaterialHandovertoConsumer";
                paraName[31] = "XMLMaterial";
                paraName[32] = "XMLCivil";
                paraName[33] = "PCCBeddingLen";
                paraName[34] = "PCCBeddingWid";
                paraName[35] = "PCCBeddingDep";
                paraName[36] = "RoadCuttingType";
                paraName[37] = "RoadCuttingLen";
                paraName[38] = "RoadCuttingWid";
                paraName[39] = "RoadCuttingDep";
                paraName[40] = "FromNode";
                paraName[41] = "ToNode";
                paraName[42] = "RegMobile";
                paraName[43] = "AltMobile";
                paraName[44] = "GIS";
                paraName[45] = "DMA";
                paraName[46] = "SR";
                paraName[47] = "IsCommissioned";
                paraName[48] = "N_Digit";
                paraName[49] = "Emp_Code";
                paraName[50] = "IP";
                paraName[51] = "MeterInstallId";
                paraName[52] = "SealMake";
                paraName[53] = "MeterBoxMake";
                paraName[54] = "FragmentPosition";
                paraName[55] = "MeterOwnership";
                paraName[56] = "Remarks";
                paraName[57] = "AvgConsumption";

                jsonMeterInstallSaveResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, "MeterInstallationSave", params, paraName);
                //Log.e("NewMeterResponse", jsonMeterInstallSaveResponse);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                meterInstallationProgress.dismiss();
                String[] enums = gson.fromJson(jsonMeterInstallSaveResponse, String[].class);
                if (enums[0].equalsIgnoreCase("Success")) {
                    if (radiobuttonValStr.equalsIgnoreCase("S")) {
                        MessageWindow.throwOutMMMFragment(mCon, "Seal Details Saved Successfully", "Success", MainActivity.class);
                    } else if (radiobuttonValStr.equalsIgnoreCase("MB")) {
                        MessageWindow.throwOutMMMFragment(mCon, "Meter Box Details Saved Successfully", "Success", MainActivity.class);
                    } else {
                        MessageWindow.throwOutMMMFragment(mCon, "New Meter Details Saved Successfully", "Success", MainActivity.class);
                    }
                    clearAllSharedPrefs();
                    return;
                } else if (enums[0].equalsIgnoreCase("Failure")) {
                    android.app.AlertDialog.Builder alertBuilder = new android.app.AlertDialog.Builder(mCon);
                    alertBuilder.setCancelable(false);
                    alertBuilder.setTitle("Alert");
                    alertBuilder.setMessage("Meter Installation Process Not Completed");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    android.app.AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else if (enums[0].equalsIgnoreCase("Duplicate")) {
                    android.app.AlertDialog.Builder alertBuilder = new android.app.AlertDialog.Builder(mCon);
                    alertBuilder.setCancelable(false);
                    alertBuilder.setTitle("Alert");
                    alertBuilder.setMessage(getResources().getString(R.string.please_forward_complaint_to_mmg));
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(mCon, MainActivity.class);
                            startActivity(intent);
                            dialog.cancel();
                        }
                    });
                    android.app.AlertDialog alert = alertBuilder.create();
                    alert.show();
                }

            } catch (Exception e) {
                Log.e("Exception", e.toString());
                meterInstallationProgress.dismiss();
                MessageWindow.errorWindow(mCon,"Something went wrong");
                String error = e.toString();
                ErrorClass.errorData(mCon, "Authentication Fragment", "Send Data to meter Installation", error);
            }
        }
    }

    public void displayReceivedData(String makerCodeId, String oldmeterno, String sealNoStr, String installDtStr,
                                    String coonectionLoad, String pastMeterReadingStr,
                                    String submitStatus, String radiobuttonValStr, String consumerNoStr, String zoneStr,
                                    String groupStr, String refTypeStr, String connSizeStr, String property_assessmnt, String fromNodeStr,
                                    String toNodeStr, String primaryMobStr, String alternateMobStr, String gis_bidStr, String dmaId,
                                    String srId, String msrId, String commisioned_noncommisioned, String employee_code, String mac_address,
                                    String mContList, boolean isSubmitted) {

        zone_str = zoneStr;
        group_str = groupStr;
        from_node_str = fromNodeStr;
        to_node_str = toNodeStr;
        primary_mob_str = primaryMobStr;
        alternate_mob_str = alternateMobStr;
        gis_bid_str = gis_bidStr;
        dma_id = dmaId;
        sr_id = srId;
        property_assess = property_assessmnt;
        isConsumerSubmitted = isSubmitted;
    }

    public void displayOldMeterDetails(String oldmakerCodeId, String oldmeterNoStr, String oldinstallDtStr, String oldmeterSizeStr,
                                       String oldsealNoStr, String pastReadingStr, String oldMtrStsId, String oldmeterTypeId, String finalReadingStr,
                                       String finalStatusStr, String meter_observationId, String meter_reasonId, boolean isOldSubmitted) {

        old_maker_code_id = oldmakerCodeId;
        mtrSizeId = oldmeterSizeStr;
        serialNoStr = oldmeterNoStr;
        previousReading = pastReadingStr;
        finalReadingValStr = finalReadingStr;
        finalStatusIdStr = finalStatusStr;
        meterReasonId = meter_reasonId;
        meterObservationId = meter_observationId;
        is_old_submitted = isOldSubmitted;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_date: {

                DatePickerDialog datePickerDialog = new DatePickerDialog(mCon, fromCalendarDate, fromDateCalendar.get(Calendar.YEAR), fromDateCalendar.get(Calendar.MONTH),
                        fromDateCalendar.get(Calendar.DAY_OF_MONTH));

                String dtStart = lastReadingDate;
                Date sdate = null;
                SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
                try {
                    sdate = format.parse(dtStart);
                } catch (ParseException e) {
                    MessageWindow.errorWindow(mCon,e.getMessage());
                    e.printStackTrace();
                }

                datePickerDialog.getDatePicker().setMinDate(sdate.getTime());
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.show();
            }
            break;

            case R.id.submitButton:
                if (checkValidation()) {
                    onSubmitNewMeterFrag1();
                }
                // onSubmitNewMeterFrag();
                break;

            case R.id.saveAndExit:
                if (radiobuttonValStr.equalsIgnoreCase("R")) {
                    meterNoStr = meterNoEditText.getText().toString();
                    meterMakeStr = metermakeSpinner.getSelectedItem().toString().trim();
                    sizeStr = meterSizeSpinner.getSelectedItem().toString().trim();

                    if (meterMakeStr.equalsIgnoreCase("--Select--")) {
                        MessageWindow.messageWindow(mCon,"Please Select Meter Make","Alert");
                    } else if (meterNoStr.equalsIgnoreCase("")) {
                        MessageWindow.messageWindow(mCon,"Please Enter Meter Number","Alert");
                    } else if (sizeStr.equalsIgnoreCase("--Select--")) {
                        MessageWindow.messageWindow(mCon,"Please Enter Meter Size","Alert");
                    } else {
                        saveDataToServer();
                    }
                } else {
                    saveDataToServer();
                }
                break;

            default:
                break;
        }
    }

    private void setTimeNewMeter() {

        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR);
        mMinute = c.get(Calendar.MINUTE);
        mSec = c.get(Calendar.SECOND);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(mCon,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        //if (c.get(Calendar.AM_PM) == Calendar.AM) //pinky commented 20/01/2022
                        if (hourOfDay < 12)//pinky added 20/01/2022
                            am_pm = "AM";
                        else if (c.get(Calendar.AM_PM) == Calendar.PM)
                            am_pm = "PM";

                        strHrsToShow = (hourOfDay == 0) ? "12" : hourOfDay + "";

                        String currentDate = newSdf.format(new Date());
                        Date currDate = null, selectedDate = null;
                        try {
                            currDate = newSdf.parse(currentDate);
                            selectedDate = newSdf.parse(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (selectedDate.compareTo(currDate) < 0) {

                            time = strHrsToShow + ":" + minute + " " + am_pm;
                            dateTime = String.format("%s %s", date, time);
                            et_date.setText(dateTime);

                        } else if (selectedDate.compareTo(currDate) == 0) {
                            if (hourOfDay < (c.get(Calendar.HOUR_OF_DAY))) {
                                time = strHrsToShow + ":" + minute + " " + am_pm;
                                dateTime = String.format("%s %s", date, time);
                                et_date.setText(dateTime);
                            } else if (hourOfDay == (c.get(Calendar.HOUR_OF_DAY))) {
                                if (minute < (c.get(Calendar.MINUTE))) {
                                    time = strHrsToShow + ":" + minute + " " + am_pm;
                                    dateTime = String.format("%s %s", date, time);
                                    et_date.setText(dateTime);
                                } else {
                                    MessageWindow.messageWindow(mCon,"Future time could not select","Alert");
                                }
                            } else {
                                MessageWindow.messageWindow(mCon,"Future time could not select","Alert");
                            }
                        } else {
                            MessageWindow.messageWindow(mCon,"Future time could not select","Alert");
                        }
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    private void onSubmitNewMeterFrag1() {
        if (radiobuttonValStr.equalsIgnoreCase("OH")) {

            UtilitySharedPreferences.setPrefs(getActivity(), Constants.NEW_METER_NO, meterNoStr);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.NEW_AVERAGECONSUMTION, newAvrageconsum);


            sendNewMeterDetails.sndNewMtrDet(makerCodeId, meterNoStr, installDtStr, meterSizeId, sealNoStr,
                    initialReadingStr, meterTypeId, meterLocationId, protectedBoxIdStr, taxNoStr,
                    meterHandoverIdStr, "", dial_digit, isNewMeterSubmitted,newAvrageconsum);
            ((MMGScreenActivity) getActivity()).onClickNext(2);

            MMGScreenActivity.animationOnArrow();

            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.VALIDMETER);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.VALIDMETER, "0");

        } else if (radiobuttonValStr.equalsIgnoreCase("MB")) {
            sendNewMeterDetails.sndNewMtrDet("", "", installDtStr, "", "",
                    "", "", "", protectedBoxIdStr, "",
                    "", "", "", true,"");
            ((MMGScreenActivity) getActivity()).onClickNext(2);

            MMGScreenActivity.animationOnArrow();

        } else if (radiobuttonValStr.equalsIgnoreCase("S")) {


            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.NEW_SEALNO);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.NEW_SEALNO, sealNoStr);

            // Log.e("sealNoStr",sealNoStr+" lll");
            sendNewMeterDetails.sndNewMtrDet("", "", installDtStr, "", sealNoStr,
                    "", "", "", "", "",
                    "", "", "", true,"");

            ((MMGScreenActivity) getActivity()).onClickNext(2);

            MMGScreenActivity.animationOnArrow();
        } else {

            //UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.NEW_METER_NO);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.NEW_METER_NO, meterNoStr);

            mmgMeterSizeModel = realmOperations.fetchMeterSizeByName(sizeStr);
            meterSizeId = String.valueOf(mmgMeterSizeModel.getMCS_ID());

            sendMtrSize.sendmeterSize(sizeStr, meterSizeId);

            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.NEW_MAKERCODE);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.NEW_MAKERCODE, makerCodeId);

            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.NEW_METERNUM);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.NEW_METERNUM, meterNoStr);

            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.NEW_INSTALLDATE);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.NEW_INSTALLDATE, installDtStr);

            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.NEW_METERSIZE);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.NEW_METERSIZE, meterSizeId);

            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.NEW_SEALNO);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.NEW_SEALNO, sealNoStr);

            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.NEW_INITIALREADING);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.NEW_INITIALREADING, initialReadingStr);

            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.NEW_AVERAGECONSUMTION);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.NEW_AVERAGECONSUMTION, newAvrageconsum);


            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.NEW_METERTYPE);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.NEW_METERTYPE, meterTypeId);

            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.NEW_METERLOCATION);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.NEW_METERLOCATION, meterLocationId);

            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.NEW_PROTECTEDBOX);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.NEW_PROTECTEDBOX, protectedBoxIdStr);

            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.NEW_TAXNO);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.NEW_TAXNO, taxNoStr);

            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.MATERIALHANDOVER);
            UtilitySharedPreferences.setPrefs(getActivity(), Constants.MATERIALHANDOVER, meterHandoverIdStr);


          /*  if (pagename != null) {
                if (pagename.equalsIgnoreCase("MeterInstallationContractorDet")) {

                    getMeterContractorDetails("M");
                } else {
                    getMeterContractorDetails("M");
                }
            } else {*/
                getMeterContractorDetails("M");
           // }
        }
    }

    private boolean checkValidation() {

        if (radiobuttonValStr.equalsIgnoreCase("OH")) {
            meterMakeStr = "";
            meterNoStr = "";
            installDtStr = et_date.getText().toString().trim();
            sizeStr = "";
            sealNoStr = "";
            initialReadingStr = "";
            meterTypeStr = "";
            meterLocationStr = "";
            protectedBoxStr = "";
            meterHandoverIdStr = "";
            sealMakeStr = "";
            meterBoxMakeStr = "";
        } else if (radiobuttonValStr.equalsIgnoreCase("S")) {
            meterMakeStr = "";
            meterNoStr = "";
            installDtStr = et_date.getText().toString().trim();
            sizeStr = "";
            sealNoStr = sealNoEditText.getText().toString().trim();
            initialReadingStr = "";
            meterTypeStr = "";
            meterLocationStr = "";
            protectedBoxStr = "";
            meterHandoverStr = "";
            sealMakeStr = sealmakeSpinner.getSelectedItem().toString().trim();
            meterBoxMakeStr = "";
        } else if (radiobuttonValStr.equalsIgnoreCase("MB")) {
            meterMakeStr = "";
            meterNoStr = "";
            installDtStr = et_date.getText().toString().trim();
            sizeStr = "";
            sealNoStr = "";
            initialReadingStr = "";
            meterTypeStr = "";
            meterLocationStr = "";
            protectedBoxStr = "";
            meterHandoverStr = "";
            sealMakeStr = "";
            meterBoxMakeStr = meterboxmakeSpinner.getSelectedItem().toString().trim();
            Log.e("meterBoxMakeStr", meterBoxMakeStr);
            if (meterBoxMakeStr.equalsIgnoreCase("--Select--")) {
               // MessageWindow.messageWindow(mCon,"Please select Meter Box Make","Alert");
                return false;
            }
        } else {
            meterMakeStr = metermakeSpinner.getSelectedItem().toString().trim();
            meterNoStr = meterNoEditText.getText().toString().trim();
            try {
                if (meterNoStr.length() < 8) {
                    meterNoStr = String.format("%08d", Integer.parseInt(meterNoStr));
                }
            } catch (Exception exception) {

            }
            installDtStr = et_date.getText().toString().trim();
            newAvrageconsum = etNewavrageconsum.getText().toString().trim();
            sizeStr = meterSizeSpinner.getSelectedItem().toString().trim();
            meterown = meterOwnershipspinner.getSelectedItem().toString().trim();
            sealNoStr = sealNoEditText.getText().toString().trim();
            initialReadingStr = initialReadingEditText.getText().toString().trim();
            meterTypeStr = meterTypSpinner.getSelectedItem().toString();
            meterLocationStr = locationSpinner.getSelectedItem().toString();
            protectedBoxStr = protectedBoxSpinner.getSelectedItem().toString().trim();
            meterHandoverStr = mtrHandoverSpinner.getSelectedItem().toString().trim();
            sealMakeStr = sealmakeSpinner.getSelectedItem().toString().trim();
            meterBoxMakeStr = meterboxmakeSpinner.getSelectedItem().toString().trim();

            if (commisioned_noncommisioned.equalsIgnoreCase("N")) {
                dial_digit = dialDigitSpinner.getSelectedItem().toString().trim();
            }

            if (radiobuttonValStr.equalsIgnoreCase("S")) {
               /* if (sealMakeStr.equalsIgnoreCase("--Select--")) {
                    MessageWindow.messageWindow(mCon,"Please Select Seal Make","Alert");
                    return false;
                }

                if (sealNoStr.equalsIgnoreCase("")) {
                    MessageWindow.messageWindow(mCon,"Please Enter Seal Number","Alert");

                    return false;
                }*/
            } else if (radiobuttonValStr.equalsIgnoreCase("MB")) {
               /* if (meterBoxMakeStr.equalsIgnoreCase("--Select--")) {
                    MessageWindow.messageWindow(mCon,"Please Enter Meter Box Make","Alert");

                    return false;
                }
                if (protectedBoxStr.equalsIgnoreCase("--Select--")) {
                    MessageWindow.messageWindow(mCon,"Please select Protected with Box","Alert");

                    return false;
                }*/
            } else {
                if (meterMakeStr.equalsIgnoreCase("--Select--")) {
                   // MessageWindow.messageWindow(mCon,"Please select Meter Make","Alert");
                    return false;
                } else if (meterNoStr.equalsIgnoreCase("")) {

                    MessageWindow.messageWindow(mCon,"Please Enter Meter Number","Alert");

                    return false;
                } else if (sizeStr.equalsIgnoreCase("--Select--")) {
                    MessageWindow.messageWindow(mCon,"Please Enter Meter Size","Alert");

                    return false;
                } else if (installDtStr.equalsIgnoreCase("")) {
                    MessageWindow.messageWindow(mCon,"Please Enter Installation Date","Alert");

                    return false;
                } else if (!sealNoStr.equalsIgnoreCase("") && sealMakeStr.equalsIgnoreCase("--Select--")) {//pinky added on 19/03/2022
                    MessageWindow.messageWindow(mCon,"Please Select Seal Make","Alert");

                    return false;
                } else if (initialReadingStr.equalsIgnoreCase("")) {
                    MessageWindow.messageWindow(mCon,"Please Enter Initial Reading","Alert");
                    return false;
                } else if (meterTypeStr.equalsIgnoreCase("--Select--")) {
                    MessageWindow.messageWindow(mCon,"Please Select Meter Type","Alert");

                    return false;
                } else if (meterLocationStr.equalsIgnoreCase("--Select--")) {
                    MessageWindow.messageWindow(mCon,"Please select Location of Meter","Alert");

                    return false;
                }
              /*  else if (protectedBoxStr.equalsIgnoreCase("--Select--")) {
                    MessageWindow.messageWindow(mCon,"Please select Protected with Box","Alert");

                    return false;
                }
                else if (protectedBoxStr.equalsIgnoreCase("Yes") && meterBoxMakeStr.equalsIgnoreCase("--Select--")) {//pinky added on 19/03/2022
                    MessageWindow.messageWindow(mCon,"Please select Meter Box Make","Alert");

                    return false;
                }*/
                /*else if (meterHandoverStr.equalsIgnoreCase("-- Select --")) {
                    Toast.makeText(mCon, "Please select Removed Meter Handed Over To Consumer", Toast.LENGTH_SHORT).show();
                    return false;
                }*/
                else if (radiobuttonValStr.equalsIgnoreCase("OH")
                        && meterHandoverStr.equalsIgnoreCase("-- Select --")) {
                    MessageWindow.messageWindow(mCon,"Please select Removed Meter Handed over to Consumer","Alert");

                    return false;
                } else if (!radiobuttonValStr.equalsIgnoreCase("N")) {
                    if (meterHandoverStr.equalsIgnoreCase("-- Select --")) {
                        MessageWindow.messageWindow(mCon,"Please select Removed Meter Handed Over To Consumer","Alert");

                        return false;
                    }
                }
                if (meterown.equalsIgnoreCase("--Select--")) {
                    MessageWindow.messageWindow(mCon,"Please select Meter Ownership","Alert");

                    return false;
                }

                if (dial_digit.equalsIgnoreCase("--Select--")) {
                    MessageWindow.messageWindow(mCon,"Please select Dial Digit","Alert");

                    return false;
                }
            }
        }
        return true;
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

    String date = "";

    private void updateFromDateCalendar() {
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        date = sdf.format(fromDateCalendar.getTime());

        setTimeNewMeter();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.dialDigitSpinner:

                meterNoData();

                String digits = dialDigitSpinner.getSelectedItem().toString();

                if (digits.equalsIgnoreCase("--Select--")) {
                    // initialReadingEditText.setText("");
                } else {
                    // initialReadingEditText.setText("");
                    InputFilter[] FilterArray = new InputFilter[1];
                    FilterArray[0] = new InputFilter.LengthFilter(Integer.parseInt(digits));
                    initialReadingEditText.setFilters(FilterArray);
                    dial_digit = digits;
                }

                break;

            case R.id.meterSizeSpinner:

                meterNoData();

                String meterSizeName = meterSizeSpinner.getSelectedItem().toString();
                String makerCodeName1 = metermakeSpinner.getSelectedItem().toString();
                if (!makerCodeName1.equalsIgnoreCase("--Select--")) {
                    mmgMakerCodeModel = realmOperations.fetchMakerCodeByName(makerCodeName1);
                    makerCodeId = String.valueOf(mmgMakerCodeModel.getMMFG_MFGCODE());
                }

                if (pagename != null) {
                    if (meterSizeName.equalsIgnoreCase("--Select--")) {
                        // dialDigitEditText.setText("");
                        dialDigitSpinner.setSelection(0);

                    } else {
                        mmgMeterSizeModel = realmOperations.fetchMeterSizeByName(meterSizeName);
                        meterSizeId = String.valueOf(mmgMeterSizeModel.getMCS_ID());
                        meterSizeIdStr = String.valueOf(meterSizeId);
                        meterNoStr = meterNoEditText.getText().toString();

                        if (!radiobuttonValStr.equalsIgnoreCase("MB")) {
                            if (meterNoStr.equalsIgnoreCase("") ||
                                    makerCodeId.equalsIgnoreCase("") ||
                                    meterSizeIdStr.equalsIgnoreCase("0")) {
                                if (meterMakeStr.equalsIgnoreCase("--Select--")) {
                                    MessageWindow.messageWindow(mCon,"Please select Meter Make","Alert");

                                }
                                if (meterNoStr.equalsIgnoreCase("")) {
                                    MessageWindow.messageWindow(mCon,"Please Enter Meter Number","Alert");

                                    meterSizeSpinner.setSelection(0);
                                }
                            } else {
                                if (MI_N_METER == null || MI_N_SIZE == null || MI_N_METER == "" || MI_N_SIZE == "") {
                                    if (!App.backPress.equalsIgnoreCase("Y") || !App.backPressMaterialFragment.equalsIgnoreCase("Y")) {
                                        getDialDigit();
                                    }
                                    App.backPress = "N";
                                    App.backPressMaterialFragment = "N";
                                } else {
                                    if (!App.backPress.equalsIgnoreCase("Y") || !App.backPressMaterialFragment.equalsIgnoreCase("Y")) {
                                        getDialDigit();
                                    }
                                    App.backPress = "N";
                                    App.backPressMaterialFragment = "N";
                                }
                            }
                        }
                    }

                } else {
                    if (meterSizeName.equalsIgnoreCase("--Select--")) {
                        //dialDigitEditText.setText("");
                        dialDigitSpinner.setSelection(0);
                    } else {
                        mmgMeterSizeModel = realmOperations.fetchMeterSizeByName(meterSizeName);
                        meterSizeId = String.valueOf(mmgMeterSizeModel.getMCS_ID());
                        meterSizeIdStr = String.valueOf(meterSizeId);

                        meterNoStr = meterNoEditText.getText().toString();

                        if (!meterNoStr.equalsIgnoreCase("") && !makerCodeId.equalsIgnoreCase("")
                                && !meterSizeIdStr.equalsIgnoreCase("0")) {
                            if (!App.backPressMaterialFragment.equalsIgnoreCase("Y")) {
                                getDialDigit();

                            }
                            App.backPressMaterialFragment = "N";
                            //   getDialDigit();
                        }

                        if (!radiobuttonValStr.equalsIgnoreCase("MB")) {
                            if (meterNoStr.equalsIgnoreCase("") ||
                                    makerCodeId.equalsIgnoreCase("")
                                    || meterSizeIdStr.equalsIgnoreCase("0")) {
                                if (meterMakeStr.equalsIgnoreCase("--Select--")) {
                                    MessageWindow.messageWindow(mCon,"Please Select Meter Make","Alert");

                                }
                                if (meterNoStr.equalsIgnoreCase("")) {
                                    MessageWindow.messageWindow(mCon,"Please Enter Meter Number","Alert");

                                }
                            }
                        }
                    }
                }
                break;

            case R.id.metermakeSpinner:

                meterNoData();

                String makerCodeName = metermakeSpinner.getSelectedItem().toString();

                if (dataFound.equalsIgnoreCase("DataFound")) {
                    if (MI_N_SIZE.equalsIgnoreCase("")) {
                        meterSizeSpinner.setSelection(0);
                    }//else condition commented by pinky on 09/03/2022
                    /*else {
                        meterSizeSpinner.setSelection(Integer.parseInt(
                                MI_N_SIZE));
                    }*/
                }
                if (makerCodeName.equalsIgnoreCase("--Select--")) {
                    //meterNoEditText.setEnabled(true);
                    meterSizeSpinner.setSelection(0);

                } else {
                    if (pagename != null) {
                        if (pagename.equalsIgnoreCase("MeterInstallationContractorDet")) {
                            mmgMakerCodeModel = realmOperations.fetchMakerCodeByName(makerCodeName);
                            makerCodeId = String.valueOf(mmgMakerCodeModel.getMMFG_MFGCODE());

                            meterMakeIdStr = String.valueOf(makerCodeId);
                            //meterMakeDropdown();
                        } else {
                            mmgMakerCodeModel = realmOperations.fetchMakerCodeByName(makerCodeName);
                            makerCodeId = String.valueOf(mmgMakerCodeModel.getMMFG_MFGCODE());

                            meterMakeIdStr = String.valueOf(makerCodeId);
                        }
                    } else {
                        mmgMakerCodeModel = realmOperations.fetchMakerCodeByName(makerCodeName);
                        makerCodeId = String.valueOf(mmgMakerCodeModel.getMMFG_MFGCODE());

                        meterMakeIdStr = String.valueOf(makerCodeId);
                    }
                }
                break;

            case R.id.sealmakeSpinner:

                meterNoData();

                String sealCodeName = sealmakeSpinner.getSelectedItem().toString();

                if (sealCodeName.equalsIgnoreCase("--Select--")) {

                    sealmakeSpinner.setSelection(0);
                    sealmakeId = "";//pinky added on 11/03/2022

                } else {
                    //pinky added this if condition on 19/03/2022
                    if (sealNoEditText.getText().toString().equalsIgnoreCase("")) {
                        MessageWindow.messageWindow(mCon,"Please Enter Seal Number","Alert");

                        sealmakeSpinner.setSelection(0);
                        sealmakeId = "";
                    } else {
                        if (pagename != null) {
                            if (pagename.equalsIgnoreCase("MeterInstallationContractorDet")) {
                                mmgMakerCodeModel = realmOperations.fetchMakerCodeByName(sealCodeName);
                                sealmakeId = String.valueOf(mmgMakerCodeModel.getMMFG_MFGCODE());

                                sealMakeStr = String.valueOf(sealmakeId);
                                if (!sealNoEditText.getText().toString().isEmpty())
                                    getSealContractorDetails(sealmakeId);

                                // Log.e("sealMakeId1",sealmakeId);
                            } else {
                                mmgMakerCodeModel = realmOperations.fetchMakerCodeByName(sealCodeName);
                                sealmakeId = String.valueOf(mmgMakerCodeModel.getMMFG_MFGCODE());

                                sealMakeStr = String.valueOf(sealmakeId);
                                if (!sealNoEditText.getText().toString().isEmpty())
                                    getSealContractorDetails(sealmakeId);
                                //Log.e("sealMakeId2",sealmakeId);

                            }
                        } else {
                            mmgMakerCodeModel = realmOperations.fetchMakerCodeByName(sealCodeName);
                            sealmakeId = String.valueOf(mmgMakerCodeModel.getMMFG_MFGCODE());

                            sealMakeStr = String.valueOf(sealmakeId);
                            if (!sealNoEditText.getText().toString().isEmpty())
                                getSealContractorDetails(sealmakeId);

                            //Log.e("sealMakeId3",sealmakeId);
                        }
                    }
                }

                break;

            case R.id.meterBoxMakeSpinner:
                meterNoData();

                String meterboxCodeName = meterboxmakeSpinner.getSelectedItem().toString();

                if (meterboxCodeName.equalsIgnoreCase("--Select--")) {
                    // meterNoEditText.setEnabled(true);
                    meterboxmakeSpinner.setSelection(0);
                    meterboxmakeId = "";
                } else {
                    if (pagename != null) {
                        if (pagename.equalsIgnoreCase("MeterInstallationContractorDet")) {
                            mmgMakerCodeModel = realmOperations.fetchMakerCodeByName(meterboxCodeName);
                            meterboxmakeId = String.valueOf(mmgMakerCodeModel.getMMFG_MFGCODE());

                            meterBoxMakeStr = String.valueOf(meterboxmakeId);
                            //meterMakeDropdown();
                        } else {
                            mmgMakerCodeModel = realmOperations.fetchMakerCodeByName(meterboxCodeName);
                            meterboxmakeId = String.valueOf(mmgMakerCodeModel.getMMFG_MFGCODE());

                            meterBoxMakeStr = String.valueOf(meterboxmakeId);
                        }
                    } else {
                        mmgMakerCodeModel = realmOperations.fetchMakerCodeByName(meterboxCodeName);
                        meterboxmakeId = String.valueOf(mmgMakerCodeModel.getMMFG_MFGCODE());
                        meterBoxMakeStr = String.valueOf(meterboxmakeId);
                    }
                    if (radiobuttonValStr.equalsIgnoreCase("R")) {
                        getMeterBoxBalanceDetails(UtilitySharedPreferences.getPrefs(getActivity(), Constants.CONTACTORNAME),
                                UtilitySharedPreferences.getPrefs(getActivity(), Constants.CONTACTOREMP), "A");
                    }
                }

                break;

            case R.id.meterTypSpinner:
                meterNoData();

                String meterTypeName = meterTypSpinner.getSelectedItem().toString();
                if (pagename != null) {
                    if (pagename.equalsIgnoreCase("MeterInstallationContractorDet")) {
                        if (!meterTypeName.equalsIgnoreCase("--Select--")) {
                            mmgMeterTypeModel = realmOperations.fetchMeterTypeByName(meterTypeName);
                            meterTypeId = String.valueOf(mmgMeterTypeModel.getMTC_METERTYPE_CODE());
                            meterTypeIdStr = String.valueOf(meterTypeId);
                        }
                        // meterTypeDropwdown();
                    } else {
                        if (!meterTypeName.equalsIgnoreCase("--Select--")) {
                            mmgMeterTypeModel = realmOperations.fetchMeterTypeByName(meterTypeName);
                            meterTypeId = String.valueOf(mmgMeterTypeModel.getMTC_METERTYPE_CODE());
                            meterTypeIdStr = String.valueOf(meterTypeId);
                        }
                        //  meterTypeDropwdown();
                    }
                } else {
                    if (!meterTypeName.equalsIgnoreCase("--Select--")) {
                        mmgMeterTypeModel = realmOperations.fetchMeterTypeByName(meterTypeName);
                        meterTypeId = String.valueOf(mmgMeterTypeModel.getMTC_METERTYPE_CODE());
                        meterTypeIdStr = String.valueOf(meterTypeId);
                    }
                }

                break;

            case R.id.locationSpinner:
                meterNoData();

                String locationName = locationSpinner.getSelectedItem().toString();

                if (pagename != null) {
                    if (pagename.equalsIgnoreCase("MeterInstallationContractorDet")) {

                        if (!MI_N_METERLOCATION.equalsIgnoreCase("") && !MI_N_METERLOCATION.equalsIgnoreCase("0")) {
                            locationSpinner.setSelection(Integer.parseInt(MI_N_METERLOCATION));
                        }


                    } else {
                        if (!locationName.equalsIgnoreCase("--Select--")) {
                            mmgMeterLocationModel = realmOperations.fetchMtrLocationByName(locationName);
                            meterLocationId = String.valueOf(mmgMeterLocationModel.getML_ID());
                            meterLocationIdStr = String.valueOf(meterLocationId);

                        }
                    }
                } else {
                    if (!locationName.equalsIgnoreCase("--Select--")) {
                        mmgMeterLocationModel = realmOperations.fetchMtrLocationByName(locationName);
                        meterLocationId = String.valueOf(mmgMeterLocationModel.getML_ID());
                        meterLocationIdStr = String.valueOf(meterLocationId);
                    } else {
                        if (MI_N_METERLOCATION != null && !MI_N_METERLOCATION.equalsIgnoreCase("")) {
                            locationSpinner.setSelection(Integer.parseInt(MI_N_METERLOCATION));
                        }

                    }
                }
                break;

            case R.id.protectedBoxSpinner:

                meterNoData();

                String protectedBoxName = protectedBoxSpinner.getSelectedItem().toString();
                if (!protectedBoxName.equalsIgnoreCase("--Select--")) {
                    if (pagename != null) {
                        if (pagename.equalsIgnoreCase("MeterInstallationContractorDet")) {
                            //protectedBoxDropdown();
                            if (protectedBoxName.equalsIgnoreCase("Yes")) {
                                protectedBoxIdStr = "1";
                                meterboxmakeSpinner.setEnabled(true);
                            } else if (protectedBoxName.equalsIgnoreCase("No")) {
                                protectedBoxIdStr = "2";
                                meterboxmakeSpinner.setSelection(0);
                                meterboxmakeId = "";
                                meterboxmakeSpinner.setEnabled(false);
                            }
                        } else {
                            if (protectedBoxName.equalsIgnoreCase("Yes")) {
                                protectedBoxIdStr = "1";
                                meterboxmakeSpinner.setEnabled(true);
                            } else if (protectedBoxName.equalsIgnoreCase("No")) {
                                protectedBoxIdStr = "2";
                                meterboxmakeSpinner.setSelection(0);
                                meterboxmakeId = "";
                                meterboxmakeSpinner.setEnabled(false);
                            }
                        }
                    } else {
                        if (protectedBoxName.equalsIgnoreCase("Yes")) {
                            protectedBoxIdStr = "1";
                            meterboxmakeSpinner.setEnabled(true);
                        } else if (protectedBoxName.equalsIgnoreCase("No")) {
                            protectedBoxIdStr = "2";
                            meterboxmakeSpinner.setSelection(0);
                            meterboxmakeId = "";
                            meterboxmakeSpinner.setEnabled(false);
                        }
                    }
                }
                break;
            case R.id.mtrHandoverSpinner:

                meterNoData();

                String meterHandoverName = mtrHandoverSpinner.getSelectedItem().toString();
                if (!meterHandoverName.equalsIgnoreCase("-- Select --")) {
                    if (pagename != null) {
                        if (pagename.equalsIgnoreCase("MeterInstallationContractorDet")) {
                            if (meterHandoverName.equalsIgnoreCase("Yes")) {
                                meterHandoverIdStr = "1";
                            } else if (meterHandoverName.equalsIgnoreCase("No")) {
                                meterHandoverIdStr = "2";
                            }
                            //  handoverDropdown();
                        } else {
                            if (meterHandoverName.equalsIgnoreCase("Yes")) {
                                meterHandoverIdStr = "1";
                            } else if (meterHandoverName.equalsIgnoreCase("No")) {
                                meterHandoverIdStr = "2";
                            }
                        }
                    } else {
                        if (meterHandoverName.equalsIgnoreCase("Yes")) {
                            meterHandoverIdStr = "1";
                        } else if (meterHandoverName.equalsIgnoreCase("No")) {
                            meterHandoverIdStr = "2";
                        }
                    }
                }
                break;
            case R.id.meterOwnership:

                meterNoData();

                String meterown = meterOwnershipspinner.getSelectedItem().toString();

                if (meterown.equalsIgnoreCase("--Select--")) {
                    meterOwnershipspinner.setSelection(0);
                    UtilitySharedPreferences.setPrefs(getActivity(), Constants.MI_N_METEROWNERSHIP, "0");
                } else {
                    if (meterown.equalsIgnoreCase("Government")) {
                        meterown = "1";
                    } else if (meterown.equalsIgnoreCase("Private")) {
                        meterown = "2";
                    }
                    UtilitySharedPreferences.setPrefs(getActivity(), Constants.MI_N_METEROWNERSHIP, meterown);
                }

                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public interface SendMtrSizeMM {
        void sendmeterSize(String sizeStr, String sizeId);
    }

    public interface SendNewMeterDetails {
        void sndNewMtrDet(String makerCodeId, String meterNoStr, String installDtStr, String meterSizeId, String sealNoStr
                , String initialReadingStr, String meterTypeId, String meterLocationId, String protectedBoxIdStr,
                          String taxNoStr, String meterHandoverIdStr, String contList, String dial_digit, boolean isNewMeterSubmitted,String avrageconsumtion);
    }

    @SuppressLint("StaticFieldLeak")
    private class Get_MeterContractorDetailTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                String paraName[] = new String[5];
                paraName[0] = "Material";
                paraName[1] = "MakeId";
                paraName[2] = "SizeId";
                paraName[3] = "Meter";
                paraName[4] = "MeterInstallId";

                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.MMG_GetMeterContractorDetails, params, paraName);

                //Log.e("meterDetailsResponse", jsonResponse);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                //pinky commented these 3 line
                // UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.VALIDMETER);
                //UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.CONTACTORNAME);
                //UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.CONTACTOREMP);

                MMGContractorResponseDetail[] mmgContractorResponseDetails = gson.fromJson(jsonResponse, MMGContractorResponseDetail[].class);
                if (jsonResponse.equalsIgnoreCase("[]")) {
                    if (commisioned_noncommisioned.equalsIgnoreCase("N")) {
                        isNewMeterSubmitted = true;
                        UtilitySharedPreferences.setPrefs(getActivity(), Constants.NEW_METER_NO, meterNoStr);

                        sendNewMeterDetails.sndNewMtrDet(makerCodeId, meterNoStr, installDtStr, meterSizeId, sealNoStr,
                                initialReadingStr, meterTypeId, meterLocationId, protectedBoxIdStr, taxNoStr,
                                meterHandoverIdStr, "", dial_digit, isNewMeterSubmitted,newAvrageconsum);

                        ((MMGScreenActivity) getActivity()).onClickNext(2);
                        MMGScreenActivity.animationOnArrow();

                    } else if (commisioned_noncommisioned.equalsIgnoreCase("Y")) {
                        MessageWindow.msgWindow(getActivity(), "This Meter is not available at fixer");
                    }
                }
                if (mmgContractorResponseDetails != null) {
                    meter_status = mmgContractorResponseDetails[0].getMETERSTATUS();

                    if (commisioned_noncommisioned.equalsIgnoreCase("N")) {
                        dial_digit = dial_digit;

                    } else {
                        dial_digit = mmgContractorResponseDetails[0].getMDT_TOTAL_DIGITS();
                    }

                    if (dial_digit != null) {
                        setDialDigitSpinner(dial_digit);
                        // dialDigitEditText.setText(dial_digit);
                        setDialDigitSpinner(MI_N_DIGIT);
                    } else {
                        // dialDigitEditText.setText("");
                        dialDigitSpinner.setSelection(0);
                    }

                    InputFilter[] FilterArray = new InputFilter[1];
                    FilterArray[0] = new InputFilter.LengthFilter(Integer.parseInt(dial_digit));
                    initialReadingEditText.setFilters(FilterArray);
                    if (commisioned_noncommisioned.equalsIgnoreCase("Y")) {
                        if (meter_status.equals("ValidMeter")) {
                            saveAndExit.setEnabled(true);

                            cont_id = mmgContractorResponseDetails[0].getM_CONT_CODE();
                            cont_emp_code = mmgContractorResponseDetails[0].getM_CONT_EMP_CODE();

                            UtilitySharedPreferences.setPrefs(getActivity(), Constants.CONTACTORNAME, cont_id);
                            UtilitySharedPreferences.setPrefs(getActivity(), Constants.CONTACTOREMP, cont_emp_code);

                            valid_meter = meter_status;

                            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.VALIDMETER);
                            UtilitySharedPreferences.setPrefs(getActivity(), Constants.VALIDMETER, valid_meter);

                            UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.METERSTATUS);
                            UtilitySharedPreferences.setPrefs(getActivity(), Constants.METERSTATUS, meter_status);

                            if (commisioned_noncommisioned.equalsIgnoreCase("Y")) {
                                isNewMeterSubmitted = true;
                                UtilitySharedPreferences.setPrefs(getActivity(), Constants.NEW_METER_NO, meterNoStr);

                                sendNewMeterDetails.sndNewMtrDet(makerCodeId, meterNoStr, installDtStr, meterSizeId, sealNoStr,
                                        initialReadingStr, meterTypeId, meterLocationId, protectedBoxIdStr, taxNoStr,
                                        meterHandoverIdStr, contList, dial_digit, isNewMeterSubmitted,newAvrageconsum);

                                ((MMGScreenActivity) getActivity()).onClickNext(2);

                                MMGScreenActivity.animationOnArrow();

                            } else if (commisioned_noncommisioned.equalsIgnoreCase("N")) {
                                MessageWindow.msgWindow(getContext(), getResources().getString(R.string.meter_available_in_our_system));
                                meterNoEditText.setText("");
                                meterSizeSpinner.setSelection(0);
                            }
                        }
                    else {

                        MessageWindow.messageWindow(mCon, getResources().getString(R.string.details_not_submitted_this_fixer_unavailable), "Alert");//added by sonali
                        //pinky added this on 09/03/2022
                        meterNoEditText.setText("");
                        meterSizeSpinner.setSelection(0);
                    }}else{

                        MessageWindow.messageWindow(mCon, getResources().getString(R.string.This_meter_Was_already_assigned_with), "Alert");//added by sonali
                        //pinky added this on 09/03/2022
                        meterNoEditText.setText("");
                        meterSizeSpinner.setSelection(0);
                    }

                } else {
                    if (commisioned_noncommisioned.equalsIgnoreCase("N")) {
                        isNewMeterSubmitted = true;
                        UtilitySharedPreferences.setPrefs(getActivity(), Constants.NEW_METER_NO, meterNoStr);

                        sendNewMeterDetails.sndNewMtrDet(makerCodeId, meterNoStr, installDtStr, meterSizeId, sealNoStr,
                                initialReadingStr, meterTypeId, meterLocationId, protectedBoxIdStr, taxNoStr,
                                meterHandoverIdStr, contList, dial_digit, isNewMeterSubmitted,newAvrageconsum);

                        ((MMGScreenActivity) getActivity()).onClickNext(2);

                        MMGScreenActivity.animationOnArrow();
                    } else if (commisioned_noncommisioned.equalsIgnoreCase("Y")) {
                        MessageWindow.messageWindow(mCon, getResources().getString(R.string.details_not_submitted), "Alert");

                    }
                }
            } catch (Exception e) {
                Log.e("Exception", e.toString());
                String error = e.toString();
                ErrorClass.errorData(mCon, "New Meter Details Fragment", "Get Meter Contractor details task", error);
            }
        }
    }

    private void setDialDigitSpinner(String dial_digit) {
        String compareValue = dial_digit;
        dialDigitSpinner.setAdapter(dialDigitAdapter);
        if (compareValue != null) {
            int spinnerPosition = dialDigitAdapter.getPosition(compareValue);
            dialDigitSpinner.setSelection(spinnerPosition);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class Get_MeterSealDetailTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            seal_progress = new MaterialDialog.Builder(mCon)
                    .content(R.string.loading)
                    .progress(true, 0)
                    .cancelable(true)
                    .canceledOnTouchOutside(true)
                    .widgetColorRes(R.color.colorPrimary)
                    .show();
            Log.d("PreExecute", "onPreExecute: ");
        }

        @Override
        protected Void doInBackground(String... params) {
            seal_progress.dismiss();

            try {
                String paraName[] = new String[5];
                paraName[0] = "Material";
                paraName[1] = "MakeId";
                paraName[2] = "SizeId";
                paraName[3] = "Meter";
                paraName[4] = "MeterInstallId";

                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.MMG_GetMeterContractorDetails, params, paraName);

                Log.e("SealDetails", jsonResponse);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            seal_progress.dismiss();

            try {
                MMGContractorResponseDetail[] mmgContractorResponseDetails = gson.fromJson(jsonResponse, MMGContractorResponseDetail[].class);
                if (jsonResponse.equalsIgnoreCase("[]")) {

                    MessageWindow.msgWindow(getActivity(), "This Seal is not available at fixer");
                    sealmakeSpinner.setSelection(0);
                    sealNoEditText.setText("");
                    sealmakeId = "";//pinky added on 11/03/2022

                }
                if (mmgContractorResponseDetails != null) {

                    String checkMeterStr = meterNoEditText.getText().toString();

                    seal_status = mmgContractorResponseDetails[0].getSEALSTATUS();

                    if (seal_status.equals("ValidSeal")) {
                        if (!checkMeterStr.equalsIgnoreCase("")
                                && meter_status.equalsIgnoreCase("ValidMeter")) {
                            String seal_cont_id = mmgContractorResponseDetails[0].getM_CONT_CODE();
                            String seal_cont_emp_code = mmgContractorResponseDetails[0].getM_CONT_EMP_CODE();

                            if (seal_cont_id.equalsIgnoreCase(cont_id) && seal_cont_emp_code.equalsIgnoreCase(cont_emp_code)) {
                                UtilitySharedPreferences.setPrefs(getActivity(), Constants.METERSTATUS, seal_status);
                            } else {
                                MessageWindow.messageWindow(mCon, "Meter Contractor and Seal Contractor do not match", "Alert");
                                sealmakeSpinner.setSelection(0);
                                sealmakeId = "";
                                sealNoEditText.setText("");
                                sealNoStr = "";
                            }
                        } else {
                            String seal_cont_id = mmgContractorResponseDetails[0].getM_CONT_CODE();
                            String seal_cont_emp_code = mmgContractorResponseDetails[0].getM_CONT_EMP_CODE();
                            UtilitySharedPreferences.setPrefs(getActivity(), Constants.CONTACTORNAME, seal_cont_id);
                            UtilitySharedPreferences.setPrefs(getActivity(), Constants.CONTACTOREMP, seal_cont_emp_code);
                        }
                        saveAndExit.setEnabled(true);
                    } else {
                        seal_progress.dismiss();
                        MessageWindow.messageWindow(mCon, getResources().getString(R.string.details_not_submitted), "Alert");
                        //below 4 line added by pinky 09/03/2022
                        sealmakeSpinner.setSelection(0);
                        sealmakeId = "";
                        sealNoEditText.setText("");
                        sealNoStr = "";

                    }
                }
            } catch (Exception e) {
                Log.e("Exception", e.toString());
                String error = e.toString();
                seal_progress.dismiss();
            }
        }
    }

    public void getMeterContractorDetails(String type) {
        meterNoStr = meterNoEditText.getText().toString();

        String params[] = new String[5];
        params[0] = "M";
        params[1] = makerCodeId;
        params[2] = meterSizeId;
        params[3] = meterNoStr;
        params[4] = MI_METERINSTALLID;

        //Log.e("MeterParams", Arrays.toString(params));

        if (connection.isConnectingToInternet()) {
            Get_MeterContractorDetailTask get_meterContractorDetailTask = new Get_MeterContractorDetailTask();
            get_meterContractorDetailTask.execute(params);
        } else {
            MessageWindow.messageWindow(mCon,getResources().getString(R.string.no_internet_connection),"Alert");
        }
    }

    public void getSealContractorDetails(String sealCodeId) {
        sealNoStr = sealNoEditText.getText().toString();

        String params[] = new String[5];
        params[0] = "S";
        params[1] = sealCodeId;
        params[2] = "";
        params[3] = sealNoStr;
        params[4] = MI_METERINSTALLID;
        // Log.e("sealDetails", Arrays.toString(params));

        if (connection.isConnectingToInternet()) {
            Get_MeterSealDetailTask get_meterSealDetailTask = new Get_MeterSealDetailTask();
            get_meterSealDetailTask.execute(params);
        } else {
            MessageWindow.messageWindow(mCon,getResources().getString(R.string.no_internet_connection),"Alert");

        }
    }

    @SuppressLint("StaticFieldLeak")
    private class Get_DialDigit extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            digit_progress = new MaterialDialog.Builder(mCon)
                    .content(R.string.loading)
                    .progress(true, 0)
                    .cancelable(true)
                    .canceledOnTouchOutside(true)
                    .widgetColorRes(R.color.colorPrimary)
                    .show();
            Log.d("PreExecute", "onPreExecute: ");
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                String paraName[] = new String[5];
                paraName[0] = "Material";
                paraName[1] = "MakeId";
                paraName[2] = "SizeId";
                paraName[3] = "Meter";
                paraName[4] = "MeterInstallId";

                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.MMG_GetMeterContractorDetails, params, paraName);
                digit_progress.dismiss();
                // Log.e("digitResponse", jsonResponse);
            } catch (Exception e) {
                digit_progress.dismiss();
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.VALIDMETER);
                UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.CONTACTORNAME);
                UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.CONTACTOREMP);

                MMGContractorResponseDetail[] mmgContractorResponseDetails = gson.fromJson(jsonResponse, MMGContractorResponseDetail[].class);
                digit_progress.dismiss();
                if (jsonResponse.equalsIgnoreCase("[]")) {
                    if (commisioned_noncommisioned.equalsIgnoreCase("N")) {
                        saveAndExit.setEnabled(true);

                    } else {
                        if (pagename.equalsIgnoreCase("MeterInstallationEntry")) {
                            MessageWindow.msgWindow(getActivity(), "This meter is not available");
                            meterNoEditText.setText("");
                            meterSizeSpinner.setSelection(0);
                        }
                    }
                }
                if (mmgContractorResponseDetails != null) {
                    meter_status = mmgContractorResponseDetails[0].getMETERSTATUS();
                    dial_digit = mmgContractorResponseDetails[0].getMDT_TOTAL_DIGITS();
                    cont_id = mmgContractorResponseDetails[0].getM_CONT_CODE();
                    cont_emp_code = mmgContractorResponseDetails[0].getM_CONT_EMP_CODE();

                    UtilitySharedPreferences.setPrefs(getActivity(), Constants.CONTACTORNAME, cont_id);
                    UtilitySharedPreferences.setPrefs(getActivity(), Constants.CONTACTOREMP, cont_emp_code);
                    UtilitySharedPreferences.setPrefs(getActivity(), Constants.METERSTATUS, meter_status);

                    if (meter_status.equalsIgnoreCase("ValidMeter")) {
                        //clear seal fields
                        // sealmakeSpinner.setSelection(0);//pinky
                        // sealmakeId = "";//pinky
                        //sealNoEditText.setText(""); //pinky
                        //  sealNoStr = ""; //pinky

                        if (commisioned_noncommisioned.equalsIgnoreCase("N")) {
                            saveAndExit.setEnabled(false);
                            MessageWindow.msgWindow(getContext(), "This Meter is available in our system, you cannot use this");
                            meterNoEditText.setText("");
                            meterSizeSpinner.setSelection(0);
                            metermakeSpinner.setSelection(0);
                        } else if (commisioned_noncommisioned.equalsIgnoreCase("Y")) {
                            saveAndExit.setEnabled(true);

                        }
                    } else if (meter_status.equalsIgnoreCase("")) {
                        //pinky added this on 09/03/2022
                        getMeterContractorDetails("M");
                    }
                    if (dial_digit != null) {
                        //   dialDigitEditText.setText(dial_digit);
                        setDialDigitSpinner(dial_digit);
                        meterNoEditText.setEnabled(true);
                    } else {
                        // dialDigitEditText.setText("");
                        dialDigitSpinner.setSelection(0);
                    }

                    InputFilter[] FilterArray = new InputFilter[1];
                    FilterArray[0] = new InputFilter.LengthFilter(Integer.parseInt(dial_digit));
                    initialReadingEditText.setFilters(FilterArray);

                }

            } catch (Exception e) {
                digit_progress.cancel();
                Log.e("Exception", e.toString());
                String error = e.toString();
                ErrorClass.errorData(mCon, "New Meter Details Fragment", "Get Dial digit task", error);
            }
            digit_progress.dismiss();
        }
    }

    private void disableNewMeterFields() {
        if (commisioned_noncommisioned.equalsIgnoreCase("Y")) {
            dialDigitLayout.setVisibility(View.VISIBLE);
//            dialDigitEditText.setText("");
        } else if (commisioned_noncommisioned.equalsIgnoreCase("N")) {
            dialDigitLayout.setVisibility(View.VISIBLE);

            dialDigitEditText.setVisibility(View.GONE);
            dial_digit_showtext.setVisibility(View.GONE);
        }

        if (radiobuttonValStr.equalsIgnoreCase("OH")) {
            metermakeSpinner.setEnabled(false);
            meterNoEditText.setEnabled(false);
            et_date.setEnabled(false);
            ll_date.setEnabled(false);
            meterSizeSpinner.setEnabled(false);
            sealNoEditText.setEnabled(false);
            initialReadingEditText.setEnabled(false);
            meterTypSpinner.setEnabled(false);
            locationSpinner.setEnabled(false);
            liner_protectedBox.setEnabled(false);
            protectedBoxSpinner.setEnabled(false);
            mtrHandoverSpinner.setEnabled(false);
            dialDigitSpinner.setEnabled(false);
            meterboxmakeSpinner.setEnabled(false);
            sealmakeSpinner.setEnabled(false);
        }

        if (radiobuttonValStr.equalsIgnoreCase("N")) {
            mtrHandoverSpinner.setEnabled(false);
        }

        if (radiobuttonValStr.equalsIgnoreCase("OH")) {
            backFragment(mCon, "You can directly go to fixer details screen", "Alert");
            //MessageWindow.messageAuthenticationWindow(mCon, "You Can Directly Go to Authentication Page","Alert");
        }
    }

    private void meterMakeDropdown() {
        ArrayList<String> meterMakeName = new ArrayList<>();
        ArrayList<Float> meterMakeType = new ArrayList<>();

        meterMakeName = realmOperations.fetchMakerCode();
        meterMakeType = realmOperations.fetchMakerType();

        ArrayList<String> meterMakeList = new ArrayList<>();
        meterMakeList.add("--Select--");

        for (int i = 0; i < meterMakeType.size() - 1; i++) {
            if (meterMakeType.get(i) == 1.0) {
                meterMakeList.add(meterMakeName.get(i));
            }
        }

        ArrayAdapter metermakeAdapter;
        if (MI_N_MAKE != null && !(MI_N_MAKE.equalsIgnoreCase("0")) && !(MI_N_MAKE.equalsIgnoreCase(""))) {
            try {
                mmgMakerCodeModel = realmOperations.fetchMakerCodeId(MI_N_MAKE);
                String makerType = mmgMakerCodeModel.getMMFG_MFGNAME();

                int makerId = meterMakeList.indexOf(makerType);

                metermakeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, meterMakeList);
                metermakeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                metermakeSpinner.setAdapter(metermakeAdapter);
                metermakeSpinner.setSelection(makerId);
            } catch (Exception ex) {
                ex.printStackTrace();
                metermakeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, meterMakeList);
                metermakeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                metermakeSpinner.setAdapter(metermakeAdapter);
            }
        } else {
            metermakeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, meterMakeList);
            metermakeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            metermakeSpinner.setAdapter(metermakeAdapter);
            metermakeSpinner.setSelection(0);
            metermakeSpinner.setOnItemSelectedListener(this);
        }
    }

    private void sealMakeDropdown() {
        ArrayList<String> sealMakeName = new ArrayList<>();
        ArrayList<Float> sealMakeType = new ArrayList<>();

        sealMakeName = realmOperations.fetchMakerCode();
        sealMakeType = realmOperations.fetchMakerType();

        ArrayList<String> sealMakeList = new ArrayList<>();
        sealMakeList.add("--Select--");

        for (int i = 0; i < sealMakeType.size() - 1; i++) {
            if (sealMakeType.get(i) == 2.0) {
                sealMakeList.add(sealMakeName.get(i));
            }
        }

        ArrayAdapter sealMakeAdapter;
        if (MI_N_SEAL_MAKE != null && !(MI_N_SEAL_MAKE.equalsIgnoreCase("0"))
                && !(MI_N_SEAL_MAKE.equalsIgnoreCase(""))) {
            try {
                mmgMakerCodeModel = realmOperations.fetchMakerCodeId(MI_N_SEAL_MAKE);
                String makerType = mmgMakerCodeModel.getMMFG_MFGNAME();

                int makerId = sealMakeList.indexOf(makerType);
                // Log.e("sealPos", String.valueOf(makerId));
                sealMakeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, sealMakeList);
                sealMakeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sealmakeSpinner.setAdapter(sealMakeAdapter);
                if (makerId == 1) {
                    sealmakeSpinner.setSelection(1);
                } else if (makerId == 2) {
                    sealmakeSpinner.setSelection(2);
                } else {
                    sealmakeSpinner.setSelection(0);

                }

            } catch (Exception ex) {
                ex.printStackTrace();
                sealMakeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, sealMakeList);
                sealMakeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sealmakeSpinner.setAdapter(sealMakeAdapter);

            }
        } else {
            sealMakeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, sealMakeList);
            sealMakeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sealmakeSpinner.setAdapter(sealMakeAdapter);
        }
    }

    private void meterBoxMakeDropdown() {
        ArrayList<String> meterBoxMakeName = new ArrayList<>();
        ArrayList<Float> meterBoxMakeType = new ArrayList<>();

        meterBoxMakeName = realmOperations.fetchMakerCode();
        meterBoxMakeType = realmOperations.fetchMakerType();

        ArrayList<String> meterBoxMakeList = new ArrayList<>();
        meterBoxMakeList.add("--Select--");

        for (int i = 0; i < meterBoxMakeType.size() - 1; i++) {
            if (meterBoxMakeType.get(i) == 3.0) {
                meterBoxMakeList.add(meterBoxMakeName.get(i));
            }
        }

        ArrayAdapter meterBoxMakeAdapter;
        if (MI_N_METER_BOX_MAKE != null
                && !(MI_N_METER_BOX_MAKE.equalsIgnoreCase("0"))
                && !(MI_N_METER_BOX_MAKE.equalsIgnoreCase(""))) {
            try {
                mmgMakerCodeModel = realmOperations.fetchMakerCodeId(MI_N_METER_BOX_MAKE);
                String makerType = mmgMakerCodeModel.getMMFG_MFGNAME();

                int makerId = meterBoxMakeList.indexOf(makerType);

                // Log.e("meterBoxPos", String.valueOf(makerId));

                meterBoxMakeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, meterBoxMakeList);
                meterBoxMakeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                meterboxmakeSpinner.setAdapter(meterBoxMakeAdapter);
                meterboxmakeSpinner.setSelection(makerId);
//              metermakeSpinner.setOnItemSelectedListener(this);
            } catch (Exception ex) {
                ex.printStackTrace();
               /* meterBoxMakeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, meterBoxMakeList);
                meterBoxMakeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                meterboxmakeSpinner.setAdapter(meterBoxMakeAdapter);*/
//              metermakeSpinner.setOnItemSelectedListener(this);
            }
        } else {
            meterBoxMakeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, meterBoxMakeList);
            meterBoxMakeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            meterboxmakeSpinner.setAdapter(meterBoxMakeAdapter);
//          metermakeSpinner.setOnItemSelectedListener(this);
        }
    }

    private void meterSizeDropdown() {
        ArrayList<String> meterSizeName = new ArrayList<>();
        meterSizeName = realmOperations.fetchMeterSize();
        ArrayList<String> meterSizeList = new ArrayList<>();
        meterSizeList.add("--Select--");
        meterSizeList.addAll(meterSizeName);

        try {
            ArrayAdapter meterSizeAdapetr;
            if (MI_N_SIZE != null && !(MI_N_SIZE.equalsIgnoreCase("")) && !MI_N_SIZE.equalsIgnoreCase("0")) {
                mmgMeterSizeModel = realmOperations.fetchMeterSize(Integer.parseInt(MI_N_SIZE));
                String meterSize = mmgMeterSizeModel.getCONNSIZEMM();
                int meterSizeId = meterSizeList.indexOf(meterSize);
                // Log.e("meterSizeId", String.valueOf(meterSizeId));

                meterSizeAdapetr = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, meterSizeList);
                meterSizeAdapetr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                meterSizeSpinner.setAdapter(meterSizeAdapetr);
                meterSizeSpinner.setSelection(meterSizeId);
                //meterSizeSpinner.setOnItemSelectedListener(this);
            } else {
                meterSizeAdapetr = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, meterSizeList);
                meterSizeAdapetr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                meterSizeSpinner.setAdapter(meterSizeAdapetr);
                meterSizeSpinner.setSelection(0);//pinky added on 19/01/2022
                //meterSizeSpinner.setOnItemSelectedListener(this);
            }

        } catch (Exception ex) {

        }
    }

    private void meterTypeDropwdown() {
        ArrayList<String> meterTypeName = new ArrayList<>();
        meterTypeName = realmOperations.fetchMeterType();
        ArrayList<String> meterTypeList = new ArrayList<>();
        meterTypeList.add("--Select--");
        meterTypeList.addAll(meterTypeName);

        ArrayAdapter meterTypeAdapter;
        if (MI_N_METERTYPE != null && !(MI_N_METERTYPE.equalsIgnoreCase(""))) {
            if (MI_N_METERTYPE.equalsIgnoreCase("0")) {
                meterTypeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, meterTypeList);
                meterTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                meterTypSpinner.setAdapter(meterTypeAdapter);

                // meterTypSpinner.setOnItemSelectedListener(this);
            } else {
                mmgMeterTypeModel = realmOperations.fetchMeterTypeId(MI_N_METERTYPE);
                String meterType = mmgMeterTypeModel.getMTC_TYPEDESC();
                int meterMakeId = meterTypeList.indexOf(meterType);

                meterTypeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, meterTypeList);
                meterTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                meterTypSpinner.setAdapter(meterTypeAdapter);
                meterTypSpinner.setSelection(meterMakeId);
            }
        } else {
            meterTypeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, meterTypeList);
            meterTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            meterTypSpinner.setAdapter(meterTypeAdapter);

            // meterTypSpinner.setOnItemSelectedListener(this);
        }
    }

    private void meterLocationDropdown() {
        ArrayList<String> meterLocationName = new ArrayList<>();
        meterLocationName = realmOperations.fetchMtrLocation();
        ArrayList<String> meterLocationList = new ArrayList<>();
        meterLocationList.add("--Select--");
        meterLocationList.addAll(meterLocationName);

//        if(pagename != null){
//            if(pagename.equalsIgnoreCase("MeterInstallationContractorDet")) {

        ArrayAdapter meterLocationAdapter;
        if (MI_N_METERLOCATION != null && !(MI_N_METERLOCATION.equalsIgnoreCase(""))) {
            if (MI_N_METERLOCATION.equalsIgnoreCase("0")) {
                meterLocationAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, meterLocationList);
                meterLocationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                locationSpinner.setAdapter(meterLocationAdapter);
                //  locationSpinner.setOnItemSelectedListener(this);
            } else {
                mmgMeterLocationModel = realmOperations.fetchMeterLocationId(Integer.parseInt(MI_N_METERLOCATION));
                String meterLocation = mmgMeterLocationModel.getML_DESC();

                int meterLocationId = meterLocationList.indexOf(meterLocation);
                meterLocationAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, meterLocationList);
                meterLocationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                locationSpinner.setAdapter(meterLocationAdapter);
                locationSpinner.setSelection(meterLocationId);
            }
        } else {
            meterLocationAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, meterLocationList);
            meterLocationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            locationSpinner.setAdapter(meterLocationAdapter);
            //   locationSpinner.setOnItemSelectedListener(this);
        }

    }

    private void protectedBoxDropdown() {
//        if(pagename != null) {
//            if (pagename.equalsIgnoreCase("MeterInstallationContractorDet")) {
        if (MI_N_ISPROTECTED != null) {
            if (MI_N_ISPROTECTED.equalsIgnoreCase("1")) {
                protectedBoxSpinner.setSelection(1);
            } else if (MI_N_ISPROTECTED.equalsIgnoreCase("2")) {
                protectedBoxSpinner.setSelection(2);
            } else {
                protectedBoxSpinner.setSelection(0);
            }
        }
        protectedBoxSpinner.setOnItemSelectedListener(this);
    }

    private void handoverDropdown() {
//        if(pagename.equalsIgnoreCase("MeterInstallationContractorDet")){
        if (MI_N_ISMETERHANDOVER != null) {
            if (MI_N_ISMETERHANDOVER.equalsIgnoreCase("1")) {
                mtrHandoverSpinner.setSelection(1);
            } else if (MI_N_ISMETERHANDOVER.equalsIgnoreCase("2")) {
                mtrHandoverSpinner.setSelection(2);
            } else {
                mtrHandoverSpinner.setSelection(0);
            }
        }
        mtrHandoverSpinner.setOnItemSelectedListener(this);
    }

    private void getDialDigit() {
        String params[] = new String[5];
        params[0] = "M";
        params[1] = makerCodeId;  //1 Bylan
        params[2] = meterSizeId; //15 mm
        params[3] = meterNoStr;//00001056
        params[4] = MI_METERINSTALLID; //5462 like that

        if (connection.isConnectingToInternet()) {
            Get_DialDigit get_dialDigit = new Get_DialDigit();
            get_dialDigit.execute(params);
        } else {
            MessageWindow.messageWindow(mCon,getResources().getString(R.string.no_internet_connection),"Alert");

        }
    }

    public void backFragment(Context context, String msg, String title) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(title)
                    .setCancelable(false)
                    .setMessage(msg);

            builder.setNegativeButton(context.getString(R.string.cancel),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            App.backPress = "Y";
                            ((MMGScreenActivity) context).onClickPrev();

                        }
                    });
            builder.setPositiveButton(context.getString(R.string.ok),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ((MMGScreenActivity) context).skipNewMeterDetails();

                            dialog.dismiss();
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearAllSharedPrefs() {
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.DATAFOUND);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.METERSIZEID);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.OLD_METER_NO);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.NEW_METER_NO);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.CONSUMER_NAME);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.CONSUMER_NO);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.CONSUMER_SOURCE);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.BU);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.PC);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.CONSUMERREFERENCENUMBER);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.CONTACTORNAME);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.CONTACTOREMP);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.VENDORCODE);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.MATERIALHANDOVER);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.NEW_MAKERCODE);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.NEW_METERNUM);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.OLD_INSTALLDATE);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.O_MANUFACTURE_CODE);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.NEW_INSTALLDATE);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.NEW_METERSIZE);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.NEW_SEALNO);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.NEW_INITIALREADING);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.NEW_METERTYPE);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.NEW_METERLOCATION);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.NEW_PROTECTEDBOX);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.NEW_TAXNO);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.PCCLEN);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.PCCWIDTH);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.PCCDEPTH);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.PCCTOTAL);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.RDCUTTINGID);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.RDCUTTINGLENGTH);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.RDCUTTINGWIDTH);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.RDCUTTINGDEPTH);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.RDCUTTINGTOTAL);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.MATERIALXML);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.CIVILMEASUREMENTXML);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.OLD_MAKERCODE);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.OLD_METERNUM);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.OLD_INSTALLDT);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.OLD_METERSIZE);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.OLDSEALNUM);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.PASTMETERNO);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.OLDMTRSTS);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.OLDMETERTYPE);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.FINALREADING);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.FINALSTATUS);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.REASONID);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.RADIOBUTTONVAL);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.METERSTATUS);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.CONNECTIONLOAD);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.SUBMITMATERIALBUTTONTAG);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.SUBMITCVLMEASUREMENTBUTTONTAG);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.SUBMITCIVILLIST);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.MAKERCODENAME);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.METERTYPENAME);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.COMMISIONED_NONCOMMISIONED);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.PROPERTY_ASSESSMENT);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.FROM_NODE);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.TO_NODE);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.PRIMARY_MOBILE);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.ALTERNATE_MOBILE);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.GIS_BID);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.SUBMIT_STATUS);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.MTR_SIZE_ID);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.ALLOCATED_WORK_LIST);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.MTR_TYPE_CODE_ID);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.VALIDMETER);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.CONTLIST);
        UtilitySharedPreferences.clearPrefKey(getActivity(), Constants.MI_METERINSTALLID);
    }

    @Override
    public void setUserVisibleHint(boolean isFragmentVisible_) {
        super.setUserVisibleHint(true);

        if (this.isVisible()) {
            if (isFragmentVisible_) {
                new NewMeterDetFragment();
                radiobuttonValStr = UtilitySharedPreferences.getPrefs(getActivity(), Constants.RADIOBUTTONVAL);
                //fetchDetailsFromContractor();
            }
        }
    }

    public void getMeterBoxBalanceDetails(String c_id, String c_emp_id, String radiobuttonValStr) {

        String params[] = new String[3];
        params[0] = c_id;
        params[1] = c_emp_id;
        params[2] = radiobuttonValStr;

        //Log.e("params", Arrays.toString(params));

        meterBalanceProgressDialog = new MaterialDialog.Builder(mCon)
                .content(R.string.loading)
                .progress(true, 0)
                .cancelable(false)
                .canceledOnTouchOutside(true)
                .widgetColorRes(R.color.colorPrimary)
                .show();

        if (connection.isConnectingToInternet()) {
            Get_MeterBoxBalance get_meterBoxBalance = new Get_MeterBoxBalance();
            get_meterBoxBalance.execute(params);
        } else {
            MessageWindow.messageWindow(mCon,getResources().getString(R.string.no_internet_connection),"Alert");

        }
    }

    @SuppressLint("StaticFieldLeak")
    private class Get_MeterBoxBalance extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                String paraName[] = new String[3];
                paraName[0] = "Cont_code";
                paraName[1] = "Emp";
                paraName[2] = "MaterialType";

                jsonResponseMeterBalance = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.getBalContractorQty, params, paraName);
                Log.e("meterBoxBalanceDetails", "meterBoxBalanceDetails: " + jsonResponseMeterBalance);

                meterBalanceProgressDialog.dismiss();
            } catch (Exception e) {
                meterBalanceProgressDialog.dismiss();
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                if (jsonResponseMeterBalance != null) {
                    JSONObject object = new JSONObject(jsonResponseMeterBalance);
                    JSONArray jsonArray = new JSONArray(object.getString("ContractorMeterQuantity"));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        if (jsonObject.has("METERBOX")) {
                            if (Float.parseFloat(jsonObject.getString("METERBOX")) == 0.0) {
                                MessageWindow.messageWindow(mCon, getResources().getString(R.string.meterbox_balance), "Alert");
                                meterboxmakeSpinner.setSelection(0);
                                meterboxmakeId = "";
                            }
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void meterNoData(){
        String finalStr="";
        if(!meterNoEditText.getText().toString().isEmpty()&&meterNoEditText.getText().toString().length()<8){
            int count=8-meterNoEditText.getText().toString().length();
            for(int i=0;i<count;i++){
                finalStr=finalStr+"0";
            }
        }
        meterNoEditText.setText(finalStr+meterNoEditText.getText());

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
        realmOperations.close();
        ((App) mCon.getApplicationContext()).startActivityTransitionTimer();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCon = null;
    }

}
