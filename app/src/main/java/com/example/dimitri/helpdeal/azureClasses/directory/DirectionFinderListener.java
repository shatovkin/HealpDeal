package com.example.dimitri.helpdeal.azureClasses.directory;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Mai Thanh Hiep on 4/3/2016.
 */
public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
