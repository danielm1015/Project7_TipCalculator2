package com.murach.tipcalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Daniel Martinez on 7/12/2017.
 */

public class Database extends SQLiteOpenHelper{

    // Define database variables
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tipCalculator.db";
    public static final String TABLE_NAME= "tips";
    public static final String COLUMN_ID = "_id";
    public static final int COLUMN_BILL_DATE = 0;
    public static final float COLUMN_BILL_AMOUNT = 50.10f;
    public static final float COLUMN_TIP_PERCENT= .15f;

    // Define SQLit database variables
    private SQLiteDatabase databaseHandler;


    // Constructor
    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Responsible for creating a table for the first time

    // Implement Methods
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_BILL_DATE +" INTEGER NOT NULL, "
                + COLUMN_BILL_AMOUNT + " REAL NOT NULL"
                + COLUMN_TIP_PERCENT + " REAL NOT NULL"+");";
        sqLiteDatabase.execSQL(query);

    }

    // Responsible to making update to an existing table
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        // Delete the entire table if it exits
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Recreate the table with the new properties
        onCreate(sqLiteDatabase);

    }

    public Database open() throws SQLException {
        databaseHandler = getWritableDatabase(); // get reference to the database
        return this;
    }

    // Add new row to the database
    public ArrayList<Tip> getTips(){

        Tip tip = new Tip();

        // Content value is built into android that allows you to add several values in one statement
        ContentValues values = new ContentValues();
        values.put(tip.getBillAmountFormatted(), COLUMN_BILL_AMOUNT);
        values.put(tip.getDateStringFormatted(), COLUMN_BILL_DATE);
        values.put(tip.getTipPercentFormatted(), COLUMN_TIP_PERCENT);

        // Call the open method to get reference to the database
        open();
        databaseHandler.insert(TABLE_NAME, null, values);

        //
        String[] allColumns = new String[] {
                tip.getBillAmountFormatted(), tip.getDateStringFormatted(), tip.getTipPercentFormatted()};
        ArrayList<Tip> tipArrayList = new ArrayList<Tip>();
        Cursor cursor = databaseHandler.query(TABLE_NAME, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while(cursor.moveToNext())
        {
             tipArrayList.add();
        }

        // Once you're done with database close it to give memory back (prevent memory leak)
        close();

        return tipArrayList;
    }


    // Delete all info from database
    public void deleteTable() {

        open();
        // Delete all records from database
        databaseHandler.execSQL("DELETE FROM " + TABLE_NAME + ";");
    }


}
