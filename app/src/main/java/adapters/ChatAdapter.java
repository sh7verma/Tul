package adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.app.tul.ChatActivity;
import com.app.tul.R;

import org.apache.commons.lang3.StringEscapeUtils;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import model.ChatMessageModel;
import utils.Constants;
import utils.Utils;


public class ChatAdapter extends BaseAdapter {
    private Context con;
    private LayoutInflater contact_inflate;
    private int mHeight;
    private int mWidth;
    private ArrayList<String> mKeys;
    HashMap<String, ChatMessageModel> mData;
    private Utils util;
    private String otherProfilePic, mOtherUserID;
    private ChatActivity mChatActivity;
    SimpleDateFormat localFormat = new SimpleDateFormat("hh:mm aa", Locale.US);


    @SuppressWarnings("static-access")
    public ChatAdapter(Context context, ArrayList<String> mKeys, HashMap<String, ChatMessageModel> mData,
                       String otherProfilePic, ChatActivity mChatActivity, String mOtherUserID) {
        this.mData = mData;
        this.mKeys = mKeys;
        this.otherProfilePic = otherProfilePic;
        this.con = context;
        this.contact_inflate = LayoutInflater.from(con);
        this.mChatActivity = mChatActivity;
        util = new Utils(con);
        mHeight = util.getInt("height", 0);
        mWidth = util.getInt("width", 0);
        this.mOtherUserID = mOtherUserID;
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

            convertView = contact_inflate.inflate(R.layout.item_chat, null);
            holder = new ViewHolder();

            LinearLayout.LayoutParams imgParms = new LinearLayout.LayoutParams(mWidth / 8, mWidth / 8);
            imgParms.setMargins(mWidth / 32, 0, mWidth / 32, 0);

            LinearLayout.LayoutParams layParms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layParms.setMargins(mWidth/32, 0, mWidth/32, mWidth / 32);
            holder.llSend = (LinearLayout) convertView.findViewById(R.id.ll_send);
            holder.llSend.setLayoutParams(layParms);

            holder.llReceive = (LinearLayout) convertView.findViewById(R.id.ll_receive);
            holder.llReceive.setLayoutParams(layParms);

            holder.llSendInside = (LinearLayout) convertView.findViewById(R.id.ll_send_inside);
            holder.llReceiveInside = (LinearLayout) convertView.findViewById(R.id.ll_receive_inside);

            holder.llSendTime = (LinearLayout) convertView.findViewById(R.id.ll_send_time);
//            holder.llSendTime.setPadding(0, 0, mWidth / 32, 0);

            holder.imgSend = (ImageView) convertView.findViewById(R.id.img_send);
            holder.imgReceive = (ImageView) convertView.findViewById(R.id.img_receive);

            holder.imgReceive.setLayoutParams(imgParms);
            holder.imgSend.setLayoutParams(imgParms);

            holder.txtTime = (TextView) convertView.findViewById(R.id.txt_time);
            holder.txtTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));
            holder.txtTime.setPadding(mWidth / 32, mWidth / 64, mWidth / 32, mWidth / 64);

            holder.txtReceive = (TextView) convertView.findViewById(R.id.txt_receive);
            holder.txtReceive.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
            holder.txtReceive.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);

            holder.txtSend = (TextView) convertView.findViewById(R.id.txt_send);
            holder.txtSend.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
            holder.txtSend.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);

            holder.txtReceiveTime = (TextView) convertView.findViewById(R.id.txt_receive_time);
            holder.txtReceiveTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));
            holder.txtReceiveTime.setPadding(0, 0, mWidth / 32, mWidth / 32);

            holder.txtSendTime = (TextView) convertView.findViewById(R.id.txt_send_time);
            holder.txtSendTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));
            holder.txtSendTime.setPadding(mWidth / 32, 0, mWidth / 84, 0);

            holder.imgMsgStatus = (ImageView) convertView.findViewById(R.id.img_msg_status);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        if (mData.get(mKeys.get(position)).is_header()) {
//            holder.txtTime.setVisibility(View.VISIBLE);
            holder.llReceive.setVisibility(View.GONE);
            holder.llSend.setVisibility(View.GONE);
            holder.txtTime.setText(mData.get(mKeys.get(position)).getShow_HeaderText());
        } else {
//            holder.txtTime.setVisibility(View.GONE);

            boolean containsOtherText = false;
            int tempLength = 1;
            int emojiCounter = 0;
            String tempChatTrimmed = null;
            int flag = 0;
            String show = "";
            // separate input by spaces ( URLs don't have spaces )
            String[] parts = mData.get(mKeys.get(position)).getMessage().split("\\s+");

            // Attempt to convert each item into an URL.
            for (String item : parts)
                try {
                    URL url = new URL(item);
                    // If possible then replace with anchor...
                    System.out.print("<a href=\"" + url + "\">" + url + "</a> ");
                    show = show + "<a href=\"" + url + "\">" + url + "</a> ";
                    flag = 1;
                } catch (Exception e) {
                    // If there was an URL that was not it!...
                    System.out.print(item + " ");
                    show = show + item + " ";
                }
            for (int i = 0; i < mData.get(mKeys.get(position)).getMessage().length(); ) {
                if (mData.get(mKeys.get(position)).getMessage().length() >= i + 2) {
                    tempChatTrimmed = mData.get(mKeys.get(position)).getMessage().trim().substring(i, i + 2);
                    String toServerUnicodeEncoded1 = StringEscapeUtils.escapeJava(tempChatTrimmed);
                    if (toServerUnicodeEncoded1.startsWith("\\u")) {
                        emojiCounter++;
                        i = i + 2;
                    } else if (!toServerUnicodeEncoded1.startsWith("\\u")) {
                        containsOtherText = true;
                    }
                } else {
                    tempChatTrimmed = mData.get(mKeys.get(position)).getMessage().trim().substring(i, i + 1);
                    String toServerUnicodeEncoded1 = StringEscapeUtils.escapeJava(tempChatTrimmed);
                    if (toServerUnicodeEncoded1.startsWith("\\u")) {
                        emojiCounter++;
                        i = i + 1;
                    } else if (!toServerUnicodeEncoded1.startsWith("\\u")) {
                        containsOtherText = true;
                    }
                }
                if (emojiCounter > 3 || containsOtherText) {
                    break;
                }
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(Constants.getLocalTime(Long.parseLong(mData.get(mKeys.get(position)).getMessage_time())));

            if (mData.get(mKeys.get(position)).getSender_id().equals(util.getString("user_id", ""))) {
                /// sender
                if (mChatActivity.mSelectedPosition == position) {
                    holder.llSend.setBackgroundColor(con.getResources().getColor(R.color.light_grey));
                } else {
                    holder.llSend.setBackgroundColor(con.getResources().getColor(R.color.white_color));
                }
                holder.llSend.setVisibility(View.VISIBLE);
                holder.llReceive.setVisibility(View.GONE);

                if (emojiCounter == 1 && !containsOtherText) {
                    holder.txtSend.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.09));
                } else if (emojiCounter == 2 && !containsOtherText) {
                    holder.txtSend.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.082));
                } else if (emojiCounter == 3 && !containsOtherText) {
                    holder.txtSend.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.07));
                } else {
                    holder.txtSend.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
                }

                if (flag == 0) {
                    holder.txtSend.setText(mData.get(mKeys.get(position)).getMessage());
                    holder.txtSend.setTextColor(ContextCompat.getColor(con, R.color.white_color));
                } else {
                    holder.txtSend.setText(Html.fromHtml(show));
                    holder.txtSend.setMovementMethod(LinkMovementMethod.getInstance());
                    holder.txtSend.setTextColor(ContextCompat.getColor(con, R.color.facebook));
                }
                holder.txtSendTime.setText(localFormat.format(calendar.getTime()));

