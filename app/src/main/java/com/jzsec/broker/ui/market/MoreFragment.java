package com.jzsec.broker.ui.market;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.library.BaseRecyclerAdapter;
import com.github.library.BaseViewHolder;
import com.github.library.listener.RequestLoadMoreListener;
import com.jzsec.broker.R;
import com.jzsec.broker.base.BaseLazyFragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by zhaopan on 16/7/30.
 * e-mail: kangqiao610@gmail.com
 */
public class MoreFragment extends BaseLazyFragment implements SwipeRefreshLayout.OnRefreshListener, RequestLoadMoreListener {

    //delayMillis
    private static final int DELAY_MILLIS = 1500;
    private static final int TOTAL_COUNT = 15;

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;

    private BaseRecyclerAdapter<String> mAdapter;
    private Handler mHandler = new Handler();

    public static SupportFragment newInstance(Bundle bundle) {
        Bundle arg = new Bundle();
        if (null != bundle) MarketHelper.transferMarketParam(arg, bundle);
        SupportFragment fragment = new MoreFragment();
        fragment.setArguments(arg);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more_info, container, false);
        return view;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState) {
        mRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mRecyclerView.setAdapter(mAdapter = new BaseRecyclerAdapter<String>(getContext(), getItemDatas(), R.layout.rv_item) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.tv_item_text, item);
            }
        });

        addHeaderView();
        openLoadMore();
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {

    }

    private void addHeaderView() {
        View headerView = LayoutInflater.from(getContext()).inflate(R.layout.rv_header, null);
        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mAdapter.addHeaderView(headerView);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "your click headerView", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void openLoadMore() {
        mAdapter.openLoadingMore(true);
        mAdapter.setOnLoadMoreListener(this);
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.setData(getItemDatas());
                mRefreshLayout.setRefreshing(false);
            }
        }, DELAY_MILLIS);
    }

    @Override
    public void onLoadMoreRequested() {

        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                if (mAdapter.getData().size() >= TOTAL_COUNT) {
                    mAdapter.notifyDataChangeAfterLoadMore(false);
                    mAdapter.addNoMoreView();
                } else {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.notifyDataChangeAfterLoadMore(addDatas(), true);
                        }
                    }, DELAY_MILLIS);
                }
            }
        });

    }

    public static List<String> getItemDatas() {
        List<String> mList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            mList.add("欢迎关注康桥zp");
        }
        return mList;
    }

    public static List<String> addDatas() {
        List<String> mList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            mList.add("我是新增条目" + (i + 1));
        }
        return mList;
    }

}
