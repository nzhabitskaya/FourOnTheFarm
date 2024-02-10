package com.chameapps.fouronthefarm.activity;

import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chameapps.fouronthefarm.R;
import com.chameapps.fouronthefarm.utils.GameUtils;

public class AboutActivity extends BaseActivity{

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_activity);
		
		// Resize image
		ImageView logo_img = (ImageView)findViewById(R.id.image_logo);
		Display display = getWindowManager().getDefaultDisplay();
		
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
		// Resize font
		TextView about = (TextView)findViewById(R.id.about);
				
		if(GameUtils.isTablet(getApplicationContext())){
			float px = metrics.density * metrics.widthPixels / metrics.xdpi;
			about.setTextSize(px * 10);
       	}else{
       		about.setTextSize(18.f);
		}
        
        String version;
		try {
			version = this.getString(R.string.version, this.getPackageManager().getPackageInfo(this.getPackageName(), 0 ).versionName);
			about.setText(version);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		
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

