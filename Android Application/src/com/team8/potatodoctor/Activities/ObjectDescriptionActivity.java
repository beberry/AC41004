package com.team8.potatodoctor.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import com.team8.potatodoctor.R;
import com.team8.potatodoctor.DatabaseObjects.PestEntity;
import com.team8.potatodoctor.DatabaseObjects.PlantLeafSymptomsEntity;
import com.team8.potatodoctor.DatabaseObjects.TuberSymptomEntity;
import com.team8.potatodoctor.Models.Repositories.PestRepository;
import com.team8.potatodoctor.Models.Repositories.PlantLeafRepository;
import com.team8.potatodoctor.Models.Repositories.TuberRepository;
import com.team8.potatodoctor.activities.MenuBarActivities.ExitActivity;
import com.team8.potatodoctor.activities.MenuBarActivities.ImageShareActivity;
import com.team8.potatodoctor.activities.MenuBarActivities.SettingsActivity;
import com.team8.potatodoctor.activities.MenuBarActivities.UpdateActivity;
import com.team8.potatodoctor.Adapters.GalleryImageAdapter;

/*
 * Generalised class to extract information from the database related to a specific Pest/Symptom. 
 */
@SuppressWarnings("deprecation")
public class ObjectDescriptionActivity extends Activity
{
	//ImageView for full sized image when selected.
	ImageView selectedImage;
	
	private PestRepository pestRepository;
	private TuberRepository tuberRepository;
	private PlantLeafRepository plantLeafRepository;
	
	
	//TextView to contain text for specific Pest/Disease.
	TextView textView;
	
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_object_description);
		
		pestRepository = new PestRepository(getApplicationContext());
		tuberRepository = new TuberRepository(getApplicationContext());
		plantLeafRepository = new PlantLeafRepository(getApplicationContext());
		String type = "";
		int position = 0;
		
		//Extract parameters from the intent.
	    Bundle extras = getIntent().getExtras();
	    if(extras !=null)
	    {
	    	type = extras.getString("Type");
	    	position = extras.getInt("Position");
	    }
	    String description = "";
	    String title = "";
	    if(type.equals("potato_PlantLeaf"))
	    {
	    	title = plantLeafRepository.getAllPlantLeafs().get(position).getName();
	    	description = plantLeafRepository.getAllPlantLeafs().get(position).getDescription();
	    }
	    else if(type.equals("potato_Pest"))
	    {
	    	title = pestRepository.getAllPests().get(position).getName();
	    	description = pestRepository.getAllPests().get(position).getDescription();
	    }
	    else if(type.equals("potato_Tuber"))
	    {
	    	title = tuberRepository.getAllTubers().get(position).getName();
	    	description = tuberRepository.getAllTubers().get(position).getDescription();
	    }

	    setTitle(title);
	    //Setup ImageGallery
	    setImageGallery();
	    
        //Find TextView and allow scrolling.
        textView = (TextView)findViewById(R.id.textViewItem);
        textView.setText(description);
        textView.setMovementMethod(new ScrollingMovementMethod());
	}

  
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}
	
	private void setImageGallery()
	{
		//Set up the Image Gallery.
	    Gallery gallery = (Gallery) findViewById(R.id.imageGallery);
        selectedImage=(ImageView)findViewById(R.id.imageSelected);
        gallery.setSpacing(1);
        Bundle extras = getIntent().getExtras();
        String type = extras.getString("Type");
        Log.w("hello","TYPE = "+type);
	    	if(type.equals("potato_Pest"))
	    	{
                PestEntity currentPest = pestRepository.getAllPests().get(extras.getInt("Position"));
                gallery.setAdapter(new GalleryImageAdapter(this,currentPest));
                if(currentPest.getPhotos().size() > 0)
                {
                	selectedImage.setImageURI(Uri.parse(currentPest.getPhotos().get(0).getName()));
                }
                
	    	}
	    	else if(type.equals("potato_Tuber"))
	    	{
	    		TuberSymptomEntity tuber = tuberRepository.getAllTubers().get(extras.getInt("Position"));
	            gallery.setAdapter(new GalleryImageAdapter(this,tuber));
	    		if(tuber.getPhotos().size() > 0)
	    		{
	    			selectedImage.setImageURI(Uri.parse(tuber.getPhotos().get(0).getName()));
	    		}
	    	}
	    	else if(type.equals("potato_PlantLeaf"))
	    	{
	    		PlantLeafSymptomsEntity plantLeaf = plantLeafRepository.getAllPlantLeafs().get(extras.getInt("Position"));
	            gallery.setAdapter(new GalleryImageAdapter(this,plantLeaf));
	    		if(plantLeaf.getPhotos().size() > 0)
	    		{
	    			selectedImage.setImageURI(Uri.parse(plantLeaf.getPhotos().get(0).getName()));
	    		}
	    	}
        
        //Set up Event Listener for Images.
        gallery.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
             
        	    Bundle extras = getIntent().getExtras();
        	    String type;
        	    if(extras !=null)
        	    {
        	    	type = extras.getString("Type");
        	    	if(type.equals("potato_Pest"))
        	    	{
                        PestEntity currentPest = pestRepository.getAllPests().get(extras.getInt("Position"));
                        selectedImage.setImageURI(Uri.parse(currentPest.getPhotos().get(position).getName()));
        	    	}
        	    	else if(type.equals("potato_Tuber"))
        	    	{
        	    		TuberSymptomEntity tuber = tuberRepository.getAllTubers().get(extras.getInt("Position"));
        	    		selectedImage.setImageURI(Uri.parse(tuber.getPhotos().get(position).getName()));
        	    	}
        	    	else if(type.equals("potato_PlantLeafs"))
        	    	{
        	    		PlantLeafSymptomsEntity plantLeaf = plantLeafRepository.getAllPlantLeafs().get(extras.getInt("Position"));
        	    		selectedImage.setImageURI(Uri.parse(plantLeaf.getPhotos().get(position).getName()));
        	    	}
        	    	else
        	    	{
        	    		Log.w("hello", "not implemented");
        	    	}
        	    }
        	    else
        	    {
        	    	Log.w("hello", "ERROR PLEASE HELP");
        	    }

            }
        });
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
	    switch (item.getItemId())
	    {
	    case (R.id.action_imageshare):
	        this.startActivity(new Intent(this, ImageShareActivity.class));
	        return true;
	    case (R.id.action_update):
	        this.startActivity(new Intent(this, UpdateActivity.class));
	        return true;
	    case (R.id.action_settings):
	        this.startActivity(new Intent(this, SettingsActivity.class));
	        return true;
	    case (R.id.action_exit):
	        this.startActivity(new Intent(this, ExitActivity.class));
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
}