//                if (!TextUtils.isEmpty(util.getString("pic_url", "")))
//                    Picasso.with(con).load(util.getString("pic_url", "")).resize((mWidth / 6), (mWidth / 6)).centerCrop()
//                            .transform(new CircleTransform()).placeholder(R.drawable.ic_no_dp).into(holder.imgSend);
//                else
//                    Picasso.with(con).load(R.drawable.ic_no_dp).resize((mWidth / 6), (mWidth / 6)).centerCrop()
//                            .transform(new CircleTransform()).into(holder.imgSend);

//                if (!mData.get(mKeys.get(position - 1)).is_header()) {
//                    if (mData.get(mKeys.get(position - 1)).getSender_id().equals(util.getString("user_id", ""))) {
//                        holder.imgSend.setVisibility(View.INVISIBLE);
//                    } else {
//                        holder.imgSend.setVisibility(View.VISIBLE);
//                    }
//                } else {
//                    holder.imgSend.setVisibility(View.VISIBLE);
//                }

                if (mData.get(mKeys.get(position)).getMessage_status() == Constants.SEND)
                    holder.imgMsgStatus.setImageResource(R.mipmap.ic_sent);
                else if (mData.get(mKeys.get(position)).getMessage_status() == Constants.DELEIVERED)
                    holder.imgMsgStatus.setImageResource(R.mipmap.ic_delivered);
                else if (mData.get(mKeys.get(position)).getMessage_status() == Constants.READ)
                    holder.imgMsgStatus.setImageResource(R.mipmap.ic_seen);
            } else {
                /// receiver
                if (mChatActivity.mSelectedPosition == position) {
                    holder.llReceive.setBackgroundColor(con.getResources().getColor(R.color.light_grey));
                } else {
                    holder.llReceive.setBackgroundColor(con.getResources().getColor(R.color.white_color));
                }

                holder.llReceive.setVisibility(View.VISIBLE);
                holder.llSend.setVisibility(View.GONE);

                if (emojiCounter == 1 && !containsOtherText) {
                    holder.txtReceive.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.09));
                } else if (emojiCounter == 2 && !containsOtherText) {
                    holder.txtReceive.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.082));
                } else if (emojiCounter == 3 && !containsOtherText) {
                    holder.txtReceive.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.07));
                } else {
                    holder.txtReceive.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
                }

                if (flag == 0) {
                    holder.txtReceive.setText(mData.get(mKeys.get(position)).getMessage());
                    holder.txtReceive.setTextColor(ContextCompat.getColor(con, R.color.black_color));
                } else {
                    holder.txtReceive.setText(Html.fromHtml(show));
                    holder.txtReceive.setMovementMethod(LinkMovementMethod.getInstance());
                    holder.txtReceive.setTextColor(ContextCompat.getColor(con, R.color.facebook));
                }

