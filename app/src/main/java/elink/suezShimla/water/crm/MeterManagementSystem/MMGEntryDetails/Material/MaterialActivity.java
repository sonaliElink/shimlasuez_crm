package elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Material;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;

public class MaterialActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayout linerLayout;
    MaterialModel materialModel;
    List<MaterialModel> materialList= new ArrayList<MaterialModel>();
    MaterialAdapter materialAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);
        linerLayout= findViewById(R.id.linerLayout);
        recyclerView= findViewById(R.id.recyclerView);

     /*   materialModel = new MaterialModel(1,"5","EF Saddle 20mm","1","1","1","1","Nos");
        materialList.add(materialModel);
        materialModel = new MaterialModel(2,"6","EF Coupler 20mm","1","1","1","1","Nos");
        materialList.add(materialModel);
        materialModel = new MaterialModel(3,"7","MDPE Pipe 20mm","1","1","1","1","Nos");
        materialList.add(materialModel);
        materialModel = new MaterialModel(4,"8","PP Compression 90 Bend 20mm","1","1","1","1","Nos");
        materialList.add(materialModel);
        materialModel = new MaterialModel(5,"9","GI Pipe 15mm","1","2","1","1","Nos");
        materialList.add(materialModel);
        materialModel = new MaterialModel(6,"10","GI Elbow 15mm","1","2","1","1","Nos");
        materialList.add(materialModel);
        materialAdapter=new MaterialAdpater(this,materialList);
        recyclerView.setAdapter(materialAdapter);
        materialAdapter.notifyDataSetChanged();*/

    }
    @Override
    public void onResume() {
        super.onResume();

        App myApp = (App) this.getApplication();
        if (myApp.wasInBackground) {
            finish();
            startActivity(new Intent(this, SplashScreen.class));
        }

        myApp.stopActivityTransitionTimer();
    }

    @Override
    public void onPause() {
        super.onPause();
        ((App) this.getApplication()).startActivityTransitionTimer();
    }
}
