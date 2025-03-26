package elink.suezShimla.water.crm.DisconnectionAndDismantling.DisconnectionOrderlist;

import android.annotation.SuppressLint;
import android.content.Context;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import android.view.View;
import android.widget.Toast;

import elink.suezShimla.water.crm.R;

public class DisconnectionOrderListActivity extends AppCompatActivity {

    private Context mCon;

    private BottomSheetDialog sheetDialog;

    private AppCompatSpinner connectionSizeSpinner;

    private AppCompatImageView closeImageView;

    private String fromLimitStr, toLimitStr, connectionSizeStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disconnection_order_list);
        // prevent ss and hide content when app is on background
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mCon = this;

        sheetDialog = new BottomSheetDialog(mCon);

        disconnectionOrderListBottomSheetDialog();

    }

    private void disconnectionOrderListBottomSheetDialog() {

        @SuppressLint("InflateParams")
        View sheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_disconnection_order_list, null);
        sheetDialog.setContentView(sheetView);

        MaterialButton showButton = sheetView.findViewById(R.id.showButton);

        connectionSizeSpinner = sheetView.findViewById(R.id.connectionSizeSpinner);


        closeImageView = sheetView.findViewById(R.id.closeImageView);

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                connectionSizeStr = connectionSizeSpinner.getSelectedItem().toString().trim();
                //validate();
            }
        });

        closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Toast.makeText(mCon, "Plz enter details to proceed", Toast.LENGTH_SHORT).show();
            }
        });

        sheetDialog.setCanceledOnTouchOutside(false);
        sheetDialog.setCancelable(false);
        sheetDialog.show();

    }
}
