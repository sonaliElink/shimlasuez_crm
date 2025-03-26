package elink.suezShimla.water.crm.MeterManagementSystem.AcceptMMGDept.Adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import elink.suezShimla.water.crm.MeterManagementSystem.AcceptMMGDept.Activity.AcceptMMGMeterDetailsActivity;
import elink.suezShimla.water.crm.MeterManagementSystem.AcceptMMGDept.Model.AcceptMMGDeptModel;
import elink.suezShimla.water.crm.R;

public class AcceptMMGDeptAdapter extends RecyclerView.Adapter<AcceptMMGDeptAdapter.MyViewHolder> {

    private List<AcceptMMGDeptModel> data;
    private Context mCon;
    LayoutInflater inflater;

    public AcceptMMGDeptAdapter(Context mCOn) {
        this.mCon = mCOn;
        data = new ArrayList<>();
        inflater = LayoutInflater.from(mCOn);
    }

    public void addList(List<AcceptMMGDeptModel> acceptMMGDeptModels) {
        if (data != null) {
            clear();

            data.addAll(acceptMMGDeptModels);
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
    public AcceptMMGDeptAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.row_accept_mmg_dept, viewGroup, false);
        return new AcceptMMGDeptAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AcceptMMGDeptAdapter.MyViewHolder holder, int i) {
        final AcceptMMGDeptModel current = data.get(i);

        String srNo = "" + current.getSrNo();

        holder.srNoTextView.setText(srNo);
        holder.dispatchDateTextView.setText(current.getDispatchDate());
        holder.meterCountTextView.setText(current.getMeterCount());
        holder.issuedToPersonTextView.setText(current.getIssuedToPerson());

        holder.row_linerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mCon, AcceptMMGMeterDetailsActivity.class);
                intent.putExtra("dispatchDate",current.getDispatchDate());
                intent.putExtra("meterCount",current.getMeterCount());
                intent.putExtra("issuedToPerson",current.getIssuedToPerson());
                mCon.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView srNoTextView, dispatchDateTextView, meterCountTextView, issuedToPersonTextView;
        LinearLayout row_linerLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            srNoTextView = itemView.findViewById(R.id.srNoTextView);
            dispatchDateTextView = itemView.findViewById(R.id.dispatchDateTextView);
            meterCountTextView = itemView.findViewById(R.id.meterCountTextView);
            issuedToPersonTextView = itemView.findViewById(R.id.issuedToPersonTextView);
            row_linerLayout = itemView.findViewById(R.id.row_linerLayout);
        }
    }
}
