package com.jzsec.broker.ui.my;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jzsec.broker.R;
import com.jzsec.broker.base.BaseFragment;
import com.jzsec.broker.utils.Zlog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zhaopan on 16/8/19.
 * e-mail: kangqiao610@gmail.com
 */
public class AdjustBrokerageListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG = "AdjustBrokerageListFragment";

    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(android.R.id.list)
    RecyclerView recyclerView;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            loadedData();
        }
    };

    private LinearLayoutManager mLayoutManager;
    private SampleAdapter adapter;

    public static AdjustBrokerageListFragment newInstance() {
        Bundle args = new Bundle();
        AdjustBrokerageListFragment fragment = new AdjustBrokerageListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //允许SwipeBack操作.
        setSwipeBackEnable(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adjust_brokerage_list, container, false);
        return view;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState) {
        swipeRefreshLayout.setColorSchemeResources(R.color.color_4bae4f, R.color.color_005eaa, R.color.color_512da8, R.color.color_cd3e3a);
        swipeRefreshLayout.setOnRefreshListener(this);
        // 这句话是为了，第一次进入页面的时候显示加载进度条
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));

/*        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                Zlog.e(TAG, "onScrollStateChanged() newState="+newState);
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == adapter.getItemCount()) { //上拉刷新.
                    Zlog.e(TAG, "setRefreshing begin");
                    swipeRefreshLayout.setRefreshing(true);
                    // 此处在现实项目中，请换成网络请求数据代码，sendRequest .....
                    handler.sendEmptyMessageDelayed(0, 3000);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                Zlog.e(TAG, "onScrolled() dx="+dx+", dy="+dy);
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });*/

        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new SampleAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void loadedData() {
        Zlog.e(TAG, "loadedData() after");
        adapter.getList().clear();
        for(int i = 0; i < 20; i++) {
            adapter.getList().add(i);
        }
        adapter.notifyDataSetChanged();
        Zlog.e(TAG, "setRefreshing end");
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        Zlog.e(TAG, "onRefresh()");
        handler.sendEmptyMessageDelayed(0, 3000);
    }

    static class SampleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<Integer> list;

        private static final int TYPE_ITEM = 0;
        private static final int TYPE_FOOTER = 1;

        public List<Integer> getList() {
            return list;
        }

        public SampleAdapter() {
            list = new ArrayList<Integer>();
        }

        // RecyclerView的count设置为数据总条数+ 1（footerView）
        @Override
        public int getItemCount() {
            return list.size() + 1;
        }

        @Override
        public int getItemViewType(int position) {
            // 最后一个item设置为footerView
            if (position + 1 == getItemCount()) {
                return TYPE_FOOTER;
            } else {
                return TYPE_ITEM;
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            if (holder instanceof ItemViewHolder) {
                ((ItemViewHolder) holder).textView.setText(String.valueOf(list.get(position)));
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_ITEM) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content_view, null);
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                return new ItemViewHolder(view);
            }
            // type == TYPE_FOOTER 返回footerView
            else if (viewType == TYPE_FOOTER) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer_view, null);
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                return new FooterViewHolder(view);
            }

            return null;
        }

        class FooterViewHolder extends RecyclerView.ViewHolder {
            public FooterViewHolder(View view) {
                super(view);
            }
        }

        class ItemViewHolder extends RecyclerView.ViewHolder {
            TextView textView;

            public ItemViewHolder(View view) {
                super(view);
                textView = (TextView) view.findViewById(R.id.tv_content);
            }
        }
    }
}
