package elink.suezShimla.water.crm.NoConsumerComplaint.NCTodaysCompleteWork.Adapter;

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

import elink.suezShimla.water.crm.NoConsumerComplaint.NCTodaysCompleteWork.Model.NCTodayWorkCompletionResponseModel;
import elink.suezShimla.water.crm.R;

public class NCTodayWorkCompleteAdapter extends RecyclerView.Adapter<NCTodayWorkCompleteAdapter.MyViewHolder>{

    private List<NCTodayWorkCompletionResponseModel> data;

    private Context mCon;

    private LayoutInflater inflater;
    public NCTodayWorkCompleteAdapter(Context mCon) {
        this.mCon = mCon;
        data = new ArrayList<>();
        inflater = LayoutInflater.from(mCon);
    }

    public void addList(List<NCTodayWorkCompletionResponseModel> nctodayCompleteWorkModelList) {
        if (data != null) {
            clear();

            data.addAll(nctodayCompleteWorkModelList);
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
    public NCTodayWorkCompleteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.row_nc_todays_workcomplete, viewGroup, false);

        return new NCTodayWorkCompleteAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NCTodayWorkCompleteAdapter.MyViewHolder holder, int position) {
        final NCTodayWorkCompletionResponseModel current = data.get(position);

        holder.complaintNumberTextView.setText(current.getCOMNO());
        holder.attendedByTextView.setText(current.getATTENDEDBY());
        holder.complaintDateTimeTextView.setText(current.getCOM_COMPDATE());
        holder.complaintTypeTextView.setText(current.getCOMPTYPE());
        holder.complaintSubTypeTextView.setText(current.getCOMPSUBTYPE());
        holder.subZoneTextView.setText(current.getSUBZONE());
        holder.workAllocationDateTextView.setText(current.getCOM_ALLOCATIONDATE());
        holder.workCompletionDateTextView.setText(current.getCOM_WORKCOMPLETIONDATE());
        holder.remarkTextView.setText(current.getCOM_REMARKS());
        holder.allocatedEmpTextView.setText(current.getFLM_ALOCTO());


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

        private TextView complaintNumberTextView, attendedByTextView, allocatedEmpTextView, complaintDateTimeTextView,
                complaintTypeTextView, complaintSubTypeTextView, subZoneTextView,
                workAllocationDateTextView, workCompletionDateTextView, remarkTextView;
        private CardView workCardView;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            complaintNumberTextView = itemView.findViewById(R.id.complaintNumberTextView);
            attendedByTextView = itemView.findViewById(R.id.attendedByTextView);
            allocatedEmpTextView = itemView.findViewById(R.id.allocatedEmpTextView);
            complaintDateTimeTextView = itemView.findViewById(R.id.complaintDateTimeTextView);
            complaintNumberTextView = itemView.findViewById(R.id.complaintNumberTextView);
            complaintTypeTextView = itemView.findViewById(R.id.complaintTypeTextView);
            complaintSubTypeTextView = itemView.findViewById(R.id.complaintSubTypeTextView);
            subZoneTextView = itemView.findViewById(R.id.subZoneTextView);
            workAllocationDateTextView = itemView.findViewById(R.id.workAllocationDateTextView);
            workCompletionDateTextView = itemView.findViewById(R.id.workCompletionDateTextView);
            remarkTextView = itemView.findViewById(R.id.remarkTextView);
            workCardView = itemView.findViewById(R.id.workCardView);
        }
    }
}
