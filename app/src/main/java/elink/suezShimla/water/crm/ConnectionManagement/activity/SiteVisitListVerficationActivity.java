package elink.suezShimla.water.crm.ConnectionManagement.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Objects;

import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.ConnectionManagement.model.SiteVisitModel;
import elink.suezShimla.water.crm.ConnectionManagement.utility.TimeReceiver;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;

public class SiteVisitListVerficationActivity extends AppCompatActivity implements View.OnFocusChangeListener, View.OnKeyListener, TextWatcher, View.OnClickListener {
    private SiteVisitModel model;
    private EditText pinOneEditText, pinTwoEditText, pinThreeEditText, pinFourEditText, pin_hidden_edittext;
    private Context mCon;
    String mobileNoStr="",requestNoStr="";
    TextView tvMobile,tv_resend,tv_timer;
    ImageView iv_back;
    Button btn_continue;

    String pinOneStr="",pinTwoStr="",pinThreeStr="",pinFourStr="",pinFinalStr="",apptype="";

    private CountDownTimer countDownTimer; // lock user for 30 sec
    private int timeToStart;
    private int Count =4;
    private TimerState timerState;
    private static final int MAX_TIME = 30; //Time length is 30 seconds

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_verification);

        mCon=this;
        init();
        checkUnsuccessfulAttempts();
    }

    private void init() {
        model = getIntent().getParcelableExtra("siteVisitEntity");



        mobileNoStr = model.getAM_APP_PHONEM();
        requestNoStr = model.getREQUEST_NO();

        pinOneEditText = findViewById(R.id.pinOneEditText);
        pinTwoEditText = findViewById(R.id.pinTwoEditText);
        pinThreeEditText = findViewById(R.id.pinThreeEditText);
        pinFourEditText = findViewById(R.id.pinFourEditText);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        btn_continue = findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(this);
        tv_timer = findViewById(R.id.tv_timer);
        tv_resend = findViewById(R.id.tv_resend);
        tvMobile = findViewById(R.id.tvMobile);
        tvMobile.setText(mobileNoStr);
        pin_hidden_edittext = findViewById(R.id.pin_hidden_edittext);

        pinFourEditText.addTextChangedListener(new TextWatcher() {
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

        setPINListeners();

    }

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
        }
    }
    private void setPINListeners() {
        pin_hidden_edittext.addTextChangedListener(this);

        pinOneEditText.setOnFocusChangeListener(this);
        pinTwoEditText.setOnFocusChangeListener(this);
        pinThreeEditText.setOnFocusChangeListener(this);
        pinFourEditText.setOnFocusChangeListener(this);

        pinOneEditText.setOnKeyListener(this);
        pinTwoEditText.setOnKeyListener(this);
        pinThreeEditText.setOnKeyListener(this);
        pinFourEditText.setOnKeyListener(this);
        pin_hidden_edittext.setOnKeyListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        final int id = v.getId();
        switch (id) {
            case R.id.pinOneEditText:
                if (hasFocus) {
                    setFocus(pin_hidden_edittext);
                    showSoftKeyboard(pin_hidden_edittext);
                }
                break;

            case R.id.pinTwoEditText:
                if (hasFocus) {
                    setFocus(pin_hidden_edittext);
                    showSoftKeyboard(pin_hidden_edittext);
                }
                break;

            case R.id.pinThreeEditText:
                if (hasFocus) {
                    setFocus(pin_hidden_edittext);
                    showSoftKeyboard(pin_hidden_edittext);
                }
                break;

            case R.id.pinFourEditText:
                if (hasFocus) {
                    setFocus(pin_hidden_edittext);
                    showSoftKeyboard(pin_hidden_edittext);
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
                        if (pin_hidden_edittext.getText().length() == 4)
                            pinFourEditText.setText("");
                        else if (pin_hidden_edittext.getText().length() == 3)
                            pinThreeEditText.setText("");
                        else if (pin_hidden_edittext.getText().length() == 2)
                            pinTwoEditText.setText("");
                        else if (pin_hidden_edittext.getText().length() == 1)
                            pinOneEditText.setText("");
                        if (pin_hidden_edittext.length() > 0)
                            pin_hidden_edittext.setText(pin_hidden_edittext.getText().subSequence(0, pin_hidden_edittext.length() - 1));
                        return true;
                    }

                    break;

                default:
                    return false;
            }
        }

        return false;    }


    public void showSoftKeyboard(EditText editText) {
        if (editText == null)
            return;

        InputMethodManager imm = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, 0);
    }

    public static void setFocus(EditText editText) {
        if (editText == null)
            return;

        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() == 0) {
            pinOneEditText.setText("");
        } else if (s.length() == 1) {
            pinOneEditText.setText(s.charAt(0) + "");
            pinTwoEditText.setText("");
            pinThreeEditText.setText("");
            pinFourEditText.setText("");
        } else if (s.length() == 2) {
            pinTwoEditText.setText(s.charAt(1) + "");
            pinThreeEditText.setText("");
            pinFourEditText.setText("");
        } else if (s.length() == 3) {
            pinThreeEditText.setText(s.charAt(2) + "");
            pinFourEditText.setText("");
        } else if (s.length() == 4) {
            pinFourEditText.setText(s.charAt(3) + "");

            hideSoftKeyboard(pinFourEditText);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void hideSoftKeyboard(EditText editText) {
        if (editText == null)
            return;

        InputMethodManager imm = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:{
                startBackActivity();
            }
            break;
            case R.id.btn_continue:{
                if(validation()) {

                    verfiyPin();
                }
            }
            break;
            default:
                break;
        }

    }

    private void verfiyPin() {
        pinFinalStr=pinOneStr+pinTwoStr+pinThreeStr+pinFourStr;
        if(pinFinalStr.equalsIgnoreCase("1234")){
            apptype = model.getAM_AAPP_NO_TYPE();
           // String applicationNumberFixStr = model.getREQUEST_NO();
            // String str = requestNoStr;
            //String[] arrOfStr = str.split("C", 5);
           // String sitedata= arrOfStr[0];
            if(apptype.equals("6")||apptype.equals("7")) {
                startsitevisit();/*shifting boring*/
            }else {
                startNextActivity();

            }
        }else{
            Toast.makeText(this, "Please enter Correct Code", Toast.LENGTH_SHORT).show();
            setClearField();
        }
    }

    private void setClearField() {
        pinOneEditText.setText(null);
        pinTwoEditText.setText(null);
        pinThreeEditText.setText(null);
        pinFourEditText.setText(null);
        pin_hidden_edittext.setText(null);
        setFocus(pin_hidden_edittext);
        showSoftKeyboard(pin_hidden_edittext);

    }

    private boolean validation() {

        pinOneStr= pinOneEditText.getText().toString().trim();
        pinTwoStr= pinTwoEditText.getText().toString().trim();
        pinThreeStr= pinThreeEditText.getText().toString().trim();
        pinFourStr= pinFourEditText.getText().toString().trim();

        if(pinOneStr.equalsIgnoreCase("")){
            Toast.makeText(this, "Please enter 4 digit code  ", Toast.LENGTH_SHORT).show();
            return false;
        }
         if(pinTwoStr.equalsIgnoreCase("")){
            Toast.makeText(this, "Please enter 4 digit code  ", Toast.LENGTH_SHORT).show();
            return false;
        }
         if(pinThreeStr.equalsIgnoreCase("")){
            Toast.makeText(this, "Please enter 4 digit code  ", Toast.LENGTH_SHORT).show();
            return false;
        }
         if(pinFourStr.equalsIgnoreCase("")){
            Toast.makeText(this, "Please enter 4 digit code  ", Toast.LENGTH_SHORT).show();
            return false;
        }



        return true;
    }



    private void startNextActivity() {
        Intent i = new Intent(SiteVisitListVerficationActivity.this,SiteVisitListActivityDetails.class);
        i.putExtra("siteVisitEntity",model);
        startActivity(i);

        finish();
    }
    private void startsitevisit() {
        Intent i = new Intent(SiteVisitListVerficationActivity.this,Siteuploaddocs.class);
        i.putExtra("siteVisitEntity",model);

        startActivity(i);

        finish();
    }
    private void startBackActivity() {
        Intent intent = new Intent(this,SiteVisitListActivity.class);
        startActivity(intent);
        finish();
    }

    private void checkUnsuccessfulAttempts() {


          //  btn_login.setEnabled(false);
            if (timerState == TimerState.STOPPED) {
                UtilitySharedPreferences.setStartedTime(SiteVisitListVerficationActivity.this,(int) getNow());
                startTimer();
                timerState = TimerState.RUNNING;
            }

    }
