package com.chameapps.fouronthefarm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.FrameLayout;

import com.chameapps.fouronthefarm.R;
import com.pearmobile.fouronthefarm.views.CustomView;

public class SplashActivity extends BaseActivity {
	private final int splashScreenDisplayTime = 5000; // show splash screen with
														// this timeout
	private int displayWidth;
	private int displayHeight;
	private int cellWidth;

	private CustomView heapView;
	private CustomView ballView;
	private FrameLayout foreground;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// No Title bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// no system menu
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// do not power off back light
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.splash_screen);
		
		foreground = (FrameLayout) findViewById(R.id.splash_layout);

		displayWidth = getWindowManager().getDefaultDisplay().getWidth();
		displayHeight = getWindowManager().getDefaultDisplay().getHeight();
		cellWidth = displayWidth / 7;
		int gamePanelHeight = displayHeight*6/8;
		cellHeight = gamePanelHeight / ( 6 + 2 );

		// ResizeLogoImage
		//resizeLogoImage(6);

		drawHeaps();
		startAnimation();
	}

	private void drawHeaps() {
		displayWidth = getWindowManager().getDefaultDisplay().getWidth();
		displayHeight = getWindowManager().getDefaultDisplay().getHeight();
		cellWidth = displayWidth / 7;

		FrameLayout foreground = (FrameLayout) findViewById(R.id.splash_layout);
		for (int x = 0; x < 7; x++) {
			heapView = new CustomView(this, R.drawable.heap);
			foreground.addView(heapView);
			heapView.moveView(x * cellWidth, -displayHeight * 1 / 16);
		}
	}

	private void drawBall(int x) {
		if (x == 1 || x == 3 || x == 5 || x == 7)
			ballView = new CustomView(this, R.drawable.pumpkin);
		else
			ballView = new CustomView(this, R.drawable.tomato);
		foreground.addView(ballView);
	}
	
	private void startAnimation(){
		drawBall(-1);
		animationHorizontal(-1);
	}

	private void animationHorizontal(final int x) {
		Animation animation = ballView.moveView(x * cellWidth, -displayHeight * 5 / 44);
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
				if(x < 7){
					drawBall(x + 1);
					animationHorizontal(x + 1);
				}
				else{
					/*foreground.removeAllViews();
					drawHeaps();
					startAnimation();*/
					Intent intent = new Intent(SplashActivity.this, StartActivity.class);
					finish();
					startActivity(intent);
				}
			}
		});
	}
}