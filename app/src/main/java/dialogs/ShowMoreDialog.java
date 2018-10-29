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
import android.widget.TextView;

import com.app.tul.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dev on 10/11/17.
 */

public class ShowMoreDialog implements View.OnClickListener {

    @BindView(R.id.bt_dialog_ok)
    Button btOk;
    @BindView(R.id.et_dialog_write_rule)
    TextView edWriteRule;
    @BindView(R.id.cv_rule)
    CardView cvRule;
    @BindView(R.id.ll_inside)
    LinearLayout llInside;

    private Context mContext;
    private Dialog mDialog;
    private int mWidth, mHeight;
    private String text;

    public ShowMoreDialog(Context context, int mWidth, int mHeight, String text) {
        mContext = context;
        this.mWidth = mWidth;
        this.mHeight = mHeight;
        this.text = text;
    }

    public void showDialog() {
        View view;
        mDialog = new Dialog(mContext);
        mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        view = View.inflate(mContext, R.layout.dialog_show_more, null);
        mDialog.setContentView(view);

        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        ButterKnife.bind(this, view);
        initUI();
        mDialog.show();
        initListeners();

    }

    private void initUI() {
        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/semibold.ttf");

        LinearLayout.LayoutParams cvParms = new LinearLayout.LayoutParams((int) (mWidth * .9), (int)(mHeight / 1.5));
        cvRule.setLayoutParams(cvParms);

        llInside.setPadding(mWidth / 32, 0, mWidth / 32, mWidth / 24);

        edWriteRule.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        edWriteRule.setTypeface(typeface);

        edWriteRule.setText(text);

        btOk.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        btOk.setTypeface(typeface);


    }

    private void initListeners() {
        btOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_dialog_ok:
                mDialog.dismiss();
        }
    }
}