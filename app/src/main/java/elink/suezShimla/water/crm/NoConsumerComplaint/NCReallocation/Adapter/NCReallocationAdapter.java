package elink.suezShimla.water.crm.NoConsumerComplaint.NCReallocation.Adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.NoConsumerComplaint.NCReallocation.Activity.NCWorkReallocationDetailsActivity;
import elink.suezShimla.water.crm.NoConsumerComplaint.NCReallocation.Model.NCReallocationModel;
import elink.suezShimla.water.crm.R;

public class NCReallocationAdapter extends RecyclerView.Adapter<NCReallocationAdapter.MyViewHolder>{
    private Context mCon;
    private List<NCReallocationModel> data;
    LayoutInflater inflater;

    public NCReallocationAdapter(Context mCon) {
        this.mCon = mCon;
        inflater = LayoutInflater.from(mCon);
        data = new ArrayList<>();
    }

    public void addList(List<NCReallocationModel> reallocationModels) {
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
    public NCReallocationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.row_work_re_allocation, viewGroup, false);
        return new NCReallocationAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NCReallocationAdapter.MyViewHolder holder, int position) {
        final NCReallocationModel current = data.get(position);

        holder.complaintNumberTextView.setText(current.getCOMNO());
        holder.consumerNoTextView.setText(current.getCOM_SERVICE_NO());
        holder.dateTimeTextView.setText(current.getCOM_COMPDATE());
        holder.complaintTypeTextView.setText(current.getCMTM_NAME());
        holder.NameTextView.setText(current.getCONSUMER_NAME());
        holder.NumberTextView.setText(current.getMOBILE());
        holder.AddressTextView.setText(current.getADDRESS_NO());
        holder.vipTextView.setText(current.getVIP());

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NCWorkReallocationDetailsActivity.class);
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
                intent.putExtra("assignedCode", current.getASSIGNEDCODE());
                intent.putExtra("assignedName", current.getASSIGNEDNAME());
                intent.putExtra("vipName", current.getVIP());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView complaintNumberTextView, consumerNoTextView, dateTimeTextView, complaintTypeTextView, NameTextView, NumberTextView, AddressTextView,vipTextView;
        private CardView card_view;
        private ImageView locationImageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            complaintNumberTextView = itemView.findViewById(R.id.complaintNumberTextView);
            consumerNoTextView = itemView.findViewById(R.id.consumerNoTextView);
            dateTimeTextView = itemView.findViewById(R.id.dateTimeTextView);
            complaintTypeTextView = itemView.findViewById(R.id.complaintSubTypeTextView);
            NameTextView = itemView.findViewById(R.id.NameTextView);
            NumberTextView = itemView.findViewById(R.id.NumberTextView);
            AddressTextView = itemView.findViewById(R.id.AddressTextView);
            card_view = itemView.findViewById(R.id.card_view);
            locationImageView = itemView.findViewById(R.id.locationImageView);
            vipTextView = itemView.findViewById(R.id.vipTextView);
        }
    }
}
