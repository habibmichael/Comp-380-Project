package com.l2l.androided.mh122354.comp380;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class CameraActivity extends AppCompatActivity {

    private Camera camera;
    private CameraView mPreview;
    Button captureButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        captureButton= (Button)findViewById(R.id.button_capture);

        camera = getCameraInstance();

        mPreview = new CameraView(this,camera);
        FrameLayout preview = (FrameLayout)findViewById(R.id.cameraView);
        preview.addView(mPreview);



    }




    public static Camera getCameraInstance(){

        Camera c = null;
        try{

            c = Camera.open();
        }catch(Exception e){

            Log.d("Camera Error:",e.toString());
        }
        return c;
    }


}
