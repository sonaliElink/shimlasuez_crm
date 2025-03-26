package elink.suezShimla.water.crm.ConnectionRequest.Activity.ApplicationRequest.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AbsoluteLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.ConnectionRequest.Activity.ApplicationRequest.Model.ApplicationStatusModel;
import elink.suezShimla.water.crm.R;

public class ApplicationStatusAdapter extends RecyclerView.Adapter<ApplicationStatusAdapter.MyViewHolder> {

    private List<ApplicationStatusModel> data;
    private Context mCOn;
    LayoutInflater inflater;

    public ApplicationStatusAdapter(Context mCOn) {

        this.mCOn = mCOn;
        data = new ArrayList<>();
        inflater = LayoutInflater.from(mCOn);
    }

    public void addList(List<ApplicationStatusModel> applicationStatusModels) {
        if (data != null) {
            clear();

            data.addAll(applicationStatusModels);
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
    public ApplicationStatusAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.row_application_status, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ApplicationStatusAdapter.MyViewHolder holder, int i) {
        final ApplicationStatusModel current = data.get(i);

        holder.applicationNumberTextView.setText(current.getComplaintNo());
        holder.applicationTextView.setText(current.getApplicationType());
        holder.applicationDateTextView.setText(current.getDate());
        holder.applicantNameTextView.setText(current.getApplicantName());
        holder.statusTextView.setText(current.getStatus());
        holder.billingTextView.setText(current.getBillingAddress());
        holder.siteAddTextView.setText(current.getSiteAddress());
        holder.contactNoTextView.setText(current.getContactNo());
        holder.emailTimeTextView.setText(current.getEmailId());
        holder.meetingDateTextView.setText(current.getMeetingDate());
        holder.sourceTypeTextView.setText(current.getSourceType());
        holder.govtEmpTextView.setText(current.getGovtEmp());
        holder.remarkTextView.setText(current.getRemark());
        holder.statusDateTextView.setText(current.getStatusDate());

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

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView applicationNumberTextView, applicationTextView, applicationDateTextView, applicantNameTextView, statusTextView, billingTextView, siteAddTextView, contactNoTextView,
                emailTimeTextView, meetingDateTextView, sourceTypeTextView, govtEmpTextView, remarkTextView, statusDateTextView;
        CardView card_view;
        LinearLayout billLinearLayout;
        AppCompatImageView arrowImageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            applicationNumberTextView = itemView.findViewById(R.id.applicationNumberTextView);
            applicationTextView = itemView.findViewById(R.id.applicationTextView);
            applicationDateTextView = itemView.findViewById(R.id.applicationDateTextView);
            applicantNameTextView = itemView.findViewById(R.id.applicantNameTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            billingTextView = itemView.findViewById(R.id.billingTextView);
            siteAddTextView = itemView.findViewById(R.id.siteAddTextView);
            contactNoTextView = itemView.findViewById(R.id.contactNoTextView);
            emailTimeTextView = itemView.findViewById(R.id.emailTimeTextView);
            meetingDateTextView = itemView.findViewById(R.id.meetingDateTextView);
            sourceTypeTextView = itemView.findViewById(R.id.sourceTypeTextView);
            govtEmpTextView = itemView.findViewById(R.id.govtEmpTextView);
            remarkTextView = itemView.findViewById(R.id.remarkTextView);
            statusDateTextView = itemView.findViewById(R.id.statusDateTextView);
            arrowImageView = itemView.findViewById(R.id.arrowImageView);
            card_view = itemView.findViewById(R.id.card_view);
            billLinearLayout = itemView.findViewById(R.id.billLinearLayout);
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
