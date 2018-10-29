package com.app.tul;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsMessage;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.concurrent.TimeUnit;

import api.RetrofitClient;
import butterknife.BindView;
import butterknife.ButterKnife;
import model.ChatProfileModel;
import model.SignupModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Connection_Detector;
import utils.Constants;
import utils.CustomLoadingDialog;
import utils.Utils;


public class VerifyPhoneActivity extends Activity implements View.OnClickListener {

    public CountDownTimer mTimer;
    public long time;
    Context mContext;
    Utils util;
    int mWidth, mHeight;
    @BindView(R.id.ll_main)
    LinearLayout llMain;
    @BindView(R.id.img_verify_status)
    ImageView imgVerifyStatus;
    @BindView(R.id.img_cross)
    ImageView imgCross;
    @BindView(R.id.txt_verify_msg)
    TextView txtVerifyMsg;
    @BindView(R.id.ed_code_1)
    EditText edCode1;
    @BindView(R.id.ed_code_2)
    EditText edCode2;
    @BindView(R.id.ed_code_3)
    EditText edCode3;
    @BindView(R.id.ed_code_4)
    EditText edCode4;
    @BindView(R.id.txt_call)
    TextView txtCall;
    @BindView(R.id.txt_resend)
    TextView txtResend;
    @BindView(R.id.txt_timer)
    TextView txtTimer;
    @BindView(R.id.txt_done)
    TextView txtDone;
    StringBuilder mStringBuilder;
    private String mCode;
    private final BroadcastReceiver SMS_Reciever = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
// TODO Auto-generated method stub

// TODO Auto-generated method stub
            Bundle bndl = intent.getExtras();
            SmsMessage[] msg = null;
            if (null != bndl) {
// ---retrieve the SMS message received---
                Object[] pdus = (Object[]) bndl.get("pdus");
                msg = new SmsMessage[pdus.length];
                for (int i = 0; i < msg.length; i++) {
                    msg[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    try {
                        String text = msg[i].getMessageBody().toString();
                        if (text.contains("Kindly verify your account on TUL")) {
                            mCode = (text.substring(text.length() - 4,
                                    text.length())).trim();

                            for (int ii = 0; ii < mCode.length(); ii++) {
                                if (ii == 0)
                                    edCode1.setText(Character.toString(text.charAt(ii)));
                                else if (ii == 1)
                                    edCode2.setText(Character.toString(text.charAt(ii)));
                                else if (ii == 2)
                                    edCode3.setText(Character.toString(text.charAt(ii)));
                                else if (ii == 3)
                                    edCode4.setText(Character.toString(text.charAt(ii)));
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        WindowManager.LayoutParams wmlp = this.getWindow().getAttributes();
        wmlp.gravity = Gravity.BOTTOM;
        this.setFinishOnTouchOutside(false);
        setContentView(R.layout.activity_verify_phone);

        mContext = VerifyPhoneActivity.this;
        util = new Utils(mContext);
        mWidth = util.getInt("width", 0);
        mHeight = util.getInt("height", 0);

        getWindow().setLayout(mWidth, wmlp.height);
        ButterKnife.bind(this);

        initUI();
        initListener();
        setData();
        disabledViews();
        startTimer();
        disableButtons();
//        Toast.makeText(mContext, "The OTP is 1111 for now", Toast.LENGTH_SHORT).show();

        imgVerifyStatus.setImageResource(R.mipmap.ic_type_code);

        edCode1.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (edCode1.getText().toString().length() == 1) {
                    edCode2.requestFocus();
                    edCode2.setEnabled(true);
                    if (edCode1.getText().toString().length() > 0 && edCode2.getText().toString().length() > 0
                            && edCode3.getText().toString().length() > 0 && edCode4.getText().toString().length() > 0) {
                        checkOTP();
                    }
                } else {
                    if (edCode1.getText().toString().length() > 0) {
                        edCode1.setText(edCode1.getText().toString().substring(0, 1));
                        edCode2.requestFocus();
                    } else {
                        imgVerifyStatus.setImageResource(R.mipmap.ic_type_code);
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });
        edCode2.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                Log.d("1", "1");
                if (edCode2.getText().toString().length() == 1) {
                    edCode3.requestFocus();
                    edCode3.setEnabled(true);
                    if (edCode1.getText().toString().length() > 0 && edCode2.getText().toString().length() > 0
                            && edCode3.getText().toString().length() > 0 && edCode4.getText().toString().length() > 0) {
                        checkOTP();
                    }
                } else if (edCode2.getText().toString().length() == 0) {
                    edCode1.setSelection(edCode1.getText().toString().length());
                    edCode1.requestFocus();
                    imgVerifyStatus.setImageResource(R.mipmap.ic_type_code);
                } else {
                    //edCode2.setMaxHeight(1);
                    edCode2.setText(edCode2.getText().toString().substring(0, 1));
                    edCode3.requestFocus();

                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
                Log.d("2", "2");
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                Log.d("3", "3");
            }
        });
        edCode3.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (edCode3.getText().toString().length() == 1) {
                    edCode4.requestFocus();
                    edCode4.setEnabled(true);
                    if (edCode1.getText().toString().length() > 0 && edCode2.getText().toString().length() > 0
                            && edCode3.getText().toString().length() > 0 && edCode4.getText().toString().length() > 0) {
                        checkOTP();
                    }
                } else if (edCode3.getText().toString().length() == 0) {
                    edCode2.setSelection(edCode2.getText().toString().length());
                    edCode2.requestFocus();
                    imgVerifyStatus.setImageResource(R.mipmap.ic_type_code);
                } else {
                    //edCode3.setMaxHeight(1);
                    edCode3.setText(edCode3.getText().toString().substring(0, 1));
                    edCode4.requestFocus();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if (edCode3.getText().toString().length() == 1) {
                    edCode4.requestFocus();
                }
            }
        });
        edCode4.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (edCode4.getText().toString().length() == 1) {
                    if (edCode1.getText().toString().length() > 0 && edCode2.getText().toString().length() > 0
                            && edCode3.getText().toString().length() > 0 && edCode4.getText().toString().length() > 0) {
                        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Activity.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(edCode4.getWindowToken(), 0);
                        checkOTP();
                    }
                } else if (edCode4.getText().toString().length() == 0) {
                    edCode3.setSelection(edCode3.getText().toString().length());
                    edCode3.requestFocus();
                    imgVerifyStatus.setImageResource(R.mipmap.ic_type_code);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });
    }

