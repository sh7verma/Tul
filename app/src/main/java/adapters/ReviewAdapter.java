package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tul.R;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.ArrayList;

import customviews.CircleTransform;
import customviews.MediumTextView;
import customviews.SemiboldTextView;
import model.ReviewRatingModel;
import utils.Constants;
import utils.Utils;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private static final int TYPE_ITEM = 1;
    private static final int HEADER = 0;
    Context mContext;
    ArrayList<ReviewRatingModel.ResponseBean> mReviewArrayList;
    int avgRating, count;
    private Utils utils;
    private int mWidth, mHeight;

    public ReviewAdapter(Context context, ArrayList<ReviewRatingModel.ResponseBean> reviewArrayList, int avgRating, int count) {
        mContext = context;
        mReviewArrayList = reviewArrayList;
        utils = new Utils(mContext);
        this.avgRating = avgRating;
        this.count = count;

        mWidth = utils.getInt("width", 0);
        mHeight = utils.getInt("height", 0);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        ViewHolder vhItem;
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_review_rating, viewGroup, false);
            vhItem = new ViewHolder(v, viewType);
            return vhItem;
        } else {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_header_review_ratings, viewGroup, false);
            vhItem = new ViewHolder(v, viewType);
            return vhItem;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        if (viewHolder.Holderid == 1) {
            position = position - 1;
            Picasso.with(mContext).load(mReviewArrayList.get(position).getUser_pic())
                    .resize(mWidth / 8, mWidth / 8)
                    .centerCrop().placeholder(R.mipmap.ic_small_ph_tul)
                    .transform(new CircleTransform())
                    .into(viewHolder.imgPerson);

            viewHolder.tvPersonName.setText(mReviewArrayList.get(position).getUsername());
            viewHolder.tvReviewDetail.setText(mReviewArrayList.get(position).getReview());
            try {
                viewHolder.tvDate.setText(Constants.convertReviewsDate(mReviewArrayList.get(position).getDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            viewHolder.simpleRatingItem.setRating(mReviewArrayList.get(position).getRating());
            viewHolder.llStars.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        } else {


            viewHolder.simpleRatingBar.setRating(avgRating);
            viewHolder.tvAvgRatings.setText(String.valueOf("(" + avgRating + ")"));
            if (count > 0) {
                viewHolder.tvTulReviewNo.setText(count + " REVIEWS");
                viewHolder.tvTulReviewNo.setVisibility(View.VISIBLE);
            } else {
                viewHolder.tvTulReviewNo.setVisibility(View.INVISIBLE);
            }
            viewHolder.llStarsHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mReviewArrayList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER;
        }
        return mReviewArrayList.get(position - 1) != null ? TYPE_ITEM : HEADER;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        //item
        ImageView imgPerson;
        TextView tvPersonName;
        TextView tvDate, tvReviewDetail;
        TextView btReport;
        View view;
        LinearLayout llStarsHeader;
        SimpleRatingBar simpleRatingItem;

        //header
        TextView tvTulOverallRating;
        SimpleRatingBar simpleRatingBar;
        TextView tvTulReviewNo, tvAvgRatings;
        LinearLayout llStars;

        int Holderid;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);

            if (viewType == TYPE_ITEM) {
//
                imgPerson = (ImageView) itemView.findViewById(R.id.cv_review_person_image);
                tvPersonName = (SemiboldTextView) itemView.findViewById(R.id.tv_review_person_name);
                tvDate = (MediumTextView) itemView.findViewById(R.id.tv_review_date);
                btReport = (TextView) itemView.findViewById(R.id.bt_review_report);
                tvReviewDetail = (MediumTextView) itemView.findViewById(R.id.tv_review_detail);
                view = itemView.findViewById(R.id.view1);
                llStars = (LinearLayout) itemView.findViewById(R.id.ll_stars);
                simpleRatingItem = (SimpleRatingBar) itemView.findViewById(R.id.srb_tul_rating);

                simpleRatingItem.setStarSize((float) (mWidth * 0.045));

                btReport.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.032));
                tvPersonName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
                tvDate.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));
                tvReviewDetail.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));


                Holderid = 1;
            } else if (viewType == HEADER) {
                llStarsHeader = (LinearLayout) itemView.findViewById(R.id.ll_stars);

                tvTulOverallRating = (TextView) itemView.findViewById(R.id.tv_tul_overall_rating);
                simpleRatingBar = (SimpleRatingBar) itemView.findViewById(R.id.srb_tul_rating);
                tvTulReviewNo = (TextView) itemView.findViewById(R.id.tv_tul_review_no);
                tvAvgRatings = (TextView) itemView.findViewById(R.id.tv_avg_ratings);

                tvAvgRatings.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));
                tvTulOverallRating.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
                simpleRatingBar.setStarSize((float) (mWidth * 0.045));

                tvTulReviewNo.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.055));
//                tvTulReviewNo.setText("10 REVIEWS");

                Holderid = 0;

            }
        }
    }
}
