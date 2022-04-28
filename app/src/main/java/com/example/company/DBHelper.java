package com.example.company;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "Company";
    public static final String TABLE_EMPLOYEES = "employees";
    public static final String KEY_ID = "_id";
    public static final String KEY_SURNAME = "surname";
    public static final String KEY_NAME = "name";
    public static final String KEY_PATRONYMIC = "patronymic";
    public static final String KEY_AGE = "age";
    public static final String KEY_DOLGNOST = "dolgnost";

    public static final String TABLE_ITEM = "item";
    public static final String KEY_PRODUCT = "product";
    public static final String KEY_ARTICLE = "article";
    public static final String KEY_COUNT = "count";

    public static final String TABLE_AUTHORIZATION = "authorization";
    public static final String KEY_LOGIN = "login";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_QUANTITY = "quantity";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_AUTHORIZATION + "(" + KEY_LOGIN + " text,"
                + KEY_PASSWORD + " text,"+ KEY_QUANTITY + " integer" + ")");

        db.execSQL("create table " + TABLE_EMPLOYEES + "(" + KEY_ID
                + " integer primary key," + KEY_SURNAME + " text," + KEY_NAME + " text,"
                + KEY_PATRONYMIC + " text," + KEY_AGE + " integer," + KEY_DOLGNOST + " text" + ")");

        db.execSQL("create table " + TABLE_ITEM + "(" + KEY_ID
                + " integer primary key," + KEY_PRODUCT + " text," + KEY_ARTICLE + " text,"
                + KEY_COUNT + " integer" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_EMPLOYEES);
        db.execSQL("drop table if exists " + TABLE_ITEM);
        db.execSQL("drop table if exists " + TABLE_AUTHORIZATION);

        onCreate(db);
    }
}