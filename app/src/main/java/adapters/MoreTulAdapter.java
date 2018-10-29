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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tul.ListingTulDetailActivity;
import com.app.tul.R;
import com.app.tul.Sales.SalesTulDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import model.NearByTulListingModel;
import utils.Constants;
import utils.Utils;

/**
 * Created by dev on 8/11/17.
 */

public class MoreTulAdapter extends RecyclerView.Adapter<MoreTulAdapter.ViewHolder> {

    private static final int DELETE = 2;
    ArrayList<NearByTulListingModel.ResponseBean> mMoreTulArrayList = new ArrayList<>();
    private Context con;
    private Utils utils;
    private int mWidth, mHeight;

    public MoreTulAdapter(Context con, ArrayList<NearByTulListingModel.ResponseBean> moreTulArrayList) {
        this.con = con;
        utils = new Utils(con);
        mWidth = utils.getInt("width", 0);
        mHeight = utils.getInt("height", 0);
        mMoreTulArrayList = moreTulArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_more_tuls, parent, false);
        ViewHolder vhItem = new ViewHolder(v);
        return vhItem;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tvTitle.setText(mMoreTulArrayList.get(position).getTitle());
        holder.tvPrice.setText(mMoreTulArrayList.get(position).getCurrency()+ mMoreTulArrayList.get(position).getPrice());

        Picasso.with(con)
                .load(mMoreTulArrayList.get(position).getAttachment().get(0).getThumbnail())
                .resize(mHeight / 4, mHeight / 5)
                .centerCrop()
                .into(holder.imgTul);

        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (utils.getInt(Constants.USER_LOGIN_MODE, Constants.USER_RENTAL) == Constants.USER_RENTAL) {
                    Intent intent = new Intent(con, ListingTulDetailActivity.class);
                    intent.putExtra("data", mMoreTulArrayList.get(position));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation(((Activity) con),
                                holder.imgTul, con.getString(R.string.transition));
                        ((Activity) con).startActivity(intent, option.toBundle());
                    } else {
                        ((Activity) con).startActivity(intent);
                        ((Activity) con).overridePendingTransition(R.anim.slideup_in, R.anim.slideup_out);
                    }
                } else {
                    Intent intent = new Intent(con, SalesTulDetailActivity.class);
                    intent.putExtra("id", mMoreTulArrayList.get(position).getId());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation(((Activity) con),
                                holder.imgTul, con.getString(R.string.transition));
                        ((Activity) con).startActivityForResult(intent, DELETE, option.toBundle());
                    } else {
                        con.startActivity(intent);
                        ((Activity) con).overridePendingTransition(R.anim.slideup_in, R.anim.slideup_out);
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mMoreTulArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout llMain;
        CardView cvTul;
        ImageView imgTul;
        ImageView imgHeart;
        TextView tvTitle;
        TextView tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);

            imgTul = (ImageView) itemView.findViewById(R.id.img_more_tul);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(mHeight / 4, mHeight / 5);
            imgTul.setLayoutParams(layoutParams);

            llMain = (LinearLayout) itemView.findViewById(R.id.ll_main);
            llMain.setPadding(0, 0, mWidth / 28, 0);

            FrameLayout.LayoutParams layoutParams1 = new FrameLayout.LayoutParams(mHeight / 4, (int) (mHeight / 3.7));
            llMain.setLayoutParams(layoutParams1);

            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            tvPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.03));
            tvPrice.setPadding(mWidth / 55, mHeight / 55, mHeight / 85, mHeight / 85);

            tvTitle = (TextView) itemView.findViewById(R.id.tv_tul_title);
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.03));
            tvTitle.setPadding(mWidth / 55, mHeight / 70, 0, 0);
        }
    }
}