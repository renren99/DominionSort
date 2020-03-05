package com.example.dominionsort.ui.main;

import android.content.ContentValues;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.dominionsort.DBHelper;
import com.example.dominionsort.R;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TopActivity extends AppCompatActivity {

    private static final String FILE_NAME = "dominion";

    private DBHelper dbHelper;
   ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);

        listView = (ListView) findViewById(R.id.card_list_view);

        // DBの初期化処理
        dbHelper = new DBHelper(this);
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
        bufferReader.close();
        show();
    } catch(
    IOException e)

    {
        e.printStackTrace();
    } catch (
                SQLException e) {
            e.printStackTrace();
        }
        db.close();
    }

    /**
     * 中でchoose()を呼び出し、ランダムで10枚のカードを選択する
     * @return
     */
    public void show() {
        List<Integer> tenCards = new ArrayList<Integer>();

        /**
         * 5つの数字を返す
         */
        tenCards = choose();
        if(tenCards.size() != 10){
            //何かエラー処理書く
        }

        /**
         * 受け取った配列をあてはめ、sql実行
         */

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor1 = db.rawQuery("select name from " + DBHelper.TABLE_NAME +"  where "+DBHelper.NO +" = "+ tenCards.get(0),null);
        Cursor cursor2 = db.rawQuery("select name from " + DBHelper.TABLE_NAME +"  where "+DBHelper.NO +" = "+ tenCards.get(1),null);
        Cursor cursor3 = db.rawQuery("select name from " + DBHelper.TABLE_NAME +"  where "+DBHelper.NO +" = "+ tenCards.get(2),null);
        Cursor cursor4 = db.rawQuery("select name from " + DBHelper.TABLE_NAME +"  where "+DBHelper.NO +" = "+ tenCards.get(3),null);
        Cursor cursor5 = db.rawQuery("select name from " + DBHelper.TABLE_NAME +"  where "+DBHelper.NO +" = "+ tenCards.get(4),null);
        Cursor cursor6 = db.rawQuery("select name from " + DBHelper.TABLE_NAME +"  where "+DBHelper.NO +" = "+ tenCards.get(5),null);
        Cursor cursor7 = db.rawQuery("select name from " + DBHelper.TABLE_NAME +"  where "+DBHelper.NO +" = "+ tenCards.get(6),null);
        Cursor cursor8 = db.rawQuery("select name from " + DBHelper.TABLE_NAME +"  where "+DBHelper.NO +" = "+ tenCards.get(7),null);
        Cursor cursor9 = db.rawQuery("select name from " + DBHelper.TABLE_NAME +"  where "+DBHelper.NO +" = "+ tenCards.get(8),null);
        Cursor cursor10 = db.rawQuery("select name from " + DBHelper.TABLE_NAME +"  where "+DBHelper.NO +" = "+ tenCards.get(9),null);

        //cursor.moveToFirst();
        List<String> cardList = new ArrayList<String>();
        cursor1.moveToFirst();
        cursor2.moveToFirst();
        cursor3.moveToFirst();
        cursor4.moveToFirst();
        cursor5.moveToFirst();
        cursor6.moveToFirst();
        cursor7.moveToFirst();
        cursor8.moveToFirst();
        cursor9.moveToFirst();
        cursor10.moveToFirst();

        cardList.add(cursor1.getString(cursor1.getColumnIndex(DBHelper.CARDNAME)));
        cardList.add(cursor2.getString(cursor1.getColumnIndex(DBHelper.CARDNAME)));
        cardList.add(cursor3.getString(cursor1.getColumnIndex(DBHelper.CARDNAME)));
        cardList.add(cursor4.getString(cursor1.getColumnIndex(DBHelper.CARDNAME)));
        cardList.add(cursor5.getString(cursor1.getColumnIndex(DBHelper.CARDNAME)));
        cardList.add(cursor6.getString(cursor1.getColumnIndex(DBHelper.CARDNAME)));
        cardList.add(cursor7.getString(cursor1.getColumnIndex(DBHelper.CARDNAME)));
        cardList.add(cursor8.getString(cursor1.getColumnIndex(DBHelper.CARDNAME)));
        cardList.add(cursor9.getString(cursor1.getColumnIndex(DBHelper.CARDNAME)));
        cardList.add(cursor10.getString(cursor1.getColumnIndex(DBHelper.CARDNAME)));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cardList);
        listView.setAdapter(arrayAdapter);

    }

    /**
     * カードに紐づくnumberの中からランダムに数字を5つ選ぶ
     */
    public List<Integer> choose() {
        List<Integer> numberOfCards = new ArrayList<Integer>();
        List<Integer> tenCards = new ArrayList<Integer>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c_cards = db.rawQuery("select number from " + DBHelper.TABLE_NAME, null);
        c_cards.moveToFirst();
        while (c_cards.moveToNext()) {
            //ここで何回もroopしてしまう
            // numberOfCards.add(c_cards.getInt(c_cards.getColumnIndex(DBHelper.NO)));
            numberOfCards.add(c_cards.getInt(0));
        }

        List<Integer> shuffled = new ArrayList<>(numberOfCards);
        Collections.shuffle(shuffled);

        for (int i = 0; i < 10; i++) {
            tenCards.add(shuffled.get(i));
        }
        return tenCards;
    }
}