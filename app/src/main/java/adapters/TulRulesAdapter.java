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

import customviews.MediumTextView;
import utils.Utils;

/**
 * Created by dev on 14/10/17.
 */

public class TulRulesAdapter extends BaseAdapter {


    Context mContext;
    ArrayList<String> mTulRuleArrayList = new ArrayList<>();
    private Utils utils;
    private int mWidth, mHeight;

    public TulRulesAdapter(Context context, ArrayList<String> tulRuleArrayList) {
        mContext = context;
        mTulRuleArrayList = tulRuleArrayList;
        utils = new Utils(mContext);
        mWidth = utils.getInt("width", 0);
        mHeight = utils.getInt("height", 0);
    }

    @Override
    public int getCount() {
        return mTulRuleArrayList.size();
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
        ViewHolder holder = null;
        if (holder == null) {

            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_tul_rules, parent, false);

            holder.tvRule = (TextView) convertView.findViewById(R.id.tv_tul_rules);
            holder.imgDot = (ImageView) convertView.findViewById(R.id.img_dot);

            initUI(holder);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvRule.setText(mTulRuleArrayList.get(position));

        return convertView;
    }

    private void initUI(ViewHolder holder) {


        LinearLayout.LayoutParams paramdot = new LinearLayout.LayoutParams(mWidth / 24, mWidth / 24);
        paramdot.setMargins(0, mWidth / 25, mWidth / 32, mWidth / 25);
        holder.imgDot.setLayoutParams(paramdot);

        holder.tvRule.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        holder.tvRule.setPadding(mWidth / 32, mHeight / 25, mWidth / 32, mHeight / 25);
    }


    public class ViewHolder {
        TextView tvRule;
        ImageView imgDot;
    }
}
