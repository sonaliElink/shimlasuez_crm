package elink.suezShimla.water.crm.Shantanu.RecyclerPackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Shantanu.Collection.AllDetailsFragment;
import elink.suezShimla.water.crm.Shantanu.ModelPackage.SearchConsItemModel;

public class SearchConsRecyclerView extends RecyclerView.Adapter<SearchConsViewHolder> {

    Context context;
    ArrayList<SearchConsItemModel> modelArrayList;
    RecyclerView recylcerSearchCons;

    public SearchConsRecyclerView(Context mCon, ArrayList<SearchConsItemModel> modelArrayList, RecyclerView recylcerSearchCons) {
        this.context=mCon;
        this.modelArrayList=modelArrayList;
        this.recylcerSearchCons=recylcerSearchCons;
    }

    @NonNull
    @Override
    public SearchConsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchConsViewHolder(LayoutInflater.from(context).inflate(R.layout.show_consumer_model,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchConsViewHolder holder, int position) {
        holder.consNo.setText(modelArrayList.get(position).getConsNo());
        holder.oldConsNo.setText(modelArrayList.get(position).getOldconsNo());
        holder.consName.setText(modelArrayList.get(position).getConsName());
        holder.consMobile.setText(modelArrayList.get(position).getConsMobile());

        holder.payConsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetFragment(new AllDetailsFragment());
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public void SetFragment(Fragment fragment1) {
        if (fragment1 != null) {
            ((FragmentActivity) context).getSupportFragmentManager().
                    beginTransaction().replace(R.id.container, fragment1,null).
                    addToBackStack(null).commit();
        }
    }

}

class SearchConsViewHolder extends RecyclerView.ViewHolder{

   ConstraintLayout payConsLayout;
    TextView consNo,oldConsNo,consName,consMobile;

    public SearchConsViewHolder(@NonNull View itemView) {
        super(itemView);
        payConsLayout=itemView.findViewById(R.id.payConsLayout);
        consNo=itemView.findViewById(R.id.consNo);
        oldConsNo=itemView.findViewById(R.id.oldConsNo);
        consName=itemView.findViewById(R.id.consName);
        consMobile=itemView.findViewById(R.id.consMobile);
    }
}