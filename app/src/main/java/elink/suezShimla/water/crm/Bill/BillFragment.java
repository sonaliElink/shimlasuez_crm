package elink.suezShimla.water.crm.Bill;

import static android.Manifest.permission.BLUETOOTH;
import static android.Manifest.permission.BLUETOOTH_ADMIN;
import static android.Manifest.permission.BLUETOOTH_ADVERTISE;
import static android.Manifest.permission.BLUETOOTH_CONNECT;
import static android.Manifest.permission.BLUETOOTH_SCAN;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.tiper.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import BpPrinter.mylibrary.BluetoothConnectivity;
import BpPrinter.mylibrary.BpPrinter;
import BpPrinter.mylibrary.CardReader;
import BpPrinter.mylibrary.CardScanner;
import BpPrinter.mylibrary.Scrybe;
import BpPrinter.mylibrary.UsbConnectivity;
import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.MessageWindow;
import elink.suezShimla.water.crm.R;


import elink.suezShimla.water.crm.Shantanu.ModelPackage.BankMasterModel;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;
import elink.suezShimla.water.crm.constant.AppConstant;
import io.realm.RealmResults;

public class BillFragment extends Fragment implements CardScanner, Scrybe {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Context mCon;
    LinearLayout ll_search,bottomLinearLayout,linearBankName,linearChequeInfo;
    TextView tv_lpamentdate,tv_lastpaymentamt,tv_bdate,tv_bamt,tv_duedate,tv_OLDDPC,tv_dueamt,tv_adate,tv_afteramt,tv_dpc,tv_sreading,tv_penalty,tv_fdate,tv_freading,tv_fcode,tv_scode,tv_sdate,
            tv_type,tv_size,tv_reader,tv_reading,tv_date,tv_code,tv_bu,tv_bt,tv_whtchr,tv_mtrrent,tv_arrear,
            tv_bill,txtCurrDateTime,txtCustName,txtCustAdd,txtCustTariff,txtCustStatus,txtCurrBill,txtOutAmnt,txtArrears,txtDpc
            ,txtPenalty,txtDueDate,txtBack,txtMakePayment;
    MaterialSpinner spnReceiptType,spnTransMode,spnBankName;
    TextInputEditText  edtReaderMobileNo,edt_con_no,edtConsNo,edtMobileNo,edtOutstandingAmnt,edtMicr,edtChequeNo,edtChequeDate,edtTransId;
    MaterialButton btnSearch;

    String[] receiptTypeList,transModeList;
    ArrayAdapter<String> receiptTypeAdapter,transModeAdapter;
    RealmOperations realmOperations;
    String BILL_NO;
    RealmResults<BankMasterModel> bankMasterModel;
    ArrayList<String> bankMasterModelArrayList;
    ArrayAdapter<String> bankMasterAdapter;
    String Datetime,currDate,micrCode,bankName,responseJSON,TAG="AllDetails",chequeStatus,chequeDissDate,chequeDays,CCID,machineId,cashToday,cashLimit;
    private ConnectionDetector connection;
    private Invoke invServices;
    private ProgressDialog pDialog;
    JSONArray consDetailsArray;

    LinearLayout linearReceiptLayout;
    WebView receiptWebview;
    ProgressDialog pdi;
    RadioGroup rdPrintSize;
    RadioButton rd2inch,rd3inch;
    TextView txtPrinterStatus;
    ImageView ImgSearchPrinter;
    Button btnPrint=null;
    JSONObject receiptDetailsJsonObject;


    BluetoothConnectivity BpScrybeDevice;
    UsbConnectivity m_BpUsbDevice;
    final static int CONN_TYPE_BT = 1;
    final static int CONN_TYPE_USB = 2;
    int m_conn_type = CONN_TYPE_BT;
    CardReader BpcardReader = null;
    public BpPrinter BPprinter = null;
    ArrayList<String> printerList;
    int glbPrinterWidth=32;
    private static final String[] INITIAL_PERMS = { BLUETOOTH,BLUETOOTH_ADMIN,BLUETOOTH_SCAN,BLUETOOTH_CONNECT,BLUETOOTH_ADVERTISE };
    private static final int INITIAL_REQUEST = 1337;

