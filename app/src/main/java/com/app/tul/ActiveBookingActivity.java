package com.app.tul;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adapters.MyBookingAdapter;
import adapters.TulLentAdapter;
import api.RetrofitClient;
import butterknife.BindView;
import interfaces.UpdateDataListener;
import model.AttachmentModel;
import model.BookTulModel;
import model.BookingDeleteModel;
import model.MyBookingModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Constants;

public class ActiveBookingActivity extends BaseActivity implements UpdateDataListener {

    private static final int CANCEL_BOOKING = 1;
    private static final int CANCEL_LENT = 2;
    private static final int HANDOVER_TUL = 3;
    private static final int RECEIVED_TUL = 4;
    private static final int RETURN_TUL = 5;
    private static final int LENDER_RECEIVED_TUL = 6;
    @BindView(R.id.img_back)
    ImageView imgBckImg;
    @BindView(R.id.txt_toolbar_title)
    TextView txtToolbarTitle;


    @BindView(R.id.ll_main)
    LinearLayout llMain;

    @BindView(R.id.rl_booking)
    RelativeLayout rlBooking;
    @BindView(R.id.rv_my_booking)
    RecyclerView rvBooking;
    @BindView(R.id.rv_lent)
    RecyclerView rvLent;

    @BindView(R.id.rl_tul_lent)
    RelativeLayout rlTulLent;
    @BindView(R.id.txt_no_active_tuls)
    TextView txtNoActiveTuls;
    @BindView(R.id.txt_no_lent_tuls)
    TextView txtNoLentTuls;

    LinearLayoutManager layoutManagerBooking;
    MyBookingAdapter myBookingAdapter;
    ArrayList<BookTulModel.ResponseBean> mBookingArray = new ArrayList<>();
    private boolean isLoadingBooking;

    LinearLayoutManager layoutManagerLent;
    TulLentAdapter mTulLentAdapter;
    ArrayList<BookTulModel.ResponseBean> mLentArray = new ArrayList<>();
    private boolean isLoadingLent;

    private int mOffset = 0, mOffsetLent = 0;
    private int mSelectedTab = 0;


    @Override
    protected int getContentView() {
        return R.layout.activity_active_booking;
    }

