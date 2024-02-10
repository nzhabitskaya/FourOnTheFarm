package com.chameapps.fouronthefarm.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chameapps.fouronthefarm.R;
import com.chameapps.fouronthefarm.utils.GameUtils;

public class HelpActivity extends BaseActivity{

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help_activity);
		
		TextView help_title = (TextView)findViewById(R.id.help_title);
        TextView help_text = (TextView)findViewById(R.id.help_text);
        
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/ObelixPro-cyr.ttf");

        help_title.setTypeface(font);
        if(GameUtils.isTablet(getApplicationContext()))
        	 help_title.setTextSize(26.f);
		else
			 help_title.setTextSize(16.f);
        
        help_text.setTypeface(font);
        if(GameUtils.isTablet(getApplicationContext()))
       	 	help_text.setTextSize(24.f);
		else
			help_text.setTextSize(14.f);
        help_text.setLineSpacing(5, 1);
		
        RelativeLayout help_panel = (RelativeLayout)findViewById(R.id.help_panel);
        Display display = getWindowManager().getDefaultDisplay();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(display.getHeight() * 4/ 5, display.getHeight() * 9/ 10);
		help_panel.setLayoutParams(params);		
		
		initButtonOk();
	}
	
	private void initButtonOk() {
		Button ok = (Button) findViewById(R.id.button_ok);	

		ok.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {	
				finish();
			}
		});
	}
}

