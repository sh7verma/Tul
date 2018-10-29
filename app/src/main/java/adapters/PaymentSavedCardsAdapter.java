package adapters;

import android.content.Context;
import android.content.DialogInterface;
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

import java.util.ArrayList;

import fragments.PaymentCardFragment;
import model.CardLocalModel;
import utils.Constants;
import utils.CryptLib;
import utils.Utils;

/**
 * Created by dev on 31/10/17.
 */

public class PaymentSavedCardsAdapter extends RecyclerView.Adapter<PaymentSavedCardsAdapter.ViewHolder> {

    ArrayList<CardLocalModel.ResponseBean> mArrayListCards = new ArrayList<>();
    PaymentCardFragment mPaymentCardFragment;
    private int mWidth, mHeight;
    private Context context;
    private Utils utils;
    private CryptLib cryptLib;

    public PaymentSavedCardsAdapter(Context context, ArrayList<CardLocalModel.ResponseBean> arrayList, PaymentCardFragment mPaymentCardFragment) {
        this.context = context;
        utils = new Utils(context);
        mWidth = utils.getInt("width", 0);
        mHeight = utils.getInt("height", 0);
        this.mPaymentCardFragment = mPaymentCardFragment;
        mArrayListCards = arrayList;
        try {
            cryptLib = new CryptLib();
        } catch (Exception e) {

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_saved_card, parent, false);
        ViewHolder vhItem = new ViewHolder(v);
        return vhItem;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        try {
            String decrypt = cryptLib.decryptSimple(mArrayListCards.get(position).getCard_number(), Constants.KEY, Constants.IV);
            holder.tvCardNo.setText(decrypt.substring(decrypt.length()-4,decrypt.length()));
        } catch (Exception e) {
            Log.e("Exce  = ", e + "");
        }

        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDiscardDialog(position);
            }
        });
        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                holder.imgRadio.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.ic_radio_btn_s));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArrayListCards.size();
    }

    private void alertDiscardDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.remove_card)
                .setCancelable(false)
                .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mPaymentCardFragment.hitDeleteAPI(mArrayListCards.get(position).getId(), position);
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

        TextView tvCardNo, tvDelete, tvStar;
        LinearLayout llMain, llInner;
        ImageView imgCard;
        View view1;
        ImageView imgRadio;

        public ViewHolder(View itemView) {
            super(itemView);

            imgRadio = (ImageView) itemView.findViewById(R.id.img_radio);
            llInner = (LinearLayout) itemView.findViewById(R.id.ll_inner);

            llMain = (LinearLayout) itemView.findViewById(R.id.ll_main);
            view1 = itemView.findViewById(R.id.view1);

            imgCard = (ImageView) itemView.findViewById(R.id.img_card);
            imgCard.setPadding(0, mWidth / 24, mWidth / 24, 0);

            tvCardNo = (TextView) itemView.findViewById(R.id.tv_card_no);
            tvCardNo.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
            tvCardNo.setPadding(0, mWidth / 32, 0, 0);

            tvStar = (TextView) itemView.findViewById(R.id.tv_star);
            tvStar.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));
            tvStar.setPadding(0, mWidth / 32, 0, 0);

            tvDelete = (TextView) itemView.findViewById(R.id.tv_delete);
            tvDelete.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));
            tvDelete.setPadding(0, mHeight / 42, 0, mHeight / 42);

            llMain.setPadding(0, mWidth / 32, mWidth / 32, mWidth / 32);
        }
    }

}
