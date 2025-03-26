package elink.suezShimla.water.crm.Shantanu.Collection;

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
import android.os.Build;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import BpPrinter.mylibrary.BluetoothConnectivity;
import BpPrinter.mylibrary.BpPrinter;
import BpPrinter.mylibrary.CardReader;
import BpPrinter.mylibrary.CardScanner;
import BpPrinter.mylibrary.Scrybe;
import BpPrinter.mylibrary.UsbConnectivity;
import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.MessageWindow;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Shantanu.Common.NumberToWords;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;
import elink.suezShimla.water.crm.constant.AppConstant;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReceiptWebviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
//@RequiresApi(api = Build.VERSION_CODES.S)
public class ReceiptWebviewFragment extends Fragment implements CardScanner, Scrybe {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    LinearLayout linearReceiptLayout;
    WebView receiptWebview;
    Context mCon;
    ProgressDialog pdi;
    RadioGroup rdPrintSize;
    RadioButton rd2inch,rd3inch;
    TextView txtPrinterStatus;
    ImageView ImgSearchPrinter;
    Button btnPrint=null;
    String TAG="Receipt",urlParams,consNo,receiptNo,dupReceipt;
    JSONObject receiptDetailsJsonObject;

    private ConnectionDetector connection;
    private Invoke invServices;
    private ProgressDialog pDialog;
    String responseJSON;

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

    public ReceiptWebviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReceiptWebviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReceiptWebviewFragment newInstance(String param1, String param2) {
        ReceiptWebviewFragment fragment = new ReceiptWebviewFragment();
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
        View view=inflater.inflate(R.layout.fragment_receipt_webview, container, false);
        ActivityCompat.requestPermissions(getActivity(), INITIAL_PERMS, INITIAL_REQUEST);  // request permission bluetooth
        mCon=getContext();
        linearReceiptLayout=view.findViewById(R.id.linearReceiptLayout);
        rd2inch=view.findViewById(R.id.rd2inch);
        rd3inch=view.findViewById(R.id.rd3inch);
        rdPrintSize = view.findViewById(R.id.rdPrintSize);
        txtPrinterStatus=view.findViewById(R.id.txtPrinterStatus);
        ImgSearchPrinter=view.findViewById(R.id.ImgSearchPrinter);
        btnPrint=view.findViewById(R.id.btnPrint);
        receiptWebview=view.findViewById(R.id.receiptWebview);

        linearReceiptLayout.setVisibility(View.GONE);

        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();
        pDialog = new ProgressDialog(mCon);
        pDialog.setMessage(getString(R.string.lbl_please_wait));
        pDialog.setCancelable(false);

        BpScrybeDevice = new BluetoothConnectivity(this);
        m_BpUsbDevice = new UsbConnectivity(this, getActivity());
        registerForContextMenu(ImgSearchPrinter);

        if(getArguments()!=null) {

            receiptWebview.setWebViewClient(new WebViewClient());

            pdi= new ProgressDialog(mCon);
            pdi.setTitle("Loading Receipt");
            pdi.setMessage("Please Wait..");
            pdi.setCancelable(false);
            pdi.show();

            receiptWebview.getSettings().setJavaScriptEnabled(true);

            // Set a WebViewClient to handle page navigation within the WebView
            receiptWebview.setWebViewClient(new WebViewClient());
            // Load a URL
            urlParams=getArguments().getString("Param");
            consNo=(urlParams.split(",")[1]).split(",")[0];
            receiptNo=urlParams.split(",")[0];
            if((urlParams.split(",")[2]).split(",")[0].equals("1"))
                dupReceipt="";
            else
                dupReceipt="Duplicate Receipt";
            Log.d(TAG, "onCreateView: "+urlParams);
            String aspxUrl = Constants.ReceiptUrl + AesAlgorithm.encryptUrl(urlParams, true, "Elintpower");
            receiptWebview.loadUrl(aspxUrl);
            receiptWebview.setWebViewClient(new WebViewClient() {
                public void onPageFinished(WebView view, String url) {
                    pdi.dismiss();
                    getReceipts();
                        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                             createWebPagePrint(receiptWebview);  // redirect to web n print directly
                        } */
                }
            });
        }else{
            MessageWindow.errorWindow(mCon,"Invalid url");
        }

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
                BPprinter.printImage(inputBitmap, 0);
                BPprinter.print("Maharashtra Jeevan Pradhikaran\n");
                BPprinter.print(receiptDetailsJsonObject.getString("BUM_BU_NAME")+"\n");
                BPprinter.print("Payment Receipt\n");
                BPprinter.print("*"+receiptDetailsJsonObject.getString("PM_RCPTNO")+"*\n");
                BPprinter.print("-------------------------------\n");
                BPprinter.print(receiptDetailsJsonObject.getString("ACCM_ACDESC")+" ["+receiptDetailsJsonObject.getString("BILL_MONTH")+"]\n");
                BPprinter.print("   \n");
                BPprinter.POS_Set_Text_alingment((byte) 0);   // for left alignment
                BPprinter.print("Consumer No. : "+receiptDetailsJsonObject.getString("PM_SERVNO")+"\n");
                BPprinter.print("Name : "+receiptDetailsJsonObject.getString("PM_CONSUMERNAME")+"\n");
                BPprinter.print("Amount Paid(Rs) : "+receiptDetailsJsonObject.getString("PM_PAMT")+"\n");
                BPprinter.print("["+new NumberToWords().toWords(Integer.parseInt(receiptDetailsJsonObject.getString("PM_PAMT"))) +" Rupees Only]\n");
                BPprinter.print("Payment Mode : "+receiptDetailsJsonObject.getString("TYPE")+"\n");
                BPprinter.print("Paid Date : "+receiptDetailsJsonObject.getString("RECEIPT_DATE")+"\n");
                BPprinter.print("Remark\n");
                BPprinter.print("Cashier ID : "+receiptDetailsJsonObject.getString("CASHIER_ID")+"\n\n");
                BPprinter.print("   \n");

