package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.app.tul.AddTulActivity;
import com.app.tul.R;
import com.app.tul.Sales.SalesListYourTulActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import model.TulImageModel;
import utils.Constants;
import utils.Utils;

public class TullImagesAdapter extends RecyclerView.Adapter<TullImagesAdapter.ViewHolder> {

    private static final int TYPE_ITEM = 1;
    ArrayList<TulImageModel> mData;
    private Context con;
    private Utils utils;
    private int mWidth, mHeight;
    private AddTulActivity mAddTulActivity;
    private SalesListYourTulActivity mSalesListYourTulActivity;

    public TullImagesAdapter(Context con, ArrayList<TulImageModel> mData, AddTulActivity mAddTulActivity) {
        this.con = con;
        this.mData = mData;
        utils = new Utils(con);
        this.mAddTulActivity = mAddTulActivity;
        mWidth= utils.getInt("width", 0);
        mHeight = utils.getInt("height", 0);
    }

    public TullImagesAdapter(Context con, ArrayList<TulImageModel> mData, SalesListYourTulActivity mActivity) {
        this.con = con;
        this.mData = mData;
        utils = new Utils(con);
        this.mSalesListYourTulActivity = mActivity;
        mWidth = utils.getInt("width", 0);
        mHeight = utils.getInt("height", 0);

    }

    @Override
    public TullImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_images_tul, parent, false);
        TullImagesAdapter.ViewHolder vhItem = new TullImagesAdapter.ViewHolder(v);
        return vhItem;
    }

    @Override
    public void onBindViewHolder(final TullImagesAdapter.ViewHolder holder, final int position) {

        if (mData.get(position).getType().equals(Constants.IMAGE))
            holder.imgVideoPlay.setVisibility(View.GONE);
        else if (mData.get(position).getType().equals(Constants.VIDEO))
            holder.imgVideoPlay.setVisibility(View.VISIBLE);

        if (mData.get(position).getThumbnails().startsWith("http")) {
            Picasso.with(con).load(mData.get(position).getThumbnails())
                    .resize(mWidth / 4, mWidth / 4).centerCrop()
                    .placeholder(R.drawable.primary_ripple).into(holder.imgTull);
        } else {
            File mFile = new File(mData.get(position).getThumbnails());
            Picasso.with(con).load(mFile)
                    .resize(mWidth / 4, mWidth / 4).centerCrop()
                    .placeholder(R.drawable.primary_ripple).into(holder.imgTull, new Callback() {
                @Override
                public void onSuccess() {
                    Log.e("Success = ", "Yes");
                }

                @Override
                public void onError() {
                    Log.e("Error = ", "Yes");
                }
            });
        }
       holder.rlContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (utils.getInt(Constants.USER_LOGIN_MODE, Constants.USER_RENTAL) == Constants.USER_RENTAL) {
                    mAddTulActivity.fullView(holder.getAdapterPosition());
                } else {
                    mSalesListYourTulActivity.fullView(holder.getAdapterPosition());
                }

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

        RelativeLayout rlContent;
        ImageView imgTull, imgVideoPlay;

        public ViewHolder(View itemView) {
            super(itemView);

            rlContent = (RelativeLayout) itemView.findViewById(R.id.rl_content);

            RelativeLayout.LayoutParams imgParms = new RelativeLayout.LayoutParams(mWidth / 4, mWidth / 4);
            imgParms.setMargins(mWidth / 64, 0, mWidth / 64, 0);
            imgTull = (ImageView) itemView.findViewById(R.id.img_tull);
            imgTull.setLayoutParams(imgParms);

            imgVideoPlay = (ImageView) itemView.findViewById(R.id.img_video_play);



        }
    }
}