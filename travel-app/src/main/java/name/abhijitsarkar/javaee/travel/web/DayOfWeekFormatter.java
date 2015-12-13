package name.abhijitsarkar.javaee.travel.web;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.DayOfWeek;
import java.util.Locale;

/**
 * @author Abhijit Sarkar
 */
public class DayOfWeekFormatter implements Formatter<DayOfWeek> {
    @Override
    public DayOfWeek parse(String dayOfWeek, Locale locale) throws ParseException {
        return DayOfWeek.of(Integer.valueOf(dayOfWeek));
    }

    @Override
    public String print(DayOfWeek dayOfWeek, Locale locale) {
        return dayOfWeek.toString();
    }
}
