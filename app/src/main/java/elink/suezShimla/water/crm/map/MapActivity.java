package elink.suezShimla.water.crm.map;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import elink.suezShimla.water.crm.Complaint.MapScreen.Model.LatLongModel;
import elink.suezShimla.water.crm.Database.RealmOperations;
import elink.suezShimla.water.crm.R;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private Context mCon;
    private RealmOperations realmOperations;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private SettingsClient mSettingsClient;
    protected LocationRequest mLocationRequest;
    protected LocationSettingsRequest mLocationSettingsRequest;
    protected Boolean mRequestingLocationUpdates;
    private Location mCurrentLocation, location;
    private FusedLocationProviderClient mFusedLocationClient;
    private Marker mCurrLocationMarker;

    private List<LatLongModel> LatLongModelList = new ArrayList<>();
//    private List<LatLongListEntity> LatLongModelList = new ArrayList<>();

    private String[] LatArray = {"19.1136", "19.1031", "19.2115"};
    private String[] LongArray = {"72.8697", "72.8467", "72.8737"};

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 12;
    private static final int REQUEST_PERMISSION_SETTING = 2;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    private static final int REQUEST_ENABLE_GPS = 516;
    private boolean moveCamera = false;

    MarkerOptions placeOne;
    MarkerOptions placeTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // prevent ss and hide content when app is on background
     //   getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mCon = this;
        realmOperations = new RealmOperations(mCon);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getLocation();

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

//        this.placeOne = new MarkerOptions().position(new LatLng(19.115492d, 72.872696d)).title("Andheri");
//        this.placeTwo = new MarkerOptions().position(new LatLng(19.228825d, 72.854118d)).title("Borivali");
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }

    private void getLocation() {
        final boolean result = checkPermission(mCon);
        if (result) {
            buildGoogleApiClient();
            createLocationRequest();
            buildLocationSettingsRequest();
            checkLocationSettings();
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    public boolean checkPermission(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {

                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mCon);

                   //android.support.v7.app.AlertDialog.Builder alertBuilder = new android.support.v7.app.AlertDialog.Builder(context);
                    alertBuilder.setCancelable(false);
                    alertBuilder.setTitle(R.string.permission_necessary);
                    alertBuilder.setMessage(R.string.location_permission_is_necessary);
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (ContextCompat.checkSelfPermission(mCon,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {

                    if (mGoogleApiClient == null) {
                        buildGoogleApiClient();
                    }
                }

            } else {
                boolean showRationale = false;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    showRationale = shouldShowRequestPermissionRationale(permissions[0]);
                }
                if (!showRationale) {

                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mCon);
                    alertBuilder.setCancelable(false);
                    alertBuilder.setTitle(R.string.permission_necessary);
                    alertBuilder.setMessage(R.string.location_permission_is_necessary);
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", mCon.getPackageName(), null);
                            intent.setData(uri);
                            startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    Toast.makeText(mCon, R.string.permissionDenied, Toast.LENGTH_LONG).show();
                    getLocation();
                }
                // checkLocationPermission();
            }
        }
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    private void checkLocationSettings() {
        Log.d("check", "checkLocationSettings");
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY));
        builder.setAlwaysShow(true);
        mLocationSettingsRequest = builder.build();

        mSettingsClient = LocationServices.getSettingsClient(MapActivity.this);

        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        if (mGoogleApiClient.isConnected()) {
                            startLocationUpdates();
                        } else {
                            buildGoogleApiClient();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                try {
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(MapActivity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.e("GPS", "Unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                Log.e("GPS", "Location settings are inadequate, and cannot be fixed here. Fix in Settings.");
                                openGpsEnableSetting();
                        }
                    }
                })
                .addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        Log.e("GPS", "checkLocationSettings -> onCanceled");
                    }
                });
    }

    private void openGpsEnableSetting() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(intent, REQUEST_ENABLE_GPS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    startLocationUpdates();
                    break;
                case Activity.RESULT_CANCELED:
                    checkLocationSettings();
                    break;
            }
        } else if (requestCode == REQUEST_PERMISSION_SETTING) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    detectLocation();
                    break;
                case Activity.RESULT_CANCELED:
                    getLocation();
                    break;
                default:
                    break;
            }
        }
    }

    protected void startLocationUpdates() {
        detectLocation();
    }

    private void detectLocation() {
        if (ContextCompat.checkSelfPermission(mCon,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                mRequestingLocationUpdates = true;
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        } else {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
            mRequestingLocationUpdates = true;
        }
    }

    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {
                //The last location in the list is the newest
                location = locationList.get(locationList.size() - 1);

                mCurrentLocation = location;

                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker.remove();
                }
            }
        }
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {


        GoogleMap googleMap2 = googleMap;
        LatLng n = null;
        this.mMap = googleMap2;

        ArrayList<LatLongModel> latLongListEntityArrayList = new ArrayList();
        latLongListEntityArrayList.clear();
        LatLongModel isLatLongModelExist = realmOperations.getLatLongExist();

        if (isLatLongModelExist != null) {
            LatLongModelList = realmOperations.fetchLatLong();
//            LatLongListEntity entity = new LatLongListEntity(LatLongModel.getLat(), LatLongModel.getLon(), LatLongModel.getTitle());

            for(int i = 0; i < LatLongModelList.size(); i++){
                LatLongModel latLongListEntity = LatLongModelList.get(i);
                latLongListEntityArrayList.add(latLongListEntity);
                Double lat = Double.valueOf(latLongListEntity.getLat());
                Double longi = Double.valueOf(latLongListEntity.getLon());
                n = new LatLng(lat , longi);

                CameraPosition googlePlex = CameraPosition.builder().target(new LatLng(lat, longi)).zoom(11.0f).bearing(0.0f).tilt(45.0f).build();
                this.mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 5000, null);
                this.mMap.addMarker(new MarkerOptions().position(n).title(latLongListEntity.getTitle()));
            }


        } else {
            Toast.makeText(mCon, R.string.no_data_found, Toast.LENGTH_SHORT).show();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(latLongListEntityArrayList);
        Log.d("list", stringBuilder.toString());

    }

    @Override
    public void onConnected(Bundle bundle) {
        if (mCurrentLocation == null) {
            if (ContextCompat.checkSelfPermission(mCon,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {

                    mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                    updateLocationUI();
                }
            }
        }
    }

    private void updateLocationUI() {
        if (mCurrentLocation != null) {

            final boolean mock = isMockSettingsON(mCon, mCurrentLocation);
            if (mock) {
                Toast.makeText(mCon, R.string.turnOffMockLocation, Toast.LENGTH_SHORT).show();
                //finish();
            } else {
                startLocationUpdates();
            }
        }
    }

    public static boolean isMockSettingsON(Context context, Location location) {
        return location.isFromMockProvider(); //should use location object to detect mock location
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    private void getLocation(String Lat, String Long, String title) {
        double dLat = Double.parseDouble(Lat);
        double dLong = Double.parseDouble(Long);

        LatLng latLng = new LatLng(dLat, dLong);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.alpha(0.8f);
        markerOptions.title(title);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mMap.addMarker(markerOptions);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    public void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        } else {

        }
    }

}
