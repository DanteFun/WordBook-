package com.fun.dante.wordbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Fun on 2018/4/21.
 */

public class MyDatabase extends SQLiteOpenHelper{

    private ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
    public static final String name = "Dante_DB";
    public static final int version = 1;

    public static final String TABLE_NAME = "word_table";
    public static MyDatabase myDataBase;

    private Context mContext;
    public MyDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table" + " " + TABLE_NAME + "(word text PRIMARY KEY NOT NULL,english text,american text,property text NOT NULL,example text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if(i1>i)
        {
            sqLiteDatabase.execSQL("drop" + TABLE_NAME);
            onCreate(sqLiteDatabase);
        }
    }

    public boolean insert(String word, String english, String american, String property, String example) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("word", word);
        values.put("english", english);
        values.put("american", american);
        values.put("property", property);
        values.put("example",example);
        database.insert(TABLE_NAME, null, values);
        database.close();
        return true;
    }
    public void delete(String word, String english, String american, String property, String example) {
        SQLiteDatabase database = getWritableDatabase();
        /*
        删除的条件,当id = 传入的参数id时,sex = 传入的参数sex时,age = 传入的age,hobby = 传入的hobby时
        当条件都满足时才删除这行数据,一个条件不满足就删除失败
         */
        String where = "word=? and english = ? and american = ? and property = ? and example = ?";
        String[] whereArgs = {word + "", english, american, property, example};
        database.delete(TABLE_NAME, where, whereArgs);
        database.close();
    }

    public void delete(String word) {
        SQLiteDatabase database = getWritableDatabase();
        //当条件满足id = 传入的参数的时候,就删除那整行数据,有可能有好几行都满足这个条件,满足的全部都删除
        String where = "word = ?";
        String[] whereArgs = {word + ""};
        database.delete(TABLE_NAME, where, whereArgs);
        database.close();
    }

    public void updata(String word, String english, String american, String property, String example) {
        SQLiteDatabase database = getWritableDatabase();
        String where = "word = ?";
        String[] whereArgs = {word + ""};
        ContentValues values = new ContentValues();
        values.put("english", english);
        values.put("american", american);
        values.put("property", property);
        values.put("example", example);
        //参数1  表名称  参数2  跟行列ContentValues类型的键值对Key-Value
        // 参数3  更新条件（where字句）    参数4  更新条件数组
        database.update(TABLE_NAME, values, where, whereArgs);
        database.close();
    }

    public void Single_updata(String word,String property) {
        SQLiteDatabase database = getWritableDatabase();
        String where = "word = ?";
        String[] whereArgs = {word + ""};
        ContentValues values = new ContentValues();
        values.put("property", property);
        //参数1  表名称  参数2  跟行列ContentValues类型的键值对Key-Value
        // 参数3  更新条件（where字句）    参数4  更新条件数组
        database.update(TABLE_NAME, values, where, whereArgs);
        database.close();
    }

    public Cursor query() {
        //数据库可读
        SQLiteDatabase database = getReadableDatabase();
        //查找
        Cursor query = database.query(TABLE_NAME, null, null, null, null, null, null);
        return query;
    }

    public Cursor Single_query() {
        //数据库可读
        SQLiteDatabase database = getReadableDatabase();
        //查找

        Cursor query = database.query(TABLE_NAME, null, null, null, null, null, null);
        return query;
    }

    public ArrayList query_all(){
        ArrayList a =new ArrayList();
        Cursor cursor = Single_query();
        if (cursor.moveToFirst()) {
            do {
                String word = cursor.getString(cursor.getColumnIndex("word"));
                a.add(word);
            } while (cursor.moveToNext());
        }
        return a;
    }

    public void query_entire(ArrayList word_arr,ArrayList property_arr,ArrayList american_arr){
        Cursor cursor = Single_query();
        if (cursor.moveToFirst()) {
            do {
                String word = cursor.getString(cursor.getColumnIndex("word"));
                String american = cursor.getString(cursor.getColumnIndex("american"));
                String property = cursor.getString(cursor.getColumnIndex("property"));

                word_arr.add(word);
                american_arr.add(american);
                property_arr.add(property);
            } while (cursor.moveToNext());
        }
    }

    public Cursor vague_select(String ch) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "select * from " + TABLE_NAME + " where word like ? order by word asc";
        Cursor c = database.rawQuery(sql, new String[]{"%" + ch + "%"});
        return c;
    }


    public void vague_entire(ArrayList word_arr,ArrayList property_arr,String r_word){
        Cursor cursor = vague_select(r_word);
        if (cursor.moveToFirst()) {
            do {
                String word = cursor.getString(cursor.getColumnIndex("word"));
                String property = cursor.getString(cursor.getColumnIndex("property"));

                word_arr.add(word);
                property_arr.add(property);
            } while (cursor.moveToNext());
        }
    }


}
