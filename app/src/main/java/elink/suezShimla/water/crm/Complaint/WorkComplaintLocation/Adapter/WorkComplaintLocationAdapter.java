package elink.suezShimla.water.crm.Complaint.WorkComplaintLocation.Adapter;

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

import elink.suezShimla.water.crm.Complaint.WorkComplaintLocation.Model.WorkComplaintLocationModel;
import elink.suezShimla.water.crm.Complaint.WorkComplaintLocation.Activity.WorkComplaintLocationDetailActivity;

import elink.suezShimla.water.crm.R;

public class WorkComplaintLocationAdapter extends RecyclerView.Adapter<WorkComplaintLocationAdapter.MyViewHolder> {

    private List<WorkComplaintLocationModel> data;
    private Context mCon;
    private LayoutInflater inflater;
    private String uri;
    double locationLati,locationLongi;

    public WorkComplaintLocationAdapter(Context mCon) {
        this.mCon = mCon;
        data = new ArrayList<>();
        inflater = LayoutInflater.from(mCon);
    }

    public void addList(List<WorkComplaintLocationModel> workCompletionModelList) {
        if (data != null) {
            clear();

            data.addAll(workCompletionModelList);
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
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.row_work_complaint_location, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final WorkComplaintLocationModel current = data.get(position);

        holder.complaintNumberTextView.setText(current.getComplaintNumber());
        holder.NameTextView.setText(current.getName());
        holder.NumberTextView.setText(current.getNumber());
        holder.AddressTextView.setText(current.getAddress());

        holder.workCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCon.startActivity(new Intent(v.getContext(), WorkComplaintLocationDetailActivity.class));
            }
        });

        holder.locationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    locationLati = Double.parseDouble("19.1147579");
                    locationLongi = Double.parseDouble("72.8597357");
                    holder.locationImageView.setVisibility(View.VISIBLE);

                    holder.locationImageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

//                            Intent intent = new Intent(v.getContext(), RoutingActivity.class);
//                            intent.putExtra("locationLat",locationLati);
//                            intent.putExtra("locationLongi",locationLongi);
//                            v.getContext().startActivity(intent);
                        }
                    });


               /* uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:" + 19.1147579 + "," + 72.8597357);

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
               */ }

        });

       /* Log.d("check", "type= " + PreferenceUtil.getUserType());
        if (PreferenceUtil.getUserType() != null && !PreferenceUtil.getUserType().equals("") && PreferenceUtil.getUserType().equals("Employee")) {
            holder.locationImageView.setVisibility(View.VISIBLE);
        } else if (PreferenceUtil.getUserType() != null && !PreferenceUtil.getUserType().equals("") && PreferenceUtil.getUserType().equals("Admin")) {
            holder.locationImageView.setVisibility(View.GONE);
        }*/


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView complaintNumberTextView, NameTextView, NumberTextView, AddressTextView;
        private CardView workCardView;
        private AppCompatImageView locationImageView;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            complaintNumberTextView = itemView.findViewById(R.id.complaintNumberTextView);
            NameTextView = itemView.findViewById(R.id.NameTextView);
            NumberTextView = itemView.findViewById(R.id.NumberTextView);
            AddressTextView = itemView.findViewById(R.id.AddressTextView);
            workCardView = itemView.findViewById(R.id.workCardView);

            locationImageView = itemView.findViewById(R.id.locationImageView);
        }
    }
}
