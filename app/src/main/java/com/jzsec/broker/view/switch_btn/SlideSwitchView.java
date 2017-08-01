package com.jzsec.broker.view.switch_btn;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.jzsec.broker.R;

public class SlideSwitchView extends View{
	/** Switch锟阶诧拷锟斤拷式图片 */
	private Bitmap mSwitchBottom;
	/** Switch 锟斤拷前锟斤拷式  */
	private Bitmap mSwitchThumb;
	/** Switch锟睫诧拷锟斤拷锟斤拷锟斤拷碌锟斤拷锟绞?  */
	private Bitmap mSwitchThumbNormal;
	/** Switch锟斤拷前锟斤拷指锟斤拷锟斤拷式锟斤拷锟斤拷式  */
	private Bitmap mSwitchThumbPressed;
	/** Switch 锟斤拷锟?  */
	private Bitmap mSwitchFrame;
	private Bitmap mSwitchMask;
	private float mCurrentX = 0;
	/** Switch 锟斤拷锟斤拷状态锟斤拷默锟斤拷锟斤拷  锟斤拷锟斤拷true  */
	private boolean mSwitchOn = true;
	/** Switch 锟斤拷锟斤拷贫锟斤拷锟斤拷锟?   */
	private int mMoveLength;
	/** 锟斤拷一锟轿帮拷锟铰碉拷锟斤拷效锟斤拷锟斤拷 */
	private float mLastX = 0;
	/** 锟斤拷锟狡碉拷目锟斤拷锟斤拷锟斤拷锟叫?  */
	private Rect mDest = null;
	/** 锟斤拷取源图片锟侥达拷小  */
	private Rect mSrc = null;
	/** Switch 锟狡讹拷锟斤拷偏锟斤拷锟斤拷  */
	private int mMoveDeltX = 0;
	/** 锟斤拷锟绞癸拷锟斤拷  */
	private Paint mPaint = null;
	/** Switch 状态锟斤拷锟斤拷锟接匡拷  */
	private OnSwitchChangedListener switchListener = null;
	private boolean mFlag = false;
	/** enabled 锟斤拷锟斤拷 为 true */
	private boolean mEnabled = true;
	/** 锟斤拷锟酵革拷锟斤拷龋锟斤拷锟斤拷遣锟酵革拷锟? */
	private final int MAX_ALPHA = 255;
	/** 锟斤拷前透锟斤拷锟饺ｏ拷锟斤拷锟斤拷锟斤拷要锟斤拷锟斤拷锟斤拷锟斤拷丶锟斤拷锟絜nable锟斤拷锟斤拷为false时锟斤拷锟斤拷锟矫帮拷透锟斤拷 锟斤拷锟斤拷锟斤拷锟斤拷锟皆碉拷锟? */
	private int mAlpha = MAX_ALPHA;
	/** Switch 锟叫讹拷锟角凤拷锟斤拷锟较讹拷 */
	private boolean mIsScrolled =false;
	
	public SlideSwitchView(Context context) {
		this(context, null);
	}

