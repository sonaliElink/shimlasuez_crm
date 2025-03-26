package elink.suezShimla.water.crm.MeterManagementSystem.MeterInstallationReplacementPrint;

import android.app.DatePickerDialog;
import android.content.Context;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.MeterManagementSystem.MeterInstallationReplacementPrint.Adapter.MeterInstallationReplacementPrintAdapter;
import elink.suezShimla.water.crm.MeterManagementSystem.MeterInstallationReplacementPrint.Model.MeterInstallationReplacementPrintModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;

public class MeterInstallationReplacementPrint extends AppCompatActivity {
    private Context mCon;

    private BottomSheetDialog sheetDialog;
    private RecyclerView meterInstallationReplacementRecyclerView;
    private MeterInstallationReplacementPrintAdapter meterInstallationReplacementPrintAdapter;
    private List<MeterInstallationReplacementPrintModel> meterInstallationReplacementPrintModels = new ArrayList<>();

    private Calendar fromCalendarDate, toCalendarDate;

    private ImageView closeImageView;
    private MaterialButton showButton;
    private AppCompatSpinner requestTypeSpinner;
    private TextInputLayout fromDateTextInputLayout, toDateTextInputLayout;
    private TextInputEditText fromDateInputEditText, toDateInputEditText;
    String requestTypeStr, fromDateStr, toDateStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meter_installation_replacement_print);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mCon = this;
        sheetDialog = new BottomSheetDialog(mCon);

        fromCalendarDate = Calendar.getInstance();
        toCalendarDate = Calendar.getInstance();

        meterInstallationReplacementRecyclerView = findViewById(R.id.meterInstallationReplacementRecyclerView);
        meterInstallationReplacementRecyclerView.setHasFixedSize(true);
        meterInstallationReplacementRecyclerView.setLayoutManager(new LinearLayoutManager(mCon));

        meterInstallationReplacementPrintAdapter = new MeterInstallationReplacementPrintAdapter(mCon);

        filterBottomSheet();

    }

    private void filterBottomSheet() {
        View sheetView = Objects.requireNonNull(getLayoutInflater().inflate(R.layout.bottom_sheet_meter_installation_replacement_filter, null));
        sheetDialog.setContentView(sheetView);
        sheetDialog.show();
        sheetDialog.setCanceledOnTouchOutside(false);

        fromDateTextInputLayout = sheetView.findViewById(R.id.fromDateTextInputLayout);
        toDateTextInputLayout = sheetView.findViewById(R.id.toDateTextInputLayout);
        fromDateInputEditText = sheetView.findViewById(R.id.fromDateInputEditText);
        toDateInputEditText = sheetView.findViewById(R.id.toDateInputEditText);
        closeImageView = sheetView.findViewById(R.id.closeImageView);
        showButton = sheetView.findViewById(R.id.showButton);
        requestTypeSpinner = sheetView.findViewById(R.id.requestTypeSpinner);

        final DatePickerDialog.OnDateSetListener fromDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                fromCalendarDate.set(Calendar.YEAR, year);
                fromCalendarDate.set(Calendar.MONTH, monthOfYear);
                fromCalendarDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateFromDate();
            }
        };

        final DatePickerDialog.OnDateSetListener toDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                toCalendarDate.set(Calendar.YEAR, year);
                toCalendarDate.set(Calendar.MONTH, monthOfYear);
                toCalendarDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateToDate();
            }
        };

        fromDateInputEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(mCon, fromDate, fromCalendarDate.get(Calendar.YEAR),
                        fromCalendarDate.get(Calendar.MONTH), fromCalendarDate.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
                datePickerDialog.show();
            }
        });

        toDateInputEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(mCon, toDate, toCalendarDate.get(Calendar.YEAR),
                        toCalendarDate.get(Calendar.MONTH), toCalendarDate.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
                datePickerDialog.show();
            }
        });

        closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Toast.makeText(mCon, "Plz enter details to view Meter Installation & Replacement", Toast.LENGTH_SHORT).show();
            }
        });

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fromDateStr = fromDateInputEditText.getText().toString().trim();
                toDateStr = toDateInputEditText.getText().toString().trim();
                requestTypeStr = requestTypeSpinner.getSelectedItem().toString().trim();

                validate();
            }

        });
    }

    private void updateFromDate(){
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        fromDateInputEditText.setText(sdf.format(fromCalendarDate.getTime()));
    }

    private void updateToDate(){
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        toDateInputEditText.setText(sdf.format(toCalendarDate.getTime()));
    }

    private void validate() {
        boolean isValidFromDate = false, isValidToDate = false, isValidRequestType = false;

        if (TextUtils.isEmpty(fromDateStr)) {
            isValidFromDate = false;
            fromDateTextInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            isValidFromDate = true;
            fromDateTextInputLayout.setError(null);
        }

        if (TextUtils.isEmpty(toDateStr)) {
            isValidToDate = false;
            toDateTextInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            isValidToDate = true;
            toDateTextInputLayout.setError(null);
        }

        if (requestTypeStr.equalsIgnoreCase("Request Type *")) {
            TextView view = (TextView) requestTypeSpinner.getSelectedView();
            view.setError(getResources().getString(R.string.select_options));
        } else {
            isValidRequestType = true;
            TextView view = (TextView) requestTypeSpinner.getSelectedView();
            view.setError(null);
        }

        if (isValidFromDate && isValidToDate && isValidRequestType) {
            meterInstallationReplacementData();
            sheetDialog.cancel();
        }
    }

    private void meterInstallationReplacementData(){
        MeterInstallationReplacementPrintModel data = new MeterInstallationReplacementPrintModel("021521020040", "NON TOD", "19/03/1/80", "Application", "-NA-", "-NA-",
                "0", "00456456","NON TOD", "0","3", "New Meter", "22/04/2019",
                "Yes", "-NA-");
        meterInstallationReplacementPrintModels.add(data);
        meterInstallationReplacementPrintAdapter.addList(meterInstallationReplacementPrintModels);
        meterInstallationReplacementRecyclerView.setAdapter(meterInstallationReplacementPrintAdapter);
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
        ((App) this.getApplication()).startActivityTransitionTimer();
    }
}
