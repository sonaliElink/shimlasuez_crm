package elink.suezShimla.water.crm.Shantanu.Collection;

import static elink.suezShimla.water.crm.MainActivity.IpAddCode;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.tiper.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.MessageWindow;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Shantanu.ModelPackage.BankMasterModel;
import elink.suezShimla.water.crm.Shantanu.ModelPackage.ReceiptHistoryModel;
import elink.suezShimla.water.crm.Shantanu.RecyclerPackage.ReceiptHistoryRecylerView;
import elink.suezShimla.water.crm.Utils.Checker;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;
import elink.suezShimla.water.crm.constant.AppConstant;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllDetailsFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Context mCon;
    LinearLayout custDetailsLinearLayout,bottomLinearLayout,linearBankName,linearChequeInfo;
    TextView txtRecentPymtHistory,txtCustNo,txtCurrDateTime,txtCustName,txtCustAdd,txtCustTariff,txtCustStatus,txtCurrBill,txtOutAmnt,txtArrears,txtDpc
            ,txtPenalty,txtDueDate,txtBack,txtMakePayment;
    MaterialSpinner spnReceiptType,spnTransMode,spnBankName;
    TextInputEditText edtConsNo,edtMobileNo,edtOutstandingAmnt,edtMicr,edtChequeNo,edtChequeDate,edtTransId;
    MaterialButton btnConsSearch;
    String[] receiptTypeList,transModeList;
    ArrayAdapter<String> receiptTypeAdapter,transModeAdapter;
    RealmOperations realmOperations;
    RealmResults<BankMasterModel> bankMasterModel;
    ArrayList<String> bankMasterModelArrayList;
    ArrayAdapter<String> bankMasterAdapter;
    String currDate,micrCode,bankName,responseJSON,TAG="AllDetails",chequeStatus,chequeDissDate,chequeDays,CCID,machineId,cashToday,cashLimit;
    private ConnectionDetector connection;
    private Invoke invServices;
    private ProgressDialog pDialog;
    JSONArray consDetailsArray;

    int arrayLength=0;
    Dialog dialog;
    CardView cardRecentPymtHistory;
    RecyclerView recyclerReceptConsPymtHis;
    ArrayList<ReceiptHistoryModel> receiptHistoryModelArrayList;
    ReceiptHistoryRecylerView modelAdapter;

    public AllDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllDetailsFragment newInstance(String param1, String param2) {
        AllDetailsFragment fragment = new AllDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_all_details, container, false);
        mCon = getActivity();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();
        realmOperations = new RealmOperations(mCon);
        pDialog = new ProgressDialog(mCon);
        pDialog.setMessage(getString(R.string.lbl_please_wait));
        pDialog.setCancelable(false);
        init(view);

        view.getRootView().setFocusableInTouchMode(true);
        view.getRootView().requestFocus();
        view.getRootView().setOnKeyListener( new View.OnKeyListener(){
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {
                    RemoveFragment(new CollectionDashboardFragment());
                    return true;
                }
                return false;
            }
        } );

        return view;
    }

    private void init(View view) {

        custDetailsLinearLayout=view.findViewById(R.id.custDetailsLinearLayout);
        bottomLinearLayout=view.findViewById(R.id.bottomLinearLayout);
        linearBankName=view.findViewById(R.id.linearBankName);
        linearChequeInfo=view.findViewById(R.id.linearChequeInfo);
        edtConsNo=view.findViewById(R.id.edtConsNo);
        btnConsSearch=view.findViewById(R.id.btnConsSearch);
        txtRecentPymtHistory=view.findViewById(R.id.txtRecentPymtHistory);
        txtCustNo=view.findViewById(R.id.txtCustNo);
        txtCurrDateTime=view.findViewById(R.id.txtCurrDateTime);
        txtCustName=view.findViewById(R.id.txtCustName);
        edtMobileNo=view.findViewById(R.id.edtMobileNo);
        txtCustAdd=view.findViewById(R.id.txtCustAdd);
        txtCustTariff=view.findViewById(R.id.txtCustTariff);
        txtCustStatus=view.findViewById(R.id.txtCustStatus);
        spnReceiptType=view.findViewById(R.id.spnReceiptType);
        edtOutstandingAmnt=view.findViewById(R.id.edtOutstandingAmnt);
        txtCurrBill=view.findViewById(R.id.txtCurrBill);
        txtOutAmnt=view.findViewById(R.id.txtOutAmnt);
        txtArrears=view.findViewById(R.id.txtArrears);
        txtDpc=view.findViewById(R.id.txtDpc);
        txtPenalty=view.findViewById(R.id.txtPenalty);
        txtDueDate=view.findViewById(R.id.txtDueDate);
        spnTransMode=view.findViewById(R.id.spnTransMode);
        edtMicr=view.findViewById(R.id.edtMicr);
        spnBankName=view.findViewById(R.id.spnBankName);
        edtChequeNo=view.findViewById(R.id.edtChequeNo);
        edtChequeDate=view.findViewById(R.id.edtChequeDate);
        edtTransId=view.findViewById(R.id.edtTransId);
        txtBack=view.findViewById(R.id.txtBack);
        txtMakePayment=view.findViewById(R.id.txtMakePayment);

        custDetailsLinearLayout.setVisibility(View.GONE);
        bottomLinearLayout.setVisibility(View.GONE);

        spnReceiptType.setEnabled(false);
        receiptTypeList=getResources().getStringArray(R.array.receipt_type_list);
        receiptTypeAdapter = new ArrayAdapter(mCon, R.layout.spin_list, receiptTypeList);
        spnReceiptType.setAdapter(receiptTypeAdapter);
        spnReceiptType.setSelection(0);

        transModeList=getResources().getStringArray(R.array.trans_mode);
        transModeAdapter = new ArrayAdapter(mCon, R.layout.spin_list, transModeList);
        spnTransMode.setAdapter(transModeAdapter);
        spnTransMode.setSelection(0);
        setTransModeBoxes(false,false,false,false,false,View.GONE,View.GONE);

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aaa");
        Date dateNtime = new Date(System.currentTimeMillis());
        txtCurrDateTime.setText(formatter.format(dateNtime));
        currDate = new SimpleDateFormat("dd-MMM-yyyy").format(dateNtime);
        edtChequeDate.setText(currDate);

        spnTransMode.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner materialSpinner, View view, int position, long l) {
                switch (materialSpinner.getSelectedItem().toString()){
                    case "Cash":
                        setTransModeBoxes(false,false,false,false,false,View.GONE,View.GONE);
                        break;
                    case "Cheque":
                        if(!chequeDissDate.equals("")) {
                            if(!campareChequeDatesDiff(chequeDissDate)){
                                Toast.makeText(mCon, "You are not allowed for Cheque Payment", Toast.LENGTH_SHORT).show();
                                spnTransMode.setSelection(0);
                                setTransModeBoxes(false, false, false, false, false,View.GONE,View.GONE);
                                break;
                            }
                        }
                        setTransModeBoxes(true, true, true, true, false, View.VISIBLE, View.VISIBLE);
                        break;
                    case "Demand Draft":
                        setTransModeBoxes(true,true,true,true,false, View.VISIBLE, View.VISIBLE);
                        break;
                    case "CC/DC":
                        setTransModeBoxes(false,false,false,false,true,View.GONE,View.GONE);
                        break;
                    default:
                        setTransModeBoxes(false,false,false,false,false,View.GONE,View.GONE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(MaterialSpinner materialSpinner) {

            }
        });

        edtMicr.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return gestureDetector.onTouchEvent(motionEvent);
            }
        });

        // spinner for BankName/MICR
        bankMasterModel=realmOperations.fetchAllBankMaster();
        bankMasterModelArrayList=new ArrayList<>();
        bankMasterModelArrayList.add("Select");
        for (int i = 0; i < bankMasterModel.size(); i++) {
            BankMasterModel bmm = bankMasterModel.get(i);
            bankMasterModelArrayList.add(bmm.getNAME());
        }
        bankMasterAdapter = new ArrayAdapter(mCon, R.layout.spin_list, bankMasterModelArrayList);
        spnBankName.setAdapter(bankMasterAdapter);
        spnBankName.setSelection(0);

        spnBankName.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(MaterialSpinner materialSpinner) {

            }

            @Override
            public void onItemSelected(MaterialSpinner materialSpinner, View view, int position, long l) {
                if (position != 0) {
                    String result = realmOperations.isExistBankMicr("NAME", spnBankName.getSelectedItem().toString());
                    if (!result.equals("Failure")) {
                        micrCode = result.split("@")[0];
                        bankName = result.split("@")[1];
                        edtMicr.setText(micrCode);
                        edtChequeNo.requestFocus();
                        InputMethodManager imm = (InputMethodManager) mCon.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(spnBankName, InputMethodManager.SHOW_IMPLICIT);
                    }else{
                        Toast.makeText(mCon, "Invalid Bank Name", Toast.LENGTH_SHORT).show();
                    }
                } else{
//                    edtMicr.setText("");
                }
            }

        });

        // Custom Dialog
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.custom_cons_pymt_history);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        cardRecentPymtHistory=dialog.findViewById(R.id.cardRecentPymtHistory);
        recyclerReceptConsPymtHis=dialog.findViewById(R.id.recyclerReceptConsPymtHis);
        recyclerReceptConsPymtHis.setLayoutManager(new LinearLayoutManager(getContext()));

        btnConsSearch.setOnClickListener(this);
        txtRecentPymtHistory.setOnClickListener(this);
        edtChequeDate.setOnClickListener(this);
        txtBack.setOnClickListener(this);
        txtMakePayment.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnConsSearch:

                SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aaa");
                Date dateNtime = new Date(System.currentTimeMillis());
                txtCurrDateTime.setText(formatter.format(dateNtime));
                currDate = new SimpleDateFormat("dd-MMM-yyyy").format(dateNtime);

                custDetailsLinearLayout.setVisibility(View.GONE);
                bottomLinearLayout.setVisibility(View.GONE);
                spnTransMode.setSelection(0);
                setTransModeBoxes(false,false,false,false,false,View.GONE,View.GONE);
                if(!edtConsNo.getText().toString().equals("")){
                    checkSetReceipts();
                }else {
                    Toast.makeText(mCon, "Please enter Customer No", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.txtRecentPymtHistory:
                if(arrayLength > 0) dialog.show();
                else  Toast.makeText(mCon, "Recent payment history not found", Toast.LENGTH_SHORT).show();
                break;
            case R.id.edtChequeDate:
                showDatePickerDialog();
                break;
            case R.id.txtBack:
                RemoveFragment(new CollectionDashboardFragment());
                break;
            case R.id.txtMakePayment:
                    if (!edtOutstandingAmnt.getText().toString().equals("") && convertStringToInt(edtOutstandingAmnt.getText().toString()) > 0) {
                        if (convertStringToInt(edtOutstandingAmnt.getText().toString()) <= convertStringToInt(cashLimit)) {
                            if (spnTransMode.getSelectedItem().toString().equals("Cheque") || spnTransMode.getSelectedItem().toString().equals("Demand Draft")) {
                                if (!spnBankName.getSelectedItem().toString().equals("Select") && !edtMicr.getText().toString().equals("") && !edtChequeNo.getText().toString().equals("") && !edtChequeDate.getText().toString().equals("")) {
                                    validatedMobile();
                                } else {
                                    Toast.makeText(mCon, "Please fill " + spnTransMode.getSelectedItem().toString() + " fields", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                validatedMobile();
                            }
                        } else {
                            Toast.makeText(mCon, "Cash payment is not allowed more than Rs. "+cashLimit, Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(mCon, "0 or negative amount is not allowed as receipt amount.", Toast.LENGTH_LONG).show();
                    }
                break;
        }
    }

    int convertStringToInt(String str){
        return Integer.parseInt(str);
    }

    void validatedMobile(){
        if(!edtMobileNo.getText().toString().equals("")){
            if(Checker.isValidMobile(edtMobileNo.getText().toString())){
                showPaymentDialog();
            }else{
                Toast.makeText(mCon, "Invalid Mobile No.", Toast.LENGTH_SHORT).show();
            }
        }else{
            showPaymentDialog();
        }
    }

    void setTransModeBoxes(boolean b, boolean b1, boolean b2, boolean b3, boolean b4, int v1, int v2){
        edtMicr.setText("");
        edtMicr.setEnabled(b);
        spnBankName.setSelection(0);
        spnBankName.setEnabled(b1);
        edtChequeNo.setText("");
        edtChequeNo.setEnabled(b2);
        edtChequeDate.setText("");
        edtChequeDate.setEnabled(b3);
        edtTransId.setText("");
        edtTransId.setEnabled(b4);
        linearBankName.setVisibility(v1);
        linearChequeInfo.setVisibility(v2);
    }

    final GestureDetector gestureDetector = new GestureDetector(mCon,new GestureDetector.SimpleOnGestureListener() {
        public boolean onDoubleTap(MotionEvent e) {
            if(edtMicr.getText().toString().length()==9 ){
                if(isValidMICRCode(edtMicr.getText().toString())){
                    checkMICRCode(edtMicr.getText().toString());
                }else {
                    spnBankName.setSelection(0);
                    Toast.makeText(mCon, "Invalid MICR Code", Toast.LENGTH_SHORT).show();
                }
            }else {
                spnBankName.setSelection(0);
                Toast.makeText(mCon, "MICR Should be 9 digit", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    });

    public void SetFragment(Fragment fragment1) {
        if (fragment1 != null) {
            ((FragmentActivity) mCon).getSupportFragmentManager().
                    beginTransaction().replace(R.id.container, fragment1,null).
                    addToBackStack(null).commit();
        }
    }

    public void RemoveFragment(Fragment fragment1) {
        if (fragment1 != null) {
            FragmentManager manager = ((FragmentActivity) mCon).getSupportFragmentManager();
            FragmentTransaction trans = manager.beginTransaction();
            trans.remove(fragment1);
            trans.commit();
            manager.popBackStack();

        }
    }

    public void RemoveCurrentFragment() {
            FragmentManager fragmentManager = getFragmentManager();
            if (fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.popBackStack();
        }
    }

    private void showDatePickerDialog() {
        Calendar c = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(mCon, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Update the EditText field with the selected date
                edtChequeDate.setText(dayOfMonth + "-" + getMonthName(monthOfYear + 1) + "-" + year);
            }
        }, c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH)); // Replace year, month, and day with your desired default date
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    public String getMonthName(int monthNumber) {
        String[] monthNames = new DateFormatSymbols().getShortMonths();
        if (monthNumber >= 0 && monthNumber < monthNames.length) {
            return monthNames[monthNumber - 1];
        } else {
            return "";
        }
    }

    public boolean isValidMICRCode(String MICRCode){

        String regex = "^[0-9]{1,9}$";
        Pattern p = Pattern.compile(regex);
        if (MICRCode == null)
        {
            return false;
        }
        Matcher m = p.matcher(MICRCode);
        return m.matches();
    }

    void checkMICRCode(String micrText){
        String result=realmOperations.isExistBankMicr("ID",micrText);
        if(!result.equals("Failure")){
            micrCode=result.split("@")[0];
            bankName=result.split("@")[1];
            spnBankName.setSelection(bankMasterAdapter.getPosition(bankName));
            Toast.makeText(mCon, "MICR Code Verified", Toast.LENGTH_SHORT).show();
        }else{
            spnBankName.setSelection(0);
            Toast.makeText(mCon, "Invalid MICR Code", Toast.LENGTH_SHORT).show();
        }
    }

    void checkSetReceipts(){
        try {
            String[] Params = new String[3];

            Params[0] = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
            Params[1] = new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon, AppConstant.SID));
            Params[2] = edtConsNo.getText().toString(); //0201110462

            if (connection.isConnectingToInternet()) {
                SetReceipt task = new SetReceipt();
                task.execute(Params);
            } else {
                MessageWindow.errorWindow(mCon, getResources().getString(R.string.no_internet_connection));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class SetReceipt extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            if (connection.isConnectingToInternet()) {
                try {
                    String paraName[] = new String[3];
                    paraName[0] = "LoginUser";
                    paraName[1] = "SessionToken";
                    paraName[2] = "ConsumerNo";

                    String username=null,password=null;
                    username=new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon, AppConstant.EMPCODE));
                    password=new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon,AppConstant.PASSWORD));

                    try {
                        responseJSON = invServices.getOtherData(Constants.URL, Constants.NameSpace, "SetReceipt", username,password, params, paraName);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                MessageWindow.errorWindow(mCon, getResources().getString(R.string.no_internet_connection));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            try {
                responseJSON = responseJSON.toUpperCase(Locale.ROOT);
                JSONObject jsonObject=new JSONObject(responseJSON);
                JSONArray table0Array=jsonObject.getJSONArray("TABLE0");
                JSONArray table1Array=jsonObject.getJSONArray("TABLE1");
                Log.d(TAG, "onPostExecute: "+jsonObject);
                hidepDialog();
                if (table1Array.getJSONObject(0).getString("QUERYSTATUS").equals("SUCCESS")) {
                    if(isCcCounter(table1Array.getJSONObject(0))){  // check counter rights
                        if(isCounterClosed(table1Array.getJSONObject(0))){  // check counter is closed or not
                            MessageWindow.msgWindow(mCon, mCon.getResources().getString(R.string.cc_closed));
                        }else{
                            CCID=table1Array.getJSONObject(0).getString("MCM_CCRIGHTS");
                            machineId=table1Array.getJSONObject(0).getString("MCM_MACHINENO");
                            chequeDays=table1Array.getJSONObject(0).getString("MCM_CHQ_PAST_DAYS_COUNT");
                            cashToday=table1Array.getJSONObject(0).getString("CASH_TODAY");
                            cashLimit=table1Array.getJSONObject(0).getString("CASH_LIMIT");
                            getConsDeatils();
                            arrayLength=table0Array.length();
                            setConsReceiptHistory(table0Array);
                        }
                    }else{
                        MessageWindow.msgWindow(mCon,  mCon.getResources().getString(R.string.cc_not_avail));
                    }
                }else{
                    MessageWindow.errorWindow(mCon, getResources().getString(R.string.something_went_wrong));
                }
            } catch (Exception e) {
                hidepDialog();
                MessageWindow.errorWindow(mCon, getResources().getString(R.string.something_went_wrong));
                Log.e("Exceptionss: ", " " + e.getMessage());
            }
        }

        @Override
        protected void onPreExecute() {
            showpDialog();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    boolean isCcCounter(JSONObject jsonObject){
        try {
            if (!jsonObject.getString("MCM_CCRIGHTS").equals("") ||  !jsonObject.getString("MCM_CCRIGHTS").equals("0")
                    || !jsonObject.getString("MCM_CCRIGHTS").equals("-99") ||  !jsonObject.getString("MCM_CCRIGHTS").equals("null") ) {
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    boolean isCounterClosed(JSONObject jsonObject){
        try {
            if (jsonObject.getString("MCM_DAYEND_DT").equals(currDate) && jsonObject.getString("MCM_DAYEND_TAG").equals("1")  ) {
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    void getConsDeatils(){
        try {
            String[] Params = new String[4];

            Params[0] = edtConsNo.getText().toString();
            Params[1] = PreferenceUtil.getZone();
            Params[2] = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
            Params[3] = new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon, AppConstant.SID));

            if (connection.isConnectingToInternet()) {
                ConsumerDetails task = new ConsumerDetails();
                task.execute(Params);
            } else {
                MessageWindow.errorWindow(mCon, getResources().getString(R.string.no_internet_connection));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class ConsumerDetails extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            if (connection.isConnectingToInternet()) {
                try {
                    String paraName[] = new String[4];
                    paraName[0] = "ConsNo";
                    paraName[1] = "DFID";
                    paraName[2] = "LoginUser";
                    paraName[3] = "SessionToken";

                    String username=null,password=null;
                    username=new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon, AppConstant.EMPCODE));
                    password=new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon,AppConstant.PASSWORD));

                    try {
                        responseJSON = invServices.getOtherData(Constants.URL, Constants.NameSpace, "ConsumerDetails", username,password, params, paraName);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                MessageWindow.errorWindow(mCon, getResources().getString(R.string.no_internet_connection));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            try {
                responseJSON = responseJSON.toUpperCase(Locale.ROOT);
                JSONObject jsonObject=new JSONObject(responseJSON);
                consDetailsArray=jsonObject.getJSONArray("TABLE0");
                JSONArray table1Array=jsonObject.getJSONArray("TABLE1");
                Log.d(TAG, "ConsumerDetails: "+jsonObject);
                hidepDialog();
                if (consDetailsArray.getJSONObject(0).getString("QUERYSTATUS").equals("SUCCESS")) {
                    chequeStatus=table1Array.getJSONObject(0).getString("QUERYSTATUS");
                    chequeDissDate=table1Array.getJSONObject(0).getString("DISHDATE");
//                    chequeDissDate="18-May-2023";
                    setConsumerDetails(consDetailsArray.getJSONObject(0));
//                    edtConsNo.setText("");
                    custDetailsLinearLayout.setVisibility(View.VISIBLE);
                    bottomLinearLayout.setVisibility(View.VISIBLE);
                } else {
                    MessageWindow.errorWindow(mCon, mCon.getResources().getString(R.string.cons_not_found));
                }
            } catch (Exception e) {
                hidepDialog();
                MessageWindow.errorWindow(mCon, getResources().getString(R.string.something_went_wrong));
                Log.e("Exceptionss: ", " " + e.getMessage());
            }
        }

        @Override
        protected void onPreExecute() {
            showpDialog();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    private void setConsumerDetails(JSONObject jsonObject) {
        try{
            txtCustNo.setText(jsonObject.getString("SRM_SERVICE_NO"));
//            txtCurrDateTime.setText(jsonObject.getString(""));
            txtCustName.setText(jsonObject.getString("NAME"));
            if(Checker.isValidMobile(jsonObject.getString("MOBILE_NO"))) {
                edtMobileNo.setText(jsonObject.getString("MOBILE_NO"));
                edtMobileNo.setEnabled(false);
            }else {
                edtMobileNo.setText("");
                edtMobileNo.setEnabled(true);
            }
            txtCustAdd.setText(jsonObject.getString("ADDR"));
            txtCustTariff.setText(jsonObject.getString("TFM_TARIFF_NAME"));
            if(jsonObject.getString("SRM_DISCON_TAG_ID").equals("1")){
                txtCustStatus.setText("RBNC");
                txtCustStatus.setBackgroundColor(getResources().getColor(R.color.red));
            }else if(jsonObject.getString("SRM_DISCON_TAG_ID").equals("3")){
                txtCustStatus.setText("Temp. Disconnected");
                txtCustStatus.setBackgroundColor(getResources().getColor(R.color.yellow_700));
            }else if(jsonObject.getString("SRM_DISCON_TAG_ID").equals("4")){
                txtCustStatus.setText("Perm. Disconnected");
                txtCustStatus.setBackgroundColor(getResources().getColor(R.color.red));
            }else {
                txtCustStatus.setText("Connected");
                txtCustStatus.setBackgroundColor(getResources().getColor(R.color.green_500));
            }
            edtOutstandingAmnt.setText(jsonObject.getString("OUTSTANDING"));
            txtCurrBill.setText(jsonObject.getString("OUTSTANDING"));
            txtOutAmnt.setText(jsonObject.getString("OUTSTANDING"));
            txtArrears.setText(jsonObject.getString("ARREAR"));
            txtDpc.setText(jsonObject.getString("DPC"));
            txtPenalty.setText(jsonObject.getString("PENALTY"));
            txtDueDate.setText(jsonObject.getString("SRM_DUE_DATE"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    boolean campareChequeDatesDiff(String inputDateStr) {
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        try {
            Date inputDate = sdf.parse(inputDateStr);
            if (inputDate != null) {
                long timeDifferenceMillis = inputDate.getTime() - currentDate.getTime();
                long daysDifference = TimeUnit.MILLISECONDS.toDays(timeDifferenceMillis);
                if (Math.abs(daysDifference) > Math.abs(Integer.parseInt(chequeDays))) {
                    Log.d(TAG, daysDifference + "campareChequeDatesDiff: The date is greater than 90 days from the current date."); // satisfied cond'n
                    return true;
                } else {
                    Log.d(TAG, daysDifference + "campareChequeDatesDiff: The date is within 90 days from the current date.");
                    return false;
                }
            } else {
                Log.d(TAG, "campareChequeDatesDiff: Invalid date format.");
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    void showPaymentDialog(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(mCon);
        builder1.setTitle("SuezCRM");
        builder1.setMessage( mCon.getResources().getString(R.string.are_u_sure_pymt));
        builder1.setCancelable(false);

        builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        makePayment();
                    }
                });

        builder1.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    void setConsReceiptHistory(JSONArray jsonArray){
        try {
            receiptHistoryModelArrayList=new ArrayList<>();
            for (int i = 0; i <jsonArray.length(); i++) {
                ReceiptHistoryModel rhm = new ReceiptHistoryModel();
                rhm.setConsNo(jsonArray.getJSONObject(i).getString("PM_SERVNO"));
                rhm.setRecptNo(jsonArray.getJSONObject(i).getString("PM_RCPTNO"));
                rhm.setRecptAmnt(jsonArray.getJSONObject(i).getString("PM_AMT"));
                rhm.setBuId(jsonArray.getJSONObject(i).getString("BUID"));
                rhm.setRecptDate(jsonArray.getJSONObject(i).getString("PM_RCPTDT"));
                receiptHistoryModelArrayList.add(rhm);
            }
            modelAdapter = new ReceiptHistoryRecylerView(mCon, receiptHistoryModelArrayList, recyclerReceptConsPymtHis);
            recyclerReceptConsPymtHis.setAdapter(modelAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void makePayment(){
        try {

            String[] Params = new String[29];

            Params[0] = getItemPosition(spnReceiptType.getSelectedItem().toString());
            Params[1] = txtCustNo.getText().toString();
            Params[2] = txtCustName.getText().toString();
            Params[3] = edtMobileNo.getText().toString();
            Params[4] = consDetailsArray.getJSONObject(0).getString("BILL_AMT");
            Params[5] = consDetailsArray.getJSONObject(0).getString("BU");
            Params[6] = consDetailsArray.getJSONObject(0).getString("PC");
            Params[7] = consDetailsArray.getJSONObject(0).getString("METERNO");
            Params[8] = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
            Params[9] = CCID;
            Params[10] = machineId;
            Params[11] = consDetailsArray.getJSONObject(0).getString("BILL_NO");
            Params[12] = getItemPosition(spnTransMode.getSelectedItem().toString());
            Params[13] = spnTransMode.getSelectedItem().toString();
            Params[14] = edtOutstandingAmnt.getText().toString();
            Params[15] = txtCurrDateTime.getText().toString();
            Params[16] = "";  // MerchantTransId
            Params[17] = "";  // CreditDebitCard
            Params[18] = consDetailsArray.getJSONObject(0).getString("BILLMONTH");
            Params[19] = edtMicr.getText().toString();
            Params[20] = edtChequeNo.getText().toString();

            if (edtChequeDate.getText().toString().isEmpty()){
                Params[21] = currDate;

            }else {
                Params[21] = edtChequeDate.getText().toString();
            }
            Params[22] = ""; // AltMobileNo
            Params[23] = "0"; // ifdiscount=3
            Params[24] = ""; // disamnt
            Params[25] = ""; // adjid  20-abhyyojna, 10-advblpaymnet
            Params[26] = IpAddCode; // IP
            Params[27] = consDetailsArray.getJSONObject(0).getString("SRM_ZONE_ID");
            Params[28] = new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon, AppConstant.SID));


            if (connection.isConnectingToInternet()) {
                MakePayment task = new MakePayment();
                task.execute(Params);
            } else {
                MessageWindow.errorWindow(mCon, getResources().getString(R.string.no_internet_connection));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class MakePayment extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            if (connection.isConnectingToInternet()) {
                try {
                    String paraName[] = new String[29];
                    paraName[0] = "PayTypeID";          paraName[1] = "ConsNo";             paraName[2] = "ConsName";       paraName[3] = "MobileNo";
                    paraName[4] = "BillAmt";            paraName[5] = "BUID";               paraName[6] = "PCID";           paraName[7] = "MeterNo";
                    paraName[8] = "LoginUser";          paraName[9] = "CCID";               paraName[10] = "MachineNo";     paraName[11] = "BillNo";
                    paraName[12] = "PayModeID";         paraName[13] = "PayModeName";       paraName[14] = "PaidAmt";       paraName[15] = "PayDate";
                    paraName[16] = "MerchantTransId";   paraName[17] = "CreditDebitCard";   paraName[18] = "BillMonth";     paraName[19] = "MICR";
                    paraName[20] = "ChequeNo";          paraName[21] = "ChequeDate";        paraName[22] = "AltMobileNo";   paraName[23] = "ifDiscount";
                    paraName[24] = "DiscountAmt";       paraName[25] = "AdjType";           paraName[26] = "IP";           paraName[27] = "ZoneID";
                    paraName[28] = "SessionToken";

                    String username=null,password=null;
                    username=new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon, AppConstant.EMPCODE));
                    password=new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon,AppConstant.PASSWORD));

                    try {
                        responseJSON = invServices.getOtherData(Constants.URL, Constants.NameSpace, "MakePayment", username,password, params, paraName);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                MessageWindow.errorWindow(mCon, getResources().getString(R.string.no_internet_connection));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            try {
                JSONObject jsonObject=new JSONObject(responseJSON);
                String tempResult=jsonObject.getString("Result");
                Log.d(TAG, "MakePayment: "+tempResult);
                hidepDialog();
                if(tempResult.split(",")[0].equals("Success")){
//                    MessageWindow.successWindow(mCon,"Payment is successful. \n Receipt No: "+tempResult.split(",")[1]);
                    Toast.makeText(mCon.getApplicationContext(), "Payment Successful", Toast.LENGTH_LONG).show();
                    custDetailsLinearLayout.setVisibility(View.GONE);
                    bottomLinearLayout.setVisibility(View.GONE);
                    RemoveCurrentFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("Param", tempResult.replaceFirst("Success,", "")); //Receipt No
                    ReceiptWebviewFragment rwf = new ReceiptWebviewFragment();
                    rwf.setArguments(bundle);
                    SetFragment(rwf);
                }else{
                    MessageWindow.errorWindow(mCon, tempResult.split(",")[1]);
                }
            } catch (Exception e) {
                hidepDialog();
                MessageWindow.errorWindow(mCon, getResources().getString(R.string.something_went_wrong));
                Log.e("Exceptionss: ", " " + e.getMessage());
            }
        }

        @Override
        protected void onPreExecute() {
            showpDialog();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    String getItemPosition(String item){
        switch(item){
            case "Water Charges":
                 return "1";
            case "Faulty Meter Replacement":
                 return "53";
            case "Cash":
                 return "1";
            case "Cheque":
                 return "2";
            case "Demand Draft":
                 return "3";
            case "CC/DC":
                 return "4";
        }
        return "1";
    }

    @Override
    public void onPause() {
        super.onPause();
        dialog.dismiss();
    }

}


