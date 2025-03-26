package elink.suezShimla.water.crm.Notifications.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import elink.suezShimla.water.crm.R;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder>{

    private Context mCon;
    private LayoutInflater inflater;
    private String uri;

    public NotificationAdapter(Context mCon) {
        this.mCon = mCon;
        inflater = LayoutInflater.from(mCon);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.row_adapter_notification, viewGroup, false);
        return new NotificationAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView tv_notiTitle,tv_notiMessage,tv_notiDate;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_notiTitle = itemView.findViewById(R.id.tv_notiTitle);
            tv_notiMessage = itemView.findViewById(R.id.tv_notiMessage);
            tv_notiDate = itemView.findViewById(R.id.tv_notiDate);

        }
    }
}
