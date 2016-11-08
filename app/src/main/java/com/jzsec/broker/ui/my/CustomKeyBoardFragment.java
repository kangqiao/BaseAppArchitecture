package com.jzsec.broker.ui.my;

import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.jzsec.broker.R;
import com.jzsec.broker.base.BaseFragment;
import com.jzsec.broker.view.customKeyboard.CustomBaseKeyboard;
import com.jzsec.broker.view.customKeyboard.CustomKeyboardManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by zhaopan on 2016/10/27.
 * e-mail: kangqiao610@gmail.com
 */
public class CustomKeyBoardFragment extends BaseFragment {

    private static final String TAG = "CustomKeyBoardFragment";
    @BindView(R.id.et_input_price1)
    EditText etInputPrice1;
    @BindView(R.id.et_input_num1)
    EditText etInputNum1;
    @BindView(R.id.et_input_num2)
    EditText etInputNum2;
    @BindView(R.id.layout_opt_container)
    LinearLayout optContainer;
    @BindView(R.id.view_under)
    View underView;

    CustomKeyboardManager customKeyboardManager;

    public interface OnSoftKeyboardStateChangedListener {
        public void OnSoftKeyboardStateChanged(boolean isKeyBoardShow, int keyboardHeight);
    }

    //注册软键盘状态变化监听
    public void addSoftKeyboardChangedListener(OnSoftKeyboardStateChangedListener listener) {
        if (listener != null) {
            mKeyboardStateListeners.add(listener);
        }
    }

    private ArrayList<OnSoftKeyboardStateChangedListener> mKeyboardStateListeners;      //软键盘状态监听列表
    private ViewTreeObserver.OnGlobalLayoutListener mLayoutChangeListener;
    private boolean mIsSoftKeyboardShowing;

