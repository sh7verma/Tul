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
import com.app.tul.RulesActivity;

import java.util.ArrayList;

public class RulesListviewAdapter extends BaseAdapter {


    Context context;
    ArrayList<String> rulesArrayList = new ArrayList<>();
    int mWidth;

    public RulesListviewAdapter(Context context, ArrayList<String> rulesArrayList, int mWidth) {
        this.rulesArrayList = rulesArrayList;
        this.context = context;
        this.mWidth = mWidth;
    }

    @Override
    public int getCount() {
        return rulesArrayList.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        MyHolder holder =null;
        if (holder == null) {

            holder = new MyHolder();
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_rules, parent, false);
            holder.imgDelete = (ImageView) convertView.findViewById(R.id.img_delete);
            holder.imgDot = (ImageView) convertView.findViewById(R.id.img_dot);
            holder.tvRules = (TextView) convertView.findViewById(R.id.tv_rules);
            holder.llRuleView = (LinearLayout) convertView.findViewById(R.id.ll_rule_view);
            initUI(holder);
            convertView.setTag(holder);

        } else {
            holder = (MyHolder) convertView.getTag();
        }

        holder.tvRules.setText(rulesArrayList.get(position));

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rulesArrayList.remove(position);
                ((RulesActivity) context).update(rulesArrayList);
            }
        });

        holder.llRuleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RulesActivity) context).openRulesDialog(rulesArrayList.get(position), position);
            }
        });

        return convertView;
    }

    private void initUI(MyHolder holder) {
        holder.imgDelete.setPadding(mWidth / 32, mWidth / 32, 0, mWidth / 32);

        LinearLayout.LayoutParams paramdot = new LinearLayout.LayoutParams(mWidth / 24, mWidth / 24);
        paramdot.setMargins(0, mWidth / 32, mWidth / 32, mWidth / 32);
        holder.imgDot.setLayoutParams(paramdot);

        holder.tvRules.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        holder.tvRules.setPadding(mWidth / 32, mWidth / 24, mWidth / 32, mWidth / 24);
    }

    class MyHolder {

        TextView tvRules;
        ImageView imgDelete, imgDot;
        LinearLayout llRuleView;
    }

}
