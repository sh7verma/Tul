package adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.tul.ChatActivity;
import com.app.tul.ConversationActivity;
import com.app.tul.R;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;

import api.RetrofitClient;
import customviews.CircleTransform;
import database.Db;
import model.ChatDialogModel;
import model.ChatMessageModel;
import model.CheckChatModel;
import retrofit2.Call;
import retrofit2.Response;
import utils.Connection_Detector;
import utils.Constants;
import utils.CustomLoadingDialog;
import utils.Utils;

/**
 * Created by dev on 18/12/17.
 */

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ViewHolder> {

    HashMap<String, ChatDialogModel> mConversationMap;
    ArrayList<String> mConversationKeys;
    SimpleDateFormat localFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
    SimpleDateFormat show_format = new SimpleDateFormat("hh:mm aa");
    SimpleDateFormat show_Date_format = new SimpleDateFormat("dd-MM-yyyy");
    Db db;
    private Context con;
    private Utils utils;
    private int mWidth, mHeight;
    private LinkedHashMap<String, ChatMessageModel> mMessageMap = new LinkedHashMap<>();
    private ArrayList<String> mMessageKeys = new ArrayList<>();

    public ConversationAdapter(Context con, HashMap<String, ChatDialogModel> conversationMap, ArrayList<String> conversationKeys) {
        this.con = con;
        utils = new Utils(con);
        mWidth = utils.getInt("width", 0);
        mHeight = utils.getInt("height", 0);
        db = new Db(con);
        mConversationMap = conversationMap;
        mConversationKeys = conversationKeys;
    }

    public static String getDayFromDateString(String stringDate, String dateTimeFormat) {

        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat(dateTimeFormat);
        try {
            date = formatter.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int reslut = calendar.get(Calendar.DAY_OF_WEEK);
        String day = "";
        switch (reslut) {
            case Calendar.MONDAY:
                day = "MONDAY";
                break;
            case Calendar.TUESDAY:
                day = "TUESDAY";
                break;
            case Calendar.WEDNESDAY:
                day = "WEDNESDAY";
                break;
            case Calendar.THURSDAY:
                day = "THURSDAY";
                break;
            case Calendar.FRIDAY:
                day = "FRIDAY";
                break;
            case Calendar.SATURDAY:
                day = "SATURDAY";
                break;
            case Calendar.SUNDAY:
                day = "SUNDAY";
                break;
        }

        return day;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_conversation, parent, false);
        ViewHolder vhItem = new ViewHolder(v);
        return vhItem;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final ChatDialogModel chatDialogModel = mConversationMap.get(mConversationKeys.get(position));

        String particpantIdArray[] = chatDialogModel.getParticipant_ids().split(",");
        String particpantId = null;
        String myId = null;
        for (int i = 0; i < particpantIdArray.length; i++) {
            if (!particpantIdArray[i].equals(utils.getString("user_id", ""))) {
                particpantId = particpantIdArray[i];
            }
        }
        final String mOtherUserId = particpantId;
        holder.txtName.setText(chatDialogModel.getName().get(particpantId));

        if (Long.parseLong(chatDialogModel.getLast_message_time()) <= Long.parseLong(chatDialogModel.getDelete_dialog_time().get(utils.getString("user_id", "")))) {
            holder.txtLastMessage.setText("");
            holder.txtUnreadCount.setVisibility(View.INVISIBLE);
            holder.txtTime.setText("");
        }

        if (chatDialogModel.getBlock_status().get(utils.getString("user_id", "")).equals("1")) {

            mMessageMap = db.getAllMessages(chatDialogModel.getChat_dialog_id(), chatDialogModel.getDelete_dialog_time().get(utils.getString("user_id", "")));

            for (String messageIds : mMessageMap.keySet()) {
                mMessageKeys.add(messageIds);
            }
            if (mMessageKeys.size() > 0) {

                holder.txtLastMessage.setText(mMessageMap.get(mMessageKeys.get(mMessageKeys.size() - 1)).getMessage());

                if (!TextUtils.isEmpty(mMessageMap.get(mMessageKeys.get(mMessageKeys.size() - 1)).getMessage_time())) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(Constants.getLocalTime(Long.parseLong(mMessageMap.get(mMessageKeys.get(mMessageKeys.size() - 1)).getMessage_time())));

                    Calendar calendarDialog = Calendar.getInstance();
                    calendarDialog.setTimeInMillis(Constants.getLocalTime(Long.parseLong(chatDialogModel.getDelete_dialog_time().get(utils.getString("user_id", "")))));

                    if (calendar.getTime().after(calendarDialog.getTime())) {
                        holder.txtLastMessage.setVisibility(View.VISIBLE);
                    } else {
                        holder.txtLastMessage.setVisibility(View.INVISIBLE);
                    }

                    holder.txtTime.setText(time(localFormat.format(calendar.getTime())));
                }
            }
        } else {

            holder.txtLastMessage.setText(chatDialogModel.getLast_message());

            if (!TextUtils.isEmpty(chatDialogModel.getLast_message_time())) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(Constants.getLocalTime(Long.parseLong(chatDialogModel.getLast_message_time())));

                Calendar calendarDialog = Calendar.getInstance();
                calendarDialog.setTimeInMillis(Constants.getLocalTime(Long.parseLong(chatDialogModel.getDelete_dialog_time().get(utils.getString("user_id", "")))));

                if (calendar.getTime().after(calendarDialog.getTime())) {
                    holder.txtLastMessage.setVisibility(View.VISIBLE);
                } else {
                    holder.txtLastMessage.setVisibility(View.INVISIBLE);
                }
                holder.txtTime.setText(time(localFormat.format(calendar.getTime())));
            }
        }

        if (chatDialogModel.getUnread_count().get(utils.getString("user_id", "")).equals("0")) {
            holder.txtUnreadCount.setVisibility(View.INVISIBLE);
        } else {
            holder.txtUnreadCount.setVisibility(View.VISIBLE);
            holder.txtUnreadCount.setText(chatDialogModel.getUnread_count().get(utils.getString("user_id", "")));
        }


        if (mConversationMap.get(mConversationKeys.get(position)).getProfile_pic().get(particpantId) != null) {
            if ((mConversationMap.get(mConversationKeys.get(position)).getProfile_pic().get(particpantId).length() > 0)) {

                Picasso.with(con).load(mConversationMap.get(mConversationKeys.get(position)).getProfile_pic().get(particpantId))
                        .resize(mWidth / 8, mWidth / 8)
                        .centerCrop().placeholder(R.mipmap.ic_small_ph_tul)
                        .transform(new CircleTransform())
                        .into(holder.imgProfilePic);
            } else {
                Picasso.with(con).load(R.mipmap.ic_small_ph_tul)
                        .resize(mWidth / 8, mWidth / 8)
                        .centerCrop()
                        .transform(new CircleTransform())
                        .into(holder.imgProfilePic);
            }
        } else {
            Picasso.with(con).load(R.mipmap.ic_small_ph_tul)
                    .resize(mWidth / 8, mWidth / 8)
                    .centerCrop()
                    .transform(new CircleTransform())
                    .into(holder.imgProfilePic);
        }


        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((new Connection_Detector(con)).isConnectingToInternet()) {
                    CustomLoadingDialog.getLoader().showLoader(con);
                    isChatDisabled(position, mOtherUserId);
                } else {
                    Toast.makeText(con, R.string.internet_error, Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.llMain.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ((ConversationActivity) con).deleteConversation(mConversationMap.get(mConversationKeys.get(position)).getChat_dialog_id());
                return false;
            }
        });

    }

    void isChatDisabled(final int position, final String otherUserId) {
        Call<CheckChatModel> call = RetrofitClient.getInstance().checkChatInList(utils.getString("access_token", ""), String.valueOf(otherUserId));
        call.enqueue(new retrofit2.Callback<CheckChatModel>() {
            @Override
            public void onResponse(Call<CheckChatModel> call, Response<CheckChatModel> response) {
                CustomLoadingDialog.getLoader().dismissLoader();
                if (response != null) {
                    if (response.body().getResponse().getIs_chat_block() == 0) {
                        Intent inChat = new Intent(con, ChatActivity.class);

                        inChat.putExtra("dialog_id", mConversationMap.get(mConversationKeys.get(position)).getChat_dialog_id());
                        inChat.putExtra("name", mConversationMap.get(mConversationKeys.get(position)).getName().get(otherUserId));

                        inChat.putExtra("other_block_status", mConversationMap.get(mConversationKeys.get(position)).getBlock_status().get(otherUserId));
                        inChat.putExtra("my_block_status", mConversationMap.get(mConversationKeys.get(position)).getBlock_status().get(utils.getString("user_id", "")));

                        con.startActivity(inChat);
                        ((Activity) con).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                    } else {
                        if (response.body().getResponse().getUser_block_status() == 1) {
                            // I am Blocked
                            Toast.makeText(con, R.string.contact_admin_user, Toast.LENGTH_SHORT).show();
                        } else if (response.body().getResponse().getAdmin_pause_status() == 1) {
                            //Place Blocked
                            Toast.makeText(con, R.string.place_is_blocked, Toast.LENGTH_SHORT).show();
                        } else if (response.body().getResponse().getOwner_block_status() == 1) {
                            //Other is Blocked
                            Toast.makeText(con, R.string.owner_is_blocked, Toast.LENGTH_SHORT).show();
                        }
//                        userBlockDialogUser(mContext);
                    }
                } else {
                    Toast.makeText(con, response.body().error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CheckChatModel> call, Throwable t) {
                CustomLoadingDialog.getLoader().dismissLoader();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mConversationMap.size();
    }

    public String time(String time) {
        try {
            Log.e("time", time);
            Calendar c = Calendar.getInstance();

            Calendar c1 = Calendar.getInstance(); // today
            c1.add(Calendar.DAY_OF_YEAR, -1);

            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
            Calendar c2 = Calendar.getInstance();
            c2.setTime(date);

            if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                    && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)) {
                //yesterday

                time = "Yesterday, " + show_format.format(date);

            } else if (c.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                    && c.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)) {
                //today
                time = "Today, " + show_format.format(date);
            } else {
                time = formateddate(time) + ", " + show_format.format(date);
            }
            return time;
        } catch (Exception e) {
            Log.e("Exce = ", e + "");
            return "";
        }
    }

    public String formateddate(String date) {
        Log.e("1", date);
        DateTime dateTime = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(date);
        DateTime today = new DateTime();
        DateTime yesterday = today.minusDays(1);
        DateTime twodaysago = today.minusDays(2);
        DateTime threedaysago = today.minusDays(3);
        DateTime fourdaysago = today.minusDays(4);
        DateTime fivedaysago = today.minusDays(5);
        DateTime sixdaysago = today.minusDays(6);
        DateTime seveendaysago = today.minusDays(7);

        Log.e("3", dateTime.toLocalDate() + "");

        if (dateTime.toLocalDate().equals(today.toLocalDate())) {

            return "Today ";
        } else if (dateTime.toLocalDate().equals(yesterday.toLocalDate())) {


            return "Yesterday ";
        } else if (dateTime.toLocalDate().equals(twodaysago.toLocalDate())) {
            String str1 = getDayFromDateString(date, "yyyy-MM-dd HH:mm:ss");
            Log.e("str1", str1);
            return str1;
        } else if (dateTime.toLocalDate().equals(threedaysago.toLocalDate())) {
            String str1 = getDayFromDateString(date, "yyyy-MM-dd HH:mm:ss");
            Log.e("str1", str1);
            return str1;
        } else if (dateTime.toLocalDate().equals(fourdaysago.toLocalDate())) {
            String str1 = getDayFromDateString(date, "yyyy-MM-dd HH:mm:ss");
            Log.e("str1", str1);
            return str1;
        } else if (dateTime.toLocalDate().equals(fivedaysago.toLocalDate())) {
            String str1 = getDayFromDateString(date, "yyyy-MM-dd HH:mm:ss");
            Log.e("str1", str1);
            return str1;
        } else if (dateTime.toLocalDate().equals(sixdaysago.toLocalDate())) {
            String str1 = getDayFromDateString(date, "yyyy-MM-dd HH:mm:ss");
            Log.e("str1", str1);
            return str1;
        } else if (dateTime.toLocalDate().equals(seveendaysago.toLocalDate())) {
            String str1 = getDayFromDateString(date, "yyyy-MM-dd HH:mm:ss");
            Log.e("str1", str1);
            return str1;
        } else {
            Date date1 = null;
            try {
                date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String str1 = show_Date_format.format(date1);
            return str1;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgProfilePic, imgTick;
        TextView txtLastMessage, txtTime, txtName, txtUnreadCount;
        LinearLayout llMain;

        public ViewHolder(View itemView) {
            super(itemView);

            llMain = (LinearLayout) itemView.findViewById(R.id.ll_main);

            txtName = (TextView) itemView.findViewById(R.id.txt_user_name);
            txtName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));

            txtTime = (TextView) itemView.findViewById(R.id.txt_time);
            txtTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

            txtLastMessage = (TextView) itemView.findViewById(R.id.txt_last_message);
            txtLastMessage.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

            txtUnreadCount = (TextView) itemView.findViewById(R.id.txt_unread_count);
            txtUnreadCount.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.035));

            imgProfilePic = (ImageView) itemView.findViewById(R.id.img_profile);
            imgTick = (ImageView) itemView.findViewById(R.id.img_tick);

        }
    }

}
