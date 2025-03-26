package elink.suezShimla.water.crm.ConnectionRequest.UploadDocuments.SelectDocument.adapter;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.ConnectionRequest.UploadDocuments.model.SelectedDocumentModel;
import elink.suezShimla.water.crm.R;

public class SelectDocumentAdapter extends RecyclerView.Adapter<SelectDocumentAdapter.MyViewHolder> {

    private Context mCon;

    private LayoutInflater inflater;

    private List<SelectedDocumentModel> data;

    public SelectDocumentAdapter(Context mCon) {
        this.mCon = mCon;
        inflater = LayoutInflater.from(mCon);
        data = new ArrayList<>();
    }

    public void addItem(SelectedDocumentModel selectedDocumentModel) {
        data.add(selectedDocumentModel);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.row_selected_doc, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final SelectedDocumentModel current = data.get(position);

        //  Log.d("check", "current = " + current);
        if (current.getDocType().equals("pdf")) {
            holder.imageRelativeLayout.setVisibility(View.GONE);
            holder.pdfRelativeLayout.setVisibility(View.VISIBLE);

            holder.pdfNameTextView.setText(current.getProofName());
        } else {
            holder.imageRelativeLayout.setVisibility(View.VISIBLE);
            holder.pdfRelativeLayout.setVisibility(View.GONE);

            holder.nameTextView.setText(current.getProofName());
            // holder.docImageView.setImageBitmap(current.getBitmap());

            ((Activity) mCon).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    holder.docImageView.setImageBitmap(current.getBitmap());
                }
            });

        }

        holder.deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout imageRelativeLayout, pdfRelativeLayout;
        AppCompatImageView docImageView, deletePdfImageView, deleteImageView;
        TextView nameTextView, pdfNameTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageRelativeLayout = itemView.findViewById(R.id.imageRelativeLayout);
            pdfRelativeLayout = itemView.findViewById(R.id.pdfRelativeLayout);
            docImageView = itemView.findViewById(R.id.docImageView);
            deletePdfImageView = itemView.findViewById(R.id.deletePdfImageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            deleteImageView = itemView.findViewById(R.id.deleteImageView);
            pdfNameTextView = itemView.findViewById(R.id.pdfNameTextView);
        }
    }
}
