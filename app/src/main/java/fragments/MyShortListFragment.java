package fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.tul.OwnProfileActivity;
import com.app.tul.R;

import java.util.ArrayList;
import java.util.List;

import adapters.MyShortListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import database.Db;
import model.NearByTulListingModel;
import utils.Constants;
import utils.Utils;

public class MyShortListFragment extends Fragment implements View.OnClickListener {

    View itemView;
    Context mContext;
    Utils util;
    int mWidth, mHeight;

    @BindView(R.id.sv_main)
    NestedScrollView svMain;
    @BindView(R.id.rv_listed_tuls)
    RecyclerView rvListedTuls;
    @BindView(R.id.txt_no_tuls)
    TextView txtNoTuls;
    @BindView(R.id.view_1)
    View view_1;

    MyShortListAdapter mAdapter;
    ArrayList<NearByTulListingModel.ResponseBean> mWishListArray = new ArrayList<>();
    LinearLayoutManager mLayoutManager;
    OwnProfileActivity mOwnProfileActivity;
    Db db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        itemView = inflater.inflate(R.layout.fragment_short_list, container, false);
        ButterKnife.bind(MyShortListFragment.this, itemView);
        mContext = getActivity();
        util = new Utils(mContext);
        db = new Db(mContext);
        mOwnProfileActivity = (OwnProfileActivity) getActivity();
        mWidth = util.getInt("width", 0);
        mHeight = util.getInt("height", 0);
        initUI();
        initListener();

        return itemView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        setLocalData();
        super.onActivityCreated(savedInstanceState);
    }

    public void setLocalData() {
        mWishListArray.clear();

        mWishListArray.addAll(db.getAllShortListedTuls());

        if (mWishListArray.size() > 0) {
            svMain.setVisibility(View.GONE);
            rvListedTuls.setVisibility(View.VISIBLE);
            if (mAdapter == null) {
                mAdapter = new MyShortListAdapter(mContext, mWishListArray);
                rvListedTuls.setAdapter(mAdapter);
            } else {
                mAdapter.notifyDataSetChanged();
            }
        } else {
            svMain.setVisibility(View.VISIBLE);
            rvListedTuls.setVisibility(View.GONE);
        }
    }

    private void initUI() {
        txtNoTuls.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        txtNoTuls.setPadding(0, mHeight / 4, 0, mWidth);

        mLayoutManager = new LinearLayoutManager(mContext);
        rvListedTuls.setLayoutManager(mLayoutManager);
        mLayoutManager = new LinearLayoutManager(mContext);
        rvListedTuls.setLayoutManager(mLayoutManager);

        rvListedTuls.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int pastVisibleItems = mLayoutManager.findLastVisibleItemPosition();
                Log.e("Last Item = ", pastVisibleItems + "");


                if (pastVisibleItems == mWishListArray.size() - 1) {
                    view_1.setVisibility(View.VISIBLE);
                } else {
                    view_1.setVisibility(View.GONE);
                }


            }
        });
    }

    private void initListener() {

    }

    @Override
    public void onClick(View v) {

    }

    public void setServerData(List<NearByTulListingModel.ResponseBean> wishlistArray) {
        if (wishlistArray.size() > 0) {
            mWishListArray.clear();
            mWishListArray.addAll(wishlistArray);
            svMain.setVisibility(View.GONE);
            rvListedTuls.setVisibility(View.VISIBLE);
            if (mAdapter == null) {
                mAdapter = new MyShortListAdapter(mContext, mWishListArray);
                rvListedTuls.setAdapter(mAdapter);
            } else {
                mAdapter.notifyDataSetChanged();
            }
        } else {
            svMain.setVisibility(View.VISIBLE);
            rvListedTuls.setVisibility(View.GONE);
        }
    }


    @Override
    public void onDestroy() {
        Constants.PROFILE_ID = 0;
        super.onDestroy();
    }
}
