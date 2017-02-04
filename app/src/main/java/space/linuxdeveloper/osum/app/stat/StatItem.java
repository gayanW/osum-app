/*
 * Copyright (c) 2017, Gayan Weerakutti <gayan@linuxdeveloper.space>
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package space.linuxdeveloper.osum.app.stat;

public class StatItem {

    private float remain;
    private int percent;
    private int max;


    private StatItem(float remain, float percent, float max) {
        this.remain = remain;
        this.percent = (int) percent;
        this.max = (int) max;
    }

    public static StatItem createFrom(float remain, float percent) {
        // calculate max
        int max = Math.round(remain / percent * 100);
        max = (max % 2 == 0) ? max : max + 1;

        return new StatItem(remain, percent, max);
    }

    public static StatItem createFrom(float remain, float percent, float max) {
        return new StatItem(remain, percent, max);
    }

    // getters
    public float getRemain() { return remain; }

    public int getPercent() { return percent; }

    public int getMax() { return max; }


    public float getUsed() { return max - remain; }
}
