package elink.suezShimla.water.crm.ConnectionRequest.Activity.ApplicationRequest;

import android.content.Context;
import com.google.android.material.button.MaterialButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import elink.suezShimla.water.crm.R;

public class AuthenticationAndApplicationDetailsActivity extends AppCompatActivity {

    private Context mCon;

    private MaterialButton submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication_and_application_details);
        // prevent ss and hide content when app is on background
      //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mCon = this;

        submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
