package com.chameapps.fouronthefarm.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.chameapps.fouronthefarm.R;
import com.chameapps.fouronthefarm.model.GameBoard;

public class GameState extends MenuActivity {
	public static final String TAG = "Save state";

	private static final String NEXT_PLAYER = "player";
	private static final String GAME_CELLS = "cells";

	protected GameBoard board;
	private SharedPreferences mPrefs;
	
	protected boolean isGameOver = false;
	
	public void getPreferences() {
	    Context mContext = this.getApplicationContext();
	    mPrefs = mContext.getSharedPreferences("FourOnTheFarmPrefs", Context.MODE_PRIVATE);
	}

    public void saveState() {
    	getPreferences();
    	SharedPreferences.Editor edit = mPrefs.edit();
        for(int r = 0; r < GameBoard.ROWS; r++){
        	for(int c = 0; c < GameBoard.COLUMNS; c++){
    			edit.putInt("" + r + c, board.gameModel[r][c]);
    		}
		}	
        edit.putInt("next", board.getNextPlayer());
        edit.commit();
    }
    
    protected void clearState() {
    	getPreferences();
    	SharedPreferences.Editor edit = mPrefs.edit();
        for(int r = 0; r < GameBoard.ROWS; r++){
        	for(int c = 0; c < GameBoard.COLUMNS; c++){
    			edit.putInt("" + r + c, 0);
    		}
		}	
        edit.putInt("next", R.drawable.tomato);
        edit.commit();
    }
    
    protected int[][] restoreState() {
		getPreferences();
		int[][] gameModel = new int[GameBoard.ROWS][GameBoard.COLUMNS];
		mPrefs.getAll();
        for(int r = 0; r < GameBoard.ROWS; r++){
        	for(int c = 0; c < GameBoard.COLUMNS; c++){
        		gameModel[r][c] = mPrefs.getInt("" + r + c, 0);
    		}
		}
        return gameModel;
    }
    
    protected int restoreNextPlayer() {
		getPreferences();
		return mPrefs.getInt("next", 1);
    }

	@Override
	public void onPause() {
		super.onPause();
		Log.d(TAG, "onPause");
		if(!isGameOver)
			saveState();
	}

	@Override
	public void onStop() {
		super.onStop();
		Log.d(TAG, "onStop");
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d(TAG, "onResume");
	}

	public void onStart() {
		super.onStart();
		Log.d(TAG, "onStart");
	}

	public void onRestart() {
		super.onRestart();
		Log.d(TAG, "onRestart");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy");
	}
}
