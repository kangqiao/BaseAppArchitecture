package com.jzsec.broker.ui.market;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.jzsec.broker.R;
import com.jzsec.broker.utils.ZPopupWindow;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhaopan on 16/9/3.
 * e-mail: kangqiao610@gmail.com
 */
public class SelectComplexRightPopupWin extends ZPopupWindow<Integer> {
    @BindView(R.id.tv_ex_right)
    TextView tvExRight;
    @BindView(R.id.tv_complex_after_right)
    TextView tvAfterRight;
    @BindView(R.id.tv_complex_before_right)
    TextView tvBeforeRight;

    SelectComplexRightListener listener;

    public interface SelectComplexRightListener{
        void changeComplexRight(int type);
    }

    public SelectComplexRightPopupWin(Activity context, SelectComplexRightListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected View onCreatePopupView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.pop_complex_right, null, false);
    }

    @Override
    protected void initView() {
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setOutsideTouchable(true);
        ButterKnife.bind(this, contentView);
    }

    @OnClick({R.id.tv_ex_right, R.id.tv_complex_after_right, R.id.tv_complex_before_right})
    void clickComplexRightTab(View view){
        int complexRightType = MarketHelper.TYPE_COMPLEX_RiGHT_NONE;
        switch (view.getId()) {
            case R.id.tv_ex_right:
                complexRightType = MarketHelper.TYPE_COMPLEX_EX_RiGHT;
                break;
            case R.id.tv_complex_after_right:
                complexRightType = MarketHelper.TYPE_COMPLEX_AFTER_RiGHT;
                break;
            case R.id.tv_complex_before_right:
                complexRightType = MarketHelper.TYPE_COMPLEX_BEFORE_RIGHT;
                break;
        }
        if(null != listener && MarketHelper.TYPE_COMPLEX_RiGHT_NONE != complexRightType) {
            listener.changeComplexRight(complexRightType);
        }
        dismiss();
    }

    @Override
    public void refreshView(Integer val) {
        switch (val) {
            case MarketHelper.TYPE_COMPLEX_EX_RiGHT:
                tvExRight.setTextColor(contentView.getResources().getColor(R.color.text_color_blue));
                tvAfterRight.setTextColor(contentView.getResources().getColor(R.color.text_color_gray_3));
                tvBeforeRight.setTextColor(contentView.getResources().getColor(R.color.text_color_gray_3));
                break;
            case MarketHelper.TYPE_COMPLEX_AFTER_RiGHT:
                tvExRight.setTextColor(contentView.getResources().getColor(R.color.text_color_gray_3));
                tvAfterRight.setTextColor(contentView.getResources().getColor(R.color.text_color_blue));
                tvBeforeRight.setTextColor(contentView.getResources().getColor(R.color.text_color_gray_3));
                break;
            case MarketHelper.TYPE_COMPLEX_BEFORE_RIGHT:
                tvExRight.setTextColor(contentView.getResources().getColor(R.color.text_color_gray_3));
                tvAfterRight.setTextColor(contentView.getResources().getColor(R.color.text_color_gray_3));
                tvBeforeRight.setTextColor(contentView.getResources().getColor(R.color.text_color_blue));
                break;
        }
    }

    public static String getComplexRightText(int type){
        switch (type){
            case MarketHelper.TYPE_COMPLEX_AFTER_RiGHT:
                return "后复权";
            case MarketHelper.TYPE_COMPLEX_BEFORE_RIGHT:
                return "前复权";
            case MarketHelper.TYPE_COMPLEX_EX_RiGHT:
                return "除权";
            default:
                return "前复权";
        }
    }
}