    public BillFragment() {
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
    public static BillFragment newInstance(String param1, String param2) {
        BillFragment fragment = new BillFragment();
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
        View view=inflater.inflate(R.layout.fragment_bill, container, false);
        ActivityCompat.requestPermissions(getActivity(), INITIAL_PERMS, INITIAL_REQUEST);  // request permission bluetooth

        mCon = getActivity();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();
        realmOperations = new RealmOperations(mCon);
        pDialog = new ProgressDialog(mCon);
        pDialog.setMessage(getString(R.string.lbl_please_wait));
        pDialog.setCancelable(false);
        init(view);
        BpScrybeDevice = new BluetoothConnectivity(this);
        m_BpUsbDevice = new UsbConnectivity(this, getActivity());
        registerForContextMenu(ImgSearchPrinter);

        view.getRootView().setFocusableInTouchMode(true);
        view.getRootView().requestFocus();
        view.getRootView().setOnKeyListener( new View.OnKeyListener(){
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {
                    return true;
                }
                return false;
            }
        } );

        return view;
    }

    private void init(View view) {
         tv_lpamentdate=view.findViewById(R.id.tv_lpamentdate);
         tv_lastpaymentamt=view.findViewById(R.id.tv_lastpaymentamt);
        ll_search=view.findViewById(R.id.ll_search);
        btnSearch=view.findViewById(R.id.btnSearch);
        edtConsNo=view.findViewById(R.id.edtConsNo);
        tv_bill=view.findViewById(R.id.tv_bill);
        txtCurrDateTime=view.findViewById(R.id.txtCurrDateTime);
        edt_con_no=view.findViewById(R.id.edt_con_no);
        edtMobileNo=view.findViewById(R.id.edtMobileNo);
        txtCustName=view.findViewById(R.id.txtCustName);
        txtCustAdd=view.findViewById(R.id.txtCustAdd);
        tv_type=view.findViewById(R.id.tv_type);
        tv_size=view.findViewById(R.id.tv_size);
        tv_reader=view.findViewById(R.id.tv_reader);
        edtReaderMobileNo=view.findViewById(R.id.edtReaderMobileNo);

        tv_bu=view.findViewById(R.id.tv_bu);
        tv_bt=view.findViewById(R.id.tv_bt);
        tv_whtchr=view.findViewById(R.id.tv_whtchr);
        tv_dpc=view.findViewById(R.id.tv_dpc);
        tv_arrear=view.findViewById(R.id.tv_arrear);
      //  tv_mtrrent=view.findViewById(R.id.tv_mtrrent);
        tv_fdate=view.findViewById(R.id.tv_fdate);
        tv_freading=view.findViewById(R.id.tv_freading);
        tv_fcode=view.findViewById(R.id.tv_fcode);
        tv_sdate=view.findViewById(R.id.tv_sdate);
        tv_sreading=view.findViewById(R.id.tv_sreading);
        tv_scode=view.findViewById(R.id.tv_scode);
        tv_bdate=view.findViewById(R.id.tv_bdate);
        tv_bamt=view.findViewById(R.id.tv_bamt);
        tv_duedate=view.findViewById(R.id.tv_duedate);
        tv_dueamt=view.findViewById(R.id.tv_dueamt);
        tv_adate=view.findViewById(R.id.tv_adate);
        tv_afteramt=view.findViewById(R.id.tv_afteramt);
        tv_OLDDPC=view.findViewById(R.id.tv_OLDDPC);
       //---------print-----------------------
        rdPrintSize=view.findViewById(R.id.rdPrintSize);
        rd2inch=view.findViewById(R.id.rd2inch);
        rd3inch=view.findViewById(R.id.rd3inch);
        txtPrinterStatus=view.findViewById(R.id.txtPrinterStatus);
        ImgSearchPrinter=view.findViewById(R.id.ImgSearchPrinter);
        btnPrint=view.findViewById(R.id.btnPrint);

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aaa");
        Date dateNtime = new Date(System.currentTimeMillis());
        Date c = Calendar.getInstance().getTime();

        currDate = new SimpleDateFormat("dd-MMM-yyyy").format(dateNtime);

        Datetime =currDate+" "+c;
        rdPrintSize.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                if (id==R.id.rd3inch) {
                    glbPrinterWidth = 48;
                    onSetPrinterType(view);
                } else {
                    glbPrinterWidth = 32;
                    onSetPrinterType(view);
                }
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // ll_search.setVisibility(View.GONE);
                if(!edtConsNo.getText().toString().equals("")){
                    getbilldetails();
                }else {
                    Toast.makeText(mCon, "Please enter Customer No", Toast.LENGTH_SHORT).show();
                }}
        });

        ImgSearchPrinter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPairedPrinters(view);
            }
        });

        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    onPrintBillBluetooth();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void onSetPrinterType(View v) {
        if (glbPrinterWidth == 32) {
            glbPrinterWidth = 32;
        } else {
            glbPrinterWidth = 48;
//            showAlert("48 Characters / Line or 3 Inch (80mm) Printer Selected!");
        }
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select Printer to connect");

        for (int i = 0; i < printerList.size(); i++) {
            menu.add(0, v.getId(), 0, printerList.get(i));
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);
        String printerName = item.getTitle().toString();
        try {
            BpScrybeDevice.connectToPrinter(printerName);
            BpcardReader = BpScrybeDevice.getCardReader(this);
            BPprinter = BpScrybeDevice.getAemPrinter();
            txtPrinterStatus.setText(getContext().getResources().getString(R.string.connected)+" with "+printerName);
            txtPrinterStatus.setTextColor(getContext().getResources().getColor(R.color.green_500));
            Toast.makeText(getContext(), "Connected with " + printerName, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            if (e.getMessage().contains("Service discovery failed")) {
                Toast.makeText(getContext(), "Not Connected\n" + printerName + " is unreachable or off otherwise it is connected with other device", Toast.LENGTH_SHORT).show();
            } else if (e.getMessage().contains("Device or resource busy")) {
                Toast.makeText(getContext(), "the device is already connected", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Unable to connect", Toast.LENGTH_SHORT).show();
            }
            txtPrinterStatus.setText(getContext().getResources().getString(R.string.not_connected));
            txtPrinterStatus.setTextColor(getContext().getResources().getColor(R.color.red));
        }
        return true;
    }

    public void onPairedPrinters(View view) {

        if (m_conn_type == CONN_TYPE_BT) {
//            String p = (String) BpScrybeDevice.pairPrinter("BTprinter0314");
            printerList = (ArrayList<String>) BpScrybeDevice.getPairedPrinters();

            if (printerList.size() > 0) {
                getActivity().openContextMenu(view);

            } else
                showAlert("No Paired Printers found");
        } else {
            if (m_BpUsbDevice != null) {
                if (m_BpUsbDevice.connectToPrinter() == true) {
                    BPprinter = m_BpUsbDevice.getUsbPrinter();
                    if (BPprinter != null) {
                        Toast.makeText(getContext(), "USB Printer Connected", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getContext(), "USB Printer Connect Fail", Toast.LENGTH_SHORT).show();

                } else
                    Toast.makeText(getContext(), "USB Printer Not Found", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(getContext(), "USB Host not Initialized", Toast.LENGTH_SHORT).show();
        }
    }


    public void onPrintBillBluetooth()  {

        if (BPprinter == null && txtPrinterStatus.getText().toString().equals("NOT CONNECTED")) {
            Toast.makeText(getContext(), "Printer not connected", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            BPprinter.Initialize_Printer();
            InputStream is = getContext().getAssets().open("mjplogo.jpg");
            Bitmap inputBitmap = BitmapFactory.decodeStream(is);
            if (glbPrinterWidth == 32) {  // for 2inch printer
                BPprinter.POS_Set_Text_alingment((byte) 0x01);   // for  center alignment
                BPprinter.printImage(inputBitmap, 10);
                BPprinter.print("Maharashtra Jeevan Pradhikaran\n");
               // BPprinter.print(receiptDetailsJsonObject.getString("BUM_BU_NAME")+"\n");
              //  BPprinter.print("Water Supply and Sanitation Department  Maharashtra \n");
              //  BPprinter.print("*"+receiptDetailsJsonObject.getString("PM_RCPTNO")+"*\n");
             //   BPprinter.print("-------------------------------\n");
             //   BPprinter.print(receiptDetailsJsonObject.getString("ACCM_ACDESC")+" ["+receiptDetailsJsonObject.getString("BILL_MONTH")+"]\n");
                BPprinter.print("-------------------------------\n");
                BPprinter.print("------"+receiptDetailsJsonObject.getString("WATER_WORKS")+"-----"+"\n");
              //  BPprinter.print("------"+Datetime+"-------"+"\n");

                BPprinter.print("   \n");
                BPprinter.print("Duplicate Bill"+" "+currDate+"\n");
                BPprinter.POS_Set_Text_alingment((byte) 0);   // for left alignment

                BPprinter.print("B. No. : "+BILL_NO+"   "+("B.Date: "+receiptDetailsJsonObject.getString("BILL_DATE")+"\n"));
               // BPprinter.print("B Date: "+receiptDetailsJsonObject.getString("PM_SERVNO")+"\n");

                BPprinter.print("Consumer No. : "+receiptDetailsJsonObject.getString("CONS_NO")+"\n");
                BPprinter.print("Mobile   No. : "+receiptDetailsJsonObject.getString("MOBILE_NO")+"\n");
                BPprinter.print("Name         : "+receiptDetailsJsonObject.getString("CON_NAME")+"\n");
                // BPprinter.print("Amount Paid(Rs) : "+receiptDetailsJsonObject.getString("PM_PAMT")+"\n");
                // BPprinter.print("["+new NumberToWords().toWords(Integer.parseInt(receiptDetailsJsonObject.getString("PM_PAMT"))) +" Rupees Only]\n");
                BPprinter.print("Type         : "+receiptDetailsJsonObject.getString("CONN_TYPE")+"   "+("Size : "+receiptDetailsJsonObject.getString("CONN_SIZE")+"\n"));
             //   BPprinter.print("Size : "+receiptDetailsJsonObject.getString("RECEIPT_DATE")+"\n");
                BPprinter.print("Reader       : "+receiptDetailsJsonObject.getString("READER_NAME")+"   "+("R. Mobile : "+receiptDetailsJsonObject.getString("READER_MOB")+"\n"));
              //  BPprinter.print("Mobile : "+receiptDetailsJsonObject.getString("RECEIPT_DATE")+"\n");
                BPprinter.print("   \n");
                BPprinter.print("----------------------------------------------\n");
                BPprinter.print("   \n");

                BPprinter.print("Date : "+"  "+"Reading  "+"Sts.Code  "+"\n");
                BPprinter.print(receiptDetailsJsonObject.getString("PAST_READING_DATE")+"  "+receiptDetailsJsonObject.getString("CURR_READING")+"  "+receiptDetailsJsonObject.getString("METER_SUB_STATUS")+"\n");
                BPprinter.print(receiptDetailsJsonObject.getString("CURR_READING_DATE")+"  "+receiptDetailsJsonObject.getString("PAST_READING")+"  "+receiptDetailsJsonObject.getString("METER_SUB_STATUS")+"\n");

                BPprinter.print("C.Unit "+"  "+"B.Unit  "+"  "+"B.Type  "+"\n");
                BPprinter.print(receiptDetailsJsonObject.getString("CON_UNIT")+"       "+receiptDetailsJsonObject.getString("BILLED_UNIT")+"       "+receiptDetailsJsonObject.getString("METER_STATUS")+"\n");
                //  BPprinter.print(receiptDetailsJsonObject.getString("PAST_READING_DATE")+receiptDetailsJsonObject.getString("BILLED_UNIT")+"messing  "+"\n");

                BPprinter.print("   \n");
                BPprinter.print("----------------------------------------------\n");
             //   BPprinter.print("Reading : "+receiptDetailsJsonObject.getString("RECEIPT_DATE")+"\n");
               // BPprinter.print("C. Unit : "+receiptDetailsJsonObject.getString("RECEIPT_DATE")+"\n");
               // BPprinter.print("B Unit : "+receiptDetailsJsonObject.getString("RECEIPT_DATE")+"\n");
               // BPprinter.print("B Type : "+receiptDetailsJsonObject.getString("RECEIPT_DATE")+"\n");
                BPprinter.print("Wtr chr : "+receiptDetailsJsonObject.getString("WATER_CHARGE")+"  " +("Arrears : "+receiptDetailsJsonObject.getString("ARREAR")+"\n"));
              //  BPprinter.print("Arrears : "+receiptDetailsJsonObject.getString("RECEIPT_DATE")+"\n");
                BPprinter.print("C.DPC  : "+receiptDetailsJsonObject.getString("SBDM_TOTAL_INTEREST")+"    "+("O.DPC : "+receiptDetailsJsonObject.getString("DPC")+"\n"));
                /*BPprinter.print("Date : "+receiptDetailsJsonObject.getString("RECEIPT_DATE")+"\n");
                BPprinter.print("Amount : "+receiptDetailsJsonObject.getString("RECEIPT_DATE")+"\n"*/
                BPprinter.print("        "+"Date     "+"Amount  "+"\n");
                BPprinter.print("Before: "+receiptDetailsJsonObject.getString("DISCOUNT_DATE")+"  "+receiptDetailsJsonObject.getString("BEFORE_DISCOUNT_AMT")+"\n");
                BPprinter.print("Due   : "+receiptDetailsJsonObject.getString("DUE_DATE")+"   "+receiptDetailsJsonObject.getString("NET_BILL_AMT")+"\n");

                BPprinter.print("After : "+receiptDetailsJsonObject.getString("AFTER_DUE_DATE")+"  "+receiptDetailsJsonObject.getString("AFTER_DUE")+"\n");
                BPprinter.print("   \n");
                BPprinter.print("LPD "+"       "+"LPA "+"\n");
                BPprinter.print(receiptDetailsJsonObject.getString("PAY_DATE")+"  "+receiptDetailsJsonObject.getString("LASTPAYMENT")+"\n");

                BPprinter.print("----------------------------------------------\n");

                BPprinter.print("Save Water Save Life"+"\n");
                BPprinter.print("----------------------------------------------\n");
                //  BPprinter.print("Paid Date : "+receiptDetailsJsonObject.getString("RECEIPT_DATE")+"\n");
                //BPprinter.print("Remark\n");
                // BPprinter.print("Cashier ID : "+receiptDetailsJsonObject.getString("CASHIER_ID")+"\n\n");
                BPprinter.print("   \n");

              /*  String[] items = receiptDetailsJsonObject.getString("ENDNOTE").split("#");
                for (String item : items){
                    BPprinter.print(item+"\n");
                }

                BPprinter.print("                          "+receiptDetailsJsonObject.getString("PM_MCNO")+""+receiptDetailsJsonObject.getString("PM_SUBMCNO")+"\n");
               */
                BPprinter.setCarriageReturn();
                BPprinter.setCarriageReturn();
                BPprinter.setCarriageReturn();
                BPprinter.setCarriageReturn();

            } else {  // for 3inch printer
                BPprinter.POS_Set_Text_alingment((byte) 0x01);   // for  center alignment
                BPprinter.printImage(inputBitmap, 15);
                BPprinter.print("Maharashtra Jeevan Pradhikaran\n");
                // BPprinter.print(receiptDetailsJsonObject.getString("BUM_BU_NAME")+"\n");
                //  BPprinter.print("Water Supply and Sanitation Department  Maharashtra \n");
                //  BPprinter.print("*"+receiptDetailsJsonObject.getString("PM_RCPTNO")+"*\n");
              //  BPprinter.print("---------------------------------------------------\n");
                //   BPprinter.print(receiptDetailsJsonObject.getString("ACCM_ACDESC")+" ["+receiptDetailsJsonObject.getString("BILL_MONTH")+"]\n");
                BPprinter.print("-------------------------------\n");
                BPprinter.print("------"+receiptDetailsJsonObject.getString("WATER_WORKS")+"-----"+"\n");
               // BPprinter.print("------"+Datetime+"-------"+"\n");
                BPprinter.print("   \n");
                BPprinter.print("Duplicate Bill"+" "+currDate+"\n");
                BPprinter.POS_Set_Text_alingment((byte) 0);   // for left alignment

                BPprinter.print("B. No. : "+BILL_NO+"   "+("B.Date: "+receiptDetailsJsonObject.getString("BILL_DATE")+"\n"));
                // BPprinter.print("B Date: "+receiptDetailsJsonObject.getString("PM_SERVNO")+"\n");

                BPprinter.print("Consumer No. : "+receiptDetailsJsonObject.getString("CONS_NO")+"\n");
                BPprinter.print("Mobile   No. : "+receiptDetailsJsonObject.getString("MOBILE_NO")+"\n");
                BPprinter.print("Name         : "+receiptDetailsJsonObject.getString("CON_NAME")+"\n");
                // BPprinter.print("Amount Paid(Rs) : "+receiptDetailsJsonObject.getString("PM_PAMT")+"\n");
                // BPprinter.print("["+new NumberToWords().toWords(Integer.parseInt(receiptDetailsJsonObject.getString("PM_PAMT"))) +" Rupees Only]\n");
                BPprinter.print("Type         : "+receiptDetailsJsonObject.getString("CONN_TYPE")+"   "+("Size : "+receiptDetailsJsonObject.getString("CONN_SIZE")+"\n"));
                //   BPprinter.print("Size : "+receiptDetailsJsonObject.getString("RECEIPT_DATE")+"\n");
                BPprinter.print("Reader       : "+receiptDetailsJsonObject.getString("READER_NAME")+"   "+("R. Mobile : "+receiptDetailsJsonObject.getString("READER_MOB")+"\n"));
                //  BPprinter.print("Mobile : "+receiptDetailsJsonObject.getString("RECEIPT_DATE")+"\n");
                BPprinter.print("   \n");
                BPprinter.print("----------------------------------------------\n");
                BPprinter.print("   \n");

                BPprinter.print("Date : "+"  "+"Reading  "+"Sts.Code  "+"\n");
                BPprinter.print(receiptDetailsJsonObject.getString("PAST_READING_DATE")+"  "+receiptDetailsJsonObject.getString("CURR_READING")+"  "+receiptDetailsJsonObject.getString("METER_SUB_STATUS")+"\n");
                BPprinter.print(receiptDetailsJsonObject.getString("CURR_READING_DATE")+"  "+receiptDetailsJsonObject.getString("PAST_READING")+"  "+receiptDetailsJsonObject.getString("METER_SUB_STATUS")+"\n");

                BPprinter.print("C.Unit "+"  "+"B.Unit  "+"  "+"B.Type  "+"\n");
                BPprinter.print(receiptDetailsJsonObject.getString("CON_UNIT")+"       "+receiptDetailsJsonObject.getString("BILLED_UNIT")+"       "+receiptDetailsJsonObject.getString("METER_STATUS")+"\n");
                //  BPprinter.print(receiptDetailsJsonObject.getString("PAST_READING_DATE")+receiptDetailsJsonObject.getString("BILLED_UNIT")+"messing  "+"\n");

                BPprinter.print("   \n");
                BPprinter.print("----------------------------------------------\n");
                //   BPprinter.print("Reading : "+receiptDetailsJsonObject.getString("RECEIPT_DATE")+"\n");
                // BPprinter.print("C. Unit : "+receiptDetailsJsonObject.getString("RECEIPT_DATE")+"\n");
                // BPprinter.print("B Unit : "+receiptDetailsJsonObject.getString("RECEIPT_DATE")+"\n");
                // BPprinter.print("B Type : "+receiptDetailsJsonObject.getString("RECEIPT_DATE")+"\n");
                BPprinter.print("Wtr chr : "+receiptDetailsJsonObject.getString("WATER_CHARGE")+"  " +("Arrears : "+receiptDetailsJsonObject.getString("ARREAR")+"\n"));
                //  BPprinter.print("Arrears : "+receiptDetailsJsonObject.getString("RECEIPT_DATE")+"\n");
                BPprinter.print("C. DPC  : "+receiptDetailsJsonObject.getString("SBDM_TOTAL_INTEREST")+"    "+("O.DPC : "+receiptDetailsJsonObject.getString("DPC")+"\n"));
                /*BPprinter.print("Date : "+receiptDetailsJsonObject.getString("RECEIPT_DATE")+"\n");
                BPprinter.print("Amount : "+receiptDetailsJsonObject.getString("RECEIPT_DATE")+"\n"*/
                BPprinter.print("        "+"Date     "+"Amount  "+"\n");
                BPprinter.print("Before: "+receiptDetailsJsonObject.getString("DISCOUNT_DATE")+"  "+receiptDetailsJsonObject.getString("BEFORE_DISCOUNT_AMT")+"\n");
                BPprinter.print("Due   : "+receiptDetailsJsonObject.getString("DUE_DATE")+"   "+receiptDetailsJsonObject.getString("NET_BILL_AMT")+"\n");

                BPprinter.print("After : "+receiptDetailsJsonObject.getString("AFTER_DUE_DATE")+"  "+receiptDetailsJsonObject.getString("AFTER_DUE")+"\n");
                BPprinter.print("   \n");
                BPprinter.print("LPD "+"       "+"LPA "+"\n");

                BPprinter.print(receiptDetailsJsonObject.getString("PAY_DATE")+"  "+receiptDetailsJsonObject.getString("LASTPAYMENT")+"\n");

                BPprinter.print("----------------------------------------------\n");

                BPprinter.print("Save Water Save Life"+"\n");
                BPprinter.print("----------------------------------------------\n");
                BPprinter.print("   \n");


                BPprinter.setCarriageReturn();
                BPprinter.setCarriageReturn();
                BPprinter.setCarriageReturn();
                BPprinter.setCarriageReturn();
            }
//            if (m_conn_type == CONN_TYPE_BT){
//                BpScrybeDevice.disConnectPrinter();
//            }
//            else{
//                m_BpUsbDevice.disConnectPrinter();
//            }
        } catch (Exception e) {
            if (e.getMessage().contains("socket closed"))
                Toast.makeText(getContext(), "Printer not connected", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getContext(), getResources().getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
        }
    }

    public void showAlert(String alertMsg) {
        AlertDialog.Builder alertBox = new AlertDialog.Builder(getContext());

        alertBox.setMessage(alertMsg).setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                return;
            }
        });

        AlertDialog alert = alertBox.create();
        alert.show();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (m_conn_type == CONN_TYPE_BT) {
                BpScrybeDevice.disConnectPrinter();
            } else {
                m_BpUsbDevice.disConnectPrinter();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void onScanMSR(String s, CardReader.CARD_TRACK card_track) {

    }

    @Override
    public void onScanDLCard(String s) {

    }

    @Override
    public void onScanRCCard(String s) {

    }

    @Override
    public void onScanRFD(String s) {

    }

    @Override
    public void onScanPacket(String s) {

    }

    @Override
    public void onScanFwUpdateRespPacket(byte[] bytes) {

    }

    @Override
    public void onDiscoveryComplete(ArrayList<String> arrayList) {

    }

    @Override
    public void onUsbConnected() {

    }




    void getbilldetails(){
        try {
            String[] Params = new String[3];
            Params[0] = edtConsNo.getText().toString();

            Params[1] = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
            Params[2] = new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon, AppConstant.SID));

            if (connection.isConnectingToInternet()) {
                Setbill task = new Setbill();
                task.execute(Params);
            } else {
                MessageWindow.errorWindow(mCon, getResources().getString(R.string.no_internet_connection));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class Setbill extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            if (connection.isConnectingToInternet()) {
                try {
                    String paraName[] = new String[3];
                    paraName[0] = "ServiceNo";
                    paraName[1] = "EmpCode";
                    paraName[2] = "SessionToken";

                    String username=null,password=null;
                    username=new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon, AppConstant.EMPCODE));
                    password=new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon,AppConstant.PASSWORD));

                    try {
                        responseJSON = invServices.getOtherData(Constants.URL, Constants.NameSpace, "MobileBillData", username,password, params, paraName);
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
                receiptDetailsJsonObject=jsonArray.getJSONObject(0);
                 BILL_NO =  receiptDetailsJsonObject.getString("BILL_NO");
                tv_bill.setText(BILL_NO);
                txtCurrDateTime.setText(receiptDetailsJsonObject.getString("BILL_DATE"));
                edt_con_no.setText(receiptDetailsJsonObject.getString("CONS_NO"));
                edtMobileNo.setText(receiptDetailsJsonObject.getString("MOBILE_NO"));
                txtCustName.setText(receiptDetailsJsonObject.getString("CON_NAME"));
                tv_type.setText(receiptDetailsJsonObject.getString("CONN_TYPE"));
                tv_size.setText(receiptDetailsJsonObject.getString("CONN_SIZE"));
                tv_reader.setText(receiptDetailsJsonObject.getString("READER_NAME"));

                edtReaderMobileNo.setText(receiptDetailsJsonObject.getString("READER_MOB"));
                tv_fdate.setText(receiptDetailsJsonObject.getString("PAST_READING_DATE"));
                tv_sdate.setText(receiptDetailsJsonObject.getString("CURR_READING_DATE"));
                tv_freading.setText(receiptDetailsJsonObject.getString("CURR_READING"));
                tv_sreading.setText(receiptDetailsJsonObject.getString("PAST_READING"));
                tv_bu.setText(receiptDetailsJsonObject.getString("BILLED_UNIT"));
                tv_bt.setText(receiptDetailsJsonObject.getString("METER_STATUS"));
                tv_whtchr.setText(receiptDetailsJsonObject.getString("WATER_CHARGE"));
                tv_dpc.setText(receiptDetailsJsonObject.getString("SBDM_TOTAL_INTEREST"));
                tv_arrear.setText(receiptDetailsJsonObject.getString("TOTAL_ARREAR"));
                tv_adate.setText(receiptDetailsJsonObject.getString("AFTER_DUE_DATE"));
                tv_bamt.setText(receiptDetailsJsonObject.getString("BEFORE_DISCOUNT_AMT"));
                tv_dueamt.setText(receiptDetailsJsonObject.getString("NET_BILL_AMT"));
                tv_afteramt.setText(receiptDetailsJsonObject.getString("AFTER_DUE"));
                tv_bdate.setText(receiptDetailsJsonObject.getString("DISCOUNT_DATE"));
                tv_duedate.setText(receiptDetailsJsonObject.getString("DUE_DATE"));
                tv_OLDDPC.setText(receiptDetailsJsonObject.getString("DPC"));
                //tv_arrear.setText(receiptDetailsJsonObject.getString("SBDM_TOTAL_INTEREST"));
                tv_lpamentdate.setText(receiptDetailsJsonObject.getString("PAY_DATE"));
                tv_lastpaymentamt.setText(receiptDetailsJsonObject.getString("LASTPAYMENT"));



                //JSONObject jsonObject=new JSONObject(responseJSON);
             //   CCID=jsonObject.getString("BILL_NO");

                //JSONArray jArray=jsonObject.getJSONArray("BILL_NO");
               // Log.d(TAG, "onPostExecute: "+jsonObject);
                hidepDialog();

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


    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}


