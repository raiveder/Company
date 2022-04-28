package com.example.company;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Users extends AppCompatActivity implements View.OnClickListener {

    Button btnBack, btnChange;

    DBHelper dbHelper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        btnChange = findViewById(R.id.btnChange);
        btnChange.setOnClickListener(this);

        dbHelper = new DBHelper(this);
        database = dbHelper.getReadableDatabase();

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
            outputArticleUp.setText("Артикул\n");
            dbOutputRowUp.addView(outputArticleUp);

            TextView outputCountUp = new TextView(this);
            paramsUp.weight = 3.0f;
            outputCountUp.setLayoutParams(paramsUp);
            outputCountUp.setText("Количество\n");
            dbOutputRowUp.addView(outputCountUp);

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

                dbOutput.addView(dbOutputRow);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.btnChange:
                startActivity(new Intent(this, Change.class));
                break;
        }
    }
}