package com.l2l.androided.mh122354.comp380;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CameraActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    @InjectView(R.id.surfaceView)
    SurfaceView surfaceView;
    @InjectView(R.id.take_photo)
    FloatingActionButton take_photo;
     SurfaceHolder surfaceHolder;
    Camera.PictureCallback jpegCallback;
    Camera.ShutterCallback shutterCallback;
    private Camera camera;
    Bitmap bitmap;
    @InjectView(R.id.photoImageView)
    ImageView photoImageview;
    boolean isPictureTaken;
    boolean imageLoaded;
    ImageView library;
    TextView hexText;
    int x,y;
    Button selectHarmonyButton;
    boolean selectedPixel;


    private static final int CAMERA_CODE = 1;
    private static final int SELECT_FILE= 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.inject(this);
        surfaceHolder = surfaceView.getHolder();
        isPictureTaken= false;
        imageLoaded=false;
        selectedPixel=false;
        library=(ImageView)findViewById(R.id.library_button);
        hexText=(TextView)findViewById(R.id.hexTextView);
        selectHarmonyButton= (Button)findViewById(R.id.selectHarmonyButton);

        //add surface holder call back to be notified when
        //underlying surface is created or destroyed
        surfaceHolder.addCallback(this);
        //deprecated but required for some versions
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        take_photo.setOnClickListener(new FloatingActionButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isPictureTaken)
                cameraImage();
            }
        });



            jpegCallback = new Camera.PictureCallback() {
                @Override
                public void onPictureTaken(byte[] bytes, Camera camera) {
                    bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    surfaceView.setVisibility(View.INVISIBLE);
                    photoImageview.setImageBitmap(bitmap);
                    isPictureTaken = true;


                }
            /*

                FileOutputStream fileOutputStream = null;
                File file_image= getDirc();
                if(!file_image.exists()&&!file_image.mkdirs()){
                    Toast.makeText(getBaseContext(),"Can't create directory to save image",Toast.LENGTH_SHORT).show();
                    return;
                }
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyymmddhhmmss");
                String date = simpleDateFormat.format(new Date());
                String photoFile = "Discovery"+date+".jpg";
                String file_name = file_image.getAbsolutePath()+"/"+photoFile;
                File picfile = new File(file_name);
                try{
                    fileOutputStream= new FileOutputStream(picfile);
                    fileOutputStream.write(bytes);
                    fileOutputStream.close();
                }catch(FileNotFoundException e){

                }catch(IOException e){}

                finally{

                }
                Toast.makeText(getBaseContext(),"Picture Saved",Toast.LENGTH_SHORT).show();
                */


            };



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((getBaseContext().checkSelfPermission(Manifest.permission.CAMERA)) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_CODE);
            }
        }


    }



    public void refreshCamera(){
        if(surfaceHolder.getSurface()==null)
            return;
        try{
            camera.stopPreview();
        }catch(Exception e ){}
        try{
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        }catch(Exception e){}
    }




    public void cameraImage(){
        camera.takePicture(null,null,jpegCallback);
        isPictureTaken=true;

    }

    public void retakePicture(View view){
        if(isPictureTaken){
            Log.d("Button Clicked","X button Clicked");
            surfaceView.setVisibility(View.VISIBLE);
            photoImageview.setImageBitmap(null);
            isPictureTaken=false;
            refreshCamera();
        }
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


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults){


        switch(requestCode){
            //choose appropriate action based on which feature asked for permission
            case CAMERA_CODE:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                            configureCamera();

                    return;
                }
                break;
            case SELECT_FILE:
                galleryIntent(library);
                break;

        }

    }
    public void configureCamera(){
        //open camera
        try{
            camera = Camera.open();
        }catch(RuntimeException ex){

        }
        Camera.Parameters parameters;
        parameters = camera.getParameters();
        parameters.setPreviewFrameRate(20);
        parameters.setPreviewSize(352,288);
        camera.setParameters(parameters);
        camera.setDisplayOrientation(90);

        //Try to set the preview
        try{
            //Tell camera where to draw preview
            camera.setPreviewDisplay(surfaceHolder);

        }catch(Exception e){



        }
    }



    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
            configureCamera();

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
            refreshCamera();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        camera.stopPreview();
        camera.release();
        camera=null;

    }

    public void galleryIntent(View view){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,"Select File"),SELECT_FILE);
        isPictureTaken=true;

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_FILE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);


                photoImageview.setImageBitmap(bitmap);
                surfaceView.setVisibility(View.INVISIBLE);
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

        if(selectedPixel&&(imageLoaded||isPictureTaken)) {
            selectHarmonyButton.setEnabled(selectedPixel && imageLoaded);
            pixelToHex(x,y);
        }



        return true;

    }

    public void pixelToHex(int x, int y){

        int red, green,blue=0;


        if(bitmap!=null&&photoImageview!=null) {
            if(y<bitmap.getHeight()) {
                int pixel = bitmap.getPixel(x, y);
                red = Color.red(pixel);
                blue = Color.blue(pixel);
                green = Color.green(pixel);
                hexText.setText("Hex Value: " + Integer.toHexString(red) + Integer.toHexString(blue) + Integer.toHexString(green));
            }

        }


    }

}
