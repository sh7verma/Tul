package com.app.tul;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import adapters.TulRulesAdapter;
import butterknife.BindView;
import customviews.SemiboldTextView;

public class TulRulesActivity extends BaseActivity {

    @BindView(R.id.lv_tul_rules)
    ListView lvTulRules;

    @BindView(R.id.txt_toolbar_title)
    SemiboldTextView txtToolbarTitle;

    @BindView(R.id.txt_no_rules)
    TextView txtNoRules;

    @BindView(R.id.img_back)
    ImageView imgBack;

    TulRulesAdapter tulRulesAdapter;
    ArrayList<String> tulRuleArrayList = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_tul_rules;
    }

    @Override
    protected void initUI() {

        tulRuleArrayList.clear();
        tulRuleArrayList.addAll(getIntent().getStringArrayListExtra("data"));

        txtNoRules.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.05));

        txtToolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
        txtToolbarTitle.setText(R.string.rules);

        tulRulesAdapter = new TulRulesAdapter(this, tulRuleArrayList);
        lvTulRules.setAdapter(tulRulesAdapter);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(mWidth / 25, 0, mWidth / 25, 0);
        lvTulRules.setLayoutParams(layoutParams);

    }

    @Override
    protected void onCreateStuff() {
        if (tulRuleArrayList.size() > 0) {
            lvTulRules.setVisibility(View.VISIBLE);
            txtNoRules.setVisibility(View.GONE);
        } else {
            lvTulRules.setVisibility(View.GONE);
            txtNoRules.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initListener() {
        imgBack.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return TulRulesActivity.this;
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
