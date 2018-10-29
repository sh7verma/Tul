package adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.Space;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.tul.ActiveBookingActivity;
import com.app.tul.OtherUserProfileActivity;
import com.app.tul.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import customviews.CircleTransform;
import model.BookTulModel;
import utils.Constants;
import utils.Utils;


/**
 * Created by dev on 14/11/17.
 */

public class TulLentAdapter extends RecyclerView.Adapter<TulLentAdapter.ViewHolder> {

    private static final int TYPE_ITEM = 1;
    private static final int VIEW_PROG = 2;
    private static final int TYPE_HEADER = 0;

    ArrayList<BookTulModel.ResponseBean> mData;
    Typeface typeface, typefaceRegular;
    Calendar calendar;
    Date currentDate;
    int mBookingCount, mLentCount;
    private Context mContext;
    private Utils utils;
    private int mWidth, mHeight;

    public TulLentAdapter(Context con, ArrayList<BookTulModel.ResponseBean> mLentArray) {
        this.mContext = con;
        utils = new Utils(con);
        mWidth = utils.getInt("width", 0);
        mHeight = utils.getInt("height", 0);
        this.mData = mLentArray;
        typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/semibold.ttf");
        typefaceRegular = Typeface.createFromAsset(mContext.getAssets(), "fonts/regular.ttf");


        calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        currentDate = new Date();
        currentDate = calendar.getTime();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder vhItem;
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_booking, parent, false);
            vhItem = new ViewHolder(v, viewType);
            return vhItem;
        } else if (viewType == VIEW_PROG) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progress_item, parent, false);
            vhItem = new ViewHolder(v, viewType);
            return vhItem;
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_active_header, parent, false);
            vhItem = new ViewHolder(v, viewType);
            return vhItem;
        }

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        if (holder.Holderid == 0) {
            holder.tvMyBookingCount.setText(mBookingCount + "");
            holder.tvLentCount.setText(mLentCount + "");
            holder.llMyBooking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ActiveBookingActivity) mContext).booking();
                }
            });
            holder.tvMyBookingCount.setText(String.valueOf(utils.getInt("booking_count", 0)));
            holder.tvLentCount.setText(String.valueOf(utils.getInt("lent_count", 0)));
        } else if (holder.Holderid == 1) {

            position = position - 1;

            holder.tvTulHanded.setText("TUL Handed");
            holder.tvTulReceived.setText("TUL Received");
            holder.tvOwner.setText("RENTED");

            holder.tvName.setText(mData.get(position).getBorrower());

            if (mData.get(position).getQuantity() == 1)
                holder.txtBookings.setText("(" + mData.get(position).getQuantity() + " Booking)");
            else
                holder.txtBookings.setText("(" + mData.get(position).getQuantity() + " Bookings)");

            Picasso.with(mContext).load(mData.get(position).getBorrower_pic())
                    .resize(mWidth / 7, mWidth / 7).transform(new CircleTransform())
                    .placeholder(R.mipmap.ic_small_ph_tul)
                    .centerCrop().into(holder.imgUser);

            holder.tvTulname.setText(mData.get(position).getTitle());

            double earnedAmount = Double.parseDouble(mData.get(position).getTotal_amount()) -
                    (Double.parseDouble(mData.get(position).getTransaction_fee()) + Double.parseDouble(mData.get(position).getAdditional_charges().getSecurity_charges()));

            holder.tvTulPrice.setText(mData.get(position).getCurrency() + " "
                    + String.format("%.2f", earnedAmount));

            try {
                holder.tvDate.setText(Constants.convertDate(mData.get(position).getBooked_at()));
                holder.tvBookingDate.setText(Constants.convertDate(mData.get(position).getDelivery_date()));
                holder.tvReturnDate.setText(Constants.convertDate(mData.get(position).getReturn_date()));
            } catch (ParseException e) {
                Log.e("Date Exce = ", e.getLocalizedMessage());
            }

            if (mData.get(position).getLender_status() == 0 && mData.get(position).getBorrower_status() == 0)
                holder.tvCancel.setVisibility(View.VISIBLE);
            else
                holder.tvCancel.setVisibility(View.GONE);

            try {
                if (mData.get(position).getLender_status() == 0) {
                    /// both show black box

                    /// recieve checks
                    holder.imgTulRecieved.setImageResource(R.mipmap.ic_mark_rec);
                    holder.tvTulHanded.setTypeface(typefaceRegular);
                    holder.tvTulHandedDate.setVisibility(View.INVISIBLE);

                    /// returned checks
                    holder.imgTulReturned.setImageResource(R.mipmap.ic_mark_rec);
                    holder.tvTulReceived.setTypeface(typefaceRegular);
                    holder.tvtulRecievedDate.setVisibility(View.INVISIBLE);
                } else if (mData.get(position).getLender_status() == 1) {
                    /// recived but not returned

                    /// recieve checks
                    holder.imgTulRecieved.setImageResource(R.mipmap.ic_tick_c);
                    holder.tvTulHanded.setTypeface(typeface);
                    holder.tvTulHandedDate.setVisibility(View.VISIBLE);
                    holder.tvTulHandedDate.setText(Constants.convertDateTime(mData.get(position).getHandover_at()));

                    /// returned checks
                    holder.imgTulReturned.setImageResource(R.mipmap.ic_mark_rec);
                    holder.tvTulReceived.setTypeface(typefaceRegular);
                    holder.tvtulRecievedDate.setVisibility(View.INVISIBLE);
                } else if (mData.get(position).getLender_status() == 2) {
                    /// recived and returned

                    /// recieve checks
                    holder.imgTulRecieved.setImageResource(R.mipmap.ic_tick_c);
                    holder.tvTulHanded.setTypeface(typeface);
                    holder.tvTulHandedDate.setVisibility(View.VISIBLE);
                    holder.tvTulHandedDate.setText(Constants.convertDateTime(mData.get(position).getHandover_at()));

                    /// returned checks
                    holder.imgTulReturned.setImageResource(R.mipmap.ic_tick_c);
                    holder.tvTulReceived.setTypeface(typeface);
                    holder.tvtulRecievedDate.setVisibility(View.VISIBLE);
                    holder.tvtulRecievedDate.setText(Constants.convertDateTime(mData.get(position).getLander_received_at()));
                }
            } catch (Exception e) {
                Log.e("Exce  = ", e + "");
            }

            final int finalPosition = position;
            holder.tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ActiveBookingActivity) mContext).cancelTulLent(String.valueOf(mData.get(finalPosition).getTool_id()),
                            String.valueOf(mData.get(finalPosition).getBooking_id()), finalPosition);
                }
            });

            holder.rlTulHanded.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mData.get(finalPosition).getLender_status() == 0) {
                        /// change lender status to 1
                        Date bookingDate = convertStringToDate(mData.get(finalPosition).getDelivery_date());
                        if (bookingDate.equals(currentDate))
                            ((ActiveBookingActivity) mContext).handOverTul(String.valueOf(mData.get(finalPosition).getTool_id()),
                                    String.valueOf(mData.get(finalPosition).getBooking_id()), finalPosition);
                        else
                            Toast.makeText(mContext, "You can't handover TUL before delivery date.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            holder.llTulReceived.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mData.get(finalPosition).getBorrower_status() == 2) {
                        /// change lender status to 2
                        ((ActiveBookingActivity) mContext).lenderReceivedTul(String.valueOf(mData.get(finalPosition).getTool_id()),
                                String.valueOf(mData.get(finalPosition).getBooking_id()), finalPosition, mData.get(finalPosition).getOwner(),
                                mData.get(finalPosition).getOwner_pic(), mData.get(finalPosition).getTotal_amount(), mData.get(finalPosition).getAddress(),
                                mData.get(finalPosition), mData.get(finalPosition).getUser_id(), mData.get(finalPosition).getBorrower_id(),
                                mData.get(finalPosition).getBorrower(), mData.get(finalPosition).getBorrower_pic());
                    } else {
                        /// show toast message.
                        if (mData.get(finalPosition).getLender_status() != 0)
                            Toast.makeText(mContext, R.string.borrower_not_returned, Toast.LENGTH_SHORT).show();
                    }
                }
            });
            holder.llClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ActiveBookingActivity) mContext).moveToTulDetailLent(finalPosition);
                }
            });
            holder.rlProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Constants.PROFILE_ID = mData.get(finalPosition).getUser_id();
                    Intent intent = new Intent(mContext, OtherUserProfileActivity.class);
                    intent.putExtra("other_user_id", mData.get(finalPosition).getBorrower_id());
                    intent.putExtra("other_user_name", mData.get(finalPosition).getBorrower());
                    intent.putExtra("other_user_pic", mData.get(finalPosition).getBorrower_pic());
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return mData.get(position - 1) != null ? TYPE_ITEM : VIEW_PROG;
        }
    }

    Date convertStringToDate(String input) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = format.parse(input);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout llMyBooking, llTulLent;
        public TextView tvMyBookingCount, tvMy, tvBooking, tvLentCount, tvLent, tvtul;
        int Holderid;
        CardView cvMain;
        RelativeLayout rlTulHanded, rlProfile;
        LinearLayout llMain, llDelivery, llDots, llDeliveryData, llInner, llClick, llTulReceived;
        ImageView imgUser, imgDot1, imgDot2, imgTulRecieved, imgTulReturned;
        TextView tvOwner, tvName, tvDate, tvTulname, tvTulPrice, tvReturnDate, tvReturnTitle, tvCancel, txtBookings,
                tvBookingTitle, tvBookingDate, tvTulHanded, tvTulReceived, tvtulRecievedDate, tvTulHandedDate;
        View line, view1;
        ProgressBar progressBar;
        Space space;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            if (viewType == TYPE_HEADER) {

                llMyBooking = (LinearLayout) itemView.findViewById(R.id.ll_my_booking);
                llMyBooking.setPadding(mWidth / 24, mHeight / 42, 0, mHeight / 42);
                llMyBooking.setBackground(ContextCompat.getDrawable(mContext, R.drawable.black_color_white_stroke));


                tvMyBookingCount = (TextView) itemView.findViewById(R.id.tv_my_booking_count);
                tvMyBookingCount.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.08));
                tvMy = (TextView) itemView.findViewById(R.id.tv_my);
                tvMy.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

                tvBooking = (TextView) itemView.findViewById(R.id.tv_booking);
                tvBooking.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

                llTulLent = (LinearLayout) itemView.findViewById(R.id.ll_tul_lent);
                llTulLent.setPadding(mWidth / 24, mHeight / 42, 0, mHeight / 42);
                llTulLent.setBackground(ContextCompat.getDrawable(mContext, R.drawable.black_stroke_default));

                tvLentCount = (TextView) itemView.findViewById(R.id.tv_my_lent_count);
                tvLentCount.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.08));

                tvLent = (TextView) itemView.findViewById(R.id.tv_lent);
                tvLent.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

                tvtul = (TextView) itemView.findViewById(R.id.tv_tul);
                tvtul.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

                tvtul.setTextColor(ContextCompat.getColor(mContext, R.color.black_color));
                tvLentCount.setTextColor(ContextCompat.getColor(mContext, R.color.black_color));
                tvLent.setTextColor(ContextCompat.getColor(mContext, R.color.black_color));

                tvBooking.setTextColor(ContextCompat.getColor(mContext, R.color.white_color));
                tvMyBookingCount.setTextColor(ContextCompat.getColor(mContext, R.color.white_color));
                tvMy.setTextColor(ContextCompat.getColor(mContext, R.color.white_color));

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mWidth / 32, ViewGroup.LayoutParams.MATCH_PARENT);
                space = (Space) itemView.findViewById(R.id.space);
                space.setLayoutParams(layoutParams);

                Holderid = 0;

            } else if (viewType == TYPE_ITEM) {
                llInner = (LinearLayout) itemView.findViewById(R.id.ll_inner);
                llInner.setPadding(0, mWidth / 32, 0, mWidth / 32);

                rlProfile = (RelativeLayout) itemView.findViewById(R.id.rl_profile);

                llClick = (LinearLayout) itemView.findViewById(R.id.ll_click);

                cvMain = (CardView) itemView.findViewById(R.id.cv_main);
                cvMain.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);

                llMain = (LinearLayout) itemView.findViewById(R.id.ll_main);
                llMain.setPadding(0, mWidth / 32, 0, mWidth / 32);

                RelativeLayout.LayoutParams profileParms = new RelativeLayout.LayoutParams(mWidth / 7, mWidth / 7);
                profileParms.addRule(RelativeLayout.CENTER_VERTICAL);
                imgUser = (ImageView) itemView.findViewById(R.id.img_user);
                imgUser.setPadding(mWidth / 32, 0, 0, 0);
                imgUser.setLayoutParams(profileParms);

                tvOwner = (TextView) itemView.findViewById(R.id.tv_owner);
                tvOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.03));
                tvOwner.setPadding(mWidth / 32, mWidth / 64, mWidth / 32, 0);

                tvName = (TextView) itemView.findViewById(R.id.tv_name);
                tvName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
                tvName.setPadding(mWidth / 32, 0, mWidth / 32, 0);

                tvDate = (TextView) itemView.findViewById(R.id.tv_date);
                tvDate.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.03));
                tvDate.setPadding(mWidth / 32, 0, mWidth / 32, 0);

                tvTulname = (TextView) itemView.findViewById(R.id.tv_tul_name);
                tvTulname.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
                tvTulname.setPadding(mWidth / 32, mWidth / 32, 0, mWidth / 64);

                tvTulPrice = (TextView) itemView.findViewById(R.id.tv_tul_price);
                tvTulPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
                tvTulPrice.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 64);

                txtBookings = (TextView) itemView.findViewById(R.id.txt_bookings);
                txtBookings.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
                txtBookings.setPadding(mWidth / 32, 0, 0, mWidth / 32);

                llDots = (LinearLayout) itemView.findViewById(R.id.ll_dots);
                llDots.setPadding(mWidth / 32, mWidth / 76, 0, 0);

                imgDot1 = (ImageView) itemView.findViewById(R.id.img_dot1);
                imgDot2 = (ImageView) itemView.findViewById(R.id.img_dot2);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mWidth / 46, mWidth / 46);
                imgDot1.setLayoutParams(layoutParams);
                imgDot2.setLayoutParams(layoutParams);

                view1 = itemView.findViewById(R.id.view1);
                view1.setPadding(0, mWidth / 42, 0, mWidth / 42);

                line = itemView.findViewById(R.id.view_line1);

                LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(2, mHeight / 20);
                layoutParams1.gravity = Gravity.CENTER;
                line.setLayoutParams(layoutParams1);

                llDeliveryData = (LinearLayout) itemView.findViewById(R.id.ll_delivery_data);
                llDeliveryData.setPadding(mWidth / 24, 0, mWidth / 24, 0);

                tvBookingTitle = (TextView) itemView.findViewById(R.id.tv_delivery_title);
                tvBookingTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

                tvBookingDate = (TextView) itemView.findViewById(R.id.tv_delivery_date);
                tvBookingDate.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.03));
                tvBookingDate.setPadding(0, 0, 0, mHeight / 64);

                tvReturnTitle = (TextView) itemView.findViewById(R.id.tv_return_title);
                tvReturnTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

                tvReturnDate = (TextView) itemView.findViewById(R.id.tv_return_date);
                tvReturnDate.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.03));

                tvTulHanded = (TextView) itemView.findViewById(R.id.tv_tul_recieved);
                tvTulHanded.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

                rlTulHanded = (RelativeLayout) itemView.findViewById(R.id.rl_tul_received);
                rlTulHanded.setPadding(0, mHeight / 32, 0, 0);

                tvTulHandedDate = (TextView) itemView.findViewById(R.id.tv_tul_recieved_date);
                tvTulHandedDate.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.03));
                tvTulHandedDate.setPadding(0, 0, 0, mWidth / 64);

                llTulReceived = (LinearLayout) itemView.findViewById(R.id.ll_tul_returned);

                tvTulReceived = (TextView) itemView.findViewById(R.id.tv_tul_returned);
                tvTulReceived.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

                tvtulRecievedDate = (TextView) itemView.findViewById(R.id.tv_tul_returned_date);
                tvtulRecievedDate.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.03));
                tvtulRecievedDate.setPadding(0, 0, 0, mWidth / 64);

                imgTulRecieved = (ImageView) itemView.findViewById(R.id.img_tul_received_check);
                imgTulRecieved.setPadding(mWidth / 32, 0, mWidth / 76, 0);

                imgTulReturned = (ImageView) itemView.findViewById(R.id.img_tul_rreturned_check);
                imgTulReturned.setPadding(mWidth / 32, 0, mWidth / 76, 0);

                tvCancel = (TextView) itemView.findViewById(R.id.tv_cancel);

                LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams3.setMargins(0, 0, mWidth / 32, 0);
                tvCancel.setLayoutParams(layoutParams3);

                tvCancel.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.03));
                tvCancel.setPadding(mWidth / 32, mHeight / 64, mWidth / 32, mHeight / 64);
                Holderid = 1;
            } else {
                progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
                Holderid = 2;
            }
        }
    }

}
