package elink.suezShimla.water.crm.MeterManagementSystem.StoreManagement.Authentication;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.MeterManagementSystem.StoreManagement.Authentication.Adapter.AuthenticationAdapter;
import elink.suezShimla.water.crm.MeterManagementSystem.StoreManagement.Authentication.Model.AuthenticationModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;

public class AuthenticationActivity extends AppCompatActivity {
    private BottomSheetDialog sheetDialog;
    private ImageView closeImageView;
    private TextInputLayout fromDate,toDate;
    private TextInputEditText  fromDateInputEditText,toDateInputEditText,meterNoEditText;
    private Context mCon;
    private MaterialButton showButton, clearBotton;
    RecyclerView recyclerView;
    private TextInputLayout remarkInputLayout,authDateLayout,authByInputLayout;
    private TextInputEditText remarkEditText,authDateEditText,authByEditText;
    private MaterialButton acceptButton;
    private AuthenticationAdapter authenticationAdapter;
    private List<AuthenticationModel> authenticationModelList = new ArrayList<>();
    private Calendar fromDateCalendar, toDateCalendar;
    private TextView totalRecordsCount;
    private MaterialDialog progress;
    private String jsonResponse = "";
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    AuthenticationAsyncTask authenticationAsyncTask;
    private String fromDateStr="", toDateStr="",authBystr="",remarkStr="",meterNostr="";
    String srNo="",meterNo="",statusDate="",makeCode="";
    boolean isSelected=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mCon = this;
        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();
        sheetDialog = new BottomSheetDialog(Objects.requireNonNull(mCon));
        remarkInputLayout=findViewById(R.id.remarkInputLayout);
        authByInputLayout=findViewById(R.id.authByInputLayout);
        authDateEditText=findViewById(R.id.authDateEditText);
        authByEditText=findViewById(R.id.authByEditText);
        acceptButton=findViewById(R.id.acceptButton);
        totalRecordsCount=findViewById(R.id.totalRecordsCount);
        View sheetView = Objects.requireNonNull(getLayoutInflater().inflate(R.layout.bottom_sheet_authentication_filter, null));

        fromDateCalendar = Calendar.getInstance();
        toDateCalendar = Calendar.getInstance();
        Date date = new Date();

        String strDateFormat = "dd-MMM-yyyy HH:mm:ss";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate = dateFormat.format(date);
        authDateEditText.setText(formattedDate.toString());

         closeImageView = sheetView.findViewById(R.id.closeImageView);
        showButton = sheetView.findViewById(R.id.showButton);
        fromDateInputEditText = sheetView.findViewById(R.id.fromDateInputEditText);
        fromDate = sheetView.findViewById(R.id.fromDate);
        toDate = sheetView.findViewById(R.id.toDate);
        toDateInputEditText = sheetView.findViewById(R.id.toDateInputEditText);
        meterNoEditText = sheetView.findViewById(R.id.meterNoEditText);
        showButton = sheetView.findViewById(R.id.showButton);
        sheetDialog.setContentView(sheetView);

