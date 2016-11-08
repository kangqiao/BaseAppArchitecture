package com.jzsec.broker.view.customKeyboard;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.jzsec.broker.R;

public abstract class CustomBaseKeyboard extends Keyboard implements KeyboardView.OnKeyboardActionListener{

    protected EditText etCurrent;
    protected View nextFocusView;
    protected CustomKeyStyle customKeyStyle;

    public CustomBaseKeyboard(Context context, int xmlLayoutResId) {
        super(context, xmlLayoutResId);
    }

    public CustomBaseKeyboard(Context context, int xmlLayoutResId, int modeId, int width, int height) {
        super(context, xmlLayoutResId, modeId, width, height);
    }

    public CustomBaseKeyboard(Context context, int xmlLayoutResId, int modeId) {
        super(context, xmlLayoutResId, modeId);
    }

    public CustomBaseKeyboard(Context context, int layoutTemplateResId, CharSequence characters, int columns, int horizontalPadding) {
        super(context, layoutTemplateResId, characters, columns, horizontalPadding);
    }

    protected int getKeyCode(int resId) {
        if (null != etCurrent) {
            return etCurrent.getContext().getResources().getInteger(resId);
        } else {
            return Integer.MIN_VALUE;
        }
    }

    public void setCurEditText(EditText etCurrent) {
        this.etCurrent = etCurrent;
    }

    public EditText getCurEditText() {
        return etCurrent;
    }

    public void setNextFocusView(View view) {
        this.nextFocusView = view;
    }

    public CustomKeyStyle getCustomKeyStyle() {
        return customKeyStyle;
    }

    public void setCustomKeyStyle(CustomKeyStyle customKeyStyle) {
        this.customKeyStyle = customKeyStyle;
    }

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        if (null != etCurrent && etCurrent.hasFocus() && !handleSpecialKey(etCurrent, primaryCode)) {
            Editable editable = etCurrent.getText();
            int start = etCurrent.getSelectionStart();

            if (primaryCode == Keyboard.KEYCODE_DELETE) { //回退
                if (!TextUtils.isEmpty(editable)) {
                    if (start > 0) {
                        editable.delete(start - 1, start);
                    }
                }
            } else if (primaryCode == getKeyCode(R.integer.keycode_empty_text)) { //清空
                editable.clear();
            } else if (primaryCode == getKeyCode(R.integer.keycode_hide_keyboard)) { //隐藏
                hideKeyboard();
            } else if (primaryCode == 46) { //小数点
                if (!editable.toString().contains(".")) {
                    editable.insert(start, Character.toString((char) primaryCode));
                }
            } else { //其他默认
                editable.insert(start, Character.toString((char) primaryCode));
            }
        }
        //getKeyboardView().postInvalidate();
    }

    public void hideKeyboard() {
        //hideSoftKeyboard(etCurrent);
        if (null != nextFocusView) nextFocusView.requestFocus();
    }

    /**
     * 处理自定义键盘的特殊定制键
     * 注: 所有的操作要针对etCurrent来操作
     *
     * @param etCurrent   当前操作的EditText
     * @param primaryCode 选择的Key
     * @return true: 已经处理过, false: 没有被处理
     */
    public abstract boolean handleSpecialKey(EditText etCurrent, int primaryCode);

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }

    public interface CustomKeyStyle {
        Drawable getKeyBackground(Key key, EditText etCur);

        Float getKeyTextSize(Key key, EditText etCur);

        Integer getKeyTextColor(Key key, EditText etCur);

        CharSequence getKeyLabel(Key key, EditText etCur);
    }

    public static class SimpleCustomKeyStyle implements CustomKeyStyle{

        @Override
        public Drawable getKeyBackground(Key key, EditText etCur) {
            return key.iconPreview;
        }

        @Override
        public Float getKeyTextSize(Key key, EditText etCur) {
            return null;
        }

        @Override
        public Integer getKeyTextColor(Key key, EditText etCur) {
            return null;
        }

        @Override
        public CharSequence getKeyLabel(Key key, EditText etCur) {
            return key.label;
        }

        protected int getKeyCode(Context context, int resId) {
            if (null != context) {
                return context.getResources().getInteger(resId);
            } else {
                return Integer.MIN_VALUE;
            }
        }

        protected Drawable getDrawable(Context context, int resId){
            if(null != context){
                return context.getResources().getDrawable(resId);
            }
            return null;
        }
    }
}