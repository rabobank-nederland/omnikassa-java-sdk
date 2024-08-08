package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.utils;

import org.junit.Test;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.utils.CalendarUtils;

import java.util.Calendar;
import java.util.TimeZone;

import static java.util.concurrent.TimeUnit.HOURS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CalendarUtilsTest {

    private static final String CALENDAR_STRING = "2016-07-28T12:58:50.205+0200";
    private static final String CALENDAR_STRING2 = "2016-07-28T12:58:50.205+02:00";
    private static final int ONE_HOUR_IN_MILLIS = (int) HOURS.toMillis(1);

    @Test
    public void testStringToCalendar() {
        assertCalendar(CalendarUtils.stringToCalendar(CALENDAR_STRING));
    }

    @Test
    public void testStringToCalendar_AlsoSupportsDoubleColons() {
        assertCalendar(CalendarUtils.stringToCalendar(CALENDAR_STRING2));
    }

    private void assertCalendar(Calendar calendar) {
        assertEquals(2016, calendar.get(Calendar.YEAR));
        assertEquals(6, calendar.get(Calendar.MONTH));
        assertEquals(28, calendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(12, calendar.get(Calendar.HOUR_OF_DAY));
        assertEquals(58, calendar.get(Calendar.MINUTE));
        assertEquals(50, calendar.get(Calendar.SECOND));
        assertEquals(205, calendar.get(Calendar.MILLISECOND));
        assertEquals(ONE_HOUR_IN_MILLIS, calendar.get(Calendar.ZONE_OFFSET));
        assertEquals(ONE_HOUR_IN_MILLIS, calendar.get(Calendar.DST_OFFSET));
    }

    @Test
    public void stringToCalendarInValidFormat() {
        assertNull(CalendarUtils.stringToCalendar("07-28T12:58:50.205+0200"));
    }

    @Test
    public void calendarToString_Should_ReturnStringWithRightDate_When_CalenderObjectIsValid() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2016);
        calendar.set(Calendar.MONTH, 6);
        calendar.set(Calendar.DAY_OF_MONTH, 28);
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 58);
        calendar.set(Calendar.SECOND, 50);
        calendar.set(Calendar.MILLISECOND, 205);
        calendar.set(Calendar.ZONE_OFFSET, ONE_HOUR_IN_MILLIS);
        calendar.set(Calendar.DST_OFFSET, ONE_HOUR_IN_MILLIS);
        assertEquals(CALENDAR_STRING, CalendarUtils.calendarToString(calendar));
    }

    @Test
    public void testCalendarToString_Null() {
        assertNull(CalendarUtils.calendarToString(null));
    }

    @Test
    public void stringToCalendarThenCalendarToString_Should_ReturnOriginalString_When_Always() {
        Calendar calendar = CalendarUtils.stringToCalendar(CALENDAR_STRING);
        String calendarString = CalendarUtils.calendarToString(calendar);
        assertEquals(CALENDAR_STRING, calendarString);
    }

}
