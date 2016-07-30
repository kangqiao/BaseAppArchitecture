package com.jzzq.broker.ui.contacts;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jzzq.broker.R;
import com.jzzq.broker.base.BaseLazyFragment;
import com.jzzq.broker.base.listener.OnItemClickListener;
import com.jzzq.broker.data.entity.Customer;
import com.jzzq.broker.ui.MainActivity2;
import com.jzzq.broker.ui.event.StartBrotherEvent;
import com.jzzq.broker.ui.event.TabSelectedEvent;
import com.jzzq.broker.ui.main.MainFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zhaopan on 16/7/27.
 * e-mail: kangqiao610@gmail.com
 */
public class ContactsFragment extends BaseLazyFragment implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private boolean mInAtTop = true;
    private int mScrollTotal;

    private CustomerAdapter mAdapter;

    public static ContactsFragment newInstance() {
        Bundle args = new Bundle();
        ContactsFragment fragment = new ContactsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts_list, container, false);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState) {
        toolbar.setTitle("联系人");
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {
        refreshLayout.setOnRefreshListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        recyclerView.setHasFixedSize(true);
        final int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.5f, getResources().getDisplayMetrics());
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0, 0, 0, space);
            }
        });
        mAdapter = new CustomerAdapter(_mActivity);
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mScrollTotal += dy;
                if (mScrollTotal <= 0) {
                    mInAtTop = true;
                } else {
                    mInAtTop = false;
                }
            }
        });

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {
                // 因为启动的MsgFragment是MainFragment的兄弟Fragment,所以需要MainFragment.start()

                // 这里我使用EventBus通知父MainFragment处理跳转(接耦),
                EventBus.getDefault().post(new StartBrotherEvent(CustomerFragment.newInstance(mAdapter.getCustomer(position))));

                // 也可以像使用getParentFragment()的方式,拿到父Fragment的引用来操作 (不建议)
//              ((MainMarketInfoFragment) getParentFragment()).startMsgBrother(MsgFragment.newInstance());
            }
        });

        List<Customer> chatList = initDatas();
        mAdapter.setDataList(chatList);
    }

    private List<Customer> initDatas() {
        List<Customer> msgList = new ArrayList<>();

        String[] name = new String[]{"小黄", "小李", "小张", "老王", "小马"};
        String[] chats = new String[]{"小黄的消息", "小李的消息", "小张的消息", "老王的消息", "小马的消息"};

        for (int i = 0; i < 15; i++) {
            int index = (int) (Math.random() * 5);
            Customer chat = new Customer();
            chat.name = name[index];
            chat.message = chats[index];
            msgList.add(chat);
        }
        return msgList;
    }

    @Override
    public void onRefresh() {
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        }, 2500);
    }


    /**
     * Reselected Tab
     */
    @Subscribe
    public void onTabSelectedEvent(TabSelectedEvent event) {
        if (event.position != MainFragment.SECOND) return;

        if (mInAtTop) {
            refreshLayout.setRefreshing(true);
            onRefresh();
        } else {
            scrollToTop();
        }
    }

    private void scrollToTop() {
        recyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(null != recyclerView) recyclerView.setAdapter(null);
        EventBus.getDefault().unregister(this);
    }
}
