package com.team8.potatodoctor.activities;

import com.team8.potatodoctor.R;
import com.team8.potatodoctor.R.id;
import com.team8.potatodoctor.R.layout;
import com.team8.potatodoctor.adapters.PestImageAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class PestsActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category);
		
		setupGridView();
	}
 
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		/*
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		*/
		return true;
	}

	/*
	 * Constructs the Grid View and populates with Images via the PestImageAdapter.
	 */
	private void setupGridView()
	{
		//Locate Grid View from .xml Layout.
		GridView gridview = (GridView) findViewById(R.id.gridview);
		
		//Attach TuberImageAdapter and adds Images.
	    gridview.setAdapter(new PestImageAdapter(this));

	    //Setup Event Listener to direct user to information page.
	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	        	Intent intentObjectDescription = new Intent(getApplicationContext(),ObjectDescriptionActivity.class);
	        	
	        	//TODO Check name of db table and remove this comment.
	        	intentObjectDescription.putExtra("Type", "potato_Pest"); //DB Table name.
	        	intentObjectDescription.putExtra("Position", position); //DB Table row index.
	        	
	    		startActivity(intentObjectDescription);
	        }
	    });
	}
}