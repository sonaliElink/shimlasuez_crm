package elink.suezShimla.water.crm.ConnectionRequest.Activity.TechnicalFeasibilityPlumberWork.Adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import elink.suezShimla.water.crm.ConnectionRequest.Activity.TechnicalFeasibilityPlumberWork.Activity.FeasibilityPlumberWorkDetailsActivity;
import elink.suezShimla.water.crm.ConnectionRequest.Activity.TechnicalFeasibilityPlumberWork.Model.TechnicalFeasibilityPlumberWorkModel;
import elink.suezShimla.water.crm.R;

public class TechnicalFeasibilityPlumberWorkAdapter extends RecyclerView.Adapter<TechnicalFeasibilityPlumberWorkAdapter.MyViewHolder> {

    private List<TechnicalFeasibilityPlumberWorkModel> data;
    private Context mCon;
    private LayoutInflater inflater;
    private String uri;

    public TechnicalFeasibilityPlumberWorkAdapter(Context mCOn) {

        this.mCon = mCOn;
        data = new ArrayList<>();
        inflater = LayoutInflater.from(mCOn);
    }

    public void addList(List<TechnicalFeasibilityPlumberWorkModel> technicalFeasibilityPlumberWorkModels) {
        if (data != null) {
            clear();

            data.addAll(technicalFeasibilityPlumberWorkModels);
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
    public TechnicalFeasibilityPlumberWorkAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.row_technical_feasibility_flumber_work, viewGroup, false);
        return new TechnicalFeasibilityPlumberWorkAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TechnicalFeasibilityPlumberWorkAdapter.MyViewHolder holder, int i) {
        final TechnicalFeasibilityPlumberWorkModel current = data.get(i);

        holder.requestNumberTextView.setText(current.getRequestNo());
        holder.dateTextView.setText(Html.fromHtml(current.getDate()));
        holder.applicantNameTextView.setText(current.getApplicantName());
        holder.contactNumberTextView.setText(current.getContactNo());
        holder.siteAddressTextView.setText(current.getSiteAddress());

        holder.locationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:" + 19.1147579 + "," + 72.8597357);

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");

                try {
                    mCon.startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    try {
                        Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        mCon.startActivity(unrestrictedIntent);
                    } catch (ActivityNotFoundException innerEx) {
                        Toast.makeText(mCon, "Please install a maps application", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCon.startActivity(new Intent(mCon, FeasibilityPlumberWorkDetailsActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView locationImageView;
        TextView requestNumberTextView, dateTextView, applicantNameTextView, contactNumberTextView, siteAddressTextView;
        CardView card_view;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            locationImageView = itemView.findViewById(R.id.locationImageView);
            requestNumberTextView = itemView.findViewById(R.id.requestNumberTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            applicantNameTextView = itemView.findViewById(R.id.applicantNameTextView);
            contactNumberTextView = itemView.findViewById(R.id.contactNumberTextView);
            siteAddressTextView = itemView.findViewById(R.id.siteAddressTextView);
            card_view = itemView.findViewById(R.id.card_view);
        }
    }
}
