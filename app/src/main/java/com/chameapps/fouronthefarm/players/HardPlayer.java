package com.chameapps.fouronthefarm.players;

import android.util.Log;

import com.chameapps.fouronthefarm.model.GameBoard;

public class HardPlayer implements Player {
    
    public HardPlayer() {
    	Log.w(TAG, "AI player initialized, level:hard");
    }
    
    private boolean check_row(int r) { return ((r>=0) && (r <=5));}
    private boolean check_col(int c) { return ((c>=0) && (c <=6));}
    private boolean valid(int i, int j) {return ((check_row(i)) && (check_col(j)));}
    
	public void setMove(int col) {
		
	}
    
	public int getType() {
			return 3;
		}
    
	@Override
    public boolean go(GameBoard b) {
        int[][] bo=b.view();
        int[] c = b.ret_col();
        int me = b.getNextPlayer();
        int oppo = 3 - me;
        
        int[] h= new int[7];
        for (int i = 0; i < 7; i++) h[i]=0;
        
        
        for (int i=0; i<7; i++)
        {
          if (c[i]==6) h[i]=0; else{
            int col = i;
            int row = 5 - c[i];
            
            
            if ((col>=3) 
                && (bo[row][col-1] == me)
                && (bo[row][col-2] == me)
                && (bo[row][col-3] == me))
                h[i]=h[i]+16;
            //right
            if ((col<=3) 
                && (bo[row][col+1] == me)
                && (bo[row][col+2] == me)
                && (bo[row][col+3] == me))
                h[i]=h[i]+16;
            //check y direction
            if ((row<=2) 
                && (bo[row+1][col] == me)
                && (bo[row+2][col] == me)
                && (bo[row+3][col] == me))
                h[i]=h[i]+16;
            //check left diagonal
            if ((col>=3) && (row<=2)
                && (bo[row+1][col-1] == me)
                && (bo[row+2][col-2] == me)
                && (bo[row+3][col-3] == me))
                h[i]=h[i]+16;
            
            if ((col<=3) && (row<=2)
                && (bo[row+1][col+1] == me)
                && (bo[row+2][col+2] == me)
                && (bo[row+3][col+3] == me))
                h[i]=h[i]+16;
            
            if ((col>=3) && (row>=3)
                && (bo[row-1][col-1] == me)
                && (bo[row-2][col-2] == me)
                && (bo[row-3][col-3] == me))
                h[i]=h[i]+16;
            
            if ((col<=3) && (row>=3)
                && (bo[row-1][col+1] == me)
                && (bo[row-2][col+2] == me)
                && (bo[row-3][col+3] == me))
                h[i]=h[i]+16;
            
            if ((col>=2) 
                && (bo[row][col-1] == me)
                && (bo[row][col-2] == me))
                h[i]=h[i]+4;
            //right
            if ((col<=4) 
                && (bo[row][col+1] == me)
                && (bo[row][col+2] == me))
                h[i]=h[i]+4;
            //check y direction
            if ((row<=3) 
                && (bo[row+1][col] == me)
                && (bo[row+2][col] == me))
                h[i]=h[i]+4;
            //check left diagonal
            if ((col>=2) && (row<=3)
                && (bo[row+1][col-1] == me)
                && (bo[row+2][col-2] == me))
                h[i]=h[i]+4;
            
            if ((col<=4) && (row<=3)
                && (bo[row+1][col+1] == me)
                && (bo[row+2][col+2] == me))
                h[i]=h[i]+4;
            
            if ((col>=2) && (row>=2)
                && (bo[row-1][col-1] == me)
                && (bo[row-2][col-2] == me))
                h[i]=h[i]+4;
            
            if ((col<=4) && (row>=2)
                && (bo[row-1][col+1] == me)
                && (bo[row-2][col+2] == me))
                h[i]=h[i]+4;
            
            if ((col>=1) 
                && (bo[row][col-1] == me))
                h[i]=h[i]+2;
            //right
            
            if ((col<=5) 
                && (bo[row][col+1] == me))
                h[i]=h[i]+2;
            //check y direction
            if ((row<=4) 
                && (bo[row+1][col] == me))
                h[i]=h[i]+2;
            //check left diagonal
            if ((col>=1) && (row<=4)
                && (bo[row+1][col-1] == me))
                h[i]=h[i]+2;
            
            if ((col<=5) && (row<=4)
                && (bo[row+1][col+1] == me))
                h[i]=h[i]+2;
            
            if ((col>=1) && (row>=1)
                && (bo[row-1][col-1] == me))
                h[i]=h[i]+2;
            
            if ((col<=5) && (row>=1)
                && (bo[row-1][col+1] == me))
                h[i]=h[i]+2;
            
            //check x direction.
            //left
            if ((col>=3) 
                && (bo[row][col-1] == oppo)
                && (bo[row][col-2] == oppo)
                && (bo[row][col-3] == oppo))
                h[i]=h[i]+8;
            //right
            if ((col<=3) 
                && (bo[row][col+1] == oppo)
                && (bo[row][col+2] == oppo)
                && (bo[row][col+3] == oppo))
                h[i]=h[i]+8;
            //check y direction
            if ((row<=2) 
                && (bo[row+1][col] == oppo)
                && (bo[row+2][col] == oppo)
                && (bo[row+3][col] == oppo))
                h[i]=h[i]+8;
            //check left diagonal
            if ((col>=3) && (row<=2)
                && (bo[row+1][col-1] == oppo)
                && (bo[row+2][col-2] == oppo)
                && (bo[row+3][col-3] == oppo))
                h[i]=h[i]+8;
            
            if ((col<=3) && (row<=2)
                && (bo[row+1][col+1] == oppo)
                && (bo[row+2][col+2] == oppo)
                && (bo[row+3][col+3] == oppo))
                h[i]=h[i]+8;
            
            if ((col>=3) && (row>=3)
                && (bo[row-1][col-1] == oppo)
                && (bo[row-2][col-2] == oppo)
                && (bo[row-3][col-3] == oppo))
                h[i]=h[i]+8;
            
            if ((col<=3) && (row>=3)
                && (bo[row-1][col+1] == oppo)
                && (bo[row-2][col+2] == oppo)
                && (bo[row-3][col+3] == oppo))
                h[i]=h[i]+8;
            
            if ((col>=2) 
                && (bo[row][col-1] == oppo)
                && (bo[row][col-2] == oppo))
                h[i]=h[i]+4;
            //right
            if ((col<=4) 
                && (bo[row][col+1] == oppo)
                && (bo[row][col+2] == oppo))
                h[i]=h[i]+4;
            //check y direction
            if ((row<=3) 
                && (bo[row+1][col] == oppo)
                && (bo[row+2][col] == oppo))
                h[i]=h[i]+4;
            //check left diagonal
            if ((col>=2) && (row<=3)
                && (bo[row+1][col-1] == oppo)
                && (bo[row+2][col-2] == oppo))
                h[i]=h[i]+4;
            
            if ((col<=4) && (row<=3)
                && (bo[row+1][col+1] == oppo)
                && (bo[row+2][col+2] == oppo))
                h[i]=h[i]+4;
            
            if ((col>=2) && (row>=2)
                && (bo[row-1][col-1] == oppo)
                && (bo[row-2][col-2] == oppo))
                h[i]=h[i]+4;
            
            if ((col<=4) && (row>=2)
                && (bo[row-1][col+1] == oppo)
                && (bo[row-2][col+2] == oppo))
                h[i]=h[i]+4;
            
            if ((col>=1) 
                && (bo[row][col-1] == oppo))
                h[i]=h[i]+2;
            //right
            
            if ((col<=5) 
                && (bo[row][col+1] == oppo))
                h[i]=h[i]+2;
            //check y direction
            if ((row<=4) 
                && (bo[row+1][col] == oppo))
                h[i]=h[i]+2;
            //check left diagonal
            if ((col>=1) && (row<=4)
                && (bo[row+1][col-1] == oppo))
                h[i]=h[i]+2;
            
            if ((col<=5) && (row<=4)
                && (bo[row+1][col+1] == oppo))
                h[i]=h[i]+2;
            
            if ((col>=1) && (row>=1)
                && (bo[row-1][col-1] == oppo))
                h[i]=h[i]+2;
            
            if ((col<=5) && (row>=1)
                && (bo[row-1][col+1] == oppo))
                h[i]=h[i]+2;            
          }              
        }
        int max = 0;
        int mm = 3;
        int sum = 0;
        for (int i=0; i<7; i++) {
            if (h[i]>max) {max=h[i]; mm=i;}
            sum= sum+h[i];
        }
        if (sum==0) mm = (int) (Math.random()*7);
        //System.out.println();
        //System.out.println(mm);
        return b.move(mm);
    } 
}
