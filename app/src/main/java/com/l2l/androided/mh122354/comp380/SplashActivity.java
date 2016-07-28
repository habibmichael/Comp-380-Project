package com.l2l.androided.mh122354.comp380;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    ImageView splashImage;
    Animation fadeInAnimation;
    Animation fadeOutAnimation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Initialize Splash ImageView & Animations
        splashImage = (ImageView)findViewById(R.id.splashImageView);
        fadeInAnimation = AnimationUtils.loadAnimation(getBaseContext(),R.anim.fade_in);
        fadeOutAnimation= AnimationUtils.loadAnimation(getBaseContext(),R.anim.fade_out);

        //Start Animation on Image View
        splashImage.startAnimation(fadeInAnimation);

        //Listen for End of Animation
        fadeInAnimation.setAnimationListener(animationListener);


    }

    //Anonymous Inner Class Initializing animation Listener
    private final Animation.AnimationListener animationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
                //When fade in ends start fade out animation & transition to log in screen
                splashImage.startAnimation(fadeOutAnimation);
                Intent i = new Intent( getBaseContext(),LoginActivity.class);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
