package elink.suezShimla.water.crm.DisconnectionAndDismantling.CaptureReason.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.DisconnectionAndDismantling.CaptureReason.Model.CaptureReasonModel;
import elink.suezShimla.water.crm.R;

public class CaptureReasonAdapter extends RecyclerView.Adapter<CaptureReasonAdapter.MyViewHolder> {

    private Context mCon;
    private List<CaptureReasonModel> data;
    LayoutInflater inflater;

    public CaptureReasonAdapter(Context mCon) {
        this.mCon = mCon;
        inflater = LayoutInflater.from(mCon);
        data = new ArrayList<>();
    }


    public void addList(List<CaptureReasonModel> captureReasonModels) {
        if (data != null) {
            clear();
            data.addAll(captureReasonModels);
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
    public CaptureReasonAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.row_capture_reason, viewGroup, false);
        return new CaptureReasonAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CaptureReasonAdapter.MyViewHolder holder, int position) {
        final CaptureReasonModel current = data.get(position);

        holder.complaintNumberTextView.setText(current.getComplaintNumber());
        holder.NameTextView.setText(current.getName());
        holder.NumberTextView.setText(current.getNumber());
        holder.AddressTextView.setText(current.getAddress());

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDialog dialog = new MaterialDialog.Builder(mCon)
                        .customView(R.layout.capture_reason_dialouge,true)
                        .title(current.getComplaintNumber())
                        .contentColor(mCon.getResources().getColor(R.color.colorPrimary))
                        .canceledOnTouchOutside(false)
                        .positiveText(R.string.submit)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .negativeText("Cancel")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });

       /* holder.locationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=19.1963079&daddr=72.8042726"));
                mCon.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView complaintNumberTextView, NameTextView, NumberTextView, AddressTextView;
        private CardView card_view;
        private ImageView locationImageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            complaintNumberTextView = itemView.findViewById(R.id.complaintNumberTextView);
            NameTextView = itemView.findViewById(R.id.NameTextView);
            NumberTextView = itemView.findViewById(R.id.NumberTextView);
            AddressTextView = itemView.findViewById(R.id.AddressTextView);
            card_view = itemView.findViewById(R.id.card_view);
            locationImageView = itemView.findViewById(R.id.locationImageView);
        }
    }
}
