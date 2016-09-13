/*
 * Copyright (c) 2016, Gayan Weerakutti <gayan@linuxdeveloper.space>
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package space.linuxdeveloper.osum.app.forecast;

import space.linuxdeveloper.osum.app.extra.AppDebugger;
import space.linuxdeveloper.osum.app.stat.Stats;
import timber.log.Timber;

/**
 * Use for debugging purposes only
 */

@SuppressWarnings("unused")
public class UsageForecastDebugger extends UsageForecast implements AppDebugger {

    public UsageForecastDebugger(Stats stats) {
        super(stats);
    }

    @Override
    public void debug() {
        Timber.d("peak max: %s", mPeakStat.getMax());
        Timber.d("peak used: %s", mPeakStat.getUsed());
        Timber.d("peak forecast: %s", getForecastUsage(mPeakStat.getUsed()));
        Timber.d("peak diff: %s", getPeakDiff());
        Timber.d("time remaining: %s", TimeCalc.getTimeRemaining());
        Timber.d("dpDt: %s", getDpDt());
        Timber.d("userBadge: %s", getUserBadge());
        Timber.d("forecastMsg: %s", getPeakForecastMsg());
    }
}