//                holder.txtReceive.setText(mData.get(mKeys.get(position)).getMessage());

                holder.txtReceiveTime.setText(localFormat.format(calendar.getTime()));

//                if (!TextUtils.isEmpty(otherProfilePic))
//                    Picasso.with(con).load(otherProfilePic).resize((mWidth / 6), (mWidth / 6)).centerCrop()
//                            .transform(new CircleTransform()).placeholder(R.drawable.ic_no_dp).into(holder.imgReceive);
//                else
//                    Picasso.with(con).load(R.drawable.ic_no_dp).resize((mWidth / 6), (mWidth / 6)).centerCrop()
//                            .transform(new CircleTransform()).into(holder.imgReceive);

//                if (!mData.get(mKeys.get(position - 1)).is_header()) {
//                    if (mData.get(mKeys.get(position - 1)).getSender_id().equals(mOtherUserID)) {
//                        holder.imgReceive.setVisibility(View.INVISIBLE);
//                    } else {
//                        holder.imgReceive.setVisibility(View.VISIBLE);
//                    }
//                } else {
//                    holder.imgReceive.setVisibility(View.VISIBLE);
//                }
            }

            holder.llSend.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mChatActivity.visibleCopyLayout(mData.get(mKeys.get(position)).getMessage());
                    mChatActivity.mSelectedPosition = position;
                    holder.llSend.setBackgroundColor(con.getResources().getColor(R.color.light_grey));
                    notifyDataSetChanged();
                    return false;
                }
            });
            holder.llReceive.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mChatActivity.visibleCopyLayout(mData.get(mKeys.get(position)).getMessage());
                    mChatActivity.mSelectedPosition = position;
                    holder.llReceive.setBackgroundColor(con.getResources().getColor(R.color.light_grey));
                    notifyDataSetChanged();
                    return false;
                }
            });
        }
        return convertView;
    }


    public class ViewHolder {
        LinearLayout llSend, llReceive, llReceiveInside, llSendInside, llSendTime;
        ImageView imgReceive, imgSend, imgMsgStatus;
        TextView txtTime, txtReceive, txtReceiveTime, txtSend, txtSendTime;
    }

}
