package elink.suezShimla.water.crm.ConnectionManagement.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import elink.suezShimla.water.crm.ActiveToPlugged.ActiveToPluggedActivity;
import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.ConnectionManagement.model.SiteVisitModel;
import elink.suezShimla.water.crm.DisconnectionMeter.ActivityDisconnectMeter;
import elink.suezShimla.water.crm.PluggedToActive.PluggedToActivity;
import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.MainActivity;
import elink.suezShimla.water.crm.MessageWindow;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Utils.PreferenceUtil;
import elink.suezShimla.water.crm.androidhsc.hscModelTable.RejectModel;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;
import elink.suezShimla.water.crm.map.SingleMapLocationActivity;

public class SiteVisitListAdapter extends RecyclerView.Adapter<SiteVisitListAdapter.MyViewHolder> {

    private Context mCon;
    private SiteVisitModel model2;
    private List<SiteVisitModel> siteVisitModelArrayList;
    String applicationStatusStr="", requestNoStr = "", applicantNameStr = "", amApplicationDateStr = "", addressStr = "", phoneNumberStr = "", statusStr = "", statusDateStr = "",
            zonStr = "", wardStr = "", appointmentDateStr = "", governmentTypeStr = "", connectionTypeStr = "", categoryNameStr = "", connectionSizeStr = "";

    RowClick rowClick;
    String reasonTypeName = "", rejectTypeName = "", remarkTypeName = "", reasonId = "", reasonName = "", jsonRejectHoldResponse = "", processCode = "", empCode = "", ipAddress = "", apptype = "";
    ArrayAdapter reasonTypeAdapter, actionadapter;
    private RealmOperations realmOperations;
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    private Integer positionNew;


    private RejectModel rejectModel;

    private MaterialDialog progress;


    TextView tv_action_name;
    Spinner actionSpinner, reasonSpinner;
    Button btn_cancle, btn_submit;
    TextInputEditText remarkEditText;
    TextInputLayout remarkTextInputLayout;


    public SiteVisitListAdapter(Context context, List<SiteVisitModel> siteVisitModelArrayList, RowClick rowClick) {
        this.mCon = context;
        this.siteVisitModelArrayList = siteVisitModelArrayList;
        this.rowClick = rowClick;
    }


