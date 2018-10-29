package com.app.tul;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tul.Sales.BecomeSellerActivity;
import com.app.tul.Sales.SalesListYourTulActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import adapters.ProfileViewpagerAdapter;
import api.RetrofitClient;
import butterknife.BindView;
import customviews.HeaderView;
import dialogs.VerifyEmailDialog;
import fragments.ListedTulsFragment;
import fragments.MyShortListFragment;
import model.AttachmentModel;
import model.NearByTulListingModel;
import model.ProfileModel;
import model.SignupModel;
import model.TransactionModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Constants;

public class OwnProfileActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener {

    public static final int TULDETAIL = 3;
    private static final int EDITPROFILE = 1;
    private static final int ADD_TUL = 2;
    private static final int REFRESH = 4;
    static Context mCon;
    static int mWid;

    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_header_view)
    HeaderView toolbarHeaderView;
    @BindView(R.id.float_header_view)
    HeaderView floatHeaderView;
    @BindView(R.id.vp_profile)
    ViewPager pager;
    @BindView(R.id.tl_profile)
    TabLayout tabs;
    @BindView(R.id.ll_main)
    CoordinatorLayout llMain;
    @BindView(R.id.img_profile)
    ImageView imgProfile;
    @BindView(R.id.img_add_tull)
    ImageView imgAddTull;
    @BindView(R.id.ll_tip)
    LinearLayout llTip;
    @BindView(R.id.txt_add)
    TextView txtAdd;
    @BindView(R.id.txt_tip_desc)
    TextView txtTipDesc;
    @BindView(R.id.img_cross)
    ImageView imgCross;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.img_tip)
    ImageView imgTip;
    @BindView(R.id.txt_switch)
    TextView txtSwitch;
    @BindView(R.id.ll_switch)
    LinearLayout llSwitch;

    ProfileViewpagerAdapter mAdapter;
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (utils.getInt(Constants.EMAIL_VERIFY, 0) == 1) {
                floatHeaderView.bindTo(utils.getString("user_name", ""), utils.getString("country_code", "") + " " + utils.getString("phone_number", ""), utils.getString("email", ""), true);
            } else {
                floatHeaderView.bindTo(utils.getString("user_name", ""), utils.getString("country_code", "") + " " + utils.getString("phone_number", ""), "Email: Not Verified", false);
            }
        }
    };
    private boolean isHideToolbarView = false;

    public static void infoClick() {
        new VerifyEmailDialog(mCon, mWid, mCon.getResources().getString(R.string.verify_email)).showDialog();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_own_profile;
    }

    @Override
    protected void initUI() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_back);

        collapsingToolbarLayout.setTitle(" ");

        appBarLayout.addOnOffsetChangedListener(this);

        CollapsingToolbarLayout.LayoutParams imgParms = new CollapsingToolbarLayout.LayoutParams(mWidth, mHeight / 3);
        imgProfile.setLayoutParams(imgParms);

        imgProfile.setColorFilter(ContextCompat.getColor(mContext, R.color.transperent), android.graphics.PorterDuff.Mode.MULTIPLY);

        LinearLayout.LayoutParams tipParms = new LinearLayout.LayoutParams((int) (mWidth * .7), ViewGroup.LayoutParams.WRAP_CONTENT);
        tipParms.setMargins(0, 0, mWidth / 32, 0);
        llTip.setLayoutParams(tipParms);

        txtAdd.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));
        txtAdd.setPadding(mWidth / 32, mWidth / 64, mWidth / 32, mWidth / 64);

        txtTipDesc.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));
        txtTipDesc.setPadding(mWidth / 32, 0, mWidth / 16, mWidth / 32);

        mAdapter = new ProfileViewpagerAdapter(getSupportFragmentManager());
        mAdapter.addFragment(new ListedTulsFragment(), getString(R.string.listed_tuls));
        mAdapter.addFragment(new MyShortListFragment(), getString(R.string.my_short_list));
        pager.setAdapter(mAdapter);

        LinearLayout.LayoutParams tipImgParms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tipImgParms.setMargins(0, 0, mWidth / 10, 0);
        imgTip.setLayoutParams(tipImgParms);

        tabs.setupWithViewPager(pager);

    }

    @Override
    protected void onCreateStuff() {
        mCon = mContext;
        mWid = mWidth;
        if (utils.getInt("lender", 0) == 1 || (utils.getInt(Constants.ISSELLER, 0) == Constants.IS_SELLER)) {
            hideTip();
        }

        if (utils.getInt(Constants.USER_LOGIN_MODE, Constants.USER_RENTAL) == Constants.USER_RENTAL) {
            txtSwitch.setText(getString(R.string.sales));
        } else {
            txtSwitch.setText(getString(R.string.rentals));
            txtTipDesc.setText(getString(R.string.list_your_tul_become_a_seller_and_start_earning));
        }

        setData();
        if (connectedToInternet()) {
            hitAPI();
            hitTransactionAPI();
        } else
            showInternetAlert(llMain);
    }


    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter(Constants.EMAIL_VERIFY));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    private void setData() {
        toolbarHeaderView.bindTo(utils.getString("user_name", ""));

        if (utils.getInt(Constants.EMAIL_VERIFY, 0) == 1) {
            floatHeaderView.bindTo(utils.getString("user_name", ""), utils.getString("country_code", "") + " " + utils.getString("phone_number", ""), utils.getString("email", ""), true);
        } else {
            floatHeaderView.bindTo(utils.getString("user_name", ""), utils.getString("country_code", "") + " " + utils.getString("phone_number", ""), "Email: Not Verified", false);
        }

        if (!TextUtils.isEmpty(utils.getString("profile_pic", "")))
            Picasso.with(mContext).load(utils.getString("profile_pic", ""))
                    .resize(mHeight / 3, mHeight / 3).centerCrop()
                    .placeholder(R.mipmap.ic_image_uploading_tul)
                    .into(imgProfile, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            Log.e("Error = ", "Yes");
                        }
                    });
        else
            Picasso.with(mContext).load(R.mipmap.ic_no_image).resize(mHeight / 3, mHeight / 3).centerCrop()
                    .into(imgProfile);

    }

    @Override
    protected void initListener() {
        imgAddTull.setOnClickListener(this);
        imgCross.setOnClickListener(this);
        llBack.setOnClickListener(this);
        imgProfile.setOnClickListener(this);
        llSwitch.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return OwnProfileActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_switch:
                hitSwitchApi();
                break;

            case R.id.img_cross:
                hideTip();
                break;
            case R.id.img_add_tull:
                if (utils.getInt(Constants.BLOCKSTATUS, 0) == 0) {
                    if (connectedToInternet()) {
                        if (utils.getInt(Constants.EMAIL_VERIFY, 0) == 1) {
                            if (llTip.getVisibility() == View.GONE) {
                                if (utils.getInt(Constants.USER_LOGIN_MODE, Constants.USER_RENTAL) ==
                                        Constants.USER_RENTAL) {
                                    Intent intent = new Intent(mContext, ListYourTulActivity.class);
                                    startActivityForResult(intent, ADD_TUL);
                                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                                } else {
                                    if (utils.getInt(Constants.ISSELLER, 0) == Constants.IS_SELLER) {
                                        Intent intent = new Intent(mContext, SalesListYourTulActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                        startActivityForResult(intent, ADD_TUL);
                                        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                                    } else {
                                        Intent intent = new Intent(mContext, BecomeSellerActivity.class);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                                    }
                                }
                            }
                        } else {
                            hitEmailVerifyAPI();
                        }
                    } else {
                        showInternetAlert(llMain);
                    }
                } else {
                    userBlockDialog(this);
                }
                break;
            case R.id.img_profile:
                ArrayList<String> picArray = new ArrayList<>();
                picArray.add(utils.getString("profile_pic", ""));
                Intent fullViewintent = new Intent(mContext, FullViewImageActivity.class);
                fullViewintent.putStringArrayListExtra("pic_array", picArray);
                startActivity(fullViewintent);
                break;
        }

    }

    private void hitSwitchApi() {
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
                    startActivity(inSplash);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);

                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        showAlert(llMain, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<SignupModel> call, Throwable t) {

            }
        });
    }

    private void hideTip() {
        llTip.setVisibility(View.GONE);
        llBack.setVisibility(View.GONE);
        imgTip.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            moveBack();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Edit").setIcon(R.mipmap.ic_edit).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (connectedToInternet()) {
                    if (utils.getInt(Constants.BLOCKSTATUS, 0) == 0) {
                        Intent inEdit = new Intent(mContext, EditProfileActivity.class);
                        inEdit.putExtra("edit", "edit");
                        startActivityForResult(inEdit, EDITPROFILE);
                        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    } else {
                        userBlockDialog(mContext);
                    }
                } else
                    showInternetAlert(llMain);
                return true;

            }
        }).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        if (percentage == 1f && isHideToolbarView) {
            toolbarHeaderView.setVisibility(View.VISIBLE);
            isHideToolbarView = !isHideToolbarView;

        } else if (percentage < 1f && !isHideToolbarView) {
            toolbarHeaderView.setVisibility(View.GONE);
            isHideToolbarView = !isHideToolbarView;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == EDITPROFILE) {
                setData();
            } else if (requestCode == ADD_TUL) {
                if (utils.getInt(Constants.USER_LOGIN_MODE, Constants.USER_RENTAL) == Constants.USER_RENTAL) {
                    utils.setInt("lender", 1);
                } else {
                    utils.setInt(Constants.ISSELLER, Constants.IS_SELLER);
                }
                hideTip();
                ((ListedTulsFragment) mAdapter.getFragment(0)).setLocalData();
                updateTabCount();
            } else if (requestCode == TULDETAIL) {
                ((ListedTulsFragment) mAdapter.getFragment(0)).setLocalData();
                updateTabCount();
            } else if (requestCode == REFRESH) {
                ((MyShortListFragment) mAdapter.getFragment(1)).setLocalData();
                updateTabCount();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void hitAPI() {
        if (utils.getInt(Constants.USER_LOGIN_MODE, Constants.USER_RENTAL) == Constants.USER_RENTAL) {
            Call<ProfileModel> call = RetrofitClient.getInstance().viewProfile(utils.getString("access_token", ""));
            call.enqueue(new Callback<ProfileModel>() {
                @Override
                public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                    if (response.body().getResponse() != null) {

                        utils.setInt(Constants.BLOCKSTATUS, response.body().getResponse().getBlock_status());
                        utils.setInt(Constants.ISEMAILSKIP, response.body().getResponse().getIs_email_skip());
                        utils.setInt(Constants.EMAIL_VERIFY, response.body().getResponse().getEmail_verify());

                        /// Listed Tul Data...
                        ((ListedTulsFragment) mAdapter.getFragment(0)).setServerData(response.body().getResponse().getTools());
                        addListedTul(response.body().getResponse().getTools());

                        /// ShortList Tul Data
                        ((MyShortListFragment) mAdapter.getFragment(1)).setServerData(response.body().getResponse().getWishlist());
                        addShortListedTul(response.body().getResponse().getWishlist());
                    } else {
                        if (response.body().error.getCode().equals(errorAccessToken)) {
                            moveToSplash();
                        } else {
                            showAlert(llMain, response.body().error.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ProfileModel> call, Throwable t) {
                    showAlert(llMain, t.getLocalizedMessage());
                }
            });
        } else {

            Call<ProfileModel> callSeller = RetrofitClient.getInstance().viewSellerProfile(utils.getString("access_token", ""));
            callSeller.enqueue(new Callback<ProfileModel>() {
                @Override
                public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                    if (response.body().getResponse() != null) {

                        utils.setInt(Constants.BLOCKSTATUS, response.body().getResponse().getBlock_status());
                        utils.setInt(Constants.ISEMAILSKIP, response.body().getResponse().getIs_email_skip());
                        utils.setInt(Constants.EMAIL_VERIFY, response.body().getResponse().getEmail_verify());

                        /// Listed Tul Data...
                        ((ListedTulsFragment) mAdapter.getFragment(0)).setServerData(response.body().getResponse().getTools());
                        addListedTul(response.body().getResponse().getTools());

                        /// ShortList Tul Data
                        ((MyShortListFragment) mAdapter.getFragment(1)).setServerData(response.body().getResponse().getWishlist());
                        addShortListedTul(response.body().getResponse().getWishlist());

                    } else {
                        if (response.body().error.getCode().equals(errorAccessToken)) {
                            moveToSplash();
                        } else {
                            showAlert(llMain, response.body().error.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ProfileModel> call, Throwable t) {
                    showAlert(llMain, t.getLocalizedMessage());
                }
            });
        }
    }

    public void updateTabCount() {
        if (db.getAllListedTuls().size() > 0)
            tabs.getTabAt(0).setText(getString(R.string.listed_tuls) + " (" + String.valueOf(db.getAllListedTuls().size()) + ")");
        else
            tabs.getTabAt(0).setText(getString(R.string.listed_tuls));

        if (db.getAllShortListedTuls().size() > 0)
            tabs.getTabAt(1).setText(getString(R.string.my_short_list) + " (" + String.valueOf(db.getAllShortListedTuls().size()) + ")");
        else
            tabs.getTabAt(1).setText(getString(R.string.my_short_list));
    }

    private void addListedTul(List<ProfileModel.ResponseBean.ToolsBean> mTulsArray) {
        for (ProfileModel.ResponseBean.ToolsBean tulItem : mTulsArray) {
            db.addListedTul(tulItem);
            for (AttachmentModel attachmentItem : tulItem.getAttachment()) {
                db.addAttachment(attachmentItem);
            }
        }
        updateTabCount();
    }

    private void addShortListedTul(List<NearByTulListingModel.ResponseBean> mWishListArray) {
        ArrayList<String> mWishListIdsArray = new ArrayList<>();
        mWishListIdsArray.addAll(db.getAllShortListedTulIds());
        for (NearByTulListingModel.ResponseBean shortListItem : mWishListArray) {
            mWishListIdsArray.remove(shortListItem.getId() + "");
            db.addShortListedTul(shortListItem);
            for (AttachmentModel attachmentItem : shortListItem.getAttachment()) {
                db.addShortListAttachment(attachmentItem);
            }
        }
        for (String tulId : mWishListIdsArray) {
            db.deleteShortListTul(tulId);
        }
        updateTabCount();
    }

    @Override
    public void onBackPressed() {
        moveBack();
    }

    private void moveBack() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    private void hitTransactionAPI() {
        Call<TransactionModel> call = RetrofitClient.getInstance().getTransactionPercentage(utils.getString("access_token", ""));
        call.enqueue(new Callback<TransactionModel>() {
            @Override
            public void onResponse(Call<TransactionModel> call, Response<TransactionModel> response) {
                if (response.body().getResponse() != null) {
                    Log.e("Transaction %age = ", response.body().getResponse());
                    utils.setString("transaction_percentage", response.body().getResponse());
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        showAlert(llMain, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<TransactionModel> call, Throwable t) {

            }
        });
    }

    private void hitEmailVerifyAPI() {
        showProgress();
        Call<TransactionModel> call = RetrofitClient.getInstance().getTransactionPercentage(utils.getString("access_token", ""));
        call.enqueue(new Callback<TransactionModel>() {
            @Override
            public void onResponse(Call<TransactionModel> call, Response<TransactionModel> response) {
                hideProgress();
                if (response.body().getResponse() != null) {
                    Log.e("Transaction %age = ", response.body().getResponse());
                    utils.setString("transaction_percentage", response.body().getResponse());

                    utils.setInt(Constants.ISEMAILSKIP, response.body().getIs_email_skip());
                    utils.setInt(Constants.EMAIL_VERIFY, response.body().getEmail_verify());
                    if (utils.getInt(Constants.EMAIL_VERIFY, 0) == 0) {
                        new VerifyEmailDialog(mContext, mWidth, getResources().getString(R.string.verify_email)).showDialog();
                    }
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        showAlert(llMain, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<TransactionModel> call, Throwable t) {

            }
        });
    }


}
