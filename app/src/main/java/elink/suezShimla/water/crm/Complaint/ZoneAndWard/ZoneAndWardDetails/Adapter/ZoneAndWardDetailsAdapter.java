package elink.suezShimla.water.crm.Complaint.ZoneAndWard.ZoneAndWardDetails.Adapter;

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

import elink.suezShimla.water.crm.Complaint.ZoneAndWard.ZoneAndWardDetails.Model.ZoneAndWardDetailsModel;
import elink.suezShimla.water.crm.R;

public class ZoneAndWardDetailsAdapter extends RecyclerView.Adapter<ZoneAndWardDetailsAdapter.MyViewHolder> {
    private Context mCon;
    private List<ZoneAndWardDetailsModel> data;
    private LayoutInflater inflater;

    public ZoneAndWardDetailsAdapter(Context context) {
        this.mCon = context;
        data = new ArrayList<>();
        inflater = LayoutInflater.from(mCon);
    }

    public void addList(List<ZoneAndWardDetailsModel> list) {
        if (data != null) {
            clear();

            data.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        if (data != null) {
            data.clear();
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.row_zone_ward_details, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int i) {
        final ZoneAndWardDetailsModel current = data.get(i);

        holder.zoneTextView.setText(current.getDTE_ZONE());
        holder.wardTextView.setText(current.getDTE_PC());

        holder.complaintTypeTextView.setText(current.getDTE_REASON());

        holder.previousPendingTextView.setText(current.getCOUNT_TOTALCOMPCOUNT());

        holder.receivedTextView.setText(current.getCOUNT_RECEIVED());
        holder.totalTextView.setText(current.getCOUNT_TOTALCOMPCOUNT());


        holder.completedTotalTextView.setText(current.getCOUNT_COMPLETEDCOMP());
        holder.completedATextView.setText(current.getA());
        holder.completedBTextView.setText(current.getB());
        holder.completedCTextView.setText(current.getC());
        holder.completedDTextView.setText(current.getD());

        holder.pendingTotalTextView.setText(current.getCOUNT_PENDINGCOMP());
        holder.pendingATextView.setText(current.getPENDINGA());
        holder.pendingBTextView.setText(current.getPENDINGB());
        holder.pendingCTextView.setText(current.getPENDINGC());
        holder.pendingDTextView.setText(current.getPENDINGD());

        holder.connectionTypeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.hideLinearLayout.getVisibility() == View.GONE) {
                    expand(holder.hideLinearLayout);
                    holder.arrowDownAppCompatImageView.animate().rotation(180).start();
                } else {
                    collapse(holder.hideLinearLayout);
                    holder.arrowDownAppCompatImageView.animate().rotation(0).start();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView zoneTextView, wardTextView, complaintTypeTextView, previousPendingTextView, receivedTextView, totalTextView, completedTotalTextView, completedATextView, completedBTextView, completedCTextView,
                completedDTextView, pendingTotalTextView, pendingATextView, pendingBTextView, pendingCTextView, pendingDTextView;
        AppCompatImageView arrowDownAppCompatImageView;
        CardView connectionTypeCardView;
        LinearLayout hideLinearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            zoneTextView = itemView.findViewById(R.id.zoneTextView);
            wardTextView = itemView.findViewById(R.id.wardTextView);
            complaintTypeTextView = itemView.findViewById(R.id.complaintSubTypeTextView);
            previousPendingTextView = itemView.findViewById(R.id.previousPendingTextView);

            receivedTextView = itemView.findViewById(R.id.receivedTextView);
            totalTextView = itemView.findViewById(R.id.totalTextView);
            completedTotalTextView = itemView.findViewById(R.id.completedTotalTextView);
            completedATextView = itemView.findViewById(R.id.completedATextView);
            completedBTextView = itemView.findViewById(R.id.completedBTextView);
            completedCTextView = itemView.findViewById(R.id.completedCTextView);
            completedDTextView = itemView.findViewById(R.id.completedDTextView);
            pendingTotalTextView = itemView.findViewById(R.id.pendingTotalTextView);
            pendingATextView = itemView.findViewById(R.id.pendingATextView);
            pendingBTextView = itemView.findViewById(R.id.pendingBTextView);
            pendingCTextView = itemView.findViewById(R.id.pendingCTextView);
            pendingDTextView = itemView.findViewById(R.id.pendingDTextView);

            connectionTypeCardView = itemView.findViewById(R.id.connectionTypeCardView);

            arrowDownAppCompatImageView = itemView.findViewById(R.id.arrowDownAppCompatImageView);

            hideLinearLayout = itemView.findViewById(R.id.hideLinearLayout);
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
