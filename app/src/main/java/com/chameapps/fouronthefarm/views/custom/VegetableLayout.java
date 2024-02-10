package com.chameapps.fouronthefarm.views.custom;

import android.content.Context;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.FrameLayout;

import com.chameapps.fouronthefarm.activity.GameActivity;
import com.chameapps.fouronthefarm.activity.callback.ChangePlayerAndPlayAICallback;
import com.chameapps.fouronthefarm.activity.callback.ChangePlayerCallback;
import com.chameapps.fouronthefarm.activity.callback.CheckIsGameOverCallback;
import com.chameapps.fouronthefarm.activity.callback.CreateNewGameCallback;
import com.chameapps.fouronthefarm.activity.callback.PlaySoundCallback;
import com.chameapps.fouronthefarm.animation.ShineFrameAnimation;
import com.chameapps.fouronthefarm.animation.TranslateAnimation;

public class VegetableLayout extends FrameLayout{
	private CreateNewGameCallback createNewGameCallback;
	private PlaySoundCallback playSoundCallback;
	private ChangePlayerAndPlayAICallback changePlayerAndPlayAICallback;
	private ChangePlayerCallback changePlayerCallback;
	private CheckIsGameOverCallback checkIsGameOverCallback;
	
	private VegetableCell vegetableCell;
	private int padding = 0;
	private int cellWidth;
	private int cellHeight;
	private Context context;
	private int currentBallId;
	private int top = 0;
	private int bottom = 0;
	private int left = 0;
	private int right = 0;
	
	public VegetableLayout(Context context, int cellWidth, int cellHeight, int currentBallId) {
		super(context);
		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;
		this.context = context;
		this.currentBallId = currentBallId;
		//if(currentBallId == R.drawable.tomato)
			//setBackgroundColor(R.color.white_color);
	}
	
	public void setMargin(int left, int top, int right, int bottom){
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
	}
	
	public void applyCreateBallAnimation(int row, int col){
		vegetableCell.applyCreateBallAnimation(row, col);
	}

	public ShineFrameAnimation applyShineAnimation(){
		return vegetableCell.applyShineAnimation();
	}
	
	public void setCoordinates(int row, int col){
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.FILL_PARENT,  LayoutParams.FILL_PARENT);
		params.width = (col + 1) * cellWidth;
		params.height = cellHeight * (row + 1);
		params.setMargins(0, 0 - padding, 0, 0);	// Moves ball rigth ant bottom (!)
		setLayoutParams(params);
		
		vegetableCell = new VegetableCell(context, cellWidth, cellHeight, currentBallId, this);
		vegetableCell.setPadding(left, top, right, bottom);   // Top padding for animation shine
		addView(vegetableCell);
		
		LayoutParams viewParams = (LayoutParams)vegetableCell.getLayoutParams(); 
		viewParams.gravity = Gravity.BOTTOM | Gravity.RIGHT;
		viewParams.width = cellWidth;
		viewParams.height = cellHeight;
		vegetableCell.setLayoutParams(viewParams);
	}
	
	public void useAllHeight(int col){
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.FILL_PARENT,  LayoutParams.FILL_PARENT);
		params.width = (col + 1) * cellWidth;
		params.height = GameActivity.displayHeight;
		setLayoutParams(params);
	}
	
	public void setStartCoordinates(int row, int col){
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.FILL_PARENT,  LayoutParams.FILL_PARENT);
		params.width = (col + 1) * cellWidth;
		params.height = GameActivity.gamePanelHeight - cellHeight * (row);
		params.setMargins(0, 0 - GameActivity.nextPlayerImgHeight/4, 0, 0);	// Moves ball rigth ant bottom (!)
		setLayoutParams(params);
		
		vegetableCell = new VegetableCell(context, cellWidth, cellHeight, currentBallId, this);
		vegetableCell.setPadding(left, top, right, bottom);   // Top padding for animation shine
		addView(vegetableCell);
		
		LayoutParams viewParams = (LayoutParams)vegetableCell.getLayoutParams(); 
		if(row > 0)
			viewParams.gravity = Gravity.BOTTOM | Gravity.RIGHT;
		else
			viewParams.gravity = Gravity.TOP | Gravity.RIGHT;
		viewParams.width = cellWidth;
		viewParams.height = cellHeight;
		vegetableCell.setLayoutParams(viewParams);
	}
	
	public void drawBall(int row, int col, int paddingTop, int paddingleftRight){
		setMargin(paddingleftRight / 2, paddingTop, paddingleftRight / 2, 0);
		setStartCoordinates(0, col);
		vegetableCell.restoreBall(row, col);
	}
	
	public void drawHeap(int row, int col, int paddingTop, int paddingleftRight){
		setMargin(paddingleftRight / 2, paddingTop, paddingleftRight / 2, 0);
		setStartCoordinates(0, col);
		vegetableCell.drawHeap(row, col);
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
	
	public void animationLayoutVertical(final int row, final int col) {
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
					animationLayoutVerticalToTop(row, col);
			}
		});
	}
	
	private void animationLayoutVerticalToTop(final int row, final int col) {
		Animation animation = moveBall(0 * cellWidth, 0 * cellWidth, cellHeight * row, cellHeight * row - cellHeight * 1/3);
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
					animationLayoutVerticalToBottom(row, col);
			}
		});
	}

	private void animationLayoutVerticalToBottom(final int row, final int col) {
		final Animation animation = moveBall(0 * cellWidth, 0 * cellWidth, cellHeight * row - cellHeight * 1/3, cellHeight * row);
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
				
				 
				animation.setFillAfter(false);
				moveLayout(row, col);
			}
		});
	}
	
	public void moveLayout(int row, int col){ //TODO
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.FILL_PARENT,  LayoutParams.FILL_PARENT);
		params.width = (col + 1) * cellWidth;
		params.height = cellHeight * (row + 1);
		setLayoutParams(params);
		
		if(row > 0){
			LayoutParams viewParams = (LayoutParams)vegetableCell.getLayoutParams(); 
			viewParams.gravity = Gravity.BOTTOM | Gravity.RIGHT;
			vegetableCell.setLayoutParams(viewParams);
		}
	}
	
	public VegetableCell getVegetableCell(){
		return vegetableCell;
	}
	
	public int getBallImageId(){
		return vegetableCell.getBallImageId();
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

