package elink.suezShimla.water.crm.Register;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.Objects;

import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.Login.LoginActivity;
import elink.suezShimla.water.crm.Login.LoginModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;


public class AuthenticateRegistrationActivity extends AppCompatActivity implements TextWatcher, View.OnFocusChangeListener, View.OnKeyListener, View.OnClickListener {

    private Context mCon;
    private RealmOperations realmOperations;
    private TextInputEditText oldPinOneEditText, oldPinTwoEditText, oldPinThreeEditText, oldPinFourEditText, newPinOneEditText, newPinTwoEditText, newPinThreeEditText, newPinFourEditText,
            confirmPinOneEditText, confirmPinTwoEditText, confirmPinThreeEditText, confirmPinFourEditText, mPinHiddenEditText;
    private TextView oldPinErrorTextView, newPinErrorTextView, confirmPinErrorTextView;
    private MaterialButton changePinButton;
    private String oldPinOneStr, oldPinTwoStr, oldPinThreeStr, oldPinFourStr, newPinOneStr, newPinTwoStr, newPinThreeStr, newPinFourStr, confirmPinOneStr, confirmPinTwoStr,
            confirmPinThreeStr, confirmPinFourStr, oldPin, newPin, confirmPin;

    private String jsonResponse = "",deviceAuthorization = "", empCode="", versionName="";
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    private MaterialDialog progress;
    private String mac="", imei="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //    getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_authenticate_registarion_demo);
        init();

    }

    private void init() {

        realmOperations = new RealmOperations(this);
        gson = new Gson();
        mCon = this;
        connection = new ConnectionDetector(this);
        invServices = new Invoke();
        oldPinOneEditText = findViewById(R.id.oldPinOneEditText);
        oldPinTwoEditText = findViewById(R.id.oldPinTwoEditText);
        oldPinThreeEditText = findViewById(R.id.oldPinThreeEditText);
        oldPinFourEditText = findViewById(R.id.oldPinFourEditText);
        newPinOneEditText = findViewById(R.id.newPinOneEditText);
        newPinTwoEditText = findViewById(R.id.newPinTwoEditText);
        newPinThreeEditText = findViewById(R.id.newPinThreeEditText);
        newPinFourEditText = findViewById(R.id.newPinFourEditText);
        confirmPinOneEditText = findViewById(R.id.confirmPinOneEditText);
        confirmPinTwoEditText = findViewById(R.id.confirmPinTwoEditText);
        confirmPinThreeEditText = findViewById(R.id.confirmPinThreeEditText);
        confirmPinFourEditText = findViewById(R.id.confirmPinFourEditText);
        mPinHiddenEditText = findViewById(R.id.pin_hidden_edittext);

        oldPinErrorTextView = findViewById(R.id.oldPinErrorTextView);
        newPinErrorTextView = findViewById(R.id.newPinErrorTextView);
        confirmPinErrorTextView = findViewById(R.id.confirmPinErrorTextView);

        changePinButton = findViewById(R.id.changePinButton);

        changePinButton.setOnClickListener(this);


        Intent startingIntent = getIntent();
        imei = startingIntent.getStringExtra("IMEINumber");
        mac = startingIntent.getStringExtra("MACAddress");
        empCode = startingIntent.getStringExtra("employeeID");
        versionName = startingIntent.getStringExtra("versionName");
       /* deviceAuthorization = UtilitySharedPreferences.getPrefs(this, AppConstant.DEVICEAUTHORIZATION);
        if(deviceAuthorization.equalsIgnoreCase("0")){
            deviceAuthorizedDialog();

        }*/
        // SharedPreferences prefs = mCon.getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
       // empCode = prefs.getString("empCode", null);

      /*  employeeLoginModel = realmOperations.fetchDataReadingByEmpCode(empCode);
        mac = employeeLoginModel.getMacAddress();
        imei = employeeLoginModel.getDeviceImei();*/

        setPINListeners();

        hideKeyboardOnLastEditText(confirmPinFourEditText);


    }




    public void hideKeyboardOnLastEditText(final TextInputEditText fourthEditText) {
        fourthEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString().trim())) {
                    hideSoftKeyboard();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void hideSoftKeyboard() {
        if (Objects.requireNonNull(this.getCurrentFocus() != null)) {
            InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(this.getCurrentFocus()).getWindowToken(), 0);
        }
    }


    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        final int id = v.getId();
        switch (id) {
            case R.id.oldPinOneEditText:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;

            case R.id.oldPinTwoEditText:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;

            case R.id.oldPinThreeEditText:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;

            case R.id.oldPinFourEditText:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;

            case R.id.newPinOneEditText:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;

            case R.id.newPinTwoEditText:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;

            case R.id.newPinThreeEditText:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;

            case R.id.newPinFourEditText:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;

            case R.id.confirmPinOneEditText:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;

            case R.id.confirmPinTwoEditText:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;

            case R.id.confirmPinThreeEditText:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;

            case R.id.confirmPinFourEditText:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;

            default:
                break;
        }

    }


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            final int id = v.getId();
            switch (id) {
                case R.id.pin_hidden_edittext:
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        if (mPinHiddenEditText.getText().length() == 12)
                            confirmPinFourEditText.setText("");
                        else if (mPinHiddenEditText.getText().length() == 11)
                            confirmPinThreeEditText.setText("");
                        else if (mPinHiddenEditText.getText().length() == 10)
                            confirmPinTwoEditText.setText("");
                        else if (mPinHiddenEditText.getText().length() == 9)
                            confirmPinOneEditText.setText("");
                        else if (mPinHiddenEditText.getText().length() == 8)
                            newPinFourEditText.setText("");
                        else if (mPinHiddenEditText.getText().length() == 7)
                            newPinThreeEditText.setText("");
                        else if (mPinHiddenEditText.getText().length() == 6)
                            newPinTwoEditText.setText("");
                        else if (mPinHiddenEditText.getText().length() == 5)
                            newPinOneEditText.setText("");
                        else if (mPinHiddenEditText.getText().length() == 4)
                            oldPinFourEditText.setText("");
                        else if (mPinHiddenEditText.getText().length() == 3)
                            oldPinThreeEditText.setText("");
                        else if (mPinHiddenEditText.getText().length() == 2)
                            oldPinTwoEditText.setText("");
                        else if (mPinHiddenEditText.getText().length() == 1)
                            oldPinOneEditText.setText("");

                        if (mPinHiddenEditText.length() > 0)
                            mPinHiddenEditText.setText(mPinHiddenEditText.getText().subSequence(0, mPinHiddenEditText.length() - 1));

                        return true;
                    }

                    break;

                default:
                    return false;
            }
        }

        return false;
    }


    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() == 0) {
            oldPinOneEditText.setText("");
        } else if (s.length() == 1) {
            oldPinOneEditText.setText(s.charAt(0) + "");
            oldPinTwoEditText.setText("");
            oldPinThreeEditText.setText("");
            oldPinFourEditText.setText("");
            newPinOneEditText.setText("");
            newPinTwoEditText.setText("");
            newPinThreeEditText.setText("");
            newPinFourEditText.setText("");
            confirmPinOneEditText.setText("");
            confirmPinTwoEditText.setText("");
            confirmPinThreeEditText.setText("");
            confirmPinFourEditText.setText("");
        } else if (s.length() == 2) {
            oldPinTwoEditText.setText(s.charAt(1) + "");
            oldPinThreeEditText.setText("");
            oldPinFourEditText.setText("");
            newPinOneEditText.setText("");
            newPinTwoEditText.setText("");
            newPinThreeEditText.setText("");
            newPinFourEditText.setText("");
            confirmPinOneEditText.setText("");
            confirmPinTwoEditText.setText("");
            confirmPinThreeEditText.setText("");
            confirmPinFourEditText.setText("");
        } else if (s.length() == 3) {
            oldPinThreeEditText.setText(s.charAt(2) + "");
            oldPinFourEditText.setText("");
            newPinOneEditText.setText("");
            newPinTwoEditText.setText("");
            newPinThreeEditText.setText("");
            newPinFourEditText.setText("");
            confirmPinOneEditText.setText("");
            confirmPinTwoEditText.setText("");
            confirmPinThreeEditText.setText("");
            confirmPinFourEditText.setText("");
        } else if (s.length() == 4) {
            oldPinFourEditText.setText(s.charAt(3) + "");
            newPinOneEditText.setText("");
            newPinTwoEditText.setText("");
            newPinThreeEditText.setText("");
            newPinFourEditText.setText("");
            confirmPinOneEditText.setText("");
            confirmPinTwoEditText.setText("");
            confirmPinThreeEditText.setText("");
            confirmPinFourEditText.setText("");
        } else if (s.length() == 5) {
            newPinOneEditText.setText(s.charAt(4) + "");
            newPinTwoEditText.setText("");
            newPinThreeEditText.setText("");
            newPinFourEditText.setText("");
            confirmPinOneEditText.setText("");
            confirmPinTwoEditText.setText("");
            confirmPinThreeEditText.setText("");
            confirmPinFourEditText.setText("");
        } else if (s.length() == 6) {
            newPinTwoEditText.setText(s.charAt(5) + "");
            newPinThreeEditText.setText("");
            newPinFourEditText.setText("");
            confirmPinOneEditText.setText("");
            confirmPinTwoEditText.setText("");
            confirmPinThreeEditText.setText("");
            confirmPinFourEditText.setText("");
        } else if (s.length() == 7) {
            newPinThreeEditText.setText(s.charAt(6) + "");
            newPinFourEditText.setText("");
            confirmPinOneEditText.setText("");
            confirmPinTwoEditText.setText("");
            confirmPinThreeEditText.setText("");
            confirmPinFourEditText.setText("");
        } else if (s.length() == 8) {
            newPinFourEditText.setText(s.charAt(7) + "");
            confirmPinOneEditText.setText("");
            confirmPinTwoEditText.setText("");
            confirmPinThreeEditText.setText("");
            confirmPinFourEditText.setText("");
        } else if (s.length() == 9) {
            confirmPinOneEditText.setText(s.charAt(8) + "");
            confirmPinTwoEditText.setText("");
            confirmPinThreeEditText.setText("");
            confirmPinFourEditText.setText("");
        } else if (s.length() == 10) {
            confirmPinTwoEditText.setText(s.charAt(9) + "");
            confirmPinThreeEditText.setText("");
            confirmPinFourEditText.setText("");
        } else if (s.length() == 11) {
            confirmPinThreeEditText.setText(s.charAt(10) + "");
            confirmPinFourEditText.setText("");
        } else if (s.length() == 12) {
            confirmPinFourEditText.setText(s.charAt(11) + "");

            hideSoftKeyboardSecond(confirmPinFourEditText);
        }
    }



    public void hideSoftKeyboardSecond(TextInputEditText editText) {
        if (editText == null)
            return;

        InputMethodManager imm = (InputMethodManager) this.getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }



    private void setPINListeners() {
        mPinHiddenEditText.addTextChangedListener(this);

        oldPinOneEditText.setOnFocusChangeListener(this);
        oldPinTwoEditText.setOnFocusChangeListener(this);
        oldPinThreeEditText.setOnFocusChangeListener(this);
        oldPinFourEditText.setOnFocusChangeListener(this);

        newPinOneEditText.setOnFocusChangeListener(this);
        newPinTwoEditText.setOnFocusChangeListener(this);
        newPinThreeEditText.setOnFocusChangeListener(this);
        newPinFourEditText.setOnFocusChangeListener(this);

        confirmPinOneEditText.setOnFocusChangeListener(this);
        confirmPinTwoEditText.setOnFocusChangeListener(this);
        confirmPinThreeEditText.setOnFocusChangeListener(this);
        confirmPinFourEditText.setOnFocusChangeListener(this);

        oldPinOneEditText.setOnKeyListener(this);
        oldPinTwoEditText.setOnKeyListener(this);
        oldPinThreeEditText.setOnKeyListener(this);
        oldPinFourEditText.setOnKeyListener(this);

        newPinOneEditText.setOnKeyListener(this);
        newPinTwoEditText.setOnKeyListener(this);
        newPinThreeEditText.setOnKeyListener(this);
        newPinFourEditText.setOnKeyListener(this);

        confirmPinOneEditText.setOnKeyListener(this);
        confirmPinTwoEditText.setOnKeyListener(this);
        confirmPinThreeEditText.setOnKeyListener(this);
        confirmPinFourEditText.setOnKeyListener(this);

        mPinHiddenEditText.setOnKeyListener(this);
    }


    public void showSoftKeyboard(TextInputEditText editText) {
        if (editText == null)
            return;

        InputMethodManager imm = (InputMethodManager) this.getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, 0);
    }


    public static void setFocus(TextInputEditText editText) {
        if (editText == null)
            return;
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
    }

    public void validate() {
        boolean isValidOldPin = false, isValidNewPin = false, isValidConfirmPin = false;

        if (!TextUtils.isEmpty(oldPinOneStr) && !TextUtils.isEmpty(oldPinTwoStr) && !TextUtils.isEmpty(oldPinThreeStr) && !TextUtils.isEmpty(oldPinFourStr)) {
            oldPinErrorTextView.setVisibility(View.INVISIBLE);
            isValidOldPin = true;
        } else {
            oldPinErrorTextView.setVisibility(View.VISIBLE);
            oldPinErrorTextView.setText(R.string.cannot_be_empty);
        }

        if (!TextUtils.isEmpty(newPinOneStr) && !TextUtils.isEmpty(newPinTwoStr) && !TextUtils.isEmpty(newPinThreeStr) && !TextUtils.isEmpty(newPinFourStr)) {
            newPinErrorTextView.setVisibility(View.INVISIBLE);
            isValidNewPin = true;
        } else {
            newPinErrorTextView.setVisibility(View.VISIBLE);
            newPinErrorTextView.setText(R.string.cannot_be_empty);
        }

        if (!TextUtils.isEmpty(confirmPinOneStr) && !TextUtils.isEmpty(confirmPinTwoStr) && !TextUtils.isEmpty(confirmPinThreeStr) && !TextUtils.isEmpty(confirmPinFourStr)) {
            if (!newPin.equals(confirmPin)) {
                confirmPinErrorTextView.setVisibility(View.VISIBLE);
                confirmPinErrorTextView.setText(R.string.pin_not_matched);
            } else {
                confirmPinErrorTextView.setVisibility(View.INVISIBLE);
                isValidConfirmPin = true;
            }
        } else {
            confirmPinErrorTextView.setVisibility(View.VISIBLE);
            confirmPinErrorTextView.setText(R.string.cannot_be_empty);
        }

        if (isValidOldPin && isValidNewPin && isValidConfirmPin) {

            if (oldPin.equals(newPin)) {
                Toast.makeText(AuthenticateRegistrationActivity.this, "New pin cannot be same as Old pin", Toast.LENGTH_SHORT).show();
            } else {
                String params[] = new String[7];

                params[0] = empCode;
                params[1] = oldPin;
                params[2] = imei;
                params[3] = mac;
                params[4] = "2";
                params[5] = newPin;
                params[6] = "";

                if (connection.isConnectingToInternet()) {
                    changeMPin changeMPin = new changeMPin();
                    changeMPin.execute(params);

                    progress = new MaterialDialog.Builder(mCon)
                            .title(R.string.app_name)
                            .content(R.string.loading)
                            .progress(true, 0)
                            .autoDismiss(false)
                            .canceledOnTouchOutside(false)
                            .widgetColorRes(R.color.colorPrimary)
                            .show();


                } else {
                    Toast.makeText(AuthenticateRegistrationActivity.this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }



    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.changePinButton) {
            oldPinOneStr = oldPinOneEditText.getText().toString().trim();
            oldPinTwoStr = oldPinTwoEditText.getText().toString().trim();
            oldPinThreeStr = oldPinThreeEditText.getText().toString().trim();
            oldPinFourStr = oldPinFourEditText.getText().toString().trim();

            newPinOneStr = newPinOneEditText.getText().toString().trim();
            newPinTwoStr = newPinTwoEditText.getText().toString().trim();
            newPinThreeStr = newPinThreeEditText.getText().toString().trim();
            newPinFourStr = newPinFourEditText.getText().toString().trim();

            confirmPinOneStr = confirmPinOneEditText.getText().toString().trim();
            confirmPinTwoStr = confirmPinTwoEditText.getText().toString().trim();
            confirmPinThreeStr = confirmPinThreeEditText.getText().toString().trim();
            confirmPinFourStr = confirmPinFourEditText.getText().toString().trim();

            oldPin = oldPinOneStr + oldPinTwoStr + oldPinThreeStr + oldPinFourStr;
            newPin = newPinOneStr + newPinTwoStr + newPinThreeStr + newPinFourStr;
            confirmPin = confirmPinOneStr + confirmPinTwoStr + confirmPinThreeStr + confirmPinFourStr;

            validate();
        }
    }


    public void setEditTextFocus(final TextInputEditText firstEditText,
                                 final TextInputEditText secondEditText) {
        firstEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString().trim())) {
                    firstEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    secondEditText.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void setEditTextBackFocus(final TextInputEditText secondEditText,
                                     final TextInputEditText firstEditText) {
        secondEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s.toString().trim())) {
                    firstEditText.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }





    @Override
    public void onPause() {
        super.onPause();
        realmOperations.close();
    }

    private class changeMPin extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {

            try {
                String paraName[] = new String[7];

                paraName[0] = "NAME";
                paraName[1] = "MPIN";
                paraName[2] = "IMEI";
                paraName[3] = "MAC";
                paraName[4] = "TYPE";
                paraName[5] = "NEWMPIN";
                paraName[6] = "deviceID";
                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, "UserLogin", params, paraName);

            } catch (Exception e) {
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                LoginModel[] enums = gson.fromJson(jsonResponse, LoginModel[].class);

                if (enums.length > 0) {
                    if (enums[0].getQueryStatus().equalsIgnoreCase("Success")) {

                       // Toast.makeText(AuthenticateRegistrationActivity.this, enums[0].getQueryStatus(), Toast.LENGTH_SHORT).show();
                      //  realmOperations.updateMPin(empCode, newPin);
                        Toast.makeText(mCon, "MPIN changed successfully ", Toast.LENGTH_SHORT).show();
                        clearPin();
                        Intent i = new Intent(AuthenticateRegistrationActivity.this, LoginActivity.class);
                        i.putExtra("IMEINumber", imei);
                        i.putExtra("MACAddress", mac);
                        i.putExtra("versionName", versionName);

                        startActivity(i);

                        finish();
                    } else {
                        Toast.makeText(AuthenticateRegistrationActivity.this, enums[0].getQueryStatus(), Toast.LENGTH_LONG).show();
                    }
                }
                progress.dismiss();
            } catch (Exception e){
              //  Toast.makeText(mCon, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("msg",""+e.getMessage());

            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }

    public void clearPin() {
        oldPinOneEditText.setText("");
        oldPinTwoEditText.setText("");
        oldPinThreeEditText.setText("");
        oldPinFourEditText.setText("");
        newPinOneEditText.setText("");
        newPinTwoEditText.setText("");
        newPinThreeEditText.setText("");
        newPinFourEditText.setText("");
        confirmPinOneEditText.setText("");
        confirmPinTwoEditText.setText("");
        confirmPinThreeEditText.setText("");
        confirmPinFourEditText.setText("");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(new Intent(mCon, SplashScreen.class));
    }

}
