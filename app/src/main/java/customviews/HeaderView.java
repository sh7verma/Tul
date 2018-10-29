package customviews;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tul.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.app.tul.OwnProfileActivity.infoClick;

public class HeaderView extends LinearLayout {

    @BindView(R.id.header_view_title)
    TextView txtTitle;

    @BindView(R.id.header_view_sub_title)
    TextView subTitle;

    @BindView(R.id.header_view_email_title)
    TextView email;

    @BindView(R.id.img_info)
    ImageView imgInfo;

    public HeaderView(Context context) {
        super(context);
    }

    public HeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HeaderView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    public void bindTo(String title) {
        bindTo(title, "","",true);
        txtTitle.setMaxLines(1);
        txtTitle.setEllipsize(TextUtils.TruncateAt.END);
    }

    public void bindTo(String title, String subTitle) {
        hideOrSetText(this.txtTitle, title);
        hideOrSetText(this.subTitle, subTitle);
    }

    public void bindTo(String title, String subTitle, String email, boolean isEmailVerified) {
        hideOrSetText(this.txtTitle, title);
        hideOrSetText(this.subTitle, subTitle);
        hideOrSetText(this.email, email);
        if (isEmailVerified) {
            imgInfo.setVisibility(GONE);
        } else {
            imgInfo.setVisibility(VISIBLE);

            imgInfo.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    infoClick();
                }
            });
        }
    }

    private void hideOrSetText(TextView tv, String text) {
        if (text == null || text.equals(""))
            tv.setVisibility(GONE);
        else {
            tv.setVisibility(VISIBLE);
            tv.setText(text);
        }
    }


}
