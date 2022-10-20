package ro.bithat.dms.service;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public final class L10nUtil {

    public static Date getBucharestDateNow() {
        LocalDateTime ldt = new Date().toInstant().atZone(TimeZone.getTimeZone("Europe/Bucharest").toZoneId()).toLocalDateTime();
        Calendar date = new GregorianCalendar(ldt.getYear(), ldt.getMonthValue() - 1, ldt.getDayOfMonth(), ldt.getHour(), ldt.getMinute(), ldt.getSecond());
        return date.getTime();
    }

}
