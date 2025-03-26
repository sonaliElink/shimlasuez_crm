package elink.suezShimla.water.crm.ConnectionManagement.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.tiper.MaterialSpinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.ConnectionManagement.activity.SiteVisitListActivity;
import elink.suezShimla.water.crm.ConnectionManagement.activity.SiteVisitListActivityDetails;
import elink.suezShimla.water.crm.ConnectionManagement.model.SiteVisitModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.constant.AppConstant;

public class NCConsumerDetailsFragment extends Fragment implements View.OnClickListener, MaterialSpinner.OnItemSelectedListener {

    TextInputEditText tvtaluka,ettaluka, etmiddlesName, etmiddlename,etApplicationNumber,etApplicationDate,etAppointmentDate,etFirstNameFix,etFirstName,tvLastName,etLastName,tvAddress,etAddress,
            tvLocation,etLocation,tvLandmark,etLandmark,tvStreetLocality,etStreetLocality,tvDistrict,etDistrict,tvCityVillage,etCityVillage,tvPostalCode,etPostalCode,etLocality,
            etwhatsapp,tvwhatsNumber,tvEmial,etEmail,tvMobile,etMobile,tvLandLineNumber,etLandLine,etcswFirstNameFix,etcswFirstName,tvcswLastName,etcswLastName;
  /*  LinearLayout ll_first_edit, ll_salutationSpinner, ll_first_name_edit, ll_edit, ll_address_edit, ll_landmark_edit, ll_street_locality_edit, ll_district_edit,
            ll_city_village_edit,ll_postal_code_edit,ll_mobile_edit,ll_land_line_edit,ll_email_edit;*/
   /* TextInputLayout lastNameInputLayout, firstNameInputLayout, addressInputLayout, landmarkInputLayout, streetLocalityInputLayout, districtInputLayout,
            cityVillageInputLayout,postalCodeInputLayout,mobileInputLayout,landLineInputLayout,emailInputLayout;*/
    MaterialSpinner salutationSpinnerFix,salutationSpinner,coswoffSpinnerFix,coswoffSpinner,tvGov,etGov;
    String addapplicationtype="",newadd="",fullname="",applicationNumberFixStr = "",applicationDateFixStr = "",appointmentDateFixStr = "",firstNameFixStr="",middleStr="",lastNameFixStr="",fatherNameFixStr="",addresFixStr="",landmarkFixStr="",
    streetLocalityFixStr="", locationFixStr ="", districtFixStr="",cityFixStr="",postalCodeFixStr="",mobileNoFixStr="",lanLineFixStr="",emailFixStr="",COSOWOStr="",isGovEmp="";


    String applicationNumberStr = "",applicationDateStr = "",appointmentDateStr = "",firstNameStr="",lastNameStr="",addresStr="",landmarkStr="",
            location ="",streetLocalityStr="",districtStr="",cityStr="",postalCodeStr="",mobileNoStr="",lanLineStr="",emailStr="",salutationStr="",SOWOCOFIRST="",SOWOCOLAST="",
            middlename,SOWOCO="",lastNameSOFOCOStr="",firstNameSOFOCOStr="",SOFOCOtr="",SOCOWOFathernameStr="",isgovtemp="";
    LinearLayout lastNdameInputLayout,etcswNameFixLayout;
ArrayAdapter GovTypeAdapter;

    SiteVisitModel model;
    SendConsumerData SCD;
    String STARTTIME = "", ALERTSTARTTIME = "";
    String rtimem = "Reading can be taken between";
    boolean submitData = false;
    String ENDTIME = "", ALERTENDTIME = "", time = "";
    Button btn_submit_consumer,btn_consumer_back;
    Context mCon;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // prevent ss and hide content when app is on background
       // getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        View rootView = inflater.inflate(R.layout.fragment_nc_consumer_details, container, false);

        SiteVisitListActivityDetails activity = (SiteVisitListActivityDetails) getActivity();
        model = activity.getSiteVisitDataData();
        mCon = getActivity();
        applicationNumberFixStr = model.getREQUEST_NO();
        appointmentDateFixStr = model.getAM_APP_APPOINT_DATE();
        firstNameFixStr = model.getAM_APP_FNAME();
        fullname=model.getNAME();
        int idx = fullname.lastIndexOf(' ');
//        String[] names = fullname.split(String.valueOf(' '));
//        String firstName = names[0];
//         middlename = names[1];