//TODO : lock screen

    private enum TimerState {
        STOPPED,
        RUNNING
    }
    private long getNow() {
        Calendar rightNow = Calendar.getInstance();
        return rightNow.getTimeInMillis() / 1000;
    }
    private void startTimer() {
        countDownTimer = new CountDownTimer(timeToStart * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timeToStart -= 1;
                updatingUI();
            }

            @Override
            public void onFinish() {
                timerState = TimerState.STOPPED;
                onTimerFinish();
                updatingUI();
            }
        }.start();
    }
    private void updatingUI() {
        if (timerState == TimerState.RUNNING) {
            tv_resend.setVisibility(View.GONE);

            tv_timer.setVisibility(View.VISIBLE);
            tv_timer.setText("RESEND OTP activate after " + timeToStart+" seconds.");
           // tv_timer.setText(""+timeToStart);

// noticeText.setText("Countdown Timer is running...");
        } else {
            tv_timer.setVisibility(View.GONE);
            tv_resend.setVisibility(View.VISIBLE);


            //  Count = 0;
// noticeText.setText("Countdown Timer stopped!");
        }

    }

    private void onTimerFinish() {
        tv_timer.setText("Done!");
        UtilitySharedPreferences.setStartedTime(SiteVisitListVerficationActivity.this,0);
        timeToStart = MAX_TIME;
        updatingUI();
    }

    private void initTimer() {

        long startTime = UtilitySharedPreferences.getStartedTime(SiteVisitListVerficationActivity.this);
        if (startTime > 0) {
            timeToStart = (int) (MAX_TIME - (getNow() - startTime));
            if (timeToStart <= 0) {
// TIMER EXPIRED
                timeToStart = MAX_TIME;
                timerState = TimerState.STOPPED;
                onTimerFinish();
            } else {
                startTimer();
                timerState = TimerState.RUNNING;
            }
        } else {
            timeToStart = MAX_TIME;
            timerState = TimerState.STOPPED;
        }
    }
    public void setAlarmManager() {
        int wakeUpTime = (UtilitySharedPreferences.getStartedTime(SiteVisitListVerficationActivity.this) + MAX_TIME) * 1000;
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, TimeReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            am.setAlarmClock(new AlarmManager.AlarmClockInfo(wakeUpTime, sender), sender);
        } else {
            am.set(AlarmManager.RTC_WAKEUP, wakeUpTime, sender);
        }
    }
    public void removeAlarmManager() {
        Intent intent = new Intent(this, TimeReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        am.cancel(sender);
    }

    @Override
    public void onBackPressed() {
        startBackActivity();
        super.onBackPressed();
    }
    @Override
    public void onResume() {
        super.onResume();

        App myApp = (App) this.getApplication();
        if (myApp.wasInBackground) {
            finish();
            startActivity(new Intent(this, SplashScreen.class));
        }

        myApp.stopActivityTransitionTimer();
    }

  /*  @Override
    public void onPause() {
        super.onPause();
        ((App) this.getApplication()).startActivityTransitionTimer();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(new Intent(mCon, SplashScreen.class));
    }*/

}
