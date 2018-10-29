package com.app.tul;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import adapters.SearchChatAdapter;
import api.RetrofitClient;
import butterknife.BindView;
import database.Db;
import model.BaseModel;
import model.ChatDialogModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Constants;


public class SearchChatActivity extends BaseActivity {

    private static final int YOU_CHOOSE = 21;
    @BindView(R.id.ll_main)
    LinearLayout llMain;
    @BindView(R.id.ed_search)
    EditText edSearch;
    @BindView(R.id.img_clear)
    ImageView imgClear;
    @BindView(R.id.txt_cancel)
    TextView txtCancel;
    @BindView(R.id.lv_search)
    ListView lvSearch;
    @BindView(R.id.txt_no_result)
    TextView txtNoResult;

    SearchChatActivity mSearchChatActivity;
    SearchChatAdapter mAdapter;
    ArrayList<ChatDialogModel> mChatArray = new ArrayList<>();
    Db db;

    @Override
    protected int getContentView() {
        return R.layout.activity_search_chat;
    }

    @Override
    protected void initUI() {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        loadData();

        mSearchChatActivity = this;

        llMain.setPadding(mWidth / 32, mWidth / 32, 0, 0);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/regular.ttf");
        edSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        edSearch.setTypeface(typeface);
        edSearch.setPadding(mWidth / 32, mWidth / 32, 0, mWidth / 32);
        edSearch.setFocusable(true);

        imgClear.setPadding(mWidth / 64, mWidth / 32, mWidth / 64, mWidth / 32);

        txtCancel.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        txtCancel.setPadding(mWidth / 32, mWidth / 32, mWidth / 32, mWidth / 32);

        txtNoResult.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.055));
        txtNoResult.setPadding(0, mWidth / 8, 0, 0);

        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    ArrayList<ChatDialogModel> tempArray = new ArrayList<>();
                    for (int i = 0; i < mChatArray.size(); i++) {

                        String particpantId[] = mChatArray.get(i).getParticipant_ids().split(",");
                        String otherUserId;
                        if (particpantId[0].equals(utils.getString("user_id", ""))) {
                            otherUserId = particpantId[1];
                        } else {
                            otherUserId = particpantId[0];
                        }
                        if (mChatArray.get(i).getName().get(otherUserId) != null) {
                            if ((mChatArray.get(i).getName().get(otherUserId).toLowerCase().contains(s.toString().toLowerCase()))) {
                                tempArray.add(mChatArray.get(i));
                            }
                        }
                    }
                    if (tempArray.size() > 0) {
                        lvSearch.setVisibility(View.VISIBLE);
                        txtNoResult.setVisibility(View.GONE);
                        mAdapter = new SearchChatAdapter(mContext, tempArray, mSearchChatActivity);
                        lvSearch.setAdapter(mAdapter);
                    } else {
                        lvSearch.setVisibility(View.GONE);
                        txtNoResult.setVisibility(View.VISIBLE);
                    }
                } else {
                    lvSearch.setVisibility(View.GONE);
                    txtNoResult.setVisibility(View.GONE);
                }
            }


            @Override
            public void afterTextChanged(Editable s) {

            }

        });

    }

    private void loadData() {
        db = new Db(this);

        for (String dialogId : db.getAllConversation().keySet()) {
            if (!db.getAllConversation().get(dialogId).getLast_message().equals(Constants.CHAT_REGEX)) {
                if (db.getAllConversation().get(dialogId).getDelete_outer_dialog().get(utils.getString("user_id", "")).equals("0"))
                    mChatArray.add(db.getAllConversation().get(dialogId));
            }
        }

    }

    @Override
    protected void onCreateStuff() {

    }

    @Override
    protected void initListener() {
        txtCancel.setOnClickListener(this);
        imgClear.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return SearchChatActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_clear:
                edSearch.setText("");

                break;
            case R.id.txt_cancel:
//                Intent intent=new Intent();
//                setResult(RESULT_OK);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                } else {
                    finish();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                }
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edSearch.getWindowToken(), 0);
    }

    //    public void unMatchUser(String dialogId, String otherUserId) {
//        Call<BaseModel> call = RetrofitClient.getInstance().unMatchUser(utils.getString("access_token", ""), otherUserId);
//        call.enqueue(new Callback<BaseModel>() {
//            @Override
//            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<BaseModel> call, Throwable t) {
//
//            }
//        });
//
//        db.deleteDialog(dialogId);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case YOU_CHOOSE:
//                    Log.e("Position = ", data.getIntExtra("position", 0) + "");
//                    ((YouChooseFragment) mAdapter.getFragment(1)).removeUser(data.getIntExtra("position", 0));
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