    private void clear() {
        if (siteVisitModelArrayList != null) {
            siteVisitModelArrayList.clear();
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // View view = inflater.inflate(R.layout.row_site_visit_list, viewGroup, false);
        // return new SiteVisitListAdapter.MyViewHolder(view);
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_site_visit_list, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
      //  Toast.makeText(mCon, "position:"+position, Toast.LENGTH_SHORT).show();
        SiteVisitModel model = siteVisitModelArrayList.get(position);
       // Log.e("sitedata","position "+model.toString());


        realmOperations = new RealmOperations(mCon);
        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();

        requestNoStr = model.getREQUEST_NO();
        applicantNameStr = model.getNAME();
        amApplicationDateStr = model.getAM_AAPP_DATE();
//                        DateFormat inputFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm");

        addressStr = model.getADDRESS();
        phoneNumberStr = model.getAM_APP_PHONEM();
        statusStr = model.getSTATUS();
        statusDateStr = model.getAM_APP_PROCDT();
        zonStr = model.getZONENAME();
        apptype = model.getAM_AAPP_NO_TYPE();
        //workAllocationDateTimeStr = current date
        wardStr = model.getSUBZONE();
        appointmentDateStr = model.getAM_APP_APPOINT_DATE();
        governmentTypeStr = model.getAM_APP_GOVT_PROPERTY();
        connectionTypeStr = model.getTARIFF_NAME();
        categoryNameStr = model.getCATEGORY_NAME();
        connectionSizeStr = model.getCONNECTION_SIZE();
        applicationStatusStr= model.getAPTM_NAME();


        holder.bind(model);
        holder.expandImageView.setOnClickListener(v -> {
            boolean expanded = model.isExpanded();
            model.setExpanded(!expanded);
            notifyItemChanged(position);
        });


        if (siteVisitModelArrayList.get(position).getAM_AAPP_NO_TYPE().equalsIgnoreCase("4") || siteVisitModelArrayList.get(position).getAM_AAPP_NO_TYPE().equalsIgnoreCase("5")) {
            holder.btn_reject_hold.setText("Reject");
            String count= mCon.getString(R.string.totalVisits, model.getNOOFVISITS());
            holder.tv_visitCount.setText(count);
            holder.tv_visitCount.setVisibility(View.VISIBLE);
        } else if(siteVisitModelArrayList.get(position).getAM_AAPP_NO_TYPE().equalsIgnoreCase("1"))
            holder.btn_reject_hold.setText("Hold");
        else {
            holder.btn_reject_hold.setText("Reject/Hold");
        }


        holder.tbv_application_number.setText(requestNoStr);
        holder.tv_application_date.setText(amApplicationDateStr);
        holder.tv_appointment_date.setText(appointmentDateStr);
        holder.tv_applicant_name.setText(applicantNameStr);
        holder.tv_status.setText(statusStr);
        holder.tv_status_date.setText(statusDateStr);
        holder.tv_contact_no.setText(phoneNumberStr);
        holder.tv_zone.setText(zonStr);
        holder.AddressTextView.setText(addressStr);
        holder.tv_ward.setText(wardStr);
        holder.tv_connection_type.setText(connectionTypeStr + "/" + categoryNameStr);
        holder.tv_connection_size_mm.setText(connectionSizeStr);
        holder.tv_govt_emp.setText(governmentTypeStr);
        holder.tv_application_status.setText(applicationStatusStr);


        /*if (model.getLATI() == null || model.getLONGI() == null || model.getLATI() == "" || model.getLONGI() == ""|| model.getLATI() =="0"|| model.getLONGI() == "0") {
            holder.ll_location.setVisibility(View.GONE);
        }*/
        if (model.getLATI().equalsIgnoreCase("0") || model.getLATI().equalsIgnoreCase("NULL") || model.getLATI().equalsIgnoreCase("")) {
            holder.ll_location.setVisibility(View.GONE);

        }

        holder.ll_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCon, SingleMapLocationActivity.class);

                intent.putExtra("locationLat", model.getLATI());
                intent.putExtra("locationLongi", model.getLONGI());
                intent.putExtra("strAddress", model.getAM_APP_SERVNO());
                intent.putExtra("consumerNum", model.getADDRESS());
                intent.putExtra("consumerName", model.getNAME());
                intent.putExtra("tag", "SV");
                mCon.startActivity(intent);
                ((Activity) mCon).finish();


            }
        });


    }

    @Override
    public int getItemCount() {
        return siteVisitModelArrayList.size();
    }

    public void filterList(List<SiteVisitModel> list) {
        siteVisitModelArrayList = list;
        notifyDataSetChanged();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tbv_application_number,tv_application_status, tv_application_date, tv_appointment_date, tv_applicant_name, tv_status_date, tv_contact_no, tv_zone,
                AddressTextView, tv_ward, tv_connection_type, tv_connection_size_mm, tv_govt_emp, tv_status,tv_visitCount;

        private ImageView locationImageView, expandImageView;
        Button btn_proceed, btn_reject_hold;
        CardView card_view;
        private View subItem;
        LinearLayout ll_location;


        public MyViewHolder(View itemView) {
            super(itemView);
            tbv_application_number = itemView.findViewById(R.id.tbv_application_number);
            tv_application_status= itemView.findViewById(R.id.application_status);
            tv_application_date = itemView.findViewById(R.id.tv_application_date);
            tv_appointment_date = itemView.findViewById(R.id.tv_appointment_date);
            tv_applicant_name = itemView.findViewById(R.id.tv_applicant_name);
            AddressTextView = itemView.findViewById(R.id.AddressTextView);
            tv_status_date = itemView.findViewById(R.id.tv_status_date);
            tv_contact_no = itemView.findViewById(R.id.tv_contact_no);
            tv_zone = itemView.findViewById(R.id.tv_zone);
            tv_ward = itemView.findViewById(R.id.tv_ward);
            locationImageView = itemView.findViewById(R.id.locationImageView);
            expandImageView = itemView.findViewById(R.id.expandImageView);
            tv_connection_type = itemView.findViewById(R.id.tv_connection_type);
            tv_connection_size_mm = itemView.findViewById(R.id.tv_connection_size_mm);
            tv_govt_emp = itemView.findViewById(R.id.tv_govt_emp);
            tv_status = itemView.findViewById(R.id.tv_status);
            card_view = itemView.findViewById(R.id.card_view);
            ll_location = itemView.findViewById(R.id.ll_location);
            btn_proceed = itemView.findViewById(R.id.btn_proceed);
            btn_reject_hold = itemView.findViewById(R.id.btn_reject_hold);
            tv_visitCount= itemView.findViewById(R.id.visitCount);

            subItem = itemView.findViewById(R.id.linear_expand);

            btn_proceed.setOnClickListener(this);
            btn_reject_hold.setOnClickListener(this);

            try {
                // Decrypt EmpCode
                empCode = new AesAlgorithm().decrypt(PreferenceUtil.getEmployeeCode());
            } catch (Exception e) {
                e.printStackTrace();
            }
            ipAddress = PreferenceUtil.getMac();

        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_proceed) {
                int position = getAdapterPosition();
                requestProceed(position);
            } else if (v.getId() == R.id.btn_reject_hold) {
                int position = getAdapterPosition();
                positionNew=getAdapterPosition();
                int tag=0;

                if (siteVisitModelArrayList.get(position).getAM_AAPP_NO_TYPE().equalsIgnoreCase("4") || siteVisitModelArrayList.get(position).getAM_AAPP_NO_TYPE().equalsIgnoreCase("5")) {

                  tag=4;
                }else if(siteVisitModelArrayList.get(position).getAM_AAPP_NO_TYPE().equalsIgnoreCase("1")){

                    tag=1;
                }
                else {
                   tag=0;

                }
                showCustomDialog(itemView, position, tag);


            }


        }


        private void showCustomDialog(View itemView, int position, int tag) {


            //before inflating the custom alert dialog layout, we will get the current activity viewgroup
            ViewGroup viewGroup = itemView.findViewById(android.R.id.content);
            //then we will inflate the custom alert dialog xml that we created

            View dialogView = LayoutInflater.from(mCon).inflate(R.layout.dialog_reject_hold, viewGroup, false);
            tv_action_name = dialogView.findViewById(R.id.tv_action_name);

            actionSpinner = dialogView.findViewById(R.id.actionSpinner);
            ArrayList<String> reasonByName = new ArrayList<>();
            reasonByName = realmOperations.fetchRejectReason();
            Log.e("action", reasonByName.toString());
            String[] actionRejectItems = new String[0];
            reasonSpinner = dialogView.findViewById(R.id.reasonSpinner);
            ArrayList<String> actionTypeList = new ArrayList<>();
            if (tag==4) {
                actionTypeList.addAll(Arrays.asList(mCon.getResources().getStringArray(R.array.action_reject_without_hold)));

            }else if(tag==1){
                actionTypeList.addAll(Arrays.asList(mCon.getResources().getStringArray(R.array.action_reject_without_reject)));

            }
            else {

                actionTypeList.addAll(Arrays.asList(mCon.getResources().getStringArray(R.array.action_reject)));
            }

            actionadapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, actionTypeList);
            actionadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            actionSpinner.setAdapter(actionadapter);


            ArrayList<String> reasonTypeList = new ArrayList<>();
            reasonTypeList.add("Select");
            reasonTypeList.addAll(reasonByName);

            reasonTypeAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, reasonTypeList);
            reasonTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            reasonSpinner.setAdapter(reasonTypeAdapter);

            remarkEditText = dialogView.findViewById(R.id.remarkEditText);

            actionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    rejectTypeName = actionSpinner.getSelectedItem().toString();
                    if (rejectTypeName.equalsIgnoreCase("Select")) {
                    } else {
                        if (rejectTypeName.equalsIgnoreCase("Reject")) {
                            processCode = "187";
                        } else if (rejectTypeName.equalsIgnoreCase("Hold")) {
                            processCode = "188";
                        } else if (rejectTypeName.equalsIgnoreCase("Re-appointment")) {
                            processCode = "121";
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            reasonSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    reasonTypeName = reasonSpinner.getSelectedItem().toString();
                    if (reasonTypeName.equalsIgnoreCase("Select")) {
                        reasonSpinner.setSelection(0);
                    } else {
                        rejectModel = realmOperations.fetchRejectTypeByName(reasonTypeName);
                        reasonId = rejectModel.getID();
                        reasonName = rejectModel.getREM_REASONNM();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            btn_cancle = dialogView.findViewById(R.id.btn_cancle);
            btn_submit = dialogView.findViewById(R.id.btn_submit);


            //Now we need an AlertDialog.Builder object
            AlertDialog.Builder builder = new AlertDialog.Builder(mCon);

            //setting the view of the builder to our custom view that we already inflated
            builder.setView(dialogView);
            builder.setCancelable(false);

            //finally creating the alert dialog and displaying it
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            btn_cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();

                }
            });
            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (validation()) {
                        callRejectHold(alertDialog);
                    }

                }
            });
        }


        public void bind(SiteVisitModel model) {
            boolean expanded = model.isExpanded();

            subItem.setVisibility(expanded ? View.VISIBLE : View.GONE);

          /*  title.setText(movie.getTitle());
            genre.setText("Genre: " + movie.getGenre());
            year.setText("Year: " + movie.getYear());*/
        }
    }

    private boolean validation() {

        rejectTypeName = actionSpinner.getSelectedItem().toString();
        reasonTypeName = reasonSpinner.getSelectedItem().toString();
        remarkTypeName = remarkEditText.getText().toString();

        if (rejectTypeName.equalsIgnoreCase("Select")) {
            Toast.makeText(mCon, R.string.please_select_reject_type, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (reasonTypeName.equalsIgnoreCase("Select")) {
            Toast.makeText(mCon, R.string.please_select_reason_type, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (remarkTypeName.equalsIgnoreCase("")) {
            Toast.makeText(mCon, "Please enter remark", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;

    }

    private void callRejectHold(AlertDialog alertDialog) {

        String params[] = new String[8];
        params[0] = apptype;//need to be as app type.
        params[1] = siteVisitModelArrayList.get(positionNew).getREQUEST_NO();
        params[2] = processCode;
        params[3] = reasonId;
        params[4] = reasonName;
        params[5] = remarkTypeName;
        params[6] = empCode;
        params[7] = ipAddress;

        if (connection.isConnectingToInternet()) {
            // Log.d("param",params.toString());
            RejectHoldEntry rejectHoldEntry = new RejectHoldEntry(alertDialog);
            rejectHoldEntry.execute(params);

        } else {
            Toast.makeText(mCon, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }


    @SuppressLint("StaticFieldLeak")
    private class RejectHoldEntry extends AsyncTask<String, Void, Void> {
        AlertDialog alertDialog;

        public RejectHoldEntry(AlertDialog alertDialog) {
            this.alertDialog = alertDialog;
        }

        @Override
        protected void onPreExecute() {
            progress = new MaterialDialog.Builder(mCon)
                    .content(R.string.loading)
                    .widgetColorRes(R.color.colorPrimary)
                    .canceledOnTouchOutside(false)
                    .autoDismiss(false)
                    .progress(true, 0)
                    .show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                String paraName[] = new String[8];
                paraName[0] = "AppType";
                paraName[1] = "RefNo";
                paraName[2] = "ProcessCode";
                paraName[3] = "Rej_Hold_Reason_ID";
                paraName[4] = "Rej_Hold_Reason";
                paraName[5] = "Remark";
                paraName[6] = "EmpCode";
                paraName[7] = "IP";
                jsonRejectHoldResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.Update_Application_Status, params, paraName);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                String[] enums = gson.fromJson(jsonRejectHoldResponse, String[].class);
                if (enums[0].equalsIgnoreCase("Success")) {
                    alertDialog.dismiss();
                    if (rejectTypeName.equalsIgnoreCase("Reject")) {
                        //remainder remark
                        MessageWindow.throwOutFromWindow(mCon, "Application rejected Successfully.", "Success", MainActivity.class);
                    } else if (rejectTypeName.equalsIgnoreCase("Hold")) {
                        //remainder remark
                        MessageWindow.throwOutFromWindow(mCon, "Application on hold.", "Success", MainActivity.class);
                    } else if (rejectTypeName.equalsIgnoreCase("Re-appointment")) {
                        MessageWindow.throwOutFromWindow(mCon, "Application Re-appointment Successfully.", "Success", MainActivity.class);
                    }
                } else {
                    alertDialog.dismiss();
                    MessageWindow.errorWindow(mCon, enums[0].toString());
                }

            } catch (Exception e) {
                alertDialog.dismiss();
                Log.d("check", e.getMessage());
                Toast.makeText(mCon, "" + jsonRejectHoldResponse, Toast.LENGTH_LONG).show();
                String error = e.toString();
            }

            progress.dismiss();
        }
    }


    private void requestProceed(int position) {
        model2 = ((Activity) mCon).getIntent().getParcelableExtra("siteVisitEntity");


        if (siteVisitModelArrayList.get(position).getAM_AAPP_NO_TYPE().equalsIgnoreCase("1") || siteVisitModelArrayList.get(position).getAM_AAPP_NO_TYPE().equalsIgnoreCase("6") || siteVisitModelArrayList.get(position).getAM_AAPP_NO_TYPE().equalsIgnoreCase("7") || siteVisitModelArrayList.get(position).getAM_AAPP_NO_TYPE().equalsIgnoreCase("12")) {
            final Dialog dialog = new Dialog(mCon);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.layout_custom_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            TextView tv_title = dialog.findViewById(R.id.tv_title);
            tv_title.setText("Site Visit");
            TextView tv_description = dialog.findViewById(R.id.tv_description);
            tv_description.setText("Do you want to process site visit?");
            FrameLayout mDialogNo = dialog.findViewById(R.id.frmCancel);
            mDialogNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            FrameLayout mDialogOk = dialog.findViewById(R.id.frmOk);
            mDialogOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SiteVisitModel siteVisitModel = siteVisitModelArrayList.get(position);
                    rowClick.rowClicked(v.getId(), siteVisitModel);
                    dialog.dismiss();
                }
            });
            dialog.show();


        }/*else if(siteVisitModelArrayList.get(position).getAM_AAPP_NO_TYPE().equalsIgnoreCase("6")) {

            Intent i = new Intent(mCon, Siteuploaddocs.class);
            i.putExtra("siteVisitEntity",model2);
            mCon.startActivity(i);
            ((Activity) mCon).finish();

        }*/ else if (siteVisitModelArrayList.get(position).getAM_AAPP_NO_TYPE().equalsIgnoreCase("4")) {
            final Dialog dialog = new Dialog(mCon);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.layout_custom_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            TextView tv_title = dialog.findViewById(R.id.tv_title);
            tv_title.setText("Site Visit");
            TextView tv_description = dialog.findViewById(R.id.tv_description);
            tv_description.setText("Do you want to process site visit?");
            FrameLayout mDialogNo = dialog.findViewById(R.id.frmCancel);
            mDialogNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            FrameLayout mDialogOk = dialog.findViewById(R.id.frmOk);
            mDialogOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mCon, ActiveToPluggedActivity.class);
                    Log.e("daaata", siteVisitModelArrayList.get(position).toString());


                    intent.putExtra("consnum", siteVisitModelArrayList.get(position).getAM_APP_SERVNO());

                    intent.putExtra("conName", siteVisitModelArrayList.get(position).getNAME());
                    intent.putExtra("firstName", siteVisitModelArrayList.get(position).getAM_APP_FNAME());

                    intent.putExtra("contact", siteVisitModelArrayList.get(position).getAM_APP_PHONEM());
                    intent.putExtra("flat", siteVisitModelArrayList.get(position).getAM_APP_NO_OF_ROOMS());
                    intent.putExtra("meternum", siteVisitModelArrayList.get(position).getMTRM_SERIAL_NO());
                    intent.putExtra("applicationnum", siteVisitModelArrayList.get(position).getREQUEST_NO());
                    intent.putExtra("address", siteVisitModelArrayList.get(position).getADDRESS());
                    intent.putExtra("status", siteVisitModelArrayList.get(position).getSTATUS());
                    intent.putExtra("MTRM_CURRENT_READING", siteVisitModelArrayList.get(position).getMTRM_CURRENT_READING());
                    mCon.startActivity(intent);
                    ((Activity) mCon).finish();
                    dialog.dismiss();
                }
            });
            dialog.show();


        } else if (siteVisitModelArrayList.get(position).getAM_AAPP_NO_TYPE().equalsIgnoreCase("5")) {
            final Dialog dialog = new Dialog(mCon);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.layout_custom_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            TextView tv_title = dialog.findViewById(R.id.tv_title);
            tv_title.setText("Site Visit");
            TextView tv_description = dialog.findViewById(R.id.tv_description);
            tv_description.setText("Do you want to process site visit?");
            FrameLayout mDialogNo = dialog.findViewById(R.id.frmCancel);
            mDialogNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            FrameLayout mDialogOk = dialog.findViewById(R.id.frmOk);
            mDialogOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mCon, PluggedToActivity.class);


                    intent.putExtra("consnum", siteVisitModelArrayList.get(position).getAM_APP_SERVNO());

                    intent.putExtra("conName", siteVisitModelArrayList.get(position).getNAME());
                    intent.putExtra("firstName", siteVisitModelArrayList.get(position).getAM_APP_FNAME());

                    intent.putExtra("contact", siteVisitModelArrayList.get(position).getAM_APP_PHONEM());
                    intent.putExtra("flat", siteVisitModelArrayList.get(position).getAM_APP_NO_OF_ROOMS());
                    intent.putExtra("meternum", siteVisitModelArrayList.get(position).getMTRM_SERIAL_NO());
                    intent.putExtra("applicationnum", siteVisitModelArrayList.get(position).getREQUEST_NO());
                    intent.putExtra("address", siteVisitModelArrayList.get(position).getADDRESS());
                    intent.putExtra("status", siteVisitModelArrayList.get(position).getSTATUS());
                    intent.putExtra("MTRM_CURRENT_READING", siteVisitModelArrayList.get(position).getMTRM_CURRENT_READING());
                    mCon.startActivity(intent);
                    ((Activity) mCon).finish();
                    dialog.dismiss();
                }
            });
            dialog.show();


        } else if (siteVisitModelArrayList.get(position).getAM_AAPP_NO_TYPE().equalsIgnoreCase("18")) {

            Intent intent = new Intent(mCon, ActivityDisconnectMeter.class);


            intent.putExtra("consnum", siteVisitModelArrayList.get(position).getAM_APP_SERVNO());

            intent.putExtra("conName", siteVisitModelArrayList.get(position).getNAME());
            intent.putExtra("firstName", siteVisitModelArrayList.get(position).getAM_APP_FNAME());

            intent.putExtra("contact", siteVisitModelArrayList.get(position).getAM_APP_PHONE());
            intent.putExtra("flat", siteVisitModelArrayList.get(position).getAM_APP_NO_OF_ROOMS());
            intent.putExtra("meternum", siteVisitModelArrayList.get(position).getMTRM_SERIAL_NO());
            intent.putExtra("applicationnum", siteVisitModelArrayList.get(position).getREQUEST_NO());





/*
            intent.putExtra("locationLongi", model.getLONGI());
            intent.putExtra("strAddress", model.getAM_APP_SERVNO());
            intent.putExtra("consumerNum", model.getADDRESS());
            intent.putExtra("consumerName", model.getNAME());
            intent.putExtra("tag", "SV");
*/
            mCon.startActivity(intent);
            ((Activity) mCon).finish();


        } else {
            MessageWindow.messageWindow(mCon, "Process flow is not confirmed", "Message");


        }


    }

    public interface RowClick {
        void rowClicked(int id, SiteVisitModel siteVisitModel);
    }


}

