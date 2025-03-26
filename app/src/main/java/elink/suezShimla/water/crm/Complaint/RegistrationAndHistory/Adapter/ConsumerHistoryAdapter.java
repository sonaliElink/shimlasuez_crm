package elink.suezShimla.water.crm.Complaint.RegistrationAndHistory.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.Complaint.RegistrationAndHistory.Activity.ActivityTrackingHistory;
import elink.suezShimla.water.crm.Complaint.RegistrationAndHistory.Model.HistoryModel;
import elink.suezShimla.water.crm.R;

public class ConsumerHistoryAdapter extends RecyclerView.Adapter<ConsumerHistoryAdapter.MyViewHolder> {

    private Context mCon;
    private List<HistoryModel> historyModelList=new ArrayList<>();
    private LayoutInflater inflater;
    private String uri;
    double locationLati,locationLongi;
    String consumerNumber="";

    public ConsumerHistoryAdapter(Context mCon,List<HistoryModel> historyModelList,String consumerNumber) {
        this.mCon = mCon;
        inflater = LayoutInflater.from(mCon);
        this.historyModelList = historyModelList;
        this.consumerNumber = consumerNumber;
    }





    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.row_consumer_history, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String count = String.valueOf(position+1);
       final HistoryModel current = historyModelList.get(position);
        holder.srNoTextView.setText(count);
        holder.complaintNumberTextView.setText(current.getREFNO());

        holder.originTextView.setText(current.getORIGIN());
        holder.dateTimeTextView.setText(current.getAPPLICATIONDATE());

        holder.typeTextView.setText(current.getTYPENAME());
        holder.sourceTypeTextView.setText(current.getAPPSOURCE());

        holder.categoryTypeTextView.setText(current.getCATEGORY());
        holder.categorySubTypeTextView.setText(current.getSUBCATEGORY());

        holder.lastActionTextView.setText(current.getOPENCLOSED());
        holder.statusTextView.setText(current.getSTATUS());

        holder.slaStatusTextView.setText(current.getSLA());
        holder.agingTextView.setText(current.getAGING());

        holder.ll_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), ActivityTrackingHistory.class);

                intent.putExtra("tag","C");
                intent.putExtra("refNumber",current.getREFNO());

                v.getContext().startActivity(intent);
                ((Activity)v.getContext()).finish();

            }

        });
        holder.bind(current);
        holder.expandImageView.setOnClickListener(v -> {
            boolean expanded = current.isExpanded();
            current.setExpanded(!expanded);
            notifyItemChanged(position);
        });

    }

    @Override
    public int getItemCount() {
        return historyModelList.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView complaintNumberTextView, originTextView, dateTimeTextView, typeTextView, sourceTypeTextView, categoryTypeTextView, categorySubTypeTextView,
                lastActionTextView,statusTextView,srNoTextView,slaStatusTextView,agingTextView;
        private CardView card_view;
        private LinearLayout ll_history;
        private  Button btnTracking;
        private ImageView locationImageView,expandImageView;
        private View subItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            complaintNumberTextView = itemView.findViewById(R.id.complaintNumberTextView);
            originTextView = itemView.findViewById(R.id.originTextView);
            dateTimeTextView = itemView.findViewById(R.id.dateTimeTextView);
            typeTextView = itemView.findViewById(R.id.typeTextView);
            sourceTypeTextView = itemView.findViewById(R.id.sourceTypeTextView);
            categoryTypeTextView = itemView.findViewById(R.id.categoryTypeTextView);
            categorySubTypeTextView = itemView.findViewById(R.id.categorySubTypeTextView);
            lastActionTextView = itemView.findViewById(R.id.lastActionTextView);
            card_view = itemView.findViewById(R.id.card_view);
            locationImageView = itemView.findViewById(R.id.locationImageView);
            ll_history = itemView.findViewById(R.id.ll_history);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            srNoTextView = itemView.findViewById(R.id.srNoTextView);
            slaStatusTextView = itemView.findViewById(R.id.slaStatusTextView);
            agingTextView = itemView.findViewById(R.id.agingTextView);
            expandImageView = itemView.findViewById(R.id.expandImageView);
           // btnTracking = itemView.findViewById(R.id.btnTracking);
            subItem = itemView.findViewById(R.id.linear_expand);

        }

        public void bind(HistoryModel current) {
            boolean expanded = current.isExpanded();

            subItem.setVisibility(expanded ? View.VISIBLE : View.GONE);

        }
    }
}
