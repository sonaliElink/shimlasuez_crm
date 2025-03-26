package elink.suezShimla.water.crm.Shantanu.Collection;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.tiper.MaterialSpinner;

import java.util.ArrayList;

import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Shantanu.ModelPackage.SearchConsItemModel;
import elink.suezShimla.water.crm.Shantanu.RecyclerPackage.SearchConsRecyclerView;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShowConsumerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowConsumerFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Context mCon;
    MaterialSpinner spnSearchCons;
    TextInputEditText edtSearchCons;
    MaterialButton btnSearch,btnClear;
    RecyclerView recylcerSearchCons;
    ArrayList<SearchConsItemModel> modelArrayList;
    SearchConsRecyclerView modelAdapter;
    String[] spnSearchList;
    ArrayAdapter<String> spnSearchAdapter;
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;

    public ShowConsumerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShowConsumerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowConsumerFragment newInstance(String param1, String param2) {
        ShowConsumerFragment fragment = new ShowConsumerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // prevent ss and hide content when app is on background
//        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_show_consumer, container, false);
        mCon = getActivity();
        gson = new Gson();
        connection = new ConnectionDetector(mCon);
        invServices = new Invoke();
        init(view);
        return view;
    }

    private void init(View view) {
        spnSearchCons = view.findViewById(R.id.spnSearchCons);
        edtSearchCons = view.findViewById(R.id.edtSearchCons);
        btnSearch = view.findViewById(R.id.btnSearch);
        btnClear = view.findViewById(R.id.btnClear);
        recylcerSearchCons = view.findViewById(R.id.recylcerSearchCons);

        recylcerSearchCons.setLayoutManager(new LinearLayoutManager(mCon));
        modelArrayList=new ArrayList<>();

        spnSearchList=getResources().getStringArray(R.array.search_list);
        spnSearchAdapter = new ArrayAdapter(mCon, R.layout.spin_list, spnSearchList);
        spnSearchCons.setAdapter(spnSearchAdapter);
        spnSearchCons.setSelection(1);

        btnSearch.setOnClickListener(this);
        btnClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSearch:
                if(!edtSearchCons.getText().toString().equals("")){
                    modelArrayList.clear();
                    recylcerSearchCons.removeAllViews();
                    setConsList();
                }else {
                    Toast.makeText(mCon, "Please enter "+spnSearchCons.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnClear:
                modelArrayList.clear();
                recylcerSearchCons.removeAllViews();
                spnSearchCons.setSelection(1);
                edtSearchCons.setText("");
                break;
        }
    }

    private void setConsList(){
        try {

            for(int i=0; i<6;i++) {
                SearchConsItemModel sci=new SearchConsItemModel();
                sci.setConsId(""+i);
                sci.setConsNo("12893721803");
                sci.setOldconsNo("1234");
                sci.setConsName("sjakd sajsak sajhdjlasjdsahd");
                sci.setConsMobile("9899998980");

                modelArrayList.add(sci);
            }

            modelAdapter = new SearchConsRecyclerView(mCon, modelArrayList,recylcerSearchCons);
            recylcerSearchCons.setAdapter(modelAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}