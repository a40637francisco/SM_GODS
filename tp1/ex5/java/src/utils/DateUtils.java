package utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created on 03/05/2017.
 */
public class DateUtils {


    public static String generateDate(String registryDate, String initialDate, int week) {

        try {
            DateFormat format = new SimpleDateFormat("dd-mm-yyyy");
            Date date = format.parse(initialDate);
            System.out.println(date);

            GregorianCalendar gc = new GregorianCalendar();

            return null;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
