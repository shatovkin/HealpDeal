package com.example.dimitri.helpdeal.azureClasses.activities;

import android.support.v4.app.FragmentActivity;

public class GoogleMapsActivity extends FragmentActivity  {


  /*  private GoogleMap mMap;
    static Location imHere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_maps_layout);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googlemap);
        mapFragment.getMapAsync(this);
    }


    protected void OnResume() {
        super.onResume();

        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (mMap == null) {
            ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googlemap)).getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mMap = googleMap;
                    setUpMap();
                }
            });
        } else {
            setUpMap();
        }
    }

    private void setUpMap() {
        ArrayList<LatLng> listPositions = new ArrayList<LatLng>();

        listPositions.add(new LatLng(-33.866, 151.195)) ;// Sydney
        listPositions.add(new LatLng(-18.142, 178.431));  // Fiji
        listPositions   .add(new LatLng(21.291, -157.821));  // Hawaii
        listPositions .add(new LatLng(37.423, -122.091)) ; // Mountain View

        for (LatLng position: listPositions) {
            mMap.addMarker(new MarkerOptions().position(position).title("Marker in Sydney"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
        }
    }


    public void setUpMap(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera
        ArrayList<LatLng> listPositions = new ArrayList<LatLng>();

        listPositions.add(new LatLng(-33.866, 151.195)) ;// Sydney
        listPositions.add(new LatLng(-18.142, 178.431));  // Fiji
        listPositions   .add(new LatLng(21.291, -157.821));  // Hawaii
        listPositions .add(new LatLng(37.423, -122.091)) ; // Mountain View

        for (LatLng position: listPositions) {
            mMap.addMarker(new MarkerOptions().position(position).title("Marker in Sydney"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

    }

    public void defineTheDistance(String PLZ1,String PLZ2)
    {
        String locationName1 = PLZ1 + ", " + "Germany";
        String locationName2 = PLZ2 + ", " + "Germany";
        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());

    }*/
}
