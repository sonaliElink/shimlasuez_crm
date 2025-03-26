package elink.suezShimla.water.crm.NoConsumerComplaint.NCReallocation.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.Login.MasterData.Tables.SiteEngineerModel;
import elink.suezShimla.water.crm.R;

public class NCAssignedEmployeeAdapter extends RecyclerView.Adapter<NCAssignedEmployeeAdapter.MyViewHolder>{
    private Context mCon;
    private List<SiteEngineerModel> data;
    private LayoutInflater inflater;
    private RealmOperations realmOperations;

    public NCAssignedEmployeeAdapter(Context mCon) {
        this.mCon = mCon;
        inflater = LayoutInflater.from(mCon);
        data = new ArrayList<>();
    }

    public void addList(List<SiteEngineerModel> siteEngineerModels) {
        if (data != null) {
            clear();
            data.addAll(siteEngineerModels);
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
    public NCAssignedEmployeeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.row_assigned_employee, viewGroup, false);
        realmOperations = new RealmOperations(mCon);
        return new NCAssignedEmployeeAdapter.MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull NCAssignedEmployeeAdapter.MyViewHolder holder, int position) {
        final SiteEngineerModel current = data.get(position);

        holder.employeeNameCheckBox.setText(current.getEMPLOYEE_NAME());
        holder.employeeCodeTextView.setText(current.getEMPLOYEE_CODE());

        holder.employeeNameCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    realmOperations.setEngineerCheck(current.getEMPLOYEE_CODE(), true);
                } else {
                    realmOperations.setEngineerCheck(current.getEMPLOYEE_CODE(), false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private AppCompatCheckBox employeeNameCheckBox;
        private TextView employeeCodeTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            employeeNameCheckBox = itemView.findViewById(R.id.employeeNameCheckBox);
            employeeCodeTextView = itemView.findViewById(R.id.employeeCodeTextView);
        }
    }
}
