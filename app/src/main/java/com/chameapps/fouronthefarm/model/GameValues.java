package com.chameapps.fouronthefarm.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.chameapps.fouronthefarm.utils.FileUtils;

import android.util.Log;

public class GameValues {
	public final static String TAG = "GameValues";
	private static GameValues gameScore;

	private JSONArray scores;
	private int maxScore;

	GameValues() {
		JSONArray scores = FileUtils.readJSONFromFile();
		this.scores = scores;
		this.maxScore = 0;
	}

	public static GameValues getInstance() {
		if (gameScore == null)
			gameScore = new GameValues();
		return gameScore;
	}

	public int getHighScore() {
		if (scores.length() > 0) {
			try {
				for(int i = 0; i < scores.length(); i++){
					int score = scores.getJSONObject(i).getInt("score");
					if(score > maxScore)
						maxScore = score;
				}
			} catch (JSONException e) {

			}
		}
		return maxScore;
	}

	public void setHighScore(int maxScore) {
		this.maxScore = maxScore;
	}

	public JSONArray getGameScores() {
		return scores;
	}

	public void setGameScore(JSONArray score) {
		this.scores = score;
	}

	public void addGameScore(Score score) {
		this.scores.put(score);
	}
}
