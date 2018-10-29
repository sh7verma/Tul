package fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.tul.InboxActivity;
import com.app.tul.R;

import adapters.InboxNotificationAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import database.Db;
import utils.Utils;


/**
 * Created by dev on 1/12/17.
 */

public class InboxNotificationFragment extends Fragment implements View.OnClickListener {


    View itemView;
    Context mContext;
    Utils util;
    int mWidth, mHeight;

    @BindView(R.id.rv_notification)
    RecyclerView rvNotifications;
    @BindView(R.id.txt_no_notification)
    TextView txtNotifications;
    LinearLayoutManager mLayoutManager;
    Db db;
    InboxActivity mInboxActivity;
    private int mOffset = 0;
    private boolean isLoading;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        itemView = inflater.inflate(R.layout.fragment_notifications, container, false);
        ButterKnife.bind(InboxNotificationFragment.this, itemView);
        mContext = getActivity();
        util = new Utils(mContext);
        db = new Db(mContext);
        mWidth = util.getInt("width", 0);
        mHeight = util.getInt("height", 0);
        mInboxActivity = (InboxActivity) getActivity();
        initUI();
        initListener();
        return itemView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    private void initUI() {
        mLayoutManager = new LinearLayoutManager(mContext);
        rvNotifications.setLayoutManager(mLayoutManager);
    }

    private void initListener() {

    }

    @Override
    public void onClick(View v) {

    }
}
