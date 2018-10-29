package com.app.tul;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import adapters.NotificationAdapter;
import api.RetrofitClient;
import butterknife.BindView;
import model.DemoModel;
import model.NotificationModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Constants;

public class NotificationActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.txt_toolbar_title)
    TextView txtToolbarTitle;

    @BindView(R.id.rv_notification)
    RecyclerView rvNotification;
    @BindView(R.id.ll_main)
    RelativeLayout llMain;
    @BindView(R.id.txt_no_tuls)
    TextView txtNotTuls;
    @BindView(R.id.progress)
    ProgressBar progressBar;

    LinearLayoutManager layoutManager;
    NotificationAdapter mAdapter;
    ArrayList<NotificationModel.ResponseBean> mNotificationArray = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_notification;
    }


    @Override
    protected void initUI() {

        txtToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        txtToolbarTitle.setText(R.string.notifications_caps);

        txtNotTuls.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));

        layoutManager = new LinearLayoutManager(this);
        rvNotification.setLayoutManager(layoutManager);
    }

    @Override
    protected void onCreateStuff() {

        NotificationManager notificationManager = (NotificationManager) mContext
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();

        setData();
        if (connectedToInternet()) {
            if (db.getAllNotifications().size() > 0)
                progressBar.setVisibility(View.GONE);
            else
                progressBar.setVisibility(View.VISIBLE);
            hitAPI();
        } else {
            showInternetAlert(llMain);
            progressBar.setVisibility(View.GONE);
        }
    }

    private void hitAPI() {
        Call<NotificationModel> call = RetrofitClient.getInstance().getNotifications(utils.getString("access_token", ""));
        call.enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {
                if (response.body().getResponse() != null) {
                    addToLocal(response.body().getResponse());
                    setData();
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        showAlert(llMain, response.body().error.getMessage());
                    }
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<NotificationModel> call, Throwable t) {
                showAlert(llMain, t.getLocalizedMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void addToLocal(List<NotificationModel.ResponseBean> response) {
        db.deleteNotifications();
        for (NotificationModel.ResponseBean responseBean : response) {
            db.addNotification(responseBean);
        }
    }

    private void setData() {
        mNotificationArray.clear();
        mNotificationArray.addAll(db.getAllNotifications());
//        Collections.reverse(mNotificationArray);
        if (mNotificationArray.size() > 0) {
            if (rvNotification.getAdapter() == null) {
                mAdapter = new NotificationAdapter(NotificationActivity.this, mNotificationArray);
                rvNotification.setAdapter(mAdapter);
            } else
                mAdapter.notifyDataSetChanged();
            rvNotification.setVisibility(View.VISIBLE);
            txtNotTuls.setVisibility(View.GONE);
        } else {
            rvNotification.setVisibility(View.GONE);
            txtNotTuls.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initListener() {
        imgBack.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return NotificationActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                moveBack();
                break;
        }
    }

    public void markAsRead(int position) {
        if (mNotificationArray.get(position).getRead_status() == 0)
            hitReadNotificationAPI(mNotificationArray.get(position).getId(), position);

        Intent intent;
        if (mNotificationArray.get(position).getNotification_type() == 1) {
            /// user books a tul push on lender side.
            intent = new Intent(this, PushBookingTulDetailActivity.class);
            intent.putExtra("booking_id", String.valueOf(mNotificationArray.get(position).getBooking_id()));
            intent.putExtra("path", "show_rented");
            startActivity(intent);
        } else if (mNotificationArray.get(position).getNotification_type() == 2) {
            /// user cancelled booking push on lender side.
            /*intent = new Intent(this, PendingTaskActivity.class);
            intent.putExtra("booking_id", String.valueOf(mNotificationArray.get(position).getBooking_id()));
            intent.putExtra("push_type", String.valueOf(mNotificationArray.get(position).getNotification_type()));
            startActivity(intent);*/
        } else if (mNotificationArray.get(position).getNotification_type() == 3) {
            /// lender cancelled booking push on user side.
            /*intent = new Intent(this, PendingTaskActivity.class);
            intent.putExtra("booking_id", String.valueOf(mNotificationArray.get(position).getBooking_id()));
            intent.putExtra("push_type", String.valueOf(mNotificationArray.get(position).getNotification_type()));
            startActivity(intent);*/
        } else if (mNotificationArray.get(position).getNotification_type() == 4) {
            /// lender marked as  received push on user side.
            intent = new Intent(this, PendingTaskActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("booking_id", String.valueOf(mNotificationArray.get(position).getBooking_id()));
            intent.putExtra("push_type", String.valueOf(mNotificationArray.get(position).getNotification_type()));
            startActivity(intent);
        } else if (mNotificationArray.get(position).getNotification_type() == 5) {
            /// user mark received a tul push on lender side.
            intent = new Intent(this, PushBookingTulDetailActivity.class);
            intent.putExtra("booking_id", String.valueOf(mNotificationArray.get(position).getBooking_id()));
            intent.putExtra("path", "show_rented");
            startActivity(intent);
        } else if (mNotificationArray.get(position).getNotification_type() == 6) {
            /// user mark not received  push on lender side.
            intent = new Intent(this, PushBookingTulDetailActivity.class);
            intent.putExtra("booking_id", String.valueOf(mNotificationArray.get(position).getBooking_id()));
            intent.putExtra("path", "show_rented");
            startActivity(intent);
        } else if (mNotificationArray.get(position).getNotification_type() == 7) {
            /// user marked returned push on lender side.
            intent = new Intent(this, PendingTaskActivity.class);
            intent.putExtra("booking_id", String.valueOf(mNotificationArray.get(position).getBooking_id()));
            intent.putExtra("push_type", String.valueOf(mNotificationArray.get(position).getNotification_type()));
            startActivity(intent);
        } else if (mNotificationArray.get(position).getNotification_type() == 8) {
            /// lender mark not received  a tul push on user side.
            intent = new Intent(this, PushBookingTulDetailActivity.class);
            intent.putExtra("booking_id", String.valueOf(mNotificationArray.get(position).getBooking_id()));
            intent.putExtra("path", "show_owner");
            startActivity(intent);
        } else if (mNotificationArray.get(position).getNotification_type() == 11) {
            /// 24 hours prior to the booking date: Lender
            intent = new Intent(this, PushBookingTulDetailActivity.class);
            intent.putExtra("booking_id", String.valueOf(mNotificationArray.get(position).getBooking_id()));
            intent.putExtra("path", "show_rented");
            startActivity(intent);
        } else if (mNotificationArray.get(position).getNotification_type() == 12) {
            /// 24 hours prior to the booking date: User
            intent = new Intent(this, PushBookingTulDetailActivity.class);
            intent.putExtra("booking_id", String.valueOf(mNotificationArray.get(position).getBooking_id()));
            intent.putExtra("path", "show_owner");
            startActivity(intent);
        }

        mNotificationArray.get(position).setRead_status(1);
        mAdapter.notifyDataSetChanged();
        db.updateNotificationReadStatus(mNotificationArray.get(position).getId(), "1");
    }

    private void hitReadNotificationAPI(int id, final int position) {
        Call<DemoModel> call = RetrofitClient.getInstance().readNotifications(utils.getString("access_token", ""),
                String.valueOf(id));
        call.enqueue(new Callback<DemoModel>() {
            @Override
            public void onResponse(Call<DemoModel> call, Response<DemoModel> response) {
                if (response.body().response == null) {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        showAlert(llMain, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<DemoModel> call, Throwable t) {
                showAlert(llMain, t.getLocalizedMessage());
                mNotificationArray.get(position).setRead_status(0);
                mAdapter.notifyDataSetChanged();
                db.updateNotificationReadStatus(mNotificationArray.get(position).getId(), "0");
            }
        });
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

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (connectedToInternet()) {
                    hitAPI();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onBackPressed() {
        moveBack();
    }

    private void moveBack() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
        overridePendingTransition(R.anim.slidedown_in, R.anim.slidedown_out);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
