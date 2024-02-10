package com.chameapps.fouronthefarm.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;

import com.chameapps.fouronthefarm.model.GameBoard;

public class GameUtils {

	public static SharedPreferences getPreferences(Context context) {
		return context.getSharedPreferences("FourOnTheFarmPrefs",
				Context.MODE_PRIVATE);
	}

	public static int[][] restoreState(Context context) {
		SharedPreferences mPrefs = getPreferences(context);
		int[][] gameModel = new int[GameBoard.ROWS][GameBoard.COLUMNS];
		mPrefs.getAll();
		for (int r = 0; r < GameBoard.ROWS; r++) {
			for (int c = 0; c < GameBoard.COLUMNS; c++) {
				gameModel[r][c] = mPrefs.getInt("" + r + c, 0);
			}
		}
		return gameModel;
	}

	public static boolean isNewGame(Context context) {
		boolean isNewGame = true;
		int[][] gameModel = restoreState(context);
		for (int r = 0; r < GameBoard.ROWS; r++) {
			for (int c = 0; c < GameBoard.COLUMNS; c++) {
				if (gameModel[r][c] == 1) {
					isNewGame = false;
				} else if (gameModel[r][c] == 2) {
					isNewGame = false;
				}
			}
		}
		return isNewGame;
	}

	public static boolean isTablet(Context context) {
		TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		//if(getScreenSize() == Configuration.SCREENLAYOUT_SIZE_XLARGE)
		if (manager.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
			return true;
		} else {
			return false;
		}
	}
}