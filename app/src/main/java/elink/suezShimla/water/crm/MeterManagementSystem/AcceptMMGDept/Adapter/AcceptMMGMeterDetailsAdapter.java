package elink.suezShimla.water.crm.MeterManagementSystem.AcceptMMGDept.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.MeterManagementSystem.AcceptMMGDept.Model.AcceptMMGMeterDetailsModel;
import elink.suezShimla.water.crm.R;

public class AcceptMMGMeterDetailsAdapter extends RecyclerView.Adapter<AcceptMMGMeterDetailsAdapter.MyViewHolder> {

    private List<AcceptMMGMeterDetailsModel> data;
    private Context mCon;
    LayoutInflater inflater;

    public AcceptMMGMeterDetailsAdapter(Context mCOn) {

        this.mCon = mCOn;
        data = new ArrayList<>();
        inflater = LayoutInflater.from(mCOn);
    }

    public void addList(List<AcceptMMGMeterDetailsModel> acceptMMGMeterDetailsModels) {
        if (data != null) {
            clear();

            data.addAll(acceptMMGMeterDetailsModels);
            notifyDataSetChanged();
        }
    }

    public List<AcceptMMGMeterDetailsModel> getList() {
        return data;
    }

    private void clear() {
        if (data != null) {
            data.clear();
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public AcceptMMGMeterDetailsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.row_accept_meter, viewGroup, false);
        return new AcceptMMGMeterDetailsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AcceptMMGMeterDetailsAdapter.MyViewHolder holder, int i) {
        final AcceptMMGMeterDetailsModel current = data.get(i);

        String srNo = "" + current.getSrNo();

        holder.srNoCheckBox.setText(srNo);
        holder.meterNoTextView.setText(current.getMeterNo());
        holder.makeCodeTextView.setText(current.getMakeCode());
        holder.dispatchDateTextView.setText(current.getDispatchDate());

        holder.srNoCheckBox.setChecked(current.isChecked());

        holder.srNoCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    current.setChecked(true);
                } else {
                    current.setChecked(false);
                }
            }
        });

        holder.row_linerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.srNoCheckBox.isChecked()) {
                    holder.srNoCheckBox.setChecked(false);
                    current.setChecked(false);
                } else {
                    holder.srNoCheckBox.setChecked(true);
                    current.setChecked(true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        CheckBox srNoCheckBox;
        TextView meterNoTextView, makeCodeTextView, dispatchDateTextView;
        LinearLayout row_linerLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            srNoCheckBox = itemView.findViewById(R.id.srNoCheckBox);
            meterNoTextView = itemView.findViewById(R.id.meterNoTextView);
            makeCodeTextView = itemView.findViewById(R.id.makeCodeTextView);
            dispatchDateTextView = itemView.findViewById(R.id.dispatchDateTextView);
            row_linerLayout = itemView.findViewById(R.id.row_linerLayout);
        }
    }
}