    @Override
    protected void onResume() {
//        registerReceiver(SMS_Reciever, new IntentFilter(
//                "android.provider.Telephony.SMS_RECEIVED"));
        super.onResume();
    }

    @Override
    protected void onDestroy() {
//        unregisterReceiver(SMS_Reciever);
        super.onDestroy();
    }

    private void checkOTP() {
        if ((new Connection_Detector(mContext)).isConnectingToInternet()) {
            mStringBuilder = new StringBuilder();
            mStringBuilder.append(edCode1.getText().toString());
            mStringBuilder.append(edCode2.getText().toString());
            mStringBuilder.append(edCode3.getText().toString());
            mStringBuilder.append(edCode4.getText().toString());
            if (String.valueOf(util.getInt("otp", 0)).equals(mStringBuilder.toString())) {
                txtDone.setEnabled(true);
                imgVerifyStatus.setImageResource(R.mipmap.ic_done);
                txtTimer.setVisibility(View.INVISIBLE);
                mTimer.cancel();
                disableButtons();
                disableListeners();
                hitAPI();
            } else {
                imgVerifyStatus.setImageResource(R.mipmap.ic_verify);
            }
        } else
            Toast.makeText(mContext, R.string.internet, Toast.LENGTH_SHORT).show();
    }

    private void initUI() {

        RelativeLayout.LayoutParams mainParms = new RelativeLayout.LayoutParams(mWidth, mHeight / 3);
//        mainParms.setMargins(0, mHeight / 32, 0, 0);
//        mainParms.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//        llMain.setLayoutParams(mainParms);

        txtVerifyMsg.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/regular.ttf");
        edCode1.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));
        edCode1.setTypeface(typeface);

        edCode2.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));
        edCode2.setTypeface(typeface);

        edCode3.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));
        edCode3.setTypeface(typeface);

        edCode4.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));
        edCode4.setTypeface(typeface);

        txtCall.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));

        txtResend.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));

        txtTimer.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));

        txtDone.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.04));
    }

    private void disabledViews() {
        edCode2.setEnabled(false);
        edCode3.setEnabled(false);
        edCode4.setEnabled(false);
        txtDone.setEnabled(false);
    }

    private void disableListeners() {
        edCode1.setKeyListener(null);
        edCode2.setKeyListener(null);
        edCode3.setKeyListener(null);
        edCode4.setKeyListener(null);
    }

    private void disableButtons() {
        txtCall.setBackgroundResource(R.drawable.grey_default);
        txtCall.setTextColor(ContextCompat.getColor(this, R.color.hint_color_dark));
        txtResend.setBackgroundResource(R.drawable.grey_default);
        txtResend.setTextColor(ContextCompat.getColor(this, R.color.hint_color_dark));
        txtCall.setEnabled(false);
        txtResend.setEnabled(false);
    }

    private void enableButtons() {
        txtCall.setBackgroundResource(R.drawable.white_ripple);
        txtCall.setTextColor(ContextCompat.getColor(this, R.color.black_color));
        txtResend.setBackgroundResource(R.drawable.white_ripple);
        txtResend.setTextColor(ContextCompat.getColor(this, R.color.black_color));
        txtCall.setEnabled(true);
        txtResend.setEnabled(true);
    }

    private void setData() {
        txtVerifyMsg.setText(getResources().getString(R.string.verfication_hint_msg) + " " + util.getString("country_code", "") + " " + util.getString("phone_number", ""));
    }

    private void initListener() {
        imgCross.setOnClickListener(this);
        imgVerifyStatus.setOnClickListener(this);
        txtCall.setOnClickListener(this);
        txtResend.setOnClickListener(this);
        txtDone.setOnClickListener(this);
    }

    public void startTimer() {
        // final String FORMAT = "%02d:%02d";
        txtTimer.setVisibility(View.VISIBLE);
        time = (1 * 60 * 1000);
        final String FORMAT = "%02d:%02d";
        mTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
                time = millisUntilFinished;
                txtTimer.setText(String.format(
                        FORMAT,
                        TimeUnit.MILLISECONDS
                                .toMinutes(millisUntilFinished)
                                - TimeUnit.HOURS
                                .toMinutes(TimeUnit.MILLISECONDS
                                        .toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS
                                .toSeconds(millisUntilFinished)
                                - TimeUnit.MINUTES
                                .toSeconds(TimeUnit.MILLISECONDS
                                        .toMinutes(millisUntilFinished))));
            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                txtTimer.setVisibility(View.INVISIBLE);
                enableButtons();
            }
        }.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_call:
