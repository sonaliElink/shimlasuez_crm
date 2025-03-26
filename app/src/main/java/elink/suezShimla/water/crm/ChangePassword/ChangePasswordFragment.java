package elink.suezShimla.water.crm.ChangePassword;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.security.SecureRandom;
import java.util.Objects;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.ChangePassword.Model.ChangePinModel;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.MessageWindow;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Register.RegisterActivity;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;
import elink.suezShimla.water.crm.constant.AppConstant;


public class ChangePasswordFragment extends Fragment implements View.OnFocusChangeListener, View.OnKeyListener, TextWatcher {
    private Context mCon;

    private TextInputEditText oldPinOneEditText, oldPinTwoEditText, oldPinThreeEditText, oldPinFourEditText, newPinOneEditText, newPinTwoEditText, newPinThreeEditText, newPinFourEditText,
            confirmPinOneEditText, confirmPinTwoEditText, confirmPinThreeEditText, confirmPinFourEditText, mPinHiddenEditText;
    private TextView oldPinErrorTextView, newPinErrorTextView, confirmPinErrorTextView;
    private MaterialButton changePinButton;
    private String oldPinOneStr, oldPinTwoStr, oldPinThreeStr, oldPinFourStr, newPinOneStr, newPinTwoStr, newPinThreeStr, newPinFourStr, confirmPinOneStr, confirmPinTwoStr,
            confirmPinThreeStr, confirmPinFourStr, oldPin, newPin, confirmPin;
    private ChangePinModel changePinModel;
    private String jsonResponse = "", empCode;
    private Gson gson;
    private Invoke invServices;
    private ConnectionDetector connection;
    private RealmOperations realmOperations;
    private MaterialDialog progress;
    private String mac, imei,versionName;

    private KeyGenerator keyGenerator;
    private SecretKey secretKey;
    private byte[] IV = new byte[16];
    private SecureRandom random;
    AesAlgorithm aes;

    Handler handler = new Handler();

