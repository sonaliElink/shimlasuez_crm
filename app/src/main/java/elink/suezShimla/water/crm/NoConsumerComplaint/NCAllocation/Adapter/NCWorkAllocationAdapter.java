package elink.suezShimla.water.crm.NoConsumerComplaint.NCAllocation.Adapter;

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

import elink.suezShimla.water.crm.NoConsumerComplaint.NCAllocation.Activity.NCWorkAllocationDetailsActivity;
import elink.suezShimla.water.crm.NoConsumerComplaint.NCAllocation.Model.NCAllocationModel;
import elink.suezShimla.water.crm.R;

public class NCWorkAllocationAdapter extends RecyclerView.Adapter<NCWorkAllocationAdapter.MyViewHolder>{
    private Context mCon;
    private List<NCAllocationModel> data;
    private LayoutInflater inflater;
    private String uri;
    double locationLati,locationLongi;


    public NCWorkAllocationAdapter(Context mCon) {
        this.mCon = mCon;
        inflater = LayoutInflater.from(mCon);
        data = new ArrayList<>();
    }

    public void addList(List<NCAllocationModel> ncallocationModels) {
        if (data != null) {
            clear();
            data.addAll(ncallocationModels);
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
        return new NCWorkAllocationAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final NCAllocationModel current = data.get(position);

        holder.complaintNumberTextView.setText(current.getCOMNO());
        holder.consumerNoTextView.setText(current.getCOM_SERVICE_NO());
        holder.dateTimeTextView.setText(current.getCOM_COMPDATE());
        holder.complaintTypeTextView.setText(current.getCMTM_NAME());
        holder.NameTextView.setText(current.getCONSUMER_NAME());
        holder.NumberTextView.setText(current.getMOBILE());
        holder.AddressTextView.setText(current.getADDRESS_NO());
        holder.vipTextView.setText(current.getVIP());

        //Log.d("check", "" + current.getCOM_LAT() + ", " + current.getCON_LONG());

        if (current.getCOM_LAT() == null || current.getCON_LONG() == null||current.getCOM_LAT() == "" || current.getCON_LONG() == "") {
            holder.locationImageView.setVisibility(View.GONE);
        } else {

            locationLati = Double.parseDouble(current.getCOM_LAT());
            locationLongi = Double.parseDouble(current.getCON_LONG());

            holder.locationImageView.setVisibility(View.VISIBLE);

            holder.locationImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(v.getContext(), RoutingActivity.class);
//                    intent.putExtra("locationLat",locationLati);
//                    intent.putExtra("locationLongi",locationLongi);
//                    v.getContext().startActivity(intent);
                   /* uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:" + current.getCOM_LAT() + "," + current.getCON_LONG());

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setPackage("com.google.android.apps.maps");

                    try {
                        mCon.startActivity(intent);
                    } catch (ActivityNotFoundException ex) {
                        try {
                            Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                            mCon.startActivity(unrestrictedIntent);
                        } catch (ActivityNotFoundException innerEx) {
                            Toast.makeText(mCon, "Please install a maps application", Toast.LENGTH_LONG).show();
                        }
                    }*/
                }
            });
        }

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NCWorkAllocationDetailsActivity.class);
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
