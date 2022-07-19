package com.example.flyingchicken;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String USER_TABLE = "USER_TABLE";
    public static final String COLUMN_USER_ID = "USER_ID";
    public static final String COLUMN_USER_BEST_SCORE = "USER_BEST_SCORE";
    public static final String COLUMN_USER_COINS = "USER_COINS";


    public DataBaseHelper(@Nullable Context context) {
        super(context,"user.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatmet= "CREATE TABLE " + USER_TABLE + " (" + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_BEST_SCORE + " INT DEFAULT 0," + COLUMN_USER_COINS + " INT DEFAULT 0)";
        db.execSQL((createTableStatmet));


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public int getCoins(){
        String queryString ="SELECT * " + " FROM " + USER_TABLE;
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        if(cursor.moveToFirst()) {
            int coins = cursor.getInt(2);
            cursor.close();
            db.close();
            return coins;
        } else{
            cursor.close();
            db.close();
            return -1;
        }
    }
    public int setCoins(int coins){
        String queryString ="UPDATE " + USER_TABLE + " SET " + COLUMN_USER_COINS + " = " + String.valueOf(coins);
        SQLiteDatabase db =this.getWritableDatabase();
        try{
            db.execSQL(queryString);
            db.close();
            return 0;
        }catch (Exception e){
            db.close();
            return -1;
        }
    }
    public int setBestScore(int score){
        String queryString ="UPDATE " + USER_TABLE + " SET " + COLUMN_USER_BEST_SCORE + " = " + String.valueOf(score);
        SQLiteDatabase db =this.getWritableDatabase();
        try{
            db.execSQL(queryString);
            db.close();
            return 0;
        }catch (Exception e){
            db.close();
            return -1;
        }
    }
    public int getBestScore(){
        String queryString ="SELECT * " + " FROM " + USER_TABLE;
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        if(cursor.moveToFirst()) {
            int score = cursor.getInt(1);
            cursor.close();
            db.close();
            return score;
        } else{
            cursor.close();
            db.close();
            return -1;
        }
    }
    public void checkAndUpdate(){                   //se la tabella è vuota (prima volta che si usa l'app) inserico i dati dell'utente
        SQLiteDatabase db =this.getReadableDatabase();
        String count = "SELECT count(*) FROM " + USER_TABLE;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if(icount>0){
        }else{
            String query="INSERT INTO " + USER_TABLE +" VALUES( 1, 0, 0)";
            db.execSQL(query);
        }
    }
}

