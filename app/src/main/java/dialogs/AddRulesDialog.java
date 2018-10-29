package dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.app.tul.R;
import com.app.tul.RulesActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import customviews.MaterialEditText;
import utils.Constants;

/**
 * Created by dev on 26/9/17.
 */

public class AddRulesDialog implements View.OnClickListener {

    @BindView(R.id.bt_dialog_rule_add)
    Button btAddRule;
    @BindView(R.id.bt_dialog_rule_cancel)
    Button btCancel;
    @BindView(R.id.et_dialog_write_rule)
    MaterialEditText edWriteRule;
    @BindView(R.id.ll_dialog_buttons)
    LinearLayout llButtonContainer;
    @BindView(R.id.cv_rule)
    CardView cvRule;
    @BindView(R.id.ll_inside)
    LinearLayout llInside;

    private Context mContext;
    private Dialog mDialog;
    private ArrayList<String> mRulesArrayList;
    private int mWidth, mHeight;
    private String rule;

    public AddRulesDialog(Context context, ArrayList<String> mRulesArrayList, int mWidth, int mHeight, String rule) {
        mContext = context;
        this.mRulesArrayList = mRulesArrayList;
        this.mWidth = mWidth;
        this.mHeight = mHeight;
        this.rule = rule;
    }

    public void showDialog() {
        View view;
        mDialog = new Dialog(mContext);
        mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        view = View.inflate(mContext, R.layout.dialog_add_rule, null);
        mDialog.setContentView(view);

        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        ButterKnife.bind(this, view);
        initUI();
        mDialog.show();
        initListeners();

        /** show data from rules activity **/
        if (!rule.equals("")) {
            edWriteRule.setText(rule);
            btAddRule.setText("EDIT");
            edWriteRule.setSelection(edWriteRule.getText().toString().length());
        }
    }

    private void initUI() {
        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/semibold.ttf");

        LinearLayout.LayoutParams cvParms = new LinearLayout.LayoutParams((int) (mWidth * .9), mHeight / 3);
        cvRule.setLayoutParams(cvParms);

        llButtonContainer.setPadding(mWidth / 24, 0, mWidth / 24, 0);

        llInside.setPadding(mWidth / 32, 0, mWidth / 32, mWidth / 24);

        edWriteRule.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        edWriteRule.setTypeface(typeface);

        btAddRule.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        btAddRule.setTypeface(typeface);

        btCancel.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        btCancel.setTypeface(typeface);

    }

    private void initListeners() {
        btAddRule.setOnClickListener(this);
        btCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_dialog_rule_add:
                if (btAddRule.getText().toString().equalsIgnoreCase("Add")) {
                    if (!edWriteRule.getText().toString().trim().equalsIgnoreCase("")) {
                        Constants.closeKeyboard(mContext, llInside);
                        mRulesArrayList.add(edWriteRule.getText().toString().trim());
                        ((RulesActivity) mContext).update(mRulesArrayList);
                        mDialog.dismiss();
                    } else {
                        edWriteRule.setError(mContext.getString(R.string.pls_add_rules));
                    }
                } else if (btAddRule.getText().toString().equalsIgnoreCase("Edit")) {
                    if (!edWriteRule.getText().toString().trim().equalsIgnoreCase("")) {
                        Constants.closeKeyboard(mContext, llInside);
                        ((RulesActivity) mContext).updateRule(edWriteRule.getText().toString().trim());
                        mDialog.dismiss();
                    } else {
                        edWriteRule.setError(mContext.getString(R.string.pls_add_rules));
                    }
                }

                break;
            case R.id.bt_dialog_rule_cancel:
                Constants.closeKeyboard(mContext, llInside);
                mDialog.dismiss();
        }
    }
}
