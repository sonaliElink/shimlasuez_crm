package elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGCgRestroModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGCvlMeasurementResponseModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGFcRestroModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGRampAndRRModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGSaddleAndPitExcavModel;
import elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable.MMGWallBoringModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;

public class CivilMeasurementDetailsAdapter extends RecyclerView.Adapter<CivilMeasurementDetailsAdapter.MyViewHolder> {
    Context mCon;
    List<MMGCvlMeasurementResponseModel> data;   // commented by ashwini for new implementation of list
    //    List<MMGCivilMeasurementModel> data;
    LayoutInflater inflater;
    String isDropDown = "", isQuantity = "";
    private RealmOperations realmOperations;
    private MMGSaddleAndPitExcavModel mmgSaddleAndPitExcavModel;
    private MMGRampAndRRModel mmgRampAndRRModel;
    private MMGWallBoringModel mmgWallBoringModel;
    private MMGCgRestroModel mmgCgRestroModel;
    private MMGFcRestroModel mmgFcRestroModel;
    private MMGCvlMeasurementResponseModel mmgCvlMeasurementResponseModel;
    public ArrayList<MMGCvlMeasurementResponseModel> mmgUpdatedList = new ArrayList<>();

    ArrayList<String> cgRestoArr = new ArrayList<>();
    ArrayList<String> fcRestorationArr = new ArrayList<>();
    ArrayList<String> rampRrArr = new ArrayList<>();
    ArrayList<String> wBoringArr = new ArrayList<>();

    ArrayList<String> saddleAndPitName = new ArrayList<>();
    ArrayList<String> data_arraylist = new ArrayList<>();
    private ArrayAdapter saddleAndPitAdapter, pepipeAdapter, rampRRAdapter, wallBoringAdapter, cgRestroAdapter, fcRestroAdapter;
    String saddleAndPitId = "", saddleAndPitIdStr = "", pepipeId = "", pepipeIdStr = "", rampRRiId = "", rampRRIdStr = "",
            wallBoringId = "", wallBoringIdStr = "", cgRestroId = "", cgRestroIdStr = "", fcRestroId = "", fcRestroIdStr = "";

    String lengthStr = "", widthStr = "", depthStr = "", ddldval = "", serailNo = "", textViewName = "", spn_dropdown = "", my_mcd_material_id = "";
    String totalval, mcd_material_id = "", mcd_material_name = "", is_dropdown = "",
            mcd_is_quantity = "", length = "", width = "", depth = "", radiobuttonValStr = "";
    public static List<MMGCvlMeasurementResponseModel> civilUpdatedDemoList;
    public static String submitCivilmeasurementTag = "";

    public CivilMeasurementDetailsAdapter(Activity mCon, List<MMGCvlMeasurementResponseModel> cvlMeasurementList) {
        mmgCvlMeasurementResponseModel = new MMGCvlMeasurementResponseModel();
        this.mCon = mCon;
        data = cvlMeasurementList;
        inflater = LayoutInflater.from(mCon);
    }

//    public CivilMeasurementDetailsAdapter(Activity mCon, List<MMGCivilMeasurementModel> cvlMeasurementList) {
//        mmgCvlMeasurementResponseModel =new MMGCvlMeasurementResponseModel();
//        this.mCon=mCon;
//        data = cvlMeasurementList;
//        inflater = LayoutInflater.from(mCon);
//    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.layout_row_civilmesurement, viewGroup, false);

        radiobuttonValStr = UtilitySharedPreferences.getPrefs(mCon, Constants.RADIOBUTTONVAL);

