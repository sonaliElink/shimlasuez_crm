package elink.suezShimla.water.crm.ConnectionRequest.UploadDocuments;

import android.content.Context;
import android.graphics.BitmapFactory;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.ConnectionRequest.UploadDocuments.adapter.UploadDocumentAdapter;
import elink.suezShimla.water.crm.ConnectionRequest.UploadDocuments.model.SelectedDocumentModel;
import elink.suezShimla.water.crm.ConnectionRequest.UploadDocuments.model.UploadDocumentModel;
import elink.suezShimla.water.crm.R;

public class UploadDocumentsActivity extends AppCompatActivity {

    private Context mCon;

    private RecyclerView documentsRecyclerView;

    private UploadDocumentAdapter uploadDocumentAdapter;

    private List<UploadDocumentModel> uploadDocumentModelList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_documents);
        // prevent ss and hide content when app is on background
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mCon = this;

        documentsRecyclerView = findViewById(R.id.documentsRecyclerView);

        uploadDocumentAdapter = new UploadDocumentAdapter(mCon);

        documentsRecyclerView.setHasFixedSize(true);
        documentsRecyclerView.setLayoutManager(new LinearLayoutManager(mCon));

        loadDocList();

    }

    private void loadDocList() {

        List<SelectedDocumentModel> selectedDocumentModelList = new ArrayList<>();
        SelectedDocumentModel selectedDocumentModel = new SelectedDocumentModel("Driving License", BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background), "");
        selectedDocumentModelList.add(selectedDocumentModel);
        selectedDocumentModel = new SelectedDocumentModel("Voter ID  Card", BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background), "");
        selectedDocumentModelList.add(selectedDocumentModel);

        UploadDocumentModel uploadDocumentModel = new UploadDocumentModel("ID Proof",
                "I solemnly arm that the above mentioned information submitted by me is true and correct to my knowledge and belief. I hereby agree to be liable for legal consequences for any information found incorrect or untrue at a later date.",
                selectedDocumentModelList, false);
        uploadDocumentModelList.add(uploadDocumentModel);


        uploadDocumentModel = new UploadDocumentModel("Address Proof",
                "I solemnly arm that the above mentioned information submitted by me is true and correct to my knowledge and belief. I hereby agree to be liable for legal consequences for any information found incorrect or untrue at a later date.",
                selectedDocumentModelList, false);
        uploadDocumentModelList.add(uploadDocumentModel);

        uploadDocumentModel = new UploadDocumentModel("Mandatory Document ",
                "I solemnly arm that the above mentioned information submitted by me is true and correct to my knowledge and belief. I hereby agree to be liable for legal consequences for any information found incorrect or untrue at a later date.",
                selectedDocumentModelList, false);
        uploadDocumentModelList.add(uploadDocumentModel);

        uploadDocumentModel = new UploadDocumentModel("NOC",
                "I solemnly arm that the above mentioned information submitted by me is true and correct to my knowledge and belief. I hereby agree to be liable for legal consequences for any information found incorrect or untrue at a later date.",
                selectedDocumentModelList, false);
        uploadDocumentModelList.add(uploadDocumentModel);

        uploadDocumentAdapter.addList(uploadDocumentModelList);
        documentsRecyclerView.setAdapter(uploadDocumentAdapter);
    }
}
