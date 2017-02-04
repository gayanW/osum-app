/*
 * Copyright (c) 2017, Gayan Weerakutti <gayan@linuxdeveloper.space>
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package space.linuxdeveloper.osum.app.stat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Observable;

public class StatsParser extends Observable {

    private Document jDoc;

    public StatsParser(String html) {
        setSource(html);
    }

    // peak stats
    public float getPeakRemain() { return getValue(Path.PEAK_VOL); }

    public float getPeakPercent() { return getPercentage(Path.PEAK_PERCENT); }

    // total stats
    public float getTotalRemain() { return getValue(Path.TOTAL_VOL); }

    public float getTotalPercent() { return getPercentage(Path.TOTAL_PERCENT); }


    // extra stats
    public float getExtraRemain() { return getValue(Path.EXTRA_VOL); }

    public float getExtraPercent() { return getExtraPercentage(Path.EXTRA_PERCENT); }

    public float getExtraMax() { return getValue(Path.EXTRA_GB); }


    private float getPercentage(String path) {
        String value = jDoc.select(path).attr("style").split(";")[2].substring(9).replace("px", "");
        return Integer.parseInt(value) / 172f * 100;
    }

    private float getValue(String path) {
        // values can be of form 7278 -64401 4096
        // for example when multiple extra subscriptions are present
        String values = jDoc.select(path).text().replace("GB", "").replace("%", "");
        float total = 0;
        for (String value : values.split(" ")) {
            float v = Float.parseFloat(value);
            total += (v > 0) ? v : 0;
        }
        return total;
    }

    /**
     * When multiple extra subscriptions are present
     * values can be of form {7, -209, 100}
     */
    private float getExtraPercentage(String path) {
        String[] values = jDoc.select(path).text().replace("%", "").split(" ");
        int percentage = 0;
        for (String value : values) {
            float v = Integer.parseInt(value);
            percentage += (v > 0) ? v : 0;
        }
        return percentage / values.length;
    }

    public boolean hasExtra() {
        return jDoc.select(Path.EXTRA_GB).size() != 0;
    }

    public void setSource(String html) {
        this.jDoc = Jsoup.parse(html);
        setChanged();
    }

    private class Path {
        private static final String PEAK_VOL = "div.i_detail_peak p span";
        private static final String TOTAL_VOL = "div.i_detail p span";
        private static final String PEAK_PERCENT     = "div.bfill_peak";
        private static final String TOTAL_PERCENT = "div.bfill";

        private static final String EXTRA_VOL = ".vodvmsg > p:nth-child(2) > span:nth-child(5)";
        private static final String EXTRA_GB = ".vodvmsg > h5:nth-child(1) > span:nth-child(1)";
        private static final String EXTRA_PERCENT = ".label1";
    }
}

