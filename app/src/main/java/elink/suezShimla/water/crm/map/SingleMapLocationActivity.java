package elink.suezShimla.water.crm.map;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.clustering.ClusterManager;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import de.hdodenhof.circleimageview.CircleImageView;
import elink.suezShimla.water.crm.AesAlgorithm.AesAlgorithm;
import elink.suezShimla.water.crm.Complaint.Allocation.Activity.WorkAllocationActivity;
import elink.suezShimla.water.crm.Complaint.Allocation.Activity.WorkAllocationCompletionActivity;
import elink.suezShimla.water.crm.Complaint.Reallocation.Activity.WorkReAllocationCompletionActivity;
import elink.suezShimla.water.crm.Complaint.Reallocation.Activity.WorkReallocationActivity;
import elink.suezShimla.water.crm.Complaint.TodaysCompletedWork.TodayWorkCompletedActivity;
import elink.suezShimla.water.crm.Complaint.WorkCompletion.Activity.WorkCompletedActivity;
import elink.suezShimla.water.crm.ConnectionManagement.activity.SiteVisitListActivity;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.MainActivity;
import elink.suezShimla.water.crm.MeterManagementSystem.MeterInstallation.MeterInstallationActivity;
import elink.suezShimla.water.crm.MeterManagementSystem.MeterInstallation.MeterInstallationAutheticatedActivity;
import elink.suezShimla.water.crm.MeterManagementSystem.MeterInstallation.MeterInstallationSentAutheticationActivity;
import elink.suezShimla.water.crm.MeterManagementSystem.ViewContractorDetails.MeterInstallationContractorDetails;
import elink.suezShimla.water.crm.R;
import elink.suezShimla.water.crm.Splash.SplashScreen;
import elink.suezShimla.water.crm.Utils.MarkerClusterRenderer;
import elink.suezShimla.water.crm.Utils.UtilitySharedPreferences;
import elink.suezShimla.water.crm.constant.AppConstant;
import elink.suezShimla.water.crm.map.entity.LatLongListEntity;


public class SingleMapLocationActivity extends FragmentActivity implements OnMapReadyCallback, OnClickListener {
    Button btnMap;
    Polyline currentPolygon;
    private GoogleMap mMap;
    MarkerOptions placeOne;
    MarkerOptions placeTwo;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
 //   private List<ConsumersModel> consumersModels = new ArrayList<>();
    private RealmOperations realmOperations;
    private Context mCon;
    String timeStamp = "", AppVersion = "", empCode = "", RDRID = "", deviceAuthorization = "", appIsLogged = "";
    AppCompatTextView tv_toolbar;
    double lat, longi;
    LatLongListEntity latLongListEntity;
    ArrayList<LatLongListEntity> latLongListEntityArrayList = new ArrayList();


    private int consumerID, readingFlag, positionId,searchCheck;
    String consumerNum = "", Tag = "", strLat = "", strLon = "",
            strAddress="",consumerName="",tag="",fromDate="",toDate="",clickStr="",comseq="",comPno="";

    ImageView iv_sattelight,iv_back;
    boolean mapSwitch = true;

    private KeyGenerator keyGenerator;
    private SecretKey secretKey;
    private byte[] IV = new byte[16];
    private SecureRandom random;
    AesAlgorithm aes;

    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // prevent ss and hide content when app is on background
     //   getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            // AES Algorithm for encryption / decryption

            keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            secretKey = keyGenerator.generateKey();

            random = new SecureRandom();
            random.nextBytes(IV);

