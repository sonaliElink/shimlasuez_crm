package elink.suezShimla.water.crm.MeterManagementSystem.MassMeterInstallatin.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.MeterManagementSystem.MassMeterInstallatin.Model.MassMeterInstllModel;
import elink.suezShimla.water.crm.R;

public class MassMeterInstllAdapter extends RecyclerView.Adapter<MassMeterInstllAdapter.MyViewHolder> {

    private Context mCon;
    private List<MassMeterInstllModel> data;
    LayoutInflater inflater;

    public MassMeterInstllAdapter(Context mCon) {
        this.mCon = mCon;
        inflater = LayoutInflater.from(mCon);
        data = new ArrayList<>();
    }

    public void addList(List<MassMeterInstllModel> massMeterInstllModelList) {
        if (data != null) {
            clear();

            data.addAll(massMeterInstllModelList);
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
    public MassMeterInstllAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.row_mass_meter_installation,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MassMeterInstllAdapter.MyViewHolder holder, final int i) {
        final MassMeterInstllModel current = data.get(i);

        holder.srNoTextView.setText("Sr. No. :"+" "+current.getSrNo());
        holder.messageTimeEditText.setText(current.getMessage());

        holder.meterInstCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked()) {
                    holder.checkBox.setChecked(false);
                }else {
                    holder.checkBox.setChecked(true);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView srNoTextView, messageTimeEditText;

        CardView meterInstCardView;

        CheckBox checkBox;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            srNoTextView = itemView.findViewById(R.id.srNoTextView);
            messageTimeEditText = itemView.findViewById(R.id.messageTimeEditText);
            meterInstCardView = itemView.findViewById(R.id.meterInstCardView);
            checkBox = itemView.findViewById(R.id.checkbox);

        }
    }
}
