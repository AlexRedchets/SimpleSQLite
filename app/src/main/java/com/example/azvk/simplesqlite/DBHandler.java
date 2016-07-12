package com.example.azvk.simplesqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper{

    private final static int DATABASE_VERSION = 1;
    private final static String DATABASE_NAME = "items.db";
    private static final String TABLE_ITEMS = "items";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_ITEMNAME = "itemname";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_ITEMS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ITEMNAME + " TEXT " +
                ");";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST " + TABLE_ITEMS);
        onCreate(sqLiteDatabase);
    }

    //add new row to the database
    public void addNote(Items items){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEMNAME, items.get_itemName());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.insert(TABLE_ITEMS, null, values);
        sqLiteDatabase.close();
    }

    //delete note from the database
    public boolean deleteNote(String noteName){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ITEMS + " WHERE " + COLUMN_ITEMNAME + "=\"" + noteName + "\";";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.getCount() != 0) {
            cursor.close();
            sqLiteDatabase.execSQL("DELETE FROM " + TABLE_ITEMS + " WHERE " + COLUMN_ITEMNAME + "=\"" + noteName + "\";");
            return true;
        }else {
            cursor.close();
            return false;
        }
    }

    //delete all items
    public void deleteAllItems(){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM " + TABLE_ITEMS);
    }

    //print out the database
    public List<String> databaseToString(){
        List<String> dbSting = null;
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ITEMS + " WHERE 1";

        //Cursor point to a location in results
        Cursor c = sqLiteDatabase.rawQuery(query, null);
        //Move to the first row in results
        if (c != null) {
            dbSting = new ArrayList<>();
            while (c.moveToNext()) {
                if (c.getString(c.getColumnIndex(COLUMN_ITEMNAME)) != null) {
                    dbSting.add(c.getString(c.getColumnIndex(COLUMN_ITEMNAME)));
                }
            }
            c.close();
            sqLiteDatabase.close();
        }
        return dbSting;

    }
}
