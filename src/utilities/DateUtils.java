/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.WeekFields;
import java.util.Locale;

import static java.time.temporal.ChronoField.DAY_OF_WEEK;

/**
 * Provides useful methods for manipulating date objects.
 *
 * @author mab90
 */
public class DateUtils {

    /**
     * The default {@link ZoneId} for the {@link System}.
     */
    public static final ZoneId DEFAULT_ZONE_ID = ZoneId.systemDefault();

    /**
     * The UTC {@link ZoneId}.
     */
    public static final ZoneId UTC_ZONE_ID = ZoneId.of("UTC");

    /**
     * The default {@link Locale} for the {@link System}.
     */
    public static final Locale DEFAULT_LOCALE = Locale.getDefault();

    /**
     * The default {@link Locale} for the {@link System}.
     */
    public static final WeekFields WEEK_FIELDS = WeekFields.of(Locale.getDefault());

    /**
     * Converts a {@link LocalDate} into a {@link ZonedDateTime}. The time is
     * defaulted to start of the day.
     *
     * @param date The date to convert.
     * @return The zoned date time.
     */
    public static final ZonedDateTime toZonedDateTime(LocalDate date) {
        return date.atStartOfDay(DEFAULT_ZONE_ID);
    }

    /**
     * Converts a {@link LocalDate} and a {@link LocalTime} into a
     * {@link ZonedDateTime}.
     *
     * @param date The date to convert.
     * @param time The time to convert.
     * @return The zoned date time.
     */
    public static final ZonedDateTime toZonedDateTime(LocalDate date, LocalTime time) {
        return toZonedDateTime(LocalDateTime.of(date, time));
    }

    /**
     * Converts a {@link LocalDateTime} into a {@link ZonedDateTime}.
     *
     * @param date The date to convert.
     * @return The zoned date time.
     */
    public static final ZonedDateTime toZonedDateTime(LocalDateTime date) {
        return date.atZone(DEFAULT_ZONE_ID);
    }

    /**
     * Returns the current {@link LocalDate} based upon the default
     * {@link ZoneId} for the {@link System}.
     *
     * @return The current date.
     */
    public static final LocalDate getToday() {
        return LocalDate.now(DEFAULT_ZONE_ID);
    }

    /**
     * Returns the current {@link LocalTime} based upon the default
     * {@link ZoneId} for the {@link System}.
     *
     * @return The current time.
     */
    public static final LocalTime getCurrentTime() {
        return LocalTime.now(DEFAULT_ZONE_ID);
    }

    /**
     * Gets the first date of the week for the specified date. The first day of
     * the week is determined based upon the default {@link Locale}.
     *
     * @param date The date to evaluate.
     * @return The first date of the week.
     */
    public static final LocalDate getFirstDayOfWeek(LocalDate date) {
        LocalDate adjustedDate = date.with(DAY_OF_WEEK, WEEK_FIELDS.getFirstDayOfWeek().getValue());

        // If adjusted forward in time, subtract one week from the adjusted date
        if (adjustedDate.isAfter(date)) {
            adjustedDate = adjustedDate.minusWeeks(1);
        }

        return adjustedDate;
    }

    /**
     * Gets the last date of the week for the specified date. The last day of
     * the week is determined based upon the default {@link Locale}.
     *
     * @param date The date to evaluate.
     * @return The last date of the week.
     */
    public static final LocalDate getLastDayOfWeek(LocalDate date) {
        return getFirstDayOfWeek(date).plusDays(6);
    }

    /**
     * Gets the first date of the month for the specified date.
     *
     * @param date The date to evaluate.
     * @return The first date of the month.
     */
    public static final LocalDate getFirstDayOfMonth(LocalDate date) {
        YearMonth yearMonth = YearMonth.of(date.getYear(), date.getMonth());
        return yearMonth.atDay(1);
    }

    /**
     * Gets the last date of the month for the specified date.
     *
     * @param date The date to evaluate..
     * @return The last date of the month.
     */
    public static final LocalDate getLastDayOfMonth(LocalDate date) {
        YearMonth yearMonth = YearMonth.of(date.getYear(), date.getMonth());
        return yearMonth.atEndOfMonth();
    }

    /**
     * Gets the first [@link DayOfWeek} for the specified date.
     *
     * @param date The date to compare.
     * @param firstDayOfWeek The day that is considered the first day of the
     * week.
     * @return The first day of the week.
     */
    public static final LocalDate toFirstDayOfWeek(LocalDate date, DayOfWeek firstDayOfWeek) {
        LocalDate newDate = date.with(ChronoField.DAY_OF_WEEK, firstDayOfWeek.getValue());

        if (newDate.isAfter(date)) {
            newDate = newDate.minusWeeks(1);
        }

        return newDate;
    }

}
