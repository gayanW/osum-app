/*
 * Copyright (c) 2016, Gayan Weerakutti <gayan@linuxdeveloper.space>
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package space.linuxdeveloper.osum.app.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

public class StatItemsScrollView extends ScrollView  {

    private boolean mScrollable = false;

    public StatItemsScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomLayoutParms();
    }

    private void setCustomLayoutParms() {
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }

                setCustomHeight();
            }

            private void setCustomHeight() {
                ViewGroup.LayoutParams lp = getLayoutParams();
                lp.height = getHeight() / 3 * 2; // 3:statItems, 2:statItems to display
                setLayoutParams(lp);
            }
        });
    }

    // Enable/disable scrolling
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return mScrollable && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mScrollable && super.onInterceptTouchEvent(ev);
    }
}
