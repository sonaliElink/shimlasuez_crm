package elink.suezShimla.water.crm.ConnectionRequest.Activity.TechnicalFeasibilityPlumberWork.Adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.Login.MasterData.Tables.SiteEngineerModel;
import elink.suezShimla.water.crm.R;

public class SetEmployeeAdapter extends RecyclerView.Adapter<SetEmployeeAdapter.MyViewHolder> {

    private List<SiteEngineerModel> data;
    private Context mCon;
    LayoutInflater inflater;

    public SetEmployeeAdapter(Context mCOn) {

        this.mCon = mCOn;
        data = new ArrayList<>();
        inflater = LayoutInflater.from(mCOn);
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
    public SetEmployeeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.row_select_employee, viewGroup, false);
        return new SetEmployeeAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SetEmployeeAdapter.MyViewHolder holder, int i) {
        final SiteEngineerModel current = data.get(i);

        String employeeCodeStr = current.getEMPLOYEE_CODE();
        String employeeNameStr = current.getEMPLOYEE_NAME();

        String setCodeAndName = employeeCodeStr + "-" + employeeNameStr;

        holder.employeeCodeAndName.setText(setCodeAndName);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView employeeCodeAndName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            employeeCodeAndName = itemView.findViewById(R.id.employeeCodeAndName);
        }
    }
}
