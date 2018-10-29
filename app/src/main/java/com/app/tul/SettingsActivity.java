package com.app.tul;

import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import api.RetrofitClient;
import butterknife.BindView;
import io.doorbell.android.Doorbell;
import model.DemoModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.FirebaseListeners;

public class SettingsActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.txt_toolbar_title)
    TextView txtToolbarTitle;

    @BindView(R.id.ll_main)
    LinearLayout llMain;

    @BindView(R.id.tv_change_password)
    TextView tvChangePassword;
    @BindView(R.id.view_change)
    View viewChange;

    @BindView(R.id.ll_notifications)
    LinearLayout llNotifications;
    @BindView(R.id.tv_notifications)
    TextView tvNotifications;
    @BindView(R.id.sc_notifcations)
    SwitchCompat scNotifications;

    @BindView(R.id.ll_email)
    LinearLayout llEmail;
    @BindView(R.id.tv_email_notifications)
    TextView tvEmailNotifications;
    @BindView(R.id.sc_email_notifcations)
    SwitchCompat scEmailNotifications;

    @BindView(R.id.tv_blocked_user)
    TextView tvBlockedUser;
    @BindView(R.id.tv_about)
    TextView tvAbout;
    @BindView(R.id.tv_privacy)
    TextView tvPrivacy;
    @BindView(R.id.tv_terms)
    TextView tvTerms;

    @BindView(R.id.tv_faq)
    TextView tvFaq;
    @BindView(R.id.tv_feedback)
    TextView tvFeedback;
    @BindView(R.id.tv_tell_friend)
    TextView tvTellFriend;
    @BindView(R.id.tv_dispute)
    TextView tvDispute;
    @BindView(R.id.tv_change_language)
    TextView tvChangeLanguage;

    @BindView(R.id.tv_instagram)
    TextView tvInstagram;
    @BindView(R.id.tv_logout)
    TextView tvLogout;

    @BindView(R.id.ll_version)
    LinearLayout llVersion;
    @BindView(R.id.img_version)
    ImageView imgVersion;
    @BindView(R.id.tv_version)
    TextView tvVersion;

    @BindView(R.id.tv_delete_account)
    TextView tvDeleteAccount;


    @Override
    protected int getContentView() {
        return R.layout.activity_settings;
    }

    @Override
    protected void initUI() {

        txtToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.05));
        txtToolbarTitle.setText(R.string.settings_);

        llMain.setPadding(mWidth / 32, 0, mWidth / 32, 0);

        tvChangePassword.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * (0.04)));
        tvChangePassword.setPadding(0, mHeight / 40, 0, mHeight / 40);

        tvNotifications.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * (0.04)));
        tvNotifications.setPadding(0, mHeight / 40, 0, mHeight / 40);

        tvEmailNotifications.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * (0.04)));
        tvEmailNotifications.setPadding(0, mHeight / 40, 0, mHeight / 40);

        tvBlockedUser.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * (0.04)));
        tvBlockedUser.setPadding(0, mHeight / 40, 0, mHeight / 40);

        tvAbout.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * (0.04)));
        tvAbout.setPadding(0, mHeight / 40, 0, mHeight / 40);

        tvPrivacy.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * (0.04)));
        tvPrivacy.setPadding(0, mHeight / 40, 0, mHeight / 40);

        tvTerms.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * (0.04)));
        tvTerms.setPadding(0, mHeight / 40, 0, mHeight / 40);

        tvFaq.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * (0.04)));
        tvFaq.setPadding(0, mHeight / 40, 0, mHeight / 40);

        tvFeedback.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * (0.04)));
        tvFeedback.setPadding(0, mHeight / 40, 0, mHeight / 40);

        tvTellFriend.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * (0.04)));
        tvTellFriend.setPadding(0, mHeight / 40, 0, mHeight / 40);

        tvDispute.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * (0.04)));
        tvDispute.setPadding(0, mHeight / 40, 0, mHeight / 40);

        tvChangeLanguage.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * (0.04)));
        tvChangeLanguage.setPadding(0, mHeight / 40, 0, mHeight / 40);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(mWidth / 8, mHeight / 32, mWidth / 8, 0);
        tvInstagram.setLayoutParams(layoutParams);
        tvLogout.setLayoutParams(layoutParams);
        tvDeleteAccount.setLayoutParams(layoutParams);

        tvInstagram.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * (0.04)));
        tvInstagram.setPadding(0, mHeight / 42, 0, mHeight / 42);

        tvLogout.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * (0.04)));
        tvLogout.setPadding(0, mHeight / 42, 0, mHeight / 42);

        tvDeleteAccount.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * (0.04)));
        tvDeleteAccount.setPadding(0, mHeight / 42, 0, mHeight / 42);

        llVersion.setPadding(0, mHeight / 52, 0, mHeight / 52);

        LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(mWidth / 11, mWidth / 11);
        imgVersion.setLayoutParams(imgParams);

        tvVersion.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));


    }

    @Override
    protected void onCreateStuff() {
        if (utils.getInt("path", 0) == 2) {/// fb login
            tvChangePassword.setVisibility(View.GONE);
            viewChange.setVisibility(View.GONE);
        }

        scEmailNotifications.setChecked(true);

        if (utils.getInt("ring_notification", 0) == 1)
            scNotifications.setChecked(false);
        else
            scNotifications.setChecked(true);

        scEmailNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        scNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    utils.setInt("ring_notification", 0);
                else
                    utils.setInt("ring_notification", 1);
            }
        });
    }

    @Override
    protected void initListener() {
        tvTellFriend.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        tvAbout.setOnClickListener(this);
        tvBlockedUser.setOnClickListener(this);
        tvFaq.setOnClickListener(this);
        tvFeedback.setOnClickListener(this);
        tvTellFriend.setOnClickListener(this);
        tvLogout.setOnClickListener(this);
        tvDispute.setOnClickListener(this);
        tvChangePassword.setOnClickListener(this);
        tvTerms.setOnClickListener(this);
        tvPrivacy.setOnClickListener(this);
        tvDeleteAccount.setOnClickListener(this);
        tvInstagram.setOnClickListener(this);
        tvChangeLanguage.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return SettingsActivity.this;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_tell_friend:
                intent = new Intent(this, TellFriendActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
            case R.id.img_back:
                moveBack();
                break;
            case R.id.tv_blocked_user:
                intent = new Intent(this, BlockedUsersActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
            case R.id.tv_faq:
                try {
                    Uri uri = Uri.parse("http://www.thetulapp.com/faq");
                    Intent inTerms = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(inTerms);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_feedback:
                new Doorbell(this, 7676, "9cgFDjl8eEY43ieUzw6lN0l7fQY6gL8dHZ8pmCvoYYa8bCKOmgru7ULmnOurB5eI").show();
                break;
            case R.id.tv_about:
                try {
                    Uri uri = Uri.parse("http://www.thetulapp.com/about");
                    Intent inTerms = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(inTerms);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_terms:
                try {
                    Uri uri = Uri.parse("http://www.thetulapp.com/terms");
                    Intent inTerms = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(inTerms);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_privacy:
                try {
                    Uri uri = Uri.parse("http://www.thetulapp.com/privacypolicy");
                    Intent inTerms = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(inTerms);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.tv_logout:
                alertLogoutDialog();
                break;
            case R.id.tv_dispute:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:"));
                String[] recipients = {"info@thetulapp.com"};
                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                intent.putExtra(Intent.EXTRA_SUBJECT, "DISPUTE");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(intent);
                break;

            case R.id.tv_change_password:
                intent = new Intent(mContext, ChangePasswordActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                            tvChangePassword, getString(R.string.transition));
                    startActivity(intent, option.toBundle());
                } else {
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                }

                break;
            case R.id.tv_delete_account:
//                hitDeleteAccountAPI();
                deleteAccount(mContext);
                break;
            case R.id.tv_instagram:
                Uri uri = Uri.parse("http://instagram.com/_u/tulapp");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://instagram.com/tulapp")));
                }
                break;

            case R.id.tv_change_language:
                intent = new Intent(mContext, ChangeLanguageActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;

        }
    }

    private void moveBack() {
        finish();
        overridePendingTransition(R.anim.slidedown_in, R.anim.slidedown_out);
    }

    void alertLogoutDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SettingsActivity.this);
        alertDialog.setTitle(R.string.logout);
        alertDialog.setMessage(R.string.logout_warning);
        alertDialog.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                NotificationManager notificationManager = (NotificationManager) mContext
                        .getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancelAll();
                utils.clear_shf();
                db.deleteAllTables();
                FirebaseListeners.getInstance().removeAllListeners();
                Intent inSplash = new Intent(mContext, UserModeActivity.class);
                inSplash.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                inSplash.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                mContext.startActivity(inSplash);
                dialog.dismiss();
                System.exit(2);
            }
        });
        alertDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    public void deleteAccount(final Context con) {
        AlertDialog.Builder builder = new AlertDialog.Builder(con);
        builder.setMessage(R.string.delete_account_)
                .setCancelable(false)
                .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        hitDeleteAccountAPI();
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

    private void hitDeleteAccountAPI() {
        showProgress();
        Call<DemoModel> call = RetrofitClient.getInstance().deleteAccount(utils.getString("access_token", ""));
        call.enqueue(new Callback<DemoModel>() {
            @Override
            public void onResponse(Call<DemoModel> call, Response<DemoModel> response) {
                hideProgress();
                if (response.body().response != null) {
                    moveToSplash();
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else if (response.body().error.getCode().equals("300")) {
                        finish();
                        Toast.makeText(mContext, response.body().error.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        showAlert(llMain, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<DemoModel> call, Throwable t) {
                showAlert(llMain, t.getLocalizedMessage());
                hideProgress();
            }
        });
    }

}
