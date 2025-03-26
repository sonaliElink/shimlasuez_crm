package elink.suezShimla.water.crm.MeterManagementSystem.MeterInstallation.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Activity.MMGMainActivity;
import elink.suezShimla.water.crm.MeterManagementSystem.MeterInstallation.Model.ShowMeterDataModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.map.SingleMapLocationActivity;

public class MeterInstallationAdapter extends RecyclerView.Adapter<MeterInstallationAdapter.MyViewHolder> {
    LayoutInflater inflater;
    private List<ShowMeterDataModel> data;
    private Context mCon;
    private String uri;
    private String fromDate,toDate;

    public MeterInstallationAdapter(Context mCon,String fromDate, String toDate) {
        this.mCon = mCon;
        data = new ArrayList<>();
        inflater = LayoutInflater.from(mCon);
        this.fromDate = fromDate;
        this.toDate = toDate;
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
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.row_meter_install_details,viewGroup,false);
        return new MyViewHolder(view);
    }

       @Override
       public void onBindViewHolder(@NonNull final MyViewHolder holder,  int i) {

               String count = String.valueOf(i+1);
               ShowMeterDataModel current = data.get(i);
           Log.d("currentModel", ""+current);

               if (current != null) {

                   String name_add = current.getCONSUMERNAME() + "\n" + current.getNAME_ADDRESS();

                   holder.srNoTextView.setText(count);
                   holder.requestNoTextView.setText(current.getMFX_REFNO());
                   holder.consumerNoTextView.setText(current.getMFX_SERVICENO());
                   holder.nameAddressTextView.setText(name_add);
                   holder.contactNoTextView.setText(current.getCONTACTNO());
                   holder.allocationDateTextView.setText(current.getMRT_PROCESS_DATE());
                   holder.nameRequestTypeTextView.setText(current.getCOMPLAINT_SUBTYPE());
                //   String reqType = current.getREQUEST_TYPE();
                   String reqType = current.getMFX_SERVICE_TYPE();
                   String checkboxType = current.getMI_ACTION();
                   String serviceType = current.getMI_ISCOMMISSIONED();
                   String subType = current.getCOMPLAINT_SUBTYPE();
                   //Log.d("serviceType", ""+serviceType);

                   holder.card_view.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
//                    Intent intent=new Intent(mCon, MeterReadingDetailsActivity.class);
                           Intent intent = new Intent(mCon, MMGMainActivity.class);
                           String pagename = "MeterInstallationEntry";
                           intent.putExtra("reqNo", current.getMFX_REFNO());
                           intent.putExtra("ConsumerNo", current.getMFX_SERVICENO());
                           intent.putExtra("pagename", "MeterInstallationEntry");
                           intent.putExtra("reqType", reqType);
                           intent.putExtra("checkboxType", checkboxType);
                           intent.putExtra("fromAdapter", "allocatedWork");
                           intent.putExtra("serviceType", serviceType);
                           intent.putExtra("subType", subType);
                           mCon.startActivity(intent);

//                    intent.putExtra("ConsumerType", current.getServiceType());
//                    intent.putExtra("nameAddress", name_add);
//                    intent.putExtra("contactNo",current.getCONTACTNO());
////                    intent.putExtra("oldMeterNo",current.getOldMeterNO());
//                    intent.putExtra("fixerName",current.getCONTRACTORFIXER());
//

                       }
                   });
                   holder.ll_location.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
//                    Intent intent=new Intent(mCon, MeterReadingDetailsActivity.class);
                           Intent intent = new Intent(mCon, SingleMapLocationActivity.class);

                           intent.putExtra("locationLat", current.getSRM_LATITUDE());
                           intent.putExtra("locationLongi", current.getSRM_LONGITUDE());
                           intent.putExtra("strAddress", current.getNAME_ADDRESS());
                           intent.putExtra("consumerNum", current.getMFX_SERVICENO());

                           intent.putExtra("consumername", current.getCONSUMERNAME());
                           intent.putExtra("tag", "A");
                           intent.putExtra("fromDate", fromDate);
                           intent.putExtra("toDate", toDate);

                          // intent.putExtra("serviceType", current.getMFX_SERVICE_TYPE());
                           mCon.startActivity(intent);
                           ((Activity)mCon).finish();

//                    intent.putExtra("ConsumerType", current.getServiceType());
//                    intent.putExtra("nameAddress", name_add);
//                    intent.putExtra("contactNo",current.getCONTACTNO());
////                    intent.putExtra("oldMeterNo",current.getOldMeterNO());
//                    intent.putExtra("fixerName",current.getCONTRACTORFIXER());
//

                       }
                   });

               }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void filterList(List<ShowMeterDataModel> list) {
        data = list;
        notifyDataSetChanged();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView requestNoTextView, consumerNoTextView, nameAddressTextView, contactNoTextView, allocationDateTextView,srNoTextView,nameRequestTypeTextView;
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
            nameRequestTypeTextView = itemView.findViewById(R.id.nameRequestTypeTextView);
            card_view = itemView.findViewById(R.id.card_view);
            ll_location = itemView.findViewById(R.id.ll_location);
        }
    }


}
