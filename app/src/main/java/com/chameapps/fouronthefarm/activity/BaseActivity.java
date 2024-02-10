package com.chameapps.fouronthefarm.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.chameapps.fouronthefarm.R;
import com.chameapps.fouronthefarm.utils.Prefutils;

public class BaseActivity extends Activity{
	public static final String TAG = "BaseActivity";
	
	public static final int DIALOG_EXIT = 0;
	public static final int DIALOG_ABOUT = 1;
	public static final int DIALOG_NEW_GAME = 2;
	public static final int DIALOG_USER_MESSAGE = 3;
	public static final int DIALOG_HELP = 4;
	
	// Difficulty prefs
	protected RadioButton game_level0;
	protected RadioButton game_level1;
	protected RadioButton game_level2;
	protected RadioButton game_level3;
	protected Button ok_button;
	
	// Cell size
	public static int displayWidth;
	public static int displayHeight;
	public static int cellWidth;
	public static int cellHeight;
	
	private CustomDialog selectGameLevelDialog;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		displayHeight = getWindowManager().getDefaultDisplay().getHeight();
		displayWidth = getWindowManager().getDefaultDisplay().getWidth();
		
		/*DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels; //320 dip
		int height = dm.heightPixels; //533 dip
		int widthPix = (int) Math.ceil(dm.widthPixels * (dm.densityDpi / 160.0));*/
	}
	
	/** xlarge screens are at least 960dp x 720dp
		large screens are at least 640dp x 480dp
		normal screens are at least 470dp x 320dp
		small screens are at least 426dp x 320dp */
	
	protected void printScreenSize(){
		//Determine screen size
		if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {     
	    	Log.i(TAG, "XLarge screen");
	
	    }
		else if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {     
	    	Log.i(TAG, "Large screen");
	
	    }
	    else if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {     
	    	Log.i(TAG, "Normal sized screen");
	
	    } 
	    else if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {     
	    	Log.i(TAG, "Small sized screen");
	    }
	    else {
	    	Log.i(TAG, "Screen size is neither large, normal or small");
	    }
	    
	    //Determine density
	    DisplayMetrics metrics = new DisplayMetrics();
	    getWindowManager().getDefaultDisplay().getMetrics(metrics);
	    int density = metrics.densityDpi;
	
	    if (density==DisplayMetrics.DENSITY_XHIGH) {
	    	Log.i(TAG, "DENSITY_XHIGH... Density is " + String.valueOf(density));
	    }
	    else if (density==DisplayMetrics.DENSITY_HIGH) {
	    	Log.i(TAG, "DENSITY_HIGH... Density is " + String.valueOf(density));
	    }
	    else if (density==DisplayMetrics.DENSITY_MEDIUM) {
	    	Log.i(TAG, "DENSITY_MEDIUM... Density is " + String.valueOf(density));
	    }
	    else if (density==DisplayMetrics.DENSITY_LOW) {
	    	Log.i(TAG, "DENSITY_LOW... Density is " + String.valueOf(density));
	    }
	    else {
	        Log.i(TAG, "Density is neither HIGH, MEDIUM OR LOW.  Density is " + String.valueOf(density));
	    }
	}
	
	protected int getScreenSize(){
		return getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
	}
	
	public static int getCurrentScreenSize(Context context){
		return context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
	}
	
	protected void openActivity(Class activity){
		Intent intent = new Intent(BaseActivity.this, activity);
		finish();
		startActivity(intent);
	}
	
	protected void createSelectGameLevelDialog(){
		selectGameLevelDialog = new CustomDialog(this, 2);
        
		//radioGroup = (RadioGroup) selectGameLevelDialog.findViewById(R.id.radiogroup);
		game_level0 = (RadioButton) selectGameLevelDialog.findViewById(R.id.game_level0);
		game_level1 = (RadioButton) selectGameLevelDialog.findViewById(R.id.game_level1);
		game_level2 = (RadioButton) selectGameLevelDialog.findViewById(R.id.game_level2);
		game_level3 = (RadioButton) selectGameLevelDialog.findViewById(R.id.game_level3);
		
		LinearLayout game_level0_layout = (LinearLayout) selectGameLevelDialog.findViewById(R.id.game_level0_layout);
		LinearLayout game_level1_layout = (LinearLayout) selectGameLevelDialog.findViewById(R.id.game_level1_layout);
		LinearLayout game_level2_layout = (LinearLayout) selectGameLevelDialog.findViewById(R.id.game_level2_layout);
		LinearLayout game_level3_layout = (LinearLayout) selectGameLevelDialog.findViewById(R.id.game_level3_layout);
		
		ImageView text0 = (ImageView) selectGameLevelDialog.findViewById(R.id.text0);
		ImageView text1 = (ImageView) selectGameLevelDialog.findViewById(R.id.text1);
		ImageView text2 = (ImageView) selectGameLevelDialog.findViewById(R.id.text2);
		ImageView text3 = (ImageView) selectGameLevelDialog.findViewById(R.id.text3);
		ok_button = (Button) selectGameLevelDialog.findViewById(R.id.ok_button);
		ok_button.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				selectGameLevelDialog.hide();	
			}
		});
		//radioGroup.setOnCheckedChangeListener(checkChangeListener);
		
		OnTouchListener listener0 = new OnTouchListener() {			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Prefutils.setGameLevel(BaseActivity.this, Prefutils.TWO_PLAYERS);
				initPlayers();
				game_level0.setChecked(true);
				game_level1.setChecked(false);
				game_level2.setChecked(false);
				game_level3.setChecked(false);
				//selectGameLevelDialog.hide();
				changePlayer();
				return false;
			}
		};
		game_level0_layout.setOnTouchListener(listener0);
		game_level0.setOnTouchListener(listener0);
		text0.setOnTouchListener(listener0);
		
		OnTouchListener listener1 = new OnTouchListener() {		
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Prefutils.setGameLevel(BaseActivity.this, Prefutils.ONE_PLAYER_EASY);
				initPlayers();
				game_level0.setChecked(false);
				game_level1.setChecked(true);
				game_level2.setChecked(false);
				game_level3.setChecked(false);
				//selectGameLevelDialog.hide();
				changePlayer();
				return false;
			}
		};
		game_level1_layout.setOnTouchListener(listener1);
		game_level1.setOnTouchListener(listener1);
		text1.setOnTouchListener(listener1);
		
		OnTouchListener listener2 = new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Prefutils.setGameLevel(BaseActivity.this, Prefutils.ONE_PLAYER_MEDIUM);
				initPlayers();
				game_level1.setChecked(false);
				game_level0.setChecked(false);
				game_level2.setChecked(true);
				game_level3.setChecked(false);
				//selectGameLevelDialog.hide();
				changePlayer();
				return false;
			}
		};
		game_level2_layout.setOnTouchListener(listener2);
		game_level2.setOnTouchListener(listener2);
		text2.setOnTouchListener(listener2);
		
		OnTouchListener listener3 = new OnTouchListener() {		
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Prefutils.setGameLevel(BaseActivity.this, Prefutils.ONE_PLAYER_HARD);
				initPlayers();
				game_level1.setChecked(false);
				game_level2.setChecked(false);
				game_level0.setChecked(false);
				game_level3.setChecked(true);
				//selectGameLevelDialog.hide();
				changePlayer();
				return false;
			}
		};
		game_level3_layout.setOnTouchListener(listener3);
		game_level3.setOnTouchListener(listener3);
		text3.setOnTouchListener(listener3);
		
		//writeOnDrawable(int drawableId, String text)
	}
	
	private class CustomDialog extends Dialog{
		public CustomDialog(Context context, int sizeValue){
			super(context);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.dialog_game_level);
			getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		}
	}
	
	public BitmapDrawable writeOnDrawable(int drawableId, String text){

        Bitmap bm = BitmapFactory.decodeResource(getResources(), drawableId).copy(Bitmap.Config.ARGB_8888, true);

        Paint paint = new Paint(); 
        paint.setStyle(Style.FILL);  
        paint.setColor(Color.BLACK); 
        paint.setTextSize(20); 

        Canvas canvas = new Canvas(bm);
        canvas.drawText(text, 0, bm.getHeight()/2, paint);

        return new BitmapDrawable(bm);
    }
	
	protected void showSelectGameLevelDialog(){			
		selectGameLevelDialog.show();
        initRbViews();
	}
	
	protected void setButtonSize(Button button, int scale){
		android.view.ViewGroup.LayoutParams params = button
				.getLayoutParams();
		Display display = getWindowManager().getDefaultDisplay();
		params.width = display.getWidth() / scale;
		button.setLayoutParams(params);
	}
	
	protected void resizeImageByHeight(View view, int sizeY) {
		android.view.ViewGroup.LayoutParams params = view.getLayoutParams();
		int sizeX = sizeY * params.height / params.width;
		params.width = sizeX;
		params.height = sizeY;
		view.setLayoutParams(params);
	}
	
	protected void resizeViewByWidth(View view, int sizeX) {
		android.view.ViewGroup.LayoutParams params = view.getLayoutParams();
		int sizeY = sizeX * params.height / params.width;
		params.width = sizeX;
		//params.height = sizeY;
		view.setLayoutParams(params);
	}
	
	protected void resizeView(View view, int value) {
		int sizeX = displayWidth / value;
		resizeViewByWidth(view, sizeX);
	}
	
	protected void initRbViews() {
		int game_level = Prefutils.getGameLevel(BaseActivity.this);
		//Log.i(TAG, "Game level: " + game_level);
		if(game_level == Prefutils.TWO_PLAYERS){
			uncheckAll();
			game_level0.setChecked(true);
		}
		else if(game_level == Prefutils.ONE_PLAYER_EASY){
			uncheckAll();
			game_level1.setChecked(true);
		}
		else if(game_level == Prefutils.ONE_PLAYER_MEDIUM){
			uncheckAll();
			game_level2.setChecked(true);
		}
		else if(game_level == Prefutils.ONE_PLAYER_HARD){
			uncheckAll();
			game_level3.setChecked(true);
		}
	}
	
	protected void uncheckAll(){
		game_level0.setChecked(false);
		game_level1.setChecked(false);
		game_level2.setChecked(false);
		game_level3.setChecked(false);
	}
	
	private OnCheckedChangeListener checkChangeListener = new OnCheckedChangeListener() {
		public void onCheckedChanged(RadioGroup arg0, int id) {
			if(id == R.id.game_level0) {
				Prefutils.setGameLevel(BaseActivity.this, Prefutils.TWO_PLAYERS);
			}
			else if(id == R.id.game_level1) {
				Prefutils.setGameLevel(BaseActivity.this, Prefutils.ONE_PLAYER_EASY);
			}
			else if(id == R.id.game_level2) {
				Prefutils.setGameLevel(BaseActivity.this, Prefutils.ONE_PLAYER_MEDIUM);
			}
			else if(id == R.id.game_level3) {
				Prefutils.setGameLevel(BaseActivity.this, Prefutils.ONE_PLAYER_HARD);
			}
			initPlayers();
		}
	};
	
	protected void initPlayers(){
	}
	
	protected void changePlayer(){
		
	}
}
