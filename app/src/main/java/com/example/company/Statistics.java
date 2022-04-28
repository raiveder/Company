package com.example.company;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Statistics extends AppCompatActivity implements View.OnClickListener {

    Button btnBack;

    DBHelper dbHelper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        dbHelper = new DBHelper(this);
        database = dbHelper.getReadableDatabase();

        Cursor cursor = database.query(DBHelper.TABLE_AUTHORIZATION, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int loginIndex = cursor.getColumnIndex(DBHelper.KEY_LOGIN);
            int quantityIndex = cursor.getColumnIndex(DBHelper.KEY_QUANTITY);
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

            TextView outputLoginUp = new TextView(this);
            paramsUp.weight = 3.0f;
            outputLoginUp.setLayoutParams(paramsUp);
            outputLoginUp.setText("Пользователь\n");
            dbOutputRowUp.addView(outputLoginUp);

            TextView outputQuantityUp = new TextView(this);
            paramsUp.weight = 3.0f;
            outputQuantityUp.setLayoutParams(paramsUp);
            outputQuantityUp.setText("Кол-во посещений\n");
            dbOutputRowUp.addView(outputQuantityUp);

            dbOutput.addView(dbOutputRowUp);

            int count = 1;
            do {
                TableRow dbOutputRow = new TableRow(this);
                dbOutputRow.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                TableRow.LayoutParams params = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                TextView outputID = new TextView(this);
                params.weight = 1.0f;
                outputID.setLayoutParams(params);
                outputID.setText(String.valueOf(count));
                dbOutputRow.addView(outputID);

                TextView outputLogin = new TextView(this);
                params.weight = 3.0f;
                outputLogin.setLayoutParams(params);
                outputLogin.setText(cursor.getString(loginIndex));
                dbOutputRow.addView(outputLogin);

                TextView outputQuantity = new TextView(this);
                params.weight = 3.0f;
                outputQuantity.setLayoutParams(params);
                outputQuantity.setText(cursor.getString(quantityIndex));
                dbOutputRow.addView(outputQuantity);

                dbOutput.addView(dbOutputRow);
                count++;
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                startActivity(new Intent(this, Administrators.class));
                break;
        }
    }
}