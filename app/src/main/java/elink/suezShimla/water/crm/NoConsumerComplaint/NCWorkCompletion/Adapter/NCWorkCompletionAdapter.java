package elink.suezShimla.water.crm.NoConsumerComplaint.NCWorkCompletion.Adapter;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


import elink.suezShimla.water.crm.NoConsumerComplaint.NCWorkCompletion.Activity.NCWorkCompletionDetailedActivity;
import elink.suezShimla.water.crm.NoConsumerComplaint.NCWorkCompletion.Model.NCWorkCompletionModel;
import elink.suezShimla.water.crm.R;

public class NCWorkCompletionAdapter extends RecyclerView.Adapter<NCWorkCompletionAdapter.MyViewHolder> {
    private Context mCon;
    private List<NCWorkCompletionModel> data;
    private LayoutInflater inflater;
    private String uri;
    private String searchForStr;
    double locationLati,locationLongi;

    public NCWorkCompletionAdapter(Context mCon, String searchFor) {
        this.mCon = mCon;
        inflater = LayoutInflater.from(mCon);
        data = new ArrayList<>();
        searchForStr = searchFor;
    }

    public void addList(List<NCWorkCompletionModel> ncWorkCompletionModels) {
        if (data != null) {
            clear();
            data.addAll(ncWorkCompletionModels);
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
    public NCWorkCompletionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.row_work_completion, viewGroup, false);
        return new NCWorkCompletionAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NCWorkCompletionAdapter.MyViewHolder holder, int position) {
        final NCWorkCompletionModel current = data.get(position);

        holder.complaintNumberTextView.setText(current.getCOMNO());
        holder.consumerNoTextView.setText(current.getCOM_SERVICE_NO());
        holder.complaintWorkAlloDateTimeTextView.setText(current.getAllocatedDate());
        holder.complaintDateTimeTextView.setText(current.getCOM_COMPDATE());
        holder.complaintSubTypeTextView.setText(current.getOCRM_NAME());
        holder.consumerNameTextView.setText(current.getCMTM_NAME());
        holder.contactNoTextView.setText(current.getMOBILE());
        holder.consumerAddressTextView.setText(current.getADDRESS_NO());
        holder.vipTextView.setText(current.getVIP());
        /* locationLati = 19.0828;
        locationLongi = 72.8399;*/



       if (current.getCOM_LAT() == null || current.getCON_LONG() == null || current.getCOM_LAT() == "" || current.getCON_LONG() == "") {
       // if (String.valueOf(locationLati) == null || String.valueOf(locationLongi) == null || String.valueOf(locationLati) == "" || String.valueOf(locationLongi) == "") {
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
//                     intent.putExtra("locationLongi",locationLongi);
//                    v.getContext().startActivity(intent);
            /*        uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:" + current.getCOM_LAT() + "," + current.getCON_LONG());

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
                Intent intent = new Intent(v.getContext(), NCWorkCompletionDetailedActivity.class);
                intent.putExtra("consumerNo", current.getCOM_SERVICE_NO());
                intent.putExtra("complaintNo", current.getCOMNO());
                intent.putExtra("complaintWorkAllocateDate", current.getAllocatedDate());
                intent.putExtra("complaintDate", current.getCOM_COMPDATE());
                intent.putExtra("complaintSubType", current.getOCRM_NAME());
                intent.putExtra("tariff", current.getTARIFF());
                intent.putExtra("status", current.getSTATUS());
                intent.putExtra("consumerName", current.getCMTM_NAME());
                intent.putExtra("address", current.getADDRESS_NO());
                intent.putExtra("contactNo", current.getMOBILE());
                intent.putExtra("zone", current.getBUM_BU_NAME());
                intent.putExtra("subZone", current.getCIRCLE_NAME());
                intent.putExtra("disputeBillMonthYr", current.getCOM_YEAR_BILL());
                intent.putExtra("complaintType", current.getCMTM_NAME());
                intent.putExtra("searchForStr", searchForStr);
                intent.putExtra("vipName",current.getVIP());
                intent.putExtra("priorityStr",current.getPriority());
                intent.putExtra("allocateDate",current.getAllocatedDate());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
    private AppCompatImageView locationImageView;
    private TextView complaintNumberTextView, consumerNoTextView, complaintWorkAlloDateTimeTextView, complaintDateTimeTextView, complaintSubTypeTextView,
            consumerNameTextView, contactNoTextView, consumerAddressTextView,vipTextView;
    private CardView card_view;

    public MyViewHolder(View itemView) {
        super(itemView);
        complaintNumberTextView = itemView.findViewById(R.id.complaintNumberTextView);
        consumerNoTextView = itemView.findViewById(R.id.consumerNoTextView);
        complaintWorkAlloDateTimeTextView = itemView.findViewById(R.id.complaintWorkAlloDateTimeTextView);
        complaintDateTimeTextView = itemView.findViewById(R.id.complaintDateTimeTextView);
        complaintSubTypeTextView = itemView.findViewById(R.id.complaintSubTypeTextView);
        consumerNameTextView = itemView.findViewById(R.id.consumerNameTextView);
        contactNoTextView = itemView.findViewById(R.id.contactNoTextView);
        consumerAddressTextView = itemView.findViewById(R.id.consumerAddressTextView);
        locationImageView = itemView.findViewById(R.id.locationImageView);
        card_view = itemView.findViewById(R.id.card_view);
        vipTextView = itemView.findViewById(R.id.vipTextView);
    }
}
}