    Runnable r = new Runnable() {

        @Override
        public void run() {

            Log.d("HandlerRun", "startHandlerOnCreate");
            // TODO Auto-generated method stub
//                Toast.makeText(ActivityCashWithdrawl.this, "User is inactive from last 1 minutes",Toast.LENGTH_SHORT).show();
            timeoutMessage("User is inactive from last 1 minutes. Do you want to Continue?", "BBPS ", RegisterActivity.class);
        }
    };
    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onPause() {
        super.onPause();
        realmOperations.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // prevent ss and hide content when app is on background
      //  getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_MOVE){
                    stopHandler();//stop first and then start
                    startHandler();                }
                return true;
            }
        });
        mCon = getActivity();
        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();
        realmOperations = new RealmOperations(mCon);

        try {
            // AES Algorithm for encryption / decryption

            keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            secretKey = keyGenerator.generateKey();

            random = new SecureRandom();
            random.nextBytes(IV);

            aes=new AesAlgorithm();

        } catch (Exception e) {
            e.printStackTrace();
        }

        oldPinOneEditText = view.findViewById(R.id.oldPinOneEditText);
        oldPinTwoEditText = view.findViewById(R.id.oldPinTwoEditText);
        oldPinThreeEditText = view.findViewById(R.id.oldPinThreeEditText);
        oldPinFourEditText = view.findViewById(R.id.oldPinFourEditText);
        newPinOneEditText = view.findViewById(R.id.newPinOneEditText);
        newPinTwoEditText = view.findViewById(R.id.newPinTwoEditText);
        newPinThreeEditText = view.findViewById(R.id.newPinThreeEditText);
        newPinFourEditText = view.findViewById(R.id.newPinFourEditText);
        confirmPinOneEditText = view.findViewById(R.id.confirmPinOneEditText);
        confirmPinTwoEditText = view.findViewById(R.id.confirmPinTwoEditText);
        confirmPinThreeEditText = view.findViewById(R.id.confirmPinThreeEditText);
        confirmPinFourEditText = view.findViewById(R.id.confirmPinFourEditText);
        mPinHiddenEditText = view.findViewById(R.id.pin_hidden_edittext);

        oldPinErrorTextView = view.findViewById(R.id.oldPinErrorTextView);
        newPinErrorTextView = view.findViewById(R.id.newPinErrorTextView);
        confirmPinErrorTextView = view.findViewById(R.id.confirmPinErrorTextView);

        changePinButton = view.findViewById(R.id.changePinButton);

        SharedPreferences prefs = mCon.getSharedPreferences("MyPrefsFile", MODE_PRIVATE);

        try {
            // Decrypt EmpCode
            empCode =  new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mac = PreferenceUtil.getMac();
        imei = PreferenceUtil.getImei();
        //versionName = UtilitySharedPreferences.getPrefs();
        versionName = UtilitySharedPreferences.getPrefs(mCon, AppConstant.APPVERSION);
        try {
            versionName = aes.decrypt(versionName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        changePinModel = realmOperations.fetchDataReadingByEmpCode(PreferenceUtil.getEmployeeCode());

        setPINListeners();

        hideKeyboardOnLastEditText(confirmPinFourEditText);

        changePinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });

        return view;
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

        InputMethodManager imm = (InputMethodManager) mCon.getSystemService(Service.INPUT_METHOD_SERVICE);
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

        InputMethodManager imm = (InputMethodManager) mCon.getSystemService(Service.INPUT_METHOD_SERVICE);
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
                MessageWindow.messageWindow(mCon, getResources().getString(R.string.new_pin_cannot_be_same_as_old_pin), "Error in Changing Password");
            } else {
                String params[] = new String[6];

                params[0] = empCode;
                params[1] = oldPin;
                params[2] = PreferenceUtil.getImei();
                params[3] = PreferenceUtil.getMac();
                params[4] = "2";
                params[5] = newPin;

                if (connection.isConnectingToInternet()) {
                    ChangeMPin changeMPin = new ChangeMPin();
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
                    Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
                }
            }
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

    private void hideKeyboardOnLastEditText(final TextInputEditText fourthEditText) {
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

    private void hideSoftKeyboard() {
        if (Objects.requireNonNull(getActivity()).getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) mCon.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(getActivity().getCurrentFocus()).getWindowToken(), 0);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class ChangeMPin extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                String paraName[] = new String[6];

                paraName[0] = "NAME";
                paraName[1] = "MPIN";
                paraName[2] = "IMEI";
                paraName[3] = "MAC";
                paraName[4] = "TYPE";
                paraName[5] = "NEWMPIN";

                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, "UserLogin", params, paraName);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                ChangePinModel[] enums = gson.fromJson(jsonResponse, ChangePinModel[].class);

                if (enums.length > 0) {
                    if (enums[0].getQueryStatus().equalsIgnoreCase("Success")) {
                        progress.dismiss();
                        PreferenceUtil.setPin(newPin);
                        Toast.makeText(mCon, "MPin Changed Succesfully", Toast.LENGTH_SHORT).show();
                        realmOperations.updateMPin(PreferenceUtil.getEmployeeCode(), newPin);
                        clearPin();
                        Intent i = new Intent(mCon, SplashScreen.class);
                        i.putExtra("IMEINumber", imei);
                        i.putExtra("MACAddress", mac);
                        i.putExtra("versionName", versionName);

                        startActivity(i);
                        getActivity().finish();


                    } else {
                        Toast.makeText(mCon, enums[0].getQueryStatus(), Toast.LENGTH_LONG).show();
                        progress.dismiss();

                    }
                }
                progress.dismiss();
            } catch (Exception e){
                String error = e.toString();
                ErrorClass.errorData(mCon, "ChangePasswordFragment", "click change PinButton", error);

            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }





    private void clearPin() {
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



    public void stopHandler() {
        Log.d("HandlerRun", "stopHandlerMain");
        handler.removeCallbacks(r);
    }

    public void startHandler() {
        Log.d("HandlerRun", "startHandlerMain");
        handler.postDelayed(r, 1 * 60 * 1000); //for 5 minutes
    }

    public void timeoutMessage(String msg, String title, final Class<?> cls) {

        stopHandler();
        startActivity(new Intent(getActivity(), SplashScreen.class));
        getActivity().finish();
    }

}
