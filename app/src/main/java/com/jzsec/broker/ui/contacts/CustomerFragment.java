package com.jzsec.broker.ui.contacts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jzsec.broker.R;
import com.jzsec.broker.base.BaseFragment;
import com.jzsec.broker.data.entity.Customer;

import butterknife.BindView;


/**
 * Created by zhaopan on 16/7/28.
 * e-mail: kangqiao610@gmail.com
 */
public class CustomerFragment extends BaseFragment {
    private static final String PARAM_CUSTOMER = "param_customer";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_message)
    TextView tvMessage;

    private Customer mCustomer;

    public static CustomerFragment newInstance(Customer customer) {
        Bundle args = new Bundle();
        args.putParcelable(PARAM_CUSTOMER, customer);
        CustomerFragment fragment = new CustomerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCustomer = getArguments().getParcelable(PARAM_CUSTOMER);
        //允许SwipeBack操作.
        setSwipeBackEnable(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_detail, container, false);
        return attachToSwipeBack(view);
    }

    @Override
    protected void onBindView(Bundle savedInstanceState) {
        toolbar.setTitle(mCustomer.name);
        initToolbarNav(toolbar);
    }

    @Override
    protected void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        // 入场动画结束后执行  优化,防动画卡顿
        tvName.setText(mCustomer.name);
        tvMessage.setText(mCustomer.message);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //mRecy.setAdapter(null);
        //_mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //hideSoftInput();
    }
}
