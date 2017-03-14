package testex;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Rihards
 */
public class DateFormatterTest {

    Date date = new Date();

    public DateFormatterTest() {
    }
    IDateFormatter formatter;
    
    @Before
    public void setUp () {
        formatter = new DateFormatter();
    }

    @Test
    public void testGetFormattedDate() throws Exception {
        System.out.println("getFormattedDate");
        String timeZone = "Europe/Copenhagen";
        String dateTimeFormat = "dd MMM yyyy hh:mm aa";
        Date time = new Date();
        SimpleDateFormat simpleFormat = new SimpleDateFormat(dateTimeFormat);
        simpleFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        String expResult = simpleFormat.format(time);
        String result = formatter.getFormattedDate(timeZone, time);
        assertEquals(expResult, result);
    }
    
    @Test(expected = JokeException.class)
    public void testWrongTimeZone() throws Exception {
        String timeZone = "non time zone";
        Date time = new Date();
        String result = formatter.getFormattedDate(timeZone, time);
    }

}
