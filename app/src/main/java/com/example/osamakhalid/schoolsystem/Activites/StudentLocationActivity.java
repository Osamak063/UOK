package com.example.osamakhalid.schoolsystem.Activites;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.osamakhalid.schoolsystem.Model.LocationPoints;
import com.example.osamakhalid.schoolsystem.R;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;

public class StudentLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ChildEventListener childEventListener;
    FirebaseDatabase database;
    DatabaseReference reference;
    Marker mMarker=null;
    double latitude = 0.0, longitude = 0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        database= FirebaseDatabase.getInstance();
        reference= database.getReference().child("points");
        childEventListener= new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot!=null){
                    if(latitude!=0.0 && longitude!=0.0) {
                        System.out.println("checkingg Network");
                        mMap.addPolyline(new PolylineOptions().add(new LatLng(latitude, longitude),
                                new LatLng(dataSnapshot.getValue(LocationPoints.class).getLatitude(), dataSnapshot.getValue(LocationPoints.class).getLongitude()))
                                .width(5)
                                .color(Color.BLUE)
                        );
                    }
                    latitude = dataSnapshot.getValue(LocationPoints.class).getLatitude();
                    longitude = dataSnapshot.getValue(LocationPoints.class).getLongitude();
                    LatLng latLng = new LatLng(latitude, longitude);
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                        if(mMarker!=null){
                            mMarker.remove();
                        }
                        mMarker= mMap.addMarker(new MarkerOptions().position(latLng).title(addressList.get(0).getLocality() + "," + addressList.get(0).getLocality()));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20.2f));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        reference.addChildEventListener(childEventListener);
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
    }

}
