package elink.suezShimla.water.crm.Complaint.Allocation.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.Complaint.Allocation.Activity.WorkAllocationDetailsActivity;
import elink.suezShimla.water.crm.Complaint.Allocation.Model.AllocationModel;

import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.map.SingleMapLocationActivity;

public class WorkAllocationAdapter extends RecyclerView.Adapter<WorkAllocationAdapter.MyViewHolder> {

    private Context mCon;
    private List<AllocationModel> data;
    private LayoutInflater inflater;
    private String uri;
    double locationLati,locationLongi;

    public WorkAllocationAdapter(Context mCon) {
        this.mCon = mCon;
        inflater = LayoutInflater.from(mCon);
        data = new ArrayList<>();
    }

    public void addList(List<AllocationModel> allocationModels) {
        if (data != null) {
            clear();
            data.addAll(allocationModels);
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
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.row_work_allocation, viewGroup, false);
        return new WorkAllocationAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String count = String.valueOf(position+1);
        final AllocationModel current = data.get(position);
        holder.srNoTextView.setText(count);

        holder.complaintNumberTextView.setText(current.getCOMNO());
        holder.consumerNoTextView.setText(current.getCOM_SERVICE_NO());
        holder.dateTimeTextView.setText(current.getCOM_COMPDATE());
        holder.complaintTypeTextView.setText(current.getCMTM_NAME());
        holder.complaintSubTypeTextView.setText(current.getOCRM_NAME());
        holder.NameTextView.setText(current.getCONSUMER_NAME());
        holder.complaintByTextView.setText(current.getComplaintBy());
        holder.NumberTextView.setText(current.getMOBILE());
        holder.AddressTextView.setText(current.getADDRESS_NO());
        holder.vipTextView.setText(current.getVIP());

        if (current.getCOM_LAT() == null || current.getCON_LONG() == null || current.getCOM_LAT() == "" || current.getCON_LONG() == "") {
            holder.locationImageView.setVisibility(View.GONE);
        } else {
            locationLati = Double.parseDouble(current.getCOM_LAT());
            locationLongi = Double.parseDouble(current.getCON_LONG());
            holder.locationImageView.setVisibility(View.VISIBLE);

            holder.ll_location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(v.getContext(), SingleMapLocationActivity.class);
                    intent.putExtra("locationLat",current.getCOM_LAT());
                    intent.putExtra("locationLongi",current.getCON_LONG());
                    intent.putExtra("strAddress",current.getADDRESS_NO());
                    intent.putExtra("consumerNum",current.getCOM_SERVICE_NO());

                    intent.putExtra("consumername", current.getCMTM_NAME());
                    intent.putExtra("tag","WA");
                    v.getContext().startActivity(intent);
                    ((Activity)v.getContext()).finish();


                }
            });
        }

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), WorkAllocationDetailsActivity.class);
                intent.putExtra("complaintNo", current.getCOMNO());
                intent.putExtra("consumerNo", current.getCOM_SERVICE_NO());
                intent.putExtra("complaintDateTime", current.getCOM_COMPDATE());
                intent.putExtra("consumerName", current.getCONSUMER_NAME());
                intent.putExtra("mobileNo", current.getMOBILE());
                intent.putExtra("address", current.getADDRESS_NO());
                intent.putExtra("complaintType", current.getCMTM_NAME());
                intent.putExtra("complaintSubType", current.getOCRM_NAME());
                intent.putExtra("tariff", current.getTARIFF());
                intent.putExtra("status", current.getSTATUS());
                intent.putExtra("zone", current.getBUM_BU_NAME());
                intent.putExtra("subzone", current.getCIRCLE_NAME());
                intent.putExtra("disputeBillMonthYr", current.getCOM_YEAR_BILL());
                intent.putExtra("vipname", current.getVIP());
                intent.putExtra("sla_status", current.getSLA());
                intent.putExtra("aging", current.getAGINGOFCOMPLAINT());
                intent.putExtra("repeat_calls", current.getCOM_CALLS());
                intent.putExtra("com_seq", current.getCOM_SEQ());
                intent.putExtra("complaintCode", current.getCOM_NO_TYPE());
                intent.putExtra("priority", current.getPRIORITY());

                v.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView complaintNumberTextView, consumerNoTextView, dateTimeTextView, complaintTypeTextView, complaintSubTypeTextView,complaintByTextView,
                NameTextView, NumberTextView,
                AddressTextView,vipTextView,srNoTextView;
        private CardView card_view;
        private ImageView locationImageView;
        private LinearLayout ll_location;

        public MyViewHolder(View itemView) {
            super(itemView);
            complaintNumberTextView = itemView.findViewById(R.id.complaintNumberTextView);
            consumerNoTextView = itemView.findViewById(R.id.consumerNoTextView);
            dateTimeTextView = itemView.findViewById(R.id.dateTimeTextView);
            complaintTypeTextView = itemView.findViewById(R.id.complaintTypeTextView);
            complaintSubTypeTextView = itemView.findViewById(R.id.complaintSubTypeTextView);
            NameTextView = itemView.findViewById(R.id.NameTextView);
            complaintByTextView = itemView.findViewById(R.id.complaintByTextView);
            NumberTextView = itemView.findViewById(R.id.NumberTextView);
            AddressTextView = itemView.findViewById(R.id.AddressTextView);
            card_view = itemView.findViewById(R.id.card_view);
            locationImageView = itemView.findViewById(R.id.locationImageView);
            ll_location = itemView.findViewById(R.id.ll_location);
            vipTextView = itemView.findViewById(R.id.vipTextView);
            srNoTextView = itemView.findViewById(R.id.srNoTextView);

        }
    }
}
