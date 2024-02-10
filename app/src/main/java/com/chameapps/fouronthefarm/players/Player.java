package com.chameapps.fouronthefarm.players;

import com.chameapps.fouronthefarm.model.GameBoard;

public interface Player {
	public static final String TAG = "Player";
	
    public boolean go(GameBoard b);
    public void setMove(int col);
    public int getType();
}
