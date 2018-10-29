package fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tul.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import database.Db;
import model.BookTulModel;
import model.LentTulModel;
import utils.Constants;
import utils.Utils;

/**
 * Created by dev on 15/11/17.
 */

public class HistoryReceiptFragment extends Fragment implements View.OnClickListener {

    View itemView;
    Context mContext;
    Utils util;
    int mWidth, mHeight;
    Db db;

    @BindView(R.id.ll_main)
    LinearLayout llMain;

    @BindView(R.id.ll_cancellation_charges)
    LinearLayout llCancellationCharges;
    @BindView(R.id.tv_tul_cancel)
    TextView tvTulCancel;
    @BindView(R.id.tv_cancel_charges_title)
    TextView tvCancelChargesTitle;

    @BindView(R.id.ll_tul_charges)
    LinearLayout llCharges;
    @BindView(R.id.tv_tul_charges_title)
    TextView tvChargesTitle;
    @BindView(R.id.tv_tul_charges)
    TextView tvCharges;

    @BindView(R.id.ll_tul_security_charges)
    LinearLayout llSecurityCharges;
    @BindView(R.id.tv_tul_security_charges_title)
    TextView tvSecurityChargesTitle;
    @BindView(R.id.tv_tul_security_charges)
    TextView tvSecurityCharges;

    @BindView(R.id.ll_tul_extra)
    LinearLayout llExtraCharges;
    @BindView(R.id.tv_tul_extra_title)
    TextView tvExtraTitle;
    @BindView(R.id.tv_tul_extra)
    TextView tvtulsExtraCharges;

    @BindView(R.id.ll_tul_delivery)
    LinearLayout llDelivery;
    @BindView(R.id.tv_tul_delivery_title)
    TextView tvDeliveryTitle;
    @BindView(R.id.tv_tul_delivery)
    TextView tvtulsDelivery;

    @BindView(R.id.ll_tul_discount)
    LinearLayout llDiscount;
    @BindView(R.id.tv_tul_discount_title)
    TextView tvDiscountTitle;
    @BindView(R.id.tv_tul_discount)
    TextView tvDiscount;

    @BindView(R.id.ll_tul_gross_total)
    LinearLayout llGrossTotal;
    @BindView(R.id.tv_tul_gross_total_title)
    TextView tvGrossTotalTitle;
    @BindView(R.id.tv_tul_gross_total)
    TextView tvGrossTotal;

    @BindView(R.id.ll_transaction_charges)
    LinearLayout llTransactionCharges;
    @BindView(R.id.tv_transaction_charges_title)
    TextView tvTransactionChargesTitle;
    @BindView(R.id.tv_transaction_charges)
    TextView tvTransactionCharges;

    @BindView(R.id.ll_tul_security_refunded)
    LinearLayout llSecurityRefunded;
    @BindView(R.id.tv_tul_security_refunded_title)
    TextView tvSecurityRefundedTitle;
    @BindView(R.id.tv_tul_security_refunded)
    TextView tvSecurityRefunded;

    @BindView(R.id.ll_tul_total_price)
    LinearLayout llTotalPrice;
    @BindView(R.id.tv_tul_total_price_title)
    TextView tvTotalPriceTitle;
    @BindView(R.id.tv_tul_total_price)
    TextView tvTotalPrice;