	public SlideSwitchView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SlideSwitchView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	/**
	 * 锟斤拷始锟斤拷锟斤拷锟斤拷锟皆?
	 */
	public void init() {
		mSwitchThumbPressed = BitmapFactory.decodeResource(getResources(), R.mipmap.checkswitch_btn_pressed);
		mSwitchThumbNormal = BitmapFactory.decodeResource(getResources(),R.mipmap.checkswitch_btn_unpressed);
		mSwitchBottom = BitmapFactory.decodeResource(getResources(),R.mipmap.checkswitch_bottom);
		mSwitchFrame = BitmapFactory.decodeResource(getResources(),R.mipmap.checkswitch_frame);
		mSwitchMask = BitmapFactory.decodeResource(getResources(),R.mipmap.checkswitch_mask);
		mSwitchThumb = mSwitchThumbNormal;
		mMoveLength = mSwitchBottom.getWidth() - mSwitchFrame.getWidth();
		//锟斤拷锟斤拷锟斤拷锟斤拷锟叫?
		mDest = new Rect(0, 0, mSwitchFrame.getWidth(),mSwitchFrame.getHeight());
		mSrc = new Rect();
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setAlpha(255);
		mPaint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		setMeasuredDimension(mSwitchFrame.getWidth(), mSwitchFrame.getHeight());
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		if (mMoveDeltX > 0 || mMoveDeltX == 0 && mSwitchOn) {
			if (mSrc != null) {
				mSrc.set(mMoveLength - mMoveDeltX, 0, mSwitchBottom.getWidth()
						- mMoveDeltX, mSwitchFrame.getHeight());
			}
		} else if (mMoveDeltX < 0 || mMoveDeltX == 0 && !mSwitchOn) {
			if (mSrc != null) {
				mSrc.set(-mMoveDeltX, 0, mSwitchFrame.getWidth() - mMoveDeltX,
						mSwitchFrame.getHeight());
			}
		}
		Log.d("mAlpha", "mAlpha:" + mAlpha);
		canvas.saveLayerAlpha(new RectF(mDest), mAlpha, Canvas.MATRIX_SAVE_FLAG
				| Canvas.CLIP_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG
				| Canvas.FULL_COLOR_LAYER_SAVE_FLAG
				| Canvas.CLIP_TO_LAYER_SAVE_FLAG);
		canvas.drawBitmap(mSwitchBottom, mSrc, mDest, null);
		canvas.drawBitmap(mSwitchThumb, mSrc, mDest, null);
		canvas.drawBitmap(mSwitchFrame, 0, 0, null);
		canvas.drawBitmap(mSwitchMask, 0, 0, mPaint);
		canvas.restore();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		//锟斤拷锟紼nabled锟斤拷锟斤拷锟借定为true,锟斤拷锟斤拷效锟斤拷锟斤拷锟斤拷效
		if(!mEnabled){
			return true;
		}
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mSwitchThumb = mSwitchThumbPressed;
			mLastX = event.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			mCurrentX = event.getX();
			mMoveDeltX = (int) (mCurrentX - mLastX);
			if(mMoveDeltX > 10){
				//锟斤拷锟斤拷锟斤拷10锟斤拷锟斤拷锟斤拷锟斤拷耄拷锟斤拷愿锟斤拷玫锟绞碉拷值锟斤拷效锟斤拷
				mIsScrolled = true;
			}
			// 锟斤拷锟斤拷锟斤拷乜锟斤拷锟斤拷锟斤拷蠡锟斤拷锟斤拷锟斤拷呖锟斤拷毓锟斤拷锟斤拷锟斤拷一锟斤拷锟斤拷锟斤拷锟绞憋拷锟斤拷遣锟斤拷锟揭拷锟斤拷锟侥ｏ拷
			if ((mSwitchOn && mMoveDeltX < 0) || (!mSwitchOn && mMoveDeltX > 0)) {
				mFlag = true;
				mMoveDeltX = 0;
			}

			if (Math.abs(mMoveDeltX) > mMoveLength) {
				mMoveDeltX = mMoveDeltX > 0 ? mMoveLength : -mMoveLength;
			}
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			mSwitchThumb = mSwitchThumbNormal;
			//锟斤拷锟矫伙拷谢锟斤拷锟斤拷锟斤拷锟斤拷涂锟斤拷锟揭伙拷蔚锟斤拷锟铰硷拷
			if(!mIsScrolled){
				mMoveDeltX = mSwitchOn ? mMoveLength : -mMoveLength;
				mSwitchOn = !mSwitchOn;
				if (switchListener != null) {
					switchListener.onSwitchChange(this, mSwitchOn);
				}
				invalidate();
				mMoveDeltX = 0;
				break;
			}
			mIsScrolled = false;
			if (Math.abs(mMoveDeltX) > 0 && Math.abs(mMoveDeltX) < mMoveLength / 2) {
				mMoveDeltX = 0;
				invalidate();
			} else if (Math.abs(mMoveDeltX) > mMoveLength / 2
					&& Math.abs(mMoveDeltX) <= mMoveLength) {
				mMoveDeltX = mMoveDeltX > 0 ? mMoveLength : -mMoveLength;
				mSwitchOn = !mSwitchOn;
				if (switchListener != null) {
					switchListener.onSwitchChange(this, mSwitchOn);
				}
				invalidate();
				mMoveDeltX = 0;
			} else if (mMoveDeltX == 0 && mFlag) {
				// 锟斤拷时锟斤拷玫锟斤拷锟斤拷遣锟斤拷锟揭拷锟斤拷写锟斤拷锟侥ｏ拷锟斤拷为锟窖撅拷move锟斤拷锟斤拷
				mMoveDeltX = 0;
				mFlag = false;
			}
		default:
			break;
		}
		invalidate();
		return true;
	}
	/** 
	 * 锟斤拷锟斤拷 switch 状态锟斤拷锟斤拷
	 * */
	public void setOnChangeListener(OnSwitchChangedListener listener) {
		switchListener = listener;
	}
	/** 
	 * switch 锟斤拷锟截硷拷锟斤拷锟接匡拷
	 *  */
	public interface OnSwitchChangedListener{
		public void onSwitchChange(SlideSwitchView switchView, boolean isChecked);
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		// TODO Auto-generated method stub
		mEnabled = enabled;
		mAlpha = enabled ? MAX_ALPHA : MAX_ALPHA/2;
		Log.d("enabled",enabled ? "true": "false");
		super.setEnabled(enabled);
		invalidate();
	}
	
	/** 锟皆讹拷锟叫讹拷锟叫伙拷锟斤拷锟洁反锟斤拷锟斤拷锟斤拷 : true -->false ;false -->true */
	public void toggle() {
		setChecked(!mSwitchOn);
	}
	
    /** 锟斤拷锟斤拷选锟叫碉拷状态锟斤拷选锟斤拷:true   锟斤拷选锟斤拷: false锟斤拷 */
    public void setChecked(boolean checked) {
    	mSwitchOn = checked;
        invalidate();
    }
}