    @Override
    protected void initUI() {
        txtToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        txtToolbarTitle.setText("ACTIVE BOOKINGS");

        txtNoActiveTuls.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        txtNoActiveTuls.setPadding(0, mHeight / 5, 0, 0);

        txtNoLentTuls.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        txtNoLentTuls.setPadding(0, mHeight / 5, 0, 0);

        layoutManagerBooking = new LinearLayoutManager(this);
        rvBooking.setPadding(mWidth / 20, 0, mWidth / 20, 0);
        rvBooking.setLayoutManager(layoutManagerBooking);

        layoutManagerLent = new LinearLayoutManager(this);
        rvLent.setPadding(mWidth / 20, 0, mWidth / 20, 0);
        rvLent.setLayoutManager(layoutManagerLent);

        rvBooking.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int pastVisibleItems = layoutManagerBooking.findLastVisibleItemPosition();
                if (mBookingArray.size() > 9) {
                    if (!isLoadingBooking && pastVisibleItems == mBookingArray.size() - 1) {
                        ///visible loader
                        mOffset++;
                        mBookingArray.add(null);
                        recyclerView.post(new Runnable() {
                            public void run() {
                                myBookingAdapter.notifyItemInserted(mBookingArray.size() - 1);
                            }
                        });
                        hitMyBookingsAPI();
                        isLoadingBooking = true;
                    }
                }
            }
        });


        rvLent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int pastVisibleItems = layoutManagerLent.findLastVisibleItemPosition();
                if (mLentArray.size() > 9) {
                    if (!isLoadingLent && pastVisibleItems == mLentArray.size() - 1) {
                        ///visible loader
                        mOffsetLent++;
                        mLentArray.add(null);
                        recyclerView.post(new Runnable() {
                            public void run() {
                                mTulLentAdapter.notifyItemInserted(mLentArray.size() - 1);
                            }
                        });
                        hitTulLentAPI();
                        isLoadingLent = true;
                    }
                }
            }
        });

    }

    @Override
    protected void onCreateStuff() {

        NotificationManager notificationManager = (NotificationManager) mContext
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();

        txtNoLentTuls.setVisibility(View.GONE);
        if (db.getAllBookingIds().size() > 0) {
            mBookingArray.addAll(db.getAllBookingsTuls());
            rvBooking.setVisibility(View.VISIBLE);
            txtNoActiveTuls.setVisibility(View.GONE);
        } else {
            txtNoActiveTuls.setVisibility(View.VISIBLE);
        }

        myBookingAdapter = new MyBookingAdapter(mContext, mBookingArray);
        rvBooking.setAdapter(myBookingAdapter);

        if (db.getAllLendedTulIds().size() > 0) {
            mLentArray.addAll(db.getAllLentedTuls());
        }
        mTulLentAdapter = new TulLentAdapter(mContext, mLentArray);
        rvLent.setAdapter(mTulLentAdapter);

        if (connectedToInternet()) {
            hitMyBookingsAPI();
            hitTulLentAPI();
        } else {
            showAlert(llMain, getString(R.string.internet));
        }
    }

    private void hitMyBookingsAPI() {
        Call<MyBookingModel> call = RetrofitClient.getInstance().getMyBookings(utils.getString("access_token", ""),
                mOffset);
        call.enqueue(new Callback<MyBookingModel>() {
            @Override
            public void onResponse(Call<MyBookingModel> call, Response<MyBookingModel> response) {
                if (response.body().getResponse() != null) {
                    if (mOffset == 0)/// clear Array.
                        mBookingArray.clear();
                    else {
                        mBookingArray.remove(mBookingArray.size() - 1);
                        myBookingAdapter.notifyItemRemoved(mBookingArray.size());
                    }

                    mBookingArray.addAll(response.body().getResponse());
                    myBookingAdapter.notifyDataSetChanged();

                    if (isLoadingBooking)
                        isLoadingBooking = false;

                    if (mBookingArray.size() > 0) {
                        txtNoActiveTuls.setVisibility(View.GONE);
                    } else {
                        txtNoActiveTuls.setVisibility(View.VISIBLE);
                    }

                    /// putting values in local...
                    utils.setInt("booking_count", response.body().getCount().getMy_bookings_count());
                    utils.setInt("lent_count", response.body().getCount().getTool_lent_count());

                    /// adding booking to local DB...
                    addMyBookingsToLocal(response.body().getResponse());

                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        showAlert(rlBooking, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<MyBookingModel> call, Throwable t) {
                showAlert(rlBooking, t.getLocalizedMessage());
            }
        });
    }

    private void hitTulLentAPI() {
        Call<MyBookingModel> call = RetrofitClient.getInstance().getTulLent(utils.getString("access_token", ""),
                mOffsetLent);
        call.enqueue(new Callback<MyBookingModel>() {
            @Override
            public void onResponse(Call<MyBookingModel> call, Response<MyBookingModel> response) {
                if (response.body().getResponse() != null) {

                    if (mOffsetLent == 0)
                        mLentArray.clear();
                    else {
                        mLentArray.remove(mLentArray.size() - 1);
                        mTulLentAdapter.notifyItemRemoved(mLentArray.size());
                    }

                    mLentArray.addAll(response.body().getResponse());
                    mTulLentAdapter.notifyDataSetChanged();

                    if (isLoadingLent)
                        isLoadingLent = false;

                    if (response.body().getResponse().size() > 0)
                        txtNoLentTuls.setVisibility(View.GONE);

                    /// adding lent tuls to local DB...
                    addLendedTulToLocal(response.body().getResponse());
                } else {
                    if (response.body().error.getCode().equals(errorAccessToken)) {
                        moveToSplash();
                    } else {
                        showAlert(rlBooking, response.body().error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<MyBookingModel> call, Throwable t) {
                showAlert(rlBooking, t.getLocalizedMessage());
            }
        });
    }

    private void addMyBookingsToLocal(List<BookTulModel.ResponseBean> response) {
        ArrayList<BookingDeleteModel> mBookingIdsArray = new ArrayList<>();
        mBookingIdsArray.addAll(db.getAllBookingIds());

        for (BookingDeleteModel bookingItem : mBookingIdsArray) {
            if (mOffset == 0)
                db.deleteBooking(bookingItem.getBookingId(), bookingItem.getTulId());
        }

        for (BookTulModel.ResponseBean responseBean : response) {
            db.addMyBookingByActiveBooking(responseBean, this);
            for (AttachmentModel attachmentItem : responseBean.getAttachment()) {
                db.addMyBookingsAttachment(attachmentItem);
            }
        }
    }

    private void addLendedTulToLocal(List<BookTulModel.ResponseBean> response) {
        ArrayList<BookingDeleteModel> mLentedIdsArray = new ArrayList<>();
        mLentedIdsArray.addAll(db.getAllLendedTulIds());

        for (BookingDeleteModel bookingItem : mLentedIdsArray) {
            if (mOffsetLent == 0)
                db.deleteLendedTul(bookingItem.getBookingId(), bookingItem.getTulId());
        }

        for (BookTulModel.ResponseBean responseBean : response) {
            db.addLentedTuls(responseBean, this);
            for (AttachmentModel attachmentItem : responseBean.getAttachment()) {
                db.addLentedTulAttachment(attachmentItem);
            }
        }
    }


    @Override
    protected void initListener() {
        imgBckImg.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return ActiveBookingActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_my_booking:

                txtNoLentTuls.setVisibility(View.GONE);
                rvLent.setVisibility(View.GONE);
                rvBooking.setVisibility(View.VISIBLE);

                if (mBookingArray.size() <= 0) {
                    txtNoActiveTuls.setVisibility(View.VISIBLE);
                    rvBooking.setVisibility(View.GONE);
                } else {
                    rvBooking.setVisibility(View.VISIBLE);
                    txtNoActiveTuls.setVisibility(View.GONE);
                }
                break;
            case R.id.ll_tul_lent:

                txtNoActiveTuls.setVisibility(View.GONE);
                rvBooking.setVisibility(View.GONE);
                rvLent.setVisibility(View.VISIBLE);

                if (mLentArray.size() <= 0) {
                    txtNoLentTuls.setVisibility(View.VISIBLE);
                    rvLent.setVisibility(View.GONE);
                } else {
                    rvLent.setVisibility(View.VISIBLE);
                    txtNoLentTuls.setVisibility(View.GONE);
                }
                break;
            case R.id.img_back:
                moveBack();
                break;
        }
    }

    public void lent() {
        mSelectedTab = 1;
        rlBooking.setVisibility(View.GONE);
        rlTulLent.setVisibility(View.VISIBLE);

        if (mLentArray.size() <= 0) {
            txtNoLentTuls.setVisibility(View.VISIBLE);
        } else {
            rvLent.setVisibility(View.VISIBLE);
        }
    }

    public void booking() {
        mSelectedTab = 0;
        rlBooking.setVisibility(View.VISIBLE);
        rlTulLent.setVisibility(View.GONE);

        if (mBookingArray.size() <= 0) {
            txtNoActiveTuls.setVisibility(View.VISIBLE);
            rvBooking.setVisibility(View.VISIBLE);
        } else {
            rvBooking.setVisibility(View.VISIBLE);
            txtNoActiveTuls.setVisibility(View.GONE);
        }
    }


    private void moveBack() {
        if (LandingActivity.landingActivity != null) {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
            overridePendingTransition(R.anim.slidedown_in, R.anim.slidedown_out);
        } else {
            Intent intent = new Intent(mContext, LandingActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        moveBack();
    }

    public void moveToTulDetailBooking(int position) {
        /// borrower
        Intent intent = new Intent(mContext, BookingTulDetailActivity.class);
        intent.putExtra("cancel_status", "2");
        intent.putExtra("position", position);
        intent.putExtra("booking_data", mBookingArray.get(position));
        startActivityForResult(intent, CANCEL_BOOKING);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    public void moveToTulDetailLent(int position) {
        /// lender
        Intent intent = new Intent(mContext, BookingTulDetailActivity.class);
        intent.putExtra("cancel_status", "1");
        intent.putExtra("position", position);
        intent.putExtra("booking_data", mLentArray.get(position));
        startActivityForResult(intent, CANCEL_LENT);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    public void cancelTulBooking(final String tool_id, final String booking_id, final int position, String mCancelationCharges) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.cancel_booking_charged_1) + mCancelationCharges + getString(R.string.cancel_booking_charged_2))
                .setCancelable(false)
                .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(mContext, SignatureActivity.class);
                        intent.putExtra("tulData", mBookingArray.get(position));
                        intent.putExtra("path", "1");/// cancel
                        intent.putExtra("cancel_status", "2");
                        intent.putExtra("tool_id", tool_id);
                        intent.putExtra("booking_id", booking_id);
                        intent.putExtra("position", position);
                        startActivityForResult(intent, CANCEL_BOOKING);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
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

    public void cancelTulLent(final String tool_id, final String booking_id, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.overall_rating_reduced)
                .setCancelable(false)
                .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(mContext, SignatureActivity.class);
                        intent.putExtra("tulData", mLentArray.get(position));
                        intent.putExtra("path", "1");/// cancel
                        intent.putExtra("cancel_status", "1");
                        intent.putExtra("tool_id", tool_id);
                        intent.putExtra("booking_id", booking_id);
                        intent.putExtra("position", position);
                        startActivityForResult(intent, CANCEL_LENT);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
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


    public void handOverTul(final String tool_id, final String booking_id, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.mark_tull_handled_to_borrower)
                .setCancelable(false)
                .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(mContext, SignatureActivity.class);
                        intent.putExtra("path", "2");/// handover
                        intent.putExtra("tool_id", tool_id);
                        intent.putExtra("booking_id", booking_id);
                        intent.putExtra("position", position);
                        startActivityForResult(intent, HANDOVER_TUL);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
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

    public void receivedTul(final String tool_id, final String booking_id, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.mark_tul_received)
                .setCancelable(false)
                .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(mContext, SignatureActivity.class);
                        intent.putExtra("path", "3");/// received
                        intent.putExtra("tool_id", tool_id);
                        intent.putExtra("booking_id", booking_id);
                        intent.putExtra("position", position);
                        startActivityForResult(intent, RECEIVED_TUL);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
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

    public void returnTul(final String tool_id, final String booking_id, final int position,
                          final String owner, final String ownerPic, final String totalAmount, final String address,
                          final int user_id, final int borrower_id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.tull_marked_return_warning)
                .setCancelable(false)
                .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(mContext, SignatureActivity.class);
                        intent.putExtra("path", "4");/// return
                        intent.putExtra("tool_id", tool_id);
                        intent.putExtra("booking_id", booking_id);
                        intent.putExtra("position", position);
                        intent.putExtra("owner", owner);
                        intent.putExtra("ownerPic", ownerPic);
                        intent.putExtra("totalAmount", totalAmount);
                        intent.putExtra("address", address);
                        intent.putExtra("user_id", user_id);
                        intent.putExtra("borrower_id", borrower_id);
                        startActivityForResult(intent, RETURN_TUL);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
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

    public void lenderReceivedTul(final String tool_id, final String booking_id, final int position,
                                  final String owner, final String owner_pic, final String total_amount,
                                  final String address, final BookTulModel.ResponseBean responseBean,
                                  final int user_id, final int borrower_id, final String borrower, final String borrower_pic) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.received_your_tul_back)
                .setCancelable(false)
                .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(mContext, SignatureActivity.class);
                        intent.putExtra("tulData", responseBean);
                        intent.putExtra("path", "5");/// lender received tul
                        intent.putExtra("tool_id", tool_id);
                        intent.putExtra("booking_id", booking_id);
                        intent.putExtra("position", position);
                        intent.putExtra("owner", owner);
                        intent.putExtra("ownerPic", owner_pic);
                        intent.putExtra("totalAmount", total_amount);
                        intent.putExtra("address", address);
                        intent.putExtra("user_id", user_id);
                        intent.putExtra("borrower_id", borrower_id);
                        intent.putExtra("borrower", borrower);
                        intent.putExtra("borrower_pic", borrower_pic);
                        startActivityForResult(intent, LENDER_RECEIVED_TUL);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == CANCEL_BOOKING) {

                int count = utils.getInt("booking_count", 0) - 1;
                utils.setInt("booking_count", count);

                /// remove from booking array
                mBookingArray.remove(data.getIntExtra("position", 0));
                myBookingAdapter.notifyDataSetChanged();

                if (mTulLentAdapter != null)
                    mTulLentAdapter.notifyDataSetChanged();

                if (mBookingArray.size() == 0)
                    txtNoActiveTuls.setVisibility(View.VISIBLE);

            } else if (requestCode == CANCEL_LENT) {
                int count = utils.getInt("lent_count", 0) - 1;
                utils.setInt("lent_count", count);

                /// remove from lent array
                mLentArray.remove(data.getIntExtra("position", 0));
                mTulLentAdapter.notifyDataSetChanged();

                if (myBookingAdapter != null)
                    myBookingAdapter.notifyDataSetChanged();

                if (mLentArray.size() == 0)
                    txtNoLentTuls.setVisibility(View.VISIBLE);
            } else if (requestCode == HANDOVER_TUL) {
                /// marked as handover
                mLentArray.get(data.getIntExtra("position", 0)).setLender_status(1);
                mLentArray.get(data.getIntExtra("position", 0)).setHandover_at(data.getStringExtra("date"));
                mTulLentAdapter.notifyDataSetChanged();
                if (mLentArray.size() == 0)
                    txtNoLentTuls.setVisibility(View.VISIBLE);
            } else if (requestCode == RECEIVED_TUL) {
                /// marked as received
                mBookingArray.get(data.getIntExtra("position", 0)).setBorrower_status(1);
                mBookingArray.get(data.getIntExtra("position", 0)).setBorrower_received_at(data.getStringExtra("date"));
                myBookingAdapter.notifyDataSetChanged();
                if (mBookingArray.size() == 0)
                    txtNoActiveTuls.setVisibility(View.VISIBLE);
            } else if (requestCode == RETURN_TUL) {
                /// marked as returned
                mBookingArray.get(data.getIntExtra("position", 0)).setBorrower_status(2);
                mBookingArray.get(data.getIntExtra("position", 0)).setBorrower_returned_at(data.getStringExtra("date"));
                myBookingAdapter.notifyDataSetChanged();
                if (mBookingArray.size() == 0)
                    txtNoActiveTuls.setVisibility(View.VISIBLE);
            } else if (requestCode == LENDER_RECEIVED_TUL) {
                /// marked as received back
//                mLentArray.get(data.getIntExtra("position", 0)).setLender_status(2);
//                mLentArray.get(data.getIntExtra("position", 0)).setLander_received_at(data.getStringExtra("date"));

                mLentArray.remove(data.getIntExtra("position", 0));
                mTulLentAdapter.notifyDataSetChanged();

                if (myBookingAdapter != null)
                    myBookingAdapter.notifyDataSetChanged();

                int count = utils.getInt("lent_count", 0) - 1;
                utils.setInt("lent_count", count);
                if (mLentArray.size() == 0)
                    txtNoLentTuls.setVisibility(View.VISIBLE);
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter(Constants.BROADCAST));
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
                if (intent.getStringExtra("push_type").equals("1")) {
                    /// user book tul push on lender side
                    if (connectedToInternet()) {
                        mOffset = 0;
                        hitMyBookingsAPI();
                    } else
                        showAlert(llMain, errorInternet);
                } else if (intent.getStringExtra("push_type").equals("2")) {
                    ///user cancel booking push on lender side
                    Log.e("push_type", intent.getStringExtra("push_type"));
                    Log.e("Booking_id", intent.getStringExtra("booking_id"));
                    Log.e("tool_id", intent.getStringExtra("tool_id"));
                    /// delete from lender table
                    db.deleteLendedTulByPush(Integer.parseInt(intent.getStringExtra("booking_id"))
                            , intent.getStringExtra("tool_id"));
                } else if (intent.getStringExtra("push_type").equals("3")) {
                    ///lender cancel booking push on user side
                    Log.e("push_type", intent.getStringExtra("push_type"));
                    Log.e("Booking_id", intent.getStringExtra("booking_id"));
                    Log.e("tool_id", intent.getStringExtra("tool_id"));
                    /// delete from booking table
                    db.deleteBookingByPush(Integer.parseInt(intent.getStringExtra("booking_id"))
                            , intent.getStringExtra("tool_id"));
                } else if (intent.getStringExtra("push_type").equals("4")) {
                    /// lender handover tul push on user side
                    db.updateLentStatusByPush(Integer.parseInt(intent.getStringExtra("booking_id")), "1");
                } else if (intent.getStringExtra("push_type").equals("5")) {
                    /// user marked as received push on lender side
                    db.updateBorrowerStatusByPush(Integer.parseInt(intent.getStringExtra("booking_id")), "1");
                } else if (intent.getStringExtra("push_type").equals("7")) {
                    /// user returned tul push on lender side
                    db.updateBorrowerStatusByPush(Integer.parseInt(intent.getStringExtra("booking_id")), "2");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    @Override
    public void updateBookingData() {

        mBookingArray.clear();
        mBookingArray.addAll(db.getAllBookingsTuls());
        myBookingAdapter.notifyDataSetChanged();

        if (mSelectedTab == 0) {
            if (db.getAllBookingIds().size() == 0) {
                txtNoActiveTuls.setVisibility(View.VISIBLE);
            }
        }
        if (mTulLentAdapter != null)
            mTulLentAdapter.notifyDataSetChanged();

    }

    @Override
    public void updateTulLentData() {

        mLentArray.clear();
        mLentArray.addAll(db.getAllLentedTuls());
        mTulLentAdapter.notifyDataSetChanged();

        if (mSelectedTab == 1) {
            if (db.getAllLendedTulIds().size() == 0) {
                txtNoLentTuls.setVisibility(View.VISIBLE);
            }
        }
        if (myBookingAdapter != null)
            myBookingAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        Constants.PROFILE_ID = 0;
        super.onDestroy();
    }

}
