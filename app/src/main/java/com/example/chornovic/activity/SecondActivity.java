package com.example.chornovic.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.chornovic.R;

/**
 * Created by Юрий on 26.05.2016.
 */

public class SecondActivity extends Activity implements View.OnClickListener {

    private EditText name;
    private EditText comment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Button exit = (Button) findViewById(R.id.exitButton);
        Button save = (Button) findViewById(R.id.saveButton);
        name = (EditText) findViewById(R.id.nameEditText);
        comment = (EditText) findViewById(R.id.commentsEditText);
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
