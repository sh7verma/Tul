package com.app.tul;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import adapters.RulesListviewAdapter;
import butterknife.BindView;
import dialogs.AddRulesDialog;
import utils.Constants;

public class RulesActivity extends BaseActivity {

    @BindView(R.id.lv_add_rules)
    ListView rulesListView;
    @BindView(R.id.ll_rules_container)
    LinearLayout llContainer;
    @BindView(R.id.bt_rule_add)
    Button btAddRule;
    @BindView(R.id.bt_done)
    Button btDone;
    @BindView(R.id.img_back)
    ImageView imgBckImg;
    @BindView(R.id.txt_toolbar_title)
    TextView txtToolbarTitle;
    @BindView(R.id.tv_rules_detail)
    TextView txtRulesDetail;
    @BindView(R.id.ll_add_option)
    LinearLayout llAddOption;

    ArrayList<String> mRulesArrayList = new ArrayList<>();
    RulesListviewAdapter mRulesListviewAdapter;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRulesArrayList.clear();
        mRulesArrayList.addAll(getIntent().getStringArrayListExtra("rules_array"));
        mRulesListviewAdapter = new RulesListviewAdapter(this, mRulesArrayList, mWidth);
        rulesListView.setAdapter(mRulesListviewAdapter);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_rules;
    }

    @Override
    protected void initUI() {

        txtToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));

        txtRulesDetail.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.040));
        txtRulesDetail.setPadding(mWidth / 16, 0, mWidth / 15, mWidth / 32);

        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/semibold.ttf");

        btAddRule.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        btAddRule.setTypeface(typeface);

        btDone.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        btDone.setTypeface(typeface);

        LinearLayout.LayoutParams param3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        param3.setMargins(mWidth / 32, 0, mWidth / 32, 0);
        param3.gravity = Gravity.CENTER;
        llContainer.setLayoutParams(param3);
        txtToolbarTitle.setText(R.string.add_rules);
    }

    @Override
    protected void onCreateStuff() {

    }

    @Override
    protected void initListener() {
        btAddRule.setOnClickListener(this);
        imgBckImg.setOnClickListener(this);
        btDone.setOnClickListener(this);
    }

    public void update(ArrayList<String> mRulesArrayList) {
        this.mRulesArrayList = mRulesArrayList;
        mRulesListviewAdapter.notifyDataSetChanged();
    }

    public void updateRule(String updatedRule) {
        mRulesArrayList.remove(pos);
        mRulesArrayList.add(pos, updatedRule);
        mRulesListviewAdapter.notifyDataSetChanged();
    }

    @Override
    protected Context getContext() {
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_rule_add:
                openRulesDialog("", 0);
                break;
            case R.id.img_back:
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                break;
            case R.id.bt_done:
                Intent intent = new Intent();
                intent.putStringArrayListExtra("rules_array", mRulesArrayList);
                setResult(RESULT_OK, intent);
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                break;
        }
    }

    public void openRulesDialog(String rule, int pos) {
        this.pos = pos;
        new AddRulesDialog(this, mRulesArrayList, mWidth, mHeight, rule).showDialog();
        Constants.showKeyboard(mContext, llAddOption);
    }
}
