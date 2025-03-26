package elink.suezShimla.water.crm.ConnectionRequest.UploadDocuments.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.button.MaterialButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AbsoluteLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.ConnectionRequest.UploadDocuments.SelectDocument.SelectDocumentActivity;
import elink.suezShimla.water.crm.ConnectionRequest.UploadDocuments.model.UploadDocumentModel;
import elink.suezShimla.water.crm.R;

public class UploadDocumentAdapter extends RecyclerView.Adapter<UploadDocumentAdapter.MyViewHolder> {

    private List<UploadDocumentModel> data;

    private LayoutInflater inflater;

    private Context mCon;

    public UploadDocumentAdapter(Context mCon) {
        this.mCon = mCon;
        inflater = LayoutInflater.from(mCon);
        data = new ArrayList<>();
    }

    public void addList(List<UploadDocumentModel> uploadDocumentModelList) {
        if (data != null) {
            clear();

            data.addAll(uploadDocumentModelList);
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
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.row_document_type, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final UploadDocumentModel current = data.get(position);

        holder.proofOfDocumentNameTextView.setText(current.getProofName());
        holder.documentMsgTextView.setText(current.getProofDescription());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!current.getSelected()) {
                    expand(holder.proofExpandRelativeLayout);
                    holder.arrowDocumentImageView.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                    current.setSelected(true);
                } else {
                    collapse(holder.proofExpandRelativeLayout);
                    holder.arrowDocumentImageView.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                    current.setSelected(false);
                }

            }
        });

        holder.uploadedDocumentAdapter.addList(current.getSelectDocumentModelList());

        holder.uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCon.startActivity(new Intent(mCon, SelectDocumentActivity.class));
            }
        });
    }

    private static void expand(final View v) {
        v.measure(AbsoluteLayout.LayoutParams.MATCH_PARENT,
                AbsoluteLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for
        // views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime,
                                               Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? AbsoluteLayout.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (targetHeight /
                v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    private static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime,
                                               Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int)
                            (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (initialHeight /
                v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView proofOfDocumentNameTextView, documentMsgTextView;

        RecyclerView proofDocumentRecyclerView;

        MaterialButton uploadButton;

        RelativeLayout proofExpandRelativeLayout, relativeLayout;

        AppCompatImageView arrowDocumentImageView;

        UploadedDocumentAdapter uploadedDocumentAdapter;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            proofOfDocumentNameTextView = itemView.findViewById(R.id.proofOfDocumentNameTextView);
            documentMsgTextView = itemView.findViewById(R.id.documentMsgTextView);

            proofExpandRelativeLayout = itemView.findViewById(R.id.proofExpandRelativeLayout);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);

            proofDocumentRecyclerView = itemView.findViewById(R.id.proofDocumentRecyclerView);

            arrowDocumentImageView = itemView.findViewById(R.id.arrowDocumentImageView);

            uploadButton = itemView.findViewById(R.id.uploadButton);

            uploadedDocumentAdapter = new UploadedDocumentAdapter(mCon);
            proofDocumentRecyclerView.setHasFixedSize(true);
            proofDocumentRecyclerView.setLayoutManager(new LinearLayoutManager(mCon));
            proofDocumentRecyclerView.setAdapter(uploadedDocumentAdapter);
        }
    }
}