    public static SupportFragment newInstance() {
        Bundle args = new Bundle();
        SupportFragment fragment = new CustomKeyBoardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_keyboard, container, false);
        //_mActivity.getWindow().setSoftInputMode(SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //_mActivity.getWindow().setSoftInputMode(SOFT_INPUT_STATE_UNSPECIFIED);
    }

    //int mKeyboardHeight = 0;

    @Override
    protected void onBindView(Bundle savedInstanceState) {
        customKeyboardManager = new CustomKeyboardManager(_mActivity);

        CustomBaseKeyboard priceKeyboard = new CustomBaseKeyboard(getContext(), R.xml.stock_price_num_keyboard) {
            @Override
            public boolean handleSpecialKey(EditText etCurrent, int primaryCode) {
                if (primaryCode == getKeyCode( R.integer.keycode_cur_price)) {
                    etCurrent.setText("9.999$");
                    return true;
                }
                return false;
            }
        };

        customKeyboardManager.attachTo($(R.id.et_input_price1), priceKeyboard);
        customKeyboardManager.attachTo($(R.id.et_input_price2), priceKeyboard);
        //customKeyboardManager.attachTo($(R.id.et_input_price3), priceKeyboard);
        customKeyboardManager.attachTo($(R.id.et_input_price4), priceKeyboard);
        customKeyboardManager.attachTo($(R.id.et_input_price5), priceKeyboard);
        customKeyboardManager.attachTo($(R.id.et_input_price6), priceKeyboard);

        CustomBaseKeyboard numKeyboard =  new CustomBaseKeyboard(getContext(), R.xml.stock_trade_num_keyboard) {
            @Override
            public boolean handleSpecialKey(EditText etCurrent, int primaryCode) {
                Editable editable = etCurrent.getText();
                int start = etCurrent.getSelectionEnd();
                if (primaryCode == getKeyCode( R.integer.keycode_stocknum_000)) {
                    editable.insert(start, "000");
                    return true;
                } else if (primaryCode == getKeyCode(R.integer.keycode_stocknum_half)){
                    etCurrent.setText(1000 /2+"");
                    return true;
                } else if (primaryCode == getKeyCode(R.integer.keycode_stocknum_all)){
                    setStockNumAll(etCurrent);
                    return true;
                } else if (primaryCode == getKeyCode(R.integer.keycode_stock_sell)){
                    etCurrent.setText("999999");
                    hideKeyboard();
                    return true;
                }
                return false;
            }
        };

        numKeyboard.setCustomKeyStyle(new CustomBaseKeyboard.SimpleCustomKeyStyle(){
            @Override
            public Drawable getKeyBackground(Keyboard.Key key, EditText etCur) {
                if(getKeyCode(etCur.getContext(), R.integer.keycode_stock_sell) == key.codes[0]) {
                    if (R.id.et_input_num1 == etCur.getId()) {
                        return getDrawable(etCur.getContext(), R.drawable.bg_custom_key_blue);
                    } else if (R.id.et_input_num2 == etCur.getId()) {
                        return getDrawable(etCur.getContext(), R.drawable.bg_custom_key_red);
                    }
                }
                return super.getKeyBackground(key, etCur);
            }

            @Override
            public CharSequence getKeyLabel(Keyboard.Key key, EditText etCur) {
                if(getKeyCode(etCur.getContext(), R.integer.keycode_stock_sell) == key.codes[0]) {
                    if (R.id.et_input_num1 == etCur.getId()) {
                        return "卖出";
                    } else if (R.id.et_input_num2 == etCur.getId()) {
                        return "买入";
                    }
                }
                return super.getKeyLabel(key, etCur);
            }
        });
        customKeyboardManager.attachTo(etInputNum1, numKeyboard);
        customKeyboardManager.attachTo(etInputNum2, numKeyboard);

        customKeyboardManager.setShowUnderView(underView);
        //customKeyboardManager.attachTo(etInputOther, CustomKeyboardManager.KEYBOARD_TYPE_STOCK);

        /*mIsSoftKeyboardShowing = false;
        mKeyboardStateListeners = new ArrayList<OnSoftKeyboardStateChangedListener>();
        mLayoutChangeListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //判断窗口可见区域大小
                Rect r = new Rect();
                _mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //如果屏幕高度和Window可见区域高度差值大于整个屏幕高度的1/3，则表示软键盘显示中，否则软键盘为隐藏状态。
                int heightDifference = screenHeight - (r.bottom - r.top);
                boolean isKeyboardShowing = heightDifference > screenHeight/3;

                //如果之前软键盘状态为显示，现在为关闭，或者之前为关闭，现在为显示，则表示软键盘的状态发生了改变
                if ((mIsSoftKeyboardShowing && !isKeyboardShowing) || (!mIsSoftKeyboardShowing && isKeyboardShowing)) {
                    mIsSoftKeyboardShowing = isKeyboardShowing;
                    for (int i = 0; i < mKeyboardStateListeners.size(); i++) {
                        OnSoftKeyboardStateChangedListener listener = mKeyboardStateListeners.get(i);
                        listener.OnSoftKeyboardStateChanged(mIsSoftKeyboardShowing, heightDifference);
                    }
                }
            }
        };
        //注册布局变化监听
        _mActivity.getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(mLayoutChangeListener);


        addSoftKeyboardChangedListener(new OnSoftKeyboardStateChangedListener() {
            @Override
            public void OnSoftKeyboardStateChanged(boolean isKeyBoardShow, int keyboardHeight) {
                mKeyboardHeight = keyboardHeight;
                Zlog.d("OnSoftKeyboardStateChanged", "isKeyBoardShow = "+isKeyBoardShow+", keyboradHeight = "+ mKeyboardHeight);
            }
        });

        etInputNum1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Zlog.d("onFocusChange", "true, keyboradHeight = "+ mKeyboardHeight);
                }else {
                    Zlog.d("onFocusChange", "false, keyboradHeight = "+ mKeyboardHeight);
                }
            }
        });*/
    }

    @OnClick({R.id.tv_go_trade, R.id.tv_go_buy})
    public void onClickView(View view) {
        if (view.getId() == R.id.tv_go_trade)
            customKeyboardManager.showSoftKeyboard((EditText) view);
        if (view.getId() == R.id.tv_go_buy)
            customKeyboardManager.hideSoftKeyboard((EditText) view);
    }

    @OnClick(R.id.tv_stock_all)
    public void setStockNumAll(View view){
        etInputNum2.setText("10000");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //移除布局变化监听
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            _mActivity.getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(mLayoutChangeListener);
        } else {
            _mActivity.getWindow().getDecorView().getViewTreeObserver().removeGlobalOnLayoutListener(mLayoutChangeListener);
        }
        super.onDestroy();
    }
}
