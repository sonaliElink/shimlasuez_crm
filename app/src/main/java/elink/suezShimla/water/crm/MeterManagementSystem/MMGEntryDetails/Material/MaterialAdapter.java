package elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Material;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.Utils.Constants;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.ErrorClass;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Model.MMGMaterialResponseModel;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.asyncClass.ConnectionDetector;
import elink.suezShimla.water.crm.asyncClass.Invoke;


public class MaterialAdapter extends RecyclerView.Adapter<MaterialAdapter.MyViewHolder>  {
    MMGMaterialResponseModel mmgMaterialResponseModel;
    private ArrayList<MMGMaterialResponseModel> spinnerDetailList = new ArrayList<>();
    List<MMGMaterialResponseModel> data;
    public List<MMGMaterialResponseModel> materialDummyList=new ArrayList<MMGMaterialResponseModel>();

    Context mCon;
    MaterialDialog detailprogress;
    String srNo = "", materialId = "", materialName = "", defaultQty = "", uomName = "",  mrmGroupID = "", strSize = "", meterSizeID = ""
            ,jsonResponse="",serialNo="",drpdwnId="",drpdwnName="",drpDownId="",drpdwonIdStr="",radiobuttonValStr="",materialNamedrpDownValName="";
    LayoutInflater inflater;
    private ConnectionDetector connection;
    private Invoke invServices;
    private Gson gson;
    List<MMGMaterialResponseModel> materialList= new ArrayList<MMGMaterialResponseModel>();
    List<MMGMaterialResponseModel> spinnerDrpdownList= new ArrayList<MMGMaterialResponseModel>();
    ArrayAdapter spinnerArrayAdapter;
    RealmOperations realmOperations;
    List<String> drpdwnNameList = new ArrayList<String>();
    ArrayAdapter dropdownAdapter;
    //String submitTag="";
    RowClick rowClick;
    boolean isClick = true;
    String MI_METERINSTALLID, MI_ACTION, MI_CONSUMER, MI_BU, MI_PC, MI_REFNO, MI_O_SIZE, MI_O_METER
            ,MI_O_MAKE, MI_O_PREVIOUSREADING, MI_O_FINALREADING,MI_O_FINALSTATUS, MI_O_REASON, MI_O_METERTYPE,
            MI_N_MAKE, MI_N_SIZE, MI_N_SEAL,MI_N_METER, MI_INSTALLATIONDATE, MI_N_INITIALREADING
            ,MI_N_METERTYPE, MI_N_METERLOCATION, MI_N_ISPROTECTED, MI_PROPERTYTAXNO, MI_N_ISMETERHANDOVER,
            MI_CONTRACTOR, MI_CONTRACTOREMP, MI_N_ISMATERIALHANDOVER, MI_PCCBEDDINGLEN, MI_PCCBEDDINGWID,
            MI_PCCBEDDINGDEP, MI_ROADCUTTINGTYPE, MI_ROADCUTTINGLEN, MI_ROADCUTTINGWID, MI_ROADCUTTINGDEP, MI_FROMNODE,
            MI_TONODE, MI_REGMOBILE, MI_ALTMOBILE, MI_GIS, MI_DMA, MI_SR, MI_MODIFIEDBY, MI_MODIFIEDDATE, MI_IP, MI_AUTHENTICATEDATE,
            MI_AUTHREJECTBY, MI_REJECTEDDATE, MI_STATUS, MI_ISACTIVE, MI_XMLMATERIAL, MI_XMLCIVIL, MI_O_OBSERVATION,
            MI_SOURCE, MI_ISCOMMISSIONED, MI_CONTRACTOROTHER, MI_CONTRACTOREMPOTHER, MI_N_DIGIT, MSRID;
    Bundle bundle = new Bundle();
//    String pagename="", contList="";

    ArrayList<String> drpdownName = new ArrayList<>();
    ArrayList<String>  drpdownList = new ArrayList<>();

    public static List<MMGMaterialResponseModel> materialUpdatedList;
    public static  String submitMaterialTag;


