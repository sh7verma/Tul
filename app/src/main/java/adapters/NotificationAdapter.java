package adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tul.NotificationActivity;
import com.app.tul.R;
import com.google.maps.android.data.Style;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.ArrayList;

import customviews.BoldTextView;
import customviews.Card;
import customviews.CircleTransform;
import model.NotificationModel;
import utils.Constants;
import utils.Utils;

/**
 * Created by dev on 26/12/17.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    ArrayList<NotificationModel.ResponseBean> mData;
    private Context mContext;
    private Utils utils;
    private int mWidth, mHeight;

    public NotificationAdapter(Context context, ArrayList<NotificationModel.ResponseBean> response) {
        mContext = context;
        utils = new Utils(mContext);
        mData = response;
        mWidth = utils.getInt("width", 0);
        mHeight = utils.getInt("height", 0);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder vhItem;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        vhItem = new ViewHolder(v);
        return vhItem;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Picasso.with(mContext).load(mData.get(position).getUser_pic())
                .resize(mWidth / 8, mWidth / 8)
                .centerCrop().placeholder(R.mipmap.ic_small_ph_tul)
                .transform(new CircleTransform())
                .into(holder.imgUser);

        if (mData.get(position).getRead_status() == 0)
            holder.llMain.setBackgroundResource(R.drawable.white_black_stroke);
        else
            holder.llMain.setBackgroundResource(R.drawable.white_default);

        if (mData.get(position).getMessage().contains(mData.get(position).getUsername())) {
            int count = mData.get(position).getMessage().indexOf(mData.get(position).getUsername());
            int stringCount = mData.get(position).getUsername().length();
            SpannableString spannableString = new SpannableString(mData.get(position).getMessage());
            spannableString.setSpan(new StyleSpan(Typeface.BOLD), count, count + stringCount, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.txtNotification.setText(spannableString);
        } else {
            holder.txtNotification.setText(mData.get(position).getMessage());
        }
        try {
            holder.txtTime.setText(Constants.displayDateTime(mData.get(position).getCreated_at()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.cvMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NotificationActivity) mContext).markAsRead(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cvMain;
        ImageView imgUser;
        LinearLayout llMain;
        TextView txtNotification, txtTime;

        public ViewHolder(View itemView) {
            super(itemView);

            cvMain = (CardView) itemView.findViewById(R.id.cv_main);

            imgUser = (ImageView) itemView.findViewById(R.id.img_user);

            txtNotification = (TextView) itemView.findViewById(R.id.tv_notification);
            txtNotification.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
            txtNotification.setPadding(mWidth / 32, 0, mWidth / 32, 0);

            txtTime = (TextView) itemView.findViewById(R.id.tv_time);
            txtTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.03));
            txtTime.setPadding(mWidth / 32, 0, mWidth / 32, 0);

            llMain = (LinearLayout) itemView.findViewById(R.id.ll_main);
        }
    }
}


