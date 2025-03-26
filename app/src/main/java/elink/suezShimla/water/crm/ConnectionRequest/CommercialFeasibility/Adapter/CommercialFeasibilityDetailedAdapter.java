package elink.suezShimla.water.crm.ConnectionRequest.CommercialFeasibility.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.ConnectionRequest.CommercialFeasibility.Model.CommercialFeasibilityModel;
import elink.suezShimla.water.crm.R;

public class CommercialFeasibilityDetailedAdapter extends RecyclerView.Adapter<CommercialFeasibilityDetailedAdapter.MyViewHolder> {
    private Context mCon;
    private LayoutInflater inflater;
    private List<CommercialFeasibilityModel> data;

    public CommercialFeasibilityDetailedAdapter(Context context) {
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
        View view = inflater.inflate(R.layout.row_commercial_feasibility_detailed, viewGroup, false);
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

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView applicationNoTextView, dateTextView, complaintTypeTextView, applicantNameTextView, contactNumberTextView, siteAddressTextView;
        CardView card_view;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            applicationNoTextView = itemView.findViewById(R.id.applicationNoTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            complaintTypeTextView = itemView.findViewById(R.id.complaintSubTypeTextView);
            applicantNameTextView = itemView.findViewById(R.id.applicantNameTextView);
            contactNumberTextView = itemView.findViewById(R.id.contactNumberTextView);
            siteAddressTextView = itemView.findViewById(R.id.siteAddressTextView);
            card_view = itemView.findViewById(R.id.card_view);
        }
    }
}
