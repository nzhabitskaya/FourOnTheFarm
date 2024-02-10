package com.chameapps.fouronthefarm.model;

public class GameBoard{
	public static final int FIRST_PLAYER = 1;
	public static final int SECOND_PLAYER = 2;
	
	public static final int ROWS = 6;
	public static final int COLUMNS = 7;
	private int coordX = 0;
	private int coordY = 0;

	public int[] gameCells;
	public int[][] gameModel;
	
	private int[][] gameWinners = new int[ROWS * COLUMNS / 2][2];
	private int k = 0;
	
	private boolean isOut = true;
	public String moveList;

	private int nextPlayer = FIRST_PLAYER;
	private int winner = 0;
	private int score = 0;

	/** Creates a new instance of GameBoard */
	public GameBoard() {
		nextPlayer = 1;
		gameModel = new int[ROWS][COLUMNS];
		gameCells = new int[COLUMNS];
		winner = 0;
		clear();
		moveList = "";
		isOut = true;
	}

	public void parseMove(String move_list) {
		for (int i = 0; i < move_list.length(); i++) {
			int tm = Integer.parseInt((new Character(move_list.charAt(i)))
					.toString());
			move(tm);
		}

	}

	public boolean move(int pos) {
		if ((pos < 0) || (pos > 6) && gameCells[pos] < ROWS) {
			System.out.println("invalid input\n\n");
			return false;
		} else {
			if ((gameCells[pos] == ROWS) || isOut) {
				System.out.println("Column full");
				return false;
			} else {
				coordY = pos;
				moveList += (new Integer(pos)).toString();
				coordX = 5 - gameCells[pos];
				gameCells[pos]++;
				gameModel[coordX][coordY] = nextPlayer;
				nextPlayer = 3 - nextPlayer;
				return true;
			}
		}

	}
	
	public boolean move(int ball, int x, int y) {
		if ((y < 0) || (y > 6) && gameCells[y] < ROWS) {
			System.out.println("invalid input\n\n");
			return false;
		} else {
			if ((gameCells[y] == ROWS) || isOut) {
				System.out.println("Column full");
				return false;
			} else {
				coordY = y;
				moveList += (new Integer(y)).toString();
				coordX = x;
				gameCells[y]++;
				gameModel[coordX][coordY] = ball;
				return true;
			}
		}

	}

	public int[][] view() {
		return gameModel;
	}

	public int[] viewcol() {
		return gameCells;
	}