//                Toast.makeText(mContext, "Work in Progress", Toast.LENGTH_SHORT).show();
                startTimer();
                disableButtons();
                if ((new Connection_Detector(mContext)).isConnectingToInternet())
                    hitCallAPI();
                else
                    Toast.makeText(mContext, R.string.internet, Toast.LENGTH_SHORT).show();
                break;
            case R.id.txt_resend:
                if ((new Connection_Detector(mContext)).isConnectingToInternet())
                    hitResendAPI();
                else
                    Toast.makeText(mContext, R.string.internet, Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_cross:
                Constants.closeKeyboard(mContext, imgCross);
                finish();
                break;
            case R.id.txt_done:
                if ((new Connection_Detector(mContext)).isConnectingToInternet())
                    hitAPI();
                else
                    Toast.makeText(mContext, R.string.internet, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void hitResendAPI() {
        CustomLoadingDialog.getLoader().showLoader(this);
        Call<SignupModel> call = RetrofitClient.getInstance().callResend(util.getString("access_token", ""));
        call.enqueue(new Callback<SignupModel>() {
            @Override
            public void onResponse(Call<SignupModel> call, Response<SignupModel> response) {
                if (response.body().getResponse() != null) {
                    util.setInt("otp", response.body().getResponse().getOtp());
                    startTimer();
                    disableButtons();
                    Toast.makeText(VerifyPhoneActivity.this, R.string.otp_successfully_set, Toast.LENGTH_SHORT).show();
                } else {
                    if (response.body().error.getCode().equals(getResources().getString(R.string.invalid_access_token))) {
                        Constants.moveToSplash(mContext, util);
                    } else {
                        Toast.makeText(VerifyPhoneActivity.this, response.body().error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                CustomLoadingDialog.getLoader().dismissLoader();
            }

            @Override
            public void onFailure(Call<SignupModel> call, Throwable t) {
                Toast.makeText(VerifyPhoneActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                CustomLoadingDialog.getLoader().dismissLoader();
            }
        });
    }

    private void hitCallAPI() {
        CustomLoadingDialog.getLoader().showLoader(mContext);
        Call<SignupModel> call = RetrofitClient.getInstance().callUser(util.getString("access_token", ""));
        call.enqueue(new Callback<SignupModel>() {
            @Override
            public void onResponse(Call<SignupModel> call, Response<SignupModel> response) {
                CustomLoadingDialog.getLoader().dismissLoader();
                if (response.body().getResponse() != null) {
                    Toast.makeText(VerifyPhoneActivity.this, R.string.call_initiated_successfully, Toast.LENGTH_SHORT).show();
                    util.setInt("otp", response.body().getResponse().getOtp());
                } else {
                    if (response.body().error.getCode().equals(getResources().getString(R.string.invalid_access_token))) {
                        Constants.moveToSplash(mContext, util);
                    } else {
                        Toast.makeText(VerifyPhoneActivity.this, response.body().error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<SignupModel> call, Throwable t) {
                CustomLoadingDialog.getLoader().dismissLoader();
            }
        });
    }

    private void hitAPI() {
        CustomLoadingDialog.getLoader().showLoader(mContext);
        Call<SignupModel> call = RetrofitClient.getInstance().userVerify(util.getString("access_token", ""),
                mStringBuilder.toString());
        call.enqueue(new Callback<SignupModel>() {
            @Override
            public void onResponse(Call<SignupModel> call, Response<SignupModel> response) {
                if (response.body().getResponse() != null) {
                    CustomLoadingDialog.getLoader().dismissLoader();
                    ///
                    registerToFirebase(response.body().getResponse());

                    util.setString("access_token", response.body().getResponse().getAccess_token());
                    util.setString("email", response.body().getResponse().getEmail());
                    util.setInt("status", response.body().getResponse().getStatus());
                    util.setString("user_name", response.body().getResponse().getUsername());
                    util.setString("phone_number", response.body().getResponse().getPhone_number());
                    util.setString("country_code", response.body().getResponse().getCountry_code());
                    util.setString("profile_pic", response.body().getResponse().getUser_pic());
                    util.setInt("lender", response.body().getResponse().getLender());
                    util.setInt(Constants.ISSELLER, response.body().getResponse().getIs_seller());
                    util.setInt(Constants.ISCOMPANY, response.body().getResponse().getIs_company());
                    util.setString(Constants.VAT, response.body().getResponse().getVat());
                    util.setInt(Constants.BLOCKSTATUS, response.body().getResponse().getBlock_status());
                    util.setInt(Constants.ISGUEST, response.body().getResponse().getIs_guest());
                    util.setInt(Constants.ISEMAILSKIP, response.body().getResponse().getIs_email_skip());
                    util.setString(Constants.PRIMARY_CURRENCY, response.body().getResponse().getPrimary_currency());
                    util.setString(Constants.IS_CURRENCY_SELECTED, response.body().getResponse().getCurrency_selected());

                    util.setString(Constants.UNVERIFIED_EMAIL, response.body().getResponse().getUnverified_email());
                    util.setString(Constants.USER_LATITUDE, response.body().getResponse().getLatitude());
                    util.setString(Constants.USER_LONGITUDE, response.body().getResponse().getLongitude());

                    if (getIntent().hasExtra(Constants.REG_GUEST)) {
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        Intent inStarted = new Intent(mContext, LandingActivity.class);
                        inStarted.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        inStarted.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(inStarted);
                    }
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                } else {
                    if (response.body().error.getCode().equals(getResources().getString(R.string.invalid_access_token))) {
                        Constants.moveToSplash(mContext, util);
                    } else {
                        Toast.makeText(VerifyPhoneActivity.this, response.body().error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SignupModel> call, Throwable t) {
                Toast.makeText(VerifyPhoneActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                CustomLoadingDialog.getLoader().dismissLoader();
            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    private void registerToFirebase(SignupModel.ResponseBean response) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        ChatProfileModel users = new ChatProfileModel();
        users.setName(response.getFirst_name() + " " + response.getLast_name());
        users.setFirst_name(response.getFirst_name());
        users.setLast_name(response.getLast_name());
        users.setAccess_token(response.getAccess_token());
        users.setPush_token(FirebaseInstanceId.getInstance().getToken());
        users.setUser_id(String.valueOf(response.getId()));
        users.setProfile_pic(response.getUser_pic());
        users.setOnline_status(Constants.ONLINE);
        users.setBadge_count(Constants.OFFLINE);
        users.setBlock_status(Constants.OFFLINE);
        users.setPlatform_status(String.valueOf(Constants.PLATFORM_STATUS));
        users.setMessages_notification("1");
        util.setString(Constants.USER_LATITUDE, response.getLatitude());
        util.setString(Constants.USER_LONGITUDE, response.getLongitude());


        mDatabase.child(Constants.USER_ENDPOINT).child(String.valueOf(response.getId())).setValue(users);
    }


}
