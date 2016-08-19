package com.l2l.androided.mh122354.comp380;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectFuncActivity extends AppCompatActivity {

    Button insertButton;
    Button loadButton;
    Button cameraButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_func);

        insertButton=(Button)findViewById(R.id.insertButton);
        loadButton= (Button)findViewById(R.id.loadPhoto);
        cameraButton= (Button)findViewById(R.id.cameraButton);




    }
    public void load(View view){

        Intent i = new Intent(getBaseContext(),LoadImageActivity.class);
        startActivity(i);
    }
    public void picture(View view){

        Intent i = new Intent(getBaseContext(),CameraActivity.class);
        startActivity(i);
    }

    public void insert(View view){

       // Intent i = new Intent(getBaseContext(),InsertValues.class);
     //   startActivity(i);
    }
}