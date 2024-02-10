package com.chameapps.fouronthefarm.utils;

import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class Glyphs {

	private static final String TAG = Glyphs.class.getSimpleName();
	private Bitmap bitmap1;
	private Bitmap bitmap2;
	private Bitmap bitmap3;

	private Map<Character, Bitmap> glyphs = new HashMap<Character, Bitmap>(62);
	
	// the characters in the English alphabet
	private char[] charactersU = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G',
			'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
			'U', 'V', 'W', 'X', 'Y', 'Z' };
	private char[] charactersL = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
			'u', 'v', 'w', 'x', 'y', 'z' };
	private char[] numbers = new char[] { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9' };
	private char[] symbols = new char[] { '!', '@', '#', '$', '%', '&', '*',
			'(',')', '-', '_', '+', '=', '?', '/', '.', ',', '\\', '\'',
			'"', ':', ';', '[', ']', '�', '^', '|' };
	
	public Glyphs(Bitmap bitmap1, Bitmap bitmap2, Bitmap bitmap3) {
		super();
		this.bitmap1 = bitmap1;
		this.bitmap2 = bitmap2;
		this.bitmap3 = bitmap3;
		
		for (int i = 0; i < 26; i++) {
			glyphs.put(charactersU[i], Bitmap.createBitmap(bitmap1,
					0 + (i * 21), 0, 21, 26));
		}
		for (int i = 0; i < 26; i++) {
			glyphs.put(charactersL[i], Bitmap.createBitmap(bitmap1,
					0 + (i * 21), 0, 21, 26));
		}
		for (int i = 0; i < 10; i++) {
			glyphs.put(numbers[i], Bitmap.createBitmap(bitmap2,
					0 + (i * 21), 0, 21, 26));
		}
		for (int i = 0; i < 27; i++) {
			glyphs.put(symbols[i], Bitmap.createBitmap(bitmap3,
					0 + (i * 20), 0, 20, 26));
		}
	}
	
	/**
	 * Draws the string onto the canvas at <code>x</code> and <code>y</code>
	 * @param text
	 */
	public void drawString(Canvas canvas, String text, int x, int y) {
		if (canvas == null) {
			Log.d(TAG, "Canvas is null");
		}
		for (int i = 0; i < text.length(); i++) {
			Character ch = text.charAt(i);
			if (glyphs.get(ch) != null) {
				canvas.drawBitmap(glyphs.get(ch), x + (i * 24), y, null);
			}
		}
	}
}
