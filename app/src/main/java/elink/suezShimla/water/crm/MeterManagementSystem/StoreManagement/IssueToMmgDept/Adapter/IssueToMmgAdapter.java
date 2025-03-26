package elink.suezShimla.water.crm.MeterManagementSystem.StoreManagement.IssueToMmgDept.Adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.MeterManagementSystem.StoreManagement.IssueToMmgDept.IssueToMmgDialoug;
import elink.suezShimla.water.crm.MeterManagementSystem.StoreManagement.IssueToMmgDept.Model.IssueToMmgModel;
import elink.suezShimla.water.crm.R;

public class IssueToMmgAdapter extends RecyclerView.Adapter<IssueToMmgAdapter.MyViewHolder> {

    private Context mCOn;
    private LayoutInflater inflater;
    private List<IssueToMmgModel> data;

    public IssueToMmgAdapter(Context mCOn) {
        this.mCOn = mCOn;
        data = new ArrayList<>();
        inflater = LayoutInflater.from(mCOn);
    }

    public void addList(List<IssueToMmgModel> issueToMmgModelList) {
        if (data != null) {
            clear();

            data.addAll(issueToMmgModelList);
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
    public IssueToMmgAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.row_issue_to_mmg_dept, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IssueToMmgAdapter.MyViewHolder holder, int i) {
        final IssueToMmgModel current = data.get(i);

        holder.srNoTextView.setText(current.getSrNo());
        holder.authDateTextView.setText(current.getAuthDate());
        holder.availableStockTextView.setText(String.valueOf(current.getAvailableStock()) );
        holder.imgCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mCOn, IssueToMmgDialoug.class);
                mCOn.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView srNoTextView, authDateTextView, availableStockTextView;

        MaterialCardView imgCardView;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            srNoTextView = itemView.findViewById(R.id.srNoTextView);
            authDateTextView = itemView.findViewById(R.id.authDateTextView);
            availableStockTextView = itemView.findViewById(R.id.availableStockTextView);

            imgCardView = itemView.findViewById(R.id.imgcardView);
        }
    }
}
