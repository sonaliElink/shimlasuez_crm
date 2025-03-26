package elink.suezShimla.water.crm.ConnectionRequest.CommercialFeasibility.Adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import elink.suezShimla.water.crm.ConnectionRequest.CommercialFeasibility.CommercialFeasibilityDetailedActivity;
import elink.suezShimla.water.crm.ConnectionRequest.CommercialFeasibility.Model.CommercialFeasibilityModel;
import elink.suezShimla.water.crm.R;

public class CommercialFeasibilityAdapter extends RecyclerView.Adapter<CommercialFeasibilityAdapter.MyViewHolder> {
    private Context mCon;
    private LayoutInflater inflater;
    private List<CommercialFeasibilityModel> data;
    private String uri;

    public CommercialFeasibilityAdapter(Context context) {
        this.mCon = context;
        inflater = LayoutInflater.from(mCon);
        data = new ArrayList<>();
    }

    public void addList(List<CommercialFeasibilityModel> list) {
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
        View view = inflater.inflate(R.layout.row_commercial_feasibility, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final CommercialFeasibilityModel current = data.get(position);

        holder.applicationNoTextView.setText(current.getApplicationNo());
        holder.dateTextView.setText(current.getApplicationDate());
        holder.complaintTypeTextView.setText(current.getComplaintType());
        holder.applicantNameTextView.setText(current.getApplicantName());
        holder.contactNumberTextView.setText(current.getContactNo());
        holder.siteAddressTextView.setText(current.getAddress());

        holder.locationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:" + 19.1147579 + "," + 72.8597357);

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
                }
            }
        });

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCon, CommercialFeasibilityDetailedActivity.class);
                intent.putExtra("applicantName", current.getApplicantName());
                intent.putExtra("applicantContactNo", current.getContactNo());
                intent.putExtra("applicantAddress", current.getAddress());
                mCon.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView applicationNoTextView, dateTextView, complaintTypeTextView, applicantNameTextView, contactNumberTextView, siteAddressTextView;
        AppCompatImageView locationImageView;
        CardView card_view;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            applicationNoTextView = itemView.findViewById(R.id.applicationNoTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            complaintTypeTextView = itemView.findViewById(R.id.complaintSubTypeTextView);
            applicantNameTextView = itemView.findViewById(R.id.applicantNameTextView);
            contactNumberTextView = itemView.findViewById(R.id.contactNumberTextView);
            siteAddressTextView = itemView.findViewById(R.id.siteAddressTextView);
            locationImageView = itemView.findViewById(R.id.locationImageView);
            card_view = itemView.findViewById(R.id.card_view);
        }
    }
}
