package elink.suezShimla.water.crm.MeterManagementSystem.MMGReports.Adapter;

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

import java.util.List;

import elink.suezShimla.water.crm.MeterManagementSystem.MMGReports.Model.MMGZoneWardDetailModel;
import elink.suezShimla.water.crm.R;

public class MMGZoneWardDetailAdapter  extends RecyclerView.Adapter<MMGZoneWardDetailAdapter.MyViewHolder> {
    private Context mCon;

    private LayoutInflater inflater;
    List<String> movieList ;
    List<MMGZoneWardDetailModel> data ;

    public MMGZoneWardDetailAdapter(Context mCon, List<MMGZoneWardDetailModel> data) {
        this.mCon = mCon ;
        this.data = data;
        inflater = LayoutInflater.from(mCon);
    }
    /*public void addList(List<MMGZoneWardDetailModel> list) {
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
    }*/
    @NonNull
    @Override
    public MMGZoneWardDetailAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.mmg_zone_ward_adapter_layout, viewGroup, false);
        return new MMGZoneWardDetailAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MMGZoneWardDetailAdapter.MyViewHolder holder, int i) {
        //
       final MMGZoneWardDetailModel Current= data.get(i);


        holder.zoneTextView.setText(data.get(i).getZM_ZONE_ID());
        holder.wardTextView.setText(data.get(i).getDTE_ZONE());

        holder.mmgSubTypeTextView.setText(data.get(i).getCOM_TYPE());

        holder.previousPendingTextView.setText(data.get(i).getCOUNT_TOTALCOMPCOUNT());

      /*  holder.receivedTextView.setText(data.get(i).getCOUNT_RECEIVED());
        holder.totalTextView.setText(data.get(i).getCOUNT_TOTALCOMPCOUNT());*/


        holder.completedTotalTextView.setText(data.get(i).getCOUNT_TOTALCOMPCOUNT());
        holder.completedATextView.setText(data.get(i).getA());
        holder.completedBTextView.setText(data.get(i).getB());
        holder.completedCTextView.setText(data.get(i).getC());
        holder.completedDTextView.setText(data.get(i).getD());

        holder.pendingTotalTextView.setText(data.get(i).getCOUNT_TOTALCOMPCOUNT());
        holder.pendingATextView.setText(data.get(i).getPENDINGA());
        holder.pendingBTextView.setText(data.get(i).getPENDINGB());
        holder.pendingCTextView.setText(data.get(i).getPENDINGC());
        holder.pendingDTextView.setText(data.get(i).getPENDINGD());

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

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView zoneTextView, wardTextView, mmgSubTypeTextView, previousPendingTextView,  completedTotalTextView, completedATextView, completedBTextView, completedCTextView,
                completedDTextView, pendingTotalTextView, pendingATextView, pendingBTextView, pendingCTextView, pendingDTextView;
        AppCompatImageView arrowDownAppCompatImageView;
        CardView connectionTypeCardView;
        LinearLayout hideLinearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            zoneTextView = itemView.findViewById(R.id.zoneTextView);
            wardTextView = itemView.findViewById(R.id.wardTextView);
            mmgSubTypeTextView = itemView.findViewById(R.id.mmgSubTypeTextView);
            previousPendingTextView = itemView.findViewById(R.id.previousPendingTextView);
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
    @Override
    public int getItemCount() {
        return data.size();
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
