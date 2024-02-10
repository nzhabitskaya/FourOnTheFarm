package com.chameapps.fouronthefarm.activity;

import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chameapps.fouronthefarm.R;
import com.chameapps.fouronthefarm.activity.callback.ChangePlayerAndPlayAICallback;
import com.chameapps.fouronthefarm.activity.callback.ChangePlayerCallback;
import com.chameapps.fouronthefarm.activity.callback.CheckIsGameOverCallback;
import com.chameapps.fouronthefarm.activity.callback.CreateNewGameCallback;
import com.chameapps.fouronthefarm.activity.callback.PlaySoundCallback;
import com.chameapps.fouronthefarm.model.GameBoard;
import com.chameapps.fouronthefarm.players.EasyPlayer;
import com.chameapps.fouronthefarm.players.HardPlayer;
import com.chameapps.fouronthefarm.players.HumanPlayer;
import com.chameapps.fouronthefarm.players.MediumPlayer;
import com.chameapps.fouronthefarm.players.Player;
import com.chameapps.fouronthefarm.utils.GameUtils;
import com.chameapps.fouronthefarm.utils.Prefutils;
import com.chameapps.fouronthefarm.views.custom.VegetableCell;
import com.chameapps.fouronthefarm.views.custom.VegetableLayout;

public class GameActivity extends GameState implements CreateNewGameCallback, PlaySoundCallback,
ChangePlayerAndPlayAICallback, ChangePlayerCallback, CheckIsGameOverCallback{
	public static final String TAG = "GameActivity";
	
	//Game panel sizes
	public static int gamePanelHeight;
	
	// Ball views list
	private VegetableLayout[][] balls;

	// Sound variables
	private SoundPool sounds;
	private int sAction;
	private int sDisabled;
	private int sWin;
	
	//Game score
	public static int score;

	// Ball view
	private int currentBallImageId;

	// Next player name
	private int nextPlayerName;
	private TextView playerNameView;
	private ImageView nextPlayerImg;
	private int nextPlayerImgWidth;
	public static int nextPlayerImgHeight;

	// Game layouts
	private FrameLayout heapPanel;
	private FrameLayout animPanel;
	private RelativeLayout mainPanel;

	// Players
	private static Player p1;
	private static Player p2;

	private static int p1TypeFlag = 0;
	private static int p2TypeFlag = 0;

	// This variable shows if AI plays with human
	private boolean isAI = false;

	// New game after animation ends
	public static boolean isNewGame = false;

	// Game model
	private boolean isDialogShow = false;

	// Animation
	public static boolean isAnimation = false;

	// Sound
	public static boolean isPlaySound = false;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");
		setContentView(R.layout.game_panel);
		board = new GameBoard();
		
		// create select game level dialog
		createSelectGameLevelDialog();

		initViews();
		initSounds();
		initDialogs();
		initPlayers();
		
		loadPrevGame();
		
		setCurrentPlayerFont();
	}
	
	private void setCurrentPlayerFont(){
		Typeface font = Typeface.createFromAsset(getAssets(), "fonts/ObelixPro-cyr.ttf");
		playerNameView.setTypeface(font);
		playerNameView.setTextSize(15.f);
		playerNameView.setLineSpacing(5, 1);
		
		if(GameUtils.isTablet(getApplicationContext())){
			playerNameView.setTextSize(22.f);
		}
		else{
			playerNameView.setTextSize(16.f);
		}
	}

	public void onRestart() {
		super.onRestart();
		Log.d(TAG, "onRestart");
		initPlayers();
		initSound();
	}

	protected void initPlayers() {
		int game_level = Prefutils.getGameLevel(this);
		switch (game_level) {
		case Prefutils.TWO_PLAYERS:
			p1 = new HumanPlayer();
			p2 = new HumanPlayer();
			isAI = false;
			break;
		case Prefutils.ONE_PLAYER_EASY:
			p1 = new HumanPlayer();
			p2 = new EasyPlayer();
			isAI = true;
			break;
		case Prefutils.ONE_PLAYER_MEDIUM:
			p1 = new HumanPlayer();
			p2 = new MediumPlayer();
			isAI = true;
			break;
		case Prefutils.ONE_PLAYER_HARD:
			p1 = new HumanPlayer();
			p2 = new HardPlayer();
			isAI = true;
			break;
		}
	}

	private void initViews() {
		displayWidth = getWindowManager().getDefaultDisplay().getWidth();
		displayHeight = getWindowManager().getDefaultDisplay().getHeight();	
		
		// Resize game panel
		setSizeForGamePanel();

		// Display current player
		playerNameView = (TextView) findViewById(R.id.current_player);	
		nextPlayerImg = (ImageView) findViewById(R.id.player_image);	
		nextPlayerImg.setImageResource(R.drawable.tomato);
		
		BitmapDrawable bd = (BitmapDrawable) this.getResources().getDrawable(R.drawable.tomato);
		nextPlayerImgHeight = bd.getBitmap().getHeight();
		nextPlayerImgWidth = bd.getBitmap().getWidth();
		
		// Add game area touch listener
		mainPanel.setOnTouchListener(touchListener);
		
		// Sound on/off
		initSound();

		// Draw foreground
		drawHeaps();
		
		// Init array of ball views
		balls = new VegetableLayout[board.ROWS][board.COLUMNS];
	}
	
	private void setSizeForGamePanel(){
		LinearLayout top_panel = (LinearLayout) findViewById(R.id.top_panel2);
		RelativeLayout bottom_panel = (RelativeLayout) findViewById(R.id.bottom_panel);
		RelativeLayout main_panel = (RelativeLayout) findViewById(R.id.main_panel);
		heapPanel = (FrameLayout) findViewById(R.id.heap_panel2);
		animPanel = (FrameLayout) findViewById(R.id.anim_panel2);
		mainPanel = (RelativeLayout) findViewById(R.id.main_panel);
		
		gamePanelHeight = displayHeight - top_panel.getBackground().getMinimumHeight() - bottom_panel.getBackground().getMinimumHeight();
		
		// Cell size
		cellWidth = displayWidth / board.COLUMNS;
		cellHeight = gamePanelHeight / (board.ROWS + 1);
		
		android.view.ViewGroup.LayoutParams paramsTop = top_panel.getLayoutParams();
		android.view.ViewGroup.LayoutParams params5 = bottom_panel.getLayoutParams();
		
		// Game panels
		android.view.ViewGroup.LayoutParams params1 = heapPanel.getLayoutParams();
		params1.height =  displayHeight - paramsTop.height;
		params1.width =  displayWidth;
		heapPanel.setPadding(0, 25/*nextPlayerImgHeight *2/3*/, 0, 0);
		heapPanel.setLayoutParams(params1);
		
		android.view.ViewGroup.LayoutParams params2 = animPanel.getLayoutParams();
		params2.height =  displayHeight - paramsTop.height;
		params2.width =  displayWidth;
		animPanel.setPadding(0, 0, 0, 0);
		animPanel.setLayoutParams(params2);
		
		android.view.ViewGroup.LayoutParams params3 = main_panel.getLayoutParams();
		params3.height =  gamePanelHeight;
		params3.width =  displayWidth;
		main_panel.setLayoutParams(params3);
	}

	private void drawHeaps() {
		for  (int x = 0; x < board.COLUMNS; x++)
			for (int y = 0; y < board.ROWS; y++) 
				drawHeap(heapPanel, R.drawable.heap, y + 1, x);
	}

	private void initSounds() {
		sounds = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		sAction = sounds.load(this, R.raw.action, 1);
		sDisabled = sounds.load(this, R.raw.disabled, 1);
		sWin = sounds.load(this, R.raw.win, 1);
	}

	private void clearGamePanel() {
		isGameOver = false;
		isDialogShow = false;
	}

	private void addBall(int currentBall) {
		if (isGameOver) {
			gameOver();
		} else {
			// playerView.setImageResource(currentBall);
			setNextPlayerName();
		}
	}
	
	private void playVSComputer(int currentBall) {
		if (isGameOver) {
			gameOver();
		} else {
			// playerView.setImageResource(currentBall);
			setNextPlayerName();

			// If Human plays vs Computer, computer moves
			if (isAI && currentBall == R.drawable.pumpkin) {
				p2.go(board);
				redrawAIBoard();
			}
			if ((isAI && currentBall == R.drawable.tomato) || !isAI) {
				isAnimation = false;
			}
		}
	}
	
	// Animate created ball
	private void paintBall(int row, int col){
		VegetableLayout ballView = new VegetableLayout(this, cellWidth, cellHeight, currentBallImageId);
		balls[row][col] = ballView;
		animPanel.addView(ballView);
		
		ballView.setMargin((cellWidth - nextPlayerImgWidth) / 2, cellHeight - nextPlayerImgHeight, (cellWidth - nextPlayerImgWidth) / 2, 0);		
		ballView.setStartCoordinates(0, col); // creates layout with ball at the width = col, height = row
		ballView.applyCreateBallAnimation(row + 1, col); // plays animation of the view
		
		ballView.setCreateNewGameCallback(this);
		ballView.setPlaySoundCallback(this);
		ballView.setChangePlayerAndPlayAICallback(this);
		ballView.setCheckIsGameOverCallback(this);
	}
	
	private void printGameModel(){
		int [][] model = board.getGameModel();
		String line = "";
		for (int y = 0; y < board.ROWS; y++) { 
			for (int x = 0; x < board.COLUMNS; x++){
				line += model[y][x];
				if(model[y][x] == board.getWinner())
					Log.e("model[" + y + "][" + x + "]=", "" + model[y][x]);
			}
			Log.w("line " + y + ": ", line);
			line = "";
		}
		Log.w("", "----------------------------------");
	}
	
	// Animate created ball
		private void paintAIBall(int row, int col){
			VegetableLayout ballView = new VegetableLayout(this, cellWidth, cellHeight, currentBallImageId);
			balls[row][col] = ballView;
			/*if(cellWidth > nextPlayerImgWidth || cellHeight > nextPlayerImgHeight)
				ball.setMargin((cellWidth - nextPlayerImgWidth) / 2, cellHeight - nextPlayerImgHeight, (cellWidth - nextPlayerImgWidth) / 2, 0);*/
			ballView.setMargin((cellWidth - nextPlayerImgWidth) / 2, cellHeight - nextPlayerImgHeight, (cellWidth - nextPlayerImgWidth) / 2, 0);
			animPanel.addView(ballView);
			
			ballView.setStartCoordinates(0, col); // creates layout with ball at the width = col, height = row
			ballView.applyCreateBallAnimation(row + 1, col); // plays animation of the view
			
			ballView.setCreateNewGameCallback(this);
			ballView.setPlaySoundCallback(this);
			ballView.setChangePlayerCallback(this);
			ballView.setCheckIsGameOverCallback(this);
		}
	
	// Restore game state, draw existing balls
	private void drawView(FrameLayout layout, int currentBallView, int row, int col){
		VegetableLayout ballView = new VegetableLayout(this, cellWidth, cellHeight, currentBallView);
		ballView.setMargin((cellWidth - nextPlayerImgWidth) / 2, cellHeight - nextPlayerImgHeight, (cellWidth - nextPlayerImgWidth) / 2, 0);
		balls[row][col] = ballView;
		layout.addView(ballView);
		ballView.setCoordinates(row + 1, col); // creates layout with ball at the width = col, height = row
	}
	
	// Restore game state, draw existing balls
		private void drawHeap(FrameLayout layout, int currentBallView, int row, int col){
			VegetableLayout heapView = new VegetableLayout(this, cellWidth, cellHeight, currentBallView);
			layout.addView(heapView);
			
			heapView.setMargin((cellWidth - nextPlayerImgWidth) / 2, cellHeight - nextPlayerImgHeight, (cellWidth - nextPlayerImgWidth) / 2, 0);
			heapView.setCoordinates(row, col);
		}
	
	private void setNextPlayerName() {		
		int player = board.getNextPlayer();
		if (isAI) {
			if (player == GameBoard.FIRST_PLAYER)
				nextPlayerName = R.string.your_turn;
			else
				nextPlayerName = R.string.computers_turn;
		} else {
			if (player == GameBoard.FIRST_PLAYER)
				nextPlayerName = R.string.tomatoes;
			else
				nextPlayerName = R.string.pumpkins;
		}
		playerNameView.setText(nextPlayerName);
	}

	private void initSound() {
		isPlaySound = Prefutils.getSound(GameActivity.this);
		// soundCb.setChecked(isPlaySound);
	}

	OnTouchListener touchListener = new View.OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent arg) {
			int actionType = arg.getAction();
			switch (actionType) {
			case MotionEvent.ACTION_DOWN:
				// Calculate game area
				int pos = (int) (arg.getX() / cellWidth);
				if (pos < board.COLUMNS && !isAnimation && !isGameOver) {
					if (board.getNextPlayer() == 1)
						p1.setMove(pos);
					else
						p2.setMove(pos);					
					play();
				}
			}
			return true;
		}
	};

	protected void changePlayer() {
		if (!isGameOver) {
			if (board.getNextPlayer() == 1) {
				currentBallImageId = R.drawable.tomato;
			} else if (board.getNextPlayer() == 2) {
				currentBallImageId = R.drawable.pumpkin;
			}
			setNextPlayerName();
			nextPlayerImg.setImageResource(currentBallImageId);
		}
	}

	// ------------------------------------- Game
	// algorithm--------------------------------------//

	protected void createNewGame() {
		clearGamePanel();
		balls = new VegetableLayout[board.ROWS][board.COLUMNS];
		board = new GameBoard();
		board.setOut(false);
		changePlayer();
		animPanel.removeAllViews();
		addBall(currentBallImageId);
		isNewGame = false;
		playerNameView.setVisibility(View.VISIBLE);
		nextPlayerImg.setVisibility(View.VISIBLE);

		if (p1TypeFlag == 1) {
			p1.go(board);
			redrawBoard();
		}

		if ((p1TypeFlag == 1) && (p2TypeFlag == 1)) {
			while (!board.isOver()) {
				if (board.getNextPlayer() == 1)
					p1.go(board);
				else
					p2.go(board);
				redrawBoard();
			}
		}
	}
	
	protected boolean loadPrevGame() {
		boolean isNewGame = true;
		int[][] gameModel = restoreState();
		for(int r = 0; r < GameBoard.ROWS; r++){
        	for(int c = 0; c < GameBoard.COLUMNS; c++){
        		if(gameModel[r][c] == 1){
        			drawView(animPanel, R.drawable.tomato, r, c);
        			board.moveList += (new Integer(c)).toString();
        			board.gameCells[c]++;
        			isNewGame = false;
        		}
        		else if(gameModel[r][c] == 2){
        			drawView(animPanel, R.drawable.pumpkin, r, c);
        			board.moveList += (new Integer(c)).toString();
        			board.gameCells[c]++;
        			isNewGame = false;
        		}
    		}
		}
		if(isNewGame)
			showSelectGameLevelDialog();
		
		board.gameModel = gameModel;
		board.setNextPlayer(restoreNextPlayer());
		
		board.setOut(false);
		changePlayer();
		addBall(currentBallImageId);
		isNewGame = false;

		if (p1TypeFlag == 1) {
			p1.go(board);
			redrawBoard();
		}

		if ((p1TypeFlag == 1) && (p2TypeFlag == 1)) {
			while (!board.isOver()) {
				if (board.getNextPlayer() == 1)
					p1.go(board);
				else
					p2.go(board);
				redrawBoard();
			}
		}
		
		return isNewGame;
	}

	private void redrawBoard() {
		int r = board.getCoordX();
		int c = board.getCoordY();
		paintBall(r, c);

		if (board.isOver() == true) {
			isGameOver = true;
		}
	}
	
	private void redrawAIBoard() {
		int r = board.getCoordX();
		int c = board.getCoordY();
		paintAIBall(r, c);

		if (board.isOver() == true) {
			isGameOver = true;
		}
	}

	// ------------------------------------------------------- Play Game
	// -----------------------------------

	public void play() {
		if (board.getNextPlayer() == 1) {
			if (p1.go(board))
				redrawBoard();
			else {
				if (isPlaySound)
					sounds.play(sDisabled, 1.0f, 1.0f, 0, 0, 1.5f);
			}
		} else {
			if (p2.go(board))
				redrawBoard();
			else {
				if (isPlaySound)
					sounds.play(sDisabled, 1.0f, 1.0f, 0, 0, 1.5f);
			}
		}
	}

	private void gameOver() {
		if (isGameOver) {
			playerNameView.setVisibility(View.INVISIBLE);
			nextPlayerImg.setVisibility(View.INVISIBLE);
			score = calculateScore();
			
			if (board.getWinner() == 1) {
				if (isDialogShow == false){
					if(!isAI)
						showDialog(DIALOG_USER_MESSAGE, MenuActivity.PLAYER1, (isAI)?-1:score);
					else
						showDialog(DIALOG_USER_MESSAGE, MenuActivity.YOU, (isAI)?-1:score);
				}
			} else if (board.getWinner() == 2) {
				if (isDialogShow == false){
					if(!isAI)
						showDialog(DIALOG_USER_MESSAGE, MenuActivity.PLAYER2, (isAI)?-1:score);
					else
						showDialog(DIALOG_USER_MESSAGE, MenuActivity.COMPUTER, (isAI)?-1:score);
				}
			} else if (board.getWinner() == 0){
				if (isDialogShow == false){
					showDialog(DIALOG_USER_MESSAGE, MenuActivity.NOBODY, -1);
				}
			}
			if (isPlaySound)
				sounds.play(sWin, 1.0f, 1.0f, 0, 0, 1.5f);
			isDialogShow = true;
			
			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
			    public void run() {
			    	hightLightWinners();
			    }
			}, 500);  // 500 milliseconds
		}
	}
	
	private void hightLightWinners(){
		// Highlight winners animation
		int[][] gameWinners = board.calculateWinner();
		for(int i = 0; i < gameWinners.length; i++){
			if(gameWinners[i][0] != -1){
				int row = gameWinners[i][1];
				int col = gameWinners[i][0];
				VegetableCell ball = (VegetableCell)balls[row][col].getChildAt(0);
				ball.applyShineAnimation();
			}
		}
	}
	
	private int calculateScore() {
		score = 0;
		int emptyCellsCount = 0;
		int level0 = 155;
		int level1 = 75;
		int level2 = 175;
		int level3 = 205;
		if (isGameOver) {
			int[][] model = board.getGameModel();
			for(int i = 0; i < board.ROWS; i++)
				for(int j = 0; j < board.COLUMNS; j++){
					int cell = model[i][j];
					if(cell == 0)
						emptyCellsCount ++;
				}
			
			int game_level = Prefutils.getGameLevel(this);
			switch (game_level) {
			case Prefutils.TWO_PLAYERS:
				score =  emptyCellsCount * level0;
				break;
			case Prefutils.ONE_PLAYER_EASY:
				score =  emptyCellsCount * level1;
				break;
			case Prefutils.ONE_PLAYER_MEDIUM:
				score =  emptyCellsCount * level2;
				break;
			case Prefutils.ONE_PLAYER_HARD:
				score =  emptyCellsCount * level3;
				break;
			}			
		}	
		return score;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	        showDialog(DIALOG_EXIT, MenuActivity.NOBODY, -1);
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}

	@Override
	public void isChangePlayerAndPlayAI() {
		if(!isGameOver){
			changePlayer();
			if(isAI){
				playVSComputer(currentBallImageId);
			}
		}
	}
	
	@Override
	public void isChangePlayer() {
		if(!isGameOver){
			changePlayer();
		}
	}

	@Override
	public void isPlaySound() {
		if(isGameOver){
			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
			    public void run() {
			    	sounds.play(sAction, 1.0f, 1.0f, 0, 0, 1.5f);
			    }
			}, 2000);
		}else{
			sounds.play(sAction, 1.0f, 1.0f, 0, 0, 1.5f);
		}
	}

	@Override
	public void isCreateNewGame() {
		createNewGame();
	}
	
	private void checkIsGameOver() {
		calculateScore();
		if (isGameOver) {
			gameOver();
		} else {
			setNextPlayerName();
		}
	}

	@Override
	public void isGameOverCheck() {
		checkIsGameOver();
	}
}
