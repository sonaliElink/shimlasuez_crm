package elink.suezShimla.water.crm.Complaint.Reallocation.Adapter;

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

import elink.suezShimla.water.crm.Complaint.Reallocation.Activity.WorkReAllocationDetailsActivity;
import elink.suezShimla.water.crm.Complaint.Reallocation.Model.ReallocationResponseDataModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.map.SingleMapLocationActivity;

public class ReallocationAdapter extends RecyclerView.Adapter<ReallocationAdapter.MyViewHolder> {

    private Context mCon;
    private List<ReallocationResponseDataModel> data;
    LayoutInflater inflater;

    public ReallocationAdapter(Context mCon) {
        this.mCon = mCon;
        inflater = LayoutInflater.from(mCon);
        data = new ArrayList<>();
    }

    public void addList(List<ReallocationResponseDataModel> reallocationModels) {
        if (data != null) {
            clear();
            data.addAll(reallocationModels);
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
    public ReallocationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.row_work_re_allocation, viewGroup, false);
        return new ReallocationAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReallocationAdapter.MyViewHolder holder, int position) {
        String count = String.valueOf(position+1);
        final ReallocationResponseDataModel current = data.get(position);
        holder.srNoTextView.setText(count);
        holder.complaintNumberTextView.setText(current.getCOMNO());
        holder.consumerNoTextView.setText(current.getCOM_SERVICE_NO());
        holder.dateTimeTextView.setText(current.getCOM_COMPDATE());
        holder.complaintTypeTextView.setText(current.getCMTM_NAME());
        holder.complaintSubTypeTextView.setText(current.getOCRM_NAME());
        holder.NameTextView.setText(current.getCONSUMER_NAME());
        holder.NumberTextView.setText(current.getMOBILE());
        holder.AddressTextView.setText(current.getADDRESS_NO());
        holder.vipTextView.setText(current.getVIP());

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), WorkReAllocationDetailsActivity.class);
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
                intent.putExtra("priority", current.getPRIORITY());
                intent.putExtra("sourceType", current.getSOURCETYPE());
                intent.putExtra("assignedCode", current.getASSIGNEDCODE());
                intent.putExtra("assignedName", current.getASSIGNEDNAME());
                intent.putExtra("vipName", current.getVIP());
                intent.putExtra("com_seq", current.getCOM_SEQ());
                intent.putExtra("complaintCode", current.getCOM_NO_TYPE());
                intent.putExtra("repeatCall", current.getCOM_CALLS());
                intent.putExtra("agging", current.getAGINGOFCOMPLAINT());
                intent.putExtra("sla", current.getSLA());
                v.getContext().startActivity(intent);
            }
        });

        holder.ll_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), SingleMapLocationActivity.class);
                intent.putExtra("locationLat",current.getSRM_LATITUDE());
                intent.putExtra("locationLongi",current.getSRM_LONGITUDE());
                intent.putExtra("strAddress",current.getADDRESS_NO());
                intent.putExtra("consumerNum",current.getCOM_SERVICE_NO());

                intent.putExtra("consumername", current.getCMTM_NAME());
                intent.putExtra("tag","WR");
                v.getContext().startActivity(intent);
                ((Activity)v.getContext()).finish();


            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView complaintNumberTextView,srNoTextView, consumerNoTextView, dateTimeTextView, complaintTypeTextView,complaintSubTypeTextView, NameTextView, NumberTextView, AddressTextView,vipTextView;
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
            NumberTextView = itemView.findViewById(R.id.NumberTextView);
            AddressTextView = itemView.findViewById(R.id.AddressTextView);
            card_view = itemView.findViewById(R.id.card_view);
            locationImageView = itemView.findViewById(R.id.locationImageView);
            vipTextView = itemView.findViewById(R.id.vipTextView);
            srNoTextView = itemView.findViewById(R.id.srNoTextView);
            ll_location = itemView.findViewById(R.id.ll_location);
        }
    }
}
