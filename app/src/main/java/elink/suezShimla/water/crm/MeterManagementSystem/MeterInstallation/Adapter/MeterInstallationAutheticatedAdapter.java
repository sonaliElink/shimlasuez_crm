package elink.suezShimla.water.crm.MeterManagementSystem.MeterInstallation.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.MeterManagementSystem.MeterInstallation.Model.ShowMeterDataModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.map.SingleMapLocationActivity;

public class MeterInstallationAutheticatedAdapter extends RecyclerView.Adapter<MeterInstallationAutheticatedAdapter.MyViewHolder> {
    LayoutInflater inflater;
    private List<ShowMeterDataModel> data;
    private Context mCon;
    private String uri;

    public MeterInstallationAutheticatedAdapter(Context mCon) {
        this.mCon = mCon;
        data = new ArrayList<>();
        inflater = LayoutInflater.from(mCon);
    }

    public void addList(List<ShowMeterDataModel> meterInstallationModelList) {
        if (data != null) {
            clear();

            data.addAll(meterInstallationModelList);
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
    public MeterInstallationAutheticatedAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.row_meter_autheticated_install_details,viewGroup,false);
        return new MyViewHolder(view);
    }

       @Override
       public void onBindViewHolder(@NonNull final MeterInstallationAutheticatedAdapter.MyViewHolder holder,  int i) {

             ShowMeterDataModel current = data.get(i);
            String count = String.valueOf(i+1);
           if(current!=null) {
               String name_add = current.getCONSUMERNAME() + "\n" + current.getNAME_ADDRESS();
               holder.srNoTextView.setText(count);

               holder.requestNoTextView.setText(current.getMFX_REFNO());
               holder.consumerNoTextView.setText(current.getMFX_SERVICENO());
               holder.nameAddressTextView.setText(name_add);
               holder.contactNoTextView.setText(current.getCONTACTNO());
               holder.allocationDateTextView.setText(current.getMRT_PROCESS_DATE());
               String reqType = current.getREQUEST_TYPE();
               holder.ll_location.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Intent intent = new Intent(mCon, SingleMapLocationActivity.class);

                       intent.putExtra("locationLat", current.getSRM_LATITUDE());
                       intent.putExtra("locationLongi", current.getSRM_LONGITUDE());
                       intent.putExtra("strAddress", current.getNAME_ADDRESS());
                       intent.putExtra("consumerNum", current.getMFX_SERVICENO());
                       intent.putExtra("consumername", current.getCONSUMERNAME());
                       intent.putExtra("tag", "W");
                       mCon.startActivity(intent);
                       ((Activity)mCon).finish();


                   }
               });
           }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView requestNoTextView, consumerNoTextView, nameAddressTextView, contactNoTextView, allocationDateTextView,srNoTextView;
        CardView card_view;
        LinearLayout ll_location;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            requestNoTextView = itemView.findViewById(R.id.requestNoTextView);
            consumerNoTextView = itemView.findViewById(R.id.consumerNoTextView);
            nameAddressTextView = itemView.findViewById(R.id.nameAddressTextView);
            contactNoTextView = itemView.findViewById(R.id.contactNoTextView);
            allocationDateTextView = itemView.findViewById(R.id.allocationDateTextView);
            srNoTextView = itemView.findViewById(R.id.srNoTextView);
            card_view = itemView.findViewById(R.id.card_view);
            ll_location = itemView.findViewById(R.id.ll_location);
        }
    }


}
