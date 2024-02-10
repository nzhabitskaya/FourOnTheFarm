package com.chameapps.fouronthefarm.views.custom;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

public class VegetableView extends ImageView{
	public VegetableView(Context context, int currentBall) {		
		super(context);
		invalidate();
		setImageResource(currentBall);
	}
}