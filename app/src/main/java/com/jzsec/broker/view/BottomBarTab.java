package com.jzsec.broker.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jzsec.broker.R;

/**
 * Created by YoKeyword on 16/6/3.
 */
public class BottomBarTab extends FrameLayout {
    private ImageView mIcon;
    private ImageView mRedPoint;
    private TextView mTvTitle;
    private Context mContext;
    private int mTabPosition = -1;

    public BottomBarTab(Context context, @DrawableRes int icon, CharSequence title) {
        this(context, null, icon, title);
    }

    public BottomBarTab(Context context, AttributeSet attrs, int icon, CharSequence title) {
        this(context, attrs, 0, icon, title);
    }

    public BottomBarTab(Context context, AttributeSet attrs, int defStyleAttr, int icon, CharSequence title) {
        super(context, attrs, defStyleAttr);
        init(context, icon, title);
    }

    private void init(Context context, int icon, CharSequence title) {
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(new int[]{R.attr.selectableItemBackgroundBorderless});
        Drawable drawable = typedArray.getDrawable(0);
        setBackgroundDrawable(drawable);
        typedArray.recycle();
/**
 <RelativeLayout
 android:layout_width="200dp"
 android:layout_height="100dp"
 android:layout_gravity="center"
 android:background="@color/theme_black_7f"
 android:gravity="center_vertical">

 <ImageView
 android:id="@+id/icon"
 android:layout_width="wrap_content"
 android:layout_height="wrap_content"
 android:layout_centerHorizontal="true"
 android:src="@mipmap/main_tab_contacts" />

 <ImageView
 android:layout_width="wrap_content"
 android:layout_height="wrap_content"
 android:layout_marginLeft="2dp"
 android:layout_toRightOf="@+id/icon"
 android:src="@mipmap/ic_red_point" />

 <TextView
 android:layout_centerHorizontal="true"
 android:layout_width="wrap_content"
 android:layout_height="wrap_content"
 android:layout_below="@+id/icon"
 android:layout_marginTop="2dp"
 android:text="联系人" />
 </RelativeLayout>
 */
        RelativeLayout rlContainer = new RelativeLayout(context);
        //lLContainer.setOrientation(LinearLayout.VERTICAL);
        rlContainer.setGravity(Gravity.CENTER_VERTICAL);
        LayoutParams paramsContainer = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsContainer.gravity = Gravity.CENTER;
        rlContainer.setLayoutParams(paramsContainer);

        mIcon = new ImageView(context);
        mIcon.setId(icon); //用它的icon resourceId为ID, 可能会冲突.
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 27, getResources().getDisplayMetrics());
        RelativeLayout.LayoutParams paramIcon = new RelativeLayout.LayoutParams(size, size);
        paramIcon.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mIcon.setImageResource(icon);
        mIcon.setLayoutParams(paramIcon);
        mIcon.setColorFilter(ContextCompat.getColor(context, R.color.tab_unselect));
        rlContainer.addView(mIcon);

        mTvTitle = new TextView(context);
        mTvTitle.setText(title);
        RelativeLayout.LayoutParams paramsTitle = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsTitle.topMargin =  (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
        paramsTitle.addRule(RelativeLayout.BELOW, mIcon.getId());
        paramsTitle.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mTvTitle.setTextSize(14);
        mTvTitle.setTextColor(ContextCompat.getColor(context, R.color.tab_unselect));
        mTvTitle.setLayoutParams(paramsTitle);
        rlContainer.addView(mTvTitle);

        mRedPoint = new ImageView(context);
        mRedPoint.setImageResource(R.mipmap.ic_red_point);
        mRedPoint.setVisibility(INVISIBLE);
        RelativeLayout.LayoutParams paramRedPoint = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsTitle.leftMargin =  (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
        paramRedPoint.addRule(RelativeLayout.RIGHT_OF, mIcon.getId());
        mRedPoint.setLayoutParams(paramRedPoint);
        rlContainer.addView(mRedPoint);

        addView(rlContainer);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (selected) {
            mIcon.setColorFilter(ContextCompat.getColor(mContext, R.color.tab_select));
            mTvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.tab_select));
        } else {
            mIcon.setColorFilter(ContextCompat.getColor(mContext, R.color.tab_unselect));
            mTvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.tab_unselect));
        }

        mIcon.setSelected(selected);
        mTvTitle.setSelected(selected);
    }

    public void setTabPosition(int position) {
        mTabPosition = position;
        if (position == 0) {
            setSelected(true);
        }
    }

    public int getTabPosition() {
        return mTabPosition;
    }

    public void setRedPoint(boolean redPoint) {
        mRedPoint.setVisibility(redPoint? VISIBLE: INVISIBLE);
    }
}
