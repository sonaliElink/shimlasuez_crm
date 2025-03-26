package elink.suezShimla.water.crm.ConnectionRequest.Activity.TechnicalFeasibilityPlumberWork.Activity;

import android.content.Context;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

import elink.suezShimla.water.crm.ConnectionRequest.Activity.TechnicalFeasibilityPlumberWork.Adapter.SetEmployeeAdapter;
import elink.suezShimla.water.crm.ConnectionRequest.Activity.TechnicalFeasibilityPlumberWork.Model.SetEmployeeModel;
import elink.suezShimla.water.crm.R;

public class FeasibilityPlumberWorkDetailsActivity extends AppCompatActivity {

    private Context mCon;

    private ActionBar actionBar;

    private BottomSheetDialog setEmployeeDialog, setJobCard;
    private MaterialButton selectEmployeeButton, jobCardButton, submitButton, calculateButton, clearButton, jobSubmitButton;
    private AppCompatImageView employeeCloseImageView, jobCloseImageView;

    private RadioGroup radioGroup;
    private RadioButton areaRadioButton, departmentRadioButton;
    private AppCompatSpinner selectAreaSpinner, mainPipelineSpinner, serviceLineSpinner;

    private TextView selectEmployeeLabel;
    private View dividerView;

    private TextInputLayout boringChargesInputLayout, securityDepositInputLayout, newPipelineExpenditureInputLayout, visitingChargesInputLayout, meterCostInputLayout, bituminousFirstNumberInputLayout,
            bituminousSecondNumberInputLayout, bituminousTotalInputLayout, reinforceFirstNumberInputLayout, reinforceSecondNumberInputLayout, reinforceTotalInputLayout, waterFirstNumberInputLayout,
            waterSecondNumberInputLayout, waterTotalInputLayout, totalAmountInputLayout;

    private TextInputEditText boringChargesEditText, securityDepositEditText, newPipelineExpenditureEditText, visitingChargesEditText, meterCostEditText, bituminousFirstNumberEditText,
            bituminousSecondNumberEditText, bituminousTotalEditText, reinforceFirstNumberEditText, reinforceSecondNumberEditText, reinforceTotalEditText, waterFirstNumberEditText,
            waterSecondNumberEditText, waterTotalEditText, totalAmountEditText;

    private RecyclerView setEmployeeRecyclerView;
    private SetEmployeeAdapter setEmployeeAdapter;
    private List<SetEmployeeModel> setEmployeeModelList;

