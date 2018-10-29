package adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.app.tul.PlayVideo;
import com.app.tul.R;
import com.app.tul.TullFullViewActivity;
import com.facebook.Profile;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import model.AttachmentModel;
import model.NearByTulListingModel;
import model.ProfileModel;
import model.TulImageModel;
import utils.Constants;
import utils.Utils;


public class TulDetailImagesAdapter extends PagerAdapter {
    Context con;
    private int mScreenwidth, mScreenheight;
    private ArrayList<AttachmentModel> mData;
    private LayoutInflater inflater;
    Utils utils;

    public TulDetailImagesAdapter(Context con, ArrayList<AttachmentModel> mData) {
        this.con = con;
        inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        utils = new Utils(con);
        this.mScreenwidth = utils.getInt("width", 0);
        this.mScreenheight = utils.getInt("height", 0);
        this.mData = mData;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        Log.e("position", position + "");

        View itemView = inflater.inflate(R.layout.item_fullview, container, false);

        RelativeLayout.LayoutParams imgParms = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mScreenheight / 3);
        ImageView imgPic = (ImageView) itemView.findViewById(R.id.img_pic);
        imgPic.setLayoutParams(imgParms);

        RelativeLayout.LayoutParams loader_parms = new RelativeLayout.LayoutParams(mScreenwidth / 7, mScreenwidth / 7);
        loader_parms.addRule(RelativeLayout.CENTER_IN_PARENT);
        final ProgressBar pbLoad = (ProgressBar) itemView.findViewById(R.id.pb_load);
        pbLoad.setLayoutParams(loader_parms);

        ImageView imgVideoPlay = (ImageView) itemView.findViewById(R.id.img_video_play);

        if (mData.get(position).getType().equals(Constants.VIDEO_TEXT))
            imgVideoPlay.setVisibility(View.VISIBLE);
        else
            imgVideoPlay.setVisibility(View.GONE);

        imgPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(con, TullFullViewActivity.class);
                intent.putExtra("hide", "yes");
                intent.putExtra("position", position);
                intent.putParcelableArrayListExtra("tullImages", getImagesArray());
                con.startActivity(intent);
            }
        });

        if (mData.get(position).getType().equals(Constants.IMAGE_TEXT)) {
            Picasso.with(con).load(mData.get(position).getAttachment()).centerCrop()
                    .resize(mScreenwidth, mScreenheight / 3)
                    .placeholder(R.drawable.primary_ripple)
                    .into(imgPic, new Callback() {
                        @Override
                        public void onSuccess() {
                            pbLoad.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            pbLoad.setVisibility(View.GONE);
                        }
                    });
        } else {
            Picasso.with(con).load(mData.get(position).getThumbnail()).centerCrop()
                    .resize(mScreenwidth, mScreenheight / 3)
                    .placeholder(R.drawable.primary_ripple)
                    .into(imgPic, new Callback() {
                        @Override
                        public void onSuccess() {
                            pbLoad.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            pbLoad.setVisibility(View.GONE);
                        }
                    });

        }

        ((ViewPager) container).addView(itemView);
        return itemView;
    }

    private ArrayList<TulImageModel> getImagesArray() {
        ArrayList<TulImageModel> imagesArray = new ArrayList<>();
        for (AttachmentModel item : mData) {
            TulImageModel imageModel = new TulImageModel();
            imageModel.setId(item.getId());
            imageModel.setType(item.getType());
            if (item.getType().equals(Constants.IMAGE_TEXT))
                imageModel.setThumbnails(item.getAttachment());
            else
                imageModel.setThumbnails(item.getThumbnail());
            imageModel.setPath(item.getAttachment());
            imagesArray.add(imageModel);
        }
        return imagesArray;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
