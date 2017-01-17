package com.jzsec.broker.ui.test;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jzsec.broker.R;
import com.jzsec.broker.base.BaseFragment;
import com.jzsec.broker.view.layout.flow.FlowTagLayout;
import com.jzsec.broker.view.layout.flow.OnInitSelectedPosition;
import com.jzsec.broker.view.layout.flow.OnTagClickListener;
import com.jzsec.broker.view.layout.flow.OnTagSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by zhaopan on 2016/10/25.
 * e-mail: kangqiao610@gmail.com
 */

public class TestLayoutFragment extends BaseFragment {
    private static final String TAG = "TestLayoutFragment";

    @BindView(R.id.color_flow_layout)
    FlowTagLayout mColorFlowTagLayout;
    @BindView(R.id.size_flow_layout)
    FlowTagLayout mSizeFlowTagLayout;
    @BindView(R.id.mobile_flow_layout)
    FlowTagLayout mMobileFlowTagLayout;
    private TagAdapter<String> mSizeTagAdapter;
    private TagAdapter<String> mColorTagAdapter;
    private TagAdapter<String> mMobileTagAdapter;

    public static SupportFragment newInstance() {
        Bundle args = new Bundle();
        SupportFragment fragment = new TestLayoutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_layout, container, false);
        return view;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState) {
        //颜色
        mColorTagAdapter = new TagAdapter<>(_mActivity);
        mColorFlowTagLayout.setAdapter(mColorTagAdapter);
        mColorFlowTagLayout.setOnTagClickListener(new OnTagClickListener() {
            @Override
            public void onItemClick(FlowTagLayout parent, View view, int position) {
                Snackbar.make(view, "颜色:" + parent.getAdapter().getItem(position), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //尺寸
        mSizeTagAdapter = new TagAdapter<>(_mActivity);
        mSizeFlowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        mSizeFlowTagLayout.setAdapter(mSizeTagAdapter);
        mSizeFlowTagLayout.setOnTagSelectListener(new OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                if (selectedList != null && selectedList.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (int i : selectedList) {
                        sb.append(parent.getAdapter().getItem(i));
                        sb.append(":");
                    }
                    Snackbar.make(parent, "移动研发:" + sb.toString(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                    Snackbar.make(parent, "没有选择标签", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        //移动研发标签
        mMobileTagAdapter = new TagAdapter<>(_mActivity);
        mMobileFlowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
        mMobileFlowTagLayout.setAdapter(mMobileTagAdapter);
        mMobileFlowTagLayout.setOnTagSelectListener(new OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                if (selectedList != null && selectedList.size() > 0) {
                    StringBuilder sb = new StringBuilder();

                    for (int i : selectedList) {
                        sb.append(parent.getAdapter().getItem(i));
                        sb.append(":");
                    }
                    Snackbar.make(parent, "移动研发:" + sb.toString(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                    Snackbar.make(parent, "没有选择标签", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        initColorData();

        initSizeData();

        initMobileData();

        //清除所有已经被选择的选项
        $(R.id.bt_clear_all)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        mColorFlowTagLayout.clearAllOption();
                        mSizeFlowTagLayout.clearAllOption();
                        mMobileFlowTagLayout.clearAllOption();
                    }
                });
    }

    private void initMobileData() {
        List<String> dataSource = new ArrayList<>();
        dataSource.add("android");
        dataSource.add("安卓");
        dataSource.add("SDK源码");
        dataSource.add("IOS");
        dataSource.add("iPhone");
        dataSource.add("游戏");
        dataSource.add("fragment");
        dataSource.add("viewcontroller");
        dataSource.add("cocoachina");
        dataSource.add("移动研发工程师");
        dataSource.add("移动互联网");
        dataSource.add("高薪+期权");
        mMobileTagAdapter.onlyAddAll(dataSource);
    }

    private void initColorData() {
        List<String> dataSource = new ArrayList<>();
        dataSource.add("红色");
        dataSource.add("黑色");
        dataSource.add("花边色");
        dataSource.add("深蓝色");
        dataSource.add("白色");
        dataSource.add("玫瑰红色");
        dataSource.add("紫黑紫兰色");
        dataSource.add("葡萄红色");
        dataSource.add("屎黄色");
        dataSource.add("绿色");
        dataSource.add("彩虹色");
        dataSource.add("牡丹色");
        mColorTagAdapter.onlyAddAll(dataSource);
    }

    /**
     * 初始化数据
     */
    private void initSizeData() {
        List<String> dataSource = new ArrayList<>();
        dataSource.add("28 (2.1尺)");
        dataSource.add("29 (2.2尺)");
        dataSource.add("30 (2.3尺)");
        dataSource.add("31 (2.4尺)");
        dataSource.add("32 (2.5尺)........");
        dataSource.add("33 (2.6尺)");
        dataSource.add("34 (2.7尺)");
        dataSource.add("35 (2.8尺)");
        dataSource.add("36 (2.9尺)");
        dataSource.add("37 (3.0尺)");
        dataSource.add("38 (3.1尺)");
        dataSource.add("39 (3.2尺)........");
        mSizeTagAdapter.onlyAddAll(dataSource);
    }

    public class TagAdapter<T> extends BaseAdapter implements OnInitSelectedPosition {

        private final Context mContext;
        private final List<T> mDataList;

        public TagAdapter(Context context) {
            this.mContext = context;
            mDataList = new ArrayList<>();
        }

        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return mDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = LayoutInflater.from(mContext).inflate(R.layout.item_flow_tag, null);

            TextView textView = (TextView) view.findViewById(R.id.tv_tag);
            T t = mDataList.get(position);

            if (t instanceof String) {
                textView.setText((String) t);
            }
            return view;
        }

        public void onlyAddAll(List<T> datas) {
            mDataList.addAll(datas);
            notifyDataSetChanged();
        }

        public void clearAndAddAll(List<T> datas) {
            mDataList.clear();
            onlyAddAll(datas);
        }

        @Override
        public boolean isSelectedPosition(int position) {
            if (position % 2 == 0) {
                return true;
            }
            return false;
        }
    }
}
