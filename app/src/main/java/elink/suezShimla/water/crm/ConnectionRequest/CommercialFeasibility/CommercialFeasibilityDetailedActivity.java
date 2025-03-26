package elink.suezShimla.water.crm.ConnectionRequest.CommercialFeasibility;

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

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.ConnectionRequest.CommercialFeasibility.Adapter.CommercialFeasibilityDetailedAdapter;
import elink.suezShimla.water.crm.ConnectionRequest.CommercialFeasibility.Model.CommercialFeasibilityModel;
import elink.suezShimla.water.crm.R;

public class CommercialFeasibilityDetailedActivity extends AppCompatActivity {
    private Context mCon;
    private BottomSheetDialog sheetDialog;
    private RecyclerView commercialFeasibilityDetailedRecyclerView;
    private CommercialFeasibilityDetailedAdapter commercialFeasibilityDetailedAdapter;
    private List<CommercialFeasibilityModel> commercialFeasibilityModelList;

    private TextInputLayout applicationNameTextInputLayout, contactNoTextInputLayout, addressTextInputLayout;
    private TextInputEditText applicationNameTimeEditText, contactNoTimeEditText, addressTimeEditText;
    private MaterialButton searchButton;
    private AppCompatImageView closeImageView;

    private String nameStr, contactNoStr, addressStr;
    private String applicantNameStr, applicantContactNoStr, applicantAddressStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commercial_feasibility_detailed);
        // prevent ss and hide content when app is on background
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mCon = this;

        nameStr = getIntent().getStringExtra("applicantName");
        contactNoStr = getIntent().getStringExtra("applicantContactNo");
        addressStr = getIntent().getStringExtra("applicantAddress");

        sheetDialog = new BottomSheetDialog(mCon);
        commercialFeasibilityDetailedAdapter = new CommercialFeasibilityDetailedAdapter(mCon);

        commercialFeasibilityDetailedRecyclerView = findViewById(R.id.commercialFeasibilityDetailedRecyclerView);
        commercialFeasibilityDetailedRecyclerView.setHasFixedSize(true);
        commercialFeasibilityDetailedRecyclerView.setLayoutManager(new LinearLayoutManager(mCon));

        commercialFeasibilityDetailBottomSheet();
    }

    public void commercialFeasibilityDetailBottomSheet() {
        View sheetBehavior = getLayoutInflater().inflate(R.layout.bottom_sheet_commercial_feasibility_detailed, null);
        sheetDialog.setContentView(sheetBehavior);

        applicationNameTextInputLayout = sheetBehavior.findViewById(R.id.applicationNameTextInputLayout);
        contactNoTextInputLayout = sheetBehavior.findViewById(R.id.contactNoTextInputLayout);
        addressTextInputLayout = sheetBehavior.findViewById(R.id.addressTextInputLayout);

        applicationNameTimeEditText = sheetBehavior.findViewById(R.id.applicationNameTimeEditText);
        contactNoTimeEditText = sheetBehavior.findViewById(R.id.contactNoTimeEditText);
        addressTimeEditText = sheetBehavior.findViewById(R.id.addressTimeEditText);
        closeImageView = sheetBehavior.findViewById(R.id.closeImageView);
        searchButton = sheetBehavior.findViewById(R.id.searchButton);

        applicationNameTimeEditText.setText(nameStr);
        contactNoTimeEditText.setText(contactNoStr);
        addressTimeEditText.setText(addressStr);

        closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commercialFeasibilityModelList != null) {
                    sheetDialog.cancel();
                } else {
                    finish();
                }
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applicantNameStr = applicationNameTimeEditText.getText().toString().trim();
                applicantContactNoStr = contactNoTimeEditText.getText().toString().trim();
                applicantAddressStr = addressTimeEditText.getText().toString().trim();

                validate();
            }
        });

        sheetDialog.show();
        sheetDialog.setCancelable(false);
    }

    public void validate() {
        boolean isValidName, isValidContactNo, isValidAddress;

        if (TextUtils.isEmpty(applicantNameStr)) {
            isValidName = false;
            applicationNameTextInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            isValidName = true;
            applicationNameTextInputLayout.setError(null);
        }

        if (TextUtils.isEmpty(applicantContactNoStr)) {
            isValidContactNo = false;
            contactNoTextInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            isValidContactNo = true;
            contactNoTextInputLayout.setError(null);
        }

        if (TextUtils.isEmpty(applicantAddressStr)) {
            isValidAddress = false;
            addressTextInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            isValidAddress = true;
            addressTextInputLayout.setError(null);
        }

        if (isValidName && isValidContactNo && isValidAddress) {
            loadCommercialFeasibilityDetail();
            sheetDialog.cancel();
        }
    }

    public void loadCommercialFeasibilityDetail() {
        commercialFeasibilityModelList = new ArrayList<>();

        CommercialFeasibilityModel data = new CommercialFeasibilityModel("19_03_1_94", "25 Nov 2018", "New Connection", "Pranay Das", "9985621444", "Malad (East), Mumbai - 4000056");
        commercialFeasibilityModelList.add(data);

        data = new CommercialFeasibilityModel("19_03_1_94", "25 Nov 2018", "New Connection", "Pranay Das", "9985621444", "Malad (East), Mumbai - 4000056");
        commercialFeasibilityModelList.add(data);

        commercialFeasibilityDetailedAdapter.addList(commercialFeasibilityModelList);
        commercialFeasibilityDetailedRecyclerView.setAdapter(commercialFeasibilityDetailedAdapter);
    }
}
