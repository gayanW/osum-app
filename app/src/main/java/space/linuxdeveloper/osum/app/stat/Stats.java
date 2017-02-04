/*
 * Copyright (c) 2017, Gayan Weerakutti <gayan@linuxdeveloper.space>
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package space.linuxdeveloper.osum.app.stat;


import android.support.annotation.Nullable;

import java.util.Observable;
import java.util.Observer;

public class Stats implements Observer {
    private StatItem mPeakStat;
    private StatItem mTotalStat;
    private StatItem mExtraStat;

    private  StatItemGen mStatItemGen;

    public Stats(StatItemGen statItemGen) {
        mStatItemGen = statItemGen;
        updateStats();
    }

    private void updateStats() {
        mPeakStat = mStatItemGen.getPeakStat();
        mTotalStat = mStatItemGen.getTotalStat();

        if (mStatItemGen.getStatsParser().hasExtra()) {
            mExtraStat = mStatItemGen.getExtraStat();
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        updateStats();
    }

    // getters
    public StatItem getPeakStat() { return mPeakStat; }

    public StatItem getTotalStat() { return mTotalStat; }

    @Nullable
    public StatItem getExtraStat() { return mExtraStat; }


}
