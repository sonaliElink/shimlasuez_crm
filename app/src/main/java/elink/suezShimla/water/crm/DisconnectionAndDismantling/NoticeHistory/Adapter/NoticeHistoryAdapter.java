package elink.suezShimla.water.crm.DisconnectionAndDismantling.NoticeHistory.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.DisconnectionAndDismantling.NoticeHistory.Model.NoticeHistoryModel;
import elink.suezShimla.water.crm.R;

public class NoticeHistoryAdapter extends RecyclerView.Adapter<NoticeHistoryAdapter.MyViewHolder> {
    private Context mCon;
    private LayoutInflater inflater;
    private List<NoticeHistoryModel> data;

    public NoticeHistoryAdapter(Context context){
        this.mCon = context;
        inflater = LayoutInflater.from(mCon);
        data = new ArrayList<>();
    }

    public void addList(List<NoticeHistoryModel> list){
        if(data != null){
            clear();
            data.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void clear(){
        if(data != null){
            data.clear();
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.row_notice_history, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final NoticeHistoryModel current = data.get(position);

        holder.totalNoticeTextView.setText(current.getTotalNotice());
        holder.printingPendingNoticeTextView.setText(current.getPrintingPendingNotice());
        holder.totalNoticeCountTextView.setText(current.getTotalNoticeCount());
        holder.totalNoticeAmountTextView.setText(current.getTotalNoticeAmount());
        holder.noticeDateTextView.setText(current.getNoticeDate());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView totalNoticeTextView, printingPendingNoticeTextView, totalNoticeCountTextView, totalNoticeAmountTextView, noticeDateTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            totalNoticeTextView = itemView.findViewById(R.id.totalNoticeTextView);
            printingPendingNoticeTextView = itemView.findViewById(R.id.printingPendingNoticeTextView);
            totalNoticeCountTextView = itemView.findViewById(R.id.totalNoticeCountTextView);
            totalNoticeAmountTextView = itemView.findViewById(R.id.totalNoticeAmountTextView);
            noticeDateTextView = itemView.findViewById(R.id.noticeDateTextView);
        }
    }
}
