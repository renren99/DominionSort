package com.example.dominionsort;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


// データベースを使うためにSQLiteOpenHelperを継承する。
public class DBHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME ="cardlist";
    public static final String NO = "number";
    public static final String CARDNAME = "name";
    public static final String SET = "sets";
    public static final String COST = "cost";
    public static final String CARDTYPE = "type";
    private static final int version = 1;

    public DBHelper(Context context) {
        super(context, TABLE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + TABLE_NAME +
                        "(" + NO + " INTEGER ," +
                        CARDNAME + " TEXT," +
                        SET + " TEXT," +
                        COST +  " INTEGER," +
                        CARDTYPE +  " TEXT" +
                        ");");
    }

    // 新しい項目を追加したい時に使うメソッド    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean deleteAll(SQLiteDatabase db){

        return db.delete(TABLE_NAME, null, null) > 0;
    }
}