package com.jzsec.broker.ui.test;

import android.widget.CheckBox;
import android.widget.CompoundButton;

/**
 * Created by zhaopan on 2017/6/16.
 * e-mail: kangqiao610@gmail.com
 * 复选框改成单选框
 */

public class CheckBoxToSingle {

    CheckBox curCheckBox = null;
    Object mCurSelectBean = null;
    private void testSingle(){
        final Object item = null;
        final CheckBox cbContract = null;
        cbContract.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(null != curCheckBox) curCheckBox.setChecked(mCurSelectBean == item);//如果有选择过了, 要先清除掉.
                    curCheckBox = cbContract;
                    mCurSelectBean = item;
                    //setRepayMoney(mCurSelectBean.getNoRepayment()+"");
                    //tvRepayTotal.setText(null == mCurSelectBean? "0.00": Arith.formatNumber(mCurSelectBean.getNoRepayment()));
                } else if(mCurSelectBean == item){ //如果第二次选择时, 说明取消了. 所以置为空.
                    mCurSelectBean = null;
                    //setRepayMoney("");
                    //tvRepayTotal.setText("0.00");
                    curCheckBox = null;
                }
            }
        });
        //cbContract.setChecked(selectContract.contains(item));
        cbContract.setChecked(mCurSelectBean == item);
    }
}
