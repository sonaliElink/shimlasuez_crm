package elink.suezShimla.water.crm.ConnectionRequest.UploadDocuments.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.ConnectionRequest.UploadDocuments.model.SelectedDocumentModel;
import elink.suezShimla.water.crm.R;

public class UploadedDocumentAdapter extends RecyclerView.Adapter<UploadedDocumentAdapter.MyViewHolder> {

    private Context mCon;

    private List<SelectedDocumentModel> data;

    private LayoutInflater inflater;

    UploadedDocumentAdapter(Context mCon) {
        this.mCon = mCon;
        inflater = LayoutInflater.from(mCon);
        data = new ArrayList<>();
    }

    public void addList(List<SelectedDocumentModel> selectDocumentModelList) {
        if (data != null) {
            data.clear();
            notifyDataSetChanged();

            data.addAll(selectDocumentModelList);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.row_uploaded_doc, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final SelectedDocumentModel current = data.get(position);

        holder.nameTextView.setText(current.getProofName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.nameTextView);
        }
    }
}
