package adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tul.R;
import com.app.tul.TulAccountDetailActivity;

import java.util.ArrayList;

import fragments.PaymentAccountFragment;
import model.CreateStripeAccountModel;
import utils.Utils;


public class PaymentAccountsAdapter extends RecyclerView.Adapter<PaymentAccountsAdapter.ViewHolder> {

    private ArrayList<CreateStripeAccountModel.ResponseBean> mAccountArrayList = new ArrayList<>();
    private PaymentAccountFragment mPaymentAccountFragment;
    private int mWidth, mHeight;
    private Context context;
    private Utils utils;
    private int prevoiusPrimary;

    public PaymentAccountsAdapter(Context context, ArrayList<CreateStripeAccountModel.ResponseBean> arrayList, PaymentAccountFragment mPaymentAccountFragment) {
        this.context = context;
        utils = new Utils(context);
        mWidth = utils.getInt("width", 0);
        mHeight = utils.getInt("height", 0);
        mAccountArrayList = arrayList;
        this.mPaymentAccountFragment = mPaymentAccountFragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment_accounts, parent, false);
        ViewHolder vhItem = new ViewHolder(v);
        return vhItem;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvName.setText(mAccountArrayList.get(position).getAccount_holder_name());
        try {
            String accountNo = mAccountArrayList.get(position).getAccount_number();
            holder.tvAccountNo.setText(accountNo.substring(accountNo.length() - 4, accountNo.length()));
        } catch (Exception e) {
            Log.e("Exce  = ", e + "");
        }

        if (mAccountArrayList.get(position).getIsPrimary() == 1) {
            holder.imgCard.setImageResource(R.mipmap.ic_radio_btn_s);
            prevoiusPrimary = position;
            holder.tvDelete.setVisibility(View.INVISIBLE);
        } else {
            holder.imgCard.setImageResource(R.mipmap.ic_radio_btn);
            holder.tvDelete.setVisibility(View.VISIBLE);
        }

        holder.llData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateStripeAccountModel.ResponseBean bankBean = mAccountArrayList.get(position);
                Intent intent = new Intent(context, TulAccountDetailActivity.class);
                intent.putExtra("data_account", bankBean);
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            }
        });

        holder.imgCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAccountArrayList.get(position).getIsPrimary() == 0)
                    mPaymentAccountFragment.updatePrimary(position, prevoiusPrimary);
            }
        });

        if (mAccountArrayList.get(position).getAmount() != null)
            holder.tvEarningCount.setText(utils.getCurrency(mAccountArrayList.get(position).getCurrency()) + " " + mAccountArrayList.get(position).getAmount());
        else
            holder.tvEarningCount.setText(utils.getCurrency(mAccountArrayList.get(position).getCurrency()) + " 0.0");

        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDiscardDialog(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAccountArrayList.size();
    }

    private void alertDiscardDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.remove_account)
                .setCancelable(false)
                .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mPaymentAccountFragment.delete(position);
                        dialog.cancel();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvAccountNo, tvStar, tvDelete, tvName, tvEarning, tvEarningCount;
        LinearLayout llMain, llEarning, llData;

        ImageView imgCard;
        View view1;

        public ViewHolder(View itemView) {
            super(itemView);

            llMain = (LinearLayout) itemView.findViewById(R.id.ll_main);

            llData = (LinearLayout) itemView.findViewById(R.id.ll_data);

            view1 = itemView.findViewById(R.id.view1);

            imgCard = (ImageView) itemView.findViewById(R.id.img_card);
            imgCard.setPadding(mWidth / 64, mWidth / 24, mWidth / 32, mWidth / 24);

            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));
            tvName.setPadding(0, mWidth / 32, 0, mWidth / 66);

            tvAccountNo = (TextView) itemView.findViewById(R.id.tv_card_no);
            tvAccountNo.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.030));

            tvStar = (TextView) itemView.findViewById(R.id.tv_star);
            tvStar.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.030));

            tvDelete = (TextView) itemView.findViewById(R.id.tv_delete);
            tvDelete.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));
            tvDelete.setPadding(mWidth / 32, mHeight / 62, mWidth / 32, mHeight / 30);

            llEarning = (LinearLayout) itemView.findViewById(R.id.ll_earning);
            llEarning.setPadding(mWidth / 30, mWidth / 100, mWidth / 30, mWidth / 100);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, mWidth / 32, 0, 0);
            llEarning.setLayoutParams(layoutParams);

            tvEarning = (TextView) itemView.findViewById(R.id.tv_earning_title);
            tvEarning.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.025));

            tvEarningCount = (TextView) itemView.findViewById(R.id.tv_earning_count);
            tvEarningCount.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));

            llMain.setPadding(0, mWidth / 32, mWidth / 32, mWidth / 32);

        }
    }


}