        authenticationAdapter = new AuthenticationAdapter(mCon);
        recyclerView = findViewById(R.id.mdRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mCon));

        authenticationFilter();

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
        fromDateInputEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(mCon, fromCalendarDate, fromDateCalendar
                        .get(Calendar.YEAR), fromDateCalendar.get(Calendar.MONTH),
                        fromDateCalendar.get(Calendar.DAY_OF_MONTH));
            //    datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
                datePickerDialog.show();
            }
        });
        toDateInputEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(mCon, toCalendarDate, toDateCalendar
                        .get(Calendar.YEAR), toDateCalendar.get(Calendar.MONTH),
                        toDateCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
                datePickerDialog.show();
            }
        });
        acceptButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              authBystr=authByEditText.getText().toString();
              validate();
        }
       });
    }

    private void loadData() {
        AuthenticationModel model = new AuthenticationModel("1", "00011078", "19-Mar-2019 04:26 PM", "1-ARAD",true);
        authenticationModelList.add(model);

        model = new AuthenticationModel("2", "00011079", "19-Mar-2019 04:26 PM", "1-ARAD", false);
        authenticationModelList.add(model);

        model = new AuthenticationModel("3", "00011080", "19-Mar-2019 04:26 PM", "1-ARAD", false);
        authenticationModelList.add(model);

        model = new AuthenticationModel("4", "00011081", "19-Mar-2019 04:26 PM", "1-ARAD", false);
        authenticationModelList.add(model);

        model = new AuthenticationModel("5", "00011082", "19-Mar-2019 04:26 PM", "1-ARAD", false);
        authenticationModelList.add(model);

        authenticationAdapter.addList(authenticationModelList);
        recyclerView.setAdapter(authenticationAdapter);
    }

    public void authenticationFilter() {
        sheetDialog.show();
        sheetDialog.setCanceledOnTouchOutside(false);

        closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Toast.makeText(mCon, "Plz enter details to view history", Toast.LENGTH_SHORT).show();
            }
        });

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetDialog.cancel();
                fromDateStr=fromDateInputEditText.getText().toString();
                toDateStr=toDateInputEditText.getText().toString();
                meterNostr=meterNoEditText.getText().toString();
                getAuthenticationlistData();
                //loadData();
            }
        });
    }

    private void updateFromDateCalendar() {
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        fromDateInputEditText.setText(sdf.format(fromDateCalendar.getTime()));
    }

    // To Date Picker
    private void updateToDateCalendar() {
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        toDateInputEditText.setText(sdf.format(toDateCalendar.getTime()));
    }
    private void validate() {

        boolean  isvalidauthBy = false;

        if (TextUtils.isEmpty(authBystr)) {
            authByInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            isvalidauthBy = true;
            authByInputLayout.setError(null);
        }

        if (isvalidauthBy ) {
           Toast.makeText(AuthenticationActivity.this,"Authentication Successful",Toast.LENGTH_SHORT).show();
           finish();
        }
    }
    @SuppressLint("StaticFieldLeak")
    private class AuthenticationAsyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            progress = new MaterialDialog.Builder(mCon)
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
                String paraNames[] = new String[3];
                paraNames[0] = "FromDate";
                paraNames[1] = "ToDate";
                paraNames[2] = "MeterNo";
                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.MMG_StoreManagementAuthenticationShow, params, paraNames);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                authenticationModelList =new ArrayList<>();
                AuthenticationModel[] enums = gson.fromJson(jsonResponse, AuthenticationModel[].class);
                String SerialNo="";
                if (enums != null && enums.length > 0) {

                    for (AuthenticationModel model : enums) {

                        srNo =model.getSrNo();
                        SerialNo= (srNo.substring(0, srNo.indexOf('.')));
                        meterNo = model.getMeterNo();
                        statusDate = model.getStatusDate();
                        makeCode = model.getMakeCode();

                        AuthenticationModel data = new AuthenticationModel(SerialNo,meterNo,statusDate,makeCode,isSelected);
                        authenticationModelList.add(data);

                        authenticationAdapter.addList(authenticationModelList);
                    }
                     totalRecordsCount.setText(getResources().getString(R.string.total_no_of_records) + enums.length);
                    recyclerView.setAdapter(authenticationAdapter);
                    authenticationAdapter.notifyDataSetChanged();

                    remarkInputLayout.setVisibility(View.VISIBLE);
                    acceptButton.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                Log.e("AuthenticationException",e.toString());
                String error = e.toString();
                ErrorClass.errorData(mCon, "StoreManagement Authentication", "MMG_StoreManagementAuthenticationShow task Authentication", error);
            }
            progress.dismiss();
        }
    }

    private void getAuthenticationlistData(){
        String[] params = new String[3];
        params[0] = fromDateStr;
        params[1] = toDateStr;
        params[2] = meterNostr;

        if (connection.isConnectingToInternet()) {
            authenticationAsyncTask  = new AuthenticationAsyncTask();
            authenticationAsyncTask.execute(params);
        } else {
            Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
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
