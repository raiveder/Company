package com.example.company;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Change extends AppCompatActivity  implements View.OnClickListener {

    Button btnSet, btnBack;
    EditText login, password;

    DBHelper dbHelper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        btnSet = findViewById(R.id.btnSet);
        btnSet.setOnClickListener(this);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        login = findViewById(R.id.login);
        login.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                login.setHint("");
            else
                login.setHint("Логин");
        });

        password = findViewById(R.id.password);
        password.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                password.setHint("");
            else
                password.setHint("Новый пароль");
        });

        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSet:
                boolean logged = true;
                Cursor logCursor = database.query(DBHelper.TABLE_AUTHORIZATION, null, null, null, null, null, null);
                if (logCursor.moveToFirst()) {
                    int loginIndex = logCursor.getColumnIndex(DBHelper.KEY_LOGIN);
                    do {
                        if (login.getText().toString().equals(logCursor.getString(loginIndex))) {
                            logged = false;
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(DBHelper.KEY_PASSWORD, password.getText().toString());
                            database.update(DBHelper.TABLE_AUTHORIZATION, contentValues, DBHelper.KEY_LOGIN + " = '" + login.getText().toString() + "'", null);
                            Toast.makeText(this, "Пароль успешно изменён\nНовый пароль: " + password.getText().toString(), Toast.LENGTH_LONG).show();
                            startActivity(new Intent(this, Users.class));
                            break;
                        }
                    } while (logCursor.moveToNext());
                }
                logCursor.close();
                if (logged) {
                    Toast.makeText(this, "Введённый логин не был найден", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnBack:
                startActivity(new Intent(this, Users.class));
                break;
        }
    }
}