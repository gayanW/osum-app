/*
 * Copyright (c) 2016, Gayan Weerakutti <gayan@linuxdeveloper.space>
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package space.linuxdeveloper.osum.app.stat;

public class StatItemGen {

    private StatsParser statsParser;

    public StatItemGen(StatsParser statsParser) {
        this.statsParser = statsParser;
    }

    public StatItem getPeakStat() {
        float peakRemain = statsParser.getPeakRemain();
        float peakPercent = statsParser.getPeakPercent();
        return StatItem.createFrom(peakRemain, peakPercent);
    }

    public StatItem getTotalStat() {
        float totalRemain = statsParser.getTotalRemain();
        float totalPercent = statsParser.getTotalPercent();
        return StatItem.createFrom(totalRemain, totalPercent);
    }

    public StatItem getExtraStat() {
        float extraRemain = statsParser.getExtraRemain();
        float extraPercent = statsParser.getExtraPercent();
        float extraMax = statsParser.getExtraMax();

        return StatItem.createFrom(extraRemain, extraPercent, extraMax);
    }

    // getter
    public StatsParser getStatsParser() {
        return statsParser;
    }
}
