package com.chameapps.fouronthefarm.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chameapps.fouronthefarm.R;
import com.chameapps.fouronthefarm.utils.GameUtils;
import com.chameapps.fouronthefarm.utils.Prefutils;

public class StartActivity extends BaseActivity{
	public static final String TAG = "StartActivity";
	private Button startBtn;
	private Button soundBtn;
	private Button helpBtn;
	private Button aboutBtn;
	// TODO for banner
	private Button buyBtn;
	private CustomDialog buyDialog;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_activity);
		
		// TODO for banner
		createBuyDialog();
		initButtons();
		
		// Resize panel with buttons
		setLayoutMarginTop();
		
		if(!GameUtils.isNewGame(this)){
			Log.i(TAG, "Load prev game");
			//loadPrevGame();
			Intent intent = new Intent(StartActivity.this, GameActivity.class);
			Bundle mBundle = new Bundle();
			mBundle.putBoolean("isShowPrefs", false);
			intent.putExtras(mBundle);
			startActivity(intent);
		}
	}
	
	private void setLayoutMarginTop(){
		LinearLayout layout = (LinearLayout) findViewById(R.id.buttons_panel);
		layout.setPadding(0, displayHeight / 2, 0, 0);
	}
	
	private void initButtons() {
		startBtn = (Button) findViewById(R.id.start_button);
		soundBtn = (Button) findViewById(R.id.sound);
		helpBtn = (Button) findViewById(R.id.help);
		aboutBtn = (Button) findViewById(R.id.about);
		
		boolean isSound = Prefutils.getSound(StartActivity.this);
		if(isSound)
			soundBtn.setBackgroundResource(R.drawable.button_sound_on);
		else
			soundBtn.setBackgroundResource(R.drawable.button_sound_off);

		startBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {	
				Intent intent = new Intent(StartActivity.this, GameActivity.class);
				Bundle mBundle = new Bundle();
				mBundle.putBoolean("isShowPrefs", true);
				intent.putExtras(mBundle);
				startActivity(intent);
			}
		});
		
		soundBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				boolean isSound = Prefutils.getSound(StartActivity.this);
				if(isSound){
					Prefutils.setSound(StartActivity.this, false);
					soundBtn.setBackgroundResource(R.drawable.button_sound_off);
				}
				else{
					Prefutils.setSound(StartActivity.this, true);
					soundBtn.setBackgroundResource(R.drawable.button_sound_on);
				}
			}
		});

		helpBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(StartActivity.this, HelpActivity.class);
				startActivity(intent);
				//showDialog(DIALOG_HELP, MenuActivity.NOBODY, -1);
			}
		});
		
		aboutBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {	
				Intent intent = new Intent(StartActivity.this, AboutActivity.class);
				startActivity(intent);
			}
		});

	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	finish();
	        System.exit(0);
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	// TODO for banner
	protected void createBuyDialog(){
		buyDialog = new CustomDialog(this, R.layout.dialog_buy);
		TextView dialog_label3 = (TextView) buyDialog.findViewById(R.id.dialog_label);
		setDialogFontAndText(dialog_label3, R.string.to_buy, 28.f, 16.f);
	    Button ok_button = (Button) buyDialog.findViewById(R.id.buy_button);
	    Button cancel_button = (Button) buyDialog.findViewById(R.id.cancel_button);
		ok_button.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				buyDialog.hide();
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.pearmobile.fouronthefarm&feature=search_result#?t=W251bGwsMSwxLDEsImNvbS5wZWFybW9iaWxlLmZvdXJvbnRoZWZhcm0iXQ"));
				startActivity(browserIntent);
			}
		});
		cancel_button.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				buyDialog.hide();
			}
		});
	}
	
	private void setDialogFontAndText(TextView textView, int resid, float textSizeX, float textSize){
		Typeface font = Typeface.createFromAsset(getAssets(), "fonts/ObelixPro-cyr.ttf");
		textView.setTypeface(font);
		textView.setText(resid);
		if(GameUtils.isTablet(getApplicationContext()))
			textView.setTextSize(textSizeX);
		else
			textView.setTextSize(textSize);
		textView.setLineSpacing(5, 1);
	}
	
	private void setDialogFontAndText(TextView textView, int resid){
		Typeface font = Typeface.createFromAsset(getAssets(), "fonts/ObelixPro-cyr.ttf");
		textView.setTypeface(font);
		textView.setText(resid);
		if(GameUtils.isTablet(getApplicationContext()))
			textView.setTextSize(32.f);
		else
			textView.setTextSize(20.f);
		textView.setLineSpacing(5, 1);
	}
	
	class CustomDialog extends Dialog{
		public CustomDialog(Context context, int layout){
			super(context);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(layout);
			getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		}
	}

}
