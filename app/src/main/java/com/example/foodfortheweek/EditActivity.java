package com.example.foodfortheweek;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
    }


    public void saveAndReturn(View view) {
        ArrayList<String> keywordList = new ArrayList<>();

        EditText editTextMon = findViewById(R.id.editTextMon);
        keywordList.add(editTextMon.getText().toString());

        EditText editTextTue = findViewById(R.id.editTextTue);
        keywordList.add(editTextTue.getText().toString());

        EditText editTextWen = findViewById(R.id.editTextWen);
        keywordList.add(editTextWen.getText().toString());

        EditText editTextThu = findViewById(R.id.editTextThu);
        keywordList.add(editTextThu.getText().toString());

        EditText editTextFri = findViewById(R.id.editTextFri);
        keywordList.add(editTextFri.getText().toString());


        Intent returnMain = new Intent(this,MainActivity.class);
        returnMain.putExtra("keywords", keywordList);
        startActivity(returnMain);
    }
}