    @BindView(R.id.ll_other_charges)
    LinearLayout llOtherCharges;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        itemView = inflater.inflate(R.layout.fragment_tul_receipt, container, false);
        ButterKnife.bind(HistoryReceiptFragment.this, itemView);
        mContext = getActivity();
        util = new Utils(mContext);
        mWidth = util.getInt("width", 0);
        mHeight = util.getInt("height", 0);
        db = new Db(mContext);
        initUI();
        initListener();
        return itemView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    public void setdata(BookTulModel.ResponseBean mTulModel, int path) {
        /// path = 1 for borrower
        /// path = 2 for lender
        if (mTulModel.getBorrower_status() == 3) {
            llCancellationCharges.setVisibility(View.VISIBLE);
            llOtherCharges.setVisibility(View.GONE);
            if (path == 1) {
                /// calculate cancellation charges
                double percentage = Double.parseDouble(mTulModel.getCancel_percentage());
                double price = Double.parseDouble(mTulModel.getPrice());
                double cancellationCharges = (price * percentage) / 100;
                tvTulCancel.setText(mTulModel.getCurrency() + " " + String.format("%.2f", cancellationCharges));
                ///
            } else {
                tvTulCancel.setVisibility(View.GONE);
                tvCancelChargesTitle.setText(R.string.borrower_cancelled_tull);
            }
        } else if (mTulModel.getLender_status() == 3) {
            llCancellationCharges.setVisibility(View.VISIBLE);
            llOtherCharges.setVisibility(View.GONE);
            tvTulCancel.setVisibility(View.GONE);
            if (path == 2) {
                tvCancelChargesTitle.setText(R.string.rating_reduced);
            } else {
                tvCancelChargesTitle.setText(R.string.lender_canceled_tull);
            }
        } else {
            llCancellationCharges.setVisibility(View.GONE);
            llOtherCharges.setVisibility(View.VISIBLE);
        }

       /* if (TextUtils.isEmpty(mTulModel.getAdditional_charges().getFee())||mTulModel.getAdditional_charges().getFee().equals("0.00")) {
//            mTulModel.getAdditional_charges().setFee(String.valueOf(0.00));
        } else {
            mTulModel.getAdditional_charges().setFee(String.valueOf(0.00));
        }*/

        if (mTulModel.getAdditional_charges().getSecurity_charges().equals("")) {
            mTulModel.getAdditional_charges().setSecurity_charges(String.valueOf(0));
        }
        double securityRefundedCharges = 0;

        if (mTulModel.getRefund_status() == 1) {
            securityRefundedCharges = Double.parseDouble(mTulModel.getAdditional_charges().getSecurity_charges());
        } else {
            securityRefundedCharges = 0;
        }

        String dates[] = null;
        if (mTulModel.getSelected_date().contains(",")) {
            dates = mTulModel.getSelected_date().split(",");
        }
        int countDays;
        if (dates != null)
            countDays = dates.length;
        else
            countDays = 1;

        double tulCharges = countDays * mTulModel.getQuantity() * Double.parseDouble(mTulModel.getPrice());

        double grossTotal;
        if (path == 2) {
            /// lender
            tvTransactionChargesTitle.setText("Less Service Fee (" + String.format("%.2f", Double.parseDouble(mTulModel.getTransaction_percentage())) + "%)");
            if (mTulModel.getExtra_charges() == 1)
                grossTotal = tulCharges - Double.parseDouble(mTulModel.getDiscount()) +
                        Double.parseDouble(mTulModel.getAdditional_charges().getSecurity_charges()) +
                        Double.parseDouble(mTulModel.getAdditional_charges().getFee()) +
                        Double.parseDouble(mTulModel.getDelivery_cost()) - Double.parseDouble(mTulModel.getTransaction_fee());
            else
                grossTotal = tulCharges - Double.parseDouble(mTulModel.getDiscount()) +
                        Double.parseDouble(mTulModel.getAdditional_charges().getSecurity_charges()) +
                        Double.parseDouble(mTulModel.getDelivery_cost()) - Double.parseDouble(mTulModel.getTransaction_fee());
        } else {
            /// borrower
            llTransactionCharges.setVisibility(View.GONE);
            if (mTulModel.getExtra_charges() == 1)
                grossTotal = tulCharges - Double.parseDouble(mTulModel.getDiscount()) +
                        Double.parseDouble(mTulModel.getAdditional_charges().getSecurity_charges()) +
                        Double.parseDouble(mTulModel.getAdditional_charges().getFee()) +
                        Double.parseDouble(mTulModel.getDelivery_cost());
            else
                grossTotal = tulCharges - Double.parseDouble(mTulModel.getDiscount()) +
                        Double.parseDouble(mTulModel.getAdditional_charges().getSecurity_charges()) +
                        Double.parseDouble(mTulModel.getDelivery_cost());
        }

        double total;
        if (mTulModel.getRefund_status() == 1)
            total = grossTotal - securityRefundedCharges;
        else
            total = grossTotal + securityRefundedCharges;


        tvCharges.setText(mTulModel.getCurrency() + " " + String.format("%.2f", tulCharges));

        tvDiscount.setText(mTulModel.getCurrency() + " " + mTulModel.getDiscount());

        tvTransactionCharges.setText(mTulModel.getCurrency() + " " + mTulModel.getTransaction_fee());

        tvSecurityCharges.setText(mTulModel.getCurrency() + " " +
                String.format("%.2f", Double.parseDouble(mTulModel.getAdditional_charges().getSecurity_charges())));

        if (mTulModel.getExtra_charges() == 1)
            tvtulsExtraCharges.setText(mTulModel.getCurrency() + " " +
                    String.format("%.2f", Double.parseDouble(mTulModel.getAdditional_charges().getFee())));
        else
            tvtulsExtraCharges.setText("0.00");

        tvtulsDelivery.setText(mTulModel.getCurrency() + " " +
                String.format("%.2f", Double.parseDouble(mTulModel.getDelivery_cost())));

        tvGrossTotal.setText(mTulModel.getCurrency() + " " + String.format("%.2f", grossTotal));

        tvSecurityRefunded.setText(mTulModel.getCurrency() + " " + String.format("%.2f", securityRefundedCharges));

        tvTotalPrice.setText(mTulModel.getCurrency() + " " + String.format("%.2f", total));

    }

    private void initUI() {

        llMain.setPadding(mWidth / 32, mHeight / 32, mWidth / 32, mHeight / 32);

        tvCharges.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvChargesTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

        tvTulCancel.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvCancelChargesTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

        llSecurityCharges.setPadding(0, mHeight / 32, 0, 0);
        tvSecurityCharges.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvSecurityChargesTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

        llCancellationCharges.setPadding(0, 0, 0, mHeight / 32);

        llExtraCharges.setPadding(0, mHeight / 32, 0, 0);
        tvtulsExtraCharges.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvExtraTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

        llDelivery.setPadding(0, mHeight / 32, 0, mHeight / 32);
        tvtulsDelivery.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvDeliveryTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

        llDiscount.setPadding(0, mHeight / 32, 0, 0);
        tvDiscountTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvDiscount.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

        llGrossTotal.setPadding(0, mHeight / 32, 0, 0);
        tvGrossTotalTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvGrossTotal.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

        llSecurityRefunded.setPadding(0, mHeight / 32, 0, mHeight / 32);
        tvSecurityRefundedTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvSecurityRefunded.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

        llTransactionCharges.setPadding(0, 0, 0, mHeight / 32);
        tvTransactionCharges.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvTransactionChargesTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

        llTotalPrice.setPadding(0, mHeight / 32, 0, 0);
        tvTotalPriceTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));
        tvTotalPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

    }

    private void initListener() {

    }

    @Override
    public void onClick(View v) {

    }
}
