package com.elink.googlemapdemo;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, OnClickListener {
    Button btnMap;
    Polyline currentPolygon;
    private GoogleMap mMap;
    MarkerOptions placeOne;
    MarkerOptions placeTwo;

    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
        Button button = (Button) findViewById(R.id.btnMap);
        this.btnMap = button;
        button.setOnClickListener(this);
        this.placeOne = new MarkerOptions().position(new LatLng(19.115492d, 72.872696d)).title("Andheri");
        this.placeTwo = new MarkerOptions().position(new LatLng(19.228825d, 72.854118d)).title("Borivali");
        //String url = getUrl(this.placeOne.getPosition(), this.placeTwo.getPosition(), "driving");
    }

    public void onMapReady(GoogleMap googleMap) {
        GoogleMap googleMap2 = googleMap;
        this.mMap = googleMap2;
        googleMap2.addMarker(this.placeOne);
        this.mMap.addMarker(this.placeTwo);
        CameraPosition googlePlex = CameraPosition.builder().target(new LatLng(22.7739d, 71.6673d)).zoom(7.0f).bearing(0.0f).tilt(45.0f).build();
        this.mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 5000, null);
        ArrayList<LatLongListEntity> latLongListEntityArrayList = new ArrayList();
        latLongListEntityArrayList.clear();
        LatLongListEntity entity2 = new LatLongListEntity(19.115492d, 72.872696d, "Andheri");
        LatLongListEntity entity = new LatLongListEntity(19.228825d, 72.854118d, "Borivali");
        LatLongListEntity entity1 = new LatLongListEntity(19.085649d, 72.908218d, "Ghatkoper");
        LatLongListEntity entity3 = new LatLongListEntity(19.21833d, 72.978088d, "Thane");
        LatLongListEntity Powai = new LatLongListEntity(19.1176d, 72.906d, "Powai");
        LatLongListEntity Chandivali = new LatLongListEntity(19.1075d, 72.9018d, "Chandivali");
        LatLongListEntity Bandra = new LatLongListEntity(19.0596d, 72.8295d, "Bandra");
        LatLongListEntity Chembur = new LatLongListEntity(19.0522d, 72.9005d, "Chembur");
        LatLongListEntity Worli = new LatLongListEntity(18.9986d, 72.8174d, "Worli");
        LatLongListEntity Bhandup = new LatLongListEntity(19.1511d, 72.9372d, "Bhandup");
        LatLongListEntity Goregaon = new LatLongListEntity(19.1663d, 72.8526d, "Goregaon");
        LatLongListEntity Kandivali = new LatLongListEntity(19.2029d, 72.8518d, "Kandivali");
        LatLongListEntity Mulund = new LatLongListEntity(19.1726d, 72.9425d, "Mulund");
        LatLongListEntity Vikhroli = new LatLongListEntity(19.1024d, 72.92d, "Vikhroli");
        latLongListEntityArrayList.add(entity2);
        latLongListEntityArrayList.add(entity);
        latLongListEntityArrayList.add(entity1);
        latLongListEntityArrayList.add(entity3);
        latLongListEntityArrayList.add(Powai);
        latLongListEntityArrayList.add(Chandivali);
        latLongListEntityArrayList.add(Bandra);
        latLongListEntityArrayList.add(Chembur);
        latLongListEntityArrayList.add(Worli);
        latLongListEntityArrayList.add(Bhandup);
        latLongListEntityArrayList.add(Goregaon);
        latLongListEntityArrayList.add(Kandivali);
        latLongListEntityArrayList.add(Mulund);
        latLongListEntityArrayList.add(Vikhroli);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(latLongListEntityArrayList);
        Log.d("list", stringBuilder.toString());
        LatLng n = null;
        int i = 0;
        while (true) {
            LatLongListEntity entity22 = entity2;
            if (i < latLongListEntityArrayList.size()) {
                entity = (LatLongListEntity) latLongListEntityArrayList.get(i);
                LatLongListEntity entity12 = entity1;
                LatLongListEntity entity32 = entity3;
                LatLongListEntity Powai2 = Powai;
                LatLongListEntity Chandivali2 = Chandivali;
                n = new LatLng(entity.getLat(), entity.getLongs());
                this.mMap.addMarker(new MarkerOptions().position(n).title(entity.getPlace()));
                //this.mMap.addMarker(new MarkerOptions().position(n).icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(this, R.drawable.man, "Manish")))).setTitle("iPragmatech Solutions Pvt Lmt");
                i++;
                entity2 = entity22;
                entity1 = entity12;
                entity3 = entity32;
                Powai = Powai2;
                Chandivali = Chandivali2;
            } else {
                this.mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(n, 10.0f));
                return;
            }
        }
    }

 /*   public static Bitmap createCustomMarker(Context context, int resource, String _name) {
        View marker = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.custom_marker_layout, null);
        ((CircleImageView) marker.findViewById(R.id.user_dp)).setImageResource(resource);
        ((TextView) marker.findViewById(R.id.name)).setText(_name);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        marker.setLayoutParams(new LayoutParams(52, -2));
        marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(marker.getMeasuredWidth(), marker.getMeasuredHeight(), Config.ARGB_8888);
        marker.draw(new Canvas(bitmap));
        return bitmap;
    }

    private String getUrl(LatLng origin, LatLng dest, String direction) {
        String strOrigin = new StringBuilder();
        strOrigin.append("origin=");
        strOrigin.append(origin.latitude);
        String str = ",";
        strOrigin.append(str);
        strOrigin.append(origin.longitude);
        strOrigin = strOrigin.toString();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("dest=");
        stringBuilder.append(dest.latitude);
        stringBuilder.append(str);
        stringBuilder.append(dest.longitude);
        str = stringBuilder.toString();
        String mode = new StringBuilder();
        mode.append("mode=");
        mode.append(direction);
        mode = mode.toString();
        String parameter = new StringBuilder();
        parameter.append(strOrigin);
        String str2 = "&";
        parameter.append(str2);
        parameter.append(str);
        parameter.append(str2);
        parameter.append(mode);
        parameter = parameter.toString();
        String url = new StringBuilder();
        url.append("https://maps.googleapis.com/maps/api/directions/");
        url.append("json");
        url.append("?");
        url.append(parameter);
        url.append("&key=");
        url.append(getString(R.string.google_maps_key));
        return url.toString();
    }*/

    public void onClick(View v) {
        if (v.getId() == R.id.btnMap) {
            StartMapActivity();
        }
    }

    private void StartMapActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void onTaskDone(Object... values) {
        Polyline polyline = this.currentPolygon;
        if (polyline != null) {
            polyline.remove();
        }
        this.currentPolygon = this.mMap.addPolyline((PolylineOptions) values[0]);
    }
}
