package com.info.advanztest.fragments;
import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.dbagv_000.advanz101test.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.info.advanztest.dbfunctions.LocationDBHelper;
import com.info.advanztest.dbfunctions.LocationsContentProvider;
import com.info.advanztest.utility.CommonFunctions;
import com.info.advanztest.utility.Constants;
public class MapFragment extends Fragment implements OnMapReadyCallback ,GoogleMap.OnMapLongClickListener{
    GoogleMap googleMap;
    SupportMapFragment mapFragment;
    private static View rootView;

    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)parent.removeView(rootView);
        }
        try {
            rootView = inflater.inflate(R.layout.fragment_map, container, false);
        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }
        initializeViews();
        return rootView;

    }


    public void initializeViews() {
            mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_locations);
            mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap=googleMap;
        if (googleMap != null) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            LatLng currentLatLng=new LatLng(Constants.current_lat,Constants.current_lng);
            googleMap.setMyLocationEnabled(true);
            googleMap.setOnMapLongClickListener(this);
            CommonFunctions.zoomToLocation(currentLatLng,googleMap,13);
            CommonFunctions.addDraggingMarker(googleMap,currentLatLng,"Current Location");
        }
    }


    @Override
    public void onMapLongClick(final LatLng latLng) {
        googleMap.clear();
        LatLng currentLatLng=new LatLng(Constants.current_lat,Constants.current_lng);
        CommonFunctions.addDraggingMarker(googleMap,currentLatLng,"Current Location");
        googleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .draggable(true));

        Snackbar snackbar = Snackbar
                .make(rootView, R.string.create_alert_for_location, Snackbar.LENGTH_LONG)
                .setDuration(2000)
                .setAction(R.string.create, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        saveLocation(latLng);
                    }
                });
        snackbar.setActionTextColor(Color.WHITE);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }

// Save Location Functions...
    public void saveLocation(LatLng point){
        ContentValues contentValues = new ContentValues();
        contentValues.put(LocationDBHelper.FIELD_LAT, point.latitude );
        contentValues.put(LocationDBHelper.FIELD_LNG, point.longitude);
        contentValues.put(LocationDBHelper.FIELD_ZOOM, googleMap.getCameraPosition().zoom);
        LocationInsertTask insertTask = new LocationInsertTask();
        insertTask.execute(contentValues);
    }
    private class LocationInsertTask extends AsyncTask<ContentValues, Void, Void> {
        @Override
        protected Void doInBackground(ContentValues... contentValues) {
            /** Setting up values to insert the clicked location into SQLite database */
            getActivity().getContentResolver().insert(LocationsContentProvider.CONTENT_URI, contentValues[0]);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getContext(), R.string.location_saved, Toast.LENGTH_SHORT).show();
        }
    }
}
