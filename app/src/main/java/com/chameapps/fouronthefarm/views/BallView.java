package com.chameapps.fouronthefarm.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.chameapps.fouronthefarm.R;
import com.chameapps.fouronthefarm.animation.TranslateAnimation;

public class BallView extends ImageView {
	private Drawable mBallImage;
	private int mCanvasWidth;
	private int mCanvasHeight;
	private Rect mBallRect;
	public static int mColumns;
	private GestureDetector gestures;
	private int mBallSize;

	public BallView(Context context, int res) {
		super(context);
		gestures = new GestureDetector(context, new GestureListener(this));
		mBallImage = context.getResources().getDrawable(res);
		setPadding(0, 0, 0, 0);
		mBallRect = new Rect();
		mColumns = 7;
	}
	
	public Drawable getDrawable(){
		return mBallImage;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		mBallImage.setBounds(mBallRect);		
		mBallImage.draw(canvas);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mCanvasWidth = w;
		mCanvasHeight = h;
		mBallSize = (mCanvasWidth / mColumns);
		mBallRect.set(2, mCanvasHeight - mBallSize, mBallSize, mCanvasHeight);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return gestures.onTouchEvent(event);
	}

	public void drawImage(int iX) {
		mBallRect.set(iX, mCanvasHeight - mBallSize, iX + mBallSize,
				mCanvasHeight);
		invalidate();
	}

	public boolean checkClickOnBall(MotionEvent e) {
		Rect ballCord = mBallImage.getBounds();
		int x = (int) e.getX();
		if (x > ballCord.left && x < ballCord.right)
			return true;
		return false;
	}

	public void onMove(float dx, float dy) {
		mBallRect = mBallImage.copyBounds();
		mBallRect.left += (int) dx;
		mBallRect.right += (int) dx;
		invalidate();
	}
	
	public Animation moveBall(float fromXDelta, float toXDelta,
			float fromYDelta, float toYDelta) {
		Animation animation = new TranslateAnimation(fromXDelta, toXDelta,
				fromYDelta, toYDelta);
		animation.setFillAfter(true);
		animation.setInterpolator(new AccelerateInterpolator());
		startAnimation(animation);
		return animation;
	}
	
	public Animation rotateBall(Context context) {
		Animation anim = AnimationUtils.loadAnimation(context, R.anim.rotate_animation2);
		anim.setFillAfter(true);
		this.startAnimation(anim);
		return anim;
	}
}

class GestureListener implements GestureDetector.OnGestureListener,
		GestureDetector.OnDoubleTapListener {

	BallView view;

	public GestureListener(BallView view) {
		this.view = view;
	}

	public boolean onDown(MotionEvent e) {
		return view.checkClickOnBall(e);
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2,
			final float velocityX, final float velocityY) {
		return false;
	}

	public boolean onDoubleTap(MotionEvent e) {
		return true;
	}

	public void onLongPress(MotionEvent e) {
	}

	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		//view.onMove(-distanceX, -distanceY);
		return true;
	}

	public void onShowPress(MotionEvent e) {
	}

	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	public boolean onDoubleTapEvent(MotionEvent e) {
		return false;
	}

	public boolean onSingleTapConfirmed(MotionEvent e) {
		return false;
	}
}