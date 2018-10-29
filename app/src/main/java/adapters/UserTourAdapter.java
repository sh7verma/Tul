package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.app.tul.R;

import java.util.ArrayList;

import model.ContentModel;
import utils.Utils;

public class UserTourAdapter extends RecyclerView.Adapter<UserTourAdapter.ViewHolder> {

    Context con;
    int mWidth;
    int mHeight;
    Utils utils;
    ArrayList<ContentModel> mData;
    int lastPosition = -1;

    public UserTourAdapter(Context con, ArrayList<ContentModel> mData) {
        this.con = con;
        this.mData = mData;
        utils = new Utils(con);
        mWidth = utils.getInt("width", 0);
        mHeight = utils.getInt("height", 0);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_howitworks, parent, false);
        ViewHolder vhItem = new ViewHolder(v);
        return vhItem;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (position + 1 == 3)
            holder.viewSideBelow.setVisibility(View.GONE);
        else
            holder.viewSideBelow.setVisibility(View.VISIBLE);

        if (position == 0)
            holder.viewSideAbove.setVisibility(View.INVISIBLE);
        else
            holder.viewSideAbove.setVisibility(View.VISIBLE);

        holder.txtTittle.setText(mData.get(position).getTittle());
        holder.txtDescription.setText(mData.get(position).getDescrption());

        setAnimation(holder.llMain, position);
    }


    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(con, R.anim.fadein);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTittle, txtDescription;
        LinearLayout llMain;
        View viewSideAbove;
        View viewSideBelow;

        public ViewHolder(View itemView) {
            super(itemView);

            txtTittle = (TextView) itemView.findViewById(R.id.txt_tittle);
            txtTittle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));

            txtDescription = (TextView) itemView.findViewById(R.id.txt_description);
            txtDescription.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));

            llMain = (LinearLayout) itemView.findViewById(R.id.ll_main);

            viewSideBelow = (View) itemView.findViewById(R.id.view_side_below);
            viewSideAbove = (View) itemView.findViewById(R.id.view_side_above);
        }
    }
}
