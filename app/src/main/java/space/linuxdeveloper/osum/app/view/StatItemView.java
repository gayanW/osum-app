/*
 * Copyright (c) 2016, Gayan Weerakutti <gayan@linuxdeveloper.space>
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package space.linuxdeveloper.osum.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import space.linuxdeveloper.osum.app.R;
import space.linuxdeveloper.osum.app.stat.StatItem;

public class StatItemView extends LinearLayout {

    private String statLabel;
    private Drawable statImage;

    public StatItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.stats_item_merge, this, true);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.StatItemView,
                0, 0);

        try {
            statLabel = a.getString(R.styleable.StatItemView_item_label);
            statImage = a.getDrawable(R.styleable.StatItemView_item_image);
        } finally {
            a.recycle();
        }

        applyStyledAttributes();
    }

    private void applyStyledAttributes() {
        if (statLabel != null) {
            TextView itemLabel = (TextView) findViewById(R.id.item_label);
            itemLabel.setText(statLabel);
        }

        if (statImage != null) {
            ImageView itemImage = (ImageView) findViewById(R.id.item_image);
            itemImage.setImageDrawable(statImage);
        }
    }

    public void setStatItem(StatItem statItem) {
        TextView percentageText = (TextView) findViewById(R.id.percentage_text);
        TextView remainText = (TextView) findViewById(R.id.remain_text);
        TextView maxText = (TextView) findViewById(R.id.max_text);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        if (statItem != null) {
            percentageText.setText(String.format("%s%%", String.valueOf(statItem.getPercent())));
            remainText.setText(String.valueOf(statItem.getRemain()));
            maxText.setText(String.valueOf(statItem.getMax()));

            progressBar.setProgress(statItem.getPercent());

        }
        else { // probably EXTRA
            progressBar.setProgress(0);
        }

    }
}
