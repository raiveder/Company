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

public class Employees extends AppCompatActivity implements View.OnClickListener {

    Button btnClear, btnBack;

    DBHelper dbHelper;
    SQLiteDatabase database;
    ContentValues contentValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        dbHelper = new DBHelper(this);
        database = dbHelper.getReadableDatabase();

        UpdateTable();
    }

    public void UpdateTable() {
        Cursor cursor = database.query(DBHelper.TABLE_EMPLOYEES, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int surnameIndex = cursor.getColumnIndex(DBHelper.KEY_SURNAME);
            int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
            int patronymicIndex = cursor.getColumnIndex(DBHelper.KEY_PATRONYMIC);
            int ageIndex = cursor.getColumnIndex(DBHelper.KEY_AGE);
            int dolgnostIndex = cursor.getColumnIndex(DBHelper.KEY_DOLGNOST);
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

            TextView outputFIOUp = new TextView(this);
            paramsUp.weight = 3.0f;
            outputFIOUp.setLayoutParams(paramsUp);
            outputFIOUp.setText("Фамилия И.О.\n");
            dbOutputRowUp.addView(outputFIOUp);

            TextView outputAgeUp = new TextView(this);
            paramsUp.weight = 3.0f;
            outputAgeUp.setLayoutParams(paramsUp);
            outputAgeUp.setText("Возраст\n");
            dbOutputRowUp.addView(outputAgeUp);

            TextView outputDolgnostUp = new TextView(this);
            paramsUp.weight = 3.0f;
            outputDolgnostUp.setLayoutParams(paramsUp);
            outputDolgnostUp.setText("Должность\n");
            dbOutputRowUp.addView(outputDolgnostUp);

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

                TextView outputFIO = new TextView(this);
                params.weight = 3.0f;
                outputFIO.setLayoutParams(params);
                outputFIO.setText(cursor.getString(surnameIndex) + ". " + cursor.getString(nameIndex).substring(0, 1) + ". " + cursor.getString(patronymicIndex).substring(0, 1) + ".");
                dbOutputRow.addView(outputFIO);

                TextView outputAge = new TextView(this);
                params.weight = 3.0f;
                outputAge.setLayoutParams(params);
                outputAge.setText(cursor.getString(ageIndex));
                dbOutputRow.addView(outputAge);

                TextView outputDolgnost = new TextView(this);
                params.weight = 3.0f;
                outputDolgnost.setLayoutParams(params);
                outputDolgnost.setText(cursor.getString(dolgnostIndex));
                dbOutputRow.addView(outputDolgnost);

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
                database.delete(DBHelper.TABLE_EMPLOYEES, null, null);
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

                database.delete(DBHelper.TABLE_EMPLOYEES, DBHelper.KEY_ID + " = ?", new String[]{String.valueOf((v.getId()))});

                contentValues = new ContentValues();
                Cursor cursorUpdater = database.query(DBHelper.TABLE_EMPLOYEES, null, null, null, null, null, null);
                if (cursorUpdater.moveToFirst()) {
                    int idIndex = cursorUpdater.getColumnIndex(DBHelper.KEY_ID);
                    int surnameIndex = cursorUpdater.getColumnIndex(DBHelper.KEY_SURNAME);
                    int nameIndex = cursorUpdater.getColumnIndex(DBHelper.KEY_NAME);
                    int patronymicIndex = cursorUpdater.getColumnIndex(DBHelper.KEY_PATRONYMIC);
                    int ageIndex = cursorUpdater.getColumnIndex(DBHelper.KEY_AGE);
                    int dolgnostIndex = cursorUpdater.getColumnIndex(DBHelper.KEY_DOLGNOST);
                    int realID = 1;
                    do {
                        if (cursorUpdater.getInt(idIndex) > realID) {
                            contentValues.put(DBHelper.KEY_ID, realID);
                            contentValues.put(DBHelper.KEY_SURNAME, cursorUpdater.getString(surnameIndex));
                            contentValues.put(DBHelper.KEY_NAME, cursorUpdater.getString(nameIndex));
                            contentValues.put(DBHelper.KEY_PATRONYMIC, cursorUpdater.getString(patronymicIndex));
                            contentValues.put(DBHelper.KEY_AGE, cursorUpdater.getString(ageIndex));
                            contentValues.put(DBHelper.KEY_DOLGNOST, cursorUpdater.getString(dolgnostIndex));
                            database.replace(DBHelper.TABLE_EMPLOYEES, null, contentValues);
                        }
                        realID++;
                    } while (cursorUpdater.moveToNext());
                    if (cursorUpdater.moveToLast()) {
                        database.delete(DBHelper.TABLE_EMPLOYEES, DBHelper.KEY_ID + " = ?", new String[]{cursorUpdater.getString(idIndex)});
                    }
                    UpdateTable();
                }
                break;
        }
    }
}