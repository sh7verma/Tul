package fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.app.tul.OwnProfileActivity;
import com.app.tul.R;

import java.util.ArrayList;
import java.util.List;

import adapters.ListedTulAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import database.Db;
import model.ProfileModel;
import utils.Utils;


public class ListedTulsFragment extends Fragment implements View.OnClickListener {

    View itemView;
    Context mContext;
    Utils util;
    int mWidth, mHeight;

    @BindView(R.id.rv_listed_tuls)
    RecyclerView rvListedTuls;
    @BindView(R.id.txt_no_tuls)
    TextView txtNoTuls;
    @BindView(R.id.sv_main)
    NestedScrollView svMain;
    @BindView(R.id.view_1)
    View view_1;

    ListedTulAdapter mAdpater;
    ArrayList<ProfileModel.ResponseBean.ToolsBean> mTulsArray = new ArrayList<>();
    Db db;
    OwnProfileActivity mOwnProfileActivity;
    LinearLayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        itemView = inflater.inflate(R.layout.fragment_listed_tul, container, false);
        ButterKnife.bind(ListedTulsFragment.this, itemView);
        mContext = getActivity();
        util = new Utils(mContext);
        mWidth = util.getInt("width", 0);
        mHeight = util.getInt("height", 0);
        db = new Db(mContext);
        mOwnProfileActivity = (OwnProfileActivity) getActivity();
        initUI();
        initListener();

        return itemView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        setLocalData();
        mOwnProfileActivity.updateTabCount();
        super.onActivityCreated(savedInstanceState);
    }

    private void initUI() {
        txtNoTuls.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        txtNoTuls.setPadding(0, mHeight / 4, 0, mWidth);
        mLayoutManager = new LinearLayoutManager(mContext);
        rvListedTuls.setLayoutManager(mLayoutManager);

        rvListedTuls.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int pastVisibleItems = mLayoutManager.findLastVisibleItemPosition();
                if (pastVisibleItems == mTulsArray.size() - 1) {
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

    public void setServerData(List<ProfileModel.ResponseBean.ToolsBean> tulsArray) {
        if (tulsArray.size() > 0) {
            mTulsArray.clear();
            mTulsArray.addAll(tulsArray);
            svMain.setVisibility(View.GONE);
            rvListedTuls.setVisibility(View.VISIBLE);
            if (mAdpater == null) {
                mAdpater = new ListedTulAdapter(mContext, mTulsArray);
                rvListedTuls.setAdapter(mAdpater);
            } else {
                mAdpater.notifyDataSetChanged();
            }
        } else {
            svMain.setVisibility(View.VISIBLE);
            rvListedTuls.setVisibility(View.GONE);
        }
    }

    public void setLocalData() {
        mTulsArray.clear();
        mTulsArray.addAll(db.getAllListedTuls());
        if (mTulsArray.size() > 0) {
            svMain.setVisibility(View.GONE);
            rvListedTuls.setVisibility(View.VISIBLE);
            if (mAdpater == null) {
                mAdpater = new ListedTulAdapter(mContext, mTulsArray);
                rvListedTuls.setAdapter(mAdpater);
            } else {
                mAdpater.notifyDataSetChanged();
            }
        } else {
            svMain.setVisibility(View.VISIBLE);
            rvListedTuls.setVisibility(View.GONE);
        }
    }


}