        middleStr = model.getAM_APP_MNAME();
        addapplicationtype = model.getAM_AAPP_NO_TYPE();
        newadd = model.getNEWADDRESS();

        applicationDateFixStr = model.getAM_AAPP_DATE();
        lastNameFixStr = model.getAM_APP_SURNAME();
        fatherNameFixStr = model.getFATHERNAME();

        addresFixStr = model.getAM_APP_ADDRESS1();
        landmarkFixStr = model.getAM_APP_ADDRESS3();
        locationFixStr = model.getAM_APP_LOCALITY();
        streetLocalityFixStr = model.getAM_APP_ADDRESS2();
        districtFixStr = model.getAM_APP_ADDRESS4();
        isGovEmp= model.getAM_APP_ISMSCDCL_EMPLOYEE_ID();
        cityFixStr = model.getAM_APP_ADDRESS2();
        postalCodeFixStr = model.getAM_APP_PINCODE();
        mobileNoFixStr = model.getAM_APP_PHONEM();
        lanLineFixStr = model.getAM_APP_PHONE();
        emailFixStr = model.getAM_APP_EMAIL();
        COSOWOStr = model.getFATHERNAME();
        try {
            String[] str = COSOWOStr.split("\\ ");

            SOWOCO = str[0];
            SOWOCOFIRST = str[1];
            SOWOCOLAST = str[2];
        }catch (Exception ex){
           // String error= ex.getMessage();
        }
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

