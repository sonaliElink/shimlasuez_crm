package elink.suezShimla.water.crm.Shantanu.Collection;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.MessageWindow;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Shantanu.ModelPackage.ReceiptHistoryModel;
import elink.suezShimla.water.crm.Shantanu.RecyclerPackage.ReceiptHistoryRecylerView;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;
import elink.suezShimla.water.crm.constant.AppConstant;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CollectionDashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CollectionDashboardFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Context mCon;
    SwipeRefreshLayout refreshLayout;
    LinearLayout linearCCAvil;
    PieChart amountChart;
    TextView txtReceipt,txtAmount;
    CardView cardBarChart,cardRecptAmnt,cardPymtHistory;
    RecyclerView recyclerReceptPymtHis;
    MaterialButton btnMakePayment;
    private ConnectionDetector connection;
    private Invoke invServices;
    private ProgressDialog pDialog;
    String responseJSON,TAG="CCDash",currDate;
    ArrayList<ReceiptHistoryModel> receiptHistoryModelArrayList;
    ReceiptHistoryRecylerView modelAdapter;
    JSONArray receiptDataJsonArray,counterDataJsonArray;

    public CollectionDashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CollectionDashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CollectionDashboardFragment newInstance(String param1, String param2) {
        CollectionDashboardFragment fragment = new CollectionDashboardFragment();
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
        // prevent ss and hide content when app is on background
     //   getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_collection_dashboard, container, false);
        mCon = getActivity();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();
        pDialog = new ProgressDialog(mCon);
        pDialog.setMessage(getString(R.string.lbl_please_wait));
        pDialog.setCancelable(false);

//        SetFragment(new PrinterFragment());

        init(view);

        return view;
    }

    private void init(View view) {
        refreshLayout = view.findViewById(R.id.refreshLayout);
        linearCCAvil = view.findViewById(R.id.linearCCAvil);
        cardBarChart = view.findViewById(R.id.cardBarChart);
        cardRecptAmnt = view.findViewById(R.id.cardRecptAmnt);
        amountChart = view.findViewById(R.id.amountChart);
        txtReceipt = view.findViewById(R.id.txtReceipt);
        txtAmount = view.findViewById(R.id.txtAmount);
        cardPymtHistory = view.findViewById(R.id.cardPymtHistory);
        recyclerReceptPymtHis = view.findViewById(R.id.recyclerReceptPymtHis);
        btnMakePayment = view.findViewById(R.id.btnMakePayment);

        refreshLayout.setColorSchemeResources(android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark, android.R.color.holo_blue_dark);

        recyclerReceptPymtHis.setLayoutManager(new LinearLayoutManager(mCon));
        receiptHistoryModelArrayList=new ArrayList<>();

        cardBarChart.setVisibility(View.GONE);
        cardRecptAmnt.setVisibility(View.GONE);
        cardPymtHistory.setVisibility(View.GONE);
        btnMakePayment.setVisibility(View.GONE);

        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        currDate = sdf.format(currentDate);

        checkSetReceipts();

        // Refresh  the layout
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerReceptPymtHis.setLayoutManager(new LinearLayoutManager(mCon));
                receiptHistoryModelArrayList=new ArrayList<>();

                cardBarChart.setVisibility(View.GONE);
                cardRecptAmnt.setVisibility(View.GONE);
                cardPymtHistory.setVisibility(View.GONE);
                btnMakePayment.setVisibility(View.GONE);

                currDate = sdf.format(currentDate);

                checkSetReceipts();
                refreshLayout.setRefreshing(false);
            }
        });

        btnMakePayment.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnMakePayment:
                SetFragment(new AllDetailsFragment());
                break;
        }
    }

    public void SetFragment(Fragment fragment1) {
        if (fragment1 != null) {
            ((FragmentActivity) getContext()).getSupportFragmentManager().
                    beginTransaction().replace(R.id.container, fragment1,null).
                    addToBackStack(null).commit();
        }
    }

    void checkSetReceipts(){
        try {
            String[] Params = new String[3];

            Params[0] = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
            Params[1] = new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon, AppConstant.SID));
            Params[2] = ""; //0201110462

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
                JSONObject jsonObject=new JSONObject(responseJSON);
                receiptDataJsonArray=jsonObject.getJSONArray("table0");
                counterDataJsonArray=jsonObject.getJSONArray("table1");


                Log.d(TAG, "onPostExecute: "+jsonObject);
                hidepDialog();
                if (counterDataJsonArray.getJSONObject(0).getString("QueryStatus").equals("Success")) {
                    if(isCcCounter(counterDataJsonArray.getJSONObject(0))){
                        setPaymentDashboard();
                    }else{
                        MessageWindow.msgWindow(mCon, mCon.getResources().getString(R.string.cc_not_avail));
                    }
                }else{
                    MessageWindow.errorWindow(mCon, mCon.getResources().getString(R.string.cc_not_avail));
                }

            } catch (Exception e) {
                hidepDialog();
                MessageWindow.errorWindow(mCon,getResources().getString(R.string.something_went_wrong));
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
            if (!jsonObject.getString("MCM_CCRIGHTS").equals("") &&  !jsonObject.getString("MCM_CCRIGHTS").equals("0")
                    && !jsonObject.getString("MCM_CCRIGHTS").equals("-99") &&  !jsonObject.getString("MCM_CCRIGHTS").equals("null") ) {
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

    void setPaymentDashboard(){
        try {
            String[] Params = new String[4];

            Params[0] = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
            Params[1] = currDate;
            Params[2] = "0";
            Params[3] = new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon, AppConstant.SID));

            if (connection.isConnectingToInternet()) {
                SetPaymentDashboard task = new SetPaymentDashboard();
                task.execute(Params);
            } else {
                MessageWindow.errorWindow(mCon, getResources().getString(R.string.no_internet_connection));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class SetPaymentDashboard extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            if (connection.isConnectingToInternet()) {
                try {
                    String paraName[] = new String[4];
                    paraName[0] = "LoginUser";
                    paraName[1] = "PayDate";
                    paraName[2] = "Tag";
                    paraName[3] = "SessionToken";

                    String username=null,password=null;
                    username=new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon, AppConstant.EMPCODE));
                    password=new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon,AppConstant.PASSWORD));

                    try {
                        responseJSON = invServices.getOtherData(Constants.URL, Constants.NameSpace, "PAYMENTDASHBOARD", username,password, params, paraName);
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
                JSONArray jsonArray=new JSONArray(responseJSON);
                Log.d(TAG, "onPostExecute: "+responseJSON);
                hidepDialog();
                // set barchart data

                if(!jsonArray.getJSONObject(0).getString("TOTAL_RECEIPT").equals("0")) {
                    showAmountPieChart(amountChart,jsonArray,true);
                    txtReceipt.setText(jsonArray.getJSONObject(0).getString("TOTAL_RECEIPT"));
                    txtAmount.setText(jsonArray.getJSONObject(0).getString("TOTAL_AMOUNT"));
                }else{
                    showAmountPieChart(amountChart,jsonArray,false);
                    txtReceipt.setText("0");
                    txtAmount.setText("0");
                }
                cardBarChart.setVisibility(View.VISIBLE);
                cardRecptAmnt.setVisibility(View.VISIBLE);

                // set receipt history
                if(!receiptDataJsonArray.getJSONObject(0).getString("QueryStatus").equals("Failure")){
                    setReceiptHistory(receiptDataJsonArray);
                    cardPymtHistory.setVisibility(View.VISIBLE);
                }else{
                    cardPymtHistory.setVisibility(View.GONE);
                }

                // check is counter closed
                if (isCounterClosed(counterDataJsonArray.getJSONObject(0))) {
                    btnMakePayment.setVisibility(View.GONE);
                    MessageWindow.msgWindow(mCon, mCon.getResources().getString(R.string.cc_closed));
                } else {
                    btnMakePayment.setVisibility(View.VISIBLE);
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

    void setReceiptHistory(JSONArray jsonArray){
        try {
            for (int i = 0; i <jsonArray.length(); i++) {
                ReceiptHistoryModel rhm = new ReceiptHistoryModel();
                rhm.setConsNo(jsonArray.getJSONObject(i).getString("PM_SERVNO"));
                rhm.setRecptNo(jsonArray.getJSONObject(i).getString("PM_RCPTNO"));
                rhm.setRecptAmnt(jsonArray.getJSONObject(i).getString("PM_AMT"));
                rhm.setBuId(jsonArray.getJSONObject(i).getString("BUID"));
                rhm.setRecptDate("");
                receiptHistoryModelArrayList.add(rhm);
            }
            modelAdapter = new ReceiptHistoryRecylerView(mCon, receiptHistoryModelArrayList, recyclerReceptPymtHis);
            recyclerReceptPymtHis.setAdapter(modelAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

   private void showAmountPieChart(PieChart pieChart, JSONArray jsonArray, boolean isJsonArray){

        try {
            ArrayList<PieEntry> pieEntries = new ArrayList<>();
            String label = "";

            //initializing data
            Map<String, Integer> typeAmountMap = new HashMap<>();
            if(!jsonArray.getJSONObject(0).getString("TOTAL_CASH").equals("0"))
                typeAmountMap.put("Cash " + jsonArray.getJSONObject(0).getString("TOTAL_CASH"), Integer.parseInt(jsonArray.getJSONObject(0).getString("TOTAL_CASH")));
            if(!jsonArray.getJSONObject(0).getString("TOTAL_CHEQUE").equals("0"))
                typeAmountMap.put("Cheque " + jsonArray.getJSONObject(0).getString("TOTAL_CHEQUE"), Integer.parseInt(jsonArray.getJSONObject(0).getString("TOTAL_CHEQUE")));
            if(!jsonArray.getJSONObject(0).getString("TOTAL_DD").equals("0"))
                typeAmountMap.put("DD " + jsonArray.getJSONObject(0).getString("TOTAL_DD"), Integer.parseInt(jsonArray.getJSONObject(0).getString("TOTAL_DD")));
            if(!jsonArray.getJSONObject(0).getString("TOTAL_CC_DC").equals("0"))
                typeAmountMap.put("CD/DD " + jsonArray.getJSONObject(0).getString("TOTAL_CC_DC"), Integer.parseInt(jsonArray.getJSONObject(0).getString("TOTAL_CC_DC")));
//            typeAmountMap.put("Online " + jsonArray.getJSONObject(0).getString("TOTAL_ONLINE"), Integer.parseInt(jsonArray.getJSONObject(0).getString("TOTAL_ONLINE")));
//            typeAmountMap.put("RTGS " + jsonArray.getJSONObject(0).getString("TOTAL_RTGS"), Integer.parseInt(jsonArray.getJSONObject(0).getString("TOTAL_RTGS")));
//            typeAmountMap.put("UPI " + jsonArray.getJSONObject(0).getString("TOTAL_UPI"), Integer.parseInt(jsonArray.getJSONObject(0).getString("TOTAL_UPI")));

//            typeAmountMap.put("CD/DD 58123", 58123);

            //initializing colors for the entries
            ArrayList<Integer> colors = new ArrayList<>();
            colors.add(Color.parseColor("#FFA726"));
            colors.add(Color.parseColor("#66BB6A"));
            colors.add(Color.parseColor("#EF5350"));

            //input data and fit data into pie chart entry
            for (String type : typeAmountMap.keySet()) {
                pieEntries.add(new PieEntry(typeAmountMap.get(type).floatValue(), type));
            }

            //collecting the entries with label name
            PieDataSet pieDataSet = new PieDataSet(pieEntries, label);
            //setting text size of the value
            pieDataSet.setValueTextSize(12f);
            //providing color list for coloring different entries
            pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
            //grouping the data set from entry to chart
            PieData pieData = new PieData(pieDataSet);
            //showing the value of the entries, default true if not set
            pieData.setDrawValues(true);

            if(isJsonArray) pieChart.setData(pieData);  // check if data is available or not
            pieChart.getDescription().setEnabled(false);
            pieChart.invalidate();

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

}