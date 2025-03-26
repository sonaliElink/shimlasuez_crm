package elink.suezShimla.water.crm.NoConsumerComplaint.NCAllocation.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import elink.suezShimla.water.crm.NoConsumerComplaint.NCAllocation.Adapter.NCWorkAllocationAdapter;
import elink.suezShimla.water.crm.NoConsumerComplaint.NCAllocation.Model.NCAllocationModel;
import elink.suezShimla.water.crm.R;


public class NCWorkAllocationFragment extends Fragment {
    private Context mCon;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView allocationRecyclerView;
    private List<NCAllocationModel>AllocationModelList;
    private NCWorkAllocationAdapter ncWorkAllocationAdapter;
    private BottomSheetDialog sheetBehavior;

    private Calendar fromDateCalendar, toDateCalendar;

    private MaterialButton showButton;
    private AppCompatImageView closeImageView;
    private TextInputLayout fromDateInputLayout, toDateInputLayout;
    private TextInputEditText fromDateEditText, toDateEditText;
    private String fromDateStr, toDateStr;

    public NCWorkAllocationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ncwork_allocation, container, false);

        mCon = getActivity();

        fromDateCalendar = Calendar.getInstance();
        toDateCalendar = Calendar.getInstance();

        sheetBehavior = new BottomSheetDialog(Objects.requireNonNull(mCon));


        allocationRecyclerView = view.findViewById(R.id.allocationRecyclerView);
        allocationRecyclerView.setHasFixedSize(true);
        allocationRecyclerView.setLayoutManager(new LinearLayoutManager(mCon));

        ncWorkAllocationAdapter = new NCWorkAllocationAdapter(mCon);

        workAllocationBottomFilterDialog();

        return view;
    }

    // Bottom Dialog Filter
    public void workAllocationBottomFilterDialog() {
        View sheetView = Objects.requireNonNull(getActivity()).getLayoutInflater().inflate(R.layout.bottom_sheet_work_allocation_filter, null);
        sheetBehavior.setContentView(sheetView);
        sheetBehavior.setCancelable(false);

        closeImageView = sheetView.findViewById(R.id.closeImageView);
        showButton = sheetView.findViewById(R.id.showButton);
        fromDateInputLayout = sheetView.findViewById(R.id.fromDateInputLayout);
        toDateInputLayout = sheetView.findViewById(R.id.toDateInputLayout);
        fromDateEditText = sheetView.findViewById(R.id.fromDateEditText);
        toDateEditText = sheetView.findViewById(R.id.toDateEditText);

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

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDateStr = fromDateEditText.getText().toString().trim();
                toDateStr = toDateEditText.getText().toString().trim();

                validateBottomDialog();
            }
        });

        closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetBehavior.cancel();
            }
        });
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

    // Validate Bottom Dialog Filter
    private void validateBottomDialog() {
        boolean isValidFromDate = false, isValidToDate = false;

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

        if (isValidFromDate && isValidToDate) {
            allocationData();
            sheetBehavior.cancel();
        }
    }

    // Set Allocation data in recycler
    private void allocationData() {
        AllocationModelList = new ArrayList<>();

//        for (int i = 0; i < 10; i++) {
//            AllocationModel allocationModels = new AllocationModel("WM/21/07/16/52 " + i, "PATEL JANTBI GAISUDIN " + i, "886616893" + i, "Flat No. " + i + " Triveni Apartments Pitam Pura NEW DELHI 110034 INDIA");
//            NCAllocationModelList.add(allocationModels);
//            WorkAllocationAdapter.addList(NCAllocationModelList);
//            allocationRecyclerView.setAdapter(WorkAllocationAdapter);
//            WorkAllocationAdapter.notifyDataSetChanged();
//        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.filter_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_filter) {
            sheetBehavior.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
