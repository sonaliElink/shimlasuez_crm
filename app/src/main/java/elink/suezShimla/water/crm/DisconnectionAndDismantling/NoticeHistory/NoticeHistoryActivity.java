package elink.suezShimla.water.crm.DisconnectionAndDismantling.NoticeHistory;

import android.content.Context;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import elink.suezShimla.water.crm.DisconnectionAndDismantling.NoticeHistory.Adapter.NoticeHistoryAdapter;
import elink.suezShimla.water.crm.DisconnectionAndDismantling.NoticeHistory.Model.NoticeHistoryModel;
import elink.suezShimla.water.crm.R;

public class NoticeHistoryActivity extends AppCompatActivity {
    private Context mCon;
    private BottomSheetDialog sheetDialog;
    private RecyclerView noticeHistoryRecyclerView;
    private List<NoticeHistoryModel> noticeHistoryModelList = new ArrayList<>();
    private NoticeHistoryAdapter noticeHistoryAdapter;

    private MaterialButton showButton;
    private AppCompatImageView closeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_history);
        // prevent ss and hide content when app is on background
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mCon = this;

        getSupportActionBar().setTitle(getResources().getString(R.string.notice_history));

        sheetDialog = new BottomSheetDialog(Objects.requireNonNull(mCon));
        noticeHistoryRecyclerView = findViewById(R.id.noticeHistoryRecyclerView);

        noticeHistoryAdapter = new NoticeHistoryAdapter(mCon);

        noticeHistoryRecyclerView.setHasFixedSize(true);
        noticeHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(mCon));

        noticeHistoryBottomDialog();
    }

    public void noticeHistoryBottomDialog(){
        View sheetView = Objects.requireNonNull(getLayoutInflater().inflate(R.layout.bottom_sheet_notice_history, null));
        sheetDialog.setContentView(sheetView);
        sheetDialog.show();
        sheetDialog.setCancelable(false);

        showButton = sheetView.findViewById(R.id.showButton);
        closeImageView = sheetView.findViewById(R.id.closeImageView);

        closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noticeHistoryData();
                sheetDialog.cancel();
            }
        });
    }

    public void noticeHistoryData(){
        NoticeHistoryModel data = new NoticeHistoryModel("1", "0", "1", "19348.75", "31-Dec-2015");
        noticeHistoryModelList.add(data);

        data = new NoticeHistoryModel("2", "0", "2", "22739", "23-Dec-2015");
        noticeHistoryModelList.add(data);

        data = new NoticeHistoryModel("1", "0", "1", "15246.45", "17-Dec-2015");
        noticeHistoryModelList.add(data);

        data = new NoticeHistoryModel("2", "0", "2", "7030.50", "12-Dec-2015");
        noticeHistoryModelList.add(data);

        data = new NoticeHistoryModel("1", "0", "2", "3515.25", "11-Dec-2015");
        noticeHistoryModelList.add(data);

        noticeHistoryAdapter.addList(noticeHistoryModelList);
        noticeHistoryRecyclerView.setAdapter(noticeHistoryAdapter);
    }
}
