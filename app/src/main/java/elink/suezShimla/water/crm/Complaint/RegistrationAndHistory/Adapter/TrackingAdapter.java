package elink.suezShimla.water.crm.Complaint.RegistrationAndHistory.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.Complaint.RegistrationAndHistory.Model.TractingModel;
import elink.suezShimla.water.crm.R;

public class TrackingAdapter extends RecyclerView.Adapter<TrackingAdapter.MyViewHolder> {

    private Context mCon;
    private List<TractingModel> data;
    private LayoutInflater inflater;
    private String uri;

    public TrackingAdapter(Context mCon) {
        this.mCon = mCon;
        inflater = LayoutInflater.from(mCon);
        data = new ArrayList<>();
    }

    public void addList(List<TractingModel> tractingModels) {
        if (data != null) {
            clear();
            data.addAll(tractingModels);
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
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.row_tracking, viewGroup, false);
        return new TrackingAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String count = String.valueOf(position+1);
        final TractingModel current = data.get(position);
        holder.srNoTextView.setText(count);

        holder.complaintNumberTextView.setText(current.getFCOMNO());
        holder.typeTextView.setText(current.getTYPE());
        holder.consumerNoTextView.setText(current.getCMM_SERVICE_NO());
        holder.dateTimeTextView.setText(current.getCMM_PROCDT());
        holder.sourceTypeTextView.setText(current.getCSM_SOURCEDESC());
        holder.complaintSubTypeTextView.setText(current.getREASON());
        holder.raisedByTextView.setText(current.getCMM_COMPBY());
        holder.repeatCallTextView.setText(current.getREPEATCALL());
        holder.deptTextView.setText(current.getCSCM_SECNAME());
        holder.agingTextView.setText(current.getAGING());
        holder.processByTextView.setText(current.getEMP());
        holder.statusTextView.setText(current.getCMM_STATUS());

       /*
    "{
    "TYPE": "C",
    "FCOMNO": "MC1210200021",
    "CMM_SERVICE_NO": "5567259",
    "CMM_COMPBY": "P.KATHIRVELU   ",
    "OCRM_NAME": null,
    "CMM_PROCDT": "2021-02-11T15:45:46",
    "CSCM_SECNAME": "Meter Reading Management",
    "CMM_REMARKS": null,
    "CMM_STATUS": "New Seal Connected",
    "EMP": "WATER STAGING",
    "CMM_PROCCODE": "3111",
    "REPEATCALL": "0",
    "REASON": "Meter Seal Installation / Replacement",
    "CMM_COMNO_SEQUENCE": "11022121",
    "COM_NO_TYPE": "M",
    "SUBTYPEID": "44",
    "COMPBY": "P.KATHIRVELU  P.KATHIRVELU  ",
    "CSM_SOURCEDESC": "Suez Employee",
    "AGING": "0 Hrs",
    "TODAYREPEATCOUNT": "0"
  },*/

;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView complaintNumberTextView, consumerNoTextView, dateTimeTextView, sourceTypeTextView, complaintSubTypeTextView, raisedByTextView, repeatCallTextView,
                deptTextView,agingTextView,srNoTextView,typeTextView,processByTextView,statusTextView;
        private CardView card_view;
        private ImageView locationImageView;
        private LinearLayout ll_location;

        public MyViewHolder(View itemView) {
            super(itemView);
            complaintNumberTextView = itemView.findViewById(R.id.complaintNumberTextView);
            consumerNoTextView = itemView.findViewById(R.id.consumerNoTextView);
            dateTimeTextView = itemView.findViewById(R.id.dateTimeTextView);
            sourceTypeTextView = itemView.findViewById(R.id.sourceTypeTextView);
            complaintSubTypeTextView = itemView.findViewById(R.id.complaintSubTypeTextView);
            raisedByTextView = itemView.findViewById(R.id.raisedByTextView);
            repeatCallTextView = itemView.findViewById(R.id.repeatCallTextView);
            deptTextView = itemView.findViewById(R.id.deptTextView);
            card_view = itemView.findViewById(R.id.card_view);
            locationImageView = itemView.findViewById(R.id.locationImageView);
            ll_location = itemView.findViewById(R.id.ll_location);
            agingTextView = itemView.findViewById(R.id.agingTextView);
            srNoTextView = itemView.findViewById(R.id.srNoTextView);
            typeTextView = itemView.findViewById(R.id.typeTextView);
            processByTextView = itemView.findViewById(R.id.processByTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);

        }
    }
}
