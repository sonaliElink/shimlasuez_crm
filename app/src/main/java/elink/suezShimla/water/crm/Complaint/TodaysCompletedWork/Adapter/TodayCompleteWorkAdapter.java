package elink.suezShimla.water.crm.Complaint.TodaysCompletedWork.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.Complaint.TodaysCompletedWork.Model.TodayWorkCompletionResponseModel;
import elink.suezShimla.water.crm.R;

public class TodayCompleteWorkAdapter extends RecyclerView.Adapter<TodayCompleteWorkAdapter.MyViewHolder> {

    private List<TodayWorkCompletionResponseModel> data;
    private Context mCon;
    private LayoutInflater inflater;
    public TodayCompleteWorkAdapter(Context mCon) {
        this.mCon = mCon;
        data = new ArrayList<>();
        inflater = LayoutInflater.from(mCon);
    }

    public void addList(List<TodayWorkCompletionResponseModel> todayCompleteWorkModelList) {
        if (data != null) {
            clear();

            data.addAll(todayCompleteWorkModelList);
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
    public TodayCompleteWorkAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.row_today_complete_work, viewGroup, false);

        return new TodayCompleteWorkAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodayCompleteWorkAdapter.MyViewHolder holder, int position) {
        String count = String.valueOf(position+1);

        final TodayWorkCompletionResponseModel current = data.get(position);
        holder.srNoTextView.setText(count);

        String mobileNo= current.getPHONENO().substring(0, current.getPHONENO().indexOf('.'));
        holder.complaintNumberTextView.setText(current.getCOMNO());
        holder.attendedByTextView.setText(current.getATTENDEDBY());
        holder.allocatedEmpTextView.setText(current.getCOM_EMP_ALLOCATE());
        holder.consumerNoTextView.setText(current.getSRM_SERVICE_NO());
        holder.complaintDateTimeTextView.setText(current.getCOM_COMPDATE());
        holder.complaintTypeTextView.setText(current.getCOMPTYPE());
        holder.complaintSubTypeTextView.setText(current.getCOMPSUBTYPE());
        holder.subZoneTextView.setText(current.getSUBZONE());
        holder.tariffTextView.setText(current.getTARIFF());
        holder.nameTextView.setText(current.getCONSUMERNAME());

        holder.numberTextView.setText(mobileNo);
        holder.addressTextView.setText(current.getADDRESS());
        holder.workAllocationDateTextView.setText(current.getCOM_ALLOCATIONDATE());
        holder.workCompletionDateTextView.setText(current.getCOM_WORKCOMPLETIONDATE());
        holder.statusTextView.setText(current.getCOM_STATUS());
        holder.remarkTextView.setText(current.getCOM_REMARKS());
        holder.vipTextView.setText(current.getVIP());
        String originStr = current.getORIGIN();
        if(originStr.equalsIgnoreCase("1")) {
            holder.originTextView.setText("Internal");
        }else {
            holder.originTextView.setText("External");

        }
       /* holder.workCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCon.startActivity(new Intent(v.getContext(), WorkComplaintLocationDetailActivity.class));
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView complaintNumberTextView, attendedByTextView, allocatedEmpTextView, consumerNoTextView, complaintDateTimeTextView,
                complaintTypeTextView, complaintSubTypeTextView, subZoneTextView, tariffTextView, nameTextView, numberTextView, addressTextView,
                workAllocationDateTextView, workCompletionDateTextView, statusTextView, remarkTextView,vipTextView,srNoTextView,originTextView;
        private CardView workCardView;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            complaintNumberTextView = itemView.findViewById(R.id.complaintNumberTextView);
            attendedByTextView = itemView.findViewById(R.id.attendedByTextView);
            allocatedEmpTextView = itemView.findViewById(R.id.allocatedEmpTextView);
            consumerNoTextView = itemView.findViewById(R.id.consumerNoTextView);
            complaintDateTimeTextView = itemView.findViewById(R.id.complaintDateTimeTextView);
            complaintTypeTextView = itemView.findViewById(R.id.complaintTypeTextView);
            complaintSubTypeTextView = itemView.findViewById(R.id.complaintSubTypeTextView);
            subZoneTextView = itemView.findViewById(R.id.subZoneTextView);
            tariffTextView = itemView.findViewById(R.id.tariffTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            numberTextView = itemView.findViewById(R.id.numberTextView);
            addressTextView = itemView.findViewById(R.id.addressTextView);
            workAllocationDateTextView = itemView.findViewById(R.id.workAllocationDateTextView);
            workCompletionDateTextView = itemView.findViewById(R.id.workCompletionDateTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            remarkTextView = itemView.findViewById(R.id.remarkTextView);
            workCardView = itemView.findViewById(R.id.workCardView);
            vipTextView = itemView.findViewById(R.id.vipTextView);
            srNoTextView = itemView.findViewById(R.id.srNoTextView);
            originTextView = itemView.findViewById(R.id.originTextView);

        }
    }
}
