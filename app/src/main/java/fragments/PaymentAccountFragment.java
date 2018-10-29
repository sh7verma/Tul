package fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.tul.AddPaymentAccountActivity;
import com.app.tul.AfterWalkthroughActivity;
import com.app.tul.PaymentActivity;
import com.app.tul.R;

import java.util.ArrayList;

import adapters.PaymentAccountsAdapter;
import api.RetrofitClient;
import butterknife.BindView;
import butterknife.ButterKnife;
import database.Db;
import model.AccountModel;
import model.CreateStripeAccountModel;
import model.DemoModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Connection_Detector;
import utils.CustomLoadingDialog;
import utils.Utils;

import static android.app.Activity.RESULT_OK;

public class PaymentAccountFragment extends Fragment implements View.OnClickListener {

    private static final int ADD_ACCOUNT = 1;
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

    @BindView(R.id.txt_no_account)
    TextView tvNoAccount;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    Db db;
    PaymentActivity mPaymentActivity;
    PaymentAccountsAdapter mAdapter;
    ArrayList<CreateStripeAccountModel.ResponseBean> mArrayList = new ArrayList<>();
    PaymentAccountFragment mPaymentAccountFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        itemView = inflater.inflate(R.layout.fragment_payment_accounts, container, false);
        ButterKnife.bind(PaymentAccountFragment.this, itemView);
        mContext = getActivity();
        util = new Utils(mContext);
        mWidth = util.getInt("width", 0);
        mHeight = util.getInt("height", 0);
        db = new Db(mContext);
        mPaymentActivity = (PaymentActivity) getActivity();
        mPaymentAccountFragment = this;
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

        rvListedCards.setLayoutManager(new LinearLayoutManager(mContext));

        llAddCard.setPadding(0, mHeight / 40, 0, mHeight / 40);

        imgButton.setPadding(mWidth / 26, 0, 0, 0);

        tvButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));
        tvButton.setTypeface(typeface);

        tvNoAccount.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        tvNoAccount.setTypeface(typeface);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new PaymentAccountsAdapter(getActivity(), mArrayList, mPaymentAccountFragment);
        rvListedCards.setAdapter(mAdapter);
        setLocalData();
        if ((new Connection_Detector(mContext)).isConnectingToInternet()) {
            hitAPI();
        } else {
            showAlert(llMain, errorInternet);
        }
    }

    private void hitAPI() {
        Call<AccountModel> call = RetrofitClient.getInstance().getAccounts(util.getString("access_token", ""));
        call.enqueue(new Callback<AccountModel>() {
            @Override
            public void onResponse(Call<AccountModel> call, Response<AccountModel> response) {
                if (response.body().getResponse() != null) {
                    if (response.body().getResponse().size() > 0) {
                        for (int i = 0; i < response.body().getResponse().size(); i++) {
                            db.addAccount(response.body().getResponse().get(i));
                        }
                    }
                    setLocalData();
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        showAlert(rvListedCards, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<AccountModel> call, Throwable t) {
                showAlert(rvListedCards, t.getMessage());
                progressBar.setVisibility(View.GONE);
                tvNoAccount.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initListener() {
        llAddCard.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        setLocalData();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.ll_add_card: {
                if ((new Connection_Detector(mContext)).isConnectingToInternet()) {
                    intent = new Intent(getActivity(), AddPaymentAccountActivity.class);
                    startActivityForResult(intent, ADD_ACCOUNT);
                } else {
                    showAlert(llMain, errorInternet);
                }


                break;
            }
        }
    }

    public void setLocalData() {
        mArrayList.clear();
        mArrayList.addAll(db.getAllAccounts());
        if (mArrayList.size() > 0) {
            tvNoAccount.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            if (rvListedCards.getAdapter() == null)
                rvListedCards.setAdapter(mAdapter);
            else
                mAdapter.notifyDataSetChanged();
        } else {
            tvNoAccount.setVisibility(View.VISIBLE);

            if (progressBar.getVisibility() == View.VISIBLE)
                progressBar.setVisibility(View.GONE);
            else
                progressBar.setVisibility(View.GONE);
        }
    }

    public void delete(int position) {
        if ((new Connection_Detector(mContext)).isConnectingToInternet()) {
            hitDeleteAPI(mArrayList.get(position).getAccountId(), position);
        } else {
            showAlert(llMain, errorInternet);
        }

    }

    private void hitDeleteAPI(int accountId, final int position) {
        CustomLoadingDialog.getLoader().showLoader(mContext);
        Call<DemoModel> call = RetrofitClient.getInstance().deleteAccount(util.getString("access_token", ""),
                accountId);
        call.enqueue(new Callback<DemoModel>() {
            @Override
            public void onResponse(Call<DemoModel> call, Response<DemoModel> response) {
                if (response.body().response != null) {
                    db.removeAccount(mArrayList.get(position));
                    mArrayList.remove(position);
                    setLocalData();
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
            public void onFailure(Call<DemoModel> call, Throwable t) {
                t.getLocalizedMessage();
                CustomLoadingDialog.getLoader().dismissLoader();
            }
        });
    }

    protected void showAlert(View view, String message) {
        Snackbar mSnackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        mSnackbar.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ADD_ACCOUNT:
                    if (data.getBooleanExtra("accountAlready", true)) {
                        int newAccountId = data.getIntExtra("accountId", 0);
                        int previousAccountId = 0;
                        for (CreateStripeAccountModel.ResponseBean responseItem : db.getAllAccounts()) {
                            if (responseItem.getIsPrimary() == 1) {
                                previousAccountId = responseItem.getAccountId();
                                break;
                            }
                        }
                        alertNewAccountPrimaryDialog(newAccountId, previousAccountId);
                    } else {
                        setLocalData();
                    }
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


    public void updatePrimary(int position, int previousPrimary) {
        if ((new Connection_Detector(mContext)).isConnectingToInternet()) {
            alertPrimaryDialog(position, previousPrimary);
        } else
            showAlert(llAddCard, errorInternet);
    }

    private void alertPrimaryDialog(final int position, final int previousPosition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(R.string.primary_account)
                .setCancelable(false)
                .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if ((new Connection_Detector(mContext)).isConnectingToInternet()) {
                            hitPrimaryAccountAPI(position, previousPosition);
                        } else
                            showAlert(llAddCard, errorInternet);
                        dialog.cancel();
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

    private void hitPrimaryAccountAPI(final int position, final int previousPosition) {
        CustomLoadingDialog.getLoader().showLoader(mContext);
        Call<DemoModel> call = RetrofitClient.getInstance().makePrimaryAccount(util.getString("access_token", "")
                , mArrayList.get(position).getAccountId());
        call.enqueue(new Callback<DemoModel>() {
            @Override
            public void onResponse(Call<DemoModel> call, Response<DemoModel> response) {
                if (response.body().response != null) {
                    mArrayList.get(position).setIsPrimary(1);
                    mArrayList.get(previousPosition).setIsPrimary(0);
                    mAdapter.notifyDataSetChanged();
                    db.updatePrimaryAccount(mArrayList.get(position).getAccountId(), 1);
                    db.updatePrimaryAccount(mArrayList.get(previousPosition).getAccountId(), 0);
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
            public void onFailure(Call<DemoModel> call, Throwable t) {
                showAlert(llAddCard, t.getLocalizedMessage());
                CustomLoadingDialog.getLoader().dismissLoader();
            }
        });
    }

    private void hitNewPrimaryAccountAPI(final int newAccountId, final int previousAccountId) {
        CustomLoadingDialog.getLoader().showLoader(mContext);
        Call<DemoModel> call = RetrofitClient.getInstance().makePrimaryAccount(util.getString("access_token", "")
                , newAccountId);
        call.enqueue(new Callback<DemoModel>() {
            @Override
            public void onResponse(Call<DemoModel> call, Response<DemoModel> response) {
                if (response.body().response != null) {
                    db.updatePrimaryAccount(newAccountId, 1);
                    db.updatePrimaryAccount(previousAccountId, 0);
                    setLocalData();
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
            public void onFailure(Call<DemoModel> call, Throwable t) {
                showAlert(llAddCard, t.getLocalizedMessage());
                CustomLoadingDialog.getLoader().dismissLoader();
            }
        });
    }


    private void alertNewAccountPrimaryDialog(final int newAccountId, final int previousAccountId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(R.string.primary_account)
                .setCancelable(false)
                .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if ((new Connection_Detector(mContext)).isConnectingToInternet()) {
                            hitNewPrimaryAccountAPI(newAccountId, previousAccountId);
                        } else
                            showAlert(llAddCard, errorInternet);
                        dialog.cancel();
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }


}
