package com.shidduckbook.appintro;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntro2Fragment;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.shidduckbook.R;

    public class AppIntroActivity extends AppIntro2 {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setFadeAnimation();
        //        setContentView(R.layout.activity_app_intro);
        int BackColor = getResources().getColor(R.color.color_Blue);

        addSlide(AppIntro2Fragment.newInstance("Intro Page 1", "Here is the description of Application", R.drawable.app_logo,BackColor));
        addSlide(AppIntro2Fragment.newInstance("Intro Page 2", "Here is the description of Application Page 2", R.drawable.app_logo, BackColor));

        setBarColor(Color.parseColor("#3F51B5"));
//      setSeparatorColor(Color.parseColor("#2196F3"));
        // Hide Skip/Done button.

        setProgressButtonEnabled(true);
        showSkipButton(false);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.

        setVibrate(true);
        setVibrateIntensity(30);
    }


    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}