    public MaterialAdapter(Activity mCon, List<MMGMaterialResponseModel> materialList, RowClick rowClick) {

        this.mCon = mCon;
        data = materialList;
        inflater = LayoutInflater.from(mCon);
        this.rowClick= rowClick;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.layout_rowmaterial, viewGroup, false);
        radiobuttonValStr = UtilitySharedPreferences.getPrefs(mCon, Constants.RADIOBUTTONVAL);
        gson = new Gson();
        invServices = new Invoke();
        connection = new ConnectionDetector(mCon);
        realmOperations = new RealmOperations(mCon);
        drpdownName = realmOperations.fetchdrpdownDetails();

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int pos) {
        final MMGMaterialResponseModel current = data.get(pos);

//        pagename = bundle.getString("pagename");
//        contList = bundle.getString("contList");

        holder.tv_sequenceId.setText(String.valueOf(current.getSLNO()));

        if (current.getMRM_MATERIAL_NAME().equalsIgnoreCase("Dropdown"))
        {
            holder.category_spinner.setVisibility(View.VISIBLE);

            drpdownList.add("--Select--");
            drpdownList.addAll(drpdownName);

            dropdownAdapter = new ArrayAdapter(mCon, R.layout.spinner_dropdown_item, drpdownList);
            dropdownAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            holder.category_spinner.setAdapter(dropdownAdapter);

            String mat_id = current.getMRM_MATERIAL_ID();
            String mat_name="";
            if(mat_id == null || mat_id.equalsIgnoreCase(""))
            {
                //Do Nothing
            }else {
                try {
                    mmgMaterialResponseModel = realmOperations.fetchdrpdownById(mat_id);
                    mat_name = mmgMaterialResponseModel.getMRM_MATERIAL_NAME();
                    Log.i("mat_name", "" + mat_name);

                    int material = drpdownList.indexOf(mat_name);

                    ArrayList<String> drpDownValues = new ArrayList<>();
                    drpDownValues.addAll(drpdownList);

                    dropdownAdapter = new ArrayAdapter<>(mCon, android.R.layout.simple_spinner_item, drpDownValues);
                    dropdownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    holder.category_spinner.setAdapter(dropdownAdapter);
                    holder.category_spinner.setSelection(material);
                }catch (Exception dd){
                    holder.category_spinner.setVisibility(View.VISIBLE);


                    drpdownList.add("--Select--");
                    drpdownList.addAll(drpdownName);

                    dropdownAdapter = new ArrayAdapter(mCon, R.layout.spinner_dropdown_item, drpdownList);
                    dropdownAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    holder.category_spinner.setAdapter(dropdownAdapter);
                }
            }

        } else {
            holder.tv_categoryName.setVisibility(View.VISIBLE);
            holder.tv_categoryName.setText(current.getMRM_MATERIAL_NAME());
            holder.tv_nos.setText(current.getUOM_NAME());
        }

        if (current.getDEFAULTQUANTITY() == null) {
            holder.et_categoryValue.setFocusableInTouchMode(true);
            holder.et_categoryValue.setClickable(true);
            holder.et_categoryValue.setCursorVisible(true);
        } else {
            holder.et_categoryValue.setText(current.getDEFAULTQUANTITY());
            holder.et_categoryValue.setClickable(true);
        }

        holder.tv_nos.setText(current.getUOM_NAME());

        holder.et_categoryValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
               /* if(data.get(i).getMRM_GROUPID().equals("null")){
                    mrmGroupID="";
                }else{
                    mrmGroupID=data.get(i).getMRM_GROUPID();
                }*/
                submitMaterialTag = "Enable";
                UtilitySharedPreferences.clearPrefKey(mCon,Constants.SUBMITMATERIALBUTTONTAG);
                UtilitySharedPreferences.setPrefs(mCon, Constants.SUBMITMATERIALBUTTONTAG,submitMaterialTag);
                defaultQty = holder.et_categoryValue.getText().toString();


                mmgMaterialResponseModel = new MMGMaterialResponseModel(current.getSLNO(), current.getMRM_MATERIAL_ID(),current.getMRM_MATERIAL_NAME(),defaultQty,current.getUOM_NAME(),"");
//                materialDummyList.add(mmgMaterialResponseModel);
                materialUpdatedList = data;
            }
        });

        holder.category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              String drpDownValName=holder.category_spinner.getSelectedItem().toString();
              if(drpDownValName.equalsIgnoreCase("--Select--")){

              }else{
                  mmgMaterialResponseModel = realmOperations.fetchdrpdownByName(drpDownValName);
                  drpDownId = String.valueOf(mmgMaterialResponseModel.getMRM_MATERIAL_ID());
                  Log.e("drpDownId",String.valueOf(drpDownId));
                  drpdwonIdStr = (drpDownId);
                 // materialNamedrpDownValName = drpDownValName;
                  materialNamedrpDownValName= mmgMaterialResponseModel.getMRM_MATERIAL_NAME();
                  defaultQty=mmgMaterialResponseModel.getDEFAULTQUANTITY();
                  holder.category_spinner.setSelection(position);

                  mmgMaterialResponseModel = new MMGMaterialResponseModel(current.getSLNO(), current.getMRM_MATERIAL_ID(),current.getMRM_MATERIAL_NAME(),defaultQty,current.getUOM_NAME(),"");
                  if(!drpDownValName.equalsIgnoreCase("Dropdown")){
                   //   mmgMaterialResponseModel = new MMGMaterialResponseModel(current.getSLNO(), drpdwonIdStr ,current.getMRM_MATERIAL_NAME(),defaultQty,current.getUOM_NAME(),"");
                      mmgMaterialResponseModel = new MMGMaterialResponseModel(current.getSLNO(), drpdwonIdStr ,materialNamedrpDownValName,defaultQty,current.getUOM_NAME(),"");
                      data.set(pos, mmgMaterialResponseModel);
                  }else {
                      data.set(pos, mmgMaterialResponseModel);
                  }

              }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int curr = Integer.parseInt(holder.et_categoryValue.getText().toString());
                int newQty = curr+1;
                holder.et_categoryValue.setText(newQty+"");
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int curr = Integer.parseInt(holder.et_categoryValue.getText().toString());
                if(curr > 1) {
                    int newQty = curr - 1;
                    holder.et_categoryValue.setText(newQty + "");
                }
            }
        });

