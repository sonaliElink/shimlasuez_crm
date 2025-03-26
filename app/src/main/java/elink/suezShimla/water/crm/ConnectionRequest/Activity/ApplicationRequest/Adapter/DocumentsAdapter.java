package elink.suezShimla.water.crm.ConnectionRequest.Activity.ApplicationRequest.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.ConnectionRequest.Activity.ApplicationRequest.Model.DocumentsModel;
import elink.suezShimla.water.crm.R;

public class DocumentsAdapter extends RecyclerView.Adapter<DocumentsAdapter.MyViewHolder> {

    private List<DocumentsModel> data;
    private Context mCon;
    private LayoutInflater inflater;

    public DocumentsAdapter(Context mCon) {
        this.mCon = mCon;
        data = new ArrayList<>();
        inflater = LayoutInflater.from(mCon);
    }

    public void addList(List<DocumentsModel> documentsModels) {
        if (data != null) {
            clear();

            data.addAll(documentsModels);
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
    public DocumentsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.row_documents, viewGroup, false);

        return new DocumentsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DocumentsAdapter.MyViewHolder holder, int position) {
        final DocumentsModel current = data.get(position);

        holder.proofTextView.setText(current.getProofs());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView proofTextView;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            proofTextView = itemView.findViewById(R.id.proofTextView);
        }
    }
}
