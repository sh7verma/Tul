package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.tul.R;

import java.util.ArrayList;

import model.SalesPriceFee;
import utils.Constants;
import utils.Utils;

/**
 * Created by dev on 4/9/18.
 */

public class SalesInfoAdapter extends RecyclerView.Adapter<SalesInfoAdapter.ViewHolder> {

    ArrayList<SalesPriceFee.ResponseBean> mData;
    private Context con;
    private Utils utils;
    private int mWidth, mHeight;

    public SalesInfoAdapter(Context con, ArrayList<SalesPriceFee.ResponseBean> mData) {
        this.con = con;
        this.mData = mData;
        utils = new Utils(con);
        mWidth = utils.getInt("width", 0);
        mHeight = utils.getInt("height", 0);
    }

    @Override
    public SalesInfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sales_info, parent, false);
        ViewHolder vhItem = new ViewHolder(v);
        return vhItem;
    }

    @Override
    public void onBindViewHolder(final SalesInfoAdapter.ViewHolder holder, final int position) {
        holder.txtRange.setText(utils.getCurrency(utils.getString(Constants.PRIMARY_CURRENCY, "")) + mData.get(position).getMin_price() + " - "
                + mData.get(position).getMax_price());
        holder.txtPercentage.setText(mData.get(position).getTransaction_percentage() + "%");
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtRange, txtPercentage;

        public ViewHolder(View itemView) {
            super(itemView);
            txtRange = (TextView) itemView.findViewById(R.id.txt_range);
            txtPercentage = (TextView) itemView.findViewById(R.id.txt_percentage);

        }
    }


}
