package elink.suezShimla.water.crm.MeterManagementSystem.StoreManagement.IssueToMmgDept.Adapter;

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

import elink.suezShimla.water.crm.MeterManagementSystem.StoreManagement.IssueToMmgDept.Model.IssueToMmgDialougModel;
import elink.suezShimla.water.crm.R;

public class IssueToMmgDialougAdapter extends RecyclerView.Adapter<IssueToMmgDialougAdapter.MyViewHolder> {

    private Context mCOn;
    private LayoutInflater inflater;
    private List<IssueToMmgDialougModel> data;
    boolean isSelected=false;

    public IssueToMmgDialougAdapter(Context mCOn) {
        this.mCOn = mCOn;
        data = new ArrayList<>();
        inflater = LayoutInflater.from(mCOn);
    }

    public void addList(List<IssueToMmgDialougModel> issueToMmgDialougModelList) {
        if (data != null) {
            clear();

            data.addAll(issueToMmgDialougModelList);
            notifyDataSetChanged();
        }
    }

    public List<IssueToMmgDialougModel> getList() {
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
    public IssueToMmgDialougAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.issue_to_mmg_onclick_details, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final IssueToMmgDialougAdapter.MyViewHolder holder, int i) {
        final IssueToMmgDialougModel current = data.get(i);

        holder.srNoTextView.setText("Sr. No. :"+""+current.getSrNo());
        holder.meterNoTextView.setText("Meter No. :"+""+current.getMeterNo());
        holder.makeCodeTextView.setText(current.getMakeCode());
        holder.statusDateTextView.setText(current.getStatusDate());
        holder.checkbox.setChecked(current.isSelected());

        holder.dialougCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkbox.isChecked()){
                    holder.checkbox.setChecked(false);
                }else {

                    holder.checkbox.setChecked(true);
                    isSelected=true;
       IssueToMmgDialougModel model = new IssueToMmgDialougModel(current.getSrNo(),current.getMeterNo(),current.getMakeCode(),current.getStatusDate(),isSelected);
            data.add(model);

             //for(IssueToMmgDialougModel modelData:data){
            if(model.getMeterNo()==data.get(i).getMeterNo()){
                data.remove(i);
          //  }
        }
            notifyDataSetChanged();
        }
    }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView srNoTextView, meterNoTextView, makeCodeTextView, statusDateTextView;

        CheckBox checkbox;

        CardView dialougCardView;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            srNoTextView = itemView.findViewById(R.id.srNoTextView);
            meterNoTextView = itemView.findViewById(R.id.meterNoTextView);
            makeCodeTextView = itemView.findViewById(R.id.makeCodeTextView);
            statusDateTextView = itemView.findViewById(R.id.statusDateTextView);

            checkbox = itemView.findViewById(R.id.checkbox);
            dialougCardView = itemView.findViewById(R.id.dialougCardView);
        }
    }
}