        if (addapplicationtype.equalsIgnoreCase("12")){

            tvtaluka = rootView.findViewById(R.id.tvtaluka);
            ettaluka= rootView.findViewById(R.id.ettaluka);

//            salutationSpinner = rootView.findViewById(R.id.salutationSpinner);

//            coswoffSpinner = rootView.findViewById(R.id.coswoffSpinner);


            etFirstName.setEnabled(false);
            etmiddlesName.setEnabled(false);
            etLastName.setEnabled(false);

            etAddress.setEnabled(false);

            etLandmark.setEnabled(false);
            etStreetLocality.setEnabled(false);
            etLocality.setEnabled(false);
            etLocation.setEnabled(false);

            etDistrict.setEnabled(false);

            etCityVillage.setEnabled(false);

            etPostalCode.setEnabled(false);

            etEmail.setEnabled(false);

            etwhatsapp.setEnabled(false);

            etcswFirstName.setEnabled(false);

            etcswLastName.setEnabled(false);

            etMobile.setEnabled(false);


            lastNdameInputLayout.setEnabled(false);


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

        tvtaluka = rootView.findViewById(R.id.tvtaluka);
        ettaluka= rootView.findViewById(R.id.ettaluka);

        salutationSpinner = rootView.findViewById(R.id.salutationSpinner);
        salutationSpinner.setOnItemSelectedListener(this);
        salutationSpinnerFix = rootView.findViewById(R.id.salutationSpinnerFix);

        coswoffSpinner = rootView.findViewById(R.id.coswoffSpinner);
        coswoffSpinner.setOnItemSelectedListener(this);
        coswoffSpinnerFix = rootView.findViewById(R.id.coswoffSpinnerFix);

        etApplicationNumber = rootView.findViewById(R.id.etApplicationNumber);
        etApplicationDate = rootView.findViewById(R.id.etApplicationDate);
        etAppointmentDate = rootView.findViewById(R.id.etAppointmentDate);
        etFirstNameFix = rootView.findViewById(R.id.etFirstNameFix);
        etFirstName = rootView.findViewById(R.id.etFirstName);
        etmiddlename= rootView.findViewById(R.id.etmiddlename);
        etmiddlesName= rootView.findViewById(R.id.etmiddlesName);
        tvLastName = rootView.findViewById(R.id.tvLastName);
        etLastName = rootView.findViewById(R.id.etLastName);
        tvAddress = rootView.findViewById(R.id.tvAddress);
        etAddress = rootView.findViewById(R.id.etAddress);
        tvLandmark = rootView.findViewById(R.id.tvLandmark);
        etLandmark = rootView.findViewById(R.id.etLandmark);
        tvStreetLocality = rootView.findViewById(R.id.tvStreetLocality);
        etStreetLocality = rootView.findViewById(R.id.etStreetLocality);
        etLocality= rootView.findViewById(R.id.etLocality);
        tvLocation = rootView.findViewById(R.id.tvLocality);
        etLocation = rootView.findViewById(R.id.etLocality);

        tvDistrict = rootView.findViewById(R.id.tvDistrict);
        etDistrict = rootView.findViewById(R.id.etDistrict);
        tvGov= rootView.findViewById(R.id.tvGov);
        etGov=rootView.findViewById(R.id.etGov);

        tvCityVillage = rootView.findViewById(R.id.tvCityVillage);
        etCityVillage = rootView.findViewById(R.id.etCityVillage);

        tvPostalCode = rootView.findViewById(R.id.tvPostalCode);
        etPostalCode = rootView.findViewById(R.id.etPostalCode);

        tvEmial = rootView.findViewById(R.id.tvEmial);
        etEmail = rootView.findViewById(R.id.etEmail);

        tvwhatsNumber = rootView.findViewById(R.id.tvwhatsNumber);
        etwhatsapp = rootView.findViewById(R.id.etwhatsapp);

        etcswFirstNameFix = rootView.findViewById(R.id.etcswFirstNameFix);
        etcswFirstName = rootView.findViewById(R.id.etcswFirstName);

        tvcswLastName = rootView.findViewById(R.id.tvcswLastName);
        etcswLastName = rootView.findViewById(R.id.etcswLastName);

        tvMobile = rootView.findViewById(R.id.tvMobile);
        etMobile = rootView.findViewById(R.id.etMobile);

        etcswNameFixLayout = rootView.findViewById(R.id.etcswNameFixLayout);
        lastNdameInputLayout = rootView.findViewById(R.id.lastNdameInputLayout);

        btn_submit_consumer = rootView.findViewById(R.id.btn_submit_consumer);
        btn_submit_consumer.setOnClickListener(this);

        btn_consumer_back = rootView.findViewById(R.id.btn_consumer_back);
        btn_consumer_back.setOnClickListener(this);


        initSetData();



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.salutation, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        salutationSpinner.setAdapter(adapter);
        salutationSpinnerFix.setAdapter(adapter);
        salutationSpinner.setSelection(1);
        salutationSpinnerFix.setSelection(1);
        // selectedSalutation=  salutationSpinner.getHint().toString();
        salutationSpinnerFix.setOnItemSelectedListener(this);
        salutationSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> cswAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.csw, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        coswoffSpinner.setAdapter(cswAdapter);
        coswoffSpinnerFix.setAdapter(cswAdapter);
        coswoffSpinner.setSelection(0);
        if(SOWOCO.equalsIgnoreCase("S/o")){

            coswoffSpinner.setSelection(0);
            coswoffSpinnerFix.setSelection(0);
        }else if(SOWOCO.equalsIgnoreCase("W/o")){
            coswoffSpinner.setSelection(1);
            coswoffSpinnerFix.setSelection(1);
        }else if(SOWOCO.equalsIgnoreCase("C/o")){
            coswoffSpinner.setSelection(2);
            coswoffSpinnerFix.setSelection(2);
        }

        coswoffSpinnerFix.setOnItemSelectedListener(this);
        coswoffSpinner.setOnItemSelectedListener(this);

    }

    private void initSetData() {
        setGovValue();
        etApplicationNumber.setText(applicationNumberFixStr);
        etApplicationDate.setText(applicationDateFixStr);
        etAppointmentDate.setText(appointmentDateFixStr);
        etFirstNameFix.setText(firstNameFixStr);
        etFirstName.setText(firstNameFixStr);
        etmiddlesName.setText(middleStr);
        etmiddlename.setText(middleStr);
        etLastName.setText(lastNameFixStr);
        tvLastName.setText(lastNameFixStr);
        tvAddress.setText(addresFixStr);
        etAddress.setText(addresFixStr);
        if(addapplicationtype.equals("6")){
            etAddress.setText(newadd);

        }else {
            etAddress.setText(addresFixStr);

        }
        tvLocation.setText(locationFixStr);
        etLocation.setText(locationFixStr);
        tvLandmark.setText(landmarkFixStr);
        etLandmark.setText(landmarkFixStr);
        tvStreetLocality.setText(cityFixStr);
        etStreetLocality.setText(cityFixStr);
        etLocality.setText(locationFixStr);
        tvDistrict.setText(districtFixStr);
        etDistrict.setText(districtFixStr);
        tvCityVillage.setText(cityFixStr);
        etCityVillage.setText(cityFixStr);
        tvPostalCode.setText(postalCodeFixStr);
        etPostalCode.setText(postalCodeFixStr);
        tvEmial.setText(emailFixStr);
        etEmail.setText(emailFixStr);
        tvMobile.setText(mobileNoFixStr);
        etMobile.setText(mobileNoFixStr);
        tvwhatsNumber.setText(lanLineFixStr);
        etwhatsapp.setText(lanLineFixStr);
        etcswFirstNameFix.setText(SOWOCOFIRST);
        etcswFirstName.setText(SOWOCOFIRST);
        tvcswLastName.setText(SOWOCOLAST);
        etcswLastName.setText(SOWOCOLAST);


    }

