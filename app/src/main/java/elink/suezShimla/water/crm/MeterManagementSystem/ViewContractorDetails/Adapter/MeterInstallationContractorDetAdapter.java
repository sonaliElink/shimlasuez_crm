package elink.suezShimla.water.crm.MeterManagementSystem.ViewContractorDetails.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Activity.MMGMainActivity;
import elink.suezShimla.water.crm.MeterManagementSystem.ViewContractorDetails.Model.MMGGetAllMtrInstallByContModel;
import elink.suezShimla.water.crm.MeterManagementSystem.ViewContractorDetails.Model.MMGGetMeterInstallByIdSingleContractor;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.asyncClass.Invoke;

public class MeterInstallationContractorDetAdapter extends RecyclerView.Adapter<MeterInstallationContractorDetAdapter.MyViewHolder> {
    private Context mCon;
    private List<MMGGetAllMtrInstallByContModel> data;
    String action = "", ref_no = "", is_commisioned = "", jsonResponse = "", meterInstallId = "", pagename = "", consumerNo = "";
    private MaterialDialog progress;
    private Invoke invServices;
    private Gson gson;
    private LayoutInflater inflater;
    private List<MMGGetAllMtrInstallByContModel> mmgGetAllMtrInstallByContModelArrayList = new ArrayList<>();
    MMGGetMeterInstallByIdSingleContractor contModels;
    private ArrayList<MMGGetMeterInstallByIdSingleContractor> contList = new ArrayList<>();
    int position;

    public MeterInstallationContractorDetAdapter(Context context) {
        this.mCon = context;
        data = new ArrayList<>();
        inflater = LayoutInflater.from(mCon);
    }

    public void addList(List<MMGGetAllMtrInstallByContModel> list) {
        if (data != null) {
            clear();

            data.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        if (data != null) {
            data.clear();
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.activity_meter_installation_contractor_details_row, viewGroup, false);

        gson = new Gson();
        invServices = new Invoke();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,  int i) {
         MMGGetAllMtrInstallByContModel current = data.get(i);
        String count = String.valueOf(i+1);
        if(current!=null) {
            holder.srNoTextView.setText(count);

            holder.tv_consumer_no.setText(current.getMI_CONSUMER());
            holder.tv_final_status.setText(current.getO_FINALSTATUS());
            holder.tv_consumer_name.setText(current.getCONSUMERNAME()+" "+current.getNAME_ADDRESS());
            holder.requestNoTextView.setText(current.getREFNO());
            holder.contactNoTextView.setText(current.getCONTACTNO());
          //  holder.tv_date_time.setText(current.getMI_INSTALLATIONDATE());
            holder.tv_date_time.setText(current.getLAST_SAVE_DATE());
            meterInstallId = current.getMI_METERINSTALLID();
//        meterInstallId = meterInstallId.substring(0, meterInstallId.indexOf('.'));

            holder.contractor_details_cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String params[] = new String[1];
                    params[0] = current.getMI_METERINSTALLID();

                    GetMeterInstallById getMeterInstallById = new GetMeterInstallById();
                    getMeterInstallById.execute(params);
                }
            });

        }


