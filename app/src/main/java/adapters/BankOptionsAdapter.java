package adapters;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.app.tul.R;

import java.util.ArrayList;

import utils.Utils;

/**
 * Created by Rajat on 01-07-2016.
 */
public class BankOptionsAdapter extends BaseAdapter {
    private Context con;
    private LayoutInflater contact_inflate;
    private int mHeight;
    private int mWidth;
    private ArrayList<String> mData;
    private Utils util;
    private String mSelected;

    @SuppressWarnings("static-access")
    public BankOptionsAdapter(Context context, ArrayList<String> mData, String selected) {
        this.mData = mData;
        this.con = context;
        this.contact_inflate = LayoutInflater.from(con);
        util = new Utils(con);
        mHeight = util.getInt("height", 0);
        mWidth = util.getInt("width", 0);
        mSelected = selected;
    }

    @Override
    public int getCount() {
        return mData.size();

    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }


    @SuppressWarnings("deprecation")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final ViewHolder holder;
        if (convertView == null) {

            convertView = contact_inflate.inflate(R.layout.item_options, null);
            holder = new ViewHolder();

            holder.llMain = (LinearLayout) convertView.findViewById(R.id.ll_main);

            holder.llClick = (LinearLayout) convertView.findViewById(R.id.ll_click);
            holder.llClick.setPadding(mWidth / 32, 0, mWidth / 32, 0);

            holder.txtData = (TextView) convertView.findViewById(R.id.txt_data);
            holder.txtData.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (util.getInt("width", 0) * 0.05));
            holder.txtData.setPadding(0, mHeight / 48, 0, mHeight / 48);

            holder.imgTick = (ImageView) convertView.findViewById(R.id.img_tick);

            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();


        if (mSelected.equals(mData.get(position)))
            holder.imgTick.setVisibility(View.VISIBLE);
        else
            holder.imgTick.setVisibility(View.GONE);

        holder.txtData.setText(mData.get(position));

        return convertView;

    }


    public static class ViewHolder {
        LinearLayout llClick, llMain;
        TextView txtData;
        ImageView imgTick;
    }

}
