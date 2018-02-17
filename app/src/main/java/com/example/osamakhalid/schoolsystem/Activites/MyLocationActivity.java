package com.example.osamakhalid.schoolsystem.Activites;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.example.osamakhalid.schoolsystem.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.osamakhalid.schoolsystem.Model.LocationPoints;
import java.io.IOException;
import java.util.List;

public class MyLocationActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    double latitude = 0.0, longitude = 0.0;
    GoogleApiClient googleApiClient;
    FirebaseDatabase database;
    DatabaseReference reference,deleteref;
    Marker mMarker=null;
    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        database= FirebaseDatabase.getInstance();
        reference= database.getReference().child("points");
        deleteref= database.getReference();
        deleteref.child("points").removeValue();

        // locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ActivityCompat.requestPermissions(MyLocationActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1002);
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            System.out.println("checkingg return");
            return;
        }


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (googleApiClient == null) {

            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
                    .build();
        }
        googleApiClient.connect();

        // Add a marker in Sydney and move the camera
        // LatLng sydney = new LatLng(-34, 151);
        // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        /*
        mLocation = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        if(mLocation.isProviderEnabled(mLocation.NETWORK_PROVIDER)){

            mLocation.requestLocationUpdates(mLocation.NETWORK_PROVIDER, 1, 1, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    reference.push().setValue(new LocationPoints(location.getLatitude(),location.getLongitude()));
                    if(latitude!=0.0 && longitude!=0.0) {
                        System.out.println("checkingg Network");
                        mMap.addPolyline(new PolylineOptions().add(new LatLng(latitude, longitude),
                                new LatLng(location.getLatitude(), location.getLongitude()))
                                .width(5)
                                .color(Color.BLUE)
                        );
                    }
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();

                    LatLng latLng = new LatLng(latitude, longitude);
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                        if(mMarker!=null){
                            mMarker.remove();
                        }
                        mMarker=mMap.addMarker(new MarkerOptions().position(latLng).title(addressList.get(0).getLocality() + "," + addressList.get(0).getLocality()));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20.2f));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });

        }else if(mLocation.isProviderEnabled(mLocation.GPS_PROVIDER)){

            mLocation.requestLocationUpdates(mLocation.GPS_PROVIDER, 1, 1, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    reference.push().setValue(new LocationPoints(location.getLatitude(),location.getLongitude()));
                    if(latitude!=0.0 && longitude!=0.0) {
                        System.out.println("checkingg Network");
                        mMap.addPolyline(new PolylineOptions().add(new LatLng(latitude, longitude),
                                new LatLng(location.getLatitude(), location.getLongitude()))
                                .width(5)
                                .color(Color.BLUE)
                        );
                    }
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();

                    LatLng latLng = new LatLng(latitude, longitude);
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                        if(mMarker!=null){
                            mMarker.remove();
                        }
                        mMarker=mMap.addMarker(new MarkerOptions().position(latLng).title(addressList.get(0).getLocality() + "," + addressList.get(0).getLocality()));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20.2f));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }


*/
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ActivityCompat.requestPermissions(MyLocationActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1002);
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            System.out.println("checkingg return");
            return;
        }

        HandlerThread handlerThread = new HandlerThread("MyHandlerThread");
        handlerThread.start();
        // Now get the Looper from the HandlerThread
        // NOTE: This call will block until the HandlerThread gets control and initializes its Looper
        Looper looper = handlerThread.getLooper();
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setSmallestDisplacement(0);
        locationRequest.setInterval(1000);
        //locationRequest.setNumUpdates(3);
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, new com.google.android.gms.location.LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {
                reference.push().setValue(new LocationPoints(location.getLatitude(),location.getLongitude()));
                System.out.println("checkingg lat="+location.getLatitude()+", lang="+location.getLongitude());
                MyLocationActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(latitude!=0.0 && longitude!=0.0) {
                            System.out.println("checkingg Network");
                            mMap.addPolyline(new PolylineOptions().add(new LatLng(latitude, longitude),
                                    new LatLng(location.getLatitude(), location.getLongitude()))
                                    .width(5)
                                    .color(Color.BLUE)
                            );
                        }
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();

                        LatLng latLng = new LatLng(latitude, longitude);
                        Geocoder geocoder = new Geocoder(getApplicationContext());
                        try {
                            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                            if(mMarker!=null){
                                mMarker.remove();
                            }
                            mMarker=mMap.addMarker(new MarkerOptions().position(latLng).title(addressList.get(0).getLocality() + "," + addressList.get(0).getLocality()));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20.2f));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        },looper);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
