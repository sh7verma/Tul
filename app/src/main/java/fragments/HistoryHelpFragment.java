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

import com.app.tul.R;

import java.util.ArrayList;

import adapters.HelpAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import database.Db;
import utils.Utils;

/**
 * Created by dev on 15/11/17.
 */

public class HistoryHelpFragment extends Fragment implements View.OnClickListener {

    View itemView;
    Context mContext;
    Utils util;
    int mWidth, mHeight;
    Db db;
    @BindView(R.id.rv_help)
    RecyclerView rvHelp;
    LinearLayoutManager mLayoutManager;
    HelpAdapter mAdapter;
    ArrayList<String> mContentArrayList=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        itemView = inflater.inflate(R.layout.fragment_tul_help, container, false);
        ButterKnife.bind(HistoryHelpFragment.this, itemView);
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
        mContentArrayList.add("In case of any queries/disputes, you can contact at  info@thetulapp.com.");
        mContentArrayList.add("For more information, please visit our website at http://thetulapp.com/");
        super.onActivityCreated(savedInstanceState);
    }

    private void initUI() {
        mAdapter = new HelpAdapter(mContext,mContentArrayList);
        mLayoutManager = new LinearLayoutManager(mContext);
        rvHelp.setLayoutManager(mLayoutManager);
        rvHelp.setAdapter(mAdapter);
        rvHelp.setPadding(mWidth / 32, 0, mWidth / 32, 0);
//        rvHelp.setNestedScrollingEnabled(false);
    }

    private void initListener() {

    }

    @Override
    public void onClick(View v) {

    }
}

