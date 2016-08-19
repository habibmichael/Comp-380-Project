package com.l2l.androided.mh122354.comp380;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class LoadImageActivity extends AppCompatActivity {

    private final int  SELECT_FILE = 1;
    Button loadImageButton;
    Button selectHarmonyButton;
    ImageView loadedImageView;
    Bitmap bitmap;
    TextView hexText;
    boolean selectedPixel;
    boolean imageLoaded;
    int x,y;
    String hex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_image);

        loadImageButton= (Button)findViewById(R.id.loadImageButton);
        selectHarmonyButton= (Button)findViewById(R.id.selectHarmonyButton);
        loadedImageView = (ImageView)findViewById(R.id.loadedImageView);
        selectedPixel=false;
        imageLoaded=false;
        hexText = (TextView)findViewById(R.id.hexTextView);
        selectHarmonyButton.setEnabled(false);


        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                  SELECT_FILE);

        }
    }



    public void galleryIntent(View view){

        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,"Select File"),SELECT_FILE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults){

        switch(requestCode){
            case SELECT_FILE:
                galleryIntent(loadImageButton);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_FILE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                loadedImageView.setImageBitmap(bitmap);
                imageLoaded=true;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

            x=(int)event.getX();
            y= (int)event.getY();
            selectedPixel=true;

        if(selectedPixel&&imageLoaded) {
            selectHarmonyButton.setEnabled(selectedPixel && imageLoaded);
            pixelToHex(x,y);
        }



        return true;

    }

    public void pixelToHex(int x, int y){

        int red, green,blue=0;


        if(bitmap!=null&&loadedImageView!=null) {
            if(y<bitmap.getHeight()) {
                int pixel = bitmap.getPixel(x, y);
                red = Color.red(pixel);
                blue = Color.blue(pixel);
                green = Color.green(pixel);
                hex =  Integer.toHexString(red) + Integer.toHexString(blue) + Integer.toHexString(green);
                hexText.setText("Hex Value: "+hex);
            }

        }


    }
/*
    public void toHarmony(View view){
        Intent i = new Intent(getBaseContext(),HarmonyActivity.class);
        i.putExtra("HEX",hex);
        startActivity(i);

    }
*/



}
