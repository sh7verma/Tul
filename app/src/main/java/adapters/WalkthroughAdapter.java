package adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tul.R;

import java.util.ArrayList;

import utils.Utils;


public class WalkthroughAdapter extends PagerAdapter {

    Context con;
    LayoutInflater inflater;
    int mWidth, mHeight;
    int[] mWalkArray;
    Utils util;

    public WalkthroughAdapter(Context con, int[] mWalkArray) {
        this.con = con;
        util = new Utils(con);
        inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mWidth = util.getInt("width", 0);
        this.mHeight = util.getInt("height", 0);
        this.mWalkArray = mWalkArray;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mWalkArray.length;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.e("position", position + "");

        View itemView = inflater.inflate(R.layout.item_walkthrough, container, false);

        ImageView imgIcon = (ImageView) itemView.findViewById(R.id.img_walk);
        imgIcon.setBackgroundResource(mWalkArray[position]);

        ((ViewPager) container).addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(container);
    }
}
