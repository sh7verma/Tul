package com.app.tul;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import adapters.PlaceInfoArrayAdapter;
import butterknife.BindView;
import model.PlaceInfo;
import utils.Constants;
import utils.GpsStatusDetector;
import utils.MainApplication;

public class LocationMapActivity extends BaseActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener, View.OnClickListener, GpsStatusDetector.GpsStatusDetectorCallBack {

    private static final String TAG_RESULT = "predictions";
    final int LOCATION_CHECK = 1;
    //  Marker marker;
    boolean flagmarker = false;
    double mLatitude;
    double mLongitude;
    View view;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.img_clear)
    ImageView imgClear;
    @BindView(R.id.txt_cancel)
    TextView txtCancel;
    @BindView(R.id.ed_search)
    EditText etSearch;
    @BindView(R.id.txt_search)
    TextView txtSearch;
    @BindView(R.id.bt_map_location_selected_done)
    Button btLocationSelected;
    @BindView(R.id.ll_list_container)
    LinearLayout llListContainer;
    @BindView(R.id.txt_no_location)
    TextView txtNoLocation;
    @BindView(R.id.txt_searching)
    TextView txtSearching;
    @BindView(R.id.search_list)
    ListView lvSearch;
    @BindView(R.id.img_my_location)
    ImageView imgMyLocation;
    String mAddress;
    InputMethodManager imm;
    View mapView;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    ProgressDialog progDailog;
    SupportMapFragment mapFragment;
    //    URL url;
    private GoogleMap mMap;
    private ArrayList<PlaceInfo> mPlaceInfoArrayList;
    private String url;
    private PlaceInfoArrayAdapter adapter;
    private String placeId;
    private GoogleApiClient mGoogleApiClient;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private GpsStatusDetector mGpsStatusDetector;

    @Override
    protected int getContentView() {
        return R.layout.activity_location_map;
    }

    @Override
    protected void initUI() {

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/semibold.ttf");
        Typeface typefaceRegular = Typeface.createFromAsset(getAssets(), "fonts/regular.ttf");

        llSearch.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);

        etSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        etSearch.setTypeface(typefaceRegular);
        etSearch.setPadding(mWidth / 32, mWidth / 32, 0, mWidth / 32);

        txtSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        txtSearch.setPadding(mWidth / 32, mWidth / 32, 0, mWidth / 32);

        imgClear.setPadding(mWidth / 64, mWidth / 32, mWidth / 64, mWidth / 32);

        txtCancel.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));
        txtCancel.setPadding(mWidth / 32, mWidth / 32, 0, mWidth / 32);

        txtNoLocation.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        txtNoLocation.setPadding(mWidth / 32, mWidth / 32, 0, mWidth / 32);
        txtNoLocation.setPadding(mWidth / 28, mWidth / 28, 0, 0);
        txtNoLocation.setVisibility(View.GONE);

        txtSearching.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));
        txtSearching.setPadding(mWidth / 32, mWidth / 32, 0, mWidth / 32);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mPlaceInfoArrayList = new ArrayList<PlaceInfo>();

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        llListContainer.animate().translationY(1000);

        etSearch.setOnClickListener(this);

        adapter = new PlaceInfoArrayAdapter(LocationMapActivity.this, mPlaceInfoArrayList);
        lvSearch.setAdapter(adapter);

        etSearch.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    lvSearch.setVisibility(View.VISIBLE);
                    llListContainer.setTranslationY(0);
                    if (txtSearch.getVisibility() == View.VISIBLE) {
                        txtSearch.setVisibility(View.GONE);
                        etSearch.setVisibility(View.VISIBLE);
                        etSearch.requestFocus();
                    } else {
                        updateList(s.toString());
                    }
                    imgClear.setVisibility(View.VISIBLE);
                } else {
                    imgClear.setVisibility(View.INVISIBLE);
                    lvSearch.setVisibility(View.GONE);

                    if (adapter != null) {
                        mPlaceInfoArrayList.clear();
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

        lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (connectedToInternet()) {
                    progDailog = ProgressDialog.show(mContext, "Please wait ...", "Fetching Location", true);
                    String placeId = mPlaceInfoArrayList.get(position).getPlaceId();
                    getLocationById(placeId, position);

                    View ve = LocationMapActivity.this.getCurrentFocus();
                    if (ve != null) {
                        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(ve.getWindowToken(), 0);
                    }
                } else
                    showInternetAlert(llSearch);
            }
        });


    }


    @Override
    protected void onCreateStuff() {

        if (!connectedToInternet())
            showInternetAlert(llSearch);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_CHECK);
        } else {
            mGpsStatusDetector = new GpsStatusDetector(this);
            mGpsStatusDetector.checkGpsStatus(2);
        }
    }

    @Override
    protected void initListener() {
        txtSearch.setOnClickListener(this);
        btLocationSelected.setOnClickListener(this);
        imgClear.setOnClickListener(this);
        txtCancel.setOnClickListener(this);
        txtNoLocation.setOnClickListener(this);
        llSearch.setOnClickListener(this);
        imgMyLocation.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return LocationMapActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_map_location_selected_done: {
                if (txtSearch.getText().length() > 0) {
                    Intent intent = new Intent();
                    intent.putExtra("latitude", String.valueOf(mLatitude));
                    intent.putExtra("longitude", String.valueOf(mLongitude));
                    intent.putExtra("address", mAddress);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(this, R.string.reselect_your_location, Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.img_clear: {
                if (adapter != null) {
                    mPlaceInfoArrayList.clear();
                    adapter.notifyDataSetChanged();
                }
                etSearch.setText("");
//                llListContainer.animate().translationY(mHeight);
                break;
            }
            case R.id.txt_cancel: {
                Constants.closeKeyboard(mContext, txtCancel);
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
            case R.id.txt_search:
                llListContainer.setVisibility(View.VISIBLE);
                llListContainer.animate().translationY(0);
                etSearch.setText(txtSearch.getText().toString());
                etSearch.requestFocus();
                Constants.showKeyboard(mContext, llSearch);
                break;
            case R.id.txt_no_location:
                llListContainer.setTranslationY(mHeight);
                txtSearch.setVisibility(View.VISIBLE);
                etSearch.setVisibility(View.GONE);
                Constants.closeKeyboard(mContext, llSearch);
                break;
            case R.id.img_my_location:
                LatLng latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                //move map camera

                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14);
                mMap.animateCamera(cameraUpdate);
                break;
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if (mLastLocation == null){

            mLastLocation = location;
        }
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
//        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14);
        mMap.animateCamera(cameraUpdate);
        flagmarker = true;

        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {

                if (flagmarker) {
                    Log.d("Location", String.valueOf(mMap.getCameraPosition().target));
                    mLatitude = mMap.getCameraPosition().target.latitude;
                    mLongitude = mMap.getCameraPosition().target.longitude;
                }

            }
        });
        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                if (connectedToInternet()) {
                    if (flagmarker) {
                        Log.d("LocationcameraIdle", String.valueOf(mMap.getCameraPosition().target));
                        mLatitude = mMap.getCameraPosition().target.latitude;
                        mLongitude = mMap.getCameraPosition().target.longitude;
                        txtSearch.setText("");
                        getAddress(mLatitude, mLongitude);
                        btLocationSelected.setEnabled(true);
                    }
                } else
                    showInternetAlert(llSearch);
            }
        });

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

        mMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
            @Override
            public void onCameraMoveStarted(int i) {
                btLocationSelected.setEnabled(false);
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(mContext, R.raw.style));
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
//
//Disable Map Toolbar:
        mMap.getUiSettings().setMapToolbarEnabled(false);


        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .build();
        mGoogleApiClient.connect();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                mGpsStatusDetector.checkOnActivityResult(requestCode, resultCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case LOCATION_CHECK:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        onCreateStuff();
                        mMap.setMyLocationEnabled(true);
                    }
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void onGpsSettingStatus(boolean enabled) {

    }

    @Override
    public void onGpsAlertCanceledByUser() {

    }

    @Override
    public void showLocationScreen() {

    }

    void getAddress(double latitude, double longitude) {

        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            Toast.makeText(mContext, R.string.sorry_unable_to_fetch_location, Toast.LENGTH_SHORT).show();
        }
        if (addresses != null && addresses.size() > 0) {
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getSubLocality() + " , " + addresses.get(0).getSubAdminArea();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();

            mAddress = address;
            txtSearch.setText(address + "," + city);
        }
    }

    public void updateList(String place) {

        String input = "";
        txtSearching.setVisibility(View.VISIBLE);

        try {
            input = "input=" + URLEncoder.encode(place, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        String output = "json";
        String parameter = input + "&location=" + mLatitude
                + ","
                + mLongitude
                + "&radius="
                + 500 + "&sensor=true&language=en&key=" + getResources().getString(R.string.google_maps_key);
        url = "https://maps.googleapis.com/maps/api/place/autocomplete/" + output + "?" + parameter;
        Log.d("url", url);

//        url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
//                + latitude
//                + ","
//                + longitude
//                + "&radius="
//                + 20000
//                + "&keyword="
//                + place
//                + "&sensor=true&key=AIzaSyBAPTeUV-04HFtCIt3Ac8MtVFqim9CDlV4";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("Response iss:: " + response);
                try {
                    JSONArray ja = response.getJSONArray(TAG_RESULT);

                    if (ja.length() > 0) {
                        mPlaceInfoArrayList.clear();

                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject c = ja.getJSONObject(i);
                            String description = c.getString("description");
                            placeId = c.getString("place_id");
                            mPlaceInfoArrayList.add(new PlaceInfo(description, placeId));
                        }
                        if (mPlaceInfoArrayList.size() > 0) {
                            txtNoLocation.setVisibility(View.GONE);
                        }
                        adapter = new PlaceInfoArrayAdapter(LocationMapActivity.this, mPlaceInfoArrayList);
                        lvSearch.setAdapter(adapter);
                    } else {

                        txtNoLocation.setVisibility(View.VISIBLE);
                        mPlaceInfoArrayList.clear();
                        adapter.notifyDataSetChanged();
                    }
                    txtSearching.setVisibility(View.GONE);
                } catch (Exception e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    txtSearching.setText(error.getLocalizedMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        MainApplication.getInstance().addToRequestQueue(jsonObjReq, "jreq");
    }

    public void getLocationById(String placeId, final int position) {
        Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId)
                .setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(PlaceBuffer places) {
                        if (places.getStatus().isSuccess()) {
                            progDailog.dismiss();
                            final Place myPlace = places.get(0);
                            LatLng queriedLocation = myPlace.getLatLng();
                            mLatitude = queriedLocation.latitude;
                            mLongitude = queriedLocation.longitude;
                            Intent intent = new Intent();
                            intent.putExtra("latitude", String.valueOf(mLatitude));
                            intent.putExtra("longitude", String.valueOf(mLongitude));
                            intent.putExtra("address", mPlaceInfoArrayList.get(position).getName());
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                        places.release();
                    }
                });
    }

}
