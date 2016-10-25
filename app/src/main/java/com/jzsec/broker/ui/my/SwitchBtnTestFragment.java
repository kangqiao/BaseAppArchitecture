package com.jzsec.broker.ui.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.jzsec.broker.R;
import com.jzsec.broker.base.BaseFragment;
import com.jzsec.broker.view.switch_btn.CheckSwitchButton;
import com.jzsec.broker.view.switch_btn.SlideSwitchView;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by zhaopan on 2016/10/25.
 * e-mail: kangqiao610@gmail.com
 */

public class SwitchBtnTestFragment  extends BaseFragment {
    private static final String TAG = "SwitchBtnTestFragment";

    private ToggleButton mTogBtn;
    private CheckSwitchButton mCheckSwithcButton;
    private CheckSwitchButton mEnableCheckSwithcButton;
    private SlideSwitchView mSlideSwitchView;

    public static SupportFragment newInstance() {
        Bundle args = new Bundle();
        SupportFragment fragment = new SwitchBtnTestFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_switch_btn, container, false);
        return view;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState) {
        mTogBtn = (ToggleButton) $(R.id.mTogBtn); // ��ȡ���ؼ�
        mTogBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    //ѡ��
                }else{
                    //δѡ��
                }
            }
        });// ��Ӽ����¼�
        mCheckSwithcButton = (CheckSwitchButton)$(R.id.mCheckSwithcButton);
        mEnableCheckSwithcButton = (CheckSwitchButton)$(R.id.mEnableCheckSwithcButton);
        mCheckSwithcButton.setChecked(false);
        mCheckSwithcButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    mEnableCheckSwithcButton.setEnabled(false);
                    mSlideSwitchView.setEnabled(false);
                }else{
                    mEnableCheckSwithcButton.setEnabled(true);
                    mSlideSwitchView.setEnabled(true);
                }
            }
        });
        mSlideSwitchView = (SlideSwitchView) $(R.id.mSlideSwitchView);
        mSlideSwitchView.setOnChangeListener(new SlideSwitchView.OnSwitchChangedListener() {

            @Override
            public void onSwitchChange(SlideSwitchView switchView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){

                }
            }
        });
    }
}
