package adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.tul.AfterWalkthroughActivity;
import com.app.tul.R;
import com.app.tul.Sales.SalesTulDetailActivity;
import com.app.tul.Sales.SellerProfileActivity;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import customviews.CircleTransform;
import database.Db;
import model.SalesTulDetailModel;
import utils.Constants;
import utils.Utils;

/**
 * Created by dev on 22/8/18.
 */

public class SalesTulListingAdapter extends RecyclerView.Adapter<SalesTulListingAdapter.ViewHolder> {

    private static final int TYPE_ITEM = 1;
    private static final int DELETE = 2;
    private static final int VIEW_PROG = 3;
    ArrayList<SalesTulDetailModel.ResponseBean> mData;
    LinearLayout.LayoutParams viewParms, cvParms;
    Db db;
    private Context con;
    private Utils utils;
    private int mWidth, mHeight, mTulImageHeight, mProfileImageDimen, mTulImageWidth;

    public SalesTulListingAdapter(Context con, ArrayList<SalesTulDetailModel.ResponseBean> mData) {
        this.con = con;
        this.mData = mData;
        utils = new Utils(con);
        db = new Db(con);
        mWidth = utils.getInt("width", 0);
        mHeight = utils.getInt("height", 0);
        mTulImageHeight = mHeight / 3;
        mTulImageWidth = mWidth - (mWidth / 64);
        mProfileImageDimen = mWidth / 8;

        cvParms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mTulImageHeight);
        cvParms.setMargins(mWidth / 32, 0, mWidth / 32, mWidth / 64);

        viewParms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        viewParms.setMargins(mWidth / 18, 0, mWidth / 18, 0);
    }

    @Override
    public SalesTulListingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SalesTulListingAdapter.ViewHolder vhItem;
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sales_tul, parent, false);
            vhItem = new SalesTulListingAdapter.ViewHolder(v, viewType);
            return vhItem;
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progress_item, parent, false);
            vhItem = new SalesTulListingAdapter.ViewHolder(v, viewType);
            return vhItem;
        }
    }

    @Override
    public void onBindViewHolder(final SalesTulListingAdapter.ViewHolder holder, final int position) {
        if (holder.Holderid == 1) {

//            if (mData.get(position).getUser_id() == Integer.parseInt(utils.getString("user_id", "")))
//                holder.imgChat.setVisibility(View.INVISIBLE);
//            else
//                holder.imgChat.setVisibility(View.VISIBLE);

            holder.txtOwnerName.setText(mData.get(position).getOwner());

            holder.txtTittle.setText(mData.get(position).getTitle());

            holder.txtPrice.setText(mData.get(position).getCurrency() + " " + mData.get(position).getPrice());

            holder.srbTul.setRating(mData.get(position).getRating());
            holder.srbTul.setEnabled(false);

            holder.txtToolCondition.setText(mData.get(position).getCondition() + "");

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

            Picasso.with(con).load(mData.get(position).getOwner_pic())
                    .resize(mProfileImageDimen, mProfileImageDimen)
                    .centerCrop().placeholder(R.mipmap.ic_small_ph_tul)
                    .transform(new CircleTransform())
                    .into(holder.imgProfile);

            holder.cvTul.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Constants.PROFILE_ID = 0;
                    Intent intent = new Intent(con, SalesTulDetailActivity.class);
                    intent.putExtra("data", mData.get(position));
                    intent.putExtra("position", position);
                    intent.putExtra("id", mData.get(position).getId());
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

            holder.llNameInside.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (utils.getInt(Constants.ISGUEST, 0) == 0) {
                        Constants.PROFILE_ID = mData.get(position).getUser_id();
                        Intent intent = new Intent(con, SellerProfileActivity.class);
                        intent.putExtra("other_user_id", mData.get(position).getUser_id());
                        intent.putExtra("other_user_name", mData.get(position).getOwner());
                        intent.putExtra("other_user_pic", mData.get(position).getOwner_pic());
                        con.startActivity(intent);
                        ((Activity) con).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                    } else {
                        loginFirst();
                    }
                }
            });

            holder.imgProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (utils.getInt(Constants.ISGUEST, 0) == 0) {
                        Constants.PROFILE_ID = mData.get(position).getUser_id();
                        Intent intent = new Intent(con, SellerProfileActivity.class);
                        intent.putExtra("other_user_id", mData.get(position).getUser_id());
                        intent.putExtra("other_user_name", mData.get(position).getOwner());
                        intent.putExtra("other_user_pic", mData.get(position).getOwner_pic());
                        con.startActivity(intent);
                        ((Activity) con).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                    } else {
                        loginFirst();
                    }
                }
            });
        }
    }

    private void loginFirst() {
        AlertDialog.Builder builder = new AlertDialog.Builder(con);
        builder.setMessage(R.string.Login_First)
                .setCancelable(false)
                .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent start = new Intent(con, AfterWalkthroughActivity.class);
                        start.putExtra(Constants.REG_GUEST, 1);
                        ((Activity) con).startActivity(start);
                        ((Activity) con).overridePendingTransition(R.anim.slideup_in, R.anim.slideup_out);
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
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position) != null ? TYPE_ITEM : VIEW_PROG;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;
        int Holderid;
        LinearLayout llName, llRating, llNameInside;
        TextView txtTittle, txtPrice, txtOwner, txtOwnerName, txtToolCondition;
        SimpleRatingBar srbTul;
        CardView cvTul;
        ImageView imgTul, imgProfile, imgVideoPlay;
        View view1;


        public ViewHolder(View itemView, int viewType) {
            super(itemView);

            if (viewType == TYPE_ITEM) {
                cvTul = (CardView) itemView.findViewById(R.id.cv_tul);
                cvTul.setLayoutParams(cvParms);

                imgTul = (ImageView) itemView.findViewById(R.id.img_tul);

                view1 = (View) itemView.findViewById(R.id.view_1);
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

                llNameInside = (LinearLayout) itemView.findViewById(R.id.ll_name_inside);

                txtOwnerName = (TextView) itemView.findViewById(R.id.txt_owner_name);
                txtOwnerName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));

                LinearLayout.LayoutParams profileParms = new LinearLayout.LayoutParams(mProfileImageDimen, mProfileImageDimen);
                profileParms.setMargins(mWidth / 64, 0, mWidth / 32, 0);
                imgProfile = (ImageView) itemView.findViewById(R.id.img_profile);
                imgProfile.setLayoutParams(profileParms);

                imgVideoPlay = (ImageView) itemView.findViewById(R.id.img_video_play);

                srbTul = (SimpleRatingBar) itemView.findViewById(R.id.srb_tul);
                srbTul.setPadding(0, mWidth / 32, mWidth / 32, 0);

                txtToolCondition = (TextView) itemView.findViewById(R.id.txt_tool_condition);

                llRating = (LinearLayout) itemView.findViewById(R.id.ll_rating);
                Holderid = 1;
            } else {
                progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
                Holderid = 0;
            }
        }
    }

}
