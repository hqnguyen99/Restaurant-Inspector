package model;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Date utility class that converts date in integer to LocalDate object.
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public enum DateUtil {
    MONTH_DAY {
        public String getDateString(int DateInt) {
            return MONTH_DAY.getDateString(intToDate(DateInt));
        }

        public String getDateString(LocalDate date) {
            return date.format(DateTimeFormatter.ofPattern("LLL dd"));
        }
    },
    MONTH_DAY_YEAR {
        public String getDateString(int DateInt) {
            return MONTH_DAY_YEAR.getDateString(intToDate(DateInt));
        }

        public String getDateString(LocalDate date) {
            return date.format(DateTimeFormatter.ofPattern("LLL dd yyyy"));
        }
    };

    abstract public String getDateString(int dateInt);
    abstract public String getDateString(LocalDate date);

    public static long daysFromNow(int dateInt) {
        return daysFromNow(intToDate(dateInt));
    }

    public static long daysFromNow(@NonNull LocalDate date) {
        return ChronoUnit.DAYS.between(date, LocalDate.now());
    }

    public static LocalDate intToDate(int dateInt) {
        int remainder = dateInt;
        int year = remainder / 10000;
        remainder %= 10000;
        int month = remainder / 100;
        remainder %= 100;
        int dayOfMonth = remainder;

        return LocalDate.of(year, month, dayOfMonth);
    }
}
