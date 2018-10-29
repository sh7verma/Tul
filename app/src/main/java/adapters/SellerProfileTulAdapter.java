package adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tul.R;
import com.app.tul.Sales.SalesTulDetailActivity;
import com.app.tul.Sales.SellerProfileActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import model.SalesTulDetailModel;
import utils.Constants;
import utils.Utils;

/**
 * Created by dev on 23/8/18.
 */

public class SellerProfileTulAdapter extends RecyclerView.Adapter<SellerProfileTulAdapter.ViewHolder> {

    private static final int MOVE_BACK = 1;
    private static final int DELETE = 2;

    LinearLayout.LayoutParams cvParms, viewParms;
    ArrayList<SalesTulDetailModel.ResponseBean> mData;
    private Context mContext;
    private Utils utils;
    private int mWidth, mHeight, mTulImageHeight, mTulImageWidth;

    public SellerProfileTulAdapter(Context context, ArrayList<SalesTulDetailModel.ResponseBean> data) {
        mContext = context;
        utils = new Utils(mContext);
        mWidth = utils.getInt("width", 0);
        mHeight = utils.getInt("height", 0);
        mData = data;

        mTulImageHeight = mHeight / 3;
        mTulImageWidth = mWidth - (mWidth / 64);

        cvParms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mTulImageHeight);
        cvParms.setMargins(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);
        viewParms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        viewParms.setMargins(mWidth / 16, 0, mWidth / 16, 0);
    }

    @Override
    public SellerProfileTulAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SellerProfileTulAdapter.ViewHolder vhItem;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listed_tuls, parent, false);
        vhItem = new SellerProfileTulAdapter.ViewHolder(v);
        return vhItem;
    }

    @Override
    public void onBindViewHolder(final SellerProfileTulAdapter.ViewHolder holder, final int position) {

        if (mData.get(position).getAttachment().get(0).getType().equals(Constants.IMAGE_TEXT)) {
            holder.imgVideoPlay.setVisibility(View.GONE);
            Picasso.with(mContext).load(mData.get(position).getAttachment().get(0).getAttachment())
                    .resize(mTulImageWidth, mTulImageHeight).centerCrop().placeholder(R.drawable.primary_ripple)
                    .into(holder.imgTul);
        } else if (mData.get(position).getAttachment().get(0).getType().equals(Constants.VIDEO_TEXT)) {
            holder.imgVideoPlay.setVisibility(View.VISIBLE);
            Picasso.with(mContext).load(mData.get(position).getAttachment().get(0).getThumbnail())
                    .resize(mTulImageWidth, mTulImageHeight)
                    .centerCrop().placeholder(R.drawable.primary_ripple)
                    .into(holder.imgTul);
        }
        holder.txtTittle.setText(mData.get(position).getTitle());
        holder.txtPrice.setText( mData.get(position).getCurrency() + " " + mData.get(position).getPrice());
        holder.txtToolCondition.setText(mData.get(position).getCondition() + "");

        holder.cvTul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constants.TUL_ID == mData.get(position).getId())
                    ((SellerProfileActivity) mContext).moveBack();
                else {
                    Intent intent = new Intent(mContext, SalesTulDetailActivity.class);
                    intent.putExtra("data", mData.get(position));
                    intent.putExtra("position", position);
                    intent.putExtra("id", mData.get(position).getId());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation(((Activity) mContext),
                                holder.imgTul, mContext.getString(R.string.transition));
                        ((Activity) mContext).startActivityForResult(intent, DELETE, option.toBundle());
                    } else {
                        ((Activity) mContext).startActivityForResult(intent, DELETE);
                        ((Activity) mContext).overridePendingTransition(R.anim.slideup_in, R.anim.slideup_out);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTittle, txtPrice, txtToolCondition;
        CardView cvTul;
        ImageView imgTul, imgVideoPlay;
        View view1;

        public ViewHolder(View itemView) {
            super(itemView);

            cvTul = (CardView) itemView.findViewById(R.id.cv_tul);
            cvTul.setLayoutParams(cvParms);

            view1 = (View) itemView.findViewById(R.id.view_1);
            view1.setLayoutParams(viewParms);

            imgTul = (ImageView) itemView.findViewById(R.id.img_tul);

            txtTittle = (TextView) itemView.findViewById(R.id.txt_tittle);
            txtTittle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));
            txtTittle.setPadding(mWidth / 32, 0, mWidth / 5, mWidth / 28);

            txtPrice = (TextView) itemView.findViewById(R.id.txt_price);
            txtPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));
            txtPrice.setPadding(0, 0, mWidth / 32, mWidth / 32);

            imgVideoPlay = (ImageView) itemView.findViewById(R.id.img_video_play);

            txtToolCondition = (TextView) itemView.findViewById(R.id.txt_tool_condition);

            if (utils.getInt(Constants.USER_LOGIN_MODE, Constants.USER_RENTAL) == Constants.USER_BUY) {
                txtToolCondition.setVisibility(View.VISIBLE);
            }

        }
    }
}
