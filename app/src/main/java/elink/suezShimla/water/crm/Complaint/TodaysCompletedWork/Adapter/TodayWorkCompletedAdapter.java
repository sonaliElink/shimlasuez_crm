package elink.suezShimla.water.crm.Complaint.TodaysCompletedWork.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import elink.suezShimla.water.crm.Complaint.WorkCompletion.Model.WorkCompletionResponseModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.map.SingleMapLocationActivity;

public class TodayWorkCompletedAdapter extends RecyclerView.Adapter<TodayWorkCompletedAdapter.MyViewHolder> {

    private Context mCon;
    private List<WorkCompletionResponseModel> data;
    private LayoutInflater inflater;
    private String uri;
    private String searchForStr,fromDate,toDate,clickStr;
    double locationLati,locationLongi;


    public TodayWorkCompletedAdapter(Context mCon, String searchFor, String fromDate, String toDate, String clickStr) {
        this.mCon = mCon;
        inflater = LayoutInflater.from(mCon);
        data = new ArrayList<>();
        searchForStr = searchFor;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.clickStr = clickStr;
    }

    public void addList(List<WorkCompletionResponseModel> workCompletionModels) {
        if (data != null) {
            clear();
            data.addAll(workCompletionModels);
            notifyDataSetChanged();
        }
    }

    private void clear() {
        if (data != null) {
            data.clear();
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public TodayWorkCompletedAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.row_completed_work, viewGroup, false);
        return new TodayWorkCompletedAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodayWorkCompletedAdapter.MyViewHolder holder, int position) {
        String count = String.valueOf(position+1);
        final WorkCompletionResponseModel current = data.get(position);
        holder.srNoTextView.setText(count);

        holder.complaintNumberTextView.setText(current.getCOMNO());
        holder.consumerNoTextView.setText(current.getCOM_SERVICE_NO());
        holder.complaintWorkAlloDateTimeTextView.setText(current.getCOM_ALLOCATIONDATE());
        holder.complaintDateTimeTextView.setText(current.getCOM_COMPDATE());
        holder.complaintSubTypeTextView.setText(current.getOCRM_NAME());
        holder.consumerNameTextView.setText(current.getCONSUMER_NAME());
        holder.complaintByTextView.setText(current.getNAME());
        holder.contactNoTextView.setText(current.getCONTACTNO());
        holder.consumerAddressTextView.setText(current.getADDRESS_NO());
        holder.vipTextView.setText(current.getVIP());
        String originStr = current.getORIGIN();
        holder.readerRemarkTextView.setText(current.getREADERREMARK());
        if(originStr.equalsIgnoreCase("1")) {
            holder.originTextView.setText("Internal");
        }else {
            holder.originTextView.setText("External");

        }
        if (current.getSRM_LATITUDE() == null || current.getSRM_LONGITUDE() == null||current.getSRM_LATITUDE() == "" && current.getSRM_LONGITUDE() == "") {
            holder.locationImageView.setVisibility(View.GONE);
        } else {
            holder.locationImageView.setVisibility(View.VISIBLE);

            holder.locationImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(v.getContext(), SingleMapLocationActivity.class);
                    intent.putExtra("locationLat",current.getSRM_LATITUDE());
                    intent.putExtra("locationLongi",current.getSRM_LONGITUDE());
                    intent.putExtra("strAddress",current.getADDRESS_NO());
                    intent.putExtra("consumerNum",current.getCOM_SERVICE_NO());

                    intent.putExtra("tag","TC");
                    intent.putExtra("fromDate", fromDate);
                    intent.putExtra("toDate", toDate);
                    intent.putExtra("strClick", clickStr);
                    v.getContext().startActivity(intent);
                    ((Activity)v.getContext()).finish();

                }
            });
        }

/*
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), WorkCompletionDetailedActivity.class);
                intent.putExtra("consumerNo", current.getCOM_SERVICE_NO());
                intent.putExtra("complaintNo", current.getCOMNO());
                intent.putExtra("complaintWorkAllocateDate", current.getCOM_ALLOCATIONDATE());
                intent.putExtra("complaintDate", current.getCOM_COMPDATE());
                intent.putExtra("complaintSubType", current.getOCRM_NAME());
                intent.putExtra("tariff", current.getTARIFF());
                intent.putExtra("status", current.getSTATUS());
                intent.putExtra("consumerName", current.getNAME());
                intent.putExtra("address", current.getADDRESS_NO());
                intent.putExtra("priority", current.getPRIORITY());
                intent.putExtra("contactNo", current.getCONTACTNO());
                intent.putExtra("zone", current.getBUM_BU_NAME());
                intent.putExtra("subZone", current.getCIRCLE_NAME());
                intent.putExtra("disputeBillMonthYr", current.getCOM_YEAR_BILL());
                intent.putExtra("meterNo", current.getCOM_METER_REPLACE());
                intent.putExtra("meterTransId", current.getCOM_METER_TRANSID());
                intent.putExtra("address1", current.getADDRESS1());
                intent.putExtra("address2", current.getADDRESS2());
                intent.putExtra("address3", current.getADDRESS3());
                intent.putExtra("pincode", current.getPIN());
                intent.putExtra("complaintType", current.getCMTM_NAME());
                intent.putExtra("searchForStr", searchForStr);
                intent.putExtra("vipName",current.getVIP());
                intent.putExtra("actionForm",current.getCOM_REMARKS());
                intent.putExtra("complaintCode",current.getCOM_NO_TYPE());
                intent.putExtra("repeatCall",current.getCOM_CALLS());
                intent.putExtra("agging",current.getAGINGOFCOMPLAINT());
                intent.putExtra("sla",current.getSLA());
                intent.putExtra("remark",current.getCOM_REMARKS());
                intent.putExtra("comType",current.getCOM_TYPE());
                v.getContext().startActivity(intent);
        }
        });
*/
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void filterList(List<WorkCompletionResponseModel> list) {
        data = list;
        notifyDataSetChanged();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView locationImageView;
        private TextView complaintNumberTextView, consumerNoTextView, complaintWorkAlloDateTimeTextView, complaintDateTimeTextView, complaintSubTypeTextView,
                consumerNameTextView,complaintByTextView, contactNoTextView, consumerAddressTextView,vipTextView,srNoTextView,originTextView,readerRemarkTextView;
        private CardView card_view;

        public MyViewHolder(View itemView) {
            super(itemView);
            complaintNumberTextView = itemView.findViewById(R.id.complaintNumberTextView);
            consumerNoTextView = itemView.findViewById(R.id.consumerNoTextView);
            complaintWorkAlloDateTimeTextView = itemView.findViewById(R.id.complaintWorkAlloDateTimeTextView);
            complaintDateTimeTextView = itemView.findViewById(R.id.complaintDateTimeTextView);
            complaintSubTypeTextView = itemView.findViewById(R.id.complaintSubTypeTextView);
            consumerNameTextView = itemView.findViewById(R.id.consumerNameTextView);
            complaintByTextView = itemView.findViewById(R.id.complaintByTextView);
            contactNoTextView = itemView.findViewById(R.id.contactNoTextView);
            consumerAddressTextView = itemView.findViewById(R.id.consumerAddressTextView);
            locationImageView = itemView.findViewById(R.id.locationImageView);
            card_view = itemView.findViewById(R.id.card_view);
            vipTextView = itemView.findViewById(R.id.vipTextView);
            srNoTextView = itemView.findViewById(R.id.srNoTextView);
            originTextView = itemView.findViewById(R.id.originTextView);
            readerRemarkTextView = itemView.findViewById(R.id.readerRemarkTextView);
        }
    }
}
