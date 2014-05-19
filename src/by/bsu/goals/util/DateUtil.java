package by.bsu.goals.util;

import by.bsu.goals.log.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil
{

    public static final String DATE_TEMPLATE = "dd MMM yyyy";
    private static DateFormat simpleDateFormat;
    private static Logger logger = new Logger(DateUtil.class);

    public static Date parseStringToDate(String template, String target)
    {
        simpleDateFormat = new SimpleDateFormat(template);
        Date result;
        try
        {
            result = simpleDateFormat.parse(target);
        } catch (ParseException e)
        {
            logger.e("Exception rised when string parsed to date", e);
            throw new RuntimeException();
        }
        return result;
    }

    public static String formatDateToString(String template, Date target)
    {
        simpleDateFormat = new SimpleDateFormat(template);
        String result = simpleDateFormat.format(target);
        return result;
    }

    public static String dateToDMmYyy(long timestamp)
    {
        simpleDateFormat = new SimpleDateFormat(DATE_TEMPLATE);
        String result = simpleDateFormat.format(new Date(timestamp));
        return result;
    }

    public static boolean isValidDates(String startDate, String finichDate)
    {
        Date stDate = parseStringToDate(DATE_TEMPLATE,
                startDate);
        Date fnDate = parseStringToDate(DATE_TEMPLATE,
                finichDate);
        if (fnDate.compareTo(stDate) == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
