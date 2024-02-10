package com.chameapps.fouronthefarm.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Score extends JSONObject{
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String SCORE = "score";
	
	public Score(int id, String name, int score) throws JSONException{
		put(ID, id);
		put(NAME, name);
		put(SCORE, score);
	}
	
	public int getId() throws JSONException {
		return (Integer)get(ID);
	}
	public void setId(int id) throws JSONException {
		put(ID, id);
	}
	public String getName() throws JSONException {
		return (String)get(NAME);
	}
	public void setName(String name) throws JSONException {
		put(NAME, name);
	}
	public int getScore() throws JSONException {
		return (Integer)get(SCORE);
	}
	public void setScore(int score) throws JSONException {
		put(SCORE, score);
	}
}
