package com.l2l.androided.mh122354.comp380;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by mh122354 on 7/29/2016.
 */
public class CameraView extends SurfaceView implements SurfaceHolder.Callback {

        private SurfaceHolder mHolder;
        private Camera mCamera;

    public CameraView (Context context, Camera camera){
        super(context);

        mCamera= camera;

        //Install call back to get notified when surface is created or destroyed
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);



    }
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if(checkCameraHardware(getContext())) {
            try {

                mCamera.setPreviewDisplay(surfaceHolder);
                mCamera.setDisplayOrientation(90);
                mCamera.startPreview();


            } catch (IOException e) {
                Log.d("Camera Error", "Error setting camera preview" + e.getMessage());
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
      /*
        if (mHolder.getSurface()==null){

            //preview surface does not exist
            return;

        }

        try{

            mCamera.stopPreview();

        }catch(Exception e){
            //ignore : tried to stop a non-existent preview
        }

        //set preview size and make any resize,rotate or
        //reformatting changes here

        //start with new settings
        try{
        mCamera.setPreviewDisplay(mHolder);
        mCamera.startPreview();
        } catch(Exception e){
        Log.d("Camera Error", "Error starting preview"+e.getMessage());

        }
        */
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
    //check if device has camera
    private boolean checkCameraHardware(Context context){

        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            return true;
        }
        else {
            return false;
        }
    }

}