        realmOperations = new RealmOperations(mCon);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CivilMeasurementDetailsAdapter.MyViewHolder holder, int pos) {
        final MMGCvlMeasurementResponseModel current = data.get(pos);
//      holder.setIsRecyclable(false);
        holder.tv_materialName.setText(current.getMCD_MATERIAL_NAME());

        if (current.getLENGTH() != null) {
            holder.lenEdittext.setText(current.getLENGTH());
            lengthStr = holder.lenEdittext.getText().toString();

        }

        if (current.getWIDTH() != null) {
            holder.widthEdittext.setText(current.getWIDTH());
            widthStr = holder.widthEdittext.getText().toString();

        }

        if (current.getDEPTH() != null) {
            holder.depthEdittext.setText(current.getDEPTH());
            depthStr = holder.depthEdittext.getText().toString();
        }

        if (!lengthStr.isEmpty()  && !widthStr.isEmpty() && !depthStr.isEmpty()) {
            if (!lengthStr.equalsIgnoreCase("null") && !widthStr.equalsIgnoreCase("null") &&
                    !depthStr.equalsIgnoreCase("null")) {
                totalval = String.valueOf(Integer.parseInt(lengthStr) * Integer.parseInt(widthStr) * Integer.parseInt(depthStr));

                holder.totalEdittext.setText(totalval);
            } else {
                Log.e("KKK", lengthStr + " l" + widthStr + " w" + depthStr);
            }
        }

        textViewName = holder.tv_materialName.getText().toString();

        try {
            holder.lenEdittext.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    try {
                        lengthStr = holder.lenEdittext.getText().toString();

                        widthStr = holder.widthEdittext.getText().toString();

                        depthStr = holder.depthEdittext.getText().toString();

                        totalval = String.valueOf(Integer.parseInt(lengthStr) * Integer.parseInt(widthStr) * Integer.parseInt(depthStr));

                        holder.totalEdittext.setText(totalval);
                    } catch (NumberFormatException exc) {
                    }
                }
            });

            holder.widthEdittext.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    try {
                        lengthStr = holder.lenEdittext.getText().toString();

                        widthStr = holder.widthEdittext.getText().toString();

                        depthStr = holder.depthEdittext.getText().toString();

                        totalval = String.valueOf(Integer.parseInt(lengthStr) * Integer.parseInt(widthStr) * Integer.parseInt(depthStr));

                        holder.totalEdittext.setText(totalval);

                    } catch (NumberFormatException exc) {

                    }
                }
            });

            holder.depthEdittext.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    // if (!lengthStr.equals("") && !widthStr.equals("") && !depthStr.equals("")) {
                    try {
                        lengthStr = holder.lenEdittext.getText().toString();

                        widthStr = holder.widthEdittext.getText().toString();

                        depthStr = holder.depthEdittext.getText().toString();

                        totalval = String.valueOf(Integer.parseInt(lengthStr) * Integer.parseInt(widthStr) * Integer.parseInt(depthStr));

                        holder.totalEdittext.setText(totalval);
                      /*  if(data.get(pos).getDDLID().equals("null")){
                            ddldval="";
                        }else{
                            ddldval=data.get(pos).getDDLID();
                        }*/

                        serailNo = current.getSLNO();
                        mcd_material_id = current.getMCD_MATERIAL_ID();
                        mcd_material_name = current.getMCD_MATERIAL_NAME();
                        is_dropdown = current.getMCD_ISDROPDOWN();
                        mcd_is_quantity = current.getMCD_ISQUANTITY();
                        length = current.getLENGTH();
                        width = current.getWIDTH();
                        depth = current.getDEPTH();
                        for (int i = 0; i > data.size(); i++) {
                            lengthStr = holder.lenEdittext.getText().toString();
                            widthStr = holder.widthEdittext.getText().toString();
                            depthStr = holder.depthEdittext.getText().toString();
                            totalval = String.valueOf(Integer.parseInt(lengthStr) + Integer.parseInt(widthStr) + Integer.parseInt(depthStr));
                            spn_dropdown = holder.drpDown.getSelectedItem().toString();

                            JSONArray jArray = new JSONArray();
                            JSONObject jsonObj = new JSONObject();
                            try {
                                jsonObj.put("SLNO", serailNo);
                                jsonObj.put("MCD_MATERIAL_ID", mcd_material_id);
                                jsonObj.put("MCD_MATERIAL_NAME", mcd_material_name);
                                jsonObj.put("MCD_ISDROPDOWN", is_dropdown);
                                jsonObj.put("MCD_ISQUANTITY", isQuantity);
                                jsonObj.put("LENGTH ", length);
                                jsonObj.put("WIDTH ", width);
                                jsonObj.put("DEPTH ", depth);

                                jArray.put(jsonObj);

                            } catch (Exception ex) {

                            }
                            Log.d("JSON", jArray.toString());

                        }
                        Log.i("C_LIST", "" + data);

                        submitCivilmeasurementTag = "Enable";
                        UtilitySharedPreferences.clearPrefKey(mCon, Constants.SUBMITCVLMEASUREMENTBUTTONTAG);
                        UtilitySharedPreferences.setPrefs(mCon, Constants.SUBMITCVLMEASUREMENTBUTTONTAG, submitCivilmeasurementTag);

                /*    UtilitySharedPreferences.clearPrefKey(mCon, Constants.SUBMITCIVILLIST);
                    UtilitySharedPreferences.setPrefsList(mCon, Constants.SUBMITCIVILLIST, mmgUpdatedList);*/
                    } catch (NumberFormatException exc) {

                    }
                    //  }
                }

                int m = 1;

            });

        } catch (NumberFormatException ex) { // handle your exception
            Log.e("number", "" + ex.toString());
        }

        holder.totalEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                serailNo = current.getSLNO();
                mcd_material_id = current.getMCD_MATERIAL_ID();
                mcd_material_name = current.getMCD_MATERIAL_NAME();
                is_dropdown = current.getMCD_ISDROPDOWN();
                mcd_is_quantity = current.getMCD_ISQUANTITY();
                length = current.getLENGTH();
                width = current.getWIDTH();
                depth = current.getDEPTH();
                for (int i = 0; i > data.size(); i++) {
                    lengthStr = holder.lenEdittext.getText().toString();
                    widthStr = holder.widthEdittext.getText().toString();
                    depthStr = holder.depthEdittext.getText().toString();
                    totalval = String.valueOf(Integer.parseInt(lengthStr) + Integer.parseInt(widthStr) + Integer.parseInt(depthStr));
                    spn_dropdown = holder.drpDown.getSelectedItem().toString();

                    JSONArray jArray = new JSONArray();
                    JSONObject jsonObj = new JSONObject();
                    try {
                        jsonObj.put("SLNO", serailNo);
                        jsonObj.put("MCD_MATERIAL_ID", mcd_material_id);
                        jsonObj.put("MCD_MATERIAL_NAME", mcd_material_name);
                        jsonObj.put("MCD_ISDROPDOWN", is_dropdown);
                        jsonObj.put("MCD_ISQUANTITY", isQuantity);
                        jsonObj.put("LENGTH ", length);
                        jsonObj.put("WIDTH ", width);
                        jsonObj.put("DEPTH ", depth);

                        jArray.put(jsonObj);

                    } catch (Exception ex) {
                    }
                    Log.d("JSON", jArray.toString());
                }

                try {
                    is_dropdown = current.getMCD_ISDROPDOWN();
                    if (is_dropdown.equalsIgnoreCase("1")) {
                        mmgCvlMeasurementResponseModel = new MMGCvlMeasurementResponseModel(serailNo.substring(0,
                                serailNo.indexOf('.')), current.getMCD_MATERIAL_ID(), current.getMCD_MATERIAL_NAME(),
                                current.getMCD_ISDROPDOWN(), current.getMCD_ISQUANTITY(), my_mcd_material_id, "1",
                                lengthStr, widthStr, depthStr);
                    } else {
                        mmgCvlMeasurementResponseModel = new MMGCvlMeasurementResponseModel(serailNo.substring(0,
                                serailNo.indexOf('.')), current.getMCD_MATERIAL_ID(), current.getMCD_MATERIAL_NAME(),
                                current.getMCD_ISDROPDOWN(), current.getMCD_ISQUANTITY(), "", "1",
                                lengthStr, widthStr, depthStr);
//                        mmgUpdatedList.add(mmgCvlMeasurementResponseModel);
                    }
                    data.set(pos, mmgCvlMeasurementResponseModel);
                    Log.i("C_LIST", "" + data);
                    civilUpdatedDemoList = data;

                } catch (Exception exCivil) {
                    exCivil.printStackTrace();
                }
            }
        });

        isDropDown = current.getMCD_ISDROPDOWN();
        if (isDropDown.equalsIgnoreCase("0")) {
            holder.drpDown.setVisibility(View.GONE);
        } else {
            holder.drpDown.setVisibility(View.VISIBLE);
        }
        isQuantity = current.getMCD_ISQUANTITY();

        if (isQuantity.equalsIgnoreCase("0")) {
            holder.liner_Qty.setVisibility(View.GONE);
        } else {
            holder.liner_Qty.setVisibility(View.GONE);
        }

        if (textViewName.equalsIgnoreCase("CG &  Resto")) {
            cgRestoArr = realmOperations.fetchCGR();
            ArrayList<String> saddleAndPitList = new ArrayList<>();
            saddleAndPitList.add("--Select--");
            saddleAndPitList.addAll(cgRestoArr);

            cgRestroAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, saddleAndPitList);
            cgRestroAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            holder.drpDown.setAdapter(cgRestroAdapter);

            String dropDownPosition = current.getDDLID();
            if (current.getDDLID() != null && !current.getDDLID().equalsIgnoreCase("0")) {
                holder.drpDown.setSelection(Integer.parseInt(dropDownPosition));
            }

            holder.drpDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String drpDownValue = holder.drpDown.getSelectedItem().toString();
                    if (drpDownValue.equalsIgnoreCase("--Select--")) {
                        //  Toast.makeText(mCon, "Please select meter make", Toast.LENGTH_SHORT).show();
                    } else {
                        mmgCgRestroModel = realmOperations.fetchCGRByName(drpDownValue);
                        String drpDownId = String.valueOf(mmgCgRestroModel.getCGR_ID());
                        Log.d("drpDownId", String.valueOf(drpDownId));
                        my_mcd_material_id = (drpDownId);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } else if (textViewName.equalsIgnoreCase("FC  &  Restoration With Cement Mortar")) {
            fcRestorationArr = realmOperations.fetchFCR();
            ArrayList<String> saddleAndPitList = new ArrayList<>();
            saddleAndPitList.add("--Select--");
            saddleAndPitList.addAll(fcRestorationArr);

            fcRestroAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, saddleAndPitList);
            fcRestroAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            holder.drpDown.setAdapter(fcRestroAdapter);

            String dropDownPosition = current.getDDLID();
            if (current.getDDLID() != null && !current.getDDLID().equalsIgnoreCase("0")) {
                holder.drpDown.setSelection(Integer.parseInt(dropDownPosition));
            }

            holder.drpDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String drpDownValue = holder.drpDown.getSelectedItem().toString();
                    if (drpDownValue.equalsIgnoreCase("--Select--")) {
                        //  Toast.makeText(mCon, "Please select meter make", Toast.LENGTH_SHORT).show();
                    } else {
                        mmgFcRestroModel = realmOperations.fetchFCRByName(drpDownValue);
                        String drpDownId = String.valueOf(mmgFcRestroModel.getFCR_ID());
                        Log.d("drpDownId", String.valueOf(drpDownId));
                        my_mcd_material_id = (drpDownId);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } else if (textViewName.equalsIgnoreCase("Ramp R& R")) {
            rampRrArr = realmOperations.fetchRAMPRR();
            ArrayList<String> saddleAndPitList = new ArrayList<>();
            saddleAndPitList.add("--Select--");
            saddleAndPitList.addAll(rampRrArr);

            rampRRAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, saddleAndPitList);
            rampRRAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.drpDown.setAdapter(rampRRAdapter);

            String dropDownPosition = current.getDDLID();
            if (current.getDDLID() != null && !current.getDDLID().equalsIgnoreCase("0") && !current.getDDLID().equalsIgnoreCase("")) {
                holder.drpDown.setSelection(Integer.parseInt(dropDownPosition));
            }
            holder.drpDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String drpDownValue = holder.drpDown.getSelectedItem().toString();
                    if (drpDownValue.equalsIgnoreCase("--Select--")) {
                    } else {
                        mmgRampAndRRModel = realmOperations.fetchRAMPRRByName(drpDownValue);
                        String drpDownId = String.valueOf(mmgRampAndRRModel.getRRR_ID());
                        Log.d("drpDownId", String.valueOf(drpDownId));
                        my_mcd_material_id = (drpDownId);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } else if (textViewName.equalsIgnoreCase("W. Boring")) {
            wBoringArr = realmOperations.fetchWallBoring();
            ArrayList<String> saddleAndPitList = new ArrayList<>();
            saddleAndPitList.add("--Select--");
            saddleAndPitList.addAll(wBoringArr);

            wallBoringAdapter = new ArrayAdapter(mCon, android.R.layout.simple_spinner_item, saddleAndPitList);
            wallBoringAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.drpDown.setAdapter(wallBoringAdapter);

            String dropDownPosition = current.getDDLID();
            if (current.getDDLID() != null && !current.getDDLID().equalsIgnoreCase("0")) {
                holder.drpDown.setSelection(Integer.parseInt(dropDownPosition));
            }

            holder.drpDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String drpDownValue = holder.drpDown.getSelectedItem().toString();
                    if (drpDownValue.equalsIgnoreCase("--Select--")) {
                        //  Toast.makeText(mCon, "Please select meter make", Toast.LENGTH_SHORT).show();
                    } else {
                        mmgWallBoringModel = realmOperations.fetchWallBoringByName(drpDownValue);
                        String drpDownId = String.valueOf(mmgWallBoringModel.getWB_ID());
                        Log.d("drpDownId", String.valueOf(drpDownId));
                        my_mcd_material_id = (drpDownId);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addList(List<MMGCvlMeasurementResponseModel> cvlMeasurementList) {
        if (data != null) {
            data.addAll(cvlMeasurementList);

        }
    }

    private void clear() {
        if (data != null) {
            data.clear();
            notifyDataSetChanged();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_materialName;
        private EditText qtyEdittext, lenEdittext, widthEdittext, depthEdittext, totalEdittext;
        Spinner drpDown;
        LinearLayout liner_Qty;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            initViews(itemView);
        }

        public void initViews(View itemView) {
            tv_materialName = itemView.findViewById(R.id.tv_materialName);
            qtyEdittext = itemView.findViewById(R.id.qtyEdittext);
            lenEdittext = itemView.findViewById(R.id.lenEdittext);
            widthEdittext = itemView.findViewById(R.id.widthEdittext);
            depthEdittext = itemView.findViewById(R.id.depthEdittext);
            totalEdittext = itemView.findViewById(R.id.totalEdittext);
            drpDown = itemView.findViewById(R.id.drpDown);
            liner_Qty = itemView.findViewById(R.id.liner_Qty);

            if (radiobuttonValStr.equalsIgnoreCase("R")) {
                lenEdittext.setEnabled(false);
                lenEdittext.setClickable(false);

                widthEdittext.setEnabled(false);
                widthEdittext.setClickable(false);

                depthEdittext.setEnabled(false);
                depthEdittext.setClickable(false);

                drpDown.setEnabled(false);
                drpDown.setClickable(false);

            }

        }

    }
}
