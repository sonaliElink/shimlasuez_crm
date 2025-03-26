package elink.suezShimla.water.crm.DisconnectionAndDismantling.ReConnectionWorkClosing.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.DisconnectionAndDismantling.ReConnectionWorkClosing.Model.ReConnectionWorkClosingModel;

public class ReConnectionWorkClosingAdapter extends RecyclerView.Adapter<ReConnectionWorkClosingAdapter.MyViewHolder> {
    private Context mCon;
    private LayoutInflater inflater;
    private List<ReConnectionWorkClosingModel> data;

    public ReConnectionWorkClosingAdapter(Context context){
        this.mCon = context;
        inflater = LayoutInflater.from(mCon);
        data = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
