package com.info.advanztest.utility;

import android.content.Context;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by dbagv_000 on 4/27/2017.
 */
public class CommonFunctions {

    public static void zoomToLocation(LatLng source, GoogleMap googleMap, float zoom) {
        if(source!=null&&googleMap!=null) {
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(source, zoom);
            googleMap.animateCamera(cameraUpdate);
        }
    }

    public static void addDraggingMarker(GoogleMap googleMap, LatLng latLng, String title) {
        if(latLng!=null&&googleMap!=null){
            MarkerOptions options = new MarkerOptions();
            options.position(latLng);
            options.title(title);
           // options.icon(BitmapDescriptorFactory.fromBitmap(ResourceUtil.getBitmap(context, iconID)));
            options.icon(BitmapDescriptorFactory.defaultMarker());
            googleMap.addMarker(options).setDraggable(true);
        }

    }

}
