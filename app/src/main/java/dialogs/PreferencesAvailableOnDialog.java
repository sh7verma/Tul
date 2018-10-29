package dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tul.PreferencesActivity;
import com.app.tul.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PreferencesAvailableOnDialog implements View.OnClickListener {

    @BindView(R.id.ll_preferences_dialog_available_main_container)
    LinearLayout llMainLayout;
    @BindView(R.id.ll_preferences_dialog_available_cancel_container)
    LinearLayout llCancelContainer;

    @BindView(R.id.ll_preferences_dialog_available_only_weekends_container)
    LinearLayout llOnlyWeekendsContainer;

    @BindView(R.id.ll_preferences_dialog_available_weekdays_container)
    LinearLayout llWeekdaysContainer;

    @BindView(R.id.ll_preferences_dialog_available_both_container)
    LinearLayout llbothContainer;

    @BindView(R.id.tv_preferences_dialog_available_title)
    TextView tvTitle;

    @BindView(R.id.tv_preferences_dialog_available_only_weekends)
    TextView tvOnlyWeekends;
    @BindView(R.id.tv_preferences_dialog_available_both)
    TextView tvBoth;
    @BindView(R.id.tv_preferences_dialog_available_weekdays)
    TextView tvWeekdays;
    @BindView(R.id.img_preferences_both_selected)
    ImageView imgBothSelected;
    @BindView(R.id.img_preferences_only_weekends_selected)
    ImageView imgOnlyWeekendsSelected;
    @BindView(R.id.img_preferences_weekdays_selected)
    ImageView imgWeekdaysSelected;

    @BindView(R.id.img_dismiss_dialog)
    ImageView imgDismissDialog;

    Context mContext;
    int mWidth;
    Dialog mDialog;
    String mText;

    public PreferencesAvailableOnDialog(Context context, int mWidth, String text) {
        mContext = context;
        this.mWidth = mWidth;
        mText = text;
    }

    public void showDialog() {
        View view;
        mDialog = new Dialog(mContext);
        mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        view = View.inflate(mContext, R.layout.dialog_preferences_available_on, null);
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

        llMainLayout.setPadding(mWidth / 20, mWidth / 32, mWidth / 20, mWidth / 32);
        llOnlyWeekendsContainer.setPadding(0, mWidth / 28, 0, mWidth / 28);
        llWeekdaysContainer.setPadding(0, mWidth / 28, 0, mWidth / 28);
        llbothContainer.setPadding(0, mWidth / 28, 0, mWidth / 28);
        tvTitle.setPadding(0, mWidth / 50, 0, mWidth / 28);

        tvOnlyWeekends.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvBoth.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvWeekdays.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

        if (mText.equals(tvOnlyWeekends.getText())) {
            imgOnlyWeekendsSelected.setVisibility(View.VISIBLE);
        } else if (mText.equals(tvBoth.getText())) {
            imgBothSelected.setVisibility(View.VISIBLE);
        } else {
            imgWeekdaysSelected.setVisibility(View.VISIBLE);
        }

    }

    private void initListeners() {

        llbothContainer.setOnClickListener(this);
        llWeekdaysContainer.setOnClickListener(this);
        llOnlyWeekendsContainer.setOnClickListener(this);
        imgDismissDialog.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_preferences_dialog_available_both_container: {
                ((PreferencesActivity) mContext).setResultOfAvailableOn(tvBoth.getText().toString());
                mDialog.dismiss();
                break;
            }
            case R.id.ll_preferences_dialog_available_only_weekends_container: {
                ((PreferencesActivity) mContext).setResultOfAvailableOn(tvOnlyWeekends.getText().toString());
                mDialog.dismiss();
                break;
            }
            case R.id.ll_preferences_dialog_available_weekdays_container: {
                ((PreferencesActivity) mContext).setResultOfAvailableOn(tvWeekdays.getText().toString());
                mDialog.dismiss();
                break;
            }
            case R.id.img_dismiss_dialog: {
                mDialog.dismiss();
                break;
            }
        }
    }
}
