/*
 * Copyright (c) 2016, Gayan Weerakutti <gayan@linuxdeveloper.space>
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package space.linuxdeveloper.osum.app.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.Toast;

import space.linuxdeveloper.osum.app.AppSavedData;
import space.linuxdeveloper.osum.app.R;


public class AnimatedBackground extends HorizontalScrollView {

    private ImageView mBackgroundImg;
    private TranslateAnimation mAnim;

    private Context mContext;

    public AnimatedBackground(Context context, AttributeSet attrs) {
        super(context, attrs);
        initVars(context);
        inflateLayout(context);
        updateReferences();

        // Apply custom attributes
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AnimatedBackground, 0, 0);
        try {
            Drawable backgroundImg = typedArray.getDrawable(R.styleable.AnimatedBackground_backgroundImage);
            mBackgroundImg.setImageDrawable(backgroundImg);
        } finally {
            typedArray.recycle();
        }

        setHorizontalScrollBarEnabled(false);
    }

    private void initVars(Context context) {
        mContext = context;

    }

    private void updateReferences() {
        mBackgroundImg = (ImageView) findViewById(R.id.background_img);
    }

    private void inflateLayout(Context context) {
        // Inflate layout
        LayoutInflater inflater = (LayoutInflater)  context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.animated_background, this);
    }

    /**
     * Calling getWidth() and getHeight() on ImageView from onCreate() in activity, won't work.
     * We need to wait for activity window to attached and then call getWidth() and getHeight() on ImageView.
     */
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        mAnim = newTranslateAnimation(getScreenWidth());

        if (AppSavedData.getInstance(mContext).isAnimationOn())
            mBackgroundImg.startAnimation(mAnim);
    }

    private int getScreenWidth() {
        // Initialize DisplayMetrics object
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        return displayMetrics.widthPixels;
    }

    private TranslateAnimation newTranslateAnimation(int screenWidth) {
        float imageWidth = mBackgroundImg.getWidth();

        TranslateAnimation translateAnimation = new TranslateAnimation(0f, -imageWidth + screenWidth, 0f, 0f);
        translateAnimation.setDuration(50000);
        translateAnimation.setRepeatMode(Animation.REVERSE);
        translateAnimation.setRepeatCount(Animation.INFINITE);

        return  translateAnimation;
    }

    public void toggleAnimation() {
        final boolean isAnimationOn = AppSavedData.getInstance(mContext).isAnimationOn();
        if (isAnimationOn) {
            mBackgroundImg.clearAnimation();
            Toast.makeText(getContext(), R.string.animation_disabled, Toast.LENGTH_LONG).show();
        }
        else {
            mBackgroundImg.startAnimation(mAnim);
            Toast.makeText(getContext(), R.string.animation_enabled, Toast.LENGTH_LONG).show();
        }
        AppSavedData.getInstance(mContext).setAnimationOn(!isAnimationOn);
    }
}
