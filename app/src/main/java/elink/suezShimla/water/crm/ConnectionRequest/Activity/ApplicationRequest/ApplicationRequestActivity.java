package elink.suezShimla.water.crm.ConnectionRequest.Activity.ApplicationRequest;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.AbsoluteLayout;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import elink.suezShimla.water.crm.ConnectionRequest.Activity.ApplicationRequest.Adapter.DocumentsAdapter;
import elink.suezShimla.water.crm.ConnectionRequest.Activity.ApplicationRequest.Model.DocumentsModel;
import elink.suezShimla.water.crm.ConnectionRequest.UploadDocuments.UploadDocumentsActivity;
import elink.suezShimla.water.crm.R;

public class ApplicationRequestActivity extends AppCompatActivity {

    private Context mCon;

    private MaterialButton nextButton, backButton, submitButton, BottomSubmitButton;

    private RelativeLayout applicationTypeRelativeLayout, consumerDetailsRelativeLayout, connectionDetailsRelativeLayout, ownerInfoRelativeLayout;

    private LinearLayout idProofLinerLayout, addressProofLinerLayout, mandatoryDocumentsLinerLayout, nocLinerLayout;

    private AppCompatImageView pageOneImageView, pageTwoImageView, pageThreeImageView, pageFourImageView;

    private AppCompatImageView idProofDownImageView, addressProofDownImageView, mandatoryDownImageView, nocDownImageView;

    private TextView applicationRequestDateTextView, pageCountTextView;

    private RadioGroup radioGroup;

    private MaterialCardView connectionDetailsCardView;

    private RadioButton permanentConnectionRadioButton, temporaryConnectionRadioButton;

    private AppCompatSpinner mainTariffSpinner, areaTypeSpinner, connectionSizeSpinner, propertySpinner, govtEmpSpinner,
            existingConnectionSpinner, ownershipSpinner, siteDistrictSpinner, siteVillageSpinner, billingDistrictSpinner, billingVillageSpinner;

    private TextInputLayout firstNameInputLayout, lastNameInputLayout, mobileNoInputLayout, landlordOwnerNameInputLayout, addressInputLayout, ownerContactNoInputLayout,
            siteAddressLine1InputLayout, siteLandmarkInputLayout, sitePinCodeInputLayout, billingAddressLine1InputLayout, billingLandmarkInputLayout, billingPinCodeInputLayout, startDateInputLayout,
            endDateInputLayout, daysInputLayout, remarkTextInputLayout, nearByConsumerNoInputLayout;

    private TextInputEditText firstNameEditText, middleNameEditText, lastNameEditText, marathiNameEditText, mobileNoEditText, codeEditText, landLineNameEditText,
            emailEditText, landlordOwnerNameEditText, addressEditText, ownerContactNoEditText, siteAddressLie1EditText, siteAddressLie2EditText, siteLandmarkEditText,
            sitePinCodeEditText, siteMarathiAddrEditText, billingAddressLie1EditText, billingAddressLie2EditText, billingMarathiAddrEditText, billingLandmarkEditText,
            billingPinCodeEditText, startDateEditText, endDateEditText, daysEditText, appFeeEditText, totalAmtEditText, remarkEditText, nearByConsumerNoEditText;

    private String sourceTypeStr, firstNameStr, lastNameStr, mobileNoStr, mainTariffStr, areaTypeStr, connectionSizeStr, propertyStr, govtEmpStr, existingConnectionStr,
            ownershipStr, nearByConsumerNoStr, landlordOwnerNameStr, addressStr, ownerContactNoStr, siteAddressLine1Str, siteAddressLine2Str, siteLandmarkStr,
            sitePinCodeStr, siteDistrictStr, siteVillageStr, siteMarathiAddrStr, billingAddressLine1Str, billingLandmarkStr,
            billingPinCodeStr, billingDistrictStr, billingVillageStr, startDateStr, endDateStr, daysStr, remarkStr;

    private CheckBox billingAddressCheckBox;

    private BottomSheetDialog sheetBehavior;

    private RecyclerView idProofRecyclerView, addressProofRecyclerView, mandatoryDocumentsRecyclerView, nocRecyclerView;
    private DocumentsAdapter documentsAdapter;
    private List<DocumentsModel> documentsModelList = new ArrayList<>();

    private RelativeLayout pageOneRelativeView, pageTwoRelativeView, pageThreeRelativeView, pageFourRelativeView;

