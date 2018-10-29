package adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.tul.R;
import com.app.tul.SelectCategoryActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import model.CategoriesModel;
import utils.Constants;
import utils.Utils;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private static final int TYPE_ITEM = 1;
    private Context con;
    private Utils utils;
    ArrayList<CategoriesModel.ResponseBean> mData;
    private int mWidth, mHeight;
    String selectedData;
    SelectCategoryActivity mSelectCategoryActivity;

    public CategoryAdapter(Context con, ArrayList<CategoriesModel.ResponseBean> mData, String selectedData, SelectCategoryActivity mSelectCategoryActivity) {
        this.con = con;
        this.mData = mData;
        utils = new Utils(con);
        mWidth = utils.getInt("width", 0);
        mHeight = utils.getInt("height", 0);
        this.selectedData = selectedData;
        this.mSelectCategoryActivity = mSelectCategoryActivity;
    }

    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categories, parent, false);
        CategoryAdapter.ViewHolder vhItem = new CategoryAdapter.ViewHolder(v);
        return vhItem;
    }

    @Override
    public void onBindViewHolder(final CategoryAdapter.ViewHolder holder, final int position) {

        holder.txtCat.setText(mData.get(position).getCategory_name());

        if (selectedData.equals(mData.get(position).getCategory_name())) {
            holder.txtCat.setTextColor(ContextCompat.getColor(con, R.color.black_color));
            holder.imgTick.setVisibility(View.VISIBLE);
        } else {
            holder.txtCat.setTextColor(ContextCompat.getColor(con, R.color.hint_color_dark));
            holder.imgTick.setVisibility(View.INVISIBLE);
        }

        holder.llCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedData = mData.get(position).getCategory_name();
                mSelectCategoryActivity.mSelectedCat = selectedData;
                mSelectCategoryActivity.mSelectedCatId = mData.get(position).getId();
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtCat;
        LinearLayout llCategory;
        ImageView imgTick;

        public ViewHolder(View itemView) {
            super(itemView);

            txtCat = (TextView) itemView.findViewById(R.id.txt_cat);
            txtCat.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));
            txtCat.setPadding(0, mWidth / 24, 0, mWidth / 24);

            llCategory = (LinearLayout) itemView.findViewById(R.id.ll_category);

            imgTick = (ImageView) itemView.findViewById(R.id.img_tick);
        }
    }
}
