package com.example.dominionsort.ui.main;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dominionsort.DBHelper;
import com.example.dominionsort.MainActivity;
import com.example.dominionsort.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TopActivity extends AppCompatActivity {

    private static final String FILE_NAME = "dominion";
    private DBHelper dbHelper;
    TextView card1;
    TextView card2;
    TextView card3;
    TextView card4;
    TextView card5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);

        card1 = (TextView) findViewById(R.id.textView1);
        card2 = (TextView) findViewById(R.id.textView2);
        card3 = (TextView) findViewById(R.id.textView3);
        card4 = (TextView) findViewById(R.id.textView4);
        card5 = (TextView) findViewById(R.id.textView5);


        // DBの初期化処理
        dbHelper = new DBHelper(this, FILE_NAME, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 存在確認
        String query = "SELECT COUNT(*) FROM " + DBHelper.TABLE_NAME + ";";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        //読み込み
        try{
        AssetManager assetManager = getResources().getAssets();
        InputStream is = assetManager.open("dominion.csv");
        InputStreamReader inputStreamReader = new InputStreamReader(is);
        BufferedReader bufferReader = new BufferedReader(inputStreamReader);
        String line = "";
        String array[] = null;
        while ((line = bufferReader.readLine()) != null) {
            array = line.split(",");
            ContentValues values = new ContentValues();
            values.put(DBHelper.NO, array[0]);
            values.put(DBHelper.CARDNAME, array[1]);
            values.put(DBHelper.SET, array[2]);
            values.put(DBHelper.COST, array[3]);
            values.put(DBHelper.CARDTYPE, array[4]);
            db.insert(DBHelper.TABLE_NAME, null, values);
        }
        show(db);
        bufferReader.close();
    } catch(
    IOException e)

    {
        e.printStackTrace();
    } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    public void show(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("select name from " + DBHelper.TABLE_NAME +"  where "+DBHelper.NO +" = 15",null);
       // cursor.moveToFirst();
        card1.setText(cursor.getString(2));
    }
}