package com.example.company;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class Administrators extends AppCompatActivity implements View.OnClickListener {

    Button btnAddEmp, btnClearEmp, btnShowEmp, btnAddItem, btnClearItem, btnShowItem, btnStat, btnBack;
    EditText dbSurname, dbName, dbPatronymic, dbAge, dbDolgnost, dbProduct, dbArticle, dbCount;

    DBHelper dbHelper;
    SQLiteDatabase database;
    ContentValues contentValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrators);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnAddEmp = findViewById(R.id.btnAddEmp);
        btnAddEmp.setOnClickListener(this);

        btnClearEmp = findViewById(R.id.btnClearEmp);
        btnClearEmp.setOnClickListener(this);

        btnShowEmp = findViewById(R.id.btnShowEmp);
        btnShowEmp.setOnClickListener(this);

        btnAddItem = findViewById(R.id.btnAddItem);
        btnAddItem.setOnClickListener(this);

        btnClearItem = findViewById(R.id.btnClearItem);
        btnClearItem.setOnClickListener(this);

        btnShowItem = findViewById(R.id.btnShowItem);
        btnShowItem.setOnClickListener(this);

        btnStat = findViewById(R.id.btnStat);
        btnStat.setOnClickListener(this);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        dbSurname = findViewById(R.id.surname);
        dbSurname.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                dbSurname.setHint("");
            else
                dbSurname.setHint("Фамилия");
        });
        dbName = findViewById(R.id.name);
        dbName.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                dbName.setHint("");
            else
                dbName.setHint("Имя");
        });
        dbPatronymic = findViewById(R.id.patronymic);
        dbPatronymic.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                dbPatronymic.setHint("");
            else
                dbPatronymic.setHint("Отчество");
        });
        dbAge = findViewById(R.id.age);
        dbAge.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                dbAge.setHint("");
            else
                dbAge.setHint("Возраст");
        });
        dbDolgnost = findViewById(R.id.dolgnost);
        dbDolgnost.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                dbDolgnost.setHint("");
            else
                dbDolgnost.setHint("Должность");
        });

        dbProduct = findViewById(R.id.product);
        dbProduct.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                dbProduct.setHint("");
            else
                dbProduct.setHint("Наименование");
        });

        dbArticle = findViewById(R.id.article);
        dbArticle.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                dbArticle.setHint("");
            else
                dbArticle.setHint("Артикул");
        });
        dbCount = findViewById(R.id.count);
        dbCount.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                dbCount.setHint("");
            else
                dbCount.setHint("Количество");
        });


        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnAddEmp:
                String surname = dbSurname.getText().toString();
                String name = dbName.getText().toString();
                String patronymic = dbPatronymic.getText().toString();
                String age = dbAge.getText().toString();
                String dolgnost = dbDolgnost.getText().toString();

                contentValues = new ContentValues();

                contentValues.put(DBHelper.KEY_SURNAME, surname);
                contentValues.put(DBHelper.KEY_NAME, name);
                contentValues.put(DBHelper.KEY_PATRONYMIC, patronymic);
                contentValues.put(DBHelper.KEY_AGE, age);
                contentValues.put(DBHelper.KEY_DOLGNOST, dolgnost);

                database.insert(DBHelper.TABLE_EMPLOYEES, null, contentValues);

                dbSurname.setText(null);
                dbName.setText(null);
                dbPatronymic.setText(null);
                dbAge.setText(null);
                dbDolgnost.setText(null);

                dbSurname.clearFocus();
                dbName.clearFocus();
                dbPatronymic.clearFocus();
                dbAge.clearFocus();
                dbDolgnost.clearFocus();
                break;

            case R.id.btnClearEmp:

                dbSurname.setText(null);
                dbName.setText(null);
                dbPatronymic.setText(null);
                dbAge.setText(null);
                dbDolgnost.setText(null);

                dbSurname.clearFocus();
                dbName.clearFocus();
                dbPatronymic.clearFocus();
                dbAge.clearFocus();
                dbDolgnost.clearFocus();
                break;

            case R.id.btnShowEmp:
                startActivity(new Intent(this, Employees.class));
                break;

            case R.id.btnAddItem:
                String product = dbProduct.getText().toString();
                String article = dbArticle.getText().toString();
                String count = dbCount.getText().toString();

                contentValues = new ContentValues();

                contentValues.put(DBHelper.KEY_PRODUCT, product);
                contentValues.put(DBHelper.KEY_ARTICLE, article);
                contentValues.put(DBHelper.KEY_COUNT, count);

                database.insert(DBHelper.TABLE_ITEM, null, contentValues);

                dbProduct.setText(null);
                dbArticle.setText(null);
                dbCount.setText(null);

                dbProduct.clearFocus();
                dbArticle.clearFocus();
                dbCount.clearFocus();
                break;

            case R.id.btnClearItem:

                dbProduct.setText(null);
                dbArticle.setText(null);
                dbCount.setText(null);

                dbProduct.clearFocus();
                dbArticle.clearFocus();
                dbCount.clearFocus();
                break;

            case R.id.btnShowItem:
                startActivity(new Intent(this, Items.class));
                break;

            case R.id.btnStat:
                startActivity(new Intent(this, Statistics.class));
                break;

            case R.id.btnBack:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}