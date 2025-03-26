package elink.suezShimla.water.crm.ConnectionRequest.Activity.TechnicalFeasibilityPlumberWork;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import elink.suezShimla.water.crm.ConnectionRequest.Activity.TechnicalFeasibilityPlumberWork.Adapter.TechnicalFeasibilityPlumberWorkAdapter;
import elink.suezShimla.water.crm.ConnectionRequest.Activity.TechnicalFeasibilityPlumberWork.Model.TechnicalFeasibilityPlumberWorkModel;
import elink.suezShimla.water.crm.R;

public class TechnicalFeasibilityPlumberWorkActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private Context mCon;

    private BottomSheetDialog sheetBehavior;

    private SwipeRefreshLayout swipeRefreshLayout;

    private Calendar fromDateCalendar, toDateCalendar;

    private TextInputLayout fromDateInputLayout, toDateInputLayout;
    private TextInputEditText fromDateEditText, toDateEditText;

    private MaterialButton showButton;

    private AppCompatImageView closeImageView;
    private AppCompatSpinner proceedToSpinner;

    private RadioGroup radioGroup;
    private RadioButton workAllocationRadioButton, workCompletionRadioButton;

    private RecyclerView technicalRecycler;
    private TechnicalFeasibilityPlumberWorkAdapter feasibilityPlumberWorkAdapter;
    private List<TechnicalFeasibilityPlumberWorkModel> feasibilityPlumberWorkModelList;

    private String fromDateStr, toDateStr, proceedToStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technical_feasibility_plumber_work);

        mCon = this;

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark, android.R.color.holo_red_dark);

        fromDateCalendar = Calendar.getInstance();
        toDateCalendar = Calendar.getInstance();

        sheetBehavior = new BottomSheetDialog(Objects.requireNonNull(mCon));

        technicalRecycler = findViewById(R.id.technicalRecycler);
        feasibilityPlumberWorkAdapter = new TechnicalFeasibilityPlumberWorkAdapter(mCon);

        technicalRecycler.setHasFixedSize(true);
        technicalRecycler.setLayoutManager(new LinearLayoutManager(mCon));

        filterBottomSheetDialog();
    }

    private void filterBottomSheetDialog() {

        @SuppressLint("InflateParams") View sheetView = Objects.requireNonNull(getLayoutInflater().inflate(R.layout.bottom_sheet_techinal_feasibility_plumber, null));
        sheetBehavior.setContentView(sheetView);

        closeImageView = sheetView.findViewById(R.id.employeeCloseImageView);
        showButton = sheetView.findViewById(R.id.showButton);
        proceedToSpinner = sheetView.findViewById(R.id.proceedToSpinner);
        radioGroup = sheetView.findViewById(R.id.radioGroup);
        workAllocationRadioButton = sheetView.findViewById(R.id.workAllocationRadioButton);
        workCompletionRadioButton = sheetView.findViewById(R.id.workCompletionRadioButton);

        fromDateInputLayout = sheetView.findViewById(R.id.fromDateInputLayout);
        toDateInputLayout = sheetView.findViewById(R.id.toDateInputLayout);
        fromDateEditText = sheetView.findViewById(R.id.fromDateEditText);
        toDateEditText = sheetView.findViewById(R.id.toDateEditText);

        sheetBehavior.setCanceledOnTouchOutside(false);
        sheetBehavior.setCancelable(false);
        sheetBehavior.show();

        final DatePickerDialog.OnDateSetListener fromCalendarDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                fromDateCalendar.set(Calendar.YEAR, year);
                fromDateCalendar.set(Calendar.MONTH, monthOfYear);
                fromDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateFromDateCalendar();
            }
        };

        final DatePickerDialog.OnDateSetListener toCalendarDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                toDateCalendar.set(Calendar.YEAR, year);
                toDateCalendar.set(Calendar.MONTH, monthOfYear);
                toDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateToDateCalendar();
            }
        };

        fromDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(mCon, fromCalendarDate, fromDateCalendar
                        .get(Calendar.YEAR), fromDateCalendar.get(Calendar.MONTH),
                        fromDateCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
                datePickerDialog.show();
            }
        });

        toDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(mCon, toCalendarDate, toDateCalendar
                        .get(Calendar.YEAR), toDateCalendar.get(Calendar.MONTH),
                        toDateCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
                datePickerDialog.show();
            }
        });

        closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (feasibilityPlumberWorkModelList != null) {
                    sheetBehavior.cancel();
                } else {
                    finish();
                    Toast.makeText(mCon, "Technical Feasibility or Plumber Work", Toast.LENGTH_SHORT).show();
                }
            }
        });

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fromDateStr = fromDateEditText.getText().toString().trim();
                toDateStr = toDateEditText.getText().toString().trim();
                proceedToStr = proceedToSpinner.getSelectedItem().toString().trim();
                validateApplicationRequest();
            }
        });
    }

    // Validate Filter Request
    private void validateApplicationRequest() {


        boolean isValidFromDate = false, isValidToDate = false, isValidProceedSpinner = false, isValidRadioGroup = false;

        if (TextUtils.isEmpty(fromDateStr)) {
            fromDateInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            fromDateInputLayout.setError(null);
            isValidFromDate = true;
        }

        if (TextUtils.isEmpty(toDateStr)) {
            toDateInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            toDateInputLayout.setError(null);
            isValidToDate = true;
        }

        if (proceedToStr.equalsIgnoreCase("Proceed To*")) {
            TextView view = (TextView) proceedToSpinner.getSelectedView();
            view.setError(getResources().getString(R.string.select_options));
        } else {
            isValidProceedSpinner = true;
            TextView view = (TextView) proceedToSpinner.getSelectedView();
            view.setError(null);
        }

        if (radioGroup.getCheckedRadioButtonId() == -1) {
            workAllocationRadioButton.setError(getResources().getString(R.string.select_options));
            workCompletionRadioButton.setError(getResources().getString(R.string.select_options));
        } else {
            isValidRadioGroup = true;
            workAllocationRadioButton.setError(null);
            workCompletionRadioButton.setError(null);
        }

        if (isValidFromDate && isValidToDate && isValidProceedSpinner && isValidRadioGroup) {
            sheetBehavior.cancel();
            loadFeasibilityPlumberWork();
        }
    }

    // From Date Picker
    private void updateFromDateCalendar() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        fromDateEditText.setText(sdf.format(fromDateCalendar.getTime()));
    }

    // To Date Picker
    private void updateToDateCalendar() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        toDateEditText.setText(sdf.format(toDateCalendar.getTime()));
    }

    // Load Recycler Data
    private void loadFeasibilityPlumberWork() {
        feasibilityPlumberWorkModelList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            TechnicalFeasibilityPlumberWorkModel model = new TechnicalFeasibilityPlumberWorkModel("19/03/1/9" + i, "1" + i + "Mar 2019 ", "MR. SOURABH BABAN JADHAV", "945353453" + i, "HILL TOP,MALKAPUR,HILLTOP-44310" + i);
            feasibilityPlumberWorkModelList.add(model);
            feasibilityPlumberWorkAdapter.addList(feasibilityPlumberWorkModelList);
            technicalRecycler.setAdapter(feasibilityPlumberWorkAdapter);
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.filter_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_filter) {
            sheetBehavior.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        loadFeasibilityPlumberWork();
    }
}
