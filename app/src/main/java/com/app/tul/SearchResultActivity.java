package com.app.tul;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
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
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

import java.util.ArrayList;
import java.util.List;

import adapters.SearchResultAdapter;
import api.RetrofitClient;
import butterknife.BindView;
import model.SearchResultModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Constants;
import utils.GpsStatusDetector;
import utils.GpsTracker;


public class SearchResultActivity extends BaseActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, GpsStatusDetector.GpsStatusDetectorCallBack,
        GpsTracker.GetLocationCallback,
        ClusterManager.OnClusterClickListener<SearchResultModel.ResponseBean>,
        ClusterManager.OnClusterItemClickListener<SearchResultModel.ResponseBean> {

    private static final int LOCATION = 1;
    private static final int AGAIN_CLUSTER = 2;
    private static final int AGAIN_CLUSTER_LIST = 3;
    private static final int ACTIVE_BOOKINGS = 4;
    final int LOCATION_CHECK = 1;
    @BindView(R.id.rl_below)
    RelativeLayout rlBelow;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_clear)
    ImageView imgClear;
    @BindView(R.id.txt_filter)
    TextView txtFilter;
    @BindView(R.id.rv_categories)
    RecyclerView rvCategories;
    @BindView(R.id.pb_cat)
    ProgressBar pbCat;
    @BindView(R.id.txt_no_cat)
    TextView txtNoCat;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;

    /// GPS...
    GpsTracker gps;
    double mLatitude = 0.0, mLongitude = 0.0;
    View mapView;
    private GoogleMap mMap;
    private GpsStatusDetector mGpsStatusDetector;
    private ClusterManager<SearchResultModel.ResponseBean> mClusterManager;
    private ArrayList<SearchResultModel.ResponseBean> mClusterArray = new ArrayList<>();
    private SearchResultAdapter mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_search_result;
    }

    @Override
    protected void initUI() {
        txtFilter.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));

        LinearLayout.LayoutParams belowParms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mHeight / 3);
        rlBelow.setLayoutParams(belowParms);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvCategories.setLayoutManager(layoutManager);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapView = mapFragment.getView();
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onCreateStuff() {

        if (Constants.FILTER_COUNT == 1)
            txtFilter.setText(Constants.FILTER_COUNT + getString(R.string.filter_applied));
        else if (Constants.FILTER_COUNT > 1)
            txtFilter.setText(Constants.FILTER_COUNT + getString(R.string.filters_applied));
        else
            txtFilter.setText("Filter Applied");

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_CHECK);
        } else {
            mGpsStatusDetector = new GpsStatusDetector(this);
            mGpsStatusDetector.checkGpsStatus(2);
        }

        try {
            mMap.clear();
            mClusterArray.clear();
            rlBelow.setVisibility(View.INVISIBLE);
            if (connectedToInternet())
                if (Constants.LATITUDE == 0.0 && Constants.LONGITUDE == 0.0)
                    hitAPI(String.valueOf(mLatitude), String.valueOf(mLongitude));
                else
                    hitAPI(String.valueOf(Constants.LATITUDE), String.valueOf(Constants.LONGITUDE));
            else
                showInternetAlert(rlBelow);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {

        if (Constants.FILTER_COUNT == 1)
            txtFilter.setText(Constants.FILTER_COUNT + getString(R.string.filter_applied));
        else if (Constants.FILTER_COUNT > 1)
            txtFilter.setText(Constants.FILTER_COUNT + getString(R.string.filters_applied));
        else
            txtFilter.setText("Filter Applied");

        mMap.clear();
        mClusterArray.clear();
        rlBelow.setVisibility(View.INVISIBLE);

        LatLng latLng;
        if (Constants.LATITUDE == 0.0 && Constants.LONGITUDE == 0.0)
            latLng = new LatLng(mLatitude, mLongitude);
        else
            latLng = new LatLng(Constants.LATITUDE, Constants.LONGITUDE);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 16);
        mMap.animateCamera(cameraUpdate);

        if (connectedToInternet()) {
            if (Constants.LATITUDE == 0.0 && Constants.LONGITUDE == 0.0)
                hitAPI(String.valueOf(mLatitude), String.valueOf(mLongitude));
            else
                hitAPI(String.valueOf(Constants.LATITUDE), String.valueOf(Constants.LONGITUDE));
        } else
            showInternetAlert(rlBelow);

        super.onNewIntent(intent);
    }

    @Override
    protected void initListener() {
        imgBack.setOnClickListener(this);
        imgClear.setOnClickListener(this);
        txtFilter.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return SearchResultActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                overridePendingTransition(R.anim.slidedown_in, R.anim.slidedown_out);
                break;
            case R.id.img_clear:
                alertDialogClear();
                break;
            case R.id.txt_filter:
                Intent intent = new Intent(mContext, SearchActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                break;
        }
    }

    private void alertDialogClear() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.clear_filter_warining))
                .setCancelable(false)
                .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Constants.TITLE = null;
                        dialog.cancel();
                        finish();
                        overridePendingTransition(R.anim.slidedown_in, R.anim.slidedown_out);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
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

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng;
        if (Constants.LATITUDE == 0.0 && Constants.LONGITUDE == 0.0)
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
        else
            latLng = new LatLng(Constants.LATITUDE, Constants.LONGITUDE);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 16);
        mMap.animateCamera(cameraUpdate);
        mLatitude = location.getLatitude();
        mLongitude = location.getLongitude();

        if (connectedToInternet()) {
            if (Constants.LATITUDE == 0.0 && Constants.LONGITUDE == 0.0)
                hitAPI(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
            else
                hitAPI(String.valueOf(Constants.LATITUDE), String.valueOf(Constants.LONGITUDE));
        }

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(mContext, R.raw.style));
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        if (mapView != null && mapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            locationButton.setLayoutParams(layoutParams);
        }

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
                .build();
        mGoogleApiClient.connect();
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
        if (enabled) {
            gps = new GpsTracker(mContext);
            mLatitude = gps.latitude;
            mLongitude = gps.longitude;

            Log.e("Lat Status= ", mLatitude + "//" + mLongitude);
            if (mLatitude == 0.0 && mLongitude == 0.0) {
                GpsTracker.setGetLocationCallback(SearchResultActivity.this);
            }
        }
    }

    @Override
    public void onGpsAlertCanceledByUser() {

    }

    @Override
    public void showLocationScreen() {
//        Intent inLoc = new Intent(mContext, LocationRequestActivity.class);
//        startActivityForResult(inLoc, LOCATION);
    }

    @Override
    public void onLocationRetrieved(Location location) {
        if (mLatitude == 0.0 && mLongitude == 0.0) {
            mLatitude = gps.getLatitude();
            mLongitude = gps.getLongitude();
            Log.e("Lat = ", mLatitude + "//" + mLongitude);
        }
    }

    @Override
    public boolean onClusterClick(Cluster<SearchResultModel.ResponseBean> cluster) {
        ArrayList<String> tulIds = new ArrayList<>();
        for (SearchResultModel.ResponseBean responseBean : cluster.getItems()) {
            tulIds.add(String.valueOf(responseBean.getId()));
        }
        Intent intent = new Intent(mContext, NearByTulListingActivity.class);
        intent.putStringArrayListExtra("tulIds", tulIds);
        intent.putExtra("getdataonback", "yes");
        startActivityForResult(intent, AGAIN_CLUSTER_LIST);
        overridePendingTransition(R.anim.slideup_in, R.anim.slideup_out);
        return true;
    }

    @Override
    public boolean onClusterItemClick(SearchResultModel.ResponseBean item) {
        Intent intent = new Intent(mContext, ListingTulDetailActivity.class);
        intent.putExtra("id", item.getId());
        startActivityForResult(intent, AGAIN_CLUSTER);
        overridePendingTransition(R.anim.slideup_in, R.anim.slideup_out);
        return true;
    }

    private void hitAPI(String latitude, String longitude) {
        showProgress();
        int bestRated = 0;
        if (Constants.BEST_RATED)
            bestRated = 1;
        Call<SearchResultModel> call = RetrofitClient.getInstance().searchTul(utils.getString("access_token", ""),
                Constants.TITLE, Constants.CATEGORY_ID, Constants.DELIVERY_TYPE, Constants.MAX_PRICE_SEARCH,
                Constants.MIN_PRICE_SEARCH, Constants.AVAILABILTY, latitude, longitude, bestRated, Constants.DISTANCE);
        call.enqueue(new Callback<SearchResultModel>() {
            @Override
            public void onResponse(Call<SearchResultModel> call, Response<SearchResultModel> response) {
                hideProgress();
                rlBelow.setVisibility(View.VISIBLE);
                if (response.body().getResponse() != null) {
                    startClustering(response.body().getResponse());
                    if (response.body().getResponse().size() == 0) {
                        rvCategories.setVisibility(View.GONE);
                        txtNoCat.setVisibility(View.VISIBLE);
                    } else {
                        txtNoCat.setVisibility(View.GONE);
                        rvCategories.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (response.body().error.getCode().equals(getResources().getString(R.string.invalid_access_token))) {
                        Constants.moveToSplash(mContext, utils);
                    } else {
                        showAlert(imgBack, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchResultModel> call, Throwable t) {
                hideProgress();
            }
        });
    }

    private void startClustering(List<SearchResultModel.ResponseBean> response) {
        mClusterManager = new ClusterManager<SearchResultModel.ResponseBean>(this, mMap);
        mClusterManager.setRenderer(new TulRender());
        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
        mMap.clear();
        mClusterManager.clearItems();
        mClusterManager.addItems(response);
        mClusterManager.cluster();
        mClusterManager.setOnClusterClickListener(this);
        mClusterManager.setOnClusterItemClickListener(this);

        mClusterArray.clear();
        mClusterArray.addAll(response);

        rvCategories.setVisibility(View.VISIBLE);
        if (rvCategories.getAdapter() == null) {
            mAdapter = new SearchResultAdapter(mContext, mClusterArray);
            rvCategories.setAdapter(mAdapter);
        } else
            mAdapter.notifyDataSetChanged();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case LOCATION:
                    gps = new GpsTracker(mContext);
                    mLatitude = gps.latitude;
                    mLongitude = gps.longitude;
                    if (mLatitude == 0.0 && mLongitude == 0.0) {
                        GpsTracker.setGetLocationCallback(SearchResultActivity.this);
                    }
                    break;
                case AGAIN_CLUSTER:
                    if (connectedToInternet())
                        hitAPI(String.valueOf(mLatitude), String.valueOf(mLongitude));
                    else
                        showInternetAlert(rlBelow);
                    break;
                case AGAIN_CLUSTER_LIST:
                    if (connectedToInternet())
                        hitAPI(String.valueOf(mLatitude), String.valueOf(mLongitude));
                    else
                        showInternetAlert(rlBelow);
                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);

        if (ContextCompat.checkSelfPermission(SearchResultActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                mGpsStatusDetector.checkOnActivityResult(requestCode, resultCode);
        }

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slidedown_in, R.anim.slidedown_out);
    }

    private class TulRender extends DefaultClusterRenderer<SearchResultModel.ResponseBean> {
        private final IconGenerator mIconGenerator = new IconGenerator(mContext);
        private final IconGenerator mClusterIconGenerator = new IconGenerator(mContext);
        private TextView txtCountCluster, txtTitleCluster, txtCount, txtTitle;
        private LinearLayout llCluster, llItem;

        public TulRender() {
            super(getApplicationContext(), mMap, mClusterManager);

            View clusterItem = getLayoutInflater().inflate(R.layout.item_cluster, null);
            mClusterIconGenerator.setContentView(clusterItem);

            llCluster = (LinearLayout) clusterItem.findViewById(R.id.ll_cluster);
            llCluster.setPadding(mWidth / 64, mWidth / 64, 0, mWidth / 64);

            LinearLayout.LayoutParams countParms = new LinearLayout.LayoutParams(mWidth / 12, mWidth / 12);
            txtCountCluster = (TextView) clusterItem.findViewById(R.id.txt_count);
            txtCountCluster.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));
            txtCountCluster.setLayoutParams(countParms);

            txtTitleCluster = (TextView) clusterItem.findViewById(R.id.txt_title);
            txtTitleCluster.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
            txtTitleCluster.setPadding(mWidth / 32, 0, mWidth / 32, 0);
            txtTitleCluster.setAllCaps(false);

            View singleItem = getLayoutInflater().inflate(R.layout.item_cluster, null);
            mIconGenerator.setContentView(singleItem);

            llItem = (LinearLayout) singleItem.findViewById(R.id.ll_cluster);
            llItem.setPadding(mWidth / 64, mWidth / 64, 0, mWidth / 64);

            txtCount = (TextView) singleItem.findViewById(R.id.txt_count);
            txtCount.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));
            txtCount.setLayoutParams(countParms);

            txtTitle = (TextView) singleItem.findViewById(R.id.txt_title);
            txtTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
            txtTitle.setPadding(mWidth / 32, 0, mWidth / 32, 0);
            txtTitle.setAllCaps(true);
        }

        @Override
        protected void onBeforeClusterItemRendered(SearchResultModel.ResponseBean tulItem, MarkerOptions markerOptions) {
            // Draw a single person.
            // Set the info window to show their name.
            txtCount.setText("1");
            if (tulItem.getTitle().length() > 10)
                txtTitle.setText(tulItem.getTitle().substring(0, 10) + "...");
            else
                txtTitle.setText(tulItem.getTitle());

            Bitmap icon = mIconGenerator.makeIcon("1");
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
        }

        @Override
        protected void onBeforeClusterRendered(Cluster<SearchResultModel.ResponseBean> cluster, MarkerOptions markerOptions) {
            // Draw multiple people.
            // Note: this method runs on the UI thread. Don't spend too much time in here (like in this example).
            txtCountCluster.setText(String.valueOf(cluster.getSize()));
            txtTitleCluster.setText("TULs");
            Bitmap icon = mClusterIconGenerator.makeIcon(String.valueOf(cluster.getSize()));
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
        }

        @Override
        protected boolean shouldRenderAsCluster(Cluster cluster) {
            // Always render clusters.
            return cluster.getSize() > 1;
        }
    }
}
