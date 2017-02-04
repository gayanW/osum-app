/*
 * Copyright (c) 2017, Gayan Weerakutti <gayan@linuxdeveloper.space>
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package space.linuxdeveloper.osum.app.forecast;

import android.annotation.SuppressLint;

import space.linuxdeveloper.osum.app.stat.StatItem;
import space.linuxdeveloper.osum.app.stat.Stats;

public class UsageForecast {

    private StatItem mPeakStat;
    private StatItem mTotalStat;
    private StatItem mExtraStat;

    // User Badges
    private static final int SAVER = 0;
    private static final int SPENDER = 1;
    private static final int GAMBLER = 2;

    public UsageForecast(Stats stats) {
        mPeakStat = stats.getPeakStat();
        mTotalStat = stats.getTotalStat();
        mExtraStat = stats.getExtraStat();
    }

    /** Forecast usage at the end of the month */
    private float getForecastUsage(float currentUsage) {
        return currentUsage / TimeCalc.getTimeElapsed() * TimeCalc.getMillisInMonth();
    }

    public String getPeakForecastMsg() {
        float remainPeak = mPeakStat.getRemain();
        int remainingDays = TimeCalc.getRemainingDays();

        // If has extra
        if (mExtraStat != null)
            return " You have " + mExtraStat.getRemain() + " MB Extra remaining out of " + mExtraStat.getMax()
                    + " GB.";

        String forecastMsg = "";
        switch (getUserBadge()) {
            case GAMBLER:
                // If peak volume unusable
                if (mTotalStat.getRemain() == 0.0f && remainPeak > 0) {
                    forecastMsg = "You have unusable " + remainPeak +  " GB, because it has been utilized for upload and off-peak download.";
                }
                else {
                    forecastMsg = "Steady! You have only " + remainPeak + " GB remaining over "
                            + remainingDays + " days. Stay on target!";
                }
                break;
            case SPENDER:
                forecastMsg = "Spender! You have " + remainPeak + " GB remaining over "
                        + remainingDays + " days.";
                break;
            case SAVER:
                forecastMsg = "Saver! You have " + remainPeak + " GB remaining over "
                        + remainingDays + " days.";
                break;
        }

        return forecastMsg;
    }

    /**
     * Difference between (peak) forecast usage and the max
     */
    private float getPeakDiff() {
        float usedPeak = mPeakStat.getUsed();
        float forecastPeakUsage = getForecastUsage(usedPeak);
        float maxPeakVol = mPeakStat.getMax();

        return maxPeakVol - forecastPeakUsage;
    }

    /**
     * Special value used for forecasting
     * @return peakDiff / timeRemaining
     */
    private float getDpDt() {
        return getPeakDiff() / TimeCalc.getTimeRemaining();
    }

    /**
     * Convert dpDt value to a string
     * @return a floating string with one decimal places
     */
    @SuppressLint("DefaultLocale")
    public String getDpDtAsText() {
        String dpDtStr = "";

        float dpDt = getDpDt();
        if (dpDt > 0)
            dpDtStr = "+";

        return dpDtStr + ((dpDt != 0) ?
                String.format("%.1f", getDpDt() * 1.0e9) :
                "..N/A");
    }

    public int getUserBadge() {
        float dpDt = getDpDt();
        int badge;
        if (dpDt < -0.2e-9 || dpDt == 0f) {
            badge = GAMBLER;
        }
        else if (dpDt < 0.2e-9) {
            badge = SPENDER;
        }
        else {
            badge = SAVER;
        }

        return badge;
    }
}
