package com.chameapps.fouronthefarm.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Environment;
import android.util.Log;

public class FileUtils {
	public static final String TAG = "FileUtils";
	private static final String FOLDER = "/FourOnTheFarm";
	private static final String FILE_NAME = "FourOnTheFarm.txt";

	public static void saveJSONToFile(JSONArray scores) {
		try {
			String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
		    File myNewFolder = new File(extStorageDirectory + FOLDER);
		    myNewFolder.mkdir();
			
			File file = new File(Environment.getExternalStorageDirectory() + FOLDER + File.separator + FILE_NAME);	
			
			file.createNewFile();
			
			String data = scores.toString();
			// write the bytes in file
			if (file.exists()) {
				OutputStream fo = new FileOutputStream(file);
				fo.write(data.getBytes());
				fo.close();
				//Log.i(TAG, "file created: " + file);
				// url = upload.upload(file);
			}
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}
	}
	
	public static JSONArray readJSONFromFile() {
		JSONArray entries = new JSONArray();
		try
        {
            String x = "";
            String file_url = Environment.getExternalStorageDirectory() + FOLDER + File.separator + FILE_NAME;
            InputStream is = new FileInputStream(file_url);
            byte [] buffer = new byte[is.available()];
            while (is.read(buffer) != -1);
            String jsontext = new String(buffer);
            entries = new JSONArray(jsontext);
        }
        catch (Exception je)
        {
            je.getMessage();
        }
		
		return entries;
	}
	
	public static JSONArray sortArrayByScore(JSONArray unsortedScoreArray) {
		
		JSONObject[] scoresList = new JSONObject[unsortedScoreArray.length()];

		// fill an array with your events
		for (int i = 0; i < unsortedScoreArray.length(); i++) {
		    try {
				scoresList[i] = unsortedScoreArray.getJSONObject(i);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		// sort them
		Arrays.sort(scoresList, new Comparator<JSONObject>() {
		    public int compare(JSONObject score1, JSONObject score2) {
		    	Integer val1;
		    	Integer val2;
				try {
					val2 = Integer.parseInt(score1.getString("score"));
					val1 = Integer.parseInt(score2.getString("score"));					
					return val1.compareTo(val2);
					
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return 0;
		    }
		});
		
		return new JSONArray(Arrays.asList(scoresList));
	}
	
	public static boolean isAlreadyContainsScore(String name, int score) {
		
		JSONArray scoreArray = readJSONFromFile();
		for (int i = 0; i < scoreArray.length(); i++) {
		    try {
		    	JSONObject scoreObj = scoreArray.getJSONObject(i);
		    	if(scoreObj.getString("name").replaceAll("\\s","").equals(name.replaceAll("\\s","")) && Integer.parseInt(scoreObj.getString("score")) == score)
		    		return true;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}
}
