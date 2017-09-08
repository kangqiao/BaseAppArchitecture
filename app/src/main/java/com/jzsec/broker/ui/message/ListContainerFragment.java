package com.jzsec.broker.ui.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jzsec.broker.R;
import com.jzsec.broker.base.BaseFragment;
import com.jzsec.broker.utils.Zlog;
import com.jzsec.broker.view.layout.ListContainerLayout;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by zhaopan on 2017/9/7.
 */

public class ListContainerFragment extends BaseFragment{
    private static final String TAG = "ListContainerFragment";

    @BindView(R.id.list_layout_1)
    public ListContainerLayout listLayout1;
    @BindView(R.id.list_layout_2)
    public ListContainerLayout listLayout2;

    ListContainerLayout.ItemViewCreator titleViewCreator;

    public static SupportFragment newInstance() {
        Bundle args = new Bundle();
        SupportFragment fragment = new ListContainerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_container_layout, container, false);
    }

    @Override
    protected void onBindView(Bundle savedInstanceState) {
        Zlog.d(TAG, "onBindView");
        titleViewCreator = new TitleItemViewCreator();

        listLayout1.addItemView(titleViewCreator, "我是ListcontainerLayout的Item1");
        listLayout1.addItemView(titleViewCreator, "我是ListcontainerLayout的Item11");
        listLayout1.addItemView(titleViewCreator, "我是ListcontainerLayout的Item111");

        List<String> list = Arrays.asList("item-title2", "item-title22", "2item-title222");
        listLayout2.setItemViewCreator(titleViewCreator);
        listLayout2.setDataList(list);
    }


    public static class TitleItemViewCreator extends ListContainerLayout.ItemViewCreator<String>{

        @Override
        protected View onCreateItemView(LayoutInflater inflater, ViewGroup container) {
            return inflater.inflate(R.layout.item_view_list_container_1, container, false);
        }

        @Override
        protected void setData(String title) {
            TextView tvTitle = findView(R.id.tv_title);
            tvTitle.setText(title);
        }
    }
}
