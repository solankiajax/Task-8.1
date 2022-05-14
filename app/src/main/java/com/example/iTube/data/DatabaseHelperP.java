package com.example.iTube.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.iTube.model.Playlist;
import com.example.iTube.model.User;
import com.example.iTube.util.Util;
import com.example.iTube.util.UtilP;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelperP extends SQLiteOpenHelper {
    private static final String TAG = "tag";

    public DatabaseHelperP(@Nullable Context context) {
        super(context, UtilP.DATABASE_NAME, null, UtilP.DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_USER_TABLE = "CREATE TABLE " + UtilP.TABLE_NAME + "(" + UtilP.VIDEO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + UtilP.USER_ID + " INTEGER , " +  UtilP.VIDEO_URI + " TEXT)";
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String DROP_USER_TABLE = "DROP TABLE IF EXISTS '" + UtilP.TABLE_NAME + "'";
        sqLiteDatabase.rawQuery(DROP_USER_TABLE,null);
        onCreate(sqLiteDatabase);
    }

    public long insertVideo (Playlist playlist)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UtilP.USER_ID, playlist.getUser_id());
        contentValues.put(UtilP.VIDEO_URI, playlist.getVideo_uri());
        long newRowId = db.insert(UtilP.TABLE_NAME, null, contentValues);
        db.close();
        return newRowId;
    }

    public Playlist fetchVideObject(Integer user_id, Integer video_id){
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Playlist video = new Playlist();
            String selectAll = " SELECT * FROM " + UtilP.TABLE_NAME + " where " + UtilP.USER_ID+ " = '" + user_id +
                    "' and " + UtilP.VIDEO_ID + " = '" + video_id + "'";
            Cursor cursor = db.rawQuery(selectAll, null);

            if (cursor.moveToFirst()) {
                do {
                    video.setUser_id(cursor.getInt(1));
                    video.setVideo_uri(cursor.getString(2));

                } while (cursor.moveToNext());
            }
            else{
                return new Playlist();
            }
            return video;
        }
        catch (Exception e){

            Log.d("reached",e.getMessage());
            return new Playlist();
        }
    }

    public List<Playlist> fetchAllVideo (Integer user_id){
        List<Playlist> playList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = " SELECT * FROM " + UtilP.TABLE_NAME + " where " + UtilP.USER_ID + " = '" + user_id + "'";
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) {
            do {
                Playlist video = new Playlist();

                video.setVideo_id(cursor.getInt(0));
                video.setUser_id(cursor.getInt(1));
                video.setVideo_uri(cursor.getString(2));
                playList.add(video);
            } while (cursor.moveToNext());
        }
        return playList;
    }
}
