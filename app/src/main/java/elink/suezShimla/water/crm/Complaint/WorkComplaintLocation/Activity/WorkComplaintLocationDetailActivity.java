package elink.suezShimla.water.crm.Complaint.WorkComplaintLocation.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Objects;

import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;

public class WorkComplaintLocationDetailActivity extends AppCompatActivity {
    private MaterialButton forwardButton;
    private BottomSheetDialog sheetBehavior;
    private Context mCon;
    private AppCompatImageView locationImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_completion_detail);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mCon = this;
        locationImageView = findViewById(R.id.locationImageView);
        forwardButton = findViewById(R.id.forwardButton);
        sheetBehavior = new BottomSheetDialog(Objects.requireNonNull(mCon));

        View sheetView = Objects.requireNonNull(getLayoutInflater().inflate(R.layout.bottom_sheet_work_completion_fwd_section, null));
        sheetBehavior.setContentView(sheetView);
        FrameLayout bottomSheet = sheetBehavior.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior.from(Objects.requireNonNull(bottomSheet)).setState(BottomSheetBehavior.STATE_EXPANDED);
        sheetBehavior.setCancelable(false);
        sheetBehavior.setCanceledOnTouchOutside(false);

        MaterialButton proceedButton = sheetView.findViewById(R.id.proceedButton);
        MaterialButton submitButton = sheetView.findViewById(R.id.submitButton);

        final AppCompatImageView closeImageView = sheetView.findViewById(R.id.employeeCloseImageView);

        final CheckBox checkbox = sheetView.findViewById(R.id.checkbox);

        final TextView goBackTextView = sheetView.findViewById(R.id.goBackTextView);
        final TextView otpLabelTextView = sheetView.findViewById(R.id.otpLabelTextView);
        final TextView reasonLabelTextView = sheetView.findViewById(R.id.reasonLabelTextView);

        final TextInputLayout otpTextInputLayout = sheetView.findViewById(R.id.otpTextInputLayout);
        final TextInputLayout reasonTextInputLayout = sheetView.findViewById(R.id.reasonTextInputLayout);

        final LinearLayout fullDataLinearLayout = sheetView.findViewById(R.id.fullDataLinearLayout);
        final LinearLayout otpLinearLayout = sheetView.findViewById(R.id.otpLinearLayout);

       /* if (PreferenceUtil.getUserType() != null && !PreferenceUtil.getUserType().equals("") && PreferenceUtil.getUserType().equals("Employee")) {
            locationImageView.setVisibility(View.VISIBLE);
        } else if (PreferenceUtil.getUserType() != null && !PreferenceUtil.getUserType().equals("") && PreferenceUtil.getUserType().equals("Admin")) {
            locationImageView.setVisibility(View.GONE);
        }*/

        locationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=19.1963079&daddr=72.8042726"));
                startActivity(intent);
            }
        });

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    otpLabelTextView.setVisibility(View.GONE);
                    otpTextInputLayout.setVisibility(View.GONE);

                    reasonLabelTextView.setVisibility(View.VISIBLE);
                    reasonTextInputLayout.setVisibility(View.VISIBLE);

                } else {
                    otpLabelTextView.setVisibility(View.VISIBLE);
                    otpTextInputLayout.setVisibility(View.VISIBLE);

                    reasonLabelTextView.setVisibility(View.GONE);
                    reasonTextInputLayout.setVisibility(View.GONE);
                }
            }
        });

        closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetBehavior.cancel();
            }
        });

        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullDataLinearLayout.setVisibility(View.GONE);
                otpLinearLayout.setVisibility(View.VISIBLE);
            }
        });

        goBackTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullDataLinearLayout.setVisibility(View.VISIBLE);
                otpLinearLayout.setVisibility(View.GONE);
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetBehavior.show();
            }
        });

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

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(new Intent(mCon, SplashScreen.class));
    }
}