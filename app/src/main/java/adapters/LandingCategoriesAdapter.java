package adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.app.tul.CategoryTulListAcitivty;
import com.app.tul.LandingActivity;
import com.app.tul.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import customviews.RippleBackground;
import database.Db;
import model.CategoriesModel;
import utils.Constants;
import utils.Utils;

/**
 * Created by applify on 9/29/2017.
 */

public class LandingCategoriesAdapter extends RecyclerView.Adapter<LandingCategoriesAdapter.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int AGAIN_CLUSTER_LIST = 3;
    private Context con;
    private Utils utils;
    private FrameLayout.LayoutParams mainParms;
    ArrayList<CategoriesModel.ResponseBean> mData;
    private int mWidth, mHeight;
    Db db;

    public LandingCategoriesAdapter(Context con, ArrayList<CategoriesModel.ResponseBean> mData) {
        this.con = con;
        this.mData = mData;
        utils = new Utils(con);
        mWidth = utils.getInt("width", 0);
        mHeight = utils.getInt("height", 0);
        db = new Db(con);
        mainParms = new FrameLayout.LayoutParams(0, 0);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewHolder vhItem;
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_landing_categories, parent, false);
            vhItem = new ViewHolder(v, viewType);
            return vhItem;
        } else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ripple, parent, false);
            vhItem = new ViewHolder(v, viewType);
            return vhItem;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (holder.Holderid == 1) {

            holder.txtTittle.setText(mData.get(position - 1).getCategory_name());

            holder.rlContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("Position = ", position + "");
                    Intent intent = new Intent(con, CategoryTulListAcitivty.class);
                    intent.putExtra("id", String.valueOf(mData.get(position - 1).getId()));
                    intent.putExtra("category", mData.get(position - 1).getCategory_name());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation(((Activity) con),
                                holder.txtTittle, con.getString(R.string.transition));
                        ((Activity) con).startActivityForResult(intent, AGAIN_CLUSTER_LIST, option.toBundle());
                    } else {
                        ((Activity) con).startActivityForResult(intent, AGAIN_CLUSTER_LIST);
                        ((Activity) con).overridePendingTransition(R.anim.slideup_in, R.anim.slideup_out);
                    }
                }
            });

            Picasso.with(con).load(mData.get(position - 1).getImage())
                    .resize(Constants.dpToPx(160), Constants.dpToPx(160)).centerCrop()
                    .placeholder(R.drawable.primary_ripple).into(holder.imgCat);
        } else {
            holder.rippleBackground.startRippleAnimation();
            if (utils.getInt("booking_count", 0) == 0 && utils.getInt("lent_count", 0) == 0) {
                holder.rlRipple.setLayoutParams(mainParms);
            }
            holder.rlRipple.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((LandingActivity) con).moveToActiveBookings();
                }
            });
            holder.txtNo.setText(String.valueOf(utils.getInt("booking_count", 0)));
        }
    }

    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;
        else
            return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        int Holderid;
        TextView txtTittle, txtNo, txtActive;
        RelativeLayout rlContent, rlRipple;
        ImageView imgCat;
        RippleBackground rippleBackground;

        public ViewHolder(View itemView, int ViewType) {
            super(itemView);
            if (ViewType == TYPE_HEADER) {

                rlRipple = (RelativeLayout) itemView.findViewById(R.id.rl_ripple);

                txtNo = (TextView) itemView.findViewById(R.id.txt_no);
                txtNo.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.06));
                txtNo.setPadding(0, 0, 0, mWidth / 64);

                txtActive = (TextView) itemView.findViewById(R.id.txt_active);
                txtActive.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));

                rippleBackground = (RippleBackground) itemView.findViewById(R.id.rippleBackground);
                Holderid = 0;
            } else {
                txtTittle = (TextView) itemView.findViewById(R.id.txt_tittle);
                txtTittle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));

                rlContent = (RelativeLayout) itemView.findViewById(R.id.rl_content);

                imgCat = (ImageView) itemView.findViewById(R.id.img_cat);
                Holderid = 1;
            }
        }
    }
}
