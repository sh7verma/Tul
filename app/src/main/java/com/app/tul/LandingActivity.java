package com.app.tul;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.tul.Sales.SalesSearchActivity;
import com.app.tul.Sales.SalesTulDetailActivity;
import com.app.tul.Sales.SalesTulListingActivity;
import com.app.tul.Sales.SellerProfileActivity;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
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
import com.sbstrm.appirater.Appirater;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import adapters.LandingCategoriesAdapter;
import adapters.NavigationAdapter;
import api.RetrofitClient;
import butterknife.BindView;
import interfaces.SwitchClickInterface;
import me.leolin.shortcutbadger.ShortcutBadger;
import model.BookTulModel;
import model.CategoriesModel;
import model.ChatDialogModel;
import model.NearByTulModel;
import model.PendingTaskModel;
import model.SalesSellerModel;
import model.SignupModel;
import model.TransactionModel;
import model.VersionModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import services.FirebaseChatService;
import utils.Constants;
import utils.GpsStatusDetector;
import utils.GpsTracker;
import utils.MainApplication;

public class LandingActivity extends BaseActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, GpsStatusDetector.GpsStatusDetectorCallBack,
        GpsTracker.GetLocationCallback,
        ClusterManager.OnClusterClickListener<NearByTulModel.ResponseBean>,
        ClusterManager.OnClusterItemClickListener<NearByTulModel.ResponseBean> {

    private static final int LOCATION = 1;
    private static final int AGAIN_CLUSTER = 2;
    private static final int AGAIN_CLUSTER_LIST = 3;
    private static final int ACTIVE_BOOKINGS = 4;
    private static final int REFRESH_MARKERS = 5;
    private static final int NOTIFICATION = 6;
    public static LandingActivity landingActivity = null;
    final int LOCATION_CHECK = 1;
    @BindView(R.id.dl_navigation)
    DrawerLayout dlNavigation;
    @BindView(R.id.rv_navigation)
    RecyclerView rvNavigation;
    @BindView(R.id.img_menu)
    ImageView imgMenu;
    @BindView(R.id.img_search)
    ImageView imgSearch;
    @BindView(R.id.txt_looking)
    TextView txtLooking;
    @BindView(R.id.rv_categories)
    RecyclerView rvCategories;
    @BindView(R.id.txt_version)
    TextView txtVersion;
    @BindView(R.id.pb_cat)
    ProgressBar pbCat;
    @BindView(R.id.txt_no_cat)
    TextView txtNoCat;
    @BindView(R.id.rl_category)
    RelativeLayout rlCategory;
    @BindView(R.id.img_list)
    ImageView imgList;
    @BindView(R.id.img_search_sales)
    ImageView imgSearchSales;

    NavigationAdapter mAdapter;
    LandingCategoriesAdapter mCategoryAdapter;
    String TITLES[] = {"Home", "Search", "History", "Inbox", "Payments", "Settings", "Lender Dashboard"};
    int ICONS[] = {R.mipmap.ic_home,
            R.mipmap.ic_search,
            R.mipmap.ic_history,
            R.mipmap.ic_inbox,
            R.mipmap.ic_payment,
            R.mipmap.ic_settings,
            R.mipmap.ic_dashboard};
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;

    /// GPS...
    GpsTracker gps;
    double mLatitude = 0.0, mLongitude = 0.0;
    View mapView;
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                imgSearch.setImageResource(R.mipmap.ic_noti);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    SwitchClickInterface mSwitchClick = new SwitchClickInterface() {
        @Override
        public void onSwitchClick() {
            hitSwitchApi();
        }
    };
    private ArrayList<CategoriesModel.ResponseBean> mCategoriesArray = new ArrayList<>();
    private GoogleMap mMap;
    private GpsStatusDetector mGpsStatusDetector;
    private ClusterManager<NearByTulModel.ResponseBean> mClusterManager;
    private ArrayList<NearByTulModel.ResponseBean> mClusterArray = new ArrayList<>();
    private Tracker mTracker;
    private String forceUpdate = "";

    @Override
    protected int getContentView() {
        return R.layout.activity_landing;
    }

    @Override
    protected void initUI() {

        /// close drawer
        dlNavigation.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        txtLooking.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));

        txtNoCat.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.050));

        txtVersion.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.030));

        mAdapter = new NavigationAdapter(mContext, TITLES, ICONS, dlNavigation, rvNavigation, mSwitchClick);
        rvNavigation.setLayoutManager(new LinearLayoutManager(this));
        rvNavigation.setHasFixedSize(true);
        rvNavigation.setAdapter(mAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvCategories.setLayoutManager(layoutManager);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapView = mapFragment.getView();
        mapFragment.getMapAsync(this);

        hitPendingTaksAPI();
        if (utils.getInt(Constants.USER_LOGIN_MODE, Constants.USER_RENTAL) == Constants.USER_RENTAL) {
            getCategories();
            rlCategory.setVisibility(View.VISIBLE);
            imgList.setVisibility(View.GONE);

            imgSearchSales.setVisibility(View.GONE);
            imgSearch.setVisibility(View.VISIBLE);
        } else {
            rlCategory.setVisibility(View.GONE);
            imgList.setVisibility(View.VISIBLE);

            imgSearchSales.setVisibility(View.VISIBLE);
            imgSearch.setVisibility(View.GONE);
            TITLES[2] = getString(R.string.orders_);
            ICONS[2] = R.mipmap.ic_purchasing_order;
        }
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(rlCategory.getWindowToken(), 0);
        hitVersionAPI();

    }

    @Override
    protected void onCreateStuff() {

        if (utils.getInt(Constants.USER_LOGIN_MODE, Constants.USER_RENTAL) == Constants.USER_BUY) {
            String data = getIntent().getDataString();
            if (!TextUtils.isEmpty(data)) {
                Uri uri = Uri.parse(data);
                Set<String> args = uri.getQueryParameterNames();
                Log.e("Arguments = ", args.toString() + " " + uri.getPath());
                if (!TextUtils.isEmpty(utils.getString("access_token", ""))) {
                    Intent intent = new Intent(mContext, SalesTulDetailActivity.class);
                    intent.putExtra("id", uri.getQueryParameter("id"));
                    startActivity(intent);
                }
            }
        }

        /// remove broadcast notification
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(16);

//        if (utils.getInt(Constants.ISGUEST, 0) == 0) {
        /// start service to start listeners
        Intent intent = new Intent(mContext, FirebaseChatService.class);
        startService(intent);
//        }


        /// show broadcast popup
        if (getIntent().hasExtra("message"))
            showBroadCast();

        // Obtain the shared Tracker instance.
        MainApplication application = (MainApplication) getApplication();
        mTracker = application.getDefaultTracker();

        utils.setInt("inside_chat", 0);
        utils.setString("inside_dialog_id", "");

        landingActivity = this;
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_CHECK);
        } else {
            mGpsStatusDetector = new GpsStatusDetector(this);
            mGpsStatusDetector.checkGpsStatus(2);
        }

        /// appirator dialog
        Appirater.appLaunched(mContext);

        /// displaying chat dot on navigation icon
        updateMenuMarker();

        /// displaying version code
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            txtVersion.setText("App Version " + pInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        /// displaying dot on notification icon
        if (db.getAllUnReadNotifications() == 0)
            imgSearch.setImageResource(R.mipmap.ic_notification);
        else
            imgSearch.setImageResource(R.mipmap.ic_noti);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        /// show broadcast popup
        if (intent.hasExtra("message"))
            showBroadCastUpdate(intent.getStringExtra("message"), intent.getStringExtra("title"));
        super.onNewIntent(intent);
    }

    private void showBroadCastUpdate(String message, String title) {
        Intent intentBroadCast = new Intent(mContext, BroadCastDialog.class);
        intentBroadCast.putExtra("message", message);
        intentBroadCast.putExtra("title", title);
        startActivity(intentBroadCast);
    }

    private void showBroadCast() {
        Intent intentBroadCast = new Intent(mContext, BroadCastDialog.class);
        intentBroadCast.putExtra("message", getIntent().getStringExtra("message"));
        intentBroadCast.putExtra("title", getIntent().getStringExtra("title"));
        startActivity(intentBroadCast);
    }

    @Override
    protected void initListener() {
        imgMenu.setOnClickListener(this);
        imgSearch.setOnClickListener(this);
        txtLooking.setOnClickListener(this);
        imgList.setOnClickListener(this);
        imgSearchSales.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return LandingActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_menu:
                if (utils.getInt(Constants.ISGUEST, 0) == 0) {
                    dlNavigation.openDrawer(Gravity.LEFT);
                } else {
                    loginFirst(this);
                }
                break;
            case R.id.txt_looking:
                if (utils.getInt(Constants.USER_LOGIN_MODE, Constants.USER_RENTAL) == Constants.USER_RENTAL) {
                    if (Constants.TITLE != null) {
                        /// filter applied
                        Intent searchIntent = new Intent(mContext, SearchResultActivity.class);
                        startActivity(searchIntent);
                        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    } else {
                        /// filter not applied
                        Intent searchIntent = new Intent(mContext, SearchActivity.class);
                        startActivity(searchIntent);
                        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    }
                } else {
                    Intent searchIntent = new Intent(mContext, SalesSearchActivity.class);
                    startActivity(searchIntent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                }
                break;

            case R.id.img_search_sales:
                if (utils.getInt(Constants.USER_LOGIN_MODE, Constants.USER_RENTAL) != Constants.USER_RENTAL) {
                    Intent searchIntent = new Intent(mContext, SalesSearchActivity.class);
                    startActivity(searchIntent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                }
                break;
            case R.id.img_search:
                if (utils.getInt(Constants.USER_LOGIN_MODE, Constants.USER_RENTAL) == Constants.USER_RENTAL) {
                    if (utils.getInt(Constants.ISGUEST, 0) == 0) {
                        Intent notiIntent = new Intent(mContext, NotificationActivity.class);
                        startActivityForResult(notiIntent, NOTIFICATION);
                        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    } else {
                        loginFirst(this);
                    }
                }
                break;

            case R.id.img_list:
                if (mLatitude != 0.0) {
                    Intent salesDetail = new Intent(mContext, SalesTulListingActivity.class);
                    salesDetail.putExtra("latitude", String.valueOf(mLatitude));
                    salesDetail.putExtra("longitude", String.valueOf(mLongitude));
                    startActivity(salesDetail);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                }
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

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10.0f);

        mMap.animateCamera(cameraUpdate);
        utils.setString("latitude", String.valueOf(location.getLatitude()));
        utils.setString("longitude", String.valueOf(location.getLongitude()));

        if (utils.getInt(Constants.USER_LOGIN_MODE, Constants.USER_RENTAL) == Constants.USER_RENTAL) {
            hitNearByTulsAPI(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
        } else {
            hitNearByTulsSalesAPI(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
        }
        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }

    private void hitNearByTulsSalesAPI(String latitude, String longitude) {
        Call<SalesSellerModel> call = RetrofitClient.getInstance().nearByToolsSales(
                utils.getString("access_token", ""), latitude, longitude);
        call.enqueue(new Callback<SalesSellerModel>() {
            @Override
            public void onResponse(Call<SalesSellerModel> call, Response<SalesSellerModel> response) {
                if (response.body().getResponse() != null) {

                    SalesSellerModel model = response.body();

                    utils.setInt("rating", response.body().getRating());
                    ////
                    NearByTulModel bean = new NearByTulModel();
                    List<NearByTulModel.ResponseBean> list = new ArrayList<>();

                    for (int i = 0; i < model.getResponse().size(); i++) {
                        NearByTulModel.ResponseBean responseBean = new NearByTulModel.ResponseBean();
                        responseBean.setId(model.getResponse().get(i).getId());
                        responseBean.setLatitude(model.getResponse().get(i).getLatitude());
                        responseBean.setLongitude(model.getResponse().get(i).getLongitude());
                        responseBean.setCategory_name(model.getResponse().get(i).getUsername());
                        responseBean.setCategory_id(model.getResponse().get(i).getId());
                        list.add(responseBean);
                    }
                    ////

                    bean.setResponse(list);
                    startClustering(bean.getResponse());

                } else {
                    if (String.valueOf(response.body().getCode()).equals(getResources().getString(R.string.invalid_access_token))) {
                        Constants.moveToSplash(mContext, utils);
                    } else {
                        showAlert(imgMenu, String.valueOf(response.body().getCode()));
                    }
                }
            }

            @Override
            public void onFailure(Call<SalesSellerModel> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private void hitNearByTulsAPI(String latitude, String longitude) {
        Call<NearByTulModel> call = RetrofitClient.getInstance().nearByTools(utils.getString("access_token", ""),
                latitude, longitude);
        call.enqueue(new Callback<NearByTulModel>() {
            @Override
            public void onResponse(Call<NearByTulModel> call, Response<NearByTulModel> response) {
                if (response.body().getResponse() != null) {
                    startClustering(response.body().getResponse());
                    utils.setInt("booking_count", response.body().getCount().getMy_bookings_count());
                    utils.setInt("lent_count", response.body().getCount().getTool_lent_count());
                    utils.setInt("rating", response.body().getCount().getRating());

                    if (rvCategories.getAdapter() == null) {
                        mCategoryAdapter = new LandingCategoriesAdapter(mContext, mCategoriesArray);
                        rvCategories.setAdapter(mCategoryAdapter);
                    } else {
                        mCategoryAdapter.notifyDataSetChanged();
                    }
                    if (mAdapter != null) {
                        mAdapter.notifyDataSetChanged();
                    }
                    setUnread(response.body().getCount().getUnread_count());
                } else {
                    if (response.body().error.getCode().equals(getResources().getString(R.string.invalid_access_token))) {
                        Constants.moveToSplash(mContext, utils);
                    } else {
                        showAlert(imgMenu, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<NearByTulModel> call, Throwable t) {

            }
        });
    }

    private void setUnread(int unread_count) {
        if (unread_count == 0)
            imgSearch.setImageResource(R.mipmap.ic_notification);
        else
            imgSearch.setImageResource(R.mipmap.ic_noti);
    }

    private void startClustering(List<NearByTulModel.ResponseBean> response) {
        mClusterManager = new ClusterManager<NearByTulModel.ResponseBean>(this, mMap);
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case LOCATION:
                    gps = new GpsTracker(mContext);
                    mLatitude = gps.latitude;
                    mLongitude = gps.longitude;

                    Log.e("Lat Status= ", mLatitude + "//" + mLongitude);
                    if (mLatitude == 0.0 && mLongitude == 0.0) {
                        GpsTracker.setGetLocationCallback(LandingActivity.this);
                    }
                    break;
                case AGAIN_CLUSTER:
                    for (NearByTulModel.ResponseBean item : mClusterArray) {
                        if (item.getId() == data.getIntExtra("id", 0)) {
                            mClusterManager.removeItem(item);
                            mClusterManager.cluster();
                            break;
                        }
                    }
                    break;
                case AGAIN_CLUSTER_LIST:
                    for (int Ids : data.getIntegerArrayListExtra("deletedIds")) {
                        for (NearByTulModel.ResponseBean item : mClusterArray) {
                            if (item.getId() == Ids) {
                                mClusterManager.removeItem(item);
                                break;
                            }
                        }
                    }
                    mClusterManager.cluster();
                    break;
                case ACTIVE_BOOKINGS:
                    mCategoryAdapter.notifyDataSetChanged();
                    break;
                case REFRESH_MARKERS:

                    if (utils.getInt(Constants.USER_LOGIN_MODE, Constants.USER_RENTAL) == Constants.USER_RENTAL) {
                        hitNearByTulsAPI(mLatitude + "", mLongitude + "");
                    } else {
                        hitNearByTulsSalesAPI(mLatitude + "", mLongitude + "");
                    }

                    updateMenuMarker();
                    break;
                case NOTIFICATION:
                    if (db.getAllUnReadNotifications() == 0)
                        imgSearch.setImageResource(R.mipmap.ic_notification);
                    else
                        imgSearch.setImageResource(R.mipmap.ic_noti);
                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);

        if (ContextCompat.checkSelfPermission(LandingActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
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
        if (enabled) {
            gps = new GpsTracker(mContext);
            mLatitude = gps.latitude;
            mLongitude = gps.longitude;

            Log.e("Lat Status= ", mLatitude + "//" + mLongitude);
            if (mLatitude == 0.0 && mLongitude == 0.0) {
                GpsTracker.setGetLocationCallback(LandingActivity.this);
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

    private void hitPendingTaksAPI() {
        Call<PendingTaskModel> call = RetrofitClient.getInstance().getPendingTasks(utils.getString("access_token", ""));
        call.enqueue(new Callback<PendingTaskModel>() {
                         @Override
                         public void onResponse(Call<PendingTaskModel> call, Response<PendingTaskModel> response) {
                             if (response.body().getResponse() != null) {
                                 if (response.body().getResponse().size() > 0) {
                                     for (BookTulModel.ResponseBean pendingResponseModel : response.body().getResponse()) {
                                         Intent intent = new Intent(mContext, PendingTaskActivity.class);
                                         intent.putExtra("data", pendingResponseModel);
                                         startActivity(intent);
                                     }
                                 }
                             } else {
                                 if (response.body().error.getCode().equals(errorAccessToken))
                                     moveToSplash();
                                 else
                                     showAlert(txtLooking, response.body().error.getMessage());
                             }
                         }

                         @Override
                         public void onFailure(Call<PendingTaskModel> call, Throwable t) {
                             hideProgress();
                         }
                     }
        );
    }

    private void getCategories() {

        pbCat.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(utils.getString("categories", ""))) {
            CategoriesModel categoriesModel = mGson.fromJson(utils.getString("categories", ""), CategoriesModel.class);
            mCategoriesArray.addAll(categoriesModel.getResponse());
            mCategoryAdapter = new LandingCategoriesAdapter(mContext, mCategoriesArray);
            rvCategories.setAdapter(mCategoryAdapter);
            pbCat.setVisibility(View.GONE);
        }

        if (connectedToInternet()) {
            Call<CategoriesModel> call = RetrofitClient.getInstance().getCategories();
            call.enqueue(new Callback<CategoriesModel>() {
                @Override
                public void onResponse(Call<CategoriesModel> call, Response<CategoriesModel> response) {
                    pbCat.setVisibility(View.GONE);
                    if (response.body().getResponse() != null) {

                        utils.setString("categories", mGson.toJson(response.body()));
                        mCategoriesArray.clear();
                        mCategoriesArray.addAll(response.body().getResponse());
                        mCategoryAdapter = new LandingCategoriesAdapter(mContext, mCategoriesArray);
                        rvCategories.setAdapter(mCategoryAdapter);
                    } else {
                        if (response.body().error.getCode().equals(getResources().getString(R.string.invalid_access_token))) {
                            Constants.moveToSplash(mContext, utils);
                        } else {
                            showAlert(imgMenu, response.body().error.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<CategoriesModel> call, Throwable t) {
                    pbCat.setVisibility(View.GONE);
                    showAlert(imgMenu, t.getLocalizedMessage());
                }
            });
        } else {
            Toast.makeText(mContext, getResources().getString(R.string.internet), Toast.LENGTH_SHORT).show();
            txtNoCat.setVisibility(View.VISIBLE);
        }
    }

    private void hitVersionAPI() {
        Call<VersionModel> call = RetrofitClient.getInstance().getVersion();
        call.enqueue(new Callback<VersionModel>() {
            @Override
            public void onResponse(Call<VersionModel> call, Response<VersionModel> response) {
                if (response.body().getResponse() != null) {
                    try {
                        PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                        String version = pInfo.versionName;
                        Log.e("Version  = ", version);
                        forceUpdate = response.body().getResponse().getUpdate();
                        if (!version.equals(response.body().getResponse().getVersion())) {
                            dialog_update();
                        }
                    } catch (Exception e) {
                        Log.e("Exce  = ", e + "");
                    }
                }
            }

            @Override
            public void onFailure(Call<VersionModel> call, Throwable t) {
                showAlert(rvCategories, t.getLocalizedMessage());
            }
        });
    }

    private void hitTransactionAPI() {
        Call<TransactionModel> call = RetrofitClient.getInstance().getTransactionPercentage(utils.getString("access_token", ""));
        call.enqueue(new Callback<TransactionModel>() {
            @Override
            public void onResponse(Call<TransactionModel> call, Response<TransactionModel> response) {
                if (response.body().getResponse() != null) {
                    Log.e("Transaction %age = ", response.body().getResponse());

                    utils.setString("transaction_percentage", response.body().getResponse());

                    utils.setInt(Constants.ISEMAILSKIP, response.body().getIs_email_skip());
                    utils.setInt(Constants.EMAIL_VERIFY, response.body().getEmail_verify());
                    utils.setInt(Constants.BLOCKSTATUS, response.body().getBlock_status());

                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        showAlert(txtNoCat, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<TransactionModel> call, Throwable t) {

            }
        });
    }


    @Override
    protected void onResume() {

        mTracker.setScreenName("Landing Activity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        if (utils.getInt(Constants.ISGUEST, 0) == 0) {
            if (db.getAllUnReadNotifications() == 0)
                imgSearch.setImageResource(R.mipmap.ic_notification);
            else
                imgSearch.setImageResource(R.mipmap.ic_noti);

            utils.setInt("badge_count", 0);
            ShortcutBadger.removeCount(mContext);
            if (mAdapter != null) {
                mAdapter.notifyDataSetChanged();
            }
        }
        hitTransactionAPI();
        super.onResume();
    }

    @Override
    public boolean onClusterClick(Cluster<NearByTulModel.ResponseBean> cluster) {
        ArrayList<String> tulIds = new ArrayList<>();
        for (NearByTulModel.ResponseBean responseBean : cluster.getItems()) {
            tulIds.add(String.valueOf(responseBean.getId()));
        }
        Intent intent;
        if (utils.getInt(Constants.USER_LOGIN_MODE, Constants.USER_RENTAL) == Constants.USER_RENTAL) {
            intent = new Intent(mContext, NearByTulListingActivity.class);
        } else {
            intent = new Intent(mContext, SalesTulListingActivity.class);
        }

        intent.putStringArrayListExtra("tulIds", tulIds);
        startActivityForResult(intent, AGAIN_CLUSTER_LIST);
        overridePendingTransition(R.anim.slideup_in, R.anim.slideup_out);

        return true;
    }

    @Override
    public boolean onClusterItemClick(NearByTulModel.ResponseBean item) {
        Intent intent;

        if (utils.getInt(Constants.USER_LOGIN_MODE, Constants.USER_RENTAL) == Constants.USER_RENTAL) {
            intent = new Intent(mContext, ListingTulDetailActivity.class);
            intent.putExtra("id", item.getId());
            startActivityForResult(intent, AGAIN_CLUSTER);
            overridePendingTransition(R.anim.slideup_in, R.anim.slideup_out);
        } else {
            if (connectedToInternet()) {
                intent = new Intent(mContext, SellerProfileActivity.class);
                intent.putExtra("other_user_id", item.getId());
                intent.putExtra("id", item.getId());
                startActivityForResult(intent, AGAIN_CLUSTER);
                overridePendingTransition(R.anim.slideup_in, R.anim.slideup_out);
            } else {
                showInternetAlert(rlCategory);
            }
        }


        return true;
    }

    public void moveToActiveBookings() {
        Intent intentActive = new Intent(mContext, ActiveBookingActivity.class);
        startActivityForResult(intentActive, ACTIVE_BOOKINGS);
        overridePendingTransition(R.anim.slideup_in, R.anim.slideup_out);
    }

    @Override
    protected void onDestroy() {
        landingActivity = null;
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter(Constants.NOTIFICATION));
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    private void updateMenuMarker() {
        Map<String, ChatDialogModel> totalConversationMap = new LinkedHashMap<>();
        ArrayList<String> conversationKeys = new ArrayList<>();

        totalConversationMap = db.getAllConversation();
        for (String chatDialogId : totalConversationMap.keySet()) {
            ChatDialogModel chatDialogModel = new ChatDialogModel();
            chatDialogModel = totalConversationMap.get(chatDialogId);
            if (chatDialogModel.getLast_message().equals(Constants.CHAT_REGEX)) {

            } else {
                if (chatDialogModel.getUnread_count().get(utils.getString("user_id", "")) != null) {
                    if (!chatDialogModel.getUnread_count().get(utils.getString("user_id", "")).equals("0")) {
                        conversationKeys.add(chatDialogId);
                    }
                }

            }
        }
        Log.d("Unreada", String.valueOf(conversationKeys.size()));

        if (conversationKeys.size() > 0) {
            imgMenu.setImageResource(R.mipmap.ic_menu_1);
        } else {
            imgMenu.setImageResource(R.mipmap.ic_menu);
        }
    }

    void dialog_update() {
        final Dialog phone_dialog = new Dialog(mContext);
        phone_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        phone_dialog.setContentView(R.layout.discard);
        if (forceUpdate.equals(""))
            phone_dialog.setCanceledOnTouchOutside(true);
        else
            phone_dialog.setCanceledOnTouchOutside(false);

        LinearLayout.LayoutParams phone_params = new LinearLayout.LayoutParams(mWidth - mWidth / 6, (int) (mHeight / 3));
        final LinearLayout main_lay = (LinearLayout) phone_dialog.findViewById(R.id.main_lay);
        main_lay.setLayoutParams(phone_params);

        TextView textDiscard = (TextView) phone_dialog.findViewById(R.id.text_discard);
        textDiscard.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        textDiscard.setPadding(mWidth / 32, 0, mWidth / 32, 0);
        textDiscard.setText("A newer version of TUL is available\nwould you like to update?");

        TextView txtConfirm = (TextView) phone_dialog.findViewById(R.id.txt_confirm);
        txtConfirm.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        txtConfirm.setPadding(mWidth / 32, 0, mWidth / 32, 0);
        txtConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.app.tul&hl=en"));
                startActivity(intent);
            }
        });

        TextView txtCancel = (TextView) phone_dialog.findViewById(R.id.txt_cancel);
        txtCancel.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
        txtCancel.setPadding(mWidth / 32, 0, mWidth / 32, 0);

        if (forceUpdate.equals(""))
            txtCancel.setVisibility(View.VISIBLE);
        else
            txtCancel.setVisibility(View.GONE);

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone_dialog.dismiss();

            }
        });

        if (!forceUpdate.equals("")) {
            phone_dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    finish();
                }
            });
        }

        phone_dialog.show();
    }

    public void hitSwitchApi() {
        showProgress();
        String status;

        if (utils.getInt(Constants.USER_LOGIN_MODE, Constants.USER_RENTAL) == Constants.USER_RENTAL) {
            status = "1";
        } else {
            status = "0";
        }

        Call<SignupModel> call = RetrofitClient.getInstance().switch_profile(utils.getString("access_token", ""), status);
        call.enqueue(new Callback<SignupModel>() {
            @Override
            public void onResponse(Call<SignupModel> call, Response<SignupModel> response) {
                hideProgress();
                if (response.body().getResponse() != null) {

                    utils.setString("user_id", String.valueOf(response.body().getResponse().getId()));
                    utils.setString("access_token", response.body().getResponse().getAccess_token());
                    utils.setString("email", response.body().getResponse().getEmail());
                    utils.setInt("status", response.body().getResponse().getStatus());
                    utils.setString("user_name", response.body().getResponse().getUsername());
                    utils.setString("first_name", response.body().getResponse().getFirst_name());
                    utils.setString("last_name", response.body().getResponse().getLast_name());
                    utils.setString("phone_number", response.body().getResponse().getPhone_number());
                    utils.setString("country_code", response.body().getResponse().getCountry_code());
                    utils.setString("profile_pic", response.body().getResponse().getUser_pic());
                    utils.setInt("lender", response.body().getResponse().getLender());
                    utils.setInt(Constants.ISSELLER, response.body().getResponse().getIs_seller());
                    utils.setInt(Constants.ISCOMPANY, response.body().getResponse().getIs_company());
                    utils.setString(Constants.VAT, response.body().getResponse().getVat());
                    utils.setInt("email_verify", response.body().getResponse().getEmail_verify());
                    utils.setInt("new_message", 1);
                    utils.setInt("path", 1);
                    utils.setString(Constants.PRIMARY_CURRENCY, response.body().getResponse().getPrimary_currency());
                    utils.setString(Constants.IS_CURRENCY_SELECTED, response.body().getResponse().getCurrency_selected());

                    //Skip//
                    utils.setInt(Constants.BLOCKSTATUS, response.body().getResponse().getBlock_status());
                    utils.setInt(Constants.ISGUEST, response.body().getResponse().getIs_guest());
                    utils.setInt(Constants.ISEMAILSKIP, response.body().getResponse().getIs_email_skip());
                    utils.setString(Constants.VAT, response.body().getResponse().getVat());
                    utils.setInt(Constants.USER_LOGIN_MODE, response.body().getResponse().getLogin_type());

                    utils.setString(Constants.UNVERIFIED_EMAIL, response.body().getResponse().getUnverified_email());

                    utils.setString(Constants.USER_LATITUDE, response.body().getResponse().getLatitude());
                    utils.setString(Constants.USER_LONGITUDE, response.body().getResponse().getLongitude());

                    Intent inSplash = new Intent(mContext, LandingActivity.class);
                    inSplash.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    inSplash.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    mContext.startActivity(inSplash);

                } else {

                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        showAlert(rvCategories, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<SignupModel> call, Throwable t) {

            }
        });
    }

    private class TulRender extends DefaultClusterRenderer<NearByTulModel.ResponseBean> {
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
        protected void onBeforeClusterItemRendered(NearByTulModel.ResponseBean tulItem, MarkerOptions markerOptions) {
            // Draw a single person.
            // Set the info window to show their name.
            txtCount.setText("1");
            txtTitle.setText(tulItem.getCategory_name());
            Bitmap icon = mIconGenerator.makeIcon("1");
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
        }

        @Override
        protected void onBeforeClusterRendered(Cluster<NearByTulModel.ResponseBean> cluster, MarkerOptions markerOptions) {
            // Draw multiple people.
            // Note: this method runs on the UI thread. Don't spend too much time in here (like in this example).
            txtCountCluster.setText(String.valueOf(cluster.getSize()));
            if (utils.getInt(Constants.USER_LOGIN_MODE, Constants.USER_RENTAL) == Constants.USER_RENTAL) {
                txtTitleCluster.setText(R.string.TULs);
            } else {
                txtTitleCluster.setText(R.string.SELLERS);

            }
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
