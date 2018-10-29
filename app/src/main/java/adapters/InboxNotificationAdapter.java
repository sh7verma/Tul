package adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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

/**
 * Created by dev on 1/12/17.
 */

public class InboxNotificationAdapter extends RecyclerView.Adapter<InboxNotificationAdapter.ViewHolder> {

    ArrayList<String> mData;
    private Context con;
    private Utils utils;
    private int mWidth, mHeight;

    public InboxNotificationAdapter(Context con) {
        this.con = con;
        utils = new Utils(con);
        mWidth = utils.getInt("width", 0);
        mHeight = utils.getInt("height", 0);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_notification, viewGroup, false);
        ViewHolder vhItem = new ViewHolder(v);
        return vhItem;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNotification, tvTime;
        ImageView imgUser;
        CardView cvCard;
        LinearLayout llMain;

        public ViewHolder(View itemView) {
            super(itemView);

            cvCard = (CardView) itemView.findViewById(R.id.cv_main);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(mWidth / 42, mHeight / 42, mWidth / 42, mWidth / 72);
            cvCard.setLayoutParams(layoutParams);

            llMain = (LinearLayout) itemView.findViewById(R.id.ll_main);
            llMain.setPadding(mWidth / 52, mHeight / 52, mWidth / 52, mHeight / 52);

            imgUser = (ImageView) itemView.findViewById(R.id.img_user);
            imgUser.setPadding(0, 0, mWidth / 32, 0);

            tvNotification = (TextView) itemView.findViewById(R.id.tv_notification);
            tvNotification.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.03));

            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.03));
            tvTime.setPadding(0, mWidth / 62, 0, 0);

        }
    }
}
