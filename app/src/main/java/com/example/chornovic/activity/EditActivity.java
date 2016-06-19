package com.example.chornovic.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.chornovic.R;

public class EditActivity extends Activity implements View.OnClickListener {

    private EditText name;
    private EditText comment;
    private String names, comments, times;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent = getIntent();
        names = intent.getStringExtra("names");
        comments = intent.getStringExtra("comments");
        times = intent.getStringExtra("times");
        Button save = (Button) findViewById(R.id.saveButton);
        name = (EditText) findViewById(R.id.nameEditText);
        comment = (EditText) findViewById(R.id.commentsEditText);
        name.setText(names);
        comment.setText(comments);
        Button exit = (Button) findViewById(R.id.exitButton);
        save.setOnClickListener(this);
        exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveButton: {
                //data transfer between activities
                Intent intent = new Intent();
                intent.putExtra("name", name.getText().toString());
                intent.putExtra("comment", comment.getText().toString());
                intent.putExtra("time", times);
                setResult(RESULT_OK, intent);
                finish();
                break;
            }
            case R.id.exitButton: {
                finish();
                break;
            }
        }

    }

}
