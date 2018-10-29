package adapters;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.tul.R;

import java.util.ArrayList;

import utils.Utils;


public class TulCancellationPoliciesAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<String> mArrayList;
    private int mWidth, mHeight;
    private Utils utils;

    public TulCancellationPoliciesAdapter(Context context, ArrayList<String> arrayList) {
        mContext = context;
        mArrayList = arrayList;
        utils = new Utils(mContext);
        mWidth = utils.getInt("width", 0);
        mHeight = utils.getInt("height", 0);
    }

    @Override
    public int getCount() {
        return 5;
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


        MyHolder holder = null;
        if (holder == null) {

            holder = new MyHolder();
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_tul_cancellation_policies, parent, false);
            holder.tvRules = (TextView) convertView.findViewById(R.id.tv_rules);
            initUI(holder);
            convertView.setTag(holder);

        } else {
            holder = (MyHolder) convertView.getTag();
        }
//        holder.tvRules.setText(mArrayList.get(position));
        return convertView;
    }

    private void initUI(MyHolder holder) {

        holder.tvRules.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        holder.tvRules.setPadding(0, mWidth / 24, 0, mWidth / 24);
    }


    class MyHolder {
        TextView tvRules;
    }
}
