package com.app.tul;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import adapters.BlockedUsersAdapter;
import adapters.ConversationAdapter;
import butterknife.BindView;
import model.ChatDialogModel;
import utils.Constants;

/**
 * Created by dev on 14/12/17.
 */

public class BlockedUsersActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView imgBckImg;
    @BindView(R.id.txt_toolbar_title)
    TextView txtToolbarTitle;


    @BindView(R.id.rv_blocked_users)
    RecyclerView rvBlockedUsers;

    @BindView(R.id.txt_no_blocked_users)
    TextView txtNoBlockedUser;

    BlockedUsersAdapter mAdapter;

    Map<String, ChatDialogModel> totalConversationMap = new LinkedHashMap<>();

    ArrayList<String> conversationKeys = new ArrayList<>();
    HashMap<String, ChatDialogModel> conversationMap = new HashMap<>();

    ArrayList<String> totalKeys = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_blocked_users;
    }

    @Override
    protected void initUI() {

        txtToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.05));
        txtToolbarTitle.setText("BLOCKED USERS");

        rvBlockedUsers.setPadding(mWidth / 32, 0, mWidth / 32, 0);
        rvBlockedUsers.setLayoutManager(new LinearLayoutManager(mContext));

        txtNoBlockedUser.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));

    }

    @Override
    protected void onCreateStuff() {
        totalConversationMap = db.getAllConversation();

        for (String chatDialogId : totalConversationMap.keySet()) {
            totalKeys.add(chatDialogId);
            ChatDialogModel chatDialogModel = new ChatDialogModel();
            chatDialogModel = totalConversationMap.get(chatDialogId);

            String particpantIdArray[] = chatDialogModel.getParticipant_ids().split(",");
            String particpantId = null;
            for (int i = 0; i < particpantIdArray.length; i++) {
                if (!particpantIdArray[i].equals(utils.getString("user_id", ""))) {
                    particpantId = particpantIdArray[i];
                }
            }
            if (chatDialogModel.getBlock_status().get(particpantId).equals("1")) {

                conversationMap.put(chatDialogId, chatDialogModel);
                conversationKeys.add(chatDialogId);
            }
        }

        if (conversationMap.size() > 0) {
            rvBlockedUsers.setVisibility(View.VISIBLE);
            txtNoBlockedUser.setVisibility(View.GONE);
            mAdapter = new BlockedUsersAdapter(mContext, conversationMap, conversationKeys);
            rvBlockedUsers.setAdapter(mAdapter);
        } else {
            txtNoBlockedUser.setVisibility(View.VISIBLE);
            rvBlockedUsers.setVisibility(View.GONE);
        }
    }

    public void updateAdapter(String chat_dialog_id) {

        conversationMap.remove(chat_dialog_id);
        conversationKeys.remove(chat_dialog_id);

        if (conversationMap.size() > 0) {
            rvBlockedUsers.setVisibility(View.VISIBLE);
            txtNoBlockedUser.setVisibility(View.GONE);
        } else {
            txtNoBlockedUser.setVisibility(View.VISIBLE);
            rvBlockedUsers.setVisibility(View.GONE);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initListener() {
        imgBckImg.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return BlockedUsersActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                break;
        }

    }
}
