package adapters;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tul.R;

import java.util.ArrayList;

import model.PlaceInfo;
import utils.Utils;


/**
 * Created by dev on 5/10/17.
 */

public class PlaceInfoArrayAdapter extends BaseAdapter {


    Context mContext;
    ArrayList<PlaceInfo> mPlaceInfoArrayList;
    private Utils utils;
    private int mWidth, mHeight;

    public PlaceInfoArrayAdapter(Context context, ArrayList<PlaceInfo> placeInfoArrayList) {
        mContext = context;
        mPlaceInfoArrayList = placeInfoArrayList;
        utils = new Utils(mContext);
        mWidth = utils.getInt("width", 0);
        mHeight = utils.getInt("height", 0);
    }

    @Override
    public int getCount() {
        return mPlaceInfoArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = (LinearLayout) inflater.inflate(R.layout.item_place_search, parent, false);

        LinearLayout llMain = (LinearLayout) convertView.findViewById(R.id.ll_main);
        llMain.setPadding(mWidth / 32, 0, mWidth / 32, 0);

        TextView txtView = (TextView) convertView.findViewById(R.id.tvset_location_on_map);
        txtView.setText(mPlaceInfoArrayList.get(position).getName());
        txtView.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));

        return convertView;
    }
}
