package dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.tul.R;

import api.RetrofitClient;
import butterknife.BindView;
import butterknife.ButterKnife;
import model.DemoModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Connection_Detector;
import utils.Utils;

/**
 * Created by dev on 8/3/18.
 */

public class VerifyEmailDialog implements View.OnClickListener {

    @BindView(R.id.txt_verify_email)
    TextView txtEmail;
    @BindView(R.id.bt_cancel)
    Button btCancel;
    @BindView(R.id.bt_resend)
    Button btResend;
    @BindView(R.id.progress)
    ProgressBar progressBar;

    private Utils utils;
    private Context mContext;
    private Dialog mDialog;
    private int mWidth;
    private String text;

    public VerifyEmailDialog(Context context, int mWidth, String text) {
        mContext = context;
        utils = new Utils(mContext);
        this.mWidth = mWidth;
        this.text = text;
    }

    public void showDialog() {
        View view;
        mDialog = new Dialog(mContext);
        mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        view = View.inflate(mContext, R.layout.dialog_verify_email, null);
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

        txtEmail.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        txtEmail.setText(text);

        btCancel.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        btResend.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
    }

    private void initListeners() {
        btCancel.setOnClickListener(this);
        btResend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_resend:
                hitApi();
                break;
            case R.id.bt_cancel:
                mDialog.dismiss();
                break;
        }
    }

    void hitApi() {
        if ((new Connection_Detector(mContext)).isConnectingToInternet()) {
            progressBar.setVisibility(View.VISIBLE);
            btCancel.setEnabled(false);
            btResend.setEnabled(false);
            btCancel.setBackgroundColor(mContext.getResources().getColor(R.color.light_grey));
            btResend.setText(R.string.sending_dot);
            Call<DemoModel> call = RetrofitClient.getInstance().resendEmail(utils.getString("access_token", ""));
            call.enqueue(new Callback<DemoModel>() {
                @Override
                public void onResponse(Call<DemoModel> call, Response<DemoModel> response) {
                    progressBar.setVisibility(View.INVISIBLE);
                    if (response != null) {
                        Toast.makeText(mContext, R.string.verification_email_sent, Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                    } else {
                        Toast.makeText(mContext, R.string.something_was_wrong, Toast.LENGTH_SHORT).show();
                        btResend.setText(R.string.resend);
                        btCancel.setEnabled(true);
                        btResend.setEnabled(true);
                        btCancel.setBackgroundColor(mContext.getResources().getColor(R.color.black_color));
                    }
                }

                @Override
                public void onFailure(Call<DemoModel> call, Throwable t) {
                    Toast.makeText(mContext,R.string.something_was_wrong, Toast.LENGTH_SHORT).show();
                    btResend.setText(R.string.resend);
                    btCancel.setEnabled(true);
                    btResend.setEnabled(true);
                    btCancel.setBackgroundColor(mContext.getResources().getColor(R.color.black_color));
                }
            });
        } else {
            Toast.makeText(mContext, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            mDialog.dismiss();
        }
    }
}