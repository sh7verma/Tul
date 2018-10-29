package fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.tul.DashBoardActivity;
import com.app.tul.R;
import com.app.tul.TulDetailActivity;
import com.cocosw.bottomsheet.BottomSheet;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.util.ArrayList;
import java.util.List;

import adapters.StatisticsTulAdapter;
import api.RetrofitClient;
import butterknife.BindView;
import butterknife.ButterKnife;
import database.Db;
import model.DashboardMyTulModel;
import model.StatisticsModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Connection_Detector;
import utils.Constants;
import utils.CustomLoadingDialog;
import utils.Utils;

import static android.app.Activity.RESULT_OK;

/**
 * Created by dev on 27/11/17.
 */

public class StatisticsFragment extends Fragment implements View.OnClickListener {

    public static final int TULDETAIL = 1;

    @BindView(R.id.rv_listing)
    RecyclerView rvListing;
    @BindView(R.id.txt_no_tuls)
    TextView txtNoTul;

    DashBoardActivity mDashBoardActivity;
    StatisticsTulAdapter mAdapter;

    Context mContext;
    Utils util;
    int mWidth, mHeight;
    Db db;

    ArrayList<DashboardMyTulModel.ResponseBean> mDashboardTul = new ArrayList<>();
    LinearLayoutManager mLayoutManager;
    private boolean isLoading;
    int mFilterOption = 0, mOffset = 0;
    private StatisticsFragment mFragment;
    StatisticsModel mStatModel;
    boolean isProgressVisible;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        ButterKnife.bind(StatisticsFragment.this, view);
        mContext = getActivity();
        util = new Utils(mContext);
        mDashBoardActivity = (DashBoardActivity) getActivity();
        mWidth = util.getInt("width", 0);
        mHeight = util.getInt("height", 0);
        db = new Db(mContext);
        mFragment = this;
        initUI();
        initListener();
        return view;
    }

    public void initUI() {

        mLayoutManager = new LinearLayoutManager(mContext);
        rvListing.setLayoutManager(mLayoutManager);
        rvListing.setNestedScrollingEnabled(false);

        txtNoTul.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.05));
        txtNoTul.setPadding(0, 0, 0, mHeight / 16);

        rvListing.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int pastVisibleItems = mLayoutManager.findLastVisibleItemPosition();
                if (mDashboardTul.size() > 9) {
                    if (!isLoading && pastVisibleItems == mDashboardTul.size() - 1) {
                        ///visible loader
                        mOffset++;
                        mDashboardTul.add(null);
                        recyclerView.post(new Runnable() {
                            public void run() {
                                mAdapter.notifyItemInserted(mDashboardTul.size() - 1);
                            }
                        });
                        hitTulApi();
                        isLoading = true;
                    }
                }
            }
        });

        if ((new Connection_Detector(mContext)).isConnectingToInternet()) {
            hitAPI();
        } else {
            Toast.makeText(mDashBoardActivity, R.string.internet_error, Toast.LENGTH_SHORT).show();
            txtNoTul.setVisibility(View.VISIBLE);
            txtNoTul.setText(R.string.no_internet_connection);
            rvListing.setVisibility(View.GONE);
        }
    }

    void initListener() {

    }

    void hitAPI() {
        Call<StatisticsModel> call = RetrofitClient.getInstance().getLenderStatistics(util.getString("access_token", ""));
        call.enqueue(new Callback<StatisticsModel>() {
            @Override
            public void onResponse(Call<StatisticsModel> call, Response<StatisticsModel> response) {
                if (response.body().getResponse() != null) {
                    if (response.body().getResponse().size() > 0) {
                        mStatModel = new StatisticsModel();
                        setData(response.body().getResponse());
                        hitTulApi();
                    }
                } else {
                    if (response.body().error.getCode().equals(getResources().getString(R.string.invalid_access_token))) {
                        Constants.moveToSplash(mContext, util);
                    } else {
                        mDashBoardActivity.showAlert(rvListing, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<StatisticsModel> call, Throwable t) {
                t.printStackTrace();
                txtNoTul.setVisibility(View.VISIBLE);
                rvListing.setVisibility(View.GONE);
            }
        });
    }

    void hitTulApi() {

        Call<DashboardMyTulModel> call = RetrofitClient.getInstance().getDashBoardMyTul(util.getString("access_token", ""),
                mOffset, mFilterOption);
        call.enqueue(new Callback<DashboardMyTulModel>() {
            @Override
            public void onResponse(Call<DashboardMyTulModel> call, Response<DashboardMyTulModel> response) {
                if (response.body().getResponse() != null) {
                    if (isProgressVisible) {
                        CustomLoadingDialog.getLoader().dismissLoader();
                        isProgressVisible = false;
                    }
                    rvListing.setVisibility(View.VISIBLE);
                    if (mOffset == 0) {
                        mDashboardTul.clear();
                        if (response.body().getResponse().size() > 0) {
                            txtNoTul.setVisibility(View.GONE);
                            mDashboardTul.addAll(response.body().getResponse());
                        } else {
                            txtNoTul.setVisibility(View.VISIBLE);
                        }
                        mAdapter.notifyDataSetChanged();
                    } else {
                        mDashboardTul.remove(mDashboardTul.size() - 1);
                        mAdapter.notifyItemRemoved(mDashboardTul.size());
                        mDashboardTul.addAll(response.body().getResponse());
                        mAdapter.notifyDataSetChanged();
                        isLoading = false;
                    }
                } else {
                    if (isProgressVisible) {
                        CustomLoadingDialog.getLoader().dismissLoader();
                        isProgressVisible = false;
                    }
                    txtNoTul.setVisibility(View.VISIBLE);
                    if (response.body().error.getCode().equals(getResources().getString(R.string.invalid_access_token))) {
                        Constants.moveToSplash(mContext, util);
                    } else {
                        mDashBoardActivity.showAlert(rvListing, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<DashboardMyTulModel> call, Throwable t) {
                t.printStackTrace();
                txtNoTul.setVisibility(View.VISIBLE);
                rvListing.setVisibility(View.GONE);
                if (isProgressVisible) {
                    CustomLoadingDialog.getLoader().dismissLoader();
                    isProgressVisible = false;
                }
            }
        });
    }

    private void setData(List<StatisticsModel.ResponseBean> response) {
        mStatModel.setResponse(response);
        mAdapter = new StatisticsTulAdapter(mContext, mDashboardTul, mFragment, mStatModel);
        rvListing.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_filter:
                imageOptions();
                break;
        }
    }

    public void imageOptions() {
        new BottomSheet.Builder(mContext, R.style.BottomSheet_Dialog)
                .sheet(R.menu.menu_filter).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case R.id.item_monthly:
                        if ((new Connection_Detector(mContext)).isConnectingToInternet()) {
                            if (mFilterOption != 2) {
                                mFilterOption = 2;
                                mOffset = 0;
                                isProgressVisible = true;
                                CustomLoadingDialog.getLoader().showLoader(mContext);
                                hitTulApi();
                            }
                        } else
                            mDashBoardActivity.showAlert(rvListing, mDashBoardActivity.errorInternet);
                        break;
                    case R.id.item_weekely:
                        if ((new Connection_Detector(mContext)).isConnectingToInternet()) {
                            if (mFilterOption != 1) {
                                mFilterOption = 1;
                                mOffset = 0;
                                isProgressVisible = true;
                                CustomLoadingDialog.getLoader().showLoader(mContext);
                                hitTulApi();
                            }
                        } else
                            mDashBoardActivity.showAlert(rvListing, mDashBoardActivity.errorInternet);
                        break;
                    case R.id.item_life_time:
                        if ((new Connection_Detector(mContext)).isConnectingToInternet()) {
                            if (mFilterOption != 0) {
                                mFilterOption = 0;
                                mOffset = 0;
                                isProgressVisible = true;
                                CustomLoadingDialog.getLoader().showLoader(mContext);
                                hitTulApi();
                            }
                        } else
                            mDashBoardActivity.showAlert(rvListing, mDashBoardActivity.errorInternet);
                        break;
                }
            }
        }).show();
    }

    public void moveToDetail(int position) {
        if ((new Connection_Detector(mContext)).isConnectingToInternet()) {
            Intent intent = new Intent(mContext, TulDetailActivity.class);
            intent.putExtra("position", position);
            intent.putExtra("tul_id", mDashboardTul.get(position).getTool_id());
            startActivityForResult(intent, TULDETAIL);
            ((Activity) mContext).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        } else {
            Snackbar.make(rvListing, R.string.internet_error, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == TULDETAIL) {
                mDashboardTul.remove(data.getIntExtra("position", 0));
                mAdapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
