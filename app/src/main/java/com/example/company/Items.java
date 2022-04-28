package com.example.company;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Items extends AppCompatActivity implements View.OnClickListener {

    Button btnClear, btnBack;

    DBHelper dbHelper;
    SQLiteDatabase database;
    ContentValues contentValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        dbHelper = new DBHelper(this);
        database = dbHelper.getReadableDatabase();

        UpdateTable();
    }

    public void UpdateTable() {
        Cursor cursor = database.query(DBHelper.TABLE_ITEM, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int productIndex = cursor.getColumnIndex(DBHelper.KEY_PRODUCT);
            int articleIndex = cursor.getColumnIndex(DBHelper.KEY_ARTICLE);
            int countIndex = cursor.getColumnIndex(DBHelper.KEY_COUNT);
            TableLayout dbOutput = findViewById(R.id.dbOutput);
            dbOutput.removeAllViews();

            TableRow dbOutputRowUp = new TableRow(this);
            dbOutputRowUp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            TableRow.LayoutParams paramsUp = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            TextView outputIDUp = new TextView(this);
            paramsUp.weight = 1.0f;
            outputIDUp.setLayoutParams(paramsUp);
            outputIDUp.setText("№\n");
            dbOutputRowUp.addView(outputIDUp);

            TextView outputProductUp = new TextView(this);
            paramsUp.weight = 3.0f;
            outputProductUp.setLayoutParams(paramsUp);
            outputProductUp.setText("Наименование\n");
            dbOutputRowUp.addView(outputProductUp);

            TextView outputArticleUp = new TextView(this);
            paramsUp.weight = 3.0f;
            outputArticleUp.setLayoutParams(paramsUp);
            outputArticleUp.setText("Арт.\n");
            dbOutputRowUp.addView(outputArticleUp);

            TextView outputCountUp = new TextView(this);
            paramsUp.weight = 3.0f;
            outputCountUp.setLayoutParams(paramsUp);
            outputCountUp.setText("Кол-во\n");
            dbOutputRowUp.addView(outputCountUp);

            TextView outputActUp = new TextView(this);
            paramsUp.weight = 1.0f;
            outputActUp.setLayoutParams(paramsUp);
            outputActUp.setText("\n");
            dbOutputRowUp.addView(outputActUp);

            dbOutput.addView(dbOutputRowUp);

            do {
                TableRow dbOutputRow = new TableRow(this);
                dbOutputRow.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                TableRow.LayoutParams params = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                TextView outputID = new TextView(this);
                params.weight = 1.0f;
                outputID.setLayoutParams(params);
                outputID.setText(cursor.getString(idIndex));
                dbOutputRow.addView(outputID);

                TextView outputProduct = new TextView(this);
                params.weight = 3.0f;
                outputProduct.setLayoutParams(params);
                outputProduct.setText(cursor.getString(productIndex));
                dbOutputRow.addView(outputProduct);

                TextView outputArticle = new TextView(this);
                params.weight = 3.0f;
                outputArticle.setLayoutParams(params);
                outputArticle.setText(cursor.getString(articleIndex));
                dbOutputRow.addView(outputArticle);

                TextView outputCount = new TextView(this);
                params.weight = 3.0f;
                outputCount.setLayoutParams(params);
                outputCount.setText(cursor.getString(countIndex));
                dbOutputRow.addView(outputCount);

                Button deleteBtn = new Button(this);
                deleteBtn.setOnClickListener(this);
                params.weight = 1.0f;
                deleteBtn.setLayoutParams(params);
                deleteBtn.setText("Удалить");
                deleteBtn.setId(cursor.getInt(idIndex));
                dbOutputRow.addView(deleteBtn);

                dbOutput.addView(dbOutputRow);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnClear:
                database.delete(DBHelper.TABLE_ITEM, null, null);
                TableLayout dbOutput = findViewById(R.id.dbOutput);
                dbOutput.removeAllViews();
                UpdateTable();
                break;

            case R.id.btnBack:
                startActivity(new Intent(this, Administrators.class));
                break;

            default:
                View outputDBRow = (View) v.getParent();
                ViewGroup outputDB = (ViewGroup) outputDBRow.getParent();
                outputDB.removeView(outputDBRow);
                outputDB.invalidate();

                database.delete(DBHelper.TABLE_ITEM, DBHelper.KEY_ID + " = ?", new String[]{String.valueOf((v.getId()))});

                contentValues = new ContentValues();
                Cursor cursorUpdater = database.query(DBHelper.TABLE_ITEM, null, null, null, null, null, null);
                if (cursorUpdater.moveToFirst()) {
                    int idIndex = cursorUpdater.getColumnIndex(DBHelper.KEY_ID);
                    int productIndex = cursorUpdater.getColumnIndex(DBHelper.KEY_PRODUCT);
                    int articleIndex = cursorUpdater.getColumnIndex(DBHelper.KEY_ARTICLE);
                    int countIndex = cursorUpdater.getColumnIndex(DBHelper.KEY_COUNT);
                    int realID = 1;
                    do {
                        if (cursorUpdater.getInt(idIndex) > realID) {
                            contentValues.put(DBHelper.KEY_ID, realID);
                            contentValues.put(DBHelper.KEY_PRODUCT, cursorUpdater.getString(productIndex));
                            contentValues.put(DBHelper.KEY_ARTICLE, cursorUpdater.getString(articleIndex));
                            contentValues.put(DBHelper.KEY_COUNT, cursorUpdater.getString(countIndex));
                            database.replace(DBHelper.TABLE_ITEM, null, contentValues);
                        }
                        realID++;
                    } while (cursorUpdater.moveToNext());
                    if (cursorUpdater.moveToLast()) {
                        database.delete(DBHelper.TABLE_ITEM, DBHelper.KEY_ID + " = ?", new String[]{cursorUpdater.getString(idIndex)});
                    }
                    UpdateTable();
                }
                break;
        }
    }
}