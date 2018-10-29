package adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.StrictMode;
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

import com.app.tul.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import customviews.TouchImageView;

import static utils.Constants.getImageOrientation;
import static utils.Constants.getImageUri;

public class FullImageViewAdapter extends PagerAdapter {
    Context con;
    int mScreenwidth, mScreenheight;
    ArrayList<String> pic_array;
    String path;
    LayoutInflater inflater;

    public FullImageViewAdapter(Context con, ArrayList<String> pic_array, String path, int mWidth, int mHeight) {
        this.con = con;
        inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mScreenwidth = mWidth;
        this.mScreenheight = mHeight;
        this.pic_array = pic_array;
        this.path = path;
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return pic_array.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.e("position", position + "");

        View itemView = inflater.inflate(R.layout.item_fullview, container, false);

        RelativeLayout.LayoutParams pic_parms = new RelativeLayout.LayoutParams(mScreenwidth, (int) (mScreenheight / 1.5));
        pic_parms.addRule(RelativeLayout.CENTER_IN_PARENT);
        pic_parms.setMargins(mScreenwidth / 64, mScreenwidth / 64, mScreenwidth / 64, mScreenwidth / 64);
        TouchImageView imgPic = (TouchImageView) itemView.findViewById(R.id.img_pic);
//        imgPic.setLayoutParams(pic_parms);

        RelativeLayout.LayoutParams loader_parms = new RelativeLayout.LayoutParams(mScreenwidth / 7, mScreenwidth / 7);
        loader_parms.addRule(RelativeLayout.CENTER_IN_PARENT);
        final ProgressBar pbLoad = (ProgressBar) itemView.findViewById(R.id.pb_load);
        pbLoad.setLayoutParams(loader_parms);


        if (path.equals("1")) {
            if (pic_array.get(position).startsWith("http")) {
                imgPic.setImageBitmap(getBitmapFromURL(pic_array.get(position)));
                pbLoad.setVisibility(View.GONE);
                /*Picasso.with(con).load(pic_array.get(position)).
                        into(imgPic, new Callback() {
                            @Override
                            public void onSuccess() {
                                pbLoad.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {

                            }
                        });*/
            } else {
                File mFile = new File(pic_array.get(position));
               /* Picasso.with(con).load(Uri.parse(pic_array.get(position)))
                        .into(imgPic, new Callback() {
                            @Override
                            public void onSuccess() {
                                pbLoad.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {

                            }
                        });*/

                imgPic.setImageURI(Uri.parse(pic_array.get(position)));
                pbLoad.setVisibility(View.GONE);
            }
        } else {
            Picasso.with(con).load(pic_array.get(position))
                    .into(imgPic, new Callback() {
                        @Override
                        public void onSuccess() {
                            pbLoad.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {

                        }
                    });
        }
        ((ViewPager) container).addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    public Bitmap getBitmapFromURL(String src) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);

            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();

            Bitmap myBitmap1 = BitmapFactory.decodeStream(input);

            return myBitmap1;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
