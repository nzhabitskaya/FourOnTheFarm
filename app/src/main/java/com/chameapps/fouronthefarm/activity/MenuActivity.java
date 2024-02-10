package com.chameapps.fouronthefarm.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.chameapps.fouronthefarm.R;
import com.chameapps.fouronthefarm.utils.GameUtils;

public class MenuActivity extends BaseActivity{
	public static final int PLAYER1 = 0;
	public static final int PLAYER2 = 1;
	public static final int NOBODY = 2;
	public static final int COMPUTER = 3;
	public static final int YOU = 4;
	
	// New game after animation ends
	protected boolean isNewGame = false;
	protected boolean isAnimate = false;
	
	public static final String TAG = "MenuActivity";
	
	protected CustomDialog player1WinsDialog;
	protected CustomDialog player2WinsDialog;
	protected CustomDialog nobodyWinsDialog;
	protected CustomDialog youWinsDialog;
	protected CustomDialog computerWinsDialog;
	protected CustomDialog newGameDialog;
	protected CustomDialog exitDialog;
	
	protected void createNewGame(){
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.game_menu, menu);
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if(id == R.id.exit) {
			showDialog(DIALOG_EXIT, MenuActivity.NOBODY, -1);
		}
		else if(id == R.id.help) {
			Intent intent2 = new Intent(MenuActivity.this, HelpActivity.class);
			startActivity(intent2);
		}
		else if(id == R.id.about) {
			Intent intent = new Intent(MenuActivity.this, AboutActivity.class);
			startActivity(intent);
	}
		else if(id == R.id.new_game) {
			showDialog(DIALOG_NEW_GAME, MenuActivity.NOBODY, -1);
		}
		return true;
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
	
	protected void initDialogs(){
		player1WinsDialog = new CustomDialog(this, R.layout.dialog_ok);
		TextView dialog_label = (TextView) player1WinsDialog.findViewById(R.id.dialog_label);
		setDialogFontAndText(dialog_label, R.string.tomatoes_won);
        Button ok_button = (Button) player1WinsDialog.findViewById(R.id.ok_button);
		ok_button.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				player1WinsDialog.hide();
				showScore();
			}
		});
		
		player2WinsDialog = new CustomDialog(this, R.layout.dialog_ok);
		TextView dialog_label2 = (TextView) player2WinsDialog.findViewById(R.id.dialog_label);
		setDialogFontAndText(dialog_label2, R.string.pumpkins_won);
        Button ok_button2 = (Button) player2WinsDialog.findViewById(R.id.ok_button);
		ok_button2.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				player2WinsDialog.hide();
				showScore();	
			}
		});
		
		nobodyWinsDialog = new CustomDialog(this, R.layout.dialog_ok);
		TextView dialog_label3 = (TextView) nobodyWinsDialog.findViewById(R.id.dialog_label);
		setDialogFontAndText(dialog_label3, R.string.nobody_won);
        Button ok_button3 = (Button) nobodyWinsDialog.findViewById(R.id.ok_button);
		ok_button3.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				nobodyWinsDialog.hide();
				createNewGame();	
			}
		});
		
		youWinsDialog = new CustomDialog(this, R.layout.dialog_ok);
		TextView dialog_label4 = (TextView) youWinsDialog.findViewById(R.id.dialog_label);
		setDialogFontAndText(dialog_label4, R.string.you_won);
        Button ok_button4 = (Button) youWinsDialog.findViewById(R.id.ok_button);
		ok_button4.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				youWinsDialog.hide();
				createNewGame();
				showScore();	
			}
		});
		
		computerWinsDialog = new CustomDialog(this, R.layout.dialog_ok);
		TextView dialog_label5 = (TextView) computerWinsDialog.findViewById(R.id.dialog_label);
		setDialogFontAndText(dialog_label5, R.string.computer_won);
        Button ok_button5 = (Button) computerWinsDialog.findViewById(R.id.ok_button);
		ok_button5.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				computerWinsDialog.hide();
				createNewGame();	
			}
		});
		
		newGameDialog = new CustomDialog(this, R.layout.dialog_ok_cancel);
		TextView dialog_label6 = (TextView) newGameDialog.findViewById(R.id.dialog_label);
		setDialogFontAndText(dialog_label6, R.string.msg_new_game);
        Button ok_button6 = (Button) newGameDialog.findViewById(R.id.ok_button);
        ok_button6.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				newGameDialog.hide();
				if (isAnimate == false){
					createNewGame();
					showSelectGameLevelDialog();
				}
				else
					isNewGame = true;	
			}
		});
		Button ok_button7 = (Button) newGameDialog.findViewById(R.id.cancel_button);
		ok_button7.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				newGameDialog.hide();	
			}
		});
		
		exitDialog = new CustomDialog(this, R.layout.dialog_ok_cancel);
        Button ok_button8 = (Button) exitDialog.findViewById(R.id.ok_button);
        TextView dialog_label7 = (TextView) exitDialog.findViewById(R.id.dialog_label);
		setDialogFontAndText(dialog_label7, R.string.msg_exit);
        ok_button8.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				exitDialog.hide();
				createNewGame();
				finish();
			}
		});
		Button ok_button9 = (Button) exitDialog.findViewById(R.id.cancel_button);
		ok_button9.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				exitDialog.hide();	
			}
		});
	}
	
	private void showScore(){
		Intent intent = new Intent(MenuActivity.this, ScoreActivity.class);
	    createNewGame();
	    startActivity(intent);
	}
	
	protected void showDialog(int id, int winner, final int score) {
		switch (id) {
		case DIALOG_USER_MESSAGE:
			if(winner == PLAYER1){
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {
				    public void run() {
				    	player1WinsDialog.show();
				    }
				}, 3000);  // 3000 milliseconds
			}
			else if (winner == PLAYER2){
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {
				    public void run() {
				    	player2WinsDialog.show();
				    }
				}, 3000);
			}
			else if (winner == NOBODY){
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {
				    public void run() {
				    	nobodyWinsDialog.show();
				    }
				}, 3000);
			}
			else if (winner == YOU)
			{
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {
				    public void run() {
				    	youWinsDialog.show();
				    }
				}, 3000);  // 3000 milliseconds\
			}
			else if (winner == COMPUTER)
			{
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {
				    public void run() {
				    	computerWinsDialog.show();
				    }
				}, 3000);
			}
			break;
		case DIALOG_EXIT:
			exitDialog.show();
			break;
		case DIALOG_NEW_GAME:
			newGameDialog.show();
			break;
		}
	}
	
	//------------------------------------------------ Custom dialogs----------------------------------------------------------//
	
	class CustomDialog extends Dialog{
		public CustomDialog(Context context, int layout){
			super(context);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(layout);
			getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		}
	}
}
