package com.chameapps.fouronthefarm.utils;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.TextView;

public class FontUtils {

	public static void increaseFontSize(TextView textView, Typeface font, String text, int textWidth) {
		
		Log.e("Display width: ", "" + textWidth);
		
		float textSize = 10.f;
		textView.setTypeface(font);
		textView.setTextSize(textSize);
		Paint paint = textView.getPaint();
		Log.e("10.f: ", "" + paint.measureText(text));
		
		textSize = 12.f;
		textView.setTypeface(font);
		textView.setTextSize(textSize);
		paint = textView.getPaint();
		Log.e("12.f: ", "" + paint.measureText(text));
		
		textSize = 14.f;
		textView.setTypeface(font);
		textView.setTextSize(textSize);
		paint = textView.getPaint();
		Log.e("14.f: ", "" + paint.measureText(text));
		
		while(paint.measureText(text) < textWidth){
			textSize = textSize + 2;
			textView.setTextSize(textSize);
			paint = textView.getPaint();
			Log.e("" + textSize + ": ", "" + paint.measureText(text));
		}
	}
}
