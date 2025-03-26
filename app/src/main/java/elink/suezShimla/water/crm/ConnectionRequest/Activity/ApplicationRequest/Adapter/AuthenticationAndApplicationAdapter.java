package elink.suezShimla.water.crm.ConnectionRequest.Activity.ApplicationRequest.Adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.ConnectionRequest.Activity.ApplicationRequest.AuthenticationAndApplicationDetailsActivity;
import elink.suezShimla.water.crm.ConnectionRequest.Activity.ApplicationRequest.Model.AuthenticationAndApplicationModel;
import elink.suezShimla.water.crm.R;

public class AuthenticationAndApplicationAdapter extends RecyclerView.Adapter<AuthenticationAndApplicationAdapter.MyViewHolder> {

    private List<AuthenticationAndApplicationModel> data;
    private Context mCon;
    LayoutInflater inflater;

    public AuthenticationAndApplicationAdapter(Context mCOn) {

        this.mCon = mCOn;
        data = new ArrayList<>();
        inflater = LayoutInflater.from(mCOn);
    }

    public void addList(List<AuthenticationAndApplicationModel> authenticationAndApplicationModels) {
        if (data != null) {
            clear();

            data.addAll(authenticationAndApplicationModels);
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
    public AuthenticationAndApplicationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.row_authentication_and_application, viewGroup, false);
        return new AuthenticationAndApplicationAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AuthenticationAndApplicationAdapter.MyViewHolder holder, int i) {
        final AuthenticationAndApplicationModel current = data.get(i);

        holder.applicationNumberTextView.setText(current.getApplicationNumber());
        holder.applicationTypeTextView.setText(current.getApplicationType());
        holder.applicationDateTextView.setText(current.getDate());
        holder.applicantNameTextView.setText(current.getApplicaintName());
        holder.contactNoTextView.setText(current.getContactNumber());

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCon.startActivity(new Intent(mCon, AuthenticationAndApplicationDetailsActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView applicationNumberTextView, applicationTypeTextView, applicationDateTextView, applicantNameTextView, contactNoTextView;
        CardView card_view;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            applicationNumberTextView = itemView.findViewById(R.id.applicationNumberTextView);
            applicationTypeTextView = itemView.findViewById(R.id.applicationTypeTextView);
            applicationDateTextView = itemView.findViewById(R.id.applicationDateTextView);
            applicantNameTextView = itemView.findViewById(R.id.applicantNameTextView);
            contactNoTextView = itemView.findViewById(R.id.contactNoTextView);
            card_view = itemView.findViewById(R.id.card_view);
        }
    }
}
