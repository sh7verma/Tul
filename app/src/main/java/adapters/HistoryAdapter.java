package adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
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
import android.widget.TextView;

import com.app.tul.HistoryTulDetailActivity;
import com.app.tul.OtherUserProfileActivity;
import com.app.tul.R;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import customviews.CircleTransform;
import model.BookTulModel;
import model.LentTulModel;
import utils.Constants;
import utils.Utils;

/**
 * Created by dev on 20/11/17.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private static final int DELETE = 2;
    ArrayList<BookTulModel.ResponseBean> mData;
    LinearLayout.LayoutParams viewParms, cvParms;
    int mFragment = 0;
    private Context con;
    private Utils utils;
    private int mWidth, mHeight, mTulImageHeight, mProfileImageDimen, mTulImageWidth;

    private static final int TYPE_ITEM = 1;
    private static final int VIEW_PROG = 3;

    public HistoryAdapter(Context con, ArrayList<BookTulModel.ResponseBean> mData, int fragment) {
        this.con = con;
        this.mData = mData;
        utils = new Utils(con);
        mWidth = utils.getInt("width", 0);
        mHeight = utils.getInt("height", 0);
        mTulImageHeight = mHeight / 3;
        mTulImageWidth = mWidth - (mWidth / 64);
        mFragment = fragment;
        mProfileImageDimen = mWidth / 8;

        cvParms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mTulImageHeight);
        cvParms.setMargins(mWidth / 32, 0, mWidth / 32, mWidth / 64);

        viewParms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        viewParms.setMargins(mWidth / 18, 0, mWidth / 18, 0);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewHolder vhItem;
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
            vhItem = new ViewHolder(v, viewType);
            return vhItem;
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progress_item, parent, false);
            vhItem = new ViewHolder(v, viewType);
            return vhItem;
        }
    }


    @Override
    public int getItemViewType(int position) {
        return mData.get(position) != null ? TYPE_ITEM : VIEW_PROG;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (holder.Holderid == 1) {
            if (mFragment == 1) {
                holder.txtOwner.setText(R.string.owner_);
                holder.txtOwnerName.setText(mData.get(position).getOwner());
                Picasso.with(con).load(mData.get(position).getOwner_pic())
                        .resize(mProfileImageDimen, mProfileImageDimen).centerCrop()
                        .placeholder(R.mipmap.ic_small_ph_tul)
                        .transform(new CircleTransform())
                        .into(holder.imgProfile);
            } else {
                holder.txtOwner.setText(R.string.rented_to_);
                holder.txtOwnerName.setText(mData.get(position).getBorrower());
                Picasso.with(con).load(mData.get(position).getBorrower_pic())
                        .resize(mProfileImageDimen, mProfileImageDimen).centerCrop()
                        .placeholder(R.mipmap.ic_small_ph_tul)
                        .transform(new CircleTransform())
                        .into(holder.imgProfile);
            }

            holder.txtTittle.setText(mData.get(position).getTitle());
            holder.txtPrice.setText(mData.get(position).getCurrency()+ " " + String.format("%.2f", Double.parseDouble(mData.get(position).getPrice())));


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

            holder.cvTul.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(con, HistoryTulDetailActivity.class);
                    intent.putExtra("path", mFragment);
                    intent.putExtra("data", mData.get(position));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation(((Activity) con),
                                holder.imgTul, con.getString(R.string.transition));
                        ((Activity) con).startActivityForResult(intent, DELETE, option.toBundle());
                    } else {
                        ((Activity) con).startActivityForResult(intent, DELETE);
                        ((Activity) con).overridePendingTransition(R.anim.slideup_in, R.anim.slideup_out);
                    }
                }
            });


            holder.llName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mFragment == 1) {
                        Constants.PROFILE_ID = mData.get(position).getUser_id();
                        Intent intent = new Intent(con, OtherUserProfileActivity.class);
                        intent.putExtra("other_user_id", mData.get(position).getUser_id());
                        intent.putExtra("other_user_name", mData.get(position).getOwner());
                        intent.putExtra("other_user_pic", mData.get(position).getOwner_pic());
                        con.startActivity(intent);
                        ((Activity) con).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                    } else {
                        Constants.PROFILE_ID = mData.get(position).getUser_id();
                        Intent intent = new Intent(con, OtherUserProfileActivity.class);
                        intent.putExtra("other_user_id", mData.get(position).getBorrower_id());
                        intent.putExtra("other_user_name", mData.get(position).getBorrower());
                        intent.putExtra("other_user_pic", mData.get(position).getBorrower_pic());
                        con.startActivity(intent);
                        ((Activity) con).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        int Holderid;
        LinearLayout llName, llRating;
        TextView txtTittle, txtPrice, txtOwner, txtOwnerName;
        SimpleRatingBar srbTul;
        CardView cvTul;
        ImageView imgTul, imgProfile, imgVideoPlay;
        View view1;
        ProgressBar progressBar;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            if (viewType == TYPE_ITEM) {
                cvTul = (CardView) itemView.findViewById(R.id.cv_tul);
                cvTul.setLayoutParams(cvParms);

                imgTul = (ImageView) itemView.findViewById(R.id.img_tul);

                view1 = itemView.findViewById(R.id.view_1);
                view1.setLayoutParams(viewParms);

                txtTittle = (TextView) itemView.findViewById(R.id.txt_tittle);
                txtTittle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));
                txtTittle.setPadding(mWidth / 32, 0, mWidth / 5, mWidth / 28);

                txtPrice = (TextView) itemView.findViewById(R.id.txt_price);
                txtPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.043));
                txtPrice.setPadding(0, 0, mWidth / 32, mWidth / 32);

                txtOwner = (TextView) itemView.findViewById(R.id.txt_owner);
                txtOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));

                LinearLayout.LayoutParams nameParms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                nameParms.setMargins(mWidth / 32, 0, mWidth / 32, mWidth / 32);
                llName = (LinearLayout) itemView.findViewById(R.id.ll_name);
                llName.setLayoutParams(nameParms);

                txtOwnerName = (TextView) itemView.findViewById(R.id.txt_owner_name);
                txtOwnerName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));

                LinearLayout.LayoutParams profileParms = new LinearLayout.LayoutParams(mProfileImageDimen, mProfileImageDimen);
                profileParms.setMargins(mWidth / 64, 0, mWidth / 32, 0);
                imgProfile = (ImageView) itemView.findViewById(R.id.img_profile);
                imgProfile.setLayoutParams(profileParms);

                imgVideoPlay = (ImageView) itemView.findViewById(R.id.img_video_play);

                srbTul = (SimpleRatingBar) itemView.findViewById(R.id.srb_tul);
                srbTul.setVisibility(View.GONE);

                llRating = (LinearLayout) itemView.findViewById(R.id.ll_rating);
                Holderid = 1;
            } else {
                progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
                Holderid = 0;
            }

        }
    }
}