//        if(contList == null || contList.equalsIgnoreCase("")) {
//        }else {
//            listAfterDownload();
//        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

//    private void listAfterDownload(){
//        contList = bundle.getString("contList");
//        try {
//            JSONArray jsonArray = new JSONArray(contList);
//            JSONObject jsnobject = new JSONObject();
//
//            for (int i = 0; i < jsonArray.length(); i++) {
//                jsnobject = jsonArray.getJSONObject(i);
//
//                MI_METERINSTALLID = jsnobject.getString("MI_METERINSTALLID");
//                MI_ACTION = jsnobject.getString("MI_ACTION");
//                MI_CONSUMER = jsnobject.getString("MI_CONSUMER");
//                MI_BU = jsnobject.getString("MI_BU");
//                MI_PC = jsnobject.getString("MI_PC");
//                MI_REFNO = jsnobject.getString("MI_REFNO");
//                MI_O_SIZE = jsnobject.getString("MI_O_SIZE");
//                MI_O_METER = jsnobject.getString("MI_O_METER");
//                MI_O_MAKE = jsnobject.getString("MI_O_MAKE");
//                MI_O_PREVIOUSREADING = jsnobject.getString("MI_O_PREVIOUSREADING");
//                MI_O_FINALREADING = jsnobject.getString("MI_O_FINALREADING");
//                MI_O_FINALSTATUS = jsnobject.getString("MI_O_FINALSTATUS");
//                MI_O_REASON = jsnobject.getString("MI_O_REASON");
//                MI_O_METERTYPE = jsnobject.getString("MI_O_METERTYPE");
//                MI_N_MAKE = jsnobject.getString("MI_N_MAKE");
//                MI_N_SIZE = jsnobject.getString("MI_N_SIZE");
//                MI_N_SEAL = jsnobject.getString("MI_N_SEAL");
//                MI_N_METER = jsnobject.getString("MI_N_METER");
//                MI_INSTALLATIONDATE = jsnobject.getString("MI_INSTALLATIONDATE");
//                MI_N_INITIALREADING = jsnobject.getString("MI_N_INITIALREADING");
//                MI_N_METERTYPE = jsnobject.getString("MI_N_METERTYPE");
//                MI_N_METERLOCATION = jsnobject.getString("MI_N_METERLOCATION");
//                MI_N_ISPROTECTED = jsnobject.getString("MI_N_ISPROTECTED");
//                MI_PROPERTYTAXNO = jsnobject.getString("MI_PROPERTYTAXNO");
//                MI_N_ISMETERHANDOVER = jsnobject.getString("MI_N_ISMETERHANDOVER");
//                MI_CONTRACTOR = jsnobject.getString("MI_CONTRACTOR");
//                MI_CONTRACTOREMP = jsnobject.getString("MI_CONTRACTOREMP");
//                MI_N_ISMATERIALHANDOVER = jsnobject.getString("MI_N_ISMATERIALHANDOVER");
//                MI_PCCBEDDINGLEN = jsnobject.getString("MI_PCCBEDDINGLEN");
//                MI_PCCBEDDINGWID = jsnobject.getString("MI_PCCBEDDINGWID");
//                MI_PCCBEDDINGDEP = jsnobject.getString("MI_PCCBEDDINGDEP");
//                MI_ROADCUTTINGTYPE = jsnobject.getString("MI_ROADCUTTINGTYPE");
//                MI_ROADCUTTINGLEN = jsnobject.getString("MI_ROADCUTTINGLEN");
//                MI_ROADCUTTINGWID = jsnobject.getString("MI_ROADCUTTINGWID");
//                MI_ROADCUTTINGDEP = jsnobject.getString("MI_ROADCUTTINGDEP");
//                MI_FROMNODE = jsnobject.getString("MI_FROMNODE");
//                MI_TONODE = jsnobject.getString("MI_TONODE");
//                MI_REGMOBILE = jsnobject.getString("MI_REGMOBILE");
//                MI_ALTMOBILE = jsnobject.getString("MI_ALTMOBILE");
//                MI_GIS = jsnobject.getString("MI_GIS");
//                MI_DMA = jsnobject.getString("MI_DMA");
//                MI_SR = jsnobject.getString("MI_SR");
//                MI_MODIFIEDBY = jsnobject.getString("MI_MODIFIEDBY");
//                MI_MODIFIEDDATE = jsnobject.getString("MI_MODIFIEDDATE");
//                MI_IP = jsnobject.getString("MI_IP");
//                MI_AUTHENTICATEDATE = jsnobject.getString("MI_AUTHENTICATEDATE");
//                MI_AUTHREJECTBY = jsnobject.getString("MI_AUTHREJECTBY");
//                MI_REJECTEDDATE = jsnobject.getString("MI_REJECTEDDATE");
//                MI_STATUS = jsnobject.getString("MI_STATUS");
//                MI_ISACTIVE = jsnobject.getString("MI_ISACTIVE");
//                MI_XMLMATERIAL = jsnobject.getString("MI_XMLMATERIAL");
//                MI_XMLCIVIL = jsnobject.getString("MI_XMLCIVIL");
//                MI_O_OBSERVATION = jsnobject.getString("MI_O_OBSERVATION");
//                MI_SOURCE = jsnobject.getString("MI_SOURCE");
//                MI_ISCOMMISSIONED = jsnobject.getString("MI_ISCOMMISSIONED");
//                MI_CONTRACTOROTHER = jsnobject.getString("MI_CONTRACTOROTHER");
//                MI_CONTRACTOREMPOTHER = jsnobject.getString("MI_CONTRACTOREMPOTHER");
//                MI_N_DIGIT = jsnobject.getString("MI_N_DIGIT");
//                MSRID = jsnobject.getString("MSRID");
//            }
//            setMaterial();
//
//        }catch (Exception e){
//            Log.e("Exp MtrlStr", ""+e.getMessage());
//        }
//    }

    private void setMaterial(){
        Toast.makeText(mCon, "contlist", Toast.LENGTH_SHORT).show();
    }

    public void addList(List<MMGMaterialResponseModel> materialList) {
        if (data != null) {
            data.addAll(materialList);
            notifyDataSetChanged();
        }
    }

    private void clear() {
        if (data != null) {
            data.clear();
            notifyDataSetChanged();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_sequenceId, tv_categoryName, tv_nos;
        EditText et_categoryValue;
        Spinner category_spinner;
        LinearLayout row_linerLayout,ll_material;
        ImageButton plus, minus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_sequenceId = itemView.findViewById(R.id.tv_sequenceId);
            tv_categoryName = itemView.findViewById(R.id.tv_categoryName);
            tv_nos = itemView.findViewById(R.id.tv_nos);
            category_spinner = itemView.findViewById(R.id.category_spinner);
            et_categoryValue = itemView.findViewById(R.id.et_categoryValue);
            ll_material = itemView.findViewById(R.id.ll_material);
            plus = itemView.findViewById(R.id.plus);
            minus = itemView.findViewById(R.id.minus);

            if(radiobuttonValStr.equalsIgnoreCase("R")) {
                category_spinner.setEnabled(false);
                category_spinner.setClickable(false);

                et_categoryValue.setEnabled(false);
                et_categoryValue.setClickable(false);
//                Toast.makeText(mCon, "Please Go To Authentication Page", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onClick(View v) {

        }

    }

    @SuppressLint("StaticFieldLeak")
    private class Get_MaterialDetailsSpinnerTask extends AsyncTask<String, Void, Void> {
        //meter replacement new screen async task for getting master data

        @Override
        protected Void doInBackground(String... params) {
            try {
                String paraName[] = new String[2];
                paraName[0] = "MeterSizeId";
                paraName[1] = "Type";

                jsonResponse = invServices.getDataWITHParams(Constants.URL, Constants.NameSpace, Constants.MMG_GetMaterialDetailsByMCSID, params, paraName);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                MMGMaterialResponseModel[] mmgGetDetailsResponseModel =gson.fromJson(jsonResponse, MMGMaterialResponseModel[].class);

                if(mmgGetDetailsResponseModel!=null) {
                    if (mmgGetDetailsResponseModel != null && mmgGetDetailsResponseModel.length > 0) {
                        for (MMGMaterialResponseModel model : mmgGetDetailsResponseModel) {

                            srNo=model.getSLNO();
                            materialId=model.getMRM_MATERIAL_ID();
                            materialName=model.getMRM_MATERIAL_NAME();
                            materialName.replaceAll("&deg;","\u00B0");

                            defaultQty=model.getDEFAULTQUANTITY();
                            uomName=model.getUOM_NAME();
                            mrmGroupID=model.getMRM_GROUPID();
//                            serialNo=srNo.substring(0,srNo.indexOf('.'));
                            serialNo=srNo;

                            MMGMaterialResponseModel mmgMaterialResponseModel= new MMGMaterialResponseModel(serialNo,materialId,materialName,defaultQty,uomName,mrmGroupID);
                            spinnerDrpdownList.add(mmgMaterialResponseModel);
                        }
                    }
                }
                //detailprogress.dismiss();
            }catch (Exception e){
                Log.e("Exception",e.toString());
                String error = e.toString();
                ErrorClass.errorData(mCon, "MaterialAdapter", "FetchingMaterialList", error);
            }
        }
    }


    public void  getDropDownDetails(){
        meterSizeID= UtilitySharedPreferences.getPrefs(mCon, Constants.MTR_SIZE_ID);

        String params[] = new String[2];
        params[0] = meterSizeID;
        params[1] = "1";

        Get_MaterialDetailsSpinnerTask get_materialDetailsTask =new Get_MaterialDetailsSpinnerTask();
        get_materialDetailsTask.execute(params);
    }

    public interface RowClick {
        void rowClicked(int id,MMGMaterialResponseModel mmgMaterialResponseModel);
    }
}
