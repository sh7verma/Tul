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
import com.app.tul.NearByTulListingActivity;
import com.app.tul.R;
import com.app.tul.TulDetailActivity;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import model.ProfileModel;
import model.SearchResultModel;
import utils.Constants;
import utils.Utils;

/**
 * Created by applify on 12/21/2017.
 */

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {

    private static final int TYPE_ITEM = 1;
    private Context con;
    private Utils utils;
    ArrayList<SearchResultModel.ResponseBean> mData;
    private static final int TULDETAIL = 3;
    private int mWidth, mHeight, mTulImageHeight, mTulImageWidth;
    LinearLayout.LayoutParams cvParms;
    FrameLayout.LayoutParams mainParms;

    public SearchResultAdapter(Context con, ArrayList<SearchResultModel.ResponseBean> mData) {
        this.con = con;
        this.mData = mData;
        utils = new Utils(con);
        mWidth = utils.getInt("width", 0);
        mHeight = utils.getInt("height", 0);

        mTulImageHeight = (int) (mHeight / 4);
        mTulImageWidth = (int) (mWidth / 2);

        mainParms = new FrameLayout.LayoutParams(mTulImageWidth, ViewGroup.LayoutParams.WRAP_CONTENT);

        cvParms = new LinearLayout.LayoutParams(mTulImageWidth, mTulImageHeight);
        cvParms.setMargins(mWidth / 48, 0, mWidth / 48, 0);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result, parent, false);
        ViewHolder vhItem = new ViewHolder(v);
        return vhItem;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
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

        holder.txtTittle.setText(mData.get(position).getTitle());
        holder.txtPrice.setText(mData.get(position).getCurrency()+ " " + mData.get(position).getPrice());
        holder.srbTul.setRating(mData.get(position).getRating());

        holder.cvTul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(con, ListingTulDetailActivity.class);
                intent.putExtra("search_data", mData.get(position));
                intent.putExtra("position", position);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation(((Activity) con),
                            holder.imgTul, con.getString(R.string.transition));
                    ((Activity) con).startActivityForResult(intent, TULDETAIL, option.toBundle());
                } else {
                    ((Activity) con).startActivityForResult(intent, TULDETAIL);
                    ((Activity) con).overridePendingTransition(R.anim.slideup_in, R.anim.slideup_out);
                }
            }
        });

        holder.llRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /// no op
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTittle, txtPrice;
        CardView cvTul;
        ImageView imgTul, imgVideoPlay;
        SimpleRatingBar srbTul;
        LinearLayout llRating, llMain;

        public ViewHolder(View itemView) {
            super(itemView);

            llMain = (LinearLayout) itemView.findViewById(R.id.ll_main);
            llMain.setLayoutParams(mainParms);

            cvTul = (CardView) itemView.findViewById(R.id.cv_tul);
            cvTul.setLayoutParams(cvParms);

            imgTul = (ImageView) itemView.findViewById(R.id.img_tul);

            txtTittle = (TextView) itemView.findViewById(R.id.txt_tittle);
            txtTittle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));
            txtTittle.setPadding(mWidth / 20, 0, 0, mWidth / 64);

            txtPrice = (TextView) itemView.findViewById(R.id.txt_price);
            txtPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));
            txtPrice.setPadding(mWidth / 32, 0, mWidth / 32, mWidth / 64);

            imgVideoPlay = (ImageView) itemView.findViewById(R.id.img_video);

            srbTul = (SimpleRatingBar) itemView.findViewById(R.id.srb_tul);
            srbTul.setPadding(mWidth / 20, 0, 0, 0);

            llRating = (LinearLayout) itemView.findViewById(R.id.ll_rating);
        }
    }
}