//        holder.contractor_details_cardview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mCon, MMGMainActivity.class);
//                intent.putExtra("pagename", "MeterInstallationContractorDet");
//                intent.putExtra("positionId",i);
//                intent.putExtra("consumerNo", current.getMI_CONSUMER());
//
//                intent.putExtra("action", action);
//                intent.putExtra("ref_no", ref_no);
//                intent.putExtra("is_commisioned", is_commisioned);
////                intent.putExtra("pagename", "MeterInstallationContractorDet");
////                intent.putExtra("pagename", "MeterInstallationContractorDet");
////                intent.putExtra("pagename", "MeterInstallationContractorDet");
////                intent.putExtra("pagename", "MeterInstallationContractorDet");
////                intent.putExtra("pagename", "MeterInstallationContractorDet");
////                intent.putExtra("pagename", "MeterInstallationContractorDet");
////                intent.putExtra("pagename", "MeterInstallationContractorDet");
////                intent.putExtra("pagename", "MeterInstallationContractorDet");
////                intent.putExtra("pagename", "MeterInstallationContractorDet");
////                intent.putExtra("pagename", "MeterInstallationContractorDet");
////                intent.putExtra("pagename", "MeterInstallationContractorDet");
////                intent.putExtra("pagename", "MeterInstallationContractorDet");
////                intent.putExtra("pagename", "MeterInstallationContractorDet");
////                intent.putExtra("pagename", "MeterInstallationContractorDet");
//
//                mCon.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_consumer_no, tv_final_status, tv_consumer_name, requestNoTextView, contactNoTextView,tv_date_time,srNoTextView;
        CardView contractor_details_cardview;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            contractor_details_cardview = itemView.findViewById(R.id.contractor_details_cardview);

            tv_consumer_no = itemView.findViewById(R.id.tv_consumer_no);
            tv_final_status = itemView.findViewById(R.id.tv_final_status);
            tv_consumer_name = itemView.findViewById(R.id.tv_consumer_name);
            requestNoTextView = itemView.findViewById(R.id.requestNoTextView);
            contactNoTextView = itemView.findViewById(R.id.contactNoTextView);
            tv_date_time = itemView.findViewById(R.id.tv_date_time);
            srNoTextView = itemView.findViewById(R.id.srNoTextView);

        }

    }

    @SuppressLint("StaticFieldLeak")
    class GetMeterInstallById extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                String paraName[] = new String[1];
                paraName[0] = "MtrInstallId";

                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.GetMeterInstallById, params, paraName);

            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                progress.dismiss();
                contList.clear();
                MMGGetMeterInstallByIdSingleContractor[] enums = gson.fromJson(jsonResponse, MMGGetMeterInstallByIdSingleContractor[].class);
                if (!enums[0].getMI_METERINSTALLID().equalsIgnoreCase("NULL")) {
                    for (MMGGetMeterInstallByIdSingleContractor model : enums) {
                        contModels = new MMGGetMeterInstallByIdSingleContractor(model.getMI_METERINSTALLID(),
                                model.getMI_ACTION(),
                                model.getMI_CONSUMER(),
                                model.getMI_BU(),
                                model.getMI_PC(),
                                model.getMI_REFNO(),
                                model.getMI_O_SIZE(),
                                model.getMI_O_METER(),
                                model.getMI_O_MAKE(),
                                model.getMI_O_PREVIOUSREADING(),
                                model.getMI_O_FINALREADING(),
                                model.getMI_O_FINALSTATUS(),
                                model.getMI_O_REASON(),
                                model.getMI_O_METERTYPE(),
                                model.getMI_N_MAKE(),
                                model.getMI_N_SIZE(),
                                model.getMI_N_SEAL(),
                                model.getMI_N_METER(),
                                model.getMI_INSTALLATIONDATE(),
                                model.getMI_N_INITIALREADING(),
                                model.getMI_N_METERTYPE(),
                                model.getMI_N_METERLOCATION(),
                                model.getMI_N_ISPROTECTED(),
                                model.getMI_PROPERTYTAXNO(),
                                model.getMI_N_ISMETERHANDOVER(),
                                model.getMI_CONTRACTOR(),
                                model.getMI_CONTRACTOREMP(),
                                model.getMI_N_ISMATERIALHANDOVER(),
                                model.getMI_PCCBEDDINGLEN(),
                                model.getMI_PCCBEDDINGWID(),
                                model.getMI_PCCBEDDINGDEP(),
                                model.getMI_ROADCUTTINGTYPE(),
                                model.getMI_ROADCUTTINGLEN(),
                                model.getMI_ROADCUTTINGWID(),
                                model.getMI_ROADCUTTINGDEP(),
                                model.getMI_FROMNODE(),
                                model.getMI_TONODE(),
                                model.getMI_REGMOBILE(),
                                model.getMI_ALTMOBILE(),
                                model.getMI_GIS(),
                                model.getMI_DMA(),
                                model.getMI_SR(),
                                model.getMI_MODIFIEDBY(),
                                model.getMI_MODIFIEDDATE(),
                                model.getMI_IP(),
                                model.getMI_AUTHENTICATEDATE(),
                                model.getMI_AUTHREJECTBY(),
                                model.getMI_REJECTEDDATE(),
                                model.getMI_STATUS(),
                                model.getMI_ISACTIVE(),
                                model.getMI_XMLMATERIAL(),
                                model.getMI_XMLCIVIL(),
                                model.getMI_O_OBSERVATION(),
                                model.getMI_SOURCE(),
                                model.getMI_ISCOMMISSIONED(),
                                model.getMI_CONTRACTOROTHER(),
                                model.getMI_CONTRACTOREMPOTHER(),
                                model.getMI_N_DIGIT(),
                                model.getMSRID());
                        contList.add(contModels);

                        action = contModels.getMI_ACTION();
                        consumerNo = contModels.getMI_CONSUMER();
                        ref_no = contModels.getMI_REFNO();
                        is_commisioned = contModels.getMI_ISCOMMISSIONED();

                        pagename = "MeterInstallationContractorDet";

                        JSONArray jArray = new JSONArray();

                        for (MMGGetMeterInstallByIdSingleContractor installByContModel : contList) {
                            JSONObject jsonObj = new JSONObject();
                            try {
                                jsonObj.put("MI_METERINSTALLID", installByContModel.getMI_METERINSTALLID());
                                jsonObj.put("MI_ACTION", installByContModel.getMI_ACTION());
                                jsonObj.put("MI_CONSUMER", installByContModel.getMI_CONSUMER());
                                jsonObj.put("MI_BU", installByContModel.getMI_BU());
                                jsonObj.put("MI_PC", installByContModel.getMI_PC());
                                jsonObj.put("MI_REFNO", installByContModel.getMI_REFNO());
                                jsonObj.put("MI_O_SIZE", installByContModel.getMI_O_SIZE());
                                jsonObj.put("MI_O_METER", installByContModel.getMI_O_METER());
                                jsonObj.put("MI_O_MAKE", installByContModel.getMI_O_MAKE());
                                jsonObj.put("MI_O_PREVIOUSREADING", installByContModel.getMI_O_PREVIOUSREADING());
                                jsonObj.put("MI_O_FINALREADING", installByContModel.getMI_O_FINALREADING());
                                jsonObj.put("MI_O_FINALSTATUS", installByContModel.getMI_O_FINALSTATUS());
                                jsonObj.put("MI_O_REASON", installByContModel.getMI_O_REASON());
                                jsonObj.put("MI_O_METERTYPE", installByContModel.getMI_O_METERTYPE());
                                jsonObj.put("MI_N_MAKE", installByContModel.getMI_N_MAKE());
                                jsonObj.put("MI_N_SIZE", installByContModel.getMI_N_SIZE());
                                jsonObj.put("MI_N_SEAL", installByContModel.getMI_N_SEAL());
                                jsonObj.put("MI_N_METER", installByContModel.getMI_N_METER());
                                jsonObj.put("MI_INSTALLATIONDATE", installByContModel.getMI_INSTALLATIONDATE());
                                jsonObj.put("MI_N_INITIALREADING", installByContModel.getMI_N_INITIALREADING());
                                jsonObj.put("MI_N_METERTYPE", installByContModel.getMI_N_METERTYPE());
                                jsonObj.put("MI_N_METERLOCATION", installByContModel.getMI_N_METERLOCATION());
                                jsonObj.put("MI_N_ISPROTECTED", installByContModel.getMI_N_ISPROTECTED());
                                jsonObj.put("MI_PROPERTYTAXNO", installByContModel.getMI_PROPERTYTAXNO());
                                jsonObj.put("MI_N_ISMETERHANDOVER", installByContModel.getMI_N_ISMETERHANDOVER());
                                jsonObj.put("MI_CONTRACTOR", installByContModel.getMI_CONTRACTOR());
                                jsonObj.put("MI_CONTRACTOREMP", installByContModel.getMI_CONTRACTOREMP());
                                jsonObj.put("MI_N_ISMATERIALHANDOVER", installByContModel.getMI_N_ISMATERIALHANDOVER());
                                jsonObj.put("MI_PCCBEDDINGLEN", installByContModel.getMI_PCCBEDDINGLEN());
                                jsonObj.put("MI_PCCBEDDINGWID", installByContModel.getMI_PCCBEDDINGWID());
                                jsonObj.put("MI_PCCBEDDINGDEP", installByContModel.getMI_PCCBEDDINGDEP());
                                jsonObj.put("MI_ROADCUTTINGTYPE", installByContModel.getMI_ROADCUTTINGTYPE());
                                jsonObj.put("MI_ROADCUTTINGLEN", installByContModel.getMI_ROADCUTTINGLEN());
                                jsonObj.put("MI_ROADCUTTINGWID", installByContModel.getMI_ROADCUTTINGWID());
                                jsonObj.put("MI_ROADCUTTINGDEP", installByContModel.getMI_ROADCUTTINGDEP());
                                jsonObj.put("MI_FROMNODE", installByContModel.getMI_FROMNODE());
                                jsonObj.put("MI_TONODE", installByContModel.getMI_TONODE());
                                jsonObj.put("MI_REGMOBILE", installByContModel.getMI_REGMOBILE());
                                jsonObj.put("MI_ALTMOBILE", installByContModel.getMI_ALTMOBILE());
                                jsonObj.put("MI_GIS", installByContModel.getMI_GIS());
                                jsonObj.put("MI_DMA", installByContModel.getMI_DMA());
                                jsonObj.put("MI_SR", installByContModel.getMI_SR());
                                jsonObj.put("MI_MODIFIEDBY", installByContModel.getMI_MODIFIEDBY());
                                jsonObj.put("MI_MODIFIEDDATE", installByContModel.getMI_MODIFIEDDATE());
                                jsonObj.put("MI_IP", installByContModel.getMI_IP());
                                jsonObj.put("MI_AUTHENTICATEDATE", installByContModel.getMI_AUTHENTICATEDATE());
                                jsonObj.put("MI_AUTHREJECTBY", installByContModel.getMI_AUTHREJECTBY());
                                jsonObj.put("MI_REJECTEDDATE", installByContModel.getMI_REJECTEDDATE());
                                jsonObj.put("MI_STATUS", installByContModel.getMI_STATUS());
                                jsonObj.put("MI_ISACTIVE", installByContModel.getMI_ISACTIVE());
                                jsonObj.put("MI_XMLMATERIAL", installByContModel.getMI_XMLMATERIAL());
                                jsonObj.put("MI_XMLCIVIL", installByContModel.getMI_XMLCIVIL());
                                jsonObj.put("MI_O_OBSERVATION", installByContModel.getMI_O_OBSERVATION());
                                jsonObj.put("MI_SOURCE", installByContModel.getMI_SOURCE());
                                jsonObj.put("MI_ISCOMMISSIONED", installByContModel.getMI_ISCOMMISSIONED());
                                jsonObj.put("MI_CONTRACTOROTHER", installByContModel.getMI_CONTRACTOROTHER());
                                jsonObj.put("MI_CONTRACTOREMPOTHER", installByContModel.getMI_CONTRACTOREMPOTHER());
                                jsonObj.put("MI_N_DIGIT", installByContModel.getMI_N_DIGIT());
                                jsonObj.put("MSRID", installByContModel.getMSRID());
                                jArray.put(jsonObj);

                                Log.d("check", String.valueOf(jArray));

                                Intent intent = new Intent(mCon, MMGMainActivity.class);
                                intent.putExtra("pagename", pagename);
                                intent.putExtra("consumerNo", consumerNo);
                                intent.putExtra("action", action);
                                intent.putExtra("ref_no", ref_no);
                                intent.putExtra("is_commisioned", is_commisioned);
                                intent.putExtra("contList", String.valueOf(jArray));
                                mCon.startActivity(intent);

                            } catch (Exception e){progress.dismiss();}
                        }
                    }
                }
            } catch (Exception e) {
                Log.d("Exception ", " " + e.getMessage());
                progress.dismiss();
                String error = e.toString();
                ErrorClass.errorData(mCon, "MeterInstallation Contractor Det Adapter", "Get Meter Install By Id task", error);
            }
        }

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
    }

}


