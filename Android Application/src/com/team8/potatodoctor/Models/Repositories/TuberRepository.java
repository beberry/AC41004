package com.team8.potatodoctor.Models.Repositories;

import java.util.LinkedList;

import com.team8.potatodoctor.DatabaseObjects.PestEntity;
import com.team8.potatodoctor.DatabaseObjects.PhotoEntity;
import com.team8.potatodoctor.DatabaseObjects.PhotoLinkerEntity;
import com.team8.potatodoctor.DatabaseObjects.TuberSymptomEntity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TuberRepository extends SQLiteOpenHelper

{
	private static final String DATABASE_NAME = "potato.db";
	private static final int DATABASE_VERSION = 1;
	
	public TuberRepository(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onCreate(db);	
	}
	
	 public LinkedList<TuberSymptomEntity> getAllTubers() {
	        LinkedList<TuberSymptomEntity> tubers = new LinkedList<TuberSymptomEntity>();

	        SQLiteDatabase db = getWritableDatabase();
	        Cursor cursor = db.rawQuery("SELECT * FROM potato_Tuber", null);

	        if (cursor.moveToFirst()) {
	            do {
	            	TuberSymptomEntity tuber = new TuberSymptomEntity();
	            	tuber.setId(cursor.getInt(cursor.getColumnIndex("Id")));
	            	tuber.setName(cursor.getString(cursor.getColumnIndex("Name")));
	                tuber.setDescription(cursor.getString(cursor.getColumnIndex("Description")));
	                tuber.setPhotos(getTuberPhotos(tuber));
	                tubers.add(tuber);
	            }
	            while (cursor.moveToNext());

	        }
	        db.close();
	        return tubers;
	    }
		
		public void insertTuber(TuberSymptomEntity tuber)
		{
				SQLiteDatabase db = this.getWritableDatabase();
				ContentValues values = new ContentValues();
				values.put("Id", tuber.getId());
				values.put("Name", tuber.getName());
				values.put("Description", tuber.getDescription());
				db.insert("potato_Tuber", null, values);
				db.close();
		}
		
		public void insertTuberPhotoLinker(PhotoLinkerEntity linker)
		{
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("Id", linker.getId());
			values.put("PhotoId", linker.getPhotoId());
			values.put("TuberId", linker.getEntryId());
			db.insert("potato_Tuber_photo", null, values);
			db.close();
		}
		
	    private LinkedList<Integer> getPhotoLinkersForTuber(TuberSymptomEntity tuber) {
	        LinkedList<Integer> photoIds = new LinkedList<Integer>();

	        SQLiteDatabase db = getWritableDatabase();
	        Cursor cursor = db.rawQuery("SELECT photoId FROM potato_Tuber_photo WHERE PestId = "+tuber.getId(), null);

	        if (cursor.moveToFirst()) {
	            do {
	            	photoIds.add(cursor.getInt(cursor.getColumnIndex("PhotoId")));
	            }
	            while (cursor.moveToNext());
	        }
	        db.close();
	        return photoIds;
	    }
		
		public LinkedList<PhotoEntity> getTuberPhotos(TuberSymptomEntity tuber)
		{
			LinkedList<Integer> photoIds = getPhotoLinkersForTuber(tuber);
			if(photoIds.size() == 0)
			{
				return new LinkedList<PhotoEntity>();
			}
			String SQLQuery = "SELECT * FROM potato_Photo WHERE Id = ";
			for(int i = 0; i < photoIds.size(); i++)
			{
				SQLQuery+= photoIds.get(i).toString();
				if(i < photoIds.size() - 1)
				{
					SQLQuery+= " OR Id = ";
				}
			}
			LinkedList<PhotoEntity> photos = new LinkedList<PhotoEntity>();
	        SQLiteDatabase db = getWritableDatabase();
	        Cursor cursor = db.rawQuery(SQLQuery, null);

	        if (cursor.moveToFirst()) {
	            do {
	            	PhotoEntity photo = new PhotoEntity();
	            	photo.setId(cursor.getInt(cursor.getColumnIndex("Id")));
	            	photo.setName(cursor.getString(cursor.getColumnIndex("Name")));
	            	photos.add(photo);
	            }
	            while (cursor.moveToNext());
	        }
	        db.close();
	        return photos;
		}
}