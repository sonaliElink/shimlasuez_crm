package elink.suezShimla.water.crm.Shantanu.RecyclerPackage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Shantanu.Collection.ReceiptWebviewFragment;
import elink.suezShimla.water.crm.Shantanu.ModelPackage.ReceiptHistoryModel;

public class ReceiptHistoryRecylerView  extends  RecyclerView.Adapter<ReceiptHistoryViewHolder>{

    Context context;
    ArrayList<ReceiptHistoryModel> receiptHistoryModelArrayList;
    RecyclerView recyclerReceptPymtHis;

    public ReceiptHistoryRecylerView(Context mCon, ArrayList<ReceiptHistoryModel> receiptHistoryModelArrayList, RecyclerView recyclerReceptPymtHis) {
        this.context=mCon;
        this.receiptHistoryModelArrayList=receiptHistoryModelArrayList;
        this.recyclerReceptPymtHis=recyclerReceptPymtHis;
    }

    @NonNull
    @Override
    public ReceiptHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReceiptHistoryViewHolder(LayoutInflater.from(context).inflate(R.layout.receipt_model,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiptHistoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(receiptHistoryModelArrayList.get(position).getRecptDate().equals(""))
            holder.txtRecptConsNo.setText(receiptHistoryModelArrayList.get(position).getConsNo());
        else
            holder.txtRecptConsNo.setText(receiptHistoryModelArrayList.get(position).getRecptDate());
        holder.txtRecptNo.setText(receiptHistoryModelArrayList.get(position).getRecptNo());
        holder.txtRecptAmnt.setText(receiptHistoryModelArrayList.get(position).getRecptAmnt());

        holder.imgRecptView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("Param", receiptHistoryModelArrayList.get(position).getRecptNo()+","+receiptHistoryModelArrayList.get(position).getConsNo()+",2,A,0,"+receiptHistoryModelArrayList.get(position).getBuId());
                ReceiptWebviewFragment rwf = new ReceiptWebviewFragment();
                rwf.setArguments(bundle);
                SetFragment(rwf);
            }
        });

    }

    public void SetFragment(Fragment fragment1) {
        if (fragment1 != null) {
            ((FragmentActivity) context).getSupportFragmentManager().
                    beginTransaction().replace(R.id.container, fragment1,null).
                    addToBackStack(null).commit();
        }
    }

    @Override
    public int getItemCount() {
        return receiptHistoryModelArrayList.size();
    }
}

class ReceiptHistoryViewHolder extends RecyclerView.ViewHolder{

    TextView txtRecptConsNo,txtRecptNo,txtRecptAmnt;
    ImageView imgRecptView;

    public ReceiptHistoryViewHolder(@NonNull View itemView) {
        super(itemView);
        txtRecptConsNo=itemView.findViewById(R.id.txtRecptConsNo);
        txtRecptNo=itemView.findViewById(R.id.txtRecptNo);
        txtRecptAmnt=itemView.findViewById(R.id.txtRecptAmnt);
        imgRecptView=itemView.findViewById(R.id.imgRecptView);
    }
}
