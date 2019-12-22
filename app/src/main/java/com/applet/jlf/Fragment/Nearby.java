package com.applet.jlf.Fragment;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.applet.jlf.MainActivity;
import com.applet.jlf.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import static com.applet.jlf.R.drawable.ic_arrow_downward;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Nearby.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Nearby#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Nearby extends Fragment implements LocationListener, OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    LocationManager locationManager;

    static GoogleMap mMap;

    private BottomSheetBehavior mBehavior;

    ImageButton imageButton;

    FloatingActionButton locationFAB ;
    Double cLat;
    Double cLon;

    public Nearby() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Nearby.
     */
    // TODO: Rename and change types and number of parameters
    public static Nearby newInstance(String param1, String param2) {
        Nearby fragment = new Nearby();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nearby, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapNearby);
        mapFragment.getMapAsync(this);
        getLocation();

        //get bottom sheet view
        final LinearLayout bottom_sheet = view.findViewById(R.id.bottom_sheet);

        //Image Button for bottom sheet
        imageButton = view.findViewById(R.id.drawerbtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){
                    mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }else if(mBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED){
                    mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }
        });



        locationFAB = view.findViewById(R.id.locationFAB);
        locationFAB.animate().translationY(-20).setStartDelay(1).setDuration(20).start();
        locationFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cLat!=null){
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(cLat,cLon),12));
                }
            }
        });

        //init the bottom sheet behaviour
        mBehavior = BottomSheetBehavior.from(bottom_sheet);

        if(mBehavior.getState() == 3){
            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if(newState == BottomSheetBehavior.STATE_EXPANDED){
                    MainActivity.getCustomToolbar().animate().translationY(-(2 * MainActivity.getCustomToolbar().getHeight())).start();
                    imageButton.setImageResource(R.drawable.ic_arrow_downward);
                    locationFAB.animate().translationX(200).setStartDelay(1).setDuration(20).start();
                }else if(newState == BottomSheetBehavior.STATE_COLLAPSED){
                    MainActivity.getCustomToolbar().animate().translationY(0).setStartDelay(1).setDuration(100).start();
                    imageButton.setImageResource(R.drawable.ic_arrow_upward);
                    locationFAB.animate().translationX(-5).setStartDelay(1).setDuration(100).start();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onLocationChanged(Location location) {

        cLat = location.getLatitude();
        cLon = location.getLongitude();
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "Please grant permission", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.setLatLngBoundsForCameraTarget(new LatLngBounds(new LatLng(26.7113687,75.6461082),new LatLng(27.0616201,75.8993452)));

        Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude()),12));
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    void getLocation() {
        try {
            locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 5, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }


}
