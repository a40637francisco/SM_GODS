package generators.betters;

import utils.RandomUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created on 03/05/2017.
 */
public class DateGenerator {

    private String min = "01-01-2017";
    private String max = "31-12-2017";

    public DateGenerator setMin(String min) {
        this.min = min;
        return this;
    }

    public DateGenerator setMax(String min) {
        this.max = min;
        return this;
    }

    public String get() {
        GregorianCalendar gc = new GregorianCalendar();
        int year = randBetween(getYear(min), getYear(max));
        gc.set(Calendar.YEAR, year);
        int dayOfYear = randBetween(1, gc.getActualMaximum(Calendar.DAY_OF_YEAR));
        gc.set(Calendar.DAY_OF_YEAR, dayOfYear);

        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String ret = formatter.format(gc.getTime());

        return ret;
    }

    public static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }

    private int getYear(String date) {
        String[] splited = date.split("-");
        return Integer.parseInt(splited[2]);
    }





    public static String calculateDate(String checkDate, String initialDate, int week) {

        LocalDate toCheck = LocalDate.parse(checkDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        LocalDate init = LocalDate.parse(initialDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        if(toCheck.isAfter(init)) return null;

        init = init.plusWeeks(week-1);

        DayOfWeek doW = init.getDayOfWeek();

        LocalDate min = init.minusDays(doW.getValue()- 1);
        int rnd = RandomUtils.randomInt(6);
        LocalDate date = min.plusDays(rnd);

        return date.toString();


        /*
        try {
            DateFormat format = new SimpleDateFormat("dd-mm-yyyy");

            Date date = format.parse(initialDate);

            GregorianCalendar gc = new GregorianCalendar();

            gc.add(Calendar.DATE, );

            return format.format(gc.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        } */
    }


}
