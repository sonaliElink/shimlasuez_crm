package elink.suezShimla.water.crm.MeterManagementSystem.IssueMeterToFixer.Adapter;

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

import elink.suezShimla.water.crm.MeterManagementSystem.IssueMeterToFixer.Activity.IssueMeterToFixerDetailedActivity;
import elink.suezShimla.water.crm.MeterManagementSystem.IssueMeterToFixer.Model.IssueMeterFixerModel;
import elink.suezShimla.water.crm.R;

public class IssueMeterToFixerAdapter extends RecyclerView.Adapter<IssueMeterToFixerAdapter.MyViewHolder> {

    private List<IssueMeterFixerModel> data;

    private LayoutInflater inflater;

    private Context mCon;

    public IssueMeterToFixerAdapter(Context mCon) {
        this.mCon = mCon;
        inflater = LayoutInflater.from(mCon);
        data = new ArrayList<>();
    }

    public void addList(List<IssueMeterFixerModel> issueMeterFixerModelList) {
        if (data != null) {
            clear();

            data.addAll(issueMeterFixerModelList);
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
    public IssueMeterToFixerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.row_issue_meter_fixer, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final IssueMeterFixerModel current = data.get(position);

        holder.requestNoTextView.setText(current.getREF_NO());
        holder.consumerNoTextView.setText(current.getSERVICENO());
        holder.nameAddressTextView.setText(current.getNAME_ADDRESS());
        holder.contactNoTextView.setText(current.getSRM_S_MOBILE_NO());

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(mCon, IssueMeterToFixerDetailedActivity.class);
                intent.putExtra("zone",current.getBU());
                intent.putExtra("subZone",current.getPCNAME());
                intent.putExtra("consumerNo",current.getSERVICENO());
                intent.putExtra("reqNo",current.getREF_NO());
                intent.putExtra("contactNo",current.getSRM_S_MOBILE_NO());
                intent.putExtra("nameAdd",current.getNAME_ADDRESS());
                intent.putExtra("oldMtrNo",current.getOLDMETERNO());
                intent.putExtra("reqSubType",current.getSUBTYPE());
                intent.putExtra("fwdmmgDt",current.getMRT_REQUESTDATE());
                intent.putExtra("rejRes",current.getREJ_RES());
                intent.putExtra("observ",current.getOBS());
                intent.putExtra("oldMeterNumber",current.getOLDMETERNO());

                intent.putExtra("sourceType",current.getSOURCETYPE());
                intent.putExtra("bu",current.getBUM_BU_ID());
                intent.putExtra("pc",current.getPC());
                intent.putExtra("orm",current.getOCRM_ID());

                mCon.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView requestNoTextView, consumerNoTextView, nameAddressTextView, contactNoTextView;
        CardView card_view;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            requestNoTextView = itemView.findViewById(R.id.requestNoTextView);
            consumerNoTextView = itemView.findViewById(R.id.consumerNoTextView);
            nameAddressTextView = itemView.findViewById(R.id.nameAddressTextView);
            contactNoTextView = itemView.findViewById(R.id.contactNoTextView);
            card_view = itemView.findViewById(R.id.card_view);
        }
    }
}
