package com.chameapps.fouronthefarm.activity;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.chameapps.fouronthefarm.R;
import com.chameapps.fouronthefarm.adapter.ScoreListAdapter;
import com.chameapps.fouronthefarm.model.GameValues;
import com.chameapps.fouronthefarm.model.Score;
import com.chameapps.fouronthefarm.utils.FileUtils;
import com.chameapps.fouronthefarm.utils.GameUtils;

public class ScoreActivity extends BaseActivity {
	private CustomDialog dialog;
	private EditText score_text;
	private JSONArray scores;
	private Typeface font;
	private ListView list;
	private ScoreListAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score_activity);
		displayHeight = getWindowManager().getDefaultDisplay().getHeight();
		
		setSizeForScorePanel();

		dialog = new CustomDialog(this);
		showCustomDialog();
	}
	
	private void setSizeForScorePanel(){
		// Getting adapter by passing xml data ArrayList
		font = Typeface.createFromAsset(getAssets(), "fonts/ObelixPro-cyr.ttf");
		scores = FileUtils.readJSONFromFile();
		adapter = new ScoreListAdapter(getApplicationContext(), this, scores, font);
		
		// List view of scores
		list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
	}
	
	private void showCustomDialog(){	
		
		dialog.show();
		
		score_text.setText(" " + GameActivity.score);
		score_text.setEnabled(false);
        
        final EditText nameEditText = (EditText) dialog.findViewById(R.id.name_text);
        
        Button ok_button = (Button) dialog.findViewById(R.id.ok_button);
		ok_button.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				dialog.hide();
				String name = nameEditText.getText().toString();
				if(name.replaceAll("\\s","").length() > 0)
					try{
		        		JSONArray scores = GameValues.getInstance().getGameScores();
		        		if(!FileUtils.isAlreadyContainsScore(name, GameActivity.score)){
			        		Score gameScore = new Score(scores.length(), name, GameActivity.score);
			        		GameValues.getInstance().addGameScore(gameScore);
			        		FileUtils.saveJSONToFile(GameValues.getInstance().getGameScores());
			        		
			        		adapter.setScores(scores);
							adapter.notifyDataSetChanged();
		        		}
		        		else finish();
		        	}catch(JSONException e){
		        		e.printStackTrace();
		        	}
				else
					finish();
			}
		});
		Button cancel_button = (Button) dialog.findViewById(R.id.cancel_button);
		cancel_button.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				dialog.hide();
			}
		});
	}
	
	class CustomDialog extends Dialog{
		public CustomDialog(Context context){
			super(context);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.high_score_dialog);
			getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			
			Typeface font = Typeface.createFromAsset(getAssets(), "fonts/ObelixPro-cyr.ttf");
			
			EditText name_text = (EditText)findViewById(R.id.name_text);
			name_text.setTypeface(font);
			
			name_text.setLineSpacing(5, 1);
			
			score_text = (EditText)findViewById(R.id.score_text);
			score_text.setTypeface(font);
			if(GameUtils.isTablet(getContext())){
				name_text.setTextSize(24.f);
				score_text.setTextSize(24.f);
			}
			else{
				score_text.setTextSize(20.f);
				name_text.setTextSize(20.f);
			}
			score_text.setLineSpacing(5, 1);
		}
	}
}
