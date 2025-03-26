package elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Activity.MMGMainActivity;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;

public class PartialMeterInstallationSave {
    Context mCon;
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    private MaterialDialog progress;
    static String cntrctrNameStr = "", vendorcodeStr = "", cntrctrEmpStr = "", makerCodeIdStr = "",
            meterNumStr = "", meterSizeIdStr = "", sealNumStr = "", initialReadingNoStr = "",
            meterTypeIdStr = "", meterLocationIdNo = "", protectedBoxIdNoStr = "", taxNumStr = "", sourceTag = "",
            cntrctrNameIdStr = "",radioButtonVal="", otherContractorStr="", otherContractorEmpStr="",
            other_code_idStr="";

    StringBuilder materialDetailXml = new StringBuilder();
    StringBuilder cvlMeasurementXml = new StringBuilder();
    private String jsonMeterInstallSaveResponse="", submit_status="";

    public static void installationData(){
        try{

        }catch (Exception e){

        }
    }

//    public void submitNewDetails(){
//        if(radioButtonVal.equalsIgnoreCase("R")){
//            try {
//                materialDetailXml.toString().equals("");
//                cvlMeasurementXml.toString().equals("");
//            }catch (Exception ex) {
//            }
//
//        }else {
//            Log.e("materialDetailXml", materialDetailXml.toString());
//            try {
//                displayMaterialDet(list);
//                displayCvlMeasurementDet(cvllist);
//
//            }catch (Exception ex){
//                // Toast.makeText(mCon, "Something Went Wrong", Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        String params[] = new String[52];
//        params[0] = submit_status;              //IsSubmit
//        params[1] = radioButtonVal;             //Action
//        params[2] = consumerNo;                 //Consumer
//        params[3] = BU;
//        params[4] = PC;
//        params[5] = refNo;
//        params[6] = oldmeterSizeNum;
//        params[7] = oldmeterNumStr;
//        params[8] = oldmakerCodeNumId;
//        if(pastMeterNoRcvdStr == null) {
//            params[9] = "0";
//        }else{
//            params[9] = pastMeterNoRcvdStr;
//        }
//        params[10] = finalReadingNumStr;
//        params[11] = finalStatusNumStr;             //final Status
//        params[12] = meterObservationIdStr;
//        params[13] = reasonId;
//        params[14] = mtrTypeCode;
//        if(radioButtonVal.equals("OH")){
//            params[15] = "";
//            params[16] = "";                       // N_Size
//            params[17] = "";                      // N_Seal
//            params[18] = "";                     // N_Meter
//            params[19] = installDateStr;                    // InstallationDate
//            params[20] = "";                    // N_InitialReading
//            params[21] = "";                    // N_MeterType
//            params[22] = "";                    // N_MeterLocation
//            params[23] = "";                    // N_IsProtected
//            params[24] = property_assessmnt_num;                    // PropertyTaxNo
//            params[25] = "";                    // N_IsMeterHandovertoConsumer
//            params[25] = "";
//        }
//        else {
//            if(makerCodeIdStr == null) {
//                params[15] = "0";
//            }else{
//                params[15] = makerCodeIdStr;
//            }
//            params[16] = meterSizeIdStr;            // N_Size
//            params[17] = sealNumStr;                // N_Seal
//            params[18] = meterNumStr;               // N_Meter
//            params[19] = installDateStr;            // InstallationDate
//            params[20] = initialReadingNoStr;       // N_InitialReading
//            if(meterTypeIdStr == null) {
//                params[21] = "0";                   // N_MeterType
//            }else{
//                params[21] = meterTypeIdStr;
//            }
//            params[22] = meterLocationIdNo;         // N_MeterLocation
//            params[23] = protectedBoxIdNoStr;       // N_IsProtected
//            params[24] = property_assessmnt_num;                 // Property Assessment
//            if((radioButtonVal.equals("OH")) || radioButtonVal.equals("N")) {
//                params[25] = "";       // N_IsMeterHandovertoConsumer
//            } else {
//                params[25] = meterHandoverStr;
//            }
//        }
//        Log.d("cntrctrNameIdStr", ""+contractorId);
//        if(contractorId.equalsIgnoreCase("OTHER") || contractorId.equalsIgnoreCase("") ||
//                cntrctrNameIdStr.equalsIgnoreCase("OTHER")){
//            params[26] = "-99";
//        } else {
//            params[26] = contractorId;          // Contractor id in general case and (-99) in case of OTHER
//        }
//        params[27] = c_employee_id;           // ContractorEmp
//        params[28] = otherContractorStr;       // Other Code getText of Fixer code
//        params[29] = otherContractorEmpStr;       // OtherContractor emp getText of Fixer name
//        params[30] = materialHandoverStr;       // OtherContractor
//        if(radioButtonVal.equalsIgnoreCase("R")){
//            params[31] = "";                    // OtherContractorEmp
//            params[32] = "";
//        }else{
//            params[31] = materialDetailXml.toString();
//            params[32] = cvlMeasurementXml.toString();
//        }
//        params[33] = pccLengthNumStr;
//        params[34] = pccWidthNumStr;
//        params[35] = pccDepthNumStr;
//        params[36] = roadCuttingIdNumStr;
//        params[37] = rdLengthNumStr;
//        params[38] = rdWidthNumStr;
//        params[39] = rdDepthNumStr;
//
//        params[40] = fromNode;
//        params[41] = toNode;
//        params[42] = primary_mobile;
//        params[43] = alt_mobile;
//        params[44] = gis_bid_str;
//        params[45] = dma_str;
//        params[46] = sr_str;
//        params[47] = commisioned_noncommissioned_str;
//        params[48] = dial_digit;
//        params[49] = emp_login_str;
//        params[50] = ip_str;
//        params[51] = "";                    // Query String
//
//        SendDataToMeterInstallation sendDataToMeterInstallation = new SendDataToMeterInstallation();
//        sendDataToMeterInstallation.execute(params);
//        Log.i("AuthParam", ""+params);
//    }

