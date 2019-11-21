package com.example.lab2_digitalidcard.ui.map;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab2_digitalidcard.R;
import com.example.lab2_digitalidcard.adapter.MapListAdapter;
import com.example.lab2_digitalidcard.data.model.UserProfile;
import com.example.lab2_digitalidcard.utility.PermissionUtils;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.ButterKnife;

public class MapFragment extends Fragment implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,OnMapReadyCallback,MapListAdapter.ItemClickListener {

    private GoogleMap mMap;
    final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2;

    boolean mLocationPermissionGranted = false;
    Location mLastKnownLocation = null;
    private LatLngBounds AUSTRALIA = new LatLngBounds(
            new LatLng(-44, 113), new LatLng(-10, 154));

    FusedLocationProviderClient mFusedLocationProviderClient = null;

    PlacesClient placesClient;

    AutocompleteSupportFragment autocompleteFragment;

    MapView mapView;

    List<Place> SuggestedPlace = new ArrayList<>();

    EditText place_input;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_maps,container,false);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());



        mapView = v.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        place_input = v.findViewById(R.id.place_input);
        recyclerView = v.findViewById(R.id.rvAnimals);

/*        // Initialize the AutocompleteSupportFragment.
        autocompleteFragment = (AutocompleteSupportFragment) getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));*/


        Places.initialize(getActivity(),getResources().getString(R.string.google_maps_key));
        placesClient = Places.createClient(getActivity());


        searchBarHandle();

        return v;
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    //4. rank by review

    public void searchBarHandle(){

        place_input.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();

                    // Create a RectangularBounds object.
                    LatLngBounds bound = mMap.getProjection().getVisibleRegion().latLngBounds;

                    RectangularBounds bounds = RectangularBounds.newInstance(bound.southwest, bound.northeast);

                    // Use the builder to create a FindAutocompletePredictionsRequest.

                    FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                            .setLocationBias(bounds)
                            //.setLocationRestriction(bounds)
                            .setCountry("us")
                            .setTypeFilter(TypeFilter.ESTABLISHMENT)
                            .setSessionToken(token)
                            .setQuery(v.getText().toString())
                            .build();

                    placesClient.findAutocompletePredictions(request).addOnSuccessListener((response) -> {
                        if(response.getAutocompletePredictions().size()<=0){
                            SuggestedPlace.clear();
                            createrecyclerView();
                        }
                        for (AutocompletePrediction prediction : response.getAutocompletePredictions()) {
                            Log.i("maptag", prediction.getPlaceId());
                            Log.i("maptag", prediction.getPrimaryText(null).toString());

                            SuggestedPlace.clear();
                            List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.RATING);
                            FetchPlaceRequest fetchPlaceRequest = FetchPlaceRequest.newInstance(prediction.getPlaceId(), placeFields);
                            placesClient.fetchPlace(fetchPlaceRequest).addOnSuccessListener((fetchPlaceResponse) -> {
                                Place placeTMP = fetchPlaceResponse.getPlace();
                                Log.i("maptag", "Place found: " + placeTMP.getName());

                                mMap.addMarker(new MarkerOptions().position(placeTMP.getLatLng())
                                        .title(prediction.getPrimaryText(null).toString())
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                                        .draggable(false).visible(true));
                                mapView.invalidate();

                                SuggestedPlace.add(placeTMP);
                                createrecyclerView();

                            }).addOnFailureListener((exception) -> {
                                if (exception instanceof ApiException) {
                                    ApiException apiException = (ApiException) exception;
                                    int statusCode = apiException.getStatusCode();
                                    // Handle error with given status code.
                                    Log.e("maptag", "Place not found: " + exception.getMessage());
                                }
                            });
                        }
                    }).addOnFailureListener((exception) -> {
                        if (exception instanceof ApiException) {
                            ApiException apiException = (ApiException) exception;
                            Log.e("maptag", "Place not found: " + apiException.getStatusCode());
                        }
                    });
                }
                return false;
            }
        });

        // Set up a PlaceSelectionListener to handle the response.
        /*autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {

            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("maptag", "Place: " + place.getName() + ", " + place.getId());
                // Create a new token for the autocomplete session. Pass this to FindAutocompletePredictionsRequest,
                // and once again when the user makes a selection (for example when calling fetchPlace()).
                AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();

                // Create a RectangularBounds object.
                RectangularBounds bounds = RectangularBounds.newInstance(
                        new LatLng(-33.880490, 151.184363),
                        new LatLng(-33.858754, 151.229596));
                // Use the builder to create a FindAutocompletePredictionsRequest.

                FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                        .setLocationBias(bounds)
                        //.setLocationRestriction(bounds)
                        .setCountry("us")
                        .setTypeFilter(TypeFilter.ADDRESS)
                        .setSessionToken(token)
                        .setQuery("Coffee")
                        .build();

                placesClient.findAutocompletePredictions(request).addOnSuccessListener((response) -> {
                    for (AutocompletePrediction prediction : response.getAutocompletePredictions()) {
                        Log.i("maptag", prediction.getPlaceId());
                        Log.i("maptag", prediction.getPrimaryText(null).toString());

                        List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
                        FetchPlaceRequest fetchPlaceRequest = FetchPlaceRequest.newInstance(prediction.getPlaceId(), placeFields);
                        placesClient.fetchPlace(fetchPlaceRequest).addOnSuccessListener((fetchPlaceResponse) -> {
                            Place placeTMP = fetchPlaceResponse.getPlace();
                            Log.i("maptag", "Place found: " + placeTMP.getName());

                            mMap.addMarker(new MarkerOptions().position(placeTMP.getLatLng())
                                    .title(prediction.getPrimaryText(null).toString())
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                                    .draggable(false).visible(true));
                            mapView.invalidate();
                        }).addOnFailureListener((exception) -> {
                            if (exception instanceof ApiException) {
                                ApiException apiException = (ApiException) exception;
                                int statusCode = apiException.getStatusCode();
                                // Handle error with given status code.
                                Log.e("maptag", "Place not found: " + exception.getMessage());
                            }
                        });
                    }
                }).addOnFailureListener((exception) -> {
                    if (exception instanceof ApiException) {
                        ApiException apiException = (ApiException) exception;
                        Log.e("maptag", "Place not found: " + apiException.getStatusCode());
                    }
                });

                String placeId = String.valueOf(place.getId());

                List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS);
                FetchPlaceRequest request1 = FetchPlaceRequest.builder(placeId, placeFields).build();
                placesClient.fetchPlace(request1).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
                    @Override
                    public void onSuccess(FetchPlaceResponse response) {
                        Place place = response.getPlace();
                        String add = place.getAddress();
                        String pp = place.getPhoneNumber();
                        //clickListener.click(place);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        if (exception instanceof ApiException) {
                            ApiException apiException = (ApiException) exception;
                            Log.e("maptag", "Place not found: " + apiException.getStatusCode());
                        }
                    }
                });

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("maptag", "An error occurred: " + status);
            }
        });*/
    }

    private RecyclerView recyclerView;
    private MapListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public void createrecyclerView(){
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView

        if(SuggestedPlace.size()>0){
            recyclerView.setVisibility(View.VISIBLE);
            Collections.sort(SuggestedPlace, new Comparator<Place>(){

                @Override
                public int compare(Place place, Place t1) {
                    if(place != null && t1!=null) {
                        return place.getRating().compareTo(t1.getRating());
                    }else return 0;

                }
            });
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SuggestedPlace.get(0).getLatLng(), 10));
        }else{
            recyclerView.setVisibility(View.GONE);
        }

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


        // specify an adapter (see also next example)
        mAdapter = new MapListAdapter(getContext(), SuggestedPlace);
        mAdapter.setClickListener(this);
        recyclerView.setAdapter(mAdapter);

    }

    public void listSuggestionClicked(String placeId){
        List<Place.Field> placeFields = Arrays.asList(Place.Field.LAT_LNG);

        FetchPlaceRequest fetchPlaceRequest = FetchPlaceRequest.builder(placeId, placeFields).build();
        placesClient.fetchPlace(fetchPlaceRequest).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
            @Override
            public void onSuccess(FetchPlaceResponse fetchPlaceResponse) {
                Place place = fetchPlaceResponse.getPlace();
                Log.i("mytag", "Place found: " + place.getName());
                LatLng latLngOfPlace = place.getLatLng();
                if (latLngOfPlace != null) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngOfPlace, 10));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ApiException) {
                    ApiException apiException = (ApiException) e;
                    apiException.printStackTrace();
                    int statusCode = apiException.getStatusCode();
                    Log.i("mytag", "place not found: " + e.getMessage());
                    Log.i("mytag", "status code: " + statusCode);
                }
            }
        });
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();

    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(getActivity(), LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
            mLocationPermissionGranted = true;
        }
    }


    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                enableMyLocation();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Task locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(getActivity(), new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = (Location) task.getResult();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()),10));
                        } else {
                            //Log.d(TAG, "Current location is null. Using defaults.");
                            //Log.e(TAG, "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(AUSTRALIA.getCenter(), 10));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch(SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(getActivity(), "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(getActivity(), "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(View view, int position) {
        Place p = SuggestedPlace.get(position);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(p.getLatLng(), 10));
        mMap.addMarker(new MarkerOptions().position(p.getLatLng())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .draggable(false).visible(true));
    }
}
