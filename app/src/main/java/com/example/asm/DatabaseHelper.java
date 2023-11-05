package com.example.asm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "HikeTracker.db";
    private static final int DATABASE_VERSION = 1;

    // Các trường của bảng hikes
    public static final String TABLE_HIKES = "hikes";
    public static final String COLUMN_ID_HIKE = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_PARKING = "parking";
    public static final String COLUMN_LENGTH = "length";
    public static final String COLUMN_DIFFICULTY = "difficulty";


    private static final String TABLE_OBSERVATIONS = "observations";
    public static final String COLUMN_ID_OBSERVATION = "id";
    private static final String COLUMN_HIKE_ID = "hike_id";
    private static final String COLUMN_OBSERVATION = "observation";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_COMMENTS = "comments";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableObservation = "CREATE TABLE " + TABLE_OBSERVATIONS + " (" +
                COLUMN_ID_OBSERVATION + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_HIKE_ID + " INTEGER, " +
                COLUMN_OBSERVATION + " TEXT, " +
                COLUMN_TIME + " TEXT, " +
                COLUMN_COMMENTS + " TEXT)";
        db.execSQL(createTableObservation);
        String createTableHike = "CREATE TABLE " + TABLE_HIKES + " (" +
                COLUMN_ID_HIKE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_LOCATION + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_PARKING + " TEXT, " +
                COLUMN_LENGTH + " TEXT, " +
                COLUMN_DIFFICULTY + " TEXT)";
        db.execSQL(createTableHike);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insertHike(String name, String location, String date, String parking, String length, String difficulty) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_LOCATION, location);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_PARKING, parking);
        values.put(COLUMN_LENGTH, length);
        values.put(COLUMN_DIFFICULTY, difficulty);
        return db.insert(TABLE_HIKES, null, values);
    }
    public List<HikeModel> getAllHikes() {
        List<HikeModel> hikes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID_HIKE, COLUMN_NAME, COLUMN_LOCATION, COLUMN_DATE, COLUMN_PARKING, COLUMN_LENGTH, COLUMN_DIFFICULTY};
        Cursor cursor = db.query(TABLE_HIKES, columns, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                HikeModel hike = new HikeModel();
                int idColumnIndex = cursor.getColumnIndex(COLUMN_ID_HIKE);
                if (idColumnIndex != -1) {
                    hike.setId(cursor.getInt(idColumnIndex));
                } else {
                    hike.setId(-1);
                }

                int nameColumnIndex = cursor.getColumnIndex(COLUMN_NAME);
                if (nameColumnIndex != -1) {
                    hike.setName(cursor.getString(nameColumnIndex));
                } else {
                    hike.setName("");
                }

                int locationColumnIndex = cursor.getColumnIndex(COLUMN_LOCATION);
                if (locationColumnIndex != -1) {
                    hike.setLocation(cursor.getString(locationColumnIndex));
                } else {
                    hike.setLocation("");
                }

                int dateColumnIndex = cursor.getColumnIndex(COLUMN_DATE);
                if (dateColumnIndex != -1) {
                    hike.setDate(cursor.getString(dateColumnIndex));
                } else {
                    hike.setDate("");
                }

                int parkingAvailableColumnIndex = cursor.getColumnIndex(COLUMN_PARKING);
                if (parkingAvailableColumnIndex != -1) {
                    hike.setParking(cursor.getString(parkingAvailableColumnIndex));
                } else {
                    hike.setParking("");
                }

                int lengthColumnIndex = cursor.getColumnIndex(COLUMN_LENGTH);
                if (lengthColumnIndex != -1) {
                    hike.setLength(cursor.getString(lengthColumnIndex));
                } else {
                    hike.setLength("");
                }

                int difficultyLevelColumnIndex = cursor.getColumnIndex(COLUMN_DIFFICULTY);
                if (difficultyLevelColumnIndex != -1) {
                    hike.setDifficulty(cursor.getString(difficultyLevelColumnIndex));
                } else {
                    hike.setDifficulty("");
                }
                hikes.add(hike);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return hikes;
    }
    public List<HikeModel> searchHikes(String query) {
        List<HikeModel> hikeList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();


        String selectQuery = "SELECT * FROM " + TABLE_HIKES +
                " WHERE " + COLUMN_NAME + " LIKE '%" + query + "%' OR " +
                COLUMN_LOCATION + " LIKE '%" + query + "%'";


        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                HikeModel hike = new HikeModel();

                int nameColumnIndex = cursor.getColumnIndex(COLUMN_NAME);
                if (nameColumnIndex != -1) {
                    hike.setName(cursor.getString(nameColumnIndex));
                } else {
                    hike.setName("");
                }

                int locationColumnIndex = cursor.getColumnIndex(COLUMN_LOCATION);
                if (locationColumnIndex != -1) {
                    hike.setLocation(cursor.getString(locationColumnIndex));
                } else {
                    hike.setLocation("");
                }

                int dateColumnIndex = cursor.getColumnIndex(COLUMN_DATE);
                if (dateColumnIndex != -1) {
                    hike.setDate(cursor.getString(dateColumnIndex));
                } else {
                    hike.setDate("");
                }

                int parkingAvailableColumnIndex = cursor.getColumnIndex(COLUMN_PARKING);
                if (parkingAvailableColumnIndex != -1) {
                    hike.setParking(cursor.getString(parkingAvailableColumnIndex));
                } else {
                    hike.setParking("");
                }

                int lengthColumnIndex = cursor.getColumnIndex(COLUMN_LENGTH);
                if (lengthColumnIndex != -1) {
                    hike.setLength(cursor.getString(lengthColumnIndex));
                } else {
                    hike.setLength("");
                }

                int difficultyLevelColumnIndex = cursor.getColumnIndex(COLUMN_DIFFICULTY);
                if (difficultyLevelColumnIndex != -1) {
                    hike.setDifficulty(cursor.getString(difficultyLevelColumnIndex));
                } else {
                    hike.setDifficulty("");
                }

                hikeList.add(hike);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return hikeList;
    }
    public long updateHike(int hikeId, String name, String location, String date, String parking, String length, String difficulty) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, name);
        values.put(COLUMN_LOCATION, location);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_PARKING, parking);
        values.put(COLUMN_LENGTH, length);
        values.put(COLUMN_DIFFICULTY, difficulty);

        String whereClause = COLUMN_ID_HIKE + " = ?";
        String[] whereArgs = {String.valueOf(hikeId)};

        int rowsUpdated = db.update(TABLE_HIKES, values, whereClause, whereArgs);

        return rowsUpdated;
    }
    public boolean deleteHike(int hikeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = COLUMN_ID_HIKE + " = ?";
        String[] whereArgs = { String.valueOf(hikeId) };
        int result = db.delete(TABLE_HIKES, whereClause, whereArgs);
        db.close();
        return result > 0;
    }
    public boolean deleteObservation(int observationId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = COLUMN_ID_OBSERVATION + " = ?";
        String[] whereArgs = { String.valueOf(observationId) };
        int result = db.delete(TABLE_OBSERVATIONS, whereClause, whereArgs);
        db.close();
        return result > 0;
    }
    private static final String CREATE_OBSERVATIONS_TABLE = "CREATE TABLE " + TABLE_OBSERVATIONS + " (" +
            COLUMN_ID_OBSERVATION + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_HIKE_ID + " INTEGER, " +
            COLUMN_OBSERVATION + " TEXT, " +
            COLUMN_TIME + " TEXT, " +
            COLUMN_COMMENTS + " TEXT)";


    public long insertObservation(int hikeId, String observation, String time, String comments) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HIKE_ID, hikeId);
        values.put(COLUMN_OBSERVATION, observation);
        values.put(COLUMN_TIME, time);
        values.put(COLUMN_COMMENTS, comments);
        return db.insert(TABLE_OBSERVATIONS, null, values);
    }

    public long updateObservation(int observationId, String newObservation, String newTime, String newComments) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_OBSERVATION, newObservation);
        values.put(COLUMN_TIME, newTime);
        values.put(COLUMN_COMMENTS, newComments);

        String whereClause = COLUMN_ID_OBSERVATION + " = ?";
        String[] whereArgs = {String.valueOf(observationId)};

        int rowsUpdated = db.update(TABLE_OBSERVATIONS, values, whereClause, whereArgs);

        db.close();

        return rowsUpdated;
    }

    public boolean hasObservationsForHike(int hikeId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT COUNT(*) FROM " + TABLE_OBSERVATIONS +
                " WHERE " + COLUMN_HIKE_ID + " = " + hikeId;
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }
    public List<ObservationModel> getObservationsForHike(int hikeId) {
        List<ObservationModel> observations = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID_OBSERVATION, COLUMN_OBSERVATION,COLUMN_HIKE_ID , COLUMN_TIME, COLUMN_COMMENTS};
        String selection = COLUMN_HIKE_ID + " = ?";
        String[] selectionArgs = {String.valueOf(hikeId)};
        Cursor cursor = db.query(TABLE_OBSERVATIONS, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                ObservationModel observation = new ObservationModel();
                int idColumnIndex = cursor.getColumnIndex(COLUMN_ID_OBSERVATION);
                if (idColumnIndex != -1) {
                    observation.setId(cursor.getInt(idColumnIndex));
                } else {
                    observation.setId(-1);
                }
                int observationColumIndex = cursor.getColumnIndex(COLUMN_OBSERVATION);
                if(observationColumIndex != -1){
                    observation.setObservation(cursor.getString(observationColumIndex));
                }else {
                    observation.setObservation("");
                }
                int timeColumIndex = cursor.getColumnIndex(COLUMN_TIME);
                if(timeColumIndex != -1){
                    observation.setTime(cursor.getString(timeColumIndex));
                }else {
                    observation.setTime("");
                }
                int hikeIdColumIndex = cursor.getColumnIndex((COLUMN_HIKE_ID));
                if(hikeIdColumIndex != -1){
                    observation.setHikeId(cursor.getInt(hikeIdColumIndex));
                }else{
                    observation.setHikeId(0);
                }
                int commentColumIndex = cursor.getColumnIndex(COLUMN_COMMENTS);
                if(commentColumIndex != -1){
                    observation.setComments(cursor.getString(commentColumIndex));
                }else {
                    observation.setComments("");
                }
                observations.add(observation);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return observations;
    }
}