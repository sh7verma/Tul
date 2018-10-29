package dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tul.PreferencesActivity;
import com.app.tul.R;
import com.app.tul.SearchActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAvailbiltyDialog implements View.OnClickListener {

    @BindView(R.id.ll_main)
    LinearLayout llMain;
    @BindView(R.id.ll_cancel)
    LinearLayout llCancel;
    @BindView(R.id.img_cancel)
    ImageView imgCancel;

    @BindView(R.id.ll_option1)
    LinearLayout llOption1;
    @BindView(R.id.tv_option1)
    TextView tvOption1;
    @BindView(R.id.img_option1)
    ImageView imgOption1;

    @BindView(R.id.ll_option2)
    LinearLayout llOption2;
    @BindView(R.id.tv_option2)
    TextView tvOption2;
    @BindView(R.id.img_option2)
    ImageView imgOption2;

    @BindView(R.id.ll_option3)
    LinearLayout llOption3;
    @BindView(R.id.tv_option3)
    TextView tvOption3;
    @BindView(R.id.img_option3)
    ImageView imgOption3;

    private Context mContext;
    private int mWidth;
    private Dialog mDialog;
    private String mText;
    private int path;

    public SearchAvailbiltyDialog(Context context, int mWidth, String text, int path) {
        mContext = context;
        this.mWidth = mWidth;
        this.path = path;
        this.mText = text;
    }

    public void showDialog() {
        View view;
        mDialog = new Dialog(mContext);
        mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        view = View.inflate(mContext, R.layout.dialog_search, null);
        mDialog.setContentView(view);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        ButterKnife.bind(this, view);
        mDialog.getWindow().getAttributes().windowAnimations = R.style.DialogSlideAnim;
        initUI();
        mDialog.show();
        initListeners();
    }

    private void initUI() {

        llMain.setPadding(mWidth / 20, mWidth / 32, mWidth / 20, mWidth / 32);

        llOption1.setPadding(0, mWidth / 28, 0, mWidth / 28);

        llOption2.setPadding(0, mWidth / 28, 0, mWidth / 28);

        llOption3.setPadding(0, mWidth / 28, 0, mWidth / 28);

        tvOption1.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

        tvOption2.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

        tvOption3.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

        if (path == 1) {
            /// availbilty modes
            tvOption1.setText(mContext.getString(R.string.only_weekends));
            tvOption2.setText(mContext.getString(R.string.weekdays));
            tvOption3.setText(mContext.getString(R.string.all));
        } else {
            /// pickup modes
            tvOption1.setText(R.string.contact_delivery_option);
            tvOption2.setText(mContext.getString(R.string.pickup));
            tvOption3.setText(mContext.getString(R.string.both));
        }

        if (mText.equals(mContext.getString(R.string.deliver_in_5_miles))) {
            imgOption1.setVisibility(View.VISIBLE);
        } else if (mText.equals(tvOption2.getText())) {
            imgOption2.setVisibility(View.VISIBLE);
        } else {
            imgOption3.setVisibility(View.VISIBLE);
        }

    }

    private void initListeners() {
        llOption1.setOnClickListener(this);
        llOption2.setOnClickListener(this);
        llOption3.setOnClickListener(this);
        imgCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            0 for pickup, 1 for delivery,2 for both
//            1 for weekdays, 2 for weekend
            case R.id.ll_option1: {
                if (path == 1)
                    ((SearchActivity) mContext).setResultOfAvailableOn(tvOption1.getText().toString(), 2);
                else
                    ((SearchActivity) mContext).setResultOfPickup(mContext.getString(R.string.deliver_in_5_miles), 1);
                mDialog.dismiss();
                break;
            }
            case R.id.ll_option2: {
                if (path == 1)
                    ((SearchActivity) mContext).setResultOfAvailableOn(tvOption2.getText().toString(), 1);
                else
                    ((SearchActivity) mContext).setResultOfPickup(tvOption2.getText().toString(), 0);
                mDialog.dismiss();
                break;
            }
            case R.id.ll_option3: {
                if (path == 1)
                    ((SearchActivity) mContext).setResultOfAvailableOn(tvOption3.getText().toString(), 0);
                else
                    ((SearchActivity) mContext).setResultOfPickup(tvOption3.getText().toString(), 2);
                mDialog.dismiss();
                break;
            }
            case R.id.img_cancel: {
                mDialog.dismiss();
                break;
            }
        }
    }
}