    private void setGovValue() {


            ArrayList<String> propertyType = new ArrayList<>();

            String[] gov = {  "No","Yes"};
            GovTypeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, gov);
            GovTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


            tvGov.setAdapter(GovTypeAdapter);
            if(isGovEmp.equals("0")) {
                // int catInt = getPropertyPos(propertyTypeName);

                tvGov.setSelection(0);
            }else {
                tvGov.setSelection(1);
            }
            etGov.setAdapter(GovTypeAdapter);
            if(isGovEmp.equals("0")) {
                // int catInt = getPropertyPos(propertyTypeName);

                etGov.setSelection(0);
            }else {
                etGov.setSelection(1);
            }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit_consumer:
            {
                if(validation()){
                    SOCOWOFathernameStr=SOFOCOtr+" "+firstNameSOFOCOStr+" "+lastNameSOFOCOStr;

                    dataNext();
                           }
            }
            break;

            case R.id.btn_consumer_back:{
                backActivity();
            }

            default:
                break;
        }
    }

    private void backActivity() {
        Intent intent = new Intent(getActivity(), SiteVisitListActivity.class);
       // intent.putExtra("Tag", "3");
        startActivity(intent);
        getActivity().finish();

    }

    private boolean validation() {
        salutationStr = salutationSpinner.getSelectedItem().toString();
        firstNameStr = etFirstName.getText().toString();
        lastNameStr = etLastName.getText().toString();
        SOFOCOtr = coswoffSpinner.getSelectedItem().toString();
        firstNameSOFOCOStr = etcswFirstName.getText().toString();
        lastNameSOFOCOStr = etcswLastName.getText().toString();
        addresStr = etAddress.getText().toString();
        landmarkStr = etLandmark.getText().toString();
        location = etLocation.getText().toString();
       streetLocalityStr = etStreetLocality.getText().toString();
       districtStr = etDistrict.getText().toString();
       cityStr = etCityVillage.getText().toString();
       postalCodeStr = etPostalCode.getText().toString();
       mobileNoStr = etMobile.getText().toString();
       lanLineStr = etwhatsapp.getText().toString();
       emailStr = etEmail.getText().toString();
       isgovtemp= etGov.getSelectedItem().toString();





        if (salutationStr.equalsIgnoreCase("Select")) {

            salutationSpinner.setError(getResources().getString(R.string.cannot_be_empty));

            Toast.makeText(getActivity(), R.string.select_salutation, Toast.LENGTH_SHORT).show();
            return false;
        }   else {
            salutationSpinner.setError(null);

        }

        if (firstNameStr.equalsIgnoreCase("")) {
            etFirstName.setError(getResources().getString(R.string.cannot_be_empty));
            Toast.makeText(getActivity(), R.string.please_enter_first_name, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            etFirstName.setError(null);

        }
        if (lastNameStr.equalsIgnoreCase("")) {
            etLastName.setError(getResources().getString(R.string.cannot_be_empty));
            Toast.makeText(getActivity(), R.string.please_enter_last_name, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            etLastName.setError(null);

        }
        if (SOFOCOtr.equalsIgnoreCase("Select")) {

            coswoffSpinner.setError(getResources().getString(R.string.cannot_be_empty));

            Toast.makeText(getActivity(), R.string.select_care_off, Toast.LENGTH_SHORT).show();
            return false;
        }   else {
      coswoffSpinner.setError(null);

        }

       /* if (firstNameSOFOCOStr.equalsIgnoreCase("")) {
            etcswFirstName.setError(getResources().getString(R.string.cannot_be_empty));
            Toast.makeText(getActivity(), R.string.please_enter_first_name, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            etcswFirstName.setError(null);

        }
        if (lastNameSOFOCOStr.equalsIgnoreCase("")) {
            etcswLastName.setError(getResources().getString(R.string.cannot_be_empty));
            Toast.makeText(getActivity(), R.string.please_enter_last_name, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            etcswLastName.setError(null);

        }*/

        if (addresStr.equalsIgnoreCase("")) {
            etAddress.setError(getResources().getString(R.string.cannot_be_empty));
            Toast.makeText(getActivity(), R.string.please_enter_address, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            etAddress.setError(null);

        }
     /*   if (landmarkStr.equalsIgnoreCase("")) {
            etLandmark.setError(getResources().getString(R.string.cannot_be_empty));
            Toast.makeText(getActivity(), R.string.please_enter_landmark, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            etLandmark.setError(null);

        }*/
        if (streetLocalityStr.equalsIgnoreCase("")) {
            etStreetLocality.setError(getResources().getString(R.string.cannot_be_empty));
            Toast.makeText(getActivity(), R.string.please_enter_street_locality, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            etStreetLocality.setError(null);

        }if (districtStr.equalsIgnoreCase("")) {
            etDistrict.setError(getResources().getString(R.string.cannot_be_empty));
            Toast.makeText(getActivity(), R.string.please_enter_district, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            etDistrict.setError(null);

      /*  }if (cityStr.equalsIgnoreCase("")) {
            etCityVillage.setError(getResources().getString(R.string.cannot_be_empty));
            Toast.makeText(getActivity(), R.string.please_enter_city_village, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            etCityVillage.setError(null);
*/
        }if (postalCodeStr.equalsIgnoreCase("")) {
            etPostalCode.setError(getResources().getString(R.string.cannot_be_empty));
            Toast.makeText(getActivity(), R.string.please_enter_postal_code, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            etPostalCode.setError(null);

        }
        /*if (mobileNoStr.equalsIgnoreCase("")) {
            etMobile.setError(getResources().getString(R.string.cannot_be_empty));
            Toast.makeText(getActivity(), R.string.please_enter_mobile_no, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            etMobile.setError(null);

        }
        if (lanLineStr.equalsIgnoreCase("")) {
            etLandLine.setError(getResources().getString(R.string.cannot_be_empty));
            Toast.makeText(getActivity(), R.string.please_enter_landline_no, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            etLandLine.setError(null);

        }if (emailStr.equalsIgnoreCase("")) {
            etEmail.setError(getResources().getString(R.string.cannot_be_empty));
            Toast.makeText(getActivity(), R.string.please_enter_email, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            etEmail.setError(null);

        }
*/


        return true;
    }

    private void dataNext() {

            SiteVisitModel siteVisitModel = new SiteVisitModel(applicationNumberFixStr,salutationStr,firstNameStr,middleStr,middleStr,lastNameStr,addresStr, landmarkStr,streetLocalityStr,districtStr,cityStr,postalCodeStr,mobileNoStr,lanLineStr,emailStr, location,isgovtemp);
            SCD.sendData(siteVisitModel);
    //                 MessageWindow.msgWindow(getContext(), "Details Submitted Succesfully Slide Right for Old Meter Details");
       //     Toast.makeText(getActivity(), getResources().getString(R.string.details_submitted_successfully), Toast.LENGTH_SHORT).show();

                ((SiteVisitListActivityDetails) getActivity()).onClickNext();

    }


        public interface SendConsumerData {
            //        void sendData(String submitStatus, String radiobuttonValStr,  String makerCodeId,String serialNoStr,String installDtStr,String meterSizeStr,String sealNoStr,String pastMeterReadingStr);
            void sendData(SiteVisitModel siteVisitModel);
        }
    @Override
    public void onItemSelected(MaterialSpinner materialSpinner, View view, int i, long l) {
        switch (materialSpinner.getId()) {

            case R.id.salutationSpinner: {
                String selectedValue = salutationSpinner.getSelectedItem().toString();
                if (selectedValue.equalsIgnoreCase("Select")) {
                    salutationStr = "0";
                } else {
                    salutationStr = selectedValue;
                }
            }
            break;

            default:
                break;

        }
    }

    @Override
    public void onNothingSelected(MaterialSpinner materialSpinner) {

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            SCD = (SendConsumerData) getActivity();
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
