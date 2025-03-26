package elink.suezShimla.water.crm.DisconnectionAndDismantling.DefaulterList;

import android.annotation.SuppressLint;
import android.content.Context;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import elink.suezShimla.water.crm.R;

public class DefaulterListActivity extends AppCompatActivity {

    private Context mCon;

    private BottomSheetDialog sheetDialog;

    private TextInputEditText fromLimitEditText, toLimitEditText;

    private AppCompatSpinner connectionSizeSpinner;

    private AppCompatImageView closeImageView;

    private String fromLimitStr, toLimitStr, connectionSizeStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defaulter_list);
        // prevent ss and hide content when app is on background
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mCon = this;

        sheetDialog = new BottomSheetDialog(mCon);

        defaulterListBottomSheetDialog();

    }

    private void defaulterListBottomSheetDialog() {
        @SuppressLint("InflateParams")
        View sheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_defaulter_list, null);
        sheetDialog.setContentView(sheetView);

        MaterialButton showButton = sheetView.findViewById(R.id.showButton);

        connectionSizeSpinner = sheetView.findViewById(R.id.connectionSizeSpinner);

        fromLimitEditText = sheetView.findViewById(R.id.fromLimitEditText);
        toLimitEditText = sheetView.findViewById(R.id.toLimitEditText);

        closeImageView = sheetView.findViewById(R.id.closeImageView);

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fromLimitStr = fromLimitEditText.getText().toString().trim();
                toLimitStr = toLimitEditText.getText().toString().trim();

                connectionSizeStr = connectionSizeSpinner.getSelectedItem().toString().trim();

                validate();
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

    private void validate() {
        boolean isvalidFrom = false, isvalidTo = false, isvalidConnectionSize = false;

        if (TextUtils.isEmpty(fromLimitStr)) {
            fromLimitEditText.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            isvalidFrom = true;
            fromLimitEditText.setError(null);
        }

        if (TextUtils.isEmpty(toLimitStr)) {
            toLimitEditText.setError(getResources().getString(R.string.cannot_be_empty));
        } else {
            isvalidTo = true;
            toLimitEditText.setError(null);
        }

        if (connectionSizeStr.equalsIgnoreCase("Connection Size (In Inch)*")) {
            TextView view = (TextView) connectionSizeSpinner.getSelectedView();
            view.setError(getResources().getString(R.string.select_options));
        } else {
            isvalidConnectionSize = true;
            TextView view = (TextView) connectionSizeSpinner.getSelectedView();
            view.setError(null);
        }

        if (isvalidFrom && isvalidTo && isvalidConnectionSize) {
            sheetDialog.cancel();
        }
    }
}
