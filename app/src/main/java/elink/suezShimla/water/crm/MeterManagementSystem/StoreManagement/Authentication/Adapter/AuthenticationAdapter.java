package elink.suezShimla.water.crm.MeterManagementSystem.StoreManagement.Authentication.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.MeterManagementSystem.StoreManagement.Authentication.Model.AuthenticationModel;
import elink.suezShimla.water.crm.R;

public class AuthenticationAdapter extends RecyclerView.Adapter<AuthenticationAdapter.MyViewHolder> {

    private Context mCOn;
    private LayoutInflater inflater;
    private List<AuthenticationModel> data;

    public AuthenticationAdapter(Context mCOn) {
        this.mCOn = mCOn;
        data = new ArrayList<>();
        inflater = LayoutInflater.from(mCOn);
    }

    public void addList(List<AuthenticationModel> authenticationModelList) {
        if (data != null) {
            clear();

            data.addAll(authenticationModelList);
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
    public AuthenticationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.row_authentication_meter_details, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AuthenticationAdapter.MyViewHolder holder, int i) {
        final AuthenticationModel current = data.get(i);

        holder.srNoTextView.setText("Sr. No. :"+" "+current.getSrNo());
        holder.meterNoTextView.setText("Meter No. :"+" "+current.getMeterNo());
        holder.checkbox.setChecked(current.isSelected());
        holder.makeCodeTextView.setText(current.getMakeCode());
        holder.statusDateTextView.setText(current.getStatusDate());
        holder.srNoTextView.setText(current.getSrNo());

        holder.authMeterCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkbox.isChecked()) {
                    holder.checkbox.setChecked(false);
                } else {
                    holder.checkbox.setChecked(true);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView deleteImg;

        TextView srNoTextView, meterNoTextView, makeCodeTextView, statusDateTextView;

        AppCompatCheckBox checkbox;

        CardView authMeterCardView;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            srNoTextView = itemView.findViewById(R.id.srNoTextView);
            meterNoTextView = itemView.findViewById(R.id.meterNoTextView);
            deleteImg = itemView.findViewById(R.id.deleteImg);
            checkbox = itemView.findViewById(R.id.checkbox);
            makeCodeTextView = itemView.findViewById(R.id.makeCodeTextView);
            statusDateTextView = itemView.findViewById(R.id.statusDateTextView);
            authMeterCardView = itemView.findViewById(R.id.authMeterCardView);
        }
    }

}