                String[] items = receiptDetailsJsonObject.getString("ENDNOTE").split("#");
                for (String item : items){
                    BPprinter.print(item+"\n");
                }
                BPprinter.print("   \n");

                BPprinter.print("                          "+receiptDetailsJsonObject.getString("PM_MCNO")+""+receiptDetailsJsonObject.getString("PM_SUBMCNO")+"\n");
                BPprinter.setCarriageReturn();
                BPprinter.setCarriageReturn();
                BPprinter.setCarriageReturn();
                BPprinter.setCarriageReturn();

            } else {  // for 3inch printer
                BPprinter.POS_Set_Text_alingment((byte) 0x01);   // for  center alignment
                BPprinter.printImage(inputBitmap, 1);
                BPprinter.print("Maharashtra Jeevan Pradhikaran\n");
                BPprinter.print(receiptDetailsJsonObject.getString("BUM_BU_NAME")+"\n");
                BPprinter.print("Payment Receipt\n");
                BPprinter.print("*"+receiptDetailsJsonObject.getString("PM_RCPTNO")+"*\n");
                BPprinter.print("-------------------------------\n");
                BPprinter.print(receiptDetailsJsonObject.getString("ACCM_ACDESC")+" ["+receiptDetailsJsonObject.getString("BILL_MONTH")+"]\n");
                BPprinter.print("   \n");
                BPprinter.POS_Set_Text_alingment((byte) 0);   // for left alignment
                BPprinter.print("Consumer No. : "+receiptDetailsJsonObject.getString("PM_SERVNO")+"\n");
                BPprinter.print("Name : "+receiptDetailsJsonObject.getString("PM_CONSUMERNAME")+"\n");
                BPprinter.print("Amount Paid(Rs) : "+receiptDetailsJsonObject.getString("PM_PAMT")+"\n");
                BPprinter.print("["+new NumberToWords().toWords(Integer.parseInt(receiptDetailsJsonObject.getString("PM_PAMT"))) +" Rupees Only]\n");
                BPprinter.print("Payment Mode : "+receiptDetailsJsonObject.getString("TYPE")+"\n");
                BPprinter.print("Paid Date : "+receiptDetailsJsonObject.getString("RECEIPT_DATE")+"\n");
                BPprinter.print("Remark\n");
                BPprinter.print("Cashier ID : "+receiptDetailsJsonObject.getString("CASHIER_ID")+"\n");
                BPprinter.print("   \n");

                String[] items = receiptDetailsJsonObject.getString("ENDNOTE").split("#");
                for (String item : items){
                    BPprinter.print(item+"\n");
                }
                BPprinter.print(" \n");

                BPprinter.print("                          "+receiptDetailsJsonObject.getString("PM_MCNO")+""+receiptDetailsJsonObject.getString("PM_SUBMCNO")+"\n");
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

    public  void createWebPagePrint(WebView webView) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            return;

        PrintManager printManager = (PrintManager) mCon.getSystemService(Context.PRINT_SERVICE);
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter();
        String jobName = "Print Receipt";
        PrintAttributes.Builder builder = new PrintAttributes.Builder();
        builder.setMediaSize(PrintAttributes.MediaSize.ISO_A5);
        PrintJob printJob = printManager.print(jobName, printAdapter, builder.build());

        if (printJob.isCompleted()) {
            Toast.makeText(mCon.getApplicationContext(), "Print Completed", Toast.LENGTH_LONG).show();
        } else if (printJob.isFailed()) {
            Toast.makeText(mCon.getApplicationContext(), "Print Failed", Toast.LENGTH_LONG).show();
        }
        // Save the job object for later status checking
    }   //redirect to web n print directly

    private String fetchHtmlContent(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            InputStream inputStream = connection.getInputStream();
            StringBuilder htmlContent = new StringBuilder();
            int c;
            while ((c = inputStream.read()) != -1) {
                htmlContent.append((char) c);
            }
            inputStream.close();

            return htmlContent.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
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

    void getReceipts(){
        try {
            String[] Params = new String[6];

            Params[0] = consNo;
            Params[1] = receiptNo;
            Params[2] = PreferenceUtil.getZone();
            Params[3] = "A";
            Params[4] = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
            Params[5] = new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon, AppConstant.SID));

            if (connection.isConnectingToInternet()) {
                GetReceipt task = new GetReceipt();
                task.execute(Params);
            } else {
                MessageWindow.errorWindow(mCon, getResources().getString(R.string.no_internet_connection));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class GetReceipt extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            if (connection.isConnectingToInternet()) {
                try {
                    String paraName[] = new String[6];
                    paraName[0] = "ConsNo";
                    paraName[1] = "ReceiptNo";
                    paraName[2] = "DFID";
                    paraName[3] = "Tag";
                    paraName[4] = "LoginUser";
                    paraName[5] = "SessionToken";

                    String username=null,password=null;
                    username=new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon, AppConstant.EMPCODE));
                    password=new AesAlgorithm().decrypt(UtilitySharedPreferences.getPrefs(mCon,AppConstant.PASSWORD));

                    try {
                        responseJSON = invServices.getOtherData(Constants.URL, Constants.NameSpace, "GetReceipt", username,password, params, paraName);
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
                JSONArray jsonArray=new JSONArray(responseJSON);
                Log.d(TAG, "onPostExecute: "+jsonArray);
                hidepDialog();
                if (jsonArray.getJSONObject(0).getString("QueryStatus").equals("Success")) {
                    receiptDetailsJsonObject=jsonArray.getJSONObject(0);
                    linearReceiptLayout.setVisibility(View.VISIBLE);
                }else{
                    MessageWindow.errorWindow(mCon, mCon.getResources().getString(R.string.something_went_wrong));
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

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
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

    /* private class DownloadHtmlAndConvertToPdfTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            String aspxUrl = "https://mjpwss.elinkjal.com/consumer/frm_Receiptprint1.aspx?ReqNo="+ AesAlgorithm.encryptUrl("102210320044211,0201062359,1,A,0,11",true,"Elintpower");
            String htmlContent = fetchHtmlContent(aspxUrl);
            if (htmlContent != null) {
                if(checkPdfPhotoPermission()) {
                    receiptWebview.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null);
//                generatePdf("sad");
                }else
                    requestPdfPhotoPermission();
            }
            return null;
        }
    }


    private void generatePdf(String fileName) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            // Create a PDF document
            PdfDocument document = new PdfDocument();
            // Create a PageInfo object
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
            // Start a new page
            PdfDocument.Page page = document.startPage(pageInfo);
            // Create a canvas to draw on the page
            receiptWebview.draw(page.getCanvas());
            // Finish the page
            document.finishPage(page);

            try {


                File[] fpath = mCon.getExternalFilesDirs(null);
                String folderPath=fpath[0].toString() + "/MJPCRM/Receipts/";
                File dir = new File(folderPath);
                if(!dir.exists()){
                    dir.mkdirs();
                }
                String finalFilePath = folderPath+ fileName+".pdf";

                File outputFile = new File(finalFilePath);
                FileOutputStream outputStream = new FileOutputStream(outputFile);
                document.writeTo(outputStream);
                document.close();
                outputStream.close();
                // open final pdf file
                openFile(outputFile);
            } catch (IOException e) {
                e.printStackTrace();
                showToast(mCon.getResources().getString(R.string.something_went_wrong));
            }
        }
    }

    private void openFile(File pdfFile){
        pdi.dismiss();
        if (pdfFile.exists()) {
            try {
                Intent intentShareFile = new Intent(Intent.ACTION_VIEW);
                Uri apkURI = FileProvider.getUriForFile(mCon.getApplicationContext(),
                        mCon.getApplicationContext().getPackageName() + ".provider", pdfFile);
                intentShareFile.setDataAndType(apkURI, URLConnection.guessContentTypeFromName(pdfFile.getName()));
                intentShareFile.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intentShareFile.putExtra(Intent.EXTRA_STREAM, apkURI);

                // Start the activity to open the PDF file
                showToast("Receipt downloaded successfull");
                mCon.startActivity(Intent.createChooser(intentShareFile, "View Receipt"));
            } catch (ActivityNotFoundException e) {
                // In case no PDF viewer is installed on the device.
                showToast("No PDF viewer found.");
            }
        }else {
            // PDF file doesn't exist
            showToast("Receipt PDF not found");
        }
    }

    private void showToast(String msg){
        ((Activity)mCon).runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(mCon.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean checkPdfPhotoPermission() {
        return ActivityCompat.checkSelfPermission(mCon, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ;
    }

    private void requestPdfPhotoPermission() {
        ActivityCompat.requestPermissions( (Activity) mCon,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE },1);
    } */

}