    @SuppressLint("StaticFieldLeak")
    private class SendDataToMeterInstallation extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            progress = new MaterialDialog.Builder(mCon)
                    .content(R.string.loading)
                    .progress(true, 0)
                    .cancelable(false)
                    .canceledOnTouchOutside(false)
                    .widgetColorRes(R.color.colorPrimary)
                    .show();
        }
        @Override
        protected Void doInBackground(String... params) {
            try {
                String paraName[] = new String[52];
                paraName[0] = "IsSubmit";
                paraName[1] = "Action";
                paraName[2] = "Consumer";
                paraName[3] = "BU";
                paraName[4] = "PC";
                paraName[5] = "RefNo";
                paraName[6] = "O_Size";
                paraName[7] = "O_Meter";
                paraName[8] = "O_Make";
                paraName[9] = "O_PreviousReading";
                paraName[10] = "O_FinalReading";
                paraName[11] = "O_FinalStatus";
                paraName[12] = "O_StatusObservation";
                paraName[13] = "O_Reason";
                paraName[14] = "O_MeterType";
                paraName[15] = "N_Make";
                paraName[16] = "N_Size";
                paraName[17] = "N_Seal";
                paraName[18] = "N_Meter";
                paraName[19] = "InstallationDate";
                paraName[20] = "N_InitialReading";
                paraName[21] = "N_MeterType";
                paraName[22] = "N_MeterLocation";
                paraName[23] = "N_IsProtected";
                paraName[24] = "PropertyTaxNo";
                paraName[25] = "N_IsMeterHandovertoConsumer";
                paraName[26] = "Contractor";
                paraName[27] = "ContractorEmp";
                paraName[28] = "OtherContractor";
                paraName[29] = "OtherContractorEmp";
                paraName[30] = "N_IsMaterialHandovertoConsumer";
                paraName[31] = "XMLMaterial";
                paraName[32] = "XMLCivil";
                paraName[33] = "PCCBeddingLen";
                paraName[34] = "PCCBeddingWid";
                paraName[35] = "PCCBeddingDep";
                paraName[36] = "RoadCuttingType";
                paraName[37] = "RoadCuttingLen";
                paraName[38] = "RoadCuttingWid";
                paraName[39] = "RoadCuttingDep";
                paraName[40] = "FromNode";
                paraName[41] = "ToNode";
                paraName[42] = "RegMobile";
                paraName[43] = "AltMobile";
                paraName[44] = "GIS";
                paraName[45] = "DMA";
                paraName[46] = "SR";
                paraName[47] = "IsCommissioned";
                paraName[48] = "N_Digit";
                paraName[49] = "Emp_Code";
                paraName[50] = "IP";
                paraName[51] = "MeterInstallId";

                jsonMeterInstallSaveResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, "MeterInstallationSave", params, paraName);
                Log.e("jsonResponse", jsonMeterInstallSaveResponse);
                Log.e("params", String.valueOf(params));

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                progress.dismiss();
                String[] enums = gson.fromJson(jsonMeterInstallSaveResponse, String[].class);
                String msgString = "";
                if (enums[0].equalsIgnoreCase("Success")) {
                    if(submit_status.equalsIgnoreCase("N")) {
                        msgString = "Meter Installation Record Saved Successfully";
                    }else if(submit_status.equalsIgnoreCase("Y")) {
                        msgString = "Meter Installation Record Submitted Successfully";
                    }
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mCon);
                    alertBuilder.setCancelable(false);
                    alertBuilder.setTitle("Success");
                    alertBuilder.setMessage(msgString);
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            UtilitySharedPreferences.clearPrefKey(mCon, Constants.CONTLIST);
                            Intent intent = new Intent(mCon, MMGMainActivity.class);
//                            startActivity(intent);
//                            dialog.cancel();
//                            getActivity().finish();
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();

                }else if(enums[0].equalsIgnoreCase("Failure")){
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mCon);
                    alertBuilder.setCancelable(false);
                    alertBuilder.setTitle("Failure");
                    alertBuilder.setMessage("Meter Installation Process Not Completed");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else if(enums[0].equalsIgnoreCase("Duplicate")){
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mCon);
                    alertBuilder.setCancelable(false);
                    alertBuilder.setTitle("Alert");
                    alertBuilder.setMessage("Meter Related Complaint already pending, \nPlease forward complaint to MMG for further process.");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(mCon, MMGMainActivity.class);
                           // startActivity(intent);
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                }

                UtilitySharedPreferences.clearPrefKey(mCon, Constants.VALIDMETER);
                UtilitySharedPreferences.clearPrefKey(mCon, Constants.CONTACTORNAME);
                UtilitySharedPreferences.clearPrefKey(mCon, Constants.CONTACTOREMP);
                UtilitySharedPreferences.clearPrefKey(mCon, Constants.CONTLIST);
                UtilitySharedPreferences.clearPrefKey(mCon, Constants.NEW_METER_NO);
                UtilitySharedPreferences.clearPrefKey(mCon, Constants.NEW_METERNUM);
                UtilitySharedPreferences.clearPrefKey(mCon, Constants.NEW_INSTALLDATE);
                UtilitySharedPreferences.clearPrefKey(mCon, Constants.NEW_METERSIZE);
                UtilitySharedPreferences.clearPrefKey(mCon, Constants.NEW_SEALNO);
                UtilitySharedPreferences.clearPrefKey(mCon, Constants.NEW_INITIALREADING);
                UtilitySharedPreferences.clearPrefKey(mCon, Constants.NEW_METERTYPE);
                UtilitySharedPreferences.clearPrefKey(mCon, Constants.NEW_METERLOCATION);
                UtilitySharedPreferences.clearPrefKey(mCon, Constants.NEW_PROTECTEDBOX);
                UtilitySharedPreferences.clearPrefKey(mCon, Constants.NEW_TAXNO);

            } catch (Exception e) {
                Log.e("Exception", e.toString());
                progress.dismiss();
                Toast.makeText(mCon, "Something went wrong", Toast.LENGTH_SHORT).show();
                String error = e.toString();
                ErrorClass.errorData(mCon, "Authentication Fragment", "Send Data to meter Installation", error);
            }
        }
    }
}
