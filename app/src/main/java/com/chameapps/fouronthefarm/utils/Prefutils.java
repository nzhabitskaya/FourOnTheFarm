package com.chameapps.fouronthefarm.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class Prefutils {
	public static final int TWO_PLAYERS = 0;
	public static final int ONE_PLAYER_EASY = 1;
	public static final int ONE_PLAYER_MEDIUM = 2;
	public static final int ONE_PLAYER_HARD = 3;

	public static void setGameLevel(Context context, int level) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = prefs.edit();
		switch (level) {
		case TWO_PLAYERS:
			editor.putString(KEY.GAME_LEVEL, "0");
			break;
		case ONE_PLAYER_EASY:
			editor.putString(KEY.GAME_LEVEL, "1");
			break;
		case ONE_PLAYER_MEDIUM:
			editor.putString(KEY.GAME_LEVEL, "2");
			break;
		case ONE_PLAYER_HARD:
			editor.putString(KEY.GAME_LEVEL, "3");
			break;
		}
		editor.commit();
	}
	
	public static int getGameLevel(Context context){
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		return Integer.parseInt(prefs.getString(KEY.GAME_LEVEL, "0"));
	}
	
	public static void setSound(Context context, boolean isSoundOn) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = prefs.edit();
			editor.putBoolean(KEY.SOUND, isSoundOn);
		editor.commit();
	}
	
	public static boolean getSound(Context context) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		return prefs.getBoolean(KEY.SOUND, false);
	}
	
	public static void setPlayer1Name(Context context, String name) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = prefs.edit();
		editor.putString(KEY.PLAYER1, name);
		editor.commit();
	}
	
	public static void setPlayer2Name(Context context, String name) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = prefs.edit();
		editor.putString(KEY.PLAYER2, name);
		editor.commit();
	}
	
	public String getPlayer1Name(Context context){
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		return prefs.getString(KEY.PLAYER1, "");
	}
	
	public String getPlayer2Name(Context context){
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		return prefs.getString(KEY.PLAYER2, "");
	}
	
	public static class KEY {	
		public static final String GAME_LEVEL = "game_level";
		public static final String SOUND = "sound_settings";
		public static final String PLAYER1 = "player1_name";
		public static final String PLAYER2 = "player2_name";
	}
}
