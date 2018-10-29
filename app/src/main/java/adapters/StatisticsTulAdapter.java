package adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.tul.R;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import fragments.StatisticsFragment;
import model.DashboardMyTulModel;
import model.StatisticsModel;
import utils.Constants;
import utils.Utils;

public class StatisticsTulAdapter extends RecyclerView.Adapter<StatisticsTulAdapter.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int VIEW_PROG = 2;

    ArrayList<DashboardMyTulModel.ResponseBean> mData;
    LinearLayout.LayoutParams cvParms, lineParams;
    StatisticsModel mStatModel;
    private Context con;
    private Utils utils;
    private int mWidth, mHeight, mTulImageHeight, mTulImageWidth;
    private StatisticsFragment mFragment;

    public StatisticsTulAdapter(Context con, ArrayList<DashboardMyTulModel.ResponseBean> mData, StatisticsFragment mFragment, StatisticsModel mStatModel) {
        this.con = con;
        this.mData = mData;
        utils = new Utils(con);
        mWidth = utils.getInt("width", 0);
        mHeight = utils.getInt("height", 0);
        mTulImageHeight = mHeight / 3;
        mTulImageWidth = mWidth - (mWidth / 64);
        this.mFragment = mFragment;
        this.mStatModel = mStatModel;

        cvParms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mTulImageHeight);
        cvParms.setMargins(mWidth / 32, 0, mWidth / 32, 0);
        lineParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
        lineParams.setMargins(0, 0, 0, mHeight / 32);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder vhItem;
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dashboard_tullist, parent, false);
            vhItem = new ViewHolder(v, viewType);
            return vhItem;
        } else if (viewType == VIEW_PROG) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progress_item, parent, false);
            vhItem = new ViewHolder(v, viewType);
            return vhItem;
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_statistics, parent, false);
            vhItem = new ViewHolder(v, viewType);
            return vhItem;
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (holder.Holderid == 1) {
            position = position - 1;
            Log.d("size", String.valueOf(mData.size()));
            if (mData.get(position).getAttachment().get(0).getType().equals(Constants.IMAGE_TEXT)) {
                holder.imgVideoPlay.setVisibility(View.GONE);
                Picasso.with(con).load(mData.get(position).getAttachment().get(0).getAttachment())
                        .resize(mTulImageWidth, mTulImageHeight).centerCrop().placeholder(R.drawable.primary_ripple)
                        .into(holder.imgTul);
            } else if (mData.get(position).getAttachment().get(0).getType().equals(Constants.VIDEO_TEXT)) {
                holder.imgVideoPlay.setVisibility(View.VISIBLE);
                Picasso.with(con).load(mData.get(position).getAttachment().get(0).getThumbnail())
                        .resize(mTulImageWidth, mTulImageHeight)
                        .centerCrop().placeholder(R.drawable.primary_ripple)
                        .into(holder.imgTul);
            }

            holder.tvTittle.setText(mData.get(position).getTitle());
            holder.tvPrice.setText(mData.get(position).getCurrency() + " " + mData.get(position).getPrice());

            if (mData.get(position).getTotal_bookings() == 0)
                holder.tvBookedTul.setText("Not booked yet!");
            else
                holder.tvBookedTul.setText(String.valueOf(mData.get(position).getTotal_bookings()));

            holder.tvTotalEarnings.setText(mData.get(position).getCurrency() + " " + mData.get(position).getTotal_earning());
            holder.tvShortlisted.setText(" " + mData.get(position).getWishlist_count() + " Shortlisted");
            holder.tvViews.setText(" " + mData.get(position).getViews() + " Views");

            final int finalPosition = position;
            holder.llClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFragment.moveToDetail(finalPosition);
                }
            });
        } else if (holder.Holderid == 0) {
            if (mData.size() > 0) {
                holder.tvListingTilte.setVisibility(View.VISIBLE);
            } else {
                holder.tvListingTilte.setVisibility(View.GONE);
            }
            holder.tvEarning.setText(utils.getCurrency(utils.getString(Constants.PRIMARY_CURRENCY, "")) + " " + mStatModel.getResponse().get(0).getTotal_earnings());
            holder.tvRating.setText("(" + mStatModel.getResponse().get(0).getRating() + ")");
            holder.srbTul.setRating(mStatModel.getResponse().get(0).getRating());
            holder.llStars.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /// no op
                }
            });
            holder.imgFilter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFragment.imageOptions();
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        int Holderid;
        TextView tvTittle, tvPrice, tvBookedTitle, tvTotalEarningTitle,
                tvBookedTul, tvTotalEarnings,
                tvShortlisted, tvViews;
        LinearLayout llData, llClick;
        CardView cvTul;
        ImageView imgTul, imgVideoPlay;
        View viewLine;

        ProgressBar progressBar;

        TextView tvListingTilte;
        LinearLayout llUpper;
        TextView tvEarningTitle;
        TextView tvLifetime;
        TextView tvEarning;
        TextView tvRatingTitle;
        ImageView imgFilter;
        SimpleRatingBar srbTulhidden;
        SimpleRatingBar srbTul;
        RelativeLayout rlStars;
        LinearLayout llStars;
        TextView tvRating;


        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            if (viewType == TYPE_ITEM) {

                cvTul = (CardView) itemView.findViewById(R.id.cv_tul);
                cvTul.setLayoutParams(cvParms);

                llClick = (LinearLayout) itemView.findViewById(R.id.ll_click);

                imgTul = (ImageView) itemView.findViewById(R.id.img_tul);

                tvTittle = (TextView) itemView.findViewById(R.id.txt_tittle);
                tvTittle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));
                tvTittle.setPadding(mWidth / 32, 0, mWidth / 5, mWidth / 28);

                tvPrice = (TextView) itemView.findViewById(R.id.txt_price);
                tvPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));
                tvPrice.setPadding(0, 0, mWidth / 32, mWidth / 32);

                llData = (LinearLayout) itemView.findViewById(R.id.ll_data);
                llData.setPadding(mWidth / 28, 0, mWidth / 28, 0);

                imgVideoPlay = (ImageView) itemView.findViewById(R.id.img_video_play);

                tvBookedTitle = (TextView) itemView.findViewById(R.id.tv_booked_till_title);
                tvBookedTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
                tvBookedTitle.setPadding(0, mWidth / 42, 0, mWidth / 84);

                tvTotalEarningTitle = (TextView) itemView.findViewById(R.id.tv_total_earnings_title);
                tvTotalEarningTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
                tvTotalEarningTitle.setPadding(0, mWidth / 42, 0, mWidth / 84);

                tvBookedTul = (TextView) itemView.findViewById(R.id.tv_tul_booked);
                tvBookedTul.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));

                tvTotalEarnings = (TextView) itemView.findViewById(R.id.tv_total_earnings);
                tvTotalEarnings.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));

                tvShortlisted = (TextView) itemView.findViewById(R.id.tv_shortlisted);
                tvShortlisted.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));
                tvShortlisted.setPadding(0, mHeight / 64, 0, mHeight / 64);

                tvViews = (TextView) itemView.findViewById(R.id.tv_views);
                tvViews.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));
                tvViews.setPadding(0, mHeight / 64, 0, mHeight / 64);

                viewLine = itemView.findViewById(R.id.view);
                viewLine.setLayoutParams(lineParams);
                Holderid = 1;

            } else if (viewType == VIEW_PROG) {
                progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
                Holderid = 2;
            } else {
                tvListingTilte = (TextView) itemView.findViewById(R.id.tv_listing_title);
                llUpper = (LinearLayout) itemView.findViewById(R.id.ll_upper);
                tvEarningTitle = (TextView) itemView.findViewById(R.id.tv_earning_title);
                tvLifetime = (TextView) itemView.findViewById(R.id.tv_lifetime);
                tvEarning = (TextView) itemView.findViewById(R.id.tv_earning);
                tvRatingTitle = (TextView) itemView.findViewById(R.id.tv_rating_title);
                imgFilter = (ImageView) itemView.findViewById(R.id.img_filter);
                srbTulhidden = (SimpleRatingBar) itemView.findViewById(R.id.srb_tul_hidden);
                srbTul = (SimpleRatingBar) itemView.findViewById(R.id.srb_tul);
                rlStars = (RelativeLayout) itemView.findViewById(R.id.rl_stars);
                llStars = (LinearLayout) itemView.findViewById(R.id.ll_stars);
                tvRating = (TextView) itemView.findViewById(R.id.tv_rating);

                llUpper.setPadding(mWidth / 32, mHeight / 32, mWidth / 32, 0);

                tvEarningTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));

                imgFilter.setPadding(mWidth / 64, mWidth / 64, mWidth / 64, mWidth / 64);

                tvLifetime.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.03));

                tvEarning.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
                tvEarning.setPadding(0, mWidth / 42, 0, mHeight / 26);

                tvRatingTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
                tvRatingTitle.setPadding(0, mHeight / 26, 0, mHeight / 42);

                tvRating.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
                tvRating.setPadding(0, 0, 0, mHeight / 18);

                srbTulhidden.setStarSize((float) (mWidth * 0.05));
                srbTulhidden.setPadding(0, 0, mWidth / 32, mHeight / 18);

                srbTul.setStarSize((float) (mWidth * 0.05));
                srbTul.setPadding(0, 0, mWidth / 32, mHeight / 18);

                tvListingTilte.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
                tvListingTilte.setPadding(mWidth / 28, mHeight / 64, mWidth / 32, mHeight / 64);

                Holderid = 0;
            }
        }
    }
}