    private Calendar startCalendar, endCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_request);
        // prevent ss and hide content when app is on background
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mCon = this;

        startCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();

        // Bottom Button View
        nextButton = findViewById(R.id.nextButton);
        backButton = findViewById(R.id.backButton);
        submitButton = findViewById(R.id.submitButton);
        pageCountTextView = findViewById(R.id.pageCountTextView);

        // Form Page One
        applicationTypeRelativeLayout = findViewById(R.id.applicationTypeRelativeLayout);
        pageOneRelativeView = findViewById(R.id.pageOneRelativeView);
        pageOneImageView = findViewById(R.id.pageOneImageView);
        applicationRequestDateTextView = findViewById(R.id.applicationRequestDateTextView);
        radioGroup = findViewById(R.id.radioGroup);
        permanentConnectionRadioButton = findViewById(R.id.permanentConnectionRadioButton);
        temporaryConnectionRadioButton = findViewById(R.id.temporaryConnectionRadioButton);

        firstNameEditText = findViewById(R.id.firstNameEditText);
        consumerDetailsRelativeLayout = findViewById(R.id.consumerDetailsRelativeLayout);
        pageTwoRelativeView = findViewById(R.id.pageTwoRelativeView);
        pageTwoImageView = findViewById(R.id.pageTwoImageView);
        pageThreeImageView = findViewById(R.id.pageThreeImageView);
        firstNameInputLayout = findViewById(R.id.firstNameInputLayout);
        lastNameInputLayout = findViewById(R.id.lastNameInputLayout);
        marathiNameEditText = findViewById(R.id.marathiNameEditText);
        mobileNoInputLayout = findViewById(R.id.mobileNoInputLayout);
        middleNameEditText = findViewById(R.id.middleNameEditText);
        mobileNoEditText = findViewById(R.id.mobileNoEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        codeEditText = findViewById(R.id.codeEditText);
        landLineNameEditText = findViewById(R.id.landLineNameEditText);
        emailEditText = findViewById(R.id.emailEditText);

        ownerInfoRelativeLayout = findViewById(R.id.ownerInfoRelativeLayout);
        pageFourRelativeView = findViewById(R.id.pageFourRelativeView);
        pageFourImageView = findViewById(R.id.pageFourImageView);
        landlordOwnerNameInputLayout = findViewById(R.id.landlordOwnerNameInputLayout);
        addressInputLayout = findViewById(R.id.addressInputLayout);
        ownerContactNoInputLayout = findViewById(R.id.ownerContactNoInputLayout);
        landlordOwnerNameEditText = findViewById(R.id.landlordOwnerNameEditText);
        addressEditText = findViewById(R.id.addressEditText);
        ownerContactNoEditText = findViewById(R.id.ownerContactNoEditText);

        connectionDetailsCardView = findViewById(R.id.connectionDetailsCardView);
        startDateInputLayout = findViewById(R.id.startDateInputLayout);
        endDateInputLayout = findViewById(R.id.endDateInputLayout);
        daysInputLayout = findViewById(R.id.daysInputLayout);
        startDateEditText = findViewById(R.id.startDateEditText);
        endDateEditText = findViewById(R.id.endDateEditText);
        daysEditText = findViewById(R.id.daysEditText);
        appFeeEditText = findViewById(R.id.appFeeEditText);
        totalAmtEditText = findViewById(R.id.totalAmtEditText);


        // Form Page Two
        connectionDetailsRelativeLayout = findViewById(R.id.connectionDetailsRelativeLayout);
        pageThreeRelativeView = findViewById(R.id.pageThreeRelativeView);
        pageThreeImageView = findViewById(R.id.pageThreeImageView);
        mainTariffSpinner = findViewById(R.id.mainTariffSpinner);
        areaTypeSpinner = findViewById(R.id.areaTypeSpinner);
        connectionSizeSpinner = findViewById(R.id.connectionSizeSpinner);
        propertySpinner = findViewById(R.id.propertySpinner);
        govtEmpSpinner = findViewById(R.id.govtEmpSpinner);
        existingConnectionSpinner = findViewById(R.id.existingConnectionSpinner);
        ownershipSpinner = findViewById(R.id.ownershipSpinner);

        nearByConsumerNoInputLayout = findViewById(R.id.nearByConsumerNoInputLayout);
        nearByConsumerNoEditText = findViewById(R.id.nearByConsumerNoEditText);


        // Form Page Three
        siteAddressLine1InputLayout = findViewById(R.id.siteAddressLie1InputLayout);
        siteLandmarkInputLayout = findViewById(R.id.siteLandmarkInputLayout);
        sitePinCodeInputLayout = findViewById(R.id.sitePinCodeInputLayout);
        siteAddressLie1EditText = findViewById(R.id.siteAddressLie1EditText);
        siteAddressLie2EditText = findViewById(R.id.siteAddressLie2EditText);
        siteLandmarkEditText = findViewById(R.id.siteLandmarkEditText);
        sitePinCodeEditText = findViewById(R.id.sitePinCodeEditText);
        siteMarathiAddrEditText = findViewById(R.id.siteMarathiAddrEditText);
        siteDistrictSpinner = findViewById(R.id.siteDistrictSpinner);
        siteVillageSpinner = findViewById(R.id.siteVillageSpinner);

        billingAddressLine1InputLayout = findViewById(R.id.billingAddressLie1InputLayout);
        billingLandmarkInputLayout = findViewById(R.id.billingLandmarkInputLayout);
        billingPinCodeInputLayout = findViewById(R.id.billingPinCodeInputLayout);
        billingAddressLie1EditText = findViewById(R.id.billingAddressLie1EditText);
        billingAddressLie2EditText = findViewById(R.id.billingAddressLie2EditText);
        billingMarathiAddrEditText = findViewById(R.id.billingMarathiAddrEditText);
        billingLandmarkEditText = findViewById(R.id.billingLandmarkEditText);
        billingPinCodeEditText = findViewById(R.id.billingPinCodeEditText);
        billingDistrictSpinner = findViewById(R.id.billingDistrictSpinner);
        billingVillageSpinner = findViewById(R.id.billingVillageSpinner);
        billingAddressCheckBox = findViewById(R.id.billingAddressCheckBox);


        // Form Page Four
        idProofRecyclerView = findViewById(R.id.idProofRecyclerView);
        addressProofRecyclerView = findViewById(R.id.addressProofRecyclerView);
        mandatoryDocumentsRecyclerView = findViewById(R.id.mandatoryDocumentsRecyclerView);
        nocRecyclerView = findViewById(R.id.nocRecyclerView);
        idProofDownImageView = findViewById(R.id.idProofDownImageView);
        addressProofDownImageView = findViewById(R.id.addressProofDownImageView);
        mandatoryDownImageView = findViewById(R.id.mandatoryDownImageView);
        nocDownImageView = findViewById(R.id.nocDownImageView);
        idProofLinerLayout = findViewById(R.id.idProofLinerLayout);
        addressProofLinerLayout = findViewById(R.id.addressProofLinerLayout);
        mandatoryDocumentsLinerLayout = findViewById(R.id.mandatoryDocumentsLinerLayout);
        nocLinerLayout = findViewById(R.id.nocLinerLayout);

        // Documents
        documentsAdapter = new DocumentsAdapter(mCon);
        setRecycler(idProofRecyclerView);
        setRecycler(addressProofRecyclerView);
        setRecycler(mandatoryDocumentsRecyclerView);
        setRecycler(nocRecyclerView);

        sheetBehavior = new BottomSheetDialog(Objects.requireNonNull(mCon));

        View sheetView = Objects.requireNonNull(getLayoutInflater().inflate(R.layout.bottom_sheet_remarks, null));
        sheetBehavior.setContentView(sheetView);

        BottomSubmitButton = sheetBehavior.findViewById(R.id.BottomSubmitButton);
        remarkTextInputLayout = sheetBehavior.findViewById(R.id.remarkTextInputLayout);
        remarkEditText = sheetBehavior.findViewById(R.id.remarkEditText);

        RadioButtonChecker();

        loadRecycler();

        pageFourButtonClick();

        final DatePickerDialog.OnDateSetListener startCalendarDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                startCalendar.set(Calendar.YEAR, year);
                startCalendar.set(Calendar.MONTH, monthOfYear);
                startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateStartCalendar();
            }
        };

        final DatePickerDialog.OnDateSetListener endCalendarDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                endCalendar.set(Calendar.YEAR, year);
                endCalendar.set(Calendar.MONTH, monthOfYear);
                endCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateEndCalendar();
            }
        };

        startDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(mCon, startCalendarDate, startCalendar
                        .get(Calendar.YEAR), startCalendar.get(Calendar.MONTH),
                        startCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
                datePickerDialog.show();
            }
        });

        endDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(mCon, endCalendarDate, startCalendar
                        .get(Calendar.YEAR), endCalendar.get(Calendar.MONTH),
                        endCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
                datePickerDialog.show();
            }
        });


        // Next Button OnClick
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pageOneRelativeView.getVisibility() == View.VISIBLE) {
                    nextButton.setVisibility(View.VISIBLE);
                    submitButton.setVisibility(View.GONE);
                    pageOneButtonClick();
                } else if (pageTwoRelativeView.getVisibility() == View.VISIBLE) {
                    nextButton.setVisibility(View.VISIBLE);
                    submitButton.setVisibility(View.GONE);
                    pageTwoButtonClick();
                } else if (pageThreeRelativeView.getVisibility() == View.VISIBLE) {
                    pageThreeButtonClick();
                }
            }
        });

        // Back Button OnClick
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pageTwoRelativeView.getVisibility() == View.VISIBLE) {
                    nextButton.setVisibility(View.VISIBLE);
                    submitButton.setVisibility(View.GONE);
                    formVisibility(pageTwoImageView, pageOneImageView, pageTwoRelativeView, pageOneRelativeView,
                            1, R.anim.exit_to_right, R.anim.enter_from_left);
                    backButton.setVisibility(View.GONE);
                } else if (pageThreeRelativeView.getVisibility() == View.VISIBLE) {
                    nextButton.setVisibility(View.VISIBLE);
                    submitButton.setVisibility(View.GONE);
                    formVisibility(pageThreeImageView, pageTwoImageView, pageThreeRelativeView, pageTwoRelativeView,
                            2, R.anim.exit_to_right, R.anim.enter_from_left);
                } else if (pageFourRelativeView.getVisibility() == View.VISIBLE) {
                    nextButton.setVisibility(View.VISIBLE);
                    submitButton.setVisibility(View.GONE);
                    formVisibility(pageFourImageView, pageThreeImageView, pageFourRelativeView, pageThreeRelativeView,
                            3, R.anim.exit_to_right, R.anim.enter_from_left);
                }
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetBehavior.show();
            }
        });

        BottomSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSubmitButtonClick();
            }
        });

        billingAddressCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                siteAddressLine1Str = siteAddressLie1EditText.getText().toString().trim();
                siteAddressLine2Str = siteAddressLie2EditText.getText().toString().trim();
                siteLandmarkStr = siteLandmarkEditText.getText().toString().trim();
                sitePinCodeStr = sitePinCodeEditText.getText().toString().trim();
                siteMarathiAddrStr = siteMarathiAddrEditText.getText().toString().trim();
                siteDistrictStr = siteDistrictSpinner.getSelectedItem().toString().trim();
                siteVillageStr = siteVillageSpinner.getSelectedItem().toString().trim();
                copySiteAddress(isChecked);
            }
        });
    }

    // Page One Button Click
    private void pageOneButtonClick() {
        firstNameStr = firstNameEditText.getText().toString().trim();
        lastNameStr = lastNameEditText.getText().toString().trim();
        mobileNoStr = mobileNoEditText.getText().toString().trim();

        landlordOwnerNameStr = landlordOwnerNameEditText.getText().toString().trim();
        addressStr = addressEditText.getText().toString().trim();
        ownerContactNoStr = ownerContactNoEditText.getText().toString().trim();

        startDateStr = startDateEditText.getText().toString();
        endDateStr = endDateEditText.getText().toString();
        daysStr = daysEditText.getText().toString();

        validatePageOne();
    }

    // Page Two Button Click
    private void pageTwoButtonClick() {
        mainTariffStr = mainTariffSpinner.getSelectedItem().toString().trim();
        areaTypeStr = areaTypeSpinner.getSelectedItem().toString().trim();
        connectionSizeStr = connectionSizeSpinner.getSelectedItem().toString().trim();
        propertyStr = propertySpinner.getSelectedItem().toString().trim();
        govtEmpStr = govtEmpSpinner.getSelectedItem().toString().trim();
        existingConnectionStr = existingConnectionSpinner.getSelectedItem().toString().trim();
        ownershipStr = ownershipSpinner.getSelectedItem().toString().trim();

        nearByConsumerNoStr = nearByConsumerNoEditText.getText().toString().trim();

        validatePageTwo();
    }

    // Page Three Button Click
    private void pageThreeButtonClick() {
        siteAddressLine1Str = siteAddressLie1EditText.getText().toString().trim();
        siteAddressLine2Str = siteAddressLie2EditText.getText().toString().trim();
        siteLandmarkStr = siteLandmarkEditText.getText().toString().trim();
        sitePinCodeStr = sitePinCodeEditText.getText().toString().trim();
        siteMarathiAddrStr = siteMarathiAddrEditText.getText().toString().trim();
        siteDistrictStr = siteDistrictSpinner.getSelectedItem().toString().trim();
        siteVillageStr = siteVillageSpinner.getSelectedItem().toString().trim();

        billingAddressLine1Str = billingAddressLie1EditText.getText().toString().trim();
        billingLandmarkStr = billingLandmarkEditText.getText().toString().trim();
        billingPinCodeStr = billingPinCodeEditText.getText().toString().trim();
        billingDistrictStr = billingDistrictSpinner.getSelectedItem().toString().trim();
        billingVillageStr = billingVillageSpinner.getSelectedItem().toString().trim();

        validatePageThree();
    }

    // Page Four Button Click
    private void pageFourButtonClick() {

        idProofLinerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idProofRecyclerView.getVisibility() == View.GONE) {
                    expand(idProofRecyclerView);
                    idProofDownImageView.animate().rotation(180).start();
                } else {
                    collapse(idProofRecyclerView);
                    idProofDownImageView.animate().rotation(0).start();
                }
            }
        });

        addressProofLinerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addressProofRecyclerView.getVisibility() == View.GONE) {
                    expand(addressProofRecyclerView);
                    addressProofDownImageView.animate().rotation(180).start();
                } else {
                    collapse(addressProofRecyclerView);
                    addressProofDownImageView.animate().rotation(0).start();
                }
            }
        });

        mandatoryDocumentsLinerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mandatoryDocumentsRecyclerView.getVisibility() == View.GONE) {
                    expand(mandatoryDocumentsRecyclerView);
                    mandatoryDownImageView.animate().rotation(180).start();
                } else {
                    collapse(mandatoryDocumentsRecyclerView);
                    mandatoryDownImageView.animate().rotation(0).start();
                }
            }
        });

        nocLinerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nocRecyclerView.getVisibility() == View.GONE) {
                    expand(nocRecyclerView);
                    nocDownImageView.animate().rotation(180).start();
                } else {
                    collapse(nocRecyclerView);
                    nocDownImageView.animate().rotation(0).start();
                }
            }
        });
    }

    // Consumer Area Details Button Click
    private void consumerAreaDetailsButtonClick() {
        if (permanentConnectionRadioButton.isChecked()) {
            connectionDetailsCardView.setVisibility(View.GONE);
        } else if (temporaryConnectionRadioButton.isChecked()) {
            connectionDetailsCardView.setVisibility(View.VISIBLE);
        }
    }

    // Documents Button Click
    private void documentsButtonClick() {
        nextButton.setVisibility(View.GONE);
        submitButton.setVisibility(View.VISIBLE);
    }

    // Bottom Submit Button Click
    private void BottomSubmitButtonClick() {
        remarkStr = remarkEditText.getText().toString();
        validateRemark();
    }

    // Validate Page One
    private void validatePageOne() {

        boolean isValidRadioGroup = false, isValidFirstName = false, isValidLastName = false, isValidMobileNo = false, isValidLandlordOwner = false,
                isValidAddress = false, isValidOwnerContactNo = false, isValidStartDateSt = false, isValidEndDate = false, isValidDaysStr = false;

        if (TextUtils.isEmpty(firstNameStr)) {
            firstNameInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            firstNameInputLayout.setError(null);
            isValidFirstName = true;
        }

        if (TextUtils.isEmpty(lastNameStr)) {
            lastNameInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            lastNameInputLayout.setError(null);
            isValidLastName = true;
        }

        if (TextUtils.isEmpty(mobileNoStr)) {
            mobileNoInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else if (mobileNoStr.length() < 10) {
            mobileNoInputLayout.setError(getResources().getString(R.string.invalidate_number));
        } else if (!mobileNoStr.startsWith("6") && !mobileNoStr.startsWith("7") && !mobileNoStr.startsWith("8") && !mobileNoStr.startsWith("9")) {
            mobileNoInputLayout.setError(getResources().getString(R.string.only_indian_number_allowed));
        } else {
            mobileNoInputLayout.setError(null);
            isValidMobileNo = true;
        }

        if (TextUtils.isEmpty(landlordOwnerNameStr)) {
            landlordOwnerNameInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            landlordOwnerNameInputLayout.setError(null);
            isValidLandlordOwner = true;
        }

        if (TextUtils.isEmpty(addressStr)) {
            addressInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            addressInputLayout.setError(null);
            isValidAddress = true;
        }

        if (TextUtils.isEmpty(ownerContactNoStr)) {
            ownerContactNoInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else if (ownerContactNoStr.length() < 10) {
            ownerContactNoInputLayout.setError(getResources().getString(R.string.invalidate_number));
        } else if (!ownerContactNoStr.startsWith("6") && !ownerContactNoStr.startsWith("7") && !ownerContactNoStr.startsWith("8") && !ownerContactNoStr.startsWith("9")) {
            ownerContactNoInputLayout.setError(getResources().getString(R.string.only_indian_number_allowed));
        } else {
            ownerContactNoInputLayout.setError(null);
            isValidOwnerContactNo = true;
        }

        if (TextUtils.isEmpty(startDateStr)) {
            startDateInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            startDateInputLayout.setError(null);
            isValidStartDateSt = true;
        }

        if (TextUtils.isEmpty(endDateStr)) {
            endDateInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            endDateInputLayout.setError(null);
            isValidEndDate = true;
        }

        if (TextUtils.isEmpty(daysStr)) {
            daysInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            daysInputLayout.setError(null);
            isValidDaysStr = true;
        }

        if (radioGroup.getCheckedRadioButtonId() == -1) {
            permanentConnectionRadioButton.setError(getResources().getString(R.string.select_options));
            temporaryConnectionRadioButton.setError(getResources().getString(R.string.select_options));
        } else {
            isValidRadioGroup = true;
            permanentConnectionRadioButton.setError(null);
            temporaryConnectionRadioButton.setError(null);
        }

        if (isValidRadioGroup) {

            int id = radioGroup.getCheckedRadioButtonId();

            RadioButton ansButton = findViewById(id);
            if (ansButton.getText().toString().equals(getResources().getString(R.string.permanent_connection))) {

                if (isValidFirstName && isValidLastName && isValidMobileNo && isValidLandlordOwner && isValidAddress && isValidOwnerContactNo) {
                    consumerDetailsView();
                }
            } else {
                if (isValidFirstName && isValidLastName && isValidMobileNo && isValidLandlordOwner && isValidAddress && isValidOwnerContactNo && isValidStartDateSt
                        && isValidEndDate && isValidDaysStr) {

                    consumerDetailsView();
                }
            }
        }
    }

    // Validate Page Two
    private void validatePageTwo() {

        boolean isValidMainTariff = false, isValidAreaType = false, isValidConnectionSize = false, isValidProperty = false,
                isValidGovtEmp = false, isValidExistingConnection = false, isValidOwnership = false;

        if (mainTariffStr.equalsIgnoreCase("Main Tariff*")) {
            TextView view = (TextView) mainTariffSpinner.getSelectedView();
            view.setError(getResources().getString(R.string.select_options));
        } else {
            isValidMainTariff = true;
            TextView view = (TextView) mainTariffSpinner.getSelectedView();
            view.setError(null);
        }

        if (areaTypeStr.equalsIgnoreCase("Area Type*")) {
            TextView view = (TextView) areaTypeSpinner.getSelectedView();
            view.setError(getResources().getString(R.string.select_options));
        } else {
            isValidAreaType = true;
            TextView view = (TextView) areaTypeSpinner.getSelectedView();
            view.setError(null);
        }

        if (connectionSizeStr.equalsIgnoreCase("Connection Size (In Inch)*")) {
            TextView view = (TextView) connectionSizeSpinner.getSelectedView();
            view.setError(getResources().getString(R.string.select_options));
        } else {
            isValidConnectionSize = true;
            TextView view = (TextView) connectionSizeSpinner.getSelectedView();
            view.setError(null);
        }

        if (propertyStr.equalsIgnoreCase("Property*")) {
            TextView view = (TextView) propertySpinner.getSelectedView();
            view.setError(getResources().getString(R.string.select_options));
        } else {
            isValidProperty = true;
            TextView view = (TextView) propertySpinner.getSelectedView();
            view.setError(null);
        }

        if (govtEmpStr.equalsIgnoreCase("Govt. Emp*")) {
            TextView view = (TextView) govtEmpSpinner.getSelectedView();
            view.setError(getResources().getString(R.string.select_options));
        } else {
            isValidGovtEmp = true;
            TextView view = (TextView) govtEmpSpinner.getSelectedView();
            view.setError(null);
        }

        if (existingConnectionStr.equalsIgnoreCase("Existing Connection*")) {
            TextView view = (TextView) existingConnectionSpinner.getSelectedView();
            view.setError(getResources().getString(R.string.select_options));
        } else {
            isValidExistingConnection = true;
            TextView view = (TextView) existingConnectionSpinner.getSelectedView();
            view.setError(null);
        }

        if (ownershipStr.equalsIgnoreCase("OwnerShip*")) {
            TextView view = (TextView) ownershipSpinner.getSelectedView();
            view.setError(getResources().getString(R.string.select_options));
        } else {
            isValidOwnership = true;
            TextView view = (TextView) ownershipSpinner.getSelectedView();
            view.setError(null);
        }

        if (isValidMainTariff && isValidAreaType && isValidConnectionSize && isValidProperty && isValidGovtEmp && isValidExistingConnection
                && isValidOwnership) {

            formVisibility(pageTwoImageView, pageThreeImageView, pageTwoRelativeView, pageThreeRelativeView,
                    3, R.anim.exit_to_left, R.anim.enter_from_right);

        }
    }

    // Validate Page Three
    private void validatePageThree() {
        boolean isValidSiteAddress = false, isValidSiteLandMark = false, isValidSitePinCode = false, isValidSiteDistrict = false, isValidSiteVillage = false,
                isValidBillingAddress = false, isValidBillingLandMark = false, isValidBillingPinCode = false, isValidBillingDistrict = false,
                isValidBillingVillage = false;

        if (TextUtils.isEmpty(siteAddressLine1Str)) {
            siteAddressLine1InputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            siteAddressLine1InputLayout.setError(null);
            isValidSiteAddress = true;
        }

        if (TextUtils.isEmpty(siteLandmarkStr)) {
            siteLandmarkInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            siteLandmarkInputLayout.setError(null);
            isValidSiteLandMark = true;
        }

        if (TextUtils.isEmpty(sitePinCodeStr)) {
            sitePinCodeInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            sitePinCodeInputLayout.setError(null);
            isValidSitePinCode = true;
        }

        if (siteDistrictStr.equalsIgnoreCase("District*")) {
            TextView view = (TextView) siteDistrictSpinner.getSelectedView();
            view.setError(getResources().getString(R.string.select_options));
        } else {
            isValidSiteDistrict = true;
            TextView view = (TextView) siteDistrictSpinner.getSelectedView();
            view.setError(null);
        }

        if (siteVillageStr.equalsIgnoreCase("City/Village*")) {
            TextView view = (TextView) siteVillageSpinner.getSelectedView();
            view.setError(getResources().getString(R.string.select_options));
        } else {
            isValidSiteVillage = true;
            TextView view = (TextView) siteVillageSpinner.getSelectedView();
            view.setError(null);
        }

        if (TextUtils.isEmpty(billingAddressLine1Str)) {
            billingAddressLine1InputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            billingAddressLine1InputLayout.setError(null);
            isValidBillingAddress = true;
        }

        if (TextUtils.isEmpty(billingLandmarkStr)) {
            billingLandmarkInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            billingLandmarkInputLayout.setError(null);
            isValidBillingLandMark = true;
        }

        if (TextUtils.isEmpty(billingPinCodeStr)) {
            billingPinCodeInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            billingPinCodeInputLayout.setError(null);
            isValidBillingPinCode = true;
        }

        if (billingDistrictStr.equalsIgnoreCase("District*")) {
            TextView view = (TextView) billingDistrictSpinner.getSelectedView();
            view.setError(getResources().getString(R.string.select_options));
        } else {
            isValidBillingDistrict = true;
            TextView view = (TextView) billingDistrictSpinner.getSelectedView();
            view.setError(null);
        }

        if (billingVillageStr.equalsIgnoreCase("City/Village*")) {
            TextView view = (TextView) billingVillageSpinner.getSelectedView();
            view.setError(getResources().getString(R.string.select_options));
        } else {
            isValidBillingVillage = true;
            TextView view = (TextView) billingVillageSpinner.getSelectedView();
            view.setError(null);
        }

        if (isValidBillingAddress && isValidBillingLandMark && isValidBillingPinCode && isValidBillingDistrict && isValidBillingVillage && isValidSiteAddress
                && isValidSiteLandMark && isValidSitePinCode && isValidSiteDistrict && isValidSiteVillage) {
            formVisibility(pageThreeImageView, pageFourImageView, pageThreeRelativeView, pageFourRelativeView,
                    4, R.anim.exit_to_left, R.anim.enter_from_right);

            nextButton.setVisibility(View.GONE);
            submitButton.setVisibility(View.VISIBLE);
        }
    }

    // Copy Site Address
    private void copySiteAddress(boolean isChecked) {

        boolean isValidSiteAddress = false, isValidSiteLandMark = false, isValidSitePinCode = false, isValidSiteDistrict = false, isValidSiteVillage = false;

        if (TextUtils.isEmpty(siteAddressLine1Str)) {
            siteAddressLine1InputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            siteAddressLine1InputLayout.setError(null);
            isValidSiteAddress = true;
        }

        if (TextUtils.isEmpty(siteLandmarkStr)) {
            siteLandmarkInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            siteLandmarkInputLayout.setError(null);
            isValidSiteLandMark = true;
        }

        if (TextUtils.isEmpty(sitePinCodeStr)) {
            sitePinCodeInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            sitePinCodeInputLayout.setError(null);
            isValidSitePinCode = true;
        }

        if (siteDistrictStr.equalsIgnoreCase("District*")) {
            TextView view = (TextView) siteDistrictSpinner.getSelectedView();
            view.setError(getResources().getString(R.string.select_options));
        } else {
            isValidSiteDistrict = true;
            TextView view = (TextView) siteDistrictSpinner.getSelectedView();
            view.setError(null);
        }

        if (siteVillageStr.equalsIgnoreCase("City/Village*")) {
            TextView view = (TextView) siteVillageSpinner.getSelectedView();
            view.setError(getResources().getString(R.string.select_options));
        } else {
            isValidSiteVillage = true;
            TextView view = (TextView) siteVillageSpinner.getSelectedView();
            view.setError(null);
        }

        if (isValidSiteAddress && isValidSiteLandMark && isValidSitePinCode && isValidSiteDistrict && isValidSiteVillage) {
            if (isChecked) {
                int district = siteDistrictSpinner.getSelectedItemPosition();
                int village = siteVillageSpinner.getSelectedItemPosition();

                billingDistrictSpinner.setSelection(district);
                billingVillageSpinner.setSelection(village);

                billingAddressLie1EditText.setText(siteAddressLine1Str);
                billingLandmarkEditText.setText(siteLandmarkStr);
                billingPinCodeEditText.setText(sitePinCodeStr);

                if (!TextUtils.isEmpty(siteAddressLine2Str)) {
                    billingAddressLie2EditText.setText(siteAddressLine2Str);
                }

                if (!TextUtils.isEmpty(siteAddressLine2Str)) {
                    billingMarathiAddrEditText.setText(siteMarathiAddrStr);
                }

            } else {
                billingAddressLie1EditText.setText(null);
                billingLandmarkEditText.setText(null);
                billingPinCodeEditText.setText(null);
                billingAddressLie2EditText.setText(null);
                billingMarathiAddrEditText.setText(null);
                billingDistrictSpinner.setSelection(0);
                billingVillageSpinner.setSelection(0);
            }
        } else {
            billingAddressCheckBox.setChecked(false);
        }
    }

    // Validate Remark
    private void validateRemark() {
        boolean isValidRemark = false;

        if (TextUtils.isEmpty(remarkStr)) {
            remarkTextInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            remarkTextInputLayout.setError(null);
            isValidRemark = true;
        }

        if (isValidRemark) {

            MaterialDialog dialog = new MaterialDialog.Builder(mCon)
                    .title(getResources().getString(R.string.upload_documents))
                    .content(getResources().getString(R.string.upload_documents_right_now))
                    .contentColor(mCon.getResources().getColor(R.color.colorPrimary))
                    .canceledOnTouchOutside(false)
                    .positiveText(getResources().getString(R.string.yes))
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            startActivity(new Intent(mCon, UploadDocumentsActivity.class));
                            finish();
                        }
                    })
                    .negativeText(getResources().getString(R.string.cancel))
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            finish();
                        }
                    }).show();
        }
    }

    // Start Date Picker
    private void updateStartCalendar() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        startDateEditText.setText(sdf.format(startCalendar.getTime()));
    }

    // End Date Picker
    private void updateEndCalendar() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        endDateEditText.setText(sdf.format(endCalendar.getTime()));
    }

    // forms Visibility Function
    public void formVisibility(AppCompatImageView firstAppCompatImageView, AppCompatImageView
            secondAppCompatImageView, RelativeLayout formStart, RelativeLayout formEnd, int page,
                               int anim1, int anim2) {
        firstAppCompatImageView.setColorFilter(ContextCompat.getColor(mCon, R.color.Grey500), android.graphics.PorterDuff.Mode.SRC_IN);
        secondAppCompatImageView.setColorFilter(ContextCompat.getColor(mCon, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
        formStart.setVisibility(View.GONE);
        formStart.startAnimation(AnimationUtils.loadAnimation(mCon, anim1));
        formEnd.setVisibility(View.VISIBLE);
        formEnd.startAnimation(AnimationUtils.loadAnimation(mCon, anim2));
        pageCountTextView.setText("Page " + page + " of");
    }

    // Consumer Details View
    private void consumerDetailsView() {
        formVisibility(pageOneImageView, pageTwoImageView, pageOneRelativeView, pageTwoRelativeView,
                2, R.anim.exit_to_left, R.anim.enter_from_right);
        backButton.setVisibility(View.VISIBLE);
    }

    // Radio Button Checker
    public void RadioButtonChecker() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.permanentConnectionRadioButton) {
                    connectionDetailsCardView.setVisibility(View.GONE);
                    startDateEditText.setText(null);
                    endDateEditText.setText(null);
                    daysEditText.setText(null);
                    appFeeEditText.setText(null);
                    totalAmtEditText.setText(null);

                } else if (checkedId == R.id.temporaryConnectionRadioButton) {

                    connectionDetailsCardView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    // Set RecyclerView
    public void setRecycler(RecyclerView recycler) {
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(mCon));
    }

    // load RecyclerVIew
    public void loadRecycler() {
        for (int i = 1; i <= 5; i++) {
            DocumentsModel documentsModel = new DocumentsModel("Proof " + i);
            documentsModelList.add(documentsModel);
            documentsAdapter.addList(documentsModelList);
            idProofRecyclerView.setAdapter(documentsAdapter);
            addressProofRecyclerView.setAdapter(documentsAdapter);
            mandatoryDocumentsRecyclerView.setAdapter(documentsAdapter);
            nocRecyclerView.setAdapter(documentsAdapter);
        }
    }

    // Expand And Collapse Code
    private static void expand(final View v) {
        v.measure(AbsoluteLayout.LayoutParams.MATCH_PARENT,
                AbsoluteLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for
        // views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime,
                                               Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? AbsoluteLayout.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (targetHeight /
                v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    private static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime,
                                               Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int)
                            (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (initialHeight /
                v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }
}
