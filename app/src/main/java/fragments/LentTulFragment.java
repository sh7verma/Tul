package fragments;

import android.content.Context;
import android.os.Build;
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

import com.app.tul.HistoryActivity;
import com.app.tul.R;

import java.util.ArrayList;
import java.util.List;

import adapters.HistoryAdapter;
import api.RetrofitClient;
import butterknife.BindView;
import butterknife.ButterKnife;
import database.Db;
import model.AttachmentModel;
import model.BookTulModel;
import model.LentTulModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Connection_Detector;
import utils.Constants;
import utils.CustomLoadingDialog;
import utils.Utils;

/**
 * Created by dev on 20/11/17.
 */

public class LentTulFragment extends Fragment implements View.OnClickListener {

    View itemView;
    Context mContext;
    Utils util;
    int mWidth, mHeight;
    int mFragment = 0;


    @BindView(R.id.sv_main)
    NestedScrollView svMain;
    @BindView(R.id.rv_listed_tuls)
    RecyclerView rvListedTuls;
    @BindView(R.id.txt_no_tuls)
    TextView txtNoTuls;

    HistoryAdapter mAdapter;
    ArrayList<BookTulModel.ResponseBean> mListArray = new ArrayList<>();
    LinearLayoutManager mLayoutManager;
    HistoryActivity mHistoryActivity;
    Db db;
    private int mOffset = 0;
    private boolean isLoading;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        itemView = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(LentTulFragment.this, itemView);
        mContext = getActivity();
        util = new Utils(mContext);
        db = new Db(mContext);
        mHistoryActivity = (HistoryActivity) getActivity();
        mWidth = util.getInt("width", 0);
        mHeight = util.getInt("height", 0);
        initUI();
        initListener();
        return itemView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setLocalData();
        if ((new Connection_Detector(mContext)).isConnectingToInternet()) {
            hitAPI();
        }
    }

    public void setLocalData() {
        mListArray.clear();
        mListArray.addAll(db.getAllHistoryLendTuls());
        if (mListArray.size() > 0) {
            svMain.setVisibility(View.GONE);
            rvListedTuls.setVisibility(View.VISIBLE);
            if (mAdapter == null) {
                rvListedTuls.setLayoutManager(mLayoutManager);
                mAdapter = new HistoryAdapter(mContext, mListArray, mFragment);
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
        rvListedTuls.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mListArray.size() > 9) {
                    if ((new Connection_Detector(mContext)).isConnectingToInternet()) {
                        int pastVisibleItems = mLayoutManager.findLastVisibleItemPosition();
                        if (!isLoading && pastVisibleItems == mListArray.size() - 1) {
                            mOffset++;
                            mListArray.add(null);
                            recyclerView.post(new Runnable() {
                                public void run() {
                                    mAdapter.notifyItemInserted(mListArray.size() - 1);
                                }
                            });
                            hitAPI();
                            isLoading = true;
                        }
                    }
                }
            }
        });
    }

    private void initListener() {

    }

    @Override
    public void onClick(View v) {

    }

    public void setServerData(List<BookTulModel.ResponseBean> listArray) {
        if (mOffset == 0)
            mListArray.clear();

        mListArray.addAll(listArray);
        if (mListArray.size() > 0) {
            svMain.setVisibility(View.GONE);
            rvListedTuls.setVisibility(View.VISIBLE);
            if (mAdapter == null) {
                rvListedTuls.setLayoutManager(mLayoutManager);
                mAdapter = new HistoryAdapter(mContext, mListArray, mFragment);
                rvListedTuls.setAdapter(mAdapter);
            } else {
                mAdapter.notifyDataSetChanged();
                if (isLoading)
                    isLoading = false;
            }
        } else {
            svMain.setVisibility(View.VISIBLE);
            rvListedTuls.setVisibility(View.GONE);
        }
    }

    private void hitAPI() {
        Call<LentTulModel> call = RetrofitClient.getInstance().getLentTul(util.getString("access_token", ""), mOffset);
        call.enqueue(new Callback<LentTulModel>() {
            @Override
            public void onResponse(Call<LentTulModel> call, Response<LentTulModel> response) {
                if (response.body().getResponse() != null) {
                    if(mListArray.size()>0&&mListArray.get(mListArray.size()-1)==null){
                        mListArray.remove(mListArray.size() - 1);
                        mAdapter.notifyItemRemoved(mListArray.size());
                    }
//                    if (mOffset == 0)/// clear Array.
//                        mListArray.clear();
//                    else {
//                        mListArray.remove(mListArray.size() - 1);
//                        mAdapter.notifyItemRemoved(mListArray.size());
//                    }
                    setServerData(response.body().getResponse());
                    addToLocal(response.body().getResponse());
                } else {
                    if (response.body().error.getCode().equals(getResources().getString(R.string.invalid_access_token))) {
                        Constants.moveToSplash(mContext, util);
                    } else {
                        mHistoryActivity.showAlert(rvListedTuls, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<LentTulModel> call, Throwable t) {
                mHistoryActivity.showAlert(rvListedTuls, t.getLocalizedMessage());
                t.printStackTrace();
                if (mOffset == 0)
                    hideProgress();
            }
        });
    }

    private void addToLocal(List<BookTulModel.ResponseBean> response) {
        for (int i = 0; i < response.size(); i++) {
            db.addHistoryLendTul(response.get(i));
            for (AttachmentModel attachmentItem : response.get(i).getAttachment()) {
                db.addHistoryLentAttachment(attachmentItem);
            }
        }
    }


    protected void showProgress() {
        svMain.setVisibility(View.GONE);
        CustomLoadingDialog.getLoader().showLoader(mHistoryActivity);
    }

    private void moveBack() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mHistoryActivity.finishAfterTransition();
        } else {
            mHistoryActivity.finish();
            mHistoryActivity.overridePendingTransition(R.anim.slidedown_in, R.anim.slidedown_out);
        }
    }

    protected void hideProgress() {
        CustomLoadingDialog.getLoader().dismissLoader();
    }


}