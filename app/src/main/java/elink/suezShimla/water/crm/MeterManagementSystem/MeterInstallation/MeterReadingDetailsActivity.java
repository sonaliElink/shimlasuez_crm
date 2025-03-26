package elink.suezShimla.water.crm.MeterManagementSystem.MeterInstallation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AbsoluteLayout;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;

public class MeterReadingDetailsActivity extends AppCompatActivity {
    private LinearLayout collapseLinearLayout, oldMeterCollapseLinearLayout, newMeterCollapseLinearLayout;

    private CardView card_view, oldMeterCard_view, newMeterCard_view;

    private ImageView arrowImageView, oldMeterArrowImageView, newMeterArrowImageView;

    private TextView consummerNoTextView,serviceTypeTextView,referenceNoTextView,zoneTextView,subzoneTextView,fixerNameTextView,contactNoTextView,
            issueDateTextView,sequenceTypeTextView,walkRouteTextView,consumerCategoryTextView,nameAddressTextView,newMeterNoTextView;

    private Spinner meterInstalledSpinner,meterNotInstallReasonSpinner,forwardToSectionSpinner;

    private EditText installationDateEditText;

    private TextInputEditText selectEmpTextInputEditText;

    private Context mCon;
    private MaterialDialog progress;
    private String jsonResponse = "";
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    MeterinstallDetailsAsyncTask meterinstallDetailsAsyncTask;
    String V_ConsumerNo="",V_ConsumerType="",V_ReqNo="",V_NameAdd="",V_ContactNo="",V_OldMeterNo="",V_FixerName="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meter_reading_details);
      //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mCon = this;
        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();
        consummerNoTextView = findViewById(R.id.consummerNoTextView);
        serviceTypeTextView = findViewById(R.id.serviceTypeTextView);
        referenceNoTextView = findViewById(R.id.referenceNoTextView);
        zoneTextView = findViewById(R.id.zoneTextView);
        subzoneTextView = findViewById(R.id.subzoneTextView);
        fixerNameTextView = findViewById(R.id.fixerNameTextView);
        contactNoTextView = findViewById(R.id.contactNoTextView);
        issueDateTextView = findViewById(R.id.issueDateTextView);
        sequenceTypeTextView = findViewById(R.id.sequenceTypeTextView);
        walkRouteTextView = findViewById(R.id.walkRouteTextView);
        consumerCategoryTextView = findViewById(R.id.consumerCategoryTextView);
        nameAddressTextView = findViewById(R.id.nameAddressTextView);
        newMeterNoTextView=findViewById(R.id.newMeterNoTextView);

        Bundle bundle=getIntent().getExtras();
        V_ConsumerNo=bundle.getString("ConsumerNo");
//        V_ConsumerType=bundle.getString("ConsumerType");
        V_ReqNo=bundle.getString("reqNo");
        V_NameAdd=bundle.getString("nameAddress");
        V_ContactNo=bundle.getString("contactNo");
//        V_OldMeterNo=bundle.getString("oldMeterNo");
        V_FixerName=bundle.getString("fixerName");

        //set data on textview

        //getMeterInstallationDetails();
         Fn_loadData();

        collapseLinearLayout = findViewById(R.id.collapseLinearLayout);
        newMeterCollapseLinearLayout = findViewById(R.id.newMeterCollapseLinearLayout);
        oldMeterCollapseLinearLayout = findViewById(R.id.oldMeterCollapseLinearLayout);
        selectEmpTextInputEditText = findViewById(R.id.selectEmpTextInputEditText);

        card_view = findViewById(R.id.card_view);
        newMeterCard_view = findViewById(R.id.newMeterCard_view);
        oldMeterCard_view = findViewById(R.id.oldMeterCard_view);

        arrowImageView = findViewById(R.id.arrowImageView);
        newMeterArrowImageView = findViewById(R.id.newMeterArrowImageView);
        oldMeterArrowImageView = findViewById(R.id.oldMeterArrowImageView);

        selectEmpTextInputEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                MaterialDialog dialog = new MaterialDialog.Builder(mCon)
                        .title(R.string.selectEmployee)
                        .customView(R.layout.select_emp_dialouge,true)
                        .contentColor(mCon.getResources().getColor(R.color.colorPrimary))
                        .canceledOnTouchOutside(false)
                        .positiveText(R.string.submit)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .negativeText(R.string.cancel)
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                return false;
            }


        });

        oldMeterCard_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (oldMeterCollapseLinearLayout.getVisibility() == View.GONE) {
                    expand(oldMeterCollapseLinearLayout);
                    oldMeterArrowImageView.animate().rotation(180).start();
                } else {
                    collapse(oldMeterCollapseLinearLayout);
                    oldMeterArrowImageView.animate().rotation(0).start();
                }
            }
        });

        card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (collapseLinearLayout.getVisibility() == View.GONE) {
                    expand(collapseLinearLayout);
                    arrowImageView.animate().rotation(180).start();
                } else {
                    collapse(collapseLinearLayout);
                    arrowImageView.animate().rotation(0).start();
                }
            }
        });

        newMeterCard_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newMeterCollapseLinearLayout.getVisibility() == View.GONE) {
                    expand(newMeterCollapseLinearLayout);
                    newMeterArrowImageView.animate().rotation(180).start();
                } else {
                    collapse(newMeterCollapseLinearLayout);
                    newMeterArrowImageView.animate().rotation(0).start();
                }
            }
        });
    }

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

    public void Fn_loadData(){
        consummerNoTextView.setText("Consumer No : 101093102");
        serviceTypeTextView.setText("STANDARD");
        referenceNoTextView.setText("16/07/1/34");
        zoneTextView.setText(" Coimbatore ");
        subzoneTextView.setText("Circle 1");
        fixerNameTextView.setText(" EMP502-Mr. Rinki Arge  ");
        contactNoTextView.setText("9815415665");
        issueDateTextView.setText("12-Mar-2019 02:19 PM");
        sequenceTypeTextView.setText("NA");
        walkRouteTextView.setText("NA");
        consumerCategoryTextView.setText("NA");
        nameAddressTextView.setText("NA");
    }

    @SuppressLint("StaticFieldLeak")
    private class MeterinstallDetailsAsyncTask extends AsyncTask<String, Void, Void> {
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
                String paraNames[] = new String[9];

                paraNames[0] = "SourceType";
                paraNames[1] = "Rights";
                paraNames[2] = "ComplaintSubType";
                paraNames[3] = "ConsumerNo";
                paraNames[4] = "FromIssueDate";
                paraNames[5] = "ToIssueDate";
                paraNames[6] = "BU";
                paraNames[7] = "PC";
                paraNames[8] = "Tag";

                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.MMG_MeterInstallationReplacementShow, params, paraNames);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {

            } catch (Exception e) {
                Log.e("MeterReadingActivity",e.toString());

                String error = e.toString();
                ErrorClass.errorData(mCon, "MeterReadingActivity", "Button", error);
            }
            progress.dismiss();
        }
    }

    private void getMeterInstallationDetails(){

        String[] params = new String[3];
        params[0] = "";
        params[1] = "";
        params[2] = "";
        params[3] = "";
        params[4] = "";
        params[5] = "";
        params[6] = "";
        params[7] = "";
        params[8] = "";
        if (connection.isConnectingToInternet()) {
             meterinstallDetailsAsyncTask  = new MeterinstallDetailsAsyncTask();
             meterinstallDetailsAsyncTask.execute(params);
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
