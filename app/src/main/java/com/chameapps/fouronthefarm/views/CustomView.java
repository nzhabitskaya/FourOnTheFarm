package com.pearmobile.fouronthefarm.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.chameapps.fouronthefarm.animation.TranslateAnimation;

public class CustomView extends ImageView {
	private Drawable mBallImage;
	private int mCanvasWidth;
	private int mCanvasHeight;
	private Rect mBallRect;
	public static int mColumns;
	public static int mRows;
	private int mBallSize;

	public CustomView(Context context, int res) {
		super(context);
		mBallImage = context.getResources().getDrawable(res);
		setPadding(0, 0, 0, 0);
		mBallRect = new Rect();
		mColumns = 7;
		mRows = 6;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		mBallImage.setBounds(mBallRect);		
		mBallImage.draw(canvas);
	}
	
	public Animation moveView(float xDelta, float yDelta) {
		Animation animation = new TranslateAnimation(xDelta, xDelta,
				yDelta, yDelta);
		animation.setFillAfter(true);
		animation.setInterpolator(new AccelerateInterpolator());
		this.startAnimation(animation);
		return animation;
	}
	
	public Animation transparentView(float fromXDelta, float toXDelta,
			float fromYDelta, float toYDelta) {
		Animation animation = new TranslateAnimation(fromXDelta, toXDelta,
				fromYDelta, toYDelta);
		animation.setFillAfter(true);
		animation.setInterpolator(new AccelerateInterpolator());
		this.startAnimation(animation);
		return animation;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mCanvasWidth = w;
		mCanvasHeight = h;
		mBallSize = (mCanvasWidth / mColumns);
		mBallRect.set(2, mCanvasHeight - mBallSize, mBallSize, mCanvasHeight);
	}

	public void drawImage(int iX, int iY) {
		mBallRect.set(iX, iY, iX + mBallSize,
				iY + mBallSize);
		mBallRect.left += iX;
		mBallRect.right += iY;
		invalidate();
	}
}