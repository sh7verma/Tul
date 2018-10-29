package com.app.tul;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import adapters.BlockedUsersAdapter;
import adapters.ConversationAdapter;
import butterknife.BindView;
import interfaces.ChatListener;
import model.ChatDialogModel;
import utils.Constants;
import utils.FirebaseListeners;

/**
 * Created by dev on 18/12/17.
 */

public class ConversationActivity extends BaseActivity implements ChatListener {

    @BindView(R.id.img_back)
    ImageView imgBckImg;
    @BindView(R.id.txt_toolbar_title)
    TextView txtToolbarTitle;
    @BindView(R.id.img_search)
    ImageView imgSearch;

    @BindView(R.id.rv_chat)
    RecyclerView rvChat;

    @BindView(R.id.txt_no_notification)
    TextView txtNoResult;

    LinearLayoutManager mLayoutManager;
    ConversationAdapter mAdapter;

    Map<String, ChatDialogModel> totalConversationMap = new LinkedHashMap<>();

    ArrayList<String> conversationKeys = new ArrayList<>();
    HashMap<String, ChatDialogModel> conversationMap = new HashMap<>();

    ArrayList<String> totalKeys = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_conversation;
    }

    @Override
    protected void initUI() {

        txtToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        txtToolbarTitle.setText("INBOX");

        imgSearch.setVisibility(View.VISIBLE);

        mLayoutManager = new LinearLayoutManager(mContext);
        rvChat.setLayoutManager(mLayoutManager);

        txtNoResult.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.055));
        txtNoResult.setPadding(0, mWidth / 8, 0, 0);

        FirebaseListeners.getInstance().intializeChatListener(this);
        setData();
    }

    private void setData() {
        totalConversationMap = db.getAllConversation();
        for (String chatDialogId : totalConversationMap.keySet()) {
            totalKeys.add(chatDialogId);
            ChatDialogModel chatDialogModel = new ChatDialogModel();
            chatDialogModel = totalConversationMap.get(chatDialogId);
            if (chatDialogModel.getLast_message().equals(Constants.CHAT_REGEX)) {

            } else {
                if (chatDialogModel.getDelete_outer_dialog().get(utils.getString("user_id", "")).equals("0")) {
                    conversationMap.put(chatDialogId, chatDialogModel);
                    conversationKeys.add(chatDialogId);
                }
            }
        }

        if (conversationMap.size() > 0) {
            rvChat.setVisibility(View.VISIBLE);
            txtNoResult.setVisibility(View.GONE);
            mAdapter = new ConversationAdapter(mContext, conversationMap, conversationKeys);
            rvChat.setAdapter(mAdapter);
        } else {
            txtNoResult.setVisibility(View.VISIBLE);
            rvChat.setVisibility(View.GONE);
        }
//        showHideViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreateStuff() {
        NotificationManager notificationManager = (NotificationManager) mContext
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    @Override
    protected void initListener() {

        imgBckImg.setOnClickListener(this);
        imgSearch.setOnClickListener(this);

    }

    @Override
    protected Context getContext() {
        return ConversationActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                moveBack();
                break;
            case R.id.img_search:
                Intent intent = new Intent(mContext, SearchChatActivity.class);
                mContext.startActivity(intent);
                ((Activity) mContext).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
        }
    }


    @Override
    public void onChildAdded(ChatDialogModel chatDialogModel) {
        Log.e("conversation Added = ", "Yes");
        if (chatDialogModel.getLast_message().equals(Constants.CHAT_REGEX)) {

        } else {
            conversationMap.put(chatDialogModel.getChat_dialog_id(), chatDialogModel);
            if (!conversationKeys.contains(chatDialogModel.getChat_dialog_id()))
                conversationKeys.add(chatDialogModel.getChat_dialog_id());
        }
        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onChildChanged(ChatDialogModel chatDialogModel) {
        Log.e("conversation changed = ", "Yes");
        totalConversationMap.clear();

        conversationMap.clear();
        conversationKeys.clear();

        totalConversationMap = db.getAllConversation();

        for (String chatDialogId : totalConversationMap.keySet()) {
            totalKeys.add(chatDialogId);
            ChatDialogModel chatDialog = new ChatDialogModel();
            chatDialog = totalConversationMap.get(chatDialogId);
            if (chatDialog.getLast_message().equals(Constants.CHAT_REGEX)) {

            } else {
                if (chatDialogModel.getDelete_outer_dialog().get(utils.getString("user_id", "")).equals("0")) {
                    conversationMap.put(chatDialogId, chatDialog);
                    conversationKeys.add(chatDialogId);
                }
            }
        }
        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();

        if (conversationMap.size() > 0) {
            txtNoResult.setVisibility(View.GONE);
            rvChat.setVisibility(View.VISIBLE);
        } else {
            txtNoResult.setVisibility(View.VISIBLE);
            rvChat.setVisibility(View.GONE);
        }
    }

    @Override
    public void onChildDelete(ChatDialogModel chatDialogModel) {

        if (conversationKeys.contains(chatDialogModel.getChat_dialog_id())) {
            Log.e("ChatDialogId 2", chatDialogModel.getChat_dialog_id());
            conversationMap.remove(chatDialogModel.getChat_dialog_id());
            conversationKeys.remove(chatDialogModel.getChat_dialog_id());
        }
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }

        FirebaseDatabase.getInstance().getReference().child(Constants.USER_ENDPOINT).child(utils.getString("user_id", ""))
                .child("chat_dialog_ids").child(chatDialogModel.getChat_dialog_id()).removeValue();
    }

    @Override
    public void onBackPressed() {
        moveBack();
    }

    private void moveBack() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    public void deleteConversation(final String chat_dialog_id) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.clear_conversation_warining)
                .setCancelable(false)
                .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (connectedToInternet()) {
                            FirebaseDatabase.getInstance().getReference().child(Constants.CHAT_ENDPOINT)
                                    .child(chat_dialog_id).child("delete_outer_dialog")
                                    .child(utils.getString("user_id", ""))
                                    .setValue("1");
                            db.deleteConversationById(chat_dialog_id);
                            clearChat(chat_dialog_id);
                            setData();
                        } else
                            showInternetAlert(imgSearch);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void clearChat(String chat_dialog_id) {
        FirebaseDatabase.getInstance().getReference().child(Constants.CHAT_ENDPOINT).child(chat_dialog_id).child("delete_dialog_time")
                .child(utils.getString("user_id", "")).setValue(String.valueOf(Constants.getUtcTime(System.currentTimeMillis())));
        db.deleteAllMessages(chat_dialog_id);
    }
}
