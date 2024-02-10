package com.chameapps.fouronthefarm.players;

import android.util.Log;

import com.chameapps.fouronthefarm.model.GameBoard;
public class EasyPlayer implements Player{
    
    public EasyPlayer() {
    	Log.w(TAG, "AI player initialized, level: easy");
    }
    
	public void setMove(int col) {
		
	}
   
	public int getType() {
			return 1;
		}
	
	@Override
    public boolean go(GameBoard board) {
        int m= (int) (Math.random()*7);
        
        while (board.gameCells[m]==6) m=(int) (Math.random()*7);
        
        //System.out.println(m);        
        return board.move(m);
    }    
}