            aes=new AesAlgorithm();

        } catch (Exception e) {
            e.printStackTrace();
        }

        init();

        fetchLocation();


    }

    private void init() {

        iv_sattelight = findViewById(R.id.iv_sattelight);
        iv_sattelight.setOnClickListener(this);
        tv_toolbar = findViewById(R.id.tv_toolbar);
        tv_toolbar.setText(getResources().getString(R.string.consumer_location));

        mCon = SingleMapLocationActivity.this;

        realmOperations = new RealmOperations(mCon);

       try {
           AppVersion = UtilitySharedPreferences.getPrefs(mCon, AppConstant.APPVERSION);
           AppVersion = aes.decrypt( AppVersion);
            empCode = UtilitySharedPreferences.getPrefs(mCon, AppConstant.EMPCODE);
            empCode = aes.decrypt( empCode);
        } catch (Exception e) {
            e.printStackTrace();
        }


        RDRID = UtilitySharedPreferences.getPrefs(mCon, AppConstant.DOWNLOADEDREADERID);

        deviceAuthorization = UtilitySharedPreferences.getPrefs(this, AppConstant.DEVICEAUTHORIZATION);
//        deviceAuthorization = aes.decrypt( deviceAuthorization.getBytes(), secretKey, IV);

        appIsLogged = UtilitySharedPreferences.getPrefs(this, AppConstant.APP_ISLOGGED);


        Intent startingIntent = getIntent();
        strLat = startingIntent.getStringExtra("locationLat");
        strLon = startingIntent.getStringExtra("locationLongi");
        strAddress = startingIntent.getStringExtra("strAddress");
        consumerNum = startingIntent.getStringExtra("consumerNum");
        consumerName = startingIntent.getStringExtra("consumername");
        tag = startingIntent.getStringExtra("tag");

        fromDate = startingIntent.getStringExtra("fromDate");
        toDate = startingIntent.getStringExtra("toDate");
        clickStr = startingIntent.getStringExtra("strClick");
        comseq = startingIntent.getStringExtra("comseq");
        comPno = startingIntent.getStringExtra("comPno");

        latLongListEntityArrayList.clear();

        //Toast.makeText(mCon, strLat+strLon, Toast.LENGTH_SHORT).show();

        if(strLat!=null || strLon!=null) {
            if (strLat.equalsIgnoreCase("") || strLon.equalsIgnoreCase("")) {

            } else {
                double lat = Double.parseDouble(strLat);
                double longs = Double.parseDouble(strLon);

                latLongListEntity = new LatLongListEntity(lat, longs, strAddress, consumerNum, consumerName);
                latLongListEntityArrayList.add(latLongListEntity);


                //LatLongListEntity latLongListEntity = new LatLongListEntity(lat, , "Andheri");


            }
        }
        Log.d("list", "" + latLongListEntityArrayList);
        int a = 0;
    }


    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;


                    lat = currentLocation.getLatitude();
                    longi = currentLocation.getLongitude();
                    //    lat = 11.02564770;
                    // longi = 76.96892380;
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    assert supportMapFragment != null;

                    supportMapFragment.getMapAsync(SingleMapLocationActivity.this);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLocation();
                }
                break;
        }
    }


    public void onMapReady(GoogleMap googleMap) {
        GoogleMap googleMap2 = googleMap;
        this.mMap = googleMap2;
        // Toast.makeText(getApplicationContext(), lat + "" + longi, Toast.LENGTH_SHORT).show();
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        LatLng latLng = new LatLng(lat, longi);
        // MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I am here!");
        this.mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(this, R.drawable.man, "I am Here")))).setTitle("I am Here");

        setUpClusterManager(googleMap);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(latLongListEntityArrayList);
        Log.d("list", stringBuilder.toString());
        LatLng n = null;
        int i = 0;


        while (true) {

            if (i < latLongListEntityArrayList.size()) {
                latLongListEntity = (LatLongListEntity) latLongListEntityArrayList.get(i);

                n = new LatLng(latLongListEntity.getLat(), latLongListEntity.getLongs());
                String title = latLongListEntity.getPlace();

                //this.mMap.addMarker(new MarkerOptions().position(n).title(latLongListEntity.getPlace()).icon(bitmapDescriptorFromVector(this, R.drawable.ic_interface))).setTitle((consumerName));


                //this.mMap.addMarker(new MarkerOptions().position(n).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map)));
                i++;

            } else {
                if (latLongListEntityArrayList.size() == 0) {

                    CameraPosition googlePlex = CameraPosition.builder()
                            .target(new LatLng(lat, longi))
                            .zoom(14)
                            .bearing(0)
                            .tilt(45)
                            .build();

                    this.mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 3000, null);
                    return;
                } else {
                    CameraPosition googlePlex = CameraPosition.builder()
                            .target(new LatLng(latLongListEntity.getLat(), latLongListEntity.getLongs()))
                            .zoom(14)
                            .bearing(0)
                            .tilt(45)
                            .build();

                    this.mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 3000, null);
                    //this.mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(n, 13.0f))
                    //
                    return;
                }
            }
        }
    }
    private void setUpClusterManager(GoogleMap googleMap) {

        ClusterManager<LatLongListEntity> clusterManager = new ClusterManager<>(this, googleMap);

        MarkerClusterRenderer markerClusterRenderer = new MarkerClusterRenderer(this, googleMap, clusterManager);

        clusterManager.setRenderer(markerClusterRenderer);

        List<LatLongListEntity> items = latLongListEntityArrayList;

        clusterManager.addItems(items);

        clusterManager.cluster();
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_interface);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
     //   Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
      //  vectorDrawable.setBounds(40, 20, vectorDrawable.getIntrinsicWidth() + 40, vectorDrawable.getIntrinsicHeight() + 20);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
      //  vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    public static Bitmap createCustomMarker(Context context, @DrawableRes int resource, String _name) {

        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);

        CircleImageView markerImage = (CircleImageView) marker.findViewById(R.id.user_dp);
        markerImage.setImageResource(resource);
        TextView txt_name = (TextView) marker.findViewById(R.id.name);
        txt_name.setText(_name);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        marker.setLayoutParams(new ViewGroup.LayoutParams(32, ViewGroup.LayoutParams.WRAP_CONTENT));
        marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(marker.getMeasuredWidth(), marker.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        marker.draw(canvas);

        return bitmap;
    }


    public void onClick(View v) {
        if (v.getId() == R.id.iv_sattelight) {
            if (mapSwitch == true) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                mapSwitch=false;


            }else {
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                mapSwitch=true;

            }
            // StartMapActivity();
        }

        if (v.getId() == R.id.iv_back) {
           // startBackActivity();

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


    public void deviceAuthorizedDialog() {

        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(R.string.alert)
                .titleColorRes(R.color.red_500)
                .content(R.string.device_authorization)
                .contentColor(this.getResources().getColor(R.color.colorPrimary))
                .canceledOnTouchOutside(false)
                .positiveText(R.string.ok)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        Intent intent = new Intent(SingleMapLocationActivity.this, SplashScreen.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        dialog.dismiss();
                    }
                }).show();
    }



    @Override
    public void onBackPressed() {
        startBackActivity();
        super.onBackPressed();
    }

    private void startBackActivity() {
        if(tag.equalsIgnoreCase("A")) {
            Intent i = new Intent(mCon, MeterInstallationActivity.class);
            i.putExtra("fromDate", fromDate);
            i.putExtra("toDate", toDate);
            startActivity(i);
            finish();
        }else  if(tag.equalsIgnoreCase("P")) {
            Intent i = new Intent(mCon, MeterInstallationSentAutheticationActivity.class);
            i.putExtra("fromDate", fromDate);
            i.putExtra("toDate", toDate);
            startActivity(i);
            finish();
        }else  if(tag.equalsIgnoreCase("S")) {
            Intent i = new Intent(mCon, MeterInstallationContractorDetails.class);
            i.putExtra("fromPage", "savedDetails");
            i.putExtra("fromDate", fromDate);
            i.putExtra("toDate", toDate);
            startActivity(i);
            finish();
        }else  if(tag.equalsIgnoreCase("WRS")) {
            Intent i = new Intent(mCon, MeterInstallationContractorDetails.class);
            i.putExtra("fromPage", "workreallocate");
            i.putExtra("fromDate", fromDate);
            i.putExtra("toDate", toDate);
            startActivity(i);
            finish();
        }else  if(tag.equalsIgnoreCase("W")) {
            Intent i = new Intent(mCon, MeterInstallationAutheticatedActivity.class);
            startActivity(i);
            finish();
        }else  if(tag.equalsIgnoreCase("WA")) {
            Intent i = new Intent(mCon, WorkAllocationActivity.class);
            startActivity(i);
            finish();
        }else  if(tag.equalsIgnoreCase("SV")) {
            Intent i = new Intent(mCon, SiteVisitListActivity.class);
            startActivity(i);
            finish();
        }else  if(tag.equalsIgnoreCase("WR")) {
            Intent i = new Intent(mCon, WorkReallocationActivity.class);
            startActivity(i);
            finish();
        }else  if(tag.equalsIgnoreCase("WC")) {
            Intent i = new Intent(mCon, WorkReAllocationCompletionActivity.class);
            i.putExtra("fromDate", fromDate);
            i.putExtra("toDate", toDate);
            i.putExtra("strClick", clickStr);
            startActivity(i);
            finish();
        }else  if(tag.equalsIgnoreCase("WAC")) {
            Intent i = new Intent(mCon, WorkAllocationCompletionActivity.class);
            i.putExtra("fromDate", fromDate);
            i.putExtra("toDate", toDate);
            i.putExtra("strClick", clickStr);
            startActivity(i);
            finish();
        }else  if(tag.equalsIgnoreCase("WF")) {
            Intent i = new Intent(mCon, WorkCompletedActivity.class);
            i.putExtra("fromDate", fromDate);
            i.putExtra("toDate", toDate);
            i.putExtra("strClick", clickStr);
            startActivity(i);
            finish();
        }else  if(tag.equalsIgnoreCase("TC")) {
            Intent i = new Intent(mCon, TodayWorkCompletedActivity.class);
            i.putExtra("fromDate", fromDate);
            i.putExtra("toDate", toDate);
            i.putExtra("strClick", clickStr);
            startActivity(i);
            finish();
        }else {
            Intent i = new Intent(mCon, MainActivity.class);
            startActivity(i);
            finish();
        }
    }
}
