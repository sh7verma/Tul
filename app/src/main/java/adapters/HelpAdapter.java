package adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.tul.R;

import java.net.URL;
import java.util.ArrayList;

import utils.Utils;

/**
 * Created by dev on 15/11/17.
 */

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.ViewHolder> {

    private Context con;
    private Utils utils;
    private int mWidth, mHeight;
    ArrayList<String> mContentArrayList;

    public HelpAdapter(Context con, ArrayList<String> mContentArrayList) {
        this.con = con;
        utils = new Utils(con);
        mWidth = utils.getInt("width", 0);
        mHeight = utils.getInt("height", 0);
        this.mContentArrayList = mContentArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_help, parent, false);
        ViewHolder vhItem = new ViewHolder(v);
        return vhItem;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvHelp.setText(mContentArrayList.get(position));
        holder.tvHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:"));
                    String[] recipients = {"info@thetulapp.com"};
                    intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                    intent.putExtra(Intent.EXTRA_SUBJECT, "DISPUTE");
                    intent.putExtra(Intent.EXTRA_TEXT, "");
                    con.startActivity(intent);
                } else if (position == 1) {
                    Uri uri = Uri.parse("http://www.thetulapp.com/");
                    Intent inTerms = new Intent(Intent.ACTION_VIEW, uri);
                    con.startActivity(inTerms);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mContentArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvHelp;

        public ViewHolder(View itemView) {
            super(itemView);

            tvHelp = (TextView) itemView.findViewById(R.id.tv_help);
            tvHelp.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.045));
            tvHelp.setPadding(0, mHeight / 32, 0, mHeight / 32);

        }
    }
}
