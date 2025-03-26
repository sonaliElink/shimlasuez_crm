package elink.suezShimla.water.crm.MeterManagementSystem.MeterInstallationReplacementPrint.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import elink.suezShimla.water.crm.MeterManagementSystem.MeterInstallationReplacementPrint.Model.MeterInstallationReplacementPrintModel;
import elink.suezShimla.water.crm.R;

public class MeterInstallationReplacementPrintAdapter extends RecyclerView.Adapter<MeterInstallationReplacementPrintAdapter.MyViewHolder> {
    private Context mCon;
    private LayoutInflater inflater;
    private List<MeterInstallationReplacementPrintModel> data;

    public MeterInstallationReplacementPrintAdapter(Context context){
        this.mCon = context;
        inflater = LayoutInflater.from(mCon);
        data = new ArrayList<>();
    }

    public void addList(List<MeterInstallationReplacementPrintModel> list){
        if(data != null){
            clear();
            data.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void clear(){
        if(data != null){
            data.clear();
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.row_meter_installation_replacement_print, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final MeterInstallationReplacementPrintModel current = data.get(position);

        holder.serviceNoTextView.setText(current.getServiceNo());
        holder.serviceTodTypeTextView.setText(current.getServiceTodType());
        holder.requestNoTextView.setText(current.getRequestNo());
        holder.requestTypeTextView.setText(current.getRequestType());
        holder.oldMeterNoTextView.setText(current.getOldMeterNo());
        holder.newMeterNoTextView.setText(current.getNewMeterNo());
        holder.oldMeterTypeTextView.setText(current.getOldMeterType());
        holder.newMeterTodTypeTextView.setText(current.getNewMeterTodType());
        holder.oldMeterReadingTextView.setText(current.getOldMeterReading());
        holder.newMeterInitReadingTextView.setText(current.getNewMeterInitReading());
        holder.phaseTextView.setText(current.getPhase());
        holder.meterIndicationTextView.setText(current.getMeterIndicator());
        holder.meterInstalledReplacementDateTextView.setText(current.getMeterInstalledReplacedDate());
        holder.meterInstalledReplacementTextView.setText(current.getMeterInstalledReplaced());
        holder.meterNotInstalledReplacementReasonTextView.setText(current.getMeterNotInstalledReplacedReason());

        holder.locationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=19.1963079&daddr=72.8042726"));
                mCon.startActivity(intent);
            }
        });

        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.linearMeter.getVisibility() == View.GONE){
                    expand(holder.linearMeter);
                }else {
                    collapse(holder.linearMeter);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        AppCompatImageView locationImageView;
        TextView serviceNoTextView, serviceTodTypeTextView, requestNoTextView, requestTypeTextView, oldMeterNoTextView, newMeterNoTextView, oldMeterTypeTextView,
                newMeterTodTypeTextView, oldMeterReadingTextView, newMeterInitReadingTextView, phaseTextView, meterIndicationTextView,
                meterInstalledReplacementDateTextView, meterInstalledReplacementTextView, meterNotInstalledReplacementReasonTextView;
        CardView cardview;
        LinearLayout linearMeter;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            locationImageView = itemView.findViewById(R.id.locationImageView);
            serviceNoTextView = itemView.findViewById(R.id.serviceNoTextView);
            serviceTodTypeTextView = itemView.findViewById(R.id.serviceTodTypeTextView);
            requestNoTextView = itemView.findViewById(R.id.requestNoTextView);
            requestTypeTextView = itemView.findViewById(R.id.requestTypeTextView);
            oldMeterNoTextView = itemView.findViewById(R.id.oldMeterNoTextView);
            newMeterNoTextView = itemView.findViewById(R.id.newMeterNoTextView);
            oldMeterTypeTextView = itemView.findViewById(R.id.oldMeterTypeTextView);
            newMeterTodTypeTextView = itemView.findViewById(R.id.newMeterTodTypeTextView);
            oldMeterReadingTextView = itemView.findViewById(R.id.oldMeterReadingTextView);
            newMeterInitReadingTextView = itemView.findViewById(R.id.newMeterInitReadingTextView);
            phaseTextView = itemView.findViewById(R.id.phaseTextView);
            meterIndicationTextView = itemView.findViewById(R.id.meterIndicationTextView);
            meterInstalledReplacementDateTextView = itemView.findViewById(R.id.meterInstalledReplacementDateTextView);
            meterInstalledReplacementTextView = itemView.findViewById(R.id.meterInstalledReplacementTextView);
            meterNotInstalledReplacementReasonTextView = itemView.findViewById(R.id.meterNotInstalledReplacementReasonTextView);
            cardview = itemView.findViewById(R.id.cardview);
            linearMeter = itemView.findViewById(R.id.linearMeter);
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
