package fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.app.tul.AddPaymentCardActivity;
import com.app.tul.AfterWalkthroughActivity;
import com.app.tul.PaymentActivity;
import com.app.tul.R;

import java.util.ArrayList;

import adapters.PaymentSavedCardsAdapter;
import api.RetrofitClient;
import butterknife.BindView;
import butterknife.ButterKnife;
import database.Db;
import model.CardLocalModel;
import model.CardModel;
import model.DeleteCardModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Connection_Detector;
import utils.CustomLoadingDialog;
import utils.Utils;

import static android.app.Activity.RESULT_OK;

/**
 * Created by dev on 31/10/17.
 */

public class PaymentCardFragment extends Fragment implements View.OnClickListener {

    private static final int ADD_CARD = 1;
    protected String errorInternet;
    protected String errorAPI;
    protected String errorAccessToken;
    View itemView;
    Context mContext;
    Utils util;
    int mWidth, mHeight;
    @BindView(R.id.rv_listed_card)
    RecyclerView rvListedCards;
    @BindView(R.id.ll_add_card)
    RelativeLayout llAddCard;
    @BindView(R.id.tv_button)
    TextView tvButton;
    @BindView(R.id.img_button)
    ImageView imgButton;
    @BindView(R.id.ll_main)
    RelativeLayout llMain;
    @BindView(R.id.txt_no_card)
    TextView tvNoCard;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    ArrayList<CardLocalModel.ResponseBean> mArrayList = new ArrayList<>();
    Db db;
    PaymentActivity mPaymentActivity;
    PaymentSavedCardsAdapter mAdapter;
    PaymentCardFragment mPaymentCardFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        itemView = inflater.inflate(R.layout.fragment_payment_savedata, container, false);
        ButterKnife.bind(PaymentCardFragment.this, itemView);
        mContext = getActivity();
        util = new Utils(mContext);
        mWidth = util.getInt("width", 0);
        mHeight = util.getInt("height", 0);
        db = new Db(mContext);
        mPaymentActivity = (PaymentActivity) getActivity();
        mPaymentCardFragment = this;
        initUI();
        initListener();
        errorInternet = getResources().getString(R.string.internet);
        errorAPI = getResources().getString(R.string.error);
        errorAccessToken = getResources().getString(R.string.invalid_access_token);
        return itemView;
    }

    private void initUI() {
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/semibold.ttf");

        llMain.setPadding(mWidth / 40, 0, mWidth / 40, mHeight / 40);

        llAddCard.setPadding(0, mHeight / 40, 0, mHeight / 40);

        imgButton.setPadding(mWidth / 26, 0, 0, 0);

        tvButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));
        tvButton.setTypeface(typeface);

        tvNoCard.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        tvNoCard.setTypeface(typeface);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rvListedCards.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new PaymentSavedCardsAdapter(getActivity(), mArrayList, mPaymentCardFragment);
        setLocalData();
        if ((new Connection_Detector(mContext)).isConnectingToInternet()) {
            hitAPI();
        } else {
            showAlert(llMain, errorInternet);
        }
    }

    private void initListener() {
        llAddCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.ll_add_card: {
                intent = new Intent(getActivity(), AddPaymentCardActivity.class);
                startActivityForResult(intent, ADD_CARD);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void setLocalData() {
        mArrayList.clear();
        mArrayList.addAll(db.getAllCards());
        if (mArrayList.size() > 0) {
            tvNoCard.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);

            if (rvListedCards.getAdapter() == null)
                rvListedCards.setAdapter(mAdapter);
            else
                mAdapter.notifyDataSetChanged();
        } else {
            tvNoCard.setVisibility(View.VISIBLE);

            if (progressBar.getVisibility() == View.VISIBLE)
                progressBar.setVisibility(View.GONE);
            else
                progressBar.setVisibility(View.GONE);
        }

    }

    private void hitAPI() {
        Call<CardModel> call = RetrofitClient.getInstance().viewCard(util.getString("access_token", ""));
        Log.d("access_token", util.getString("access_token", ""));
        call.enqueue(new Callback<CardModel>() {
            @Override
            public void onResponse(Call<CardModel> call, Response<CardModel> response) {
                Log.d("responsexcz", response.toString());
                if (response.body().getResponse() != null) {
                    if (response.body().getResponse().size() > 0) {
                        for (int i = 0; i < response.body().getResponse().size(); i++) {
                            db.addCard(response.body().getResponse().get(i));
                        }
                    }
                    setLocalData();
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        showAlert(llMain, response.body().error.getMessage());
                    } else {
                        showAlert(llMain, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<CardModel> call, Throwable t) {
                showAlert(llMain, t.getMessage());
                progressBar.setVisibility(View.GONE);
                tvNoCard.setVisibility(View.VISIBLE);
            }
        });
    }

    public void hitDeleteAPI(final int id, final int position) {
        if ((new Connection_Detector(mContext)).isConnectingToInternet()) {
            Call<DeleteCardModel> call = RetrofitClient.getInstance().deleteCard(util.getString("access_token", ""), id);
            call.enqueue(new Callback<DeleteCardModel>() {
                @Override
                public void onResponse(Call<DeleteCardModel> call, Response<DeleteCardModel> response) {
                    if (response.body().getResponse() != null) {
                        db.removeCard(id);
                        mArrayList.remove(position);
                        setLocalData();
                        progressBar.setVisibility(View.GONE);
                        showAlert(llMain, String.valueOf(response.body().getResponse()));
                    } else {
                        if (response.body().error.getCode().equals(errorAccessToken)) {
                            moveToSplash();
                        } else {
                            showAlert(llMain, response.body().error.getMessage());
                        }
                    }
                    CustomLoadingDialog.getLoader().dismissLoader();
                }

                @Override
                public void onFailure(Call<DeleteCardModel> call, Throwable t) {
                    showAlert(llMain, t.getMessage());
                    CustomLoadingDialog.getLoader().dismissLoader();
                }
            });
        } else {
            showAlert(llMain, errorInternet);
        }
    }

    protected void showAlert(View view, String message) {
        Snackbar mSnackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        mSnackbar.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ADD_CARD:
                    setLocalData();
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void moveToSplash() {
        Toast.makeText(mContext, R.string.Someone_else_login_on_another, Toast.LENGTH_LONG).show();
        db.deleteAllTables();
        util.clear_shf();
        Intent inSplash = new Intent(mContext, AfterWalkthroughActivity.class);
        inSplash.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        inSplash.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(inSplash);
        getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }

}
