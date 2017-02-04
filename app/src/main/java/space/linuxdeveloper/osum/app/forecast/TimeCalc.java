/*
 * Copyright (c) 2017, Gayan Weerakutti <gayan@linuxdeveloper.space>
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package space.linuxdeveloper.osum.app.forecast;


import java.util.Calendar;
import java.util.Date;


class TimeCalc {

    private static Calendar calendar = Calendar.getInstance();

    /**
     * Calculate the time elapsed since the beginning of the month
     * @return the number of milliseconds elapsed since the beginning of the month
     */
    public static long getTimeElapsed() {
        Date rightNow = Calendar.getInstance().getTime();

        setCalendarToBeginning();
        Date startOfMonth = calendar.getTime();

        return rightNow.getTime() - startOfMonth.getTime();
    }

    /**
     * Calculate the remaining time in milliseconds
     * @return the time remaining in milliseconds
     */
    public static long getTimeRemaining() {
        Date rightNow = Calendar.getInstance().getTime();

        setCalendarToEnd();
        Date endOfMonth = calendar.getTime();

        return endOfMonth.getTime() - rightNow.getTime();
    }

    public static int getRemainingDays() {
        long millis = getTimeRemaining();

        // Convert millis to days
        // pre calculated: 24 * 60 * 60 * 1000
        return Math.round(millis / 86400000.0f);
    }

    /**
     * @return the number of milliseconds in the month
     */
    public static long getMillisInMonth() {
        setCalendarToBeginning();
        Date startOfMonth = calendar.getTime();

        setCalendarToEnd();
        Date endOfMonth = calendar.getTime();

        return endOfMonth.getTime() - startOfMonth.getTime();
    }

    private static void setCalendarToBeginning() {
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private static void setCalendarToEnd() {
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
    }
}
