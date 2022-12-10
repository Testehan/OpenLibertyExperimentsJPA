package com.testehan.openliberty.frontend.ui.util;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The TimeMap is use to generate the time fields (day, month, year and hour) for an event form.
 */
@Named
@ApplicationScoped
public class TimeMapUtil implements Serializable {

    private static final long serialVersionUID = 1L;

    private static Map<String, Object> days;// TODO Not sure why these maps are needed or how they are used..in eventForm.xhtml eventBean.getHoursMap()
    private static Map<String, Object> months;
    private static Map<String, Object> years;
    private static Map<String, Object> hours;

    public TimeMapUtil() {
    }

    /**
     * WebAppJSF - generate the hour and date maps
     */
    static {
        hours = new LinkedHashMap<>();
        for (int i = 0; i <= 23; i++) {
            DateFormat inputDateFormat = new SimpleDateFormat("H");
            try {
                Date date = inputDateFormat.parse(String.valueOf(i));
                DateFormat outputDateFormat = new SimpleDateFormat("hh:mm a");
                String output = outputDateFormat.format(date);
                hours.put(output, output);
            } catch (ParseException e) {
            }
        }
        days = new LinkedHashMap<>();
        for (int i = 1; i <= 31; i++) {
            days.put(String.valueOf(i), String.valueOf(i));
        }
        months = new LinkedHashMap<>();
        for (int i = 1; i <= 12; i++) {
            String monthLabel = new DateFormatSymbols().getMonths()[i - 1];
            months.put(monthLabel, monthLabel);
        }
        years = new LinkedHashMap<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear; i <= currentYear + 5; i++) {
            years.put(String.valueOf(i), String.valueOf(i));
        }
    }

    /**
     * This method returns a map of hours
     */
    public static Map<String, Object> getHours() {
        return hours;
    }

    /**
     * This method returns a map of days
     */
    public static Map<String, Object> getDays() {
        return days;
    }

    /**
     * This method returns a map of months
     */
    public static Map<String, Object> getMonths() {
        return months;
    }

    /**
     * This method returns a map of years
     */
    public static Map<String, Object> getYears() {
        return years;
    }

    /**
     * Mapped the stored time string into a user readable Date object.
     */
    public static String getMappedDate(String storedTime) {
        DateFormat inputDateFormat = new SimpleDateFormat("M d yyyy H");
        try {
            Date date = inputDateFormat.parse(storedTime);
            DateFormat outputDateFormat = new SimpleDateFormat("MMMM d, yyyy, hh:mm a");
            String output = outputDateFormat.format(date);
            return output;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
