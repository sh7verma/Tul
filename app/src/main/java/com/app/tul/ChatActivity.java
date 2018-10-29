package com.app.tul;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import adapters.ChatAdapter;
import api.RetrofitClient;
import butterknife.BindView;
import customviews.CircleTransform;
import database.Db;
import interfaces.MessageListener;
import interfaces.ProfileListener;
import interfaces.RefreshDialogListener;
import interfaces.UnreadCountListener;
import model.ChatDialogModel;
import model.ChatMessageModel;
import model.ChatNotificationModel;
import model.ChatProfileModel;
import model.CheckChatModel;
import retrofit2.Call;
import retrofit2.Response;
import utils.Connection_Detector;
import utils.Constants;
import utils.CustomLoadingDialog;
import utils.FirebaseListeners;

/**
 * Created by system009 on 18/12/17.
 */
public class ChatActivity extends BaseActivity implements MessageListener, UnreadCountListener, ProfileListener,
        RefreshDialogListener {

    private static final long DELAY_TIME = 800;
    private final int OPTION = 1;
    private final int CLEAR_CHAT = 2;
    private final int UNMATCH = 3;
    public int mSelectedPosition = -1;
    public int mUnReadCount;
    public int mBadgeCount;
    public int mBadgeCountPush;
    public String mOnlineStatus;
    public int mOtherBlockStatus;
    public int mMyBlockStatus;
    @BindView(R.id.ll_top)
    LinearLayout llTop;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_profile)
    ImageView imgProfile;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.img_options)
    ImageView imgOptions;
    @BindView(R.id.rl_copy)
    RelativeLayout rlCopy;
    @BindView(R.id.txt_hide)
    TextView txtHide;
    @BindView(R.id.txt_copy)
    TextView txtCopy;
    TextView txtMatchTime;
    @BindView(R.id.lv_chat)
    ListView lvChat;
    @BindView(R.id.ll_outer_send)
    LinearLayout llOuterSend;
    @BindView(R.id.ed_message)
    EditText edMessage;
    @BindView(R.id.ll_send)
    LinearLayout llSend;
    @BindView(R.id.txt_time_header)
    TextView txtTimeHeader;
    Db db;
    DatabaseReference mChatRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mDialogRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mMessageRefrence = FirebaseDatabase.getInstance().getReference();
    SimpleDateFormat chat_date_format = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    SimpleDateFormat today_date_format = new SimpleDateFormat("hh:mm aa", Locale.US);
    SimpleDateFormat show_dateheader_format = new SimpleDateFormat("dd MMM yyyy", Locale.US);
    SimpleDateFormat localFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
    private ChatAdapter mChatAdapter;
    private ChatDialogModel mChatDialogModel;
    private LinkedHashMap<String, ChatMessageModel> mMessageMap = new LinkedHashMap<>();
    private ArrayList<String> mMessageKeys = new ArrayList<>();
    private String mOtherProfilePic, mOtherUserID, mOtherPlatformStatus, mOtherPushToken, mOtherPushStatus;
    private ChatActivity mChatActivity = null;
    private ChildEventListener mMessageListener;
    private String mCopyContent;
    private String mLocalDialogId;

    public static String getDate(long milliSeconds, String dateFormat) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);

        Calendar c1 = Calendar.getInstance(); // today
        c1.add(Calendar.DAY_OF_YEAR, -1); // yesterday

        Calendar c2 = Calendar.getInstance();
        c2.setTime(new Date(milliSeconds)); // your date

        Calendar c3 = Calendar.getInstance();

        String a;
        if ((c3.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) && (c3.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR))) {

            a = "Today";
        } else if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)) {
            a = "Yesterday";
        } else {

            a = formatter.format(calendar.getTime());
        }
        return a;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_chat;
    }

    @Override
    protected void onStart() {
        super.onStart();
//      MainApplication.getInstance().trackScreenView(getString(R.string.chat_page));
    }

    @Override
    protected void initUI() {

        imgBack.setPadding(0, mWidth / 24, 0, mWidth / 24);

        imgOptions.setPadding(0, mWidth / 24, 0, mWidth / 24);

        txtHide.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));
        txtHide.setPadding(mWidth / 24, mWidth / 24, mWidth / 24, mWidth / 24);

        txtCopy.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));
        txtCopy.setPadding(mWidth / 24, mWidth / 24, mWidth / 24, mWidth / 24);

        LinearLayout.LayoutParams imgParms = new LinearLayout.LayoutParams(mWidth / 10, mWidth / 10);
        imgParms.setMargins(0, mWidth / 64, 0, mWidth / 64);
        imgProfile.setLayoutParams(imgParms);

        txtName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        txtName.setPadding(mWidth / 32, mWidth / 64, 0, mWidth / 64);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/regular.ttf");
        edMessage.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        edMessage.setPadding(mWidth / 32, mWidth / 24, mWidth / 32, mWidth / 24);
        edMessage.setTypeface(typeface);

        llSend.setPadding(mWidth / 32, 0, mWidth / 32, 0);

        txtTimeHeader.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));

        txtTimeHeader.setPadding(mWidth / 32, mWidth / 64, mWidth / 32, mWidth / 64);

        llSend.setEnabled(false);
        llSend.setAlpha(.6f);

        edMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    llSend.setEnabled(true);
                    llSend.setAlpha(1f);
                } else {
                    llSend.setEnabled(false);
                    llSend.setAlpha(.6f);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edMessage.setFilters(new InputFilter[]{new InputFilter() {
            public CharSequence filter(CharSequence src, int start, int end, Spanned dst, int dstart, int dend) {
                if (!src.toString().matches("[0-9]")) {
                    return src;
                } else {
                    src = "";
                }
                return src;
            }
        }});


        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                txtTimeHeader.animate().alpha(0f).setDuration(200);
            }
        };

        lvChat.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                Log.e("dialog_id = ", "scrollState = " + scrollState);
                if (scrollState == 1 || scrollState == 2) {
                    txtTimeHeader.animate().alpha(1f).setDuration(200);
                } else {

                    handler.removeCallbacks(runnable);
                    handler.postDelayed(runnable, DELAY_TIME);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

//                mMessageMap
                Log.e("dialog_id = ", "firstVisibleItem = " + firstVisibleItem + " visibleItemCount = " + visibleItemCount
                        + " totalItemCount = " + totalItemCount);
                if (mMessageMap.size() == 0 && mMessageMap.size() <= visibleItemCount) {
                    txtTimeHeader.setVisibility(View.INVISIBLE);
                } else {
                    txtTimeHeader.setVisibility(View.VISIBLE);
                }

                if (mMessageMap != null && mMessageKeys != null && mMessageMap.size() > 0 && mMessageKeys.size() > 0 &&
                        mMessageMap.get(mMessageKeys.get(firstVisibleItem)).getMessage_time() != null) {

                    String date = getDate(Long.parseLong(mMessageMap.get(mMessageKeys.get(firstVisibleItem)).getMessage_time())
                            , "dd MMM yyyy");
                    Log.e("dialog_id = ", "date = " + date);
                    txtTimeHeader.setText(date);
                    handler.removeCallbacks(runnable);
                    handler.postDelayed(runnable, DELAY_TIME);
                }
            }
        });
        try {
            mOtherBlockStatus = Integer.parseInt(getIntent().getStringExtra("other_block_status"));
            mMyBlockStatus = Integer.parseInt(getIntent().getStringExtra("my_block_status"));
        } catch (Exception e) {
            mOtherBlockStatus = 0;
            mMyBlockStatus = 0;
        }

    }

    void isChatDisabled(final String otherUserId) {
        CustomLoadingDialog.getLoader().showLoader(mContext);
        Call<CheckChatModel> call = RetrofitClient.getInstance().checkChatInList(utils.getString("access_token", ""), String.valueOf(otherUserId));
        call.enqueue(new retrofit2.Callback<CheckChatModel>() {
            @Override
            public void onResponse(Call<CheckChatModel> call, Response<CheckChatModel> response) {
                CustomLoadingDialog.getLoader().dismissLoader();
                if (response != null) {
                    if (response.body().getResponse().getIs_chat_block() == 1) {
                        if (response.body().getResponse().getUser_block_status() == 1) {
                            // I am Blocked
                            Toast.makeText(mContext, R.string.contact_admin_user, Toast.LENGTH_SHORT).show();
                        } else if (response.body().getResponse().getAdmin_pause_status() == 1) {
                            //Place Blocked
                            Toast.makeText(mContext, R.string.place_is_blocked, Toast.LENGTH_SHORT).show();
                        } else if (response.body().getResponse().getOwner_block_status() == 1) {
                            //Other is Blocked
                            Toast.makeText(mContext, R.string.owner_is_blocked, Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }
                } else {
                    Toast.makeText(mContext, R.string.something_was_wrong, Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<CheckChatModel> call, Throwable t) {
                CustomLoadingDialog.getLoader().dismissLoader();
                Toast.makeText(mContext, R.string.something_was_wrong, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Log.e("dialog_id = ", intent.getStringExtra("dialog_id"));

        mLocalDialogId = intent.getStringExtra("dialog_id");
        String name = intent.getStringExtra("name");

        ArrayList<String> allDialogs = db.getDialog("");
        if (allDialogs.contains(intent.getStringExtra("dialog_id"))) {
            Map<String, ChatDialogModel> tempMap = db.getConversationById(intent.getStringExtra("dialog_id"));
            mChatDialogModel = tempMap.get(intent.getStringExtra("dialog_id"));
            setProfileData();
            setChatData();
        } else {
            if (LandingActivity.landingActivity != null) {
                finish();
            } else {
                Intent inStarted = new Intent(this, LandingActivity.class);
                startActivity(inStarted);
                finish();
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
            }
//            Toast.makeText(mChatActivity, "You have been unmatched", Toast.LENGTH_LONG).show();
        }
    }

    private void setProfileData() {
        String particpantIdArray[] = mChatDialogModel.getParticipant_ids().split(",");
        String particpantId = null;
        for (int i = 0; i < particpantIdArray.length; i++) {

            if (!particpantIdArray[i].equals(utils.getString("user_id", "")))
                particpantId = particpantIdArray[i];
        }
        txtName.setText(mChatDialogModel.getName().get(particpantId));
        mOtherProfilePic = mChatDialogModel.getProfile_pic().get(particpantId);
        mOtherUserID = particpantId;

        /// Start listening other profile
        FirebaseListeners.getInstance().startOtherProfileListener(mOtherUserID);

        if (!TextUtils.isEmpty(mChatDialogModel.getProfile_pic().get(particpantId)))
            Picasso.with(mContext).load(mChatDialogModel.getProfile_pic().get(particpantId)).resize((mWidth / 7), (mWidth / 7)).centerCrop()
                    .transform(new CircleTransform()).placeholder(R.mipmap.ic_no_image).into(imgProfile);
        else
            Picasso.with(mContext).load(R.mipmap.ic_no_image).resize((mWidth / 7), (mWidth / 7)).centerCrop()
                    .transform(new CircleTransform()).into(imgProfile);
    }

    private void setChatData() {
        startMessageListener();
        ///set unread count to 0
        FirebaseDatabase.getInstance().getReference().child(Constants.CHAT_ENDPOINT).child(mChatDialogModel.getChat_dialog_id())
                .child("unread_count").child(utils.getString("user_id", "")).setValue("0");

        ///set badge count to 0
        FirebaseDatabase.getInstance().getReference().child(Constants.USER_ENDPOINT)
                .child(utils.getString("user_id", "")).child("badge_count").setValue("0");

        mMessageMap.clear();
        mMessageKeys.clear();

        mMessageMap = db.getAllMessages(mChatDialogModel.getChat_dialog_id(), mChatDialogModel.getDelete_dialog_time().get(utils.getString("user_id", "")));

        for (String messageIds : mMessageMap.keySet()) {
            mMessageKeys.add(messageIds);
            if (!mMessageMap.get(messageIds).is_header()) {
                if ((!mMessageMap.get(messageIds).getSender_id().equals(utils.getString("user_id", ""))) &&
                        (mMessageMap.get(messageIds).getMessage_status() != Constants.READ)) {
                    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
                    mRef.child(Constants.MESSAGES_ENDPOINT).child(mChatDialogModel.getChat_dialog_id())
                            .child(messageIds).child("message_status").setValue(Constants.READ);
                }
            }
        }
//        showHeader();
        mChatAdapter = new ChatAdapter(mContext, mMessageKeys, mMessageMap, mOtherProfilePic, mChatActivity, mOtherUserID);
        lvChat.setAdapter(mChatAdapter);
        lvChat.setSelection(mMessageKeys.size() - 1);

        lvChat.post(new Runnable() {
            public void run() {
                lvChat.setSelection(lvChat.getCount() - 1);
            }
        });
    }

    /*void showHeader() {
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.header_new_match, lvChat, false);
        if (lvChat.getHeaderViewsCount() > 0)
            lvChat.removeHeaderView(header);
        txtMatchTime = (TextView) header.findViewById(R.id.txt_match_time);
        txtMatchTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));
        txtMatchTime.setPadding(0, mWidth / 64, 0, mWidth / 64);
        try {
            Calendar calendarDialog = Calendar.getInstance();
            calendarDialog.setTimeInMillis(Constants.getLocalTime(Long.parseLong(mChatDialogModel.getDialog_created_time())));
            txtMatchTime.setText("You Matched with " + mChatDialogModel.getName().get(mOtherUserID) + " on " + localFormat.format(calendarDialog.getTime()));
        } catch (Exception e) {
            Log.e("Exce = ", e + "");
        }
        lvChat.addHeaderView(header, null, false);
    }*/

    @Override
    protected void onCreateStuff() {
        mChatActivity = this;
        db = new Db(this);
//        txtTimeHeader.setVisibility(View.INVISIBLE);


        ArrayList<String> allDialogs = db.getDialog("");
        String name = getIntent().getStringExtra("name");
        mLocalDialogId = getIntent().getStringExtra("dialog_id");
        if (allDialogs.contains(getIntent().getStringExtra("dialog_id"))) {
            Map<String, ChatDialogModel> tempMap = db.getConversationById(getIntent().getStringExtra("dialog_id"));
            mChatDialogModel = tempMap.get(getIntent().getStringExtra("dialog_id"));
            setProfileData();
            setChatData();
            FirebaseListeners.getInstance().intializeUnreadListener(this);
            FirebaseListeners.getInstance().intializeOtherProfileListener(this);
            FirebaseListeners.getInstance().intializeRefreshListener(this);
        } else {
//            Toast.makeText(mChatActivity, "You have been unmatched", Toast.LENGTH_LONG).show();
            if (LandingActivity.landingActivity != null) {
                finish();
            } else {
                Intent inStarted = new Intent(mContext, LandingActivity.class);
                startActivity(inStarted);
                finish();
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
            }
        }

        if (getIntent().hasExtra("from_push")) {
            isChatDisabled(mOtherUserID);
        }
//        mChatDialogModel = getIntent().getParcelableExtra("chatModel");
    }

    @Override
    protected void initListener() {
        imgBack.setOnClickListener(this);
        imgOptions.setOnClickListener(this);
        llSend.setOnClickListener(this);
        txtHide.setOnClickListener(this);
        txtCopy.setOnClickListener(this);
        imgProfile.setOnClickListener(this);
        txtName.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return ChatActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_profile:

            case R.id.txt_name:
                if ((new Connection_Detector(mContext)).isConnectingToInternet()) {
                    Constants.PROFILE_ID = Integer.parseInt(mOtherUserID);
                    Constants.CHAT_PROFILE_ID = Integer.parseInt(mOtherUserID);
                    Intent intent = new Intent(mContext, OtherUserProfileActivity.class);
                    intent.putExtra("other_user_id", Integer.parseInt(mOtherUserID));
                    intent.putExtra("other_user_name", mChatDialogModel.getName().get(mOtherUserID));
                    intent.putExtra("other_user_pic", mChatDialogModel.getProfile_pic().get(mOtherUserID));
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                } else {
                    Toast.makeText(mContext, R.string.internet, Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.txt_hide:
                rlCopy.setVisibility(View.GONE);
                mSelectedPosition = -1;
                if (mChatAdapter != null)
                    mChatAdapter.notifyDataSetChanged();
                break;
            case R.id.txt_copy:
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("simple text", mCopyContent);
                clipboard.setPrimaryClip(clip);
                rlCopy.setVisibility(View.GONE);
                mSelectedPosition = -1;
                if (mChatAdapter != null)
                    mChatAdapter.notifyDataSetChanged();
                break;
            case R.id.ll_send:
                if ((new Connection_Detector(mContext)).isConnectingToInternet()) {
                    if (mOtherBlockStatus == 0) {

                        String finalMessage = edMessage.getText().toString().trim();
                        if (finalMessage.length() < 2000) {
                            if (finalMessage.length() > 0) {


                                String[] tempMessage = finalMessage.split(" ");
                                String newMessage = "";
                                for (int i = 0; i < tempMessage.length; i++) {
                                    if (tempMessage[i].toLowerCase().equals("facebook")) {
                                        newMessage = newMessage + "******** ";
                                    } else if (tempMessage[i].toLowerCase().equals("messanger")) {
                                        newMessage = newMessage + "********* ";
                                    } else if (tempMessage[i].toLowerCase().equals("whatsapp")) {
                                        newMessage = newMessage + "******** ";
                                    } else {
                                        newMessage = newMessage + tempMessage[i] + " ";
                                    }
                                }

                                String message = newMessage.substring(0, newMessage.length() - 1);

                                if (message.contains("FACEBOOK")) {
                                    message = message.replace("FACEBOOK", "********");
                                }
                                if (message.contains("facebook")) {
                                    message = message.replace("facebook", "********");
                                }
                                if (message.contains("Facebook")) {
                                    message = message.replace("Facebook", "********");
                                }
                                if (message.contains("MESSENGER")) {
                                    message = message.replace("MESSENGER", "*********");
                                }
                                if (message.contains("messenger")) {
                                    message = message.replace("messenger", "*********");
                                }
                                if (message.contains("Messenger")) {
                                    message = message.replace("Messenger", "*********");
                                }
                                if (message.contains("WHATSAPP")) {
                                    message = message.replace("WHATSAPP", "********");
                                }
                                if (message.contains("whatsapp")) {
                                    message = message.replace("whatsapp", "********");
                                }
                                if (message.contains("Whatsapp")) {
                                    message = message.replace("Whatsapp", "********");
                                }

                                if (message.contains("0")) {
                                    message = message.replace("0", "*");
                                }
                                if (message.contains("1")) {
                                    message = message.replace("1", "*");
                                }
                                if (message.contains("2")) {
                                    message = message.replace("2", "*");
                                }
                                if (message.contains("3")) {
                                    message = message.replace("3", "*");
                                }
                                if (message.contains("4")) {
                                    message = message.replace("4", "*");
                                }
                                if (message.contains("5")) {
                                    message = message.replace("5", "*");
                                }
                                if (message.contains("6")) {
                                    message = message.replace("6", "*");
                                }
                                if (message.contains("7")) {
                                    message = message.replace("7", "*");
                                }
                                if (message.contains("8")) {
                                    message = message.replace("8", "*");
                                }
                                if (message.contains("9")) {
                                    message = message.replace("9", "*");
                                }
                                sendMessage(message);
                                edMessage.setText("");
                            }
                        } else {
                            Toast.makeText(ChatActivity.this, R.string.maxx_limit_2000, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        unBlockDialog();
                    }

                } else {
                    showAlert(llSend, errorInternet);
                }
                break;
            case R.id.img_back:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(imgBack.getWindowToken(), 0);
                if (LandingActivity.landingActivity != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        finishAfterTransition();
                    else
                        finish();
                } else {
                    Intent inStarted = new Intent(mContext, LandingActivity.class);
                    startActivity(inStarted);
                    finish();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                }
                break;
            case R.id.img_options:
                optionDialog();

//                Intent inOptions = new Intent(mContext, ChatOptionsActivity.class);
//                inOptions.putExtra("user_id", mOtherUserID);
//                inOptions.putExtra("user_pic", mOtherProfilePic);
//                inOptions.putExtra("mute_status", mChatDialogModel.getMute().get(utils.getString("user_id", "")));
//                startActivityForResult(inOptions, OPTION);

                break;
        }

    }

    private void sendMessage(String message) {
        String key = mChatRef.child(Constants.MESSAGES_ENDPOINT).child(mChatDialogModel.getChat_dialog_id()).push().getKey();

        ChatMessageModel chatMessageModel = new ChatMessageModel();
        chatMessageModel.setMessage(message);
        chatMessageModel.setMessage_status(Constants.SEND);
        chatMessageModel.setMessage_time(String.valueOf(Constants.getUtcTime(System.currentTimeMillis())));
        chatMessageModel.setSender_id(utils.getString("user_id", ""));
        chatMessageModel.setChat_dialog_id(mChatDialogModel.getChat_dialog_id());
        chatMessageModel.setMessage_id(key);

        db.addMessage(chatMessageModel);
        createHeader(chatMessageModel);

        if (mOtherBlockStatus == 0 && mMyBlockStatus == 0) {
            /// add meesage to message table
            mChatRef.child(Constants.MESSAGES_ENDPOINT).child(mChatDialogModel.getChat_dialog_id()).child(key).setValue(chatMessageModel);

            /// update Dialog/Chat Table
            Map<String, Object> tempMap = new HashMap<>();
            tempMap.put("last_message", message);
            tempMap.put("last_message_id", key);
            tempMap.put("last_message_sender_id", utils.getString("user_id", ""));
            tempMap.put("last_message_time", String.valueOf(Constants.getUtcTime(System.currentTimeMillis())));

            //Delete Dialog status
            Map<String, String> deleteDialogMap = new HashMap<String, String>();
            deleteDialogMap.put(utils.getString("user_id", ""), "0");
            deleteDialogMap.put(mOtherUserID, "0");
            tempMap.put("delete_outer_dialog", deleteDialogMap);

            mDialogRef.child(Constants.CHAT_ENDPOINT).child(mChatDialogModel.getChat_dialog_id()).updateChildren(tempMap);

            /// update Dialog/Chat Unread Count
            mUnReadCount++;
            mDialogRef.child(Constants.CHAT_ENDPOINT).child(mChatDialogModel.getChat_dialog_id()).child("unread_count")
                    .child(mOtherUserID).setValue(String.valueOf(mUnReadCount));

            /// update other user profile badge Count
            if (mOnlineStatus != null) {
                if (!mOnlineStatus.equals(Constants.ONLINE)) {
                    mBadgeCount++;
                    FirebaseDatabase.getInstance().getReference().child(Constants.USER_ENDPOINT)
                            .child(mOtherUserID).child("badge_count").setValue(String.valueOf(mBadgeCount));
                }
            }
            /// adding value in notification table
            lvChat.setSelection(lvChat.getCount() - 1);
            sendNotification(message);
        }

    }

    private void sendNotification(String message) {
        if (mOnlineStatus != null && mOtherPushStatus.equals("1")) {
            DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
            String key = mRef.child(Constants.NOTIFICATION_ENDPOINT).push().getKey();
            ChatNotificationModel notiItem = new ChatNotificationModel();
            notiItem.setName(utils.getString("user_name", ""));
            notiItem.setMessage(message);
            notiItem.setBadge_count(String.valueOf(mBadgeCountPush + 1));
            notiItem.setUser_id(mOtherUserID);
            notiItem.setPush_token(mOtherPushToken);
            notiItem.setProfile_pic(utils.getString("profile_pic", ""));
            notiItem.setChat_dialog_id(mChatDialogModel.getChat_dialog_id());
            notiItem.setParticipant_ids(mChatDialogModel.getParticipant_ids());
            notiItem.setPlatform_status(mOtherPlatformStatus);
            notiItem.setMute(mChatDialogModel.getMute().get(mOtherUserID));
            notiItem.setMessages_notification(mOtherPushStatus);
            mRef.child(Constants.NOTIFICATION_ENDPOINT).child(key).setValue(notiItem);
        }
    }

    @Override
    public void onMessageAdded(ChatMessageModel chatMessageModel) {
        if (chatMessageModel.getChat_dialog_id().equals(mChatDialogModel.getChat_dialog_id())) {
            if (mOtherBlockStatus == 0) {
                if (!mMessageKeys.contains(chatMessageModel.getMessage_id())) {
                    FirebaseDatabase.getInstance().getReference().child(Constants.CHAT_ENDPOINT).child(mChatDialogModel.getChat_dialog_id())
                            .child("unread_count").child(utils.getString("user_id", "")).setValue("0");
                    FirebaseDatabase.getInstance().getReference().child(Constants.MESSAGES_ENDPOINT).child(mChatDialogModel.getChat_dialog_id())
                            .child(chatMessageModel.getMessage_id()).child("message_status").setValue(Constants.READ);
                    chatMessageModel.setMessage_status(Constants.READ);
                    mMessageKeys.add(chatMessageModel.getMessage_id());
                    createHeader(chatMessageModel);
                }
            }
        }
    }

    @Override
    public void onMessageChanged(ChatMessageModel chatMessageModel) {
        if (chatMessageModel.getChat_dialog_id().equals(mChatDialogModel.getChat_dialog_id())) {
            if (mOtherBlockStatus == 0) {
                db.addMessage(chatMessageModel);
                if (!mMessageKeys.contains(chatMessageModel.getMessage_id()))
                    mMessageKeys.add(chatMessageModel.getMessage_id());
                mMessageMap.put(chatMessageModel.getMessage_id(), chatMessageModel);
                if (mChatAdapter != null)
                    mChatAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onMessageDelete(ChatMessageModel chatMessageModel) {

    }

    @Override
    public void onRefreshDialog() {
        Map<String, ChatDialogModel> tempMap = db.getConversationById(mLocalDialogId);
        mChatDialogModel = tempMap.get(mLocalDialogId);
    }


    public void visibleCopyLayout(String message) {
        mCopyContent = message;
        rlCopy.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case OPTION:
                    if (data.getIntExtra("CLEAR_CHAT", 0) == CLEAR_CHAT) {
                        clearChat();
                    }

                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void clearChat() {
        mDialogRef.child(Constants.CHAT_ENDPOINT).child(mChatDialogModel.getChat_dialog_id()).child("delete_dialog_time")
                .child(utils.getString("user_id", "")).setValue(String.valueOf(Constants.getUtcTime(System.currentTimeMillis())));
        db.deleteAllMessages(mChatDialogModel.getChat_dialog_id());
        mMessageMap.clear();
        mMessageKeys.clear();
        if (mChatAdapter != null)
            mChatAdapter.notifyDataSetChanged();
    }

    private ChatMessageModel createHeader(ChatMessageModel message) {
        String lastMessageDate = "", newMessageDate = "";
//            long timeinMillis1 = Long.parseLong(message.message_time);
        Calendar calNew = Calendar.getInstance();
        try {
            long timeinMillis1 = Long.parseLong(message.getMessage_time());
            calNew.setTimeInMillis(timeinMillis1);
        } catch (Exception e) {
            e.printStackTrace();
            calNew.setTimeInMillis(calNew.getTimeInMillis());
        }
//            calNew.setTimeInMillis(timeinMillis1);
        newMessageDate = chat_date_format.format(calNew.getTime());

        if (mMessageKeys.contains(message.getMessage_id())) { // already
            message.setIs_header(false);
            message.setShow_message_datetime(today_date_format.format(calNew.getTime()));
            mMessageMap.put(message.getMessage_id(), message);
        } else {
            // set header if required
            long firstMessageTime = 0;
            if (mMessageKeys.size() > 0) {
                Calendar calDb = Calendar.getInstance();
                long timeinMillis;
                try {
                    timeinMillis = Long.parseLong(mMessageMap.get(mMessageKeys.get(mMessageKeys.size() - 1)).getMessage_time());
                    firstMessageTime = Long.parseLong(mMessageMap.get(mMessageKeys.get(mMessageKeys.size() - 1)).getMessage_time());
                    calDb.setTimeInMillis(timeinMillis);
                } catch (Exception e) {
                    e.printStackTrace();
                    calDb.setTimeInMillis(calDb.getTimeInMillis());
                }
                lastMessageDate = chat_date_format.format(calDb.getTime());
            }
            long newTime = Long.parseLong(message.getMessage_time());
            if (newTime >= firstMessageTime) {

                Calendar cal = Calendar.getInstance();
                String today = chat_date_format.format(cal.getTime());

                Calendar cal1 = Calendar.getInstance();
                cal1.add(Calendar.DATE, -1);
                String yesterday = chat_date_format.format(cal1.getTime());

                if (!TextUtils.equals(lastMessageDate, newMessageDate)) {
                    ChatMessageModel mMessage = new ChatMessageModel();
                    mMessage.setIs_header(true);

                    if (newMessageDate.equals(today)) {
                        mMessage.setShow_HeaderText("Today");
                        mMessage.setShow_message_datetime(today_date_format.format(calNew.getTime()));
                    } else if (newMessageDate.equals(yesterday)) {
                        mMessage.setShow_HeaderText("Yesterday");
                        mMessage.setShow_message_datetime(today_date_format.format(calNew.getTime()));
                    } else {
                        mMessage.setShow_HeaderText(show_dateheader_format.format(calNew.getTime()));
                        mMessage.setShow_message_datetime(today_date_format.format(calNew.getTime()));
                    }
                    mMessageMap.put(newMessageDate, mMessage);
                    mMessageKeys.add(newMessageDate);
                }
                message.setIs_header(false);
                message.setShow_message_datetime(today_date_format.format(calNew.getTime()));
                mMessageMap.put(message.getMessage_id(), message);
                mMessageKeys.add(message.getMessage_id());
            }
        }
        if (mChatAdapter != null)
            mChatAdapter.notifyDataSetChanged();
        return message;
    }

    @Override
    public void getUnreadCount(ChatDialogModel chatDialogModel) {
        if (chatDialogModel.getChat_dialog_id() != null) {
            if (chatDialogModel.getChat_dialog_id().equals(mChatDialogModel.getChat_dialog_id())) {
                mUnReadCount = Integer.parseInt(chatDialogModel.getUnread_count().get(mOtherUserID));
                mOtherBlockStatus = Integer.parseInt(chatDialogModel.getBlock_status().get(mOtherUserID));
                mMyBlockStatus = Integer.parseInt(chatDialogModel.getBlock_status().get(utils.getString("user_id", "")));
            }
        }
    }

    @Override
    public void onProfileChildChanged(ChatProfileModel chatProfileModel) {
        if (chatProfileModel.getUser_id().equals(mOtherUserID)) {
            mOnlineStatus = chatProfileModel.getOnline_status();
            mBadgeCount = Integer.parseInt(chatProfileModel.getBadge_count());
            mBadgeCountPush = Integer.parseInt(chatProfileModel.getBadge_count());
            mOtherPushToken = chatProfileModel.getPush_token();
            mOtherPlatformStatus = chatProfileModel.getPlatform_status();
            mOtherPushStatus = chatProfileModel.getMessages_notification();
        }
    }

    private void startMessageListener() {
        mMessageListener = mMessageRefrence.child(Constants.CHAT_ENDPOINT).orderByChild("chat_dialog_id").equalTo(mChatDialogModel.getChat_dialog_id()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                if (mChatActivity != null)
//                    Toast.makeText(mChatActivity, "You have been unmatched", Toast.LENGTH_LONG).show();
                    finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (LandingActivity.landingActivity != null) {
            super.onBackPressed();
        } else {
            Intent inStarted = new Intent(mContext, LandingActivity.class);
            startActivity(inStarted);
            finish();
            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
        }
    }


    @Override
    protected void onPause() {
        utils.setInt("inside_chat", 0);
        utils.setString("inside_dialog_id", "");
        super.onPause();

    }


    @Override
    protected void onResume() {
        FirebaseListeners.getInstance().intializeMessageListener(this, mChatDialogModel.getChat_dialog_id());

        NotificationManager notificationManager = (NotificationManager) mContext
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
        utils.setInt("inside_chat", 1);
        utils.setString("inside_dialog_id", mChatDialogModel.getChat_dialog_id());
//        ShortcutBadger.removeCount(con);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        FirebaseListeners.getInstance().intializeUnreadListener(null);
        FirebaseListeners.getInstance().intializeOtherProfileListener(null);
        FirebaseListeners.getInstance().removeOtherProfileListener();
        FirebaseListeners.getInstance().intializeMessageListener(null, "");

        if (mMessageRefrence != null && mMessageListener != null) {
            mMessageRefrence.removeEventListener(mMessageListener);
        }
        Constants.CHAT_PROFILE_ID = 0;
        Constants.PROFILE_ID = 0;
        mChatActivity = null;
        super.onDestroy();
    }

    private void optionDialog() {

        final Dialog mDialog = new Dialog(ChatActivity.this);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.setContentView(R.layout.dialog_chat_clear);
        WindowManager.LayoutParams wmlp = mDialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.TOP | Gravity.RIGHT;
        mDialog.setCanceledOnTouchOutside(true);

        FrameLayout.LayoutParams phone_params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        phone_params.setMargins(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);

        LinearLayout main_lay = (LinearLayout) mDialog.findViewById(R.id.ll_main);
        main_lay.setLayoutParams(phone_params);

        LinearLayout llInner = (LinearLayout) mDialog.findViewById(R.id.ll_inner);
        llInner.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);

        TextView txtBlock = (TextView) mDialog.findViewById(R.id.txt_block);
        txtBlock.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        txtBlock.setPadding(0, mWidth / 32, 0, mWidth / 32);

        TextView txtClear = (TextView) mDialog.findViewById(R.id.txt_clear);
        txtClear.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        txtClear.setPadding(0, mWidth / 32, 0, mWidth / 32);

        if (mOtherBlockStatus == 0) {
            txtBlock.setText("Block");
        } else {
            txtBlock.setText("Unblock");
        }

        txtClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((new Connection_Detector(mContext)).isConnectingToInternet()) {
                    clearDialog();
                    mDialog.dismiss();
                } else {
                    showAlert(llSend, errorInternet);
                    mDialog.dismiss();
                }
            }
        });

        txtBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((new Connection_Detector(mContext)).isConnectingToInternet()) {
                    if (mOtherBlockStatus == 0) {
                        blockDialog();
                    } else {
                        unBlockDialog();
                    }
                    mDialog.dismiss();
                } else {
                    mDialog.dismiss();
                    showAlert(llSend, errorInternet);
                }
            }
        });

        mDialog.show();
    }

    void blockDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ChatActivity.this);
        alertDialog.setMessage("Are you sure you want to block this user?");
        alertDialog.setPositiveButton("BLOCK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                FirebaseDatabase.getInstance().getReference().child(Constants.CHAT_ENDPOINT).child(mChatDialogModel.getChat_dialog_id())
                        .child("block_status").child(mOtherUserID).setValue("1");
                mOtherBlockStatus = 1;
                dialog.dismiss();

            }
        });
        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    void clearDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ChatActivity.this);
        alertDialog.setMessage("Are you sure you want to clear this conversation?");
        alertDialog.setPositiveButton("CLEAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                clearChat();
                dialog.dismiss();

            }
        });
        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    void unBlockDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ChatActivity.this);
        alertDialog.setMessage("You need to unblock this user to send a message.");
        alertDialog.setPositiveButton("UNBLOCK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                FirebaseDatabase.getInstance().getReference().child(Constants.CHAT_ENDPOINT).child(mChatDialogModel.getChat_dialog_id())
                        .child("block_status").child(mOtherUserID).setValue("0");
                mOtherBlockStatus = 0;
                dialog.dismiss();

            }
        });
        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }


}