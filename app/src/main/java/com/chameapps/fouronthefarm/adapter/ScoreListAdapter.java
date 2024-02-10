package com.chameapps.fouronthefarm.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chameapps.fouronthefarm.R;
import com.chameapps.fouronthefarm.activity.BaseActivity;
import com.chameapps.fouronthefarm.activity.GameActivity;
import com.chameapps.fouronthefarm.model.Score;
import com.chameapps.fouronthefarm.utils.FileUtils;

public class ScoreListAdapter extends BaseAdapter {
	 
    private Activity activity;
    private JSONArray scores;
    private static LayoutInflater inflater = null;
    private Typeface font;
    private int displayHeight;
    private Context context;
 
    public ScoreListAdapter(Context context, Activity activity, JSONArray scores, Typeface font) {
        this.activity = activity;
        this.scores = FileUtils.sortArrayByScore(scores);
        this.font = font;
        this.inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.displayHeight = GameActivity.displayHeight;
        this.context = context;
    }
    
    public void setScores(JSONArray scores){
    	this.scores = FileUtils.sortArrayByScore(scores);
    }
 
    public int getCount() {
        return scores.length();
    }
 
    public Object getItem(int position) {
        return position;
    }
 
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.score_list_row, null);
 
        TextView score_name = (TextView)vi.findViewById(R.id.score_name);
        TextView score_value = (TextView)vi.findViewById(R.id.score_value);
        
        // Text view font style
        score_name.setTypeface(font);
        score_name.setLineSpacing(5, 1);
		
        score_value.setTypeface(font);
        score_value.setLineSpacing(5, 1);
        
        if(BaseActivity.getCurrentScreenSize(context) == Configuration.SCREENLAYOUT_SIZE_XLARGE){
        	score_name.setTextSize(22.f);
        	score_value.setTextSize(22.f);
		}
		else{
			score_name.setTextSize(16.f);
			score_value.setTextSize(16.f);
		}
        
        Display display = activity.getWindowManager().getDefaultDisplay();
        
        android.view.ViewGroup.LayoutParams params4 = score_name.getLayoutParams();
		params4.width =  display.getWidth() * 3/10;
		score_name.setLayoutParams(params4);
		
		android.view.ViewGroup.LayoutParams params5 = score_value.getLayoutParams();
		params5.width =  display.getWidth() * 3/10;
		score_value.setLayoutParams(params5);
 
        // Setting all values in listview
        try {
        	JSONObject score = (JSONObject)scores.get(position);
			score_name.setText(score.getString(Score.NAME));
			score_value.setText(score.getString(Score.SCORE));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return vi;
    }
}
