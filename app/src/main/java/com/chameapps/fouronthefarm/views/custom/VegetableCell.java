package com.chameapps.fouronthefarm.views.custom;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.chameapps.fouronthefarm.activity.GameActivity;
import com.chameapps.fouronthefarm.activity.callback.ChangePlayerAndPlayAICallback;
import com.chameapps.fouronthefarm.activity.callback.ChangePlayerCallback;
import com.chameapps.fouronthefarm.activity.callback.CheckIsGameOverCallback;
import com.chameapps.fouronthefarm.activity.callback.CreateNewGameCallback;
import com.chameapps.fouronthefarm.activity.callback.PlaySoundCallback;
import com.chameapps.fouronthefarm.animation.FrameAnimation;
import com.chameapps.fouronthefarm.animation.FrameAnimationCallback;
import com.chameapps.fouronthefarm.animation.ShineFrameAnimation;
import com.chameapps.fouronthefarm.animation.TranslateAnimation;

public class VegetableCell extends FrameLayout implements FrameAnimationCallback{
	private VegetableLayout layout;
	private VegetableView vegetableView;
	private int currentBallImageId;
	private Context context;
	private int cellWidth;
	private int cellHeight;
	private android.view.ViewGroup.LayoutParams params;
	
	private CreateNewGameCallback createNewGameCallback;
	private PlaySoundCallback playSoundCallback;
	private ChangePlayerAndPlayAICallback changePlayerAndPlayAICallback;
	private ChangePlayerCallback changePlayerCallback;
	private CheckIsGameOverCallback checkIsGameOverCallback;

	public VegetableCell(Context context, int cellWidth, int cellHeight, int currentBallImageId, VegetableLayout layout) {
		super(context);
		this.context = context;
		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;
		this.currentBallImageId = currentBallImageId;
		this.layout = layout;
		
		setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		params =  getLayoutParams();
		params.height = cellHeight;
		params.width = cellWidth;
		setLayoutParams(params);
		
		vegetableView = new VegetableView(context, currentBallImageId);
		addView(vegetableView);
	}
	
	public Animation moveBall(float fromXDelta, float toXDelta,
			float fromYDelta, float toYDelta) {
		Animation animation = new TranslateAnimation(fromXDelta, toXDelta,
				fromYDelta, toYDelta);
		animation.setFillAfter(true);
		animation.setFillEnabled(true);
		startAnimation(animation);
		return animation;
	}
	
	public VegetableLayout getLayout(){
		return layout;
	}
	
	public Animation moveBall(float xDelta, float yDelta) {
		Animation animation = new TranslateAnimation(xDelta, xDelta,
				yDelta, yDelta);
		animation.setFillAfter(true);
		animation.setFillEnabled(true);
		startAnimation(animation);
		return animation;
	}
	
	public ShineFrameAnimation applyShineAnimation(){
		return new ShineFrameAnimation(context, vegetableView, currentBallImageId);
	}
	
	public void applyCreateBallAnimation(int row, int col){
		FrameAnimation animation = new FrameAnimation(context, vegetableView, row, col, currentBallImageId);
		animation.setAnimationCallback(this);
		GameActivity.isAnimation = true;
	}

	@Override
	public void onFrameAnimationEnd(int row, int col) {
		layout.animationLayoutVertical(row, col);
	}
	
	private void animationVertical(final int row, final int col) {
		Animation animation = moveBall(0 * cellWidth, 0 * cellWidth, 0, cellHeight * row);
		animation.setDuration(500);
		animation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				if (GameActivity.isNewGame) {
					createNewGameCallback.isCreateNewGame();
					GameActivity.isAnimation = false;
				} else
					animationVerticalToTop(row, col);
			}
		});
	}
	
	private void animationVerticalToTop(final int row, final int col) {
		Animation animation = moveBall(0 * cellWidth, 0 * cellWidth, cellHeight * row, cellHeight * row  - cellHeight * 1/ 3);
		animation.setDuration(100);
		animation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				if (GameActivity.isNewGame) {
					createNewGameCallback.isCreateNewGame();
					GameActivity.isAnimation = false;
				} else
					animationVerticalToBottom(row, col);
			}
		});
	}

	private void animationVerticalToBottom(final int row, final int col) {
		final Animation animation = moveBall(0 * cellWidth, 0 * cellWidth, cellHeight * row - cellHeight * 1/ 3, cellHeight * row);
		animation.setDuration(70);
		animation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation arg0) {
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				GameActivity.isAnimation = false;
				checkIsGameOverCallback.isGameOverCheck();
				if (GameActivity.isNewGame) {
					createNewGameCallback.isCreateNewGame();
					GameActivity.isAnimation = false;
				} else {
					if (GameActivity.isPlaySound)
						playSoundCallback.isPlaySound();

					if(changePlayerAndPlayAICallback != null)
						changePlayerAndPlayAICallback.isChangePlayerAndPlayAI();
					else
						if(changePlayerCallback != null)
							changePlayerCallback.isChangePlayer();
				}
			}
		});
	}
	
	public ImageView getImageView(){
		return vegetableView;
	}
	
	public void restoreBall(int row, int col) {
		moveBall(0 * cellWidth, cellHeight * row - GameActivity.nextPlayerImgHeight * 2/ 5 + cellHeight * 5/ 9);
	}
	
	public void drawHeap(int row, int col) {
		moveBall(0 * cellWidth, cellHeight * row + GameActivity.displayHeight/16);
	}
	
	public int getBallImageId(){
		return currentBallImageId;
	}
	
	public void setCreateNewGameCallback(CreateNewGameCallback createNewGameCallback) {
		this.createNewGameCallback = createNewGameCallback;
	}

	public void setPlaySoundCallback(PlaySoundCallback playSoundCallback) {
		this.playSoundCallback = playSoundCallback;
	}

	public void setChangePlayerAndPlayAICallback(ChangePlayerAndPlayAICallback changePlayerAndPlayAICallback) {
		this.changePlayerAndPlayAICallback = changePlayerAndPlayAICallback;
	}
	
	public void setChangePlayerCallback(ChangePlayerCallback changePlayerCallback) {
		this.changePlayerCallback = changePlayerCallback;
	}
	
	public void setCheckIsGameOverCallback(CheckIsGameOverCallback checkIsGameOverCallback) {
		this.checkIsGameOverCallback = checkIsGameOverCallback;
	}
}
