package elink.suezShimla.water.crm.ConnectionRequest.CommercialFeasibility;

import android.content.Context;
import android.content.Intent;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
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

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.map.MapActivity;
import elink.suezShimla.water.crm.ConnectionRequest.CommercialFeasibility.Adapter.CommercialFeasibilityAdapter;
import elink.suezShimla.water.crm.ConnectionRequest.CommercialFeasibility.Model.CommercialFeasibilityModel;
import elink.suezShimla.water.crm.R;

public class CommercialFeasibilityActivity extends AppCompatActivity {
    private Context mCon;
    private BottomSheetDialog sheetDialog;
    private RecyclerView commercialFeasibilityRecyclerView;
    private List<CommercialFeasibilityModel> commercialFeasibilityModelList;
    ;
    private CommercialFeasibilityAdapter commercialFeasibilityAdapter;

    private TextInputLayout applicationNoTextInputLayout;
    private TextInputEditText applicationNoTimeEditText;
    private AppCompatSpinner applicationTypeSpinner;
    private AppCompatImageView closeImageView;
    private MaterialButton showButton;

    private String applicationNoStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commercial_feasibility);
        // prevent ss and hide content when app is on background
      //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mCon = this;

        sheetDialog = new BottomSheetDialog(mCon);
        commercialFeasibilityAdapter = new CommercialFeasibilityAdapter(mCon);

        commercialFeasibilityRecyclerView = findViewById(R.id.commercialFeasibilityRecyclerView);
        commercialFeasibilityRecyclerView.setHasFixedSize(true);
        commercialFeasibilityRecyclerView.setLayoutManager(new LinearLayoutManager(mCon));

        commercialFeasibilityBottomSheet();
    }

    public void commercialFeasibilityBottomSheet() {
        View sheetBehavior = getLayoutInflater().inflate(R.layout.bottom_sheet_commercial_feasibility, null);
        sheetDialog.setContentView(sheetBehavior);

        applicationNoTextInputLayout = sheetBehavior.findViewById(R.id.applicationNoTextInputLayout);
        applicationNoTimeEditText = sheetBehavior.findViewById(R.id.applicationNoTimeEditText);
        applicationTypeSpinner = sheetBehavior.findViewById(R.id.applicationTypeSpinner);
        closeImageView = sheetBehavior.findViewById(R.id.closeImageView);
        showButton = sheetBehavior.findViewById(R.id.showButton);

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applicationNoStr = applicationNoTimeEditText.getText().toString().trim();
                validate();
            }
        });

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

        sheetDialog.show();
        sheetDialog.setCancelable(false);
    }

    public void validate() {
        boolean isValidApplicationNo = false;

        if (TextUtils.isEmpty(applicationNoStr)) {
            isValidApplicationNo = false;
            applicationNoTextInputLayout.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            isValidApplicationNo = true;
            applicationNoTextInputLayout.setError(null);
        }

        if (isValidApplicationNo) {
            loadCommercialFeasibilityData();
            sheetDialog.cancel();
        }
    }

    public void loadCommercialFeasibilityData() {
        commercialFeasibilityModelList = new ArrayList<>();

        CommercialFeasibilityModel data = new CommercialFeasibilityModel("19_03_1_94", "25 Nov 2018", "New Connection", "Pranay Das", "9985621444", "Malad (East), Mumbai - 4000056");
        commercialFeasibilityModelList.add(data);

        data = new CommercialFeasibilityModel("19_03_1_94", "5 Jan 2015", "New Connection", "Sreeraj R", "9865235596", "Andheri (East), Mumbai - 4000059");
        commercialFeasibilityModelList.add(data);

        commercialFeasibilityAdapter.addList(commercialFeasibilityModelList);
        commercialFeasibilityRecyclerView.setAdapter(commercialFeasibilityAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter_location_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        /*if (PreferenceUtil.getUserType() != null && !PreferenceUtil.getUserType().equals("") && PreferenceUtil.getUserType().equals("Employee")) {
            menu.findItem(R.id.locButton).setVisible(true);
        } else if (PreferenceUtil.getUserType() != null && !PreferenceUtil.getUserType().equals("") && PreferenceUtil.getUserType().equals("Admin")) {
            menu.findItem(R.id.locButton).setVisible(false);
        }*/

        return super.onPrepareOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.locButton) {
            startActivity(new Intent(mCon, MapActivity.class));
        } else if (id == R.id.action_filter) {
            sheetDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