	public void clear() {
		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 7; j++) {
				gameModel[i][j] = 0;
			}
		for (int j = 0; j < 7; j++)
			gameCells[j] = 0;
	}

	public boolean isOut() {
		return isOut;
	}

	public void setOut(boolean isOut) {
		this.isOut = isOut;
	}

	public int getNextPlayer() {
		return nextPlayer;
	}
	
	public void changePlayer() {
		nextPlayer = 3 - nextPlayer;
	}

	public int getWinner() {
		return winner;
	}

	public int[] ret_col() {
		return gameCells;
	}
	
	public void setScore(int score){
		this.score = score;
	}
	
	public int getScore(){
		return score;
	}

	public int getCoordX() {
		return coordX;
	}

	public void setCoordX(int coordX) {
		this.coordX = coordX;
	}

	public int getCoordY() {
		return coordY;
	}

	public void setCoordY(int coordY) {
		this.coordY = coordY;
	}

	public String getMoveList() {
		return moveList;
	}

	public void setMoveList(String moveList) {
		this.moveList = moveList;
	}

	public void setNextPlayer(int nextPlayer) {
		this.nextPlayer = nextPlayer;
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}

	public int[][] getGameModel() {
		return gameModel;
	}

	public void setGameModel(int[][] gameModel) {
		this.gameModel = gameModel;
	}

	public boolean isOver() {
		String line_x = "";
		String line_y = "";
		String line_ld = (new Integer(gameModel[coordX][coordY])).toString();
		String line_rd = (new Integer(gameModel[coordX][coordY])).toString(); 
		
		String s = (new Integer(3 - nextPlayer)).toString();
		String sub = s + s + s + s;
		String match = "[012]*" + sub + "[" + s + "]*[012]*";
		for (int i = 0; i < 7; i++) {
			int cell = gameModel[coordX][i];
			line_x += (new Integer(cell)).toString();
		}
		for (int i = 0; i < 6; i++) {
			int cell = gameModel[i][coordY];
			line_y += (new Integer(cell)).toString();
		}

		int i = 0;
		int tempx = coordX;
		int tempy = coordY;
		while ((tempx > 0) && (tempy > 0)) {
			tempx--;
			tempy--;
			line_ld = (new Integer(gameModel[tempx][tempy])).toString() + line_ld;
			i++;
		}

		tempx = coordX;
		tempy = coordY;
		while ((tempx < 5) && (tempy < 6)) {
			tempx++;
			tempy++;
			line_ld = line_ld + (new Integer(gameModel[tempx][tempy])).toString();
			i++;
		}

		i = 0;
		tempx = coordX;
		tempy = coordY;
		while ((tempx > 0) && (tempy < 6)) {
			tempx--;
			tempy++;
			line_rd = (new Integer(gameModel[tempx][tempy])).toString() + line_rd;
			i++;
		}

		tempx = coordX;
		tempy = coordY;
		while ((tempx < 5) && (tempy > 0)) {
			tempx++;
			tempy--;
			line_rd = line_rd + (new Integer(gameModel[tempx][tempy])).toString();
			i++;
		}

		if ((line_x.matches(match)) || (line_y.matches(match))
				|| (line_ld.matches(match)) || (line_rd.matches(match))) {
			winner = 3 - nextPlayer;
			
			if(line_x.matches(match)){
				int index = line_x.indexOf(s+s+s+s);
				int j = 0;
				while((j + index) < line_x.length() && line_x.charAt(index + j) == s.charAt(0)){
					j++;
				}
			}
			
			if(line_y.matches(match)){
				int index = line_y.indexOf(s+s+s+s);
				int j = 0;
				while((j + index) < line_y.length() && line_y.charAt(index + j) == s.charAt(0)){
					j++;
				}
			}
			
			if(line_ld.matches(match)){
				int index = line_ld.indexOf(s+s+s+s);
				int j = 0;
				while((j + index) < line_ld.length() && line_ld.charAt(index + j) == s.charAt(0)){
					j++;
				}
			}
			
			if(line_rd.matches(match)){
				int index = line_rd.indexOf(s+s+s+s);
				int j = 0;
				while((j + index) < line_rd.length() && line_rd.charAt(index + j) == s.charAt(0)){
					j++;
				}
			}
			return true;
		}

		int z = 0;
		for (int g = 0; g < 6; g++)
			for (int j = 0; j < 7; j++)
				if (gameModel[g][j] == 0)
					z = 1;

		if (z == 0) {
			return true;
		}

		return false;
	}
	
	public void addToWinnersArray(int x, int y){
		if(x != -1 && y != -1 && k < gameWinners.length && !isContainsInArray(x, y)){
			gameWinners[k][1] = x;
			gameWinners[k][0] = y;
			k++;
		}
	}
	
	private boolean isContainsInArray(int x, int y){
		for(int k = 0; k < gameWinners.length; k++){
			if(gameWinners[k][0] == x && gameWinners[k][1] == y)
				return true;
		}
		return false;
	} 
	
	public int[][] calculateWinner(){
		String line_x = "";
		String line_y = "";
		String line_ld = "";
		String line_rd = "";
		
		int[][] gameWinners_x = new int[COLUMNS][2];
		int[][] gameWinners_y = new int[COLUMNS][2];
		int[][] gameWinners_ld = new int[COLUMNS * 2][2];
		int[][] gameWinners_rd = new int[COLUMNS * 2][2];
		
		k = 0;
		for (int i = 0; i < gameWinners.length; i++){
			gameWinners[i][0] = -1;
			gameWinners[i][1] = -1;
		}
		for (int i = 0; i < COLUMNS; i++){
			gameWinners_x[i][0] = -1;
			gameWinners_x[i][1] = -1;
			gameWinners_y[i][0] = -1;
			gameWinners_y[i][1] = -1;
		}
		for (int i = 0; i < COLUMNS * 2; i++){
			gameWinners_ld[i][0] = -1;
			gameWinners_ld[i][1] = -1;
			gameWinners_rd[i][0] = -1;
			gameWinners_rd[i][1] = -1;
		} 
		
		String s = (new Integer(3 - nextPlayer)).toString();
		String sub = s + s + s + s;
		String match = "[012]*" + sub + "[" + s + "]*[012]*";
		for (int i = 0; i < 7; i++) {
			int cell = gameModel[coordX][i];
			line_x += (new Integer(cell)).toString();
			gameWinners_x[i][1] = coordX;
			gameWinners_x[i][0] = i;
		}
		for (int i = 0; i < 6; i++) {
			int cell = gameModel[i][coordY];
			line_y += (new Integer(cell)).toString();
			gameWinners_y[i][1] = i;
			gameWinners_y[i][0] = coordY;
		}

		int i = 0;
		int tempx = coordX;
		int tempy = coordY;
		while ((tempx > 0) && (tempy > 0)) {
			tempx--;
			tempy--;
			i++;
		}

		while ((tempx < 5) && (tempy < 6)) {
			tempx++;
			tempy++;
			line_ld = line_ld + (new Integer(gameModel[tempx][tempy])).toString();
			gameWinners_ld[line_ld.length() - 1][1] = tempx;
			gameWinners_ld[line_ld.length() - 1][0] = tempy;
			i++;
		}

		
		i = 0;
		tempx = coordX;
		tempy = coordY;
		
		while ((tempx > 0) && (tempy < 7)) {
			tempx--;
			tempy++;
			i++;
		}
		
		while ((tempx < 5) && (tempy > 0)) {
			tempx++;
			tempy--;
			line_rd = line_rd + (new Integer(gameModel[tempx][tempy])).toString();
			gameWinners_rd[line_rd.length() - 1][1] = tempx;
			gameWinners_rd[line_rd.length() - 1][0] = tempy;
			i++;
		}

		if ((line_x.matches(match)) || (line_y.matches(match))
				|| (line_ld.matches(match)) || (line_rd.matches(match))) {
			winner = 3 - nextPlayer;
			
			if(line_x.matches(match)){
				int index = line_x.indexOf(s+s+s+s);
				int j = 0;
				while((j + index) < line_x.length() && line_x.charAt(index + j) == s.charAt(0)){
					gameWinners[j][0] = gameWinners_x[j + index][0];
					gameWinners[j][1] = gameWinners_x[j + index][1];
					j++;
				}
			}
			
			if(line_y.matches(match)){
				int index = line_y.indexOf(s+s+s+s);
				int j = 0;
				while((j + index) < line_y.length() && line_y.charAt(index + j) == s.charAt(0)){
					gameWinners[j][0] = gameWinners_y[j + index][0];
					gameWinners[j][1] = gameWinners_y[j + index][1];
					j++;
				}
			}
			
			if(line_ld.matches(match)){
				int index = line_ld.indexOf(s+s+s+s);
				int j = 0;
				while((j + index) < line_ld.length() && line_ld.charAt(index + j) == s.charAt(0)){
					gameWinners[j][0] = gameWinners_ld[j + index][0];
					gameWinners[j][1] = gameWinners_ld[j + index][1];
					j++;
				}
			}
			
			if(line_rd.matches(match)){
				int index = line_rd.indexOf(s+s+s+s);
				int j = 0;
				while((j + index) < line_rd.length() && line_rd.charAt(index + j) == s.charAt(0)){
					gameWinners[j][0] = gameWinners_rd[j + index][0];
					gameWinners[j][1] = gameWinners_rd[j + index][1];
					j++;
				}
			}
		}
		return gameWinners;
	}
}