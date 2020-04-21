package com.example.jghafo.trafficscotlandapp.MoreInfoFragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.jghafo.trafficscotlandapp.R;
import com.example.jghafo.trafficscotlandapp.UserInterfaces.CurrentRoadworks.RSSFeed;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

//Name: Jabbar Ghafoor
//Student ID: S1514090

public class GoogleMapsApiFragment extends Fragment implements GoogleMap.OnMarkerClickListener {


    int Position;
    List<RSSFeed> data;
    List<LatLng> latLngList;

    LatLng view_location_at_first;

    String startdate;
    String enddate;
    List<MapItems> eventList;
    String desc;
    private TextView eventName, location;
    private TextView startDate, endDate, info;
    private TextView url;
    private TextView pubDate;

    LatLng LatLang;
    LocationRequest locationRequest;
    LocationCallback locationCallback;
    FusedLocationProviderClient fusedLocationProviderClient;
    Location currentLocation;
    private GoogleMap map;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {

            map = googleMap;

            fusedLocationProviderClient.getLastLocation().addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            }).addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {

                    for (int i = 0; i < latLngList.size(); i++) {
                        map.addMarker(new MarkerOptions()
                                .position(latLngList.get(i))
                                .icon(bitmapDescriptorFromVector(getContext(), R.drawable.ic_place_black_24dp)));

                    }

                    map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                        @Override
                        public View getInfoWindow(Marker marker) {
                            return null;
                        }
                        @Override
                        public View getInfoContents(Marker marker) {

                            View v = getLayoutInflater().inflate(R.layout.map_dialoge, null);
                            eventName = (TextView) v.findViewById(R.id.event);
                            location = (TextView) v.findViewById(R.id.location);
                            startDate = (TextView) v.findViewById(R.id.start);
                            endDate = (TextView) v.findViewById(R.id.end);
                            url = (TextView) v.findViewById(R.id.url);
                            info = (TextView) v.findViewById(R.id.info);
                            pubDate = (TextView) v.findViewById(R.id.datePublished);


                            for (int i = 0; i < eventList.size(); i++) {
                                MapItems item = eventList.get(i);
                                if (item.getLatLng().equals(marker.getPosition())) {

                                    eventName.setText(item.getName());
                                    location.setText("Location: " + item.getLocation());
                                    startDate.setText(item.getStartdate());
                                    endDate.setText(item.getEnddate());
                                    info.setText(item.getInfo());
                                    url.setText("Link: " + item.getUrl());
                                    pubDate.setText("Publication Date: " + item.getPubDate());
                                } else {
                                }
                            }
                            return v;
                        }
                    });


                    LatLng latLng = view_location_at_first;
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                    Marker marker = map.addMarker(new MarkerOptions()
                            .position(latLng)
                            .icon(bitmapDescriptorFromVector(getContext(), R.drawable.ic_place_black_24dp)));
                    marker.showInfoWindow();


                }

            });
        }

    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.maps_fragment, container, false);
        initLocation();

        latLngList = new ArrayList<>();

        if (getActivity().getIntent() != null) {
            String returnStr = getActivity().getIntent().getStringExtra("listItem");
            Gson gson = new Gson();
            Type intentDataType = new TypeToken<List<RSSFeed>>() {
            }.getType();
            data = gson.fromJson(returnStr, intentDataType);
        }
        if (getActivity().getIntent().getIntExtra("pos", 0) >= 0) {
            Position = getActivity().getIntent().getIntExtra("pos", 0);
        }
        eventList = new ArrayList<MapItems>();

        for (int i = 0; i < data.size(); i++) {

            if (data.get(i).getGeorss_point() != null) {
                String latlng = data.get(i).getGeorss_point();
                String[] splited = latlng.split("\\s+");
                double latitude = Double.parseDouble(splited[0]);
                double longitude = Double.parseDouble(splited[1]);

                LatLang = new LatLng(latitude, longitude);

                latLngList.add(LatLang);

            } else {

                LatLang = new LatLng(0, 0);
            }

            if (data.get(i).getDescription() != null) {
                String description = data.get(i).getDescription();
                String[] split_description = description.split("<br />");


                if (split_description.length < 2) {

                    startdate = split_description[0];
                    enddate = "  ";
                    desc = "   ";

                } else if (split_description.length < 3) {

                    startdate = split_description[0];
                    enddate = split_description[1];
                    desc = "   ";


                } else {
                    startdate = split_description[0];
                    enddate = split_description[1];
                    desc = split_description[2];
                }
            } else {
                startdate = "no information4";
                enddate = "no information5";
                desc = "no information6";
            }

            MapItems mm = new MapItems(data.get(i).getTitle(), data.get(i).getTitle(), startdate, enddate, desc, data.get(i).getLink()
                    , data.get(i).getAuthor(), data.get(i).getComments(), data.get(i).getPubDate(), LatLang);

            eventList.add(mm);
        }


        RSSFeed item = data.get(Position);
        if (item.getGeorss_point() != null) {
            String ViewLocation_atFirst = item.getGeorss_point();
            String[] splited = ViewLocation_atFirst.split("\\s+");
            double latitude_first_view = Double.parseDouble(splited[0]);
            double longitude_first_view = Double.parseDouble(splited[1]);

            view_location_at_first = new LatLng(latitude_first_view, longitude_first_view);
        } else
        {
            view_location_at_first = LatLang;
        }
        return root;
    }

    private void initLocation() {
        buildLocationRequest();
        buildLocationCallback();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    private void buildLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(500);
        locationRequest.setSmallestDisplacement(10f);
    }

    private void buildLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                currentLocation = locationResult.getLastLocation();
            }
        };
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);

        }
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_place_black_24dp);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(40, 20, vectorDrawable.getIntrinsicWidth() + 40, vectorDrawable.getIntrinsicHeight() + 20);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return true;
    }
}

