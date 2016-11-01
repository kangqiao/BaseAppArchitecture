package com.jzsec.broker.ui.my;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.jzsec.broker.R;

import java.lang.reflect.Method;


/**
 * Created by zhaopan on 2016/10/28.
 * e-mail: kangqiao610@gmail.com
 */

public class CustomKeyboardManager implements OnFocusChangeListener {
    private static final String TAG = "CustomKeyboardManager";

    private Activity mActivity;
    private ViewGroup mRootView;
    private FrameLayout mKeyboardViewContainer;
    private KeyboardView mKeyboardView;
    private int mKeyboardHeight;
    private FrameLayout.LayoutParams mKeyboardViewLayoutParams;
    private View mShowUnderView;
    private View etFocusScavenger;

    public CustomKeyboardManager(Activity activity) {
        mActivity = activity;
        mRootView = (ViewGroup) (mActivity.getWindow().getDecorView().findViewById(android.R.id.content));

        mKeyboardViewContainer = (FrameLayout) mActivity.getLayoutInflater().inflate(R.layout.view_custome_keyboard_view, null);
        mKeyboardView = (KeyboardView) mKeyboardViewContainer.findViewById(R.id.keyboard_view);
        etFocusScavenger = mKeyboardViewContainer.findViewById(R.id.et_focus_scavenger);
        hideSystemSoftKeyboard((EditText) etFocusScavenger);

        mKeyboardViewLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mKeyboardViewLayoutParams.gravity = Gravity.BOTTOM;
    }

    public void attachTo(EditText editText, BaseKeyboard keyboard) {
        hideSystemSoftKeyboard(editText);
        editText.setTag(R.id.edittext_bind_keyboard, keyboard);
        editText.setOnFocusChangeListener(this);
    }

    private BaseKeyboard getKeyboard(View view) {
        Object tag = view.getTag(R.id.edittext_bind_keyboard);
        if (null != tag && tag instanceof BaseKeyboard) {
            return (BaseKeyboard) tag;
        }
        return null;
    }

    private void refreshKeyboard(BaseKeyboard keyboard) {
        mKeyboardView.setKeyboard(keyboard);
        mKeyboardView.setEnabled(true);
        mKeyboardView.setPreviewEnabled(false);
        mKeyboardView.setOnKeyboardActionListener(keyboard);
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mKeyboardView.measure(width, height);
        mKeyboardHeight = mKeyboardView.getMeasuredHeight();
    }

    public KeyboardView getKeyboardView() {
        return mKeyboardView;
    }

    /**
     * 替换焦点清理者, 即当控件失去焦点后, 焦点交由view来继续处理.
     * @param view
     */
    public void requestFocus(View view){
        etFocusScavenger = view;
    }

    public void setShowUnderView(View view) {
        mShowUnderView = view;
    }

    private int getMoveHeight(View view) {
        Rect rect = new Rect();
        mRootView.getWindowVisibleDisplayFrame(rect);

        int[] vLocation = new int[2];
        view.getLocationOnScreen(vLocation);
        int keyboardTop = vLocation[1] + view.getHeight() + view.getPaddingBottom() + view.getPaddingTop();
        if (rect.bottom - keyboardTop > mKeyboardHeight) {
            return 0;
        }
        if (null != mShowUnderView) {
            int[] underVLocation = new int[2];
            mShowUnderView.getLocationOnScreen(underVLocation);
            keyboardTop = underVLocation[1] + mShowUnderView.getHeight() + mShowUnderView.getPaddingBottom() + mShowUnderView.getPaddingTop();
        }
        int moveHeight = keyboardTop + mKeyboardHeight - rect.bottom;
        return moveHeight > 0 ? moveHeight : 0;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v instanceof EditText) {
            EditText attachEditText = (EditText) v;
            if (hasFocus) {
                showSoftKeyboard(attachEditText);
            } else {
                hideSoftKeyboard(attachEditText);
            }
        }
    }

    public void showSoftKeyboard(EditText view) {
        BaseKeyboard keyboard = getKeyboard(view);
        if (null == keyboard) {
            Log.e(TAG, "The EditText not bind BaseKeyboard!");
            return;
        }
        keyboard.setCurEditText(view);
        keyboard.setNextFocusView(etFocusScavenger);
        refreshKeyboard(keyboard);

        mRootView.addView(mKeyboardViewContainer, mKeyboardViewLayoutParams);

        mKeyboardViewContainer.setAnimation(AnimationUtils.loadAnimation(mActivity, R.anim.down_to_up));

        int moveHeight = getMoveHeight(view);
        if (moveHeight > 0) {
            mRootView.getChildAt(0).scrollBy(0, moveHeight);
        } else {
            moveHeight = 0;
        }

        view.setTag(R.id.keyboard_view_move_height, moveHeight);
    }

    public void hideSoftKeyboard(EditText view) {
        int moveHeight = 0;
        Object tag = view.getTag(R.id.keyboard_view_move_height);
        if (null != tag) moveHeight = (int) tag;
        if (moveHeight > 0) {
            mRootView.getChildAt(0).scrollBy(0, -1 * moveHeight);
            view.setTag(R.id.keyboard_view_move_height, 0);
        }

        mRootView.removeView(mKeyboardViewContainer);

        mKeyboardViewContainer.setAnimation(AnimationUtils.loadAnimation(mActivity, R.anim.up_to_hide));
    }

    /**
     * 隐藏系统键盘
     *
     * @param editText
     */
    public static void hideSystemSoftKeyboard(EditText editText) {
        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt >= 11) {
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(editText, false);
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            editText.setInputType(InputType.TYPE_NULL);
        }
    }

    public static abstract class BaseKeyboard extends Keyboard implements KeyboardView.OnKeyboardActionListener {

        protected EditText etCurrent;
        protected View nextFocusView;

        public BaseKeyboard(Context context, int xmlLayoutResId) {
            super(context, xmlLayoutResId);
        }

        public BaseKeyboard(Context context, int xmlLayoutResId, int modeId, int width, int height) {
            super(context, xmlLayoutResId, modeId, width, height);
        }

        public BaseKeyboard(Context context, int xmlLayoutResId, int modeId) {
            super(context, xmlLayoutResId, modeId);
        }

        public BaseKeyboard(Context context, int layoutTemplateResId, CharSequence characters, int columns, int horizontalPadding) {
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

        public void setNextFocusView(View view){
            this.nextFocusView = view;
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
                } else if (primaryCode == getKeyCode(R.integer.keycode_empty_text)) {
                    editable.clear();
                } else if (primaryCode == getKeyCode(R.integer.keycode_hide_keyboard)) {
                    //hideSoftKeyboard(etCurrent);
                    if(null != nextFocusView) nextFocusView.requestFocus();
                } else if(primaryCode == 46) {
                    if(!editable.toString().contains(".")) {
                        editable.insert(start, Character.toString((char) primaryCode));
                    }
                } else {
                    editable.insert(start, Character.toString((char) primaryCode));
                }
            }
            //getKeyboardView().postInvalidate();
        }

        /**
         * 处理自定义键盘的特殊定制键
         *   注: 所有的操作要针对etCurrent来操作
         * @param etCurrent 当前操作的EditText
         * @param primaryCode 选择的Key
         * @return
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
    }
}