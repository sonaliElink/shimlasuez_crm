package elink.suezShimla.water.crm.MeterManagementSystem.MeterInstallation.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
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

import elink.suezShimla.water.crm.MeterManagementSystem.MeterInstallation.Model.ShowMeterDataModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.WebActivity;
import elink.suezShimla.water.crm.map.SingleMapLocationActivity;

import static elink.suezShimla.water.crm.Utils.Constants.MIC_URL;

public class MeterInstallationSentAutheticationAdapter extends RecyclerView.Adapter<MeterInstallationSentAutheticationAdapter.MyViewHolder> {
    LayoutInflater inflater;
    private List<ShowMeterDataModel> data;
    private Context mCon;
    private String uri;
    private String fromDate,toDate;

    public MeterInstallationSentAutheticationAdapter(Context mCon,String fromDate, String toDate) {
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
    public MeterInstallationSentAutheticationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.row_meter_sent_authetication_install_details, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MeterInstallationSentAutheticationAdapter.MyViewHolder holder, int i) {
        ShowMeterDataModel current = data.get(i);

        String count = String.valueOf(i + 1);
        if (current != null) {
            String name_add = current.getCONSUMERNAME() + "\n" + current.getNAME_ADDRESS();

            String pdfUrl = current.getMICPATH();

            holder.srNoTextView.setText(count);
            holder.requestNoTextView.setText(current.getMFX_REFNO());
            holder.consumerNoTextView.setText(current.getMFX_SERVICENO());
            holder.nameAddressTextView.setText(name_add);
            holder.contactNoTextView.setText(current.getCONTACTNO());
            // holder.allocationDateTextView.setText(current.getMFX_ISSUE_DATE());
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

                    intent.putExtra("tag", "P");
                    intent.putExtra("fromDate", fromDate);
                    intent.putExtra("toDate", toDate);
                    mCon.startActivity(intent);
                    ((Activity) mCon).finish();


                }
            });

            if (pdfUrl.equalsIgnoreCase("")) {
                holder.pdfImageView.setVisibility(View.GONE);
            } else if (!pdfUrl.equalsIgnoreCase("")) {
                holder.pdfImageView.setVisibility(View.VISIBLE);
            }

            holder.pdfImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String fullPath = MIC_URL + pdfUrl;
                    //openURL(fullPath);

                    Log.e("PDFUrl",fullPath);
                    mCon.startActivity(new Intent(mCon, WebActivity.class)
                            .putExtra("link",  fullPath));
                    //openURL("http://docs.google.com/viewer?url=" + fullPath);
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
        TextView requestNoTextView, consumerNoTextView, nameAddressTextView, contactNoTextView, allocationDateTextView, srNoTextView;
        CardView card_view;
        LinearLayout ll_location;
        ImageView pdfImageView;


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
            pdfImageView = itemView.findViewById(R.id.pdfImageView);
        }
    }

    private void openURL(String s) {
        Uri uri = Uri.parse(s);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri,"text/html");
        mCon.startActivity(intent);
    }

}
