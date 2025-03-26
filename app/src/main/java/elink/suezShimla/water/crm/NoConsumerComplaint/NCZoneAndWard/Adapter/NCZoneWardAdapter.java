package elink.suezShimla.water.crm.NoConsumerComplaint.NCZoneAndWard.Adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.NoConsumerComplaint.NCZoneAndWard.Model.NCZoneWardModel;
import elink.suezShimla.water.crm.NoConsumerComplaint.NCZoneAndWard.NCZoneAndWardDetails.NCZoneWardDetailsActivity;
import elink.suezShimla.water.crm.R;

public class NCZoneWardAdapter extends RecyclerView.Adapter<NCZoneWardAdapter.MyViewHolder> {
    private Context mCon;
    private List<NCZoneWardModel> data;
    private LayoutInflater inflater;

    public NCZoneWardAdapter(Context context) {
        mCon = context;
        data = new ArrayList<>();
        inflater = LayoutInflater.from(mCon);
    }

    public void addList(List<NCZoneWardModel> list) {
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
        View view = inflater.inflate(R.layout.row_zone_ward, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        final NCZoneWardModel current = data.get(i);

        holder.zoneTextView.setText(current.getZone());
        holder.wardTextView.setText(current.getWard());

        holder.zoneWardCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ward = String.valueOf(current.getWard_id());
                String zone = String.valueOf(current.getZone_id());

                Intent i = new Intent(mCon, NCZoneWardDetailsActivity.class);
                i.putExtra("GETWARD", current.getWard());
                i.putExtra("GETWARD_ID", ward);
                i.putExtra("GETZONE_ID", zone);
                mCon.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void filterList(List<NCZoneWardModel> list) {
        data = list;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView zoneTextView, wardTextView;
        CardView zoneWardCardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            zoneTextView = itemView.findViewById(R.id.zoneTextView);
            wardTextView = itemView.findViewById(R.id.wardTextView);
            zoneWardCardView = itemView.findViewById(R.id.zoneWardCardView);
        }
    }
}
