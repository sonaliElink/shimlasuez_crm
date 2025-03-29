package elink.suezShimla.water.crm.ConnectionManagement;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.ConnectionManagement.activity.SiteVisitDashboardActivity;
import elink.suezShimla.water.crm.ConnectionManagement.activity.SiteVisitListActivity;
import elink.suezShimla.water.crm.ConnectionManagement.model.NSCMasterModel;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.androidhsc.hscModel.MasterDataForAndroidHSCModel;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.AreaModel;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.ConnCategoryModel;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.ConnPurposeModel;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.DwellingUnitModel;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.LotModel;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.PropertyTypeModel;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.RejectModel;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.RoadOwnershipModel;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.RoadRestorationLenRoadModel;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.RoadTypeModel;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.SizeModel;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.WardModel;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;
import elink.suezShimla.water.crm.constant.AppConstant;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConnectionManagementFragment extends Fragment implements View.OnClickListener {

    List<DwellingUnitModel> list;

    private Context mCon;
    private RecyclerView zoneWardRecyclerView;
    private RelativeLayout viewAllRelativeLayout;
    private LinearLayout ll_nc_registration, ll_nc_status, ll_nc_appointment_fix, ll_nc_site_vist_list;
    TextView tv_nc_registration, tv_nc_status, tvPhotoInstallation;

    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    MaterialDialog hscMasterProgress;
    RealmOperations realmOperations;
    String jsonResponseAndroidHSC = "";


    AreaModel areaModel;
    ConnCategoryModel connCategoryModel;
    ConnPurposeModel connPurposeModel;
    DwellingUnitModel dwellingUnitModel;
    LotModel lotModel;
    PropertyTypeModel propertyTypeModel;
    RoadOwnershipModel roadOwnershipModel;
    RoadRestorationLenRoadModel roadRestorationLenRoadModel;
    RoadTypeModel roadTypeModel;
    SizeModel sizeModel;
    WardModel wardModel;

    AppCompatRadioButton rbLeft, rbRight, rbCenter;
    LinearLayout ll_nc_schedule_visit, ll_nc_visit_completed, ll_nc_visit_pending;
    Intent intent;
    TextView tvNSCVisitSechdule, tvNSCVisitCompleted, tvNSCVisitPending;
    String empMasterDataCode = "",sessionid="", Tag = "1", jsonMasterResponse = "", visitPendingStr = "", visitCompleted = "", scheduleVisit = "";
    String visitPendingMonthStr = "", visitMonthCompleted = "", visitPendingYearStr = "", visitYearCompleted = "";

    private MaterialDialog progress;
    boolean today = true, month = false, year = false;
    String formattedDate = "", startDateStr = "", endDateStr = "", startYearDateStr = "", endYearDateStr = "", fromDate = "", toDate = "";
    String STARTTIME = "", ALERTSTARTTIME = "";
    String rtimem = "Reading can be taken between";
    boolean submitData = false;
    String ENDTIME = "", ALERTENDTIME = "", time = "";

    private KeyGenerator keyGenerator;
    private SecretKey secretKey;
    private byte[] IV = new byte[16];
    private SecureRandom random;
    AesAlgorithm aes;

    public ConnectionManagementFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // prevent ss and hide content when app is on background
       // getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_connection_management, container, false);
        mCon = getActivity();

        realmOperations = new RealmOperations(mCon);

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

        init(view);
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

    private void init(View view) {
        ll_nc_registration = view.findViewById(R.id.ll_nc_registration);
        ll_nc_status = view.findViewById(R.id.ll_nc_status);
        ll_nc_appointment_fix = view.findViewById(R.id.ll_nc_appointment_fix);
        ll_nc_site_vist_list = view.findViewById(R.id.ll_nc_site_vist_list);

        tv_nc_registration = view.findViewById(R.id.tv_nc_registration);
        tv_nc_registration.setSelected(true);
        tv_nc_status = view.findViewById(R.id.tv_nc_status);
        tv_nc_status.setSelected(true);
        tvPhotoInstallation = view.findViewById(R.id.tvPhotoInstallation);
        // tvPhotoInstallation.setSelected(true);

        ll_nc_site_vist_list.setOnClickListener(this);
        ll_nc_registration.setOnClickListener(this);
        ll_nc_status.setOnClickListener(this);
        ll_nc_appointment_fix.setOnClickListener(this);

        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();


        areaModel = realmOperations.getAreaModel();
        connCategoryModel = realmOperations.getConnCategory();
        connPurposeModel = realmOperations.getConnPurpose();
        dwellingUnitModel = realmOperations.getDwellingUnit();

        lotModel = realmOperations.getLot();
        propertyTypeModel = realmOperations.getPropertyType();
        roadOwnershipModel = realmOperations.getRoadOwnership();
        roadRestorationLenRoadModel = realmOperations.getRoadRestorationLenRoad();
        roadTypeModel = realmOperations.getRoadTypeModel();
        sizeModel = realmOperations.getSizeModel();
        wardModel = realmOperations.getWardModel();

       if (areaModel == null && connCategoryModel == null && connPurposeModel == null
                && dwellingUnitModel == null && lotModel == null
                && propertyTypeModel == null && roadOwnershipModel == null && roadRestorationLenRoadModel == null && roadTypeModel == null
                && sizeModel == null) {
            if (connection.isConnectingToInternet()) {
                GetMasterDataForAndroid_HSC();

            } else {
                Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_LONG).show();

            }
        }


        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();

        rbLeft = view.findViewById(R.id.rbLeft);
        rbRight = view.findViewById(R.id.rbRight);
        rbCenter = view.findViewById(R.id.rbCenter);
        rbLeft.setOnClickListener(this);
        rbRight.setOnClickListener(this);
        rbCenter.setOnClickListener(this);


        ll_nc_schedule_visit = view.findViewById(R.id.ll_nc_schedule_visit);
        ll_nc_visit_completed = view.findViewById(R.id.ll_nc_visit_completed);
        ll_nc_visit_pending = view.findViewById(R.id.ll_nc_visit_pending);

        tvNSCVisitPending = view.findViewById(R.id.tvNSCVisitPending);
        tvNSCVisitCompleted = view.findViewById(R.id.tvNSCVisitCompleted);
        tvNSCVisitSechdule = view.findViewById(R.id.tvNSCVisitSechdule);

        ll_nc_schedule_visit.setOnClickListener(this);
        //  ll_nc_visit_completed.setOnClickListener(this);
        ll_nc_visit_pending.setOnClickListener(this);
        empMasterDataCode = UtilitySharedPreferences.getPrefs(mCon, AppConstant.EMPCODE);
        try {
            empMasterDataCode = aes.decrypt( empMasterDataCode);
            sessionid=new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon,AppConstant.SID));

        } catch (Exception e) {
            e.printStackTrace();
        }
        getMasterDataCount();

        currentDate();

    }

    private void currentDate() {
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date monthFirstDay = calendar.getTime();
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date monthLastDay = calendar.getTime();
        // Create first day of year
        Calendar firstDayOfCurrentYear = Calendar.getInstance();
        firstDayOfCurrentYear.set(Calendar.DATE, 1);
        firstDayOfCurrentYear.set(Calendar.MONTH, 0);
        // Create last day of year

        Calendar lastDayOfCurrentYear = Calendar.getInstance();
        lastDayOfCurrentYear.set(Calendar.DATE, 31);
        lastDayOfCurrentYear.set(Calendar.MONTH, 11);
        Calendar c = Calendar.getInstance();


        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);


        formattedDate = df.format(c.getTime());
        startDateStr = df.format(monthFirstDay);
        endDateStr = df.format(monthLastDay);
        startYearDateStr = df.format(firstDayOfCurrentYear.getTime());
        endYearDateStr = df.format(lastDayOfCurrentYear.getTime());

    }

    private void getMasterDataCount() {
        String[] params = new String[3];

        params[0] = empMasterDataCode;
        params[1] = Tag;
        params[2]=sessionid;
        if (connection.isConnectingToInternet()) {
            MasterCount masterCount = new MasterCount();
            masterCount.execute(params);
        } else {
            Toast.makeText(mCon, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }

    }

    private class MasterCount extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            progress = new MaterialDialog.Builder(mCon)
                    .content(R.string.please_wait)
                    .progress(true, 0)
                    .cancelable(false)
                    .canceledOnTouchOutside(false)
                    .widgetColorRes(R.color.colorPrimary)
                    .show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {

                String username=null,password=null;
                username=new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon,AppConstant.EMPCODE));
                password=new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon,AppConstant.PASSWORD));

                String paraName[] = new String[3];
                paraName[0] = "LoginUser";
                paraName[1] = "Tag";
                paraName[2] = "SessionToken";

                jsonMasterResponse = invServices.getOtherData(Constants.URL, Constants.NameSpace, Constants.NSCDASHBOARD,username,password, params, paraName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                NSCMasterModel[] enums = gson.fromJson(jsonMasterResponse, NSCMasterModel[].class);

                if (enums.length > 0) {

                    visitPendingStr = enums[0].getPENDING_TODAY();
                    visitCompleted = enums[0].getCOMPLETED_TODAY();

                    visitPendingMonthStr = enums[0].getPENDING_MONTH();
                    visitMonthCompleted = enums[0].getCOMPLETED_MONTH();

                    visitPendingYearStr = enums[0].getPENDING_YEAR();
                    visitYearCompleted = enums[0].getCOMPLETED_YEAR();

                    tvNSCVisitPending.setText(String.valueOf(visitPendingStr));
                    tvNSCVisitCompleted.setText(String.valueOf(visitCompleted));


                }
                progress.dismiss();
            } catch (Exception e) {
                Log.d("check", e.getMessage());
                //  Toast.makeText(mCon, e.getMessage(), Toast.LENGTH_SHORT).show();
                // error = e.toString();
                String error = e.toString();
                ErrorClass.errorData(mCon, "LoginActivity", "Click Login Button", error);
            }
            progress.dismiss();
        }
    }


   /* public void onRadioButtonClickk(View v){
        boolean isSelected = ((AppCompatRadioButton)v).isChecked();
        switch (v.getId()){
            case R.id.rbLeft:
                if(isSelected){
                    rbLeft.setTextColor(getResources().getColor(R.color.white));
                    rbRight.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    rbCenter.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    // getMasterDataCount();
                    today=true;
                    year=false;
                    month=false;
                    tvNSCVisitPending.setText(String.valueOf(visitPendingStr));
                    tvNSCVisitCompleted.setText(String.valueOf(visitCompleted));

                }
                break;
            case R.id.rbCenter:
                if(isSelected){
                    rbLeft.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    rbRight.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    rbCenter.setTextColor(getResources().getColor(R.color.white));
                    // getMasterDataCount();
                    month=true;
                    today=false;
                    year=false;
                    tvNSCVisitPending.setText(String.valueOf(visitPendingMonthStr));
                    tvNSCVisitCompleted.setText(String.valueOf(visitMonthCompleted));
                    // month=false;

                }
                break;
            case R.id.rbRight:
                if(isSelected){
                    rbRight.setTextColor(getResources().getColor(R.color.white));
                    rbLeft.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    rbCenter.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    //  getMasterDataCount();
                    year=true;
                    today=false;
                    month=false;
                    tvNSCVisitPending.setText(String.valueOf(visitPendingYearStr));
                    tvNSCVisitCompleted.setText(String.valueOf(visitYearCompleted));
                    // year=false;


                }
                break;
        }
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.rbLeft:
                if (rbLeft.isChecked()) {
                    rbLeft.setTextColor(getActivity().getResources().getColor(R.color.white));
                    rbRight.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                    rbCenter.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                    // getMasterDataCount();
                    today = true;
                    year = false;
                    month = false;
                    tvNSCVisitPending.setText(String.valueOf(visitPendingStr));
                    tvNSCVisitCompleted.setText(String.valueOf(visitCompleted));

                }
                break;
            case R.id.rbCenter:
                if (rbCenter.isChecked()) {
                    rbLeft.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));

                    rbRight.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                    rbCenter.setTextColor(getActivity().getResources().getColor(R.color.white));
                    // getMasterDataCount();
                    month = true;
                    today = false;
                    year = false;
                    tvNSCVisitPending.setText(String.valueOf(visitPendingMonthStr));
                    tvNSCVisitCompleted.setText(String.valueOf(visitMonthCompleted));
                    // month=false;

                }
                break;
            case R.id.rbRight:
                if (rbRight.isChecked()) {
                    rbRight.setTextColor(getActivity().getResources().getColor(R.color.white));
                    rbLeft.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                    rbCenter.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                    //  getMasterDataCount();
                    year = true;
                    today = false;
                    month = false;
                    tvNSCVisitPending.setText(String.valueOf(visitPendingYearStr));
                    tvNSCVisitCompleted.setText(String.valueOf(visitYearCompleted));
                    // year=false;


                }
                break;

            case R.id.ll_nc_schedule_visit:
                if (today) {
                    fromDate = formattedDate;
                    toDate = formattedDate;
                } else if (month) {
                    fromDate = startDateStr;
                    toDate = endDateStr;
                } else if (year) {
                    fromDate = startYearDateStr;
                    toDate = endYearDateStr;
                }

                intent = new Intent(mCon, SiteVisitListActivity.class);
                intent.putExtra("fromDate", fromDate);
                intent.putExtra("toDate", toDate);

                startActivity(intent);
                getActivity().finish();
                break;


            case R.id.ll_nc_visit_pending:
                if (today) {
                    fromDate = formattedDate;
                    toDate = formattedDate;
                } else if (month) {
                    fromDate = startDateStr;
                    toDate = endDateStr;
                } else if (year) {
                    fromDate = startYearDateStr;
                    toDate = endYearDateStr;
                }

                intent = new Intent(mCon, SiteVisitListActivity.class);
                intent.putExtra("fromDate", fromDate);
                intent.putExtra("toDate", toDate);

                startActivity(intent);
                getActivity().finish();
                break;

            case R.id.ll_nc_registration: {

            }
            break;
            case R.id.ll_nc_site_vist_list: {
                //Intent intent = new Intent(mCon, SiteVisitListActivity.class);
                Intent intent = new Intent(mCon, SiteVisitDashboardActivity.class);
                startActivity(intent);
            }
            break;

            default:
                break;
        }
    }

    private void GetMasterDataForAndroid_HSC() {
        MasterDataForAndroidHSC masterDataForAndroidHSC = new MasterDataForAndroidHSC();
        masterDataForAndroidHSC.execute();
    }


    private class MasterDataForAndroidHSC extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            hscMasterProgress = new MaterialDialog.Builder(mCon)
                    .content(R.string.loading)
                    .progress(true, 0)
                    .widgetColorRes(R.color.colorPrimary)
                    .canceledOnTouchOutside(false)
                    .cancelable(false)
                    .show();
        }

        @Override
        protected Void doInBackground(String... voids) {
            try {
                jsonResponseAndroidHSC = invServices.getDataWOParams(Constants.URL, Constants.NameSpace, Constants.GetMasterDataForAndroid_HSC);

                Log.e("dwellingResponse",jsonResponseAndroidHSC);
                // jsonResponseAndroidHSC = invServices.getDataWOParams(Constants.URL, Constants.NameSpace, "MeterObservations");
            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            hscMasterProgress.show();
            try {
                MasterDataForAndroidHSCModel dataForAndroidHSC = gson.fromJson(jsonResponseAndroidHSC, MasterDataForAndroidHSCModel.class);

                if (dataForAndroidHSC != null) {
                    realmOperations.deleteConnCategoryTable();
                    for (ConnCategoryModel connCategoryModel : dataForAndroidHSC.getConnCategoryModelList()) {
                        ConnCategoryModel categoryModel = new ConnCategoryModel(connCategoryModel.getCATEGORY_ID(), connCategoryModel.getCATEGORY_NAME());
                        realmOperations.addConnCategory(categoryModel);
                    }

                    realmOperations.deletePropertyTypeTable();
                    for (PropertyTypeModel propertyTypeModel : dataForAndroidHSC.getPropertyTypeModels()) {
                        PropertyTypeModel propertyTypeModel1 = new PropertyTypeModel(propertyTypeModel.getPRM_ID(), propertyTypeModel.getPRM_NAME(), propertyTypeModel.getPRM_TARIFF());
                        realmOperations.addPropertyType(propertyTypeModel1);
                    }


                    realmOperations.deleteConnPurposeTable();
                    for (ConnPurposeModel connPurposeModel : dataForAndroidHSC.getConnPurposeModels()) {
                        ConnPurposeModel connPurposeModel1 = new ConnPurposeModel(connPurposeModel.getMCT_ID(), connPurposeModel.getMCT_CONNTYPE_NAME());
                        realmOperations.addConnPurpose(connPurposeModel1);
                    }

                    realmOperations.deleteDwellingUnitTable();

                    list=new ArrayList<>();

                    for (DwellingUnitModel dwellingUnitModel : dataForAndroidHSC.getDwellingUnitModels()) {
                        list.add(dwellingUnitModel);
                    }

                    Collections.sort(list, new Comparator<DwellingUnitModel>() {
                        @Override
                        public int compare(DwellingUnitModel lhs, DwellingUnitModel rhs) {
                            return Integer.parseInt(lhs.getID())-Integer.parseInt(rhs.getID());
                        }
                    });

                    for (DwellingUnitModel dwellingUnitModel : list) {

                        DwellingUnitModel dwellingUnitModel1 = new DwellingUnitModel(dwellingUnitModel.getID(), dwellingUnitModel.getTEXT());
                        realmOperations.addDwellingUnit(dwellingUnitModel1);
                    }

                    realmOperations.deleteNetworkDistLineSize_MeterSanctionSizeTable();
                    for (SizeModel sizeModel : dataForAndroidHSC.getSizeModels()) {
                        SizeModel sizeModel1 = new SizeModel(sizeModel.getID(), sizeModel.getNAME());
                        realmOperations.addNetworkDistLineSize_MeterSanctionSize(sizeModel1);
                    }

                    realmOperations.deleteRoadRestorationLenRoadTable();
                    for (RoadRestorationLenRoadModel roadRestorationLenRoadModel : dataForAndroidHSC.getRoadRestorationLenRoadModels()) {
                        RoadRestorationLenRoadModel roadRestorationLenRoadModel1 = new RoadRestorationLenRoadModel(roadRestorationLenRoadModel.getID(), roadRestorationLenRoadModel.getTEXT());
                        realmOperations.addRoadRestorationLenRoad(roadRestorationLenRoadModel1);
                    }

                    realmOperations.deleteRoadTypeTable();
                    for (RoadTypeModel roadTypeModel : dataForAndroidHSC.getRoadTypeModels()) {
                        RoadTypeModel roadTypeModel1 = new RoadTypeModel(roadTypeModel.getID(), roadTypeModel.getREM_REASONNM());
                        realmOperations.addRoadType(roadTypeModel1);
                    }
                    realmOperations.deleteRoadOwnershipTable();
                    for (RoadOwnershipModel roadOwnershipModel : dataForAndroidHSC.getRoadOwnershipModels()) {
                        RoadOwnershipModel roadOwnershipModel1 = new RoadOwnershipModel(roadOwnershipModel.getID(), roadOwnershipModel.getREM_REASONNM());
                        realmOperations.addRoadOwnership(roadOwnershipModel1);
                    }

                    realmOperations.deleteWardTable();
                    for (WardModel wardModel : dataForAndroidHSC.getWardModels()) {
                        WardModel wardModel1 = new WardModel(wardModel.getID(), wardModel.getNAME(), wardModel.getZONEID());
                        realmOperations.addWard(wardModel1);
                    }

                    realmOperations.deleteLotTable();
                    for (LotModel lotModel : dataForAndroidHSC.getLotModels()) {
                        LotModel wardModel1 = new LotModel(lotModel.getMR_ID(), lotModel.getMR_NAME(), lotModel.getBUID(), lotModel.getPCID());
                        realmOperations.addLot(wardModel1);
                    }

                    realmOperations.deleteAreaTable();
                    for (AreaModel areaModel : dataForAndroidHSC.getAreaModels()) {
                        AreaModel areaModel1 = new AreaModel(areaModel.getAREAID(), areaModel.getNAME());
                        realmOperations.addArea(areaModel1);
                    }
                    realmOperations.deleteRejectTable();
                    for (RejectModel rejectModel : dataForAndroidHSC.getRejectModels()) {
                        RejectModel rejectModel1 = new RejectModel(rejectModel.getID(), rejectModel.getREM_REASONNM());
                        realmOperations.addReject(rejectModel1);
                        Log.e("daata",rejectModel1.toString());
                    }

                }
                hscMasterProgress.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
                hscMasterProgress.dismiss();
            }
        }

    }

  /*  @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.ll_nc_registration:{

            }
            break;
            case R.id.ll_nc_site_vist_list:
            {
                //Intent intent = new Intent(mCon, SiteVisitListActivity.class);
                Intent intent = new Intent(mCon, SiteVisitDashboardActivity.class);
                startActivity(intent);
            }
            break;
            default:
                break;
        }

    }*/






//    @Override
//    public void onResume() {
//        super.onResume();
//
//        App myApp = (App)mCon.getApplicationContext();
//        if (myApp.wasInBackground) {
//            getActivity().finish();
//            startActivity(new Intent(mCon, SplashScreen.class));
//        }
//
//        myApp.stopActivityTransitionTimer();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        realmOperations.close();
//        ((App) mCon.getApplicationContext()).startActivityTransitionTimer();
//    }
}
