package com.chameapps.fouronthefarm.players;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.util.Log;

import com.chameapps.fouronthefarm.model.GameBoard;

public class HumanPlayer implements Player {
	private int m = -1;

	public void setMove(int col) {
		m = col;
	}

	/** Creates a new instance of HumanPlayer */
	public HumanPlayer() {
		Log.w(TAG, "Human player initialized");
	}

	public int getType() {
		return 0;
	}

	@Override
	public boolean go(GameBoard b) {
		int n = 0;
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String s = null;
		if (m == -2) {
			try {
				s = br.readLine();
			} catch (IOException ioe) {
				// won't happen too often from the keyboard
			}

			n = Integer.parseInt(s);
		}

		if ((m != -1) && (m != -2)) {
			// System.out.println(m);
			return b.move(m);
		} else {
			return b.move(n);
			// System.out.println(n);
		}
	}
}
