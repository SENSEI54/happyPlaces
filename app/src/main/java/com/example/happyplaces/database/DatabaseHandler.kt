package com.example.happyplaces.database

import android.annotation.SuppressLint
import com.example.happyplaces.models.HappyPlaceModel
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteException

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "HappyPlaceDatabase"
        private const val TABLE_HAPPY_PLACE = "HappyPlacesTable"


        private const val KEY_ID = "_id"
        private const val KEY_TITTLE = "tittle"
        private const val KEY_IMAGE = "Image"
        private const val KEY_DESCRIPTION = "Description"
        private const val KEY_DATE = "Date"
        private const val KEY_LOCATION = "Location"
        private const val KEY_LATITUDE = "Latitude"
        private const val KEY_LONGITUDE = "Longitude"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_HAPPY_PLACE_TABLE = ("CREATE TABLE " + TABLE_HAPPY_PLACE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TITTLE + " TEXT,"
                + KEY_IMAGE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_DATE + " TEXT,"
                + KEY_LOCATION + " TEXT,"
                + KEY_LATITUDE + " TEXT,"
                + KEY_LONGITUDE + " TEXT)")
        db?.execSQL(CREATE_HAPPY_PLACE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_HAPPY_PLACE")
        onCreate(db)
    }


    @SuppressLint("Range")
    fun getHappyPlacesList(): ArrayList<HappyPlaceModel> {

        val happyPlaceList: ArrayList<HappyPlaceModel> = ArrayList()
        val selectQuery = "SELECT  * FROM $TABLE_HAPPY_PLACE"
        val db = this.readableDatabase

        try {
            val cursor: Cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    val place = HappyPlaceModel(
                        cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                        cursor.getString(cursor.getColumnIndex(KEY_TITTLE)),
                        cursor.getString(cursor.getColumnIndex(KEY_IMAGE)),
                        cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(KEY_DATE)),
                        cursor.getString(cursor.getColumnIndex(KEY_LOCATION)),
                        cursor.getDouble(cursor.getColumnIndex(KEY_LATITUDE)),
                        cursor.getDouble(cursor.getColumnIndex(KEY_LONGITUDE))
                    )
                    happyPlaceList.add(place)

                } while (cursor.moveToNext())
            }
            cursor.close()
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        return happyPlaceList
    }


    fun addHappyPlaces(happyPlace: HappyPlaceModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_TITTLE, happyPlace.title)
        contentValues.put(KEY_IMAGE, happyPlace.Image)
        contentValues.put(KEY_DESCRIPTION, happyPlace.Description)
        contentValues.put(KEY_DATE, happyPlace.Date)
        contentValues.put(KEY_LOCATION, happyPlace.Location)
        contentValues.put(KEY_LATITUDE, happyPlace.Latitude)
        contentValues.put(KEY_LONGITUDE, happyPlace.Longitude)


        val result = db.insert(TABLE_HAPPY_PLACE, null, contentValues)
        db.close()
        return result
    }

    fun updateHappyPlace(happyPlace: HappyPlaceModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_TITTLE, happyPlace.title)
        contentValues.put(KEY_IMAGE, happyPlace.Image)
        contentValues.put(
            KEY_DESCRIPTION,
            happyPlace.Description
        )
        contentValues.put(KEY_DATE, happyPlace.Date)
        contentValues.put(KEY_LOCATION, happyPlace.Location)
        contentValues.put(KEY_LATITUDE, happyPlace.Latitude)
        contentValues.put(KEY_LONGITUDE, happyPlace.Longitude)


        val success = db.update(TABLE_HAPPY_PLACE, contentValues, KEY_ID + "=" + happyPlace.id, null)

        db.close()
        return success
    }

    fun deleteHappyPlace(happyPlace: HappyPlaceModel):Int{
        val db=this.writableDatabase
        val success = db.delete(TABLE_HAPPY_PLACE, KEY_ID+"="+happyPlace.id,null)
        db.close()
        return success
    }

}
