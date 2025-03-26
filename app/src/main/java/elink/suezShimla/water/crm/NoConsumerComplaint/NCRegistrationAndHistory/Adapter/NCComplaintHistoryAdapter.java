package elink.suezShimla.water.crm.NoConsumerComplaint.NCRegistrationAndHistory.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.NoConsumerComplaint.NCRegistrationAndHistory.Model.NCComplaintHistoryModel;
import elink.suezShimla.water.crm.R;

public class NCComplaintHistoryAdapter extends RecyclerView.Adapter<NCComplaintHistoryAdapter.MyViewHolder>{
    private Context mCon;
    private List<NCComplaintHistoryModel> data;
    LayoutInflater inflater;
    public NCComplaintHistoryAdapter(Context mCon) {
        this.mCon = mCon;
        inflater = LayoutInflater.from(mCon);
        data = new ArrayList<>();
    }

    public void addList(List<NCComplaintHistoryModel> nccomplaintHistoryModelList) {
        if (data != null) {
            clear();

            data.addAll(nccomplaintHistoryModelList);
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
    public NCComplaintHistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.row_complaint_history, viewGroup, false);
        return new NCComplaintHistoryAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NCComplaintHistoryAdapter.MyViewHolder holder, int position) {
        final NCComplaintHistoryModel current = data.get(position);

        holder.complaintNumberTextView.setText(current.getCOMNO());
        holder.repeatCallTextView.setText(current.getCOM_CALLS());
        holder.dateTimeTextView.setText(current.getCOM_COMPDATE());
        holder.complaintTypeTextView.setText(current.getCMTM_NAME());
        holder.reasonTextView.setText(current.getOCRM_NAME());
        holder.complaintByTextView.setText(current.getNAME());
        holder.workAllocationDateTimeTextView.setText(current.getCOM_ALLOCATIONDATE());
        holder.workCompletionDateTimeTextView.setText(current.getCOM_WORKCOMPLETIONDATE());
        holder.faultManTextView.setText(current.getCOM_INSPCD());
        holder.meterNoTextView.setText(current.getCOM_METER_REPLACE());
        holder.sectionTextView.setText(current.getCSCM_SECNAME());

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (holder.billLinearLayout.getVisibility() == View.GONE) {
                    expand(holder.billLinearLayout);
                    holder.arrowImageView.animate().rotation(180).start();
                } else {
                    collapse(holder.billLinearLayout);
                    holder.arrowImageView.animate().rotation(0).start();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView complaintNumberTextView, complaintTypeTextView, complaintByTextView, meterNoTextView, reasonTextView, workAllocationDateTimeTextView, workCompletionDateTimeTextView,
                faultManTextView, sectionTextView, repeatCallTextView, dateTimeTextView;
        private ImageView arrowImageView;
        private LinearLayout billLinearLayout, linear;
        private CardView card_view;

        public MyViewHolder(View itemView) {
            super(itemView);
            complaintNumberTextView = itemView.findViewById(R.id.complaintNumberTextView);
            complaintTypeTextView = itemView.findViewById(R.id.complaintSubTypeTextView);
            complaintByTextView = itemView.findViewById(R.id.complaintByTextView);
            meterNoTextView = itemView.findViewById(R.id.meterNoTextView);
            reasonTextView = itemView.findViewById(R.id.reasonTextView);
            workAllocationDateTimeTextView = itemView.findViewById(R.id.workAllocationDateTimeTextView);
            workCompletionDateTimeTextView = itemView.findViewById(R.id.workCompletionDateTimeTextView);
            faultManTextView = itemView.findViewById(R.id.faultManTextView);
            sectionTextView = itemView.findViewById(R.id.sectionTextView);
            repeatCallTextView = itemView.findViewById(R.id.repeatCallTextView);
            dateTimeTextView = itemView.findViewById(R.id.dateTimeTextView);
            arrowImageView = itemView.findViewById(R.id.arrowImageView);
            billLinearLayout = itemView.findViewById(R.id.billLinearLayout);
            linear = itemView.findViewById(R.id.linear);
            card_view = itemView.findViewById(R.id.card_view);
        }
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
}
