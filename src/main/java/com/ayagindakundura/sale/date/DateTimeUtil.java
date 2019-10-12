package com.ayagindakundura.sale.date;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {

    private static boolean isFrozen = false;

    private static Date currentDate;

    public static synchronized void freezeDate() {
        currentDate = new Date();
        isFrozen = true;
    }

    public static Date now() {
        if (isFrozen) {
            return currentDate;
        }
        return new Date();
    }

    public static Date getTruncatedDate() {
        return DateUtils.truncate(now(), Calendar.DATE);
    }

    public static void unFreezeDate() {
        isFrozen = false;
    }
}
