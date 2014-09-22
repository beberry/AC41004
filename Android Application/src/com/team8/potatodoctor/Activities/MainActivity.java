package com.team8.potatodoctor.activities;

import java.util.concurrent.ExecutionException;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.team8.potatodoctor.R;
import com.team8.potatodoctor.DatabaseObjects.PestEntity;
import com.team8.potatodoctor.DatabaseObjects.PhotoEntity;
import com.team8.potatodoctor.Models.AppUpdater;
import com.team8.potatodoctor.Models.MediaFetcher;
import com.team8.potatodoctor.Models.Repositories.PestRepository;

public class MainActivity extends Activity 
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		Log.d("Problem Determination", "onCreate() ENTRY");
		//Log.w("hello","reached"); 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		new Thread(new Runnable() {
	        public void run() {
	        	updateDB();
	        }
	    }).start();
		AppUpdater appUpdater = new AppUpdater(getApplicationContext());
		try {
			appUpdater.updateLocalFiles();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Intent intentCategoriesList = new Intent(getApplicationContext(),CategoriesListActivity.class);
		startActivity(intentCategoriesList);
		Log.d("Problem Determination", "onCreate() EXIT");
	}
	
	private void findFiles()
	{
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void updateDB()
	{
		new MediaFetcher().execute("http://www.beberry.lv/potato/images/u/9b3daf26cffd95878fc3e6bbf5b22b27.jpg","Pests");
		AppUpdater appUpdater = new AppUpdater(getApplicationContext());
		try {
			appUpdater.updateDatabaseTables();
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "Failed to update the database", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		} 
		
		PestRepository pestRepository = new PestRepository(getApplicationContext());
		for(PestEntity pest : pestRepository.getAllPests())
		{
			Log.w("hello",pest.getName()+" 1");
			for(PhotoEntity photo : pest.getPhotos())
			{
				Log.w("hello", "Photos: "+photo.getName());
			}
		}
	}
}