    private String selectAreaStr, mainPipelineStr, serviceLineStr, boringChargesStr, securityDepositStr, newPipelineExpenditureStr, visitingChargesStr, meterCostStr, bituminousFirstNumberStr,
            bituminousSecondNumberStr, bituminousTotalStr, reinforceFirstNumberStr, reinforceSecondNumberStr, reinforceTotalStr, waterFirstNumberStr,
            waterSecondNumberStr, waterTotalStr, totalAmountStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feasibility_plumber_work_details);

        mCon = this;

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getResources().getString(R.string._19_03_1_94));
        }

        setEmployeeDialog = new BottomSheetDialog(Objects.requireNonNull(mCon));
        setJobCard = new BottomSheetDialog(Objects.requireNonNull(mCon));
        selectEmployeeButton = findViewById(R.id.selectEmployeeButton);
        jobCardButton = findViewById(R.id.jobCardButton);
        selectEmployeeLabel = findViewById(R.id.selectEmployeeLabel);
        dividerView = findViewById(R.id.dividerView);

        selectEmployeeLabel.setVisibility(View.GONE);
        dividerView.setVisibility(View.GONE);

        setEmployeeRecyclerView = findViewById(R.id.setEmployeeRecyclerView);
        setEmployeeAdapter = new SetEmployeeAdapter(mCon);


        setEmployeeRecyclerView.setHasFixedSize(true);
        setEmployeeRecyclerView.setLayoutManager(new GridLayoutManager(mCon, 2));

        SelectEmployeeDialog();
        setJobCardDialog();

        selectEmployeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEmployeeDialog.show();
            }
        });

        jobCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setJobCard.show();
            }
        });

    }

    // Set Employee Dialog
    public void SelectEmployeeDialog() {
        View sheetView = Objects.requireNonNull(getLayoutInflater().inflate(R.layout.bottom_dialog_select_employee, null));
        setEmployeeDialog.setContentView(sheetView);
        setEmployeeDialog.setCancelable(false);

        employeeCloseImageView = sheetView.findViewById(R.id.employeeCloseImageView);
        submitButton = sheetView.findViewById(R.id.submitButton);
        radioGroup = sheetView.findViewById(R.id.radioGroup);
        areaRadioButton = sheetView.findViewById(R.id.areaRadioButton);
        selectAreaSpinner = sheetView.findViewById(R.id.selectAreaSpinner);
        departmentRadioButton = sheetView.findViewById(R.id.departmentRadioButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAreaStr = selectAreaSpinner.getSelectedItem().toString().trim();
                validateSelectEmployee();
            }
        });

        employeeCloseImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEmployeeDialog.cancel();
            }
        });
    }

    // Set Job Dialog
    public void setJobCardDialog() {
        View sheetView = Objects.requireNonNull(getLayoutInflater().inflate(R.layout.bottom_dialog_job_card, null));
        setJobCard.setContentView(sheetView);
        setJobCard.setCancelable(false);

        jobCloseImageView = sheetView.findViewById(R.id.jobCloseImageView);
        mainPipelineSpinner = sheetView.findViewById(R.id.mainPipelineSpinner);
        serviceLineSpinner = sheetView.findViewById(R.id.serviceLineSpinner);
        calculateButton = sheetView.findViewById(R.id.calculateButton);
        jobSubmitButton = sheetView.findViewById(R.id.jobSubmitButton);

        boringChargesInputLayout = sheetView.findViewById(R.id.boringChargesInputLayout);
        securityDepositInputLayout = sheetView.findViewById(R.id.securityDepositInputLayout);
        newPipelineExpenditureInputLayout = sheetView.findViewById(R.id.newPipelineExpenditureInputLayout);
        visitingChargesInputLayout = sheetView.findViewById(R.id.visitingChargesInputLayout);
        meterCostInputLayout = sheetView.findViewById(R.id.meterCostInputLayout);
        bituminousFirstNumberInputLayout = sheetView.findViewById(R.id.bituminousFirstNumberInputLayout);
        bituminousSecondNumberInputLayout = sheetView.findViewById(R.id.bituminousSecondNumberInputLayout);
        bituminousTotalInputLayout = sheetView.findViewById(R.id.bituminousTotalInputLayout);
        reinforceFirstNumberInputLayout = sheetView.findViewById(R.id.reinforceFirstNumberInputLayout);
        reinforceSecondNumberInputLayout = sheetView.findViewById(R.id.reinforceSecondNumberInputLayout);
        reinforceTotalInputLayout = sheetView.findViewById(R.id.reinforceTotalInputLayout);
        waterFirstNumberInputLayout = sheetView.findViewById(R.id.waterFirstNumberInputLayout);
        waterSecondNumberInputLayout = sheetView.findViewById(R.id.waterSecondNumberInputLayout);
        waterTotalInputLayout = sheetView.findViewById(R.id.waterTotalInputLayout);
        totalAmountInputLayout = sheetView.findViewById(R.id.totalAmountInputLayout);

        boringChargesEditText = sheetView.findViewById(R.id.boringChargesEditText);
        securityDepositEditText = sheetView.findViewById(R.id.securityDepositEditText);
        newPipelineExpenditureEditText = sheetView.findViewById(R.id.newPipelineExpenditureEditText);
        visitingChargesEditText = sheetView.findViewById(R.id.visitingChargesEditText);
        meterCostEditText = sheetView.findViewById(R.id.meterCostEditText);
        bituminousFirstNumberEditText = sheetView.findViewById(R.id.bituminousFirstNumberEditText);
        bituminousSecondNumberEditText = sheetView.findViewById(R.id.bituminousSecondNumberEditText);
        bituminousTotalEditText = sheetView.findViewById(R.id.bituminousTotalEditText);
        reinforceFirstNumberEditText = sheetView.findViewById(R.id.reinforceFirstNumberEditText);
        reinforceSecondNumberEditText = sheetView.findViewById(R.id.reinforceSecondNumberEditText);
        reinforceTotalEditText = sheetView.findViewById(R.id.reinforceTotalEditText);
        waterFirstNumberEditText = sheetView.findViewById(R.id.waterFirstNumberEditText);
        waterSecondNumberEditText = sheetView.findViewById(R.id.waterSecondNumberEditText);
        waterTotalEditText = sheetView.findViewById(R.id.waterTotalEditText);
        totalAmountEditText = sheetView.findViewById(R.id.totalAmountEditText);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bituminousFirstNumberStr = bituminousFirstNumberEditText.getText().toString().trim();
                bituminousSecondNumberStr = bituminousSecondNumberEditText.getText().toString().trim();
                bituminousTotalStr = bituminousTotalEditText.getText().toString().trim();
                reinforceFirstNumberStr = reinforceFirstNumberEditText.getText().toString().trim();
                reinforceSecondNumberStr = reinforceSecondNumberEditText.getText().toString().trim();
                reinforceTotalStr = reinforceTotalEditText.getText().toString().trim();
                waterFirstNumberStr = waterFirstNumberEditText.getText().toString().trim();
                waterSecondNumberStr = waterSecondNumberEditText.getText().toString().trim();
                waterTotalStr = waterTotalEditText.getText().toString().trim();

                validateCalculate();
            }
        });

        jobSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mainPipelineStr = mainPipelineSpinner.getSelectedItem().toString().trim();
                serviceLineStr = serviceLineSpinner.getSelectedItem().toString().trim();
                boringChargesStr = boringChargesEditText.getText().toString().trim();
                securityDepositStr = securityDepositEditText.getText().toString().trim();
                newPipelineExpenditureStr = newPipelineExpenditureEditText.getText().toString().trim();
                visitingChargesStr = visitingChargesEditText.getText().toString().trim();
                meterCostStr = meterCostEditText.getText().toString().trim();
                totalAmountStr = totalAmountEditText.getText().toString().trim();

                validateJobSubmit();
            }
        });

        jobCloseImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setJobCard.cancel();
            }
        });
    }

    // Validate select Employee
    private void validateSelectEmployee() {

        boolean isValidRadioGroup = false, isValidArea = false;

        if (radioGroup.getCheckedRadioButtonId() == -1) {
            areaRadioButton.setError(getResources().getString(R.string.select_options));
            departmentRadioButton.setError(getResources().getString(R.string.select_options));
        } else {
            isValidRadioGroup = true;
            areaRadioButton.setError(null);
            departmentRadioButton.setError(null);
        }


        if (selectAreaStr.equalsIgnoreCase("Select Area*")) {
            TextView view = (TextView) selectAreaSpinner.getSelectedView();
            view.setError(getResources().getString(R.string.select_options));
        } else {
            isValidArea = true;
            TextView view = (TextView) selectAreaSpinner.getSelectedView();
            view.setError(null);
        }

        if (isValidRadioGroup && isValidArea) {
            setEmployeeRecycler();
        }
    }

    // Validate Calculate Job
    private void validateCalculate() {
        boolean isValidBituminousFirstNumber = false, isValidBituminousSecondNumber = false, isValidBituminousTotal = false, isValidReinforceFirstNumber = false,
                isValidReinforceSecondNumber = false, isValidReinforceTotal = false, isValidWaterFirstNumber = false, isValidWaterSecondNumber = false, isValidWaterTotal = false;

        if (TextUtils.isEmpty(bituminousFirstNumberStr)) {
            bituminousFirstNumberInputLayout.setError(getResources().getString(R.string.empty));
        } else {
            bituminousFirstNumberInputLayout.setError(null);
            isValidBituminousFirstNumber = true;
        }

        if (TextUtils.isEmpty(bituminousSecondNumberStr)) {
            bituminousSecondNumberInputLayout.setError(getResources().getString(R.string.empty));
        } else {
            bituminousSecondNumberInputLayout.setError(null);
            isValidBituminousSecondNumber = true;
        }

        if (TextUtils.isEmpty(bituminousTotalStr)) {
            bituminousTotalInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            bituminousTotalInputLayout.setError(null);
            isValidBituminousTotal = true;
        }

        if (TextUtils.isEmpty(reinforceFirstNumberStr)) {
            reinforceFirstNumberInputLayout.setError(getResources().getString(R.string.empty));
        } else {
            reinforceFirstNumberInputLayout.setError(null);
            isValidReinforceFirstNumber = true;
        }

        if (TextUtils.isEmpty(reinforceSecondNumberStr)) {
            reinforceSecondNumberInputLayout.setError(getResources().getString(R.string.empty));
        } else {
            reinforceSecondNumberInputLayout.setError(null);
            isValidReinforceSecondNumber = true;
        }

        if (TextUtils.isEmpty(reinforceTotalStr)) {
            reinforceTotalInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            reinforceTotalInputLayout.setError(null);
            isValidReinforceTotal = true;
        }

        if (TextUtils.isEmpty(waterFirstNumberStr)) {
            waterFirstNumberInputLayout.setError(getResources().getString(R.string.empty));
        } else {
            waterFirstNumberInputLayout.setError(null);
            isValidWaterFirstNumber = true;
        }

        if (TextUtils.isEmpty(waterSecondNumberStr)) {
            waterSecondNumberInputLayout.setError(getResources().getString(R.string.empty));
        } else {
            waterSecondNumberInputLayout.setError(null);
            isValidWaterSecondNumber = true;
        }

        if (TextUtils.isEmpty(waterTotalStr)) {
            waterTotalInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            waterTotalInputLayout.setError(null);
            isValidWaterTotal = true;
        }

        if (isValidBituminousFirstNumber && isValidBituminousSecondNumber && isValidBituminousTotal && isValidReinforceFirstNumber && isValidReinforceSecondNumber &&
                isValidReinforceTotal && isValidWaterFirstNumber && isValidWaterSecondNumber && isValidWaterTotal) {
            totalAmountEditText.setText("0");
        }
    }

    // Validate Job Submit
    private void validateJobSubmit() {
        boolean isValidMainPipeline = false, isValidServiceLine = false, isValidBoringCharges = false, isValidSecurityDeposit = false, isValidNewPipelineExpenditure = false,
                isValidVisitingCharges = false, isValidMeterCost = false, isValidTotalAmount = false;


        if (mainPipelineStr.equalsIgnoreCase("Main Pipeline of Municipal Corporation*")) {
            TextView view = (TextView) mainPipelineSpinner.getSelectedView();
            view.setError(getResources().getString(R.string.select_options));
        } else {
            isValidMainPipeline = true;
            TextView view = (TextView) mainPipelineSpinner.getSelectedView();
            view.setError(null);
        }

        if (serviceLineStr.equalsIgnoreCase("Service Line of Municipal Corporation*")) {
            TextView view = (TextView) serviceLineSpinner.getSelectedView();
            view.setError(getResources().getString(R.string.select_options));
        } else {
            isValidServiceLine = true;
            TextView view = (TextView) serviceLineSpinner.getSelectedView();
            view.setError(null);
        }

        if (TextUtils.isEmpty(boringChargesStr)) {
            boringChargesInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            boringChargesInputLayout.setError(null);
            isValidBoringCharges = true;
        }

        if (TextUtils.isEmpty(securityDepositStr)) {
            securityDepositInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            securityDepositInputLayout.setError(null);
            isValidSecurityDeposit = true;
        }

        if (TextUtils.isEmpty(newPipelineExpenditureStr)) {
            newPipelineExpenditureInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            newPipelineExpenditureInputLayout.setError(null);
            isValidNewPipelineExpenditure = true;
        }

        if (TextUtils.isEmpty(visitingChargesStr)) {
            visitingChargesInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            visitingChargesInputLayout.setError(null);
            isValidVisitingCharges = true;
        }

        if (TextUtils.isEmpty(meterCostStr)) {
            meterCostInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            meterCostInputLayout.setError(null);
            isValidMeterCost = true;
        }

        if (TextUtils.isEmpty(totalAmountStr)) {
            totalAmountInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            totalAmountInputLayout.setError(null);
            isValidTotalAmount = true;
        }

        if (isValidMainPipeline && isValidServiceLine && isValidBoringCharges && isValidSecurityDeposit && isValidNewPipelineExpenditure && isValidVisitingCharges &&
                isValidMeterCost && isValidTotalAmount) {
            setJobCard.cancel();
        }

    }

    // Load Recycler Data
    private void setEmployeeRecycler() {
        /*setEmployeeModelList = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            SetEmployeeModel model = new SetEmployeeModel("00" + i, "Kruti Trivedi" + i);
            setEmployeeModelList.add(model);
            setEmployeeAdapter.addList(setEmployeeModelList);
            setEmployeeRecyclerView.setAdapter(setEmployeeAdapter);
            selectEmployeeLabel.setVisibility(View.VISIBLE);
            dividerView.setVisibility(View.VISIBLE);
            setEmployeeDialog.cancel();
        }*/
    }
}
