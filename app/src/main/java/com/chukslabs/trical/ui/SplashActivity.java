package com.chukslabs.trical.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.chukslabs.trical.R;
import com.chukslabs.trical.ui.courses.CoursesListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by echuquilin on 13/06/17.
 */
public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.logo_calculator) ImageView mLogoCalculator;

    @OnClick(R.id.logo_calculator)
    public void onLogoclicked(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(SplashActivity.this, R.anim.breath_animation));
    }

    private final int SPLASH_DURATION = 3000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        startApp();
    }

    private void startApp() {
        mLogoCalculator.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_from_center));
        new Handler().postDelayed(splashRunnable, SPLASH_DURATION);
    }

    private Runnable splashRunnable = new Runnable() {
        @Override
        public void run() {
            Intent intent = CoursesListActivity.makeIntent(SplashActivity.this);
            startActivity(intent);
            finish();
        }
    };

}
