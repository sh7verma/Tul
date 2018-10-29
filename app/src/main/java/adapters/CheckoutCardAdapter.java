package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tul.R;
import com.app.tul.Sales.SaleCheckoutActivity;
import com.app.tul.TulCheckOutDetailsActivity;

import java.util.ArrayList;

import model.CardLocalModel;
import utils.Constants;
import utils.CryptLib;
import utils.Utils;

/**
 * Created by dev on 13/11/17.
 */

public class CheckoutCardAdapter extends RecyclerView.Adapter<CheckoutCardAdapter.ViewHolder> {

    ArrayList<CardLocalModel.ResponseBean> mArrayListCards = new ArrayList<>();
    private int mWidth, mHeight;
    private Context mContext;
    private Utils utils;
    private CryptLib cryptLib;
    private int mPreviousPosition;

    public CheckoutCardAdapter(Context context, ArrayList<CardLocalModel.ResponseBean> arrayList) {
        this.mContext = context;
        utils = new Utils(mContext);
        mWidth = utils.getInt("width", 0);
        mHeight = utils.getInt("height", 0);
        mArrayListCards = arrayList;
        try {
            cryptLib = new CryptLib();
        } catch (Exception e) {

        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checkout_cards, parent, false);
        ViewHolder vhItem = new ViewHolder(v);
        return vhItem;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        try {
            String decrypt = cryptLib.decryptSimple(mArrayListCards.get(position).getCard_number(), Constants.KEY, Constants.IV);
            holder.tvCardNo.setText(decrypt.substring(decrypt.length() - 4, decrypt.length()));
        } catch (Exception e) {
            Log.e("Exce  = ", e + "");
        }

        if (mArrayListCards.get(position).is_selected()) {
            holder.imgRadio.setImageResource(R.mipmap.ic_radio_btn_s);
            try {
                ((TulCheckOutDetailsActivity) mContext).edCVV = holder.edCvv;
            } catch (Exception e) {
                ((SaleCheckoutActivity) mContext).edCVV = holder.edCvv;
            }
            holder.edCvv.setVisibility(View.VISIBLE);
            mPreviousPosition = position;

        } else {
            holder.imgRadio.setImageResource(R.mipmap.ic_radio_btn);
            holder.edCvv.setVisibility(View.INVISIBLE);
        }

        holder.imgRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mArrayListCards.get(position).is_selected()) {
                    mArrayListCards.get(position).setIs_selected(true);
                    mArrayListCards.get(mPreviousPosition).setIs_selected(false);
                    holder.edCvv.setText("");
                    notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mArrayListCards.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCardNo, tvStar;
        LinearLayout llMain, llInner;
        ImageView imgCard;
        View view1;
        ImageView imgRadio;
        EditText edCvv;

        public ViewHolder(View itemView) {
            super(itemView);

            imgRadio = (ImageView) itemView.findViewById(R.id.img_radio);
            imgRadio.setPadding(mWidth / 64, mWidth / 64, mWidth / 64, mWidth / 64);

            llMain = (LinearLayout) itemView.findViewById(R.id.ll_main);

            llInner = (LinearLayout) itemView.findViewById(R.id.ll_inner);
            llInner.setPadding(0, 0, 0, mHeight / 20);

            view1 = itemView.findViewById(R.id.view1);

            imgCard = (ImageView) itemView.findViewById(R.id.img_card);
            imgCard.setPadding(0, mWidth / 32, mWidth / 24, 0);

            tvCardNo = (TextView) itemView.findViewById(R.id.tv_card_no);
            tvCardNo.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));
            tvCardNo.setPadding(mWidth / 64, mWidth / 36, 0, 0);

            edCvv = (EditText) itemView.findViewById(R.id.ed_cvv);
            edCvv.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));
            edCvv.setPadding(mWidth / 32, mWidth / 62, mWidth / 32, mWidth / 62);

            tvStar = (TextView) itemView.findViewById(R.id.tv_star);
            tvStar.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));
            tvStar.setPadding(0, mWidth / 32, 0, 0);

            llMain.setPadding(0, mWidth / 32, mWidth / 32, mWidth / 32);
        }
    }
}
