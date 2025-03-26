package elink.suezShimla.water.crm.DisconnectionAndDismantling.ReConnectionWorkClosing;

import android.app.DatePickerDialog;
import android.content.Context;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import elink.suezShimla.water.crm.R;

public class ReConnectionWorkClosingActivity extends AppCompatActivity {
    private Context mCon;
    private RecyclerView reConnectionWorkClosingRecyclerView;

    private Calendar fromDateCalendar, toDateCalendar;

    private BottomSheetDialog sheetDialog;
    private AppCompatImageView closeImageView;
    private TextInputLayout fromDateInputLayout, toDateInputLayout;
    private TextInputEditText fromDateEditText, toDateEditText, consumerNoInputEditText;
    private MaterialButton showButton, clearButton;
    private String fromDateStr, toDateStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_connection_work_closing);
        // prevent ss and hide content when app is on background
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mCon = this;

        getSupportActionBar().setTitle(getResources().getString(R.string.re_connection_work_closing));

        fromDateCalendar = Calendar.getInstance();
        toDateCalendar = Calendar.getInstance();

        sheetDialog = new BottomSheetDialog(mCon);

        reConnectionWorkClosingRecyclerView = findViewById(R.id.reConnectionWorkClosingRecyclerView);
        reConnectionWorkClosingRecyclerView.setHasFixedSize(true);
        reConnectionWorkClosingRecyclerView.setLayoutManager(new LinearLayoutManager(mCon));

        reConnWorkClosingBottomDialog();
    }

    private void reConnWorkClosingBottomDialog(){
        View sheetView = Objects.requireNonNull(getLayoutInflater().inflate(R.layout.bottom_sheet_re_connection_work_closing, null));
        sheetDialog.setContentView(sheetView);
        sheetDialog.show();
        sheetDialog.setCancelable(false);

        closeImageView = sheetView.findViewById(R.id.closeImageView);
        fromDateInputLayout = sheetView.findViewById(R.id.fromDateInputLayout);
        toDateInputLayout = sheetView.findViewById(R.id.toDateInputLayout);
        fromDateEditText = sheetView.findViewById(R.id.fromDateEditText);
        toDateEditText = sheetView.findViewById(R.id.toDateEditText);
        consumerNoInputEditText = sheetView.findViewById(R.id.consumerNoInputEditText);
        showButton = sheetView.findViewById(R.id.showButton);
        clearButton = sheetView.findViewById(R.id.clearButton);

        final DatePickerDialog.OnDateSetListener fromDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                fromDateCalendar.set(Calendar.YEAR, year);
                fromDateCalendar.set(Calendar.MONTH, monthOfYear);
                fromDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateFromDate();
            }
        };

        final DatePickerDialog.OnDateSetListener toDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                toDateCalendar.set(Calendar.YEAR, year);
                toDateCalendar.set(Calendar.MONTH, monthOfYear);
                toDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateToDate();
            }
        };

        fromDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(mCon, fromDate, fromDateCalendar.get(Calendar.YEAR),
                        fromDateCalendar.get(Calendar.MONTH), fromDateCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
                datePickerDialog.show();
            }
        });

        toDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(mCon, toDate, toDateCalendar.get(Calendar.YEAR),
                        toDateCalendar.get(Calendar.MONTH), toDateCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
                datePickerDialog.show();
            }
        });

        closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDateStr = fromDateEditText.getText().toString().trim();
                toDateStr = toDateEditText.getText().toString().trim();

                validate();
            }
        });
    }

    private void updateFromDate(){
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        fromDateEditText.setText(sdf.format(fromDateCalendar.getTime()));
    }

    private void updateToDate(){
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        toDateEditText.setText(sdf.format(toDateCalendar.getTime()));
    }

    private void validate(){
        boolean isValidFromDate = false, isValidToDate = false;

        if(TextUtils.isEmpty(fromDateStr)){
            fromDateInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
            isValidFromDate = false;
        }else {
            fromDateInputLayout.setError(null);
            isValidFromDate = true;
        }

        if(TextUtils.isEmpty(toDateStr)){
            toDateInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
            isValidToDate = false;
        }else {
            toDateInputLayout.setError(null);
            isValidToDate = true;
        }

        if(isValidFromDate && isValidToDate){
            reConnWorkClosingData();
            sheetDialog.cancel();
        }
    }

    private void reConnWorkClosingData(){

    }
}
